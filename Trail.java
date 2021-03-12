package com.mycompany.projetopoo;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Trail extends GameObject{

    private float alpha = 1;
    private Handler handler;
    private Color color;
    private int largura, altura;
    float trilha;
    
    // life é um valor entre 0.001 e 0.1 - quanto menor o valor maior 
    
    public Trail(int x, int y, ID id, Color color, int largura, int altura, float trilha, Handler handler){
        super(x, y, id);
        this.handler = handler;
        this.color = color;
        this.largura = largura;
        this.altura = altura;
        this.trilha = trilha;
    }
    public void tick() {
        if(alpha > trilha){
            alpha -= trilha - 0.0001f;
        }else handler.removeObject(this);
    }
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setComposite(makeTransparent(alpha));
        
        g.setColor(color);
        g.fillRect(x, y, largura, altura);
        
        g2d.setComposite(makeTransparent(1));
    }
    private AlphaComposite makeTransparent(float alpha){
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }
    public Rectangle getBounds() {
        return null;
    }
    
}