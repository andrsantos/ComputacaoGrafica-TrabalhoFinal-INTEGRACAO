package com.ComputacaoGrafica.CG_TrabalhoFinal.models;

import lombok.Data;

@Data
public class ElipseRequest {
    private Ponto centro;
    private int raioX;
    private int raioY;
}