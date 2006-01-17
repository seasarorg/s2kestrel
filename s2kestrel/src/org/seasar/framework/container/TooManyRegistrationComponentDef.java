package org.seasar.framework.container;


/**
 * 1つのキーに複数のコンポーネントが登録された場合に使用されます。
 * 
 * @author koichik
 */
public interface TooManyRegistrationComponentDef extends ComponentDef {
    void addComponentDef(ComponentDef cd);
    Class[] getComponentClasses();
}
