package co.edu.puj.rpg.modelo;

public class Personaje {

    private String nombre;
    private int puntosVida;
    private int vidaMaxima;
    private int puntosAtaque;
    private int nivel;
    private double oro;
    private boolean estaVivo;

}

public Personaje (String nombre, int vidaMaxima, int puntosAtaque, double oro){
    this.getNombre = nombre;
    this.getvidaMaxima = vidaMaxima;
    this.getpuntosAtaque = puntosAtaque;
    this.getoro = oro;
}