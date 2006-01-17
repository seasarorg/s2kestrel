package org.seasar.extension.timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.seasar.framework.util.SLinkedList;

public class TimeoutManager implements Runnable {

	private static TimeoutManager _instance = new TimeoutManager();

	private Thread thread_;
	private SLinkedList timeoutTaskList_ = new SLinkedList();

	private TimeoutManager() {
		start();
	}

	public static TimeoutManager getInstance() {
		return _instance;
	}

	public void start() {
		thread_ = new Thread(this);
		thread_.setDaemon(true);
		thread_.start();
	}

	public void stop() {
		thread_.interrupt();
		thread_ = null;
	}

	public synchronized void clear() {
		timeoutTaskList_.clear();
	}

	public void run() {
	    List expiredTask = new ArrayList();
		while (thread_ != null) {
			try {
				synchronized (timeoutTaskList_) {
					while (timeoutTaskList_.isEmpty()) {
						timeoutTaskList_.wait();
					}
					for (SLinkedList.Entry e = timeoutTaskList_.getFirstEntry();
						e != null;
						e = e.getNext()) {
						TimeoutTask task = (TimeoutTask) e.getElement();
						if (task.isCanceled()) {
							e.remove();
							continue;
						}
						if (task.isStopped()) {
							continue;
						}
						if (task.isExpired()) {
							expiredTask.add(task);
							if (!task.isPermanent()) {
								e.remove();
							}
						}
					}
				}
				for (Iterator it = expiredTask.iterator(); it.hasNext(); ) {
					TimeoutTask task = (TimeoutTask) it.next();
					task.expired();
					if (task.isPermanent()) {
						task.restart();
					}
				}
				expiredTask.clear();
				Thread.sleep(1000);
			} catch (InterruptedException ignore) {
			}
		}
	}

	public TimeoutTask addTimeoutTarget(
		TimeoutTarget timeoutTarget,
		int timeout,
		boolean permanent) {

		TimeoutTask task = new TimeoutTask(timeoutTarget, timeout, permanent);
		synchronized (timeoutTaskList_) {
			timeoutTaskList_.addLast(task);
			timeoutTaskList_.notify();
		}
		return task;
	}

	public int getTimeoutTaskCount() {
		return timeoutTaskList_.size();
	}
}