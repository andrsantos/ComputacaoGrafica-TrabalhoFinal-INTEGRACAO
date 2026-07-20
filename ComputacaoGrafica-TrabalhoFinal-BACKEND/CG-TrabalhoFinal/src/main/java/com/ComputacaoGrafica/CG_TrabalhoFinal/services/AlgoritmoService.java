package com.ComputacaoGrafica.CG_TrabalhoFinal.services;
import java.util.List;

import com.ComputacaoGrafica.CG_TrabalhoFinal.models.BezierRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.CirculoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.ElipseRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.EscalaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.PoligonoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.PolilinhaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.Ponto;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.ProjecaoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RecortePoligonoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RecorteRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RetaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RotacaoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.TranslacaoRequest;

public interface AlgoritmoService {
    List<Ponto> calcularBresenham(RetaRequest request);
    List<Ponto> calcularCirculo(CirculoRequest request);
    List<Ponto> calcularElipse(ElipseRequest request);
    List<Ponto> calcularPolilinha(PolilinhaRequest request);
    List<Ponto> calcularBezier(BezierRequest request);
    List<Ponto> preencherPoligono(PoligonoRequest request);
    List<Ponto> calcularRecorte(RecorteRequest request);
    List<Ponto> calcularRecortePoligono(RecortePoligonoRequest request);
    List<Ponto> transladar(TranslacaoRequest request);
    List<Ponto> escalar(EscalaRequest request);
    List<Ponto> rotacionar(RotacaoRequest request);
    List<Ponto> projetarOrtogonal(ProjecaoRequest request);
    List<Ponto> projetarObliqua(ProjecaoRequest request);
    List<Ponto> projetarPerspectiva(ProjecaoRequest request);
}