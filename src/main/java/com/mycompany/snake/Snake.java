/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

import static com.mycompany.snake.Board.NUM_COLS;
import static com.mycompany.snake.Board.NUM_ROWS;
import static com.mycompany.snake.Direction.DOWN;
import static com.mycompany.snake.Direction.LEFT;
import static com.mycompany.snake.Direction.RIGTH;
import static com.mycompany.snake.Direction.UP;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alu10191634
 */
public class Snake {
    private Direction direction;
    private List<Node> listOfNodes;
    private int initNodes;
    private int toGrow;

    public Snake(Direction direction, int initNodes) {
        this.direction = direction;
        this.initNodes = initNodes;
        initialSnake(initNodes);

    }

    private void initialSnake(int initNodes) {
        int rows = Board.NUM_ROWS;
        int cols = Board.NUM_COLS;
        int initPos = cols / 4;
        listOfNodes = new ArrayList<>();
       
        for (int i = 0; i < initNodes; i++) {
            Node node = new Node(rows / 2, initPos);
            listOfNodes.add(node);
            initPos--;
        }
    }
    

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void printSnake(Graphics g, int squareWidth, int squareHeight) {
        boolean isHead = true;
        Node nodeToPrint;
        for (int i = 0; i < listOfNodes.size(); i++) {
            nodeToPrint = listOfNodes.get(i);
            if (isHead) {
                Util.drawSquare(g, nodeToPrint.getRow(), nodeToPrint.getCol(), squareWidth, squareHeight, SquareType.HEAD);
                isHead = false;
            } else {
                Util.drawSquare(g, nodeToPrint.getRow(), nodeToPrint.getCol(), squareWidth, squareHeight, SquareType.BODY);
            }
        }
    }

    public boolean containNode(int row, int col) {
        for (int i = 0; i < listOfNodes.size(); i++) {
            Node nodeInSnake = listOfNodes.get(i);
            if (row == nodeInSnake.getRow() && col == nodeInSnake.getCol()) {
                return true;
            }
        }
        return false;
    }

    public void move() {
        Node node;
        int row = listOfNodes.get(0).getRow();
        int col = listOfNodes.get(0).getCol();
        switch (direction) {
            case UP:
                node = new Node(row - 1, col);
                listOfNodes.add(0, node);
                break;
            case DOWN:
                node = new Node(row + 1, col);
                listOfNodes.add(0, node);
                break;
            case LEFT:
                node = new Node(row, col - 1);
                listOfNodes.add(0, node);
                break;
            case RIGTH:
                node = new Node(row, col + 1);
                listOfNodes.add(0, node);
                break;
            default:
                throw new AssertionError();
        }
        if(toGrow <= 0) {
            listOfNodes.remove(listOfNodes.size() - 1);
        } else {
            toGrow--;
        }
    }

    public boolean canMove() {
        int row = listOfNodes.get(0).getRow();
        int col = listOfNodes.get(0).getCol();
        switch (direction) {
            case UP:
                if (row - 1 < 0 || containNode(row - 1, col)) {
                    return false;
                }
                break;
            case DOWN:
                if (row + 1 >= NUM_ROWS || containNode(row + 1, col)) {
                    return false;
                }
                break;
            case LEFT:
                if (col - 1 < 0 || containNode(row, col - 1)) {
                    return false;
                }
                break;
            case RIGTH:
                if (col + 1 >= NUM_COLS || containNode(row, col + 1)) {
                    return false;
                }
                break;
            default:
                throw new AssertionError();
        }
        return true;
    }

    /*public boolean eatFood (Food food){
        int foodRow = food.getRow();
        int foodCol = food.getCol();
        int headRow = listOfNodes.get(0).getRow();
        int headCol = listOfNodes.get(0).getCol();       
        switch (direction) {
            case UP:
                if (headRow - 1 == foodRow && headCol == foodCol) {
                    return false;
                }
                break;
             case DOWN:               
                if (headRow + 1 == foodRow && headCol == foodCol) {
                    return false;
                }
                break;
            case LEFT:                
                if (headCol - 1 == foodCol && headRow == foodRow) {
                    return false;
                }
                break;
            case RIGTH:
               if (headCol + 1 == foodCol && headRow == foodCol) {
                    return false;
                }
                break;
            default:
                throw new AssertionError();
        }
        return true;
        
    }*/
    

    public boolean eatFood(Food food) {
        int score = 0;
        if (food == null){
            return false;
        }
        int foodRow = food.getRow();
        int foodCol = food.getCol();
        int headRow = listOfNodes.get(0).getRow();
        int headCol = listOfNodes.get(0).getCol();
        if (foodRow == headRow && foodCol == headCol) {
            toGrow += food.nodesWhenEat();
            return true;
        } else {
            return false;
        }
    }
    


}
