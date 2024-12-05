
import java.util.Scanner;

/**
 * A classe {@code Normal} representa uma subclasse da classe {@code ProdutoAlimentar} sujeito
 * à taxa normal de IVA. Produtos normais podem receber um desconto de 10% na
 * taxa de IVA caso sejam biológicos.
 *
 * <p>A taxa padrão depende da localização do cliente:</p>
 * <ul>
 *     <li>Portugal Continental: 23%</li>
 *     <li>Madeira: 22%</li>
 *     <li>Açores: 16%</li>
 * </ul>
 *
 * @see ProdutoAlimentar
 * @author Akcel Graça
 * @version 3.0
 */
public class Normal extends ProdutoAlimentar {

    /**
     * Construtor para inicializar um produto de taxa normal.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param Biologico Indica se o produto é biológico ("Sim" ou "Não").
     */
    public Normal(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                  String Biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario, Biologico);
    }

    /**
     * Calcula o IVA do produto com base na localização do cliente.
     * <ul>
     *     <li>A taxa padrão é reduzida em 10% caso o produto seja biológico.</li>
     * </ul>
     *
     * @param localizacao A localização do cliente que afeta a taxa de IVA.
     * @return O valor do IVA calculado para o produto.
     */
    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.23;
            case Madeira -> 0.22;
            case Açores -> 0.16;
        };

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    /**
     * Permite ao usuário editar os atributos específicos do produto, como o estado biológico.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
    @Override
    public void editarAtributos(Scanner scanner) {
        // Editar biológico
        while (true) {
            try {
                System.out.print("É biológico (1 - Sim, 0 - Não) (atual: " + getBiologico() + "): ");
                String entrada = scanner.nextLine();
                if (entrada.isBlank()) break; // Manter o valor atual
                int isBio = Integer.parseInt(entrada);
                setBiologico(isBio == 1 ? "Sim" : "Não");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 1 para Sim ou 0 para Não.");
            }
        }
    }

    /**
     * Retorna o tipo do produto, que é "Normal".
     *
     * @return Uma string representando o tipo do produto.
     */
    @Override
    public String getTipoProduto() {
        return "Normal";
    }

    /**
     * Retorna detalhes específicos sobre o produto.
     *
     * @return Uma string contendo informações específicas do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return super.detalhesEspecificos() + "\n";
    }
}
