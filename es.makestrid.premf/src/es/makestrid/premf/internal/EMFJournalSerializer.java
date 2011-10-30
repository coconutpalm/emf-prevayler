package es.makestrid.premf.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.prevayler.foundation.serialization.Serializer;

public class EMFJournalSerializer implements Serializer {

	@Override
	public void writeObject(OutputStream stream, Object object)
			throws IOException {
		EMFJournalCommand change = (EMFJournalCommand) object;
		XMIResourceImpl writer = new XMIResourceImpl();
		writer.getContents().add(change.getChangeTransaction());
		writer.save(stream, null);
	}

	@Override
	public Object readObject(InputStream stream) throws IOException, ClassNotFoundException {
		return null;
	}

}
