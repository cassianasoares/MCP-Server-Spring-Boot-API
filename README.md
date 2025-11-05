# MCP Server Spring Boot API - YouTube Subtitle Service

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring AI](https://img.shields.io/badge/Spring%20AI-1.0.1-blue.svg)](https://spring.io/projects/spring-ai)
[![Docker](https://img.shields.io/badge/Docker-Hub-2496ED.svg?logo=docker&logoColor=white)](https://hub.docker.com/r/cassias18/mcp-server-subtube)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

Um servidor MCP (Model Context Protocol) construído com Spring Boot que fornece funcionalidades para obter legendas de vídeos do YouTube através de múltiplas APIs.

## 📋 Sobre o Projeto

Este projeto implementa um servidor MCP usando Spring AI que expõe ferramentas (tools) para assistentes de IA consumirem. O foco principal é fornecer acesso a legendas de vídeos do YouTube em diferentes idiomas e formatos.

### O que é MCP?

O Model Context Protocol (MCP) é um protocolo que permite que modelos de IA interajam com ferramentas externas de forma padronizada. Este servidor expõe uma ferramenta `get_subtitle` que pode ser consumida por assistentes de IA como Claude, GPT, etc.

## ✨ Funcionalidades

- 🎯 **Ferramenta MCP**: Expõe a ferramenta `get_subtitle` para assistentes de IA
- 🌐 **Múltiplas APIs**: Implementa estratégia de fallback entre diferentes APIs de legendas
- 🔄 **SSE (Server-Sent Events)**: Suporta comunicação em tempo real via SSE
- 📝 **Formatos Múltiplos**: Suporta diferentes formatos de texto para legendas
- 🌍 **Multi-idioma**: Permite buscar legendas em diferentes idiomas

## 🚀 Tecnologias Utilizadas

- **Spring Boot 3.5.4**: Framework principal
- **Spring AI 1.0.1**: Integração com MCP
- **Java 17**: Versão do JDK
- **Spring Web**: Para endpoints REST
- **Spring Validation**: Validação de dados
- **Jackson**: Serialização/deserialização JSON
- **RestTemplate**: Cliente HTTP para chamadas de API

## 📦 Requisitos

- Java 17 ou superior
- Maven 3.6+
- Uma ou mais APIs de legendas configuradas
- Docker (opcional, para executar via container)

## ⚙️ Configuração


### Configuração do MCP Server

O servidor está configurado com as seguintes características:

- **Tipo**: Síncrono
- **SSE Endpoint**: `/api/v1/sse`
- **MCP Message Endpoint**: `/api/v1/mcp`
- **Capabilities**: Apenas `tool` habilitado
- **Tool Change Notification**: Ativado

## 🔧 Instalação

### Opção 1: Executar com Docker (Recomendado)

A forma mais rápida de começar é usando a imagem Docker oficial:

```bash
# Baixar e executar a imagem
docker pull cassias18/mcp-server-subtube:latest

# Executar o container
docker run -d \
  -p 8060:8060 \
  --name mcp-subtube \
  cassias18/mcp-server-subtube:latest
```

**Com docker-compose:**

```yaml
version: '3.8'

services:
  mcp-subtube:
    image: cassias18/mcp-server-subtube:latest
    container_name: mcp-subtube
    ports:
      - "8060:8060"
    restart: unless-stopped
```

Execute com:
```bash
docker-compose up -d
```

**Verificar se está rodando:**
```bash
curl http://localhost:8060/actuator/health
```

### Opção 2: Compilar do Código Fonte

1. Clone o repositório:
```bash
git clone https://github.com/cassianasoares/MCP-Server-Spring-Boot-API.git
cd MCP-Server-Spring-Boot-API
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

Ou execute o JAR gerado:
```bash
java -jar target/mcpservicetube-0.0.1-SNAPSHOT.jar
```

### Opção 3: Criar sua Própria Imagem Docker

Se você fez modificações no código e quer criar sua própria imagem:

```dockerfile
# Dockerfile
FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8060
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Construa e execute:
```bash
docker build -t mcp-server-subtube:local .
docker run -p 8060:8060 mcp-server-subtube:local
```

## 📖 Uso

### Endpoint REST

O servidor expõe um endpoint REST que também pode ser usado diretamente:

```http
GET /sub?video_id={VIDEO_ID}&lang={LANGUAGE}&text_format={FORMAT}
```

**Parâmetros:**
- `video_id`: ID do vídeo do YouTube
- `lang`: Código do idioma (ex: `en`, `pt`, `es`)
- `text_format`: Formato do texto da legenda (ex: `srt`, `vtt`, `txt`)

**Exemplo:**
```bash
curl "http://localhost:8060/sub?video_id=dQw4w9WgXcQ&lang=en&text_format=srt"
```

### Usando como MCP Tool

Quando integrado com um assistente de IA compatível com MCP, a ferramenta `get_subtitle` estará disponível automaticamente:

**Nome da Ferramenta**: `get_subtitle`

**Descrição**: Get subtitle for a video

**Parâmetros**:
- `video_id`: ID do vídeo
- `lang`: Idioma desejado
- `text_format`: Formato do texto

### Endpoints SSE

Para integração via Server-Sent Events:
- **SSE Endpoint**: `http://localhost:8060/api/v1/sse`
- **MCP Messages**: `http://localhost:8060/api/v1/mcp`

## 🏗️ Arquitetura

### Estrutura do Projeto

```
com.demo.mcpservicetube
├── controller
│   └── SubController.java          # Controlador REST e Tool MCP
├── service
│   └── SubtitleService.java        # Lógica de negócio
├── strategy
│   └── RequestApi.java             # Interface para estratégias de API
├── model
│   └── Video.java                  # Modelo de dados
└── McpservicetubeApplication.java  # Classe principal
```

### Padrões Implementados

- **Strategy Pattern**: Para suportar múltiplas APIs de legendas com fallback automático
- **Dependency Injection**: Gerenciamento de dependências via Spring
- **Tool Callback Provider**: Exposição de métodos como ferramentas MCP

## 🔍 Logging

O projeto está configurado com logging detalhado para o protocolo MCP:

```properties
logging.level.io.modelcontextprotocol=TRACE
logging.level.org.springframework.ai.mcp=TRACE
```

Isso permite debugar facilmente a comunicação MCP e as chamadas de ferramentas.

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👤 Autor

**Cassiana Soares**
- GitHub: [@cassianasoares](https://github.com/cassianasoares)

## 📞 Suporte

Para reportar bugs ou solicitar features, por favor abra uma [issue](https://github.com/cassianasoares/MCP-Server-Spring-Boot-API/issues).

---

⭐ Se este projeto foi útil para você, considere dar uma estrela no repositório!
