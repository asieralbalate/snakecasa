/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package com.mycompany.snake;

/**
 *
 * @author alu10191634
 */
public class ConfigData {
    
    private int level;
    private String name;
    public static ConfigData instance = new ConfigData();
    
    private ConfigData() {
        level= 0;
        name = "NoName";
    }
    
    public void setLevel(int level){
        if(level < 0) {
            this.level = 0;
        } else if (level > 2) {
            this.level = 2;
        } else {
            this.level= level;
        }
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public int getlevel(){
        return level;
    }
    
    public String getName(){
        return name;
    }
}
