package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;

import entity.EntityLogger;
import entity.Nivel;

public class LoggingUtil {

	ConectionManagerFactory1 myConectionManagerFactory1 = new ConectionManagerFactory1();
	ConectionManagerFActory2 myConectionManagerFActory2 = new ConectionManagerFActory2();

	public EntityLogger create() {
		// ConectionManagerFactory1 myConectionManagerFactory1 = new
		// ConectionManagerFactory1();
		// ConectionManagerFActory2 myConectionManagerFActory2=new
		// ConectionManagerFActory2();
		EntityLogger myEntityLogger = new EntityLogger();

		try {
			myEntityLogger = read();
			System.out.println(myEntityLogger);
			if (myEntityLogger.getMyNivel().toString().equals("ERROR")) {
				// ConectionManagerFactory1 myConectionManagerFactory1 = new
				// ConectionManagerFactory1();
				EntityManager manager1 = myConectionManagerFactory1.getFactory().createEntityManager();
				try {
					manager1.getTransaction().begin();
					manager1.merge(myEntityLogger);
					manager1.getTransaction().commit();
					return myEntityLogger;
				} catch (Exception e) {
					e.printStackTrace();
					manager1.getTransaction().rollback();
					return null;
				} finally {
					manager1.close();
					 myConectionManagerFactory1.close();
				}
			} else if (myEntityLogger.getMyNivel().toString().equals("WARN")
					|| myEntityLogger.getMyNivel().toString().equals("INFO")) {
				// ConectionManagerFActory2 myConectionManagerFActory2 = new
				// ConectionManagerFActory2();
				EntityManager manager2 = myConectionManagerFActory2.getFactory().createEntityManager();
				try {
					manager2.getTransaction().begin();
					manager2.merge(myEntityLogger);
					manager2.getTransaction().commit();
					return myEntityLogger;
				} catch (Exception e) {
					e.printStackTrace();
					manager2.getTransaction().rollback();
					return null;
				} finally {
					manager2.close();
					 myConectionManagerFActory2.close();
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<EntityLogger> DisplayERRORLevel() {
		// ConectionManagerFactory1 myConectionManagerFactory1 = new
		// ConectionManagerFactory1();
		EntityManager manager = myConectionManagerFactory1.getFactory().createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<EntityLogger> loggers = manager.createQuery("select b from EntityLogger b").getResultList();

			return loggers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			 myConectionManagerFactory1.close();
		}
	}

	public List<EntityLogger> DisplayWARNLevel() {
		// ConectionManagerFActory2 myConectionManagerFActory2 = new
		// ConectionManagerFActory2();
		EntityManager manager = myConectionManagerFActory2.getFactory().createEntityManager();
		try {
			@SuppressWarnings("unchecked")
			List<EntityLogger> loggers = manager.createQuery("select l from EntityLogger l where mynivel=1 ")
					.getResultList();
			return loggers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			 myConectionManagerFActory2.close();
		}
	}

	public List<EntityLogger> DisplayINFOlevel() {
		ConectionManagerFActory2 myConectionManagerFActory2 = new ConectionManagerFActory2();
		EntityManager manager = myConectionManagerFActory2.getFactory().createEntityManager();
		try {

			@SuppressWarnings("unchecked")
			List<EntityLogger> loggers = manager.createQuery("select l from EntityLogger l where mynivel=2 ")
					.getResultList();
			return loggers;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			manager.close();
			 myConectionManagerFActory2.close();
		}
	}

	public List<List<EntityLogger>> askForLevel() {
		List<List<EntityLogger>> myList = new ArrayList<List<EntityLogger>>();
		System.out.println(" insert the wanted level: ");
		Scanner scanner = new Scanner(System.in);
		String insertLevel = "";
		insertLevel = scanner.nextLine();
		if (insertLevel.toUpperCase().equals("ERROR")) {
			myList.add(DisplayERRORLevel());
		} else if (insertLevel.toUpperCase().equals("WARN")) {
			myList.add(DisplayERRORLevel());
			myList.add(DisplayWARNLevel());
		} else if (insertLevel.toUpperCase().equals("INFO")) {
			myList.add(DisplayERRORLevel());
			myList.add(DisplayWARNLevel());
			myList.add(DisplayINFOlevel());
		}
		return myList;
	}

	public  EntityLogger read() {
		EntityLogger myEntityLogger = new EntityLogger();

		try {
			myEntityLogger = wriitte();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myEntityLogger;
	}

	public void menu() {
		boolean var = true;
		int choice = -1;
		while (var) {
			System.out.println(" Choose you options ^_^: ");
			System.out.println("1. Insert new logg message ");
			System.out.println("2. Display my message");
			Scanner scanner = new Scanner(System.in);
			try {
				choice = scanner.nextInt();
				switch (choice) {
				case 1:
					create();
					break;
				case 2:
					System.out.println(askForLevel());
					break;
				default:
					System.out.println(" Wrong insertion , please try again ");
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(" Do you want to continue ?");
			String myOption = "no";
			scanner = new Scanner(System.in);
			try {
				myOption = scanner.nextLine();
				if (!myOption.equals("no")) {
					var = true;
				} else {
					var = false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public  EntityLogger wriitte() {
		boolean ok;
		EntityLogger myEntityLogger = new EntityLogger();
		MyDate dataNoua = new MyDate();
		Nivel myNivel = null;
		String text = null, level = "";
		Scanner scanner = new Scanner(System.in);
		do {
			ok=false;
			scanner=new Scanner(System.in);
			System.out.println("introduceti nivelul:");
			
				level = scanner.nextLine();
				System.out.println(level.toUpperCase());
				if (level.toUpperCase().equals("ERROR")) {
					myEntityLogger.setMyNivel(myNivel.ERROR);
				} else if (level.toUpperCase().equals("WARN")) {
					myEntityLogger.setMyNivel(myNivel.WARN);
				} else if (level.toUpperCase().equals("INFO")) {
					myEntityLogger.setMyNivel(myNivel.INFO);
				} else {
					System.out.println("Error please insert again");
					ok=true;
				}
		} while (ok==true);
		System.out.println("Insert the text");
		text = scanner.nextLine();
		myEntityLogger.setText(text);
		myEntityLogger.setData(dataNoua.afisare());
		return myEntityLogger;
	}
}
