 - Arquivo com classe principal: Main.java
 - Nome da classe principal: Main

Para executar:

javac Main.java
java .:hsqldb.jar Main

----- Backend -----

 - Manager.java - Classe singleton que contém os métodos para manipulação do banco de dados, implementa MediaDAO
    - MediaDAO.java - Interface que contém os métodos que devem ser implementados para manipulação do banco de dados
 - Media.java - Classe abstrata que contém os atributos comuns a todas as mídias (Filme, Série e Documentário)
    - Filme.java - Classe que representa um filme, herdando de `Media`
    - Serie.java - Classe que representa uma série, herdando de `Media`
    - Documentary.java - Classe que representa um documentário, herdando de `Media`
 - MediaListFactory.java - Classe que retorna fabricas para gerar listas de `Media`

----- Frontend -----

 - Cadastro.java: Responsável por mostrar a tela de cadastro de obras no banco de dados
 - Consultar.java: Arquivo para abrir a janela de consulta das obras cadastradas, podendo ser escolhido entre "Filmes", "Séries" ou "Documentários"
 - Inserir.java: Opção de tela para editar obras existentes no banco de dados
 - MostraDocumentários.java: Lista os Documentários presentes
 - MostraFilmes.java: Lista os Filmes presentes
 - MostraSeries.java: Lista as Séries presentes
 - Remover.java: Remove do banco de dados a opção selecionada
 - XilftenMenu.java: Janela do menu principal da aplicação. Lugar em que estão disponíveis as opções a serem selecionadas para manipulação dos registros

Para melhor explicação de cada classe e seus métodos, veja os comentários presentes no código.