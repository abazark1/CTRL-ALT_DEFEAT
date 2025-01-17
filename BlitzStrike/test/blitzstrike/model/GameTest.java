/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package blitzstrike.model;

import blitzstrike.model.effects.powerups.Obstacle;
import blitzstrike.model.monsters.Monster;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private String mapPath;
    private int numberOfGames;

    @Before
    public void setUp() {
        this.player1 = new Player("Kitana");
        this.player2 = new Player("Mileena");
        this.mapPath = "src/blitzstrike/res/map1.txt";
        this.numberOfGames = 3;
        this.game = new Game(1, "", player1, player2, numberOfGames);
    }

    @Test
    public void testConstructor_successfulInitialization() {
        assertEquals(this.game.getPlayer11(), this.player1);
        assertEquals(this.game.getPlayer22(), this.player2);
        assertEquals(this.numberOfGames, this.game.getRoundsToWin());
        assertFalse(this.game.isGameOrRoundEnded());
    }

    @Test
    public void testLoadNextRound() {
        this.game.loadNextRound();
        assertEquals(this.game.getPlayer11(), this.player1);
        assertEquals(this.game.getPlayer22(), this.player2);
        assertFalse(this.game.isGameOrRoundEnded());
    }

    @Test
    public void testRestartgame() {
        this.game.restartgame();
        assertEquals(this.game.getPlayer11(), this.player1);
        assertEquals(this.game.getPlayer22(), this.player2);
        assertEquals(0, this.game.getPlayer1Score());
        assertEquals(0, this.game.getPlayer2Score());
        assertFalse(this.game.isGameOrRoundEnded());
    }
    
    @Test
    public void testContinueGame() {
        this.mapPath = "src/blitzstrike/files/Kitana_Mileena_1_0_0_3.txt";
        try {
            game.continueGame(this.mapPath);
            assertEquals(game.getPlayer11().getName(), "Kitana");
            assertEquals(game.getPlayer22().getName(), "Mileena");
            assertEquals(game.getPlayer1Score(), 0);
            assertEquals(game.getPlayer2Score(), 0);
            assertEquals(game.getRoundsToWin(), 3);
        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }

    @Test
    public void testPlayerDiesFromCollisionWithAMonster() {
        this.game.loadMap();
        for (int i = 0; i < 8; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        this.game.movePlayer1(Direction.RIGHT);
        assertFalse(this.game.getPlayer11().isAlive());
    }

    @Test
    public void testPlayerDiesFromBombExplosion() {
        this.game.loadMap();
        for (int i = 0; i < 7; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        for (int i = 0; i < 10; i++) {
            this.game.movePlayer1(Direction.RIGHT);
        }
        for (int i = 0; i < 4; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            this.game.movePlayer1(Direction.RIGHT);
        }
        this.game.getPlayer11().placeBomb();
        List<Bomb> bombs = this.game.getPlayer11().getBombs();
        bombs.get(0).handleExplosion(player1, player2, this.game.getMonsters());
        assertFalse(this.game.getPlayer11().isAlive());
        assertFalse(this.game.getPlayer22().isAlive());
    }

    @Test
    public void testPlayersBombRangeIsDecreasedUnderBombRangeCurse() {
        this.game.loadMap();
        this.game.getPlayer22().setBlastRange(1);
        for (int i = 0; i < 7; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        for (int i = 0; i < 10; i++) {
            this.game.movePlayer1(Direction.RIGHT);
        }
        for (int i = 0; i < 4; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            this.game.movePlayer1(Direction.RIGHT);
        }
        this.game.getPlayer22().placeBomb();
        List<Bomb> bombs = this.game.getPlayer22().getBombs();
        bombs.get(0).handleExplosion(player1, player2, this.game.getMonsters());
        assertTrue(this.game.getPlayer11().isAlive());
        assertFalse(this.game.getPlayer22().isAlive());
    }

    @Test
    public void testPlayersCannotPlaceBombsUnderNoBombCurse() {
        this.game.loadMap();
        this.game.getPlayer22().setNoBombCurse(true);
        this.game.getPlayer11().setNoBombCurse(true);
        this.game.getPlayer22().placeBomb();
        this.game.getPlayer11().placeBomb();

        List<Bomb> bombs22 = this.game.getPlayer22().getBombs();
        List<Bomb> bombs11 = this.game.getPlayer11().getBombs();
        assertEquals(0, bombs22.size());
        assertEquals(0, bombs11.size());
    }

    @Test
    public void testPlayersCanPlaceMoreThanOneBombUnderBombIncreasePowerUp() {
        this.game.loadMap();
        this.game.getPlayer22().setBombNumber(2);
        this.game.getPlayer11().setBombNumber(3);
        this.game.getPlayer22().placeBomb();
        this.game.getPlayer11().placeBomb();
        this.game.getPlayer22().placeBomb();
        this.game.getPlayer11().placeBomb();
        this.game.getPlayer22().placeBomb();
        this.game.getPlayer11().placeBomb();
        List<Bomb> bombs22 = this.game.getPlayer22().getBombs();
        List<Bomb> bombs11 = this.game.getPlayer11().getBombs();
        assertEquals(2, bombs22.size());
        assertEquals(3, bombs11.size());
    }

    @Test
    public void testPlayerCanGoThroughWallsAndBoxesUndexGhostPowerUp() {
        this.game.loadMap();
        this.game.getPlayer11().setGhost(true);
        for (int i = 0; i < 11; i++) {
            this.game.getPlayer11().movePlayer(Direction.UP, this.game.getPlayer22());
        }
        assertEquals(1, this.game.getPlayer11().getPosition().getX());
        assertEquals(2, this.game.getPlayer11().getPosition().getY());
    }

    @Test
    public void testPlayerDoesntDieUnderInvinciblePowerUp() {
        this.game.loadMap();
        this.game.getPlayer11().setInvincible(true);
        this.game.getPlayer22().setInvincible(true);

        for (int i = 0; i < 8; i++) {
            this.game.movePlayer1(Direction.UP);
        }
        this.game.movePlayer1(Direction.RIGHT);

        this.game.getPlayer22().placeBomb();
        List<Bomb> bombs = this.game.getPlayer22().getBombs();
        bombs.get(0).handleExplosion(this.game.getPlayer11(), this.game.getPlayer22(), this.game.getMonsters());

        assertTrue(this.game.getPlayer11().isAlive());
        assertTrue(this.game.getPlayer22().isAlive());
    }
    
    @Test
    public void testFollowedByMonsters(){
        this.game.loadMap();
        this.game.getPlayer11().setFollowedByMonsters(true);
        for(Monster m : this.game.getMonsters()){
            m.targetPlayer();
            m.updateTarget();
            Player pl = m.getTargetPlayer();
            assertEquals(pl, this.game.getPlayer11());
        }
    }
    
    @Test
    public void testIgnoredByMonsters(){
        this.game.loadMap();
        this.game.getPlayer11().setRollerSkate(true);
        for(Monster m : this.game.getMonsters()){
            m.ignorePlayer();
            m.updateIgnoredPlayer();
            Player pl = m.getIgnoredPlayer();
            assertEquals(pl, this.game.getPlayer11());
        }
    }
    
    @Test
    public void testPlayerCanPlaceObstaclesUntilMax(){
        this.game.loadMap();
        Obstacle obstacle = new Obstacle(false);
        obstacle.applyEffect(game.getPlayer11());
        this.game.getPlayer11().placeObstacle();
        this.game.getPlayer11().placeObstacle();
        List<ObstacleBox> obst11 = this.game.getPlayer11().getObstacles();
        assertEquals(2, obst11.size());
        
        this.game.getPlayer11().placeObstacle();
        this.game.getPlayer11().placeObstacle();
        assertTrue(this.game.getPlayer11().getObstacles().size() == this.game.getPlayer11().getMaxNumOfObstacles());
    }
}
