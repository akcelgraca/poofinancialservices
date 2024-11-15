public class Cliente {

    public enum Localizacao{
        PortugalContinental,
        Madeira,
        Açores;
    }

    // Atributos
    private String nome;
    private String numeroContribuinte; // NIF
    private Localizacao localizacao;

    // Construtor com parâmetros
    public Cliente(String nome, String numeroContribuinte, Localizacao localizacao) {
        if (!validarNumeroContribuinte(numeroContribuinte)) {
            throw new IllegalArgumentException("Número de contribuinte inválido!");
        }
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

    public String getNumeroContribuinte() {
        return numeroContribuinte;
    }

    public void setNumeroContribuinte(String numeroContribuinte){
        verificarcontribuinte(numeroContribuinte);
        this.numeroContribuinte = numeroContribuinte;
    }

    public Localizacao getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Localizacao localizacao) {
        this.localizacao = localizacao;
    }

    // Método para validar o número de contribuinte (exemplo simples)
    private boolean validarNumeroContribuinte(String numeroContribuinte) {
        return numeroContribuinte != null && numeroContribuinte.matches("\\d{9}");
    }

    public void verificarcontribuinte(String num){
        if (!validarNumeroContribuinte(num)) {
            throw new IllegalArgumentException("Número de contribuinte inválido!");
        }

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

