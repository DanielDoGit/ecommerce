package configuration;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import beans.Cidade;



public class Principal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			
		
			session.beginTransaction();
			Cidade c = (Cidade) session.load(Cidade.class, 2);
			session.getTransaction().commit();
			session.close();
			
			System.out.println(c);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (NullPointerException e) {
			e.printStackTrace();
		}
		
	}

}
