/*
 * Copyright (c) 2011, dooApp <contact@dooapp.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of dooApp nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.dooapp.fxform.view.factory;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.util.Callback;

import com.dooapp.fxform.model.ElementController;
import com.dooapp.fxform.view.NodeCreationException;

/**
 * User: Antoine Mischler <antoine@dooapp.com> Date: 25/08/11 Time: 18:45
 */
public class DefaultLabelFactory implements NodeFactory<ElementController> {

	public DisposableNode createNode(ElementController controller) throws NodeCreationException {
		Label label = new Label();
		label.setMinWidth(Label.USE_PREF_SIZE);
        label.setMaxWidth(Label.USE_PREF_SIZE);
		label.textProperty().bind(controller.getLabel());
		return new DisposableNodeWrapper(label, new Callback<Node, Void>() {
			public Void call(Node node) {
				return null;
			}
		});
	}
}