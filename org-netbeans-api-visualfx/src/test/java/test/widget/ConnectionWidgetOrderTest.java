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

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.layout.LayoutFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ConnectionWidgetOrderTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.getActions ().addAction (scene.createWidgetHoverAction ());

        ConnectionWidget conn1 = new ConnectionWidget (scene);
        conn1.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (100, 100)));
        conn1.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (200, 200)));
        conn1.getActions ().addAction (scene.createWidgetHoverAction ());
        scene.addChild (conn1);

        LabelWidget label1 = new LabelWidget (scene, "First (= bottom-most) - should be always below the other connection");
        conn1.addChild (label1);
        conn1.setConstraint (label1, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 0);
        
        ConnectionWidget conn2 = new ConnectionWidget (scene);
        conn2.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (100, 200)));
        conn2.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (200, 100)));
        conn2.getActions ().addAction (scene.createWidgetHoverAction ());
        scene.addChild (conn2);

        LabelWidget label2 = new LabelWidget (scene, "Last (= top-most) - should be always above the other connection");
        conn2.addChild (label2);
        conn2.setConstraint (label2, LayoutFactory.ConnectionWidgetLayoutAlignment.BOTTOM_RIGHT, 0);

//        conn1.bringToFront (); // uncommenting this line make the connection order opposite

        SceneSupport.show (scene);
    }

}
