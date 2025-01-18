# Projeto de exemplo para desenvolvimento de apps compatíveis com a TOTVS Store
---

A partir da release 1.6.2 do Fluig, será possível instalar os componentes diretamente na Store, mais especificamente através da página "[Itens da Store](http://tdn.totvs.com/x/yUwXEQ)" dentro da plataforma.

Esse projeto é um exemplo de componente, já na estrutura correta, que deve ser seguido caso o desenvolvedor deseje criar um componente e disponibilizá-lo como aplicativo para venda na TOTVS Store.
O projeto está construído utilizando o gerenciador de projeto/dependências [Apache Maven](https://maven.apache.org/). Quanto ao desenvolvimento HTML, JS e freemarker, Mustache para a widget. Está incluído também a instrução para a criação de uma nova página, com um novo layout e já com essa nova widget adicionada.

O layout customizado é uma maneira de criar uma página sem o menu tradicional do Fluig. 

O projeto ainda conta com uma implementação de uma API Rest em Java.
A estrutura está definida da seguinte maneira:

###custom-component
###### custom-component-pack
###### custom-component-config
###### custom-component-entity
###### custom-component-service
###### custom-component-rest
###### custom-component-widget
###### custom-component-widget-menu
###### custom-component-layout
###### custom-component-layout-custom
###### pom.xml
---

* **custom-component-pack**: projeto responsável por empacotar todo o custom-component em um arquivo do tipo .ear.

	O package do projeto deve ser do tipo **.EAR** (Enterprise Application Archive).
	Para essa configuração, no arquivo custom-component/custom-component-pack/pom.xml a tag <packing> deve estar assim: **<packaging>ear</packaging>**.
---


* **custom-component-config**: projeto onde estão os arquivos de configuração do componente:

	Para que o componente seja instalado corretamente, é necessário o arquivo **component.xml**.
	Esse arquivo se encontra em custom-component-config/src/main/resources/component.xml.
	É obrigatório que o **component code** seja único: **component code="custom_component_app"**.
	
	Para criar uma ou mais páginas e já disponibilizar a(s) widget(s), é necessário o arquivo **pages.xml**.
	Esse arquivo se encontra em custom-component-config/src/main/resources/pages.xml.
	Lembrando que o código da página precisa ser único: ex.: **<code>custom-component-page</code>**.
	
	O ícone da página criada, pode ser encontrado em custom-component-config/src/main/webapp/resources/images/custom-component.png.
---

* **custom-component-entity**: projeto responsável em criar tabelas no banco de dados, dedicadas apenas a esse app

    A partir da release do Fluig 1.6.5-190514 ou superior, está disponível a criação de tabelas customizadas do app no banco de dados do fluig. Com isso, será possível ter uma estrutura própria, com serviços dedicados e exclusivos para seu app. Isso facilita o armazenamento de alguns dados, sem a necessidade de utilizar formulários ou até mesmo um serviço externo. 
	
	Para utilizar essa funcionalidade, algumas diretrizes são obrigatórias:
	
		- DataSource name: AppDS
		- Não criar relacionamento com as tabelas da plataforma (PK, FK, view, trigger, índices, etc)		
		- Não inserir/alterar/remover registros das tabelas do fluig. Para isso, utilize as API, WebServices e/ou SDK
		- Criar índices para as tabelas do app
		- No nome das tabelas, adicionar o prefixo com o nome da empresa/parceiro + “_”. Ex.: XYZ Company: XYZ_my_table
		- Não utilizar prefixo de tabelas do fluig. Ex.: fdn, wcm, social, etc...
		
    Algumas recomendações:
    
        - Utilizar limit e offset nas consultas que buscam registros
        - Ao criar uma consulta/select, especificar as colunas (evite utilizar select * from)
        - Criar um VO específico para retorno invés de retornar a própria entidade
        - Utilize padrões de projetos em seu desenvolvimento: DAO, Factory, Builder, Chain of Responsibility...
        
    Esse projeto é um modelo que você pode seguir para criar suas tabelas no Fluig. Fique atento ao arquivo persistence.xml, onde é informado o data source que obrigatoriamente deve ser utilizado para os parceiros criarem suas tabelas: AppDS.
    
    Também é possível encontrar as classes definidas como entidades, que serão suas tabelas no banco de dados, bem como a criação das consultas, constraints e índices.    
--- 

* **custom-component-service**:  projeto responsável por criar os serviços de CRUD com as tabelas do app

    A classe Activate.java é reponsável por provisionar a criação de um OAuth Provider e um OAuth App, de acordo com o Token inserido na classe RestConstant.java.
	- É necessário ativar o componente para o provisionamento
	- Essa funcionalidade de provisionar só está disponível a partir da release 1.6.5*
		
	Acesse a documentação para maiores informações: [Como expor dados em ambientes públicos](https://bit.ly/2SEW4bz).
	
	Pacotes:
	
		- DAO: Classes com os métodos de acesso ao banco de dados
		- Service/Impl: Interfaces e implementações das regras de negócios e permissões do app
---

* **custom-component-rest**: projeto responsável em disponibilizar as API Rest para os Services das tabelas do app

	A API Rest de exemplo(necessário estar autenticado), pode ser testada através dos endpoints:
	
		- FLUIG_URL/customrest/api/v1/category
		- FLUIG_URL/customrest/api/v1/app
	    
	A configuração de autenticação desses Rest's está no arquivo web.xml do projeto custom-component-rest.
	
	Para testar o Rest SEM autenticação, acesse o endpoint:
	
		- FLUIG_URL/customrest/api/v1/activate/userInfo/{tenantId}
	
	Lembrando que, pra efetuar essa requisição, é necessário ativar o componente para que seja provisionado a criação de um OAuth Provide e OAuth App.
---	

* **custom-component-widget**: projeto de uma widget simples. Aqui encontramos os arquivos de configuração, properties, imagens e o código-fonte da widget.

	No arquivo application.info o código precisa ser único: **application.code=customwidget**.
---	

* **custom-component-widget-menu**: projeto de uma widget de menu, para substituir o menu lateral esquerdo nativo do fluig.

	No arquivo application.info o código precisa ser único: **application.code=customwidgetmenu**.
---	

* **custom-component-layout**: projeto de um layout simples. Aqui encontramos os arquivos de configuração, properties, imagens, css e o código-fonte do layout.

	No arquivo application.info o código precisa ser único: **application.code=customlayout**.
	
	**Atenção, esse layout é compatível somente com o fluig 1.8.1 ou superior. Para versões anteriores, acesse a tag [1.8.0_OU_Inferior](https://git.fluig.com/projects/CUSTOMS/repos/projetos/browse/custom-component?at=refs%2Ftags%2F1.8.0_OU_Inferior).**
---	

* **custom-component-layout-custom**: projeto de um layout customizado, sem o menu lateral esquerdo.
	
	No arquivo application.info o código precisa ser único: **application.code=customcustomlayout**.

	**Atenção, esse layout é compatível somente com o fluig 1.8.1 ou superior. Para versões anteriores, acesse a tag [1.8.0_OU_Inferior](https://git.fluig.com/projects/CUSTOMS/repos/projetos/browse/custom-component?at=refs%2Ftags%2F1.8.0_OU_Inferior).**
---	

Para empacotar o projeto e gerar o arquivo **EAR**, será necessário utilizar o Maven. Entre com o seguinte comando na raiz do projeto:

     - mvn clean install
     - Você pode também utilzar o Eclipse for Java EE Developers para executar o comando mvn install.

Ao gerar o pacote, o próximo passo é fazer o upload através da Central de Componentes. Após enviar o .EAR, será necessário ativar o componente. Procure pelo código, que está no component.xml, faça a ativação, atualize a página(F5) e acesse as páginas criadas. (Essa etapa é necessária apenas no desenvolvimento do app).