package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConectionManagerFActory2 {
	
private static EntityManagerFactory entityManagerFactory=null; 
	
	public EntityManagerFactory getFactory()
	{
		if(entityManagerFactory==null)
		{
			entityManagerFactory=Persistence.createEntityManagerFactory("LoggingINFOWARN");
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
