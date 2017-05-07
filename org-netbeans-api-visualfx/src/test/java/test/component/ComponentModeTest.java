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

import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.SeparatorWidget;
import test.SceneSupport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author David Kaspar
 */
public class ComponentModeTest {

    public static void main (String[] args) {
        final Scene scene = new Scene ();
        scene.setBorder (BorderFactory.createEmptyBorder (10));
        scene.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 10));


        JTextField textField = new JTextField ("Text for editing - try to edit me. When the JTextField component is hidden, then the Widget just renders it.");
        final ComponentWidget textFieldWidget = new ComponentWidget (scene, textField);

        JToggleButton button = new JToggleButton ("Click to hide/show JTextField component bellow. The ComponentWidget is still in the scene and rendered.");
        button.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                textFieldWidget.setComponentVisible (! textFieldWidget.isComponentVisible ());
                scene.validate ();
            }
        });

        scene.addChild (new ComponentWidget (scene, button));
        scene.addChild (textFieldWidget);

        SeparatorWidget separator = new SeparatorWidget (scene, SeparatorWidget.Orientation.HORIZONTAL);
        scene.addChild (separator);

        JTextField textField2 = new JTextField ("Text for editing - try to edit me.");
        final ComponentWidget textFieldWidget2 = new ComponentWidget (scene, textField2);

        JToggleButton button2 = new JToggleButton ("Click to remove/add ComponentWidget from/to the scene.");
        button2.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                if (textFieldWidget2.getParentWidget () != null)
                    scene.removeChild (textFieldWidget2);
                else
                    scene.addChild (textFieldWidget2);
                scene.validate ();
            }
        });

        scene.addChild (new ComponentWidget (scene, button2));
        scene.addChild (textFieldWidget2);

        SceneSupport.show (scene);
        // TODO - call detach method on all ComponentWidget to prevent memory leaks
    }

}
