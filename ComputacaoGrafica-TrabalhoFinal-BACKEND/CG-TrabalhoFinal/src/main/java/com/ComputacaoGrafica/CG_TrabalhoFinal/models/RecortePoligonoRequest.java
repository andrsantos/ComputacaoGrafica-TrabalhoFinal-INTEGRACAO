package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import java.util.List;
import lombok.Data;

@Data
public class RecortePoligonoRequest {
    private List<Ponto> pontos;
    private int xMin;
    private int yMin;
    private int xMax;
    private int yMax;
}