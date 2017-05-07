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

package test.connect;

import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.anchor.AnchorFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ConnectActionLockTest {

    public static void main (String[] args) {
        final Scene scene = new Scene ();

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        final LayerWidget connLayer = new LayerWidget (scene);
        scene.addChild (connLayer);

        LayerWidget interLayer = new LayerWidget (scene);
        scene.addChild (interLayer);

        LabelWidget label1 = new LabelWidget (scene, "Drag with left-mouse button");
        label1.setPreferredLocation (new Point (100, 100));
        mainLayer.addChild (label1);

        final LabelWidget label2 = new LabelWidget (scene, "Still hold the left-mouse button and press right-mouse button additionally");
        label2.setPreferredLocation (new Point (200, 150));
        mainLayer.addChild (label2);

        label1.getActions ().addAction (ActionFactory.createConnectAction (interLayer, new ConnectProvider() {
            public boolean isSourceWidget (Widget sourceWidget) {
                return true;
            }

            public ConnectorState isTargetWidget (Widget sourceWidget, Widget targetWidget) {
                return targetWidget == label2 ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
            }

            public boolean hasCustomTargetWidgetResolver (Scene scene) {
                return false;
            }

            public Widget resolveTargetWidget (Scene scene, Point sceneLocation) {
                return null;
            }

            public void createConnection (Widget sourceWidget, Widget targetWidget) {
                ConnectionWidget conn = new ConnectionWidget (scene);
                conn.setSourceAnchor (AnchorFactory.createRectangularAnchor (sourceWidget));
                conn.setTargetAnchor (AnchorFactory.createRectangularAnchor (targetWidget));
                connLayer.addChild (conn);
            }
        }));

        SceneSupport.show (scene);
    }

}
