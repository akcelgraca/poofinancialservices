public abstract class Cliente {

    public enum Localizacao{
        PortugalContinental,
        Madeira,
        Açores;
    }

    // Atributos
    private String nome;
    private int numeroContribuinte; // NIF
    private Localizacao localizacao;

    // Construtor com parâmetros
    public Cliente(String nome, int numeroContribuinte, Localizacao localizacao) {
        this.nome = nome;
        this.numeroContribuinte = numeroContribuinte;
        this.localizacao = localizacao;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(int numeroContribuinte){
        this.numeroContribuinte = numeroContribuinte;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    // Método para validar o número de contribuinte
    private boolean validarNumeroContribuinte(int numeroContribuinte) {
        return String.valueOf(numeroContribuinte).length() == 9;
    }

    // Método toString para exibir detalhes do cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "nome='" + nome + '\'' +
                ", numeroContribuinte='" + numeroContribuinte + '\'' +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}

