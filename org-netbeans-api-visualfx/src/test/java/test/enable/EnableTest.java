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
package test.enable;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author David Kaspar
 */
public class EnableTest {

    public static void main (String[] args) {
        final Scene scene = new Scene ();
        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        final IconNodeWidget widget = new IconNodeWidget (scene);
        widget.setImage (Utilities.loadImage ("test/resources/displayable_64.png")); // NOI18N
        widget.setLabel ("You can move this widget by dragging. To disable/enable the widget, press the button.");
        widget.getLabelWidget ().setBackground (Color.GRAY);
        widget.setPreferredLocation (new Point (100, 50));
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        layer.addChild (widget);

        final JToggleButton button = new JToggleButton ("Press this button to disable/enable the widget. When disabled, then it cannot be moved.", true);
        button.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                boolean b = ! widget.isEnabled ();
                button.setSelected (b);
                widget.setEnabled (b);
                widget.getImageWidget ().setPaintAsDisabled (! b);
                widget.getLabelWidget ().setPaintAsDisabled (! b);
                scene.validate ();
            }
        });
        ComponentWidget componentWidget = new ComponentWidget (scene, button);
        componentWidget.setPreferredLocation (new Point (100, 150));
        layer.addChild (componentWidget);

        SceneSupport.show (scene);

    }

}
