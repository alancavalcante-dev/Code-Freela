# 💼 CodeFreela - Plataforma de Conexão entre Clientes e Desenvolvedores

O **CodeFreela** é uma plataforma fullstack baseada em containers Docker que conecta **clientes** e **desenvolvedores** para colaboração em projetos. Com integração ao **Gitea** (GitHub self-hosted), controle de ponto dos desenvolvedores, e análise automática via IA, a plataforma visa automatizar todo o ciclo de vida de um projeto freelance e comunicação flexivel e 
clara entre o desenvolvedor e o cliente.

---

## 🧱 Arquitetura Geral

Utiliza `docker-compose` para gerenciar 4 containers principais:

| Serviço | Função                                       | Porta                 |
| ------- | -------------------------------------------- | --------------------- |
| `db`    | Banco de dados PostgreSQL                    | 5434                  |
| `api`   | Backend com regras de negócio e controle JWT | 8080                  |
| `gitea` | GitHub local para repositórios por projeto   | 3000 (web), 222 (SSH) |
| `app`   | Frontend em React (comentado por enquanto)   | 5173                  |

Todos os containers se comunicam por meio da rede Docker `codefreela-network`.

---

## 🔧 Funcionalidades Atuais

### 🚀 Fluxo principal do sistema

1. Cliente publica um projeto.
2. Desenvolvedores veem e dão **match**.
3. Se o cliente aceitar:

   * É criado um `projectBusiness` (negociação).
   * API registra o desenvolvedor no Gitea e cria repositório.
   * Envio de e-mail ao desenvolvedor com credenciais e link do repositório.

### ⏱ Marcação de Ponto

* Desenvolvedor registra início e fim de uma sessão de trabalho.
* Commits realizados nesse intervalo são analisados.

### 🤖 Análise por IA

* O cliente acessa a área do projeto.
* Vê os pontos registrados pelo desenvolvedor.
* Pode clicar em "Analisar por IA".
* A API busca o último commit dentro do período e usa o **DeepSeek (via Ollama)** para analisar o patch.
* Comentários são gerados automaticamente explicando o que foi feito.

### 🔐 Backend

* Autenticação com JWT
* Cadastro e login de usuários
* CRUD de portfólios, projetos, perfis, negociações
* Integração com Gitea (usuário + repositório)
* Pesquisa pública de projetos com suporte a Specifications (Spring)

---

## 📦 Como subir o ambiente

### 1. Clone o repositório

```bash
git clone https://github.com/alancavalcante-dev/Code-Freela.git
cd codefreela
```

### 2. Crie os arquivos `.env`

```env
POSTGRES_USER=preencher
POSTGRES_PASSWORD=preencher
POSTGRES_DB=codefreela

USERNAME_ADMIN=preencher
PASSWORD_ADMIN=preencher
DATABASE_URL=jdbc:postgresql://db:5432/codefreela
JWT_SECRET=supersecret
EMAIL_SENDER=preencher

USER_UID=1000
USER_GID=1000
GITEA__database__DB_TYPE=postgres
GITEA__database__HOST=db:5432
GITEA__database__NAME=codefreela
GITEA__database__USER=preencher
GITEA__database__PASSWD=preencher
```

### 3. Suba os containers

```bash
docker-compose up -d --build
```

### 4. Acesse os serviços

* API: [http://localhost:8080](http://localhost:8080)
* Gitea: [http://localhost:3000](http://localhost:3000)

---

## 🧪 Futuras Implementações

| Funcionalidade                                                    | Status |
| ----------------------------------------------------------------- | ------ |
| Pagamento antecipado para começar o projeto (dev)                 | 🔜     |
| Chat cliente <-> desenvolvedor (para negociação e após)           | 🔜     |
| Chatbot de entrada com redirecionamento                           | 🔜     |
| Cache de dados do chatbot via Redis                               | 🔜     |
| Integração com Google OAuth2                                      | 🔜     |
| Deploy automático temporário de projeto (10 min via Docker Swarm) | 🔜     |
| Frontend completo com React                                       | 🔜     |
| Integração com API do PayPal para pagamentos                      | 🔜     |

---

## 📁 Estrutura do Projeto

```
codefreela/
├── codefreelaapi/       # Backend (Spring Boot)
│   └── src/main/java/io/github/alancavalcante_dev/codefreelaapi/
│       ├── commom/
│       │   └── validate, Error, GlobalExceptionHandler
│       ├── config/
│       ├── domain/
│       │   ├── appointment/
│       │   ├── container/
│       │   ├── entity/
│       │   ├── generatedcommentia/
│       │   │   ├── ArtificialIntelligencePrompt.java
│       │   │   └── GeneratorCommentIA.java
│       │   ├── gitea/
│       │   ├── notification/
│       │   ├── portfolio/
│       │   ├── profile/
│       │   ├── project/
│       │   └── projectbusiness/
│       ├── exceptions/
│       ├── infrastructure/
│       │   ├── repository/
│       │   └── security/
│       ├── mapper/
│       └── presentation/
│           ├── controller/
│           └── dto/
│       └── CodefreelaapiApplication.java
├── frontend/            # Frontend (React - comentado)
├── gitea/               # Dados persistentes do Gitea
├── .env                 # Arquivo de variáveis de ambiente
├── docker-compose.yml   # Coordenação dos containers
└── README.md            # Documentação do projeto
```

---

## 👨‍💻 Autor

Desenvolvido por **Alan Pereira**
Contato: [github.com/alancavalcante-dev](https://github.com/alancavalcante-dev)

---

## 📄 Licença

Este projeto está licenciado sob a Licença **MIT** apenas para fins de **visualização e estudo**. Para reutilização do código, entre em contato com o autor. Esse projeto está sendo feito com objetivo de estudos e aprendizado.
