
package dispensa8;

import ch.suspi.simulator.sensors.barcode.Barcode;
import java.util.ArrayList;
import java.util.List;

public class TipoProdotto {
private final Barcode barcode;
private final List<String> listaCategorie;
private final double maxPeso;


public TipoProdotto(Barcode barcode,List<String> listaCategorie,double maxPeso)
{
    this.barcode = barcode;
    this.listaCategorie = new ArrayList<>();
    this.maxPeso = maxPeso;
    
    for(String s : listaCategorie)
    {
        this.listaCategorie.add(s);
    }
         
}
}
/*
id
nome
lista tipi
max peso
data scadenza

*/