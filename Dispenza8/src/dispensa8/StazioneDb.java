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
     
public static Measurement stazioneIngressoPeso (InfluxConnector ic) { 
    Measurement StazIngPeso = ic.createMeasurement("Peso", "autogen");
    StazIngPeso.addField("Peso prodotto in ingresso", FieldDesc.Type.NUMBER);
    StazIngPeso.addTag("controlla");
    return StazIngPeso;
 }

public static void stazioneIngressoPesoSave (Measurement m, double peso){
    m.save(peso);
}

public static Measurement stazioneUscitaPeso (InfluxConnector ic) { 
    Measurement StazUscPeso = ic.createMeasurement("Peso", "autogen");
    StazUscPeso.addField("Peso prodotto in uscita", FieldDesc.Type.NUMBER);
    StazUscPeso.addTag("controlla");
    return StazUscPeso;
 }

public static void stazioneUscitaPesoSave (Measurement m, double peso){
    m.save(peso);
}

public static Measurement stazioneIngressoBarcode (InfluxConnector ic) { 
    Measurement StazIngBar = ic.createMeasurement("Barcode", "autogen");
    StazIngBar.addField("Barcode prodotto in ingresso", FieldDesc.Type.STRING);
    StazIngBar.addTag("leggi");
    return StazIngBar;
 }

public static void stazioneIngressoBarcodeSave (Measurement m, String barcode){
    m.save(barcode);
}

public static Measurement stazioneUscitaBarcode (InfluxConnector ic) { 
    Measurement StazUscBar = ic.createMeasurement("Barcode", "autogen");
    StazUscBar.addField("Barcode prodotto in ingresso", FieldDesc.Type.STRING);
    StazUscBar.addTag("leggi");
    return StazUscBar;
 }

public static void stazioneUscitaBarcodeSave (Measurement m, String barcode){
    m.save(barcode);
}

}

