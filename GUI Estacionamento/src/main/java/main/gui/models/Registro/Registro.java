package main.gui.models.Registro;

import main.gui.models.Vaga.Vaga;
import main.gui.models.Veiculo.Veiculo;

import java.time.LocalDateTime;

public class Registro {

    private int id;
    private LocalDateTime entrada;
    private LocalDateTime saida;
    private Vaga vaga;
    private Veiculo veiculo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getEntrada() {
        return entrada;
    }

    public void setEntrada(LocalDateTime entrada) {
        this.entrada = entrada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    @Override
    public String toString() {
        return "Registro{" +
                "id=" + id +
                ", entrada=" + entrada +
                ", saida=" + saida +
                ", vaga=" + vaga +
                ", veiculo=" + veiculo +
                '}';
    }
}
