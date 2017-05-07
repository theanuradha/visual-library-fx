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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * @author David Kaspar
 */
public class RoutingPolicyTest extends Scene {

	private LayerWidget mainLayer;
	private ConnectionWidget connection;

	public RoutingPolicyTest() {
		mainLayer = new LayerWidget(this);
		addChild(mainLayer);
		LayerWidget connLayer = new LayerWidget(this);
		addChild(connLayer);

		createLabel("Buttons in toolbar", 200, 100, Color.YELLOW);
		createLabel("allows you to switch", 200, 130, Color.YELLOW);
		createLabel("routing policy of the connection widget.", 200, 160, Color.YELLOW);

		createLabel("Try double-clicking on the connection widget.", 200, 250, Color.CYAN);
		createLabel("Try dragging control points of the connection widget.", 200, 280, Color.CYAN);
		createLabel("Try force re-routing of the connection widget.", 200, 310, Color.CYAN);

		Widget source = createLabel("Source", 50, 200, Color.GREEN);
		Widget target = createLabel("Target", 450, 200, Color.GREEN);

		connection = new ConnectionWidget(this);
		connection.setSourceAnchor(
				AnchorFactory.createDirectionalAnchor(source, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
		connection.setTargetAnchor(
				AnchorFactory.createDirectionalAnchor(target, AnchorFactory.DirectionalAnchorKind.HORIZONTAL));
		connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		connection.setPaintControlPoints(true);
		connection.setControlPointShape(PointShape.SQUARE_FILLED_BIG);
		connection.setRouter(RouterFactory.createOrthogonalSearchRouter(mainLayer));
		connection.getActions().addAction(ActionFactory.createAddRemoveControlPointAction());
		connection.getActions().addAction(ActionFactory.createFreeMoveControlPointAction());
		connLayer.addChild(connection);
	}

	private Widget createLabel(String text, int x, int y, Color color) {
		LabelWidget label = new LabelWidget(this, text);
		label.setOpaque(true);
		label.setBackground(color);
		label.setBorder(BorderFactory.createLineBorder(5));
		label.setPreferredLocation(new Point(x, y));
		label.getActions().addAction(ActionFactory.createMoveAction());
		mainLayer.addChild(label);
		return label;
	}

	private JComponent createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JToolBar bar = new JToolBar();
		bar.add(new AbstractAction("Force Rerouting") {
			public void actionPerformed(ActionEvent e) {
				connection.reroute();
				validate();
			}
		});
		bar.addSeparator();
		bar.add(new JLabel("Switch routing policy:"));
		bar.add(new AbstractAction("Always Route") {
			public void actionPerformed(ActionEvent e) {
				connection.setRoutingPolicy(ConnectionWidget.RoutingPolicy.ALWAYS_ROUTE);
				validate();
			}
		});
		bar.add(new AbstractAction("Update End Points Only") {
			public void actionPerformed(ActionEvent e) {
				connection.setRoutingPolicy(ConnectionWidget.RoutingPolicy.UPDATE_END_POINTS_ONLY);
				validate();
			}
		});
		bar.add(new AbstractAction("Disable Routing Until End Point is Moved") {
			public void actionPerformed(ActionEvent e) {
				connection.setRoutingPolicy(ConnectionWidget.RoutingPolicy.DISABLE_ROUTING_UNTIL_END_POINT_IS_MOVED);
				validate();
			}
		});
		bar.add(new AbstractAction("Disable Routing") {
			public void actionPerformed(ActionEvent e) {
				connection.setRoutingPolicy(ConnectionWidget.RoutingPolicy.DISABLE_ROUTING);
				validate();
			}
		});
		panel.add(bar, BorderLayout.NORTH);

		// panel.add (createView (), BorderLayout.CENTER);
		return panel;
	}

	public static void main(String[] args) {
		// SceneSupport.show (new RoutingPolicyTest ().createPanel ());
	}

}
