# 🍽️ RestManager - Sistema de Gestão de Restaurantes

## 📖 Descrição do Projeto

O **RestManager** é um sistema robusto de gestão para restaurantes desenvolvido como parte do Tech Challenge da FIAP. O sistema permite que restaurantes gerenciem eficientemente suas operações, incluindo cadastro de usuários, restaurantes e cardápios, seguindo os princípios da **Clean Architecture**.

Este projeto foi desenvolvido para atender às necessidades de um consórcio de restaurantes que buscavam uma solução única e compartilhada, oferecendo funcionalidades completas de CRUD para:

- **👥 Gestão de Usuários** (Donos de Restaurante e Clientes)
- **🏪 Cadastro de Restaurantes** 
- **📋 Gerenciamento de Cardápios**

---

## 🏗️ Arquitetura

O sistema implementa **Clean Architecture** organizada em 4 camadas principais:

### 🎯 **Domain Layer** (`domain/`)
- **Models**: Entidades de negócio (`Usuario`, `Restaurante`, `ItemCardapio`, `Endereco`)
- **Use Cases**: Lógica de negócio organizada por funcionalidade
- **Repositories**: Interfaces para acesso a dados
- **Exceptions**: Exceções customizadas de negócio

### 🔄 **Application Layer** (`application/`)
- **DTOs**: Objetos de transferência de dados para requisições/respostas da API
- **Mappers**: Conversores MapStruct para entidades/DTOs

### 🛠️ **Infrastructure Layer** (`infrastructure/`)
- **Persistence**: Entidades JPA e implementações de repositórios
- **Configuration**: Classes de configuração (Security, Swagger)
- **Exception Handling**: Manipulador global de exceções

### 🌐 **Presentation Layer** (`presentation/`)
- **Controllers**: Endpoints REST da API
- **API Interfaces**: Especificações OpenAPI com anotações Swagger

---

## 🚀 Tecnologias Principais

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| **Spring Boot** | 3.4.5 | Framework principal |
| **Java** | 17 | Linguagem de programação |
| **PostgreSQL** | 15 | Banco de dados |
| **Spring Data JPA** | - | Persistência de dados |
| **MapStruct** | 1.5.5 | Mapeamento de objetos |
| **Lombok** | - | Redução de boilerplate |
| **SpringDoc OpenAPI** | 2.8.6 | Documentação da API |
| **Spring Security** | - | Segurança |
| **JaCoCo** | 0.8.10 | Cobertura de testes |
| **Docker & Docker Compose** | - | Containerização |

---

## 📊 Endpoints da API

### 👥 **Usuários** (`/v1/usuarios`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/v1/usuarios` | Criar novo usuário |
| `GET` | `/v1/usuarios/{id}` | Buscar usuário por ID |
| `PUT` | `/v1/usuarios/{id}` | Atualizar usuário |
| `DELETE` | `/v1/usuarios/{id}` | Excluir usuário |
| `PATCH` | `/v1/usuarios/{id}/senha` | Alterar senha |

#### 📝 **Exemplo de Payload - Criar Usuário:**
```json
{
  "nome": "João Silva",
  "email": "joao@email.com",
  "login": "joao.silva",
  "senha": "minhasenha123",
  "tipoUsuario": "PROPRIETARIO",
  "endereco": {
    "rua": "Rua das Flores, 123",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01234-567"
  }
}
```

### 🏪 **Restaurantes** (`/api/v1/restaurantes`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/restaurantes/{idProprietario}` | Cadastrar restaurante |
| `GET` | `/api/v1/restaurantes/{id}` | Buscar restaurante por ID |
| `PUT` | `/api/v1/restaurantes/{idRestaurante}/proprietario/{idProprietario}` | Atualizar restaurante |
| `DELETE` | `/api/v1/restaurantes/{id}` | Excluir restaurante |
| `GET` | `/api/v1/restaurantes/proprietario/{idProprietario}` | Listar restaurantes do proprietário |

#### 📝 **Exemplo de Payload - Criar Restaurante:**
```json
{
  "nome": "Trattoria Nonna",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "11h às 23h",
  "endereco": {
    "rua": "Rua Augusta, 456",
    "bairro": "Consolação",
    "cidade": "São Paulo",
    "estado": "SP",
    "cep": "01305-000"
  }
}
```

### 📋 **Itens do Cardápio** (`/api/v1/itens-cardapio`)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| `POST` | `/api/v1/itens-cardapio/{idRestaurante}` | Criar item do cardápio |
| `GET` | `/api/v1/itens-cardapio/{id}` | Buscar item por ID |
| `PUT` | `/api/v1/itens-cardapio/{idItem}/restaurante/{idRestaurante}` | Atualizar item |
| `DELETE` | `/api/v1/itens-cardapio/{id}` | Excluir item |
| `GET` | `/api/v1/itens-cardapio/restaurante/{idRestaurante}` | Listar itens do restaurante |

#### 📝 **Exemplo de Payload - Criar Item:**
```json
{
  "nome": "Pizza Margherita",
  "descricao": "Pizza tradicional com molho de tomate, mussarela e manjericão",
  "preco": 32.90,
  "disponivel": true,
  "caminhoFoto": "/images/pizza-margherita.jpg"
}
```

---

## ⚙️ Configuração e Execução

### 📋 **Pré-requisitos**

- [Docker](https://www.docker.com/products/docker-desktop) ≥ 20.0
- [Docker Compose](https://docs.docker.com/compose/) ≥ 2.0
- [Git](https://git-scm.com/) ≥ 2.0

### 🚦 **Execução com Docker (Recomendado)**

1. **Clone o repositório:**
```bash
git clone <repository-url>
cd tech-challenge-api-restaurante
```

2. **Execute com Docker Compose:**
```bash
cd restmanager
docker-compose up --build
```

3. **Aguarde a inicialização:**
   - 🐘 PostgreSQL estará disponível na porta `5432`
   - 🌐 API estará disponível na porta `8081`

4. **Teste a aplicação:**
   - **Swagger UI**: http://localhost:8081/swagger-ui.html
   - **API Base URL**: http://localhost:8081/api/v1

### 🛠️ **Execução Local (Desenvolvimento)**

1. **Configure o banco PostgreSQL localmente:**
```bash
# Via Docker
docker run --name postgres-restmanager \
  -e POSTGRES_DB=restdb \
  -e POSTGRES_USER=restuser \
  -e POSTGRES_PASSWORD=restpass \
  -p 5432:5432 \
  -d postgres:15
```

2. **Configure as variáveis de ambiente:**
```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/restdb
export SPRING_DATASOURCE_USERNAME=restuser
export SPRING_DATASOURCE_PASSWORD=restpass
```

3. **Execute a aplicação:**
```bash
cd restmanager
./mvnw spring-boot:run
```

### 📊 **Comandos de Desenvolvimento**

```bash
# Compilar aplicação
mvn clean package

# Executar testes
mvn test

# Gerar relatório de cobertura
mvn clean verify
# Relatórios disponíveis em: target/site/jacoco/

# Pular testes durante build
mvn clean package -DskipTests
```

---

## 🗄️ **Banco de Dados**

### 📊 **Configuração PostgreSQL**

| Parâmetro | Valor |
|-----------|-------|
| **Database** | `restdb` |
| **Usuário** | `restuser` |
| **Senha** | `restpass` |
| **Porta** | `5432` |
| **Host** | `localhost` (local) / `postgres` (Docker) |

### 🔗 **Conexão Manual ao Banco:**
```bash
# Via linha de comando
psql -h localhost -p 5432 -U restuser -d restdb

# Via clients (DBeaver, TablePlus, etc.)
Host: localhost
Port: 5432
Database: restdb
Username: restuser
Password: restpass
```

### 📋 **Principais Tabelas:**
- `usuarios` - Dados dos usuários (clientes e proprietários)
- `enderecos` - Endereços associados
- `restaurantes` - Dados dos restaurantes
- `item_cardapio` - Itens dos cardápios

---

## 🧪 **Testes e Qualidade**

### 📊 **Cobertura de Testes**
- **Meta**: 80% de cobertura
- **Atual**: Em desenvolvimento
- **Tipos**: Testes unitários e de integração

### 🔍 **Executar Testes:**
```bash
# Todos os testes
mvn test

# Testes específicos
mvn test -Dtest=UsuarioUseCaseTest

# Cobertura com relatório
mvn clean verify
```

### 📈 **Relatórios:**
- **JaCoCo**: `target/site/jacoco/index.html`
- **Surefire**: `target/surefire-reports/`

---

## 📚 **Documentação da API**

### 📖 **Swagger UI**
Acesse a documentação interativa em:
**http://localhost:8081/swagger-ui.html**

### 📄 **OpenAPI Specification**
Especificação disponível em:
**http://localhost:8081/v3/api-docs**

---

## 🔧 **Configurações Técnicas**

### 🌍 **Variáveis de Ambiente**

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `SPRING_DATASOURCE_URL` | - | URL do banco PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | - | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | - | Senha do banco |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | `update` | Estratégia DDL do Hibernate |
| `SPRING_JPA_SHOW_SQL` | `true` | Exibir SQL no log |
| `SERVER_PORT` | `8081` | Porta da aplicação |

### 🔐 **Segurança**
- Spring Security configurado
- Endpoints protegidos por autenticação
- Senhas criptografadas com BCrypt

### 📝 **Logs**
- Nível DEBUG para pacote `br.com.posfiap.restmanager`
- Logs estruturados para requisições/respostas
- SQL logging habilitado

---

## 🐳 **Docker**

### 📦 **Containers**
- **restmanager-api**: Aplicação Spring Boot (porta 8081)
- **restmanager-db**: PostgreSQL 15 (porta 5432)

### 💾 **Volumes**
- `pgdata`: Persistência dos dados PostgreSQL

### ⚡ **Comandos Úteis:**
```bash
# Iniciar containers
docker-compose up -d

# Ver logs da aplicação
docker-compose logs -f app

# Ver logs do banco
docker-compose logs -f postgres

# Parar e remover containers
docker-compose down

# Parar e remover volumes (CUIDADO: apaga dados)
docker-compose down -v
```

---

## 🚨 **Troubleshooting**

### ❌ **Problemas Comuns:**

**1. Erro de conexão com banco:**
```
Causa: PostgreSQL não iniciado ou configuração incorreta
Solução: Verificar docker-compose up e logs do container postgres
```

**2. Porta 8081 já em uso:**
```
Causa: Outra aplicação usando a porta
Solução: Parar outras aplicações ou alterar SERVER_PORT
```

**3. Erro de permissão Docker:**
```
Causa: Usuário sem permissões Docker
Solução: Adicionar usuário ao grupo docker ou usar sudo
```

---

## 👥 **Tipos de Usuário**

O sistema suporta dois tipos de usuário:

### 🏪 **PROPRIETARIO**
- Pode cadastrar e gerenciar restaurantes
- Pode criar e gerenciar cardápios
- Acesso completo às funcionalidades de gestão

### 👤 **CLIENTE** 
- Pode visualizar restaurantes e cardápios
- Funcionalidades de cliente (futuras implementações)

---

## 🗂️ **Estrutura do Projeto**

```
restmanager/
├── src/main/java/br/com/posfiap/restmanager/
│   ├── domain/                 # Camada de Domínio
│   │   ├── model/             # Entidades de negócio
│   │   ├── usecase/           # Casos de uso
│   │   ├── repository/        # Interfaces de repositório
│   │   └── exception/         # Exceções de negócio
│   ├── application/           # Camada de Aplicação
│   │   ├── dto/              # Data Transfer Objects
│   │   └── mapper/           # Mappers MapStruct
│   ├── infrastructure/        # Camada de Infraestrutura
│   │   ├── persistence/      # JPA e implementações
│   │   ├── configuration/    # Configurações Spring
│   │   └── exception/        # Exception handlers
│   └── presentation/          # Camada de Apresentação
│       └── controller/        # Controllers REST
├── src/test/                  # Testes unitários e integração
├── docker-compose.yaml       # Orquestração Docker
├── dockerfile                # Imagem da aplicação
└── pom.xml                   # Dependências Maven
```

---

## 📋 **Entregáveis do Projeto**

### ✅ **Implementado**
- [x] **Funcionalidades CRUD completas**
  - [x] Cadastro de Tipos de Usuário
  - [x] Cadastro de Restaurante  
  - [x] Cadastro de Itens do Cardápio
- [x] **Clean Architecture**
- [x] **Docker Compose configurado**
- [x] **Documentação detalhada** (este documento)
- [x] **Código organizado e documentado**

### 🔄 **Em Desenvolvimento**
- [ ] **Collections Postman** para testes
- [ ] **Cobertura de testes 80%**
- [ ] **Testes de integração completos**

### 📋 **Próximos Passos**
- [ ] **Vídeo demonstrativo** (5 minutos)
- [ ] **Repositório público** no GitHub

---

## 📞 **Suporte**

Para dúvidas ou problemas:

1. **Verifique os logs**: `docker-compose logs -f app`
2. **Consulte a documentação**: Swagger UI
3. **Revise este README**: Seção troubleshooting

---

## 📄 **Licença**

Este projeto foi desenvolvido como parte do Tech Challenge da FIAP - Fase 2.

---

**🚀 Desenvolvido com Spring Boot + Clean Architecture + Docker**