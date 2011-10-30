package es.makestrid.premf.proxies;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.prevayler.Prevayler;

public class ResourceProxy implements Resource {
	
	public static Resource wrap(Resource delegate) {
		return new ResourceProxy(delegate);
	}

	private Resource delegate;
	private Prevayler prevayler;
	
	public ResourceProxy(Resource delegate) {
		this.delegate = delegate;
	}
	
	public void setPrevayler(Prevayler prevayler) {
		this.prevayler = prevayler;
	}
	
	public Resource unwrap() {
		return delegate;
	}

	@Override
	public EList<Adapter> eAdapters() {
		return delegate.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		return delegate.eDeliver();
	}

	@Override
	public void eSetDeliver(boolean deliver) {
		delegate.eSetDeliver(deliver);
	}

	@Override
	public void eNotify(Notification notification) {
		delegate.eNotify(notification);
	}

	@Override
	public ResourceSet getResourceSet() {
		return delegate.getResourceSet();
	}

	@Override
	public URI getURI() {
		return delegate.getURI();
	}

	@Override
	public void setURI(URI uri) {
		delegate.setURI(uri);
	}

	@Override
	public long getTimeStamp() {
		return delegate.getTimeStamp();
	}

	@Override
	public void setTimeStamp(long timeStamp) {
		delegate.setTimeStamp(timeStamp);
	}

	@Override
	public EList<EObject> getContents() {
		return EListProxy.wrap(delegate.getContents(), prevayler);
	}

	@Override
	public TreeIterator<EObject> getAllContents() {
		return TreeIteratorProxy.wrap(delegate.getAllContents(), prevayler);
	}

	@Override
	public String getURIFragment(EObject eObject) {
		return delegate.getURIFragment(eObject);
	}

	@Override
	public EObject getEObject(String uriFragment) {
		return delegate.getEObject(uriFragment);
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		delegate.save(options);
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		delegate.load(options);
	}

	@Override
	public void save(OutputStream outputStream, Map<?, ?> options)
			throws IOException {
		delegate.save(outputStream, options);
	}

	@Override
	public void load(InputStream inputStream, Map<?, ?> options)
			throws IOException {
		delegate.load(inputStream, options);
	}

	@Override
	public boolean isTrackingModification() {
		return delegate.isTrackingModification();
	}

	@Override
	public void setTrackingModification(boolean isTrackingModification) {
		delegate.setTrackingModification(isTrackingModification);
	}

	@Override
	public boolean isModified() {
		return delegate.isModified();
	}

	@Override
	public void setModified(boolean isModified) {
		delegate.setModified(isModified);
	}

	@Override
	public boolean isLoaded() {
		return delegate.isLoaded();
	}

	@Override
	public void unload() {
		delegate.unload();
	}

	@Override
	public void delete(Map<?, ?> options) throws IOException {
		delegate.delete(options);
	}

	@Override
	public EList<Diagnostic> getErrors() {
		return delegate.getErrors();
	}

	@Override
	public EList<Diagnostic> getWarnings() {
		return delegate.getWarnings();
	}
}

