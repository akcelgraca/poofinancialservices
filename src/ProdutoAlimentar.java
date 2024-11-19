import java.util.ArrayList;
public class ProdutoAlimentar extends Produto {
    private boolean isBiologico;
    private TipoTaxa tipoTaxa;
    private ArrayList<String> certificacoes;
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
        this.certificacoes = certificacoes;
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
    public String toString() {
        return
                "codigo='" + getCodigo() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                ", quantidade=" + getQuantidade() +
                ", valorUnitario=" + getValorUnitario() +
                ", tipoTaxa=" + tipoTaxa +
                ", categoria=" + (categoria != null ? categoria : "N/A") +
                ", isBiologico=" + isBiologico +
                ", certificacoes=" + (certificacoes != null ? certificacoes : "N/A");
    }
}
