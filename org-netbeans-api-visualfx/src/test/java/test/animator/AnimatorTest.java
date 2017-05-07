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
package test.animator;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.awt.event.MouseEvent;
import javafx.scene.input.MouseButton;

/**
 * @author David Kaspar
 */
public class AnimatorTest extends GraphScene.StringGraph {

    private static final Image IMAGE = Utilities.loadImage ("test/resources/displayable_64.png"); // NOI18N

    private LayerWidget layer;

    private WidgetAction moveAction = ActionFactory.createMoveAction ();

    public AnimatorTest () {
        layer = new LayerWidget (this);
        addChild (layer);
        getActions ().addAction (ActionFactory.createZoomAction ());
        //getActions ().addAction (ActionFactory.createPanAction ());
        getActions ().addAction (new MyAction ());
    }

    protected Widget attachNodeWidget (String node) {
        IconNodeWidget widget = new IconNodeWidget (this);
        widget.setImage (IMAGE);
        widget.setLabel (node);
        layer.addChild (widget);

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

    public class MyAction extends WidgetAction.Adapter {

        public State mousePressed (Widget widget, WidgetMouseEvent event) {
            moveTo (event.getButton () == MouseButton.PRIMARY ? event.getPoint () : null);
            return State.CONSUMED;
        }

        public State mouseDragged (Widget widget, WidgetMouseEvent event) {
            moveTo (event.getPoint ());
            return State.CONSUMED;
        }

    }

    private void moveTo (Point point) {
        int index = 0;
        for (String node : getNodes ())
            getSceneAnimator ().animatePreferredLocation (findWidget (node), point != null ? point : new Point (++ index * 100, index * 100));
//          findWidget (node).setPreferredLocation (point != null ? point : new Point (++ index * 100, index * 100));
    }

    public static void main (String[] args) {
       
         SceneSupport.show (()->{
          AnimatorTest scene = new AnimatorTest ();
        scene.addNode ("form [Form]");
        scene.addNode ("list [List]");
        scene.addNode ("canvas [Canvas]");
        scene.addNode ("alert [Alert]");
        scene.moveTo (null);
        
        return scene;
         
         });
       
    }

}
