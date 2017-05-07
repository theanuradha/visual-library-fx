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
package test.resize;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ResizeTest extends Scene {

    private LayerWidget layer;
    private WidgetAction resizeAction;
    private WidgetAction moveAction;

    public ResizeTest () {
        setBackground (Color.LIGHT_GRAY);

        layer = new LayerWidget (this);
        addChild (layer);

        resizeAction = ActionFactory.createResizeAction ();
        moveAction = ActionFactory.createMoveAction ();

        createLabel (100, 100).setBorder (BorderFactory.createResizeBorder (5));
        createLabel (200, 200).setBorder (BorderFactory.createResizeBorder (8, Color.BLACK, true));
        createLabel (300, 300).setBorder (BorderFactory.createBevelBorder (true));
        createLabel (400, 400).setBorder (BorderFactory.createImageBorder (new Insets (5, 5, 5, 5), Utilities.loadImage ("test/resources/shadow_normal.png"))); // NOI18N
    }

    public LabelWidget createLabel (int x, int y) {
        LabelWidget label = new LabelWidget (this, "Drag border to resize me. Drag inner area to move me.");
        label.setOpaque (true);
        label.setBackground (Color.WHITE);
        label.setCheckClipping (true);
        label.setAlignment (LabelWidget.Alignment.CENTER);
        label.setVerticalAlignment (LabelWidget.VerticalAlignment.CENTER);
        label.setPreferredLocation (new Point (x, y));
        label.getActions ().addAction (resizeAction);
        label.getActions ().addAction (moveAction);
        layer.addChild (label);
        return label;
    }

    public static void main (String[] args) {
        SceneSupport.show (new ResizeTest ());
    }

}
