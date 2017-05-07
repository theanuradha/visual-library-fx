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
package test.scroll;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.*;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class SwingScrollTest extends Scene {

    public SwingScrollTest () {
        getActions ().addAction (ActionFactory.createZoomAction ());
        LayerWidget layer = new LayerWidget (this);
        addChild (layer);

        SwingScrollWidget scroll = new SwingScrollWidget (this);
        scroll.setBorder (BorderFactory.createResizeBorder (8, Color.BLUE, false));
        scroll.setPreferredLocation (new Point (50, 50));
        scroll.setMinimumSize (new Dimension (100, 200));
        scroll.setMaximumSize (new Dimension (500, 500));
        layer.addChild (scroll);

        Widget view = new Widget (this);
        view.setLayout (LayoutFactory.createVerticalFlowLayout ());

        view.addChild (new LabelWidget (this, "Shrink the area for showing scroll bars"));
        view.addChild (new LabelWidget (this, "Drag scroll bars to move the view"));
        view.addChild (new LabelWidget (this, "Click on arrow and slider to perform unit and block scroll of the view"));
        view.addChild (new SeparatorWidget (this, SeparatorWidget.Orientation.HORIZONTAL));
        view.addChild (new ImageWidget (this, Utilities.loadImage ("test/resources/displayable_64.png")));
        view.addChild (new LabelWidget (this, "Long Long Long Long Long Long Label 1"));
        view.addChild (new LabelWidget (this, "Label 1"));
        view.addChild (new LabelWidget (this, "Label 2"));
        view.addChild (new LabelWidget (this, "Label 3"));
        view.addChild (new LabelWidget (this, "Label 4"));
        view.addChild (new LabelWidget (this, "Label 5"));
        view.addChild (new LabelWidget (this, "Long Long Long Long Long Long Label 5"));
        view.addChild (new LabelWidget (this, "Label 6"));
        view.addChild (new LabelWidget (this, "Label 7"));
        view.addChild (new LabelWidget (this, "Label 8"));
        view.addChild (new LabelWidget (this, "Label 9"));
        view.addChild (new LabelWidget (this, "Label 0"));
        view.addChild (new LabelWidget (this, "Long Long Long Long Long Long Label 0"));

        scroll.setView (view);

        scroll.getActions ().addAction (ActionFactory.createResizeAction ());
    }

    public static void main (String[] args) {
        SceneSupport.show (new SwingScrollTest ());
    }

}
