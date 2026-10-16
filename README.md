# TodoSimple API

##  Sobre o Projeto

A TodoSimple API é uma aplicação REST desenvolvida com Java e Spring Boot seguindo a arquitetura MVC (Model-View-Controller), criada com o objetivo de aprofundar conhecimentos em desenvolvimento back-end, APIs RESTful, persistência de dados e boas práticas de engenharia de software.

O projeto simula um sistema de gerenciamento de tarefas onde usuários podem ser cadastrados e, futuramente, gerenciar suas próprias listas de atividades.

Mais do que uma simples API de tarefas, este projeto serve como laboratório de aprendizado contínuo para explorar recursos avançados do ecossistema Spring.

---

##  Tecnologias Utilizadas

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Jakarta Validation
* Jackson
* Arquitetura MVC
* REST API

---

##  Estrutura do Projeto

O projeto foi organizado seguindo os princípios da arquitetura MVC:

```text
src
 ├── controllers
 ├── services
 ├── repositories
 ├── models
 ├── exceptions
 └── config
```

### Responsabilidades

* **Controllers:** recebem as requisições HTTP.
* **Services:** concentram as regras de negócio.
* **Repositories:** realizam o acesso aos dados.
* **Models:** representam as entidades da aplicação.
* **Config:** configurações gerais do sistema.
* **Exceptions:** tratamento centralizado de erros.

---

##  Funcionalidades Implementadas

### Usuários

* Cadastro de usuários
* Busca de usuários
* Atualização de usuários
* Exclusão de usuários
* Validações utilizando Jakarta Validation
* Proteção da senha com `@JsonProperty(WRITE_ONLY)`

### Persistência

* Integração com banco de dados relacional
* Mapeamento objeto-relacional utilizando JPA/Hibernate

---

##  Objetivos de Aprendizado

Este projeto está sendo desenvolvido para consolidar conhecimentos em:

* Programação Orientada a Objetos
* Desenvolvimento de APIs REST
* Spring Boot
* JPA/Hibernate
* Tratamento de exceções
* Validação de dados
* Boas práticas de arquitetura
* Testes automatizados
* Segurança com Spring Security

---





##  Evolução do Projeto

Este repositório representa minha jornada de aprendizado no ecossistema Java e Spring. Cada nova funcionalidade adicionada busca aproximar o projeto de um ambiente profissional, aplicando conceitos utilizados no mercado de desenvolvimento back-end.

---


GitHub: https://github.com/DavidSia16
LinkedIn: https://www.linkedin.com/in/david-alves-2629b4341/
