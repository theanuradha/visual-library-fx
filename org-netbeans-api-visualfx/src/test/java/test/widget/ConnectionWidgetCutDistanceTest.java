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
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.layout.LayoutFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ConnectionWidgetCutDistanceTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        ConnectionWidget conn1 = new ConnectionWidget (scene);
        conn1.setControlPointCutDistance (8);
        conn1.setRouter (RouterFactory.createOrthogonalSearchRouter ());
        conn1.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (50, 50)));
        conn1.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (450, 150)));
        conn1.getActions ().addAction (scene.createWidgetHoverAction ());
        scene.addChild (conn1);

        LabelWidget label1 = new LabelWidget (scene, "Control Point Cut Distance = 8");
        conn1.addChild (label1);
        conn1.setConstraint (label1, LayoutFactory.ConnectionWidgetLayoutAlignment.BOTTOM_RIGHT, 0);

        ConnectionWidget conn2 = new ConnectionWidget (scene);
        conn2.setControlPointCutDistance (0);
        conn2.setRouter (RouterFactory.createOrthogonalSearchRouter ());
        conn2.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (50, 250)));
        conn2.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (450, 350)));
        conn2.getActions ().addAction (scene.createWidgetHoverAction ());
        scene.addChild (conn2);

        LabelWidget label2 = new LabelWidget (scene, "Control Point Cut Distance = 0");
        conn2.addChild (label2);
        conn2.setConstraint (label2, LayoutFactory.ConnectionWidgetLayoutAlignment.BOTTOM_RIGHT, 0);
        
        SceneSupport.show (scene);
    }

}
