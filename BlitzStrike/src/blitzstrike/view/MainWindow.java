/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.view;

import javax.swing.*;
import java.awt.event.*;
import blitzstrike.model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;
import res.ResourceLoader;

/**
 *
 * @author emedash
 */
public class MainWindow extends JFrame{
    private Game game;
    private View view; ///in the class diagram Board board
    private JLabel timeLabel;
    
    private JFrame frame;
    public MainWindow() throws IOException {
        
        frame = new JFrame("Blitzstrike");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
            final JPanel mMenu = new JPanel(){
            @Override
            public void paintComponent(Graphics gc){
                super.paintComponent(gc);
                try{
                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/monster2.png");
                    gc.drawImage(mMenuImage, 350, 250, null);
                    
//                    Image mMenuImage = ResourceLoader.loadImage("res/wall.png");
//                    int x = (mMenu.getWidth() - mMenuImage.getWidth(null)) / 2;
//                    int y = (mMenu.getHeight() - mMenuImage.getHeight(null)) / 2;
//                    gc.drawImage(mMenuImage, x, y, null);

                }
                catch (IOException e){
                }
            }
   
            };
         
      final JFrame optionMenu =new JFrame();
      optionMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 

        mMenu.setLayout(new BorderLayout());
        mMenu.setBackground(Color.BLACK);
        final JPanel tMenu = new JPanel();
        tMenu.setLayout(new FlowLayout());
        tMenu.setBackground(Color.DARK_GRAY);
        final JButton play = new JButton("PLAY");
	tMenu.add(play);
	final JButton scoreBoard = new JButton("ScoreBoard");
	tMenu.add(scoreBoard);
        final JButton quit = new JButton("QUIT");
	mMenu.add(tMenu,BorderLayout.SOUTH);
        frame.add(mMenu);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();      
        frame.setVisible(true);
        setLocationRelativeTo(null);
 
         
    /*
    MainWindow(){
        game =  new Game();
        view = new View();
        timeLabel = new JLabel();
    }
    */
    
//    public void createMainMenuItems(JMenu m){
//        
//    }
//    public void createGameMenuItems(JMenu m){
//        
//    }
//    public void createGameCsaleItems(JMenu m, double to, double from, double by){
//        
//    }
    
            }
}
