## Buscar Usuário por ID

- **Método:** GET
- **URL:** `http://localhost:8081/v1/usuarios/{id}`
- **Descrição:** Busca os dados do usuário pelo ID informado na URL.

### Parâmetros de URL

| Parâmetro | Tipo   | Descrição                        | Exemplo |
| --------- | ------ | --------------------------------|---------|
| id        | string | ID do usuário a ser buscado     | 1       |

---

## Inserir Usuário

- **Método:** POST
- **URL:** `http://localhost:8081/v1/usuarios`
- **Headers:**
    - `Accept: */*`
    - `Content-Type: application/json`

- **Body (JSON):**

```json
{
  "nome": "Ana Beatriz Silva",
  "email": "ana.silva@gmail.com",
  "login": "anasilva",
  "senha": "SenhaForte123!",
  "tipoUsuario": "CLIENTE",
  "endereco": {
    "rua": "Rua das Acácias",
    "numero": "123",
    "complemento": "Apto 202",
    "bairro": "Jardim das Flores",
    "cidade": "São Paulo",
    "cep": "04567890",
    "estado": "SP",
    "pais": "Brasil"
  }
}
```

---

## Deletar Usuário por ID

- **Método:** DELETE
- **URL:** `http://localhost:8081/v1/usuarios/{id}`
- **Headers:**
    - `Accept: */*`

### Parâmetros de URL

| Parâmetro | Tipo   | Descrição                                     | Exemplo |
| --------- | ------ | ---------------------------------------------|---------|
| id        | string | ID do usuário que deseja excluir as informações | 1       |

---

## Atualizar Usuário por ID

- **Método:** PUT
- **URL:** `http://localhost:8081/v1/usuarios/{id}`
- **Headers:**
    - `Accept: */*`
    - `Content-Type: application/json`

- **Parâmetros de URL**

| Parâmetro | Tipo   | Descrição                                  | Exemplo |
| --------- | ------ | ------------------------------------------|---------|
| id        | string | ID do usuário que deseja atualizar         | 1       |

- **Body (JSON):**

```json
{
  "nome": "Ana Beatriz Silva",
  "email": "ana.silva@gmail.com",
  "login": "anaana",
  "tipoUsuario": "CLIENTE",
  "endereco": {
    "rua": "Rua das Acácias",
    "numero": "12345",
    "complemento": "Apto 202",
    "bairro": "Jardim das Flores",
    "cidade": "São Paulo",
    "cep": "04567890",
    "estado": "SP",
    "pais": "Brasil"
  }
}
```

---

## Atualizar a senha do Usuário por ID

- **Método:** PATCH
- **URL:** `http://localhost:8081/v1/usuarios/{id}/senha`
- **Headers:**
    - `Accept: */*`
    - `Content-Type: application/json`

- **Parâmetros de URL**

| Parâmetro | Tipo   | Descrição                                  | Exemplo |
| --------- | ------ | ------------------------------------------|---------|
| id        | string | ID do usuário que deseja atualizar a senha | 1       |

- **Body (JSON):**

```json
{
  "senhaAtual": "SenhaForte123!",
  "novaSenha": "SenhaForte321!"
}
```

---

## Autenticar Usuário por Login e Senha

- **Método:** POST
- **URL:** `http://localhost:8081/v1/autenticacao/login`
- **Headers:**
    - `Accept: */*`
    - `Content-Type: application/json`

- **Body (JSON):**

```json
{
  "login": "anasilva",
  "senha": "SenhaForte123!"
}
```

---

**Observações:**

- Substitua `{id}` nos endpoints pelos IDs reais dos usuários.
- Os exemplos de body podem ser alterados conforme a necessidade da requisição.
