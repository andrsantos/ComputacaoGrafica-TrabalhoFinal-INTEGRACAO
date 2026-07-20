package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import lombok.Data;

@Data
public class Ponto3D {
    private double x;
    private double y;
    private double z;

    public Ponto3D() {}

    public Ponto3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}