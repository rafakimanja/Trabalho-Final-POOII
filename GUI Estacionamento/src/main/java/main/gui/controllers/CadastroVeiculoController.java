package main.gui.controllers;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import main.gui.MainApplication;

import main.gui.models.Daos.VeiculoDAO;
import main.gui.models.Pessoa.Pessoa;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.DatabaseException;

import java.net.URL;
import java.util.ResourceBundle;

public class CadastroVeiculoController implements Initializable {

    @FXML
    private ChoiceBox<String> tipoVeiculo;

    private String[] tipo = {"carro", "moto", "caminhao"};

    @FXML
    private BorderPane rootPane;

    @FXML
    private TextField txtCor;

    @FXML
    private TextField txtCpfProp;

    @FXML
    private TextArea txtModelo;

    @FXML
    private TextField txtNomeProp;

    @FXML
    private TextField txtPlaca;

    private boolean isEditar;

    @FXML
    void handleSalvar() {

        Veiculo v;
        Pessoa p;

        if(this.isEditar){
            v = (Veiculo)rootPane.getUserData();
            p = v.getProprietario();
        }
        else {
            v = new Veiculo();
            p = new Pessoa();
        }

        String cor = txtCor.getText();
        String modelo = txtModelo.getText();
        String placa = txtPlaca.getText();
        String tipo = tipoVeiculo.getValue();
        String nomeProp = txtNomeProp.getText();
        String cpfProp = txtCpfProp.getText();

        p.setNome(nomeProp);
        p.setCpf(cpfProp);

        v.setPlaca(placa);
        v.setModelo(modelo);
        v.setCor(cor);
        v.setTipo(tipo);
        v.setProprietario(p);

        VeiculoDAO vDao = new VeiculoDAO();

        if(this.isEditar){
            try{
                vDao.update(v);
            }catch (DatabaseException d){
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao salvar!");
                alerta.setContentText(d.toString());
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }
        }else {
            try {
                vDao.insert(v);
            } catch (DatabaseException d) {
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
        MainApplication.loadScene("TelaVeiculos");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> verificaTelaEdicao());
        tipoVeiculo.getItems().addAll(tipo);
    }

    private void verificaTelaEdicao(){
        var veiculo = (Veiculo)rootPane.getUserData();
        if (veiculo == null) this.isEditar=false;
        else{
            this.isEditar = true;
            Pessoa p = veiculo.getProprietario();
            txtPlaca.setText(veiculo.getPlaca());
            txtModelo.setText(veiculo.getModelo());
            txtCor.setText(veiculo.getCor());
            tipoVeiculo.setValue(veiculo.getTipo().toString());
            txtNomeProp.setText(p.getNome());
            txtCpfProp.setText(p.getCpf());
        }
    }
}
