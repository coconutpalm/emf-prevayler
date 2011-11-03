package es.makestrid.premf;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import testdata.Person;
import testdata.TestdataFactory;
import testdata.impl.TestdataPackageImpl;

public class EmfPersisterTest extends TestCase {
	
	private static final String TEMPDIR = "/tmp";
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

	
	public void testSaveAndRestoreOnePerson() throws Exception {
		String tempDirPath = tempDir().getAbsolutePath();

		// Create a Resource and make it persistent
		Resource firstSystem = new XMIResourceImpl();
		EmfPersister persister = new EmfPersister(firstSystem, tempDirPath, -1);
		Resource persistentSystem = persister.getPersistentSystemRoot();

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
}
