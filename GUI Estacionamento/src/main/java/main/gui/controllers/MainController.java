package main.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.gui.MainApplication;
import main.gui.models.Daos.RegistroDAO;
import main.gui.models.Daos.VagaDAO;
import main.gui.models.Registro.Registro;
import main.gui.models.Vaga.Vaga;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.DatabaseException;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ObservableList<Registro> registros;

    @FXML
    private TableColumn<Registro, LocalDateTime> clmEntrada;

    @FXML
    private TableColumn<Registro, Integer> clmId;

    @FXML
    private TableColumn<Registro, LocalDateTime> clmSaida;

    @FXML
    private TableColumn<Registro, Vaga> clmVaga;

    @FXML
    private TableColumn<Registro, Veiculo> clmVeiculo;

    @FXML
    private TableView<Registro> tabRegistros;

    @FXML
    void handleAdicionaRegistro() {
        MainApplication.loadScene("TelaForm-Registro");
    }

    @FXML
    void handleEditarRegistro() {
        Registro alvo = this.tabRegistros.getSelectionModel().getSelectedItem();
        if(alvo != null) MainApplication.loadScene("TelaForm-Registro", alvo);
        else{
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Veiculo nao selecionado!");
            alerta.setContentText("Voce deve escolher uma veiculo para poder excluir...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    @FXML
    void handleExcluiRegistro() {
        Registro alvo = this.tabRegistros.getSelectionModel().getSelectedItem(); //Objeto selecionado na tabela
        if(alvo != null){
            RegistroDAO rDao = new RegistroDAO();
            VagaDAO vDao = new VagaDAO();

            Vaga vagaAlvo = vDao.get(alvo.getVaga().getId());
            vagaAlvo.setStatus(false);

            try {
                rDao.delete(alvo.getId());
                vDao.update(vagaAlvo);
            } catch (DatabaseException e) {
                var alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Erro ao excluir!");
                alerta.setContentText("Erro ao excluir vaga no banco de dados!");
                alerta.setTitle("Erro!");
                alerta.showAndWait();
            }

            MainApplication.loadScene("main-view");
        }else{
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Vaga nao selecionada!");
            alerta.setContentText("Voce deve escolher uma vaga para poder excluir...");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

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

    public void geraLista(){
        try{
            RegistroDAO rDao = new RegistroDAO();
            registros = FXCollections.observableArrayList(rDao.list(10, 0));
            tabRegistros.setItems(registros);
        }catch (DatabaseException e){
            var alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Dados nao obtidos!");
            alerta.setContentText("Falha ao puxar os dados no banco de dados");
            alerta.setTitle("Atencao");
            alerta.showAndWait();
        }
    }

    public String formataData(LocalDateTime data){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = data.format(formatter);
        return dataFormatada;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        geraLista();
        clmId.setCellValueFactory(new PropertyValueFactory<>("id"));
        clmEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
        clmSaida.setCellValueFactory(new PropertyValueFactory<>("saida"));
        clmVaga.setCellValueFactory(new PropertyValueFactory<>("vaga"));
        clmVeiculo.setCellValueFactory(new PropertyValueFactory<>("veiculo"));

        clmEntrada.setCellFactory(column -> new TableCell<Registro, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formataData(item));  // Formatar a data
                }
            }
        });

        clmSaida.setCellFactory(column -> new TableCell<Registro, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(formataData(item));  // Formatar a data
                }
            }
        });

        clmVeiculo.setCellFactory(column -> new TableCell<Registro, Veiculo>() {
            @Override
            protected void updateItem(Veiculo veiculo, boolean empty) {
                super.updateItem(veiculo, empty);
                if (empty || veiculo == null) {
                    setText(null);
                } else {
                    setText(veiculo.getPlaca()+" | "+veiculo.getTipo().toString());
                }
            }
        });

        clmVaga.setCellFactory(column -> new TableCell<Registro, Vaga>() {
            @Override
            protected void updateItem(Vaga vaga, boolean empty) {
                super.updateItem(vaga, empty);
                if (empty || vaga == null) {
                    setText(null);
                } else {
                    setText(vaga.getDescricao());
                }
            }
        });

    }
}
