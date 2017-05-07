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
package test.component;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class ComponentTest extends Scene {

    private final WidgetAction moveAction = ActionFactory.createMoveAction ();
    private int pos = 0;

    public ComponentTest () {
        getActions ().addAction (ActionFactory.createZoomAction ());

        LayerWidget layer = new LayerWidget (this);
        addChild (layer);

        layer.addChild (new LabelWidget (this, "Scroll mouse-wheel button to zoom"));

        layer.addChild (createMoveableComponent (new JLabel ("Swing JLabel component integrated")));
        layer.addChild (createMoveableComponent (new JComboBox (new String[] { "First", "Second", "Third" })));
        layer.addChild (createMoveableComponent (new JList (new String[] { "First", "Second", "Third" })));
    }

    private Widget createMoveableComponent (Component component) {
        Widget widget = new Widget (this);
        widget.setLayout (LayoutFactory.createVerticalFlowLayout ());
        widget.setBorder (BorderFactory.createLineBorder ());
        widget.getActions ().addAction (moveAction);

        LabelWidget label = new LabelWidget (this, "Drag this to move widget");
        label.setOpaque (true);
        label.setBackground (Color.LIGHT_GRAY);
        widget.addChild (label);

        ComponentWidget componentWidget = new ComponentWidget (this, component);
        widget.addChild (componentWidget);

        pos += 100;
        widget.setPreferredLocation (new Point (pos, pos));
        return widget;
    }

    public static void main (String[] args) {
        SceneSupport.show (new ComponentTest ());
        // TODO - call detach method on all ComponentWidget to prevent memory leaks
    }

}
