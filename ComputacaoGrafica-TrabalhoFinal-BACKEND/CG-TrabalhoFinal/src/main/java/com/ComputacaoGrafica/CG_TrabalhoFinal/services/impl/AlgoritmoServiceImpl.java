package com.ComputacaoGrafica.CG_TrabalhoFinal.services.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.BezierRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.CirculoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.ElipseRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.EscalaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.PoligonoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.PolilinhaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.Ponto;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.Ponto3D;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.ProjecaoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RecortePoligonoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RecorteRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RetaRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.RotacaoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.models.TranslacaoRequest;
import com.ComputacaoGrafica.CG_TrabalhoFinal.services.AlgoritmoService;

@Service
public class AlgoritmoServiceImpl implements AlgoritmoService {

    private static final int INSIDE = 0; // 0000
    private static final int LEFT = 1;   // 0001
    private static final int RIGHT = 2;  // 0010
    private static final int BOTTOM = 4; // 0100
    private static final int TOP = 8;    // 1000


    @Override
    public List<Ponto> calcularBresenham(RetaRequest request) {
        List<Ponto> pontos = new ArrayList<>();
        
        int x1 = request.getInicio().getX();
        int y1 = request.getInicio().getY();
        int x2 = request.getFim().getX();
        int y2 = request.getFim().getY();

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = x1 < x2 ? 1 : -1;
        int sy = y1 < y2 ? 1 : -1;
        int err = dx - dy;

        while (true) {
            pontos.add(new Ponto(x1, y1));

            if (x1 == x2 && y1 == y2) break;

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }

        return pontos;
    }


    @Override
    public List<Ponto> calcularCirculo(CirculoRequest request) {
        List<Ponto> pontos = new ArrayList<>();
        int xc = request.getCentro().getX();
        int yc = request.getCentro().getY();
        int r = request.getRaio();

        int x = 0;
        int y = r;
        int d = 3 - 2 * r;

        adicionarPontosCirculo(pontos, xc, yc, x, y);

        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            adicionarPontosCirculo(pontos, xc, yc, x, y);
        }
        return pontos;
    }

    private void adicionarPontosCirculo(List<Ponto> pontos, int xc, int yc, int x, int y) {
        pontos.add(new Ponto(xc + x, yc + y));
        pontos.add(new Ponto(xc - x, yc + y));
        pontos.add(new Ponto(xc + x, yc - y));
        pontos.add(new Ponto(xc - x, yc - y));
        pontos.add(new Ponto(xc + y, yc + x));
        pontos.add(new Ponto(xc - y, yc + x));
        pontos.add(new Ponto(xc + y, yc - x));
        pontos.add(new Ponto(xc - y, yc - x));
    }



    @Override
    public List<Ponto> calcularElipse(ElipseRequest request) {
        List<Ponto> pontos = new ArrayList<>();
        int xc = request.getCentro().getX();
        int yc = request.getCentro().getY();
        int rx = request.getRaioX();
        int ry = request.getRaioY();

        double dx, dy, d1, d2, x, y;
        x = 0;
        y = ry;

        d1 = (ry * ry) - (rx * rx * ry) + (0.25 * rx * rx);
        dx = 2 * ry * ry * x;
        dy = 2 * rx * rx * y;

        while (dx < dy) {
            adicionarPontosElipse(pontos, xc, yc, (int)x, (int)y);
            if (d1 < 0) {
                x++;
                dx = dx + (2 * ry * ry);
                d1 = d1 + dx + (ry * ry);
            } else {
                x++;
                y--;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d1 = d1 + dx - dy + (ry * ry);
            }
        }

        d2 = ((ry * ry) * ((x + 0.5) * (x + 0.5))) + ((rx * rx) * ((y - 1) * (y - 1))) - (rx * rx * ry * ry);

        while (y >= 0) {
            adicionarPontosElipse(pontos, xc, yc, (int)x, (int)y);
            if (d2 > 0) {
                y--;
                dy = dy - (2 * rx * rx);
                d2 = d2 + (rx * rx) - dy;
            } else {
                y--;
                x++;
                dx = dx + (2 * ry * ry);
                dy = dy - (2 * rx * rx);
                d2 = d2 + dx - dy + (rx * rx);
            }
        }
        return pontos;
    }


    private void adicionarPontosElipse(List<Ponto> pontos, int xc, int yc, int x, int y) {
        pontos.add(new Ponto(xc + x, yc + y));
        pontos.add(new Ponto(xc - x, yc + y));
        pontos.add(new Ponto(xc + x, yc - y));
        pontos.add(new Ponto(xc - x, yc - y));
    }

    @Override
    public List<Ponto> calcularPolilinha(PolilinhaRequest request) {
        List<Ponto> resultado = new ArrayList<>();
        List<Ponto> vertices = request.getPontos();

        if (vertices == null || vertices.size() < 2) {
            return resultado;
        }

        for (int i = 0; i < vertices.size() - 1; i++) {
            Ponto p1 = vertices.get(i);
            Ponto p2 = vertices.get(i + 1);
            
            RetaRequest reta = new RetaRequest();
            reta.setInicio(p1);
            reta.setFim(p2);
            
            resultado.addAll(calcularBresenham(reta));
        }

        return resultado;
    }



    @Override
    public List<Ponto> calcularBezier(BezierRequest request) {
        List<Ponto> resultado = new ArrayList<>();
        List<Ponto> deControle = request.getPontos();

        if (deControle == null || deControle.size() < 3) {
            return resultado;
        }

        int n = deControle.size() - 1; 
        double passo = 0.05; 
        List<Ponto> pontosDaCurva = new ArrayList<>();

        for (double t = 0; t <= 1; t += passo) {
            double x = 0;
            double y = 0;

            for (int i = 0; i <= n; i++) {
                double coeficiente = combinacao(n, i) * Math.pow(1 - t, n - i) * Math.pow(t, i);
                x += coeficiente * deControle.get(i).getX();
                y += coeficiente * deControle.get(i).getY();
            }
            pontosDaCurva.add(new Ponto((int) Math.round(x), (int) Math.round(y)));
        }

        pontosDaCurva.add(deControle.get(n));

        PolilinhaRequest polyReq = new PolilinhaRequest();
        polyReq.setPontos(pontosDaCurva);
        
        return calcularPolilinha(polyReq);
    }

    private int combinacao(int n, int k) {
        return fatorial(n) / (fatorial(k) * fatorial(n - k));
    }

    private int fatorial(int num) {
        if (num <= 1) return 1;
        int fat = 1;
        for (int i = 2; i <= num; i++) {
            fat *= i;
        }
        return fat;
    }



    @Override
    public List<Ponto> preencherPoligono(PoligonoRequest request) {
        List<Ponto> resultado = new ArrayList<>();
        List<Ponto> vertices = request.getPontos();

        if (vertices == null || vertices.size() < 3) {
            return resultado; 
        }

        int minY = vertices.get(0).getY();
        int maxY = vertices.get(0).getY();
        for (Ponto v : vertices) {
            if (v.getY() < minY) minY = v.getY();
            if (v.getY() > maxY) maxY = v.getY();
        }

        for (int y = minY; y <= maxY; y++) {
            List<Integer> intersecoesX = new ArrayList<>();

            for (int i = 0; i < vertices.size(); i++) {
                Ponto p1 = vertices.get(i);
                Ponto p2 = vertices.get((i + 1) % vertices.size()); 

                if ((p1.getY() <= y && p2.getY() > y) || (p2.getY() <= y && p1.getY() > y)) {
                    double x = p1.getX() + (double) (y - p1.getY()) * (p2.getX() - p1.getX()) / (p2.getY() - p1.getY());
                    intersecoesX.add((int) Math.round(x));
                }
            }

            intersecoesX.sort(Integer::compareTo);

            for (int i = 0; i < intersecoesX.size(); i += 2) {
                if (i + 1 < intersecoesX.size()) {
                    int xInicio = intersecoesX.get(i);
                    int xFim = intersecoesX.get(i + 1);
                    for (int x = xInicio; x <= xFim; x++) {
                        resultado.add(new Ponto(x, y));
                    }
                }
            }
        }
        return resultado;
    }



    @Override
    public List<Ponto> calcularRecorte(RecorteRequest request) {
        double xMin = request.getXMin();
        double yMin = request.getYMin(); 
        double xMax = request.getXMax();
        double yMax = request.getYMax();

        double x1 = request.getInicio().getX();
        double y1 = request.getInicio().getY();
        double x2 = request.getFim().getX();
        double y2 = request.getFim().getY();

        int code1 = computarCodigo(x1, y1, xMin, yMin, xMax, yMax);
        int code2 = computarCodigo(x2, y2, xMin, yMin, xMax, yMax);
        boolean aceito = false;

        while (true) {
            if ((code1 == 0) && (code2 == 0)) {
                aceito = true;
                break;
            } else if ((code1 & code2) != 0) {
                break;
            } else {
                int codeOut = (code1 != 0) ? code1 : code2;
                double x = 0, y = 0;

                if ((codeOut & TOP) != 0) {
                    x = x1 + (x2 - x1) * (yMax - y1) / (y2 - y1);
                    y = yMax;
                } else if ((codeOut & BOTTOM) != 0) {
                    x = x1 + (x2 - x1) * (yMin - y1) / (y2 - y1);
                    y = yMin;
                } else if ((codeOut & RIGHT) != 0) {
                    y = y1 + (y2 - y1) * (xMax - x1) / (x2 - x1);
                    x = xMax;
                } else if ((codeOut & LEFT) != 0) {
                    y = y1 + (y2 - y1) * (xMin - x1) / (x2 - x1);
                    x = xMin;
                }

                if (codeOut == code1) {
                    x1 = x;
                    y1 = y;
                    code1 = computarCodigo(x1, y1, xMin, yMin, xMax, yMax);
                } else {
                    x2 = x;
                    y2 = y;
                    code2 = computarCodigo(x2, y2, xMin, yMin, xMax, yMax);
                }
            }
        }

        if (aceito) {
            RetaRequest retaClippada = new RetaRequest();
            retaClippada.setInicio(new Ponto((int) Math.round(x1), (int) Math.round(y1)));
            retaClippada.setFim(new Ponto((int) Math.round(x2), (int) Math.round(y2)));
            return calcularBresenham(retaClippada);
        }

        return new ArrayList<>(); 
    }

    private int computarCodigo(double x, double y, double xMin, double yMin, double xMax, double yMax) {
        int code = INSIDE;
        if (x < xMin) code |= LEFT;
        else if (x > xMax) code |= RIGHT;
        if (y < yMin) code |= BOTTOM;
        else if (y > yMax) code |= TOP;
        return code;
    }


    @Override
    public List<Ponto> calcularRecortePoligono(RecortePoligonoRequest request) {
        List<Ponto> poligono = request.getPontos();
        
        if (poligono == null || poligono.size() < 3) return new ArrayList<>();

        int xMin = request.getXMin();
        int yMin = request.getYMin();
        int xMax = request.getXMax();
        int yMax = request.getYMax();

        poligono = recortarAresta(poligono, 0, xMin);
        poligono = recortarAresta(poligono, 1, xMax);
        poligono = recortarAresta(poligono, 2, yMin);
        poligono = recortarAresta(poligono, 3, yMax);

        if (poligono.isEmpty()) return new ArrayList<>();

        PolilinhaRequest polyReq = new PolilinhaRequest();
        List<Ponto> poligonoFechado = new ArrayList<>(poligono);
        poligonoFechado.add(poligono.get(0)); 
        polyReq.setPontos(poligonoFechado);

        return calcularPolilinha(polyReq);
    }

    private List<Ponto> recortarAresta(List<Ponto> poly, int edge, int limite) {
        List<Ponto> resultado = new ArrayList<>();
        if (poly.isEmpty()) return resultado;

        for (int i = 0; i < poly.size(); i++) {
            Ponto atual = poly.get(i);
            Ponto proximo = poly.get((i + 1) % poly.size());

            boolean atualDentro = estaDentro(atual, edge, limite);
            boolean proximoDentro = estaDentro(proximo, edge, limite);

            if (atualDentro && proximoDentro) {
                resultado.add(proximo);
            } else if (atualDentro && !proximoDentro) {
                resultado.add(calcularIntersecao(atual, proximo, edge, limite));
            } else if (!atualDentro && proximoDentro) {
                resultado.add(calcularIntersecao(atual, proximo, edge, limite));
                resultado.add(proximo);
            }
        }
        return resultado;
    }

    private boolean estaDentro(Ponto pt, int edge, int limite) {
        switch (edge) {
            case 0: return pt.getX() >= limite; // Esquerda
            case 1: return pt.getX() <= limite; // Direita
            case 2: return pt.getY() >= limite; // Fundo
            case 3: return pt.getY() <= limite; // Topo
            default: return false;
        }
    }

    private Ponto calcularIntersecao(Ponto p1, Ponto p2, int edge, int limite) {
        double x = 0, y = 0;
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();

        if (edge == 0 || edge == 1) { 
            x = limite;
            y = p1.getY() + dy * (limite - p1.getX()) / dx;
        } else { 
            y = limite;
            x = p1.getX() + dx * (limite - p1.getY()) / dy;
        }
        return new Ponto((int) Math.round(x), (int) Math.round(y));
    }



    @Override
    public List<Ponto> transladar(TranslacaoRequest request) {
        List<Ponto> transformados = new ArrayList<>();
        if (request.getPontos() == null) return transformados;

        for (Ponto p : request.getPontos()) {
            int novoX = p.getX() + request.getTx();
            int novoY = p.getY() + request.getTy();
            transformados.add(new Ponto(novoX, novoY));
        }
        return renderizarTransformacao(transformados);
    }

    @Override
    public List<Ponto> escalar(EscalaRequest request) {
        List<Ponto> transformados = new ArrayList<>();
        if (request.getPontos() == null) return transformados;

        for (Ponto p : request.getPontos()) {
            int novoX = (int) Math.round(p.getX() * request.getSx());
            int novoY = (int) Math.round(p.getY() * request.getSy());
            transformados.add(new Ponto(novoX, novoY));
        }
        return renderizarTransformacao(transformados);
    }

    @Override
    public List<Ponto> rotacionar(RotacaoRequest request) {
        List<Ponto> transformados = new ArrayList<>();
        if (request.getPontos() == null) return transformados;

        double rad = Math.toRadians(request.getAngulo());
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        for (Ponto p : request.getPontos()) {
            double x = p.getX();
            double y = p.getY();
            
            int novoX = (int) Math.round((x * cos) - (y * sin));
            int novoY = (int) Math.round((x * sin) + (y * cos));
            
            transformados.add(new Ponto(novoX, novoY));
        }
        return renderizarTransformacao(transformados);
    }

    private List<Ponto> renderizarTransformacao(List<Ponto> vertices) {
        if (vertices.size() < 2) return vertices;
        PolilinhaRequest polyReq = new PolilinhaRequest();
        List<Ponto> fechado = new ArrayList<>(vertices);
        fechado.add(vertices.get(0)); 
        polyReq.setPontos(fechado);
        return calcularPolilinha(polyReq);
    }


    @Override
    public List<Ponto> projetarOrtogonal(ProjecaoRequest request) {
        List<Ponto> pontos2D = new ArrayList<>();
        if (request.getPontos3D() == null) return pontos2D;

        for (Ponto3D p3d : request.getPontos3D()) {
            pontos2D.add(new Ponto((int) Math.round(p3d.getX()), (int) Math.round(p3d.getY())));
        }
        return renderizarProjecao(pontos2D);
    }

    @Override
    public List<Ponto> projetarObliqua(ProjecaoRequest request) {
        List<Ponto> pontos2D = new ArrayList<>();
        if (request.getPontos3D() == null) return pontos2D;

        double rad = Math.toRadians(45);
        double l = 0.5;

        for (Ponto3D p3d : request.getPontos3D()) {
            int x = (int) Math.round(p3d.getX() + p3d.getZ() * l * Math.cos(rad));
            int y = (int) Math.round(p3d.getY() + p3d.getZ() * l * Math.sin(rad));
            pontos2D.add(new Ponto(x, y));
        }
        return renderizarProjecao(pontos2D);
    }

    @Override
    public List<Ponto> projetarPerspectiva(ProjecaoRequest request) {
        List<Ponto> pontos2D = new ArrayList<>();
        if (request.getPontos3D() == null) return pontos2D;

        double d = request.getDistancia();
        if (d == 0) d = 10; 

        for (Ponto3D p3d : request.getPontos3D()) {
            double zCamera = p3d.getZ() + d;
            if (zCamera == 0) zCamera = 0.1; 

            int x = (int) Math.round((p3d.getX() * d) / zCamera);
            int y = (int) Math.round((p3d.getY() * d) / zCamera);
            pontos2D.add(new Ponto(x, y));
        }
        return renderizarProjecao(pontos2D);
    }

    private List<Ponto> renderizarProjecao(List<Ponto> vertices) {
        if (vertices.size() < 2) return vertices;
        PolilinhaRequest polyReq = new PolilinhaRequest();
        polyReq.setPontos(vertices);
        return calcularPolilinha(polyReq); 
    }

    
}
