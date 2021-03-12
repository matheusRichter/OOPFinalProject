package com.mycompany.projetopoo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy extends GameObject{

    private Handler handler;
    
    private int base = 16;
    private int h = 16;
    private Color cor = Color.red;
    HUD hud = new HUD();
    
    public Enemy(int x, int y , ID id, Handler handler){
        super(x, y, id);
        this.handler = handler;
        
        velX = 5;
        velY = 5;
    }
    
    public Rectangle getBounds(){
        return new Rectangle(x, y, base, h);
    }
    
    public void tick() {       
        x += velX;
        y += velY;
        
        if(y <= 0 || y >= Game.ALTURA - 50) velY *= -1;
        if(x <= 0 || x >= Game.LARGURA - 50) velX *= -1;
        
        handler.addObject(new Trail(x, y, ID.Trail, cor, base, h, 0.03f, handler));
        
        colision();
    }
    
    public void colision(){
        for (int i = 0; i < handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            
            if((tempObject.getID() == ID.Enemy && x != tempObject.x && y != tempObject.y)){
                if (getBounds().intersects(tempObject.getBounds())){
                    //colision code
                    boolean verde = true;
                    if(verde == true) {cor = Color.green; verde = false;}
                    else {cor = Color.red; verde = true;}
                }
            }
        }
    }
    
    
    public void render(Graphics g) {
        g.setColor(Color.red);
        g.fillRect(x, y, base, h);
    }
    
}