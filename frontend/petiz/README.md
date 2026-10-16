# Petiz — Frontend

Frontend Angular com Bootstrap básico, sem CSS personalizado ou elementos decorativos, para um CRUD simples de tutores, animais e agendamentos. A organização segue o projeto D:\developer-work-space\application: cada componente em sua pasta, navegação por rotas e serviços HTTP separados.

## Executar

```powershell
cd D:\instituto-conecthus\frontend\petiz
npm.cmd ci
npm.cmd start
```

Abra http://localhost:4200 com o backend em http://localhost:8080. O proxy.conf.json encaminha /api ao backend durante o desenvolvimento. Para outra porta, altere o target. Em produção, configure o servidor para encaminhar /api ao backend e as rotas da interface ao index.html.

```powershell
npm.cmd run build
npm.cmd test -- --watch=false
```

O build é gerado em dist/petiz/browser.

## Responsabilidades

- models/: classes PetOwner, Pet e Booking com nomes e tipos dos DTOs REST; relacionamentos representados por IDs, como a API exige.
- service/: somente os três serviços HTTP, um por entidade, com métodos de listar, consultar, criar, editar e excluir.
- pet-owner-form/: formulário reutilizado nas telas de tutor e agendamento; cadastra o tutor e depois seus animais.
- pet-owner-list/ e pet-owner-editor/: componentes de consulta e edição de tutores e animais.
- booking-list/ e booking-editor/: componentes de consulta e edição de agendamentos; a validação da estadia fica no método de salvar do editor.
- menu/: componente de navegação.

Cada componente tem arquivos `.ts`, `.html` e `.css`, com o mesmo nome de sua pasta, conforme o Angular CLI instalado. Os arquivos CSS permanecem vazios; a aplicação utiliza somente Bootstrap. Os testes existentes ficam junto aos componentes e serviços que verificam.

As telas usam `FormsModule`, `[(ngModel)]`, `*ngIf` e `*ngFor`, propriedades comuns e serviços recebidos pelo construtor. As chamadas HTTP usam `subscribe`, sem promises, `async/await`, signals ou formulários reativos. Cada componente mostra suas próprias mensagens e tem métodos diretos para carregar, salvar e excluir. A interface verifica somente campos obrigatórios com `required` e `ngForm`. As regras de telefone, datas, valores e canil ficam no backend; erros retornados são exibidos na tela. `ChangeDetectorRef.markForCheck()` atualiza a tela após as respostas HTTP na versão instalada do Angular.

## Padrão Angular CLI

Exemplos de geração seguindo esta estrutura:

```powershell
ng generate component booking-list
ng generate component booking-editor
ng generate component pet-owner-list
ng generate component pet-owner-editor
ng generate component pet-owner-form
ng generate service service/booking-service
ng generate service service/pet-owner-service
ng generate service service/pet-service
ng generate class models/booking
ng generate class models/pet-owner
ng generate class models/pet
```

Os serviços usam `@Service()`, como o serviço HTTP do projeto de referência e o padrão do CLI instalado. Os componentes usam `@Component` com `selector`, `imports`, `templateUrl` e `styleUrl`. O CLI desta versão usa nomes como `booking-list.ts`, sem exigir o sufixo `.component.ts`.

## Contrato do backend

| Entidade | Campos da API | Endpoint |
| --- | --- | --- |
| PetOwner | id, name, telephoneNumber, petIds, bookingIds | /api/owners |
| Pet | id, petName, petBreed, petOwnerId | /api/pets |
| Booking | id, petOwnerId, petId, petCheckinDate, petCheckoutDate, bookingCost, kannel | /api/bookings |

Um tutor pode ter vários animais e agendamentos. Cada agendamento referencia um animal. Datas usam strings YYYY-MM-DD, correspondendo ao LocalDate. A saída deve ser posterior à entrada. O valor pago usa bookingCost e é exibido em reais; o backend permite valor nulo ou a partir de zero. O campo Canil usa `kannel`, inteiro opcional conforme o backend, disponível no cadastro, na edição e na listagem.

O telefone é texto (`String` no backend e `string` no frontend), com campo `type="tel"`, sem máscara ou conversão numérica. Zeros iniciais e formatação são preservados. A API de animais possui somente nome, raça e tutor.

## Fluxos

Em Tutores e animais, cadastre o tutor e adicione quantos animais precisar. Na edição, inclua animais, altere dados e remova animais. Exclusões de animais persistidos são imediatas após confirmação; cancelar o formulário não as desfaz. Exclua agendamentos vinculados antes dos animais, e os animais antes do tutor, conforme as restrições da API.

Em Novo agendamento:

- Tutor cadastrado: pesquise nome, telefone ou código, clique em Validar tutor e selecione um de seus animais. A validação consulta a API; alterar a busca desfaz a seleção.
- Tutor não cadastrado: preencha o tutor e seus animais e clique em Salvar tutor e animais. Na mesma tela, escolha o animal da estadia, preencha entrada, saída, valor pago e canil e clique em Salvar.

A API não oferece transação única para tutor, animais e agendamento. O frontend salva em sequência e conserva os IDs de cada resposta confirmada para repetir a operação na mesma tela após falha parcial. Cadastros confirmados permanecem no backend se a tela for abandonada. Uma resposta perdida após gravação ainda requer conferir a listagem antes de repetir; a API não oferece chave de idempotência.

## Verificação

Testes cobrem a seleção do tutor e seus animais, envio e edição do canil, exibição dos erros do backend e cadastro pela tela com ngModel. Escritas usam HTTP simulado, sem alterar os dados locais existentes.

Os identificadores técnicos seguem a nomenclatura em inglês do backend: PetOwner, Pet e Booking; serviços PetOwnerService, PetService e BookingService. As rotas da interface são /owners, /owners/new, /owners/:id, /bookings, /bookings/new e /bookings/:id. Os textos exibidos ao usuário permanecem em português.
