import java.util.Scanner;

public abstract class ProdutoFarmacia extends Produto {

    public ProdutoFarmacia(String codigo, String nome, String descricao, int quantidade, double valorUnitario) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
    }

    @Override
    public String getTipoProduto() {
        return "ProdutoFarmacia";
    }

    @Override
    public String detalhesEspecificos() {
        return ""; // Detalhes específicos nas subclasses
    }

    public abstract void editarAtributos(Scanner scanner);

    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    @Override
    public String toString() {
        return super.toString() + detalhesEspecificos();
    }
}
