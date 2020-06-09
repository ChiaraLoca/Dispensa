/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensa8;

import ch.industry4_0.dm.FieldDesc;
import ch.industry4_0.dm.Measurement;

import ch.industry4_0.influx.connector.InfluxConnector;
/**
 *
 * @author Riccardo
 */
public class TipoProdottoDb {
    

   
     public static Measurement prodottoMeasurement (InfluxConnector ic) { 
    Measurement TpBar = ic.createMeasurement("Barcode", "autogen");
    TpBar.addField("Barcode tipo prodotto", FieldDesc.Type.STRING);
    TpBar.addField("Peso prodotto", FieldDesc.Type.NUMBER);
    TpBar.addTag("Scaffale");
    TpBar.addTag("Azione");
    return TpBar;
 }

public static void prodottoSave (Measurement m, String barcode,Double peso,String scaffale,String azione){
    m.save(barcode,peso,scaffale,azione);//azione = aggiunto o rimosso
 }
}


