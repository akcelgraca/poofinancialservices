import java.util.Scanner;

/**
 * A classe {@code SemPrescricao} representa uma subclasse da classe {@code ProdutoFarmacia} que não requerem prescrição médica.
 * Esses produtos são organizados em categorias específicas, e a classe fornece funcionalidades
 * para editar a categoria e calcular o IVA.
 *
 * <p>As categorias disponíveis são:</p>
 * <ul>
 *     <li>{@code BELEZA}</li>
 *     <li>{@code BEM_ESTAR}</li>
 *     <li>{@code BEBES}</li>
 *     <li>{@code ANIMAIS}</li>
 *     <li>{@code OUTRO}</li>
 * </ul>
 *
 * @see ProdutoFarmacia
 * @author Akcel Graça
 * @version 3.0
 */
public class SemPrescricao extends ProdutoFarmacia {

    /**
     * Enum que define as categorias disponíveis para produtos sem prescrição.
     */
    public enum Categoria {
        BELEZA, BEM_ESTAR, BEBES, ANIMAIS, OUTRO
    }

    /**
     * Categoria do produto, do tipo {@code Categoria}.
     */
    private Categoria categoria;

    /**
     * Construtor que inicializa um produto de farmácia sem prescrição médica.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param categoria Categoria do produto, do tipo {@code Categoria}.
     * @throws IllegalArgumentException Se a categoria for nula.
     */
    public SemPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        if (categoria == null) {
            throw new IllegalArgumentException("Produtos sem prescricao devem ter uma categoria definida.");
        }
        this.categoria = categoria;
    }

    /**
     * Obtém a categoria do produto.
     *
     * @return A categoria do produto.
     */
    public Categoria getCategoria() {
        return categoria;
    }

    /**
     * Define a categoria do produto.
     *
     * @param categoria A nova categoria do produto.
     * @throws IllegalArgumentException Se a categoria for nula.
     */
    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria nao pode ser nula.");
        }
        this.categoria = categoria;
    }

    /**
     * Calcula o IVA do produto.
     * <ul>
     *     <li>A taxa padrão é de 23%.</li>
     *     <li>Produtos da categoria "ANIMAIS" recebem uma redução de 1% na taxa.</li>
     * </ul>
     *
     * @param localizacao A localização do cliente (não influencia a taxa para produtos sem prescrição).
     * @return O valor do IVA calculado para o produto.
     */
    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = 0.23; // Padrão para todos os locais
        if (categoria == Categoria.ANIMAIS) {
            taxa -= 0.01; // Redução de 1% para categoria "ANIMAIS"
        }
        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    /**
     * Permite ao usuário editar a categoria do produto.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
    @Override
    public void editarAtributos(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Digite a nova categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro) (atual: "
                        + getCategoria() + "): ");
                String categoriaEntrada = scanner.nextLine();
                if (categoriaEntrada.isBlank()) {
                    System.out.println("Nenhuma alteração foi feita.");
                    break;
                }

                Categoria novaCategoria = switch (Integer.parseInt(categoriaEntrada)) {
                    case 1 -> Categoria.BELEZA;
                    case 2 -> Categoria.BEM_ESTAR;
                    case 3 -> Categoria.BEBES;
                    case 4 -> Categoria.ANIMAIS;
                    case 5 -> Categoria.OUTRO;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };

                setCategoria(novaCategoria);
                System.out.println("Categoria atualizada com sucesso.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Escolha um numero entre 1 e 5.");
            }
        }
    }

    /**
     * Retorna detalhes específicos do produto, incluindo se ele possui prescrição
     * e a categoria do produto.
     *
     * @return Uma string contendo informações específicas do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return "Prescricao: Nao\nCategoria: " + categoria + "\n";
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
