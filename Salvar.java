package com.mycompany.projetopoo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Salvar implements Serializable{
    Game g;
    Menu m;
    HUD h;
    
    private int alt = g.ALTURA;
    private int lar = g.LARGURA;
    
    private int maxPo = Menu.maximo;
    private int maxIn = Menu.maximoInimigos;
    
    static private int r = HUD.record;
    
    public Salvar(/*int alt, int lar, int maxPo, int maxIn, int r*/){
        this.alt = alt;
        this.lar = lar;
        this.maxPo = maxPo;
        this.maxIn = maxIn;
        this.r = r;
    }
    
    public void salvar(String nome_arquivo) throws IOException {
        FileOutputStream arquivo = new FileOutputStream(nome_arquivo);
            ObjectOutputStream gravador = new ObjectOutputStream(arquivo);
            gravador.writeObject(this);
            gravador.close();
        arquivo.close();
    }
    public static Salvar abrir(String nome_arquivo) throws IOException, ClassNotFoundException {
        Salvar s;
        FileInputStream arquivo = new FileInputStream(nome_arquivo);
            ObjectInputStream restaurador = new ObjectInputStream(arquivo);
            s = (Salvar) restaurador.readObject();
            restaurador.close();
        arquivo.close();
        return s;
    }
    /*
    public void exibir(){
        System.out.println("Altura: " + alt);
        System.out.println("Largura: " + lar);
        System.out.println("Pontução máxima: " + maxPo);
        System.out.println("Número de inimigos: " + maxIn);
        System.out.println("Record: " + r);
    }*/
}
