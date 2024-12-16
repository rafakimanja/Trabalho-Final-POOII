package main.gui.models.Daos;

import main.gui.models.Vaga.Vaga;
import main.gui.services.ConnectionFactory;
import main.gui.services.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO implements InterfaceDAO<Vaga>{

    ConnectionFactory conexaoBD = new ConnectionFactory();

    @Override
    public boolean insert(Vaga vaga) {

        String sql = "INSERT INTO vagas (status, descricao) VALUES (?, ?)";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setBoolean(1, vaga.getStatus());
            stmt.setString(2, vaga.getDescricao());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao inserir Vaga!", e);
        }
    }

    @Override
    public boolean delete(int id) {

        String sql = "DELETE FROM vagas WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            throw new DatabaseException("Erro ao deletar Vaga!", e);
        }
    }

    @Override
    public boolean update(Vaga vaga) {

        String sql = "UPDATE vagas SET status = ?, descricao = ? WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setBoolean(1, vaga.getStatus());
            stmt.setString(2, vaga.getDescricao());
            stmt.setInt(3, vaga.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao atualizar Vaga!", e);
        }
    }

    @Override
    public List<Vaga> list(int limit, int offset) {

        List<Vaga> vagas = new ArrayList<>();
        String sql = "SELECT * FROM vagas LIMIT ? OFFSET ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vaga vaga = new Vaga();
                vaga.setId(rs.getInt("id"));
                vaga.setStatus(rs.getBoolean("status"));
                vaga.setDescricao(rs.getString("descricao"));
                vagas.add(vaga);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao retonar lista de Vagas!", e);
        }
        return vagas;
    }

    @Override
    public Vaga get(int id) {

        String sql = "SELECT * FROM vagas WHERE id = ?";

        try (Connection conector = conexaoBD.getConnection(); PreparedStatement stmt = conector.prepareStatement(sql);){

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Vaga vaga = new Vaga();
                vaga.setId(rs.getInt("id"));
                vaga.setStatus(rs.getBoolean("status"));
                vaga.setDescricao(rs.getString("descricao"));
                return vaga;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Erro ao pesquisar Vaga!", e);
        }
        return null;
    }
}
