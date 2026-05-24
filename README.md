# ICOL System - Backend API

> Sistema de gestão interna para a ONG ICOL (Instituto de Capacitação e Orientação Livre), focado no gerenciamento de cursos, alunos, turmas e controle de frequência.

## 📌 Sobre o Projeto

Este projeto é o Backend da solução desenvolvida para a **ONG ICOL**, uma instituição que oferece cursos gratuitos de capacitação. O objetivo do sistema é modernizar a gestão acadêmica da ONG, substituindo controles manuais por uma plataforma centralizada e segura.

O sistema foi desenvolvido seguindo a arquitetura **MVC** em camadas, utilizando **Spring Boot** para garantir robustez, escalabilidade e segurança.

## 🚀 Tecnologias Utilizadas

* **Java 17/21** - Linguagem de programação.
* **Spring Boot 3** - Framework principal.
* **Spring Data JPA (Hibernate)** - Persistência e mapeamento objeto-relacional (ORM).
* **Spring Security** - Autenticação e Criptografia de senhas (BCrypt).
* **PostgreSQL** - Banco de dados relacional (Produção).
* **H2 Database** - Banco de dados em memória (Desenvolvimento/Testes).
* **Lombok** - Redução de código boilerplate.
* **Maven** - Gerenciador de dependências.

## 🏗️ Arquitetura e Design

O projeto segue uma arquitetura em camadas (Layered Architecture) para garantir a separação de responsabilidades:

1.  **Model (Entities):** Representação das tabelas do banco de dados (`usuarios_icol`, `alunos_icol`, `cursos_icol`, etc.).
2.  **Repository:** Interfaces que estendem `JpaRepository` para comunicação direta com o banco.
3.  **Service:** Camada "Coração" do sistema, onde residem todas as **Regras de Negócio** (Validação de CPF, Criptografia, Vínculos, etc.).
4.  **Controller:** Camada REST que expõe os endpoints da API (GET, POST, PUT, DELETE) para o Frontend.
5.  **Config:** Configurações de segurança e Beans.

### Modelo de Dados (Principais Entidades)
* **Usuário:** Controle de acesso e login (Perfis: Aluno, Professor, Coordenadora).
* **Docente:** Dados específicos dos professores.
* **Aluno:** Dados cadastrais e socioeconômicos.
* **Curso:** Catálogo de programas (Aleph, Bem Viver, Livre).
* **Turma:** Oferta de cursos ligando Docentes e Alunos.
* **Matrícula & Frequência:** Controle acadêmico.

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Java JDK 17 ou superior instalado.
* Maven instalado (ou usar o wrapper `mvnw` incluso).
* Git instalado.

### Passo a Passo

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU-USUARIO/icol-backend.git](https://github.com/SEU-USUARIO/icol-backend.git)
    cd icol-backend
    ```

2.  **Configure o Banco de Dados:**
    * Por padrão, o projeto está configurado para usar o **H2 Database** (em memória) para facilitar os testes.
    * Para usar PostgreSQL, altere as configurações no arquivo `src/main/resources/application.properties` (ou `.yml`).

3.  **Execute a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

4.  **Acesse a API:**
    * O servidor iniciará na porta `8080`.
    * URL Base: `http://localhost:8080`
    * Console do Banco H2 (se habilitado): `http://localhost:8080/h2-console`

## 🧪 Testando os Endpoints

Você pode utilizar o **Postman**, **Insomnia** ou a extensão **REST Client** do VS Code.

### Exemplo de Criação de Usuário (POST /usuarios)
```json
{
  "emailUsuario": "admin@icol.org.br",
  "senha": "senhaForte123",
  "tipoUsuario": "COORDENADORA"
}