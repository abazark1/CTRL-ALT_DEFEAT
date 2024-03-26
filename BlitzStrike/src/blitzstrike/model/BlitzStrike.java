/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.view.MainWindow;
import java.io.IOException;
import javax.swing.SwingUtilities;

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
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
}
