package com.mycompany.projetopoo;

import java.util.Random;

public class Spawn {
    private Handler handler;
    private HUD hud;
    private int scoreKeep = 0;
    private Random r = new Random();
    
    public Spawn(Handler hadler, HUD hud){
        this.handler = handler;
        this.hud = hud;
    }
    
    public void tick(){
        //hud = new HUD();
        handler = new Handler();
        
        scoreKeep++; // faz com que o score aumente
        
        if (scoreKeep >= 100){
            scoreKeep = 0;
            hud.setLevel(hud.getLevel() + 1); // faz com que o level aumente
            
            // adicina inimigos ao jogo
            if (hud.getLevel() == 2){
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
            }else if (hud.getLevel() == 3){
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
            }else if (hud.getLevel() == 4){
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
            }else if (hud.getLevel() >= 4){
                handler.addObject(new Enemy(r.nextInt(Game.LARGURA), r.nextInt(Game.ALTURA), ID.Enemy, handler));
            }
        }
    }
}