package DAO;

import Conexao.ConnectionFactory;
import pacote_vaga.Conversor;
import pacote_vaga.Vaga;
import pacote_veiculo.Veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VagaDAO implements InterfaceDAO{

    ConnectionFactory conexaoBD = new ConnectionFactory();
    Conversor conversor = new Conversor();

    @Override
    public boolean insert(Object objetoModelo) {

        Connection conector = conexaoBD.getConnection();

        try {
            String sql = "INSERT INTO vagas (status, entrada, saida, descricao) VALUES (?, ?, ?, ?)";

            Vaga vaga = (Vaga)objetoModelo;

            PreparedStatement stmt = conector.prepareStatement(sql);

            stmt.setBoolean(1, vaga.getStatus());
            stmt.setTimestamp(2, conversor.toTimestamp(vaga.getEntrada_veiculo()));
            stmt.setTimestamp(3, conversor.toTimestamp(vaga.getSaida_veiculo()));
            stmt.setString(4, vaga.getDescricao());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {

        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "DELETE FROM vagas WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                conector.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean update(Object objetoModelo) {

        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "UPDATE vagas SET status = ?, entrada = ?, saida = ? WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);

            Vaga vaga = (Vaga) objetoModelo;
            stmt.setBoolean(1, vaga.getStatus());
            stmt.setTimestamp(2, conversor.toTimestamp(vaga.getEntrada_veiculo()));
            stmt.setTimestamp(3, conversor.toTimestamp(vaga.getSaida_veiculo()));
            stmt.setInt(4, vaga.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                conector.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public List list(int limit, int offset) {

        Connection conector = conexaoBD.getConnection();
        List<Vaga> vagas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM vagas LIMIT ? OFFSET ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vaga vaga = new Vaga(
                        rs.getInt("id"),
                        rs.getBoolean("status"),
                        conversor.toLocalDateTime(rs.getTimestamp("entrada")),
                        conversor.toLocalDateTime(rs.getTimestamp("saida")),
                        rs.getString("descricao")
                );
                vagas.add(vaga);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try {
                conector.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return vagas;
    }

    @Override
    public Object get(int id) {
        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "SELECT * FROM vagas WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Vaga vaga = new Vaga(
                        rs.getInt("id"),
                        rs.getBoolean("status"),
                        conversor.toLocalDateTime(rs.getTimestamp("entrada")),
                        conversor.toLocalDateTime(rs.getTimestamp("saida")),
                        rs.getString("descricao")
                );
                return vaga;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                conector.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
