package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartamentoDAO;
import model.entities.Departamento;

public class DepartamentoDaoJDBC implements DepartamentoDAO {

    private Connection conn;

    public DepartamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departamento departamento) {

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "INSERT INTO departamento " +
                "(nome) " +
                "VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, departamento.getNome());
            int executou = statement.executeUpdate();
            if (executou > 0) {
                result = statement.getGeneratedKeys();
                while (result.next()) {
                    int id = result.getInt(1);
                    departamento.setId(id);                    
                }
                System.err.println("Objeto Salvo: " + departamento);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
            }
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }

    @Override
    public void update(Departamento departamento) {
        
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                "UPDATE departamento " +
                "SET nome = ? " +
                "WHERE id = ?"
            );
            statement.setString(1, departamento.getNome());
            statement.setInt(2, departamento.getId());
            int executou = statement.executeUpdate();
            if (executou > 0) {
                System.err.println("Objeto Atualizado: " + departamento);
            } else {
                throw new DbException("Erro inesperado! Nenhuma linha foi afetada");
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                "DELETE FROM departamento " +
                "WHERE id = ?"
            );
            statement.setInt(1, id);
            int executou = statement.executeUpdate();
            if (executou > 0) {
                System.err.println("Vendedor com id = " + id + " excluido.");
            }
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }

    }

    @Override
    public Departamento findById(Integer id) {
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT departamento.* " +
                "FROM departamento " +
                "WHERE id = ?"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (result.next()) {
                return instanciaDepartamento(result);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }

    @Override
    public List<Departamento> findAll() {
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT departamento.* " +
                "FROM departamento " +
                "ORDER BY nome"
            );
            result = statement.executeQuery();
            List<Departamento> list = new ArrayList<>();
            while (result.next()) {
                list.add(instanciaDepartamento(result));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }

    private Departamento instanciaDepartamento(ResultSet result) throws SQLException {
        Departamento departamento = new Departamento();
        departamento.setId(result.getInt("id"));
        departamento.setNome(result.getString("nome"));
        return departamento;
    }

}
