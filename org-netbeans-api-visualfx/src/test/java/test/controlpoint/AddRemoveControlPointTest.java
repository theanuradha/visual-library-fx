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
package test.controlpoint;

import java.awt.Point;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;

import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class AddRemoveControlPointTest extends Scene {

	private LayerWidget mainLayer;

	public AddRemoveControlPointTest() {
		mainLayer = new LayerWidget(this);
		addChild(mainLayer);
		LayerWidget connLayer = new LayerWidget(this);
		addChild(connLayer);

		addLabel("Double-click on the connection to create a control point", 10, 30);
		addLabel("Drag a control point to move it", 10, 60);
		addLabel("Double-click on a control point to delete it", 10, 90);

		LabelWidget hello1 = addLabel("Hello", 100, 150);
		LabelWidget hello2 = addLabel("NetBeans", 300, 250);

		ConnectionWidget conn = new ConnectionWidget(this);
		conn.setPaintControlPoints(true);
		conn.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
		conn.setRouter(RouterFactory.createFreeRouter());
		conn.setSourceAnchor(AnchorFactory.createFreeRectangularAnchor(hello1, true));
		conn.setTargetAnchor(AnchorFactory.createFreeRectangularAnchor(hello2, true));
		connLayer.addChild(conn);

		conn.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
		conn.getActions().addAction(ActionFactory.createFreeMoveControlPointAction());
	}

	private LabelWidget addLabel(String text, int x, int y) {
		LabelWidget widget = new LabelWidget(this, text);

		widget.setFont(getDefaultFont().deriveFont(24.0f));
		widget.setOpaque(true);
		widget.setPreferredLocation(new Point(x, y));

		widget.getActions().addAction(ActionFactory.createMoveAction());

		mainLayer.addChild(widget);

		return widget;
	}

	public static void main(String[] args) {
		SceneSupport.show(new AddRemoveControlPointTest());
	}

}
