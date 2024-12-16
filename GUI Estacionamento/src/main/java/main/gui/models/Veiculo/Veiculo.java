package main.gui.models.Veiculo;

import main.gui.models.Pessoa.Pessoa;

import java.util.Objects;

public class Veiculo {

    private int id;
    private String placa;
    private String modelo;
    private String cor;
    private Tipo tipo;
    private Pessoa proprietario;

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

    public Pessoa getProprietario() {
        return proprietario;
    }

    public void setProprietario(Pessoa proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return id == veiculo.id && Objects.equals(placa, veiculo.placa) && Objects.equals(modelo, veiculo.modelo) && Objects.equals(cor, veiculo.cor) && tipo == veiculo.tipo && Objects.equals(proprietario, veiculo.proprietario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, placa, modelo, cor, tipo, proprietario);
    }

    @Override
    public String toString() {
        return "placa='" + placa + ", tipo=" + tipo;
    }
}
