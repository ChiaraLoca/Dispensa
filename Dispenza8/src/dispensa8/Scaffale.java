/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensa8;

import ch.suspi.simulator.sensors.analog.light.LightSensorSimulator;
import ch.suspi.simulator.sensors.analog.loadcell.LoadCellSimulator;
import ch.suspi.simulator.sensors.digital.temphumidity.TemperatureAndHumiditySimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.sensors.data.GroveTemperatureAndHumidityValue;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;

/**
 *
 * @author Chiara
 */
abstract class Scaffale {
   
    static public int numeroPin = 0;
    private final GrovePi grovePi;
    private final TemperatureAndHumiditySimulator temperatureAndHumiditySimulator;
    private final LoadCellSimulator loadCellSimulator;
    private final String nome;
    
    private final SensorMonitor monitorTemperatureAndHumiditySimulator;
    private final SensorMonitor monitorLoadCellSimulator;
    
    
    public Scaffale(GrovePi grovePi) throws IOException
    {
        this.grovePi = grovePi;
        temperatureAndHumiditySimulator = new TemperatureAndHumiditySimulator(this.grovePi,numeroPin,null);
        numeroPin++;
        loadCellSimulator = new LoadCellSimulator(grovePi, numeroPin);
        numeroPin++;
        this.nome = null;
        monitorTemperatureAndHumiditySimulator = new SensorMonitor(temperatureAndHumiditySimulator,100);
        monitorLoadCellSimulator = new SensorMonitor(loadCellSimulator, 100);
    }
    public Scaffale(GrovePi grovePi,String nome) throws IOException
    {
        this.grovePi = grovePi;
        temperatureAndHumiditySimulator = new TemperatureAndHumiditySimulator(this.grovePi,numeroPin,null);
        numeroPin++;
        loadCellSimulator = new LoadCellSimulator(grovePi, numeroPin);
        numeroPin++;
        this.nome = nome;
        monitorTemperatureAndHumiditySimulator = new SensorMonitor(temperatureAndHumiditySimulator,100);
        monitorLoadCellSimulator = new SensorMonitor(loadCellSimulator, 100);
    }
    
    public void startMonitor()
    {
        monitorTemperatureAndHumiditySimulator.start();
        monitorLoadCellSimulator.start();
    }
    
    public void stopMonitor()
    {
        monitorTemperatureAndHumiditySimulator.stop();
        monitorLoadCellSimulator.stop();
    }
    
    public GrovePi getGrovePi()
    {
        return grovePi;
    }

    public String getNome()
    {
        return nome;
    }
    public double getTemperature() throws IOException
    {
        GroveTemperatureAndHumidityValue t =(GroveTemperatureAndHumidityValue)monitorTemperatureAndHumiditySimulator.getValue();
        return t.getTemperature();
    }

    public double getHumidity() throws IOException
    {
        GroveTemperatureAndHumidityValue t =(GroveTemperatureAndHumidityValue)monitorTemperatureAndHumiditySimulator.getValue();
        return t.getHumidity();
    }
    
        public double getLoadCellSimulator() throws IOException
    {
        
        return (double) monitorLoadCellSimulator.getValue();
    }
        
    
    
    
    @Override
    public String toString()
    {
        String str="";
        try {
            str= "nome: "+nome+", Temperatura: "+getTemperature()+", Umidità: "+getHumidity()+", Peso: "+getLoadCellSimulator();
        } catch (IOException ex) {
            Logger.getLogger(Scaffale.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return str;
    }
    
        
}

class ScaffaleNormale extends Scaffale
{
   
    public ScaffaleNormale(GrovePi grovePi) throws IOException{
        super(grovePi,"Normale");
    }
}

class ScaffaleBuio extends Scaffale
{
    private final LightSensorSimulator lightSensorSimulator; 
    
    private final SensorMonitor monitorLightSensorSimulator;
    
    public ScaffaleBuio(GrovePi grovePi) throws IOException {
        super(grovePi,"Buio");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
        monitorLightSensorSimulator = new SensorMonitor(lightSensorSimulator, 100);
    }
    @Override
    public void startMonitor()
    {
        super.startMonitor();
        monitorLightSensorSimulator.start();
    }
    @Override
    public void stopMonitor()
    {
        super.stopMonitor();
        monitorLightSensorSimulator.stop();
    }
    
    
    
    public LightSensorSimulator getLightSensorSimulator()
    {
        return lightSensorSimulator;
    }
    public double getLight()
    {
        return (double) monitorLightSensorSimulator.getValue();
    }
    
    @Override
    public String toString()
    {
        String str=""; 
        try {
            str =  super.toString() +", Luce: "+ getLightSensorSimulator().get();
        } catch (IOException ex) {
            Logger.getLogger(ScaffaleCongelatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
    
}


class ScaffaleFrigorifero extends Scaffale
{
    private final LightSensorSimulator lightSensorSimulator; 
    private final SensorMonitor monitorLightSensorSimulator;
    public ScaffaleFrigorifero(GrovePi grovePi) throws IOException {
        super(grovePi,"Frigorifero");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
    
        monitorLightSensorSimulator = new SensorMonitor(lightSensorSimulator, 100);
    }
    
        @Override
    public void startMonitor()
    {
        super.startMonitor();
        monitorLightSensorSimulator.start();
    }
    @Override
    public void stopMonitor()
    {
        super.stopMonitor();
        monitorLightSensorSimulator.stop();
    }
    
    public LightSensorSimulator getLightSensorSimulator()
    {
        return lightSensorSimulator;
    }
    
    public double getLight()
    {
        return (double) monitorLightSensorSimulator.getValue();
    }
    
    @Override
    public String toString()
    {
        String str=""; 
        try {
            str =  super.toString() +", Luce: "+ getLightSensorSimulator().get();
        } catch (IOException ex) {
            Logger.getLogger(ScaffaleCongelatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
}

class ScaffaleCongelatore extends Scaffale
{
    private final LightSensorSimulator lightSensorSimulator; 
    private final SensorMonitor monitorLightSensorSimulator;
    
    public ScaffaleCongelatore(GrovePi grovePi) throws IOException {
        super(grovePi,"Congelatore");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
        
        monitorLightSensorSimulator = new SensorMonitor(lightSensorSimulator, 100);
    
    }
    @Override
    public void startMonitor()
    {
        super.startMonitor();
        monitorLightSensorSimulator.start();
    }
    @Override
    public void stopMonitor()
    {
        super.stopMonitor();
        monitorLightSensorSimulator.stop();
    }
    public double getLight()
    {
        return (double) monitorLightSensorSimulator.getValue();
    }
    public LightSensorSimulator getLightSensorSimulator()
    {
        return lightSensorSimulator;
    }
    
    @Override
    public String toString()
    {
        String str=""; 
        try {
            str =  super.toString() +", Luce: "+ getLightSensorSimulator().get();
        } catch (IOException ex) {
            Logger.getLogger(ScaffaleCongelatore.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }
}

