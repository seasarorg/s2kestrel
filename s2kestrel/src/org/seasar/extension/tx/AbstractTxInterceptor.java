package org.seasar.extension.tx;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;

import org.aopalliance.intercept.MethodInterceptor;
import org.seasar.framework.exception.SIllegalArgumentException;

/**
 * @author higa
 *
 */
public abstract class AbstractTxInterceptor implements MethodInterceptor {

	private TransactionManager transactionManager_;

	private List txRules_ = new ArrayList();

	public AbstractTxInterceptor(TransactionManager transactionManager) {
		transactionManager_ = transactionManager;
	}

	public final TransactionManager getTransactionManager() {
		return transactionManager_;
	}

	public final boolean hasTransaction() throws SystemException {
		return transactionManager_.getStatus() != Status.STATUS_NO_TRANSACTION;
	}
	
	public final Transaction getTransaction() throws SystemException {
		return transactionManager_.getTransaction();
	}

	public final void begin() throws NotSupportedException, SystemException {
		transactionManager_.begin();
	}

	public final void commit()
		throws
			SecurityException,
			IllegalStateException,
			RollbackException,
			HeuristicMixedException,
			HeuristicRollbackException,
			SystemException {

		transactionManager_.commit();
	}

	public final void rollback()
		throws IllegalStateException, SecurityException, SystemException {
		
		if (hasTransaction()) {
			transactionManager_.rollback();
		}
	}

	public final Transaction suspend() throws SystemException {
		return transactionManager_.suspend();
	}

	public final void resume(Transaction transaction)
		throws InvalidTransactionException, IllegalStateException, SystemException {
		transactionManager_.resume(transaction);
	}

    public final boolean complete(Throwable t) throws SecurityException, IllegalStateException,
            RollbackException, HeuristicMixedException, HeuristicRollbackException, SystemException {
        for (int i = 0; i < txRules_.size(); ++i) {
            TxRule rule = (TxRule) txRules_.get(i);
            if (rule.isAssignableFrom(t)) {
                return rule.complete();
            }
        }
        rollback();
        return false;
    }

    public final void addCommitRule(Class exceptionClass) {
        txRules_.add(new TxRule(exceptionClass, true));
    }

    public final void addRollbackRule(Class exceptionClass) {
        txRules_.add(new TxRule(exceptionClass, false));
    }

    private class TxRule {
        private Class exceptionClass_;
        private boolean commit_;

        public TxRule(Class exceptionClass, boolean commit) {
            if (!Throwable.class.isAssignableFrom(exceptionClass)) {
                throw new SIllegalArgumentException("ESSR0365", new Object[] { exceptionClass
                        .getName() });
            }
            exceptionClass_ = exceptionClass;
            commit_ = commit;
        }

        public boolean isAssignableFrom(Throwable t) {
            return exceptionClass_.isAssignableFrom(t.getClass());
        }

        public boolean complete() throws RollbackException, HeuristicMixedException,
                HeuristicRollbackException, SystemException {
            if (commit_) {
                commit();
            }
            else {
                rollback();
            }
            return commit_;
        }
    }
}
