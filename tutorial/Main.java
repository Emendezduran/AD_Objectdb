package tutorial;

import javax.persistence.*;
import java.util.*;


public class Main {

    // Open a database connection
    // (create a new database if it doesn't exist yet):
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("$objectdb/db/p2.odb");
    static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        
        // Store 1000 Point objects in the database:
        em.getTransaction().begin();
        for (int i = 0; i < 1000; i++) {
            Point p = new Point(i, i);
            em.persist(p);
        }
        em.getTransaction().commit();

        // Find the number of Point objects in the database:
        Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
        System.out.println("Total Points: " + q1.getSingleResult());

        // Find the average X value:
        Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
        System.out.println("Average X: " + q2.getSingleResult());
        
        
        createPoint();

        listPoints();

        // Close the database connection:
        em.close();
        emf.close();
    }
 


static private void createPoint() {
   System.out.println("CREATE POINT :");
   // manager es el EntityManager obtenido anteriorment
   EntityTransaction tx = em.getTransaction();
   tx.begin();

   try {
      em.persist(new Point(1000,1000));
      tx.commit();
   } catch (Exception e) {
      e.printStackTrace();
      tx.rollback();    
   }
}

 // Retrieve all the Point objects from the database:
 static private void listPoints() {
   System.out.println("Points list :");
   
   // Nuevamente, manager es el EntityManager obtenido anteriormente.
   List<Point> resultList = em.createQuery(
         "Select p From Point p", Point.class).getResultList();
   System.out.println("num of Points:" + resultList.size());
   for (Point next : resultList) {
      System.out.println("next point: " + next);
   }
}


}
