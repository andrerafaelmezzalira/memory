package prevayler;

final class TransactionCapsule extends Capsule<Transaction> {

	final void justExecute(
			final Transaction transaction,
			final PrevaylerData prevalentSystem) throws Exception {
		transaction.executeOn(prevalentSystem);
	}

	final Capsule<Transaction> cleanCopy() {
		return this;
	}

	TransactionCapsule(final Transaction transaction) {
		super(transaction);
	}

	TransactionCapsule(final byte[] serialized) {
		super(serialized);
	}

}
