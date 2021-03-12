package com.mycompany.projetopoo;


import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

    private static final long serialVersionUID = 1L;

    public Window(int largura, int altura, String title, Game game){
        JFrame frame = new JFrame(title);

        frame.setPreferredSize(new Dimension(largura, altura));
        frame.setMinimumSize(new Dimension(largura, altura));
        frame.setSize(Game.LARGURA, Game.ALTURA);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }
    
}