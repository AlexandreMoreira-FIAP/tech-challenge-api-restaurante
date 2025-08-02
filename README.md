
# Não Alterar essa branch !!


# 🧩 Descrição do Problema

Um grupo de restaurantes da região enfrentava altos custos com sistemas de gestão individuais. Para reduzir despesas, decidiram criar um sistema único e compartilhado, desenvolvido por estudantes.

O sistema permitirá que cada restaurante gerencie suas operações, enquanto os clientes poderão consultar informações, avaliar e fazer pedidos online. Como há restrições financeiras, o desenvolvimento será feito por fases, permitindo ajustes conforme o uso.

# ✅ Solução

Foi desenvolvida uma API RESTful utilizando os seguintes recursos e tecnologias:

- **Spring Boot 3.4.5** – Framework principal para construção da aplicação.
- **Java 17** – Linguagem de programação utilizada.
- **Docker** – Para execução da aplicação em containers.
- **PostgreSQL** – Banco de dados relacional utilizado.
- **Swagger (Springdoc OpenAPI)** – Para geração automática da documentação da API.
- **MapStruct** – Para mapeamento entre DTOs e entidades.
- **Lombok** – Para reduzir código boilerplate (getters, setters, etc.).
- **Spring Security** – Para implementação de segurança básica.
- **Bean Validation (javax.validation)** – Para validação dos dados de entrada.


# 🏗️ Arquitetura

O projeto adota a Arquitetura em Camadas, promovendo uma estrutura organizada e escalável. Essa abordagem garante separação de responsabilidades, facilitando a manutenção, testes e evolução do sistema. Além disso, seguimos os princípios SOLID para garantir um código limpo, coeso e de fácil extensão.

As principais camadas são:

- **Controller**: Responsável por receber as requisições HTTP e retornar respostas adequadas.
- **Service**: Contém a lógica de negócio, centralizando as regras da aplicação.
- **Repository**: Camada de persistência, utilizando o Spring Data JPA para abstrair operações com o banco de dados.
- **DTOs + MapStruct**: Os DTOs são usados para isolar a representação dos dados da entidade de domínio. O MapStruct automatiza o mapeamento entre DTOs e entidades.
- **Entity**: Representa as tabelas do banco de dados na forma de objetos Java.
- **Validação**: Utiliza anotações do Bean Validation para validar os dados de entrada antes do processamento.


# Desenho da Solução
- Fluxo de inclusão de um cliente.
![Desenho_Arquitetura](https://github.com/user-attachments/assets/8884678e-876c-4333-b871-e53077a74dc8)



# 📚 Documentações Complementares

- 🚀 Guia de Execução Local: Instruções para clonar o projeto, subir os containers com Docker e acessar a aplicação: [Guia](https://github.com/AlexandreMoreira-FIAP/tech-challenge-api-restaurante/blob/feature/alexandre/doc/GuiaRodarLocal.md)

- 🔌 Endpoints da API: Detalhamento completo das rotas disponíveis, com exemplos de requisição: [Endpoints](https://github.com/AlexandreMoreira-FIAP/tech-challenge-api-restaurante/blob/feature/alexandre/doc/Endpoints.md)

- Modelagem de Dados: Estrutura das entidades e seus relacionamentos: [Modelagem](https://github.com/AlexandreMoreira-FIAP/tech-challenge-api-restaurante/blob/feature/alexandre/doc/ModelagemDeDados.md) 
