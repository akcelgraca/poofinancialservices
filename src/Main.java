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
        Funcoes f = new Funcoes();
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
                case 1 -> f.criarCliente();
                case 2 -> f.editarClientes();
                case 3 -> f.listarClientes();
                case 4 -> f.criarFaturas();
                case 5 -> f.editarFatura();
                case 6 -> f.listarFaturas();
                case 7 -> f.visualizarFatura();
                case 0 -> System.out.println("A sair...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}
