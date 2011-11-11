package es.makestrid.premf.proxies;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import testdata.Person;
import testdata.TestdataFactory;
import testdata.impl.TestdataPackageImpl;
import es.makestrid.premf.PrevaylerStub;

public class EListResourceContentsProxyTest extends TestCase {

	private Resource systemRoot;
	private PrevaylerStub prevayler;
	private TestdataFactory factory;
	private EList<EObject> testee;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		TestdataPackageImpl.init();
		factory = TestdataFactory.eINSTANCE;
		
		systemRoot = new XMIResourceImpl();
		
		prevayler = new PrevaylerStub(systemRoot);
		testee = EListResourceContentsProxy.wrap(systemRoot.getContents(), prevayler, systemRoot);
	}
	
	public void testAdd() throws Exception {
		Person newPerson = factory.createPerson();
		String name = "Person0";
		newPerson.setFirstName(name);
		
		boolean result = testee.add(newPerson);
		
		assertTrue("Reported success", result);
		assertEquals("Prevayler ran add transaction", 1, prevayler.sureTransactionWithQueries.size());
		Person cloned = (Person) testee.get(0);
		assertEquals("List contains new object", newPerson.getFirstName(), cloned.getFirstName());
	}
	
	public void testAddAll() throws Exception {
		Person person1 = factory.createPerson();
		Person person2 = factory.createPerson();
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person1,
				person2
		});
		
		boolean result = testee.addAll(people);

		assertTrue("Reported success", result);
		assertEquals("Prevayler ran add transaction", 1, prevayler.sureTransactionWithQueries.size());
		assertTrue("List contains new object 1", testee.contains(person1));
		assertTrue("List contains new object 2", testee.contains(person2));
		assertEquals("List size", 2, testee.size());
	}
	
	public void testAddAllatIndex() throws Exception {
		Person person0 = factory.createPerson();
		testee.add(person0);
		
		Person person1 = factory.createPerson();
		Person person2 = factory.createPerson();
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person1,
				person2
		});
		
		boolean result = testee.addAll(0, people);

		assertTrue("Reported success", result);
		assertEquals("Prevayler ran add transaction", 2, prevayler.sureTransactionWithQueries.size());
		assertTrue("List contains new object 0", testee.contains(person0));
		assertTrue("List contains new object 1", testee.contains(person1));
		assertTrue("List contains new object 2", testee.contains(person2));
		assertEquals("List size", 3, testee.size());
		assertEquals("Object 0 in position 2", person0, testee.get(2));
	}
	
	public void testRemoveAll() throws Exception {
		Person person0 = factory.createPerson();
		Person person1 = factory.createPerson();
		Person person2 = factory.createPerson();
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person0, person1, person2
		});
		testee.addAll(people);
		
		Collection<Person> peopleToRemove = new LinkedList<Person>();
		Collections.addAll(peopleToRemove, new Person[] {
				person0, person2
		});
		
		boolean result = testee.removeAll(peopleToRemove);

		assertTrue("Reported success", result);
		assertEquals("Prevayler ran add transaction", 2, prevayler.sureTransactionWithQueries.size());
		assertFalse("List not contains new object 0", testee.contains(person0));
		assertTrue("List contains new object 1", testee.contains(person1));
		assertFalse("List not contains new object 2", testee.contains(person2));
		assertEquals("List size", 1, testee.size());
	}

	public void testRetainAll() throws Exception {
		Person person0 = factory.createPerson();
		person0.setFirstName("0");
		Person person1 = factory.createPerson();
		person1.setFirstName("1");
		Person person2 = factory.createPerson();
		person2.setFirstName("2");
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person0, person1, person2
		});
		testee.addAll(people);
		
		Collection<Person> peopleToRetain = new LinkedList<Person>();
		Collections.addAll(peopleToRetain, new Person[] {
				person0, person2
		});
		
		boolean result = testee.retainAll(peopleToRetain);

		assertTrue("Reported success", result);
		assertEquals("Prevayler ran transaction", 2, prevayler.sureTransactionWithQueries.size());
		assertTrue("List contains new object 0", testee.contains(person0));
		assertFalse("List not contains new object 1", testee.contains(person1));
		assertTrue("List contains new object 2", testee.contains(person2));
		assertEquals("List size", 2, testee.size());
	}
	
	public void testClear() throws Exception {
		Person person0 = factory.createPerson();
		person0.setFirstName("0");
		Person person1 = factory.createPerson();
		person1.setFirstName("1");
		Person person2 = factory.createPerson();
		person2.setFirstName("2");
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person0, person1, person2
		});
		testee.addAll(people);
		
		testee.clear();
		
		assertEquals("Prevayler ran transaction", 2, prevayler.sureTransactionWithQueries.size());
		assertEquals("List size", 0, testee.size());
	}
	
	public void testSet() throws Exception {
		Person person0 = factory.createPerson();
		person0.setFirstName("0");
		Person person1 = factory.createPerson();
		person1.setFirstName("1");
		Person person2 = factory.createPerson();
		person2.setFirstName("2");
		
		Collection<Person> people = new LinkedList<Person>();
		Collections.addAll(people, new Person[] {
				person0, person2
		});
		testee.addAll(people);
		
		Person oldPerson = (Person) testee.set(0, person1);

		assertEquals("Prevayler ran transaction", 2, prevayler.sureTransactionWithQueries.size());
		assertEquals("List size", 2, testee.size());
		assertEquals(person0, oldPerson);
		assertEquals(person1, testee.get(0));
	}
}
