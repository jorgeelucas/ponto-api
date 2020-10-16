
# ponto-api

API para desafio de marcação de ponto


## Arquitetura

* Maven
* Java 8
* Spring Boot
* Spring Data
* jUnit4
* Mock
* H2

## Execução

### Clone

```console
git clone https://github.com/jorgeelucas/ponto-api.git
cd ponto-api
```

### Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório *target* com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.

### Execução

Para executar o projeto com o  **Maven Spring Boot Plugin**, executar os comando abaixo:

```shell
mvn spring-boot:run
```

O comando irá rodar o projeto e subir na porta **8080**

### Testes

```console
mvn test
```
## Requisições
> Todas as requisições estão documentadas no swagger
> 
Qualquer registro de ponto possui o tipo do registro (**ENTRADA**, **INTERVALO_IDA**, **INTERVALO_VOLTA**, **SAIDA**) para simplificar os testes pois as batidas de ponto não usaram o horário automático. Caso informe um tipo diferente desses gerará um erro inesperado.

<details><summary><b>Payload (Clique aqui)</b></summary>

1. Cadastrar um novo registro: [**POST**]

    ```json
      {
        "data": "string",
        "hora": "string",
        "matriculaFuncionario":"string",
        "tipoRegistro": "string"
      }
    ```
    
2. Alterar registro por id: [**PUT**]

   ```json
      {
        "hora": "string",
        "tipoRegistro": "string",
      }
   ```

</details>

<details><summary><b>Rotas (Clique aqui)</b></summary>

> Utiliza-se o estilo RESTFull portanto todos os caminhos partem do path: **/api/v1/registros**

1. [**GET**]
	```
	Parametro = matricula:string
	```
	```
	Obtem todos os registros do funcionário por matricula.
	```
2. [**POST**]
	```json
      {
        "data": "string",
        "hora": "string",
        "matriculaFuncionario":"string",
        "tipoRegistro": "string"
      }
    ```
    ```
    Cadastra um novo registro
    ```
3. /relatorio [**GET**]
	```
	Parametro = matricula:string
	Parametro = mes:string
	```
	```
	Gera um relatório do funcionario no mes pela matricula e mês (janeiro, fevereiro...)
	```
4. /id [**DELETE**]
	```
	A variavel ID se refere ao ID do registro a ser deletado
	```
	```
	Deleta um registro da base pelo id
	```	
5. /id [**GET**]
	```
	A variavel ID se refere ao ID do registro a ser detalhado
	```
	```
	Detalha um registro da base pelo id
	```	
6. /id [**PUT**]
   ```json
      {
        "hora": "string",
        "tipoRegistro": "string",
      }
   ```
	```
	Altera um registro na base pelo id, mudando o tipo para a hora informada
	```

</details>

## Detalhes


1. Para comunicação foi usado a arquitetura REST, baseado no Restful. Os serviços recebem e respondem JSON

2. Na arquitetura foi usado a abordagem Package by Layer do Clean Arch (controllers, services, repositories)

3. Para banco de dados foi usado banco em memória h2, tanto para o projeto quanto para os testes.

4. Na documentação foi pensado no swagger por ser uma ferramenta de facil implementação e usabilidade.

5. Para testes foi usado Junit4, fazendo testes diretamente nos endpoints, testes de integração, para tentar simular mais uma chamada e as respostas mockados.

6. Nas responstas foi escolhido o objeto ResponseEntity do spring para gerenciar todo o objeto da resposta.

7. Para testes eu estava usando o POSTMAN com os endpoints informados acima.

>Obrigado pela oportunidade :)
