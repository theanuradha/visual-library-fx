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
package test.routing;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.*;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author David Kaspar
 */
public class ActionsWithRoutingPolicyTest extends Scene {

    private LayerWidget mainLayer;
    private ConnectionWidget connection;

    public ActionsWithRoutingPolicyTest () {
        mainLayer = new LayerWidget (this);
        addChild (mainLayer);
        LayerWidget connLayer = new LayerWidget (this);
        addChild (connLayer);

        Widget source = createLabel ("Source", 50, 200, Color.GREEN);
        Widget target = createLabel ("Target", 450, 200, Color.GREEN);

        connection = new ConnectionWidget (this);
        connection.setSourceAnchor (AnchorFactory.createDirectionalAnchor (source, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
        connection.setTargetAnchor (AnchorFactory.createDirectionalAnchor (target, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
        connection.setTargetAnchorShape (AnchorShape.TRIANGLE_FILLED);
        connection.setPaintControlPoints (true);
        connection.setControlPointShape (PointShape.SQUARE_FILLED_BIG);
        connection.setRouter (RouterFactory.createOrthogonalSearchRouter (mainLayer));
        connection.getActions ().addAction (ActionFactory.createAddRemoveControlPointAction (1.0, 5.0, ConnectionWidget.RoutingPolicy.UPDATE_END_POINTS_ONLY));
        connection.getActions ().addAction (ActionFactory.createMoveControlPointAction (ActionFactory.createFreeMoveControlPointProvider (), ConnectionWidget.RoutingPolicy.UPDATE_END_POINTS_ONLY));
        connLayer.addChild (connection);
    }

    private Widget createLabel (String text, int x, int y, Color color) {
        LabelWidget label = new LabelWidget (this, text);
        label.setOpaque (true);
        label.setBackground (color);
        label.setBorder (BorderFactory.createLineBorder (5));
        label.setPreferredLocation (new Point (x, y));
        label.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (label);
        return label;
    }

    private JComponent createPanel () {
        JPanel panel = new JPanel ();
        panel.setLayout (new BorderLayout ());

        JToolBar bar = new JToolBar ();
        bar.add (new AbstractAction("Force Rerouting") {
            public void actionPerformed (ActionEvent e) {
                connection.reroute ();
                validate ();
            }
        });
        bar.addSeparator ();
        bar.add (new AbstractAction("Reset routing policy to always-route") {
            public void actionPerformed (ActionEvent e) {
                connection.setRoutingPolicy (ConnectionWidget.RoutingPolicy.ALWAYS_ROUTE);
                validate ();
            }
        });
        panel.add (bar, BorderLayout.NORTH);

      //  panel.add (createView (), BorderLayout.CENTER);
        return panel;
    }

    public static void main (String[] args) {
       // SceneSupport.show (new ActionsWithRoutingPolicyTest ().createPanel ());
    }

}
