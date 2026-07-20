
export interface Ponto {
  x: number;
  y: number;
}

export interface RetaRequest {
  inicio: Ponto;
  fim: Ponto;
}

export interface CirculoRequest {
  centro: Ponto;
  raio: number;
}

export interface ElipseRequest {
  centro: Ponto;
  raioX: number;
  raioY: number;
}

export interface PolilinhaRequest {
  pontos: Ponto[];
}

export interface BezierRequest {
  pontos: Ponto[];
}

export interface PoligonoRequest {
  pontos: Ponto[];
}

export interface RecorteRequest {
  inicio: Ponto;
  fim: Ponto;
  xMin: number;
  yMin: number;
  xMax: number;
  yMax: number;
}

export interface RecortePoligonoRequest {
  pontos: Ponto[];
  xMin: number;
  yMin: number;
  xMax: number;
  yMax: number;
}

export interface TranslacaoRequest { 
  pontos: Ponto[]; 
  tx: number; 
  ty: number; 
}

export interface EscalaRequest { 
  pontos: Ponto[]; 
  sx: number; 
  sy: number; 
}

export interface RotacaoRequest { 
  pontos: Ponto[]; 
  angulo: number; 
}

export interface Ponto3D { 
    x: number; y: number; z: number; 
}

export interface ProjecaoRequest { 
    pontos3D: Ponto3D[]; distancia: number; 
}

