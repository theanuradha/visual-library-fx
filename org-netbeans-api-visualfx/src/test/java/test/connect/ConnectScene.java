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
package test.connect;

import java.awt.Point;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import javafx.scene.input.MouseButton;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class ConnectScene extends GraphScene.StringGraph {

	private LayerWidget mainLayer = new LayerWidget(this);
	private LayerWidget connectionLayer = new LayerWidget(this);
	private LayerWidget interractionLayer = new LayerWidget(this);

	private WidgetAction createAction = new SceneCreateAction();
	private WidgetAction connectAction = ActionFactory.createConnectAction(interractionLayer,
			new SceneConnectProvider());
	private WidgetAction reconnectAction = ActionFactory.createReconnectAction(new SceneReconnectProvider());

	private long nodeCounter = 0;
	private long edgeCounter = 0;

	public ConnectScene() {
		addChild(mainLayer);
		addChild(connectionLayer);
		addChild(interractionLayer);

		getActions().addAction(createAction);

		mainLayer.addChild(
				new LabelWidget(this, "Click on background to create a node. Drag a node to create a connection."));
	}

	protected Widget attachNodeWidget(String node) {
		LabelWidget label = new LabelWidget(this, node);
		label.setBorder(BorderFactory.createLineBorder(4));
		label.getActions().addAction(createObjectHoverAction());
		// label.getActions ().addAction (createSelectAction ());
		label.getActions().addAction(connectAction);
		mainLayer.addChild(label);
		return label;
	}

	protected Widget attachEdgeWidget(String edge) {
		ConnectionWidget connection = new ConnectionWidget(this);
		connection.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
		connection.setEndPointShape(PointShape.SQUARE_FILLED_BIG);
		connection.getActions().addAction(createObjectHoverAction());
		connection.getActions().addAction(createSelectAction());
		connection.getActions().addAction(reconnectAction);
		connectionLayer.addChild(connection);
		return connection;
	}

	protected void attachEdgeSourceAnchor(String edge, String oldSourceNode, String sourceNode) {
		Widget w = sourceNode != null ? findWidget(sourceNode) : null;
		((ConnectionWidget) findWidget(edge)).setSourceAnchor(AnchorFactory.createRectangularAnchor(w));
	}

	protected void attachEdgeTargetAnchor(String edge, String oldTargetNode, String targetNode) {
		Widget w = targetNode != null ? findWidget(targetNode) : null;
		((ConnectionWidget) findWidget(edge)).setTargetAnchor(AnchorFactory.createRectangularAnchor(w));
	}

	private class SceneCreateAction extends WidgetAction.Adapter {

		public State mousePressed(Widget widget, WidgetMouseEvent event) {
			if (event.getClickCount() == 1)
				if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY) {

					addNode("node" + nodeCounter++).setPreferredLocation(widget.convertLocalToScene(event.getPoint()));

					return State.CONSUMED;
				}
			return State.REJECTED;
		}

	}

	private class SceneConnectProvider implements ConnectProvider {

		private String source = null;
		private String target = null;

		public boolean isSourceWidget(Widget sourceWidget) {
			Object object = findObject(sourceWidget);
			source = isNode(object) ? (String) object : null;
			return source != null;
		}

		public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
			Object object = findObject(targetWidget);
			target = isNode(object) ? (String) object : null;
			if (target != null)
				return !source.equals(target) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
			return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
		}

		public boolean hasCustomTargetWidgetResolver(Scene scene) {
			return false;
		}

		public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
			return null;
		}

		public void createConnection(Widget sourceWidget, Widget targetWidget) {
			String edge = "edge" + edgeCounter++;
			addEdge(edge);
			setEdgeSource(edge, source);
			setEdgeTarget(edge, target);
		}

	}

	private class SceneReconnectProvider implements ReconnectProvider {

		String edge;
		String originalNode;
		String replacementNode;

		public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
		}

		public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
		}

		public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
			Object object = findObject(connectionWidget);
			edge = isEdge(object) ? (String) object : null;
			originalNode = edge != null ? getEdgeSource(edge) : null;
			return originalNode != null;
		}

		public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
			Object object = findObject(connectionWidget);
			edge = isEdge(object) ? (String) object : null;
			originalNode = edge != null ? getEdgeTarget(edge) : null;
			return originalNode != null;
		}

		public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget,
				boolean reconnectingSource) {
			Object object = findObject(replacementWidget);
			replacementNode = isNode(object) ? (String) object : null;
			if (replacementNode != null)
				return ConnectorState.ACCEPT;
			return object != null ? ConnectorState.REJECT_AND_STOP : ConnectorState.REJECT;
		}

		public boolean hasCustomReplacementWidgetResolver(Scene scene) {
			return false;
		}

		public Widget resolveReplacementWidget(Scene scene, Point sceneLocation) {
			return null;
		}

		public void reconnect(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
			if (replacementWidget == null)
				removeEdge(edge);
			else if (reconnectingSource)
				setEdgeSource(edge, replacementNode);
			else
				setEdgeTarget(edge, replacementNode);
		}

	}

	public static void main(String[] args) {
		SceneSupport.show(new ConnectScene());
	}

}
