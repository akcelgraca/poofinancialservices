public class ProdutoFarmacia extends Produto {
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
    public ProdutoFarmacia(String codigo, String nome, String descricao,int quantidade,double valorUnitario,Prescricao prescricao, CategoriaF categoriaf, String medico){
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

    @Override
    public String getTipoProduto() {
        return "ProdutoFarmacia";
    }

    @Override
    public String detalhesEspecificos() {
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("Tipo: Produto Farmácia\n");
        detalhes.append("Prescrição: ").append(prescricao == Prescricao.ComPrescricao ? "Sim" : "Não").append("\n");

        if (prescricao == Prescricao.ComPrescricao) {
            detalhes.append("Médico: ").append(getMedico()).append("\n");
        } else {
            detalhes.append("Categoria: ").append(getCategoriaf() != null ? getCategoriaf() : "Não tem categoria").append("\n");
        }

        return detalhes.toString();
    }

    // Método toString
    @Override
    public String toString() {
        String s = " Código= " + getCodigo() + "\n" +
                " Nome= " + getNome() + "\n" +
                " Descricao= " + getDescricao() + "\n" +
                " Quantidade= " + getQuantidade() + "\n" +
                " Valor unitário= " + getValorUnitario() + "\n" +
                " Prescrição= " + (prescricao == Prescricao.ComPrescricao ? "Sim" : "Não") + "\n" +
                (prescricao == Prescricao.ComPrescricao ? " Médico/a= " + medico + "\n" : "") +
                (prescricao == Prescricao.Normais
                        ? " Categoria= " + (categoriaf != null ? categoriaf : "não tem categoria") + "\n"
                        : "");
        return
                s;
    }
}

