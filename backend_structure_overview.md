# Estrutura e Arquitetura do Backend ICOL

Este documento fornece uma visão geral abrangente da estrutura do projeto `icolbackend`, explicando o propósito de cada diretório e dos principais arquivos dentro da aplicação Spring Boot.

## Arquitetura Moderna e Padrões Implementados

A aplicação evoluiu para adotar arquiteturas mais resilientes:
- **Padrão DTO (Data Transfer Object)**: Separação total entre a Camada de Banco de Dados (Entidades) e a Camada de Rede (API JSON), prevenindo vazamento de dados sensíveis e erros de recursão infinita no Jackson.
- **Global Exception Handling**: Tratamento centralizado de erros de validação (`@Valid`) e de Lógica de Negócio (`RequisicaoInvalida`, `RequisicaoNaoEncontrada`).
- **Soft Delete**: Os registros nunca são deletados fisicamente do banco de dados para proteger o histórico acadêmico. Em vez de `DELETE`, usa-se uma coluna `ativo = false` controlada invisivelmente pela API.
- **Blindagem de Regras de Negócio**: A camada `@Service` foi refatorada para impedir anomalias de capacidade de turma, matrículas duplicadas e vínculos irregulares de perfil de usuário.

## Árvore do Projeto (File Tree)

```text
icolbackend/
├── .git/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/br/org/icol/icolbackend/
│   │   │   ├── config/
│   │   │   │   ├── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   ├── dto/                 <-- (Novo) Data Transfer Objects
│   │   │   ├── enums/
│   │   │   ├── exception/           <-- (Atualizado) GlobalExceptionHandler
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── IcolsystemApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── target/
├── .gitattributes
├── .gitignore
├── compile_output.log
├── HELP.md
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Diretório Raiz (`IcolSystem/IcolBackend/icolbackend`)

*   **`.mvn/`**: Contém os arquivos de configuração do Maven Wrapper.
*   **`src/`**: Diretório principal contendo todo o código-fonte da aplicação, testes e arquivos de recursos.
*   **`target/`**: O diretório de saída onde o Maven armazena os arquivos compilados `.class` e o executável final `.jar`.
*   **`pom.xml`**: *Project Object Model*. Define dependências (Spring Boot, Spring Data JPA, Lombok, PostgreSQL), plugins e perfis.
*   **`mvnw` / `mvnw.cmd`**: Scripts executáveis do Maven Wrapper.

## Código Fonte (`src/main/java/com/br/org/icol/icolbackend`)

A aplicação segue o padrão de Arquitetura em Camadas (Layered Architecture).

### `config/`
**Propósito**: Armazena as classes de configuração globais.
*   *Arquivos*: `SecurityConfig.java` (CORS e Web Security).

### `controller/`
**Propósito**: A Camada de Apresentação (Presentation Layer). Recebe requisições HTTP, executa a validação de formato (`@Valid`), chama a camada de Service e devolve respostas REST utilizando **ResponseDTOs**.
*   *Arquivos*: Controladores para Alunos, Turmas, Frequências, Docentes, etc.

### `dto/` (Data Transfer Objects)
**Propósito**: Objetos limpos projetados exclusivamente para trafegar informações de entrada (Request) e saída (Response) da API. Garante que as entidades de banco de dados (`model`) não sejam expostas diretamente para a Web.
*   *Arquivos*: `AlunoRequestDTO`, `AlunoResponseDTO`, `TurmaRequestDTO`, etc.

### `enums/`
**Propósito**: Tipos enumerados que ditam conjuntos fixos de estados, garantindo consistência relacional.
*   *Arquivos*: `StatusMatricula`, `StatusPresenca` (Incluindo a lógica de proteção histórica `AULA_CANCELADA`), `StatusTurma`, `TiposUsuario`.

### `exception/`
**Propósito**: Centraliza a lógica de tratamento de erros.
*   *Arquivos*: `GlobalExceptionHandler` intercepta falhas (`MethodArgumentNotValidException`, `RequisicaoNaoEncontrada`, `RequisicaoInvalida`) e formata em respostas padronizadas como o modelo `{ "status": 400, "erro": "Erro de Validação", "mensagem": "..." }`.

### `model/`
**Propósito**: A Camada de Domínio (Entities). Classes mapeadas via JPA/Hibernate para o PostgreSQL.
*   *Destaques*: Estruturação de colunas `ativo` (Boolean) para suportar o Soft Delete nas tabelas primárias (`Alunos`, `Docentes`, `Turmas`, `Usuarios`, `Cursos`).

### `repository/`
**Propósito**: Interfaces do Spring Data JPA. Além dos CRUDs básicos, possui JPQL (`@Query`) desenhados sob medida para filtrar `a.ativo = true` e checar exclusividades lógicas (ex: `existsByAlunoMatrIdAndTurmaMatrId`).

### `service/`
**Propósito**: O cérebro do sistema. Esta camada contém todas as travas e validações essenciais de regra de negócio antes de tocar no banco de dados.
*   *Destaques das Responsabilidades do Service*:
    - Conversão DTO-Entidade.
    - Impedir lotação máxima e matrículas duplicadas (`MatriculaIcolService`).
    - Exigir que um usuário não possa ser Aluno e Docente simultaneamente (`AlunosIcolService`).
    - Gerenciar as chamadas de inativação (Soft Delete) protegendo o modelo de exclusão irreversível.

---
*Nota: Na pasta `IcolSystem/IcolBackend/` encontra-se o arquivo `teste.http`. Trata-se de um script de teste E2E ponta-a-ponta arquitetado para executar e testar todo o fluxo CRUD cronológico com integridade referencial.*
