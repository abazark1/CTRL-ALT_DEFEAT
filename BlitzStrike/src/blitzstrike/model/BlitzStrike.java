/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.view.MainWindow;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
//import blitzstrike.res.*;

/**
 *
 * @author aliia
 */
public class BlitzStrike {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         SwingUtilities.invokeLater(() -> {
             try {
                 new MainWindow();
             } catch (IOException ex) {
                 Logger.getLogger(BlitzStrike.class.getName()).log(Level.SEVERE, null, ex);
             }
             String path = "src/blitzstrike/model/map1.txt";
             Game g = new Game();
//             String a = g.FileReader(path);
//             System.out.println(a);
        });
    }
    
}
