package main.exemplo_javafx.models.Vaga;

import java.time.LocalDateTime;

public class Vaga {
    private int id;
    private boolean status;
    private LocalDateTime entrada_veiculo;
    private LocalDateTime saida_veiculo;
    private String descricao;

//    public Vaga(int id, boolean status, LocalDateTime entrada_veiculo, LocalDateTime saida_veiculo, String descricao) {
//        setId(id);
//        setStatus(status);
//        setEntrada_veiculo(entrada_veiculo);
//        setSaida_veiculo(saida_veiculo);
//        setDescricao(descricao);
//    }
//
//    public Vaga(boolean status, LocalDateTime entrada_veiculo, LocalDateTime saida_veiculo, String descricao) {
//        setStatus(status);
//        setEntrada_veiculo(entrada_veiculo);
//        setSaida_veiculo(saida_veiculo);
//        setDescricao(descricao);
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getEntrada_veiculo() {
        return entrada_veiculo;
    }

    public void setEntrada_veiculo(LocalDateTime entrada_veiculo) {
        this.entrada_veiculo = entrada_veiculo;
    }

    public LocalDateTime getSaida_veiculo() {
        return saida_veiculo;
    }

    public void setSaida_veiculo(LocalDateTime saida_veiculo) {
        this.saida_veiculo = saida_veiculo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "id=" + id +
                ", status=" + status +
                ", entrada_veiculo=" + entrada_veiculo +
                ", saida_veiculo=" + saida_veiculo +
                ", descricao=" + descricao +
                '}';
    }
}
