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

package javaone.demo2;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;
import javafx.scene.control.ContextMenu;

/**
 * @author David Kaspar
 */
public class ActionDemo {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild(layer);

        LabelWidget hello1 = createLabel (scene, "Hello", 100, 100);
        layer.addChild (hello1);
        LabelWidget hello2 = createLabel (scene, "NetBeans", 300, 200);
        layer.addChild (hello2);

        scene.getActions().addAction (ActionFactory.createZoomAction ());
       // scene.getActions().addAction (ActionFactory.createPanAction ());

        hello1.getActions().addAction (ActionFactory.createMoveAction ());
        hello2.getActions().addAction (ActionFactory.createMoveAction ());

        WidgetAction hoverAction = ActionFactory.createHoverAction (new MyHoverProvider ());
        scene.getActions().addAction (hoverAction);
        hello1.getActions().addAction (hoverAction);
        hello2.getActions().addAction (hoverAction);

        WidgetAction popupMenuAction = ActionFactory.createPopupMenuAction (new MyPopupProvider ());
        hello1.getActions().addAction (popupMenuAction);
        hello2.getActions().addAction (popupMenuAction);

        SceneSupport.show (scene);
    }

    private static LabelWidget createLabel (Scene scene, String text, int x, int y) {
        LabelWidget widget = new LabelWidget (scene, text);
        widget.setFont(scene.getDefaultFont().deriveFont(24.0f));
        widget.setOpaque(true);
        widget.setPreferredLocation (new Point (x, y));
        return widget;
    }

    private static class MyHoverProvider implements TwoStateHoverProvider {

        public void unsetHovering(Widget widget) {
            if (widget != null) {
                widget.setBackground (Color.WHITE);
                widget.setForeground (Color.BLACK);
            }
        }

        public void setHovering(Widget widget) {
            if (widget != null) {
                widget.setBackground (new Color (52, 124, 150));
                widget.setForeground (Color.WHITE);
            }
        }

    }

    private static class MyPopupProvider implements PopupMenuProvider {

        public ContextMenu getPopupMenu(Widget widget, Point localLocation) {
            ContextMenu menu = new ContextMenu ();
            menu.getItems().add(new javafx.scene.control.MenuItem("Open"));
            return menu;
        }

    }

}
