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
    Measurement TpBar = ic.createMeasurement("Dispensa", "autogen");
    TpBar.addField("Barcode", FieldDesc.Type.STRING);
    TpBar.addField("Peso", FieldDesc.Type.NUMBER);
    TpBar.addField("Scadenza", FieldDesc.Type.STRING);
    TpBar.addField("Totale", FieldDesc.Type.NUMBER);
    TpBar.addTag("Scaffale");
    TpBar.addTag("Azione");
    return TpBar;
 }

public static void prodottoSave (Measurement m, Prodotto prodotto,String scaffale,String azione,int totale){
    m.save(prodotto.getId(),prodotto.getPeso(),prodotto.getScadenza().toString(),totale,scaffale,azione);//azione = aggiunto o rimosso
 }
}


