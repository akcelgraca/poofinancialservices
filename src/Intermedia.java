
import java.util.Scanner;

/**
 * A classe {@code Intermedia} representa uma subclasse da  classe {@code ProdutoAlimentar} que está sujeito
 * a uma taxa intermediária de IVA. Esses produtos podem pertencer às categorias:
 * <ul>
 *     <li>Congelados</li>
 *     <li>Enlatados</li>
 *     <li>Vinho</li>
 * </ul>
 * Produtos biológicos recebem um desconto adicional no IVA, e os produtos da categoria "Vinho"
 * têm um acréscimo de 1% na taxa.
 *
 * @see ProdutoAlimentar
 * @author Akcel Graça
 * @version 3.0
 */
public class Intermedia extends ProdutoAlimentar {

    /**
     * Representa as categorias disponíveis para produtos de taxa intermediária.
     */
    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    /**
     * Categoria do produto, que pode ser "Congelados", "Enlatados" ou "Vinho".
     */
    private Categoria categoria;

    /**
     * Construtor para inicializar um produto de taxa intermediária.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param Biologico Indica se o produto é biológico ("Sim" ou "Não").
     * @param categoria Categoria do produto, do tipo {@code Categoria}.
     */
    public Intermedia(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                      String Biologico, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario, Biologico);
        this.categoria = categoria;
    }

    /**
     * Obtém a categoria do produto.
     *
     * @return A categoria do produto, do tipo {@code Categoria}.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     *
     * @param categoria A categoria a ser atribuída ao produto, do tipo {@code Categoria}.
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    /**
     * Calcula o IVA do produto com base na localização do cliente e na categoria.
     * <ul>
     *     <li>Produtos da categoria "Vinho" têm um acréscimo de 1% na taxa de IVA.</li>
     *     <li>Produtos biológicos recebem um desconto de 10% na taxa de IVA.</li>
     * </ul>
     *
     * @param localizacao A localização do cliente que afeta a taxa de IVA.
     * @return O valor do IVA calculado para o produto.
     */
    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.13;
            case Madeira -> 0.12;
            case Açores -> 0.09;
        };

        if (getCategoria() == Categoria.VINHO) {
            taxa += 0.01;
        }

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    /**
     * Permite ao usuário editar os atributos específicos do produto, como a categoria e o estado biológico.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
    @Override
    public void editarAtributos(Scanner scanner) {
        // Editar categoria
        while (true) {
            try {
                System.out.println("Digite a categoria (1 - Congelados, 2 - Enlatados, 3 - Vinho) (atual: " + categoria + "): ");
                String categoriaEntrada = scanner.nextLine();
                if (categoriaEntrada.isBlank()) break; // Manter o valor atual

                Categoria novaCategoria = switch (Integer.parseInt(categoriaEntrada)) {
                    case 1 -> Categoria.CONGELADOS;
                    case 2 -> Categoria.ENLATADOS;
                    case 3 -> Categoria.VINHO;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };
                categoria = novaCategoria;
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Escolha entre 1 (Congelados), 2 (Enlatados) ou 3 (Vinho).");
            }
        }

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
     * Retorna o tipo do produto, que é "Intermedia".
     *
     * @return Uma string representando o tipo do produto.
     */
    @Override
    public String getTipoProduto() {
        return "Intermedia";
    }

    /**
     * Retorna detalhes específicos sobre o produto, incluindo a categoria.
     *
     * @return Uma string contendo informações específicas do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return "Categoria: " + categoria + "\n" + super.detalhesEspecificos() + "\n";
    }

    /**
     * Retorna uma representação textual do produto.
     *
     * @return Uma string contendo os detalhes do produto.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
