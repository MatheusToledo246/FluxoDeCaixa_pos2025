
# 💰 Fluxo de Caixa POS 2025

Aplicativo Android simples para controle de lançamentos financeiros (créditos e débitos), utilizando banco de dados SQLite local.

## 📱 Funcionalidades

- Adicionar lançamentos de crédito ou débito
- Visualizar lista de lançamentos
- Exibição colorida e com ícones diferentes para crédito (verde) e débito (vermelho)
- Armazenamento local usando SQLite
- Interface simples com `ListView`, `Spinner` e `DatePickerDialog`
- Valores formatados como moeda brasileira (R$)

## 🗃️ Tecnologias Utilizadas

- Kotlin
- Android SDK
- SQLite
- XML (Layouts)
- `BaseAdapter` personalizado

## 🛠️ Estrutura do Projeto

- `DatabaseHandler`: gerencia criação e upgrade do banco de dados SQLite
- `Lancamento`: classe de dados que representa cada transação
- `MeuAdapter`: adaptador customizado para exibir os lançamentos na lista
- `MainActivity`: Tela principal para cadastrar lançamentos financeiros, selecionar tipo, detalhe, valor e data, e visualizar o saldo atual.
- `ListaActivity`: Tela para exibir a lista de lançamentos financeiros cadastrados.

- Layouts XML para cadastro e visualização dos dados

## 🧪 Testes e Reset do Banco

Durante testes, o banco pode ser resetado alterando a versão no `DatabaseHandler` para recriar a tabela.
