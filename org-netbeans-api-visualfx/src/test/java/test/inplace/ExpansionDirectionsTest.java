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

import org.netbeans.api.visual.action.InplaceEditorProvider;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;
import java.util.EnumSet;

/**
 * @author David Kaspar
 */
public class ExpansionDirectionsTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        ExpansionDirectionsTest.createLabel (layer, "LEFT ONLY", 50, 50, EnumSet.<InplaceEditorProvider.ExpansionDirection>of (InplaceEditorProvider.ExpansionDirection.LEFT));
        ExpansionDirectionsTest.createLabel (layer, "RIGHT ONLY", 150, 50, EnumSet.<InplaceEditorProvider.ExpansionDirection>of (InplaceEditorProvider.ExpansionDirection.RIGHT));
        ExpansionDirectionsTest.createLabel (layer, "LEFT, RIGHT", 50, 100, EnumSet.<InplaceEditorProvider.ExpansionDirection>of (InplaceEditorProvider.ExpansionDirection.LEFT, InplaceEditorProvider.ExpansionDirection.RIGHT));
        ExpansionDirectionsTest.createLabel (layer, "NONE", 150, 100, EnumSet.noneOf (InplaceEditorProvider.ExpansionDirection.class));

        SceneSupport.show (scene);
    }

    private static void createLabel (LayerWidget layer, String text, int x, int y, EnumSet<InplaceEditorProvider.ExpansionDirection> expansionDirections) {
        LabelWidget label = new LabelWidget (layer.getScene ());
        label.setOpaque (true);
        label.setBackground (Color.YELLOW);
        label.setLabel (text);
        label.setToolTipText ("Double-click to edit text");
        label.setPreferredLocation (new Point (x, y));
        label.getActions ().addAction (ActionFactory.createInplaceEditorAction (new Editor (), expansionDirections));
        layer.addChild (label);
    }

    private static class Editor implements TextFieldInplaceEditor {

        public boolean isEnabled (Widget widget) {
            return true;
        }

        public String getText (Widget widget) {
            return ((LabelWidget) widget).getLabel ();
        }

        public void setText (Widget widget, String text) {
            ((LabelWidget) widget).setLabel (text);
        }
    }

}
