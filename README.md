# 💾 RestManager API

API REST em Java com Spring Boot para gerenciamento completo de restaurantes. Sistema robusto com autenticação, CRUD completo e integração com PostgreSQL.

[![Java](https://img.shields.io/badge/Java-17-red.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.5-green.svg)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Compose-lightblue.svg)](https://docs.docker.com/compose/)
[![Coverage](https://img.shields.io/badge/Coverage-89%25-brightgreen.svg)]()

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura)
- [Funcionalidades](#-funcionalidades)
- [Tecnologias](#-tecnologias)
- [Pré-requisitos](#-pré-requisitos)
- [Instalação e Execução](#-instalação-e-execução)
- [Testes](#-testes)
- [Documentação da API](#-documentação-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Banco de Dados](#-banco-de-dados)
- [Cobertura de Testes](#-cobertura-de-testes)
- [Contribuição](#-contribuição)

---

## 🎯 Visão Geral

O **RestManager** é uma API REST desenvolvida em Java 17 com Spring Boot 3.4.5, projetada para o gerenciamento completo de restaurantes. O sistema oferece funcionalidades abrangentes para cadastro de usuários, restaurantes, itens de cardápio e autenticação segura.

### Principais Características:
- ✅ **Arquitetura Limpa** - Separação clara de responsabilidades
- ✅ **Segurança Robusta** - Autenticação com BCrypt
- ✅ **Documentação Automática** - OpenAPI/Swagger integrado
- ✅ **Testes Abrangentes** - 89% de cobertura de código
- ✅ **Containerização** - Docker Compose para fácil deploy
- ✅ **Validação Completa** - Validação de entrada em todos os endpoints

---

## 🏗 Arquitetura

O projeto segue os princípios da **Arquitetura Limpa** e **Clean Code**, organizando o código em camadas bem definidas:

```
┌─────────────────────────────────────────────────┐
│                 Web Layer                       │
│            (Controllers REST)                   │
├─────────────────────────────────────────────────┤
│              Application Layer                  │
│           (UseCases & Services)                 │
├─────────────────────────────────────────────────┤
│               Domain Layer                      │
│            (Entities & Rules)                   │
├─────────────────────────────────────────────────┤
│            Infrastructure Layer                 │
│         (Database & External APIs)              │
└─────────────────────────────────────────────────┘
```

### Camadas:

- **Web (Controllers)**: Endpoints REST com documentação OpenAPI
- **Application (UseCases/Services)**: Lógica de negócio e orquestração
- **Domain**: Entidades de domínio e regras de negócio
- **Infrastructure**: Persistência de dados e integrações externas

---

## 🚀 Funcionalidades

### 👥 Gestão de Usuários
- Cadastro de clientes e proprietários
- Consulta, atualização e exclusão de usuários
- Validação de dados de entrada
- Endereços completos com validação de CEP

### 🏪 Gestão de Restaurantes
- Cadastro completo de restaurantes
- Associação com proprietários
- Gerenciamento de tipos de cozinha
- Controle de endereços e informações de contato

### 🍕 Gestão de Itens/Cardápio
- Cadastro de itens do cardápio
- Controle de preços e descrições
- Opções de consumo (local/delivery)
- Upload e gerenciamento de fotos

### 🔐 Sistema de Autenticação
- Login seguro com BCrypt
- Diferentes tipos de usuário (Cliente/Proprietário)
- Validação de credenciais
- Tratamento de erros de autenticação

### 📊 Funcionalidades Técnicas
- Documentação automática com Swagger
- Validação completa de entrada
- Tratamento centralizado de exceções
- Logging estruturado
- Mapeamento automático DTOs/Entities

---

## 🛠 Tecnologias

### Backend
- **Java 17** - Linguagem de programação
- **Spring Boot 3.4.5** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **Spring Security** - Segurança e autenticação
- **Spring Validation** - Validação de dados

### Banco de Dados
- **PostgreSQL 15** - Banco de dados relacional
- **Flyway** - Migrations de banco

### Documentação e Testes
- **SpringDoc OpenAPI** - Documentação automática
- **JUnit 5** - Framework de testes
- **Mockito** - Mocks para testes unitários
- **JaCoCo** - Cobertura de código

### Ferramentas de Desenvolvimento
- **Maven** - Gerenciamento de dependências
- **Lombok** - Redução de boilerplate
- **MapStruct** - Mapeamento entre objetos
- **Docker & Docker Compose** - Containerização

---

## ✨ Pré-requisitos

- [Docker](https://www.docker.com/products/docker-desktop) (v20.10+)
- [Docker Compose](https://docs.docker.com/compose/) (v2.0+)
- [Git](https://git-scm.com/) (v2.30+)
- **Opcional**: [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) + [Maven 3.8+](https://maven.apache.org/) para desenvolvimento local

---

## 📦 Instalação e Execução

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/tech-challenge-api-restaurante.git
cd tech-challenge-api-restaurante
```

### 2. Execute com Docker Compose

```bash
# Suba todos os serviços
docker-compose up --build

# Ou execute em background
docker-compose up --build -d
```

### 3. Verifique se os serviços estão rodando

```bash
# Verificar logs
docker-compose logs -f restmanager

# Verificar status dos containers
docker-compose ps
```

### 4. Acesse a aplicação

- **API Base URL**: http://localhost:8081
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8081/v3/api-docs

### Desenvolvimento Local (Opcional)

```bash
# Entre no diretório do projeto
cd restmanager

# Compile o projeto
./mvnw clean compile

# Execute os testes
./mvnw test

# Execute a aplicação
./mvnw spring-boot:run
```

---

## 🧪 Testes

### Executar Testes

```bash
# Todos os testes
cd restmanager
./mvnw test

# Testes com relatório de cobertura
./mvnw test jacoco:report

# Ver relatório de cobertura
open target/site/jacoco/index.html
```

### Tipos de Testes

- **Testes Unitários**: Testam classes isoladamente com mocks
- **Testes de Integração**: Testam integração entre camadas
- **Testes de Repository**: Testam persistência de dados
- **Testes de Controller**: Testam endpoints REST

### Estrutura de Testes

```
src/test/java/
├── application/
│   ├── services/     # Testes de serviços
│   └── usecases/     # Testes de casos de uso
├── infrastructure/
│   ├── adapters/
│   │   ├── web/          # Testes de controllers
│   │   ├── persistence/  # Testes de repositories
│   │   └── config/       # Testes de configuração
├── integration/      # Testes de integração
└── RestManagerApplicationTest.java
```

---

## 📖 Documentação da API

### Swagger UI
Acesse [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) para documentação interativa.

### Principais Endpoints

#### Tipos de Usuário
```http
GET /v1/tipos-usuario - Lista tipos de usuário disponíveis
```

#### Usuários
```http
POST /v1/usuarios        - Criar usuário
GET  /v1/usuarios/{id}   - Buscar usuário por ID
PUT  /v1/usuarios/{id}   - Atualizar usuário
DELETE /v1/usuarios/{id} - Excluir usuário
GET  /v1/usuarios        - Listar usuários
```

#### Restaurantes
```http
POST /v1/restaurante        - Criar restaurante
GET  /v1/restaurante/{id}   - Buscar restaurante por ID
PUT  /v1/restaurante/{id}   - Atualizar restaurante
DELETE /v1/restaurante/{id} - Excluir restaurante
GET  /v1/restaurante        - Listar restaurantes
```

#### Itens/Cardápio
```http
POST /v1/itens        - Criar item
GET  /v1/itens/{id}   - Buscar item por ID
PUT  /v1/itens/{id}   - Atualizar item
DELETE /v1/itens/{id} - Excluir item
GET  /v1/itens        - Listar itens
```

#### Autenticação
```http
POST /v1/autenticacao/login - Fazer login
```

#### Associações
```http
POST /v1/usuario-restaurante/{usuarioId}/{restauranteId} - Associar usuário a restaurante
DELETE /v1/usuario-restaurante/{usuarioId}/{restauranteId} - Desassociar usuário de restaurante
```

### Exemplos de Payload

#### Criar Usuário
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joao.silva",
  "senha": "senha123",
  "tipoUsuario": "CLIENTE",
  "endereco": {
    "rua": "Rua das Flores",
    "numero": "123",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "cep": "01234-567",
    "estado": "SP",
    "pais": "Brasil"
  }
}
```

#### Fazer Login
```json
{
  "login": "joao.silva",
  "senha": "senha123"
}
```

---

## 📁 Estrutura do Projeto

```
restmanager/
├── src/
│   ├── main/
│   │   ├── java/br/com/posfiap/restmanager/
│   │   │   ├── application/
│   │   │   │   ├── services/        # Serviços de aplicação
│   │   │   │   └── usecases/        # Casos de uso
│   │   │   ├── domain/
│   │   │   │   └── enums/           # Enumerações do domínio
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── error/               # Tratamento de exceções
│   │   │   ├── infrastructure/
│   │   │   │   └── adapters/
│   │   │   │       ├── config/      # Configurações
│   │   │   │       ├── persistence/ # Repositórios
│   │   │   │       └── web/         # Controllers REST
│   │   │   ├── mapper/              # Mapeadores MapStruct
│   │   │   ├── service/             # Serviços de negócio
│   │   │   └── util/                # Utilitários
│   │   └── resources/
│   │       ├── application.yml      # Configurações da aplicação
│   │       └── application-test.yml # Configurações de teste
│   └── test/                        # Testes automatizados
├── target/                          # Artefatos de build
├── docker-compose.yml               # Definição dos containers
├── Dockerfile                       # Imagem Docker da aplicação
├── pom.xml                         # Dependências Maven
└── README.md                       # Esta documentação
```

---

## 📈 Banco de Dados

### Configuração

- **Database**: restdb
- **User**: restuser  
- **Password**: restpass
- **Host**: localhost (ou postgres no Docker)
- **Porta**: 5432

### Conexão Local

```bash
# Via psql
psql -h localhost -p 5432 -U restuser -d restdb

# Via Docker
docker-compose exec postgres psql -U restuser -d restdb
```

### Schema Principal

```sql
-- Principais tabelas
tb_usuario           -- Usuários do sistema
tb_restaurante       -- Restaurantes cadastrados
tb_item              -- Itens do cardápio
tb_usuario_restaurante -- Associação N:N usuários-restaurantes
```

### Tipos de Usuário

- **CLIENTE**: Usuários finais que podem fazer pedidos
- **PROPRIETARIO**: Donos de restaurantes com permissões administrativas

---

## 📊 Cobertura de Testes

### Estatísticas Atuais

- **Cobertura Total**: 89.18% (desconsiderando código gerado)
- **Linhas Testadas**: 637 de 930 (68.5%)
- **Métodos Testados**: 161 de 188 (85.6%)
- **Classes Testadas**: 34 de 34 (100%)

### Cobertura por Camada

| Camada | Cobertura | Status |
|--------|-----------|--------|
| Controllers (Web) | 100% | ✅ Completo |
| UseCases (Application) | 100% | ✅ Completo |
| Services (Application) | 99% | ✅ Completo |
| Repositories (Infrastructure) | 100% | ✅ Completo |
| Configuration | 100% | ✅ Completo |
| Error Handling | 100% | ✅ Completo |
| Domain | 100% | ✅ Completo |
| Utils | 100% | ✅ Completo |

### Exclusões da Cobertura

- **MapStruct Generated Classes**: Classes geradas automaticamente (ItemMapperImpl, RestauranteMapperImpl, UsuarioMapperImpl)
- **DTOs e Records**: Objetos de transferência de dados sem lógica
- **Main Application Class**: Classe de inicialização Spring Boot

### Visualizar Relatório

```bash
# Gerar relatório
cd restmanager
mvnw test jacoco:report

# Abrir no navegador
open target/site/jacoco/index.html
```

---

## ♻ Comandos Úteis

### Docker

```bash
# Parar containers
docker-compose down

# Parar e remover volumes
docker-compose down -v

# Rebuild containers
docker-compose up --build --force-recreate

# Ver logs
docker-compose logs -f restmanager
docker-compose logs -f postgres
```

### Maven

```bash
# Limpar e compilar
./mvnw clean compile

# Executar testes
./mvnw test

# Pular testes
./mvnw install -DskipTests

# Executar aplicação local
./mvnw spring-boot:run
```

### Banco de Dados

```bash
# Conectar ao PostgreSQL
docker-compose exec postgres psql -U restuser -d restdb

# Backup do banco
docker-compose exec postgres pg_dump -U restuser restdb > backup.sql

# Restaurar backup
docker-compose exec -T postgres psql -U restuser -d restdb < backup.sql
```

---

## 🤝 Contribuição

### Como Contribuir

1. **Fork** do projeto
2. **Clone** seu fork
3. **Crie** uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
4. **Commit** suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
5. **Push** para a branch (`git push origin feature/nova-funcionalidade`)
6. **Abra** um Pull Request

### Padrões de Desenvolvimento

- **Clean Code**: Código limpo e bem documentado
- **SOLID Principles**: Princípios de design orientado a objetos
- **Clean Architecture**: Separação clara de responsabilidades
- **TDD**: Desenvolvimento orientado por testes quando possível
- **Conventional Commits**: Mensagens de commit padronizadas

### Checklist para Pull Requests

- [ ] Código segue os padrões do projeto
- [ ] Testes foram adicionados/atualizados
- [ ] Cobertura de testes mantida acima de 80%
- [ ] Documentação foi atualizada se necessário
- [ ] Build passa sem erros
- [ ] Não há warnings de segurança

---

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido com ❤️ para o Tech Challenge FIAP

---