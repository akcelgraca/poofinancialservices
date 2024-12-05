
import java.io.Serializable;
/**
* @author Akcel Graça
 * @version 3.0
*/

public abstract class Produto implements Serializable {

    // Atributos
    /**
     * Código único que identifica o produto.
     */
    private String codigo;

    /**
     * Nome do produto.
     */
    private String nome;

    /**
     * Descrição detalhada do produto.
     */
    private String descricao;

    /**
     * Quantidade disponível ou vendida do produto.
     */
    private int quantidade;

    /**
     * Valor unitário do produto sem IVA.
     */
    private double valorUnitario;

    // Construtor
    /**
     * Construtor que inicializa os atributos básicos de um produto.
     *
     * @param codigo Código único que identifica o produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida do produto.
     * @param valorUnitario Valor unitário do produto sem IVA.
     */
    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    // Getters e Setters
    /**
     * Obtém o código do produto.
     *
     * @return O código do produto.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define o código do produto.
     *
     * @param codigo O código único a ser atribuído ao produto.
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtém o nome do produto.
     *
     * @return O nome do produto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome O nome a ser atribuído ao produto.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém a descrição do produto.
     *
     * @return A descrição do produto.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do produto.
     *
     * @param descricao A descrição a ser atribuída ao produto.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Obtém a quantidade do produto.
     *
     * @return A quantidade do produto.
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto.
     *
     * @param quantidade A quantidade a ser atribuída.
     * @throws IllegalArgumentException Se a quantidade for negativa.
     */
    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    /**
     * Obtém o valor unitário do produto sem IVA.
     *
     * @return O valor unitário do produto.
     */
    public double getValorUnitario() {
        return valorUnitario;
    }

    /**
     * Define o valor unitário do produto sem IVA.
     *
     * @param valorUnitario O valor unitário a ser atribuído.
     * @throws IllegalArgumentException Se o valor for negativo.
     */
    public void setValorUnitario(double valorUnitario) {
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo.");
        }
        this.valorUnitario = valorUnitario;
    }

    // Métodos de Cálculo
    /**
     * Calcula o valor total do produto sem IVA.
     *
     * @return O valor total sem IVA, que é a quantidade multiplicada pelo valor unitário.
     */
    public double calcularValorTotalSemIVA() {
        return quantidade * valorUnitario;
    }

    /**
     * Calcula o valor total do produto com IVA, com base na localização do cliente.
     *
     * @param localizacao A localização do cliente que determina a taxa de IVA.
     * @return O valor total com IVA.
     */
    public double calcularValorTotalComIVA(Cliente.Localizacao localizacao) {
        return calcularValorTotalSemIVA() + calcularIVA(localizacao);
    }

    // Métodos Abstratos
    /**
     * Calcula o valor do IVA com base na localização do cliente.
     *
     * @param localizacao A localização do cliente.
     * @return O valor do IVA calculado.
     */
    public abstract double calcularIVA(Cliente.Localizacao localizacao);

    /**
     * Retorna uma descrição específica do produto, definida pelas subclasses.
     *
     * @return Uma string com os detalhes específicos do produto.
     */
    public abstract String detalhesEspecificos();

    /**
     * Retorna o tipo do produto, definido pelas subclasses.
     *
     * @return Uma string representando o tipo do produto.
     */
    public abstract String getTipoProduto();

    // Representação Textual
    /**
     * Retorna uma representação textual dos atributos do produto.
     *
     * @return Uma string contendo o código, nome, descrição, quantidade, valor unitário e detalhes específicos.
     */
    @Override
    public String toString() {
        return "\nCódigo: " + codigo + "\n" +
                "Nome: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Valor Unitário (sem IVA): " + valorUnitario + "\n" +
                detalhesEspecificos();
    }
}