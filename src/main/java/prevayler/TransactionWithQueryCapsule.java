package prevayler;

final class TransactionWithQueryCapsule extends Capsule<TransactionWithQuery> {

	final void justExecute(final TransactionWithQuery transaction,
			final PrevaylerData prevalentSystem) {
		try {
			_queryResult = transaction.executeAndQuery(prevalentSystem);
		} catch (final RuntimeException rx) {
			_queryException = rx;
			throw rx;
		} catch (final Exception ex) {
			_queryException = ex;
		}
	}

	final PrevaylerData result() throws Exception {
		if (_queryException != null)
			throw _queryException;
		return _queryResult;
	}

	final Capsule<TransactionWithQuery> cleanCopy() {
		return new TransactionWithQueryCapsule(_serialized);
	}

	TransactionWithQueryCapsule(final TransactionWithQuery transactionWithQuery) {
		super(transactionWithQuery);
	}

	TransactionWithQueryCapsule(final byte[] serialized) {
		super(serialized);
	}

	transient PrevaylerData _queryResult;
	transient Exception _queryException;

}