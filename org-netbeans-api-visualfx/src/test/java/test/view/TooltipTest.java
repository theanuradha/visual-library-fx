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
package test.view;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class TooltipTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        LabelWidget label = new LabelWidget (scene, "Press Ctrl+F1 to show a tool-tip of the label (the focused widget of the scene)");
        label.setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
        label.setToolTipText ("This is the tool-tip");
        label.setPreferredLocation (new Point (100, 100));
        layer.addChild (label);

        scene.setFocusedWidget (label);

        SceneSupport.show (scene);
    }

}
