import java.util.Scanner;

/**
 * A classe abstrata {@code ProdutoFarmacia} representa uma subclasse da classe {@code Produto}
 * para produtos de farmácia. Esta classe serve como base para subclasses que definem
 * atributos e comportamentos específicos para diferentes tipos de produtos farmacêuticos.
 *
 * @see Produto
 * @author Akcel Graça
 * @version 3.0
 */
public abstract class ProdutoFarmacia extends Produto {

    /**
     * Construtor que inicializa os atributos básicos de um produto de farmácia.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     */
    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
    }

    /**
     * Retorna o tipo do produto, que é "ProdutoFarmacia".
     *
     * @return Uma string representando o tipo do produto.
     */
    @Override
    public String getTipoProduto() {
        return "ProdutoFarmacia";
    }

    /**
     * Método abstrato para editar os atributos específicos de um produto farmacêutico.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
    public abstract void editarAtributos(Scanner scanner);

    /**
     * Arredonda um valor para duas casas decimais.
     *
     * @param valor O valor a ser arredondado.
     * @return O valor arredondado com duas casas decimais.
     */
    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    /**
     * Retorna uma representação textual do produto, utilizando a implementação da classe {@code Produto}.
     *
     * @return Uma string contendo os atributos do produto de farmácia.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
