
package dispensa8;


import ch.suspi.simulator.sensors.analog.loadcell.LoadCellSimulator;
import ch.suspi.simulator.sensors.barcode.Barcode;

import ch.suspi.simulator.sensors.barcode.random.RandomBarcodeSimulator;
import ch.suspi.simulator.sensors.i2c.lcd.GroveRgbLcdSimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.sensors.synch.SensorMonitor;

class Stazione {
    
    private final GrovePi grovePi;
    private final String tipo;
    private final RandomBarcodeSimulator randomBarcodeSimulator;
    private final LoadCellSimulator loadCellSimulator; 
    private final GroveRgbLcdSimulator groveRgbLcdSimulator;
    
    private final SensorMonitor monitorRandomBarcodeSimulator;
    private final SensorMonitor monitorLoadCellSimulator;
    public Stazione(GrovePi grovePi,String tipo) throws IOException
    {
        this.grovePi = grovePi;
        this.tipo =tipo;
        this.randomBarcodeSimulator = new RandomBarcodeSimulator(""+tipo);
        this.loadCellSimulator = new LoadCellSimulator(this.grovePi,Scaffale.numeroPin);
        this.groveRgbLcdSimulator = new GroveRgbLcdSimulator();
        Scaffale.numeroPin++;
        monitorRandomBarcodeSimulator= new SensorMonitor(randomBarcodeSimulator, 100);
        monitorLoadCellSimulator= new SensorMonitor(loadCellSimulator, 100);
        
        
    }
    public void startMonitor()
    {
        monitorLoadCellSimulator.start();
        monitorRandomBarcodeSimulator.start();
    }
    public void stopMonitor()
    {
        monitorLoadCellSimulator.stop();
        monitorRandomBarcodeSimulator.stop();
    }
    
    public String getTipo()
    {
        return tipo;
    }
    public Barcode getRandomBarcodeSimulator() 
    {
        return (Barcode) monitorRandomBarcodeSimulator.getValue();
    }
    
    public double getLoadCellSimulator() throws Exception
    {
        return loadCellSimulator.get();   
    }
    
    @Override
    public String toString()
    {
        String str="";
        try {
            str="Stazione d'"+tipo+", Peso:"+getLoadCellSimulator();
            if(getRandomBarcodeSimulator()!=null)
            {
                str += ", Barcode: "+getRandomBarcodeSimulator();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Stazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
    }

    public double getPeso() {
        
        if(monitorLoadCellSimulator.isValid())
            return (double) monitorLoadCellSimulator.getValue();
        else
            return 0;
    }
    
    void setText(String text) throws IOException
    {
        groveRgbLcdSimulator.setText(text);
    }
    
    void setColor(String s) throws IOException
    {
        switch(s)
        {
            case "BLU":{groveRgbLcdSimulator.setRGB(0,0,255);break;}
            case "ROSSO":{groveRgbLcdSimulator.setRGB(255,0,0);break;}
            case "VERDE":{groveRgbLcdSimulator.setRGB(50,255,50);break;}
            case "BIANCO":{groveRgbLcdSimulator.setRGB(255,255,255);break;}
            
        }
            
        
    }
}

class ProdottoInScadenza
{
    private final GrovePi grovePi;
    private final GroveRgbLcdSimulator groveRgbLcdSimulator;
    
    public ProdottoInScadenza(GrovePi grovePi)
    {
        this.grovePi = grovePi;
        this.groveRgbLcdSimulator = new GroveRgbLcdSimulator();
        
    } 
    
    void setText(String text) throws IOException
    {
        groveRgbLcdSimulator.setText(text);
    }
    
    void setColor(int r,int g, int b) throws IOException
    {
        groveRgbLcdSimulator.setRGB(r,g,b);
    }
}
