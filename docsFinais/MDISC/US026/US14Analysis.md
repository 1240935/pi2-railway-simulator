# US14 - Análise de Complexidade (Pior Caso)

---

## Verificação de Percurso de Manutenção (Euleriano)

### Pseudocódigo (Route.findEulerianPath)

```
01. INICIO  
02. ED: graph, stations, lines, oddDegreeStations, current, degree, line  

03. stations ← obter estações do grafo  
04. lines ← obter linhas do grafo  
05. oddDegreeStations ← lista vazia  

06. REPETIR PARA cada current em stations  
07.     degree ← 0  

08.     REPETIR PARA cada line em lines  
09.         SE line.start == current OU line.end == current ENTÃO  
10.             degree ← degree + 1  
11.         FIMSE  
12.    FIMREPETIR  

13.     SE degree é ímpar ENTÃO  
14.         adicionar current a oddDegreeStations  
15.     FIMSE  
16. FIMREPETIR  

17. SE tamanho(oddDegreeStations) == 0 ENTÃO  
18.     DEVOLVE stations  
19. SENÃO SE tamanho(oddDegreeStations) == 2 ENTÃO  
20.     DEVOLVE oddDegreeStations  
21. SENÃO  
22.     DEVOLVE nulo  
23. FIMSE  

24. FIM


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

### Tabela

| **Linhas** | **Passos findEulerianPath**                                            |
|------------|------------------------------------------------------------------------|
| 04         | 1A (obter lista de estações)                                           |
| 05         | 1A (obter lista de linhas)                                             |
| 06         | 1A (inicialização de lista `oddDegreeStations`)                        |
| 08         | nC (loop externo: percorre todas as `n` estações)                      |
| 09         | nA (inicialização do grau a zero para cada estação)                    |
| 11–13      | n × mC (comparações: `line.start == current` ou `line.end == current`) |
| 12         | até 2mC por iteração (duas comparações por linha)                      |
| 13         | até mI (incrementos do grau da estação)                                |
| 16         | nC (verificação de paridade do grau — é ímpar?)                        |
| 17         | até nA (inserções na lista de estações com grau ímpar)                 |
| 20         | 1C (tamanho da lista `oddDegreeStations`)                              |
| 21         | 1R (retorna lista de todas as estações se grafo é Euleriano)           |
| 22         | 1C (verifica se há 2 vértices com grau ímpar)                          |
| 23         | 1R (retorna esses dois vértices para o caminho semi-Euleriano)         |
| 24         | 1R (senão, devolve nulo: grafo não admite caminho Euleriano)           |

### Cálculo da Complexidade

#### Etapas:

1. **Obter listas de estações e linhas**
    - `stations ← obter estações do grafo`: `O(n)`
    - `lines ← obter linhas do grafo`: `O(m)`

2. **Contar o grau de cada estação**
    - Loop externo percorre `n` estações
    - Para cada estação:
        - Loop interno percorre `m` linhas
        - Verifica se a linha liga à estação atual (2 comparações por linha)
        - Incrementa grau se for o caso

    - Total do passo: `O(n × m)`

3. **Verificar se o grafo é Euleriano**
    - Verifica paridade do grau de cada estação: `O(n)`
    - Dependendo do número de estações com grau ímpar:
        - 0 → grafo Euleriano (retorna todas as estações)
        - 2 → semi-Euleriano (retorna as duas estações com grau ímpar)
        - outro → retorna `null`

    - Total deste passo: `O(1)` ou no máximo `O(n)`

#### Expressão completa:

```
findEulerianPath:
O(n) + O(m)

O(n × m) ← contar grau de cada estação

O(n) ← verificar grau ímpar
```

Descartando os termos menos significativos:

```
O(n + m + n × m) → O(n × m)
```

### Complexidade Pior Caso

**O(n × m)**

---

## Conclusão

| Algoritmo                           | Complexidade (Pior Caso) |
|-------------------------------------|--------------------------|
| findEulerianPath (grau de vértices) | O(n × m)                 |

