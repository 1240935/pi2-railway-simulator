# US14 - Análise de Complexidade (Pior Caso)

---

## Verificação de Percurso de Manutenção (Euleriano)

### Pseudocódigo (Route.findEulerianPath)

```
procedure findEulerianPath(graph)
    oddDegreeStations := []

    para cada estação em graph.stations:
        degree := 0
        para cada linha em graph.lines:
            se linha conecta à estação:
                degree := degree + 1

        se degree é ímpar:
            adicionar estação a oddDegreeStations

    se oddDegreeStations está vazia:
        return graph.stations
    senão se oddDegreeStations.size == 2:
        return oddDegreeStations
    senão:
        return null
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
