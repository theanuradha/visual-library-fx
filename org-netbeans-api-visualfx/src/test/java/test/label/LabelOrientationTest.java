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

import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class LabelOrientationTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        LayerWidget layer = new LayerWidget (scene);
        scene.addChild(layer);

        createLabel (layer, "N O R M A L", 100, 100, LabelWidget.Orientation.NORMAL, LabelWidget.Alignment.LEFT, LabelWidget.VerticalAlignment.BASELINE).setPreferredBounds (null);
        createLabel (layer, "R O T A T E 9 0", 100, 100, LabelWidget.Orientation.ROTATE_90, LabelWidget.Alignment.LEFT, LabelWidget.VerticalAlignment.BASELINE).setPreferredBounds (null);

        createLabel (layer, "NORMAL BASELINE", 200, 100, LabelWidget.Orientation.NORMAL, LabelWidget.Alignment.BASELINE, LabelWidget.VerticalAlignment.BASELINE);
        createLabel (layer, "ROTATE90 BASELINE", 200, 300, LabelWidget.Orientation.ROTATE_90, LabelWidget.Alignment.BASELINE, LabelWidget.VerticalAlignment.BASELINE);

        createLabel (layer, "NORMAL LEFT,TOP", 400, 100, LabelWidget.Orientation.NORMAL, LabelWidget.Alignment.LEFT, LabelWidget.VerticalAlignment.TOP);
        createLabel (layer, "ROTATE90 LEFT,TOP", 400, 300, LabelWidget.Orientation.ROTATE_90, LabelWidget.Alignment.LEFT, LabelWidget.VerticalAlignment.TOP);

        createLabel (layer, "NORMAL CENTER", 600, 100, LabelWidget.Orientation.NORMAL, LabelWidget.Alignment.CENTER, LabelWidget.VerticalAlignment.CENTER);
        createLabel (layer, "ROTATE90 CENTER", 600, 300, LabelWidget.Orientation.ROTATE_90, LabelWidget.Alignment.CENTER, LabelWidget.VerticalAlignment.CENTER);

        createLabel (layer, "NORMAL RIGHT,BOTTOM", 800, 100, LabelWidget.Orientation.NORMAL, LabelWidget.Alignment.RIGHT, LabelWidget.VerticalAlignment.BOTTOM);
        createLabel (layer, "ROTATE90 RIGHT,BOTTOM", 800, 300, LabelWidget.Orientation.ROTATE_90, LabelWidget.Alignment.RIGHT, LabelWidget.VerticalAlignment.BOTTOM);

        SceneSupport.show (scene);
    }

    private static LabelWidget createLabel (LayerWidget layer, String label, int x, int y, LabelWidget.Orientation orientation, LabelWidget.Alignment alignment, LabelWidget.VerticalAlignment verticalAlignment) {
        LabelWidget widget = new LabelWidget (layer.getScene (), label);
        widget.setOpaque (true);
        widget.setBackground (Color.YELLOW);
        widget.setPreferredLocation (new Point (x, y));
        widget.setPreferredBounds (new Rectangle (-20, -30, 180, 180));
        widget.setOrientation (orientation);
        widget.setAlignment (alignment);
        widget.setVerticalAlignment (verticalAlignment);
        layer.addChild (widget);
        return widget;
    }

}
