package io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.service;


import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Deploy;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.Environment;
import io.github.alancavalcante_dev.codefreelaapi.domain.worker.deploy.entity.PortExpose;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.StringJoiner;

@Service
public class DockerfileGenerator {


    public String generateDockerfile(Deploy deploy, String giteaHost, String repoOwner, String repoName) {
        Language language = deploy.getLanguage();
        String languageVersion = deploy.getLanguageVersion();

        return switch (language) {
            case JAVA -> generateJavaDockerfile(languageVersion, giteaHost, repoOwner, repoName, deploy);
            case EXPRESS_JS, TYPESCRIPT_NODE -> generateNodeJsBackendDockerfile(languageVersion, giteaHost, repoOwner, repoName, deploy);
            case PYTHON -> generatePythonDockerfile(languageVersion, giteaHost, repoOwner, repoName, deploy);
            case REACT, VUE_JS -> generateSpaFrontendDockerfile(languageVersion, giteaHost, repoOwner, repoName, deploy);
            case NEXT_JS -> generateNextJsDockerfile(languageVersion, giteaHost, repoOwner, repoName, deploy);
            case STATIC -> generateStaticSiteDockerfile(giteaHost, repoOwner, repoName);
            case JAVASCRIPT -> generateStaticSiteDockerfile(giteaHost, repoOwner, repoName);
            default -> throw new IllegalArgumentException("Linguagem não suportada: " + language);
        };
    }

    public String generateSimpleTestDockerfile() {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("FROM nginx:stable-alpine");
        dockerfile.add("RUN echo '<h1>Build de teste funcionou!</h1>' > /usr/share/nginx/html/index.html");
        dockerfile.add("EXPOSE 80");
        dockerfile.add("CMD [\"nginx\", \"-g\", \"daemon off;\"]");
        return dockerfile.toString();
    }


    private String generateJavaDockerfile(String version, String giteaHost, String repoOwner, String repoName, Deploy deploy) {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("# Estágio 1: Build com Maven");
        dockerfile.add("FROM eclipse-temurin:" + version + "-jdk-jammy AS builder");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("RUN apt-get update && apt-get install -y git");
        dockerfile.add("WORKDIR /app");
        dockerfile.add(String.format("RUN git clone https://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("RUN ./mvnw package -DskipTests");
        dockerfile.add("");
        dockerfile.add("# Estágio 2: Imagem final de execução");
        dockerfile.add("FROM eclipse-temurin:" + version + "-jre-jammy");
        dockerfile.add("WORKDIR /app");
        dockerfile.add("COPY --from=builder /app/target/*.jar app.jar");

        addEnvironmentAndPorts(dockerfile, deploy.getVariableEnvironments(), deploy.getPortsExposes());

        dockerfile.add(String.format("ENTRYPOINT %s", deploy.getEntrypoint())); // Ex: ENTRYPOINT ["java", "-jar", "app.jar"]
        return dockerfile.toString();
    }

    private String generateNodeJsBackendDockerfile(String version, String giteaHost, String repoOwner, String repoName, Deploy deploy) {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("# Estágio 1: Build (se for TypeScript)");
        dockerfile.add("FROM node:" + version + "-alpine AS builder");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("RUN apk add --no-cache git");
        dockerfile.add("WORKDIR /app");
        dockerfile.add(String.format("RUN git clone https://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("RUN npm install");
        dockerfile.add("RUN npm run build"); // Assume que existe um script 'build' para compilar TS
        dockerfile.add("");
        dockerfile.add("# Estágio 2: Produção");
        dockerfile.add("FROM node:" + version + "-alpine");
        dockerfile.add("WORKDIR /app");
        dockerfile.add("COPY --from=builder /app/package*.json ./");
        dockerfile.add("RUN npm install --production");
        dockerfile.add("COPY --from=builder /app/dist ./dist");
        dockerfile.add("USER node");

        addEnvironmentAndPorts(dockerfile, deploy.getVariableEnvironments(), deploy.getPortsExposes());

        dockerfile.add(String.format("CMD %s", deploy.getEntrypoint())); // Ex: CMD ["node", "dist/server.js"]
        return dockerfile.toString();
    }

    private String generatePythonDockerfile(String version, String giteaHost, String repoOwner, String repoName, Deploy deploy) {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("FROM python:" + version + "-slim");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("RUN apt-get update && apt-get install -y git");
        dockerfile.add("WORKDIR /app");
        dockerfile.add(String.format("RUN git clone https://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("RUN pip install --no-cache-dir -r requirements.txt");

        addEnvironmentAndPorts(dockerfile, deploy.getVariableEnvironments(), deploy.getPortsExposes());

        dockerfile.add(String.format("CMD %s", deploy.getEntrypoint())); // Ex: CMD ["python", "./main.py"]
        return dockerfile.toString();
    }

    private String generateSpaFrontendDockerfile(String version, String giteaHost, String repoOwner, String repoName, Deploy deploy) {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("# Estágio 1: Build com Node.js");
        dockerfile.add("FROM node:" + version + "-alpine AS builder");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("RUN apk add --no-cache git");
        dockerfile.add("WORKDIR /app");
        dockerfile.add(String.format("RUN git clone https://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("RUN npm install");
        dockerfile.add("RUN npm run build");
        dockerfile.add("");
        dockerfile.add("# Estágio 2: Servidor Web com Nginx");
        dockerfile.add("FROM nginx:stable-alpine");
        dockerfile.add("COPY --from=builder /app/dist /usr/share/nginx/html"); // Para Vue/Vite
        dockerfile.add("# COPY --from=builder /app/build /usr/share/nginx/html # Para Create-React-App");
        dockerfile.add("# Lembre-se de commitar um nginx.conf para o roteamento de SPA!");
        dockerfile.add("EXPOSE 80");
        dockerfile.add("CMD [\"nginx\", \"-g\", \"daemon off;\"]");
        return dockerfile.toString();
    }

    private String generateNextJsDockerfile(String version, String giteaHost, String repoOwner, String repoName, Deploy deploy) {
        StringJoiner dockerfile = new StringJoiner("\n");
        dockerfile.add("# Estágio 1: Build");
        dockerfile.add("FROM node:" + version + "-alpine AS builder");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("RUN apk add --no-cache git");
        dockerfile.add("WORKDIR /app");
        dockerfile.add(String.format("RUN git clone https://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("RUN npm install");
        dockerfile.add("RUN npm run build");
        dockerfile.add("");
        dockerfile.add("# Estágio 2: Produção");
        dockerfile.add("FROM node:" + version + "-alpine");
        dockerfile.add("WORKDIR /app");
        dockerfile.add("ENV NODE_ENV production");
        dockerfile.add("COPY --from=builder /app/public ./public");
        dockerfile.add("COPY --from=builder --chown=node:node /app/.next/standalone ./");
        dockerfile.add("COPY --from=builder --chown=node:node /app/.next/static ./.next/static");
        dockerfile.add("USER node");

        addEnvironmentAndPorts(dockerfile, deploy.getVariableEnvironments(), deploy.getPortsExposes());

        dockerfile.add(String.format("CMD %s", deploy.getEntrypoint())); // Ex: CMD ["node", "server.js"]
        return dockerfile.toString();
    }

    private String generateStaticSiteDockerfile(String giteaHost, String repoOwner, String repoName) {
        StringJoiner dockerfile = new StringJoiner("\n");

        // --- Estágio 1: O "Clonador" ---
        // Usa uma imagem base leve do Alpine com Git apenas para clonar o repositório.
        dockerfile.add("FROM alpine/git:latest AS cloner");
        dockerfile.add("");
        dockerfile.add("ARG GITEA_TOKEN");
        dockerfile.add("");
        dockerfile.add("ARG CACHE_BUSTER");
        dockerfile.add("");
        dockerfile.add("WORKDIR /app");
        dockerfile.add("");
        dockerfile.add("RUN echo \"Busting cache with ${CACHE_BUSTER}\"");
        dockerfile.add("");
        dockerfile.add(String.format("RUN git clone http://oauth2:${GITEA_TOKEN}@%s/%s/%s.git .", giteaHost, repoOwner, repoName));
        dockerfile.add("");

        // --- Estágio 2: O Servidor Nginx Final ---
        // Começamos do zero com uma imagem limpa do Nginx.
        dockerfile.add("FROM nginx:stable-alpine");
        dockerfile.add("");
        dockerfile.add("COPY --from=cloner /app /usr/share/nginx/html");
        dockerfile.add("");
        dockerfile.add("EXPOSE 80");
        dockerfile.add("CMD [\"nginx\", \"-g\", \"daemon off;\"]");

        return dockerfile.toString();
    }


    private void addEnvironmentAndPorts(StringJoiner dockerfile, List<Environment> environments, List<PortExpose> ports) {
        if (environments != null && !environments.isEmpty()) {
            dockerfile.add("");
            environments.forEach(env -> dockerfile.add(String.format("ENV %s=\"%s\"", env.getKey(), env.getValue())));
        }
        if (ports != null && !ports.isEmpty()) {
            dockerfile.add("");
            ports.forEach(p -> dockerfile.add(String.format("EXPOSE %s", p.getPort())));
        }
        dockerfile.add("");
    }
}