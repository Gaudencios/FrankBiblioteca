📚 Sistema de Gestão de Biblioteca (Java Swing & Hibernate)

Projeto de Sistema de Gestão de Biblioteca desenvolvido em Java Swing, utilizando o padrão de arquitetura MVCR (Model, View, Controller, Repository) e o ORM Hibernate para interação com o banco de dados.

🚀 1. Funcionalidades do sistema

O sistema permite o gerenciamento de livros e usuários, além de permitir o empréstimo e devolução de livros.

📖 Cadastro de livros

O sistema deve permitir que novos livros sejam cadastrados.


Atributo

Detalhe

Título

Título principal do livro.

Tema

Gênero ou categoria (ex: Ficção, Técnico).

Autor

Autor principal.

ISBN

Código ISBN do livro.

Data de publicação

Data de lançamento (com máscara dd/MM/yyyy).

Quantidade disponível

Nº de exemplares em estoque.

Número de identificação

Chave primária (ID).

👤 Cadastro de usuários

O sistema deve permitir o cadastro de novos usuários.

Atributo

Detalhe

Nome

Nome completo do usuário.

Sexo

Gênero do usuário.

Número do celular

Telefone de contato (com máscara (xx) xxxxx-xxxx).

E-mail

Endereço de e-mail (com máscara de e-mail).

Número de identificação

Chave primária (ID).

➡️ Empréstimo de livros

Um usuário deve ser capaz de pegar livros emprestados, contanto que haja exemplares disponíveis. O sistema deve registrar:

Usuário que pegou o livro emprestado

Data do empréstimo (com máscara dd/MM/yyyy)

Data de devolução prevista

Data de devolução (preenchida ao final)

Número de identificação (ID)

⬅️ Devolução de livros

O sistema deve permitir que o usuário devolva os livros que foram emprestados, atualizando o registro de empréstimo e a quantidade de exemplares do livro.

🏗️ 2. Camadas do sistema (Arquitetura MVCR)

Model: 📦 Representa os dados do sistema (classes Livro, Usuario, Emprestimo).

Repository: 🗄️ Controla a interação com o banco de dados (Hibernate). Todos os métodos que interagem com o banco de dados são apresentados nessa camada.

View: 🖥️ Responsável pela interface do usuário (Telas em Java Swing).

Controller: 🧠 Controla a interação entre a View e o Repository, gerenciando as operações e regras de negócio.

📜 3. Regras de Negócio

🚫 Um usuário pode pegar até 5 livros emprestados ao mesmo tempo.

✅ Um livro só pode ser emprestado se houver exemplares disponíveis (quantidade > 0).

⏰ O prazo máximo de empréstimo é de 14 dias. Após esse período, o sistema deve sinalizar que o livro está atrasado.

✅ 4. Tarefas e Funcionalidades Implementadas

[x] CRUD de Livros: Implementação das operações de Criar, Ler, Atualizar e Deletar livros.

[x] CRUD de Usuários: Implementação das operações de Criar, Ler, Atualizar e Deletar usuários.

[x] Fazer Empréstimo: Implementação da lógica de empréstimo, validando as regras de negócio.

[x] Registrar Devolução: Implementação da lógica de devolução.

[x] Listar Livros Disponíveis: Filtro para mostrar apenas livros com exemplares disponíveis.

[x] Cálculo de Multas: Recurso para calcular multas em caso de devolução atrasada.