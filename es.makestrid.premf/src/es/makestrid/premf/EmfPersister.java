package es.makestrid.premf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.emf.ecore.resource.Resource;
import org.prevayler.Prevayler;
import org.prevayler.PrevaylerFactory;

import es.makestrid.premf.internal.EMFSnapshotSerializer;
import es.makestrid.premf.proxies.ResourceProxy;


public class EmfPersister {
	private static final String SNAPSHOT_SUFFIX = "xmiSnapshot";
	private static final String SNAPSHOT_EXTENSION = "." + SNAPSHOT_SUFFIX;
	private static final String JOURNAL_SUFFIX = "journal";
	private static final String JOURNAL_EXTENSION = "." + JOURNAL_SUFFIX;
	
	private Prevayler prevayler = null;
	private SnapshotThread snapshotThread = null;
	private ResourceProxy data;
	private String persistenceDir;

	public EmfPersister(Resource systemRoot, String persistenceDir, int snapshotPeriodMinutes) {
		this.persistenceDir = persistenceDir;
		
		data = (ResourceProxy) ResourceProxy.wrap(systemRoot);
		
        PrevaylerFactory factory = new PrevaylerFactory();
		factory.configurePrevalentSystem(systemRoot);
        factory.configureTransactionFiltering(false);
        factory.configureSnapshotSerializer(SNAPSHOT_SUFFIX, new EMFSnapshotSerializer());
        factory.configurePrevalenceDirectory(persistenceDir);
        try {
			prevayler = factory.create();
		} catch (Exception e) {
			throw new RuntimeException("Unexpected exception creating Prevayler", e);
		}
        data.setPrevayler(prevayler);
        
        if (snapshotPeriodMinutes > 0) {
	        snapshotThread = new SnapshotThread(prevayler, persistenceDir, snapshotPeriodMinutes);
	        snapshotThread.start();
        }
	}
	
	public Resource getPersistentSystemRoot() {
		return data;
	}
	
	public Prevayler getPrevayler() {
		return prevayler;
	}
	
	public void snapshot() throws IOException {
		prevayler.takeSnapshot();
		cleanupJournalFiles();
	}
	
	public void close() {
		if (snapshotThread != null) {
			snapshotThread.terminated = true;
			snapshotThread.interrupt();
		}
		prevayler = null;
		snapshotThread = null;
	}
	
	private int sequence(String filename) {
		return Integer.parseInt(filename.substring(0, filename.indexOf('.')));
	}
	
	public void cleanupJournalFiles() {
		int highestSnapshot = -1;
		File snapshotDir = new File(persistenceDir);
		ArrayList<String> snapshotFiles = new ArrayList<String>();
		for (String file : snapshotDir.list()) {
			if (file.endsWith(JOURNAL_EXTENSION)) {
				new File(snapshotDir.getAbsolutePath() + "/" + file).delete();
			}
			if (file.endsWith(SNAPSHOT_EXTENSION)) {
				snapshotFiles.add(file);
				int fileID = sequence(file);
				if (fileID > highestSnapshot) {
					highestSnapshot = fileID;
				}
			}
		}
		for (int i = 0; i < snapshotFiles.size(); i++) {
			String file = snapshotFiles.get(i);
			if (sequence(file) < highestSnapshot) {
				new File(snapshotDir.getAbsolutePath() + "/" + file).delete();
			}
		}
	}

	
	private class SnapshotThread extends Thread {
		private static final int MINUTES = 1000 * 60;
		private Prevayler prevayler = null;
		private String persistenceDir;
		public boolean terminated = false;
		int snapshotPeriodMinutes;

		public SnapshotThread(Prevayler prevayler, String persistenceDir, int snapshotPeriodMinutes) {
			this.prevayler = prevayler;
			this.persistenceDir = persistenceDir;
			this.snapshotPeriodMinutes = snapshotPeriodMinutes;
		}

		@Override
		public void run() {
			while (!terminated) {
				try {
					sleep(MINUTES * snapshotPeriodMinutes);
				} catch (InterruptedException e) {
				}
                try {
                	if (journalFilesExist()) {
						prevayler.takeSnapshot();
						cleanupJournalFiles();
                	}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}

		private boolean journalFilesExist() {
			File snapshotDir = new File(persistenceDir);
			for (String file : snapshotDir.list()) {
				if (file.endsWith(JOURNAL_EXTENSION)) {
					return true;
				}
			}
			return false;
		}
	}

}
