
import java.util.Scanner;

/**
 * A classe abstrata {@code ProdutoAlimentar} representa uma subclasse de {@code Produto}
 * para produtos alimentares. Esta classe adiciona a característica de ser ou não biológico
 * e define métodos e atributos adicionais específicos para produtos alimentares.
 * @see Produto
 * @author Akcel Graça
 * @version 3.0
 */
public abstract class ProdutoAlimentar extends Produto {

    /**
     * Indica se o produto é biológico. Pode ser "Sim" ou "Não".
     */
    private String Biologico;

    /**
     * Construtor para inicializar os atributos de um {@code ProdutoAlimentar}.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param Biologico Indica se o produto é biológico ("Sim" ou "Não").
     */
    public ProdutoAlimentar(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String Biologico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        this.Biologico = Biologico;
    }

    /**
     * Obtém o estado biológico do produto.
     *
     * @return Uma string indicando se o produto é biológico ("Sim" ou "Não").
     */
    public String getBiologico() {
        return Biologico;
    }

    /**
     * Define o estado biológico do produto.
     *
     * @param Biologico Uma string indicando se o produto é biológico ("Sim" ou "Não").
     */
    public void setBiologico(String Biologico) {
        this.Biologico = Biologico;
    }

    /**
     * Obtém o tipo do produto, que é "ProdutoAlimentar".
     *
     * @return Uma string representando o tipo do produto.
     */
    @Override
    public String getTipoProduto() {
        return "ProdutoAlimentar";
    }

    /**
     * Calcula o IVA do produto. Esta implementação base retorna 0
     *
     * @param localizacao A localização do cliente que afeta a taxa de IVA.
     * @return O valor do IVA calculado (atualmente 0 na classe base).
     */
    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        return 0;
    }

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
     * Retorna detalhes específicos do produto alimentar, indicando se ele é biológico.
     *
     * @return Uma string com informações específicas sobre o estado biológico do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return "Biológico: " + Biologico;
    }

    /**
     * Retorna uma representação textual do produto, utilizando a implementação de {@code Produto}.
     *
     * @return Uma string contendo os atributos do produto alimentar.
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Método abstrato para editar os atributos específicos de um produto alimentar.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
    public abstract void editarAtributos(Scanner scanner);
}
