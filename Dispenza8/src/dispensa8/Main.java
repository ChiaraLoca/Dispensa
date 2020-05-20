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
        
        
        /*InfluxConnector ic = ScaffaleDb.connection(); 
        Measurement scaffaleNormaleMeasurement = new scaffaleNormaleMeasurement(ic);
        Measurement scaffaleBuioMeasurement = new scaffaleBuioMeasurement(ic);
        Measurement scaffaleFrigoriferoMeasurement = new scaffaleFrigoriferoMeasurement(ic);
        Measurement scaffaleCongelatoreMeasurement = new scaffaleCongelatoreMeasurement(ic);*/
        
        
        while(running)
        {
            
            /*scaffaleNormaleSave(scaffaleNormaleMeasurement,scaffaleNormale.getTemperature(),scascaffaleNormale.getHumidity());
           scaffaleBuioSave(scaffaleBuioMeasurement,scaffaleBuio.getTemperature(),scaffaleBuio.getHumidity(),scaffaleBuio.getLight());
            scaffaleFrigoriferoSave(scaffaleFrigoriferoMeasurement,ScaffaleFrigorifero.getTemperature(),ScaffaleFrigorifero.getHumidity(),ScaffaleFrigorifero.getLight());
        scaffaleCongelatoreSave(scaffaleCongelatoreMeasurement,ScaffaleCongelatore.getTemperature(),ScaffaleCongelatore.getHumidity(),ScaffaleCongelatore.getLight());*/
        
        
            if(strazioneIngeresso.getPeso()>0)
            {
                System.out.println("Rilevato peso in ingresso");
            }
            
            if(strazioneUscita.getPeso()>0)
            {
                System.out.println("Rilevato peso in uscita");
            }
            
        }
        
        
        
    }
}
