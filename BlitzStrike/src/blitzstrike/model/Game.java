/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blitzstrike.model;

import java.io.BufferedReader;
import java.io.FileReader;
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
    private Cell[][] space = new Cell[MAP_SIZE][MAP_SIZE];
    
    private ArrayList<Monster> monsters = new ArrayList<>();

    public Game(Player player1, Player player2, int roundsToWin) {
        this.roundsToWin = roundsToWin;
        this.player1 = player1;
        this.player2 = player2;
    }
    public Game(){}
    
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
    
    public void loadMap(String filepath){
        
        
    }
    public void loadNextGame(){}
    
    public void endGame()
    {
        if(roundsToWin==player1.getGamesWon() || roundsToWin == player2.getGamesWon()){
            endGame= true;
        }
        
    }
    public void restartgame()
    {
        
        loadMap(filepath);
        player1.reset();
        player2.reset();
        player1Score = 0;
        player2Score = 0;
        startingTime = LocalTime.now();
        endGame = false;
    }
    public void pauseGame()
    {
        
    }
    public void continueGame(){}
    public void movePlayer1(Direction d)
    {
        player1.movePlayer(d);
    }
    public void movePlayer2(Direction d)
    {
        player2.movePlayer(d);
    }
    public void moveMonsters(Monster m){}
    public boolean isPlayerDead(){ return false;}
    public Player getWinner(){
        //промежуточный winner?
        
        return this.winner;
    }
//    public boolean gameSuccessfullEnd(){return false;}
//    public Player checkScore(){return ;}
    public boolean doCollapse(){return false;}
    public void handleCollapse(){}
    public void handleBombExplosion(){}
    public void saveGame(Player player1name, Player player2name, int player1score, int player2score, int roundsToWin, Timer timer){}
    
    public String FileReader(String filePath)
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line=br.readLine()) != null){
                sb.append(line).append('\n');
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }
        String result = sb.toString();
        return result;
        
    }
}

