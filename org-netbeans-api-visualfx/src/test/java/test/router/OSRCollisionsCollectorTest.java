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

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.router.CollisionsCollector;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LayerWidget;
import test.SceneSupport;

import java.awt.*;
import java.util.Arrays;

/**
 * This test describes issue #96462. It should route the connection correctly. The bug happens when the connection is router as directly (not orthogonally).
 *
 * @author David Kaspar
 */
public class OSRCollisionsCollectorTest {
/*
    verticalCollisions = [java.awt.Rectangle[x=116,y=111,width=115,height=84], java.awt.Rectangle[x=116,y=237,width=115,height=84], java.awt.Rectangle[x=116,y=28,width=115,height=84], java.awt.Rectangle[x=362,y=491,width=208,height=56], java.awt.Rectangle[x=321,y=163,width=321,height=170], java.awt.Rectangle[x=642,y=275,width=16,height=145], java.awt.Rectangle[x=268,y=252,width=16,height=168], java.awt.Rectangle[x=241,y=50,width=16,height=218], java.awt.Rectangle[x=228,y=206,width=16,height=69]]
    horizontalCollisions = [java.awt.Rectangle[x=116,y=111,width=115,height=84], java.awt.Rectangle[x=116,y=237,width=115,height=84], java.aw t.Rectangle[x=116,y=28,width=115,height=84], java.awt.Rectangle[x=362,y=491,width=208,height=56], java.awt.Rectangle[x=321,y=163,width=32 1,height=170], java.awt.Rectangle[x=618,y=275,width=40,height=16], java.awt.Rectangle[x=268,y=404,width=390,height=16], java.awt.Rectangl e[x=268,y=252,width=77,height=16], java.awt.Rectangle[x=197,y=50,width=60,height=16], java.awt.Rectangle[x=241,y=252,width=104,height=16]                    , java.awt.Rectangle[x=197,y=259,width=47,height=16], java.awt.Rectangle[x=228,y=206,width=117,height=16]]
    ==================================================
    Solution: [java.awt.Point[x=626,y=306], java.awt.Point[x=337,y=214]]
*/
    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        ConnectionWidget conn = new ConnectionWidget (scene);
        conn.setSourceAnchor (new OSRComputeControlPointsTest.DirAnchor (new Point (626, 306), Anchor.Direction.RIGHT));
        conn.setTargetAnchor (new OSRComputeControlPointsTest.DirAnchor (new Point (337, 214), Anchor.Direction.LEFT));
        conn.setRouter (RouterFactory.createOrthogonalSearchRouter (new FixedCollisionsCollector ()));
        layer.addChild (conn);

        SceneSupport.show (scene);
    }

    private static class FixedCollisionsCollector implements CollisionsCollector {

        public void collectCollisions (java.util.List<Rectangle> verticalCollisions, java.util.List<Rectangle> horizontalCollisions) {
            verticalCollisions.addAll (Arrays.asList (
                new Rectangle(116, 111, 115, 84),
                new Rectangle(116, 237, 115, 84),
//                new Rectangle(116, 28, 115, 84),
//                new Rectangle(362, 491, 208, 56),
                new Rectangle(321, 163, 321, 170)
//                new Rectangle(642, 275, 16, 145),
//                new Rectangle(268, 252, 16, 168)
//                new Rectangle(241, 50, 16, 218),
//                new Rectangle(228, 206, 16, 69)
            ));
            horizontalCollisions.addAll (Arrays.asList (
                new Rectangle(116, 111, 115, 84),
                new Rectangle(116, 237, 115, 84),
//                new Rectangle(116, 28, 115, 84),
//                new Rectangle(362, 491, 208, 56),
                new Rectangle(321, 163, 321, 170),
//                new Rectangle(618, 275, 40, 16),
//                new Rectangle(268, 404, 390, 16),
//                new Rectangle(268, 252, 77, 16),
//                new Rectangle(197, 50, 60, 16),
//                new Rectangle(241, 252, 104, 16),
//                new Rectangle(197, 259, 47, 16),
                new Rectangle(228, 206, 117, 16)
            ));
        }
    }

}
