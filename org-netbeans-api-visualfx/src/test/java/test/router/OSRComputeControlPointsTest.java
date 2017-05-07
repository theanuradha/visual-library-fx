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
package test.router;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.router.Router;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * This test describes issue #96460. It should route the connection correctly. The bug happens when the connection is router as directly (not orthogonally).
 * 
 * @author David Kaspar
 */
public class OSRComputeControlPointsTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.getActions ().addAction (ActionFactory.createZoomAction (2.0, false));
        scene.getActions ().addAction (ActionFactory.createPanAction ());

        LayerWidget connectionLayer = new LayerWidget (scene);
        scene.addChild (connectionLayer);

        Router router = RouterFactory.createOrthogonalSearchRouter (connectionLayer);

        ConnectionWidget conn1 = new ConnectionWidget (scene);
        conn1.setForeground (new Color (0, 255, 0, 128));
        conn1.setSourceAnchor (new DirAnchor (new Point (100, 200), Anchor.Direction.LEFT));
        conn1.setTargetAnchor (new DirAnchor (new Point (400, 300), Anchor.Direction.RIGHT));
        conn1.setRouter (router);
        connectionLayer.addChild (conn1);

        ConnectionWidget conn3 = new ConnectionWidget (scene);
        conn3.setForeground (new Color (0, 0, 255, 128));
        conn3.setSourceAnchor (new DirAnchor (new Point (100, 300), Anchor.Direction.LEFT));
        conn3.setTargetAnchor (new DirAnchor (new Point (400, 200), Anchor.Direction.RIGHT));
        conn3.setRouter (router);
        connectionLayer.addChild (conn3);

        SceneSupport.show (scene);
    }

    static class DirAnchor extends Anchor {

        private Point location;
        private Direction direction;

        public DirAnchor (Point location, Direction direction) {
            super (null);
            this.location = location;
            this.direction = direction;
        }

        public Point getRelatedSceneLocation () {
            return location;
        }

        public Result compute (Entry entry) {
            return new Result (location, direction);
        }

    }

}
