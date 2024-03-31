/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.view;

import blitzstrike.model.Game;
import blitzstrike.model.Player;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import res.ResourceLoader;

/**
 *
 * @author medina
 */
public class MainWindow extends JFrame{
    private Game game;
    private View view; ///in the class diagram Board board
    private JLabel timeLabel;
    private Player player1;
    private Player player2;
    
    private JFrame frame;
    public MainWindow() throws IOException 
    {
        
        frame = new JFrame("BlitzStrike");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        
        JMenuItem resumePause = new JMenuItem("Pause/Resume");        
        resumePause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //method to implement pause/resume
            }
        });
        menu.add(resumePause);
        
        JMenuItem restart = new JMenuItem("Restart");     
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //method to implement restart
            }
        });
        menu.add(restart);
        
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
        menu.add(exit);
        
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);
        
        
        
        final JPanel mMenu = new JPanel()
        {
            @Override
            public void paintComponent(Graphics gc){
                super.paintComponent(gc);
                try{
//                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/mainMenu.jpg");
//                    gc.drawImage(mMenuImage, 150, 150, null);
                    
                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/mainM.png");
                    int x = (getWidth() - mMenuImage.getWidth(null)) / 2;
                    int y = (getHeight() - mMenuImage.getHeight(null)) / 2;
                    gc.drawImage(mMenuImage, x, y, null);

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
   
        };
 

        //mMenu.setLayout(new BorderLayout());
        mMenu.setBackground(Color.BLACK);
        mMenu.setLayout(new BoxLayout(mMenu, BoxLayout.Y_AXIS));
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        
   
        JButton playButton = createButton("PLAY");
        JButton continueButton = createButton("CONTINUE");
        JButton quitButton = createButton("QUIT");
        
        addButton(mMenu, playButton);
        mMenu.add(Box.createVerticalStrut(10)); // Add space
        addButton(mMenu, continueButton);
        mMenu.add(Box.createVerticalStrut(10)); // Add space
        addButton(mMenu, quitButton);

	mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        frame.add(mMenu);
        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();      
        frame.setVisible(true);
        setLocationRelativeTo(null);
        
        playButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Handle play button click
            }
        });

        continueButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Handle continue button click
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });
    
    }
    
    private JButton createButton(String label) 
    {
    JButton button = new JButton(label);
    Dimension buttonSize = new Dimension(180, 60);
    button.setPreferredSize(buttonSize);
    button.setMaximumSize(buttonSize);
    button.setMinimumSize(buttonSize);
    button.setBackground(Color.BLUE);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("SansSerif", Font.BOLD, 17));
    return button;
    }
    
    // Helper method to create a button and add it to the panel
    private void addButton(JPanel panel, JButton button) 
    {
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            buttonPanel.add(button);
            panel.add(buttonPanel);
            
            
    }
    private void confirmExit() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }
    
  
    
    

    public void showRoundEndPopup(String message) {
//    SwingUtilities.invokeLater(() -> {
//        JOptionPane.showMessageDialog(this, message, "Round Over", JOptionPane.INFORMATION_MESSAGE);
//        Game g = new Game();
//        try {
//            g.loadNextRound();
//        } catch (Exception ex) {
//            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    });

    JDialog roundEndDialog = new JDialog(this, "Round Ended", true);
    roundEndDialog.setLayout(new FlowLayout());
    roundEndDialog.setSize(400, 200); // You can adjust the size based on your content
    roundEndDialog.setLocationRelativeTo(this); // Center on screen

    // Message
    JLabel messageLabel = new JLabel("<html><center>" + message + "<br>Player 1 Score: " + player1.getGamesWon() + "<br>Player 2 Score: " + player2.getGamesWon() + "</center></html>");
    roundEndDialog.add(messageLabel);

    // Next Round Button
    JButton nextRoundButton = new JButton("Go to Next Round");
    nextRoundButton.addActionListener(e -> {
        try {
            roundEndDialog.setVisible(false);
            roundEndDialog.dispose(); // Close and dispose of the dialog
            game.loadNextRound();
        } catch (Exception ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    });
    roundEndDialog.add(nextRoundButton);

    roundEndDialog.setVisible(true);

    }
}