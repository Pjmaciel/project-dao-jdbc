package app;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Application {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();
        System.out.println("");
        System.out.println("");
        System.out.println("=== Test 1: Seller findBy Id ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);
    }

}
