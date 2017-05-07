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

package javaone.demo6;

import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LevelOfDetailsWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * @author David Kaspar
 */
public class LODDemoWidget extends LevelOfDetailsWidget {

    private static final double ZOOM_MULT = 2.0;

    /** Creates a new instance of LODDemoWidget */
    public LODDemoWidget(Scene scene, int level, double zoom) {
        super (scene, zoom, zoom * ZOOM_MULT, Double.MAX_VALUE, Double.MAX_VALUE);

        setBorder (BorderFactory.createLineBorder (2));

        if (level > 1) {
            Widget vbox = new Widget (scene);
            vbox.setLayout(LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 1));
            addChild (vbox);

            Widget hbox1 = new Widget (scene);
            hbox1.setLayout(LayoutFactory.createHorizontalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 1));
            vbox.addChild(hbox1);

            Widget hbox2 = new Widget (scene);
            hbox2.setLayout(LayoutFactory.createHorizontalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 1));
            vbox.addChild(hbox2);

            hbox1.addChild(new LODDemoWidget (scene, level - 1, zoom * ZOOM_MULT));
            hbox1.addChild(new LODDemoWidget (scene, level - 1, zoom * ZOOM_MULT));

            hbox2.addChild(new LODDemoWidget (scene, level - 1, zoom * ZOOM_MULT));
            hbox2.addChild(new LODDemoWidget (scene, level - 1, zoom * ZOOM_MULT));
        } else {
            LabelWidget label = new LabelWidget (scene, "Item");
            label.setFont(scene.getDefaultFont().deriveFont (8.0f));
            addChild (label);
        }

    }

}
