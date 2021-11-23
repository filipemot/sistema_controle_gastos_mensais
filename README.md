# Sistema de Controle de Gastos Pessoais

**Última Atualização:** 23/11/2021

**Tecnologias Utilizadas:**

- **Backend:** Java com Spring Boot
- **Frontend:** Angular
- **Infraestrutura:** Docker
- **Banco de Dados:** Postgre

**Início do Projeto:** Outubro de 2021

**Modelo de Dados:**

![https://github.com/filipemot/sistema_controle_gastos_mensais/blob/main/database/database.png](https://github.com/filipemot/sistema_controle_gastos_mensais/blob/main/database/database.png)

**Execução**

**Banco/Docker**

Entrar em backend/docker

```
docker-compose -f banco_docker_compose.yml up -d
```

**Configuração Banco**

Entrar em backend/src/main/resources/application.properties

```yaml
spring.datasource.url=jdbc:postgresql://localhost:5432/controlegastos
spring.datasource.username=postgres
spring.datasource.password=postgres
```

**Criação da Imagem da Aplicação em Container**

- Compilar Projeto: `mvn clean package`
- Criar Imagem do Container: `docker build -f Dockerfile . -t sistema_controle_gastos_mensais`

**Criação do Container**

- Entrar na pasta backend/docker
- Executar `docker-compose -f aplicacao.yml down`

# API's Disponibilizadas

## Tipo de Conta

**Listar Todas**

**GET** [http://localhost:8080/api/tipoconta](http://localhost:8080/api/tipoconta)

**Pesquisar por ID**

**GET** [http://localhost:8080/api/tipoconta/{id}](http://localhost:8080/api/tipoconta/%7Bid%7D)

**Salvar**

**POST** [http://localhost:8080/api/tipoconta](http://localhost:8080/api/tipoconta)

**Body**

```json
{
	"descricao":"Supermercado"
}
```

**Atualizar**

**PUT** [http://localhost:8080/api/tipoconta/{id}](http://localhost:8080/api/tipoconta/%7Bid%7D)

**Body**

```json
{
    "descricao":"Supermercado"
}
```

**Deletar**

**DEL** [http://localhost:8080/api/tipoconta/{id}](http://localhost:8080/api/tipoconta/%7Bid%7D)

## Usuário

**Listar Todas**

**GET** [http://localhost:8080/api/usuario](http://localhost:8080/api/usuario)

**Pesquisar por ID**

**GET** [http://localhost:8080/api/usuario/{id}](http://localhost:8080/api/usuario/%7Bid%7D)

**Salvar**

**POST** [http://localhost:8080/api/usuario](http://localhost:8080/api/usuario)

**Body**

```json
{

    "nome":"nome2",
    "senhaUsuario": "123456",
    "email": "teste@teste222.com"
}
```

**Atualizar**

**PUT** [http://localhost:8080/api/usuario/{id}](http://localhost:8080/api/usuario/%7Bid%7D)

**Body**

```json
{
    "nome":"nome2",
    "senhaUsuario": "123456",
    "email": "teste@teste222.com"
}
```

**Deletar**

**DEL** [http://localhost:8080/api/usuario/{id}](http://localhost:8080/api/usuario/%7Bid%7D)

## Contas

**Listar Todas**

**GET** [http://localhost:8080/api/conta](http://localhost:8080/api/conta)

**Listar Todas - Por ID**

**GET** [http://localhost:8080/api/conta/{](http://localhost:8080/api/conta/tipoconta/%7Bid_tipo_conta%7D)id}

**Listar Todas - Por Tipo de Conta**

**GET** [http://localhost:8080/api/conta/tipoconta/{id_tipo_conta}](http://localhost:8080/api/conta/tipoconta/%7Bid_tipo_conta%7D)

**Listar Todas - Por Tipo de Conta**

**GET** [http://localhost:8080/api/conta/tipoconta/{id_tipo_conta}](http://localhost:8080/api/conta/tipoconta/%7Bid_tipo_conta%7D)

**Listar Todas - Por Tipo de Conta, Mês e Ano**

**GET** [http://localhost:8080/api/conta/tipoconta/{id_tipo_conta}/{mes}/{ano}](http://localhost:8080/api/conta/tipoconta/%7Bid_tipo_conta%7D/%7Bmes%7D/%7Bano%7D)

**Salvar**

**POST** [http://localhost:8080/api/conta](http://localhost:8080/api/conta)

**Body**

```json
{
    "usuario":{
	    "id": "569b4be9-146b-4386-a95a-b7e028320e35"
    }, "tipoConta":{
        "id": "9ad2f22d-3489-48ea-87bf-40c8808b2f0d"
    },
    "dataConta":"2021-11-04 17:35:55",
    "mesConta": 11,
    "anoConta": 2021,
    "descricao": "Conta teste",
    "valor": 10.50,
    "numeroParcela": 1,
    "totalParcelas": 1,
    "recorrente": false
 }
```

**Atualizar**

**PUT** [http://localhost:8080/api/conta/{id}](http://localhost:8080/api/conta/%7Bid%7D)

**Body**

```json
  {
    "usuario":{
	    "id": "569b4be9-146b-4386-a95a-b7e028320e35"
    }, "tipoConta":{
        "id": "9ad2f22d-3489-48ea-87bf-40c8808b2f0d"
    },
    "dataConta":"2021-11-04 17:35:55",
    "mesConta": 11,
    "anoConta": 2021,
    "descricao": "Conta teste",
    "valor": 10.50,
    "numeroParcela": 1,
    "totalParcelas": 1,
    "recorrente": false
 }
```

**Deletar**

**DEL** [http://localhost:8080/api/conta/{id}](http://localhost:8080/api/conta/%7Bid%7D)

## Recebidos

**Listar Todas**

**GET** [http://localhost:8080/api/recebidos](http://localhost:8080/api/recebidos)

**Listar Todas - Por ID**

**GET** [http://localhost:8080/api/recebidos/{id}](http://localhost:8080/api/recebidos/%7Bid%7D)

**Salvar**

**POST** [http://localhost:8080/api/recebidos](http://localhost:8080/api/recebidos)

**Body**

```json
  {
    "usuario":{
	"id": "569b4be9-146b-4386-a95a-b7e028320e35"
    },
    "dataConta":"2021-11-04 17:35:55",
    "mesConta": 11,
    "anoConta": 2021,
    "descricao": "Conta teste",
    "valor": 10.50
 }
```

**Atualizar**

**PUT** [http://localhost:8080/api/recebidos/{id}](http://localhost:8080/api/recebidos/%7Bid%7D)

**Body**

```json
  {
    "usuario":{
	"id": "569b4be9-146b-4386-a95a-b7e028320e35"
    },
    "dataConta":"2021-11-04 17:35:55",
    "mesConta": 11,
    "anoConta": 2021,
    "descricao": "Conta teste",
    "valor": 10.50
 }
```

**Deletar**

**DEL** [http://localhost:8080/api/recebidos/{id}](http://localhost:8080/api/recebidos/%7Bid%7D)

# Tarefas Executadas

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

- [x]  **Listar Contas por Tipo de Tarefa**

Dia(s) Trabalhado(s): 22/11/2021

- [x]  **Listar Contas por Período e Tipo de Conta**

Dia(s) Trabalhado(s): 22/11/2021

---

**Planejamento de Tarefas**

- [ ]  **Listar Todas Contas por Período**

Dia(s) Trabalhado(s):

- [ ]  **Listar Todas Contas por Período Inicial e Final**

Dia(s) Trabalhado(s):

- [ ]  **Refatorar Testes evitando duplicação**

Dia(s) Trabalhado(s):

- [ ]  **Refatorar Services evitando duplicação**

Dia(s) Trabalhado(s):

- [ ]  **Ajuste Validaçãpo de Email, para validar na API utlizando o @Valid**

Dia(s) Trabalhado(s):

- [ ]  **Alterar em recebidos o nome de mesConta e anoConta para mesRecebidos e anoRecebidos**

Dia(s) Trabalhado(s):

- [ ]  **Criar na tabela de usuário campo ativado/desativado**

Dia(s) Trabalhado(s):

- [ ]  **Criar api para desativar usuário**

Dia(s) Trabalhado(s):

- [ ]  **Validar Constraint de chave estrangeira ao deletar usuário com relacionamento**

Dia(s) Trabalhado(s):

---
