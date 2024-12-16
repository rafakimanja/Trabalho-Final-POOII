package main.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import main.gui.MainApplication;
import main.gui.models.Daos.VagaDAO;
import main.gui.models.Vaga.Vaga;
import main.gui.services.DatabaseException;


import java.net.URL;

import java.util.ResourceBundle;

public class CadastroVagasController implements Initializable {

    @FXML private BorderPane rootPane;
    @FXML private TextField txtDesc;
    @FXML private CheckBox isActive;

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

        v.setStatus(status);
        v.setDescricao(descricao);

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
        MainApplication.loadScene("TelaVagas");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->verificaTelaEdicao());
    }

    private void verificaTelaEdicao(){
        var vaga = (Vaga)rootPane.getUserData();
        if (vaga == null) this.isEditar=false;
        else{
            this.isEditar = true;
            txtDesc.setText(vaga.getDescricao());
            isActive.setSelected(vaga.getStatus());
        }
    }
}
