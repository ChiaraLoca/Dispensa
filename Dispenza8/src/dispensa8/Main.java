/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensa8;

import ch.industry4_0.dm.Measurement;
import ch.industry4_0.exception.DatabaseSessionException;
import ch.industry4_0.influx.connector.InfluxConnector;
import ch.suspi.simulator.grove.GrovePiSimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;

/**
 *
 * @author Chiara
 */
public class Main {
    
   
     
        
        
    
    public static void main(String[] args) throws IOException, InterruptedException, DatabaseSessionException {
        
        Logger.getLogger("GrovePi").setLevel(Level.WARNING);
        Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);

        GrovePi grovePi = new GrovePiSimulator();
        
        ScaffaleNormale scaffaleNormale = new ScaffaleNormale(grovePi);
        ScaffaleBuio scaffaleBuio = new ScaffaleBuio(grovePi);
        ScaffaleFrigorifero scaffaleFrigorifero = new ScaffaleFrigorifero(grovePi);
        ScaffaleCongelatore scaffaleCongelatore = new ScaffaleCongelatore(grovePi);
        Stazione strazioneIngeresso= new Stazione(grovePi,"ingresso");
        Stazione strazioneUscita = new Stazione(grovePi,"uscita");
        ProdottoInScadenza prodottoInScadenza = new ProdottoInScadenza(grovePi);
        
        boolean running = true;
        
        scaffaleNormale.startMonitor();
        scaffaleBuio.startMonitor();
        scaffaleFrigorifero.startMonitor();
        scaffaleCongelatore.startMonitor();
        strazioneIngeresso.startMonitor();
        strazioneUscita.startMonitor();
        
        
        InfluxConnector ic = null; 
        try {
            ic = ScaffaleDb.connection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        Measurement scaffaleNormaleMeasurement =  ScaffaleDb.scaffaleNormaleMeasurement(ic);
        Measurement scaffaleBuioMeasurement =  ScaffaleDb.scaffaleBuioMeasurement(ic);
        Measurement scaffaleFrigoriferoMeasurement = ScaffaleDb.scaffaleFrigoriferoMeasurement(ic);
        Measurement scaffaleCongelatoreMeasurement =  ScaffaleDb.scaffaleCongelatoreMeasurement(ic);
        
        Measurement scaffaleBuioLuceMeasuremen = ScaffaleDb.scaffaleBuioLuminositaMeasurement(ic);
        Measurement scaffaleFigoriferoLuceMeasuremen = ScaffaleDb.frigoriferoLuminosita(ic);
        Measurement scaffaleCongelatoreLuceMeasuremen = ScaffaleDb.congelatoreLuminositaMeasurement(ic);
        
        
        
        while(running)
        {
            
            ScaffaleDb.scaffaleNormaleSave(scaffaleNormaleMeasurement,scaffaleNormale.getTemperature(),scaffaleNormale.getHumidity());
           
            ScaffaleDb.scaffaleBuioSave(scaffaleBuioMeasurement,scaffaleBuio.getTemperature(),scaffaleBuio.getHumidity());
            ScaffaleDb.scaffaleBuoioLuminositaSave(scaffaleBuioLuceMeasuremen,scaffaleBuio.getLight());
            
            ScaffaleDb.frigoriferoSave(scaffaleFrigoriferoMeasurement, scaffaleFrigorifero.getTemperature(), scaffaleFrigorifero.getHumidity());
            ScaffaleDb.frigoriferoLuminositaSave(scaffaleFigoriferoLuceMeasuremen, scaffaleFrigorifero.getLight());
            
            ScaffaleDb.congelatoreSave(scaffaleCongelatoreMeasurement, scaffaleCongelatore.getTemperature(), scaffaleCongelatore.getHumidity());
            ScaffaleDb.congelatoreLuminositaSave(scaffaleCongelatoreLuceMeasuremen, scaffaleCongelatore.getLight());
        
        
            if(strazioneIngeresso.getPeso()>0)
            {
                System.out.println("Rilevato peso in ingresso");
            }
            
            if(strazioneUscita.getPeso()>0)
            {
                System.out.println("Rilevato peso in uscita");
            }
            
            
            Thread.sleep(1000);
            
        }
        
        
        
    }
}
