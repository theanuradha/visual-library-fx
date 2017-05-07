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
import org.netbeans.api.visual.anchor.AnchorShapeFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ArrowAnchorShapeTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();

        ConnectionWidget child = new ConnectionWidget (scene);
        child.setSourceAnchor (AnchorFactory.createFixedAnchor (new Point (100, 100)));
        child.setTargetAnchor (AnchorFactory.createFixedAnchor (new Point (200, 200)));
        child.setSourceAnchorShape (AnchorShapeFactory.createArrowAnchorShape (180, 8));
        child.setTargetAnchorShape (AnchorShapeFactory.createArrowAnchorShape (30, 16));
        scene.addChild (child);

        SceneSupport.show (scene);
    }

}
