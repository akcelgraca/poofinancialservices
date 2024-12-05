import java.time.Instant;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 * A classe {@code Main} contém o método principal {@code main} que serve como ponto de entrada
 * para o sistema de gestão de clientes e faturas.
 * O sistema interage com o usuário por meio de um menu, permitindo realizar as seguintes operações:
 * <ul>
 *     <li>Criar clientes</li>
 *     <li>Editar clientes</li>
 *     <li>Listar clientes</li>
 *     <li>Criar faturas</li>
 *     <li>Editar faturas</li>
 *     <li>Listar faturas</li>
 *     <li>Visualizar faturas</li>
 * </ul>
 * O programa continua executando até que o usuário escolha a opção de sair (opção 0).
 *
 * <p>Fluxo:</p>
 * <ol>
 *     <li>O usuário escolhe uma opção do menu.</li>
 *     <li>O sistema executa a função correspondente à opção escolhida.</li>
 *     <li>O processo se repete até que o usuário decida sair.</li>
 * </ol>
 *
 * @see Funcoes
 * @see Cliente
 * @see Fatura
 * @author Akcel Graça
 * @version 3.0
 */
public class Main {

    public static void main(String[] args) {
        Funcoes f;
        f = new Funcoes();
        Scanner scanner = new Scanner(System.in);
        //List<Fatura> faturas;
        //faturas = f.carregarFaturas();
        String op;
        int opcao = -1;

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

            try {
                System.out.print("ESCOLHA UMA OPÇÃO: ");
                op = scanner.nextLine();
                opcao = Integer.parseInt(op.trim());

                switch (opcao) {
                    case 1 -> f.criarCliente();
                    case 2 -> f.editarClientes();
                    case 3 -> f.listarClientes();
                    case 4 -> f.criarFaturas();
                    case 5 -> f.editarFatura();
                    case 6 -> f.listarFaturas();
                    case 7 -> f.visualizarFatura();
                    case 0 -> {
                        //f.salvarFaturas(faturas);
                        System.out.println("A sair...");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
            }
        } while (opcao != 0);
    }
}

