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
import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import javafx.scene.control.ContextMenu;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class PopupMenuActionTest {

	public static void main(String[] args) {
		Scene scene = new Scene();
		scene.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
		LayerWidget layer = new LayerWidget(scene);
		scene.addChild(layer);

		LabelWidget label = new LabelWidget(scene,
				"Invoke Popup Menu using ContextMenu or Shift+F10 key or right-click");
		label.setPreferredLocation(new Point(100, 100));
		label.getActions().addAction(ActionFactory.createPopupMenuAction(new PopupMenuProvider() {
			public ContextMenu getPopupMenu(Widget widget, Point localLocation) {
				ContextMenu popup = new ContextMenu();
				popup.getItems().add(new javafx.scene.control.MenuItem("Menu Item 1"));
				return popup;
			}
		}));
		layer.addChild(label);
		scene.setFocusedWidget(label);

		SceneSupport.show(scene);
	}

}
