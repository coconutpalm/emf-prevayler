package es.makestrid.premf.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.prevayler.foundation.serialization.Serializer;

import es.makestrid.premf.proxies.ResourceProxy;

public class EMFSnapshotSerializer implements Serializer {

	@Override
	public void writeObject(OutputStream stream, Object object)
			throws IOException {
		Resource root  = (Resource)object;
		root.save(stream, null);
	}

	@Override
	public Object readObject(InputStream stream) throws IOException, ClassNotFoundException {
		Resource root = constructResourceImpl();
		root.load(stream, null);
		return ResourceProxy.wrap(root);
	}

	/**
	 * @return the correct type of ResourceImpl required to deserialize your snapshot.
	 * The default implementation uses XMIResourceImpl.  Clients may override.
	 */
	public Resource constructResourceImpl() {
		return new XMIResourceImpl();
	}

}
