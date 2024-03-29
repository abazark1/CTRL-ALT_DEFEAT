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
import java.util.Timer;

/**
 *
 * @author aselh
 */
public class Game {

    public static final int MAP_SIZE = 15;
    public boolean endGame = false;

    private int roundsToWin;
    private LocalTime startingTime;
    private int gameDuration;
    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;
    private String filepath;

    private Player winner;
    private Cell[][] space;
    private boolean isPaused;

    private ArrayList<Monster> monsters;

    public Game(String filepath, Player player1, Player player2, int roundsToWin) {
        this.filepath = filepath;
        this.roundsToWin = roundsToWin;
        this.player1 = player1;
        this.player2 = player2;
    }

    public Game() {
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

    public Player getPlayer(int x, int y) {
        if (player1.getPosition().getX() == x && player1.getPosition().getY() == y) {
            return player1;
        } else if (player2.getPosition().getX() == x && player2.getPosition().getY() == y) {
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

    // basically, to refresh everything based on the file.
    // TODO?: SHOULD THIS METHOD ALSO RESET ALL THE FIELDS OF PLAYER?
    public void loadMap() {
        this.monsters = new ArrayList<>();
        this.space = new Cell[MAP_SIZE][MAP_SIZE];

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
                        break;
                    case '#':
                        cell = new Box(position);
                        break;
                    case 'a':
                        cell = new Empty(position);
                        this.player1.setPosition(position);
                        break;
                    case 'b':
                        cell = new Empty(position);
                        this.player2.setPosition(position);
                        break;
                    case '1':
                        cell = new Empty(position);
                        Monster monster = new BasicMonster(1.0, position, this.space);
                        this.monsters.add(monster);
                        break;
                    default:
                        cell = new Empty(position);
                        break;
                }
                space[row][column] = cell;
            }
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

    public void pauseGame() {
        isPaused = true;
        //stop monsters from moving
        
        //showPauseDialog();
              

    }

    public void continueGame() {
        isPaused = false;
    }

    public void movePlayer1(Direction d) {
        player1.movePlayer(d);
    }

    public void movePlayer2(Direction d) {
        player2.movePlayer(d);
    }

    public void moveMonsters(Monster m) {
        
    }

    public boolean isPlayerDead() {
        return false;
    }

    public Player getWinner() {
        //final winner
        return this.winner;
    }

    public boolean gameSuccessfullEnd() {
        return this.player1Score >= this.roundsToWin || this.player2Score >= this.roundsToWin;
    }

    // Промежуточный победитель
    public Player getCurrentRoundWinnerIfAny() throws Exception {
        if (this.player1.isAlive() && this.player2.isAlive()) {
            throw new Exception("Both can't be alive");
        } else if (this.player1.isAlive()) {
            return this.player1;
        } else if (this.player2.isAlive()) {
            return this.player2;
        } else {
            // Both died, nobody wins
            return null;
        }
    }

    public boolean doCollapse() {
        return false;
    }

    public void handleCollapse() {
        
    }

    public void handleBombExplosion(Position bombPosition, int blastRadius) {
        for (int dx = -blastRadius; dx <= blastRadius; dx++) {
        for (int dy = -blastRadius; dy <= blastRadius; dy++) {
            Position affectedPos = new Position(bombPosition.getX() + dx, bombPosition.getY() + dy);

            if (isOutOfBounds(affectedPos)) {
                continue;
            }

            Cell cell = space[affectedPos.getY()][affectedPos.getX()];
            if (cell instanceof Wall) {
                continue; //block if its wall
            }
            if (cell instanceof Box) {
                // Destroy the box and potentially reveal a power-up
                ((Box) cell).getDestroyed();
                continue;
            }
            // Check for players or monsters at the affected position
            checkAndAffectEntitiesAt(affectedPos);
        }
    }
    }

    private boolean isOutOfBounds(Position position) {
    // Returns true if the position is outside the game field.
    return position.getX() < 0 || position.getX() >= MAP_SIZE || position.getY() < 0 || position.getY() >= MAP_SIZE;
}

    public void saveGame(Player player1name, Player player2name, int player1score, int player2score, int roundsToWin, Timer timer) 
    {
          StringBuilder mapBuilder = new StringBuilder();

          // Save map state
          for (int y = 0; y < MAP_SIZE; y++) 
          {
              for (int x = 0; x < MAP_SIZE; x++) 
              {
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
}
