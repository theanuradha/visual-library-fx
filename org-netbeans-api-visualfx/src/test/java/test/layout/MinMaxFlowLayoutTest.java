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

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class MinMaxFlowLayoutTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        Widget w1 = new Widget (scene);
        w1.setBorder (BorderFactory.createLineBorder (1, Color.RED));
        w1.setLayout (LayoutFactory.createVerticalFlowLayout ());
//        w1.setPreferredSize (new Dimension (100, 100)); // uncommenting this forces the size and therefore the outer widget is smaller than widgets inside
        scene.addChild (w1);

        LabelWidget w2 = new LabelWidget (scene, "Double-click me to toggle visibility of widget below");
        w2.setBorder (BorderFactory.createLineBorder (1, Color.GREEN));
        w1.addChild (w2);

        final LabelWidget w3 = new LabelWidget (scene, "This is a big label with predefined minimal size");
        w3.setBorder (BorderFactory.createLineBorder (1, Color.BLUE));
        w3.setMinimumSize (new Dimension (400, 100));
        w1.addChild (w3);

        w2.getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                w3.setVisible (! w3.isVisible ());
            }
        }));

        SceneSupport.show (scene);
    }

}
