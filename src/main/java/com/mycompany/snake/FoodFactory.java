/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author alu10191634
 */
public class FoodFactory {
    
    public Food getFood(Snake snake){
        double random = Math.random();
        if (random < 0.2){
            return new SpecialFood(snake);
        } else{
            return new Food(snake);
        }
    }
}
