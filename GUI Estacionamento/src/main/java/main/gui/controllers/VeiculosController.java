package main.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.gui.MainApplication;
import main.gui.models.Daos.VeiculoDAO;
import main.gui.models.Pessoa.Pessoa;
import main.gui.models.Veiculo.Tipo;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.DatabaseException;

import java.net.URL;
import java.util.ResourceBundle;

public class VeiculosController implements Initializable {

    private ObservableList<Veiculo> veiculos;

    @FXML
    private TableColumn<Veiculo, String> clmCor;

    @FXML
    private TableColumn<Veiculo, Integer> clmId;

    @FXML
    private TableColumn<Veiculo, String> clmModelo;

    @FXML
    private TableColumn<Veiculo, String> clmPlaca;

    @FXML
    private TableColumn<Veiculo, Pessoa> clmProprietario;

    @FXML
    private TableColumn<Veiculo, Tipo> clmTipo;

    @FXML
    private TableView<Veiculo> tabVeiculos;

    @FXML
    void handleMudaCarros() {
        MainApplication.loadScene("TelaVeiculos");
    }

    @FXML
    void handleMudaHome() {
        MainApplication.loadScene("main-view");
    }

    @FXML
    void handleMudaVagas() {
        MainApplication.loadScene("TelaVagas");
    }

    @FXML
    void handleAdicionaVeiculo() {
        MainApplication.loadScene("TelaForm-Veiculo");
    }

    @FXML
    void handleExcluirVeiculo(){
        Veiculo alvo = this.tabVeiculos.getSelectionModel().getSelectedItem();
        if(alvo != null){
            VeiculoDAO vDao = new VeiculoDAO();

            try{
                vDao.delete(alvo.getId());
            }catch (DatabaseException e) {
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao excluir!");
                alerta.setContentText("Erro ao excluir vaga no banco de dados!");
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }

            MainApplication.loadScene("TelaVeiculos");
        }else{
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Veiculo nao selecionado!");
            alerta.setContentText("Voce deve escolher uma veiculo para poder excluir...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    @FXML
    void handleEditarVeiculo(){
        Veiculo alvo = this.tabVeiculos.getSelectionModel().getSelectedItem();
        if(alvo != null) MainApplication.loadScene("TelaForm-Veiculo", alvo);
        else{
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Veiculo nao selecionado!");
            alerta.setContentText("Voce deve escolher uma veiculo para poder excluir...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    private void geraLista(){
        try{
            VeiculoDAO vDao = new VeiculoDAO();
            veiculos = FXCollections.observableArrayList(vDao.list(10, 0));
            tabVeiculos.setItems(veiculos);
        } catch (DatabaseException e){
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Dados nao obtidos!");
            alerta.setContentText("Falha ao puxar os dados no banco de dados");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        geraLista();
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        clmModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        clmCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        clmTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        clmProprietario.setCellValueFactory(new PropertyValueFactory<>("proprietario"));
    }
}
