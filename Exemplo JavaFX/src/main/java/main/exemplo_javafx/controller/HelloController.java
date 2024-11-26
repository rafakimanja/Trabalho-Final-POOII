package main.exemplo_javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.exemplo_javafx.HelloApplication;
import main.exemplo_javafx.daos.DatabaseException;
import main.exemplo_javafx.daos.VagaDAO;
import main.exemplo_javafx.models.Vaga.Vaga;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    private ObservableList<Vaga> vagas;

    @FXML
    private Button btnAdicionaVaga;

    @FXML
    private TableColumn<Vaga, String> clmDesc;

    @FXML
    private TableColumn<Vaga, LocalDateTime> clmEntrada;

    @FXML
    private TableColumn<Vaga, Integer> clmId;

    @FXML
    private TableColumn<Vaga, LocalDateTime> clmSaida;

    @FXML
    private TableColumn<Vaga, Boolean> clmStatus;

    @FXML
    private TableView<Vaga> tabVagas;

    @FXML
    void handleAdicionaVaga(){
        HelloApplication.loadScene("TelaForm");
    }

    @FXML
    void handleExcluiVaga() {
        Vaga alvo = this.tabVagas.getSelectionModel().getSelectedItem(); //Objeto selecionado na tabela
        if(alvo != null){
            VagaDAO vDao = new VagaDAO();

            try {
                vDao.delete(alvo.getId());
            } catch (DatabaseException e) {
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao excluir!");
                alerta.setContentText("Erro ao excluir vaga no banco de dados!");
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }

            HelloApplication.loadScene("hello-view");
        }else{
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Vaga nao selecionada!");
            alerta.setContentText("Voce deve escolher uma vaga para poder excluir...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    @FXML
    void handleEditarVaga() {
        Vaga alvo = this.tabVagas.getSelectionModel().getSelectedItem(); //Objeto selecionado na tabela
        if(alvo != null){
            HelloApplication.loadScene("TelaForm", alvo);
        }else{
            var alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setHeaderText("Vaga nao selecionada!");
            alerta.setContentText("Voce deve escolher uma vaga para alterar os dados...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    private void geraLista() {
        try{
            VagaDAO vDao = new VagaDAO();
            vagas = FXCollections.observableArrayList(vDao.list(10, 0));
            tabVagas.setItems(vagas);
        }catch (DatabaseException e){
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
        clmDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        clmEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada_veiculo"));
        clmSaida.setCellValueFactory(new PropertyValueFactory<>("saida_veiculo"));
        clmStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
}
