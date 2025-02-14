import Procedimento.TipoProcedimento;

public class Procedimento {

	private TipoProcedimento tipoProcedimento;

	public Procedimento(TipoProcedimento tipoProcedimento) {
		this.tipoProcedimento = tipoProcedimento;
	}

	public TipoProcedimento getTipoProcedimento() {
		return this.tipoProcedimento;
	}

	public float calcularValorProcedimento() {
		return tipoProcedimento.calcularValorProcedimento();
	}

	public String getTipo(){
		return tipoProcedimento.getTipo();
	}

}
