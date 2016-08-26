package com.project.lili.fractalflower.gameMode;

import android.graphics.Color;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Oscar on 23/08/2016.
 */
public class ConnectFour implements Game{

    private String GAME_DEBUG = "Connect Four";

    private ArrayList<Player> players = new ArrayList<>();
    private int currentPlayer = -1;
    private int numPlayers = 2;

    //board stores the player ID, not the player array number
    private int boardSize = 4;
    private int[][] board;

    private boolean endGame = false;

    //informative update for the player
    private String gameMessage = "default";

    //default constructor makes two players
    //one human player and second AI player
    public ConnectFour() {
        this(4);
    }

    //constructor with variable size
    public ConnectFour(int size) {
        boardSize = size;

        players.add(new Player(false, 1, Color.BLUE));
        players.add(new Player(true, 2, Color.RED));

        board = new int[boardSize][boardSize];
        currentPlayer = 0;
    }

    /*
    @Override
    public void run() {
        //while we still have a valid player, switch between them and make moves
        while (currentPlayer != -1) {
            Player current = players.get(currentPlayer);
            updateBoard(current.getNextMove(board, boardSize), current.getPlayerID());

            //someone wins
            if (endGame(current)) {
                System.out.println(current.toString() + " wins");
                currentPlayer = -1;
            }
            //no more moves possible
            else if (boardIsFull()) {
                System.out.println("no one wins :(");
                currentPlayer = -1;
            }
            //continue
            else {
                currentPlayer = (currentPlayer+1)%numPlayers;
            }
        }
    }
    */

    //fill the board with the next move from AI
    public void makeAIMove() {
        int nextMove = -1;
        //if this is an AI move, automatically get next
        if (players.get(currentPlayer).getAI()) {
            nextMove = players.get(currentPlayer).getNextMove(board, boardSize);
        }
        //if this is a valid move, mark it down
        if (nextMove != -1) {
            makeMove(nextMove);
        }
    }

    //fill the board with the next move
    //check for win conditions
    public void makeMove(int circle) {
        if (!endGame) {
            board[circle / boardSize][circle % boardSize] = players.get(currentPlayer).getPlayerID();
            Log.d(GAME_DEBUG, "move at " + circle / boardSize + ", " + circle % boardSize + " for " + players.get(currentPlayer).getPlayerID());

            //if the game has ended with a win, display
            if (endGame(players.get(currentPlayer))) {
                gameMessage = players.get(currentPlayer) + " has won! Game is over.";
                System.out.println(gameMessage);
                endGame = true;
            }
            //if the board is full and there are no more moves, we have lost
            else if (boardIsFull()) {
                gameMessage = "no one won :( and game is over";
                System.out.println(gameMessage);
                endGame = true;
            }
            //otherwise proceed
            else {
                currentPlayer = (currentPlayer + 1) % numPlayers;
                gameMessage = "current player is " + currentPlayer;
            }
        }
        else {
            System.out.println("game is over!");
        }
    }

    private void updateBoard(int position, int ID) {
        if (position != -1) {
            int x = position / boardSize;
            int y = position % boardSize;
            board[x][y] = ID;
            //System.out.println(this.getId() + " player " + ID + " moves " + x + ", " + y);
        }
        else {
            //System.out.println(this.getId() + " player " + ID + " illegal move");
        }
    }

    public boolean endGame(Player currentPlayer) {
        if (currentPlayer.winCondition(boardSize, board)) {
            return true;
        }
        else return false;
    }

    public boolean boardIsFull() {
        boolean full = true;
        for (int i=0; i<boardSize; i++) {
            for (int j=0; j<boardSize; j++) {
                if (board[i][j] == 0) {
                    full = false;
                }
            }
        }
        return full;
    }

    public int[][] getBoard() {
        return board;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getColor(int i, int j) {
        int id = board[i][j];
        for (int k=0; k<players.size(); k++) {
            if (id == players.get(k).getPlayerID()) {
                return players.get(k).getColor();
            }
        }
        return -1;
    }

    public int getColor(int circle) {
        return this.getColor(circle/boardSize, circle%boardSize);
    }

    public String getGameMessage() {
        return gameMessage;
    }
}
