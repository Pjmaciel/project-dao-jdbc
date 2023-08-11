package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJdbc implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJdbc(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void saveInsert(Department department) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("INSERT INTO department (Name)"
                    + "Values (?)", Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, department.getName());

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);// vai pegar o id gerado pelo getGeneratedKeys
                    department.setId(id);// setar como Id do objeto seller criar
                }
                DB.closeResultSet(rs);
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
    public void update(Department department) {
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(" UPDATE department SET Name = ? Where Id = ?");
            pst.setString(1, department.getName());
            pst.setInt(2, department.getId());

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (UnsupportedOperationException e) {

            throw new UnsupportedOperationException("Unimplemented method 'update'");
        }
    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement("DELETE FROM department WHERE Id = ?");

            pst.setInt(1, id);

            pst.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
        } finally {

        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            pst = conn.prepareStatement("SELECT * FROM department Where Id = ?");

            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                return department;
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

    @Override
    public List<Department> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs = pst.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department department = instantiateDepartment(rs);
                list.add(department);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(null);
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException("Unimplemented method 'findAll'");

        }

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("Id"));
        department.setName(rs.getString("Name"));
        return department;
    }

}
