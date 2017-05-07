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
package test.inplace;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author David Kaspar
 */
public class InvokeInplaceEditorTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
       // scene.setBorder (BorderFactory.createEmptyBorder (8, 8, 8, 8));
        scene.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.LEFT_TOP, 8));

        final LabelWidget label = new LabelWidget (scene, "To invoke in-place editor programatically, press button below");
        final WidgetAction inplaceEditorAction = ActionFactory.createInplaceEditorAction (new TextFieldInplaceEditor() {
            public boolean isEnabled (Widget widget) {
                return true;
            }
            public String getText (Widget widget) {
                return ((LabelWidget) widget).getLabel ();
            }

            public void setText (Widget widget, String text) {
                ((LabelWidget) widget).setLabel (text);
            }
        });
        label.getActions ().addAction (inplaceEditorAction);
        scene.addChild (label);

        JButton button = new JButton ("Press this button to invoke the in-place editor for the label above");
        button.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                InplaceEditorProvider.EditorController inplaceEditorController = ActionFactory.getInplaceEditorController (inplaceEditorAction);
                inplaceEditorController.openEditor (label);
            }
        });
        scene.addChild (new ComponentWidget (scene, button));

        SceneSupport.show (scene);
    }

}
