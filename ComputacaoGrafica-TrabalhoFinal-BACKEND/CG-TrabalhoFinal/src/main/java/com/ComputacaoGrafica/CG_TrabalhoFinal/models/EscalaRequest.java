package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import java.util.List;
import lombok.Data;

@Data
public class EscalaRequest {
    private List<Ponto> pontos;
    private double sx;
    private double sy;
}