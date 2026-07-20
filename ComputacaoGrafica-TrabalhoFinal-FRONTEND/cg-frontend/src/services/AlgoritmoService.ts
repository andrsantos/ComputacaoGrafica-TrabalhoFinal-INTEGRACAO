import type {
  Ponto, RetaRequest, CirculoRequest, ElipseRequest,
  PolilinhaRequest, BezierRequest, PoligonoRequest,
  RecorteRequest, RecortePoligonoRequest, TranslacaoRequest,
  EscalaRequest, RotacaoRequest,
  ProjecaoRequest
} from '../models/AlgoritmoModels';

const API_BASE_URL = 'http://localhost:8080/api/sintese';

export const AlgoritmoService = {
  
  async calcularBresenham(payload: RetaRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/bresenham`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
      throw new Error(`Erro na API: ${response.statusText}`);
    }

    return await response.json();
  },

  async calcularCirculo(payload: CirculoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/circulo`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async calcularElipse(payload: ElipseRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/elipse`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async calcularPolilinha(payload: PolilinhaRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/polilinha`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async calcularBezier(payload: BezierRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/bezier`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async preencherPoligono(payload: PoligonoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/preenchimento`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async calcularRecorte(payload: RecorteRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/recorte`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async calcularRecortePoligono(payload: RecortePoligonoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/recorte-poligono`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload)
    });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`);
    return await response.json();
  },

  async transladar(payload: TranslacaoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/translacao`, { 
      method: 'POST', 
      headers: { 'Content-Type': 'application/json' }, 
      body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  },

  async escalar(payload: EscalaRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/escala`, 
      { method: 'POST', 
        headers: { 'Content-Type': 'application/json' }, 
        body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  },

  async rotacionar(payload: RotacaoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/rotacao`, 
      { method: 'POST', 
        headers: { 'Content-Type': 'application/json' }, 
        body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  },

  async projetarOrtogonal(payload: ProjecaoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/projecao/ortogonal`, 
      { method: 'POST', 
        headers: { 'Content-Type': 'application/json' }, 
        body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  },

  async projetarObliqua(payload: ProjecaoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/projecao/obliqua`, 
      { method: 'POST', 
        headers: { 'Content-Type': 'application/json' }, 
        body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  },

  async projetarPerspectiva(payload: ProjecaoRequest): Promise<Ponto[]> {
    const response = await fetch(`${API_BASE_URL}/projecao/perspectiva`, 
      { method: 'POST', 
        headers: { 'Content-Type': 'application/json' }, 
        body: JSON.stringify(payload) });
    if (!response.ok) throw new Error(`Erro: ${response.statusText}`); 
    return await response.json();
  }


  
};