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
package test.action;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class PanActionTest {

    private static final String MESSAGE = "The scene should be panned within the scene boundary only. You should not be able to get beyond [0,0] point or label.";

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        LabelWidget label1 = new LabelWidget (scene, MESSAGE);
        label1.setPreferredLocation (new Point (100, 200));
        layer.addChild (label1);

        LabelWidget label2 = new LabelWidget (scene, MESSAGE);
        label2.setPreferredLocation (new Point (100, 400));
        layer.addChild (label2);

        LabelWidget label3 = new LabelWidget (scene, MESSAGE);
        label3.setPreferredLocation (new Point (100, 600));
        layer.addChild (label3);

        scene.getActions ().addAction (ActionFactory.createZoomAction ());
        scene.getActions ().addAction (ActionFactory.createPanAction ());

        SceneSupport.show (scene);
    }

}
