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
package test.expand;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.model.StateModel;
import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.layout.LayoutFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ProxyAnchorExpandTest extends Scene {

    private StateModel model = new StateModel (2);

    public ProxyAnchorExpandTest () {
        setBackground (Color.LIGHT_GRAY);

        // layer for widgets
        LayerWidget mainLayer = new LayerWidget (this);
        addChild (mainLayer);
        // layer for connections
        LayerWidget connLayer = new LayerWidget (this);
        addChild (connLayer);

        // outer widget
        Widget outerWidget = new Widget (this);
        outerWidget.setOpaque (true);
        outerWidget.setBackground (Color.WHITE);
        outerWidget.setBorder (BorderFactory.createLineBorder (10));
        outerWidget.setPreferredLocation (new Point (100, 100));
        outerWidget.setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.CENTER, 4));

        outerWidget.addChild (new LabelWidget (this, "The anchor switches based on a state in StateModel used by ProxyAnchor."));
        outerWidget.addChild (new LabelWidget (this, "ConnectionWidget has the same anchors assigned all the time."));

        // inner widget
        LabelWidget innerWidget = new LabelWidget (this, "Internal frame");
        innerWidget.setBorder (BorderFactory.createLineBorder ());
        outerWidget.addChild (innerWidget);

        mainLayer.addChild (outerWidget);

        // the target widget
        Widget targetWidget = new LabelWidget (this, "Click here to switch the state in StateModel/anchor.");
        targetWidget.setOpaque (true);
        targetWidget.setBackground (Color.WHITE);
        targetWidget.setBorder (BorderFactory.createLineBorder (10));
        targetWidget.setPreferredLocation (new Point (450, 300));
        mainLayer.addChild (targetWidget);

        // an action for switching a state of a StateModel used by the ProxyAnchor to determinate an active anchor
        WidgetAction switchAction = ActionFactory.createSelectAction (new SwitchProvider ());
        targetWidget.getActions ().addAction (switchAction);

        Anchor outerAnchor = AnchorFactory.createRectangularAnchor (outerWidget);
        Anchor innerAnchor = AnchorFactory.createRectangularAnchor (innerWidget);

        // a proxy anchor which acts like an one of the specified anchors.
        // The active anchor is defined by a state in the StateModel
        Anchor proxyAnchor = AnchorFactory.createProxyAnchor (model, outerAnchor, innerAnchor);

        // the connection widget
        ConnectionWidget conn = new ConnectionWidget (this);
        conn.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);
        connLayer.addChild (conn);

        // the proxy anchor is used as a source
        conn.setSourceAnchor (proxyAnchor);
        // the target anchor is assigned to the targetWidget widget
        conn.setTargetAnchor (AnchorFactory.createRectangularAnchor (targetWidget));
    }

    public static void main (String[] args) {
        SceneSupport.show (new ProxyAnchorExpandTest ());
    }

    private class SwitchProvider implements SelectProvider {

        public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return false;
        }

        public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
            return true;
        }

        public void select (Widget widget, Point localLocation, boolean invertSelection) {
            // modifies the state of the state model
            model.increase ();
        }
    }

}
