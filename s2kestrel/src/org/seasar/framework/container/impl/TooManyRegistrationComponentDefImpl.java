package org.seasar.framework.container.impl;

import java.util.ArrayList;
import java.util.List;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.TooManyRegistrationComponentDef;
import org.seasar.framework.container.TooManyRegistrationRuntimeException;

/**
 * @author higa
 * 
 * 1つのキーに複数のコンポーネントが登録された場合に使用されます。
 */
public class TooManyRegistrationComponentDefImpl extends SimpleComponentDef implements
        TooManyRegistrationComponentDef {
    private Object key_;
    private List componentDefs_ = new ArrayList();

    public TooManyRegistrationComponentDefImpl(Object key) {
        key_ = key;
    }

    public void addComponentDef(ComponentDef cd) {
        componentDefs_.add(cd);
    }

    public Object getComponent() throws TooManyRegistrationRuntimeException {
        throw new TooManyRegistrationRuntimeException(key_, getComponentClasses());
    }

    public int getComponentDefSize() {
        return componentDefs_.size();
    }

    public ComponentDef getComponentDef(int index) {
        return (ComponentDef) componentDefs_.get(index);
    }

    public ComponentDef[] getComponentDefs() {
        return (ComponentDef[]) componentDefs_.toArray(new ComponentDef[getComponentDefSize()]);
    }

    public Class[] getComponentClasses() {
        Class[] classes = new Class[getComponentDefSize()];
        for (int i = 0; i < classes.length; ++i) {
            classes[i] = getComponentDef(i).getComponentClass();
        }
        return classes;
    }
}
