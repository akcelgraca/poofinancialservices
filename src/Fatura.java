import java.util.ArrayList;
import java.util.List;

public class Fatura {
    private int numero;
    private Cliente cliente;
    private String data;
    private ArrayList<Produto> produtos;

    //Construtor
    public Fatura(int numero,Cliente cliente, String data){
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    //Getters e Setters
    public int getNumero(){
        return numero;
    }

    public void setNumero(int numero){
        this.numero = numero;
    }

    public Cliente getCliente(){
        return cliente;
    }

    public void setCliente(Cliente cliente){
        this.cliente = cliente;
    }

    public String getData(){
        return data;
    }

    public void setData(String data){
        this.data = data;
    }

    public ArrayList<Produto> getProdutos(){
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }



    public void adicionarProduto(Produto produto){
        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto){
        this.produtos.remove(produto);
    }

    public double calcularTotalSemIVA() {
        double totalSemIVA = 0.0;
        for (Produto produto : produtos) {
            totalSemIVA += produto.calcularValorTotalSemIVA();
        }
        return totalSemIVA;
    }

    public double calcularTotalIVA() {
        double totalIVA = 0.0;
        for (Produto produto : produtos) {
            totalIVA += produto.calcularIVA(cliente.getLocalizacao());
        }
        return totalIVA;
    }

    public double calcularTotalComIVA() {
        double totalComIVA = 0.0;
        for (Produto produto : produtos) {
            totalComIVA += produto.calcularValorTotalComIVA(cliente.getLocalizacao());
        }
        return totalComIVA;
    }

    public int QuantidadeTotalProdutos() {
        int quantidadeTotal = 0;
        for (Produto produto : produtos) {
            quantidadeTotal += produto.getQuantidade();
        }
        return quantidadeTotal;
    }

    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    public String toStringComTodasFaturas(List<Fatura> todasAsFaturas) {
        String detalhes = "Fatura Nº " + numero + "\n" + ("Cliente: " + cliente.getNome() + "\n");
        detalhes += "Localização: : " + cliente.getLocalizacao() + "\n";
        detalhes += "Quantidade de produtos: " + QuantidadeTotalProdutos() + "\n";
        detalhes += "Quantidade de faturas: " + todasAsFaturas.size() + "\n";
        for (Produto produto : produtos) {
            detalhes += produto.toString() + "\n";
        }

        detalhes += "Total sem IVA: " + calcularTotalSemIVA() + "\n";
        detalhes += "Total IVA: " + calcularTotalIVA() + "\n";
        detalhes += "Total com IVA: " + calcularTotalComIVA() + "\n";

        return detalhes;
    }

    @Override
    public String toString() {
        String detalhes = "Fatura Nº " + numero + "\n";
        detalhes += "Cliente: " + cliente.getNome() + "\n";
        detalhes += "Localização: : " + cliente.getLocalizacao() + "\n";
        detalhes += "Quantidade de produtos: " + QuantidadeTotalProdutos() + "\n";
        detalhes += "Total sem IVA: " + arredondar(calcularTotalSemIVA()) + "\n";
        detalhes += "Total IVA: " + arredondar(calcularTotalIVA()) + "\n";
        detalhes += "Total com IVA: " + arredondar(calcularTotalComIVA()) + "\n";

        return detalhes;
    }


}
