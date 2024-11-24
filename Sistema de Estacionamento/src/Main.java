import DAO.VagaDAO;
import DAO.VeiculoDAO;
import pacote_vaga.Vaga;
import pacote_veiculo.Veiculo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

        List vagas = new ArrayList<>();
        VagaDAO vagaDAO = new VagaDAO();

        vagas = vagaDAO.list(10, 0);

        Vaga novaVaga = (Vaga) vagas.get(1);

        System.out.println(novaVaga);

        novaVaga.setDescricao("vaga23");

        System.out.println(novaVaga);

        System.out.println(vagaDAO.update(novaVaga));

        vagas = vagaDAO.list(10, 0);

        for (Object v : vagas){
            System.out.println(v);
        }
    }
}