
# US27 - Análise de Complexidade (Pior Caso)

---

## Encontrar Caminho com Pontos Intermédios

### Pseudocódigo (findShortestPathWithWaypoints)

```
1.  INICIO
2.  ED: graph, waypoints, start, end
3.  ED: path, partialPath, result, current

4.  path ← lista vazia
5.  result ← lista vazia

6.  current ← start

7.  REPETIR PARA cada ponto em waypoints
8.      partialPath ← dijkstra(graph, current, ponto)
9.      SE partialPath está vazia ENTÃO
10.         ESCREVER lista vazia
11.         PARAR
12.     FIMSE
13.     adicionar partialPath a result (sem duplicar current)
14.     current ← ponto
15.  FIMREPETIR

16. partialPath ← dijkstra(graph, current, end)
17. SE partialPath está vazia ENTÃO
18.     ESCREVER lista vazia
19.     PARAR
20. FIMSE
21. adicionar partialPath a result (sem duplicar current)

22. ESCREVER result
23. FIM
```

### Pseudocódigo (dijkstra)

```
1.  INICIO
2.  ED: graph, source, destination
3.  ED: distances, previous, visited, queue

4.  PARA cada vértice v em graph:
5.      distances[v] ← ∞
6.      previous[v] ← nulo
7.  FIMPARA

8.  distances[source] ← 0
9.  queue ← lista com todos os vértices

10. REPETIR ENQUANTO queue não está vazia
11.     u ← vértice em queue com menor distances[u]
12.     remover u de queue
13.     SE u == destination ENTÃO PARAR

14.     PARA cada vizinho v de u:
15.         alt ← distances[u] + peso(u,v)
16.         SE alt < distances[v] ENTÃO
17.             distances[v] ← alt
18.             previous[v] ← u
19.         FIMSE
20.     FIMPARA
21. FIMREPETIR

22. Construir caminho de destino a origem com previous[]
23. ESCREVER caminho
24. FIM
```

### Contagem de Operações no Pior Caso

Sejam:
- `n`: número de estações (vértices)
- `m`: número de linhas (arestas)
- `k`: número de pontos intermédios (waypoints)

#### findShortestPathWithWaypoints:
- Executa Dijkstra `k + 1` vezes: `O((k + 1) × dijkstra)`

#### dijkstra:
- Inicia listas: `O(n)`
- Ciclo até esvaziar a fila: `n` iterações
    - Em cada, percorre vizinhos (graus somados ≤ 2m): `O(m)`
- Total: `O(n + m)`

### Tabela:

| **Linhas** | **Passos findShortestPathWithWaypoints** | **Passos dijkstra**                                |
|------------|------------------------------------------|----------------------------------------------------|
| 04         | 1A (inicialização)                       | –                                                  |
| 06         | 1A (variável current)                    | –                                                  |
| 07–15      | k chamadas a dijkstra                    | –                                                  |
| 16–21      | 1 chamada final a dijkstra               | –                                                  |
| 22         | 1R (retorno final)                       | –                                                  |
| 04–07      | –                                        | nA (inicializações: distances, previous)           |
| 08–09      | –                                        | 1A + nA (valor source e queue)                     |
| 10–21      | –                                        | nC (iterações queue) + mC (arestas total no grafo) |
| 22         | –                                        | O(n) (construção caminho invertido)                |

### Cálculo da Complexidade

#### Etapas:

1. **Chamada de Dijkstra para cada segmento (entre waypoints)**
  - O algoritmo chama `dijkstra(...)` para cada par consecutivo entre:
    - `start → waypoint1`
    - `waypoint1 → waypoint2`
    - ...
    - `último waypoint → end`
  - No total: `k + 1` chamadas a `dijkstra` (sendo `k` o número de pontos intermédios)

2. **Dijkstra**
  - Para cada chamada:
    - Inicializa `distances[]`, `previous[]`, `visited[]` → `O(n)`
    - Processa até `n` vértices, e percorre até `m` arestas no total → `O(n + m)`
  - Cada chamada a `dijkstra(...)` custa `O(n + m)`

#### Expressão completa:

```
findShortestPathWithWaypoints:
(k + 1) × O(n + m)
```

Substituindo por notação assintótica:

```
O((k + 1)(n + m))
```

Sabendo que `k ≤ n`, a expressão também pode ser reescrita como:

```
O(n(n + m))
```

### Complexidade Pior Caso

**findShortestPathWithWaypoints:** `O((k + 1)(n + m))`  
**dijkstra:** `O(n + m)`

---

## Conclusão

| **Algoritmo**                   | **Complexidade (Pior Caso)** |
|---------------------------------|------------------------------|
| `findShortestPathWithWaypoints` | `O((k + 1)(n + m))`          |
| `dijkstra`                      | `O(n + m)`                   |
