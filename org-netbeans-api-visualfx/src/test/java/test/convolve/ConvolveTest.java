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
package test.convolve;

import org.netbeans.api.visual.widget.*;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.action.ActionFactory;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

/**
 * @author David Kaspar
 */
public class ConvolveTest extends Scene {

    Kernel blurKernel = new Kernel (5, 5, new float[]{
            0.00f, 0.00f, 0.05f, 0.00f, 0.00f,
            0.00f, 0.05f, 0.10f, 0.05f, 0.00f,
            0.05f, 0.10f, 0.20f, 0.10f, 0.05f,
            0.00f, 0.15f, 0.10f, 0.05f, 0.00f,
            0.00f, 0.00f, 0.05f, 0.00f, 0.00f,
    });

    Kernel dropShadowKernel = new Kernel (5, 5, new float[]{
            0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
            0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
            0.00f, 0.00f, 1.00f, 0.00f, 0.00f,
            0.00f, 0.00f, 0.00f, 0.00f, 0.00f,
            0.00f, 0.00f, 0.00f, 0.00f, 0.30f,
    });

    private LayerWidget layer;

    public ConvolveTest () {
        layer = new LayerWidget (this);
        addChild (layer);

        createNormalWidget (50, 50, "This is normal Widget - no effect");
        createConvolveWidget (blurKernel, 100, 150, "This is ConvolveWidget - the image with label has to be blurred");
        createConvolveWidget (dropShadowKernel, 150, 250, "This is ConvolveWidget - the image with label has to drop shadow");
    }

    private void createNormalWidget (int x, int y, String text) {
        Widget widget = new Widget (this);
        widget.setLayout (LayoutFactory.createVerticalFlowLayout ());
        widget.setPreferredLocation (new Point (x, y));

        widget.getActions ().addAction (ActionFactory.createMoveAction ());
        layer.addChild (widget);

        widget.addChild (new ImageWidget (this, Utilities.loadImage ("test/resources/displayable_64.png"))); // NOI18N
        widget.addChild (new LabelWidget (this, text));
    }

    private void createConvolveWidget (Kernel kernel, int x, int y, String text) {
        ConvolveWidget convolve = new ConvolveWidget (this, new ConvolveOp (kernel));
        convolve.setLayout (LayoutFactory.createVerticalFlowLayout ());
        convolve.setPreferredLocation (new Point (x, y));

        convolve.getActions ().addAction (ActionFactory.createMoveAction ());
        layer.addChild (convolve);

        convolve.addChild (new ImageWidget (this, Utilities.loadImage ("test/resources/displayable_64.png"))); // NOI18N
        convolve.addChild (new LabelWidget (this, text));
    }

    public static void main (String[] args) {
        SceneSupport.show (new ConvolveTest ());
    }

}
