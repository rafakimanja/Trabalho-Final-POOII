import DAO.VeiculoDAO;
import pacote_veiculo.Veiculo;

import java.util.ArrayList;
import java.util.List;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List veiculos = new ArrayList<>();

        Veiculo veiculo = new Veiculo("FGHI123", "Corolla 2.0 2018", "prata", "carro");

        VeiculoDAO coxVeiculo = new VeiculoDAO();

        coxVeiculo.insert(veiculo);
        veiculos = coxVeiculo.list(10, 0);

        for(Object v : veiculos){
            System.out.println(v);
        }
    }
}