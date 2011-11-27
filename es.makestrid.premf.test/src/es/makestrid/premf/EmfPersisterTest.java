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
		result.deleteOnExit();
		return result;
	}

	private LinkedList<Person> people(String... firstNames) {
		LinkedList<Person> peopleToStore;
		Person[] people = new Person[firstNames.length];
		int i = 0;
		for (String name : firstNames) {
			people[i] = factory.createPerson();
			people[i].setFirstName(name);
			++i;
		}
		peopleToStore = new LinkedList<Person>();
		Collections.addAll(peopleToStore, people);
		return peopleToStore;
	}
	
	private Resource makePersistentSystem(String tempDirPath) {
		Resource newSystem = new XMIResourceImpl();
		EmfPersister persister = new EmfPersister(newSystem, tempDirPath, -1);
		Resource persistentSystem = persister.getPersistentSystemRoot();
		return persistentSystem;
	}
	
	public void testAdd() throws Exception {
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
		
		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
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
		
		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);
		
		LinkedList<Person> peopleToRemove = new LinkedList<Person>();
		peopleToRemove.add(peopleToStore.get(1));
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
		
		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);
		
		LinkedList<Person> peopleToRetain = new LinkedList<Person>();
		peopleToRetain.add(peopleToStore.get(1));
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
		
		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);
		
		system.getContents().clear();

		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(0, secondSystem.getContents().size());
	}
	
	public void testAddAtIndex() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);
		
		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);

		Person extra1 = factory.createPerson();
		extra1.setFirstName("Extra! Extra!");
		
		system.getContents().add(1, extra1);

		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(3, secondSystem.getContents().size());
		Person p0 = (Person) secondSystem.getContents().get(0);
		Person p1 = (Person) secondSystem.getContents().get(1);
		Person p2 = (Person) secondSystem.getContents().get(2);
		assertEquals("Herkimer", p0.getFirstName());
		assertEquals("Extra! Extra!", p1.getFirstName());
		assertEquals("Jerkimer", p2.getFirstName());
	}
	
	public void testRemove() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);

		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);
		
		system.getContents().remove(0);
		assertEquals(1, system.getContents().size());

		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		assertEquals(1, secondSystem.getContents().size());
		Person result = (Person) secondSystem.getContents().get(0);
		assertEquals("Jerkimer", result.getFirstName());
	}

	public void testMoveObject() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);

		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer", "Yerkimer");
		system.getContents().addAll(peopleToStore);
		
		Person personToMove = peopleToStore.get(2);
		system.getContents().move(0, personToMove);
		
		assertEquals(personToMove, system.getContents().get(0));
		
		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		Person result = (Person) secondSystem.getContents().get(0);
		assertEquals("Yerkimer", result.getFirstName());
	}

	public void testMoveFromIndex() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);

		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer", "Yerkimer");
		system.getContents().addAll(peopleToStore);
		
		int MOVE_INDEX=2;
		Person personToMove = peopleToStore.get(MOVE_INDEX);
		system.getContents().move(0, MOVE_INDEX);
		
		assertEquals(personToMove, system.getContents().get(0));
		
		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		Person result = (Person) secondSystem.getContents().get(0);
		assertEquals("Yerkimer", result.getFirstName());
	}
	
	public void testObjectMethodInvoke() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();
		Resource system = makePersistentSystem(tempDirPath);

		LinkedList<Person> peopleToStore = people("Herkimer", "Jerkimer");
		system.getContents().addAll(peopleToStore);
		
		Person person = (Person) system.getContents().get(1);
		person.setFirstName("Yerkimer");
		
		assertEquals("Yerkimer", person.getFirstName());

		Resource secondSystem = makePersistentSystem(tempDirPath);
		
		Person result = (Person) secondSystem.getContents().get(1);
		assertEquals("Yerkimer", result.getFirstName());
	}

}
