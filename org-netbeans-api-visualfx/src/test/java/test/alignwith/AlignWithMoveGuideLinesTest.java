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
package test.alignwith;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class AlignWithMoveGuideLinesTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget main = new LayerWidget (scene);
        scene.addChild (main);

        LayerWidget inter = new LayerWidget (scene);
        scene.addChild (inter);

        createWidget (main, "Alignment 1", 400, 0, 100, 100);
        createWidget (main, "Alignment 2", 0, 400, 100, 100);
        LabelWidget widget = createWidget (main, "Move this to top-left corner and align it", 10, 10, 200, 100);
        widget.getActions ().addAction (ActionFactory.createAlignWithMoveAction (main, inter, null));

        SceneSupport.show (scene);
    }

    private static LabelWidget createWidget (LayerWidget main, String label, int x, int y, int width, int height) {
        LabelWidget widget = new LabelWidget (main.getScene (), label);
        widget.setBorder (BorderFactory.createResizeBorder (8));
        widget.setPreferredLocation (new Point (x, y));
        widget.setPreferredSize (new Dimension (width, height));
        main.addChild (widget);
        return widget;
    }

}
