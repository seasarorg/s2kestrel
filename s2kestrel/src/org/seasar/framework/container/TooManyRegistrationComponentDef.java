package org.seasar.framework.container;


/**
 * 1�̃L�[�ɕ����̃R���|�[�l���g���o�^���ꂽ�ꍇ�Ɏg�p����܂��B
 * 
 * @author koichik
 */
public interface TooManyRegistrationComponentDef extends ComponentDef {
    void addComponentDef(ComponentDef cd);
    Class[] getComponentClasses();
}
