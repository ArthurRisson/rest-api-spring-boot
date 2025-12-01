# Atitus Maps API (Spring Boot) ☕️

Esta é uma API RESTful desenvolvida em **Java** com **Spring Boot**, que serve como backend para a aplicação **Atitus Maps**. O sistema gere o registo de utilizadores e a persistência de pontos geográficos, garantindo segurança e integridade dos dados.

## 🚀 Funcionalidades

* **Autenticação e Segurança:**
    * Login e Registo de utilizadores com encriptação de senha (BCrypt).
    * Autenticação via **Tokens JWT (JSON Web Tokens)**.
    * Filtros de segurança personalizados com Spring Security.
* **Gestão de Pontos (Geolocalização):**
    * CRUD completo (Criar, Ler, Atualizar, Apagar) de pontos no mapa.
    * Associação automática de pontos ao utilizador autenticado.
    * Validação de propriedade (apenas o dono pode editar/apagar o seu ponto).
* **Tratamento de Exceções:** Respostas de erro claras e padronizadas.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** [Java 21](https://www.oracle.com/java/)
* **Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot)
* **Segurança:** Spring Security & JWT
* **Base de Dados:** [PostgreSQL](https://www.postgresql.org/)
* **ORM:** Spring Data JPA / Hibernate
* **Build Tool:** Maven

## ⚙️ Configuração e Execução

### Pré-requisitos
* Java JDK 21 instalado.
* PostgreSQL instalado e a correr.
* Maven (opcional, pois o projeto inclui o `mvnw`).

### Passos
1.  **Clonar o repositório:**
    ```bash
    git clone [https://github.com/ArthurRisson/rest-api-spring-boot.git](https://github.com/ArthurRisson/rest-api-spring-boot.git)
    cd rest-api-spring-boot/api-example
    ```

2.  **Configurar a Base de Dados:**
    Abre o ficheiro `src/main/resources/application.properties` e ajusta as credenciais do teu banco de dados PostgreSQL:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/db_api_example
    spring.datasource.username=teu_usuario
    spring.datasource.password=tua_senha
    ```

3.  **Executar a aplicação:**
    ```bash
    ./mvnw spring-boot:run
    ```

A API ficará disponível em `http://localhost:8081`.

## 🔌 Endpoints Principais

| Método | Rota | Descrição | Autenticação |
| :--- | :--- | :--- | :--- |
| `POST` | `/auth/signup` | Registo de novo utilizador | Não |
| `POST` | `/auth/signin` | Login e geração de Token JWT | Não |
| `GET` | `/ws/point` | Lista os pontos do utilizador logado | Sim |
| `POST` | `/ws/point` | Cria um novo ponto | Sim |
| `PUT` | `/ws/point/{id}` | Atualiza um ponto existente | Sim |
| `DELETE`| `/ws/point/{id}` | Remove um ponto | Sim |

## 👤 Autor

Desenvolvido por **Arthur Risson**.
