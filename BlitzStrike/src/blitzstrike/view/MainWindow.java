
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.view;

import blitzstrike.model.BlitzStrike;
import static blitzstrike.model.Direction.UP;
import static blitzstrike.model.Direction.DOWN;
import static blitzstrike.model.Direction.LEFT;
import static blitzstrike.model.Direction.RIGHT;
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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import res.ResourceLoader;
import javax.swing.Timer;

/**
 *
 * @author medina
 */
public class MainWindow extends JFrame {

    private Game game;
    private View view; ///in the class diagram Board board
    private JLabel timeLabel;
    private Player player1;
    private Player player2;

    private JFrame frame;

    private Timer monsterMoveTimer;
    private static final int MONSTER_MOVE = 2000;

    public MainWindow() throws IOException {

        //public void loadMainWindow(){
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

        JMenuItem exit = new JMenuItem("Save and Exit");
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExitandSave();
            }
        });
        menu.add(exit);

        menuBar.add(menu);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

        final JPanel mMenu = new JPanel() {
            @Override
            public void paintComponent(Graphics gc) {
                super.paintComponent(gc);
                try {
                    //                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/mainMenu.jpg");
                    //                    gc.drawImage(mMenuImage, 150, 150, null);

                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/mainM.png");
                    int x = (getWidth() - mMenuImage.getWidth(null)) / 2;
                    int y = (getHeight() - mMenuImage.getHeight(null)) / 2;
                    gc.drawImage(mMenuImage, x, y, null);

                } catch (IOException e) {
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
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        setLocationRelativeTo(null);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle play button click
                showGameSetupWindow(mMenu);
            }
        });

        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle continue button click
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });

        startMoveMonsterTimer();

        KeyAdapter player1KeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W:
                        player1.movePlayer(UP);
                        break;
                    case KeyEvent.VK_S:
                        player1.movePlayer(DOWN);
                        break;
                    case KeyEvent.VK_A:
                        player1.movePlayer(LEFT);
                        break;
                    case KeyEvent.VK_D:
                        player1.movePlayer(RIGHT);
                        break;
                    case KeyEvent.VK_SPACE:
                        player1.placeBomb();
                }
                view.repaint();
            }
        };

        KeyAdapter player2KeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP:
                        player2.movePlayer(UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        player2.movePlayer(DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        player2.movePlayer(LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        player2.movePlayer(RIGHT);
                        break;
                    case KeyEvent.VK_ENTER:
                        player2.placeBomb();
                }
                view.repaint();
            }
        };

        frame.addKeyListener(player1KeyListener);
        frame.addKeyListener(player2KeyListener);
    }

    //for continuous movement of monsters
    private void startMoveMonsterTimer() {
        monsterMoveTimer = new Timer(MONSTER_MOVE, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move monsters
                moveMonsters();
//                view.repaint();
            }
        });
        monsterMoveTimer.start();
    }

    private void moveMonsters() {
        if (game != null) {
            game.moveMonsters();
            // Repaint the view to update the monster positions
//            view.repaint();
        }
    }

    private JButton createButton(String label) {
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
    private void addButton(JPanel panel, JButton button) {

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

    private void confirmExitandSave() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            //function to save the game
            returnToMainMenu();
        }
    }

    public void returnToMainMenu() {
        JPanel mMenu = new JPanel();
        mMenu.setVisible(true);
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

    private void showGameEndPopup() {
        if (game.endGame) {
            JDialog gameEndDialog = new JDialog(this, "Game End", true);
            JPanel mPanel = new JPanel();
            mPanel.setLayout(new BoxLayout(mPanel, BoxLayout.Y_AXIS));
            JLabel congratsLabel = new JLabel("Congrats!");
            JLabel winnerNameLabel = new JLabel(game.getWinner().getName());
            JLabel winnerScoreLabel = new JLabel(Integer.toString(game.getRoundsToWin()));

            mPanel.add(congratsLabel);
            mPanel.add(winnerNameLabel);
            mPanel.add(winnerScoreLabel);

            JButton returnButton = new JButton("RETURN");
            returnButton.addActionListener(e -> returnToMainMenu());
            mPanel.add(returnButton);
        }
    }

    private void showGameSetupWindow(JPanel mMenu) {
        JDialog gameSetupDialog = new JDialog(this, "Game Setup", true);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel("Enter players' names:");
        mainPanel.add(nameLabel);

        JTextField player1NameField = new JTextField(15);
        mainPanel.add(player1NameField);

        mainPanel.add(Box.createHorizontalStrut(10));

        JTextField player2NameField = new JTextField(15);
        mainPanel.add(player2NameField);

        mainPanel.add(Box.createHorizontalStrut(10));

        JLabel mapLabel = new JLabel("Choose your map:");
        mainPanel.add(mapLabel);

        ButtonGroup mapButtonGroup = new ButtonGroup();

        JRadioButton map1RadioButton = new JRadioButton("Map 1");
        mapButtonGroup.add(map1RadioButton);
        mainPanel.add(map1RadioButton);

        JRadioButton map2RadioButton = new JRadioButton("Map 2");
        mapButtonGroup.add(map2RadioButton);
        mainPanel.add(map2RadioButton);

        JRadioButton map3RadioButton = new JRadioButton("Map 3");
        mapButtonGroup.add(map3RadioButton);
        mainPanel.add(map3RadioButton);

        mainPanel.add(Box.createHorizontalStrut(10));

        JLabel numGamesLabel = new JLabel("Enter number of games:");
        mainPanel.add(numGamesLabel);

        JTextField numGamesField = new JTextField(5);
        mainPanel.add(numGamesField);

        mainPanel.add(Box.createHorizontalStrut(10));

        JButton startButton = new JButton("START");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                boolean allFieldsAreGood = true;

                String player1Name = player1NameField.getText().trim();
                String player2Name = player2NameField.getText().trim();

                if (player1Name.isEmpty() || player2Name.isEmpty()) {
                    JOptionPane.showMessageDialog(gameSetupDialog, "Enter both players' names!", "Missing Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGood = false;
                }

                if (!map1RadioButton.isSelected() && !map2RadioButton.isSelected() && !(map3RadioButton != null && map3RadioButton.isSelected())) {
                    JOptionPane.showMessageDialog(gameSetupDialog, "Select a map!", "Missing Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGood = false;
                }

                int numGames = 0;
                try {
                    numGames = Integer.parseInt(numGamesField.getText());
                    if (numGames <= 0) {
                        allFieldsAreGood = false;
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gameSetupDialog, "Enter a valid number of games (positive integer)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGood = false;
                }

                if (allFieldsAreGood) {
                    int selectedMap = map1RadioButton.isSelected() ? 1 : (map2RadioButton.isSelected() ? 2 : (map3RadioButton != null && map3RadioButton.isSelected() ? 3 : 0));
                    String filePath = "src/blitzstrike/model/map" + selectedMap + ".txt";
                    
                    player1 = new Player(player1Name);
                    player2 = new Player(player2Name);

                    game = new Game(filePath, player1, player2, numGames);
                 */
                player1 = new Player("John");
                player2 = new Player("Vanessa");
                game = new Game("src/blitzstrike/res/map1.txt",player1, player2, 0);
                game.loadMap();
                try {
                    view = new View(game);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.remove(mMenu);
                frame.add(view);
                frame.revalidate();
                frame.repaint();
                gameSetupDialog.setVisible(false);
                gameSetupDialog.dispose();
//            }
        }
    }

    );
    mainPanel.add (startButton);

    mainPanel.setBorder (javax.swing.BorderFactory.createEmptyBorder

    (10, 10, 10, 10));
    gameSetupDialog.setContentPane (mainPanel);

    gameSetupDialog.pack ();

    gameSetupDialog.setLocationRelativeTo (

    this);

    gameSetupDialog.setVisible (

true);
    }
    
    
}
