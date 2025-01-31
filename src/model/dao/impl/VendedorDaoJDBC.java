package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDAO;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VendedorDaoJDBC implements VendedorDAO {

    private Connection conn = null;

    public VendedorDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vendedor vendedor) {

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "INSERT INTO vendedor " +
                "(nome,email,data_aniversario,salario_base,id_departamento) " +
                "VALUES (?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, vendedor.getNome());
            statement.setString(2, vendedor.getEmail());
            statement.setDate(3, new java.sql.Date(vendedor.getDataAniversario().getTime()));
            statement.setDouble(4, vendedor.getSalarioBase());
            statement.setInt(5, vendedor.getDepartamento().getId());
            int salvou = statement.executeUpdate();
            if (salvou > 0) {
                result = statement.getGeneratedKeys();
                while (result.next()) {
                    int id = result.getInt(1);
                    vendedor.setId(id);                    
                }
                System.err.println("Objeto Salvo!");
                System.err.println(vendedor);
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
    public void update(Vendedor vendedor) {
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "UPDATE vendedor " +
                "SET nome = ?, email = ?, data_aniversario = ?, salario_base = ?, id_departamento = ? " +
                "WHERE id = ?"
            );
            statement.setString(1, vendedor.getNome());
            statement.setString(2, vendedor.getEmail());
            statement.setDate(3, new java.sql.Date(vendedor.getDataAniversario().getTime()));
            statement.setDouble(4, vendedor.getSalarioBase());
            statement.setInt(5, vendedor.getDepartamento().getId());
            statement.setInt(6, vendedor.getId());
            int salvou = statement.executeUpdate();
            if (salvou > 0) {
                System.err.println("Objeto Atualizado!");
                System.err.println(vendedor);
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
    public void deleteById(Integer id) {

        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(
                "DELETE FROM vendedor " +
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
    public Vendedor findById(Integer id) {

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT vendedor.*, departamento.nome as depNome " +
                "FROM vendedor INNER JOIN departamento " +
                "ON vendedor.id_departamento = departamento.id " +
                "WHERE vendedor.id = ?"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();
            if (result.next()) {

                Departamento departamento = instanciaDepartamento(result);
                Vendedor vendedor = instanciaVendedor(result, departamento);

                return vendedor;
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
    public List<Vendedor> findAll() {

        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT vendedor.*, departamento.nome as depNome " +
                "FROM vendedor INNER JOIN departamento " +
                "ON vendedor.id_departamento = departamento.id " +
                "ORDER BY nome"
            );
            result = statement.executeQuery();
            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> mapDep = new HashMap<>();
            while (result.next()) {

                Departamento dep = mapDep.get(result.getInt("id_departamento"));

                if (dep == null) {
                    dep = instanciaDepartamento(result);
                    mapDep.put(dep.getId(), dep);
                }
                
                Vendedor vendedor = instanciaVendedor(result, dep);
                list.add(vendedor);

            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Error: " + e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(result);
        }

    }

    public List<Vendedor> findByDepartamento(Integer id) {
        
        PreparedStatement statement = null;
        ResultSet result = null;

        try {
            statement = conn.prepareStatement(
                "SELECT vendedor.*, departamento.nome as depNome " +
                "FROM vendedor INNER JOIN departamento " +
                "ON vendedor.id_departamento = departamento.id " +
                "WHERE id_departamento = ? " +
                "ORDER BY nome"
            );
            statement.setInt(1, id);
            result = statement.executeQuery();
            List<Vendedor> list = new ArrayList<>();
            Map<Integer, Departamento> mapDep = new HashMap<>();
            while (result.next()) {

                Departamento dep = mapDep.get(result.getInt("id_departamento"));

                if (dep == null) {
                    dep = instanciaDepartamento(result);
                    mapDep.put(dep.getId(), dep);
                }
                
                Vendedor vendedor = instanciaVendedor(result, dep);
                list.add(vendedor);

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
        departamento.setId(result.getInt("id_departamento"));
        departamento.setNome(result.getString("depNome"));
        return departamento;
    }

    private Vendedor instanciaVendedor(ResultSet result, Departamento departamento) throws SQLException {
        Vendedor vendedor = new Vendedor();
        vendedor.setId(result.getInt("id"));
        vendedor.setNome(result.getString("nome"));
        vendedor.setEmail(result.getString("email"));
        vendedor.setDataAniversario(result.getDate("data_aniversario"));
        vendedor.setSalarioBase(result.getDouble("salario_base"));
        vendedor.setDepartamento(departamento);
        return vendedor;
    }
}
