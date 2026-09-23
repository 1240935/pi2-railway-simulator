# pi2-railway-logistics-simulator

Sistema de simulação ferroviária e gestão logística desenvolvido no âmbito do **Projeto Integrador (2.º semestre)** do curso de **Engenharia Informática (LEI)** do **ISEP — Instituto Superior de Engenharia do Porto**.

A aplicação simula a exploração de uma rede ferroviária num contexto de gestão económica e logística, inspirada em jogos de estratégia ferroviária (*Railroad Tycoon*). Permite criar mapas, definir cenários, construir infraestruturas, gerir frotas de comboios e analisar o desempenho financeiro e operacional da rede.

---

## Equipa (G051)

| N.º Estudante | Nome |
|---|---|
| 1240934 | Francisco Vasconcelos |
| 1240935 | Gabriel Inácio |
| 1240936 | Gonçalo Azevedo |
| 1240941 | Paulo Ferreira |

---

## Funcionalidades

### Modo Editor
- Criação de mapas com cidades e indústrias
- Definição de cenários com restrições temporais, tecnológicas e históricas
- Persistência de mapas e cenários (serialização Java)

### Modo Jogador
- Construção e upgrade de estações (Armazém, Estação, Terminal)
- Compra de locomotivas (vapor, diesel, eléctrica)
- Atribuição de rotas e simulação mensal de cargas
- Relatórios financeiros anuais

### Algoritmos
- Verificação de viabilidade de percursos (BFS)
- Manutenção de linhas ferroviárias (percursos eulerianos)
- Caminhos mais curtos entre estações
- Análise de eficiência algorítmica e estatística de receitas

---

## Stack Tecnológico

| Tecnologia | Utilização |
|---|---|
| Java 15 | Linguagem principal |
| Maven | Build e dependências |
| JUnit 5 | Testes unitários |
| JaCoCo | Cobertura de código |
| JavaFX 21 | Interface gráfica |
| GraphStream | Visualização de grafos |

---

## Pré-requisitos

- **JDK 15+** (recomendado JDK 17 ou superior)
- **Maven 3.6+**
- Para a interface gráfica: módulos JavaFX disponíveis no runtime

---

## Instalação e Execução

### 1. Clonar o repositório

```bash
git clone https://github.com/1240936/pi2-railway-logistics-simulator.git
cd pi2-railway-logistics-simulator
```

### 2. Compilar o projecto

```bash
mvn clean compile
```

### 3. Executar testes

```bash
mvn clean test
```

### 4. Gerar relatório de cobertura

```bash
mvn test jacoco:report
```

O relatório fica disponível em `target/site/jacoco/index.html`.

### 5. Gerar JAR executável

```bash
mvn package
```

### 6. Executar a aplicação

```bash
java -jar target/project-template-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Na consola, escolhe **Login as Editor** ou **Login as Player**. Como Jogador, podes optar pela interface de consola ou pela interface gráfica (JavaFX).

---

## Estrutura do Projecto

```
├── src/
│   ├── main/java/pt/ipp/isep/dei/
│   │   ├── domain/       # Entidades de negócio
│   │   ├── repository/   # Persistência em memória
│   │   ├── controller/   # Lógica de aplicação
│   │   ├── dto/          # Data Transfer Objects
│   │   └── ui/           # Interfaces consola e JavaFX
│   ├── main/resources/fxml/   # Layouts JavaFX
│   └── test/java/        # Testes unitários
├── docsFinais/           # Documentação do projecto (US, análise, design)
├── pom.xml
└── README.md
```

---

## Documentação

A documentação completa do projecto encontra-se em [`docsFinais/`](docsFinais/):

- [Glossário](docsFinais/global-artifacts/01.requirements-engineering/glossary.md)
- [Diagrama de casos de uso](docsFinais/global-artifacts/01.requirements-engineering/use-case-diagram.md)
- [Especificação suplementar (FURPS+)](docsFinais/global-artifacts/01.requirements-engineering/supplementary-specification.md)
- [Modelo de domínio](docsFinais/global-artifacts/02.analysis/analysis.md)
- [Distribuição de tarefas](docsFinais/TeamMembersAndTasks.md)

---

## Arquitectura

O projecto segue uma arquitectura em camadas:

| Camada | Responsabilidade |
|---|---|
| **Domain** | Entidades: `Map`, `Scenario`, `Station`, `Route`, `Simulator`, `GraphC`, etc. |
| **Repository** | Singleton `Repositories` com repositórios especializados |
| **Controller** | Orquestração dos casos de uso |
| **DTO / Mapper** | Transferência de dados entre camadas |
| **UI** | Consola (`*UI`) e JavaFX (`*GUI` + FXML) |

---

## Licença

Projecto académico desenvolvido no ISEP. Consultar a equipa para condições de utilização.
