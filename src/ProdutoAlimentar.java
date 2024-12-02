import java.util.ArrayList;
import java.util.List;

public class ProdutoAlimentar extends Produto {
    private boolean isBiologico;
    private TipoTaxa tipoTaxa;
    private ArrayList<String> certificacoes;
    public static final List<String> CertificacaoValida = List.of("ISO22000","FSSC22000","HACCP", "GMP");
    private Categoria categoria;

    public enum TipoTaxa{
        REDUDIZDA,
        INTERMEDIA,
        NORMAL
    }

    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }


    public ProdutoAlimentar(String codigo, String nome, String descricao,int quantidade,double valorUnitario,TipoTaxa tipoTaxa,boolean isBiologico, ArrayList<String> certificacoes,Categoria categoria){
        super(codigo,nome,descricao,quantidade,valorUnitario);
        this.isBiologico = isBiologico;
        this.tipoTaxa = tipoTaxa;
        this.categoria = categoria;
        if(tipoTaxa == TipoTaxa.REDUDIZDA){
            if(certificacoes == null || certificacoes.isEmpty() || certificacoes.size() > 4){
                throw new IllegalArgumentException("Deve haver 1 à 4 certificações");
            }
            for (String certificacao : certificacoes){
                if(!CertificacaoValida.contains(certificacao)){
                    throw new IllegalArgumentException("Certificação Inválida: " + certificacao);
                }
            }

        }
        this.certificacoes = certificacoes != null ? certificacoes : new ArrayList<>();
    }

    // Getters e Setters
    public TipoTaxa getTipoTaxa(){
        return tipoTaxa;
    }

    public void setTipoTaxa(TipoTaxa tipoTaxa){
        this.tipoTaxa = tipoTaxa;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    public ArrayList<String> getCertificacoes(){
        return certificacoes;
    }

    public void setCertificacoes(ArrayList<String> certificacoes){
        this.certificacoes = certificacoes;
    }

    public boolean isBiologico(){
        return this.isBiologico;
    }

    public void setBiologico(boolean isBiologico){
        this.isBiologico = isBiologico;
    }

    @Override
    public String getTipoProduto() {
        return "ProdutoAlimentar";
    }


    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = 0.0;

        switch (tipoTaxa){
            case REDUDIZDA:
                taxa = switch (localizacao){
                    case PortugalContinental -> 0.06;
                    case Madeira -> 0.05;
                    case Açores -> 0.04;
                };
                taxa = 0.06;
                if(certificacoes != null && certificacoes.size() == 4){
                    taxa -= 0.01;
                }
                break;

            case INTERMEDIA:
                taxa = switch (localizacao){
                    case PortugalContinental -> 0.13;
                    case Madeira -> 0.12;
                    case Açores -> 0.09;
                };
                if(categoria == Categoria.VINHO){
                    taxa += 0.01;
                }
                break;

            case NORMAL:
                taxa = switch (localizacao){
                    case PortugalContinental -> 0.23;
                    case Madeira -> 0.22;
                    case Açores -> 0.16;
                };
                break;
        }

        if(isBiologico){
            taxa *= 0.90;
        }

        double iva = calcularValorTotalSemIVA() * taxa;
        return arredondar(iva);

    }

    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    @Override
    public String detalhesEspecificos() {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Tipo: Produto Alimentar\n");
        detalhes.append("Biológico: ").append(isBiologico() ? "Sim" : "Não").append("\n");
        detalhes.append("Tipo de Taxa: ").append(getTipoTaxa()).append("\n");

        if (getTipoTaxa() == TipoTaxa.INTERMEDIA) {
            detalhes.append("Categoria: ").append(getCategoria()).append("\n");
        }

        if (getTipoTaxa() == TipoTaxa.REDUDIZDA) {
            detalhes.append("Certificações: ").append(String.join(", ", getCertificacoes())).append("\n");
        }

        return detalhes.toString();
    }

    @Override
    public String toString() {
        String s = " Código= " + getCodigo() + "\n" +
                " Nome= " + getNome() + "\n" +
                " Descrição= " + getDescricao() + "\n" +
                " Quantidade= " + getQuantidade() + "\n" +
                " Valor unitário= " + getValorUnitario() + "\n" +
                " Tipo de taxa= " + tipoTaxa + "\n" +
                " Categoria= " + (categoria != null ? categoria : "não possui categorias") + "\n" +
                " Biológico= " + (isBiologico ? "Sim" : "Não") + "\n" +
                " Certificações= " + (certificacoes != null ? certificacoes : "N/A") + "\n";
        return s;
    }
}
