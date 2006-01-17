package org.seasar.extension.jta.xa;

import java.io.Serializable;

import javax.transaction.xa.Xid;

public class XidImpl implements Xid, Serializable {

	static final long serialVersionUID = 1L;
	private static final int FORMAT_ID = 0x1108;
	private static final byte[] INITIAL_BRANCH_ID = convert64bytes(new byte[0]);
	private static final String GLOBAL_ID_BASE =
		System.currentTimeMillis() + "/";
	private static int nextId_ = 0;

	private int hashCode_;
	private byte[] globalId_;
	private byte[] branchId_;

	public XidImpl() {
		hashCode_ = getNextId();
		globalId_ = createGlobalId();
		branchId_ = INITIAL_BRANCH_ID;
	}

	public XidImpl(Xid xid, int branchId) {
		hashCode_ = xid.hashCode();
		globalId_ = xid.getGlobalTransactionId();
		branchId_ = convert64bytes(Integer.toString(branchId).getBytes());
	}

	private byte[] createGlobalId() {
		return convert64bytes(
			(GLOBAL_ID_BASE + Integer.toString(hashCode_)).getBytes());
	}

	public byte[] getGlobalTransactionId() {
		return (byte[]) globalId_.clone();
	}

	public byte[] getBranchQualifier() {
		return (byte[]) branchId_.clone();
	}

	public int getFormatId() {
		return FORMAT_ID;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !(obj instanceof XidImpl)) {
			return false;
		}
		XidImpl other = (XidImpl) obj;
		if (hashCode_ != other.hashCode_) {
			return false;
		}
		if (globalId_.length != other.globalId_.length
			|| branchId_.length != other.branchId_.length) {

			return false;
		}
		for (int i = 0; i < globalId_.length; ++i) {
			if (globalId_[i] != other.globalId_[i]) {
				return false;
			}
		}
		for (int i = 0; i < branchId_.length; ++i) {
			if (branchId_[i] != other.branchId_[i]) {
				return false;
			}
		}
		return true;
	}

	public int hashCode() {
		return hashCode_;
	}

	public String toString() {
		return "[FormatId="
			+ FORMAT_ID
			+ ", GlobalId="
			+ new String(globalId_).trim()
			+ ", BranchId="
			+ new String(branchId_).trim()
			+ "]";
	}

	private static byte[] convert64bytes(byte[] bytes) {
		byte[] new64bytes = new byte[64];
		System.arraycopy(bytes, 0, new64bytes, 0, bytes.length);
		return new64bytes;
	}

	private static synchronized int getNextId() {
		return nextId_++;
	}
}