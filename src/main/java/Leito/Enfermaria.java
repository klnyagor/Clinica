package Leito;
public class Enfermaria extends TipoLeito {
    @Override
    public String getTipo(){
        return "ENFERMARIA";
    }

    @Override
    public float calcularValorDiarias(int qtdeDias){
        if (qtdeDias <= 3){
            return 40.00f * qtdeDias;
        } else if (qtdeDias <= 8) {
            return 35.00f * qtdeDias;
        }  
        return 30.00f * qtdeDias;
    }    
}
