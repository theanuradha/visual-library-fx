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
package test.zoom;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.layout.SceneLayout;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.LabelWidget;
import test.SceneSupport;
import test.general.StringGraphPinScene;

import javax.swing.*;
import java.awt.*;

/**
 * @author dave
 */
public class FitToViewTest extends StringGraphPinScene {

    private SceneLayout layout;
    private JScrollPane panel;

    public FitToViewTest() {
        layout = new SceneLayout (this) {
            protected void performLayout () {
                Rectangle rectangle = new Rectangle (0, 0, 1, 1);
                for (Widget widget : getChildren ())
                    rectangle = rectangle.union (widget.convertLocalToScene (widget.getBounds ()));
                Dimension dim = rectangle.getSize ();
                Dimension viewDim = panel.getViewportBorderBounds ().getSize ();
                FitToViewTest.this.setZoomFactor (Math.min ((float) viewDim.width / dim.width, (float) viewDim.height / dim.height));
            }
        };

        getActions ().addAction (ActionFactory.createZoomAction ());
        getActions ().addAction (ActionFactory.createPanAction ());
        getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                layout.invokeLayout ();
            }
        }));

       // panel = new JScrollPane (createView ());

        for (int a = 0; a < 10; a ++)
            addNode ("node" + String.valueOf (a)).setPreferredLocation (new Point (SceneSupport.randInt (1000), SceneSupport.randInt (1000)));

        addChild (new LabelWidget (this, "Double-click on the background to zoom the scene to fit the view"));

       // SceneSupport.showCore (panel);
    }

    public static void main(String[] args) {
        new FitToViewTest ();
    }

}
