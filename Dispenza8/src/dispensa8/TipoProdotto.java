
package dispensa8;

import ch.suspi.simulator.sensors.barcode.Barcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TipoProdotto {

    String getId() {
        return barcode.getText();
    }

    
    private final String nome;
    private final Barcode barcode;
    private final List<String> listaCategorie;
    private final double maxPeso;


    public TipoProdotto(String nome,Barcode barcode,List<String> listaCategorie,double maxPeso)
    {
        this.nome = nome;
        this.barcode = barcode;
        this.listaCategorie = new ArrayList<>();
        this.maxPeso = maxPeso;

        for(String s : listaCategorie)
        {
            this.listaCategorie.add(s);
        }

    }

    TipoProdotto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
 
}

class ElencoProdotti
{
    public static List<TipoProdotto> prodotti = new ArrayList<>();
    public ElencoProdotti()
    {
        ArrayList<String> ln= new ArrayList<>();ln.add("Normale");
        ArrayList<String> lb= new ArrayList<>();lb.add("Buio");
         ArrayList<String> lf= new ArrayList<>();lb.add("Frigorifero");
          ArrayList<String> lc= new ArrayList<>();lb.add("Congelatore");
        
        
        
        
        prodotti.add(new TipoProdotto("Pasta",new Barcode("100000000000",""),ln,4000));
        prodotti.add(new TipoProdotto("Acqua ",new Barcode("200000000000",""),ln,4000));
        prodotti.add(new TipoProdotto("Vino ",new Barcode("300000000000",""),lb,4000));
        prodotti.add(new TipoProdotto("Vino ",new Barcode("400000000000",""),lf,4000));
        prodotti.add(new TipoProdotto("Uova ",new Barcode("500000000000",""),lf,4000));
        
        prodotti.add(new TipoProdotto("Carne",new Barcode("600000000000",""),lc,4000));
        prodotti.add(new TipoProdotto("Pesce ",new Barcode("700000000000",""),lc,4000));
        prodotti.add(new TipoProdotto("Olio ",new Barcode("800000000000",""),lb,4000));
        prodotti.add(new TipoProdotto("Formaggio ",new Barcode("900000000000",""),lb,4000));
        prodotti.add(new TipoProdotto("Salumi ",new Barcode("110000000000",""),lb,4000));
        
        prodotti.add(new TipoProdotto("Pane ",new Barcode("120000000000",""),lc,4000));
        
        
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
    


}

class Prodotto
{
    private TipoProdotto prodotto;
    private double peso;

    @Override
    public String toString() {
        return "prodotto numero:"+ prodotto.getId() +", nome: "+ prodotto.getNome()+", peso "+ +peso+", pesoMax: "+prodotto.getMaxPeso();
    }
    
    
    
    public Prodotto()
    {
        prodotto = null;
        peso = -1;
    }
    
    public Prodotto(TipoProdotto tp, double p)
    {
        prodotto = tp;
        peso = p;
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
    
    
}