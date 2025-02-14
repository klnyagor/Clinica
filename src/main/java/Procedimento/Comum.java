package Procedimento;
public class Comum extends TipoProcedimento {
    
    @Override
    public float calcularValorProcedimento(){
        return 150.00f;
    }

    @Override
    public String getTipo(){
        return "COMUM";
    }

}
