
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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author aselh
 */
public class Game {

    public static final int MAP_SIZE = 15;
    public static final int INITIAL_BATTLE_ROYALE_DURATION = 180;
    public boolean endGame;
    public boolean endRound;

    private int roundsToWin;
    private LocalTime startingTime;

    private int currentBattleRoyaleDuration;
    private int currentBattleRoyaleTime;
    private int currentLayerOfBattleRoyale;

    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;
    private String filepath;

    private Player winner;
    private Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];
    private boolean isPaused;

    private ArrayList<Monster> monsters;

    // to check if there's any explosion for View class
    private boolean isExplosionInProgress;

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

    public Game() {
    }

    public int getCurrentBattleRoyaleTime() {
        return this.currentBattleRoyaleTime;
    }

    // basically, to refresh everything based on the file.
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

    public void continueGame(String filepath) {
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

    public void loadNextRound() {
        loadMap();
        this.player1.reset();
        this.player2.reset();
    }

    public void restartgame() {

        player1.reset();
        player2.reset();
        loadMap();
        player1Score = 0;
        player2Score = 0;
        startingTime = LocalTime.now();
        endGame = false;
    }

    public boolean isGameOrRoundEnded() {
        return this.endGame || this.endRound;
    }

    public int getPlayer1Score() {
        return this.player1Score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
    }

    public void pauseGame() {
        isPaused = true;
    }

    public void resumeGame() {
        isPaused = false;
    }

    public void movePlayer1(Direction d) {
        player1.movePlayer(d, player2);
        handleCollision();
    }

    public void movePlayer2(Direction d) {
        player2.movePlayer(d, player1);
        handleCollision();
    }

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

    public void handlePowerupTermination() {
        this.player1.handlePowerupRemoval();
        this.player2.handlePowerupRemoval();
    }

    public void removeTerminatedPowerups() {
        this.player1.removeTerminatedPowerUps();
        this.player2.removeTerminatedPowerUps();
    }

    public boolean isGameTotallyFinished() {
        return this.player1Score >= this.roundsToWin || this.player2Score >= this.roundsToWin;
    }

    // collision of monsters and players & players and effects
    public void handleCollision() {
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

    public void handleBombExplosion() {
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

            writer.write(mapBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

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

    public void removeDeadMonsters() {
        Iterator<Monster> monsterIterator = this.monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            if (!monster.isAlive()) {
                monsterIterator.remove();
            }
        }
    }

    // getters 
    public Player getPlayer11() {
        return player1;
    }

    public Player getPlayer22() {
        return player2;
    }

    public Box getBox(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Box) {
            return (Box) cell;
        }
        return null;
    }

    public Empty getEmpty(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Empty) {
            return (Empty) cell;
        }
        return null;
    }

    public Wall getWall(int x, int y) {
        Cell cell = space[y][x];
        if (cell instanceof Wall) {
            return (Wall) cell;
        }
        return null;
    }

    public Player getPlayer1(int x, int y) {
        if (player1.isAlive() && player1.getPosition().getX() == x && player1.getPosition().getY() == y) {
            return player1;
        }
        return null;
    }

    public Player getPlayer2(int x, int y) {
        if (player2.isAlive() && player2.getPosition().getX() == x && player2.getPosition().getY() == y) {
            return player2;
        }
        return null;
    }

    public Monster getMonster(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getPosition().getX() == x && monster.getPosition().getY() == y) {
                return monster;
            }
        }
        return null;
    }

    public Bomb getBomb(int x, int y) {
        if (this.player1.hasBombAtPosition(x, y)) {
            return this.player1.getBomb(x, y);
        } else if (this.player2.hasBombAtPosition(x, y)) {
            return this.player2.getBomb(x, y);
        }
        return null;
    }

    public boolean getIsPaused() {
        return isPaused;
    }

    public boolean isBombAtPosition(int x, int y) {
        return this.player1.hasBombAtPosition(x, y) || this.player2.hasBombAtPosition(x, y);
    }

    public boolean isPlayerDead() {
        return false;
    }

    private boolean isMonsterAtPosition(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(new Position(x, y))) {
                return true;
            }
        }
        return false;
    }

    public Player getWinner() {
        //final winner
        return this.winner;
    }

    public int getRoundsToWin() {
        //final winner
        return this.roundsToWin;
    }

    public void setRoundsToWin(int rToWin) {
        this.roundsToWin = rToWin;
    }

    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    private boolean isOneOfThePlayersDead() {
        return !player1.isAlive() || !player2.isAlive();
    }

    private void handleFinishRoundAndGame() {
        if (!isGameTotallyFinished()) {
            this.endRound = true;
        } else {
            this.winner = this.player1Score > this.player2Score ? this.player1 : this.player2;
            this.endGame = true;
        }
    }

    // When in the dialog we click continue, we will call this method
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

    // Main Window must check endRound and endGame and based on them call JDialog.
    public void handleDeathOfThePlayer() {
//        System.out.println("Is game ended " + this.endGame);
//        System.out.println("Is round ended " + this.endRound);
//        System.out.println(this.player1Score + "/" + this.player2Score);
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
     * Returns true if the cell at the given indices is on the edge
     *
     * @param i
     * @param j
     * @return
     */
    private boolean isEdge(int i, int j) {
        return (i == currentLayerOfBattleRoyale || i == MAP_SIZE - 1 - currentLayerOfBattleRoyale || j == currentLayerOfBattleRoyale || j == MAP_SIZE - 1 - currentLayerOfBattleRoyale);
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
     * Turns the cell at the given indices into a wall
     *
     * @param i
     * @param j
     */
    private void turnIntoWalls(int i, int j) {
        this.space[i][j] = new Wall(new Position(j, i));
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
     * Handles battle royale when the time comes
     */
    public void handleBattleRoyale() {
        if (Duration.between(this.startingTime, LocalTime.now()).getSeconds() >= currentBattleRoyaleDuration) {
            System.out.println("Shrink happens");
            shrinkMap();
            this.currentLayerOfBattleRoyale++;
            if (this.currentBattleRoyaleDuration > 20) {
                this.currentBattleRoyaleDuration *= 0.75;
                this.currentBattleRoyaleTime = this.currentBattleRoyaleDuration;
            }
            this.startingTime = LocalTime.now();
        }
    }

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

}
