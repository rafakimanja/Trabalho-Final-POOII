package main.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import main.gui.MainApplication;
import main.gui.models.Daos.RegistroDAO;
import main.gui.models.Daos.VagaDAO;
import main.gui.models.Daos.VeiculoDAO;
import main.gui.models.Registro.Registro;
import main.gui.models.Vaga.Vaga;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.DatabaseException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class CadastroRegistroController implements Initializable {

    @FXML
    private DatePicker dateEntrada;

    @FXML
    private ChoiceBox<Vaga> escVaga;

    private Vaga[] vagasDisponiveis = vagasDisponiveis();

    @FXML
    private ChoiceBox<Veiculo> escVeiculo;

    private Veiculo[] veiculosDisponiveis = veiculosDisponiveis();

    @FXML
    private BorderPane rootPane;

    @FXML
    private Spinner<Integer> spinHoras;

    private boolean isEditar;

    SpinnerValueFactory<Integer> horas = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);

    @FXML
    void handleSalvar() {

        Registro registro;

        RegistroDAO rDao = new RegistroDAO();
        VagaDAO vDao = new VagaDAO();

        LocalDateTime dt_entrada = dateEntrada.getValue().atTime(LocalTime.now());
        int horas = spinHoras.getValue();
        LocalDateTime dt_saida = dt_entrada.plusHours(horas);
        Veiculo veiculo = escVeiculo.getValue();
        Vaga vagaForm = escVaga.getValue();

        if(this.isEditar) {
            registro = (Registro)rootPane.getUserData();

            registro.setVeiculo(veiculo);
            registro.setVaga(vagaForm);
            registro.setEntrada(dt_entrada);
            registro.setSaida(dt_saida);

            try{
                rDao.update(registro);
            }catch (DatabaseException d){
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao salvar!");
                alerta.setContentText(d.toString());
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }

        } else {
            registro = new Registro();
            vagaForm.setStatus(true);

            registro.setVeiculo(veiculo);
            registro.setVaga(vagaForm);
            registro.setEntrada(dt_entrada);
            registro.setSaida(dt_saida);

            try{
                rDao.insert(registro);
                vDao.update(vagaForm);
            }catch (DatabaseException d){
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao salvar!");
                alerta.setContentText(d.toString());
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }
        }

        handleVoltar();
    }

    @FXML
    void handleVoltar() {
        MainApplication.loadScene("main-view");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> verificaTelaEdicao());
        spinHoras.setValueFactory(horas);
        escVaga.getItems().addAll(vagasDisponiveis);
        escVeiculo.getItems().addAll(veiculosDisponiveis);
    }

    private void verificaTelaEdicao(){
        var registro = (Registro)rootPane.getUserData();
        if(registro == null) this.isEditar=false;
        else{
            this.isEditar = true;
            Veiculo vei = registro.getVeiculo();
            Vaga vag = registro.getVaga();
            escVeiculo.setValue(vei);
            escVaga.setValue(vag);
            dateEntrada.setValue(registro.getEntrada().toLocalDate());
        }
    }

    private Vaga[] vagasDisponiveis(){

        VagaDAO vagaDAO = new VagaDAO();

        List<Vaga> vagasDisp = new ArrayList<>();

        List<Vaga> vagasBD = vagaDAO.list(10, 0);

        for (Vaga vaga : vagasBD){
            if (!vaga.getStatus()){
                vagasDisp.add(vaga);
            }
        }

        return vagasDisp.toArray(new Vaga[0]);
    }

    private Veiculo[] veiculosDisponiveis(){
        RegistroDAO registroDAO = new RegistroDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();

        List<Veiculo> veiculosDisp = new ArrayList<>();
        List<Veiculo> veiculosReg = new ArrayList<>();

         List<Veiculo> veiculosBD = veiculoDAO.list(10, 0);
         List<Registro> registrosBD = registroDAO.list(10, 0);

         for(Registro reg : registrosBD){
             veiculosReg.add(reg.getVeiculo());
         }

        for(Veiculo veiculo : veiculosBD){

            boolean isDisponivel = true;


            for(Veiculo veiculoReg : veiculosReg){
                if (veiculo.getId() == veiculoReg.getId()) {
                    isDisponivel = false;
                    break;
                }
            }

            if (isDisponivel) {
                veiculosDisp.add(veiculo);
            }

        }

        return veiculosDisp.toArray(new Veiculo[0]);
    }
}
