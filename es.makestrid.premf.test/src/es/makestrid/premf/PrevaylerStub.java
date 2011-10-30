package es.makestrid.premf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.prevayler.Clock;
import org.prevayler.Prevayler;
import org.prevayler.Query;
import org.prevayler.SureTransactionWithQuery;
import org.prevayler.Transaction;
import org.prevayler.TransactionWithQuery;

public class PrevaylerStub implements Prevayler {
	
	private Object prevalentSystem;

	public PrevaylerStub(Object prevalentSystem) {
		this.prevalentSystem = prevalentSystem;
	}

	@Override
	public Object prevalentSystem() {
		return prevalentSystem;
	}

	@Override
	public Clock clock() {
		throw new UnsupportedOperationException();
	}
	
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>();

	@Override
	public void execute(Transaction transaction) {
		transactions.add(transaction);
		transaction.executeOn(prevalentSystem, new Date());
	}
	
	public ArrayList<Query> queries = new ArrayList<Query>();

	@Override
	public Object execute(Query sensitiveQuery) throws Exception {
		queries.add(sensitiveQuery);
		return sensitiveQuery.query(prevalentSystem, new Date());
	}
	
	public ArrayList<TransactionWithQuery> transactionWithQueries = new ArrayList<TransactionWithQuery>();

	@Override
	public Object execute(TransactionWithQuery transactionWithQuery)
			throws Exception {
		transactionWithQueries.add(transactionWithQuery);
		return transactionWithQuery.executeAndQuery(prevalentSystem, new Date());
	}
	
	public ArrayList<SureTransactionWithQuery> sureTransactionWithQueries = new ArrayList<SureTransactionWithQuery>();

	@Override
	public Object execute(SureTransactionWithQuery sureTransactionWithQuery) {
		sureTransactionWithQueries.add(sureTransactionWithQuery);
		return sureTransactionWithQuery.executeAndQuery(prevalentSystem, new Date());
	}

	@Override
	public void takeSnapshot() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() throws IOException {
		throw new UnsupportedOperationException();
	}
}
