import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {
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
}
