# LogiTrack Pro — Backend

API REST desenvolvida com **Spring Boot 3** + **Java 17** para o sistema de gestão de frotas LogiTrack Pro.

## Stack

- Java 17 + Spring Boot 3.2
- Spring Security + JWT (jjwt 0.12)
- Spring Data JPA + PostgreSQL
- Flyway (migrations automáticas)
- Lombok
- Maven

## Endpoints principais

| Método | Rota | Descrição |
|--------|------|-----------|
| POST | `/api/auth/login` | Autenticação → retorna JWT |
| GET | `/api/veiculos` | Listar veículos |
| POST | `/api/veiculos` | Criar veículo |
| PUT | `/api/veiculos/{id}` | Atualizar veículo |
| DELETE | `/api/veiculos/{id}` | Remover veículo |
| GET | `/api/viagens` | Listar viagens (filtro opcional: `?veiculoId=`) |
| POST | `/api/viagens` | Registrar viagem |
| PUT | `/api/viagens/{id}` | Atualizar viagem |
| DELETE | `/api/viagens/{id}` | Remover viagem |
| GET | `/api/manutencoes` | Listar manutenções |
| POST | `/api/manutencoes` | Agendar manutenção |
| PUT | `/api/manutencoes/{id}` | Atualizar manutenção |
| DELETE | `/api/manutencoes/{id}` | Remover manutenção |
| GET | `/api/dashboard` | Métricas do dashboard (5 consultas SQL) |

## Métricas do Dashboard (SQL)

1. **Total KM percorrido** — soma por veículo ou frota toda
2. **Volume por Categoria** — contagem de viagens por tipo (LEVE / PESADO)
3. **Cronograma de Manutenção** — próximas 5 manutenções ordenadas por data
4. **Ranking de Utilização** — veículos ordenados por maior KM acumulado
5. **Projeção Financeira** — custo total estimado de manutenções no mês atual

## Rodar localmente

### Pré-requisitos
- Java 17+
- Maven 3.9+
- PostgreSQL 15+ rodando localmente

### Configurar banco

```sql
CREATE DATABASE logitrack;
CREATE USER logitrack WITH PASSWORD 'logitrack123';
GRANT ALL PRIVILEGES ON DATABASE logitrack TO logitrack;
```

### Variáveis de ambiente

Crie um arquivo `.env` (ou export no shell):

```bash
export DB_URL=jdbc:postgresql://localhost:5432/logitrack
export DB_USER=logitrack
export DB_PASSWORD=logitrack123
export JWT_SECRET=sua-chave-secreta-com-pelo-menos-256-bits
export JWT_EXPIRATION=86400000
```

### Executar

```bash
mvn clean spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

O Flyway criará as tabelas automaticamente e inserirá os dados seed na primeira execução.

## Usuários padrão (seed)

| Email | Senha | Role |
|-------|-------|------|
| admin@logitrack.com | admin123 | ADMIN |
| gestor@logitrack.com | admin123 | USER |

## Build para produção

```bash
mvn clean package -DskipTests
java -jar target/logitrack-backend-1.0.0.jar
```

## Decisões técnicas

- **Flyway** foi escolhido para migrations versionadas, garantindo consistência do schema em qualquer ambiente.
- **JWT stateless** — sem sessão no servidor, escalável horizontalmente.
- **DTOs separados das Entities** — evita expor o modelo de domínio e facilita validações.
- **GlobalExceptionHandler** — centraliza tratamento de erros com respostas padronizadas via `ApiResponse<T>`.
- **CORS liberado via `allowedOriginPatterns("*")`** — configurável por variável de ambiente em produção.
