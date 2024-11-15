/**
 * Classe Cliente - Representa um cliente na aplicação POO Financial Services.
 */
public class Cliente {

    // Atributos
    private String nome;
    private String numeroContribuinte; // Número de contribuinte (NIF)
    private String localizacao; // Ex.: "Portugal Continental", "Madeira", "Açores"

    // Construtor padrão
    public Cliente() {
        this.nome = "";
        this.numeroContribuinte = "";
        this.localizacao = "";
    }

    // Construtor com parâmetros
    public Cliente(String nome, String numeroContribuinte, String localizacao) {
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

    public void setNumeroContribuinte(String numeroContribuinte) {
        if (!validarNumeroContribuinte(numeroContribuinte)) {
            throw new IllegalArgumentException("Número de contribuinte inválido!");
        }
        this.numeroContribuinte = numeroContribuinte;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    // Método para validar o número de contribuinte (exemplo simples)
    private boolean validarNumeroContribuinte(String numeroContribuinte) {
        return numeroContribuinte != null && numeroContribuinte.matches("\\d{9}");
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

