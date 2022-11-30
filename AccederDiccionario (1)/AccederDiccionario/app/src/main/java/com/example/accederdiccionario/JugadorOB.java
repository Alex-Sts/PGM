package com.example.accederdiccionario;

public class JugadorOB {

    private int id;
    private int vidas;
    private String nombres;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public JugadorOB(int id, int vidas, String nombres) {
        this.id = id;
        this.vidas = vidas;
        this.nombres = nombres;
    }

    public JugadorOB() {
    }
}
