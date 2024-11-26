package main.exemplo_javafx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import main.exemplo_javafx.HelloApplication;
import main.exemplo_javafx.daos.DatabaseException;
import main.exemplo_javafx.daos.VagaDAO;
import main.exemplo_javafx.models.Vaga.Vaga;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class CadastroController implements Initializable {

    @FXML private BorderPane rootPane;
    @FXML private TextField txtDesc;
    @FXML private CheckBox isActive;
    @FXML private DatePicker dateEntrada;
    @FXML private Spinner<Integer> spinHoras;

    private boolean isEditar;

    SpinnerValueFactory<Integer> horas = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1);

    @FXML
    void handleSalvar() {
        boolean status = isActive.isSelected();
        String descricao = txtDesc.getText();

        Vaga v;
        VagaDAO vDao = new VagaDAO();

        if(this.isEditar) v = (Vaga)rootPane.getUserData();
        else v = new Vaga();

        if(status){

            LocalDateTime data_entrada = dateEntrada.getValue().atTime(LocalTime.now());
            int horas = spinHoras.getValue();
            LocalDateTime data_saida = data_entrada.plusHours(horas);


            v.setStatus(status);
            v.setEntrada_veiculo(data_entrada);
            v.setSaida_veiculo(data_saida);
            v.setDescricao(descricao);

        }else{
            v.setStatus(status);
            v.setDescricao(descricao);
        }

        if(this.isEditar){
            try {
                vDao.update(v);
            }catch (DatabaseException d){
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao atualizar!");
                alerta.setContentText("Erro ao atualizar vaga no banco de dados!");
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }
        }
        else {
            try{
                vDao.insert(v);
            }catch (DatabaseException d){
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao salvar!");
                alerta.setContentText("Erro ao salvar vaga no banco de dados!");
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }
        }
        handleVoltar();
    }

    @FXML
    void handleVoltar() {
        HelloApplication.loadScene("hello-view");

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        spinHoras.setValueFactory(horas);
        Platform.runLater(()->verificaTelaEdicao());
    }

    private void verificaTelaEdicao(){
        var vaga = (Vaga)rootPane.getUserData();
        if (vaga == null) this.isEditar=false;
        else{
            this.isEditar = true;
            txtDesc.setText(vaga.getDescricao());
            isActive.setSelected(vaga.getStatus());
            dateEntrada.setValue(vaga.getEntrada_veiculo().toLocalDate());
        }
    }
}
