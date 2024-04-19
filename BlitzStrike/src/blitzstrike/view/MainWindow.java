
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
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
    public JLabel numGamesLabel;
    private Player player1;
    private Player player2;

    private JFrame frame;

    private Timer monsterMoveTimer;
    private Timer battleRoyaleTimer;
    private static final int ONE_SECOND = 1000;

    private Timer backgroundTimer;
    private static final int BACKGROUND = 300;

    final JPanel mMenu;
    public JPanel playerPanel;
    private JPanel statsPanel;
    public KeyAdapter player1KeyListener;
    public KeyAdapter player2KeyListener;
    public String[] fileList;
    public String fileNamesString;

    private JLabel battleRoyalCountDownTime;

    public MainWindow() throws IOException {

        //public void loadMainWindow(){
        frame = new JFrame("BlitzStrike");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        statsPanel = new JPanel();
        statsPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statsPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(statsPanel, BorderLayout.NORTH);

        JMenuItem resumePause = new JMenuItem("Pause/Resume");
        resumePause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game.getIsPaused()) {
                    game.pauseGame();
                    //stop monsters from moving
                    monsterMoveTimer.stop();
                    frame.removeKeyListener(player1KeyListener);
                    frame.removeKeyListener(player2KeyListener);
                } else {
                    game.resumeGame();
                    //stop monsters from moving
                    monsterMoveTimer.restart();
                    frame.addKeyListener(player1KeyListener);
                    frame.addKeyListener(player2KeyListener);

                }
            }
        });
        menu.add(resumePause);

        JMenuItem restart = new JMenuItem("Restart");
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.restartgame();
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

        mMenu = new JPanel() {
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
                readFileNames();
                showContinueDialog();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmExit();
            }
        });

        startMoveMonsterTimer();
        startBackgroundTimer();
        startBattleRoyaleTimer();

        player1KeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_W ->
                        player1.movePlayer(UP, player2);
                    case KeyEvent.VK_S ->
                        player1.movePlayer(DOWN, player2);
                    case KeyEvent.VK_A ->
                        player1.movePlayer(LEFT, player2);
                    case KeyEvent.VK_D ->
                        player1.movePlayer(RIGHT, player2);
                    case KeyEvent.VK_SPACE ->
                        player1.placeBomb();
                }

                if (view != null) {
                    view.repaint();
                }
            }
        };

        player2KeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP ->
                        player2.movePlayer(UP, player1);
                    case KeyEvent.VK_DOWN ->
                        player2.movePlayer(DOWN, player1);
                    case KeyEvent.VK_LEFT ->
                        player2.movePlayer(LEFT, player1);
                    case KeyEvent.VK_RIGHT ->
                        player2.movePlayer(RIGHT, player1);
                    case KeyEvent.VK_ENTER ->
                        player2.placeBomb();
                }
                if (view != null) {
                    view.repaint();
                }
            }
        };

        frame.addKeyListener(player1KeyListener);
        frame.addKeyListener(player2KeyListener);
    }

    public Timer getTimer() {
        return this.backgroundTimer;
    }

    private void toggleStatsPanelVisibility(boolean visible) {
        statsPanel.setVisible(visible);
        frame.revalidate();
        frame.repaint();
    }

    //for continuous movement of monsters
    private void startMoveMonsterTimer() {
        monsterMoveTimer = new Timer(ONE_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Move monsters
                if (game != null && !game.isGameOrRoundEnded()) {
                    game.moveMonsters();

                }
            }
        });
        monsterMoveTimer.start();
        // repainting if monster has caught up to a player, so player should be removed from the map
        //view.repaint();
    }

    // for continuous repaint
    private void startBackgroundTimer() {
        backgroundTimer = new Timer(BACKGROUND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view != null) {
//                    System.out.println("I am repainting");
                    toggleStatsPanelVisibility(true);
                    view.repaint();
                }
                if (game != null) {
                    if (!game.isGameOrRoundEnded()) {
                        game.handleBombExplosion();
                        game.removeDeadMonsters();
                        game.handleCollision();
                        game.handleDeathOfThePlayer();
                        game.handleBattleRoyale();
                        game.handleCurseTermination();
                        game.removeTerminatedCurses();
                    }
                    else if(game.endRound){
                        showRoundEndPopup("Round is overr!");
                    }
                    else if(game.endGame){
                        showGameEndPopup();
                    }
                } else {
                    toggleStatsPanelVisibility(false);
                }
            }
        });
        backgroundTimer.start();
    }

    /**
     * The method to start count down for Battle Royale, it decreases the time
     * every second by one second
     */
    private void startBattleRoyaleTimer() {
        battleRoyaleTimer = new Timer(ONE_SECOND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game != null && !game.isGameOrRoundEnded()) {
                    game.decreaseBattleRoyaleTime();
                    battleRoyalCountDownTime.setText(Integer.toString(game.getCurrentBattleRoyaleTime()));
                }
            }
        });
        battleRoyaleTimer.start();
        // repainting if monster has caught up to a player, so player should be removed from the map
        //view.repaint();
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
        game.pauseGame();
        monsterMoveTimer.stop();
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            game.saveGame(player1, player2, game.getPlayer1Score(), game.getPlayer2Score(), game.getRoundsToWin());
            returnToMainMenu();
        } else {
            game.resumeGame();
            monsterMoveTimer.restart();
        }

    }

    //function that removes playing view and returns mainWindow
    public void returnToMainMenu() {
        frame.remove(view);
        frame.remove(statsPanel);
        toggleStatsPanelVisibility(false);
        frame.add(mMenu);
        frame.revalidate();
        frame.repaint();
    }
    public void readFileNames(){
        String directoryPath = "src/blitzstrike/files/";
        File directory = new File(directoryPath);
        fileList = directory.list();
        StringBuilder stringBuilder = new StringBuilder();
        if (fileList != null) {
            System.out.println("List of files in the directory:");
            for (String fileName : fileList) {
                System.out.println(fileName);
                stringBuilder.append(fileName).append("\n");
                
            }
        } else {
            System.out.println("The directory is empty or does not exist.");
            stringBuilder.append("The directory is empty or does not exist.");
        }
        fileNamesString = stringBuilder.toString();
    }
    public void showContinueDialog(){
        JDialog continueDialog = new JDialog(this, "Continue", true);
        JPanel mainPanel2 = new JPanel();
        mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));
        
        JLabel gamesLabel = new JLabel("Available games:");
        mainPanel2.add(gamesLabel);
        JLabel nameLabel = new JLabel(fileNamesString);
        mainPanel2.add(nameLabel);
        
        JLabel nameLabelPl1 = new JLabel("Enter 1st players' name:");
        mainPanel2.add(nameLabelPl1);

        JTextField player1NameFieldCont = new JTextField(15);
        mainPanel2.add(player1NameFieldCont);

        mainPanel2.add(Box.createHorizontalStrut(10));
        
        JLabel nameLabelPl2 = new JLabel("Enter 2nd players' name:");
        mainPanel2.add(nameLabelPl2);

        JTextField player2NameFieldCont = new JTextField(15);
        mainPanel2.add(player2NameFieldCont);

        mainPanel2.add(Box.createHorizontalStrut(10));
        
        JLabel pl1ScoreLabel = new JLabel("Enter 1st player's score:");
        mainPanel2.add(pl1ScoreLabel);

        JTextField pl1ScoreField = new JTextField(5);
        mainPanel2.add(pl1ScoreField);

        mainPanel2.add(Box.createHorizontalStrut(10));
        
        JLabel pl2ScoreLabel = new JLabel("Enter 2nd player's score:");
        mainPanel2.add(pl2ScoreLabel);

        JTextField pl2ScoreField = new JTextField(5);
        mainPanel2.add(pl2ScoreField);

        mainPanel2.add(Box.createHorizontalStrut(10));
        
        JLabel numGamesLabel2 = new JLabel("Enter number of games to win:");
        mainPanel2.add(numGamesLabel2);

        JTextField numGamesField2 = new JTextField(5);
        mainPanel2.add(numGamesField2);

        mainPanel2.add(Box.createHorizontalStrut(10));
        
        JButton startButton2 = new JButton("START");
        startButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
                boolean allFieldsAreGoodCont = true;

                String player1NameCont = player1NameFieldCont.getText().trim();
                String player2NameCont = player2NameFieldCont.getText().trim();
                
                Integer pl1ScoreCont = Integer.valueOf(pl1ScoreField.getText());
                Integer pl2ScoreCont = Integer.valueOf(pl2ScoreField.getText());
                int numGamesCont = Integer.parseInt(numGamesField2.getText());

                if (player1NameCont.isEmpty() || player2NameCont.isEmpty()) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter both players' names!", "Missing Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }
                
                if (pl1ScoreCont==null || pl2ScoreCont==null) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter both players' scores!", "Missing Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                numGamesCont = 0;
                try {
                    numGamesCont = Integer.parseInt(numGamesField2.getText());
                    if (numGamesCont <= 0) {
                        allFieldsAreGoodCont = false;
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter a valid number of games (positive integer)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                if (allFieldsAreGoodCont) {
                    
                    String filePathCont = "src/blitzstrike/files/" + player1NameCont+"_"+player2NameCont+"_"+ pl1ScoreCont+"_"+pl2ScoreCont+"_"+ numGamesCont + ".txt";

                    player1 = new Player(player1NameCont);
                    player2 = new Player(player2NameCont);

                    game = new Game(filePathCont, player1, player2, numGamesCont);
                
                    player1 = new Player(player1NameFieldCont.getText());
                    player2 = new Player(player2NameFieldCont.getText());
//                    game = new Game(filePathCont, player1, player2, 0);
                    game.continueGame(filePathCont);
                    game.setRoundsToWin(Integer.parseInt(numGamesField2.getText()));
                    try {
                        view = new View(game);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.remove(mMenu);
                    frame.add(view);
                    statsPanel.removeAll();
                    statsPanel.add(createPlayerStatsPanel(player1, game.getPlayer1Score()));
                    statsPanel.add(createPlayerStatsPanel(player2, game.getPlayer2Score()));
                    int sum = Integer.parseInt(pl1ScoreField.getText())+ Integer.parseInt(pl1ScoreField.getText());
                    statsPanel.add(new JLabel("Round: " + sum )); // to be changed for num games
                    frame.revalidate();
                    frame.repaint();
                    continueDialog.setVisible(false);
                    continueDialog.dispose();
                }
        }
        });
        mainPanel2.add(startButton2);

        mainPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        continueDialog.setContentPane(mainPanel2);

        continueDialog.pack();

        continueDialog.setLocationRelativeTo(
                this);

        continueDialog.setVisible(true);
        //add button
        //add errors
        //display
        game.resumeGame();
        monsterMoveTimer.restart();
        frame.addKeyListener(player1KeyListener);
        frame.addKeyListener(player2KeyListener);
    
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
        roundEndDialog.setSize(400, 200);
        roundEndDialog.setLocationRelativeTo(this); // Center on screen

        // Message
        JLabel messageLabel = new JLabel("<html><center>" + message + "<br>Player 1 Score: " + game.getPlayer1Score() + "<br>Player 2 Score: " + game.getPlayer2Score() + "</center></html>");
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
        JDialog gameEndDialog = new JDialog(this, "Game Ended", true);
        gameEndDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Set the default close operation
        gameEndDialog.setLayout(new FlowLayout());
        gameEndDialog.setLocationRelativeTo(this); // Center on screen

        JLabel messageLabel = new JLabel("<html><center>Congrats " + game.getWinner().getName() + "</center></html>");
        gameEndDialog.add(messageLabel);

        JButton returnButton = new JButton("Return to main menu");
        returnButton.addActionListener(e -> {
            gameEndDialog.dispose(); // Close and dispose of the dialog
            returnToMainMenu();
            game=null;
        });
        gameEndDialog.add(returnButton);

        gameEndDialog.pack(); // Pack the dialog instead of setting size
        gameEndDialog.setVisible(true);
    }
}
    private JPanel createPlayerStatsPanel(Player player, int score) {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        // Add player picture (replace with your image loading logic)
        ImageIcon playerImage = new ImageIcon("blitzstrike/res/monster2.png");
        JLabel playerImageLabel = new JLabel(playerImage);
        playerPanel.add(playerImageLabel);

        JLabel playerNameLabel = new JLabel(player.getName());
        playerNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        playerPanel.add(playerNameLabel);

        JLabel playerScoreLabel = new JLabel("Score: " + score);
        playerScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        playerPanel.add(playerScoreLabel);

        return playerPanel;
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

        numGamesLabel = new JLabel("Enter number of games:");
        mainPanel.add(numGamesLabel);

        JTextField numGamesField = new JTextField(5);
        mainPanel.add(numGamesField);

        mainPanel.add(Box.createHorizontalStrut(10));

        JButton startButton = new JButton("START");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    String filePath = "src/blitzstrike/res/map" + selectedMap + ".txt";

                    player1 = new Player(player1Name);
                    player2 = new Player(player2Name);

                    game = new Game(filePath, player1, player2, numGames);

                    game.loadMap();
                
                try {
                    view = new View(game);
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
                frame.remove(mMenu);
                frame.add(view);
                statsPanel.removeAll();
                statsPanel.add(createPlayerStatsPanel(player1, game.getPlayer1Score()));
                statsPanel.add(createPlayerStatsPanel(player2, game.getPlayer2Score()));
                statsPanel.add(new JLabel("Round: 1")); // to be changed for num games

                battleRoyalCountDownTime = new JLabel(Integer.toString(game.getCurrentBattleRoyaleTime()));
                statsPanel.add(battleRoyalCountDownTime);

                frame.revalidate();
                frame.repaint();
                gameSetupDialog.setVisible(false);
                gameSetupDialog.dispose();
            }
            }
        }
        );
        mainPanel.add(startButton);

        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameSetupDialog.setContentPane(mainPanel);

        gameSetupDialog.pack();

        gameSetupDialog.setLocationRelativeTo(
                this);

        gameSetupDialog.setVisible(
                true);
    }

}
