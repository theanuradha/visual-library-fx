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

import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.ReconnectProvider;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import java.awt.*;

/**
 *
 * @author alex
 */
public class SceneReconnectProvider implements ReconnectProvider {
    
    private String edge;
    private String originalNode;
    private String replacementNode;
    
    private GraphScene.StringGraph scene;
    
    public SceneReconnectProvider(GraphScene.StringGraph scene){
        this.scene=scene;
    }
    
    
    public void reconnectingStarted(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }
    
    public void reconnectingFinished(ConnectionWidget connectionWidget, boolean reconnectingSource) {
    }
    
    public boolean isSourceReconnectable(ConnectionWidget connectionWidget) {
        Object object = scene.findObject(connectionWidget);
        edge = scene.isEdge(object) ? (String) object : null;
        originalNode = edge != null ? (String) scene.getEdgeSource(edge)  : null;
        return originalNode != null;
    }
    
    public boolean isTargetReconnectable(ConnectionWidget connectionWidget) {
        Object object = scene.findObject(connectionWidget);
        edge = scene.isEdge(object) ? (String) object : null;
        originalNode = edge != null ? (String) scene.getEdgeTarget(edge)  : null;
        return originalNode != null;
    }
    
    public ConnectorState isReplacementWidget(ConnectionWidget connectionWidget, Widget replacementWidget, boolean reconnectingSource) {
        Object object = scene.findObject(replacementWidget);
        replacementNode = scene.isNode(object) ? (String) object : null;
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
            scene.removeEdge(edge);
        else if (reconnectingSource)
            scene.setEdgeSource(edge, replacementNode);
        else
            scene.setEdgeTarget(edge, replacementNode);
        scene.validate();
    }
    
}