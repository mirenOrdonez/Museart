package com.example.bottomnavbar;

import java.io.Serializable;

public class News implements Serializable {

    private int imgFoto;
    private String titulo;
    private String contenido;
    private String fechas;
    private String descripcion;

    public News(int imgFoto, String titulo, String contenido, String fechas, String descripcion) {
        this.imgFoto = imgFoto;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechas = fechas;
        this.descripcion = descripcion;
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
    public String getDescripcion() {
        return descripcion;
    }
}
