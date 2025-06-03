
# US13 - Análise de Complexidade (Pior Caso)

---

## Verificação de Viagem de Comboio

### Pseudocódigo (PathVerifier.verifyPath)

```
INICIO
ED: graph, locoType, stationType
ED: stations, candidateStations, s, start

stations ← lista de estações do grafo
candidateStations ← lista vazia

REPETIR PARA cada s em stations
    SE s.tipo == stationType ENTÃO
        adicionar s a candidateStations
    FIMSE
FIMREPETIR

REPETIR PARA cada start em candidateStations
    SE bfs(graph, locoType, stationType, start, candidateStations) ENTÃO
        ESCREVER true
        PARAR
    FIMSE
FIMREPETIR

ESCREVER false
FIM


```

### Pseudocódigo (PathVerifier.bfs)

```
INICIO
ED: graph, locoType, targetStationType, start, candidates
ED: queue, visited, current, head, connections, line, neighbor

queue ← [start]
visited ← [start]
head ← 0

REPETIR ENQUANTO head < tamanho(queue)
    current ← queue[head]
    head ← head + 1

    SE current ≠ start E current.tipo == targetStationType ENTÃO
        ESCREVER true
        PARAR
    FIMSE

    connections ← conexões do grafo a partir de current

    REPETIR PARA cada line em connections
        SE line.start == current ENTÃO
            neighbor ← line.end
        SENÃO
            neighbor ← line.start
        FIMSE

        SE neighbor está em visited ENTÃO
            CONTINUAR
        FIMSE

        SE locoType == ELECTRIC E line não é eletrificada ENTÃO
            CONTINUAR
        FIMSE

        adicionar neighbor a visited
        adicionar neighbor a queue
    FIMREPETIR
FIMREPETIR

ESCREVER false
FIM

```

### Contagem de Operações no Pior Caso

Sejam:
- `n` o número de estações
- `m` o número de linhas (arestas)
- `k` o número de estações do tipo `stationType`

#### verifyPath:
- Seleção das estações candidatas: até `n` comparações → `O(n)`
- Para cada uma das `k` estações, executa-se BFS → até `k × BFS`

#### bfs (por chamada):
- Lista `visited` pode ter até `n` inserções/verificações → `O(n)`
- Cada estação pode ter até `d` vizinhos, onde somatório de todos os graus é `2m` → complexidade linear em `n + m`

#### Total:
- verifyPath: `O(k(n + m))` ⟶ como `k ≤ n` → `O(n(n + m))`

### Complexidade Pior Caso

**O(n(n + m))**

---

## Conclusão

| Algoritmo            | Complexidade (Pior Caso) |
|----------------------|--------------------------|
| verifyPath (com BFS) | O(n(n + m))              |

> A verificação de caminho é significativamente mais custosa do que a construção do grafo. Isso reflete o esforço de percorrer múltiplas vezes o grafo em largura para diferentes estações.
