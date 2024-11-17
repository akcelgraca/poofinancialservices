public class Produtofarmacia extends Produto {
    public enum Prescricao{
        ComPrescricao,
        Normais
    }

    public enum CategoriaF{
        beleza,
        bem_estar,
        bebes,
        animais,
        outro
    }
    private Prescricao prescricao;
    private CategoriaF categoriaf;
    private String medico;
    public Produtofarmacia(String codigo, String nome, String descricao,int quantidade,double valorUnitario,Prescricao prescricao, CategoriaF categoriaf, String medico){
        super(codigo,nome,descricao,quantidade,valorUnitario);
        this.prescricao = prescricao;
        this.categoriaf = categoriaf;
        this.medico = medico;
    }

    public Prescricao getPrescricao(){
        return prescricao;
    }

    public void setPrescricao(Prescricao prescricao){
        this.prescricao = prescricao;
    }

    public CategoriaF getCategoriaf(){
        return categoriaf;
    }

    public void setCategoriaf(CategoriaF categoriaf){
        this.categoriaf = categoriaf;
    }

    public String getMedico(){
        return medico;
    }

    public void setMedico(String medico){
        this.medico = medico;
    }

    // Implementando o método abstrato da classe Produto
    @Override
    public double calcularValorTotalSemIVA() {
        return getQuantidade() * getValorUnitario();
    }

    @Override
    public double calcularIVA() {
        return 0;
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = 0.0;
        switch (prescricao){
            case ComPrescricao:
                taxa = switch (localizacao){
                    case PortugalContinental -> 0.06;
                    case Madeira -> 0.05;
                    case Açores -> 0.04;
                };
                break;
            case Normais:
                taxa = switch (localizacao){
                    case PortugalContinental,Madeira,Açores -> 0.23;
                };
                if (categoriaf == CategoriaF.animais){
                    taxa -= 0.01;
                }
                break;
        }
        double iva = calcularValorTotalSemIVA() * taxa;
        return arredondar(iva);
    }

    private double arredondar(double valor) {
        return Math.round(valor * 100.0) / 100.0; // Arredondar para 2 casas decimais
    }

    // Método toString
    @Override
    public String toString() {
        return
                "codigo='" + getCodigo() + '\'' +
                ", nome='" + getNome() + '\'' +
                ", descricao='" + getDescricao() + '\'' +
                ", quantidade=" + getQuantidade() +
                ", valorUnitario=" + getValorUnitario() +
                ", prescricao=" + prescricao +
                ", categoriaf=" + categoriaf +
                (prescricao == Prescricao.ComPrescricao ? ", medico='" + medico + '\'' : "");
    }
}

