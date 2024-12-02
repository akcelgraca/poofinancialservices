
public abstract class Produto {

    // Atributos
    private String codigo;
    private String nome;
    private String descricao;
    private int quantidade;
    private double valorUnitario;

    // Construtor com parâmetros
    public Produto(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    // Getters e Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade não pode ser negativa.");
        }
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public abstract String getTipoProduto();

    public void setValorUnitario(double valorUnitario) {
        if (valorUnitario < 0) {
            throw new IllegalArgumentException("Valor unitário não pode ser negativo.");
        }
        this.valorUnitario = valorUnitario;
    }


    public double calcularValorTotalSemIVA() {
        return quantidade * valorUnitario;
    }

    public double calcularValorTotalComIVA(Cliente.Localizacao localizacao) {
        return calcularValorTotalSemIVA() + calcularIVA(localizacao);
    }



    public abstract double calcularIVA(Cliente.Localizacao localizacao);

    public abstract String detalhesEspecificos();

    // Método toString
    @Override
    public String toString() {
        return "Código: " + codigo + "\n" +
                "Nome: " + nome + "\n" +
                "Descrição: " + descricao + "\n" +
                "Quantidade: " + quantidade + "\n" +
                "Valor Unitário (sem IVA): " + valorUnitario + "\n" +
                detalhesEspecificos();
    }

}
