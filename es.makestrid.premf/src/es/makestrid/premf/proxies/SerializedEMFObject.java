package es.makestrid.premf.proxies;

import static es.makestrid.premf.internal.EcoreHelper.deserialize;
import static es.makestrid.premf.internal.EcoreHelper.serialize;

import java.io.Serializable;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public class SerializedEMFObject implements Serializable {
	private static final long serialVersionUID = 7273731945177917579L;
	private static final String URI_NOT_CONTAINED = "/-1";

	private String uriFragment = null;
	private String serializedObject = null;

	public SerializedEMFObject(EObject object, Resource sourceResource) {
		this.uriFragment = sourceResource.getURIFragment((EObject) object);
		if (URI_NOT_CONTAINED.equals(this.uriFragment)) {
			this.serializedObject = serialize((EObject)object);
		}
	}
	
	public EObject get(Resource targetResource) {
		if (URI_NOT_CONTAINED.equals(this.uriFragment)) {
			return deserialize(serializedObject);
		} else {
			return targetResource.getEObject(uriFragment);
		}
	}
}