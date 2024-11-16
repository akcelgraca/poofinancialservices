public class Produtofarmacia extends Produto {
    public enum Prescricao{
        ComPescricao,
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
    public Produtofarmacia(String codigo, String nome, String descricao,int quantidade,double valorUnitario){
        super(codigo,nome,descricao,quantidade,valorUnitario);
        this.prescricao = prescricao;
        this.categoriaf = categoriaf;
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

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = 0.0;
        switch (prescricao){
            case ComPescricao:
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
        return calcularValorTotalSemIVA() * taxa;
    }
}

