/*
 * Copyright (c) 2011, dooApp <contact@dooapp.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of dooApp nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dooapp.fxform.model.impl;

import com.dooapp.fxform.annotation.NonVisual;
import com.dooapp.fxform.model.FieldProvider;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

/**
 * This default implementations retrieves all fields of the given source object, including inherited fields.
 * <p/>
 * User: Antoine Mischler
 * Date: 09/04/11
 * Time: 22:31
 */
public class ReflectionFieldProvider implements FieldProvider {

    public List<Field> getProperties(Object source) {
        List<Field> result = new LinkedList<Field>();
        if (source != null) {
            Class clazz = source.getClass();
            fillFields(clazz, result);
        }
        return result;
    }

    private void fillFields(Class clazz, List<Field> result) {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(NonVisual.class) == null) {
                result.add(field);
            }
        }

        for (Field field : result) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
        }
        if (clazz.getSuperclass() != null && clazz.getSuperclass() != Object.class) {
            fillFields(clazz.getSuperclass(), result);
        }
    }
}

