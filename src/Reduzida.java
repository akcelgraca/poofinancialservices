import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reduzida extends ProdutoAlimentar {
    private ArrayList<String> certificacoes;
    public static final List<String> CertificacaoValida = List.of("ISO22000","FSSC22000","HACCP", "GMP");

    public Reduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                    String Biologico, ArrayList<String> certificacoes) {
        super(codigo,nome,descricao,quantidade,valorUnitario,Biologico);
            if(certificacoes == null || certificacoes.isEmpty() || certificacoes.size() > 4){
                throw new IllegalArgumentException("Deve haver 1 à 4 certificações");
            }
            for (String certificacao : certificacoes){
                if(!CertificacaoValida.contains(certificacao)){
                    throw new IllegalArgumentException("Certificação Inválida: " + certificacao);
                }
            }
        this.certificacoes = certificacoes != null ? certificacoes : new ArrayList<>();
    }

    public ArrayList<String> getCertificacoes(){
        return certificacoes;
    }

    public void setCertificacoes(ArrayList<String> certificacoes){
        this.certificacoes = certificacoes;
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.06;
            case Madeira -> 0.05;
            case Açores -> 0.04;
        };

        if (getCertificacoes().size() == 4) {
            taxa -= 0.01;
        }

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    @Override
    public void editarAtributos(Scanner scanner) {
        // Editar certificações
        while (true) {
            try {
                System.out.println("Digite as certificações do produto (ISO22000, FSSC22000, HACCP, GMP): ");
                System.out.println("'fim' para encerrar.");

                while (certificacoes.size() < 4) {
                    try {
                        System.out.print("Certificação " + (certificacoes.size() + 1) + ": ");
                        String certificacao = scanner.nextLine();

                        // Verifica se o usuário deseja encerrar
                        if (certificacao.equalsIgnoreCase("fim")) break;

                        // Valida a certificação
                        if (CertificacaoValida.contains(certificacao)) {
                            certificacoes.add(certificacao);
                        } else {
                            System.out.println("Certificação inválida! Escolha entre: ISO22000, FSSC22000, HACCP, GMP.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro inesperado. Tente novamente.");
                    }
                }
                break;
            } catch (Exception e) {
                System.out.println("Erro ao adicionar certificações.");
            }
        }

        // Editar biológico
        while (true) {
            try {
                System.out.print("É biológico (1 - Sim, 0 - Não) (atual: " + getBiologico() + "): ");
                String entrada = scanner.nextLine();
                if (entrada.isBlank()) break; // Manter o valor atual
                int isBio = Integer.parseInt(entrada);
                setBiologico(isBio == 1 ? "Sim" : "Não");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Digite 1 para Sim ou 0 para Não.");
            }
        }
    }

    @Override
    public String getTipoProduto() {
        return "Reduzida";
    }

    @Override
    public String detalhesEspecificos() {
        return super.detalhesEspecificos() + "\nCertificações: " + String.join(", ", certificacoes);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

