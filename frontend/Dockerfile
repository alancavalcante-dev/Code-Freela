# Etapa de build
FROM node:23 AS build

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando os arquivos do projeto para o container
COPY . .

# Instalando todas as dependências do projeto
RUN npm install

# Rodando o Vite para gerar o build
RUN npm run build

# Etapa de produção (com Vite rodando)
FROM node:23 AS production

WORKDIR /app

# Copiando apenas os arquivos necessários para rodar o app
COPY --from=build /app /app

# Instalando as dependências de produção e as devDependencies necessárias
RUN npm install --production=false

# Expondo a porta em que o Vite vai rodar
EXPOSE 5173

# Iniciando o servidor de desenvolvimento do Vite e permitindo acesso externo
CMD ["npm", "run", "dev", "--", "--host"]
