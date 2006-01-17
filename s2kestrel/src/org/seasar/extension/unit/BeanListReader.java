package org.seasar.extension.unit;

import java.util.List;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

/**
 * @author higa
 *  
 */
public class BeanListReader extends BeanReader {

	/**
	 * @param map
	 */
	public BeanListReader(List list) {
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(list.get(0).getClass());
		setupColumns(beanDesc);
		for (int i = 0; i < list.size(); ++i) {
			setupRow(beanDesc, list.get(i));
		}
	}

}