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

package org.jboss.invocation.proxy;

import java.lang.reflect.Constructor;

import org.jboss.classfilewriter.ClassMethod;
import org.jboss.classfilewriter.code.CodeAttribute;

/**
 * Constructor override that simply delegates to {@code super()}.
 * 
 * @author Stuart Douglas
 * 
 */
public class DefaultConstructorBodyCreator implements ConstructorBodyCreator {

    /**
     * The singleton instance.
     */
    public static final DefaultConstructorBodyCreator INSTANCE = new DefaultConstructorBodyCreator();

    private DefaultConstructorBodyCreator() {

    }

    /** {@inheritDoc} */
    @Override
    public void overrideConstructor(ClassMethod method, Constructor<?> constructor) {
        CodeAttribute ca = method.getCodeAttribute();
        ca.aload(0);
        ca.loadMethodParameters();
        ca.invokespecial(constructor);
        ca.returnInstruction();
    }

}
