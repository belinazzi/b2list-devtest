
![](/images/Aspose.Words.da622152-350b-4315-8ff2-c15ecb45e8e3.001.jpeg)![](Aspose.Words.da622152-350b-4315-8ff2-c15ecb45e8e3.002.png)
# **B2List - Full Stack Web Developer**

O objetivo deste desafio é avaliar as competências técnicas dos candidatos a desenvolvedor Full Stack Web.

Será solicitado o desenvolvimento de uma Aplicação que realize o Cadastro de Usuário. Regras e requisitos técnicos estão detalhadas neste documento.

**Especificações Técnicas**

- **Front End:** Reactjs utilizando Vite com Stiches, Storybook para documentação dos componentes
- **API:** Java RestFul - Spring Boot, JPA e JUnit 
- **Banco de Dados:**  MySQL ou SQL Server
- **Idioma de escrita do código:** Inglês
# **Requisitos**
Considere que uma Empresa que precisa de uma solução para cadastrar e gerenciar usuários que tem acesso ao sistema. 

O desafio consiste em criar uma aplicação para o cadastro de usuários conforme os critérios de aceitação.
## Mockups de interface
Abaixo alguns mockups de interface como um guia para a criação do front-end. Fique à vontade para usar sua criatividade e melhorias na criação do front-end.

Cadastros de Usuários/ Editar usuários

![](/images/Aspose.Words.da622152-350b-4315-8ff2-c15ecb45e8e3.003.png)

Listagem de Usuários/ Editar e Exclui

![](/images/Aspose.Words.da622152-350b-4315-8ff2-c15ecb45e8e3.004.png)
## Histórias do Usuário
- **Sendo** um usuário administrativo da Empresa
- **Quero** gerenciar cadastros de usuários
- **Para** que eu possa realizar a gestão dos usuários que tem acesso ao sistema
### Critérios de aceite:
#### *Cenário: cadastrar novo usuário*
- **Dado** que estou na tela de Listagem de Usuários
- **Quando** clico em Cadastrar Usuário
- **Então** abre a tela de Cadastro do Usuário
- **E** exibe os campos obrigatórios vazios
- **Dado** que inseri dados válidos nos campos
- **Quando** clico em Salvar
- **Então** cria o novo usuário na base
- **E** retorna mensagem de sucesso
- **Caso** os campos não estejam preenchidos
- **Apresenta** mensagem de obrigatoriedade
- **Caso** o CPF seja invalido
- **Apresenta** mensagem de CPF invalido
- **Caso** o e-mail seja invalido
- **Apresenta** mensagem de e-mail invalido
- **Dado** que inseri dados válidos nos campos
- **Quando** clico em Cancelar
- **Então** retorna para tela Listagem de Usuários
- **E** não persiste a gravação dos dados no banco
#### *Cenário: listar usuários cadastrados*
- **Dado** que estou no Módulo de Gestão da Empresa
- **Quando** clico no menu Usuários
- **Então** abre a tela de Listagem de Usuários
- **E** exibe opção Cadastrar Usuários ao topo
- **E** lista dados dos usuários cadastrados
- **E** exibe opção Editar por usuários
- **E** exibe opção Excluir por usuários
#### *Cenário editar cadastro de usuários*
- **Dado** que estou na listagem de usuários
- **Quando** clico em Editar usuários
- **Então** abre a tela de Cadastro de usuários
- **E** exibe os campos do cadastro preenchidos
- **E** habilita alteração dos campos editáveis
- **Caso** o e-mail seja invalido
- **Apresenta** mensagem de e-mail invalido
- **Dado** que estou na tela de Cadastro de usuários
- **Quando** clica em Salvar
- **Então** grava os dados editáveis na base
- **Dado** que estou na tela de Cadastro de usuários
- **Quando** clica em Cancelar
- **Então** retorna para a tela de Listagem de usuários
- **E** não persiste a gravação dos dados
#### *Cenário: excluir cadastro de usuários*
- **Dado** que estou na listagem de usuários
- **Quando** clico em Excluir usuários
- **Então** exibe a modal de confirmação de exclusão
- **Dado** que estou na modal de confirmação de exclusão
- **Quando** clico em Confirmar
- **Então** exclui o registro do usuário
- **Dado** que estou na modal de confirmação de exclusão
- **Quando** clico em Cancelar
- **Então** fecha a modal e não persiste a exclusão
## Campos obrigatórios:
- **Nome** (editável)
- **Email** (editável)
- **Usuário** (não editável) 
- **CPF** (não editável) (chave única)
# **Desejável**
- Testes unitários
- Documentação da arquitetura de solução
- Documentação da API (swagger)
# **Critérios de avaliação**
- Qualidade de escrita do código
- Organização do projeto
- Qualidade da API
- Lógica da solução implementada
- Qualidade da camada de persistência
- Utilização do Git (quantidade e descrição dos commits, Git Flow, ...)
# **Instruções de entrega**
1. Crie um fork do repositório no seu GitHub
1. Inclua um arquivo chamado COMMENTS.md explicando
- Decisão da arquitetura utilizada
- Lista de bibliotecas de terceiros utilizadas
- O que você melhoraria se tivesse mais tempo
- Quais requisitos obrigatórios que não foram entregues
4. Informe ao recrutador quando concluir o desafio junto com o link do repositório



![](/images/Aspose.Words.da622152-350b-4315-8ff2-c15ecb45e8e3.005.png)
