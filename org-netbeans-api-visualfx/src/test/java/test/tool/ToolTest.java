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
package test.tool;

import java.awt.Point;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.action.*;
import test.SceneSupport;




import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * @author David Kaspar
 */
public class ToolTest extends Scene {

    private static final String ACTION_SCENE = "scene"; // NOI18N
    private static final String ACTION_MOVE = "move"; // NOI18N

    public ToolTest () {
        LayerWidget layer = new LayerWidget (this);
        addChild (layer);

        LabelWidget label = new LabelWidget (this, "Right-click to open popup menu and select an active tool.");
        label.setPreferredLocation (new Point (100, 100));
        layer.addChild (label);

        WidgetAction popup = ActionFactory.createPopupMenuAction (new MyPopupProvider ());

        getActions ().addAction (popup);

        createActions (ACTION_SCENE).addAction (popup);
        createActions (ACTION_SCENE).addAction (ActionFactory.createZoomAction ());
        createActions (ACTION_SCENE).addAction (ActionFactory.createPanAction ());

        createActions (ACTION_MOVE).addAction (popup);
        label.createActions (ACTION_MOVE).addAction (ActionFactory.createMoveAction ());
    }

    private final class MyPopupProvider implements PopupMenuProvider {

        private ContextMenu menu;

        public MyPopupProvider () {
            menu = new ContextMenu ();
            MenuItem item;

            item = new MenuItem ("Activate Scene tool - zoom/pan the scene");
            item.setUserData (ACTION_SCENE);
            item.setOnAction(e->{
            
                setActiveTool(ACTION_SCENE);
            });
            
            
            menu.getItems().add(item);

            item = new MenuItem ("Activate Move tool - move the label");
             item.setOnAction(e->{
            
                setActiveTool(ACTION_MOVE);
            });
            menu.getItems().add (item);

        }

        public ContextMenu getPopupMenu (Widget widget, Point localLocation) {
            return menu;
        }

       

    }

    public static void main (String[] args) {
        SceneSupport.show (new ToolTest ());
    }

}
