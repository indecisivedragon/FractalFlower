package com.project.lili.fractalflower.gameMode;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Oscar on 23/08/2016.
 */
public class Player {

    private boolean AI = false;

    //player not initialized
    private int playerID = -1;

    //color
    private Paint paint = new Paint();

    public Player() {
        paint.setColor(Color.BLACK);
    }

    public Player(boolean b, int id, int color) {
        AI = b;
        playerID = id;
        paint.setColor(color);
    }

    public boolean getAI() {
        return AI;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getColor() {
        return paint.getColor();
    }

    public boolean winCondition(int size, int[][] board) {
        //check columns and rows
        for (int i=0; i<size; i++) {
            boolean winColumns = true;
            boolean winRows = true;
            for(int j=0; j < size; j++) {
                if (board[i][j] != playerID) {
                    winRows = false;
                }
                if (board[j][i] != playerID) {
                    winColumns = false;
                }
            }
            if (winColumns || winRows) {
                return true;
            }
        }
        //check diagonals
        boolean winDiagonalLeft = true;
        for (int i=0; i<size; i++) {
            if (board[i][i] != playerID) {
                winDiagonalLeft = false;
            }
        }
        boolean winDiagonalRight = true;
        for (int i=0; i<size; i++) {
            if (board[i][size-i-1] != playerID) {
                winDiagonalRight = false;
            }
        }
        if (winDiagonalLeft || winDiagonalRight) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (AI) {
            return "AI player " + playerID;
        }
        else return "human player " + playerID;
    }

    public int getNextMove(int[][] board, int size) {
        //just return next valid position
        for (int i=0; i<size*size; i++) {
            if (board[i/size][i%size] == 0) {
                return i;
            }
        }
        return -1;
    }
}
