import java.util.Scanner;

public class SemPrescricao extends ProdutoFarmacia {
    public enum Categoria {
        BELEZA, BEM_ESTAR, BEBES, ANIMAIS, OUTRO
    }

    private Categoria categoria;

    public SemPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, Categoria categoria) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        if (categoria == null) {
            throw new IllegalArgumentException("Produtos sem prescricao devem ter uma categoria definida.");
        }
        this.categoria = categoria;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("Categoria nao pode ser nula.");
        }
        this.categoria = categoria;
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = 0.23; // Padrão para todos os locais
        if (categoria == Categoria.ANIMAIS) {
            taxa -= 0.01; // Redução de 1% para categoria "animais"
        }
        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    @Override
    public void editarAtributos(Scanner scanner) {
        while (true) {
            try {
                System.out.println("Digite a nova categoria (1 - Beleza, 2 - Bem-estar, 3 - Bebês, 4 - Animais, 5 - Outro) (atual: "
                        + getCategoria() + "): ");
                String categoriaEntrada = scanner.nextLine();
                if (categoriaEntrada.isBlank()) {
                    System.out.println("Nenhuma alteração foi feita.");
                    break;
                }

                Categoria novaCategoria = switch (Integer.parseInt(categoriaEntrada)) {
                    case 1 -> Categoria.BELEZA;
                    case 2 -> Categoria.BEM_ESTAR;
                    case 3 -> Categoria.BEBES;
                    case 4 -> Categoria.ANIMAIS;
                    case 5 -> Categoria.OUTRO;
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };

                setCategoria(novaCategoria);
                System.out.println("Categoria atualizada com sucesso.");
                break;
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Escolha um numero entre 1 e 5.");
            }
        }
    }


    @Override
    public String detalhesEspecificos() {
        return "Prescricao: Nao\nCategoria: " + categoria + "\n";
    }
}
