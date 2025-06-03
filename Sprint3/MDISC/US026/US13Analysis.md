
# US13 - Análise de Complexidade (Pior Caso)

---

## Verificação de Viagem de Comboio

### Pseudocódigo (PathVerifier.verifyPath)

```
procedure verifyPath(graph, locoType, stationType)
    candidateStations := estações com tipo == stationType
    for cada start em candidateStations
        if bfs(graph, locoType, stationType, start, candidateStations) then
            return true
    return false
```

### Pseudocódigo (PathVerifier.bfs)

```
procedure bfs(graph, locoType, stationType, start, candidates)
    queue := [start]
    visited := [start]
    while queue não está vazia
        current := queue.pop()
        if current ≠ start e current.tipo == stationType então
            return true
        vizinhos := conexões(current)
        para cada vizinho
            se ainda não visitado e (linha é eletrificada se locoType == ELECTRIC)
                marcar visitado
                adicionar à fila
    return false
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

## Construção do Grafo (GraphC.buildGraph)

### Pseudocódigo

```
procedure buildGraph(stations, lines)
    limpar lista de estações
    para cada estação em stations
        adicionar à lista
    limpar lista de linhas
    para cada linha em lines
        adicionar à lista
```

### Contagem Literal

- `n` operações para adicionar estações
- `m` operações para adicionar linhas

### Complexidade Pior Caso

**O(n + m)**

---

## Conclusão

| Algoritmo            | Complexidade (Pior Caso) |
|----------------------|--------------------------|
| buildGraph           | O(n + m)                 |
| verifyPath (com BFS) | O(n(n + m))              |

> A verificação de caminho é significativamente mais custosa do que a construção do grafo. Isso reflete o esforço de percorrer múltiplas vezes o grafo em largura para diferentes estações.
