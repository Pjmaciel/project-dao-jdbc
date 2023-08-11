package app;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class TestJdbcDepartment {

    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 1: Department SaveInsert ===");

        Department dpt = new Department(null, "Test");
        departmentDao.saveInsert(dpt);
        System.out.println("Saved! new Id = " + dpt.getId());

        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 2: Department findById  ===");
        System.out.println(departmentDao.findById(4));

        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 3: Department Update ===");
        dpt = departmentDao.findById(8);
        dpt.setName("Office Tools");
        departmentDao.update(dpt);
        System.out.println("Update done successfully");

        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 4: Department findAll ===");
        System.out.println(departmentDao.findAll());

        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 5: Department deleteById ===");
        departmentDao.deleteById(9);
        System.out.println("Delete done successfully");

    }

}
