package com.mycompany.projetopoo;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class Game extends Canvas implements Runnable{

    private static final long serialVersionUID = 1L;

    /*static final int LARGURA = 640;
    static final int ALTURA = LARGURA / 12 * 9;*/
    
    static private String larguraTerritorio = JOptionPane.showInputDialog(null, "Qual será a largura do território?");;
    static int LARGURA = Integer.parseInt(larguraTerritorio);
    
    static private String alturaTerritorio = JOptionPane.showInputDialog(null, "Qual será a altura do território?");
    static int ALTURA = Integer.parseInt(alturaTerritorio);
    
    private Thread thread;
    private boolean running = false;
    private final HUD hud;
    
    public static boolean paused = false;
    public int dificuldade = 0;             // 0 = normal, 1 = dificil
    
    private final Random r;
    private final Handler handler;
    private Spawn spawner;
    private Menu menu;
    
    private Date time = new Date();
    private SimpleDateFormat tm = new SimpleDateFormat("H");
    private String a = (tm.format(time));
    private int i = Integer.parseInt(a);
    
    public enum STATE{
        Menu,
        Select,
        Ajuda,
        Game,
        Fim
    };
    
    public STATE gameState = STATE.Menu;

    public Game() throws ExcecaoHorario{
        if (this.i >= 0 && this.i <= 6){
            throw new ExcecaoHorario("Não é saudável jogar na madrugada");
        }
        else{
            handler = new Handler();
            hud = new HUD();
            menu = new Menu(this,handler,hud);
            this.addKeyListener(new KeyInput(handler, this));
            this.addMouseListener(menu);

            new Window(LARGURA, ALTURA, "Projeto POO", this);

            r = new Random();

            spawner = new Spawn(handler, hud);
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta > 1){
                tick();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        if (gameState == STATE.Game){
            if(!paused){
                hud.tick();
                spawner.tick();
                handler.tick();
                if((dificuldade == 0 && (HUD.HEALTH <= 0 || hud.score >= menu.maximo)) || (dificuldade == 1 && (HUD.HEALTH <= 0 || hud.score >= menu.maximo * (1.5)))){
                    gameState = STATE.Fim;
                    handler.clearEnemies();
                    HUD.HEALTH = 100;
                }
            }
        }
        else if (gameState == STATE.Menu || gameState == STATE.Fim || gameState == STATE.Select){
            menu.tick();
            handler.tick();
        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(Color.black);
        g.fillRect(0, 0, LARGURA, ALTURA);
        
        handler.render(g);
        
        if(paused){
            g.drawString("PAUSE", 100, 100);
        }
        
        if(gameState == STATE.Game){
            hud.render(g);
        }else if (gameState == STATE.Menu || gameState == STATE.Ajuda || gameState == STATE.Fim || gameState == STATE.Select){
            menu.render(g);
        }

        g.dispose();
        bs.show();
    }

    public static int clamp(int var, int min, int max){  // impede que o player saia do campo de visão
         if(var >= max)
             return var = max;
         else if (var <= min)
             return var = min;
         else 
             return var;
    }
    
    public static void main(String[] args) throws ExcecaoHorario, IOException, ClassNotFoundException{
        Salvar save = new Salvar();
        
        try{
            save.abrir("salvo.bin");
            //save.exibir();
            new Game();
            save.salvar("salvo.bin");
        }
        catch(ClassNotFoundException e){
            new Game();
            save.salvar("salvo.bin");
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ExcecaoHorario e){
            e.printStackTrace();
        }
    }
}