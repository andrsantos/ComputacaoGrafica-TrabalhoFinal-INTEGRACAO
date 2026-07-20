package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import java.util.List;
import lombok.Data;

@Data
public class RotacaoRequest {
    private List<Ponto> pontos;
    private double angulo; 
}