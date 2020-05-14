/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dispensa8;

import ch.suspi.simulator.grove.GrovePiSimulator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iot.raspberry.grovepi.GrovePi;

public class TestClassi {
    
    public static void main(String[] args) throws IOException
    {
        Logger.getLogger("GrovePi").setLevel(Level.WARNING);
        Logger.getLogger("RaspberryPi").setLevel(Level.WARNING);

        GrovePi grovePi = new GrovePiSimulator();
        
        Scaffale n1 = new ScaffaleNormale(grovePi);
        Scaffale n2 = new ScaffaleBuio(grovePi);
        Scaffale n3 = new ScaffaleFrigorifero(grovePi);
        Scaffale n4 = new ScaffaleCongelatore(grovePi);
        
        System.out.println(n1);
        System.out.println(n2);
        System.out.println(n3);
        System.out.println(n4);
        
        
    }
}
