# CEP Tracker

---

## **Descrição da Aplicação**
A aplicação **CEP Tracker** permite buscar informações de CEPs, armazená-las no banco de dados para futuras consultas e auditar as buscas realizadas. A aplicação também possui endpoints de login e cadastro para autenticação e gerenciamento de usuários.

---

## **Fluxo Principal**

  ![Container](docs/fluxograma.png)


## **Funcionalidades Principais**

### **1. Endpoints de Autenticação**
- **POST /login**
    - Autentica um usuário existente.
    - Requer as credenciais do usuário (usuário e senha).

- **POST /register**
    - Permite o cadastro de novos usuários.
    - Requer informações do usuário, como nome, e-mail e senha.

### **2. Busca de CEP**
- **GET /api/zip-code/{zipcode}**
    - Busca informações detalhadas sobre o CEP fornecido.
    - Caso o CEP já tenha sido consultado anteriormente, as informações são recuperadas do banco de dados.
    - Se o CEP não for encontrado no banco, a aplicação faz uma consulta a uma API externa (`http://localhost:8080/{zipcode}`) para obter os dados.
    - Salva o resultado da busca na tabela `zip_code_audit` para auditoria.
    - **Permissão necessária:** Usuários com a role `USER`.

### **3. Auditoria de Consultas de CEP**
- **GET /api/zip-code/audit**
    - Retorna o histórico de consultas realizadas na tabela `zip_code_audit`.
    - Exibe informações como o CEP consultado, os dados retornados e o usuário que realizou a consulta.
    - **Permissão necessária:** Usuários com a role `ADMIN`.

---

## **Estrutura da Aplicação**

### **1. Entidades Principais**

- **ZipCode**
    - Representa as informações detalhadas de um CEP.

- **User**
    - Registra os usuários da aplicação.

- **Role**
    - Registra as permissões dos usuários.

### **2. Camadas**
- **Controller:** Gerencia os endpoints e responde às requisições HTTP.
- **Service:** Contém a lógica de negócios para as operações da aplicação.
- **Repository:** Realiza as interações com o banco de dados.

---

## Tecnologias Utilizadas no Projeto

O projeto utiliza diversas tecnologias para construção de APIs RESTful, segurança, banco de dados, monitoramento e testes. Abaixo estão listadas as principais tecnologias utilizadas:

### 1. Spring Boot
- **Dependências**:
  - `spring-boot-starter-web`: Para criar APIs RESTful com Spring MVC.
  - `spring-boot-starter-data-jpa`: Para integração com bancos de dados usando JPA.
  - `spring-boot-starter-security`: Para adicionar segurança ao aplicativo.
  - `spring-boot-starter-test`: Para testes de unidade e integração.
  - `spring-boot-starter-webflux`: Para suporte a reatividade com WebFlux.
  - `spring-boot-starter-actuator`: Para monitoramento e métricas da aplicação.

- **Descrição**: O Spring Boot é a estrutura principal para criar e configurar a aplicação, facilitando o desenvolvimento e fornecendo ferramentas para iniciar um projeto rapidamente com configurações automáticas.

### 2. PostgreSQL
- **Dependência**:
  - `postgresql`: Driver JDBC para o PostgreSQL.

- **Descrição**: Banco de dados relacional utilizado para persistência de dados da aplicação.

### 3. Hibernate
- **Dependências**:
  - `spring-boot-starter-data-jpa`: Para integração com JPA (Java Persistence API).
  - `hibernate-validator`: Para validações de dados.

- **Descrição**: O Hibernate é usado como implementador de JPA, facilitando a interação com o banco de dados relacional e fornecendo mapeamento objeto-relacional (ORM).

### 4. Segurança e Autenticação
- **Dependências**:
  - `spring-boot-starter-security`: Para adicionar autenticação e autorização à aplicação.
  - `jjwt-api`, `jjwt-impl`, `jjwt-jackson`: Para trabalhar com JWT (JSON Web Tokens), usado em autenticação e autorização.

- **Descrição**: Tecnologias de segurança para controle de acesso e autenticação com tokens JWT.

### 5. Swagger (OpenAPI)
- **Dependência**:
  - `springdoc-openapi-starter-webmvc-ui`: Para documentação automática da API RESTful com Swagger.

- **Descrição**: Usado para gerar e exibir a documentação da API de forma interativa para desenvolvedores.

### 6. Lombok
- **Dependência**:
  - `lombok`: Para reduzir o boilerplate de código, gerando automaticamente métodos getters, setters, construtores, etc.

- **Descrição**: Uma biblioteca que automatiza a criação de métodos comuns e facilita a legibilidade do código.

### 7. MapStruct
- **Dependências**:
  - `mapstruct`: Para mapeamento eficiente de objetos.

- **Descrição**: Usado para mapear dados entre diferentes objetos, por exemplo, DTOs para entidades, sem a necessidade de escrever código repetitivo.

### 8. Datadog
- **Dependências**:
  - `dd-trace-api` e `dd-java-agent`: Para monitoramento e tracing distribuído usando Datadog.
  - `micrometer-registry-datadog`: Para integrar o Micrometer com o Datadog, permitindo a coleta de métricas.

- **Descrição**: Tecnologias de monitoramento para observabilidade e rastreamento de métricas e desempenho da aplicação.

### 9. Testes
- **Dependência**:
  - `spring-boot-starter-test`: Para realizar testes de unidade e integração com Spring Test.

- **Descrição**: Ferramenta para garantir a qualidade e integridade do código da aplicação com testes automatizados.

### 10. Checkstyle
- **Dependência**:
  - `maven-checkstyle-plugin`: Para aplicar regras de estilo de código e garantir a consistência e a qualidade do código.

- **Descrição**: Usado para verificação de conformidade de estilo de código (conformidade com convenções de código).


## **Instalação e Execução**

### **Requisitos**
- Docker e docker-compose

### **Passos para Configuração**
1. git clone https://github.com/IgorBavand/ceptracker
2. docker build -t ceptracker-api .
3. docker-compose up -d

