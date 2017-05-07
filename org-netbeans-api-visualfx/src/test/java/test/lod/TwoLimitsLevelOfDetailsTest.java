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
package test.lod;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LevelOfDetailsWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class TwoLimitsLevelOfDetailsTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.setZoomFactor(1);
        scene.getActions().addAction(ActionFactory.createZoomAction (1.1, false));

        Widget root = new LevelOfDetailsWidget (scene, 0.5, 0.99, 1.01, 2);
        root.setBorder (BorderFactory.createLineBorder (10));
        root.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 4));
        scene.addChild (root);

        root.addChild (createLabel (scene, "Zoom-in or -out to see the visibility limits", 20)); // NOI18N

        SceneSupport.show (scene);
    }

    private static Widget createLabel (Scene scene, String text, int size) {
        LabelWidget label = new LabelWidget (scene, text);
        label.setFont(scene.getDefaultFont().deriveFont((float) size));
        return label;
    }

}