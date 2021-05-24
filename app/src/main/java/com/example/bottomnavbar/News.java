package com.example.bottomnavbar;

import java.io.Serializable;

public class News implements Serializable {

    private int imgFoto;
    private String titulo;
    private String contenido;
    private String fechas;

    public News(int imgFoto, String titulo, String contenido, String fechas) {
        this.imgFoto = imgFoto;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechas = fechas;
    }

    public int getImgFoto() {
        return imgFoto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public String getFechas() {
        return fechas;
    }
}
