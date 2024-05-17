
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.view;

import static blitzstrike.model.Direction.UP;
import static blitzstrike.model.Direction.DOWN;
import static blitzstrike.model.Direction.LEFT;
import static blitzstrike.model.Direction.RIGHT;
import blitzstrike.model.Game;
import blitzstrike.model.Player;
import blitzstrike.model.effects.curses.Curse;
import blitzstrike.model.effects.powerups.BlastRange;
import blitzstrike.model.effects.powerups.BombIncrease;
import blitzstrike.model.effects.powerups.Detonator;
import blitzstrike.model.effects.powerups.Obstacle;
import blitzstrike.model.effects.powerups.PowerUp;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Color.BLACK;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
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
import res.ResourceLoader;
import javax.swing.Timer;

public class MainWindow extends JFrame {

    private static final int ONE_SECOND = 1000;
    private static final int BACKGROUND = 300;

    private final JPanel mMenu;
    private final JPanel statsPanel;
    private final JPanel player1effects;
    private final JPanel player2effects;
    private JPanel playerPanel;
    private JPanel timerPanel;
    private Game game;
    private View view; ///in the class diagram Board board
    private String[] fileList;
    private String fileNamesString;
    private String[] fileNamesArray;
    private Player player1;
    private Player player2;
    private Timer monsterMoveTimer;
    private Timer battleRoyaleTimer;
    private Timer backgroundTimer;
    private KeyAdapter player1KeyListener;
    private KeyAdapter player2KeyListener;
    private JFrame frame;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JLabel battleRoyalCountDownTime;
    private JLabel numGamesLabel;
    boolean fileLoaded = false;

    public MainWindow() throws IOException {
        frame = new JFrame("BlitzStrike");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        statsPanel = new JPanel();
        statsPanel.setLayout(new BorderLayout());
        statsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        statsPanel.setBackground(Color.LIGHT_GRAY);
        frame.add(statsPanel, BorderLayout.NORTH);

        player1effects = new JPanel();
        player2effects = new JPanel();
        player1effects.setBackground(Color.LIGHT_GRAY);
        player2effects.setBackground(Color.LIGHT_GRAY);
        frame.add(player1effects, BorderLayout.WEST);
        frame.add(player2effects, BorderLayout.EAST);
        player1effects.setPreferredSize(new Dimension(80, frame.getHeight()));
        player2effects.setPreferredSize(new Dimension(80, frame.getHeight()));

////////////////////////////////////////////////////Menu Bar///////////////////////////////////////////
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

///////////////////////////////////////////////////Start main window///////////////////////////////////////////////
        mMenu = new JPanel() {
            @Override
            public void paintComponent(Graphics gc) {
                super.paintComponent(gc);
                try {
                    Image mMenuImage = ResourceLoader.loadImage("blitzstrike/res/mainM.png");
                    int x = (getWidth() - mMenuImage.getWidth(null)) / 2;
                    int y = (getHeight() - mMenuImage.getHeight(null)) / 2;
                    gc.drawImage(mMenuImage, x, y, null);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        };
        mMenu.setBackground(Color.BLACK);
        mMenu.setLayout(new BoxLayout(mMenu, BoxLayout.Y_AXIS));
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());
        mMenu.add(Box.createVerticalGlue());

/////////////////////////////////////////////////////////////Start main window buttons///////////////////////////////////////
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
        frame.setPreferredSize(new Dimension(805, 800));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
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
                if (!fileLoaded) {
                    System.exit(1);
                }

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

//////////////////////////////////////////////////////////key adapters///////////////////////////////////////////////////
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
                    case KeyEvent.VK_1 ->
                        player1.placeObstacle();
                }

                if (view != null) {
                    view.repaint();
                    //updatePlayerEffects(player1effects, player1);
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
                    case KeyEvent.VK_SHIFT ->
                        player2.placeObstacle();
                }
                if (view != null) {
                    view.repaint();
                    //updatePlayerEffects(player2effects, player2);
                }
            }
        };

        frame.addKeyListener(player1KeyListener);
        frame.addKeyListener(player2KeyListener);
    }

    /////////////////////////////////////////////////getters and setters///////////////////////////////////////////////
    /**
     * Returns the background timer used for continuous repaint.
     *
     * @return The background timer.
     */
    public Timer getTimer() {
        return this.backgroundTimer;
    }

    ////////////////////////////////////////////private methods///////////////////////
    /**
     * Toggles the visibility of the statistics panel and triggers frame
     * revalidation and repaint.
     *
     * @param visible {@code true} to make the statistics panel visible,
     * {@code false} to hide it.
     */
    private void toggleStatsPanelVisibility(boolean visible) {
        statsPanel.setVisible(visible);
        if (!visible) {
            statsPanel.removeAll();
        }
        frame.revalidate();
        frame.repaint();
    }

    private void togglePlayerEffectsVisibility(boolean visible) {
        player1effects.setVisible(visible);
        player2effects.setVisible(visible);

        if (!visible) {
            player1effects.removeAll();
            player2effects.removeAll();
        }
        player1effects.revalidate();
        player2effects.revalidate();
        player1effects.repaint();
        player2effects.repaint();
    }

    /**
     * Starts the timer for continuous movement of monsters.
     */
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

    /**
     * Starts the background timer for continuous repaint and game logic
     * updates.
     */
    private void startBackgroundTimer() {
        backgroundTimer = new Timer(BACKGROUND, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (view != null) {
//                    System.out.println("I am repainting");
                    toggleStatsPanelVisibility(true);
                    togglePlayerEffectsVisibility(true);
                    updatePlayerEffects(player1effects, player1);
                    updatePlayerEffects(player2effects, player2);
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
                        game.handlePowerupTermination();
                        game.removeTerminatedPowerups();
                        game.handleImmediateBombCurseForBothPlayers();
                        game.handleGhostCondition();
                    } else if (game.getEndRound()) {
                        showRoundEndPopup("Round is over!");
                    } else if (game.getEndGame()) {
                        showGameEndPopup();
                    }
                } else {
                    toggleStatsPanelVisibility(false);
                    togglePlayerEffectsVisibility(false);
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
                    battleRoyalCountDownTime.setText("BattleRoyale Timer: " + Integer.toString(game.getCurrentBattleRoyaleTime()));
                    if (game.getCurrentBattleRoyaleTime() <= 5) {
                        battleRoyalCountDownTime.setForeground(Color.red);
                    } else {
                        battleRoyalCountDownTime.setForeground(Color.black);
                    }
                }
            }
        });
        battleRoyaleTimer.start();
        // repainting if monster has caught up to a player, so player should be removed from the map
        //view.repaint();
    }

    /**
     * Creates a JButton with the specified label, styling, and size.
     *
     * @param label The label text for the button.
     * @return The created JButton.
     */
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

    /**
     * Helper method to create a button and add it to the specified panel.
     *
     * @param panel The panel to which the button will be added.
     * @param button The button to be added.
     */
    private void addButton(JPanel panel, JButton button) {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(button);
        panel.add(buttonPanel);

    }

    /**
     * Displays a confirmation dialog for exiting the game. Exits the program if
     * the user confirms.
     */
    private void confirmExit() {
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            System.exit(1);
        }
    }

    /**
     * Displays a confirmation dialog for exiting the game and saving progress.
     * Pauses the game and stops the monster move timer before showing the
     * dialog. Resumes the game and restarts the timer if the user cancels the
     * exit.
     */
    private void confirmExitandSave() {
        game.pauseGame();
        monsterMoveTimer.stop();
        backgroundTimer.stop();
        battleRoyaleTimer.stop();
        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?", "Exit Confirmation", JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            game.deleteSavedGameFile();
            game.saveGame(player1, player2, game.getPlayer1Score(), game.getPlayer2Score(), game.getRoundsToWin());
            returnToMainMenu();
        } else {
            game.resumeGame();
            monsterMoveTimer.restart();
            backgroundTimer.restart();
            battleRoyaleTimer.restart();
        }

    }

    /**
     * Removes the playing view and returns to the main menu. Removes the
     * statistics panel and resets its visibility before returning.
     */
    private void returnToMainMenu() {
        frame.remove(view);
        //frame.remove(statsPanel);
        toggleStatsPanelVisibility(false);
        togglePlayerEffectsVisibility(false);
        frame.add(mMenu);
        frame.revalidate();
        frame.repaint();
    }

    /**
     * Reads file names from a specified directory and sets the string
     * representation of file names. Sets the fileNamesString field to a string
     * containing the names of files in the directory.
     */
    private void readFileNames() {
        String directoryPath = "src/blitzstrike/files/";
        File directory = new File(directoryPath);
        fileList = directory.list();
        StringBuilder stringBuilder = new StringBuilder();
        if (fileList != null) {
            System.out.println("List of files in the directory:");
            for (String fileName : fileList) {
                System.out.println(fileName);
                stringBuilder.append(fileName).append(System.lineSeparator());

            }
        } else {
            System.out.println("The directory is empty or does not exist.");
            stringBuilder.append("The directory is empty or does not exist.");
        }
        fileNamesString = stringBuilder.toString();
        fileNamesArray = fileNamesString.split("\n");
    }

    /**
     * Displays a dialog for continuing a saved game. Collects player names,
     * scores, and number of games from user input to resume the game.
     */
    private void showContinueDialog() {
        JDialog continueDialog = new JDialog(this, "Continue", true);
        JPanel mainPanel2 = new JPanel();
        mainPanel2.setLayout(new BoxLayout(mainPanel2, BoxLayout.Y_AXIS));

        JLabel gamesLabel = new JLabel("Available games:");
        mainPanel2.add(gamesLabel);
        for (String filename : fileNamesArray) {
            JLabel nameLabel = new JLabel(filename);
            mainPanel2.add(nameLabel);
        }

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

        JLabel mapNumber = new JLabel("Enter the map's number:");
        mainPanel2.add(mapNumber);

        JTextField mapNumberField = new JTextField(15);
        mainPanel2.add(mapNumberField);

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
                Integer mapNumberCont = null;

                if (player1NameCont.isEmpty() || player2NameCont.isEmpty()) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter both players' names!", "Missing Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                ////MAP NUMBER//////////////////////////////
                try {

                    if (!mapNumberField.getText().equals("")) {
                        mapNumberCont = Integer.valueOf(mapNumberField.getText());

                    }
                    if (mapNumberCont == null || mapNumberCont <= 0 || mapNumberCont > 3) {
                        allFieldsAreGoodCont = false;
                        throw new NumberFormatException();
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter a valid number of the map (positive integer 1-3)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                ////SCORES////////////////////////////////////
                Integer pl1ScoreCont = null;
                Integer pl2ScoreCont = null;
                try {
                    if (!pl1ScoreField.getText().equals("")) {
                        pl1ScoreCont = Integer.valueOf(pl1ScoreField.getText());

                    }
                    if (!pl2ScoreField.getText().equals("")) {
                        pl2ScoreCont = Integer.valueOf(pl2ScoreField.getText());

                    }
                    if (pl1ScoreCont == null || pl2ScoreCont == null || pl1ScoreCont < 0 || pl2ScoreCont < 0) {
                        allFieldsAreGoodCont = false;
                        throw new NumberFormatException();

                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter both players' scores (must be > 0)!", "Invalid Information", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                /////NUMBER OF GAMES//////////////////////////////////////////
                Integer numGamesCont = 0;
                if (!numGamesField2.getText().equals("")) {
                    numGamesCont = Integer.valueOf(numGamesField2.getText());
                }

                try {
                    if (numGamesCont <= 0) {
                        allFieldsAreGoodCont = false;
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(continueDialog, "Enter a valid number of games (positive integer)!", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    allFieldsAreGoodCont = false;
                }

                if (allFieldsAreGoodCont) {
                    String filenameValid = player1NameCont + "_" + player2NameCont + "_" + mapNumberCont + "_" + pl1ScoreCont + "_" + pl2ScoreCont + "_" + numGamesCont + ".txt";
                    String filePathCont = "src/blitzstrike/files/" + player1NameCont + "_" + player2NameCont + "_" + mapNumberCont + "_" + pl1ScoreCont + "_" + pl2ScoreCont + "_" + numGamesCont + ".txt";

                    for (String filename : fileNamesArray) {
                        if (filenameValid.trim().equals(filename.trim())) {
                            System.out.println("filenameValid : " + filenameValid);
                            System.out.println("filenamein the array : " + filename);

                            fileLoaded = true;
                            break;
                        }
                    }

                    if (fileLoaded) {
                        player1 = new Player(player1NameCont);
                        player2 = new Player(player2NameCont);

                        try {
                            game = new Game(mapNumberCont, filePathCont, player1, player2, numGamesCont);
                            game.continueGame(filePathCont);
                        } catch (Exception ex) {
                            System.out.println(filePathCont);
                            System.out.println("fileLoaded is true, but file still does not exist somhow");
                            JOptionPane.showMessageDialog(continueDialog, "File not found! Enter the exisiting file", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                        }

                        game.setRoundsToWin(Integer.parseInt(numGamesField2.getText()));
                        try {
                            view = new View(game);
                        } catch (IOException ex) {
                            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        frame.remove(mMenu);
                        frame.add(view);
                        updateStatsPanel();
                        continueDialog.setVisible(false);
                        game.resumeGame();
                        monsterMoveTimer.restart();
                        backgroundTimer.restart();
                        battleRoyaleTimer.restart();
                        continueDialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(continueDialog, "File not found! Enter the exisiting file", "Invalid Input", JOptionPane.ERROR_MESSAGE);

                    }
                }
            }
        });
        mainPanel2.add(startButton2);

        mainPanel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        continueDialog.setContentPane(mainPanel2);
        continueDialog.pack();
        continueDialog.setLocationRelativeTo(this);
        continueDialog.setVisible(true);
    }

    public void updateStatsPanel() {
        statsPanel.removeAll();
        player1ScoreLabel = new JLabel("Score: " + game.getPlayer1Score());
        player2ScoreLabel = new JLabel("Score: " + game.getPlayer2Score());
        timerPanel = new JPanel();
        JPanel player1panel = (createPlayerStatsPanel(player1, player1ScoreLabel));
        JPanel player2panel = (createPlayerStatsPanel(player2, player2ScoreLabel));
        player1panel.setBackground(Color.LIGHT_GRAY);
        player2panel.setBackground(Color.LIGHT_GRAY);
        timerPanel.setBackground(Color.LIGHT_GRAY);
        statsPanel.add(player1panel, BorderLayout.WEST);
        statsPanel.add(player2panel, BorderLayout.EAST);
        JLabel roundLabel = new JLabel("Rounds of glorious domination required: " + game.getRoundsToWin());
        timerPanel.add(roundLabel);
        battleRoyalCountDownTime = new JLabel("BattleRoyale Timer: " + Integer.toString(game.getCurrentBattleRoyaleTime()));
        timerPanel.add(battleRoyalCountDownTime);
        statsPanel.add(timerPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public void updatePlayerEffects(JPanel playerEffects, Player player) {
        playerEffects.removeAll();

        for (Curse c : player.getCurses()) {
            JPanel cursePanel = new JPanel();
            cursePanel.setLayout(new BoxLayout(cursePanel, BoxLayout.Y_AXIS));
            System.out.println("Player 1 curse " + c.toString());
            String curseName = c.toString();
            curseName = curseName.replace(" ", "<br>");
            JLabel label = new JLabel("<html>" + curseName + " " + Integer.toString(c.getCurrentCurseTime()) + "</html>");
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            label.setForeground(Color.red);
            cursePanel.add(label, BorderLayout.CENTER);
            playerEffects.add(cursePanel);
            cursePanel.setBackground(Color.LIGHT_GRAY);
        }
        playerEffects.revalidate();
        playerEffects.repaint();

        for (PowerUp p : player.getPowerups()) {
            JPanel powerupPanel = new JPanel();
            powerupPanel.setLayout(new BoxLayout(powerupPanel, BoxLayout.Y_AXIS));
            System.out.println("Player 1 powerup " + p.toString());
            String powerupName = p.toString();
            powerupName = powerupName.replace(" ", "<br>");
            JLabel label = null;
            if (p.hasDuration()) {
                label = new JLabel("<html>" + powerupName + " " + Integer.toString(p.getCurrentPowerupTime()) + "</html>");
            } else {
                if(p instanceof BlastRange){
                    label = new JLabel("<html>" + powerupName+" " + Integer.toString(player.getBlastRange())+"</html>");
                }else if(p instanceof BombIncrease){
                    label = new JLabel("<html>" + powerupName + " " + Integer.toString(player.getBombNumber())+"</html>");
                }else if(p instanceof Obstacle){
                    label = new JLabel("<html>" + powerupName + " " + Integer.toString(player.getMaxNumberOfObstacles())+"</html>");
                }else if(p instanceof Detonator){
                    label = new JLabel("<html>" + powerupName + " " + Integer.toString(player.getBombNumber())+"</html>");
                }
            }
            label.setFont(new Font("SansSerif", Font.BOLD, 12));
            Color forestGreen = new Color(34, 139, 34);
            label.setForeground(forestGreen);
            powerupPanel.add(label, BorderLayout.CENTER);
            playerEffects.add(powerupPanel);
            powerupPanel.setBackground(Color.LIGHT_GRAY);
        }
        playerEffects.revalidate();
        playerEffects.repaint();
    }

    public void showRoundEndPopup(String message) {
        JDialog roundEndDialog = new JDialog(this, "Round Ended", true);
        roundEndDialog.setUndecorated(true);
        roundEndDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        roundEndDialog.setSize(400, 200);
        roundEndDialog.setLocationRelativeTo(this); // Center on screen

        // Create a JPanel with GridBagLayout
        JPanel contentPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Message Label (HTML centering already in place)
        JLabel messageLabel = new JLabel("<html><center>" + message + "<br>Player 1 Score: " + game.getPlayer1Score() + "<br>Player 2 Score: " + game.getPlayer2Score() + "</center></html>");
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 0;
        constraints.weighty = 1.0;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        contentPanel.add(messageLabel, constraints);

        // Next Round Button
        JButton nextRoundButton = new JButton("Go to Next Round");
        nextRoundButton.addActionListener(e -> {
            try {
                player1ScoreLabel.setText("Score: " + game.getPlayer1Score());
                player2ScoreLabel.setText("Score: " + game.getPlayer2Score());
                frame.repaint();
                roundEndDialog.setVisible(false);
                roundEndDialog.dispose();
                game.loadNextRound();
            } catch (Exception ex) {
                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        roundEndDialog.add(nextRoundButton);

        constraints.gridy = 1;  // Place in second row
        constraints.weighty = 0.0;  // No extra vertical space for button
        contentPanel.add(nextRoundButton, constraints);

        roundEndDialog.add(contentPanel);

        roundEndDialog.setVisible(true);
    }

    /**
     * Displays a dialog to indicate the end of the game. Displays a
     * congratulatory message to the winner and provides an option to return to
     * the main menu.
     */
    private void showGameEndPopup() {
        if (game.getEndGame()) {
            JDialog gameEndDialog = new JDialog(this, "Game Ended", true);
            gameEndDialog.setUndecorated(true);
            gameEndDialog.getRootPane().setBorder(BorderFactory.createLineBorder(BLACK, 2));
            gameEndDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); // Set the default close operation
            gameEndDialog.setLayout(new FlowLayout());
            gameEndDialog.setLocationRelativeTo(this); // Center on screen

            JLabel messageLabel = new JLabel("<html><center>Congrats " + game.getWinner().getName() + "</center></html>");
            gameEndDialog.add(messageLabel);

            JButton returnButton = new JButton("Return to main menu");
            returnButton.addActionListener(e -> {
                gameEndDialog.dispose(); // Close and dispose of the dialog
                returnToMainMenu();
                game = null;
            });
            gameEndDialog.add(returnButton);

            gameEndDialog.pack(); // Pack the dialog instead of setting size
            gameEndDialog.setVisible(true);
        }
    }

    /**
     * Creates a panel to display player statistics including name and score.
     *
     * @param player The player whose statistics are to be displayed.
     * @param playerScoreLabel The label displaying the player's score.
     * @return The JPanel containing the player's statistics.
     */
    private JPanel createPlayerStatsPanel(Player player, JLabel playerScoreLabel) {
        playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));

        JLabel playerNameLabel = new JLabel(player.getName());
        playerNameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        playerPanel.add(playerNameLabel);

        playerScoreLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        playerPanel.add(playerScoreLabel);

        return playerPanel;
    }

    /**
     * Displays a dialog for setting up a new game. Collects player names, map
     * selection, and number of games from user input to start the game.
     *
     * @param mMenu The main menu panel.
     */
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
        map1RadioButton.setSelected(true);
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

                if (!map1RadioButton.isSelected() && !map2RadioButton.isSelected() && !(map3RadioButton.isSelected())) {
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

                    int selectedMap = 1;
                    if (map1RadioButton.isSelected()) {
                        selectedMap = 1;
                    } else if (map2RadioButton.isSelected()) {
                        selectedMap = 2;
                    } else if (map3RadioButton.isSelected()) {
                        selectedMap = 3;
                    }

                    player1 = new Player(player1Name);
                    player2 = new Player(player2Name);

                    game = new Game(selectedMap, "", player1, player2, numGames);

                    game.loadMap();

                    try {
                        view = new View(game);
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    frame.remove(mMenu);
                    frame.add(view);
                    updateStatsPanel();
                    gameSetupDialog.setVisible(false);

                    //added to fix the new game, if problem appears, delete
                    monsterMoveTimer.restart();
                    backgroundTimer.restart();
                    battleRoyaleTimer.restart();
                    //////

                    gameSetupDialog.dispose();
                }
            }
        }
        );
        mainPanel.add(startButton);

        mainPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gameSetupDialog.setContentPane(mainPanel);
        gameSetupDialog.pack();
        gameSetupDialog.setLocationRelativeTo(this);
        gameSetupDialog.setVisible(true);
    }

}
