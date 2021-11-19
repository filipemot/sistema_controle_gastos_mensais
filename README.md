
# Sistema de Controle de Gastos Pessoais

**Última Atualização:** 19/11/2021

**Tecnologias Utilizadas:**

 - **Backend:** Java com Spring Boot
 - **Frontend:** Angular
- **Infraestrutura:** Docker
 -  **Banco de Dados:** Postgre

**Início do Projeto:** Outubro de 2021

**Modelo de Dados:**

![Modelo de dados](https://github.com/filipemot/sistema_controle_gastos_mensais/blob/main/database/database.png)

**Execução**

**Banco/Docker**

Entrar em backend/docker

    docker-compose -f banco_docker_compose.yml up -d

**Configuração Banco**

Entrar em backend/src/main/resources/application.properties

    spring.datasource.url=jdbc:postgresql://localhost:5432/controlegastos
    spring.datasource.username=postgres
    spring.datasource.password=postgres

**Criação do Container** 

 - Compilar Projeto: `mvn clean package`
 - Criar Imagem do Container: `docker build -f Dockerfile . -t sistema_controle_gastos_mensais`

**Criação do Container** 

- Entrar na pasta docker
- Executar `docker-compose -f aplicacao.yml down`

--------
**Tarefas Executadas**

- [x]  **Criação Modelo do Banco**

Dia(s) Trabalhado(s): 28/10/2021

- [x]  **Criação Estrutura Backend**

Dia(s) Trabalhado(s): 28/10/2021

- [x]  **Configuração Banco de Dados**

Dia(s) Trabalhado(s): 28/10/2021

- [x]  **Configuração Migração**

Dia(s) Trabalhado(s): 28/10/2021

- [x]  **Criação API - Tipo Conta - Base**

Dia(s) Trabalhado(s): 29/10/2021, 01/11/2021

- [x]  **Criação API - Usuário - Base**

Dia(s) Trabalhado(s): 01/11/2021, 04/11/2021

- [x]  **Criação API - Contas - Base**

Dia(s) Trabalhado(s): 04/11/2021

- [x]  **Criação API - Recebidos - Base**

Dia(s) Trabalhado(s): 19/11/2021

- [x]  **Alteração API - Tipo Conta - Alteração de Tipo de ID para guid**

Dia(s) Trabalhado(s): 17/11/2021

- [x]  **Alteração API - Usuário - Alteração de Tipo de ID para guid**

Dia(s) Trabalhado(s): 17/11/2021

- [x]  **Alteração API - Contas - Alteração de Tipo de ID para guid**

Dia(s) Trabalhado(s): 17/11/2021

- [x]  **Criação do container Docker da Aplicação**

Dia(s) Trabalhado(s): 19/11/2021

- [x]  **Load Tipo de Contas**

Dia(s) Trabalhado(s): 19/11/2021

--------

**Planejamento de Tarefas**

- [ ]  **Listar Contas por Tipo de Tarefa**

Dia(s) Trabalhado(s): 

- [ ]  **Listar Contas por Período**

Dia(s) Trabalhado(s): 

- [ ]  **Criar na tabela de usuário campo ativado/desativado**

Dia(s) Trabalhado(s): 

- [ ]  **Criar api para desativar usuário**

Dia(s) Trabalhado(s): 

- [ ]  **Validar Constraint de chave estrangeira ao deletar usuário com relacionamento**

Dia(s) Trabalhado(s):

