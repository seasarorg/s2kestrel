package org.seasar.framework.aop;

/**
 * @author higa
 *
 * �v���O�������̂ǂ��ɓ���̃R�[�h(Advice)��}������̂����`���܂��B<br />
 */
public interface Pointcut {
	
	/**
	 * ���̃��\�b�h��true��Ԃ������\�b�h�ɑ΂���Advice���K�p����܂��B
	 * @param methodName
	 * @return Advice���K�p����邩�ǂ�����Ԃ��܂��B
	 */
	public boolean isApplied(String methodName);
}
