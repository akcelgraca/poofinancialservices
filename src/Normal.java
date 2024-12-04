import java.util.ArrayList;
import java.util.Scanner;

public class Normal extends ProdutoAlimentar {

    public Normal(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                         String Biologico) {
        super(codigo,nome,descricao,quantidade,valorUnitario,Biologico);
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.23;
            case Madeira -> 0.22;
            case Açores -> 0.16;
        };

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    @Override
    public void editarAtributos(Scanner scanner) {
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
        return "Normal";
    }

    @Override
    public String detalhesEspecificos() {
        return super.detalhesEspecificos();
    }
}

