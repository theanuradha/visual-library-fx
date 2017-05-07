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
package test.controlpoint;

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.PointShape;
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
public class ControlPointsCursorTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        ConnectionWidget conn1 = new ConnectionWidget (scene);
        conn1.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point(100, 100)));
        conn1.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point(200, 150)));
        conn1.setRouter (RouterFactory.createOrthogonalSearchRouter ());
        conn1.setControlPointShape (PointShape.SQUARE_FILLED_SMALL);
        conn1.setEndPointShape (PointShape.SQUARE_FILLED_BIG);
        conn1.setCursor (Cursor.getPredefinedCursor (Cursor.CROSSHAIR_CURSOR));
        conn1.setControlPointsCursor (Cursor.getPredefinedCursor (Cursor.MOVE_CURSOR));
        conn1.setPaintControlPoints (true);
        scene.addChild (conn1);
        LabelWidget label1 = new LabelWidget (scene, "both connection widget and control points cursors should be used");
        conn1.addChild (label1);
        conn1.setConstraint (label1, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 0.5f);

        ConnectionWidget conn2 = new ConnectionWidget (scene);
        conn2.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point(100, 200)));
        conn2.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point(200, 250)));
        conn2.setRouter (RouterFactory.createOrthogonalSearchRouter ());
        conn2.setControlPointShape (PointShape.SQUARE_FILLED_SMALL);
        conn2.setEndPointShape (PointShape.SQUARE_FILLED_BIG);
        conn2.setCursor (Cursor.getPredefinedCursor (Cursor.CROSSHAIR_CURSOR));
        conn2.setControlPointsCursor (null);
        conn2.setPaintControlPoints (true);
        scene.addChild (conn2);
        LabelWidget label2 = new LabelWidget (scene, "connection widget cursor should be used only");
        conn2.addChild (label2);
        conn2.setConstraint (label2, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 0.5f);

        ConnectionWidget conn3 = new ConnectionWidget (scene);
        conn3.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point(100, 300)));
        conn3.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point(200, 350)));
        conn3.setRouter (RouterFactory.createOrthogonalSearchRouter ());
        conn3.setControlPointShape (PointShape.SQUARE_FILLED_SMALL);
        conn3.setEndPointShape (PointShape.SQUARE_FILLED_BIG);
        conn3.setControlPointsCursor (Cursor.getPredefinedCursor (Cursor.MOVE_CURSOR));
        conn3.setPaintControlPoints (false);
        scene.addChild (conn3);
        LabelWidget label3 = new LabelWidget (scene, "none of cursors should be used");
        conn3.addChild (label3);
        conn3.setConstraint (label3, LayoutFactory.ConnectionWidgetLayoutAlignment.TOP_RIGHT, 0.5f);

        SceneSupport.show (scene);
    }

}
