package es.makestrid.premf.internal;

import transactionmodel.ChangeTransaction;

public interface EMFJournalCommand {
	ChangeTransaction getChangeTransaction();
}
