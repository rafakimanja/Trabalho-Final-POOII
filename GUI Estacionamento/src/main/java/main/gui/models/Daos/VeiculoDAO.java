package main.gui.models.Daos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import main.gui.models.Pessoa.Pessoa;
import main.gui.models.Veiculo.Veiculo;
import main.gui.services.ConnectionFactory;
import main.gui.services.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements InterfaceDAO<Veiculo>{

    ConnectionFactory conexaoBD = new ConnectionFactory();
    Gson gson = new Gson();

    @Override
    public boolean insert(Veiculo veiculo) {

        String sql = "INSERT INTO veiculos (placa, modelo, cor, proprietario, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);) {

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());

            Pessoa p  = veiculo.getProprietario();
            String proprietarioJson = gson.toJson(p);

            stmt.setString(4, proprietarioJson);
            stmt.setString(5, veiculo.getTipo().toString());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir Veiuclo!", e);
        }
    }

    @Override
    public boolean delete(int id) {
    
        String sql = "DELETE FROM veiculos WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar Veiculo!", e);
        }
    }

    @Override
    public boolean update(Veiculo veiculo) {
        
        String sql = "UPDATE veiculos SET modelo = ?, proprietario = ?, tipo = ?, cor = ? WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){


            stmt.setString(1, veiculo.getModelo());

            Pessoa p  = veiculo.getProprietario();
            String proprietarioJson = gson.toJson(p);

            stmt.setString(2, proprietarioJson);
            stmt.setString(3, veiculo.getTipo().toString());
            stmt.setString(4, veiculo.getCor());
            stmt.setInt(5, veiculo.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar Veiculo!", e);
        }
    }

    @Override
    public List<Veiculo> list(int limit, int offset) {
        List<Veiculo> veiculos = new ArrayList<>();
        
        String sql = "SELECT * FROM veiculos LIMIT ? OFFSET ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rs.getInt("id"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setCor(rs.getString("cor"));

                String proprietario = rs.getString("proprietario");

                veiculo.setProprietario(gson.fromJson(proprietario, Pessoa.class));

                veiculo.setTipo(rs.getString("tipo"));
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retonar lista de veiculos!", e);
        }
        return veiculos;
    }

    @Override
    public Veiculo get(int id) {
        String sql = "SELECT * FROM veiculos WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Veiculo veiculo = new Veiculo();
                veiculo.setId(rs.getInt("id"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setCor(rs.getString("cor"));

                String proprietario = rs.getString("proprietario");

                veiculo.setProprietario(gson.fromJson(proprietario, Pessoa.class));

                veiculo.setTipo(rs.getString("tipo"));

                return veiculo;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao pesquisar veiculo!", e);
        }
        return null;
    }
}
