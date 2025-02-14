package Procedimento;
public class Avancado extends TipoProcedimento {
    @Override
    public float calcularValorProcedimento(){
        return 500.00f;
    }

    @Override
    public String getTipo(){
        return "AVANCADO";
    }
    
}
