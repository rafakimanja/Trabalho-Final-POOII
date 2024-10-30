package pacote_veiculo;

public class Veiculo {

    private String placa;
    private String modelo;
    private String cor;
    private Tipo tipo;

    public Veiculo(String placa, String modelo, String cor, String tipo) {
        this.setPlaca(placa);
        this.setModelo(modelo);
        this.setCor(cor);
        this.setTipo(tipo);
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {

        if(placa.length() == 7)
        {
            this.placa = placa;
        }else{
            System.out.println("Erro! Digite uma placa valida!");
        }

    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if(modelo.length() > 0){
            this.modelo = modelo;
        }else{
            System.out.println("Digite um modelo valido");
        }
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        if(cor.length() > 0){
            this.cor = cor;
        }else{
            System.out.println("Digite uma cor valida");
        }

    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        try {
            this.tipo = Tipo.valueOf(tipo.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Tipo inválido!");
        }
    }

    @Override
    public String toString() {
        return "Veiculo{" +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}