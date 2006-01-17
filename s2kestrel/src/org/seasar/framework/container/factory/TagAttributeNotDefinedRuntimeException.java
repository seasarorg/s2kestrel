package org.seasar.framework.container.factory;

import org.seasar.framework.exception.SRuntimeException;

/**
 * @author higa
 *
 * �^�O�̑���������Ă��Ȃ��ꍇ�̎��s����O�ł��B
 */
public final class TagAttributeNotDefinedRuntimeException extends SRuntimeException {

	private String tagName_;
	private String attributeName_;
	
	public TagAttributeNotDefinedRuntimeException(String tagName,
		String attributeName) {
			
		super("ESSR0056", new Object[]{tagName, attributeName});
		tagName_ = tagName;
		attributeName_ = attributeName;
	}
	
	public String getTagName() {
		return tagName_;
	}
	
	public String getAttributeName() {
		return attributeName_;
	}
}
