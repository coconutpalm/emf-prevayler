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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;

public class ResourcesHelper {
	public static final IWorkspace WS = ResourcesPlugin.getWorkspace();

	public static final IWorkspaceRoot ROOT = WS.getRoot();

	public static IResource findResourceByLocation(String location) {
		IPath path = new Path(location);
		for (int i = 0; i < path.segmentCount(); i++) {
			IResource resource = ROOT.findMember(path);
			if (resource != null && resource.exists()) {
				return resource;
			}

			path = path.removeFirstSegments(1);
		}

		return null;
	}

	public static void writeFile(IFile file, String[] content,
			IProgressMonitor monitor) throws CoreException {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < content.length; i++) {
			String line = content[i];
			buffer.append(line);
			buffer.append("\n");
		}

		writeFile(file, buffer.toString(), monitor);
	}

	public static void writeFile(IFile file, String content,
			IProgressMonitor monitor) throws CoreException {
		InputStream stream = new ByteArrayInputStream(content.getBytes());
		writeFile(file, stream, monitor);
	}

	public static void writeFile(IFile file, InputStream content,
			IProgressMonitor monitor) throws CoreException {
		if (file.exists()) {
			file.setContents(content, false, true, monitor);
		} else {
			if (file.getParent() instanceof IFolder) {
				mkdirs((IFolder) file.getParent(), monitor);
			}

			file.create(content, true, monitor);
		}
	}

	public static void mkdirs(IFolder folder, IProgressMonitor monitor)
			throws CoreException {
		if (folder != null && !folder.exists()) {
			IContainer parent = folder.getParent();
			if (parent != null && parent instanceof IFolder) {
				mkdirs((IFolder) parent, monitor);
			}

			folder.create(true, true, monitor);
		}
	}

	public static void mkdirs(IPath path, IProgressMonitor monitor)
			throws CoreException {
		IFolder folder = ROOT.getFolder(path);
		mkdirs(folder, monitor);
	}

	public static String readFileIntoString(IFile file) throws CoreException,
			IOException {
		return readFileIntoString(file, null);
	}

	public static byte[] readFileIntoByteArray(IFile file)
			throws CoreException, IOException {
		InputStream contents = file.getContents();
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			IOHelper.copy(contents, output);
			return output.toByteArray();
		} finally {
			IOHelper.close(contents);
		}
	}

	public static String readFileIntoString(IFile file, String linePrefix)
			throws CoreException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				file.getContents()));
		StringBuffer buffer = new StringBuffer();
		String line;

		while ((line = br.readLine()) != null) {
			if (linePrefix != null) {
				buffer.append(linePrefix);
			}

			buffer.append(line);
			buffer.append("\n");
		}

		return buffer.toString();
	}

	public static String[] readFileIntoStringArray(IFile file)
			throws IOException, CoreException {
		return readFileIntoStringArray(file.getContents());
	}

	public static String[] readFileIntoStringArray(InputStream stream)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		List result = new ArrayList();
		String line;

		while ((line = br.readLine()) != null) {
			result.add(line);
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	public static IProject ensureProject(String name) throws CoreException {
		IProject project = ResourcesHelper.ROOT.getProject(name);

		if (!project.exists()) {
			project.create(new NullProgressMonitor());
		}

		if (!project.isOpen()) {
			project.open(new NullProgressMonitor());
		}

		return project;
	}

	public static IFolder ensureFolder(IContainer container, String path)
			throws CoreException {
		if (container == null) {
			container = ResourcesHelper.WS.getRoot();
		}

		IFolder folder = container.getFolder(new Path(path));

		if (!folder.exists()) {
			folder.create(true, true, new NullProgressMonitor());
		}

		return folder;
	}

	public static boolean ensureFile(String path, String content,
			boolean force, IProgressMonitor monitor) throws CoreException,
			IOException {
		return ensureFile(path, content.getBytes(), force, monitor);
	}

	public static boolean ensureFile(String path, byte[] content,
			boolean force, IProgressMonitor monitor) throws CoreException,
			IOException {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = root.getFile(new Path(path));

		IContainer parent = file.getParent();
		if (parent instanceof IFolder) {
			mkdirs((IFolder) parent, monitor);
		}

		ByteArrayInputStream newContent = new ByteArrayInputStream(content);
		if (file.exists()) {
			if (!force) {
				InputStream oldContent = file.getContents();
				if (IOHelper.equals(newContent, oldContent)) {
					return false;
				}

				newContent.reset();
			}

			file.setContents(newContent, true, true, monitor);
		} else {
			file.create(newContent, true, monitor);
		}

		return true;
	}

	public static void addNatureToProject(String natureId, IProject project,
			IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures = new String[natures.length + 1];
		System.arraycopy(natures, 0, newNatures, 0, natures.length);
		newNatures[natures.length] = natureId;
		description.setNatureIds(newNatures);
		project.setDescription(description, monitor);
	}

	public static void removeNatureFromProject(String natureId,
			IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		List<String> list = new ArrayList<String>(natures.length);

		for (int i = 0; i < natures.length; i++) {
			String id = natures[i];
			if (!id.equals(natureId)) {
				list.add(id);
			}
		}
		String[] newNatures = list.toArray(new String[list.size()]);
		description.setNatureIds(newNatures);
		project.setDescription(description, monitor);
	}

	public static void addReferenceToProject(IProject referencedProject,
			IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		IProject[] referencedProjects = description.getReferencedProjects();
		IProject[] newReferencedProjects = new IProject[referencedProjects.length + 1];
		System.arraycopy(referencedProjects, 0, newReferencedProjects, 0,
				referencedProjects.length);
		newReferencedProjects[referencedProjects.length] = referencedProject;
		description.setReferencedProjects(newReferencedProjects);
		project.setDescription(description, monitor);
	}

	public static void removeReferenceFromProject(IProject referencedProject,
			IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription description = project.getDescription();
		IProject[] referencedProjects = description.getReferencedProjects();
		List<IProject> list = new ArrayList<IProject>(referencedProjects.length);

		for (int i = 0; i < referencedProjects.length; i++) {
			IProject ref = referencedProjects[i];
			if (!ref.equals(referencedProject)) {
				list.add(ref);
			}
		}
		IProject[] newReferencedProjects = list.toArray(new IProject[list
				.size()]);
		description.setReferencedProjects(newReferencedProjects);
		project.setDescription(description, monitor);
	}

	public static void removeBuilderFromProject(String builderId,
			IProject project, IProgressMonitor monitor) throws CoreException {
		IProjectDescription desc = project.getDescription();
		ICommand[] oldSpec = desc.getBuildSpec();
		int remaining = 0;
		int oldLength = oldSpec.length;

		if (oldLength == 0) {
			return;
		}

		// null out all commands that match the builder to remove
		for (int i = 0; i < oldSpec.length; i++) {
			if (oldSpec[i].getBuilderName().equals(builderId)) {
				oldSpec[i] = null;
			} else {
				remaining++;
			}
		}

		// check if any were actually removed
		if (remaining == oldSpec.length)
			return;
		ICommand[] newSpec = new ICommand[remaining];

		for (int i = 0, newIndex = 0; i < oldLength; i++) {
			if (oldSpec[i] != null)
				newSpec[newIndex++] = oldSpec[i];
		}

		desc.setBuildSpec(newSpec);
		project.setDescription(desc, IResource.NONE, monitor);
	}

	public static void addBuilderToProject(String builderId, IProject project,
			IProgressMonitor monitor) throws CoreException {
		IProjectDescription desc = project.getDescription();
		ICommand[] commands = desc.getBuildSpec();

		for (int i = 0; i < commands.length; ++i) {
			if (commands[i].getBuilderName().equals(builderId)) {
				return;
			}
		}

		// add builder to project
		ICommand command = desc.newCommand();
		command.setBuilderName(builderId);
		ICommand[] newCommands = new ICommand[commands.length + 1];

		// Add it before other builders.
		System.arraycopy(commands, 0, newCommands, 1, commands.length);
		newCommands[0] = command;
		desc.setBuildSpec(newCommands);
		project.setDescription(desc, monitor);
	}

	public static IProjectNature[] getNatures(String natureId,
			IProject[] projects) throws CoreException {
		List<IProjectNature> list = new ArrayList<IProjectNature>();

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			IProjectNature projectNature = project.getNature(natureId);
			if (projectNature != null) {
				list.add(projectNature);
			}
		}

		return list.toArray(new IProjectNature[list.size()]);
	}

	public static IProjectNature[] getNatures(String natureId)
			throws CoreException {
		return getNatures(natureId, getProjects());
	}

	public static IProject[] getProjects() {
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject[] projects = root.getProjects();
		return projects;
	}

	public static IProject[] getProjects(String natureId, IProject[] projects) {
		List<IProject> list = new ArrayList<IProject>();

		for (int i = 0; i < projects.length; i++) {
			IProject project = projects[i];
			if (hasNature(project, natureId)) {
				list.add(project);
			}
		}

		return list.toArray(new IProject[list.size()]);
	}

	public static boolean hasNature(IProject project, String natureId) {
		try {
			return project != null && project.exists() && project.isOpen()
					&& project.hasNature(natureId);
		} catch (CoreException ex) {
			return false;
		}
	}

	public static IProject[] getProjects(String natureId) {
		return getProjects(natureId, getProjects());
	}

	public static void addPostChangeListener(IResourceChangeListener listener) {
		WS.addResourceChangeListener(listener, IResourceChangeEvent.POST_CHANGE);
	}

	public static void removeListener(IResourceChangeListener listener) {
		WS.removeResourceChangeListener(listener);
	}

	public static void dump(IResourceChangeEvent event) {
		System.err.println("======================================================");
		System.err.println(event.getDelta());
	}

	public static void dump(IResourceDelta delta) {
		if (delta == null) {
			return;
		}

		String kind = kindStr(delta.getKind());
		String flags = flagsStr(delta.getFlags());
		System.err.println(
				delta.getFullPath() + " -> " + kind
						+ (flags.length() == 0 ? "" : " -> " + flags));

		IResourceDelta[] children = delta.getAffectedChildren();
		for (int i = 0; i < children.length; i++) {
			IResourceDelta child = children[i];
			dump(child);

		}
	}

	public static String kindStr(int kind) {
		switch (kind) {
		case IResourceDelta.ADDED:
			return "ADDED";
		case IResourceDelta.REMOVED:
			return "REMOVED";
		case IResourceDelta.CHANGED:
			return "CHANGED";
		case IResourceDelta.ADDED_PHANTOM:
			return "ADDED_PHANTOM";
		case IResourceDelta.REMOVED_PHANTOM:
			return "REMOVED_PHANTOM";
		}

		return Integer.toString(kind);
	}

	public static String flagsStr(int flags) {
		StringBuffer buffer = new StringBuffer();

		appendFlag(buffer, flags & IResourceDelta.CONTENT, "CONTENT");
		appendFlag(buffer, flags & IResourceDelta.MOVED_FROM, "MOVED_FROM");
		appendFlag(buffer, flags & IResourceDelta.MOVED_TO, "MOVED_TO");
		appendFlag(buffer, flags & IResourceDelta.OPEN, "OPEN");
		appendFlag(buffer, flags & IResourceDelta.TYPE, "TYPE");
		appendFlag(buffer, flags & IResourceDelta.SYNC, "SYNC");
		appendFlag(buffer, flags & IResourceDelta.MARKERS, "MARKERS");
		appendFlag(buffer, flags & IResourceDelta.REPLACED, "REPLACED");
		appendFlag(buffer, flags & IResourceDelta.DESCRIPTION, "DESCRIPTION");
		appendFlag(buffer, flags & IResourceDelta.ENCODING, "ENCODING");

		return buffer.toString();
	}

	private static void appendFlag(StringBuffer buffer, int flags, String label) {
		if (flags != 0) {
			if (buffer.length() > 0) {
				buffer.append(" | ");
			}

			buffer.append(label);
		}
	}

	public static IPath computeFullPath(URI uri) {
		if (uri == null) {
			return null;
		}

		String devicePath = uri.devicePath();
		if (devicePath == null) {
			return null;
		}

		IPath path = new Path(devicePath);
		if ("platform".equals(uri.scheme())) {
			path = path.removeFirstSegments(1);
		}

		return path.makeAbsolute();
	}

	public static List<IFile> collectFiles(IResource root) throws CoreException {
		return collectFiles(root, null);
	}

	public static List<IFile> collectFiles(IResource root, String extension)
			throws CoreException {
		FileCollector collector = extension == null ? new FileCollector()
				: new FileByExtensionCollector(extension);

		root.accept(collector);
		return collector.getFiles();
	}

	public static class DeltaDumper implements IResourceChangeListener {
		public static final String INDENT = "  ";

		public DeltaDumper() {
		}

		public void resourceChanged(IResourceChangeEvent event) {
			dump(event);
		}
	}

	public static class FileCollector implements IResourceVisitor {
		protected List<IFile> files = new ArrayList<IFile>();

		public FileCollector() {
		}

		public List<IFile> getFiles() {
			return files;
		}

		public boolean visit(IResource resource) throws CoreException {
			if (resource instanceof IFile && isMatching((IFile) resource)) {
				files.add((IFile) resource);
			}

			return true;
		}

		protected boolean isMatching(IFile file) {
			return true;
		}
	}

	public static class FileByExtensionCollector extends FileCollector {
		protected String extension;

		public FileByExtensionCollector(String extension) {
			this.extension = extension;
		}

		public String getExtension() {
			return extension;
		}

		@Override
		protected boolean isMatching(IFile file) {
			return StringHelper.equals(file.getFileExtension(), extension);
		}
	}

	public static IFile getFile(String path) {
		if (path == null || path.length() == 0) {
			throw new IllegalArgumentException("Path not specified");
		}

		IResource resource = ResourcesHelper.ROOT.findMember(new Path(path));
		if (resource != null && resource.exists()) {
			if (resource instanceof IFile) {
				return (IFile) resource;
			} else {
				throw new IllegalArgumentException("Not a file: " + path);
			}
		}

		throw new IllegalArgumentException("File not found: " + path);
	}

	public static IPath getLocation(IPath fullPath) {
		IResource resource = ResourcesHelper.ROOT.findMember(fullPath);
		if (resource != null && resource.exists()) {
			return resource.getLocation();
		}

		return ResourcesHelper.ROOT.getLocation().append(fullPath);
	}
}
