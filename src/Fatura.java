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

    public void adicionarProduto(Produto produto){
        this.produtos.add(produto);
    }

    public void removerProduto(Produto produto){
        this.produtos.remove(produto);
    }

    public double
}
