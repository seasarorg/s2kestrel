package org.seasar.extension.mock.servlet;

import javax.servlet.http.HttpSession;

import org.seasar.framework.util.ClassUtil;
/**
 * @author Satoshi Kimura
 */
public interface MockHttpSession extends HttpSession {
    String METHOD_NAME_IS_VALID = ClassUtil.getMethod(MockHttpSession.class, "isValid", null).getName();
    String METHOD_NAME_SET_VALID = ClassUtil.getMethod(MockHttpSession.class, "setValid", new Class[]{Boolean.TYPE})
            .getName();

    /**
     * {@link MockHttpSession#invalidate()}���Ă΂ꂽ���m�F���܂��B
     * 
     * @return �Ă΂ꂽ�ꍇ�Ftrue�A�Ă΂�Ă��Ȃ��ꍇ�Ffalse
     */
    boolean isValid();

    /**
     * {@link MockHttpSession#invalidate()}���Ă΂ꂽ�Ƃ��ɁA�����I�ɁA���̃��\�b�h���Ăт܂��B
     * 
     * @param valid {@link MockHttpSession#invalidate()}���Ă΂ꂽ�Ƃ��ɁAtrue
     */
    void setValid(boolean valid);
    
    void access();
}