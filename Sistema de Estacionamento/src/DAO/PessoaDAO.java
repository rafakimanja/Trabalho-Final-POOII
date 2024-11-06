package DAO;

import Conexao.ConnectionFactory;
import pacote_pessoa.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO implements InterfaceDAO{

    ConnectionFactory conexaoBD = new ConnectionFactory();

    @Override
    public boolean insert(Object objetoModelo) {

        Connection conector = conexaoBD.getConnection();

        try {
            String sql = "INSERT INTO pessoas (nome, cpf, telefone) VALUES (?, ?, ?)";

            Pessoa pessoa = (Pessoa) objetoModelo;

            PreparedStatement stmt = conector.prepareStatement(sql);

            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
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
            String sql = "DELETE FROM pessoas WHERE id = ?";

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
            String sql = "UPDATE pessoas SET nome = ?, cpf = ?, telefone = ? WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);

            Pessoa pessoa = (Pessoa) objetoModelo;
            stmt.setString(1, pessoa.getNome());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setInt(4, pessoa.getId());

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
        List<Pessoa> pessoas = new ArrayList<>();

        try {
            String sql = "SELECT * FROM pessoas LIMIT ? OFFSET ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pessoa pessoa = new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone")
                );
                pessoas.add(pessoa);
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
        return pessoas;
    }


    @Override
    public Object get(int id) {

        Connection conector = conexaoBD.getConnection();

        try{
            String sql = "SELECT * FROM pessoas WHERE id = ?";

            PreparedStatement stmt = conector.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Pessoa pessoa = new Pessoa(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("telefone")
                );
                return pessoa;
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
a
