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
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class CardLayoutWithLabelTest {

    public static void main (String[] args) {
        final Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        Widget nodeWidget = new Widget (scene);
        nodeWidget.setBorder (BorderFactory.createLineBorder (1, Color.RED));
        nodeWidget.setPreferredLocation (new Point (100, 100));
        layer.addChild (nodeWidget);

        final Widget deferredWidget = new Widget (scene);
        deferredWidget.setLayout (LayoutFactory.createCardLayout (deferredWidget));
        deferredWidget.setBorder (BorderFactory.createLineBorder (1, Color.BLUE));
        nodeWidget.addChild (deferredWidget);

        final Widget label = new LabelWidget (scene, "Click me to add ComponentWidget");
        label.setBorder (BorderFactory.createLineBorder (1, Color.GREEN));
        deferredWidget.addChild (label);
        LayoutFactory.setActiveCard (deferredWidget, label);

        label.getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                ComponentWidget component = new ComponentWidget (scene, new JButton ("This is the new ComponentWidget"));
                component.setBorder (BorderFactory.createLineBorder (1, Color.GREEN));
                deferredWidget.addChild (component);
                LayoutFactory.setActiveCard (deferredWidget, component);
            }
        }));

        scene.getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                LayoutFactory.setActiveCard (deferredWidget, label);
            }
        }));

        // to force the boundary
//        nodeWidget.setPreferredBounds (new Rectangle (0, 0, 70, 30));
//        nodeWidget.setPreferredSize (new Dimension (70, 30));
        nodeWidget.setLayout (LayoutFactory.createOverlayLayout ());
        nodeWidget.setCheckClipping (true);
        //
        
        SceneSupport.show (scene);
    }

}
