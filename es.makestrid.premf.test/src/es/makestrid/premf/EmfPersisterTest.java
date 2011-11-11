package es.makestrid.premf;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import testdata.Person;
import testdata.TestdataFactory;
import testdata.impl.TestdataPackageImpl;

public class EmfPersisterTest extends TestCase {
	
	private TestdataFactory factory;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		TestdataPackageImpl.init();
		factory = TestdataFactory.eINSTANCE;
	}
	
	private File tempDir() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		String randomTempDir = tmpDir + File.separatorChar + "test." + System.currentTimeMillis();
		File result = new File(randomTempDir);
		result.mkdirs();
		return result;
	}

	
	private Resource makePersistentSystem(String tempDirPath) {
		Resource newSystem = new XMIResourceImpl();
		EmfPersister persister = new EmfPersister(newSystem, tempDirPath, -1);
		Resource persistentSystem = persister.getPersistentSystemRoot();
		return persistentSystem;
	}
	
	public void testSaveAndRestoreOnePerson() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();

		// Create a Resource and make it persistent
		Resource persistentSystem = makePersistentSystem(tempDirPath);

		// Add a Person to the persistent resource
		Person person = factory.createPerson();
		person.setFirstName("Dave");
		persistentSystem.getContents().add(person);
		
		// Create a second (empty) Resource
		Resource secondSystem = new XMIResourceImpl();
		
		// Attach it to the persistent system's directory, which will restore the 
		// first system's contents into the second one
		new EmfPersister(secondSystem, tempDirPath, -1);
		
		// Now the second system should have a *copy* of the Person above
		assertEquals(1, secondSystem.getContents().size());
		Person restoredPerson = (Person) secondSystem.getContents().get(0);
		assertTrue("Structurally equal", EcoreUtil.equals(person, restoredPerson));
		assertFalse("Not the same object", restoredPerson.equals(person));
	}

	public void testAddAll() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);
		
		Person[] people = new Person[] {
				factory.createPerson(), factory.createPerson()
		};
		people[0].setFirstName("Herkimer");
		people[1].setFirstName("Jerkimer");
		LinkedList<Person> peopleToStore = new LinkedList<Person>();
		Collections.addAll(peopleToStore, people);
		
		system.getContents().addAll(peopleToStore);
		
		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(2, secondSystem.getContents().size());
		Person p0 = (Person) secondSystem.getContents().get(0);
		Person p1 = (Person) secondSystem.getContents().get(1);
		assertEquals("Herkimer", p0.getFirstName());
		assertEquals("Jerkimer", p1.getFirstName());
	}
	
	public void testRemoveAll() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);
		
		Person[] people = new Person[] {
				factory.createPerson(), factory.createPerson()
		};
		people[0].setFirstName("Herkimer");
		people[1].setFirstName("Jerkimer");
		LinkedList<Person> peopleToStore = new LinkedList<Person>();
		Collections.addAll(peopleToStore, people);
		
		system.getContents().addAll(peopleToStore);
		
		LinkedList<Person> peopleToRemove = new LinkedList<Person>();
		peopleToRemove.add(people[1]);
		system.getContents().removeAll(peopleToRemove);

		assertEquals(1, system.getContents().size());
		
		// Test journal replay
		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(1, secondSystem.getContents().size());
		Person p0 = (Person) secondSystem.getContents().get(0);
		assertEquals("Herkimer", p0.getFirstName());
	}
	
	public void testRetainAll() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);
		
		Person[] people = new Person[] {
				factory.createPerson(), factory.createPerson()
		};
		people[0].setFirstName("Herkimer");
		people[1].setFirstName("Jerkimer");
		LinkedList<Person> peopleToStore = new LinkedList<Person>();
		Collections.addAll(peopleToStore, people);
		
		system.getContents().addAll(peopleToStore);
		
		LinkedList<Person> peopleToRetain = new LinkedList<Person>();
		peopleToRetain.add(people[1]);
		system.getContents().retainAll(peopleToRetain);

		assertEquals(1, system.getContents().size());
		
		// Test journal replay
		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(1, secondSystem.getContents().size());
		Person p0 = (Person) secondSystem.getContents().get(0);
		assertEquals("Jerkimer", p0.getFirstName());
	}
	
	public void testClear() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);
		
		Person[] people = new Person[] {
				factory.createPerson(), factory.createPerson()
		};
		people[0].setFirstName("Herkimer");
		people[1].setFirstName("Jerkimer");
		LinkedList<Person> peopleToStore = new LinkedList<Person>();
		Collections.addAll(peopleToStore, people);

		system.getContents().addAll(peopleToStore);
		system.getContents().clear();

		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(0, secondSystem.getContents().size());
	}
}
