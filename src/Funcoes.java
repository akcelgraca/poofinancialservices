import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Funcoes {
    private ArrayList<Cliente> clientes;
    private ArrayList<Fatura> faturas;
    Scanner scanner = new Scanner(System.in);
    File dados = new File("ficheiro.txt");
    File fat = new File("faturas.txt");

    public Funcoes() {
        clientes = new ArrayList<>();
        faturas = new ArrayList<>();
    }

    public void listarFaturas() {
        List<Fatura> faturasDoFicheiro = carregarFaturasDoFicheiro();

        if (faturasDoFicheiro.isEmpty()) {
            System.out.println("Nenhuma fatura encontrada no ficheiro.");
            return;
        }

        System.out.println("Quantidade de faturas: " + faturasDoFicheiro.size() + "\n");

        for (Fatura fatura : faturasDoFicheiro) {
            System.out.println("Fatura Nº: " + fatura.getNumero());
            System.out.println("Cliente: " + fatura.getCliente().getNome());
            System.out.println("Localização do Cliente: " + fatura.getCliente().getLocalizacao());
            System.out.println("Número de Produtos: " + fatura.getProdutos().size());
            System.out.println("Total sem IVA: " + arredondar(fatura.calcularTotalSemIVA()));
            System.out.println("Total com IVA: " + arredondar(fatura.calcularTotalComIVA()));
            System.out.println("--------------------------------------------");
        }
    }

    public List<Fatura> carregarFaturasDoFicheiro() {
        List<Fatura> faturasDoFicheiro = new ArrayList<>();

        if (!fat.exists()) {
            System.out.println("O ficheiro faturas.txt não existe.");
            return faturasDoFicheiro;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fat))) {
            String linha;
            Fatura fatura = null;
            Cliente cliente = null;
            List<Produto> produtos = new ArrayList<>();
            String data = null;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Fatura Nº: ")) {
                    // Criar nova fatura
                    if (fatura != null && cliente != null) {
                        fatura.setProdutos(new ArrayList<>(produtos)); // Adicionar produtos à fatura anterior
                        faturasDoFicheiro.add(fatura); // Adicionar fatura à lista
                    }

                    // Inicializar nova fatura
                    int numeroFatura = Integer.parseInt(linha.substring(10).trim());
                    // Certifique-se de que o cliente está associado à fatura
                    fatura = new Fatura(numeroFatura, cliente, data);  // Agora associamos a fatura ao cliente e à data
                    produtos = new ArrayList<>(); // Reiniciar a lista de produtos
                } else if (linha.startsWith("Data: ")) {
                    data = linha.substring(6).trim(); // Ler a data
                } else if (linha.startsWith("Cliente: ")) {
                    String clienteInfo = linha.substring(9).trim();
                    // Separar nome e NIF do cliente
                    String[] parts = clienteInfo.split(" \\(NIF: ");
                    String nome = parts[0];
                    int nif = Integer.parseInt(parts[1].replace(")", "").trim());
                    cliente = new Cliente(nome, nif, Cliente.Localizacao.PortugalContinental); // Ajuste conforme necessário
                } else if (linha.startsWith("Produtos:")) {
                    // Continuação da leitura dos produtos
                    while ((linha = br.readLine()) != null && linha.startsWith("Codigo= ")) {
                        String codigo = linha.substring(8).trim();
                        String nomeProduto = br.readLine().substring(6).trim();
                        String descricao = br.readLine().substring(12).trim();
                        int quantidade = Integer.parseInt(br.readLine().substring(11).trim());
                        double valorUnitario = Double.parseDouble(br.readLine().substring(18).trim());

                        // Criar produto e adicionar à lista de produtos
                        Produto produto = null;

                        if (linha.contains("Tipo de taxa")) {  // Isso é para identificar que é um ProdutoAlimentar
                            // Lê o tipo de taxa
                            String tipoTaxaTexto = br.readLine().substring(14).trim();
                            ProdutoAlimentar.TipoTaxa tipoTaxa = ProdutoAlimentar.TipoTaxa.valueOf(tipoTaxaTexto.toUpperCase());

                            // Lê a categoria
                            String categoriaTexto = br.readLine().substring(9).trim();
                            ProdutoAlimentar.Categoria categoria = ProdutoAlimentar.Categoria.valueOf(categoriaTexto.toUpperCase());

                            // Lê as certificações (caso existam)
                            ArrayList<String> certificacoes = new ArrayList<>();
                            String certificacaoTexto = br.readLine().substring(14).trim();
                            if (!certificacaoTexto.equals("[]")) {
                                String[] certificacoesArray = certificacaoTexto.replace("[", "").replace("]", "").split(", ");
                                for (String cert : certificacoesArray) {
                                    certificacoes.add(cert);
                                }
                            }

                            // Instancia o ProdutoAlimentar
                            boolean isBiologico = br.readLine().substring(11).trim().equals("Sim");
                            produto = new ProdutoAlimentar(codigo, nomeProduto, descricao, quantidade, valorUnitario, tipoTaxa, isBiologico, certificacoes, categoria);
                        } else {
                            // Caso não seja ProdutoAlimentar, é um ProdutoFarmacia
                            String prescricaoTexto = br.readLine().substring(12).trim();
                            ProdutoFarmacia.Prescricao prescricao = ProdutoFarmacia.Prescricao.valueOf(prescricaoTexto.toUpperCase());

                            // Lê a categoria de ProdutoFarmacia
                            String categoriaFarmaciaTexto = br.readLine().substring(10).trim();
                            ProdutoFarmacia.CategoriaF categoriaf = ProdutoFarmacia.CategoriaF.valueOf(categoriaFarmaciaTexto.toUpperCase());

                            // Lê o médico (se necessário)
                            String medico = null;
                            if (prescricao == ProdutoFarmacia.Prescricao.ComPrescricao) {
                                medico = br.readLine().substring(9).trim();
                            }

                            // Instancia o ProdutoFarmacia
                            produto = new ProdutoFarmacia(codigo, nomeProduto, descricao, quantidade, valorUnitario, prescricao, categoriaf, medico);
                        }

                        // Adiciona o produto à lista
                        produtos.add(produto);
                    }
                }
            }

            // Se uma fatura foi lida, adicionar à lista
            if (fatura != null && cliente != null) {
                fatura.setProdutos(new ArrayList<>(produtos));
                faturasDoFicheiro.add(fatura); // Adicionar última fatura
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar faturas do ficheiro");
        }

        return faturasDoFicheiro;
    }








    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    public void visualizarFatura() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número da fatura para visualizar: ");


        int numeroFatura;
        try {
            String nF = scanner.nextLine();
            numeroFatura = Integer.parseInt(nF);
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            return;
        }

        Fatura fatura = null;
        for (Fatura f : faturas) {
            if (f.getNumero() == numeroFatura) {
                fatura = f;
                break;
            }
        }

        if (fatura == null) {
            System.out.println("Fatura não encontrada!");
            return;
        }

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());

        System.out.println("\nData: " + fatura.getData());
        System.out.println("Hora: " + date);
        System.out.println("Fatura: " + fatura.getNumero());
        System.out.println("Nome do Cliente: " + fatura.getCliente().getNome());
        System.out.println("Localização do Cliente: " + fatura.getCliente().getLocalizacao());
        System.out.println("NIF do Cliente: " + fatura.getCliente().getNumeroContribuinte());
        System.out.println("\n--- Produtos ---");
        for (Produto produto : fatura.getProdutos()) {
            System.out.println(produto);
            System.out.println("SUBTOTAL: " + arredondar(produto.calcularValorTotalSemIVA()));
            System.out.println("IVA : " + arredondar(produto.calcularIVA(fatura.getCliente().getLocalizacao())));
            System.out.println("SUBTOTAL IVA: " + arredondar(produto.calcularValorTotalComIVA(fatura.getCliente().getLocalizacao())));
        }
        System.out.println("TOTAL FARURA SEM IVA: " + arredondar(fatura.calcularTotalSemIVA()));
        System.out.println("TOTAL IVA: " + arredondar(fatura.calcularTotalIVA()));
        System.out.println("TOTAL FARURA COM IVA: " + arredondar(fatura.calcularTotalComIVA()));
    }


    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    public ArrayList<Fatura> getFaturas() {
        return faturas;
    }

    public void setFaturas(ArrayList<Fatura> faturas) {
        this.faturas = faturas;
    }

    private boolean isNomeValido(String nome) {
        for (char c : nome.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isWhitespace(c)) {
                return false; // Retorna falso se encontrar um caractere inválido
            }
        }
        return true; // Nome válido
    }

    private boolean isNumerico(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false; // Retorna falso se encontrar algo que não seja dígito
            }
        }
        return true; // É numérico
    }

    public void criarCliente() {
        Scanner scanner = new Scanner(System.in);

        int nif = 0;
        String entrada;
        String opcao;
        // Validação do Nome
        String nome = null;
        while (true) {
            try {
                System.out.print("Nome do cliente: ");
                nome = scanner.nextLine();

                // Verifica se o nome é válido
                if (!isNomeValido(nome)) {
                    throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
                }

                // Se o nome for válido, sai do loop
                break;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Mostra o erro para o usuário
            }
        }

        // Validação do NIF
        while (true) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                entrada = scanner.nextLine();

                // Verificar se o NIF tem 9 dígitos numéricos e apenas números
                if (entrada.length() != 9) {
                    System.out.println("O NIF deve conter exatamente 9 números e ser apenas números.");
                    continue;
                }

                nif = Integer.parseInt(entrada); // Converter para inteiro

                try {
                    if (lerFicheiro(nif)) {
                        System.out.println("Erro: Já existe um cliente com este NIF no ficheiro. Tente novamente.");
                        continue; // Solicita o NIF novamente
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Erro ao verificar duplicação no ficheiro");
                    continue; // Permite que o utilizador tente novamente
                }
                break; // Sai do loop se o NIF for válido e único
            } catch (NumberFormatException e) {
                System.out.println("O NIF deve conter apenas números.");
            }
        }

        // Escolher a Localização
        Cliente.Localizacao localizacao = null;
        System.out.println("Localização (1 - Portugal Continental, 2 - Madeira, 3 - Açores): ");
        while (localizacao == null) {
            try {
                opcao = scanner.nextLine();
                int opConv = Integer.parseInt(opcao);
                switch (opConv) {
                    case 1 -> localizacao = Cliente.Localizacao.PortugalContinental;
                    case 2 -> localizacao = Cliente.Localizacao.Madeira;
                    case 3 -> localizacao = Cliente.Localizacao.Açores;
                    default -> System.out.println("Opção inválida. Escolha 1, 2 ou 3.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira 1, 2 ou 3.");
            }

        }

        // Criar o Cliente
        Cliente cliente = new Cliente(nome, nif, localizacao) {
        };
        clientes.add(cliente);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dados,true))){
            bw.write("NIF: " + nif);
            bw.newLine();
            bw.write("Nome: " + nome);
            bw.newLine();
            bw.write("Localização: " + localizacao);
            bw.newLine();
            bw.write("---"); // Separador entre clientes
            bw.newLine();
        } catch (IOException ex) {
            System.out.println("Erro a escrever no ficheiro.");
        }

        System.out.println("Cliente criado com sucesso!");
    }

    // Função para verificar se o NIF já existe no ficheiro
    private boolean lerFicheiro(int nifR) {
        if (!dados.exists()) {
            return false; // Se o ficheiro não existe, o NIF não pode existir
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dados))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("NIF: ")) { // Procurar linhas que começam com "NIF: "
                    String nifNoFicheiro = linha.substring(5).trim(); // Obter o valor do NIF
                    if (Integer.parseInt(nifNoFicheiro) == nifR) {
                        return true; // NIF já existe
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro!");
        }

        return false; // NIF não encontrado
    }


    public void editarClientes() {
        Scanner scanner = new Scanner(System.in);
        boolean clienteEditado = false;

        // Validar NIF
        int nif = 0;
        while (true) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                String entrada = scanner.nextLine();

                if (entrada.length() == 9 && entrada.matches("\\d+")) {
                    nif = Integer.parseInt(entrada);
                    break;
                } else {
                    System.out.println("O NIF deve conter exatamente 9 dígitos numéricos. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro ao processar o NIF. Certifique-se de que é numérico.");
            }
        }

        // Ler o ficheiro e encontrar o cliente
        List<String> linhasAtualizadas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dados))) {
            String linha;
            boolean clienteEncontrado = false;
            String nomeAtual = null; // Nome atualizado
            String localizacaoAtual = null; // Localização atualizada

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("NIF: ")) {
                    int nifNoFicheiro = Integer.parseInt(linha.substring(5).trim());
                    if (nifNoFicheiro == nif) {
                        clienteEncontrado = true;
                        clienteEditado = true;

                        // Atualizar o nome
                        System.out.println("Cliente encontrado! Deseja alterar o nome?");
                        System.out.println("1 - Manter o nome atual");
                        System.out.println("2 - Alterar o nome");
                        System.out.print("Escolha uma opção: ");
                        String opcao = scanner.nextLine();

                        if (opcao.equals("2")) {
                            while (true) {
                                try {
                                    System.out.print("Digite o novo nome: ");
                                    String novoNome = scanner.nextLine();
                                    if (!isNomeValido(novoNome)) {
                                        throw new IllegalArgumentException("Nome inválido! Deve conter apenas letras e espaços.");
                                    }
                                    nomeAtual = novoNome; // Armazena o nome atualizado
                                    System.out.println("Nome atualizado com sucesso.");
                                    break;
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }

                        // Adicionar linha do NIF para o cliente encontrado
                        linhasAtualizadas.add(linha);
                    } else {
                        // Adicionar linha do NIF de outro cliente
                        linhasAtualizadas.add(linha);
                    }
                } else if (linha.startsWith("Nome: ")) {
                    if (clienteEncontrado) {
                        // Atualizar nome do cliente encontrado
                        if (nomeAtual != null) {
                            linhasAtualizadas.add("Nome: " + nomeAtual);
                            nomeAtual = null; // Resetar variável temporária
                        } else {
                            linhasAtualizadas.add(linha); // Adicionar o nome atual
                        }
                    } else {
                        // Adicionar o nome de outro cliente
                        linhasAtualizadas.add(linha);
                    }
                } else if (linha.startsWith("Localização: ")) {
                    if (clienteEncontrado) {
                        if (localizacaoAtual == null) {
                            // Atualizar localização
                            System.out.println("Deseja alterar a localização?");
                            System.out.println("0 - Manter atual");
                            System.out.println("1 - Portugal Continental");
                            System.out.println("2 - Madeira");
                            System.out.println("3 - Açores");
                            System.out.print("Escolha uma opção: ");
                            String opcao = scanner.nextLine();

                            switch (opcao) {
                                case "1" -> localizacaoAtual = "Portugal Continental";
                                case "2" -> localizacaoAtual = "Madeira";
                                case "3" -> localizacaoAtual = "Açores";
                                case "0" -> {
                                    // Não alterar a localização
                                    localizacaoAtual = linha.substring(13).trim();  // Manter a localização atual
                                }
                                default -> {
                                    System.out.println("Opção inválida! Nenhuma alteração realizada.");
                                    localizacaoAtual = linha.substring(13).trim(); // Manter atual em caso de erro
                                }
                            }
                        }
                        linhasAtualizadas.add("Localização: " + localizacaoAtual); // Adicionar a localização atualizada
                    } else {
                        linhasAtualizadas.add(linha); // Adicionar a localização de outro cliente
                    }
                } else {
                    linhasAtualizadas.add(linha); // Adicionar outras linhas como estão
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro");
            return;
        }

        // Verificar se o cliente foi encontrado e editado
        if (!clienteEditado) {
            System.out.printf("Cliente com o NIF %d não encontrado.%n", nif);
            return;
        }

        // Reescrever o ficheiro com as alterações
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dados))) {
            for (String linha : linhasAtualizadas) {
                bw.write(linha);
                bw.newLine();
            }
            System.out.println("Cliente atualizado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao escrever no ficheiro");
        }
    }





    public void listarClientes() {
        List<Cliente> clientesLidos = new ArrayList<>();
        if (!dados.exists()) {
            System.out.println("Nenhum cliente cadastrado no ficheiro.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dados))) {
            String linha;
            Cliente clienteAtual = null;
            while ((linha = br.readLine()) != null) {
                // Interpretar os dados do ficheiro
                if (linha.startsWith("NIF: ")) {
                    int nif = Integer.parseInt(linha.substring(5).trim());
                    clienteAtual = new Cliente("", nif, null); // Criar um cliente básico
                    clientesLidos.add(clienteAtual); // Adicionar à lista de clientes
                } else if (linha.startsWith("Nome: ") && clienteAtual != null) {
                    clienteAtual.setNome(linha.substring(6).trim()); // Atualizar o nome
                } else if (linha.startsWith("Localização: ") && clienteAtual != null) {
                    String localizacaoTexto = linha.substring(13).trim();
                    Cliente.Localizacao localizacao = switch (localizacaoTexto) {
                        case "Portugal Continental" -> Cliente.Localizacao.PortugalContinental;
                        case "Madeira" -> Cliente.Localizacao.Madeira;
                        case "Açores" -> Cliente.Localizacao.Açores;
                        default -> null;
                    };
                    clienteAtual.setLocalizacao(localizacao); // Atualizar a localização
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o ficheiro");
            return;
        }

        if (clientesLidos.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado no ficheiro.");
            return;
        }

        // Exibir os clientes lidos
        System.out.println("--- Lista de Clientes ---\n");
        for (Cliente cliente : clientesLidos) {
            System.out.println(cliente);
        }
    }

    public List<Cliente> carregarClientesDoFicheiro() {
        List<Cliente> clientesDoFicheiro = new ArrayList<>();

        if (!dados.exists()) {
            System.out.println("O ficheiro ficheiro.txt não existe.");
            return clientesDoFicheiro;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(dados))) {
            String linha;
            String nome = null;
            int nif = 0;
            Cliente.Localizacao localizacao = null;

            while ((linha = br.readLine()) != null) {
                if (linha.startsWith("Nome: ")) {
                    nome = linha.substring(6).trim();
                } else if (linha.startsWith("NIF: ")) {
                    nif = Integer.parseInt(linha.substring(5).trim());
                } else if (linha.startsWith("Localização: ")) {
                    String loc = linha.substring(13).trim();
                    localizacao = switch (loc) {
                        case "Portugal Continental" -> Cliente.Localizacao.PortugalContinental;
                        case "Madeira" -> Cliente.Localizacao.Madeira;
                        case "Açores" -> Cliente.Localizacao.Açores;
                        default -> throw new IllegalArgumentException("Localização inválida no ficheiro.");
                    };
                    // Criar e adicionar cliente à lista
                    clientesDoFicheiro.add(new Cliente(nome, nif, localizacao));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar clientes do ficheiro: ");
        }

        return clientesDoFicheiro;
    }


    public void criarFaturas() {
        Scanner scanner = new Scanner(System.in);
        // Carregar clientes do ficheiro
        List<Cliente> clientesDoFicheiro = carregarClientesDoFicheiro();

        if (clientesDoFicheiro.isEmpty()) {
            System.out.println("Não há clientes cadastrados no ficheiro. Não é possível criar uma fatura.");
            return;
        }
        String numeroConv;
        int numFatura = 0;
        // Verificar número da fatura
        while (true) {
            try {
                System.out.print("Digite o número da fatura: ");
                numeroConv = scanner.nextLine();
                numFatura = Integer.parseInt(numeroConv);

                // Verificar se o número já existe na lista ou no ficheiro
                int finalNumFatura = numFatura;
                boolean faturaExistente = faturas.stream()
                        .anyMatch(f -> f.getNumero() == finalNumFatura)
                        || faturaExisteNoFicheiro(numFatura);

                if (faturaExistente) {
                    System.out.println("Já existe uma fatura com este número. Por favor, insira outro número.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataValida = null;
        String novaData = "";
        while (dataValida == null) {
            try {
                System.out.println("Digite a data no formato dd/MM/yyyy:");
                novaData = scanner.nextLine();


                LocalDate data = LocalDate.parse(novaData, formato);


                if (data.isAfter(LocalDate.now())) {
                    System.out.println("A data não pode ser no futuro. Tente novamente.");
                } else {
                    dataValida = data; // Data válida
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy.");
            }
        }

        System.out.println("\n--- Selecionar Cliente ---");
        listarClientes();

        String entradaN;
        int niF;
        Cliente cliente = null;
        while (cliente == null) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                entradaN = scanner.nextLine();
                niF = Integer.parseInt(entradaN);
                int finalNiF = niF;
                cliente = clientesDoFicheiro.stream()
                        .filter(c -> c.getNumeroContribuinte() == finalNiF)
                        .findFirst()
                        .orElse(null);

                if (cliente == null) {
                    System.out.println("Cliente não encontrado! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número de NIF válido.");
            }
        }


        Fatura fatura = new Fatura(numFatura, cliente, novaData);

        // Adicionar produtos à fatura
        while (true) {
            System.out.println("Adicionar produto à fatura? (1 - Sim, 0 - Não): ");
            String escolha = scanner.nextLine(); // Usar nextLine para evitar conflitos

            if (escolha.equals("0")) break; // Encerrar o loop se o usuário escolher 0
            if (!escolha.equals("1")) { // Validar entrada
                System.out.println("Opção inválida. Escolha 1 para Sim ou 0 para Não.");
                continue;
            }
            // Código do produto
            String codigo;
            while (true) {
                System.out.println("Digite o código do produto: ");
                codigo = scanner.nextLine();

                // Verificar se o código já existe na lista de produtos da fatura
                boolean codigoExistente = false;
                for (Produto p : fatura.getProdutos()) {
                    if (p.getCodigo().equalsIgnoreCase(codigo)) {
                        codigoExistente = true;
                        break;
                    }
                }

                if (codigoExistente) {
                    System.out.println("Já existe um produto com este código na fatura. Por favor, insira outro código.");
                } else {
                    break; // Código válido, sair do loop
                }
            }

            System.out.println("Digite o nome do produto: ");
            String nome = scanner.nextLine();

            System.out.println("Digite a descrição do produto: ");
            String descricao = scanner.nextLine();


            int quantidade = 0;
            while (true) {
                try {
                    System.out.print("Digite a quantidade: ");
                    String entradaQuantidade = scanner.nextLine(); // Usar nextLine para entrada
                    quantidade = Integer.parseInt(entradaQuantidade); // Converter para inteiro

                    if (quantidade > 0) break;
                    System.out.println("Quantidade deve ser maior que zero.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                }
            }

            double valorUnitario;
            while (true) {
                try {
                    System.out.print("Digite o valor unitário (sem IVA): ");
                    String entradaValor = scanner.nextLine();
                    valorUnitario = Double.parseDouble(entradaValor);

                    if (valorUnitario >= 0) break;
                    System.out.println("O valor unitário não pode ser negativo.");
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
                }
            }

            System.out.println("É um produto alimentar ou de farmácia? (1 - Alimentar, 2 - Farmácia): ");
            String tipo;
            int tipoProduto;
            while (true) {
                try {
                    tipo = scanner.nextLine();
                    tipoProduto = Integer.parseInt(tipo);

                    if (tipoProduto == 1 || tipoProduto == 2) {
                        break;
                    }
                    System.out.println("Entrada inválida. Por favor, digite 1 para Alimentar ou 2 para Farmácia.");

                } catch (IllegalArgumentException e) {
                    System.out.println("Entrada inválida. Por favor, digite 1 para Alimentar ou 2 para Farmácia.");
                }
            }

            Produto produto = null;

            if (tipoProduto == 1) {
                // Produto Alimentar
                System.out.println("Digite o tipo de taxa (1 - Reduzida, 2 - Intermédia, 3 - Normal): ");
                ProdutoAlimentar.TipoTaxa taxa;
                while (true) {
                    try {
                        String tipoT = scanner.nextLine();
                        int tipoTaxa = Integer.parseInt(tipoT);

                        taxa = switch (tipoTaxa) {
                            case 1 -> ProdutoAlimentar.TipoTaxa.REDUDIZDA;
                            case 2 -> ProdutoAlimentar.TipoTaxa.INTERMEDIA;
                            case 3 -> ProdutoAlimentar.TipoTaxa.NORMAL;
                            default -> throw new IllegalArgumentException("Tipo de taxa inválido.");
                        };
                        break;
                    } catch (Exception e) {
                        System.out.println("Tipo de taxa inválido! Tem que ser 1 (Reduzida),2 (Intermédia) ou 3 (Normal)");
                    }
                }

                ArrayList<String> certificacoes = new ArrayList<>();
                if (taxa == ProdutoAlimentar.TipoTaxa.REDUDIZDA) {
                    System.out.println("Digite as certificações do produto (ISO22000, FSSC22000, HACCP, GMP): ");
                    System.out.println("'fim' para encerrar.");

                    while (certificacoes.isEmpty()) { // Enquanto nenhuma certificação for adicionada
                        while (certificacoes.size() < 4) { // Permite até 4 certificações
                            try {
                                System.out.print("Certificação " + (certificacoes.size() + 1) + ": ");
                                String certificacao = scanner.nextLine();

                                // Verifica se o usuário deseja encerrar
                                if (certificacao.equalsIgnoreCase("fim")) break;

                                // Valida a certificação
                                if (ProdutoAlimentar.CertificacaoValida.contains(certificacao)) {
                                    certificacoes.add(certificacao);
                                } else {
                                    System.out.println("Certificação inválida! Escolha entre: ISO22000, FSSC22000, HACCP, GMP.");
                                }
                            } catch (Exception e) {
                                System.out.println("Erro inesperado. Tente novamente.");
                            }
                        }

                        if (certificacoes.isEmpty()) {
                            System.out.println("Erro: Produtos de taxa reduzida devem ter pelo menos uma certificação.");
                            System.out.println("Por favor, insira pelo menos uma certificação válida.");
                        }
                    }
                }

                // Categoria para taxa intermediária
                ProdutoAlimentar.Categoria categoria = null;
                if (taxa == ProdutoAlimentar.TipoTaxa.INTERMEDIA) {
                    System.out.println("Digite a categoria do produto (1 - Congelados, 2 - Enlatados, 3 - Vinho): ");
                    while (categoria == null) {
                        try {
                            String categoriaOpc = scanner.nextLine();
                            int categoriaOpcaoA = Integer.parseInt(categoriaOpc);

                            categoria = switch (categoriaOpcaoA) {
                                case 1 -> ProdutoAlimentar.Categoria.CONGELADOS;
                                case 2 -> ProdutoAlimentar.Categoria.ENLATADOS;
                                case 3 -> ProdutoAlimentar.Categoria.VINHO;
                                default -> throw new IllegalArgumentException("Categoria inválida.");
                            };
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Escolha entre 1 (Congelados), 2 (Enlatados), ou 3 (Vinho).");
                        }
                    }
                }

                // Verificar se é biológico
                boolean isBiologico = false;
                while (true) {
                    try {
                        System.out.print("É um produto biológico? (1 - Sim, 0 - Não): ");
                        String resB = scanner.nextLine();
                        int respostaB = Integer.parseInt(resB);

                        if (respostaB == 1) {
                            isBiologico = true;
                            break;
                        } else if (respostaB == 0) {
                            isBiologico = false;
                            break;
                        } else {
                            System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                            continue;
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                    }
                }


                // Criar Produto Alimentar
                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, taxa, isBiologico, certificacoes, categoria);

            } else if (tipoProduto == 2) {
                // Produto de Farmácia
                System.out.println("É um produto com prescrição? (1 - Sim, 0 - Não): ");
                boolean comPrescricao = false;
                while (true) {
                    try {
                        String resF = scanner.nextLine();
                        int respostaF = Integer.parseInt(resF);

                        if (respostaF == 1) {
                            comPrescricao = true;
                            break;
                        } else if (respostaF == 0) {
                            comPrescricao = false;
                            break;
                        } else {
                            System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                    }
                }

                ProdutoFarmacia.Prescricao prescricao = comPrescricao
                        ? ProdutoFarmacia.Prescricao.ComPrescricao
                        : ProdutoFarmacia.Prescricao.Normais;

                ProdutoFarmacia.CategoriaF categoriaf = null;
                if (!comPrescricao) {
                    System.out.println("Selecione a categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro): ");
                    while (categoriaf == null) {
                        try {
                            String categoriaOpc = scanner.nextLine();
                            int categoriaOpcaoF = Integer.parseInt(categoriaOpc);

                            categoriaf = switch (categoriaOpcaoF) {
                                case 1 -> ProdutoFarmacia.CategoriaF.beleza;
                                case 2 -> ProdutoFarmacia.CategoriaF.bem_estar;
                                case 3 -> ProdutoFarmacia.CategoriaF.bebes;
                                case 4 -> ProdutoFarmacia.CategoriaF.animais;
                                case 5 -> ProdutoFarmacia.CategoriaF.outro;
                                default -> throw new IllegalArgumentException("Categoria inválida.");
                            };
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Escolha entre 1, 2, 3, 4 ou 5.");
                        }
                    }
                }

                String medico = null;
                if (comPrescricao) {
                    while (true) {
                        try {
                            System.out.print("Digite o nome do médico que prescreveu o medicamento: ");
                            medico = scanner.nextLine();

                            // Verifica se o nome é válido
                            if (!isNomeValido(medico)) {
                                throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
                            }

                            // Se o nome for válido, sai do loop
                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Nome inválido!"); // Mostra o erro para o usuário
                        }
                    }
                }


                // Criar Produto de Farmácia
                produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoriaf, medico);
            }

            fatura.adicionarProduto(produto);
        }
        faturas.add(fatura);
        salvarFaturaNoFicheiro(fatura);
        System.out.println("Fatura criada com sucesso!");
    }

    private boolean faturaExisteNoFicheiro(int numeroFatura) {

        if (!fat.exists()) {
            return false; // Se o ficheiro não existe, não pode haver duplicatas
        }

        try (BufferedReader br = new BufferedReader(new FileReader(fat))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // Verificar se a linha contém o número da fatura
                if (linha.contains("Fatura Nº: ")) {
                    // Extrair o número da fatura, ignorando espaços e substrings extras
                    String[] partes = linha.split(":");
                    if (partes.length > 1) {
                        int numeroExistente = Integer.parseInt(partes[1].trim()); // Usando o split para separar
                        if (numeroExistente == numeroFatura) {
                            return true; // Fatura já existe no ficheiro
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao verificar duplicatas no ficheiro: " + e.getMessage());
        }

        return false; // Se não encontrou a fatura no ficheiro
    }


    public void salvarFaturaNoFicheiro(Fatura fatura) {
        File file = new File("faturas.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.write("Fatura Nº: " + fatura.getNumero());
            bw.newLine();
            bw.write("Data: " + fatura.getData());
            bw.newLine();
            bw.write("Cliente: " + fatura.getCliente().getNome() + " (NIF: " + fatura.getCliente().getNumeroContribuinte() + ")");
            bw.newLine();
            bw.write("Localização: " + fatura.getCliente().getLocalizacao());
            bw.newLine();
            bw.write("Produtos:");
            bw.newLine();
            for (Produto produto : fatura.getProdutos()) {
                bw.write(produto.toString());
                bw.newLine();
            }
            bw.write("Total sem IVA: " + fatura.calcularTotalSemIVA());
            bw.newLine();
            bw.write("Total IVA: " + fatura.calcularTotalIVA());
            bw.newLine();
            bw.write("Total com IVA: " + fatura.calcularTotalComIVA());
            bw.newLine();
            bw.write("------------------------------------------");
            bw.newLine();
            System.out.println("Fatura salva no ficheiro faturas.txt com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar a fatura no ficheiro: " + e.getMessage());
        }
    }


    public void editarFatura() {
        System.out.println("--- Editar Fatura ---");

        // Solicitar número da fatura
        int numeroFatura;
        while (true) {
            try {
                System.out.print("Digite o número da fatura a ser editada: ");
                numeroFatura = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }

        // Procurar fatura
        Fatura fatura = null;
        for (Fatura f : faturas) {
            if (f.getNumero() == numeroFatura) {
                fatura = f;
                break;
            }
        }

        if (fatura == null) {
            System.out.println("Fatura não encontrada!");
            return;
        }

        System.out.println("Fatura selecionada: " + fatura.getNumero());

        while (true) {
            System.out.println("\n--- Menu de Edição ---");
            System.out.println("1. Adicionar produto");
            System.out.println("2. Editar produto existente");
            System.out.println("3. Eliminar produto");
            System.out.println("0. Sair da edição");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número válido.");
                continue;
            }

            switch (opcao) {
                case 1:
                    // Adicionar um novo produto
                    adicionarProdutoFatura(fatura, scanner);
                    break;
                case 2:
                    // Editar um produto existente
                    editarProdutoFatura(fatura, scanner);
                    break;
                case 3:
                    // Eliminar um produto
                    removerProdutoFatura(fatura, scanner);
                    break;
                case 0:
                    System.out.println("a Sair da edição...");
                    return;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        }
    }

    public void adicionarProdutoFatura(Fatura fatura, Scanner scanner) {
        scanner = new Scanner(System.in);
        // Código do produto
        String codigo;
        while (true) {
            System.out.println("Digite o código do produto: ");
            codigo = scanner.nextLine();

            // Verificar se o código já existe na lista de produtos da fatura
            boolean codigoExistente = false;
            for (Produto p : fatura.getProdutos()) {
                if (p.getCodigo().equalsIgnoreCase(codigo)) {
                    codigoExistente = true;
                    break;
                }
            }

            if (codigoExistente) {
                System.out.println("Já existe um produto com este código na fatura. Por favor, insira outro código.");
            } else {
                break; // Código válido, sair do loop
            }
        }

        System.out.println("Digite o nome do produto: ");
        String nome = scanner.nextLine();

        System.out.println("Digite a descrição do produto: ");
        String descricao = scanner.nextLine();


        int quantidade = 0;
        while (true) {
            try {
                System.out.print("Digite a quantidade: ");
                String entradaQuantidade = scanner.nextLine(); // Usar nextLine para entrada
                quantidade = Integer.parseInt(entradaQuantidade); // Converter para inteiro

                if (quantidade > 0) break;
                System.out.println("Quantidade deve ser maior que zero.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
            }
        }

        double valorUnitario;
        while (true) {
            try {
                System.out.print("Digite o valor unitário (sem IVA): ");
                String entradaValor = scanner.nextLine();
                valorUnitario = Double.parseDouble(entradaValor);

                if (valorUnitario >= 0) break;
                System.out.println("O valor unitário não pode ser negativo.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
            }
        }

        System.out.println("É um produto alimentar ou de farmácia? (1 - Alimentar, 2 - Farmácia): ");
        String tipo;
        int tipoProduto;
        while (true) {
            try {
                tipo = scanner.nextLine();
                tipoProduto = Integer.parseInt(tipo);

                if (tipoProduto == 1 || tipoProduto == 2) {
                    break;
                }
                System.out.println("Entrada inválida. Por favor, digite 1 para Alimentar ou 2 para Farmácia.");

            } catch (IllegalArgumentException e) {
                System.out.println("Entrada inválida. Por favor, digite 1 para Alimentar ou 2 para Farmácia.");
            }
        }

        Produto produto = null;

        if (tipoProduto == 1) {
            // Produto Alimentar
            System.out.println("Digite o tipo de taxa (1 - Reduzida, 2 - Intermédia, 3 - Normal): ");
            ProdutoAlimentar.TipoTaxa taxa;
            while (true) {
                try {
                    String tipoT = scanner.nextLine();
                    int tipoTaxa = Integer.parseInt(tipoT);

                    taxa = switch (tipoTaxa) {
                        case 1 -> ProdutoAlimentar.TipoTaxa.REDUDIZDA;
                        case 2 -> ProdutoAlimentar.TipoTaxa.INTERMEDIA;
                        case 3 -> ProdutoAlimentar.TipoTaxa.NORMAL;
                        default -> throw new IllegalArgumentException("Tipo de taxa inválido.");
                    };
                    break;
                } catch (Exception e) {
                    System.out.println("Tipo de taxa inválido! Tem que ser 1 (Reduzida),2 (Intermédia) ou 3 (Normal)");
                }
            }

            ArrayList<String> certificacoes = new ArrayList<>();
            if (taxa == ProdutoAlimentar.TipoTaxa.REDUDIZDA) {
                System.out.println("Digite as certificações do produto (ISO22000, FSSC22000, HACCP, GMP): ");
                System.out.println("'fim' para encerrar.");

                while (certificacoes.isEmpty()) { // Enquanto nenhuma certificação for adicionada
                    while (certificacoes.size() < 4) { // Permite até 4 certificações
                        try {
                            System.out.print("Certificação " + (certificacoes.size() + 1) + ": ");
                            String certificacao = scanner.nextLine();

                            // Verifica se o usuário deseja encerrar
                            if (certificacao.equalsIgnoreCase("fim")) break;

                            // Valida a certificação
                            if (ProdutoAlimentar.CertificacaoValida.contains(certificacao)) {
                                certificacoes.add(certificacao);
                            } else {
                                System.out.println("Certificação inválida! Escolha entre: ISO22000, FSSC22000, HACCP, GMP.");
                            }
                        } catch (Exception e) {
                            System.out.println("Erro inesperado. Tente novamente.");
                        }
                    }

                    if (certificacoes.isEmpty()) {
                        System.out.println("Erro: Produtos de taxa reduzida devem ter pelo menos uma certificação.");
                        System.out.println("Por favor, insira pelo menos uma certificação válida.");
                    }
                }
            }

            // Categoria para taxa intermediária
            ProdutoAlimentar.Categoria categoria = null;
            if (taxa == ProdutoAlimentar.TipoTaxa.INTERMEDIA) {
                System.out.println("Digite a categoria do produto (1 - Congelados, 2 - Enlatados, 3 - Vinho): ");
                while (categoria == null) {
                    try {
                        String categoriaOpc = scanner.nextLine();
                        int categoriaOpcaoA = Integer.parseInt(categoriaOpc);

                        categoria = switch (categoriaOpcaoA) {
                            case 1 -> ProdutoAlimentar.Categoria.CONGELADOS;
                            case 2 -> ProdutoAlimentar.Categoria.ENLATADOS;
                            case 3 -> ProdutoAlimentar.Categoria.VINHO;
                            default -> throw new IllegalArgumentException("Categoria inválida.");
                        };
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Escolha entre 1 (Congelados), 2 (Enlatados), ou 3 (Vinho).");
                    }
                }
            }

            // Verificar se é biológico
            boolean isBiologico = false;
            while (true) {
                try {
                    System.out.print("É um produto biológico? (1 - Sim, 0 - Não): ");
                    String resB = scanner.nextLine();
                    int respostaB = Integer.parseInt(resB);

                    if (respostaB == 1) {
                        isBiologico = true;
                        break;
                    } else if (respostaB == 0) {
                        isBiologico = false;
                        break;
                    } else {
                        System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                        continue;
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                }
            }


            // Criar Produto Alimentar
            produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, taxa, isBiologico, certificacoes, categoria);
            fatura.adicionarProduto(produto);

        } else if (tipoProduto == 2) {
            // Produto de Farmácia
            System.out.println("É um produto com prescrição? (1 - Sim, 0 - Não): ");
            boolean comPrescricao = false;
            while (true) {
                try {
                    String resF = scanner.nextLine();
                    int respostaF = Integer.parseInt(resF);

                    if (respostaF == 1) {
                        comPrescricao = true;
                        break;
                    } else if (respostaF == 0) {
                        comPrescricao = false;
                        break;
                    } else {
                        System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                }
            }

            ProdutoFarmacia.Prescricao prescricao = comPrescricao
                    ? ProdutoFarmacia.Prescricao.ComPrescricao
                    : ProdutoFarmacia.Prescricao.Normais;

            ProdutoFarmacia.CategoriaF categoriaf = null;
            if (!comPrescricao) {
                System.out.println("Selecione a categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro): ");
                while (categoriaf == null) {
                    try {
                        String categoriaOpc = scanner.nextLine();
                        int categoriaOpcaoF = Integer.parseInt(categoriaOpc);

                        categoriaf = switch (categoriaOpcaoF) {
                            case 1 -> ProdutoFarmacia.CategoriaF.beleza;
                            case 2 -> ProdutoFarmacia.CategoriaF.bem_estar;
                            case 3 -> ProdutoFarmacia.CategoriaF.bebes;
                            case 4 -> ProdutoFarmacia.CategoriaF.animais;
                            case 5 -> ProdutoFarmacia.CategoriaF.outro;
                            default -> throw new IllegalArgumentException("Categoria inválida.");
                        };
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Escolha entre 1, 2, 3, 4 ou 5.");
                    }
                }
            }

            String medico = null;
            if (comPrescricao) {
                while (true) {
                    try {
                        System.out.print("Digite o nome do médico que prescreveu o medicamento: ");
                        medico = scanner.nextLine();

                        // Verifica se o nome é válido
                        if (!isNomeValido(medico)) {
                            throw new IllegalArgumentException("O nome deve conter apenas letras e espaços.");
                        }

                        // Se o nome for válido, sai do loop
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Nome inválido!"); // Mostra o erro para o usuário
                    }
                }
            }

            // Criar Produto de Farmácia
            produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoriaf, medico);
            fatura.adicionarProduto(produto);
        }
    }

    public void editarProdutoFatura(Fatura fatura, Scanner scanner) {
        // Escolher produto por código
        if (fatura.getProdutos().isEmpty()) {
            System.out.println("A fatura não possui produtos para editar.");
            return;
        }
        Produto produtoSelecionado = null;
        while (produtoSelecionado == null) {
            try {
                System.out.print("Digite o código do produto a editar: ");
                String codigoProduto = scanner.nextLine();

                for (Produto produto : fatura.getProdutos()) {
                    if (produto.getCodigo().equalsIgnoreCase(codigoProduto)) {
                        produtoSelecionado = produto;
                        break;
                    }
                }

                if (produtoSelecionado == null) {
                    System.out.println("Produto com o código informado não encontrado na fatura.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " Por favor, tente novamente.");
            }
        }

        System.out.println("Produto selecionado: " + produtoSelecionado.getNome());

        // Atualizar atributos genéricos
        // Novo código
        while (true) {
            try {
                System.out.print("Novo código (atual: " + produtoSelecionado.getCodigo() + "): ");
                String novoCodigo = scanner.nextLine();
                if (!novoCodigo.isBlank()) produtoSelecionado.setCodigo(novoCodigo);
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
            }
        }

        // Novo nome
        while (true) {
            try {
                System.out.print("Novo nome (atual: " + produtoSelecionado.getNome() + "): ");
                String novoNome = scanner.nextLine();
                if (!novoNome.isBlank()) produtoSelecionado.setNome(novoNome);
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
            }
        }

        // Nova descrição
        while (true) {
            try {
                System.out.print("Nova descrição (atual: " + produtoSelecionado.getDescricao() + "): ");
                String novaDescricao = scanner.nextLine();
                if (!novaDescricao.isBlank()) produtoSelecionado.setDescricao(novaDescricao);
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Tente novamente.");
            }
        }

        // Atualizar quantidade
        while (true) {
            try {
                System.out.print("Nova quantidade (atual: " + produtoSelecionado.getQuantidade() + "): ");
                String entradaQuantidade = scanner.nextLine();
                if (!entradaQuantidade.isBlank()) {
                    int novaQuantidade = Integer.parseInt(entradaQuantidade);
                    if (novaQuantidade > 0) {
                        produtoSelecionado.setQuantidade(novaQuantidade);
                        break;
                    } else {
                        System.out.println("A quantidade deve ser maior que zero.");
                    }
                } else break; // Manter a quantidade atual
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um número inteiro.");
            }
        }

        // Atualizar valor unitário
        while (true) {
            try {
                System.out.print("Novo valor unitário (atual: " + produtoSelecionado.getValorUnitario() + "): ");
                String entradaValor = scanner.nextLine();
                if (!entradaValor.isBlank()) {
                    double novoValor = Double.parseDouble(entradaValor);
                    if (novoValor >= 0) {
                        produtoSelecionado.setValorUnitario(novoValor);
                        break;
                    } else {
                        System.out.println("O valor unitário não pode ser negativo.");
                    }
                } else break; // Manter o valor atual
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, insira um valor numérico.");
            }
        }

        // Atualizações específicas para ProdutoAlimentar
        if ("ProdutoAlimentar".equals(produtoSelecionado.getTipoProduto())) {
            editarProdutoAlimentar((ProdutoAlimentar) produtoSelecionado, scanner);
        }

        // Atualizações específicas para ProdutoFarmacia
        if ("ProdutoFarmacia".equals(produtoSelecionado.getTipoProduto())) {
            editarProdutoFarmacia((ProdutoFarmacia) produtoSelecionado, scanner);
        }

        System.out.println("Produto atualizado com sucesso!");
    }

    public void removerProdutoFatura(Fatura fatura, Scanner scanner) {
        System.out.println("--- Eliminar Produto ---");

        // Solicitar código do produto a ser removido
        System.out.print("Digite o código do produto a remover: ");
        String codigoProduto = scanner.nextLine();

        Produto produtoRemover = null;
        for (Produto p : fatura.getProdutos()) {
            if (p.getCodigo().equalsIgnoreCase(codigoProduto)) {
                produtoRemover = p;
                break;
            }
        }

        if (produtoRemover != null) {
            fatura.removerProduto(produtoRemover);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Produto não encontrado na fatura.");
        }
    }

    private void editarProdutoAlimentar(ProdutoAlimentar produto, Scanner scanner) {
        // Editar biológico
        while (true) {
            try {
                System.out.print("É biológico (1 - Sim, 0 - Não) (atual: " + produto.isBiologico() + "): ");
                String entrada = scanner.nextLine();
                if (entrada.isBlank()) break; // Manter o valor atual
                int isBio = Integer.parseInt(entrada);
                produto.setBiologico(isBio == 1);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 1 para Sim ou 0 para Não.");
            }
        }

        // Se for de taxa intermediária, editar categoria
        if (produto.getTipoTaxa() == ProdutoAlimentar.TipoTaxa.INTERMEDIA) {
            while (true) {
                try {
                    System.out.println("Digite a categoria (1 - Congelados, 2 - Enlatados, 3 - Vinho) (atual: " + produto.getCategoria() + "): ");
                    String categoriaEntrada = scanner.nextLine();
                    if (categoriaEntrada.isBlank()) break; // Manter o valor atual
                    ProdutoAlimentar.Categoria novaCategoria = switch (Integer.parseInt(categoriaEntrada)) {
                        case 1 -> ProdutoAlimentar.Categoria.CONGELADOS;
                        case 2 -> ProdutoAlimentar.Categoria.ENLATADOS;
                        case 3 -> ProdutoAlimentar.Categoria.VINHO;
                        default -> throw new IllegalArgumentException("Categoria inválida.");
                    };
                    produto.setCategoria(novaCategoria);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Escolha 1 (Congelados), 2 (Enlatados) ou 3 (Vinho).");
                }
            }
        }
    }

    private void editarProdutoFarmacia(ProdutoFarmacia produto, Scanner scanner) {
        if (produto.getPrescricao() == ProdutoFarmacia.Prescricao.ComPrescricao) {
            System.out.print("Digite o nome do médico (atual: " + produto.getMedico() + "): ");
            String novoMedico = scanner.nextLine();
            if (!novoMedico.isBlank()) produto.setMedico(novoMedico);
        } else {
            while (true) {
                try {
                    System.out.println("Digite a categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro) (atual: "
                            + produto.getCategoriaf() + "): ");
                    String categoriaEntrada = scanner.nextLine();
                    if (categoriaEntrada.isBlank()) break; // Manter o valor atual
                    ProdutoFarmacia.CategoriaF novaCategoriaF = switch (Integer.parseInt(categoriaEntrada)) {
                        case 1 -> ProdutoFarmacia.CategoriaF.beleza;
                        case 2 -> ProdutoFarmacia.CategoriaF.bem_estar;
                        case 3 -> ProdutoFarmacia.CategoriaF.bebes;
                        case 4 -> ProdutoFarmacia.CategoriaF.animais;
                        case 5 -> ProdutoFarmacia.CategoriaF.outro;
                        default -> throw new IllegalArgumentException("Categoria inválida.");
                    };
                    produto.setCategoriaf(novaCategoriaF);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Escolha 1, 2, 3, 4 ou 5.");
                }
            }
        }
    }

}