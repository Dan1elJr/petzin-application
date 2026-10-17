# PetzIn

Sistema para cadastrar tutores, pets e reservas de hospedagem. A ideia é substituir as planilhas e deixar os dados de cada animal separados.

## Como rodar o projeto

O passo a passo abaixo é para Windows, usando Docker Desktop. Você vai precisar de internet para baixar o programa e as dependências na primeira execução.

### 1. Instale o Docker Desktop

    Baixe o instalador na [página do Docker Desktop para Windows](https://docs.docker.com/desktop/setup/install/windows-install/) e siga a instalação. Se aparecer a opção **Use WSL 2**, deixe marcada. Se pedir para instalar ou atualizar esse recurso do Windows, siga as instruções e reinicie quando solicitado.

    Depois, abra o Docker Desktop e espere ele iniciar. Deixe o programa aberto enquanto usa o projeto. Ele deve estar usando contêineres Linux.

    Não precisa instalar Java, Node.js, Maven ou PostgreSQL separadamente. O Docker prepara essas ferramentas para o projeto.

### 2. Baixe o código

    No repositório do projeto, clique em **Code → Download ZIP**. Depois, clique com o botão direito no arquivo baixado e escolha **Extrair Tudo**.

    Abra a pasta extraída e procure estes arquivos e pastas:

    ```text
    backend/
    frontend/
    .env.example
    compose.yaml
    ```

    Essa é a pasta onde os comandos devem ser executados. Extraia o ZIP antes de continuar.

### 3. Abra o PowerShell nessa pasta

    3.1. No Explorador de Arquivos, abra a pasta onde está o `compose.yaml`.
    3.2 Segure **Shift** e clique com o **botão direito em uma área vazia**, sem selecionar um arquivo.
    3.3 Escolha **Abrir janela do PowerShell aqui** ou **Abrir no Terminal**. Se não aparecer, procure em **Mostrar mais opções**.

    Se o Terminal abrir com outro tipo de terminal, escolha **Windows PowerShell** na seta ao lado do botão **+**.

    Para conferir a pasta, digite o comando abaixo e pressione Enter:

    (powershell)

    dir


    A lista deve mostrar o `compose.yaml` e as pastas `backend` e `frontend`. Se não mostrar, feche a janela e repita os passos na pasta certa. Não precisa usar `cd`.

    Confira também se o Docker está funcionando. Execute uma linha por vez:

    (powershell)

    docker compose version
    docker info


    O primeiro mostra a versão do Compose. O segundo deve mostrar as informações do Docker sem erro de conexão.

### 4. Configure o banco

    Na mesma janela do PowerShell, execute:

    (powershell)

    Copy-Item .env.example .env
    notepad .env


    Isso cria o arquivo `.env` e abre no Bloco de Notas. Se ele já existir, abra com `notepad .env`, sem repetir a cópia.

    Preencha assim, trocando a senha de exemplo por uma senha sua:

    ```dotenv
    POSTGRES_DB=petzin_webservice
    POSTGRES_USER=petzin
    POSTGRES_PASSWORD=defina_uma_senha_local
    FRONTEND_PORT=3000
    BACKEND_PORT=8080
    SPRING_JPA_HIBERNATE_DDL_AUTO=update
    SPRING_JPA_SHOW_SQL=true
    ```

    Salve com **Ctrl+S** e feche o editor. O nome precisa ser `.env`, sem `.txt` no final. Não deixe usuário e senha vazios.

### 5. Inicie o projeto

    No PowerShell aberto na pasta do projeto, execute:

    (powershell)

    docker compose up --build -d


    Se tiver fechado o PowerShell, abra novamente pelo botão direito, como no passo 3.

    Esse comando baixa as imagens, instala as dependências com Maven e npm, compila o código e inicia banco, backend e interface. A primeira execução pode demorar por causa dos downloads. O banco e as tabelas são preparados automaticamente.

    Quando terminar, confira:

    (powershell)

    docker compose ps


    Os serviços `database`, `backend` e `frontend` devem estar em execução, com o estado `Up`. O banco e o backend também devem mostrar `healthy`. Se aparecer `starting`, espere um pouco e confira de novo.

    Você também pode acompanhar os serviços no Docker Desktop, em **Containers**, dentro do grupo `petzin`.

### 6. Acesse o sistema

    Abra o navegador em **http://localhost:3000**.

    Cadastre um tutor e um pet. Depois, abra o agendamento, selecione o tutor e o animal, informe as datas, o valor e o canil. A saída precisa ser depois da entrada. Salve e confira a reserva na lista.

### 7. Pare ou inicie novamente

    Use o PowerShell na pasta do `compose.yaml`. Para abrir outra janela, repita o passo 3: **Shift + botão direito em uma área vazia da pasta → Abrir janela do PowerShell aqui ou Abrir no Terminal**.

    Para parar:

    (powershell)

    docker compose down


    Os dados continuam salvos no volume do banco. Para iniciar outra vez, deixe o Docker Desktop aberto e execute:

    (powershell)

    docker compose up -d


    Se mudar o `.env`, execute `docker compose up -d` para aplicar a configuração. Se mudar o código, use `docker compose up --build -d`.

    A senha do banco é definida quando ele é criado pela primeira vez. Trocar a senha só no `.env` não muda a senha de um banco que já existe.

### Se der erro

    - **Docker não conecta:** abra o Docker Desktop, espere iniciar e tente `docker info` novamente.
    - **Não encontra o Compose:** reabra o PowerShell na pasta do `compose.yaml`, pelo botão direito, como no passo 3.
    - **Porta ocupada:** troque `FRONTEND_PORT` para `3001` ou `BACKEND_PORT` para `8081`, conforme o erro. Execute `docker compose up -d` e use a nova porta no endereço do navegador.


## TECNOLOGIAS UTILIZADAS E ANÁLISE DA APLICAÇÃO

### Tecnologias utilizadas

    - **Java versão 17 e Spring Boot:** para criar a API e organizar as operações, validações e erros, com biblioteca Junit para testes.
    - **Spring Data JPA e Hibernate:** para consultar e salvar os dados sem repetir o código de acesso ao banco.
    - **PostgreSQL:** para guardar os cadastros e manter os vínculos entre eles.
    - **Angular e TypeScript:** para criar as telas e separar os componentes das chamadas à API.
    - **Bootstrap:** para montar os formulários e as listas.
    - **Springdoc OpenAPI:** para documentar e testar a API pelo Swagger.
    - **Docker Compose e Nginx:** para executar o projeto completo e servir a interface com acesso ao backend.

### Análise do problema

    O modelo original coloca o tutor, os pets e a reserva no mesmo registro. Isso repete informações e dificulta atualizar os dados. Guardar `Rex e Bolinha` no mesmo campo também impede identificar cada animal de forma confiável.

### Mudança de telefone do Carlos

    Se o telefone estiver em cada reserva, seria necessário alterar todas elas quando Carlos mudasse de número. Se alguma fosse esquecida, ele teria contatos diferentes no sistema.

    Separei os dados do tutor em um cadastro próprio. Assim, o telefone é alterado uma vez, e as reservas continuam ligadas ao mesmo tutor pelo ID. O sistema usa o contato atual; não guarda uma cópia do telefone de cada hospedagem.

### Histórico do Rex

    Cada pet tem seu próprio cadastro e ID. Rex e Bolinha ficam separados, mesmo sendo do mesmo tutor. Isso também permite distinguir animais com nomes iguais.

    O histórico médico ainda não foi feito. Para adicionar essa função, cada atendimento teria o ID do pet, a data e a descrição. Assim, a busca pelo ID do Rex retornaria somente os registros dele.

### Como os dados ficaram no modelo corrigdo

    - `pet_owner`: ID, nome e telefone do tutor.
    - `pet`: ID, nome, raça e ID do tutor.
    - `booking`: ID, pet, tutor, entrada, saída, valor e número do canil.

    (JSON Atual)

    {
    "id": 1,
    "petOwnerId": 2,
    "petId": 3,
    "petCheckinDate": "2026-11-10",
    "petCheckoutDate": "2026-11-15",
    "bookingCost": 350.00,
    "kannel": 5
    }

    Vantagem: 

    A principal vantagem é que o JSON atual liga a reserva aos cadastros do tutor e do pet pelos IDs, sem repetir seus dados.
    - Telefone atualizado em um lugar: se Carlos mudar de número, basta alterar seu cadastro. As reservas continuam ligadas ao mesmo petOwnerId.
    - Cada animal fica identificado: petId aponta para um único pet. No modelo antigo, "Rex e Bolinha" misturava dois animais no mesmo campo.
    - Menos inconsistências: evita escrever nomes, telefones e raças de formas diferentes em cada reserva.
    - Facilita consultar históricos: o ID permite localizar as reservas de um animal, mesmo que ele tenha o mesmo nome de outro.

    Essa vantagem vem da separação dos dados no banco, que o JSON representa. Para exibir os nomes, a interface consulta os cadastros correspondentes.

    Um tutor pode ter vários pets e reservas. Cada pet pertence a um tutor e pode ter várias reservas ao longo do tempo. Os registros são ligados por IDs, usando chaves estrangeiras no banco.

    Cada reserva contém um pet. Para hospedar Rex e Bolinha no mesmo período, são feitas duas reservas, cada uma com suas datas e seu valor. Isso permite mudar a hospedagem de um sem alterar a do outro, mas não existe um total compartilhado. O valor precisa ser informado por animal, sem repetir o total nas duas reservas.

    Para juntar vários pets em uma reserva, seria necessário criar uma tabela `reserva_pets`, como sugerido no documento de apoio. Ela ligaria a reserva aos animais. Também seria preciso ajustar a API, a interface e a forma de cobrar o valor do grupo.

### API e validações

    A API permite cadastrar, consultar, atualizar e excluir tutores, pets e reservas em `/api/owners`, `/api/pets` e `/api/bookings`.

    O backend verifica campos obrigatórios, datas de saída posteriores à entrada e valores não negativos quando informados. Os valores monetários usam `BigDecimal`.

    Os erros são tratados em um lugar só: `400` para dados inválidos, `404` para cadastros procurados e não encontrados, `409` para problemas de integridade e `500` para falhas inesperadas. Ao salvar um vínculo com um registro inexistente, o banco pode rejeitar a operação e a API retornar `409`.

### Alguns pontos a evoluir na aplicação

    - Validar no backend se o pet da reserva pertence ao tutor informado. Hoje, esse filtro é feito na interface.
    - Controlar a disponibilidade dos canis.
    - Adicionar histórico médico e permitir vários pets na mesma reserva.
