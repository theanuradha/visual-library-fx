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
package test.visible;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.Layout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.border.BorderFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class VisibilityTest {

    private static LabelWidget status;
    private static WidgetAction action = ActionFactory.createSelectAction (new MySelectProvider ());

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.setLayout (LayoutFactory.createAbsoluteLayout ());
        scene.getActions ().addAction (action);

        status = new LabelWidget (scene, "Status");
        status.setPreferredLocation (new Point (10, 10));
        scene.addChild (status);

        createChildrenSet (scene, LayoutFactory.createAbsoluteLayout (), 100, 100);
        createChildrenSet (scene, LayoutFactory.createHorizontalFlowLayout (), 100, 200);
        createChildrenSet (scene, LayoutFactory.createVerticalFlowLayout (), 100, 300);
        createChildrenSet (scene, LayoutFactory.createOverlayLayout (), 100, 400);

        SceneSupport.show (scene);
    }

    private static Widget createChildrenSet (Scene scene, Layout layout, int x, int y) {
        Widget set = new Widget (scene);
        set.setBorder (BorderFactory.createResizeBorder (8));
        set.setLayout (layout);
        set.setPreferredLocation (new Point (x, y));
        set.getActions ().addAction (ActionFactory.createResizeAction ());

        Widget widget;

        widget = new LabelWidget (scene, "1     First visible");
        widget.getActions ().addAction (action);
        set.addChild (widget);

        widget = new LabelWidget (scene, "  2   Invisible - Should not affect boundary too");
        widget.getActions ().addAction (action);
        widget.setVisible (false);
        set.addChild (widget);

        widget = new LabelWidget (scene, "    3 Second visible");
        widget.getActions ().addAction (action);
        set.addChild (widget);

        scene.addChild (set);
        return set;
    }

    private static class MySelectProvider implements SelectProvider {

        public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select (Widget widget, Point localLocation, boolean invertSelection) {
            status.setLabel (widget instanceof LabelWidget ? ((LabelWidget) widget).getLabel () : "");
        }

    }

}
