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
package test.widget;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class IconNodeHeaderTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        LayerWidget theWidgetWithHeader = new LayerWidget (scene);
        theWidgetWithHeader.setPreferredLocation (new Point (100, 100)); // the reference point of the whole structure
        layer.addChild (theWidgetWithHeader);

        final LabelWidget header = new LabelWidget (scene, "Visibility of this label should not affect the position of the top-left corner of the image in the scene.");
        header.setPreferredLocation (new Point (0, 0)); // the location of the header relatively to the reference point 
        theWidgetWithHeader.addChild (0, header);

        IconNodeWidget widget = new IconNodeWidget (scene, IconNodeWidget.TextOrientation.BOTTOM_CENTER);
        widget.setImage (Utilities.loadImage ("test/resources/displayable_64.png")); // NOI18N
        widget.setLabel ("Double-click me to change the visibility of the header");
        widget.setPreferredLocation (new Point (0, 0)); // the location of icon node widget relatively to the reference point
        theWidgetWithHeader.addChild (widget);

        widget.getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                header.setVisible (! header.isVisible ());
            }
        }));

        SceneSupport.show (scene);
    }

}
