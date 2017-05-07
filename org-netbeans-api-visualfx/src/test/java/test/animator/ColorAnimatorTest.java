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
package test.animator;

import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ColorAnimatorTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.setLayout (LayoutFactory.createVerticalFlowLayout ());

        for (int a = 0; a < 10; a ++)
            scene.addChild (new MyLabelWidget (scene));

        SceneSupport.show (scene);
    }

    private static final class MyLabelWidget extends LabelWidget {

        public MyLabelWidget (Scene scene) {
            super (scene, "Move mouse over the label to see animation");
            setOpaque (true);
            setBackground (Color.WHITE);
            setForeground (Color.BLACK);
            getActions ().addAction (scene.createWidgetHoverAction ());
        }

        protected void notifyStateChanged (ObjectState previousState, ObjectState state) {
            if (previousState.isHovered ()  == state.isHovered ())
                return;
            getScene ().getSceneAnimator ().animateBackgroundColor (this, state.isHovered () ? Color.BLUE : Color.WHITE);
            getScene ().getSceneAnimator ().animateForegroundColor (this, state.isHovered () ? Color.YELLOW : Color.BLACK);
        }

    }

}
