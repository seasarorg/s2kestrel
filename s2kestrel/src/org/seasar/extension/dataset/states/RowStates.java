package org.seasar.extension.dataset.states;

import org.seasar.extension.dataset.RowState;

/**
 * @author higa
 *
 */
public interface RowStates {

	public RowState UNCHANGED = new UnchangedState();
	public RowState CREATED = new CreatedState();
	public RowState MODIFIED = new ModifiedState();
	public RowState REMOVED = new RemovedState();
}
