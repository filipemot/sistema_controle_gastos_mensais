
# Sistema de Controle de Gastos Pessoais

**Tecnologias Utilizadas:**

 - **Backend:** Java com Spring Boot
 - **Frontend:** Angular
- **Infraestrutura:** Docker
 -  **Banco de Dados:** Postgre

**Início do Projeto:** Outubro de 2021

**Modelo de Dados:**

![Modelo de dados](https://github.com/filipemot/sistema_controle_gastos_mensais/blob/main/database/database.drawio.png)

**Planejamento de Tarefas**

- **Criação Estrutura Backend** - OK
- **Configuração Banco de Dados** - OK
- **Configuração Migração** - OK
- **Criação API - Tipo Conta**
- **Criação API - Usuário**
- **Criação API - Contas**
- **Criação API - Recebidos**

**Execução**

**Banco/Docker**

Entrar em backend/docker

    docker-compose -f banco_docker_compose.yml up -d

**Configuração Banco**

Entrar em backend/src/main/resources/application.properties

    spring.datasource.url=jdbc:postgresql://localhost:5432/controlegastos
    spring.datasource.username=postgres
    spring.datasource.password=postgres

