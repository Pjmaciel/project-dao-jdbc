package model.impl;

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
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJdbc implements SellerDao {
    private Connection conn;

    public SellerDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveInsert(Seller seller) {

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "Values "
                    + "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1); // vai pegar o id gerado pelo getGeneratedKeys
                    seller.setId(id);// setar como Id do objeto seller criar
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected erro! no rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unimplemented method 'save'");
        } finally {
            DB.closeStatement(pst);
        }

    }

    @Override
    public void update(Seller seller) {

        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void deleteById(Integer id) {

        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement pst = null;
        ResultSet rs = null; // aponta para posiçao 0 que nao contem valor

        try {
            pst = conn.prepareStatement("Select seller.*, department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "Where seller.id = ?");
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller obj = instantiateSeller(rs, department);
                return obj;

            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unimplemented method 'findById'");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }

    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("Id"));
        obj.setName(rs.getString("Name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartment(department);
        return obj;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "ORDER BY Name");

            rs = pst.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>(); // lista de genericos para controlar a instancia do
                                                            // departament.

            while (rs.next()) {// como tem mais de um valor atrelado ao atributo deve fazer o while ao inves do
                               // for.
                Department dpt = map.get(rs.getInt("DepartmentId"));
                if (dpt == null) {
                    dpt = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dpt);

                }

                Seller obj = instantiateSeller(rs, dpt);
                list.add(obj);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (UnsupportedOperationException e) {

            throw new UnsupportedOperationException("Unimplemented method 'findAll'");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

    @Override
    public List<Seller> finByDepartment(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name");

            pst.setInt(1, department.getId());
            rs = pst.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();// lista de genericos para controlar a instancia do
                                                           // departament.

            while (rs.next()) { // como tem mais de um valor atrelado ao atributo deve fazer o while ao inves d
                                // for
                Department dpt = map.get(rs.getInt("DepartmentId"));// vai reaproveitar o department se existir um igual

                if (dpt == null) {
                    dpt = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dpt);

                }
                Seller obj = instantiateSeller(rs, dpt);
                list.add(obj);

            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unimplemented method 'finByDepartment'");
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(pst);
        }
    }

}
