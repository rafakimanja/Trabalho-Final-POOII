package DAO;

import Conexao.ConnectionFactory;
import pacote_veiculo.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO implements InterfaceDAO{

    ConnectionFactory conexaoBD = new ConnectionFactory();

    @Override
    public boolean insert(Object objetoModelo) {

        Connection conector = conexaoBD.getConnection();

        try {
            String sql = "INSERT INTO veiculos (placa, modelo, cor, tipo) VALUES (?, ?, ?, ?)";

            Veiculo veiculo = (Veiculo) objetoModelo;

            PreparedStatement stmt = conector.prepareStatement(sql);

            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setString(4, veiculo.getTipo().name());
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
    public boolean delete(int id) {

        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "DELETE FROM veiculos WHERE id = ?";

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
            String sql = "UPDATE veiculos SET placa = ?, modelo = ?, cor = ?, tipo = ? WHERE placa = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);

            Veiculo veiculo = (Veiculo) objetoModelo;
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getCor());
            stmt.setString(4, veiculo.getTipo().name());
            stmt.setString(5, veiculo.getPlaca());

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
        List<Veiculo> veiculos = new ArrayList<>();

        try {
            String sql = "SELECT * FROM veiculos LIMIT ? OFFSET ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Veiculo veiculo = new Veiculo(
                        rs.getString("placa"),
                        rs.getString("modelo"),
                        rs.getString("cor"),
                        rs.getString("tipo")
                );
                veiculos.add(veiculo);
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
        return veiculos;
    }


    @Override
    public Object get(int id) {

        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "SELECT * FROM veiculos WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Veiculo veiculo = new Veiculo(
                            rs.getString("placa"),
                            rs.getString("modelo"),
                            rs.getString("cor"),
                            rs.getString("tipo")
                    );
                    return veiculo;
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
