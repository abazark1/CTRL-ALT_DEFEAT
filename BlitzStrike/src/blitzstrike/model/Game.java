/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Timer;



/**
 *
 * @author aselh
 */
public class Game {
    
    public static final int MAP_SIZE = 9;
    
    private int roundsToWin;
    private LocalTime startingTime;
    private int gameDuration;
    private Player player1;
    private Player player2;
    private int player1Score;
    private int player2Score;
    
    Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];
    
    ArrayList<Monster> monsters = new ArrayList<>();
    
    
    public void loadMap(){
    
    }
    public void loadNextGame(){}
    public void endGame(){}
    public void restartgame(){}
    public void pauseGame(){}
    public void continueGame(){}
    public void movePlayer1(Direction d){}
    public void movePlayer2(Direction d){}
    public void moveMonsters(Monster m){}
    public boolean isPlayerDead(){ return false;}
    public Player getWinner(){return;}
    public boolean gameSuccessfullEnd(){return false;}
    public Player checkScore(){return ;}
    public boolean doCollapse(){return false;}
    public void handleCollapse(){}
    public void handleBombExplosion(){}
    public void saveGame(Player player1name, Player player2name, int player1score, int player2score, int roundsToWin, Timer timer){}   
    
}
