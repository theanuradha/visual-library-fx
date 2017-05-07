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
package test.context;

import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ContextTest {

    // HINT - test: toltip and cursor should be correctly shown for each label
    // HINT - test: press mouse button on the "95,95" label and release the button when the mouse is over "100,100" label
    // -> then the "100,100" label should change its background color when the mouse button is released

    public static void main (String[] args) {
        Scene scene = new Scene ();
        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        createLabel (layer, -100, -100);
        createLabel (layer, -50, -50);
        createLabel (layer, 95, 95);
        createLabel (layer, 100, 100);

        SceneSupport.show (scene);
    }

    private static void createLabel (LayerWidget layer, int x, int y) {
        LabelWidget label = new LabelWidget (layer.getScene ());
        label.setOpaque (true);
        label.setBackground (Color.YELLOW);
        label.setLabel ("Move mouse on this label to see: tooltip, cursor");
        label.setToolTipText ("This is a tooltip for the label - initial location: " + x + ","  + y);
        label.setCursor (Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        label.setPreferredLocation (new Point (x, y));
        label.getActions ().addAction (new MouseUpAction ());
        label.getActions ().addAction (new MouseLockAction ());
//        label.getActions ().addAction (ActionFactory.createMoveAction ());
        layer.addChild (label);
    }

    private static final class MouseLockAction extends WidgetAction.LockedAdapter {

        private boolean locked = false;

        protected boolean isLocked () {
            return locked;
        }

        public State mousePressed (Widget widget, WidgetMouseEvent event) {
            locked = true;
            return State.createLocked (widget, this);
        }

        public State mouseReleased (Widget widget, WidgetMouseEvent event) {
            locked = false;
            return State.REJECTED;
        }

    }

    private static final class MouseUpAction extends WidgetAction.Adapter {

        public State mouseReleased (Widget widget, WidgetMouseEvent event) {
            widget.setBackground(((Color) widget.getBackground ()).darker ());
            return State.CONSUMED;
        }

    }

}
