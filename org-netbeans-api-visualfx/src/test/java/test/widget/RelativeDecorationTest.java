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
package test.widget;

import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import test.general.UMLClassWidget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class RelativeDecorationTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        Widget widget = new Widget (scene);
        widget.setPreferredLocation (new Point (100, 100));
        widget.setBorder (BorderFactory.createLineBorder (1, Color.RED));
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (widget);

//        widget.setLayout(LayoutFactory.createOverlayLayout()); // HINT - you have to use AbsoluteLayout

        LayerWidget childLayer = new LayerWidget(scene); // HINT - or you can remove the childLayer and use umlClassWidget directly
        childLayer.setLayout(LayoutFactory.createOverlayLayout());
        widget.addChild(childLayer);

        LayerWidget decoratorLayer = new LayerWidget (scene);
        decoratorLayer.setLayout(LayoutFactory.createAbsoluteLayout());
        widget.addChild(decoratorLayer);

        UMLClassWidget uml = new UMLClassWidget (scene);
        uml.setClassName ("ClassName");
        uml.addMember (uml.createMember ("Member 1"));
        uml.addOperation (uml.createOperation ("Operation 1"));
        childLayer.addChild (uml);

        Widget decorator = new Widget(scene);
        decorator.setPreferredBounds(new Rectangle (-8, -8, 16, 16));
        decorator.setPreferredLocation(new Point(0, 0));
        decorator.setBackground(java.awt.Color.DARK_GRAY);
        decorator.setOpaque(true);
        decoratorLayer.addChild(decorator);

        SceneSupport.show (scene);
    }

}
