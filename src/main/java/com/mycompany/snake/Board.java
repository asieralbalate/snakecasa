/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.snake;

import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import javax.swing.Timer;

/**
 *
 * @author alu10191634
 */
public class Board extends javax.swing.JPanel implements InitGamer{

    public static final int NUM_ROWS = 20;
    public static final int NUM_COLS = 20;
    private Timer timer;
    private Snake snake;
    private Food food;
    private MyKeyAdapter keyAdapter;
    private int deltaTime;
    private List<Direction> movements;
    private FoodFactory foodFactory;
    private Incrementer incrementer;
    private int highScore;

    /**
     * Creates new form Board
     */
    public Board() {
        initComponents();
        myInit();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(102, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void myInit() {
        foodFactory = new FoodFactory();
        keyAdapter = new MyKeyAdapter();
        setFocusable(true);
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                tick();
            }
        });
        highScore = 0;
    }

    public void initGame() {
        snake = new Snake(Direction.RIGTH, 4);
        movements = new Vector<>(2);
        food = generateFood();
        addKeyListener(keyAdapter);
        setDeltaTime();
        incrementer.resetScore();
        timer.start();
        repaint();
    }

    private void tick() {
        if (movements.size() != 0) {
            Direction dir = movements.get(0);
            snake.setDirection(dir);
            movements.remove(0);
        }
        if (snake.canMove()) {
            snake.move();
            if (snake.eatFood(food)) {
                incrementer.incrementScore(food.getPoints());
                food = generateFood();
            }
        } else {
            processGameOver();
        }

        repaint();
        Toolkit.getDefaultToolkit().sync();
    }

    public void setDeltaTime() {
        switch (ConfigData.instance.getlevel()) {
            case 0:
                deltaTime = 500;
                break;
            case 1:
                deltaTime = 300;
                break;
            case 2:
                deltaTime = 150;
                break;
            default:
                throw new AssertionError();
        } 
        timer.setDelay(deltaTime);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (snake != null) {
            snake.printSnake(g, squareWidth(), squareHeight());

        }
        if (food != null) {
            food.printFood(g, squareWidth(), squareHeight());
        }
    }

    private Food generateFood() {
        return foodFactory.getFood(snake);
    }

    public int squareWidth() {
        return getWidth() / Board.NUM_COLS;
    }

    public int squareHeight() {
        return getHeight() / Board.NUM_ROWS;
    }
    
    public void setIncrementer(Incrementer incrementer) {
        this.incrementer = incrementer;
    }
        
    class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (snake.getDirection() != Direction.RIGTH) {
                        snake.setDirection(Direction.LEFT);
                        movements.add(Direction.LEFT);
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.setDirection(Direction.RIGTH);
                        movements.add(Direction.RIGTH);
                        break;
                    }

                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.setDirection(Direction.UP);
                        movements.add(Direction.UP);
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.setDirection(Direction.DOWN);
                        movements.add(Direction.DOWN);
                        break;
                    }
                default:
                    break;
            }
            repaint();
        }
    }

   public void processGameOver() {
       timer.stop();
       JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
       GameOverDialog gOverDialog = new GameOverDialog(topFrame, true);
       gOverDialog.setInitGamer(this);
       gOverDialog.setScore(incrementer.getScore());
       if(incrementer.getScore() > highScore){
           highScore = incrementer.getScore();
       }
       gOverDialog.setName();
       gOverDialog.setHighScore(highScore);
       gOverDialog.setVisible(true);
   }
    
    private void paintGameOver(Graphics g) {
        Util.drawSquare(g, 15, 15, squareWidth(), squareHeight(), SquareType.HEAD);
    }
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

