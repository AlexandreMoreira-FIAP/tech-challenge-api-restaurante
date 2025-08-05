# 📬 **COLLECTIONS POSTMAN - RESTMANAGER API**

> **Collections completas para teste da API RestManager - Tech Challenge FIAP**

## 📋 **ARQUIVOS INCLUÍDOS**

- `RestManager-API.postman_collection.json` - Collection principal com todos os endpoints
- `RestManager-Environment.postman_environment.json` - Environment com variáveis de configuração
- `POSTMAN-COLLECTIONS.md` - Este documento de instruções

---

## 🚀 **COMO IMPORTAR E USAR**

### **1. Importar no Postman**

#### **Opção A: Via Interface**
1. Abra o **Postman**
2. Clique em **Import** (botão no canto superior esquerdo)
3. Arraste os arquivos `.json` ou clique **Choose Files**
4. Selecione:
   - `RestManager-API.postman_collection.json`
   - `RestManager-Environment.postman_environment.json`
5. Clique **Import**

#### **Opção B: Via URL** (se disponível no repositório)
```
Collection: https://raw.githubusercontent.com/[SEU-REPO]/RestManager-API.postman_collection.json
Environment: https://raw.githubusercontent.com/[SEU-REPO]/RestManager-Environment.postman_environment.json
```

### **2. Configurar Environment**
1. No Postman, selecione **"RestManager - Local Development"** no dropdown de environments (canto superior direito)
2. ✅ Verificar se `baseUrl` está configurado para `http://localhost:8081`

---

## 🔧 **PRÉ-REQUISITOS**

### **1. Iniciar a Aplicação**
```bash
# Na pasta do projeto
cd restmanager
docker-compose up --build
```

### **2. Verificar se a API está funcionando**
- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **Health Check**: http://localhost:8081/actuator/health (se disponível)
- **Test Endpoint**: `GET http://localhost:8081/hello` (se disponível)

---

## 📊 **ESTRUTURA DA COLLECTION**

### **👥 USUÁRIOS** (5 endpoints)
```
POST   /v1/usuarios                    - Criar usuário
GET    /v1/usuarios/{id}               - Buscar usuário
PUT    /v1/usuarios/{id}               - Atualizar usuário  
DELETE /v1/usuarios/{id}               - Excluir usuário
PATCH  /v1/usuarios/{id}/senha         - Alterar senha
```

### **🏪 RESTAURANTES** (4 endpoints)
```
POST   /api/v1/restaurantes/{idProprietario}                           - Criar restaurante
GET    /api/v1/restaurantes/{id}                                       - Buscar restaurante
PUT    /api/v1/restaurantes/{idRestaurante}/proprietario/{idProprietario} - Atualizar restaurante
GET    /api/v1/restaurantes/proprietario/{idProprietario}              - Listar por proprietário
DELETE /api/v1/restaurantes/{id}                                       - Excluir restaurante
```

### **📋 ITENS DO CARDÁPIO** (5 endpoints)
```
POST   /api/v1/itens-cardapio/{idRestaurante}                          - Criar item
GET    /api/v1/itens-cardapio/{id}                                     - Buscar item
PUT    /api/v1/itens-cardapio/{idItem}/restaurante/{idRestaurante}     - Atualizar item
GET    /api/v1/itens-cardapio/restaurante/{idRestaurante}              - Listar por restaurante
DELETE /api/v1/itens-cardapio/{id}                                     - Excluir item
```

### **🗑️ LIMPEZA** (4 endpoints)
```
DELETE /api/v1/itens-cardapio/{id}     - Excluir item
DELETE /api/v1/restaurantes/{id}       - Excluir restaurante  
DELETE /v1/usuarios/{id}               - Excluir usuários
```

---

## 🔄 **FLUXO RECOMENDADO DE EXECUÇÃO**

### **Cenário 1: Teste Completo**
```
1. 👥 Criar Usuário Proprietário
2. 👥 Criar Usuário Cliente  
3. 🏪 Criar Restaurante (com o proprietário)
4. 📋 Criar Item do Cardápio (no restaurante)
5. 📋 Criar Segundo Item do Cardápio
6. 🔍 Testar todas as consultas (GET)
7. ✏️  Testar todas as atualizações (PUT/PATCH)
8. 🗑️ Executar limpeza (DELETE)
```

### **Cenário 2: Teste Individual**
- Execute qualquer endpoint isoladamente
- As **variáveis automáticas** (`{{userId}}`, `{{restauranteId}}`, etc.) são definidas pelos testes

---

## 🧪 **TESTES AUTOMATIZADOS INCLUÍDOS**

### **Cada requisição inclui:**
- ✅ **Validação de Status Code**
- ✅ **Validação de Estrutura de Resposta**
- ✅ **Captura de IDs para próximas requisições**
- ✅ **Validação de Dados Específicos**

### **Exemplo de Teste Automático:**
```javascript
pm.test("Status code is 201", function () {
    pm.response.to.have.status(201);
});

pm.test("Response has id", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('id');
    pm.collectionVariables.set("userId", jsonData.id);
});
```

---

## 🌍 **VARIÁVEIS DE AMBIENTE**

### **Configuradas Automaticamente:**
| Variável | Valor | Descrição |
|----------|-------|-----------|
| `baseUrl` | `http://localhost:8081` | URL base da API |
| `swaggerUrl` | `http://localhost:8081/swagger-ui.html` | Swagger UI |
| `databaseHost` | `localhost:5432` | Host do PostgreSQL |
| `databaseName` | `restdb` | Nome do banco |
| `databaseUser` | `restuser` | Usuário do banco |

### **Definidas Durante Execução:**
| Variável | Origem | Uso |
|----------|--------|-----|
| `userId` | Criar Usuário Proprietário | Referência para restaurantes |
| `clienteId` | Criar Usuário Cliente | Referência para testes |
| `restauranteId` | Criar Restaurante | Referência para itens |
| `itemId` | Criar Item | Referência para atualizações |
| `item2Id` | Criar Segundo Item | Referência para exclusão |

---

## 🎯 **EXEMPLOS DE PAYLOADS**

### **Usuário Proprietário:**
```json
{
  "nome": "João da Silva",
  "email": "joao.silva@email.com",
  "login": "joao.silva",
  "senha": "minhasenha123",
  "tipoUsuario": "PROPRIETARIO",
  "endereco": {
    "rua": "Rua das Flores, 123",
    "numero": "123",
    "complemento": "Apto 45",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "cep": "01234-567",
    "estado": "SP",
    "pais": "Brasil"
  }
}
```

### **Restaurante:**
```json
{
  "nome": "Trattoria Nonna",
  "tipoCozinha": "Italiana",
  "horarioFuncionamento": "11h às 23h",
  "endereco": {
    "rua": "Rua Augusta, 456",
    "numero": "456",
    "complemento": "Térreo",
    "bairro": "Consolação",
    "cidade": "São Paulo",
    "cep": "01305-000",
    "estado": "SP",
    "pais": "Brasil"
  }
}
```

### **Item do Cardápio:**
```json
{
  "nome": "Pizza Margherita",
  "descricao": "Pizza tradicional com molho de tomate, mussarela e manjericão fresco",
  "preco": 32.90,
  "disponivel": true,
  "caminhoFoto": "/images/pizza-margherita.jpg"
}
```

---

## 🔍 **TROUBLESHOOTING**

### **Problema: Connection refused**
```
❌ Erro: connect ECONNREFUSED 127.0.0.1:8081
✅ Solução: Verificar se a aplicação está rodando
   docker-compose up --build
```

### **Problema: 404 Not Found**
```
❌ Erro: Cannot GET /v1/usuarios
✅ Solução: Verificar se os endpoints estão corretos no código
   Consultar Swagger UI: http://localhost:8081/swagger-ui.html
```

### **Problema: 500 Internal Server Error**
```
❌ Erro: Erro interno do servidor
✅ Solução: Verificar logs da aplicação
   docker-compose logs -f app
   Verificar se o banco PostgreSQL está funcionando
```

### **Problema: Validation errors**
```
❌ Erro: 400 Bad Request - validation failed
✅ Solução: Verificar se todos os campos obrigatórios estão preenchidos
   Consultar DTOs no código ou Swagger
```

---

## 🎥 **EXECUÇÃO EM LOTE**

### **Runner do Postman:**
1. Clique em **RestManager API** (nome da collection)
2. Clique em **Run collection**
3. Selecione todas as requisições (ou as desejadas)
4. Configure:
   - **Environment**: RestManager - Local Development
   - **Iterations**: 1
   - **Delay**: 0ms (ou ajuste se necessário)
5. Clique **Run RestManager API**

### **CLI Newman:** (opcional)
```bash
# Instalar Newman globalmente
npm install -g newman

# Executar collection
newman run RestManager-API.postman_collection.json \
  -e RestManager-Environment.postman_environment.json \
  --reporters cli,html \
  --reporter-html-export newman-report.html
```

---

## 📞 **SUPORTE**

### **Para problemas com as collections:**
1. ✅ Verificar se a aplicação está rodando
2. ✅ Verificar se o environment está selecionado
3. ✅ Consultar Swagger UI para validar endpoints
4. ✅ Verificar logs da aplicação Docker

### **Para problemas com a API:**
1. 📖 Consultar `README.md` principal do projeto
2. 🐳 Verificar `docker-compose.yaml`
3. 📊 Acessar Swagger UI: http://localhost:8081/swagger-ui.html

---

## ✅ **CHECKLIST DE VALIDAÇÃO**

- [ ] **Collection importada com sucesso**
- [ ] **Environment selecionado**  
- [ ] **Aplicação rodando em localhost:8081**
- [ ] **PostgreSQL conectado**
- [ ] **Swagger UI acessível**
- [ ] **Primeiro endpoint (Criar Usuário) funcionando**
- [ ] **Todas as variáveis sendo capturadas automaticamente**
- [ ] **Testes automáticos passando**

---

**🚀 Desenvolvido para o Tech Challenge FIAP - RestManager API**
**📝 Collections testadas com Postman v10.24+**