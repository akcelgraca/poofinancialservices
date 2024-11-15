public class Produtoalimentar extends Produto {
    private boolean biologico;
    public Produtoalimentar(String codigo, String nome, String descricao,int quantidade,double valorUnitario){
        super(codigo,nome,descricao,quantidade,valorUnitario);
        this.biologico = biologico;
    }

    @Override
    public double calcularIVA() {
        return 0;
    }
}
