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
package test.move;

import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class SnapToGridTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        LayerWidget mainLayer = new LayerWidget (scene);
        scene.addChild (mainLayer);

        LabelWidget widget = new LabelWidget (scene, "Drag me to see snap-to-grid of 16x16 pixels.");
        widget.setBorder (BorderFactory.createLineBorder (10));
        mainLayer.addChild (widget);
        widget.getActions ().addAction (ActionFactory.createMoveAction (ActionFactory.createSnapToGridMoveStrategy (16, 16), null));

        SceneSupport.show (scene);
    }

}
