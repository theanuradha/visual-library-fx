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
package test.freeconnect;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import org.netbeans.api.visual.action.PopupMenuProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author alex
 */
public class NodeMenu implements PopupMenuProvider {
    
    private static final String DELETE_NODE_ACTION = "deleteNodeAction"; // NOI18N
    
    private ContextMenu menu;
    private IconNodeWidget node;

    private Point point;
    private GraphScene.StringGraph scene;
    
    public NodeMenu(GraphScene.StringGraph scene) {
        this.scene=scene;
        menu = new ContextMenu();
        MenuItem item;
        
        item = new MenuItem("Delete Node");
        
        item.setOnAction(e->{ scene.removeNodeWithEdges((String)scene.findObject (node));
            scene.validate();});
        
        menu.getItems().add(item);
    }
    
    public ContextMenu getPopupMenu(Widget widget,Point point){
        this.point=point;
        this.node=(IconNodeWidget)widget;
        return menu;
    }
    
  

    
}
