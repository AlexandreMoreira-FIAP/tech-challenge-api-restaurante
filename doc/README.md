## 📃 Modelagem de Dados – Sistema de Gestão de Restaurantes (Fase 1)

Nesta fase do projeto, o foco está na modelagem da entidade **Usuário**, contemplando dois tipos: `CLIENTE` e `DONO`. Cada usuário possui um endereço associado.

---

### 📦 Tabela: `usuarios`

Responsável por armazenar clientes e donos de restaurante. Terá um campo para diferenciar os tipos.

| Campo                   | Tipo             | Notas                                         |
| ----------------------- | ---------------- | --------------------------------------------- |
| `id`                    | UUID ou BIGINT   | PK, gerado automaticamente                    |
| `nome`                  | VARCHAR          | Obrigatório                                   |
| `email`                 | VARCHAR (unique) | Único, obrigatório                            |
| `login`                 | VARCHAR (unique) | Único, obrigatório                            |
| `senha`                 | VARCHAR          | Criptografada (ex: BCrypt)                    |
| `tipo_usuario`          | VARCHAR          | Enum: `CLIENTE` ou `DONO`                     |
| `data_ultima_alteracao` | TIMESTAMP        | Atualizado em cada modificação                |
| `endereco_id`           | UUID ou BIGINT   | FK (opcional), relacionamento com `enderecos` |

---

### 🏠 Tabela: `enderecos`

Separado por boas práticas (endereços podem mudar, reutilizar, etc).

| Campo         | Tipo           | Notas    |
| ------------- | -------------- | -------- |
| `id`          | UUID ou BIGINT | PK       |
| `rua`         | VARCHAR        |          |
| `numero`      | VARCHAR        |          |
| `complemento` | VARCHAR        | Opcional |
| `bairro`      | VARCHAR        |          |
| `cidade`      | VARCHAR        |          |
| `estado`      | VARCHAR        |          |
| `cep`         | VARCHAR        |          |

---

### 🔄 Relacionamento

* `usuarios.endereco_id` → `enderecos.id` (muitos-para-um)
* Cada usuário pode ter um endereço
* Um endereço pode ser usado por vários usuários (mas geralmente é 1:1)

---

### 🔍 Observações técnicas

* O campo `senha` deve ser **criptografado** com hash (ex: BCrypt)
* `tipo_usuario` pode ser armazenado como `ENUM` no Java (ou VARCHAR no banco)
* `data_ultima_alteracao` pode ser controlado com `@PreUpdate` no JPA
* Caso deseje evoluir futuramente, os tipos de usuário podem virar uma tabela separada (normalização)
