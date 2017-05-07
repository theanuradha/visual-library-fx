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
package test.alignwith;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.border.BorderFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class AlignWithTest extends Scene {

    private LayerWidget mainLayer;
    private WidgetAction moveAction;
    private WidgetAction renameAction;
    private WidgetAction eatAction;

    public AlignWithTest () {
        setBackground (Color.WHITE);

        mainLayer = new LayerWidget (this);
        addChild (mainLayer);

        LayerWidget interractionLayer = new LayerWidget (this);
        addChild (interractionLayer);

        moveAction = ActionFactory.createAlignWithMoveAction (mainLayer, interractionLayer, null);
        renameAction = ActionFactory.createInplaceEditorAction (new RenameEditor ());
        eatAction = ActionFactory.createSelectAction (new EatEventSelectProvider ());

        getActions ().addAction (ActionFactory.createSelectAction (new CreateProvider ()));

        createLabel ("Click on the scene to create a new label", new Point (10, 20));
        createLabel ("Drag a label to move it and try to align it with other ones", new Point (10, 40));
    }

    private void createLabel (String label, Point location) {
        Scene scene = mainLayer.getScene ();
        Widget widget = new LabelWidget (scene, label);

        widget.setOpaque (true);
        widget.setBackground (Color.LIGHT_GRAY);
        widget.setBorder (BorderFactory.createBevelBorder (true));
        widget.setPreferredLocation (location);

        widget.getActions ().addAction (eatAction);
        widget.getActions ().addAction (renameAction);
        widget.getActions ().addAction (moveAction);

        mainLayer.addChild (widget);
    }

    private class CreateProvider implements SelectProvider {

        public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select (Widget widget, Point localLocation, boolean invertSelection) {
            createLabel ("Double-click to rename me", localLocation);
        }

    }

    private class EatEventSelectProvider implements SelectProvider {

        public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select (Widget widget, Point localLocation, boolean invertSelection) {
        }

    }

    private static class RenameEditor implements TextFieldInplaceEditor {

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

    public static void main (String[] args) {
        SceneSupport.show (new AlignWithTest ());
    }

}
