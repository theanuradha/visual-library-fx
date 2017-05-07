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
package test.anchor;

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class InvalidAnchorNegativeTest {

    public static void main (String[] args) {
        System.out.println ("This test must fail because the target widget is not added into scene");
        Scene scene = new Scene ();

        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        LayerWidget connLayer = new LayerWidget (scene);
        scene.addChild (connLayer);

        LabelWidget source = new LabelWidget (scene, "The source of a connection.");
        source.setPreferredLocation (new Point (100, 100));
        mainLayer.addChild (source);

        LabelWidget target = new LabelWidget (scene, "The target of a connection that is not placed into a scene.");
        target.setPreferredLocation (new Point (300, 200));
//        mainLayer.addChild (target); // target is not at scene

        ConnectionWidget connection = new ConnectionWidget (scene);
        connection.setSourceAnchor (AnchorFactory.createCenterAnchor (source));
        connection.setTargetAnchor (AnchorFactory.createCenterAnchor (target));
        connLayer.addChild (connection);

        SceneSupport.show (scene);
    }

}
