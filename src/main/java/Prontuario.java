import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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

	public String imprimaConta(){
		ProntuarioRepository repository = new ProntuarioRepository(this);
		return repository.imprimaConta();
	}

	public Prontuario carregueProntuario(String arquivoCsv) throws IOException {
		ProntuarioRepository repository = new ProntuarioRepository(this);
		return repository.carregueProntuario(arquivoCsv);
	}

	public String salvePontuario() throws IOException {
		ProntuarioRepository repository = new ProntuarioRepository(this);
		return repository.salveProntuario();
	}

}
