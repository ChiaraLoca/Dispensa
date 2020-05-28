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
public class TipoProdottoDb {
    
      public static InfluxConnector connection() throws Exception {
        InfluxConnector ic =null;
  try {      
    ic = InfluxConnector.getInstance();
    ic.init("http://localhost:8086", "TipoProdottoDB", "root", "root");
     } catch (DatabaseSessionException ex){}
  return ic;
     }
   
      public static Measurement tipoProdottoBarcode (InfluxConnector ic) { 
    Measurement TpBar = ic.createMeasurement("Barcode", "autogen");
    TpBar.addField("Barcode tipo prodotto", FieldDesc.Type.STRING);
    TpBar.addTag("leggi");
    return TpBar;
 }

public static void tipoProdottoBarcodeSave (Measurement m, String barcode){
    m.save(barcode);
 }
}


