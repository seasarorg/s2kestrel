package org.seasar.extension.j2ee;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

public final class JndiContextFactory implements InitialContextFactory {

    public Context getInitialContext(Hashtable env) throws NamingException {
        return new JndiContext(env);
    }
}
