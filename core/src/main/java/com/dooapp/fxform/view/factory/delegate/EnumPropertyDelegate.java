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

package com.dooapp.fxform.view.factory.delegate;

import com.dooapp.fxform.controller.PropertyElementController;
import com.dooapp.fxform.reflection.Util;
import com.dooapp.fxform.view.NodeCreationException;
import com.dooapp.fxform.view.factory.DisposableNode;
import com.dooapp.fxform.view.factory.DisposableNodeWrapper;
import com.dooapp.fxform.view.factory.NodeFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * User: Antoine Mischler <antoine@dooapp.com>
 * Date: 17/04/11
 * Time: 00:19
 */
public class EnumPropertyDelegate implements NodeFactory<PropertyElementController<Enum>> {

    private final Logger logger = LoggerFactory.getLogger(EnumPropertyDelegate.class);

    public DisposableNode createNode(final PropertyElementController<Enum> controller) throws NodeCreationException {
        Enum[] constants = new Enum[0];
        try {
            constants = (Enum[]) controller.getElement().getValueType().getEnumConstants();
        } catch (Exception e) {
            logger.warn("Could not retrieve enum constants from controller " + controller, e);
        }
        final ChoiceBox<Enum> choiceBox = new ChoiceBox<Enum>();
        choiceBox.setItems(FXCollections.observableList(Arrays.asList(constants)));
        choiceBox.getSelectionModel().select(controller.getElement().getValue());
        final ChangeListener<Enum> enumChangeListener = new ChangeListener<Enum>() {

            public void changed(ObservableValue<? extends Enum> observableValue, Enum anEnum, Enum anEnum1) {
                controller.getElement().setValue(anEnum1);
            }
        };
        choiceBox.getSelectionModel().selectedItemProperty().addListener(enumChangeListener);
        final ChangeListener controllerListener = new ChangeListener() {
            public void changed(ObservableValue observableValue, Object o, Object o1) {
                if (o1 != null) {
                    choiceBox.getSelectionModel().select((Enum) o1);
                } else {
                    choiceBox.getSelectionModel().clearSelection();
                }
            }
        };
        controller.getElement().addListener(controllerListener);
        return new DisposableNodeWrapper(choiceBox, new Callback<Node, Void>() {
            public Void call(Node node) {
                choiceBox.getSelectionModel().selectedItemProperty().removeListener(enumChangeListener);
                controller.getElement().removeListener(controllerListener);
                return null;
            }
        });
    }
}
