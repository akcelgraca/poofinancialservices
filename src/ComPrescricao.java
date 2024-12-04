import java.util.Scanner;

public class ComPrescricao extends ProdutoFarmacia {
    private String medico;

    public ComPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String medico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        if (medico == null || medico.isBlank()) {
            throw new IllegalArgumentException("Produtos com prescricao devem ter um medico associado.");
        }
        this.medico = medico;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        if (medico == null || medico.isBlank()) {
            throw new IllegalArgumentException("Medico não pode ser vazio.");
        }
        this.medico = medico;
    }

    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.06;
            case Madeira -> 0.05;
            case Açores -> 0.04;
        };
        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    @Override
    public void editarAtributos(Scanner scanner) {
        System.out.print("Digite o nome do medico (atual: " + getMedico() + "): ");
        String novoMedico = scanner.nextLine();
        if (!novoMedico.isBlank()) {
            setMedico(novoMedico);
            System.out.println("Medico atualizado com sucesso.");
        } else {
            System.out.println("Nenhuma alteração foi feita.");
        }
    }


    @Override
    public String detalhesEspecificos() {
        return "Prescricao: Sim\nMedico/a: " + medico + "\n";
    }
}
