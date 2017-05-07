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
package test.object;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author David Kaspar
 */
public class MultiMoveActionTest extends GraphScene<String,String> {

    private static final Image IMAGE = Utilities.loadImage ("test/resources/displayable_64.png"); // NOI18N

    private LayerWidget backgroundLayer;
    private LayerWidget mainLayer;

    private WidgetAction moveAction = ActionFactory.createMoveAction (null, new MultiMoveProvider ());

    public MultiMoveActionTest () {
        addChild (backgroundLayer = new LayerWidget (this));
        addChild (mainLayer = new LayerWidget (this));

        getActions ().addAction (ActionFactory.createZoomAction ());
        getActions ().addAction (ActionFactory.createPanAction ());
        getActions ().addAction (ActionFactory.createRectangularSelectAction (this, backgroundLayer));
    }

    protected Widget attachNodeWidget (String node) {
        IconNodeWidget widget = new IconNodeWidget (this);
        widget.setImage (IMAGE);
        widget.setLabel (node);
        mainLayer.addChild (widget);

        widget.getActions ().addAction (createSelectAction ());
        widget.getActions ().addAction (createObjectHoverAction ());
        widget.getActions ().addAction (moveAction);

        return widget;
    }

    protected Widget attachEdgeWidget (String edge) {
        return null;
    }

    protected void attachEdgeSourceAnchor (String edge, String oldSourceNode, String sourceNode) {
    }

    protected void attachEdgeTargetAnchor (String edge, String oldTargetNode, String targetNode) {
    }

    public static void main (String[] args) {
        MultiMoveActionTest scene = new MultiMoveActionTest ();
        scene.addNode ("Try to select").setPreferredLocation (new Point (100, 100));
        scene.addNode ("multiple objects").setPreferredLocation (new Point (200, 200));
        scene.addNode ("and move them all").setPreferredLocation (new Point (300, 300));
        scene.addNode ("with a single drag").setPreferredLocation (new Point (400, 400));
        SceneSupport.show (scene);
    }

    private class MultiMoveProvider implements MoveProvider {

        private HashMap<Widget, Point> originals = new HashMap<Widget, Point> ();
        private Point original;

        public void movementStarted (Widget widget) {
            Object object = findObject (widget);
            if (isNode (object)) {
                for (Object o : getSelectedObjects ())
                    if (isNode (o)) {
                        Widget w = findWidget (o);
                        if (w != null)
                            originals.put (w, w.getPreferredLocation ());
                    }
            } else {
                originals.put (widget, widget.getPreferredLocation ());
            }
        }

        public void movementFinished (Widget widget) {
            originals.clear ();
            original = null;
        }

        public Point getOriginalLocation (Widget widget) {
            original = widget.getPreferredLocation ();
            return original;
        }

        public void setNewLocation (Widget widget, Point location) {
            int dx = location.x - original.x;
            int dy = location.y - original.y;
            for (Map.Entry<Widget, Point> entry : originals.entrySet ()) {
                Point point = entry.getValue ();
                entry.getKey ().setPreferredLocation (new Point (point.x + dx, point.y + dy));
            }
        }

    }

}

