# Etapa 1: build com Maven
FROM maven:3.9.4-eclipse-temurin-21 AS builder

# Define o diretório de trabalho no container
WORKDIR /app

# Copia os arquivos do projeto para o container
COPY pom.xml .
COPY src ./src

# Realiza o build do projeto (gera o .jar)
RUN mvn clean package -DskipTests

# Etapa 2: runtime com JDK leve
FROM eclipse-temurin:21-jdk-alpine

# Define o diretório onde o app será executado
WORKDIR /app

# Copia o .jar gerado para o container final
COPY --from=builder /app/target/*.jar app.jar

# Expõe a porta usada pela aplicação Spring Boot (geralmente 8080)
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
