
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.model.monsters.Monster;
import blitzstrike.model.monsters.Monster1;
import blitzstrike.model.monsters.Monster2;
import blitzstrike.model.monsters.Monster3;
import blitzstrike.model.monsters.Monster4;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {

    public static final int MAP_SIZE = 15;
    public static final int INITIAL_BATTLE_ROYALE_DURATION = 180;

    private boolean endGame;
    private boolean endRound;
    private boolean isPaused;
    private boolean isExplosionInProgress;
    private int roundsToWin;
    private int currentBattleRoyaleDuration;
    private int currentBattleRoyaleTime;
    private int currentLayerOfBattleRoyale;
    private int player1Score;
    private int player2Score;
    private Player player1;
    private Player player2;
    private Player winner;
    private String filepath;
    private LocalTime startingTime;
    private ArrayList<Monster> monsters;
    private Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];

    ////////////////////////////////////////////Public constructors//////////////////////////
    public Game() {
    }

    public Game(String filepath, Player player1, Player player2, int roundsToWin) {
        this.filepath = filepath;
        this.roundsToWin = roundsToWin;
        this.player1 = player1;
        this.player2 = player2;
        this.monsters = new ArrayList<>();
        this.isExplosionInProgress = false;
        this.endGame = false;
        this.endRound = false;
        this.currentLayerOfBattleRoyale = 1;
        this.currentBattleRoyaleDuration = INITIAL_BATTLE_ROYALE_DURATION;
        this.currentBattleRoyaleTime = currentBattleRoyaleDuration;
    }

    ///////////////////////////////////////////getters and setters//////////////////////////////////////
    /**
     * Returns true if the round is ended, otherwise false
     *
     * @return endRound value
     */
    public boolean getEndRound() {
        return this.endRound;
    }

    /**
     * Returns true if the entire game is ended, otherwise false
     *
     * @return endGame value
     */
    public boolean getEndGame() {
        return this.endGame;
    }

    /**
     * Getter
     *
     * @return currentBattleRoyaleTime value
     */
    public int getCurrentBattleRoyaleTime() {
        return this.currentBattleRoyaleTime;
    }

    /**
     * Getter
     *
     * @return player1Score value
     */
    public int getPlayer1Score() {
        return this.player1Score;
    }

    /**
     * Getter
     *
     * @return player2Score value
     */
    public int getPlayer2Score() {
        return this.player2Score;
    }

    /**
     * Getter
     *
     * @return player1 value
     */
    public Player getPlayer11() {
        return player1;
    }

    /**
     * Getter
     *
     * @return player2 value
     */
    public Player getPlayer22() {
        return player2;
    }

    /**
     * Getter
     *
     * @return isPaused value
     */
    public boolean getIsPaused() {
        return isPaused;
    }

    /**
     * Getter
     *
     * @return final winner value
     */
    public Player getWinner() {
        //final winner
        return this.winner;
    }

    /**
     * Getter
     *
     * @return final winner value
     */
    public int getRoundsToWin() {
        //final winner
        return this.roundsToWin;
    }

    /**
     * Getter
     *
     * @return array of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    /**
     * Setter
     *
     * sets a new value for roundsToWin
     */
    public void setRoundsToWin(int rToWin) {
        this.roundsToWin = rToWin;
    }

    ////////////////////////////////////public methods//////////////////////////////
    /**
     * Method to check if there is a box on a cell
     *
     * @param x The x-coordinate of the cell in the space.
     * @param y The y-coordinate of the cell in the space.
     * @return The Box object found at the specified coordinates, or null if no
     * Box exists at those coordinates.
     */
    public Box getBox(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Box) {
            return (Box) cell;
        }
        return null;
    }

    /**
     * Method to check if there is an obstacle on a cell
     *
     * @param x The x-coordinate of the cell in the space.
     * @param y The y-coordinate of the cell in the space.
     * @return The ObstacleBox object found at the specified coordinates, or
     * null if no obstacle exists at those coordinates.
     */
    public ObstacleBox getObstacle(int x, int y) {
        if (this.player1.hasObstacleAtPosition(x, y)) {
            return this.player1.getObstacle(x, y);
        } else if (this.player2.hasObstacleAtPosition(x, y)) {
            return this.player2.getObstacle(x, y);
        }
        return null;
    }

    /**
     * Method to check if there is a bomb on a cell
     *
     * @param x The x-coordinate of the cell in the space.
     * @param y The y-coordinate of the cell in the space.
     * @return The Bomb object found at the specified coordinates, or null if no
     * bomb exists at those coordinates.
     */
    public Bomb getBomb(int x, int y) {
        if (this.player1.hasBombAtPosition(x, y)) {
            return this.player1.getBomb(x, y);
        } else if (this.player2.hasBombAtPosition(x, y)) {
            return this.player2.getBomb(x, y);
        }
        return null;
    }

    /**
     * This method retrieves an Empty object from the specified coordinates
     * within the space.
     *
     * @param x The horizontal position (x-coordinate) of the cell in the space.
     * @param y The vertical position (y-coordinate) of the cell in the space.
     * @return The Empty object found at the provided coordinates, or null if
     * the cell at that location is not empty.
     */
    public Empty getEmpty(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Empty) {
            return (Empty) cell;
        }
        return null;
    }

    /**
     * This method retrieves a Wall object from the specified coordinates within
     * the space.
     *
     * @param x The horizontal position (x-coordinate) of the cell in the space.
     * @param y The vertical position (y-coordinate) of the cell in the space.
     * @return The Wall object located at the specified coordinates, or null if
     * the cell at the given coordinates is not a wall.
     */
    public Wall getWall(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Wall) {
            return (Wall) cell;
        }
        return null;
    }

    /**
     * Retrieves the first player object at the specified coordinates in the
     * game.
     *
     * @param x The x-coordinate of the player's position.
     * @param y The y-coordinate of the player's position.
     * @return The first player object located at the specified coordinates if
     * it's alive and positioned there, otherwise returns null.
     */
    public Player getPlayer1(int x, int y) {
        if (player1.isAlive() && player1.getPosition().getX() == x && player1.getPosition().getY() == y) {
            return player1;
        }
        return null;
    }

    /**
     * Retrieves the second player object at the specified coordinates in the
     * game.
     *
     * @param x The x-coordinate of the player's position.
     * @param y The y-coordinate of the player's position.
     * @return The second player object located at the specified coordinates if
     * it's alive and positioned there, otherwise returns null.
     */
    public Player getPlayer2(int x, int y) {
        if (player2.isAlive() && player2.getPosition().getX() == x && player2.getPosition().getY() == y) {
            return player2;
        }
        return null;
    }

    /**
     * Retrieves a Monster object located at the specified coordinates in the
     * game.
     *
     * @param x The x-coordinate of the position to search for a Monster.
     * @param y The y-coordinate of the position to search for a Monster.
     * @return A Monster object located at the specified coordinates if one
     * exists, otherwise returns null.
     */
    public Monster getMonster(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getPosition().getX() == x && monster.getPosition().getY() == y) {
                return monster;
            }
        }
        return null;
    }

    /**
     * Returns true if it's either end of the round or the entire game
     *
     * @return
     */
    public boolean isGameOrRoundEnded() {
        return this.endGame || this.endRound;
    }

    /**
     * Returns true if either first player's or the second player's bomb is
     * positioned at the given coordinates
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isBombAtPosition(int x, int y) {
        return this.player1.hasBombAtPosition(x, y) || this.player2.hasBombAtPosition(x, y);
    }

    /**
     * Sets the value to false when player is alive
     *
     * @return false
     */
    public boolean isPlayerDead() {
        return false;
    }

    /**
     * Moves player 1 in the specified direction and handles collisions.
     *
     * @param d The direction in which to move player 1.
     */
    public void movePlayer1(Direction d) {
        player1.movePlayer(d, player2);
        handleCollision();
    }

    /**
     * Moves player 2 in the specified direction and handles collisions.
     *
     * @param d The direction in which to move player 1.
     */
    public void movePlayer2(Direction d) {
        player2.movePlayer(d, player1);
        handleCollision();
    }

    /**
     * Moves all monsters and handles collisions.
     */
    public void moveMonsters() {
        for (Monster monster : monsters) {
            monster.move();
        }
        handleCollision();
    }

    /**
     * Handles the curses of players
     */
    public void handleCurseTermination() {
        this.player1.handleCurseRemoval();
        this.player2.handleCurseRemoval();
    }

    /**
     * Removes terminated curses from the players
     */
    public void removeTerminatedCurses() {
        this.player1.removeTerminatedCurses();
        this.player2.removeTerminatedCurses();
    }

    /**
     * Handles the termination of active power-ups for both players. This method
     * calls the handlePowerupRemoval() method for each player to remove
     * terminated power-ups.
     */
    public void handlePowerupTermination() {
        this.player1.handlePowerupRemoval();
        this.player2.handlePowerupRemoval();
    }

    /**
     * Removes terminated power-ups for both players. This method calls the
     * removeTerminatedPowerUps() method for each player.
     */
    public void removeTerminatedPowerups() {
        this.player1.removeTerminatedPowerUps();
        this.player2.removeTerminatedPowerUps();
    }

    /**
     * Checks if the game has reached its conclusion. The game is considered
     * totally finished if either player1's score or player2's score is greater
     * than or equal to the rounds required to win.
     *
     * @return true if the game is totally finished, otherwise false.
     */
    public boolean isGameTotallyFinished() {
        return this.player1Score >= this.roundsToWin || this.player2Score >= this.roundsToWin;
    }

    /**
     * Handles collisions between monsters and players, as well as between
     * players and effects. If a player encounters a monster and is not
     * invincible, the player dies. If a player steps on a box with a destroyed
     * effect, the effect is applied to the player. This method updates the game
     * space accordingly.
     */
    public void handleCollision() {
        // collision of monsters and players 
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(player1.getPosition())) {
                if (player1.isInvincible()) {
                    System.out.println(player1.getName() + " cannot die");
                } else {
                    player1.die();
                    System.out.println(player1.getName() + " has encountered a monster!");
                }
            }

            if (player2 != null && monster.getPosition().equals(player2.getPosition())) {
                if (player2.isInvincible()) {
                    System.out.println(player2.getName() + " cannot die");
                } else {
                    player2.die();
                    System.out.println(player2.getName() + " has encountered a monster!");
                }
            }
        }

        // players and effects
        if (player1.isAlive()) {
            if (this.space[player1.getPosition().getY()][player1.getPosition().getX()].isDestroyed()) {
                ((Box) this.space[player1.getPosition().getY()][player1.getPosition().getX()]).getEffect().applyEffect(player1);
                this.space[player1.getPosition().getY()][player1.getPosition().getX()] = new Empty(player1.getPosition());
            }
        }
        if (player2.isAlive()) {
            if (this.space[player2.getPosition().getY()][player2.getPosition().getX()].isDestroyed()) {
                ((Box) this.space[player2.getPosition().getY()][player2.getPosition().getX()]).getEffect().applyEffect(player2);
                this.space[player2.getPosition().getY()][player2.getPosition().getX()] = new Empty(player2.getPosition());
            }
        }
    }

    /**
     * Handles the explosion of bombs placed by both players. This method
     * triggers the explosion of each bomb and updates the game state
     * accordingly.
     */
    public void handleBombExplosion() {
        //player1 bombs
        ArrayList<Bomb> player1BombsCopy = new ArrayList<>(this.player1.getBombs());
        Iterator<Bomb> bombIterator1 = player1BombsCopy.iterator();
        while (bombIterator1.hasNext()) {
            Bomb b = bombIterator1.next();
            b.setExploding();
            if (b.getExploding()) {
                System.out.println("Bomb at X: " + b.getPosition().getX() + " Y: " + b.getPosition().getY() + " is exploding!");
                b.handleExplosion(player1, player2, monsters);
            }
        }
        //player2 bombs
        ArrayList<Bomb> player2BombsCopy = new ArrayList<>(this.player2.getBombs());
        Iterator<Bomb> bombIterator2 = player2BombsCopy.iterator();
        while (bombIterator2.hasNext()) {
            Bomb b = bombIterator2.next();
            b.setExploding();
            if (b.getExploding()) {
                System.out.println("Bomb at X: " + b.getPosition().getX() + " Y: " + b.getPosition().getY() + " is exploding!");
                b.handleExplosion(player1, player2, monsters);
            }
        }
    }

    /*
     * Checks if the cell at the specified coordinates is a Wall.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return true if the cell is a Wall, false otherwise.
     */
    public boolean isWall(int x, int y) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            return space[y][x] instanceof Wall;
        }
        return false; // Return false if the coordinates are out of bounds.
    }

    /*
     * Checks if the cell at the specified coordinates is a Box.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return true if the cell is a Wall, false otherwise.
     */
    public boolean isBox(int x, int y) {
        if (x >= 0 && x < MAP_SIZE && y >= 0 && y < MAP_SIZE) {
            return space[y][x] instanceof Box;
        }
        return false; // Return false if the coordinates are out of bounds.
    }

    /*
     * Checks if either player is positioned on a cell that is a Wall or Box and
     * handles their ghost condition. If a player is on a Wall or Box and is not
     * in a ghost state, the player will "die". The ghost state allows players
     * to pass through walls and boxes without dying. 
     */
    public void handleGhostCondition() {
        if (isWall(player1.getPosition().getX(), player1.getPosition().getY()) || isBox(player1.getPosition().getX(), player1.getPosition().getY())) {

            System.out.println("PLAYER1 POSITION IS WALL OR BOX");
            if (!player1.isGhost()) {
                player1.die();
                System.out.println("Player 1 die is called because its on the wall or box");
            }

        }
        if (isWall(player2.getPosition().getX(), player2.getPosition().getY()) || isBox(player2.getPosition().getX(), player2.getPosition().getY())) {

            System.out.println("PLAYER2 POSITION IS WALL OR BOX");

            if (!player2.isGhost()) {
                player2.die();
                System.out.println("Player 2 die is called because its on the wall or box");
            }
        }
    }

    /**
     * Removes dead monsters from the game. Dead monsters are removed from the
     * list of active monsters.
     */
    public void removeDeadMonsters() {
        Iterator<Monster> monsterIterator = this.monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            if (!monster.isAlive()) {
                monsterIterator.remove();
            }
        }
    }

    /**
     * Handles the end of a round. If the end of the round flag is set, it
     * resets the flag and loads the next round. This method is typically called
     * when the user clicks "continue" in a dialog to proceed to the next round.
     */
    public void handleEndRound() {
        if (this.endRound) {
            this.endRound = false;
            loadNextRound();
        }
    }

    /**
     * Calls placeBombFromImmediateBombCurse for both players
     */
    public void handleImmediateBombCurseForBothPlayers() {
        this.player1.placeBombFromImmediateBombCurse();
        this.player2.placeBombFromImmediateBombCurse();
    }

    /**
     * Handles the death of a player during the game. If one of the players is
     * dead and the round and game are not already ended, this method checks if
     * the dead player has been dead for a certain amount of time (equal to the
     * bomb countdown duration). If so, it increments the score of the opposing
     * player and calls the method to finish the round and potentially the game.
     */
    public void handleDeathOfThePlayer() {
        if (isOneOfThePlayersDead() && !this.endRound && !this.endGame) {
            if (this.player1.isAlive() && !this.player2.isAlive()) {
                if (Duration.between(this.player2.getDeathTime(), LocalTime.now()).getSeconds() >= Bomb.BOMB_COUNTDOWN) {
                    this.player1Score++;
                    handleFinishRoundAndGame();
                }
            } else if (this.player2.isAlive() && !this.player1.isAlive()) {
                if (Duration.between(this.player1.getDeathTime(), LocalTime.now()).getSeconds() >= Bomb.BOMB_COUNTDOWN) {
                    this.player2Score++;
                    handleFinishRoundAndGame();
                }
            } else if (!this.player1.isAlive() && !this.player2.isAlive()) {
                System.out.println("They both are dead");
                if (Duration.between(this.player1.getDeathTime(), LocalTime.now()).getSeconds() >= Bomb.BOMB_COUNTDOWN && Duration.between(this.player2.getDeathTime(), LocalTime.now()).getSeconds() >= Bomb.BOMB_COUNTDOWN) {
                    handleFinishRoundAndGame();
                }
            }
        }
    }

    /**
     * Decreases currentBattleRoyaleDuration by one
     */
    public void decreaseBattleRoyaleTime() {
        if (this.currentBattleRoyaleTime > 0) {
            this.currentBattleRoyaleTime--;
        }
    }

    /**
     * Handles the progression of the battle royale game mode. When the
     * specified duration for a battle royale round has passed, this method
     * shrinks the map and increases the layer of the battle royale zone.
     * Additionally, if the current battle royale duration is greater than 20,
     * it reduces the duration by 25% and resets the timer.
     */
    public void handleBattleRoyale() {

        if (startingTime == null) {
            startingTime = LocalTime.now();
        }
        if (Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= currentBattleRoyaleDuration) {
            System.out.println("Shrink happens");
            shrinkMap();
            this.currentLayerOfBattleRoyale++;
            if (this.currentBattleRoyaleDuration > 20) {
                this.currentBattleRoyaleDuration *= 0.75;
                this.currentBattleRoyaleTime = this.currentBattleRoyaleDuration;
            }
            //this.startingTime = LocalTime.now();
        }
    }

    /**
     * Checks if a cell at the specified coordinates can explode.
     *
     * @param x The x-coordinate of the cell.
     * @param y The y-coordinate of the cell.
     * @return true if the cell can explode, otherwise false.
     */
    public boolean canExplode(int x, int y) {
        if (x < 0 || y < 0 || x >= MAP_SIZE || y >= MAP_SIZE) {
            return false; // Out of bounds
        }

        Cell cell = space[y][x];
        if (cell instanceof Wall) {
            return false; // Walls block explosion
        }

        return true; // Empty cells and undestroyed boxes can explode
    }

    /**
     * Loads the next round of the game by reloading the map and resetting both
     * players. This method is typically called when transitioning to a new
     * round of the game.
     */
    public void loadNextRound() {

        try {
            loadMap();

            this.player1.reset();
            this.player2.reset();
            currentBattleRoyaleTime = INITIAL_BATTLE_ROYALE_DURATION;
        } catch (Exception e) {
            loadMapforContinue(filepath);
            currentBattleRoyaleTime = INITIAL_BATTLE_ROYALE_DURATION;
            this.player1.reset();
            this.player2.reset();

        }
    }

    /**
     * Restarts the game by resetting both players, reloading the map, resetting
     * scores, and updating the starting time and game status.
     * It throws exception when the game has already been saved, in this case
     * we use another method to load the game
     */
    public void restartgame() {

        try {
            player1.reset();
            player2.reset();
            loadMap();
            player1Score = 0;
            player2Score = 0;
            startingTime = LocalTime.now();
            currentBattleRoyaleTime = INITIAL_BATTLE_ROYALE_DURATION;
            endGame = false;
        } catch (Exception e) {

            player1.reset();
            player2.reset();
            loadMapforContinue(filepath);
            player1Score = 0;
            player2Score = 0;
            startingTime = LocalTime.now();
            currentBattleRoyaleTime = INITIAL_BATTLE_ROYALE_DURATION;
            endGame = false;

        }

    }

    /**
     * Pauses the game by setting the game state to paused.
     */
    public void pauseGame() {
        isPaused = true;
    }

    /**
     * Resumes the game by setting the game state to not paused.
     */
    public void resumeGame() {
        isPaused = false;
    }

    /**
     * Loads the map from the file specified by {@code filepath} and initializes
     * the game state accordingly.
     */
    public void loadMap() {
        this.monsters = new ArrayList<>();
        this.space = new Cell[MAP_SIZE][MAP_SIZE];
        this.startingTime = LocalTime.now();
        this.player1.setSpace(this.space);
        this.player2.setSpace(this.space);

        this.endRound = false;

        String map = readFile();

        //populate matrix
        for (int row = 0; row < MAP_SIZE; row++) {
            String line = map.split("\n")[row];
            for (int column = 0; column < MAP_SIZE; column++) {
                Position position = new Position(column, row);
                char symbol = line.charAt(column);
                Cell cell;
                switch (symbol) {
                    case 'x':
                        cell = new Wall(position);
                        space[row][column] = cell;
                        break;
                    case '#':
                        cell = new Box(position);
                        space[row][column] = cell;
                        break;
                    case 'a':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        this.player1.setPosition(position);
                        break;
                    case 'b':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        this.player2.setPosition(position);
                        break;
                    case '1':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        Monster monster = new Monster1(position, this.space, this, player1, player2);
                        this.monsters.add(monster);
                        break;
                    case '2':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        Monster monster2 = new Monster2(position, this.space, this, player1, player2);
                        this.monsters.add(monster2);
                        break;
                    case '3':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        Monster monster3 = new Monster3(position, this.space, this, player1, player2);
                        this.monsters.add(monster3);
                        break;
                    case '4':
                        cell = new Empty(position);
                        space[row][column] = cell;
                        Monster monster4 = new Monster4(position, this.space, this, player1, player2);
                        this.monsters.add(monster4);
                        break;
                    default:
                        cell = new Empty(position);
                        space[row][column] = cell;
                        break;
                }
                space[row][column] = cell;
            }
        }
    }

    /**
     * Loads the map for the saved games
     *
     * @param filepath The path to the file containing the saved game state.
     */
    public void loadMapforContinue(String filepath) {

        this.monsters = new ArrayList<>();
        this.space = new Cell[MAP_SIZE][MAP_SIZE];
        this.startingTime = LocalTime.now();
        this.player1.setSpace(this.space);
        this.player2.setSpace(this.space);

        //this.startingTime = LocalTime.now();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player1Name:")) {
                    continue;
                } else if (line.startsWith("Player2Name:")) {
                    continue;
                } else if (line.startsWith("Player1Score:")) {
                    continue;
                } else if (line.startsWith("Player2Score:")) {
                    continue;
                } else if (line.startsWith("RoundsToWin:")) {
                    continue;
                } else {
                    // Assuming this line is part of the map
                    // You'll start processing the map after handling all the metadata
                    break;
                }
            }

            //map
            int y = 0;
            //this.startingTime = LocalTime.now();
            while ((line = reader.readLine()) != null && y < MAP_SIZE) {
                for (int x = 0; x < line.length() && x < MAP_SIZE; x++) {
                    char symbol = line.charAt(x);
                    Position position = new Position(x, y);
                    switch (symbol) {
                        case 'x':
                            space[y][x] = new Wall(position);
                            break;
                        case '#':
                            space[y][x] = new Box(position);
                            break;
                        case 'a':
                            player1.setPosition(new Position(1, 13));
                            space[y][x] = new Empty(position);
                            break;
                        case 'b':
                            player2.setPosition(new Position(13, 1));
                            space[y][x] = new Empty(position);
                            break;
                        case '1':
                            Monster monster = new Monster1(position, space, this, player1, player2);
                            monsters.add(monster);
                            space[y][x] = new Empty(position);
                            break;
                        case '2':
                            Monster monster2 = new Monster2(position, space, this, player1, player2);
                            monsters.add(monster2);
                            space[y][x] = new Empty(position);
                            break;
                        case '3':
                            Monster monster3 = new Monster3(position, space, this, player1, player2);
                            monsters.add(monster3);
                            space[y][x] = new Empty(position);
                            break;
                        case '4':
                            Monster monster4 = new Monster4(position, space, this, player1, player2);
                            monsters.add(monster4);
                            space[y][x] = new Empty(position);
                            break;
                        case ' ':
                            space[y][x] = new Empty(position);
                            break;
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Continues the game from a saved state specified by the file path.
     *
     * @param filepath The path to the file containing the saved game state.
     */
    public void continueGame(String filepath) {

        this.monsters = new ArrayList<>();
        this.space = new Cell[MAP_SIZE][MAP_SIZE];
        this.startingTime = LocalTime.now();
        this.player1.setSpace(this.space);
        this.player2.setSpace(this.space);

        //this.startingTime = LocalTime.now();
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Player1Name:")) {
                    player1.setName(line.substring("Player1Name:".length()));
                } else if (line.startsWith("Player2Name:")) {
                    player2.setName(line.substring("Player2Name:".length()));
                } else if (line.startsWith("Player1Score:")) {
                    player1Score = Integer.parseInt(line.substring("Player1Score:".length()));
                } else if (line.startsWith("Player2Score:")) {
                    player2Score = Integer.parseInt(line.substring("Player2Score:".length()));
                } else if (line.startsWith("RoundsToWin:")) {
                    setRoundsToWin(Integer.parseInt(line.substring("RoundsToWin:".length())));
                } else {
                    // Assuming this line is part of the map
                    // You'll start processing the map after handling all the metadata
                    break;
                }
            }

            //map
            int y = 0;
            //this.startingTime = LocalTime.now();
            while ((line = reader.readLine()) != null && y < MAP_SIZE) {
                for (int x = 0; x < line.length() && x < MAP_SIZE; x++) {
                    char symbol = line.charAt(x);
                    Position position = new Position(x, y);
                    switch (symbol) {
                        case 'x':
                            space[y][x] = new Wall(position);
                            break;
                        case '#':
                            space[y][x] = new Box(position);
                            break;
                        case 'a':
                            player1.setPosition(position);
                            space[y][x] = new Empty(position);
                            break;
                        case 'b':
                            player2.setPosition(position);
                            space[y][x] = new Empty(position);
                            break;
                        case '1':
                            Monster monster = new Monster1(position, space, this, player1, player2);
                            monsters.add(monster);
                            space[y][x] = new Empty(position);
                            break;
                        case '2':
                            Monster monster2 = new Monster2(position, space, this, player1, player2);
                            monsters.add(monster2);
                            space[y][x] = new Empty(position);
                            break;
                        case '3':
                            Monster monster3 = new Monster3(position, space, this, player1, player2);
                            monsters.add(monster3);
                            space[y][x] = new Empty(position);
                            break;
                        case '4':
                            Monster monster4 = new Monster4(position, space, this, player1, player2);
                            monsters.add(monster4);
                            space[y][x] = new Empty(position);
                            break;
                        case ' ':
                            space[y][x] = new Empty(position);
                            break;
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Saves the current game state to a file. The saved data includes player
     * information, scores, rounds to win, and the current map state. The game
     * is paused during the saving process to ensure data integrity.
     *
     * @param player1 The first player.
     * @param player2 The second player.
     * @param player1score The score of the first player.
     * @param player2score The score of the second player.
     * @param roundsToWin The number of rounds required to win the game.
     */
    public void saveGame(Player player1, Player player2, int player1score, int player2score, int roundsToWin/*, Timer timer*/) {
        StringBuilder mapBuilder = new StringBuilder();
        pauseGame();
        // Save map state
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                Cell cell = space[y][x];
                if (cell instanceof Wall) {
                    mapBuilder.append('x');
                } else if (cell instanceof Box) {
                    mapBuilder.append('#');
                } else if (player1.getPosition().equals(new Position(x, y))) {
                    mapBuilder.append('a');
                } else if (player2.getPosition().equals(new Position(x, y))) {
                    mapBuilder.append('b');
                } else if (isMonsterAtPosition(x, y)) {
                    Monster m = getMonster(x, y);
                    //Char monsterString = '';
                    if (m != null) {
                        switch (m.monsterType) {
                            case BASIC:
                                mapBuilder.append('1');
                                break;
                            case MONSTER2:
                                mapBuilder.append('2');
                                break;
                            case MONSTER3:
                                mapBuilder.append('3');
                                break;
                            case MONSTER4:
                                mapBuilder.append('4');
                                break;
                        }
                    }
                    //mapBuilder.append('1');
                } else {
                    mapBuilder.append(' ');
                }
            }
            mapBuilder.append("\n");
        }

        try (FileWriter writer = new FileWriter("src/blitzstrike/files/" + player1.getName() + "_" + player2.getName() + "_" + player1Score + "_" + player2Score + "_" + roundsToWin + ".txt")) {
            writer.write("Player1Name:" + player1.getName() + "\n");
            writer.write("Player2Name:" + player2.getName() + "\n");
            writer.write("Player1Score:" + player1Score + "\n");
            writer.write("Player2Score:" + player2Score + "\n");
            writer.write("RoundsToWin:" + roundsToWin + "\n");
            writer.write("\n");

            writer.write(mapBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Reads and returns the content of a file.
     *
     * @return The content of the file as a string.
     */
    public String readFile() {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.filepath));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString();
        return result;

    }

    /////////////////private methods////////////////////////
    /**
     * Returns true if the cell at the given indices is on the edge
     *
     * @param i
     * @param j
     */
    private boolean isEdge(int i, int j) {
        return (i == currentLayerOfBattleRoyale || i == MAP_SIZE - 1 - currentLayerOfBattleRoyale || j == currentLayerOfBattleRoyale || j == MAP_SIZE - 1 - currentLayerOfBattleRoyale);
    }

    /**
     * Checks if there is a monster at the specified position.
     *
     * @param x The x-coordinate of the position to check.
     * @param y The y-coordinate of the position to check.
     * @return true if there is a monster at the specified position, otherwise
     * false.
     */
    private boolean isMonsterAtPosition(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(new Position(x, y))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if at least one of the players is dead.
     *
     * @return true if at least one of the players is dead, otherwise false.
     */
    private boolean isOneOfThePlayersDead() {
        return !player1.isAlive() || !player2.isAlive();
    }

    /**
     * Handles the completion of a round and determines if the game is totally
     * finished. If the game is not totally finished, it sets the end of the
     * round flag to true. If the game is totally finished, it determines the
     * winner based on the scores and sets the end of the game flag to true.
     */
    private void handleFinishRoundAndGame() {
        if (!isGameTotallyFinished()) {
            this.endRound = true;
        } else {
            this.winner = this.player1Score > this.player2Score ? this.player1 : this.player2;
            this.endGame = true;
            deleteSavedGameFile();
        }
    }

    /**
     * Shrinks the map
     */
    private void shrinkMap() {
        for (int i = 0; i < MAP_SIZE; i++) {
            for (int j = 0; j < MAP_SIZE; j++) {
                if (isEdge(i, j)) {
                    crushByWalls(i, j);
                    turnIntoWalls(i, j);
                }
            }
        }
    }

    /**
     * Unalives living entities in the cell at the given indices
     *
     * @param i
     * @param j
     */
    private void crushByWalls(int i, int j) {
        if (this.player1.getPosition().getX() == i && this.player1.getPosition().getY() == j && this.player2.getPosition().getX() == i && this.player2.getPosition().getY() == j) {
            this.player1.die();
            this.player2.die();
        } else if (this.player1.getPosition().getX() == i && this.player1.getPosition().getY() == j) {
            this.player1.die();
        } else if (this.player2.getPosition().getX() == i && this.player2.getPosition().getY() == j) {
            this.player2.die();
        }
        for (Monster monster : monsters) {
            if (monster.getPosition().getX() == i && monster.getPosition().getY() == j) {
                monster.die();
            }
        }
    }

    /**
     * Shrinks the map
     */
    private void turnIntoWalls(int i, int j) {
        this.space[i][j] = new Wall(new Position(j, i));
    }

    /**
     * Delete the files of saved games when they end eventually
     */
    public void deleteSavedGameFile() {
        File file = new File(filepath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Saved game file deleted successfully.");
            } else {
                System.out.println("Failed to delete the saved game file.");
            }
        }
    }

}
