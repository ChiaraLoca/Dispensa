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
import ch.suspi.simulator.sensors.barcode.Barcode;
import java.io.IOException;
import static java.lang.Thread.sleep;
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
        Stazione stazioneIngresso= new Stazione(grovePi,"ingresso");
        Stazione stazioneUscita = new Stazione(grovePi,"uscita");
        //ProdottoInScadenza prodottoInScadenza = new ProdottoInScadenza(grovePi);
        
        ElencoProdotti elencoProdotti = new ElencoProdotti();
        
        boolean running = true;
        int maxTimeout =20;
        
        
       scaffaleNormale.startMonitor();
       scaffaleBuio.startMonitor();
        scaffaleFrigorifero.startMonitor();
        scaffaleCongelatore.startMonitor();
        stazioneIngresso.startMonitor();
        stazioneUscita.startMonitor();
        
        
        /*InfluxConnector ic = null; 
        try {
            ic = ScaffaleDb.connection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
         
        /*Measurement scaffaleNormaleMeasurement =  ScaffaleDb.scaffaleNormaleMeasurement(ic);
        Measurement scaffaleBuioMeasurement =  ScaffaleDb.scaffaleBuioMeasurement(ic);
        Measurement scaffaleFrigoriferoMeasurement = ScaffaleDb.scaffaleFrigoriferoMeasurement(ic);
        Measurement scaffaleCongelatoreMeasurement =  ScaffaleDb.scaffaleCongelatoreMeasurement(ic);*/
        
        /*Measurement scaffaleBuioLuceMeasuremen = ScaffaleDb.scaffaleBuioLuminositaMeasurement(ic);
        Measurement scaffaleFigoriferoLuceMeasuremen = ScaffaleDb.frigoriferoLuminosita(ic);
        Measurement scaffaleCongelatoreLuceMeasuremen = ScaffaleDb.congelatoreLuminositaMeasurement(ic);*/
        
        //Measurement stazioneIngreassoMeasurement = StazioneDb.stazioneIngresso(ic);
        //Measurement stazioneUscitaMeasurement = StazioneDb.stazioneUscita(ic);
        
       //Measurement prodottottoMeasurment =TipoProdottoDb.tipoProdottoBarcode(ic); 
        
        
       
        
        Prodotto prodottoSenzaScaffaleUscita=null;
        Prodotto prodottoSenzaScaffaleIngresso=null;
        
        while(running)
        {
            
            //ScaffaleDb.scaffaleNormaleSave(scaffaleNormaleMeasurement,scaffaleNormale.getTemperature(),scaffaleNormale.getHumidity());
           
            /*ScaffaleDb.scaffaleBuioSave(scaffaleBuioMeasurement,scaffaleBuio.getTemperature(),scaffaleBuio.getHumidity());
            ScaffaleDb.scaffaleBuoioLuminositaSave(scaffaleBuioLuceMeasuremen,scaffaleBuio.getLight());
            
            ScaffaleDb.frigoriferoSave(scaffaleFrigoriferoMeasurement, scaffaleFrigorifero.getTemperature(), scaffaleFrigorifero.getHumidity());
            ScaffaleDb.frigoriferoLuminositaSave(scaffaleFigoriferoLuceMeasuremen, scaffaleFrigorifero.getLight());
            
            ScaffaleDb.congelatoreSave(scaffaleCongelatoreMeasurement, scaffaleCongelatore.getTemperature(), scaffaleCongelatore.getHumidity());
            ScaffaleDb.congelatoreLuminositaSave(scaffaleCongelatoreLuceMeasuremen, scaffaleCongelatore.getLight());*/
        
             stazioneIngresso.setColor("BIANCO");
             stazioneIngresso.setText("");
             
             stazioneUscita.setColor("BIANCO");
             stazioneUscita.setText("");
            
           
           
            
            
            
            
            
            
           
           
            
                controlloStazioneIngresso(prodottoSenzaScaffaleIngresso, stazioneIngresso, maxTimeout, elencoProdotti);
                controlloStazioneUscita(prodottoSenzaScaffaleIngresso, stazioneIngresso, maxTimeout, elencoProdotti);


                controlloScaffaliIngresso(stazioneIngresso, scaffaleNormale, prodottoSenzaScaffaleIngresso);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleBuio, prodottoSenzaScaffaleIngresso);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleFrigorifero, prodottoSenzaScaffaleIngresso);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleCongelatore, prodottoSenzaScaffaleIngresso);

                
                controlloScaffaliUscita(stazioneUscita, scaffaleNormale, prodottoSenzaScaffaleUscita);
                controlloScaffaliUscita(stazioneUscita, scaffaleBuio, prodottoSenzaScaffaleUscita);
                controlloScaffaliUscita(stazioneUscita, scaffaleFrigorifero, prodottoSenzaScaffaleUscita);
                controlloScaffaliUscita(stazioneUscita, scaffaleCongelatore, prodottoSenzaScaffaleUscita);
            
            
            
            
           
            
           
            
            
            
            
            
            
            
            
            /*if(strazioneUscita.getPeso()>0)
            {
                System.out.println("Rilevato peso in uscita");
            }*/
            Thread.sleep(5000);
            
        }
        
        
        
    }
    
    static void controlloScaffaliIngresso(Stazione stazione,Scaffale scaffale,Prodotto prodottoSenzaScaffaleIngresso)
    {
            String tipo = scaffale.getNome();
        System.out.println(tipo+"-------------------------------------------------------------");
            double pesoSNattuale=0;
        try {
            pesoSNattuale = scaffale.getPeso();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println(""+pesoSNattuale);
            if(prodottoSenzaScaffaleIngresso!=null && pesoSNattuale>scaffale.getPesoAttuale())
            {
                double variazionePeso = pesoSNattuale-scaffale.getPesoAttuale();
                if(prodottoSenzaScaffaleIngresso.getPeso()==variazionePeso )
                {
                    if(prodottoSenzaScaffaleIngresso.getTipoProdotto().getListaCategorie().get(0).equals(tipo))
                    {
                        System.out.println("prodtoto aggiunto allo scaffale "+tipo);
                        scaffale.addPesoAttuale(prodottoSenzaScaffaleIngresso.getPeso());
                        //db prodotto aggiunto
                        prodottoSenzaScaffaleIngresso = null;
                        try {
                            stazione.setText("PRODOTO SET "+tipo.toUpperCase());
                            stazione.setColor("VERDE");
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    else
                    {
                        try {
                            stazione.setText("PRODOTO NON VA QUI");
                            stazione.setColor("ROSSO");
                        } catch (IOException ex) {
                            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                    }
                    
                    
                }
            }



    }
    
    static void controlloScaffaliUscita(Stazione stazione,Scaffale scaffale,Prodotto prodottoSenzaScaffaleUscita)
    {
    double pesoSNattuale=0;
        try {
            pesoSNattuale = scaffale.getPeso();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
            if((prodottoSenzaScaffaleUscita!=null && pesoSNattuale<scaffale.getPesoAttuale()))
            {
                double variazionePeso = scaffale.getPesoAttuale()-pesoSNattuale;
                    if(prodottoSenzaScaffaleUscita.getPeso()==variazionePeso)
                    {
                        System.out.println("prodtoto rimosso allo scaffale normale");
                        scaffale.subPesoAttuale(prodottoSenzaScaffaleUscita.getPeso());
                        //db prodotto rimosso
                        prodottoSenzaScaffaleUscita = null;
                    try {
                        stazione.setText("PRODOTO RIMOSSO");
                        stazione.setColor("VERDE");
                    } catch (IOException ex) {
                        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                    }
            }
    }
    
    static void controlloStazioneIngresso(Prodotto prodottoSenzaScaffaleIngresso,Stazione stazioneIngresso,int maxTimeout,ElencoProdotti elencoProdotti) 
            throws IOException, InterruptedException
    {
        TipoProdotto prodottoIngresso = null;
            double prodottoIngressoPeso;
            
            
            if(prodottoSenzaScaffaleIngresso==null && (prodottoIngressoPeso=stazioneIngresso.getPeso())>0)
            {
                
                int tempo=0;
                Barcode b= null;
                while((b = stazioneIngresso.getRandomBarcodeSimulator())==null)
                {
                    if(tempo == maxTimeout)
                        break;
                    stazioneIngresso.setText("ASPETTO BARCODE PRODOTTO "+(maxTimeout-tempo));
                    stazioneIngresso.setColor("BLU");
                    tempo++;
                    sleep(1000);
                }
                if(tempo == maxTimeout)
                {
                    stazioneIngresso.setText("ERRORE TEMPO SCADUTO");
                    stazioneIngresso.setColor("ROSSO");
                    prodottoIngressoPeso=-1;
                    
                }
                else
                {
                    prodottoIngresso=elencoProdotti.cerca(b);
                    if(prodottoIngresso!=null)
                    {
                        stazioneIngresso.setText("PRODOTTO RICONOSCIUTO SCAFFALE "+prodottoIngresso.getListaCategorie().get(0));
                        stazioneIngresso.setColor("VERDE");
                        
                        prodottoSenzaScaffaleIngresso = new Prodotto(prodottoIngresso,prodottoIngressoPeso);
                        //add info db
                        //stazioneIngreassoMeasurement.save(prodottoIngressoPeso,b.getText());
                        
                        
                    }
                    else if(prodottoIngresso==null)
                    {
                        stazioneIngresso.setText("PRODOTTO NON RICONOSCIUTO");
                        stazioneIngresso.setColor("ROSSO");
                        prodottoIngressoPeso=-1;
                        

                    }
                }
                    
   
            }
            else if(prodottoSenzaScaffaleIngresso!=null)
            {
                stazioneIngresso.setText("RIPONI IL PRODOTTO");
                stazioneIngresso.setColor("VIOLA");
                System.out.println("Metti aposto il prodotto di prima");
            }
    
    }
    
    
    static void controlloStazioneUscita(Prodotto prodottoSenzaScaffaleUscita,Stazione stazioneUscita,int maxTimeout,ElencoProdotti elencoProdotti) 
            throws IOException, InterruptedException
    {
         TipoProdotto prodottoUscita = null;
            double prodottoUscitaPeso;
            
          
            if(prodottoSenzaScaffaleUscita==null&&(prodottoUscitaPeso=stazioneUscita.getPeso())>0)
            {
                
                int tempo=0;
                Barcode b= null;
                while((b = stazioneUscita.getRandomBarcodeSimulator())==null)
                {
                    if(tempo == maxTimeout)
                        break;
                    stazioneUscita.setText("ASPETTO BARCODE PRODOTTO "+(maxTimeout-tempo));
                    stazioneUscita.setColor("BLU");
                    tempo++;
                    sleep(1000);
                }
                if(tempo == maxTimeout)
                {
                    stazioneUscita.setText("ERRORE TEMPO SCADUTO");
                    stazioneUscita.setColor("ROSSO");
                    
                }
                else
                {
                    TipoProdotto t=elencoProdotti.cerca(b);
                    if(t!=null)
                    {
                        stazioneUscita.setText("PRODOTTO RICONOSCIUTO "+t.getListaCategorie().get(0));
                        stazioneUscita.setColor("VERDE");
                        
                        prodottoSenzaScaffaleUscita = new Prodotto(prodottoUscita,prodottoUscitaPeso);
                        
                        //stazioneUscitaMeasurement.save(p,b.getText());
                    }
                    else if(t==null)
                    {
                        stazioneUscita.setText("PRODOTTO NON RICONOSCIUTO");
                        stazioneUscita.setColor("ROSSO");

                    }
                }
                    
   
            }
            else if(prodottoSenzaScaffaleUscita!=null)
            {
                stazioneUscita.setText("PORTA FUORI PRODOTTO");
                stazioneUscita.setColor("VIOLA");
                System.out.println("Metti aposto il prodotto di prima");
            }
    
    
    }
}

