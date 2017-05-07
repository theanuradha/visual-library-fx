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
package test.order;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.Anchor;
import test.SceneSupport;

import java.awt.*;

/**
 * Test of how to have a connection widget in the background with anchors/widgets that are resolved later then the connection widget.
 * To solve it you have to cache and update Anchors manually in SceneListener.sceneValidated method.
 *
 * @author David Kaspar
 */
public class ReverseOrderWidgetDependencyTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget backgroundLayer = new LayerWidget (scene);
        scene.addChild (backgroundLayer);

        final ConnectionWidget conn = new ConnectionWidget (scene);
        backgroundLayer.addChild (conn);

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        Widget source = new LabelWidget (scene, "Source");
        source.setBorder (BorderFactory.createLineBorder (10));
        source.setPreferredLocation (new Point (100, 100));
        source.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (source);

        Widget target = new LabelWidget (scene, "Target");
        target.setBorder (BorderFactory.createLineBorder (10));
        target.setPreferredLocation (new Point (300, 200));
        target.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (target);

        // lazy anchor location computation

        conn.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point ()));
        conn.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point ()));

        final Anchor sourceAnchor = AnchorFactory.createRectangularAnchor (source);
        final Anchor targetAnchor = AnchorFactory.createRectangularAnchor (target);

        final Point[] anchorPointsCache = new Point[2];

        scene.addSceneListener (new Scene.SceneListener() {
            public void sceneRepaint () {
            }
            public void sceneValidating () {
            }
            public void sceneValidated () {
                Point sourcePoint = sourceAnchor.compute (conn.getSourceAnchorEntry ()).getAnchorSceneLocation ();
                Point targetPoint = targetAnchor.compute (conn.getTargetAnchorEntry ()).getAnchorSceneLocation ();
                if (! sourcePoint.equals (anchorPointsCache[0]))
                    conn.setSourceAnchor (AnchorFactory.createFixedAnchor (anchorPointsCache[0] = sourcePoint));
                if (! targetPoint.equals (anchorPointsCache[1]))
                    conn.setTargetAnchor (AnchorFactory.createFixedAnchor (anchorPointsCache[1] = targetPoint));
            }
        });
        SceneSupport.show (scene);
    }

}
