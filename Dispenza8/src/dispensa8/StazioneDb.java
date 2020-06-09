/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensa8;

import ch.industry4_0.dm.FieldDesc;
import ch.industry4_0.dm.Measurement;
import ch.industry4_0.exception.DatabaseSessionException;
import ch.industry4_0.influx.connector.InfluxConnector;
/**
 *
 * @author Riccardo
 */
public class StazioneDb {
    
     public static InfluxConnector connection() throws Exception {
        InfluxConnector ic =null;
  try {      
    ic = InfluxConnector.getInstance();
    ic.init("http://localhost:8086", "StazioneDB", "root", "root");
     } catch (DatabaseSessionException ex){}
  return ic;
     }
     
public static Measurement stazioneMeasurement (InfluxConnector ic) { 
    Measurement StazIng = ic.createMeasurement("Peso barcode stazione ingresso", "autogen");
    StazIng.addField("Peso prodotto in ingresso", FieldDesc.Type.NUMBER);
    StazIng.addField("Barcode prodotto in ingresso", FieldDesc.Type.STRING);
    
    StazIng.addTag("Tipo");
    StazIng.addTag("Accettato");
    return StazIng;
 }

public static void stazioneSave (Measurement m, double peso, String barcode,String tipo,String accettato){
    m.save(peso, barcode,tipo, accettato);
    //tipo = ingresso o uscita
    //accettato = accettato o nonAccettato
}
}

