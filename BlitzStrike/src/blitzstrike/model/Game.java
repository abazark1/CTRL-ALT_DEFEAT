
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;

/**
 *
 * @author aselh
 */
public class Game {

    public static final int MAP_SIZE = 15;
    public static final int GAME_DURATION = 180; // seconds. Needed for battle royale BUT NOT NOW!!!
    public boolean endGame = false;
    public boolean endRound = false;

    private int roundsToWin;
    private LocalTime startingTime;

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
    }

    public Game() {
    }

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

    public boolean isBombAtPosition(int x, int y) {
        return this.player1.hasBombAtPosition(x, y) || this.player2.hasBombAtPosition(x, y);
    }

    public Bomb getBomb(int x, int y) {
        if (this.player1.hasBombAtPosition(x, y)) {
            this.player1.getBomb(x, y);
        } else if (this.player2.hasBombAtPosition(x, y)) {
            return this.player2.getBomb(x, y);
        }
        return null;
    }

    // basically, to refresh everything based on the file.
    // TODO?: SHOULD THIS METHOD ALSO RESET ALL THE FIELDS OF PLAYER?
    public void loadMap() {
        this.monsters = new ArrayList<>();
        this.space = new Cell[MAP_SIZE][MAP_SIZE];
        this.startingTime = LocalTime.now();
        this.player1.setSpace(this.space);
        this.player2.setSpace(this.space);

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
                        Monster monster = new BasicMonster(1.0, position, this.space);
                        this.monsters.add(monster);
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

    public void continueGame() {
        try (BufferedReader reader = new BufferedReader(new FileReader("gamestate.txt"))) {
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
                    roundsToWin = Integer.parseInt(line.substring("RoundsToWin:".length()));
                } else {
                    // Assuming this line is part of the map
                    // You'll start processing the map after handling all the metadata
                    break; // Exit the loop to handle map separately
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
                            Monster monster = new BasicMonster(1.0, position, space);
                            monsters.add(monster);
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

    public void loadNextRound() throws Exception {
        Player player = getCurrentRoundWinnerIfAny();

        if (player != null) {
            if (player == player1) {
                this.player1Score++;
            } else if (player == player2) {
                this.player2Score++;
            }
        }

        if (!gameSuccessfullEnd()) {
            loadMap();
        }
    }

    public void endGame() {
        if (roundsToWin == player1.getGamesWon() || roundsToWin == player2.getGamesWon()) {
            endGame = true;
        }

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

//    public void pauseGame() {
//        isPaused = true;
//        //stop monsters from moving
//        
//        //showPauseDialog();
//              
//
//    }
//
//    public void resumeGame() {
//        isPaused = false;
//    }
    public void movePlayer1(Direction d) {
        player1.movePlayer(d, player2, monsters);
        handleCollision();

    }

    public void movePlayer2(Direction d) {
        player2.movePlayer(d, player1, monsters);
        handleCollision();
    }

    public void moveMonsters() {
        for (Monster monster : monsters) {
            monster.move();
        }
        handleCollision();
    }

    public void removeDeadMonsters() {
//        ArrayList<Monster> player1BombsCopy = new ArrayList<>(this.player1.getBombs());
        Iterator<Monster> monsterIterator = this.monsters.iterator();
        while (monsterIterator.hasNext()) {
            Monster monster = monsterIterator.next();
            if (!monster.isAlive()) {
                monsterIterator.remove();
            }
        }
    }

    public boolean isPlayerDead() {
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

    public ArrayList<Monster> getMonsters() {
        return this.monsters;
    }

    public boolean gameSuccessfullEnd() {
        return this.player1Score >= this.roundsToWin || this.player2Score >= this.roundsToWin;
    }

    // Промежуточный победитель
    public Player getCurrentRoundWinnerIfAny() throws Exception {
        if (this.player1.isAlive() && !this.player2.isAlive()) {
            return this.player1;
        } else if (this.player2.isAlive() && !this.player1.isAlive()) {
            return this.player2;
        } else {
            // Both died, nobody wins
            return null;
        }
    }

    public boolean doCollapse() {
        return false;
    }

    public void handleCollision() {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(player1.getPosition())) {
                player1.die();
                endRound = true;
                removePlayerFromMap(player1);
                System.out.println("Player 1 has encountered a monster and the round is over.");
                //return; // Exit the method to avoid further processing
            }

            if (player2 != null && monster.getPosition().equals(player2.getPosition())) {
                player2.die();
                endRound = true;
                removePlayerFromMap(player2);
                System.out.println("Player 2 has encountered a monster and the round is over.");
                //return;
            }
        }

        // we're checking if player have collided with effect yet
        /*Effect effectAtPlayer1Position = (player1 != null) ? getEffect(player1.getPosition()) : null;
        Effect effectAtPlayer2Position = (player2 != null) ? getEffect(player2.getPosition()) : null;
        
        if (player1 != null){
            applyEffect(player1, effectAtPlayer1Position);
        }
        if (player2 != null) {
            applyEffect(player2, effectAtPlayer2Position);
        }*/
    }

    /*
    public Effect getEffect(Position position) {
        Cell cell = space[position.getY()][position.getX()];

        if (cell instanceof Curse) {
            return ((Curse) cell).getEffect();
        } else if (cell instanceof PowerUp) {
            return ((PowerUp) cell).getEffect();
        } else if (cell instanceof EmptyEffect) {
            return ((EmptyEffect) cell).getEffect();
        } else {
            return null;
        }
    }
    
     */
    private void applyEffect(Player player, Effect effect) {
        if (effect != null) {
            effect.applyEffect(player);
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

//    public boolean isExplosionInProgress() {
//        return this.isExplosionInProgress;
//    }
    public void saveGame(Player player1name, Player player2name, int player1score, int player2score, int roundsToWin, Timer timer) {
        StringBuilder mapBuilder = new StringBuilder();

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
                    mapBuilder.append('1');
                } else {
                    mapBuilder.append(' ');
                }
            }
            mapBuilder.append("\n");
        }

        try (FileWriter writer = new FileWriter("gamestate.txt")) {
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

    private boolean isMonsterAtPosition(int x, int y) {
        for (Monster monster : monsters) {
            if (monster.getPosition().equals(new Position(x, y))) {
                return true;
            }
        }
        return false;
    }

    //check if monster has caught up to a player
    /*public void checkMonsterPlayerCollision(Monster monster) {
        if (monster.getPosition().equals(player1.getPosition())) {
            System.out.println("Monster collided with pplayer");
            player1.getExploded();
            removePlayerFromMap(player1);
        } else if (monster.getPosition().equals(player2.getPosition())) {
            System.out.println("Monster collided with player");
            player2.getExploded();
            removePlayerFromMap(player2);
        }
        
    }*/
    // removing player if monster has caught him/her
    public void removePlayerFromMap(Player player) {
        Position playerPosition = player.getPosition();
        space[playerPosition.getY()][playerPosition.getX()] = new Empty(playerPosition);
        player.setPosition(new Position(-10, -10));

    }

}
