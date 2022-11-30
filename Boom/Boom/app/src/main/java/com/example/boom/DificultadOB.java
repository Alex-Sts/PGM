package com.example.boom;

public class DificultadOB {
    private int id;
    private String dificultad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DificultadOB(int id, String dificultad) {
        this.id = id;
        this.dificultad = dificultad;
    }

    public DificultadOB() {
    }

    @Override
    public String toString() {
        return dificultad;
    }
}
