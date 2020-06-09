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
import static dispensa8.ScaffaleDb.scaffaleNormaleMeasurement;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;

/**
 *
 * @author Chiara
 */
public class Main {
    
    static List<Prodotto> dispensa = new ArrayList<>();
    static HashMap<String,Prodotto> dispensaHM = new HashMap<>();
    static InfluxConnector ic = null; 
    
    static Prodotto prodottoSenzaScaffaleUscita = null;
    static Prodotto prodottoSenzaScaffaleIngresso = null;
    
    static ElencoProdotti elencoProdotti = new ElencoProdotti();
     
        
        
    
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
        
       
        
        
        
        boolean running = true;
        int maxTimeout = 20;
        
        
       scaffaleNormale.startMonitor();
       scaffaleBuio.startMonitor();
        scaffaleFrigorifero.startMonitor();
        scaffaleCongelatore.startMonitor();
        stazioneIngresso.startMonitor();
        stazioneUscita.startMonitor();
        
        
        
        try {
            ic = ScaffaleDb.connection();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         
        
        

        
       
        
        
       
        

        
        while(running)
        {
             stazioneIngresso.setColor("BIANCO");
             stazioneIngresso.setText("");
             
             stazioneUscita.setColor("BIANCO");
             stazioneUscita.setText("");
            
             saveAllertScaffali(scaffaleNormale,scaffaleBuio,scaffaleFrigorifero,scaffaleCongelatore);
             
           
           
            
            
            
            
            
            
           
           
            
                controlloStazioneIngresso( stazioneIngresso, maxTimeout, elencoProdotti);
                controlloStazioneUscita( stazioneIngresso, maxTimeout, elencoProdotti);
                
                Thread.sleep(5000);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleNormale);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleBuio);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleFrigorifero);
                controlloScaffaliIngresso(stazioneIngresso, scaffaleCongelatore);

                
                controlloScaffaliUscita(stazioneUscita, scaffaleNormale);
                controlloScaffaliUscita(stazioneUscita, scaffaleBuio);
                controlloScaffaliUscita(stazioneUscita, scaffaleFrigorifero);
                controlloScaffaliUscita(stazioneUscita, scaffaleCongelatore);
            
            
            
            
           
            
           
            
            
            
            
            
            
            
            
            
            Thread.sleep(5000);
            controlloMax();
            
            
        }
        
        
        
    }
    
    static void controlloScaffaliIngresso( Stazione stazione,Scaffale scaffale)
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
                        
                        addProdottoDispensa(prodottoSenzaScaffaleIngresso,scaffale.getNome());
                        
                        
                        
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
    
    static void controlloScaffaliUscita(Stazione stazione,Scaffale scaffale)
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
                        removeProdottoDispensa(prodottoSenzaScaffaleUscita,scaffale.getNome());
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
    
    static void controlloStazioneIngresso(Stazione stazioneIngresso,int maxTimeout,ElencoProdotti elencoProdotti) 
            throws IOException, InterruptedException
    {
        Measurement stazioneMeasurement = StazioneDb.stazioneMeasurement(ic);
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
                        StazioneDb.stazioneSave(stazioneMeasurement,prodottoIngressoPeso,b.getText(),"Ingesso","Accettato");
                        
                    }
                    else if(prodottoIngresso==null)
                    {
                        stazioneIngresso.setText("PRODOTTO NON RICONOSCIUTO");
                        stazioneIngresso.setColor("ROSSO");
                        prodottoIngressoPeso = -1;
                        StazioneDb.stazioneSave(stazioneMeasurement,prodottoIngressoPeso,b.getText(),"Ingesso","Non_accettato");
                        

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
    
    
    static void controlloStazioneUscita(Stazione stazioneUscita,int maxTimeout,ElencoProdotti elencoProdotti) 
            throws IOException, InterruptedException
    {
        Measurement stazioneMeasurement = StazioneDb.stazioneMeasurement(ic); 
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
                        StazioneDb.stazioneSave(stazioneMeasurement,prodottoUscitaPeso,b.getText(),"Usita","Accettato");
                        
                        //stazioneUscitaMeasurement.save(p,b.getText());
                    }
                    else if(t==null)
                    {
                        stazioneUscita.setText("PRODOTTO NON RICONOSCIUTO");
                        stazioneUscita.setColor("ROSSO");
                        StazioneDb.stazioneSave(stazioneMeasurement,prodottoUscitaPeso,b.getText(),"Usita","Non_accettato");

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
    
    static void saveAllertScaffali(ScaffaleNormale scaffaleNormale,ScaffaleBuio scaffaleBuio,
            ScaffaleFrigorifero scaffaleFrigorifero,ScaffaleCongelatore scaffaleCongelatore) throws IOException
    {
        Measurement scaffaleNormaleMeasurement =  ScaffaleDb.scaffaleNormaleMeasurement(ic);
        Measurement scaffaleBuioMeasurement =  ScaffaleDb.scaffaleBuioMeasurement(ic);
        Measurement scaffaleFrigoriferoMeasurement = ScaffaleDb.scaffaleFrigoriferoMeasurement(ic);
        Measurement scaffaleCongelatoreMeasurement =  ScaffaleDb.scaffaleCongelatoreMeasurement(ic);
        
        Measurement scaffaleBuioLuceMeasuremen = ScaffaleDb.scaffaleBuioLuminositaMeasurement(ic);
        Measurement scaffaleFigoriferoLuceMeasuremen = ScaffaleDb.scaffaleFrigoriferoLuceMeasurement(ic);
        Measurement scaffaleCongelatoreLuceMeasuremen = ScaffaleDb.scaffaleCongelatoreLuceMeasurement(ic);
        
        //NORMALE
        double t=scaffaleNormale.getTemperature();
            double h=scaffaleNormale.getHumidity();
            
            ScaffaleDb.scaffaleNormaleSave(scaffaleNormaleMeasurement,t,h);
            
            if(t>25)
            {
                System.out.println("ALLERT-->Temperature scaffale normale troppo alta "+t);
            }
            if(t<5)
            {
            System.out.println("ALLERT-->Temperatura scaffale normale troppo bassa "+t);
            }
            if(h>80)
            {
                System.out.println("ALLERT-->Umidita scaffale normale troppo alta "+ h);
            }
            
            //BUIO
            t=scaffaleBuio.getTemperature();
            h=scaffaleBuio.getHumidity();
            double l = scaffaleBuio.getLight();
            ScaffaleDb.scaffaleBuioSave(scaffaleBuioMeasurement,t,h);
            ScaffaleDb.scaffaleBuoioLuminositaSave(scaffaleBuioLuceMeasuremen,l);
            if(t>25)
            {
                System.out.println("ALLERT-->Temperatura scaffale buio troppo alta "+t);
            }
            if(t<5)
            {
            System.out.println("ALLERT-->Temperatura scaffale buio troppo bassa "+t);
            }
            if(h>80)
            {
                System.out.println("ALLERT-->Umidita scaffale buio troppo alta "+ h);
            }
            if(l>10)
            {
                System.out.println("ALLERT-->Luce scaffale buio troppo alta "+ h);
            }
            
            //FRIGORIFERO
            t=scaffaleFrigorifero.getTemperature();
            h=scaffaleFrigorifero.getHumidity();
           
            ScaffaleDb.scaffaleFrigoriferoSave(scaffaleFrigoriferoMeasurement,t,h);
            //ScaffaleDb.scaffaleFrigoriferoLuminositaSave(scaffaleFigoriferoLuceMeasuremen,l);
            if(t>10)
            {
                System.out.println("ALLERT-->Temperatura scaffale frigorifero troppo alta "+t);
            }
            if(t<0)
            {
                System.out.println("ALLERT-->Temperatura scaffale frigorifero troppo bassa "+t);
            }
            if(h>80)
            {
                System.out.println("ALLERT-->Umidita scaffale frigorifero troppo alta "+ h);
            }
            
            
            //CONGELATORE
            t=scaffaleCongelatore.getTemperature();
            h=scaffaleCongelatore.getHumidity();
           
            ScaffaleDb.scaffaleCongelatoreSave(scaffaleCongelatoreMeasurement,t,h);
            //ScaffaleDb.scaffaleCongelatoreLuceSave(scaffaleCongelatoreLuceMeasuremen,l);
            if(t>4)
            {
                System.out.println("ALLERT-->Temperatura scaffale congelatore troppo alta "+t);
            }
            if(t<-5)
            {
                System.out.println("ALLERT-->Temperatura scaffale congelatore troppo bassa "+t);
            }
            if(h>80)
            {
                System.out.println("ALLERT-->Umidita scaffale congelatore troppo alta "+ h);
            }
            
    }
    
    static void addProdottoDispensa(Prodotto prodotto,String scaffale)
    {
        dispensa.add(prodotto);
        Measurement prodottottoMeasurment = TipoProdottoDb.prodottoMeasurement(ic); 
        TipoProdottoDb.prodottoSave(prodottottoMeasurment, prodotto.getId(), prodotto.getPeso(), scaffale,"aggiunto");
    
    }
    
    static void removeProdottoDispensa(Prodotto prodotto,String scaffale)
    {
        int i =0;
        for(Prodotto p : dispensa)
        {
            if(p.getId().equals(prodotto.getId()))
                break;
            i++;
        }
        if(i==dispensa.size())
        {System.out.println("Prodotto non trovato");}
        else
        {dispensa.remove(i); }
        Measurement prodottottoMeasurment = TipoProdottoDb.prodottoMeasurement(ic); 
        TipoProdottoDb.prodottoSave(prodottottoMeasurment, prodotto.getId(), prodotto.getPeso(), scaffale,"rimosso");
    
    }
    
    
    static void controlloMax()
    {
        for(Prodotto p: dispensa)
        {
            System.out.println(p);
            if(p.getPeso()>=p.getTipoProdotto().getMaxPeso())
            {
                System.out.println("\tIl Prodotto supera la quantita massima");
            }
        }
        
        for(TipoProdotto tp: elencoProdotti.getProdotti())
        {}
    
    }
}





