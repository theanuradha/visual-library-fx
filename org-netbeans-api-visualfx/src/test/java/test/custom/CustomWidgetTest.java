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
package test.custom;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class CustomWidgetTest extends Widget {

    private double radius;

    public CustomWidgetTest (Scene scene, double radius) {
        super (scene);
        this.radius = radius;
    }

    protected Rectangle calculateClientArea () {
        int r = (int) Math.ceil (radius);
        return new Rectangle (- r, - r, 2 * r + 1, 2 * r + 1);
    }

    protected void paintWidget () {
        int r = (int) Math.ceil (radius);
        Graphics2D g = getGraphics ();
        g.setColor (getForeground ());
        g.drawOval (- r, - r, 2 * r, 2 * r);
    }

    public static void main (String[] args) {
        Scene scene = new Scene ();
        scene.addChild (new CustomWidgetTest (scene, 10));
        SceneSupport.show (scene);
    }

}
