import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A classe {@code Reduzida} representa uma subclasse da classe {@code ProdutoAlimentar} que está sujeito
 * a uma taxa reduzida de IVA. Esses produtos podem ter entre 1 e 4 certificações e
 * descontos adicionais no IVA caso sejam biológicos.
 *
 * <p>As certificações válidas são:
 * <ul>
 *     <li>ISO22000</li>
 *     <li>FSSC22000</li>
 *     <li>HACCP</li>
 *     <li>GMP</li>
 * </ul>
 *
 * @see ProdutoAlimentar
 * @author Akcel Graça
 * @version 3.0
 */
public class Reduzida extends ProdutoAlimentar {

    /**
     * Lista de certificações associadas ao produto.
     * Deve conter entre 1 e 4 certificações válidas.
     */
    private ArrayList<String> certificacoes;

    /**
     * Lista de certificações válidas para produtos de taxa reduzida.
     */
    public static final List<String> CertificacaoValida = List.of("ISO22000", "FSSC22000", "HACCP", "GMP");

    /**
     * Construtor para inicializar um produto de taxa reduzida.
     *
     * @param codigo Código único do produto.
     * @param nome Nome do produto.
     * @param descricao Descrição detalhada do produto.
     * @param quantidade Quantidade disponível ou vendida.
     * @param valorUnitario Valor unitário sem IVA.
     * @param Biologico Indica se o produto é biológico ("Sim" ou "Não").
     * @param certificacoes Lista de certificações associadas ao produto (1 a 4).
     * @throws IllegalArgumentException Se as certificações forem inválidas ou o número de certificações não estiver no intervalo permitido.
     */
    public Reduzida(String codigo, String nome, String descricao, int quantidade, double valorUnitario,
                    String Biologico, ArrayList<String> certificacoes) {
        super(codigo, nome, descricao, quantidade, valorUnitario, Biologico);
        if (certificacoes == null || certificacoes.isEmpty() || certificacoes.size() > 4) {
            throw new IllegalArgumentException("Deve haver 1 à 4 certificações");
        }
        for (String certificacao : certificacoes) {
            if (!CertificacaoValida.contains(certificacao)) {
                throw new IllegalArgumentException("Certificação Inválida: " + certificacao);
            }
        }
        this.certificacoes = certificacoes != null ? certificacoes : new ArrayList<>();
    }

    /**
     * Obtém a lista de certificações do produto.
     *
     * @return Uma lista contendo as certificações do produto.
     */
    public ArrayList<String> getCertificacoes() {
        return certificacoes;
    }

    /**
     * Define as certificações associadas ao produto.
     *
     * @param certificacoes Uma lista de certificações válidas (1 a 4).
     */
    public void setCertificacoes(ArrayList<String> certificacoes) {
        this.certificacoes = certificacoes;
    }

    /**
     * Calcula o IVA do produto com base na localização do cliente.
     * <ul>
     *     <li>Desconto de 1% na taxa para produtos com 4 certificações.</li>
     *     <li>Redução de 10% no IVA para produtos biológicos.</li>
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

        if (getCertificacoes().size() == 4) {
            taxa -= 0.01;
        }

        if (getBiologico().equalsIgnoreCase("Sim")) {
            taxa *= 0.90;
        }

        return arredondar(calcularValorTotalSemIVA() * taxa);
    }

    /**
     * Permite ao usuário editar os atributos específicos do produto, como
     * as certificações e o estado biológico.
     *
     * @param scanner Um objeto {@code Scanner} para capturar entradas do usuário.
     */
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

    /**
     * Retorna o tipo do produto, que é "Reduzida".
     *
     * @return Uma string representando o tipo do produto.
     */
    @Override
    public String getTipoProduto() {
        return "Reduzida";
    }

    /**
     * Retorna detalhes específicos sobre o produto, incluindo as certificações.
     *
     * @return Uma string contendo informações específicas do produto.
     */
    @Override
    public String detalhesEspecificos() {
        return "Certificações: " + String.join(", ", certificacoes) + "\n" + super.detalhesEspecificos() + "\n";
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
