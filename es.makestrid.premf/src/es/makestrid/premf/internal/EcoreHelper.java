/***************************************************************************
 * Copyright (c) 2006 Eike Stepper, Fuggerstr. 39, 10777 Berlin, Germany.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Eike Stepper - initial API and implementation
 **************************************************************************/
package es.makestrid.premf.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.ReflectionException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;

public class EcoreHelper {
	private static final String ENCODING = "UTF-8";

	public static Set<EClass> getAllSuperClasses(EObject eObject) {
		return getAllSuperClasses(eObject.eClass());
	}

	public static Set<EClass> getAllSuperClasses(EClass eClass) {
		Set<EClass> result = new HashSet<EClass>();
		getAllSuperClasses(eClass, result);
		return result;
	}

	private static void getAllSuperClasses(EClass eClass, Set<EClass> result) {
		EList superTypes = eClass.getESuperTypes();
		for (Iterator it = superTypes.iterator(); it.hasNext();) {
			EClass element = (EClass) it.next();
			result.add(element);
			getAllSuperClasses(element, result);
		}
	}

	public static IFile getFile(Resource resource) {
		URI uri = resource.getURI();
		if (!"platform".equals(uri.scheme())) {
			return null;
		}
		if (uri.segmentCount() < 3) {
			return null;
		}

		String[] segments = uri.segments();
		if (!"resource".equals(segments[0])) {
			return null;
		}

		IPath path = new Path("/");
		for (int i = 1; i < segments.length; i++) {
			path = path.append(segments[i]);
		}

		return ResourcesHelper.ROOT.getFile(path);
	}

	public static ResourceSet createXMLResourceSet() {
		return createXMLResourceSet("*");
	}

	public static ResourceSet createXMLResourceSet(String fileExtension) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Map map = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		map.put(fileExtension, new XMLResourceFactoryImpl());
		return resourceSet;
	}

	public static Resource getXMLResource(String pathName) {
		ResourceSet resourceSet = createXMLResourceSet();
		URI uri = URI.createFileURI(pathName);
		Resource resource = resourceSet.getResource(uri, true);
		return resource;
	}

	public static Resource createXMLResource(String pathName) {
		ResourceSet resourceSet = createXMLResourceSet();
		URI uri = URI.createFileURI(pathName);
		Resource resource = resourceSet.createResource(uri);
		return resource;
	}

	public static boolean putXMLObject(String pathName, EObject object)
			throws IOException {
		Resource resource = createXMLResource(pathName);
		if (resource != null) {
			resource.getContents().add(object);
			resource.save(Collections.EMPTY_MAP);
			return true;
		}
		return false;
	}

	public static EObject getXMLObject(String pathName, int index) {
		Resource resource = getXMLResource(pathName);
		if (resource == null) {
			return null;
		}

		return (EObject) resource.getContents().get(index);
	}

	public static EObject getXMLObject(String pathName) {
		return getXMLObject(pathName, 0);
	}

	public static ResourceSet createXMIResourceSet() {
		return createXMIResourceSet("*");
	}

	public static ResourceSet createXMIResourceSet(String fileExtension) {
		ResourceSet resourceSet = new ResourceSetImpl();
		Map map = resourceSet.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		map.put(fileExtension, new XMIResourceFactoryImpl());
		return resourceSet;
	}

	public static Resource getXMIResource(String pathName) {
		ResourceSet resourceSet = createXMIResourceSet();
		URI uri = URI.createFileURI(pathName);
		Resource resource = resourceSet.getResource(uri, true);
		return resource;
	}

	public static Resource createXMIResource(String pathName) {
		ResourceSet resourceSet = createXMIResourceSet();
		URI uri = URI.createFileURI(pathName);
		Resource resource = resourceSet.createResource(uri);
		return resource;
	}
	
	/**
	 * Serialize an EObject to a String in XMI, UTF-8 encoding.
	 * 
	 * @param object The object to serialize
	 * @return The XMI string in UTF-8 encoding
	 */
	public static String serialize(EObject object) {
		Resource resource = new XMIResourceImpl();
		resource.getContents().add(object);
		
		StringWriter stringWriter = new StringWriter();
		URIConverter.WriteableOutputStream outputStream = new URIConverter.WriteableOutputStream(
				stringWriter, ENCODING);
		
		try {
			Map<String, String> options = new HashMap<String, String>();
			options.put(XMIResource.OPTION_ENCODING, ENCODING);
			resource.save(outputStream, options);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected exception", e);
		}

		String resourceString = stringWriter.toString();
		return resourceString;
	}
	
	/**
	 * Deserialize a UTF-8 String containing a single EObject back into its original EObject.
	 * 
	 * @param string The UTF-8 String
	 * @return The EObject
	 */
	public static EObject deserialize(String string) {
		ByteArrayInputStream input = null;
		try {
			input = new ByteArrayInputStream(string.getBytes(ENCODING));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unexpected", e);
		}
		
		Resource resource = new XMIResourceImpl();
		Map<String, String> options = new HashMap<String, String>();
		options.put(XMIResource.OPTION_ENCODING, ENCODING);
		try {
			resource.load(input, options);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected", e);
		}
		return resource.getContents().get(0);
	}

	public static boolean putXMIObject(String pathName, EObject object)
			throws IOException {
		Resource resource = createXMIResource(pathName);
		if (resource != null) {
			resource.getContents().add(object);
			resource.save(Collections.EMPTY_MAP);
			return true;
		}
		return false;
	}

	public static EObject getXMIObject(String pathName, int index) {
		Resource resource = getXMIResource(pathName);
		if (resource == null) {
			return null;
		}

		return (EObject) resource.getContents().get(index);
	}

	public static EObject getXMIObject(String pathName) {
		return getXMIObject(pathName, 0);
	}

	public static String getFullName(EPackage ePackage) {
		EPackage superPackage = ePackage.getESuperPackage();
		if (superPackage == null) {
			return ePackage.getName();
		}
		return getFullName(superPackage) + "." + ePackage.getName();
	}

	public static String getFullName(EClass eClass) {
		EPackage ePackage = eClass.getEPackage();
		if (ePackage == null) {
			return eClass.getName();
		}
		return getFullName(ePackage) + "." + eClass.getName();
	}

//	public static void dumpURI(URI uri, Logger logger) {
//		logger.debug("URI: " + uri);
//		logger.debug("authority: " + uri.authority());
//		logger.debug("device: " + uri.device());
//		logger.debug("devicePath: " + uri.devicePath());
//		logger.debug("fileExtension: " + uri.fileExtension());
//		logger.debug("fragment: " + uri.fragment());
//		logger.debug("host: " + uri.host());
//		logger.debug("lastSegment: " + uri.lastSegment());
//		logger.debug("opaquePart: " + uri.opaquePart());
//		logger.debug("path: " + uri.path());
//		logger.debug("port: " + uri.port());
//		logger.debug("query: " + uri.query());
//		logger.debug("scheme: " + uri.scheme());
//		logger.debug("segmentCount: " + uri.segmentCount());
//		logger.debug("toFileString: " + uri.toFileString());
//		logger.debug("userInfo: " + uri.userInfo());
//		logger.debug("hasAbsolutePath: " + uri.hasAbsolutePath());
//		logger.debug("schemehasAbsolutePath: " + uri.hasAbsolutePath());
//		logger.debug("hasAuthority: " + uri.hasAuthority());
//		logger.debug("hasDevice: " + uri.hasDevice());
//		logger.debug("hasEmptyPath: " + uri.hasEmptyPath());
//		logger.debug("hasFragment: " + uri.hasFragment());
//		logger.debug("hasOpaquePart: " + uri.hasOpaquePart());
//		logger.debug("hasPath: " + uri.hasPath());
//		logger.debug("hasQuery: " + uri.hasQuery());
//		logger.debug("hasRelativePath: " + uri.hasRelativePath());
//		logger.debug("hasTrailingPathSeparator: "
//				+ uri.hasTrailingPathSeparator());
//		logger.debug("isCurrentDocumentReference: "
//				+ uri.isCurrentDocumentReference());
//		logger.debug("isEmpty: " + uri.isEmpty());
//		logger.debug("isFile: " + uri.isFile());
//		logger.debug("isHierarchical: " + uri.isHierarchical());
//		logger.debug("isPrefix: " + uri.isPrefix());
//		logger.debug("isRelative: " + uri.isRelative());
//		logger.debug("segments: " + uri.segments());
//	}

	public static String getProjectName(URI uri) {
		if ("platform".equals(uri.scheme())) {
			if (uri.segmentCount() < 3) {
				return null;
			}

			String[] segments = uri.segments();
			if (!"resource".equals(segments[0])) {
				return null;
			}

			return segments[1];
		}

		if ("file".equals(uri.scheme())) {
			// TODO getLocation() not working for linked projects!!!
			String[] wsSegments = ResourcesHelper.ROOT.getLocation().segments();
			String[] uriSegments = uri.segments();
			if (uriSegments.length - 1 < wsSegments.length) {
				return null;
			}

			for (int i = 0; i < wsSegments.length; i++) {
				String wsSegment = wsSegments[i];
				String uriSegment = uriSegments[i];
				if (!uriSegment.equals(wsSegment)) {
					return null;
				}
			}

			return uriSegments[wsSegments.length];
		}

		return null;
	}

	public static String getFullPath(URI uri) {
		if ("platform".equals(uri.scheme())) {
			if (uri.segmentCount() < 3) {
				return null;
			}

			String[] segments = uri.segments();
			if (!"resource".equals(segments[0])) {
				return null;
			}

			IPath path = new Path("/");
			for (int i = 1; i < segments.length; i++) {
				String segment = segments[i];
				path = path.append(segment);
			}

			return path.toString();
		}

		return null;
	}

	public static String getBundleId(URI uri) {
		if ("platform".equals(uri.scheme())) {
			if (uri.segmentCount() < 3) {
				return null;
			}

			String[] segments = uri.segments();
			if (!"plugin".equals(segments[0])) {
				return null;
			}

			return segments[1];
		}

		return null;
	}

	public static Set<EPackage> getSubPackages(EPackage basePackage,
			boolean includeBasePackage) {
		Set<EPackage> result = new HashSet<EPackage>();
		if (includeBasePackage) {
			result.add(basePackage);
		}

		collectSubPackages(basePackage, result);
		return result;
	}

	private static void collectSubPackages(EPackage basePackage,
			Set<EPackage> result) {
		List<EPackage> subPackages = basePackage.getESubpackages();
		for (EPackage subPackage : subPackages) {
			result.add(subPackage);
			collectSubPackages(subPackage, result);
		}
	}

	public static Set<EPackage> getAllPackages() {
		Set<EPackage> result = new HashSet<EPackage>();
		Set<String> nsURIs = new HashSet(EPackage.Registry.INSTANCE.keySet());
		for (String nsURI : nsURIs) {
			EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
			result.add(ePackage);
			collectSubPackages(ePackage, result);
		}

		return result;
	}

	public static Set<EClass> getAllSubClasses(EClass baseClass,
			boolean includeBaseClass, boolean includeInterfaces,
			boolean includeAbstracts) {
		Set<EClass> result = new HashSet<EClass>();
		if (includeBaseClass) {
			includeClass(baseClass, includeInterfaces, includeAbstracts, result);
		}

		Set<EPackage> ePackages = getAllPackages();
		for (EPackage ePackage : ePackages) {
			List<EClassifier> classifiers = ePackage.getEClassifiers();
			for (EClassifier classifier : classifiers) {
				if (classifier instanceof EClass
						&& baseClass.isSuperTypeOf((EClass) classifier)) {
					includeClass(classifier, includeInterfaces,
							includeAbstracts, result);
				}
			}
		}

		return result;
	}

	private static void includeClass(EClassifier classifier,
			boolean includeInterfaces, boolean includeAbstracts,
			Set<EClass> result) {
		if (classifier instanceof EClass) {
			EClass eClass = (EClass) classifier;
			if (eClass.isInterface() && !includeInterfaces)
				return;
			if (eClass.isAbstract() && !includeAbstracts)
				return;
			result.add(eClass);
		}
	}
}
