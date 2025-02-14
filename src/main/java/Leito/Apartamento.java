package Leito;
public class Apartamento extends TipoLeito {
    @Override
    public String getTipo(){
        return "APARTAMENTO";
    }
    
    @Override
    public float calcularValorDiarias(int qtdeDias){
        if (qtdeDias <= 3){
            return 100.00f * qtdeDias;
        } else if (qtdeDias <= 8) {
            return 90.00f * qtdeDias;
        }
        return 80.00f * qtdeDias;
    }
}
