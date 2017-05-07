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
package test.layout;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class FlowLayoutTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        createParent (layer, LayoutFactory.SerialAlignment.JUSTIFY, 50, 50);
        createParent (layer, LayoutFactory.SerialAlignment.CENTER, 300, 50);
        createParent (layer, LayoutFactory.SerialAlignment.LEFT_TOP, 50, 300);
        createParent (layer, LayoutFactory.SerialAlignment.RIGHT_BOTTOM, 300, 300);

        SceneSupport.show (scene);
    }

    private static void createParent (LayerWidget layer, LayoutFactory.SerialAlignment align, int x, int y) {
        Widget widget = new Widget (layer.getScene ());
        widget.setBorder (BorderFactory.createResizeBorder (8, Color.BLACK, false));
        widget.setLayout (LayoutFactory.createVerticalFlowLayout (align, 0));
        widget.setPreferredLocation (new Point (x, y));
        widget.setPreferredBounds (new Rectangle (200, 200));
        widget.getActions ().addAction (ActionFactory.createResizeAction ());
        widget.getActions ().addAction (ActionFactory.createMoveAction());
        layer.addChild (widget);

        createChild (widget, Color.RED);
        createChild (widget, Color.GREEN);
        createChild (widget, Color.BLUE);
        createChild (widget, Color.BLACK);
    }

    private static void createChild (Widget parent, Color color) {
        LabelWidget child = new LabelWidget (parent.getScene (), "Color: " + Integer.toHexString (color.getRGB ()));
        child.setOpaque (true);
        child.setForeground (Color.WHITE);
        child.setBackground (color);
        parent.addChild (child);
    }

}
