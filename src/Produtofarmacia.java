public class Produtofarmacia extends Produto {
    public Produtofarmacia(String codigo, String nome, String descricao,int quantidade,double valorUnitario){
        super(codigo,nome,descricao,quantidade,valorUnitario);

    }
    @Override
    public double calcularIVA() {
        return 0;
    }
}

