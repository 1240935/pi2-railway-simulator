# US14 - Análise de Complexidade (Pior Caso)

---

## Verificação de Percurso de Manutenção (Euleriano)

### Pseudocódigo (Route.findEulerianPath)

```
INICIO
ED: graph, stations, lines, oddDegreeStations, current, degree, line

stations ← obter estações do grafo
lines ← obter linhas do grafo
oddDegreeStations ← lista vazia

REPETIR PARA cada current em stations
    degree ← 0

    REPETIR PARA cada line em lines
        SE line.start == current OU line.end == current ENTÃO
            degree ← degree + 1
        FIMSE
    FIMREPETIR

    SE degree é ímpar ENTÃO
        adicionar current a oddDegreeStations
    FIMSE
FIMREPETIR

SE tamanho(oddDegreeStations) == 0 ENTÃO
    DEVOLVE stations
SENÃO SE tamanho(oddDegreeStations) == 2 ENTÃO
    DEVOLVE oddDegreeStations
SENÃO
    DEVOLVE nulo
FIMSE

FIM

```

### Contagem de Operações no Pior Caso

Sejam:
- `n` o número de estações
- `m` o número de linhas

#### findEulerianPath:
- Loop externo percorre `n` estações → `O(n)`
- Para cada estação, percorre `m` linhas → `O(m)` por estação
- Verificação de conexão por linha → até 2 comparações
- Inserção em lista e verificação de paridade → `O(1)`

#### Total:
- Custo total: `O(n × m)`

### Complexidade Pior Caso

**O(n × m)**

---

## Conclusão

| Algoritmo                           | Complexidade (Pior Caso) |
|-------------------------------------|--------------------------|
| findEulerianPath (grau de vértices) | O(n × m)                 |

> O algoritmo de verificação de percurso Euleriano é eficiente, mas pode ser otimizado utilizando um mapa de graus previamente calculado ou representação com listas de adjacência.
