import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Fatura> faturas = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Gestão ---");
            System.out.println("1. CRIAR CLIENTE");
            System.out.println("2. EDITAR CLIENTES");
            System.out.println("3. LISTAR CLIENTES");
            System.out.println("4. CRIAR FATURA");
            System.out.println("5. EDITAR FATURA");
            System.out.println("6. LISTAR FATURAS");
            System.out.println("7. VISUALIZAR FATURAS");
            System.out.println("0. SAIR\n");
            System.out.print("ESCOLHA UMA OPÇÃO: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> criarCliente(scanner);
                case 2 -> editarClientes(scanner);
                case 3 -> listarClientes();
                case 4 -> criarFaturas(scanner);
                case 5 -> editarFatura(scanner);
                case 6 -> listarFaturas();
                case 7 -> visualizarFatura(scanner);
                case 0 -> System.out.println("A sair...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void criarCliente(Scanner scanner){
        System.out.print("Nome do cliente: ");
        String nome = scanner.nextLine();

        System.out.print("Número de contribuinte (NIF): ");
        int nif;
        while (true){
            nif = scanner.nextInt();
            scanner.nextLine();
            if(String.valueOf(nif).length() == 9) break;
            System.out.print("NIF inválido! Digite novamente (9 dígitos): ");
        }
        System.out.println("Localização (1- Portugal Continental, 2 - Madeira, 3 - Açores): ");
        int localizacaoOP;
        Cliente.Localizacao localizacao = null;
        while (localizacao ==  null){
            localizacaoOP = scanner.nextInt();
            scanner.nextLine();
            switch (localizacaoOP){
                case 1 -> localizacao = Cliente.Localizacao.PortugalContinental;
                case 2 -> localizacao = Cliente.Localizacao.Madeira;
                case 3 -> localizacao = Cliente.Localizacao.Açores;
                default -> System.out.print("Opção inválida! Escolha novamente: ");
            }
        }
        Cliente cliente = new Cliente(nome,nif,localizacao) {};
        clientes.add(cliente);
        System.out.println("Cliente criado com sucesso!");
    }

    private static void editarClientes(Scanner scanner){
        System.out.print("Digite o NIF do cliente: ");
        int nif = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.getNumeroContribuinte() == nif) {
                cliente = c;
                break;
            }
        }
        if (cliente == null) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        System.out.println("Editar Cliente: " + cliente.getNome());
        System.out.print("Novo nome: ");
        String nome = scanner.nextLine();
        if (!nome.isBlank()) {
            cliente.setNome(nome);
        }

        System.out.print("Nova localização (0 - Manter, 1- Portugal Continental, 2 - Madeira, 3 - Açores):");
        int localizacaoOP = scanner.nextInt();
        scanner.nextLine();
        switch (localizacaoOP){
            case 0 -> System.out.println("Localização mantida.");
            case 1 -> cliente.setLocalizacao(Cliente.Localizacao.PortugalContinental);
            case 2 -> cliente.setLocalizacao(Cliente.Localizacao.Madeira);
            case 3 -> cliente.setLocalizacao(Cliente.Localizacao.Açores);
            default -> System.out.println("Opção inválida! Localização mantida.");
        }
        System.out.println("Cliente atualizado com sucesso!");
    }

    private static void listarClientes(){
        if (clientes.size() == 0) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }
        System.out.println("--- Lista de Clientes ---");
        for(Cliente cliente : clientes){
            System.out.println(cliente);
        }
    }

    private static void criarFaturas(Scanner scanner){
        System.out.println("Digite o número da  fatura: ");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite a data da fatura (Formato: DD-MM-AAAA): ");
        String data = scanner.nextLine();

        System.out.println("--- Selecionar Cliente ---");
        listarClientes();
        System.out.println("Digite o NIF do cliente: ");
        int nif = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.getNumeroContribuinte() == nif) {
                cliente = c;
                break;
            }
        }
        if(cliente == null){
            System.out.println("Cliente não encontrado!");
            return;
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


            System.out.println("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Digite o valor unitário (sem IVA): ");
            double valorUnitario = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("É um produto alimentar ou de farmácia? (1 - Alimentar, 2 - Farmácia): ");
            int tipoProduto = scanner.nextInt();
            scanner.nextLine();

            Produto produto = null;
            if(tipoProduto == 1) {
                System.out.println("Digite o tipo de taxa (1 - Reduzida, 2 - Intermédia, 3 - Normal)");
                int tipoTaxa = scanner.nextInt();
                scanner.nextLine();

                ProdutoAlimentar.TipoTaxa taxa = switch (tipoTaxa) {
                    case 1 -> ProdutoAlimentar.TipoTaxa.REDUDIZDA;
                    case 2 -> ProdutoAlimentar.TipoTaxa.INTERMEDIA;
                    case 3 -> ProdutoAlimentar.TipoTaxa.NORMAL;
                    default -> throw new IllegalArgumentException("Tipo de taxa inválido.");
                };

                ArrayList<String> certificacoes = new ArrayList<>();
                if (tipoTaxa == 1) {
                    System.out.println("Digite a certificação do produto: (ISO22000,FSSC22000,HACCP,GMP): ");
                    System.out.println("'fim' para encerrar.");
                            while(certificacoes.size() < 4){
                                System.out.print("Certificação " + (certificacoes.size() + 1) + ": ");
                                String certificacao = scanner.nextLine();

                                if(certificacao.equalsIgnoreCase("fim")){
                                    break;
                                }
                                if(ProdutoAlimentar.CertificacaoValida.contains(certificacao)){
                                    certificacoes.add(certificacao);
                                }else{
                                    System.out.println("Certificação inválida! Escolha entre: ISO22000, FSSC22000, HACCP, GMP.");
                                }

                            }
                            if(certificacoes.isEmpty()){
                                System.out.println("Erro: Produtos de taxa reduzida devem ter pelo menos uma certificação.");
                            }

                }

                System.out.println("É um produto biológico? (1- Sim, 0 - Não): ");
                boolean isBiologico = scanner.nextInt() == 1;
                scanner.nextLine();

                int categoriaOpcao = 0;
                if (tipoTaxa == 2) {
                    System.out.println("Digite a categoria do produto (1 - Congelados, 2 - Enlatados, 3 - Vinho): ");
                    categoriaOpcao = scanner.nextInt();
                    scanner.nextLine();
                }

                ProdutoAlimentar.Categoria categoria = switch (categoriaOpcao) {
                    case 1 -> ProdutoAlimentar.Categoria.CONGELADOS;
                    case 2 -> ProdutoAlimentar.Categoria.ENLATADOS;
                    case 3 -> ProdutoAlimentar.Categoria.VINHO;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };


                produto = new ProdutoAlimentar(codigo, nome, descricao, quantidade, valorUnitario, taxa, isBiologico,certificacoes, categoria);
            } else if(tipoProduto == 2){
                System.out.println("É um produto com prescrição? (1- Sim,0 - Não): ");
                boolean comPrescricao = scanner.nextInt() == 1;
                scanner.nextLine();

                ProdutoFarmacia.Prescricao prescricao = comPrescricao
                        ? ProdutoFarmacia.Prescricao.ComPrescricao
                        : ProdutoFarmacia.Prescricao.Normais;
                System.out.println("Selecione a categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro): ");
                int categoriaOpcao = scanner.nextInt();
                scanner.nextLine();


                ProdutoFarmacia.CategoriaF categoriaf = switch (categoriaOpcao) {
                    case 1 -> ProdutoFarmacia.CategoriaF.beleza;
                    case 2 -> ProdutoFarmacia.CategoriaF.bem_estar;
                    case 3 -> ProdutoFarmacia.CategoriaF.bebes;
                    case 4 -> ProdutoFarmacia.CategoriaF.animais;
                    case 5 -> ProdutoFarmacia.CategoriaF.outro;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };
                String medico = null;
                if (comPrescricao) {
                    System.out.println("Digite o nome do médico que prescreveu o medicamento: ");
                    medico = scanner.nextLine();
                } else {

                }

                produto = new ProdutoFarmacia(codigo, nome, descricao, quantidade, valorUnitario, prescricao, categoriaf,medico);
            }
            fatura.adicionarProduto(produto);
        }
        faturas.add(fatura);
        System.out.println("Fatura criada com sucesso!");
    }

    private static void editarFatura(Scanner scanner){
        System.out.println("--- Editar Fatura ---");
        System.out.println("Digite o número da fatura a ser editada: ");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine();

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
        if(!novaData.isBlank()){
            fatura.setData(novaData);
        }



        System.out.println("Adicionar produto à fatura? (1 - Sim, 0 - Não): ");
        int escolha = scanner.nextInt();
        scanner.nextLine();
        if(escolha == 1){
            criarFaturas(scanner);
        }
        System.out.println("Fatura atualizada com sucesso!");

        System.out.println("Remover Produto da fatura? (1 - Sim, 0 - Não): ");
        int re = scanner.nextInt();
        scanner.nextLine();
        if(re == 1){
            faturas.remove(fatura);
        }
        System.out.println("Fatura atualizada com sucesso!");
    }

    private static void listarFaturas() {
        if (faturas.isEmpty()) {
            System.out.println("Nenhuma fatura cadastrada.");
            return;
        }

        for (Fatura fatura : faturas) {
            System.out.println(fatura);
        }
    }

    private static double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    private static void visualizarFatura(Scanner scanner) {
        System.out.println("Digite o número da fatura para visualizar: ");
        int numeroFatura = scanner.nextInt();
        scanner.nextLine();

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

        System.out.println("Fatura Nº " + fatura.getNumero());
        System.out.println("Nome do Cliente: " + fatura.getCliente().getNome());
        System.out.println("Localização do Cliente: " + fatura.getCliente().getLocalizacao());
        System.out.println("NIF do Cliente: " + fatura.getCliente().getNumeroContribuinte());
        System.out.println("Data: " + fatura.getData());
        System.out.println("\n--- Produtos ---");
        for (Produto produto : fatura.getProdutos()) {
            System.out.println(produto);
            System.out.println("Valor do produto sem IVA: " + produto.calcularValorTotalSemIVA());
            System.out.println("IVA do produto: " + produto.calcularIVA(fatura.getCliente().getLocalizacao()));
            System.out.println("Valor do produto com IVA: " + produto.calcularValorTotalComIVA(fatura.getCliente().getLocalizacao()));
        }
        System.out.println("Total Geral sem IVA: " + arredondar(fatura.calcularTotalSemIVA()));
        System.out.println("Total Geral do IVA: " + arredondar(fatura.calcularTotalIVA()));
        System.out.println("Total Geral com IVA: " + arredondar(fatura.calcularTotalComIVA()));
    }
}
