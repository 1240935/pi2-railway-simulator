
# US13 - Análise de Complexidade (Pior Caso)

---

## Verificação de Viagem de Comboio

### Pseudocódigo (PathVerifier.verifyPath)

```
1.  INICIO
2.  ED: graph, locoType, stationType
3.  ED: stations, candidateStations, s, start

4.  stations ← lista de estações do grafo
5.  candidateStations ← lista vazia

6.  REPETIR PARA cada s em stations
7.      SE s.tipo == stationType ENTÃO
8.          adicionar s a candidateStations
9.      FIMSE
10. FIMREPETIR

11. REPETIR PARA cada start em candidateStations
12.     SE bfs(graph, locoType, stationType, start, candidateStations) ENTÃO
13.         ESCREVER true
14.         PARAR
15.     FIMSE
16. FIMREPETIR

17. ESCREVER false
18. FIM

```

### Pseudocódigo (PathVerifier.bfs)

```
1.  INICIO
2.  ED: graph, locoType, targetStationType, start, candidates
3.  ED: queue, visited, current, head, connections, line, neighbor

4.  queue ← [start]
5.  visited ← [start]
6.  head ← 0

7.  REPETIR ENQUANTO head < tamanho(queue)
8.      current ← queue[head]
9.      head ← head + 1

10.     SE current ≠ start E current.tipo == targetStationType ENTÃO
11.         ESCREVER true
12.         PARAR
13.     FIMSE

14.     connections ← conexões do grafo a partir de current

15.     REPETIR PARA cada line em connections
16.         SE line.start == current ENTÃO
17.             neighbor ← line.end
18.         SENÃO
19.             neighbor ← line.start
20.         FIMSE

21.         SE neighbor está em visited ENTÃO
22.             CONTINUAR
23.         FIMSE

24.         SE locoType == ELECTRIC E line não é eletrificada ENTÃO
25.             CONTINUAR
26.         FIMSE

27.         adicionar neighbor a visited
28.         adicionar neighbor a queue
29.     FIMREPETIR
30. FIMREPETIR

31. ESCREVER false
32. FIM

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

### Tabela:

| **Linhas** | **Passos verifyPath**                        | **Passos bfs**                                             |
|------------|----------------------------------------------|------------------------------------------------------------|
| 04         | 1A (cópia da lista de estações)              | –                                                          |
| 05         | 1A (inicialização de lista vazia)            | –                                                          |
| 06–07      | nC (comparações do tipo de estação)          | –                                                          |
| 08         | até kA (adicionar até k estações candidatas) | –                                                          |
| 11         | até k chamadas a `bfs(...)`                  | –                                                          |
| 12         | até kR (interrompe ao encontrar caminho)     | –                                                          |
| 17         | 1R (se nenhum caminho encontrado)            | –                                                          |
| -          | -                                            | -                                                          |
| 04–05      | –                                            | 2A (inicialização de `queue` e `visited`)                  |
| 06         | –                                            | 1A (`head ← 0`)                                            |
| 07         | –                                            | até nC (condição do ciclo `head < queue.length`)           |
| 08         | –                                            | nA (`current ← queue[head]`)                               |
| 09         | –                                            | nI (`head ← head + 1`)                                     |
| 10         | –                                            | até nC (verificação de tipo ≠ start e tipo correto)        |
| 11–12      | –                                            | 1R (se condição satisfeita, termina)                       |
| 14         | –                                            | mA (acesso às conexões: graus totais ≤ 2m)                 |
| 15–29      | –                                            | por aresta: até 3mC (comparações start, visited, locoType) |
| 27–28      | –                                            | até 2mA (inserções em `visited` e `queue`)                 |


### Cálculo da Complexidade

#### Etapas:

1. **Filtrar Estações por Tipo**
    - `for s in stations`: percorre `n` estações → `O(n)`
    - Cria uma lista `candidateStations` com até `k ≤ n` estações

2. **Chamar BFS para cada estação candidata**
    - No pior caso, nenhuma das chamadas de `bfs(...)` encontra um caminho ⇒ todas as `k` chamadas são feitas
    - Cada chamada a `bfs` percorre no máximo `n` nós e `m` arestas ⇒ `O(n + m)` por chamada

#### Expressão completa:

```
verifyPath:
  O(n)            ← filtrar estações
+ k × O(n + m)    ← até k chamadas a bfs
```

Sabendo que `k ≤ n`, substituímos:

```
O(n) + n × O(n + m) = O(n + n² + n·m)
```

Descartando termos menos significativos:

```
O(n + n² + n·m) → O(n(n + m))
```

### Complexidade Pior Caso

**O(n(n + m))**

---

## Conclusão

| **Algoritmo**          | **Complexidade (Pior Caso)** |
|------------------------|------------------------------|
| `verifyPath` (com BFS) | `O(n(n + m))`                |
| `bfs`                  | `O(n + m)`                   |


