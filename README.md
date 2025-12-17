# POO Financial Services (POOFS)

> **Programação Orientada aos Objetos** - 2024/2025
> Licenciatura em Engenharia Informática - Universidade de Coimbra (DEI)

O **POOFS** é uma aplicação de gestão financeira desenvolvida em **Java**, destinada a empresas para o registo e manipulação de clientes e faturas. O sistema permite a emissão de faturas contendo produtos alimentares e farmacêuticos, bem como a exportação de dados para fins fiscais.

---

## Funcionalidades Principais

A aplicação funciona através de um menu interativo de texto e oferece as seguintes capacidades:

* **Gestão de Clientes:**
    * Registo de novos clientes com Nome, NIF e Localização (Continente, Madeira, Açores).
    * Edição, listagem e visualização de detalhes dos clientes.
* **Gestão de Faturas:**
    * Criação de faturas associadas a um cliente e uma data.
    * Adição de produtos (Alimentares ou Farmacêuticos) às faturas.
    * Listagem e visualização de faturas existentes.
* **Gestão de Produtos:**
    * Suporte para produtos com diferentes taxas de IVA (Reduzida, Intermédia, Normal) e certificações biológicas.

---

## Arquitetura do Sistema

O projeto segue os princípios da Programação Orientada a Objetos, estruturado nas seguintes classes principais:

* **`Main`**: Ponto de entrada da aplicação, gere o menu principal.
* **`Cliente`**: Representa a entidade cliente (atributos: Nome, NIF, Localização).
* **`Fatura`**: Gere os dados de uma fatura e a lista de produtos associados.
* **`Produto`**: Classe base para os diferentes tipos de produtos (Alimentares e Farmacêuticos).
* **`Funcoes`**: Classe utilitária contendo métodos auxiliares para a lógica do sistema.

### Tecnologias e Bibliotecas
O projeto foi desenvolvido em Java Standard Edition, utilizando as seguintes bibliotecas nativas:
* `java.util.Scanner`: Para leitura de dados introduzidos pelo utilizador.
* `java.io` (`BufferedReader`, `FileWriter`): Para persistência de dados em ficheiros de texto.
* `java.time` (`LocalDate`, `LocalTime`): Para manipulação de datas e horas nas faturas.
* `java.util.ArrayList`: Para armazenamento dinâmico de listas de clientes e faturas em memória.

---

## Persistência de Dados e Limitações

Atualmente, a aplicação utiliza **ficheiros de texto** para guardar e carregar a informação dos clientes e faturas.

**Limitações Conhecidas:**
* **Ficheiros de Objetos:** A persistência através de serialização de objetos (`.dat` ou similar) não foi totalmente implementada com sucesso. Por esse motivo, a edição de faturas antigas (carregadas de ficheiro) pode estar limitada apenas à visualização, enquanto faturas criadas na sessão atual (memória temporária) são totalmente editáveis.
* **Edição de Faturas:** A funcionalidade de editar faturas já gravadas em ficheiro de texto não está completamente operacional.

---

## Como Executar

1.  **Compilar:**
    Certifique-se de que tem o JDK instalado. Compile todos os ficheiros `.java`:
    ```bash
    javac *.java
    ```
2.  **Executar:**
    Inicie a aplicação através da classe principal:
    ```bash
    java Main
    ```
3.  **Utilização:**
    Siga as instruções apresentadas no menu de texto da consola para navegar entre as opções de gestão de clientes e faturas.

---

## Autoria
Trabalho realizado no âmbito da disciplina de POO.
