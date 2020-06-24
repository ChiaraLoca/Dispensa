
package dispensa8;

import ch.suspi.simulator.sensors.barcode.Barcode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class TipoProdotto {

    String getId() {
        if (barcode== null)
        {System.out.println("TEST-->barcode null");
                return "";}
        else
            return barcode.getText();
                
               
    }

    
    private final String nome;
    private final Barcode barcode;
    private final List<String> listaCategorie;
    private final double maxPeso;
     private final double minPeso;


    public TipoProdotto(String nome,Barcode barcode,List<String> listaCategorie,double maxPeso,double minPeso)
    {
        this.nome = nome;
        this.barcode = barcode;
        this.listaCategorie = new ArrayList<>();
        this.maxPeso = maxPeso;
        this.minPeso = minPeso;

        for(String s : listaCategorie)
        {
            this.listaCategorie.add(s);
        }

    }

    
    public String getNome()
    {
        return nome;
    }
    
    public Barcode getBarcode()
    {
        return barcode;
    }
    public List<String> getListaCategorie()
    {
        return listaCategorie;
    }
    public double getMaxPeso()
    {
        return maxPeso;
    }
    
    public String toString()
    {
        String str ="Nome: "+nome+", Barcode "+barcode.getText()+", Categorie: ";
        for(String s : listaCategorie){
        str+= s+", ";}
        str+="Peso massimo: "+maxPeso;
        
        return str;           
    }

    double getMinPeso() {
        return minPeso;
    }
 
}

class ElencoProdotti
{
    public static List<TipoProdotto> prodotti = new ArrayList<>();
    public ElencoProdotti()
    {
        ArrayList<String> ln= new ArrayList<>();ln.add("Normale");
        ArrayList<String> lb= new ArrayList<>();lb.add("Buio");
         ArrayList<String> lf= new ArrayList<>();lf.add("Frigorifero");
          ArrayList<String> lc= new ArrayList<>();lc.add("Congelatore");
        
        
        
        
        prodotti.add(new TipoProdotto("Pasta",new Barcode("100000000000",""),ln,4000,1000));
        prodotti.add(new TipoProdotto("Acqua ",new Barcode("200000000000",""),ln,4000,1000));
        prodotti.add(new TipoProdotto("Vino ",new Barcode("300000000000",""),lb,4000,1000));
        prodotti.add(new TipoProdotto("Birra ",new Barcode("400000000000",""),lf,4000,1000));
        prodotti.add(new TipoProdotto("Uova ",new Barcode("500000000000",""),lf,4000,1000));
        
        prodotti.add(new TipoProdotto("Carne",new Barcode("600000000000",""),lc,4000,1000));
        prodotti.add(new TipoProdotto("Pesce ",new Barcode("700000000000",""),lc,4000,1000));
        prodotti.add(new TipoProdotto("Olio ",new Barcode("800000000000",""),lb,4000,1000));
        prodotti.add(new TipoProdotto("Formaggio ",new Barcode("900000000000",""),lb,4000,1000));
        prodotti.add(new TipoProdotto("Salumi ",new Barcode("110000000000",""),lb,4000,1000));
        
        prodotti.add(new TipoProdotto("Pane ",new Barcode("120000000000",""),lc,4000,1000));
        
        
    }
    
    public void addProdotto(TipoProdotto p)
    {
        prodotti.add(p);
    }
    
    public TipoProdotto cerca(String nome)      
    {
        for(TipoProdotto p : prodotti){
            if(p.getNome().equals(nome))
                return p;
        }
        return null;
    }
    
    public TipoProdotto cerca(Barcode barcode)
    {
        for(TipoProdotto p : prodotti){
            if(p.getBarcode().getText().equals(barcode.getText()))
                return p;
        }
        return null;
    }

     List<TipoProdotto> getProdotti() {
        return prodotti;
    }
    


}

class Prodotto
{
    private TipoProdotto prodotto;
    private double peso;
    private LocalDateTime scadenza;

    public Prodotto(TipoProdotto prodotto,double peso)
    {
        this.prodotto = prodotto;
        this.peso = peso;
        scadenza = LocalDateTime.now().plusDays(5);
    }
    /*    public Prodotto()
    {
        this.prodotto = null;
        this.peso = 0.0;
        scadenza = LocalDateTime.now().plusDays(5);
    }*/
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.prodotto);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.peso) ^ (Double.doubleToLongBits(this.peso) >>> 32));
       
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prodotto other = (Prodotto) obj;
        
        if(!this.getId().equals(other.getId()))
            return false;
        
        if(this.getPeso() != other.getPeso())
            return false;
        
        if(!this.scadenza.isEqual(other.getScadenza()))
            return false;

        
        return true;
    }



    
    
    @Override
    public String toString() {
        
        return "prodotto numero:"+ prodotto.getId() +", nome: "+ prodotto.getNome()+", peso " +peso+", pesoMax: "+prodotto.getMaxPeso();
    }
    
    public LocalDateTime getScadenza()
    {
        return scadenza;
    }
    
    public Prodotto()
    {
        prodotto = null;
        peso = -1;
    }

    
    double getPeso()
    {
        return peso;
    }
    
    void setPeso(double peso)
    {
        this.peso = peso;
    }
    
    TipoProdotto getTipoProdotto()
    {
        return prodotto;
    }
    
    void setTipoProdotto(TipoProdotto tp)
    {
        this.prodotto = tp;
    }

    String getId() {
        return prodotto.getId();
    }

    void addPeso(double peso) {
        this.peso += peso;
    }

    void setScadenza(LocalDateTime scadenza) {
        this.scadenza = scadenza;
    }
    
    
}