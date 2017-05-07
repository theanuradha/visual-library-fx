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
package test.repaint;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.widget.*;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.awt.event.MouseEvent;
import javafx.scene.input.MouseButton;

/**
 * @author David Kaspar
 */
public class RepaintTest extends Scene {

    private static final Image IMAGE = Utilities.loadImage ("test/resources/displayable_64.png"); // NOI18N

    private ImageWidget floatingImage;
    private LabelWidget position;

    public RepaintTest () {
        LayerWidget mainLayer = new LayerWidget (this);
        addChild (mainLayer);

        LabelWidget label = new LabelWidget (this, "Left-click or right-click on background to test repainting.");
        label.setPreferredLocation (new Point (20, 20));
        mainLayer.addChild (label);

        position = new LabelWidget (this);
        position.setPreferredLocation (new Point (20, 40));
        mainLayer.addChild (position);

        ImageWidget image1 = new ImageWidget (this, IMAGE);
        image1.setPreferredLocation (new Point (100, 300));
        mainLayer.addChild (image1);

        ImageWidget image2 = new ImageWidget (this, IMAGE);
        image2.setPreferredLocation (new Point (500, 300));
        mainLayer.addChild (image2);

        floatingImage = new ImageWidget (this, IMAGE);
        floatingImage.setPreferredLocation (new Point (200, 100));
        floatingImage.getActions ().addAction (ActionFactory.createMoveAction ());
        mainLayer.addChild (floatingImage);

        LayerWidget connLayer = new LayerWidget (this);
        addChild (connLayer);

        ConnectionWidget conn1 = new ConnectionWidget (this);
        conn1.setSourceAnchor (AnchorFactory.createRectangularAnchor (image1));
        conn1.setTargetAnchor (AnchorFactory.createRectangularAnchor (floatingImage));
        connLayer.addChild (conn1);

        ConnectionWidget conn2 = new ConnectionWidget (this);
        conn2.setSourceAnchor (AnchorFactory.createRectangularAnchor (floatingImage));
        conn2.setTargetAnchor (AnchorFactory.createRectangularAnchor (image2));
        connLayer.addChild (conn2);

        getActions ().addAction (new MyAction ());
        getActions ().addAction (new PositionAction ());
    }

    private class MyAction extends WidgetAction.Adapter {

        public State mousePressed (Widget widget, WidgetMouseEvent event) {
            if (event.getButton () ==MouseButton.PRIMARY) {
                floatingImage.setPreferredLocation (new Point (400, 100));
                return State.CONSUMED;
            } else if (event.getButton () == MouseButton.MIDDLE) {
                floatingImage.setPreferredLocation (new Point (200, 100));
                return State.CONSUMED;
            }
            return State.REJECTED;
        }
    }

    private class PositionAction extends WidgetAction.Adapter {

        public State mouseMoved (Widget widget, WidgetMouseEvent event) {
            Point point = widget.convertLocalToScene (event.getPoint ());
            position.setLabel ("Position: x = " + point.x + ", y = " + point.y);
            return State.REJECTED;
        }

    }

    public static void main (String[] args) {
        SceneSupport.show (new RepaintTest ());
    }

}
