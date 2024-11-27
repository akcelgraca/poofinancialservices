import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Funcoes {
    private ArrayList<Cliente> clientes ;
    private ArrayList<Fatura> faturas;
    public Funcoes(){
        clientes = new ArrayList<>();
        faturas = new ArrayList<>();
    }

    public ArrayList<Cliente> getClientes(){
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes){
        this.clientes = clientes;
    }

    public ArrayList<Fatura> getFaturas(){
        return faturas;
    }

    public void setFaturas(ArrayList<Fatura> faturas){
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

        // Validação do Nome
        String nome;
        while (true) {
            System.out.print("Nome do cliente: ");
            nome = scanner.nextLine();

            if (isNomeValido(nome)) { // Verifica se contém apenas letras e espaços
                break; // Nome válido, sai do loop
            } else {
                System.out.println("Nome inválido! O nome não deve conter números ou caracteres especiais.");
            }
        }

        // Validação do NIF
        int nif = 0;
        while (true) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                String entrada = scanner.nextLine();

                // Verificar se o NIF tem 9 dígitos numéricos
                if (entrada.length() != 9 || !isNumerico(entrada)) {
                    throw new IllegalArgumentException("O NIF deve conter exatamente 9 números.");
                }

                nif = Integer.parseInt(entrada);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Escolher a Localização
        Cliente.Localizacao localizacao = null;
        System.out.println("Localização (1 - Portugal Continental, 2 - Madeira, 3 - Açores): ");
        while (localizacao == null) {
            try {
                int opcao = scanner.nextInt();
                switch (opcao) {
                    case 1 -> localizacao = Cliente.Localizacao.PortugalContinental;
                    case 2 -> localizacao = Cliente.Localizacao.Madeira;
                    case 3 -> localizacao = Cliente.Localizacao.Açores;
                    default -> throw new IllegalArgumentException("Opção inválida. Escolha 1, 2 ou 3.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira 1, 2 ou 3.");
                scanner.nextLine(); // Limpar o buffer
            }
        }

        // Criar o Cliente
        Cliente cliente = new Cliente(nome, nif, localizacao) {
        };
        clientes.add(cliente);
        System.out.println("Cliente criado com sucesso!");
    }


    public void editarClientes() {
        Scanner scanner = new Scanner(System.in);

        // Validação do NIF do cliente
        Cliente cliente = null;
        while (cliente == null) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                String entrada = scanner.nextLine();

                // Validar se o NIF tem 9 dígitos numéricos
                if (entrada.length() != 9 || !isNumerico(entrada)) {
                    throw new IllegalArgumentException("O NIF deve conter exatamente 9 números .");
                }

                int nif = Integer.parseInt(entrada);

                // Procurar o cliente correspondente
                for (Cliente c : clientes) {
                    if (c.getNumeroContribuinte() == nif) {
                        cliente = c;
                        break;
                    }
                }

                if (cliente == null) {
                    System.out.println("Cliente não encontrado! Por favor, insira um NIF válido.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Exibir cliente encontrado
        System.out.println("Editar Cliente: " + cliente.getNome());

        // Validação do Nome
        while (true) {
            try {
                System.out.print("Novo nome (ou pressione ENTER para manter): ");
                String nome = scanner.nextLine();

                if (!nome.isBlank()) { // Permitir manter o nome atual se o campo estiver vazio
                    if (!isNomeValido(nome)) {
                        throw new IllegalArgumentException("Nome inválido! O nome não deve conter números ou caracteres especiais.");
                    }
                    cliente.setNome(nome); // Atualizar o nome se válido
                }
                break; // Sai do loop se o nome for válido ou se foi mantido
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Atualizar Localização
        System.out.print("Nova localização (0 - Manter, 1 - Portugal Continental, 2 - Madeira, 3 - Açores): ");
        while (true) {
            try {
                int localizacaoOP = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer

                switch (localizacaoOP) {
                    case 0 -> System.out.println("Localização mantida.");
                    case 1 -> {
                        cliente.setLocalizacao(Cliente.Localizacao.PortugalContinental);
                        System.out.println("Localização atualizada para Portugal Continental.");
                    }
                    case 2 -> {
                        cliente.setLocalizacao(Cliente.Localizacao.Madeira);
                        System.out.println("Localização atualizada para Madeira.");
                    }
                    case 3 -> {
                        cliente.setLocalizacao(Cliente.Localizacao.Açores);
                        System.out.println("Localização atualizada para Açores.");
                    }
                    default -> throw new IllegalArgumentException("Opção inválida! Escolha 0, 1, 2 ou 3.");
                }
                break; // Sai do loop após uma entrada válida
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Entrada inválida! Por favor, insira um número entre 0 e 3.");
                scanner.nextLine(); // Limpar buffer
            }
        }

        System.out.println("Cliente atualizado com sucesso!");
    }


    public void listarClientes(){
        if (clientes.size() == 0) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }
        System.out.println("--- Lista de Clientes ---");
        for(Cliente cliente : clientes){
            System.out.println(cliente);
        }
    }

    public void criarFaturas(){
        Scanner scanner = new Scanner(System.in);
        int numeroFatura;
        while (true) {
            try {
                System.out.print("Digite o número da fatura: ");
                numeroFatura = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Número inválido. Por favor, digite um número inteiro.");
                scanner.nextLine(); // Limpar o buffer
            }
        }

        System.out.println("Digite a data da fatura (Formato: DD/MM/AAAA): ");
        String data = scanner.nextLine();

        System.out.println("\n--- Selecionar Cliente ---");
        listarClientes();

        int nif;
        Cliente cliente = null;
        while (cliente == null) {
            try {
                System.out.print("Digite o NIF do cliente: ");
                nif = scanner.nextInt();
                scanner.nextLine();

                for (Cliente c : clientes) {
                    if (c.getNumeroContribuinte() == nif) {
                        cliente = c;
                        break;
                    }
                }

                if (cliente == null) {
                    System.out.println("Cliente não encontrado! Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número de NIF válido.");
                scanner.nextLine(); // Limpar o buffer
            }
        }


        Fatura fatura = new Fatura(numeroFatura,cliente,data);

        // Adicionar produtos à fatura
        while(true){
            System.out.println("Adicionar produto à fatura? (1 - Sim, 0 - Não): ");
            int escolha = scanner.nextInt();
            scanner.nextLine(); // Limpar buffer
            if (escolha == 0) break;

            System.out.println("Digite o código do produto: ");
            String codigo = scanner.nextLine();

            System.out.println("Digite o nome do produto: ");
            String nome = scanner.nextLine();

            System.out.println("Digite a descrição do produto: ");
            String descricao = scanner.nextLine();


            int quantidade;
            while (true) {
                try {
                    System.out.print("Digite a quantidade: ");
                    quantidade = scanner.nextInt();
                    scanner.nextLine();
                    if (quantidade > 0) break;
                    System.out.println("Quantidade deve ser maior que zero.");
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                    scanner.nextLine();
                }
            }

            double valorUnitario;
            while (true) {
                try {
                    System.out.print("Digite o valor unitário (sem IVA): ");
                    valorUnitario = scanner.nextDouble();
                    scanner.nextLine();
                    if (valorUnitario >= 0) break;
                    System.out.println("O valor unitário não pode ser negativo.");
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, digite um valor numérico.");
                    scanner.nextLine();
                }
            }

            System.out.println("É um produto alimentar ou de farmácia? (1 - Alimentar, 2 - Farmácia): ");
            int tipoProduto;
            while (true) {
                try {
                    tipoProduto = scanner.nextInt();
                    scanner.nextLine();
                    if (tipoProduto == 1 || tipoProduto == 2) break;
                    System.out.println("Opção inválida. Digite 1 para Alimentar ou 2 para Farmácia.");
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Por favor, digite 1 para Alimentar ou 2 para Farmácia.");
                    scanner.nextLine(); // Limpar o buffer
                }
            }

            Produto produto = null;

            if (tipoProduto == 1) {
                // Produto Alimentar
                System.out.println("Digite o tipo de taxa (1 - Reduzida, 2 - Intermédia, 3 - Normal): ");
                ProdutoAlimentar.TipoTaxa taxa;
                while (true) {
                    try {
                        int tipoTaxa = scanner.nextInt();
                        scanner.nextLine();
                        taxa = switch (tipoTaxa) {
                            case 1 -> ProdutoAlimentar.TipoTaxa.REDUDIZDA;
                            case 2 -> ProdutoAlimentar.TipoTaxa.INTERMEDIA;
                            case 3 -> ProdutoAlimentar.TipoTaxa.NORMAL;
                            default -> throw new IllegalArgumentException("Tipo de taxa inválido.");
                        };
                        break;
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, escolha entre 1 (Reduzida), 2 (Intermédia), ou 3 (Normal).");
                        scanner.nextLine(); // Limpar o buffer
                    }
                }

                ArrayList<String> certificacoes = new ArrayList<>();
                if (taxa == ProdutoAlimentar.TipoTaxa.REDUDIZDA) {
                    System.out.println("Digite as certificações do produto (ISO22000, FSSC22000, HACCP, GMP): ");
                    System.out.println("'fim' para encerrar.");
                    while (certificacoes.size() < 4) {
                        System.out.print("Certificação " + (certificacoes.size() + 1) + ": ");
                        String certificacao = scanner.nextLine();
                        if (certificacao.equalsIgnoreCase("fim")) break;

                        if (ProdutoAlimentar.CertificacaoValida.contains(certificacao)) {
                            certificacoes.add(certificacao);
                        } else {
                            System.out.println("Certificação inválida! Escolha entre: ISO22000, FSSC22000, HACCP, GMP.");
                        }
                    }

                    if (certificacoes.isEmpty()) {
                        System.out.println("Erro: Produtos de taxa reduzida devem ter pelo menos uma certificação.");
                        return; // Encerra a criação do produto
                    }
                }

                // Verificar se é biológico
                boolean isBiologico = false;
                while (true) {
                    try {
                        System.out.print("É um produto biológico? (1 - Sim, 0 - Não): ");
                        int resposta = scanner.nextInt();
                        scanner.nextLine();
                        if (resposta == 1) {
                            isBiologico = true;
                            break;
                        } else if (resposta == 0) {
                            isBiologico = false;
                            break;
                        } else {
                            System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                        scanner.nextLine(); // Limpar o buffer
                    }
                }

                // Categoria para taxa intermediária
                ProdutoAlimentar.Categoria categoria = null;
                if (taxa == ProdutoAlimentar.TipoTaxa.INTERMEDIA) {
                    System.out.println("Digite a categoria do produto (1 - Congelados, 2 - Enlatados, 3 - Vinho): ");
                    while (categoria == null) {
                        try {
                            int categoriaOpcao = scanner.nextInt();
                            scanner.nextLine();
                            categoria = switch (categoriaOpcao) {
                                case 1 -> ProdutoAlimentar.Categoria.CONGELADOS;
                                case 2 -> ProdutoAlimentar.Categoria.ENLATADOS;
                                case 3 -> ProdutoAlimentar.Categoria.VINHO;
                                default -> throw new IllegalArgumentException("Categoria inválida.");
                            };
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Escolha entre 1 (Congelados), 2 (Enlatados), ou 3 (Vinho).");
                            scanner.nextLine(); // Limpar o buffer
                        }
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
                        int resposta = scanner.nextInt();
                        scanner.nextLine();
                        if (resposta == 1) {
                            comPrescricao = true;
                            break;
                        } else if (resposta == 0) {
                            comPrescricao = false;
                            break;
                        } else {
                            System.out.println("Opção inválida. Digite 1 para Sim ou 0 para Não.");
                        }
                    } catch (Exception e) {
                        System.out.println("Entrada inválida. Por favor, digite 1 para Sim ou 0 para Não.");
                        scanner.nextLine();
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
                            int categoriaOpcao = scanner.nextInt();
                            scanner.nextLine();
                            categoriaf = switch (categoriaOpcao) {
                                case 1 -> ProdutoFarmacia.CategoriaF.beleza;
                                case 2 -> ProdutoFarmacia.CategoriaF.bem_estar;
                                case 3 -> ProdutoFarmacia.CategoriaF.bebes;
                                case 4 -> ProdutoFarmacia.CategoriaF.animais;
                                case 5 -> ProdutoFarmacia.CategoriaF.outro;
                                default -> throw new IllegalArgumentException("Categoria inválida.");
                            };
                        } catch (Exception e) {
                            System.out.println("Entrada inválida. Escolha entre 1, 2, 3, 4 ou 5.");
                            scanner.nextLine();
                        }
                    }
                }

                String medico = null;
                if (comPrescricao) {
                    System.out.print("Digite o nome do médico que prescreveu o medicamento: ");
                    medico = scanner.nextLine();
                }

                // Criar Produto de Farmácia
                produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoriaf, medico);
            }

            fatura.adicionarProduto(produto);
        }
        faturas.add(fatura);
        System.out.println("Fatura criada com sucesso!");
    }

    public void editarFatura(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Editar Fatura ---");
        System.out.println("Digite o número da fatura a ser editada: ");
        int numeroFatura;
        try {
            numeroFatura = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); // Limpar o buffer
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

        System.out.println("Editar data da fatura (atual: " + fatura.getData() + "): ");
        String novaData = scanner.nextLine();
        if (!novaData.isBlank()) {
            fatura.setData(novaData);
            System.out.println("Data atualizada para: " + novaData);
        }



        // Adicionar produto
        System.out.println("Adicionar produto à fatura? (1 - Sim, 0 - Não): ");
        int escolhaAdicionar = scanner.nextInt();
        scanner.nextLine();

        if (escolhaAdicionar == 1) {
            // Reutilizar lógica para adicionar produtos (adapte de `criarFaturas`).
            System.out.println("Adicionando produto...");
            criarFaturas();
        }
        System.out.println("Fatura atualizada com sucesso!");

        // Remover produto
        System.out.println("Remover produto da fatura? (1 - Sim, 0 - Não): ");
        int escolhaRemover = scanner.nextInt();
        scanner.nextLine();

        if (escolhaRemover == 1) {
            if (fatura.getProdutos().isEmpty()) {
                System.out.println("A fatura não possui produtos para remover.");
            } else {
                System.out.println("--- Produtos na fatura ---");
                for (int i = 0; i < fatura.getProdutos().size(); i++) {
                    Produto produto = fatura.getProdutos().get(i);
                    System.out.println((i + 1) + " - " + produto.getNome());
                }
                System.out.println("Digite o número do produto a remover: ");
                try {
                    int produtoIndex = scanner.nextInt() - 1;
                    scanner.nextLine();

                    if (produtoIndex >= 0 && produtoIndex < fatura.getProdutos().size()) {
                        Produto produtoRemovido = fatura.getProdutos().remove(produtoIndex);
                        System.out.println("Produto removido: " + produtoRemovido.getNome());
                    } else {
                        System.out.println("Número inválido. Nenhum produto foi removido.");
                    }
                } catch (Exception e) {
                    System.out.println("Entrada inválida. Nenhum produto foi removido.");
                    scanner.nextLine(); // Limpar o buffer
                }
            }
        }

        System.out.println("Fatura atualizada com sucesso!");

    }

    public void listarFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura registada.");
            return;
        }

        System.out.println("Quantidade de faturas: " + faturas.size());
        for (Fatura fatura : faturas) {
            System.out.println(fatura);
        }
    }

    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    public void visualizarFatura() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o número da fatura para visualizar: ");

        int numeroFatura;
        try {
            numeroFatura = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); // Limpar o buffer
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
            System.out.println("SUBTOTAL: " + produto.calcularValorTotalSemIVA());
            System.out.println("IVA : " + produto.calcularIVA(fatura.getCliente().getLocalizacao()));
            System.out.println("SUBTOTAL IVA: " + arredondar(produto.calcularValorTotalComIVA(fatura.getCliente().getLocalizacao())));
        }
        System.out.println("TOTAL FARURA SEM IVA: " + arredondar(fatura.calcularTotalSemIVA()));
        System.out.println("TOTAL IVA: " + arredondar(fatura.calcularTotalIVA()));
        System.out.println("TOTAL FARURA COM IVA: " + arredondar(fatura.calcularTotalComIVA()));
    }
}
