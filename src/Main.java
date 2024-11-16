import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("\n--- Menu de Gestão de Clientes ---");
            System.out.println("1. Criar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Listar Clientes");
            System.out.println("0. Sair");
            System.out.println("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao){
                case 1 -> criar_cliente(scanner);
                case 2 -> editar_clientes(scanner);
                case 3 -> listar_clientes();
                case 0 -> System.out.println("A sair...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private static void criar_cliente(Scanner scanner){
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

    private static void editar_clientes(Scanner scanner){
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

    private static void listar_clientes(){
        if (clientes.size() == 0) {
            System.out.println("Nenhum cliente cadastrado");
            return;
        }
        System.out.println("--- Lista de Clientes ---");
        for(Cliente cliente : clientes){
            System.out.println(cliente);
        }
    }
}