
package dispensa8;


import ch.suspi.simulator.sensors.analog.loadcell.LoadCellSimulator;
import ch.suspi.simulator.sensors.barcode.Barcode;

import ch.suspi.simulator.sensors.barcode.random.RandomBarcodeSimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;

class Stazione {
    
    private final GrovePi grovePi;
    private final String tipo;
    private final RandomBarcodeSimulator randomBarcodeSimulator;
    private final LoadCellSimulator loadCellSimulator; 
    public Stazione(GrovePi grovePi,String tipo) throws IOException
    {
        this.grovePi = grovePi;
        this.tipo =tipo;
        this.randomBarcodeSimulator = new RandomBarcodeSimulator(""+tipo);
        this.loadCellSimulator = new LoadCellSimulator(this.grovePi,Scaffale.numeroPin);
        Scaffale.numeroPin++;
    }
    
    public String getTipo()
    {
        return tipo;
    }
    public String getRandomBarcodeSimulator() throws Exception
    {
        return randomBarcodeSimulator.get().toString();   
    }
    public Barcode getRandomBarcodeSimulator_B() throws Exception
    {
        return randomBarcodeSimulator.get();   
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
            if(getRandomBarcodeSimulator_B()!=null)
            {
                str += ", Barcode: "+getRandomBarcodeSimulator();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Stazione.class.getName()).log(Level.SEVERE, null, ex);
        }
        return str;
        
    }
         
    
    
}
