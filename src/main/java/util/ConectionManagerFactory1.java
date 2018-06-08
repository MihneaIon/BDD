package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConectionManagerFactory1 {
	
	private static EntityManagerFactory entityManagerFactory=null; 
	
	public EntityManagerFactory getFactory()
	{
		if(entityManagerFactory==null)
		{
			entityManagerFactory=Persistence.createEntityManagerFactory("LoggingERROR");
		}
		return entityManagerFactory;
	}
	
	public void close()
	{
		if(entityManagerFactory!=null && !entityManagerFactory.isOpen()) 
		{
			entityManagerFactory.close();
		}
	}

}
