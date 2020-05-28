
package dispensa8;

import ch.suspi.simulator.sensors.barcode.Barcode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TipoProdotto {

    
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
        ArrayList<String> lista1= new ArrayList<>();lista1.add("Normale");
        prodotti.add(new TipoProdotto("Pasta",new Barcode("141494104086",""),lista1,20.0));
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