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
    
    public Scaffale(GrovePi grovePi) throws IOException
    {
        this.grovePi = grovePi;
        temperatureAndHumiditySimulator = new TemperatureAndHumiditySimulator(this.grovePi,numeroPin,null);
        numeroPin++;
        loadCellSimulator = new LoadCellSimulator(grovePi, numeroPin);
        numeroPin++;
        this.nome = null;
    }
    public Scaffale(GrovePi grovePi,String nome) throws IOException
    {
        this.grovePi = grovePi;
        temperatureAndHumiditySimulator = new TemperatureAndHumiditySimulator(this.grovePi,numeroPin,null);
        numeroPin++;
        loadCellSimulator = new LoadCellSimulator(grovePi, numeroPin);
        numeroPin++;
        this.nome = nome;
    }
    
    public GrovePi getGrovePi()
    {
        return grovePi;
    }
    public TemperatureAndHumiditySimulator getTemperatureAndHumiditySimulator()
    {
        return temperatureAndHumiditySimulator;
    }
    public String getNome()
    {
        return nome;
    }
    public double getTemperature() throws IOException
    {
        return temperatureAndHumiditySimulator.get().getTemperature();
    }

    public double getHumidity() throws IOException
    {
        return temperatureAndHumiditySimulator.get().getHumidity();
    }
    
        public double getLoadCellSimulator() throws IOException
    {
        return loadCellSimulator.get();
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
    
    public ScaffaleBuio(GrovePi grovePi) throws IOException {
        super(grovePi,"Buio");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
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


class ScaffaleFrigorifero extends Scaffale
{
    private final LightSensorSimulator lightSensorSimulator; 
    
    public ScaffaleFrigorifero(GrovePi grovePi) throws IOException {
        super(grovePi,"Frigorifero");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
    
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

class ScaffaleCongelatore extends Scaffale
{
    private final LightSensorSimulator lightSensorSimulator; 
    
    public ScaffaleCongelatore(GrovePi grovePi) throws IOException {
        super(grovePi,"Congelatore");
        this.lightSensorSimulator = new LightSensorSimulator(getGrovePi(),Scaffale.numeroPin);
        Scaffale.numeroPin++;
    
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

