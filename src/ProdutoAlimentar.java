import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ProdutoAlimentar extends Produto {
    private String Biologico;

    public ProdutoAlimentar(String codigo, String nome, String descricao,int quantidade,double valorUnitario,String Biologico){
        super(codigo,nome,descricao,quantidade,valorUnitario);
        this.Biologico = Biologico;

    }

    public String getBiologico(){
        return Biologico;
    }

    public void setBiologico(String Biologico){
        this.Biologico = Biologico;
    }

    @Override
    public String getTipoProduto() {
        return "ProdutoAlimentar";
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        return 0;
    }

    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    @Override
    public String detalhesEspecificos() {
        return "Biológico: " + Biologico;
    }


    @Override
    public String toString() {
        return super.toString();
    }

    public abstract void editarAtributos(Scanner scanner);
}

