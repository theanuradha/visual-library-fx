/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 */
package test.action;

import java.awt.Point;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.model.ObjectScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;

import javafx.scene.control.Alert;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class SelectLockedActionTest {

	public static void main(String[] args) {
		final ObjectScene scene = new ObjectScene();
		LayerWidget layer = new LayerWidget(scene);
		scene.addChild(layer);
		scene.getActions().addAction(ActionFactory.createSelectAction(new SelectProvider() {

			public boolean isAimingAllowed(Widget widget, Point localLocation, boolean invertSelection) {
				return true; // HINT - this has to be true for correct
								// cooperation with another locking action (now
								// rectangular select action)
			}

			public boolean isSelectionAllowed(Widget widget, Point localLocation, boolean invertSelection) {
				return true;
			}

			public void select(Widget widget, Point localLocation, boolean invertSelection) {
				System.out.println("This message has to be printed each time you click on the scene");
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("info");
				alert.setContentText("This dialog has to be shown each time you click on the scene");
				alert.showAndWait();
			}
		}));
		scene.getActions().addAction(ActionFactory.createRectangularSelectAction(scene, layer));
		SceneSupport.show(scene);
	}

}
