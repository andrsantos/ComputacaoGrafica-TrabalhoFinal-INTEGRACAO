package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import java.util.List;

import lombok.Data;

@Data
public class ProjecaoRequest {
    private List<Ponto3D> pontos3D;
    private double distancia; 
}