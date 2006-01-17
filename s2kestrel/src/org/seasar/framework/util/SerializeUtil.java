package org.seasar.framework.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.seasar.framework.exception.ClassNotFoundRuntimeException;
import org.seasar.framework.exception.IORuntimeException;

public class SerializeUtil {

    private SerializeUtil() { }

	/**
	 * @param o
	 * @return シリアライズされたオブジェクトを返します。
	 * @throws IORuntimeException
	 * @throws ClassNotFoundRuntimeException
	 */
	public static Object serialize(final Object o)
    	throws IORuntimeException, ClassNotFoundRuntimeException {
    		
        try {
            ByteArrayOutputStream baos =
                    new ByteArrayOutputStream();
            ObjectOutputStream oos =
                    new ObjectOutputStream(baos);
            oos.writeObject(o);
            ByteArrayInputStream bais =
                    new ByteArrayInputStream(
                    baos.toByteArray());
            ObjectInputStream ois =
                    new ObjectInputStream(bais);
            return ois.readObject();
        } catch (IOException ex) {
            throw new IORuntimeException(ex);
        } catch (ClassNotFoundException ex) {
			throw new ClassNotFoundRuntimeException(ex);
        }
    }
}
