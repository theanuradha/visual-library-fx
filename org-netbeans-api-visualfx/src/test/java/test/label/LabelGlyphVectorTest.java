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
package test.label;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class LabelGlyphVectorTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.getActions ().addAction (ActionFactory.createZoomAction (1.1, false));
        scene.getActions ().addAction (ActionFactory.createPanAction ());

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        createLabel (layer, 100, 100, "We are glyph-vector labels");
        createLabel (layer, 100, 150, "Zoom the scene in/out using Ctrl+MouseWheel");
        createLabel (layer, 100, 200, "The labels should be rendered correctly all the time (no clipping)");

        SceneSupport.show (scene);
    }

    private static void createLabel (LayerWidget layer, int x, int y, String label) {
        LabelWidget widget = new LabelWidget (layer.getScene (), label);
        widget.setOpaque (true);
        widget.setBackground (Color.GREEN);
        widget.setUseGlyphVector (true);
        widget.setAlignment (LabelWidget.Alignment.CENTER);
        widget.setVerticalAlignment (LabelWidget.VerticalAlignment.CENTER);
        widget.setPreferredLocation (new Point (x, y));
        layer.addChild (widget);
    }

}
