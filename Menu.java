package com.mycompany.projetopoo;

import com.mycompany.projetopoo.Game.STATE;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Random;
import javax.swing.*;

public class Menu extends MouseAdapter implements Serializable{
    
    transient private Game game;
    transient private Handler handler;
    transient private HUD hud;
    transient private Random r = new Random();
    
    transient private static String scoreMaximo = JOptionPane.showInputDialog(null, "Qual será o score máximo?");
    static int maximo = Integer.parseInt(scoreMaximo);
        
    transient private static String inimigosMaximo = JOptionPane.showInputDialog(null, "Qual será o número de inimigos?");;
    static int maximoInimigos = Integer.parseInt(inimigosMaximo);
    
    public Menu(Game game, Handler handler, HUD hud){
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }
    
    public Menu(){
        
    }
    
    public void mousePressed(MouseEvent e){
        int mx = e.getX();
        int my = e.getY();
        
        // faz os botões do menu funcionarem
        if(game.gameState == STATE.Menu){
            // faz botão play funcionar
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.gameState = STATE.Select;
                
                return;
            }
            // faz botão ajuda funcionar
            if (mouseOver(mx, my, 210, 250, 200 , 64)){
                game.gameState = STATE.Ajuda;
            }
            // faz botão sair funcionar
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                hud.setRecord(hud.record);
                hud.getRecord();
                System.exit(1);
            }
        }
        
        // menu select
        if(game.gameState == STATE.Select){
            // faz botão normal funcionar
            if(mouseOver(mx, my, 210, 150, 200, 64)){
                game.dificuldade = 0;
                game.gameState = STATE.Game;
                handler.addObject(new Player(250, 250, ID.Player, handler));
                for(int i = 0; i < maximoInimigos; i++){
                    handler.addObject(new Enemy(r.nextInt(Game.LARGURA - 48), r.nextInt(Game.ALTURA - 72), ID.Enemy, handler));
                }
            }
            // faz botão dificil funcionar
            if (mouseOver(mx, my, 210, 250, 200 , 64)){
                game.dificuldade = 1;
                game.gameState = STATE.Game;
                handler.addObject(new Player(250, 250, ID.Player, handler));
                for(int i = 0; i < maximoInimigos * 2; i++){
                    handler.addObject(new Enemy(r.nextInt(Game.LARGURA - 48), r.nextInt(Game.ALTURA - 72), ID.Enemy, handler));
                }
            }
            // faz botão voltar funcionar
            if(mouseOver(mx, my, 210, 350, 200, 64)){
                game.gameState = STATE.Menu;
            }
        }
        
        
        // faz o botão voltar no 'menu' ajuda funcionar
        if(game.gameState == STATE.Ajuda){
            if(mouseOver(mx, my, 210,350,200,64)){
                game.gameState = STATE.Menu;
            }
        }
        // faz o botão tente de novo no game over funcionar
        if(game.gameState == STATE.Fim){
            if(mouseOver(mx, my, 210,350,200,64)){
                game.gameState = STATE.Menu;
                hud.setLevel(1);
                if(hud.score > hud.record) hud.setRecord(hud.score); // atualiza o record
                hud.setScore(0);
            }
        }
    }
    public void mouseReleased(MouseEvent e){
        
    }
    private boolean mouseOver(int mx, int my, int x, int y, int largura, int altura){
        if(mx > x && mx < x + largura){
            if(my > y && my < y + altura){
                return true;
            }else return false;
        }else return false;
    }
    public void tick(){
    
    }
    public void render(Graphics g){
        Font fnt = new Font("arial", 1, 50);
        Font fnt2 = new Font("arial", 1, 30);
        Font fnt3 = new Font("arial", 1, 20);
        
        if(game.gameState == STATE.Menu){
            g.setFont(fnt);
            // menu
            g.setColor(Color.white);
            g.drawString("MENU", 240, 60);

            g.setFont(fnt2);
            // record
            g.drawString("Record: " + hud.getRecord(), 10, 30);
            
            // botão play
            g.drawRect(210,150,200,64);
            g.drawString("Play", 270, 190);

            //botão ajuda
            g.drawRect(210,250,200,64);
            g.drawString("Ajuda", 270, 290);

            // botão sair
            g.drawRect(210,350,200,64);
            g.drawString("Sair", 270, 390);
        }
        else if(game.gameState == STATE.Ajuda){
            // 'menu' de ajuda
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("AJUDA", 240, 60);
            
            g.setFont(fnt2);
            // botão voltar
            g.drawRect(210,350,200,64);
            g.drawString("Voltar", 270, 390);
            
            g.setFont(fnt3);
            // texto de ajuda
            g.drawString("Use os botões AWSD para mover o jogador", 100, 150);
            g.drawString("Esc para pausar", 250, 200);
            g.drawString("No modo 'DIFICIL' o score máximo é multiplicado por 1.5", 50, 250);
            g.drawString("No modo 'DIFICIL' o número de inimigos é multiplicado por 2", 35, 300);
        }
        else if(game.gameState == STATE.Fim){
            // 'menu' de ajuda
            g.setFont(fnt);
            g.setColor(Color.white);
            g.drawString("Game Over", 200, 60);
            
            g.setFont(fnt3);
            // botão voltar
            g.drawRect(210,350,200,64);
            g.drawString("Tente de novo", 240, 390);
            
            g.setFont(fnt3);
            // Score final
            if (game.dificuldade == 0){ 
                if(HUD.HEALTH >= 1 && hud.score >= maximo){
                    g.drawString("Você venceu com o score de " + hud.getScore(), 175, 200);
                }else{
                    g.drawString("Você perdeu com o score de " + hud.getScore(), 175, 200);
                }
            }
            if(game.dificuldade == 1){
                if(HUD.HEALTH >= 1 && hud.score >= maximo * (1.5)){
                    g.drawString("Você venceu com o score de " + hud.getScore(), 175, 200);
                }else{
                    g.drawString("Você perdeu com o score de " + hud.getScore(), 175, 200);
                }
            }
        }
        else if(game.gameState == STATE.Select){
            g.setFont(fnt);
            // menu
            g.setColor(Color.white);
            g.drawString("Escolha a dificuldade", 60, 60);

            g.setFont(fnt2);
            // botão play
            g.drawRect(210,150,200,64);
            g.drawString("Normal", 270, 190);

            //botão ajuda
            g.drawRect(210,250,200,64);
            g.drawString("Dificil", 270, 290);

            // botão sair
            g.drawRect(210,350,200,64);
            g.drawString("Voltar", 270, 390);
        }
    }
    
    
    
    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
            ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
            gravador.writeObject(this);
            gravador.close();
        arquivo.close();
    }
    public static Menu abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        Menu s;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
            ObjectInputStream restaurador = new ObjectInputStream(arquivo);
            s = (Menu) restaurador.readObject();
            restaurador.close();
        arquivo.close();
        return s;
    }
}