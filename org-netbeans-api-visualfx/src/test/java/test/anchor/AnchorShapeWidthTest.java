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
package test.anchor;

import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.AnchorShapeFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class AnchorShapeWidthTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.getActions ().addAction (ActionFactory.createZoomAction ());
        AnchorShape shape = AnchorShapeFactory.createTriangleAnchorShape(18, true, false, 17);
        ConnectionWidget connection = new ConnectionWidget (scene);
        connection.setStroke (new BasicStroke (3.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
        connection.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (100, 100)));
        connection.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (200, 100)));
        connection.setSourceAnchorShape (shape);
        connection.setTargetAnchorShape (shape);
        scene.addChild (connection);
        SceneSupport.show (scene);
    }

}
