import java.util.ArrayList;
import java.io.Serializable;

/**
 * A classe {@code Fatura} representa uma fatura emitida por uma empresa.
 * Cada fatura está associada a um cliente, possui uma data, um número identificador,
 * e uma lista de produtos incluídos. A classe fornece métodos para calcular
 * totais com e sem IVA, além de gerenciar os produtos associados.
 *
 * @see Produto
 * @see Cliente
 * @author Akcel Graça
 * @version 3.0
 */
public class Fatura implements Serializable {

    /**
     * Número identificador da fatura.
     */
    private int numero;

    /**
     * Cliente associado à fatura.
     */
    private Cliente cliente;

    /**
     * Data da emissão da fatura, no formato "dd/MM/yyyy".
     */
    private String data;

    /**
     * Lista de produtos incluídos na fatura.
     */
    private ArrayList<Produto> produtos;

    /**
     * Construtor que inicializa uma fatura com o número, cliente e data informados.
     *
     * @param numero Número identificador da fatura.
     * @param cliente Cliente associado à fatura.
     * @param data Data de emissão da fatura, no formato "dd/MM/yyyy".
     */
    public Fatura(int numero, Cliente cliente, String data) {
        this.numero = numero;
        this.cliente = cliente;
        this.data = data;
        this.produtos = new ArrayList<>();
    }

    // Getters e Setters
    /**
     * Obtém o número da fatura.
     *
     * @return O número da fatura.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Define o número da fatura.
     *
     * @param numero O número da fatura.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Obtém o cliente associado à fatura.
     *
     * @return O cliente associado.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Define o cliente associado à fatura.
     *
     * @param cliente O cliente a ser associado.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtém a data de emissão da fatura.
     *
     * @return A data de emissão.
     */
    public String getData() {
        return data;
    }

    /**
     * Define a data de emissão da fatura.
     *
     * @param data A data de emissão, no formato "dd/MM/yyyy".
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Obtém a lista de produtos incluídos na fatura.
     *
     * @return A lista de produtos.
     */
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Define a lista de produtos incluídos na fatura.
     *
     * @param produtos A lista de produtos a ser associada.
     */
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    // Métodos de gerenciamento de produtos
    /**
     * Adiciona um produto à fatura.
     *
     * @param produto O produto a ser adicionado.
     */
    public void adicionarProduto(Produto produto) {
        this.produtos.add(produto);
    }

    /**
     * Remove um produto da fatura.
     *
     * @param produto O produto a ser removido.
     */
    public void removerProduto(Produto produto) {
        this.produtos.remove(produto);
    }

    // Métodos de cálculo
    /**
     * Calcula o total do valor dos produtos sem IVA.
     *
     * @return O total sem IVA.
     */
    public double calcularTotalSemIVA() {
        double totalSemIVA = 0.0;
        for (Produto produto : produtos) {
            totalSemIVA += produto.calcularValorTotalSemIVA();
        }
        return totalSemIVA;
    }

    /**
     * Calcula o total do IVA dos produtos com base na localização do cliente.
     *
     * @return O total do IVA.
     */
    public double calcularTotalIVA() {
        double totalIVA = 0.0;
        for (Produto produto : produtos) {
            totalIVA += produto.calcularIVA(cliente.getLocalizacao());
        }
        return totalIVA;
    }

    /**
     * Calcula o total do valor dos produtos com IVA.
     *
     * @return O total com IVA.
     */
    public double calcularTotalComIVA() {
        double totalComIVA = 0.0;
        for (Produto produto : produtos) {
            totalComIVA += produto.calcularValorTotalComIVA(cliente.getLocalizacao());
        }
        return totalComIVA;
    }

    /**
     * Calcula o total da quantidade de produtos na fatura.
     *
     * @return A quantidade total de produtos.
     */
    public int QuantidadeTotalProdutos() {
        int quantidadeTotal = 0;
        for (Produto produto : produtos) {
            quantidadeTotal += produto.getQuantidade();
        }
        return quantidadeTotal;
    }

    /**
     * Arredonda um valor para duas casas decimais.
     *
     * @param valor O valor a ser arredondado.
     * @return O valor arredondado.
     */
    public double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0;
    }

    // Representação textual
    /**
     * Retorna uma representação textual dos detalhes da fatura.
     *
     * @return Uma string com os detalhes da fatura, incluindo o cliente, localização,
     * totais e quantidade de produtos.
     */
    @Override
    public String toString() {
        String detalhes = "Fatura Nº " + numero + "\n";
        detalhes += "Cliente: " + cliente.getNome() + "\n";
        detalhes += "Localização: " + cliente.getLocalizacao() + "\n";
        detalhes += "Quantidade de produtos: " + QuantidadeTotalProdutos() + "\n";
        detalhes += "Total sem IVA: " + arredondar(calcularTotalSemIVA()) + "\n";
        detalhes += "Total IVA: " + arredondar(calcularTotalIVA()) + "\n";
        detalhes += "Total com IVA: " + arredondar(calcularTotalComIVA()) + "\n";

        return detalhes;
    }
}
