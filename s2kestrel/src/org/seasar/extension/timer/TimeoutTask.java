package org.seasar.extension.timer;

import org.seasar.framework.exception.SIllegalStateException;

public class TimeoutTask {

	private final static int ACTIVE = 0;
	private final static int STOPPED = 1;
	private final static int CANCELED = 2;

	private final TimeoutTarget timeoutTarget_;
	private final long timeoutMillis_;
	private final boolean permanent_;
	private long startTime_;
	private int status_ = ACTIVE;

	TimeoutTask(TimeoutTarget timeoutTarget, int timeout, boolean permanent) {
		timeoutTarget_ = timeoutTarget;
		timeoutMillis_ = timeout * 1000;
		permanent_ = permanent;
		startTime_ = System.currentTimeMillis();
	}

	public boolean isExpired() {
		return System.currentTimeMillis() >= startTime_ + timeoutMillis_;
	}

	public boolean isPermanent() {
		return permanent_;
	}

	public boolean isCanceled() {
		return status_ == CANCELED;
	}

	public void cancel() {
		status_ = CANCELED;
	}

	public boolean isStopped() {
		return status_ == STOPPED;
	}

	public void stop() {
		if (status_ != ACTIVE) {
			throw new SIllegalStateException(
				"ESSR0026",
				new Object[] { String.valueOf(status_)});
		}
		status_ = STOPPED;
	}

	public void restart() {
		status_ = ACTIVE;
		startTime_ = System.currentTimeMillis();
	}

	void expired() {
		timeoutTarget_.expired();
	}
}
