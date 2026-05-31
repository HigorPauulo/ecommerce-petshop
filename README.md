# API E-commerce Pet Shop

API REST para um e-commerce de produtos para pets, construída com Spring Boot e
PostgreSQL. Expõe operações de CRUD para categorias, produtos, clientes e pedidos,
com autenticação JWT, validação de entrada, tratamento de exceções e documentação
OpenAPI/Swagger.

## Tecnologias

- Java 21
- Spring Boot 3.5.8 (Web, Data JPA, Validation, Security)
- PostgreSQL 16
- Flyway (migrations)
- springdoc-openapi (Swagger UI)
- JWT (io.jsonwebtoken / jjwt)
- Maven (wrapper `./mvnw`)
- Docker / Docker Compose

## Pré-requisitos

- Java 21 (JDK)
- Docker e Docker Compose
- (opcional) Maven — o projeto inclui o wrapper `./mvnw`

## Como executar

### 1. Variáveis de ambiente

```bash
cp .env.example .env
# ajuste as variáveis se quiser (DB_PASSWORD, JWT_SECRET, etc.)
```

### 2. Subir o banco de dados (PostgreSQL via Docker)

```bash
docker compose up -d
docker compose ps    # aguarde o status "healthy"
```

### 3. Rodar a aplicação

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. O Flyway cria o schema e popula dados de
exemplo automaticamente na primeira execução.

## Documentação dos endpoints (Swagger)

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

Para acessar endpoints protegidos pelo Swagger, faça login em `/api/auth/login`,
copie o token e clique em **Authorize** (formato `Bearer <token>` já é tratado).

## Autenticação

Usuário admin criado automaticamente na primeira execução:

- **email:** `admin@petshop.com`
- **senha:** `admin123` (troque após o primeiro acesso)

Fluxo:

```bash
# Login -> retorna { "token": "...", "type": "Bearer", "expiresIn": 86400000 }
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@petshop.com","password":"admin123"}'

# Usar o token em chamadas protegidas
curl http://localhost:8080/api/orders \
  -H "Authorization: Bearer <SEU_TOKEN>"
```

Também é possível registrar um novo usuário em `POST /api/auth/register`.

## Regras de acesso

| Acesso | Endpoints |
|--------|-----------|
| Público | `POST /api/auth/**`, Swagger, `GET /api/products/**`, `GET /api/categories/**` |
| Autenticado (JWT) | Demais operações (criar/editar/excluir, clientes e pedidos) |

## Principais endpoints

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/register` | Registra usuário e retorna token |
| POST | `/api/auth/login` | Autentica e retorna token |
| GET/POST | `/api/categories` | Lista / cria categorias |
| GET/PUT/DELETE | `/api/categories/{id}` | Detalha / atualiza / exclui categoria |
| GET/POST | `/api/products` | Lista (filtro `?categoryId=`) / cria produtos |
| GET/PUT/DELETE | `/api/products/{id}` | Detalha / atualiza / exclui produto |
| GET/POST | `/api/customers` | Lista / cadastra clientes |
| GET/PUT/DELETE | `/api/customers/{id}` | Detalha / atualiza / exclui cliente |
| GET/POST | `/api/orders` | Lista (filtro `?customerId=`) / cria pedidos |
| GET/DELETE | `/api/orders/{id}` | Detalha / exclui pedido |
| PATCH | `/api/orders/{id}/status` | Atualiza status (cancelar devolve estoque) |

Listagens são paginadas: `?page=0&size=20&sort=id,desc`.

## Banco de dados

O schema é versionado com Flyway em `src/main/resources/db/migration`:

- `V1__create_schema.sql` — tabelas, FKs, constraints e índices
- `V2__seed_data.sql` — categorias, produtos e clientes de exemplo
- `V3__create_users.sql` — tabela de usuários (autenticação)

Não é necessário rodar SQL manualmente: o Flyway aplica as migrations no startup.

## Modelo de domínio

- **Category** 1—N **Product**
- **Customer** 1—N **Address**, 1—N **Order**
- **Order** 1—N **OrderItem** N—1 **Product** (relação pedido↔produtos)
- **User** — autenticação (papéis `ROLE_USER` / `ROLE_ADMIN`)

## Estrutura do projeto

```
src/main/java/br/com/importaai/ecommercepetshop
├── config        # OpenAPI, Security, DataSeeder
├── controller    # endpoints REST
├── dto           # request/response (records + validação)
├── exception     # exceções de domínio + handler global
├── mapper        # conversão entity <-> DTO
├── model         # entidades JPA
├── repository    # Spring Data JPA
├── security      # JWT (service, filtro, user details)
└── service       # regras de negócio
```

## Testes da API (Postman/Insomnia)

A coleção do Postman está em `postman/` (importe no Postman/Insomnia e ajuste a
variável `baseUrl` para `http://localhost:8080`).
