package Procedimento;
public class Basico extends TipoProcedimento{

    @Override
    public float calcularValorProcedimento(){
        return 50.00f;
    }

    @Override
    public String getTipo(){
        return "BASICO";
    }
    
}
