
# 💾 RestManager API – Guia de Execução Local
 
---

## ✨ Requisitos

- [Java17](https://www.oracle.com/br/java/technologies/downloads/)
- [Docker](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com/)

---

## 📦 Como clonar e executar

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/tech-challenge-api-restaurante.git
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

## 🌐 Acessar o Swagger
- http://localhost:8081/swagger-ui.html

## 📥 Collection para Testes no Postman

Para facilitar os testes da API, disponibilizamos duas versões da collection do Postman:

- **Collection com payloads vazios**: Ideal para quem deseja preencher os dados manualmente e testar diferentes cenários.
- **Collection com payloads preenchidos**: Contém exemplos completos prontos para envio, útil para testes rápidos.

🔗 [BaixarCollections](https://raw.githubusercontent.com/AlexandreMoreira-FIAP/tech-challenge-api-restaurante/feature/alexandre/Collections/CollectionAPI-Restaurante.json)

> 💡 Basta importar o arquivo no Postman e começar a testar os endpoints da API.

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
