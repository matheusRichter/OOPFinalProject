package com.mycompany.projetopoo;

import java.awt.Graphics;
import java.awt.Color;
import java.io.*;

public class HUD implements Serializable{
    
    transient public static int HEALTH = 100;
    
    transient private int greenValue = 255;
    
    transient static int score = 0;
    static int record = score;
    transient private int level = 1;
    
    public void tick(){     
        HEALTH = Game.clamp(HEALTH, 0, 100);
        greenValue = Game.clamp(greenValue, 0, 255);
        greenValue = HEALTH*2;
        score++;
    }
    
    public void render(Graphics g){
        g.setColor(Color.gray);
        g.fillRect(15,15,200,32);
        g.setColor(new Color(75, greenValue, 0));
        g.fillRect(15,15,HEALTH * 2,32);
        g.setColor(Color.white);
        g.drawRect(15,15,200,32);
        
        g.drawString("Score: " + score, 15, 64);
        g.drawString("Level: " + level, 15, 80);
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setLevel(int level){
        this.level = level; 
    }
    
    public int getLevel(){
        return level;
    }
    
    public void setRecord(int record){
        this.record = record;
    }
    
    public int getRecord(){
        return record;
    }
    
    
    
    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
            ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
            gravador.writeObject(this);
            gravador.close();
        arquivo.close();
    }
    public static HUD abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        HUD s;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
            ObjectInputStream restaurador = new ObjectInputStream(arquivo);
            s = (HUD) restaurador.readObject();
            restaurador.close();
        arquivo.close();
        return s;
    }
}