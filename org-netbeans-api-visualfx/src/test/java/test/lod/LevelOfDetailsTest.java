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
package test.lod;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LevelOfDetailsWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class LevelOfDetailsTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.setZoomFactor(0.2);
        scene.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.LEFT_TOP, 0));
        scene.getActions().addAction(ActionFactory.createZoomAction (1.1, false));
        scene.getActions().addAction(ActionFactory.createPanAction ());
        WidgetAction hover = ActionFactory.createHoverAction (new TwoStateHoverProvider () {
            public void setHovering (Widget widget) { widget.setOpaque(true); widget.setBackground (Color.GREEN); }
            public void unsetHovering (Widget widget) { widget.setOpaque(false); widget.setBackground (Color.WHITE); }
        });
        scene.getActions().addAction(hover);

        scene.addChild(createLabel (scene, "Use mouse-wheel for zooming, use middle button for panning.", 72));
        scene.addChild(createLabel (scene, "For more details zoom into the rectangle below.", 72));
        
        Widget root = new LevelOfDetailsWidget (scene, 0.21, 0.3, Double.MAX_VALUE, Double.MAX_VALUE);
        root.setBorder (BorderFactory.createLineBorder (10));
        root.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 4));
        scene.addChild (root);
        
        for (int a = 0; a < 10; a ++) {
            root.addChild(createLabel (scene, "Row: " + a, 36));
            
            Widget row = new LevelOfDetailsWidget (scene, 0.3, 0.5, Double.MAX_VALUE, Double.MAX_VALUE);
            row.setBorder(BorderFactory.createLineBorder (4));
            row.setLayout (LayoutFactory.createHorizontalFlowLayout (LayoutFactory.SerialAlignment.JUSTIFY, 4));
            row.getActions().addAction(hover);
            root.addChild (row);
            
            for (int b = 0; b < 20; b ++) {
                Widget item = new LevelOfDetailsWidget (scene, 0.5, 1.0, Double.MAX_VALUE, Double.MAX_VALUE);
                item.setBorder (BorderFactory.createLineBorder (2));
                item.addChild (createLabel (scene, "Item-" + a + "," + b, 18));
                item.getActions().addAction(hover);
                row.addChild(item);
            }
        }
        
        SceneSupport.show (scene);
    }
    
    private static Widget createLabel (Scene scene, String text, int size) {
        LabelWidget label = new LabelWidget (scene, text);
        label.setFont(scene.getDefaultFont().deriveFont((float) size));
        return label;
    }
    
}
