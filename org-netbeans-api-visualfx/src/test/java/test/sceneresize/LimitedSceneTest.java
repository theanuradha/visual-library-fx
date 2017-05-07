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
package test.sceneresize;

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class LimitedSceneTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.setMaximumBounds (new Rectangle (0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE));
        scene.getActions ().addAction (ActionFactory.createZoomAction ());
        scene.getActions ().addAction (ActionFactory.createPanAction ());

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        LayerWidget connLayer = new LayerWidget (scene);
        scene.addChild (connLayer);

        LabelWidget source = createLabel (mainLayer, "Source - scene is limited", 50, -20, Color.GREEN);
        LabelWidget target = createLabel (mainLayer, "Target - scene does not expand to negative values", 550, 0, Color.GREEN);
        createLabel (mainLayer, "Line must be routed around bottom side", 250, -20, Color.RED);

        ConnectionWidget conn = new ConnectionWidget (scene);
        conn.setSourceAnchor (AnchorFactory.createRectangularAnchor (source));
        conn.setTargetAnchor (AnchorFactory.createRectangularAnchor (target));
        conn.setSourceAnchorShape (AnchorShape.TRIANGLE_HOLLOW);
        conn.setTargetAnchorShape (AnchorShape.TRIANGLE_HOLLOW);
        conn.setRouter (RouterFactory.createOrthogonalSearchRouter (mainLayer, connLayer));
        connLayer.addChild (conn);

        SceneSupport.show (scene);
    }

    private static LabelWidget createLabel (LayerWidget mainLayer, String label, int x, int y, Color color) {
        LabelWidget widget = new LabelWidget (mainLayer.getScene (), label);
        widget.setOpaque (true);
        widget.setBackground (color);
        widget.setBorder (BorderFactory.createLineBorder (1));
        widget.setVerticalAlignment (LabelWidget.VerticalAlignment.CENTER);
        widget.setPreferredLocation (new Point (x, y));
        widget.setMinimumSize (new Dimension (0, 100));
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (widget);
        return widget;
    }

}
