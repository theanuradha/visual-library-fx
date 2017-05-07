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
package test.multiview;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class MultiViewTableTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        JTable table = createTable ();
        JScrollPane pane = new JScrollPane (table);
        pane.setDoubleBuffered (true); // causes JDK issue #4599654 which is worked around in ComponentWidget.paintWidget method
        ComponentWidget widget = new ComponentWidget (scene, pane);
        widget.setBorder (BorderFactory.createResizeBorder (10));
        widget.setPreferredLocation (new Point (100, 100));
        widget.getActions ().addAction (ActionFactory.createResizeAction ());
        layer.addChild (widget);

        MultiViewTest.show (scene);
    }

    private static JTable createTable () {
        JTable table = new JTable ();
        table.setModel (new DefaultTableModel (new Object[][] {
                {"11", "12"},
                {"21", "22"}
        }, new Object[] {
                "First", "Second"
        }));
        return table;
    }

}
