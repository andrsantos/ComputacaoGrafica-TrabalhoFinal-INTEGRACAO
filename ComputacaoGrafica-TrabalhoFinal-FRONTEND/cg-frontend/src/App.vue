<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { AlgoritmoService } from './services/AlgoritmoService'

const canvasRef = ref<HTMLCanvasElement | null>(null)
const algoritmoSelecionado = ref('bresenham')

const p1X = ref(0)
const p1Y = ref(0)
const p2X = ref(5)
const p2Y = ref(5)
const raio = ref(4)
const raioX = ref(6)
const raioY = ref(3)

const winXMin = ref(-5)
const winYMin = ref(-5)
const winXMax = ref(5)
const winYMax = ref(5)


const transX = ref(2)
const transY = ref(3)
const escalaX = ref(2.0)
const escalaY = ref(2.0)
const anguloRotacao = ref(45)



const polilinhaPontos = ref([
  { x: -8, y: -4 }, { x: -3, y: 6 }, { x: 3, y: -2 }, { x: 8, y: 5 }
])

const bezierPontos = ref([
  { x: -8, y: -5 }, 
  { x: 0, y: 8 },   
  { x: 8, y: -5 }   
])


const projecaoPontos = ref([
  { x: -3, y: -3, z: -3 }, // 1. Início: Face Frontal
  { x: 3, y: -3, z: -3 },  // 2. Base frontal direita
  { x: 3, y: 3, z: -3 },   // 3. Topo frontal direito
  { x: -3, y: 3, z: -3 },  // 4. Topo frontal esquerdo
  { x: -3, y: -3, z: -3 }, // 5. Fecha a Face Frontal
  { x: -3, y: -3, z: 3 },  // 6. Viaja para a base traseira esquerda
  { x: 3, y: -3, z: 3 },   // 7. Base traseira direita
  { x: 3, y: 3, z: 3 },    // 8. Topo traseiro direito
  { x: -3, y: 3, z: 3 },   // 9. Topo traseiro esquerdo
  { x: -3, y: -3, z: 3 },  // 10. Fecha a Face Traseira
  { x: -3, y: 3, z: 3 },   // 11. Sobe na traseira esquerda
  { x: -3, y: 3, z: -3 },  // 12. Conecta topo esquerdo (frente-trás)
  { x: 3, y: 3, z: -3 },   // 13. Vai para topo direito frontal
  { x: 3, y: 3, z: 3 },    // 14. Conecta topo direito (frente-trás)
  { x: 3, y: -3, z: 3 },   // 15. Desce na traseira direita
  { x: 3, y: -3, z: -3 }   // 16. Conecta base direita (frente-trás)
])


const distanciaCamera = ref(10)

const adicionarPontoProjecao = () => projecaoPontos.value.push({ x: 0, y: 0, z: 0 })
const removerPontoProjecao = (index: number) => projecaoPontos.value.splice(index, 1)

const adicionarPontoPolilinha = () => polilinhaPontos.value.push({ x: 0, y: 0 })
const removerPontoPolilinha = (index: number) => {
  if (polilinhaPontos.value.length > 2) polilinhaPontos.value.splice(index, 1)
  else alert('A polilinha precisa de pelo menos 2 pontos!')
}

const adicionarPontoBezier = () => bezierPontos.value.push({ x: 0, y: 0 })
const removerPontoBezier = (index: number) => {
  if (bezierPontos.value.length > 3) bezierPontos.value.splice(index, 1)
  else alert('A curva de Bézier requer pelo menos 3 pontos de controle!')
}

const limparCanvas = () => {
  const canvas = canvasRef.value
  if (!canvas) return
  const ctx = canvas.getContext('2d')
  if (!ctx) return

  ctx.fillStyle = '#ffffff'
  ctx.fillRect(0, 0, canvas.width, canvas.height)
  
  const escala = 25;
  ctx.strokeStyle = '#e0e0e0'
  ctx.lineWidth = 1
  for (let i = 0; i <= canvas.width; i += escala) {
    ctx.beginPath(); ctx.moveTo(i, 0); ctx.lineTo(i, canvas.height); ctx.stroke();
    ctx.beginPath(); ctx.moveTo(0, i); ctx.lineTo(canvas.width, i); ctx.stroke();
  }

  ctx.strokeStyle = '#000000'
  ctx.lineWidth = 2
  ctx.beginPath(); ctx.moveTo(0, canvas.height / 2); ctx.lineTo(canvas.width, canvas.height / 2);
  ctx.moveTo(canvas.width / 2, 0); ctx.lineTo(canvas.width / 2, canvas.height); ctx.stroke();
}

onMounted(() => { limparCanvas() })

const converterCoordenada = (x: number, y: number) => {
  const escala = 25; const centroX = 300; const centroY = 300; 
  return { canvasX: centroX + (x * escala), canvasY: centroY - (y * escala) }
}

const chamarApi = async () => {
  try {
    let pontos: { x: number; y: number }[] = []

    if (algoritmoSelecionado.value === 'bresenham') {
      pontos = await AlgoritmoService.calcularBresenham({
        inicio: { x: p1X.value, y: p1Y.value }, fim: { x: p2X.value, y: p2Y.value }
      })
    } else if (algoritmoSelecionado.value === 'circulo') {
      pontos = await AlgoritmoService.calcularCirculo({
        centro: { x: p1X.value, y: p1Y.value }, raio: raio.value
      })
    } else if (algoritmoSelecionado.value === 'elipse') {
      pontos = await AlgoritmoService.calcularElipse({
        centro: { x: p1X.value, y: p1Y.value }, raioX: raioX.value, raioY: raioY.value
      })
    } else if (algoritmoSelecionado.value === 'polilinha') {
      pontos = await AlgoritmoService.calcularPolilinha({ pontos: polilinhaPontos.value })
    } else if (algoritmoSelecionado.value === 'bezier') {
      pontos = await AlgoritmoService.calcularBezier({ pontos: bezierPontos.value })
    } else if (algoritmoSelecionado.value === 'preenchimento') {
      pontos = await AlgoritmoService.preencherPoligono({ pontos: polilinhaPontos.value })
    } else if (algoritmoSelecionado.value === 'recorte') {
      pontos = await AlgoritmoService.calcularRecorte({
        inicio: { x: p1X.value, y: p1Y.value },
        fim: { x: p2X.value, y: p2Y.value },
        xMin: winXMin.value, yMin: winYMin.value,
        xMax: winXMax.value, yMax: winYMax.value
      })
    } else if (algoritmoSelecionado.value === 'recortePoligono') {
      pontos = await AlgoritmoService.calcularRecortePoligono({
        pontos: polilinhaPontos.value,
        xMin: winXMin.value, yMin: winYMin.value,
        xMax: winXMax.value, yMax: winYMax.value
      })
    }
      else if (algoritmoSelecionado.value === 'translacao') {
      pontos = await AlgoritmoService.transladar({ pontos: polilinhaPontos.value, tx: transX.value, ty: transY.value })
    } else if (algoritmoSelecionado.value === 'escala') {
      pontos = await AlgoritmoService.escalar({ pontos: polilinhaPontos.value, sx: escalaX.value, sy: escalaY.value })
    } else if (algoritmoSelecionado.value === 'rotacao') {
      pontos = await AlgoritmoService.rotacionar({ pontos: polilinhaPontos.value, angulo: anguloRotacao.value })
    } else if (algoritmoSelecionado.value === 'projOrtogonal') {
      pontos = await AlgoritmoService.projetarOrtogonal({ pontos3D: projecaoPontos.value, distancia: distanciaCamera.value })
    } else if (algoritmoSelecionado.value === 'projObliqua') {
      pontos = await AlgoritmoService.projetarObliqua({ pontos3D: projecaoPontos.value, distancia: distanciaCamera.value })
    } else if (algoritmoSelecionado.value === 'projPerspectiva') {
      pontos = await AlgoritmoService.projetarPerspectiva({ pontos3D: projecaoPontos.value, distancia: distanciaCamera.value })
    }


    const canvas = canvasRef.value
    if (!canvas) return
    const ctx = canvas.getContext('2d')
    if (!ctx) return

    limparCanvas() 

    if (['recorte', 'recortePoligono'].includes(algoritmoSelecionado.value)) {
      ctx.strokeStyle = 'rgba(0, 0, 255, 0.5)'; 
      ctx.lineWidth = 2;
      const tEsq = converterCoordenada(winXMin.value, winYMax.value);
      const largura = (winXMax.value - winXMin.value) * 25;
      const altura = (winYMax.value - winYMin.value) * 25;
      ctx.strokeRect(tEsq.canvasX, tEsq.canvasY, largura, altura);
    }

    ctx.fillStyle = '#ff0000' 

    pontos.forEach((ponto) => {
      const { canvasX, canvasY } = converterCoordenada(ponto.x, ponto.y)
      ctx.fillRect(canvasX + 1, canvasY - 24, 23, 23) 
    })

  } catch (error) {
    console.error("Erro ao chamar o serviço:", error)
    alert("Falha ao conectar com a API.")
  }
}
</script>

<template>
  <div class="cg-container">
    <section class="canvas-section">
      <canvas ref="canvasRef" width="600" height="600" class="cg-canvas"></canvas>
    </section>

    <aside class="config-section" style="overflow-y: auto;">
      <h2>Configurações</h2>
      
      <div class="control-group">
        <label>Algoritmo:</label>
        <select v-model="algoritmoSelecionado">
          <option value="bresenham">Bresenham (Reta)</option>
          <option value="circulo">Círculo</option>
          <option value="elipse">Elipse</option>
          <option value="polilinha">Polilinha</option>
          <option value="bezier">Curva de Bézier</option>
          <option value="preenchimento">Preenchimento (Scanline)</option>
          <option value="recorte">Recorte de Linha (Cohen-Sutherland)</option>
          <option value="recortePoligono">Recorte de Polígono (Sutherland-Hodgman)</option>
          <option value="translacao">Translação 2D</option>
          <option value="escala">Escala 2D</option>
          <option value="rotacao">Rotação 2D</option>
          <option value="projOrtogonal">Projeção Ortogonal</option>
          <option value="projObliqua">Projeção Oblíqua</option>
          <option value="projPerspectiva">Projeção Perspectiva</option>
        </select>
      </div>

      <div v-if="['bresenham', 'circulo', 'elipse'].includes(algoritmoSelecionado)">
        <div class="control-group">
          <label>{{ algoritmoSelecionado === 'bresenham' ? 'Ponto Inicial' : 'Centro' }} (X, Y):</label>
          <input type="number" v-model="p1X" placeholder="X" />
          <input type="number" v-model="p1Y" placeholder="Y" />
        </div>

        <div class="control-group" v-if="algoritmoSelecionado === 'bresenham'">
          <label>Ponto Final (X, Y):</label>
          <input type="number" v-model="p2X" placeholder="X" />
          <input type="number" v-model="p2Y" placeholder="Y" />
        </div>

        <div class="control-group" v-if="algoritmoSelecionado === 'circulo'">
          <label>Raio:</label>
          <input type="number" v-model="raio" placeholder="Raio do Círculo" />
        </div>

        <div class="control-group" v-if="algoritmoSelecionado === 'elipse'">
          <label>Raio X e Raio Y:</label>
          <input type="number" v-model="raioX" placeholder="Raio X" />
          <input type="number" v-model="raioY" placeholder="Raio Y" />
        </div>
      </div>

      <div v-if="algoritmoSelecionado === 'bezier'" style="display: flex; flex-direction: column; gap: 10px;">
        <label>Pontos de Controle (Bézier):</label>
        <div v-for="(ponto, index) in bezierPontos" :key="'bez-'+index" style="display: flex; gap: 5px;">
          <input type="number" v-model="ponto.x" placeholder="X" style="width: 40%;" />
          <input type="number" v-model="ponto.y" placeholder="Y" style="width: 40%;" />
          <button @click="removerPontoBezier(index)" style="width: 20%; background-color: #ff4444; color: white; border: none; border-radius: 4px; cursor: pointer;">X</button>
        </div>
        <button @click="adicionarPontoBezier" style="padding: 8px; background-color: #9c27b0; color: white; border: none; border-radius: 4px; cursor: pointer;">+ Adicionar Ponto</button>
      </div>

      <div class="control-group" v-if="['recorte', 'recortePoligono'].includes(algoritmoSelecionado)" style="margin-top: 10px;">
        <label>Janela de Recorte (Mín e Máx):</label>
        <div style="display: flex; gap: 5px;">
          <input type="number" v-model="winXMin" placeholder="X Min" style="width: 50%;" />
          <input type="number" v-model="winYMin" placeholder="Y Min" style="width: 50%;" />
        </div>
        <div style="display: flex; gap: 5px; margin-top: 5px;">
          <input type="number" v-model="winXMax" placeholder="X Máx" style="width: 50%;" />
          <input type="number" v-model="winYMax" placeholder="Y Máx" style="width: 50%;" />
        </div>
      </div>

      <div class="control-group" v-if="algoritmoSelecionado === 'recorte'" style="margin-top: 15px;">
        <label>Reta a ser Recortada:</label>
        <div style="display: flex; gap: 5px;">
           <input type="number" v-model="p1X" placeholder="X1" style="width: 50%;" />
           <input type="number" v-model="p1Y" placeholder="Y1" style="width: 50%;" />
        </div>
        <div style="display: flex; gap: 5px; margin-top: 5px;">
           <input type="number" v-model="p2X" placeholder="X2" style="width: 50%;" />
           <input type="number" v-model="p2Y" placeholder="Y2" style="width: 50%;" />
        </div>
      </div>


      <div v-if="['polilinha', 'preenchimento', 'recortePoligono', 'translacao', 'escala', 'rotacao'].includes(algoritmoSelecionado)" style="display: flex; flex-direction: column; gap: 10px; margin-top: 10px;">
        <label>Vértices (Polígono/Polilinha):</label>
        <div v-for="(ponto, index) in polilinhaPontos" :key="'poly-'+index" style="display: flex; gap: 5px;">
          <input type="number" v-model="ponto.x" placeholder="X" style="width: 40%;" />
          <input type="number" v-model="ponto.y" placeholder="Y" style="width: 40%;" />
          <button @click="removerPontoPolilinha(index)" style="width: 20%; background-color: #ff4444; color: white; border: none; border-radius: 4px; cursor: pointer;">X</button>
        </div>
        <button @click="adicionarPontoPolilinha" style="padding: 8px; background-color: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer;">+ Adicionar Vértice</button>
      </div>

      <div v-if="['translacao', 'escala', 'rotacao'].includes(algoritmoSelecionado)">
        
        <div class="control-group" v-if="algoritmoSelecionado === 'translacao'">
          <label>Fatores de Translação (Tx, Ty):</label>
          <div style="display: flex; gap: 5px;">
            <input type="number" v-model="transX" placeholder="Tx" style="width: 50%;" />
            <input type="number" v-model="transY" placeholder="Ty" style="width: 50%;" />
          </div>
        </div>

        <div class="control-group" v-if="algoritmoSelecionado === 'escala'">
          <label>Fatores de Escala (Sx, Sy):</label>
          <div style="display: flex; gap: 5px;">
            <input type="number" step="0.1" v-model="escalaX" placeholder="Sx" style="width: 50%;" />
            <input type="number" step="0.1" v-model="escalaY" placeholder="Sy" style="width: 50%;" />
          </div>
        </div>

        <div class="control-group" v-if="algoritmoSelecionado === 'rotacao'">
          <label>Ângulo de Rotação (Graus):</label>
          <input type="number" v-model="anguloRotacao" placeholder="Ângulo" />
        </div>

      </div>

      <div v-if="['projOrtogonal', 'projObliqua', 'projPerspectiva'].includes(algoritmoSelecionado)" style="display: flex; flex-direction: column; gap: 10px; margin-top: 10px;">
        
        <div class="control-group" v-if="algoritmoSelecionado === 'projPerspectiva'">
          <label>Distância da Câmera (Foco):</label>
          <input type="number" v-model="distanciaCamera" />
        </div>

        <label>Vértices do Sólido (X, Y, Z):</label>
        <div v-for="(ponto, index) in projecaoPontos" :key="'proj-'+index" style="display: flex; gap: 5px;">
          <input type="number" v-model="ponto.x" placeholder="X" style="width: 25%;" />
          <input type="number" v-model="ponto.y" placeholder="Y" style="width: 25%;" />
          <input type="number" v-model="ponto.z" placeholder="Z" style="width: 25%;" />
          <button @click="removerPontoProjecao(index)" style="width: 20%; background-color: #ff4444; color: white; border: none; border-radius: 4px; cursor: pointer;">X</button>
        </div>
        <button @click="adicionarPontoProjecao" style="padding: 8px; background-color: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer;">+ Adicionar Ponto 3D</button>
      </div>

      <button class="btn-draw" @click="chamarApi" style="margin-top: 20px;">Executar Algoritmo</button>
    </aside>
  </div>
</template>

<style>
* { box-sizing: border-box; margin: 0; padding: 0; }
body { font-family: sans-serif; background-color: #1e1e2f; }
.cg-container { display: flex; height: 100vh; padding: 20px; gap: 20px; }
.canvas-section { flex: 1; display: flex; justify-content: center; align-items: center; background-color: #2a2a3f; border-radius: 8px; box-shadow: inset 0 0 10px rgba(0,0,0,0.5); }
.cg-canvas { background-color: white; border-radius: 4px; box-shadow: 0 4px 15px rgba(0,0,0,0.3); }
.config-section { width: 350px; background-color: #ffffff; padding: 25px; border-radius: 8px; box-shadow: 0 4px 6px rgba(0,0,0,0.1); display: flex; flex-direction: column; gap: 10px; }
h2 { color: #333; font-size: 1.5rem; border-bottom: 2px solid #eee; padding-bottom: 10px; }
.control-group { display: flex; flex-direction: column; gap: 8px; }
label { font-weight: bold; color: #555; font-size: 0.9rem; }
input, select { padding: 10px; border: 1px solid #ccc; border-radius: 4px; font-size: 1rem; }
.btn-draw { margin-top: auto; padding: 15px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 1.1rem; font-weight: bold; transition: background-color 0.2s; }
.btn-draw:hover { background-color: #45a049; }
</style>