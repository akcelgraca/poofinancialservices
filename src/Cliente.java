import java.io.Serializable;
/**
* @author Akcel Graça
 * @version 3.0
*/


public class Cliente implements Serializable {

    /**
     * Enum {@code Localizacao} representa as localizações possíveis de um cliente.
     * As opções incluem:
     * <ul>
     *     <li>{@code PortugalContinental}</li>
     *     <li>{@code Madeira}</li>
     *     <li>{@code Açores}</li>
     * </ul>
     */
    public enum Localizacao {
        PortugalContinental,
        Madeira,
        Açores;
    }

    // Atributos
    /**
     * O nome do cliente.
     */
    private String nome;

    /**
     * O número de contribuinte (NIF) do cliente.
     */
    private int numeroContribuinte;

    /**
     * A localização do cliente, representada pelo enum {@code Localizacao}.
     */
    private Localizacao localizacao;

    /**
     * Construtor que inicializa os atributos do cliente.
     *
     * @param nome O nome do cliente.
     * @param numeroContribuinte O número de contribuinte (NIF) do cliente.
     * @param localizacao A localização do cliente, do tipo {@code Localizacao}.
     */
    public Cliente(String nome, int numeroContribuinte, Localizacao localizacao) {
        this.nome = nome;
        this.localizacao = localizacao;
        this.numeroContribuinte = numeroContribuinte;
    }

    // Getters e Setters
    /**
     * Obtém o nome do cliente.
     *
     * @return O nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do cliente.
     *
     * @param nome O nome a ser atribuído ao cliente.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o número de contribuinte (NIF) do cliente.
     *
     * @return O número de contribuinte do cliente.
     */
    public int getNumeroContribuinte() {
        return numeroContribuinte;
    }

    /**
     * Define o número de contribuinte (NIF) do cliente.
     *
     * @param numeroContribuinte O número de contribuinte a ser atribuído.
     */
    public void setNumeroContribuinte(int numeroContribuinte) {
        this.numeroContribuinte = numeroContribuinte;
    }

    /**
     * Obtém a localização do cliente.
     *
     * @return A localização do cliente, do tipo {@code Localizacao}.
     */
    public Localizacao getLocalizacao() {
        return localizacao;
    }

    /**
     * Define a localização do cliente.
     *
     * @param localizacao A localização a ser atribuída, do tipo {@code Localizacao}.
     */
    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    /**
     * Retorna uma representação textual dos detalhes do cliente.
     *
     * @return Uma string contendo o nome, NIF e localização do cliente.
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "NIF: " + numeroContribuinte + "\n" +
                "Localizacao: " + localizacao + "\n";
    }
}

