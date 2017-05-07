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

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.LabelWidget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class OverlayLayoutWidgetTest extends Widget {

    public OverlayLayoutWidgetTest (Scene scene) {
        super(scene);

        setOpaque (true);
        setBackground (Color.RED);
        setBorder (BorderFactory.createResizeBorder (8));

        setLayout (LayoutFactory.createOverlayLayout ());

        getActions ().addAction (ActionFactory.createResizeAction ());
        getActions ().addAction (ActionFactory.createMoveAction ());

        LabelWidget innerWidget = new LabelWidget (scene, "Everything has to be symetric");
        innerWidget.setAlignment (LabelWidget.Alignment.CENTER);
        innerWidget.setVerticalAlignment (LabelWidget.VerticalAlignment.CENTER);
        innerWidget.setOpaque (true);
        innerWidget.setBorder (BorderFactory.createResizeBorder (4));
        innerWidget.setBackground (Color.GREEN);
        addChild(innerWidget);
    }

    public static void main (String[] args) {
        Scene scene = new Scene ();
        OverlayLayoutWidgetTest widget = new OverlayLayoutWidgetTest (scene);
        widget.setPreferredBounds (new Rectangle (-50, -50, 300, 100));
        scene.addChild (widget);
        SceneSupport.show (scene);
    }

}
