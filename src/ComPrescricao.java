import java.util.Scanner;

/**
 * A classe {@code ComPrescricao} representa uma subclasse da classe {@code ProdutoFarmacia} que requerem prescrição médica.
 * Esses produtos possuem um médico associado responsável pela prescrição.
 *
 * <p>A classe inclui validações para garantir que o nome do médico esteja preenchido
 * e fornece métodos específicos para edição e cálculo do IVA.</p>
 *
 * @see ProdutoFarmacia
 * @author Akcel Graça
 * @version 3.0
 */
public class ComPrescricao extends ProdutoFarmacia {

    /**
     * Nome do médico associado à prescrição do produto.
     */
    private String medico;

    /**
     * Construtor que inicializa um produto com prescrição médica.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param medico Nome do médico responsável pela prescrição.
     * @throws IllegalArgumentException Se o nome do médico for nulo ou vazio.
     */
    public ComPrescricao(String codigo, String nome, String descricao, int quantidade, double valorUnitario, String medico) {
        super(codigo, nome, descricao, quantidade, valorUnitario);
        if (medico == null || medico.isBlank()) {
            throw new IllegalArgumentException("Produtos com prescricao devem ter um medico associado.");
        }
        this.medico = medico;
    }

    /**
     * Obtém o nome do médico associado à prescrição.
     *
     * @return O nome do médico.
     */
    public String getMedico() {
        return medico;
    }

    /**
     * Define o nome do médico responsável pela prescrição.
     *
     * @param medico O nome do médico a ser associado.
     * @throws IllegalArgumentException Se o nome do médico for nulo ou vazio.
     */
    public void setMedico(String medico) {
        if (medico == null || medico.isBlank()) {
            throw new IllegalArgumentException("Medico não pode ser vazio.");
        }
        this.medico = medico;
    }

    /**
     * Calcula o IVA do produto com base na localização do cliente.
     * <ul>
     *     <li>Portugal Continental: 6%</li>
     *     <li>Madeira: 5%</li>
     *     <li>Açores: 4%</li>
     * </ul>
     *
     * @param localizacao A localização do cliente que afeta a taxa de IVA.
     * @return O valor do IVA calculado para o produto.
     */
    @Override
    public double calcularIVA(Cliente.Localizacao localizacao) {
        double taxa = switch (localizacao) {
            case PortugalContinental -> 0.06;
            case Madeira -> 0.05;
            case Açores -> 0.04;
        };
        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    /**
     * Permite ao usuário editar o atributo específico do produto, como o nome do médico.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
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

    /**
     * Retorna detalhes específicos do produto, incluindo se ele possui prescrição e o nome do médico associado.
     *
     * @return Uma string contendo informações específicas do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return "Prescricao: Sim\nMedico/a: " + medico + "\n";
    }

    /**
     * Retorna uma representação textual do produto.
     *
     * @return Uma string contendo os detalhes do produto.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}
