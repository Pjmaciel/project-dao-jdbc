package app;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Application {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 1: Seller findBy Id ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== Test 2: Seller findBy DepartmentId ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.finByDepartment(department);
        for (Seller s : list) {
            System.out.println(s);
        }

        System.out.println("\n=== Test 3: Seller findAll ===");

        list = sellerDao.findAll();
        for (Seller seller2 : list) {
            System.out.println(seller2);

        }

    }

}
