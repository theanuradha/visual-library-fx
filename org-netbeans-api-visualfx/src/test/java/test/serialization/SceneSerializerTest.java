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
package test.serialization;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.*;
import test.SceneSupport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * @author David Kaspar
 */
public class SceneSerializerTest extends GraphScene<String,String> {

    private static final Border BORDER_NODE = BorderFactory.createLineBorder (4, Color.BLACK);

    private LayerWidget mainLayer;
    private LayerWidget connLayer;
    private LayerWidget interactionLayer;

    long nodeIDcounter = 0;
    long edgeIDcounter = 0;

    public SceneSerializerTest () {
        addChild (mainLayer = new LayerWidget (this));
        addChild (connLayer = new LayerWidget (this));
        addChild (interactionLayer = new LayerWidget (this));

        final JPopupMenu menu = new JPopupMenu ();
        JMenuItem load = new JMenuItem ("Load scene...");
        load.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                load ();
            }
        });
        menu.add (load);
        JMenuItem save = new JMenuItem ("Save scene...");
        save.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                save ();
            }
        });
        menu.add (save);
//        getActions ().addAction (ActionFactory.createPopupMenuAction (new PopupMenuProvider() {
//            public JPopupMenu getPopupMenu (Widget widget, Point localLocation) {
//                return menu;
//            }
//        }));

        getActions ().addAction (ActionFactory.createSelectAction (new SelectProvider() {
            public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
                return false;
            }

            public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }

            public void select (Widget widget, Point localLocation, boolean invertSelection) {
                String node = "node" + ++ nodeIDcounter;
                Widget nodeWidget = addNode (node);
                nodeWidget.setPreferredLocation (localLocation);
            }
        }));
    }

    protected Widget attachNodeWidget (String node) {
        LabelWidget widget = new LabelWidget (this, node);
        widget.setBorder (BORDER_NODE);
        widget.setOpaque (true);
        widget.setBackground (Color.LIGHT_GRAY);
        widget.getActions ().addAction (ActionFactory.createExtendedConnectAction (interactionLayer, new ConnectProvider() {
            public boolean isSourceWidget (Widget sourceWidget) {
                return isNode (findObject (sourceWidget));
            }

            public ConnectorState isTargetWidget (Widget sourceWidget, Widget targetWidget) {
                return isNode (findObject (targetWidget)) ? ConnectorState.ACCEPT : ConnectorState.REJECT_AND_STOP;
            }

            public boolean hasCustomTargetWidgetResolver (Scene scene) {
                return false;
            }

            public Widget resolveTargetWidget (Scene scene, Point sceneLocation) {
                return null;
            }

            public void createConnection (Widget sourceWidget, Widget targetWidget) {
                String edge = "edge" + ++edgeIDcounter;
                addEdge (edge);
                setEdgeSource (edge, (String) findObject (sourceWidget));
                setEdgeTarget (edge, (String) findObject (targetWidget));
            }
        }));
        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (widget);
        return widget;
    }

    protected Widget attachEdgeWidget (String edge) {
        ConnectionWidget conn = new ConnectionWidget (this);
        conn.setTargetAnchorShape (AnchorShape.TRIANGLE_HOLLOW);
        connLayer.addChild (conn);
        return conn;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
        ConnectionWidget conn = (ConnectionWidget) findWidget (edge);
        Widget widget = findWidget (sourceNode);
        conn.setSourceAnchor (AnchorFactory.createRectangularAnchor (widget));
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
        ConnectionWidget conn = (ConnectionWidget) findWidget (edge);
        Widget widget = findWidget (targetNode);
        conn.setTargetAnchor (AnchorFactory.createRectangularAnchor (widget));
    }

    private void load () {
        JFileChooser chooser = new JFileChooser ();
        chooser.setDialogTitle ("Load Scene ...");
        chooser.setMultiSelectionEnabled (false);
        chooser.setFileSelectionMode (JFileChooser.FILES_ONLY);
//        if (chooser.showOpenDialog (getView ()) == JFileChooser.APPROVE_OPTION) {
//            for (String edge : new ArrayList<String> (getEdges ()))
//                removeEdge (edge);
//            for (String node : new ArrayList<String> (getNodes ()))
//                removeNode (node);
//            SceneSerializer.deserialize (this, chooser.getSelectedFile ());
//            validate ();
//        }
    }

    private void save () {
        JFileChooser chooser = new JFileChooser ();
        chooser.setDialogTitle ("Save Scene ...");
        chooser.setMultiSelectionEnabled (false);
        chooser.setFileSelectionMode (JFileChooser.FILES_ONLY);
//        if (chooser.showSaveDialog (getView ()) == JFileChooser.APPROVE_OPTION) {
//            SceneSerializer.serialize (this, chooser.getSelectedFile ());
//        }
    }

    public static void main (String[] args) {
        SceneSupport.show (new SceneSerializerTest ());
    }

}
