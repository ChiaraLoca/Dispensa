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
import ch.suspi.simulator.sensors.digital.temphumidity.TemperatureAndHumiditySimulator;
import ch.suspi.simulator.sensors.analog.light.LightSensorSimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;
import org.iot.raspberry.grovepi.sensors.data.GroveTemperatureAndHumidityValue;
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
    Measurement SnTempHum = ic.createMeasurement("Temperatura e umidita", "autogen");
    SnTempHum.addField("Temperatura sacffale normale", FieldDesc.Type.NUMBER);
    SnTempHum.addField("Umidita sacffale normale", FieldDesc.Type.NUMBER);
    SnTempHum.addTag("controlla");
    return SnTempHum;
}
public static void scaffaleNormaleSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement scaffaleBuioMeasurement(InfluxConnector ic) {
    Measurement SbTempHum = ic.createMeasurement("Temperatura e umidita", "autogen");
    SbTempHum.addField("Temperatura sacffale buio", FieldDesc.Type.NUMBER);
    SbTempHum.addField("Umidita sacffale buio", FieldDesc.Type.NUMBER);
    SbTempHum.addTag("controlla");
    return SbTempHum;
}

public static void scaffaleBuioSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement scaffaleBuioLuminositaMeasurement(InfluxConnector ic) {
    Measurement SbLum = ic.createMeasurement("Luminosita", "autogen");
    SbLum.addField("Luminosita sacffale buio", FieldDesc.Type.NUMBER);
    SbLum.addTag("controlla");
    return SbLum;
}

public static void scaffaleBuoioLuminositaSave (Measurement m, double luminosita){
    m.save(luminosita);
}

public static Measurement frigorigorifereroMeasurement(InfluxConnector ic) {
    Measurement FrTempHum = ic.createMeasurement("Temperatura e umidita", "autogen");
    FrTempHum.addField("Temperatura Frigorifero", FieldDesc.Type.NUMBER);
    FrTempHum.addField("Umidita Frigorifero", FieldDesc.Type.NUMBER);
    FrTempHum.addTag("controlla");
    return FrTempHum;
}
    
public static void frigoriferoSave(Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}

public static Measurement frigoriferoLuminosita(InfluxConnector ic){
    Measurement FrLum = ic.createMeasurement("Luminosita", "autogen");
    FrLum.addField("Luminosita frigorifero", FieldDesc.Type.NUMBER);
    FrLum.addTag("controlla");
    return FrLum;
}

public static void frigoriferoLuminositaSave (Measurement m, double luminosita){
    m.save(luminosita);
}

public static Measurement congelatoreMeasurement(InfluxConnector ic){
    Measurement CnTempHum = ic.createMeasurement("Temperatura e umidita", "autogen");
    CnTempHum.addField("Temperatura congelatore", FieldDesc.Type.NUMBER);
    CnTempHum.addField("Umidita congelatore", FieldDesc.Type.NUMBER);
    CnTempHum.addTag("controlla");
    return CnTempHum;
}
    public static void congelatoreSave (Measurement m, double temperatura, double umidita){
    m.save(temperatura, umidita);
}
    
   public static Measurement congelatoreLuminositaMeasurement(InfluxConnector ic){
    Measurement CnLum = ic.createMeasurement("Luminosita", "autogen");
    CnLum.addField("Luminosita congelatore", FieldDesc.Type.NUMBER);
    CnLum.addTag("controlla");
    return CnLum;
   }
    
   public static void congelatoreLuminositaSave (Measurement m, double luminosita){
    m.save(luminosita);
   }
}
