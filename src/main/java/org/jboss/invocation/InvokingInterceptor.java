/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.invocation;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.jboss.invocation.InvocationMessages.msg;

/**
 * @author <a href="mailto:david.lloyd@redhat.com">David M. Lloyd</a>
 */
class InvokingInterceptor implements Interceptor, Serializable {

    static final Interceptor INSTANCE = new InvokingInterceptor();
    static final InterceptorFactory FACTORY = new ImmediateInterceptorFactory(INSTANCE);

    private static final long serialVersionUID = 175221411434392097L;

    public Object processInvocation(final InterceptorContext context) throws Exception {
        final Method method = context.getMethod();
        if (method == null) {
            return null;
        }
        try {
            Object target = context.getTarget();
            if (target == null) {
                throw msg.nullProperty("target", context);
            }
            return method.invoke(target, context.getParameters());
        } catch (IllegalAccessException e) {
            final IllegalAccessError n = new IllegalAccessError(e.getMessage());
            n.setStackTrace(e.getStackTrace());
            throw n;
        } catch (InvocationTargetException e) {
            throw Interceptors.rethrow(e.getCause());
        }
    }

    protected Object readResolve() {
        return INSTANCE;
    }

    public String toString() {
        return "invoking interceptor";
    }
}
