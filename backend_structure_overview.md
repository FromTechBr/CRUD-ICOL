# Estrutura e Arquitetura do Backend ICOL

Este documento fornece uma visão geral abrangente da estrutura do projeto `icolbackend`, explicando o propósito de cada diretório e dos principais arquivos dentro da aplicação Spring Boot.

## Árvore do Projeto (File Tree)

```text
icolbackend/
├── .git/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/br/org/icol/icolbackend/
│   │   │   ├── config/
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── controller/
│   │   │   │   ├── AlunosIcolController.java
│   │   │   │   ├── AvisosMuralIcolController.java
│   │   │   │   ├── CursosIcolController.java
│   │   │   │   ├── DocentesIcolController.java
│   │   │   │   ├── FrequenciaIcolController.java
│   │   │   │   ├── MatriculaIcolController.java
│   │   │   │   ├── TurmasIcolController.java
│   │   │   │   └── UsuariosIcolController.java
│   │   │   ├── enums/
│   │   │   │   ├── StatusMatricula.java
│   │   │   │   ├── StatusPresenca.java
│   │   │   │   ├── StatusTurma.java
│   │   │   │   ├── TiposCursos.java
│   │   │   │   └── TiposUsuario.java
│   │   │   ├── exception/
│   │   │   │   └── RequisicaoNaoEncontrada.java
│   │   │   ├── model/
│   │   │   │   ├── AlunosIcol.java
│   │   │   │   ├── AvisosMuralIcol.java
│   │   │   │   ├── CursosIcol.java
│   │   │   │   ├── DocentesIcol.java
│   │   │   │   ├── FrequenciaIcol.java
│   │   │   │   ├── MatriculaIcol.java
│   │   │   │   ├── TurmasIcol.java
│   │   │   │   └── UsuariosIcol.java
│   │   │   ├── repository/
│   │   │   │   ├── AlunosIcolRepositorio.java
│   │   │   │   ├── AvisosMuralIcolRepositorio.java
│   │   │   │   ├── CursosIcolRepositorio.java
│   │   │   │   ├── DocentesIcolRepositorio.java
│   │   │   │   ├── FrequenciaIcolRepositorio.java
│   │   │   │   ├── MatriculaIcolRepositorio.java
│   │   │   │   ├── TurmasIcolRepositorio.java
│   │   │   │   └── UsuariosIcolRepositorio.java
│   │   │   ├── service/
│   │   │   │   ├── AlunosIcolService.java
│   │   │   │   ├── AvisosMuralIcolService.java
│   │   │   │   ├── CursosIcolService.java
│   │   │   │   ├── DocentesIcolService.java
│   │   │   │   ├── FrequenciaIcolService.java
│   │   │   │   ├── MatriculaIcolService.java
│   │   │   │   ├── TurmasIcolService.java
│   │   │   │   └── UsuariosIcolService.java
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

*   **`.mvn/`**: Contém os arquivos de configuração do Maven Wrapper. Isso garante que qualquer pessoa compilando o projeto utilize a mesma versão exata do Maven.
*   **`src/`**: Diretório principal contendo todo o código-fonte da aplicação, testes e arquivos de recursos.
*   **`target/`**: O diretório de saída onde o Maven armazena os arquivos compilados `.class` e o executável final `.jar`.
*   **`pom.xml`**: *Project Object Model* (Modelo de Objeto do Projeto). É o arquivo central de configuração do Maven. Ele define todas as dependências do projeto (Spring Boot, Spring Data JPA, Lombok, drivers de banco de dados, etc.), além de plugins e perfis de compilação.
*   **`mvnw` / `mvnw.cmd`**: Scripts executáveis do Maven Wrapper para Linux/Mac e Windows, respectivamente. Eles permitem compilar e rodar o projeto sem a necessidade de instalar o Maven globalmente na máquina.
*   **`README.md` / `HELP.md`**: Arquivos de documentação padrão gerados contendo instruções sobre como executar e compilar o projeto.
*   **`compile_output.log`**: Arquivo de log gerado por uma compilação anterior do projeto.
*   **`.gitignore` / `.gitattributes`**: Arquivos de configuração do Git que especificam quais pastas e arquivos devem ser ignorados pelo controle de versão.

## Código Fonte (`src/main/java/com/br/org/icol/icolbackend`)

A aplicação segue o padrão de Arquitetura em Camadas (Layered Architecture), muito comum em projetos Spring Boot.

### `config/`
**Propósito**: Armazena as classes de configuração globais da aplicação.
*   **`SecurityConfig.java`**: Contém as configurações de segurança, normalmente definindo o CORS (Cross-Origin Resource Sharing), proteção contra CSRF e regras de autorização para as rotas HTTP.

### `controller/`
**Propósito**: A Camada de Apresentação (Presentation Layer). Contém os controladores REST anotados com `@RestController`. Eles definem os pontos de acesso da API (Rotas), recebem as requisições HTTP (GET, POST, PUT, DELETE), repassam o processamento para a camada de Serviço (Service) e retornam respostas HTTP (em formato JSON) para o cliente.
*   *Arquivos*: `AlunosIcolController.java`, `AvisosMuralIcolController.java`, `CursosIcolController.java`, `DocentesIcolController.java`, `FrequenciaIcolController.java`, `MatriculaIcolController.java`, `TurmasIcolController.java`, `UsuariosIcolController.java`.

### `enums/`
**Propósito**: Contém tipos `Enum` do Java usados para definir conjuntos fixos de constantes. Isso garante segurança de tipagem e consistência de dados em toda a aplicação e no banco de dados.
*   *Arquivos*: `StatusMatricula.java`, `StatusPresenca.java`, `StatusTurma.java`, `TiposCursos.java`, `TiposUsuario.java`.

### `exception/`
**Propósito**: Abriga as classes de exceção customizadas e os manipuladores de erros globais para tratar falhas da API de forma graciosa, retornando mensagens de erro padronizadas.
*   *Arquivos*: `RequisicaoNaoEncontrada.java` (Usada para disparar uma resposta HTTP 404 Not Found quando um recurso solicitado não existe).

### `model/`
**Propósito**: A Camada de Domínio (Domain Layer). Contém as entidades JPA anotadas com `@Entity`. Essas classes são mapeadas diretamente para as tabelas do banco de dados. Elas definem a estrutura, as colunas e os relacionamentos (como `@OneToMany`, `@ManyToOne`) entre tabelas diferentes.
*   *Arquivos*: `AlunosIcol.java`, `AvisosMuralIcol.java`, `CursosIcol.java`, `DocentesIcol.java`, `FrequenciaIcol.java`, `MatriculaIcol.java`, `TurmasIcol.java`, `UsuariosIcol.java`.

### `repository/`
**Propósito**: A Camada de Acesso a Dados (Data Access Layer). Contém interfaces que herdam do Spring Data JPA (como `JpaRepository`). Estas fornecem operações de CRUD integradas e a execução de consultas no banco de dados sem a necessidade de escrever código SQL manualmente.
*   *Arquivos*: `AlunosIcolRepositorio.java`, `AvisosMuralIcolRepositorio.java`, `CursosIcolRepositorio.java`, `DocentesIcolRepositorio.java`, `FrequenciaIcolRepositorio.java`, `MatriculaIcolRepositorio.java`, `TurmasIcolRepositorio.java`, `UsuariosIcolRepositorio.java`.

### `service/`
**Propósito**: A Camada de Regras de Negócio (Business Logic Layer). Contém classes anotadas com `@Service` que orquestram a lógica da aplicação. Os Controladores repassam as requisições para os Serviços, e os Serviços interagem com os Repositórios. Isso separa as rotas HTTP das regras de negócio essenciais.
*   *Arquivos*: `AlunosIcolService.java`, `AvisosMuralIcolService.java`, `CursosIcolService.java`, `DocentesIcolService.java`, `FrequenciaIcolService.java`, `MatriculaIcolService.java`, `TurmasIcolService.java`, `UsuariosIcolService.java`.

---
*Nota: No diretório imediatamente acima de `icolbackend`, há um arquivo chamado `teste.http`. Trata-se de um arquivo de testes para Clientes HTTP (usado por extensões de IDEs, como o REST Client do VS Code) que contém exemplos de requisições, ajudando a documentar e testar manualmente as rotas da API sem depender de ferramentas externas como o Postman.*
