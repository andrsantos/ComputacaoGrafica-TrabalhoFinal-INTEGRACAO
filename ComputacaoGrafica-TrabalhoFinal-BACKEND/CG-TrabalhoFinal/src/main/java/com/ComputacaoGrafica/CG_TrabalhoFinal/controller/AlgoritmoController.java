package com.ComputacaoGrafica.CG_TrabalhoFinal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
import com.ComputacaoGrafica.CG_TrabalhoFinal.services.AlgoritmoService;

import java.util.List;

@RestController
@RequestMapping("/api/sintese")
@CrossOrigin(origins = "*")
public class AlgoritmoController {

    @Autowired
    private AlgoritmoService algoritmoService;


    @PostMapping("/bresenham")
    public List<Ponto> calcularBresenham(@RequestBody RetaRequest request) {
        return algoritmoService.calcularBresenham(request);
    }

    @PostMapping("/circulo")
    public List<Ponto> calcularCirculo(@RequestBody CirculoRequest request) {
        return algoritmoService.calcularCirculo(request);
    }

    @PostMapping("/elipse")
    public List<Ponto> calcularElipse(@RequestBody ElipseRequest request) {
        return algoritmoService.calcularElipse(request);
    }

    @PostMapping("/polilinha")
    public List<Ponto> calcularPolilinha(@RequestBody PolilinhaRequest request) {
        return algoritmoService.calcularPolilinha(request);
    }

    @PostMapping("/bezier")
    public List<Ponto> calcularBezier(@RequestBody BezierRequest request) {
        return algoritmoService.calcularBezier(request);
    }

    @PostMapping("/preenchimento")
    public List<Ponto> preencherPoligono(@RequestBody PoligonoRequest request) {
        return algoritmoService.preencherPoligono(request);
    }

    @PostMapping("/recorte")
    public List<Ponto> calcularRecorte(@RequestBody RecorteRequest request) {
        return algoritmoService.calcularRecorte(request);
    }

    @PostMapping("/recorte-poligono")
    public List<Ponto> calcularRecortePoligono(@RequestBody RecortePoligonoRequest request) {
        return algoritmoService.calcularRecortePoligono(request);
    }

    @PostMapping("/translacao")
    public List<Ponto> transladar(@RequestBody TranslacaoRequest request) { 
        return algoritmoService.transladar(request); 
    }

    @PostMapping("/escala")
    public List<Ponto> escalar(@RequestBody EscalaRequest request) { 
        return algoritmoService.escalar(request); 
    }

    @PostMapping("/rotacao")
    public List<Ponto> rotacionar(@RequestBody RotacaoRequest request) { 
        return algoritmoService.rotacionar(request); 
    }

    @PostMapping("/projecao/ortogonal")
    public List<Ponto> projetarOrtogonal(@RequestBody ProjecaoRequest request) { 
        return algoritmoService.projetarOrtogonal(request); 
    }

    @PostMapping("/projecao/obliqua")
    public List<Ponto> projetarObliqua(@RequestBody ProjecaoRequest request) { 
        return algoritmoService.projetarObliqua(request); 
    }

    @PostMapping("/projecao/perspectiva")
    public List<Ponto> projetarPerspectiva(@RequestBody ProjecaoRequest request) { 
        return algoritmoService.projetarPerspectiva(request); 
    }
    
}