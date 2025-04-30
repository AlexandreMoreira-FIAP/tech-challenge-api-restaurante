# 💾 RestManager API

API REST em Java com Spring Boot para gerenciamento de restaurantes. Integração com PostgreSQL via Docker Compose.

---

## ✨ Requisitos

- [Docker](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com/)

---

## 📦 Como clonar e executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/restmanager.git
cd restmanager
```

### 2. Suba os containers com Docker Compose

```bash
docker-compose up --build
```

Isso vai iniciar:
- A API na porta **8081**
- O banco PostgreSQL na porta **5432**

---

## ✅ Testar a API

### Endpoint de teste (GET)

```http
GET http://localhost:8081/hello
```

Resposta esperada:
```text
Olá, mundo!
```

---

## 📈 Banco de Dados

O banco PostgreSQL é criado com as seguintes credenciais:

- **Database:** restdb
- **User:** restuser
- **Password:** restpass
- **Porta:** 5432

Você pode se conectar ao banco usando clientes como DBeaver, TablePlus ou via linha de comando:

```bash
psql -h localhost -p 5432 -U restuser -d restdb
```

---

## ♻ Parar os containers

```bash
docker-compose down
```

---

## 🚀 Em desenvolvimento

Em breve:
- CRUD completo de restaurantes
- Autenticação com JWT
- Testes automatizados

---

Feito com ❤