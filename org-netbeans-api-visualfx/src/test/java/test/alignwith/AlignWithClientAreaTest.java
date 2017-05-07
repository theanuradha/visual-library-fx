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
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class AlignWithClientAreaTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);
        LayerWidget interractionLayer = new LayerWidget (scene);
        scene.addChild (interractionLayer);

        Border resizeBorder = BorderFactory.createResizeBorder (8);
        WidgetAction moveAction = ActionFactory.createAlignWithMoveAction (mainLayer, interractionLayer, null, false);
        WidgetAction resizeAction = ActionFactory.createAlignWithResizeAction (mainLayer, interractionLayer, null, false);

        createWidget (mainLayer, 100, 100, "Label 1 - Move, Resize", resizeBorder, resizeAction, moveAction);
        createWidget (mainLayer, 200, 150, "Label 2 - Move, Resize", resizeBorder, resizeAction, moveAction);
        createWidget (mainLayer, 100, 200, "Label 3 - Move, Resize", resizeBorder, resizeAction, moveAction);

        SceneSupport.show (scene);
    }


    private static void createWidget (LayerWidget mainLayer, int x, int y, String label, Border resizeBorder, WidgetAction resizeAction, WidgetAction moveAction) {
        LabelWidget widget = new LabelWidget (mainLayer.getScene (), label);
        widget.setAlignment (LabelWidget.Alignment.CENTER);
        widget.setVerticalAlignment (LabelWidget.VerticalAlignment.CENTER);
        widget.setPreferredLocation (new Point (x, y));
        widget.setOpaque (true);
        widget.setBackground (Color.YELLOW);
        widget.setBorder (resizeBorder);
        widget.getActions ().addAction (resizeAction);
        widget.getActions ().addAction (moveAction);
        mainLayer.addChild (widget);
    }

}
