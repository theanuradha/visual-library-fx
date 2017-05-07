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

package javaone.demo3;

import java.awt.Point;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Utilities;

import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class ConnectionDemo {

	public static void main(String[] args) {
		Scene scene = new Scene();

		LayerWidget mainLayer = new LayerWidget(scene);
		scene.addChild(mainLayer);

		ImageWidget first = new ImageWidget(scene);
		first.setImage(Utilities.loadImage("javaone/resources/a.png"));
		first.setPreferredLocation(new Point(100, 100));
		first.getActions().addAction(ActionFactory.createMoveAction());
		mainLayer.addChild(first);

		ImageWidget second = new ImageWidget(scene);
		second.setImage(Utilities.loadImage("javaone/resources/b.png"));
		second.setPreferredLocation(new Point(300, 200));
		second.getActions().addAction(ActionFactory.createMoveAction());
		mainLayer.addChild(second);

		LayerWidget connectionLayer = new LayerWidget(scene);
		scene.addChild(connectionLayer);

		ConnectionWidget connection = new ConnectionWidget(scene);
		connection.setSourceAnchor(AnchorFactory.createCircularAnchor(first, 32));
		connection.setTargetAnchor(AnchorFactory.createCircularAnchor(second, 32));
		connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		connectionLayer.addChild(connection);

		SceneSupport.show(scene);
	}

}
