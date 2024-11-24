package pacote_veiculo;

public class Veiculo {

    private int id;
    private String placa;
    private String modelo;
    private String cor;
    private Tipo tipo;

    public Veiculo(int id, String placa, String modelo, String cor, String tipo) {
        setId(id);
        setPlaca(placa);
        setModelo(modelo);
        setCor(cor);
        setTipo(tipo);
    }

    // Construtor sem ID para inserção de novos veículos
    public Veiculo(String placa, String modelo, String cor, String tipo) {
        setPlaca(placa);
        setModelo(modelo);
        setCor(cor);
        setTipo(tipo);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
                "id='" + id + '\'' +
                "placa='" + placa + '\'' +
                ", modelo='" + modelo + '\'' +
                ", cor='" + cor + '\'' +
                ", tipo=" + tipo +
                '}';
    }
}
