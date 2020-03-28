/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insert;
import entity.Buyer;
import entity.BuyerBidProduct;
import entity.BuyerBidProductId;
import entity.BuyerBuyProduct;
import entity.BuyerBuyProductId;
import entity.Category;
import entity.Product;
import entity.Seller;
import entity.User;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author DELL
 */
public class Insert {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration() .configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sessionFactory.openSession();
        User user=createUser(); 
        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
        System.out.println("user Done");
         Buyer buyer=createBuyer(user);
        session.beginTransaction();
        session.persist(buyer);
        session.getTransaction().commit();
        System.out.println("buyer Done");             
         Seller seller=createSeller(user);
        session.beginTransaction();
        session.persist(seller);
        session.getTransaction().commit();
        System.out.println("seller Done");
         Product product=createProduct(seller);
        session.beginTransaction();
        session.persist(product);
        session.getTransaction().commit();
        System.out.println("product Done");
        BuyerBidProductId buyerBidProductId=new BuyerBidProductId(buyer.getId(),product.getId());
        System.out.println("buyerBidProductId Done");
           BuyerBidProduct buyerBidProduct=createBuyerById(buyerBidProductId,buyer, product);
        session.beginTransaction();
        session.persist(buyerBidProduct);
        session.getTransaction().commit();

        System.out.println("buyerBidProduct Done");
        BuyerBuyProductId buyProductId=createBuyProductId(buyer,product);
        System.out.println("buyProductId Done");
        
        BuyerBuyProduct buyProduct=createBuyerProduct(buyProductId,buyer,product);
          session.beginTransaction();
        session.persist(buyProduct);
        session.getTransaction().commit();
        System.out.println("buyProduct Done");
          
        Category category=createCategory(product);
         session.beginTransaction();
        session.persist(category);
        session.getTransaction().commit();
        System.out.println("category Done");
        

    }

    private static User createUser() {
        return new User("alshimaa@gmail.com", "octobar", new Date(), "alshimaa", "123");
    }

    private static Buyer createBuyer(User user) {
        return new Buyer(user, "product");
    }

    private static Seller createSeller(User user) {
        return new Seller(user, "ahmed");
        
    }

    private static Product createProduct(Seller seller) {
      Product product= new Product("Shoes","polo",new Date(),10,new Date(),new Date());
      product.setSeller(seller);
      return product;
    }

    private static BuyerBidProduct createBuyerById(BuyerBidProductId buyerBidProductId, Buyer buyer, Product product) {
        BuyerBidProduct buyerBidProduct=new BuyerBidProduct();
        buyerBidProduct.setId(buyerBidProductId);
        buyerBidProduct.setProduct(product);
        buyerBidProduct.setBuyer(buyer);
        buyerBidProduct.setDate(new Date());
        buyerBidProduct.setQuantity(10);
        return  buyerBidProduct;
    }

    private static BuyerBuyProduct createBuyerProduct(BuyerBuyProductId buyerBidProduct, Buyer buyer, Product product) {
       return new BuyerBuyProduct(buyerBidProduct, buyer, product, new Date(), 10, 10);
    }

    private static BuyerBuyProductId createBuyProductId(Buyer buyer, Product product) {
        
        return new BuyerBuyProductId(buyer.getId(), product.getId());
    }

    private static Category createCategory(Product product) {
        Set products=new HashSet();
        products.add(product);
        return new Category("clothes", "hdhdh", products);
    }
    
    
}
