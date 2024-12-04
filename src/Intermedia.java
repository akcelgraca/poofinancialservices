import java.util.ArrayList;
import java.util.Scanner;

public class Intermedia extends ProdutoAlimentar {
    private Categoria categoria;

    public enum Categoria {
        CONGELADOS,
        ENLATADOS,
        VINHO
    }

    public Intermedia(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                             String Biologico, Categoria categoria) {
        super(codigo,nome,descricao,quantidade,valorUnitario,Biologico);
        this.categoria = categoria;
    }



    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria){
        this.categoria = categoria;
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.13;
            case Madeira -> 0.12;
            case Açores -> 0.09;
        };

        if (getCategoria() == Categoria.VINHO) {
            taxa += 0.01;
        }

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    @Override
    public void editarAtributos(Scanner scanner) {
        // Editar categoria
        while (true) {
            try {
                System.out.println("Digite a categoria (1 - Congelados, 2 - Enlatados, 3 - Vinho) (atual: " + categoria + "): ");
                String categoriaEntrada = scanner.nextLine();
                if (categoriaEntrada.isBlank()) break; // Manter o valor atual

                Categoria novaCategoria = switch (Integer.parseInt(categoriaEntrada)) {
                    case 1 -> Categoria.CONGELADOS;
                    case 2 -> Categoria.ENLATADOS;
                    case 3 -> Categoria.VINHO;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };
                categoria = novaCategoria;
                break;
            } catch (Exception e) {
                System.out.println("Entrada inválida. Escolha entre 1 (Congelados), 2 (Enlatados) ou 3 (Vinho).");
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
        return "Intermedia";
    }

    @Override
    public String detalhesEspecificos() {
        return super.detalhesEspecificos() + "\nCategoria: " + categoria;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

