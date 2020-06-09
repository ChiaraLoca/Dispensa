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
public class ScaffaleDb {      
    public static InfluxConnector connection() throws Exception {
        InfluxConnector ic =null;
  try {      
    ic = InfluxConnector.getInstance();
    ic.init("http://localhost:8086", "DispensaDB", "root", "root");
     } catch (DatabaseSessionException ex){}
  return ic;
    }
    
public static Measurement scaffaleNormaleMeasurement(InfluxConnector ic) {
    Measurement SnTempHum = ic.createMeasurement("ScaffaleNormale_Temp_Hud", "autogen");
    SnTempHum.addField("Temperatura sacffale normale", FieldDesc.Type.NUMBER);
    SnTempHum.addField("Umidita sacffale normale", FieldDesc.Type.NUMBER);
    
    return SnTempHum;
}
public static void scaffaleNormaleSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement scaffaleNormalePeso (InfluxConnector ic) { 
    Measurement SnPeso = ic.createMeasurement("ScaffaleNormale_Peso", "autogen");
    SnPeso.addField("Peso prodotto scaffale normale", FieldDesc.Type.NUMBER);
    
    return SnPeso;
 }

public static void scaffaleNormalePesoSave (Measurement m, double peso){
    m.save(peso);
}

public static Measurement scaffaleBuioMeasurement(InfluxConnector ic) {
    Measurement SbTempHum = ic.createMeasurement("ScaffaleBuio_Temp_Hud", "autogen");
    SbTempHum.addField("Temperatura sacffale buio", FieldDesc.Type.NUMBER);
    SbTempHum.addField("Umidita sacffale buio", FieldDesc.Type.NUMBER);
   
    return SbTempHum;
}

public static void scaffaleBuioSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement scaffaleBuioLuminositaMeasurement(InfluxConnector ic) {
    Measurement SbLum = ic.createMeasurement("ScaffaleBuio_Luce", "autogen");
    SbLum.addField("Luminosita sacffale buio", FieldDesc.Type.NUMBER);
    
    return SbLum;
}

public static void scaffaleBuoioLuminositaSave (Measurement m, double luminosita){
    m.save(luminosita);
}

public static Measurement scaffaleBuioPeso (InfluxConnector ic) { 
    Measurement SbPeso = ic.createMeasurement("ScaffaleBuio_Peso", "autogen");
    SbPeso.addField("Peso prodotto scaffale buio", FieldDesc.Type.NUMBER);
    
    return SbPeso;
 }

public static void scaffaleBuioPesoSave (Measurement m, double peso){
    m.save(peso);
}

public static Measurement scaffaleFrigoriferoMeasurement(InfluxConnector ic) {
    Measurement FrTempHum = ic.createMeasurement("ScaffaleFrigorifero_Temp_Hud", "autogen");
    FrTempHum.addField("Temperatura Frigorifero", FieldDesc.Type.NUMBER);
    FrTempHum.addField("Umidita Frigorifero", FieldDesc.Type.NUMBER);
    
    return FrTempHum;
}
    
public static void scaffaleFrigoriferoSave(Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement scaffaleFrigoriferoLuceMeasurement(InfluxConnector ic){
    Measurement FrLum = ic.createMeasurement("ScaffaleFrigorifero_Luce", "autogen");
    FrLum.addField("Luminosita frigorifero", FieldDesc.Type.NUMBER);
    
    return FrLum;
}

public static void scaffaleFrigoriferoLuceSave (Measurement m, double luminosita){
    m.save(luminosita);
}

public static Measurement scaffaleFriogoriferoPeso (InfluxConnector ic) { 
    Measurement FrPeso = ic.createMeasurement("ScaffaleFrigorifero_Peso", "autogen");
    FrPeso.addField("Peso prodotto scaffale frigorifero", FieldDesc.Type.NUMBER);
    return FrPeso;
 }

public static void scaffaleFrigoriferoPesoSave (Measurement m, double peso){
    m.save(peso);
}

public static Measurement scaffaleCongelatoreMeasurement(InfluxConnector ic){
    Measurement CnTempHum = ic.createMeasurement("ScaffaleCongelatore_Temp_Hud", "autogen");
    CnTempHum.addField("Temperatura congelatore", FieldDesc.Type.NUMBER);
    CnTempHum.addField("Umidita congelatore", FieldDesc.Type.NUMBER);
    return CnTempHum;
}
    public static void scaffaleCongelatoreSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}
    
   public static Measurement scaffaleCongelatoreLuceMeasurement(InfluxConnector ic){
    Measurement CnLum = ic.createMeasurement("ScaffaleCongelatore_Luce", "autogen");
    CnLum.addField("Luminosita congelatore", FieldDesc.Type.NUMBER);
    return CnLum;
   }
    
   public static void scaffaleCongelatoreLuceSave (Measurement m, double luminosita){
    m.save(luminosita);
   }
   
   public static Measurement scaffaleCongelatorePesoMeasurement (InfluxConnector ic) { 
    Measurement CnPeso = ic.createMeasurement("ScaffaleCongelatore_Peso", "autogen");
    CnPeso.addField("Peso prodotto scaffale congelatore", FieldDesc.Type.NUMBER);
    return CnPeso;
 }

public static void scaffaleCongelatorePesoSave (Measurement m, double peso){
    m.save(peso);
 }

}
