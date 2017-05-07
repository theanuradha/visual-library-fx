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
package test.keyboard;

import org.netbeans.api.visual.anchor.Anchor;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.graph.layout.GridGraphLayout;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.netbeans.api.visual.action.ActionFactory;
import org.openide.util.Utilities;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class KeyboardGraphScene extends GraphScene.StringGraph {

    private static final Image IMAGE = Utilities.loadImage ("test/resources/displayable_64.png"); // NOI18N

    private LayerWidget mainLayer;
    private LayerWidget connLayer;

    private SceneLayout layout;

    private int edgeID = 0;

    public KeyboardGraphScene () {
        addChild (mainLayer = new LayerWidget (this));
        addChild (connLayer = new LayerWidget (this));
        setKeyEventProcessingType (EventProcessingType.FOCUSED_WIDGET_AND_ITS_PARENTS);
        layout = LayoutFactory.createSceneGraphLayout (this, new GridGraphLayout<String,String> ());
        layout.invokeLayout ();
    }

    public void layout () {
        layout.invokeLayoutImmediately ();
    }

    public void addEdge (String sourceNode, String targetNode) {
        String id = "edge" + (edgeID ++);
        addEdge (id);
        setEdgeSource (id, sourceNode);
        setEdgeTarget (id, targetNode);
    }

    protected Widget attachNodeWidget (String node) {
        IconNodeWidget widget = new IconNodeWidget (this);
        widget.setImage (IMAGE);
        widget.setLabel (node);
        widget.getActions ().addAction (createSelectAction ());
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        widget.getActions ().addAction (createWidgetHoverAction ());
        mainLayer.addChild (widget);

        return widget;
    }

    protected Widget attachEdgeWidget (String edge) {
        ConnectionWidget widget = new ConnectionWidget (this);
        widget.getActions ().addAction (createSelectAction ());
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        widget.getActions ().addAction (createWidgetHoverAction ());
        connLayer.addChild (widget);
        return widget;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget conn = (ConnectionWidget) findWidget (edge);
        Widget widget = findWidget (sourceNode);
        Anchor anchor = AnchorFactory.createRectangularAnchor (widget);
        conn.setSourceAnchor (anchor);
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget conn = (ConnectionWidget) findWidget (edge);
        Widget widget = findWidget (targetNode);
        Anchor anchor = AnchorFactory.createRectangularAnchor (widget);
        conn.setTargetAnchor (anchor);
    }

}
