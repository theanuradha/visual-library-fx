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

import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class WeightFlowLayoutTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        Widget widget = new Widget (scene);
        widget.setBorder (BorderFactory.createResizeBorder (8, Color.BLACK, false));
        widget.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 10));
        widget.setPreferredLocation (new Point (50, 50));
        widget.setPreferredBounds (new Rectangle (200, 200));
        widget.getActions ().addAction (ActionFactory.createResizeAction ());
        layer.addChild (widget);

        createChild (widget, Color.RED, 10);
        createChild (widget, Color.GREEN, 20);
        createChild (widget, Color.YELLOW, 100).setVisible (false);
        createChild (widget, Color.BLUE, 30);
        createChild (widget, Color.BLACK, 40);

        SceneSupport.show (scene);
    }

    private static LabelWidget createChild (Widget parent, Color color, int weight) {
        LabelWidget child = new LabelWidget (parent.getScene (), "Weight: " + weight);
        child.setOpaque (true);
        child.setBackground (color);
        child.setForeground (Color.WHITE);
        parent.addChild (child, weight);
        return child;
    }

}
