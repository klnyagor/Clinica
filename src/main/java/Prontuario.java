import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import Leito.Apartamento;
import Leito.Enfermaria;
import Leito.TipoLeito;
import Procedimento.Avancado;
import Procedimento.Basico;
import Procedimento.Comum;
import Procedimento.TipoProcedimento;

public class Prontuario {

	private String nomePaciente;
	private Internacao internacao;
	private Set<Procedimento> procedimentos = new HashSet<>();

	public Prontuario(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public void setNomePaciente(String nomePaciente) {
		this.nomePaciente = nomePaciente;
	}

	public String getNomePaciente() {
		return this.nomePaciente;
	}

	public void setInternacao(Internacao internacao) {
		this.internacao = internacao;
	}

	public Internacao getInternacao() {
		return this.internacao;
	}

	public void addProcedimento(Procedimento procedimento) {
		this.procedimentos.add(procedimento);
	}

	public Set<Procedimento> getProcedimentos() {
		return this.procedimentos;
	}

	public String imprimaConta() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();

		StringBuilder conta = new StringBuilder();

		conta.append("----------------------------------------------------------------------------------------------");

		float valorDiarias = 0.0f;

		// Contabilizar as diárias
		if (internacao != null) {
			valorDiarias += internacao.calcularValorDiarias();
		}

		float valorTotalProcedimentos = 0.00f;
		int qtdeProcedimentosBasicos = 0;
		int qtdeProcedimentosComuns = 0;
		int qtdeProcedimentosAvancados = 0;

		//Contabiliza os procedimentos
		for (Procedimento procedimento : procedimentos) {
			valorTotalProcedimentos += procedimento.calcularValorProcedimento();

			switch (procedimento.getTipo()) {
				case "BASICO":
					qtdeProcedimentosBasicos++;
					break;

				case "COMUM":
					qtdeProcedimentosComuns++;
					break;

				case "AVANCADO":
					qtdeProcedimentosAvancados++;
					break;
			}
		}

		
		conta.append("\nA conta do(a) paciente ").append(nomePaciente).append(" tem valor total de __ ").append(formatter.format(valorDiarias + valorTotalProcedimentos)).append(" __");
		conta.append("\n\nConforme os detalhes abaixo:");


		if (internacao != null) {
			conta.append("\n\nValor Total Diárias:\t\t\t").append(formatter.format(valorDiarias));

			conta.append("\n\t\t\t\t\t").append(internacao.getQtdeDias()).append(" diária").append(internacao.getQtdeDias() > 1 ? "s" : "").append(" em ").append(internacao.getTipo().toLowerCase());
		}

		if (!procedimentos.isEmpty()) {
			conta.append("\n\nValor Total Procedimentos:\t\t").append(formatter.format(valorTotalProcedimentos));

			if (qtdeProcedimentosBasicos > 0) {
				conta.append("\n\t\t\t\t\t").append(qtdeProcedimentosBasicos).append(" procedimento").append(qtdeProcedimentosBasicos > 1 ? "s" : "").append(" básico").append(qtdeProcedimentosBasicos > 1 ? "s" : "");
			}

			if (qtdeProcedimentosComuns > 0) {
				conta.append("\n\t\t\t\t\t").append(qtdeProcedimentosComuns).append(" procedimento").append(qtdeProcedimentosComuns > 1 ? "s" : "").append(" comu").append(qtdeProcedimentosComuns > 1 ? "ns" : "m");
			}

			if (qtdeProcedimentosAvancados > 0) {
				conta.append("\n\t\t\t\t\t").append(qtdeProcedimentosAvancados).append(" procedimento").append(qtdeProcedimentosBasicos > 1 ? "s" : "").append(" avançado").append(qtdeProcedimentosAvancados > 1 ? "s" : "");
			}
		}

		conta.append("\n\nVolte sempre, a casa é sua!").append("\n----------------------------------------------------------------------------------------------");

		return conta.toString();
	}

	private static TipoLeito criarTipoLeito(String tipo){
		switch (tipo) {
			case "ENFERMARIA":
				return new Enfermaria();
			case "APARTAMENTO":
				return new Apartamento();
			default:
				throw new IllegalArgumentException();
		}
	}

	private static TipoProcedimento criarTipoProcedimento(String tipo){
		switch (tipo) {
			case "COMUM":
				return new Comum();
			case "BASICO":
				return new Basico();
			case "AVANCADO":
				return new Avancado();
			default:
				throw new IllegalArgumentException();
		}
	}

	boolean b = false;

	public Prontuario carregueProntuario(String arquivoCsv) throws IOException {
		Prontuario prontuario = new Prontuario(null);

		Path path = Paths.get(arquivoCsv);

		Stream<String> linhas = Files.lines(path);

		linhas.forEach((str) -> {
			if (b == false) {
				b = true;
			} else {
				System.out.println(str);

				String[] dados = str.split(",");

				String nomePaciente = dados[0].trim();

				TipoLeito tipoLeito = dados[1] != null && !dados[1].trim().isEmpty() ? criarTipoLeito(dados[1].trim()) : null;

				int qtdeDiasInternacao = dados[2] != null && !dados[2].trim().isEmpty() ? Integer.parseInt(dados[2].trim()) : -1;

				TipoProcedimento tipoProcedimento = dados[3] != null && !dados[3].trim().isEmpty() ? criarTipoProcedimento(dados[3].trim()) : null;

				int qtdeProcedimentos = dados.length == 5 && dados[4] != null && !dados[4].trim().isEmpty() ? Integer.parseInt(dados[4].trim()) : -1;

				prontuario.nomePaciente = nomePaciente;

				if (tipoLeito != null && qtdeDiasInternacao > 0) {
					prontuario.internacao = new Internacao(tipoLeito, qtdeDiasInternacao);
				}

				if (tipoProcedimento != null && qtdeProcedimentos > 0) {
					while (qtdeProcedimentos > 0) {
						prontuario.addProcedimento(new Procedimento(tipoProcedimento));
						qtdeProcedimentos--;
					}
				}
			}
		});

		return prontuario;
	}

	List<String> l = new ArrayList<>();

	public String salveProntuario() throws IOException {

		l.add("nome_paciente,tipo_leito,qtde_dias_internacao,tipo_procedimento,qtde_procedimentos");

		String l1 = nomePaciente + ",";

		if (internacao != null) {
			l1 += internacao.getTipo() + "," + internacao.getQtdeDias() + ",,";
			l.add(l1);
		}

		if (!procedimentos.isEmpty()) {
			Map<String, Long> procedimentosAgrupados = procedimentos.stream().collect(
					Collectors.groupingBy(p -> p.getTipoProcedimento().getTipo(), Collectors.counting()));

			List<String> procedimentosOrdenados = new ArrayList<>(procedimentosAgrupados.keySet());
			Collections.sort(procedimentosOrdenados);

			for (String chave : procedimentosOrdenados) {
				String l2 = nomePaciente + ",,," + chave + "," + procedimentosAgrupados.get(chave);
				l.add(l2);
			}
		}

		if (l.size() == 1) {
			l1 += ",,,";
			l.add(l1);
		}

		Path path = Paths.get(nomePaciente.replaceAll(" ", "_").concat(String.valueOf(System.currentTimeMillis())).concat(".csv"));

		Files.write(path, l);

		return path.toString();
	}
}
