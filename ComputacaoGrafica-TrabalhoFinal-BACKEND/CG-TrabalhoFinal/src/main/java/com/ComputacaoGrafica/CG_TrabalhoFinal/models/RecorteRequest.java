package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import lombok.Data;

@Data
public class RecorteRequest {
    private Ponto inicio;
    private Ponto fim;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
}