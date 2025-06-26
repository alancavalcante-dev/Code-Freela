# 💻 CodeFreela - Plataforma de Conexão entre Clientes e Desenvolvedores

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
* Integração com Inteligência Artifical Ollama (deepseek model)
* Pesquisa pública de projetos com suporte a Specifications (Spring)

---

## 📦 Como subir o ambiente

### 1. Clone o repositório

```bash
git clone https://github.com/alancavalcante-dev/Code-Freela.git
cd codefreela
```

### 2. Crie o arquivo `.env`

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

| Funcionalidade                                                       | Status |
|----------------------------------------------------------------------| ------ |
| Pagamento antecipado para começar o projeto (dev)                    | 🔜     |
| Chat cliente <-> desenvolvedor (para negociação e após)              | 🔜     |
| Chatbot de entrada com redirecionamento                              | 🔜     |
| Cache de dados do chatbot via Redis                                  | 🔜     |
| Integração com Google OAuth2                                         | 🔜     |
| Deploy automático temporário de projeto (10 min via Docker Swarm)    | 🔜     |
| Frontend completo com React                                          | 🔜     |
| Integração com API do PayPal para pagamentos                         | 🔜     |
| Refatorar geração de comentário IA para modelo Assíncrono (async)    | 🔜     |
| Querys mais leves e robustas para otimizar e tirar código redundante | 🔜     |
| Refatorar mapeamentos de objetos usando MapperStruct ou Reflection   | 🔜     |
| Implementar exceptions para erros omitidos                           | 🔜     |

---

## 📁 Estrutura do Projeto

```
codefreela/
├── codefreelaapi/                        
│   └── src/main/java/io/github/alancavalcante_dev/codefreelaapi/
│       ├── commom/                        # Utilitários e validações comuns
│       │   ├── globalexception/           # Handler de exceções globais
│       │   ├── notification/              # Sistema de notificações de email
│       │   └── validate/                  # Validadores e regras genéricas
│       ├── config/                       
│       ├── domain/                        # Camada de domínio
│       │   ├── appointment/             
│       │   ├── container/               
│       │   ├── entity/                  
│       │   ├── match/                    
│       │   ├── portfolio/                 
│       │   ├── profile/                   
│       │   └── project/                   
│       │       ├── controller/            
│       │       ├── dto/                   
│       │       ├── entity/                
│       │       └── service/               
│       ├── exceptions/                    
│       ├── infrastructure/                # Integrações e recursos externos
│       │   ├── artificialintelligence/    # IA: geração de conteúdo, prompts, análises, etc.
│       │   └── gitea/                     # Integração com a API do Gitea
│       │       ├── auth/                  
│       │       ├── client/                # Requisições
│       │       ├── commit/                # Lógica de manipulação de commits
│       │       ├── repository/            # Acesso a dados Gitea
│       │       └── service/               # Regras de negócio Gitea
│       ├── repository/                    
│       ├── security/                      # Segurança, autenticação e autorização
│       ├── mapper/                        # MapStruct / conversores DTO ↔ entidade
│       └── CodefreelaapiApplication.java  
├── frontend/                              # Frontend (React.js)
├── gitea/                                 # Volume de dados persistentes do container Gitea
├── .env                                   
├── docker-compose.yml                     # Orquestração dos containers (API, Gitea, DB, Frontend)
└── README.md                              # Documentação do projeto

```

---

## 🗂️ Organização das Tarefas

A organização das tarefas do projeto foi realizada com base no modelo **Kanban**, utilizando o Trello para visualizar e acompanhar o progresso do desenvolvimento. As colunas representam os diferentes estágios do fluxo de trabalho, como:

* **Backlog**: ideias e funcionalidades planejadas;
* **Em andamento**: tarefas que estão sendo implementadas;
* **Concluído**: funcionalidades finalizadas;
* **Correção e Planejamento futuro**: para bugs e melhorias estruturais.

Essa abordagem ajudou a manter o controle e a transparência no ciclo de desenvolvimento, mesmo sendo um projeto individual.

---

## 👨‍💻 Autor

Desenvolvido por **Alan Pereira Cavalcante**
<br>
<br>
Linkedln: [linkedln.com/alan-pereira-dev](https://www.linkedin.com/in/alan-pereira-dev/)<br>
E-mail: alan.cavalcante.dev@gmail.com <br>
Celular: +55 (11) 986815754

---

## 📄 Licença

Este projeto está licenciado sob a Licença **MIT** apenas para fins de **visualização e estudo**. Para reutilização do código, entre em contato com o autor. Esse projeto está sendo feito com objetivo de estudos e aprendizado.





