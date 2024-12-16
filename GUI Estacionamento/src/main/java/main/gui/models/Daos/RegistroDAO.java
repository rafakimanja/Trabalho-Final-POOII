package main.gui.models.Daos;

import main.gui.models.Registro.Conversor;
import main.gui.models.Registro.Registro;
import main.gui.models.Vaga.Vaga;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.ConnectionFactory;
import main.gui.services.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistroDAO implements InterfaceDAO<Registro>{

    ConnectionFactory conexaoBD = new ConnectionFactory();
    Conversor conversor = new Conversor();

    @Override
    public boolean insert(Registro registro) {

        String sql = "INSERT INTO registro (id_vaga, id_veiculo, data_entrada, data_saida) VALUES (?, ?, ?, ?)";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            Vaga v = registro.getVaga();
            Veiculo vei = registro.getVeiculo();

            stmt.setInt(1, v.getId());
            stmt.setInt(2, vei.getId());
            stmt.setTimestamp(3, conversor.toTimestamp(registro.getEntrada()));
            stmt.setTimestamp(4, conversor.toTimestamp(registro.getSaida()));

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir Registro!", e);
        }
    }

    @Override
    public boolean delete(int id) {

        String sql = "DELETE FROM registro WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar Registro!", e);
        }
    }

    @Override
    public boolean update(Registro registro) {

        String sql = "UPDATE registro SET id_vaga = ?, id_veiculo = ?, data_entrada = ?, data_saida = ? WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            Vaga v = registro.getVaga();
            Veiculo vei = registro.getVeiculo();

            stmt.setInt(1, v.getId());
            stmt.setInt(2, vei.getId());
            stmt.setTimestamp(3, conversor.toTimestamp(registro.getEntrada()));
            stmt.setTimestamp(4, conversor.toTimestamp(registro.getSaida()));
            stmt.setInt(5, registro.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar Registro!", e);
        }
    }

    @Override
    public List<Registro> list(int limit, int offset) {

        List<Registro> registros = new ArrayList<>();
        String sql = "SELECT * FROM registro LIMIT ? OFFSET ?";

        VagaDAO vDao = new VagaDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Registro registro = new Registro();
                registro.setId(rs.getInt("id"));
                registro.setEntrada(conversor.toLocalDateTime(rs.getTimestamp("data_entrada")));
                registro.setSaida(conversor.toLocalDateTime(rs.getTimestamp("data_saida")));
                Vaga vaga = vDao.get(rs.getInt("id_vaga"));
                Veiculo veiculo = veiculoDAO.get(rs.getInt("id_veiculo"));
                registro.setVaga(vaga);
                registro.setVeiculo(veiculo);

                registros.add(registro);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retonar lista de Vagas!", e);
        }
        return registros;
    }

    @Override
    public Registro get(int id) {

        String sql = "SELECT * FROM registro WHERE id = ?";

        VagaDAO vDao = new VagaDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Registro registro = new Registro();
                registro.setId(rs.getInt("id"));
                registro.setEntrada(conversor.toLocalDateTime(rs.getTimestamp("data_entrada")));
                registro.setSaida(conversor.toLocalDateTime(rs.getTimestamp("data_saida")));
                Vaga vaga = vDao.get(rs.getInt("id_vaga"));
                Veiculo veiculo = veiculoDAO.get(rs.getInt("id_veiculo"));
                registro.setVaga(vaga);
                registro.setVeiculo(veiculo);

                return registro;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao pesquisar Vaga!", e);
        }
        return null;
    }
}
