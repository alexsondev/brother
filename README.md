
# fluig-brother 📝  

Repositório Fluig do projeto Brother


## Pré-requisitos

- [VS Code](https://code.visualstudio.com/)
~~~bash  
choco install vscode
~~~

- [Node.js](https://nodejs.org/en/download)
~~~bash  
choco install nodejs.install
~~~

- [Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
~~~bash  
choco install git
~~~

- [Gulp](https://gulpjs.com/docs/en/getting-started/quick-start/)
~~~bash  
npm install --global gulp-cli
~~~

- [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)

Após instalação do Java, configure a variável de ambiente
`JAVA_HOME`  apontando para o diretório onde o Java foi instalado

## Dependências

Na raiz do projeto, instale as dependências. É necessário executar este comando apenas uma vez.
~~~bash  
npm install
~~~


## Git Flow
Este projeto utiliza o modelo git flow para controle do fluxo de desenvolvimento e manutenção.

#### Pacote Git Flow
Para facilitar a aplicação do fluxo foi instalado o pacote git-flow. Abaixo link que explica o funcionamento do modelo e a aplicação do pacote:

- [Fluxo de trabalho de Gitflow](https://www.atlassian.com/br/git/tutorials/comparing-workflows/gitflow-workflow)


#### Plugin VS Code  
Também foi instalado o plugin no VS Code que inclui uma interface para utilização do modelo

![Modelo Git Flow](.vscode/img/vscode.png)  


## Deploy

O deploy é realizado automaticamente sempre que um commit é realizado ou ainda uma feature, uma hotfix ou uma release são finalizadas.

Neste deploy, apenas os arquivos alterados serão compilados.

Os arquivos compilados ficarão no diretório deploy. Este diretório é apenas local, ou seja, não é enviado para o repositório.

É possível realizar o deploy fora desses fluxos. Para isso execute na raiz do projeto:

#### Deploy assistido 
Irá compilar todos os artefatos do diretório src, e enviará para o diretório src. Continuará em execução ao final da execução, observando as alterações na pasta src e recompilando sempre que uma alteração for feita

~~~bash  
npm start
~~~

**Obs:** o componente java não é compilado no deploy assistido. Para compila-lo execute o deploy por artefato, descrito abaixo

#### Deploy por artefatos 
Irá compilar todos os artefatos do tipo informado

- Java
~~~bash  
mvn clean install -f src/java/pom.xml
~~~


- Datasets
~~~bash  
gulp datasets
~~~
- Formulários
~~~bash  
gulp forms
~~~
- Workflow
~~~bash  
gulp workflow
~~~
- Widgets
~~~bash  
gulp widget
~~~
