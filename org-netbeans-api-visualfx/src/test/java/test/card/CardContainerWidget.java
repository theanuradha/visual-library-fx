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
package test.card;

import org.netbeans.api.visual.action.*;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class CardContainerWidget extends Widget {

    private Widget container;

    public CardContainerWidget (Scene scene) {
        super (scene);

        setLayout (LayoutFactory.createVerticalFlowLayout ());

        LabelWidget switchButton = new LabelWidget (scene, "Click me to switch card.");
        switchButton.setOpaque (true);
        switchButton.setBackground (Color.LIGHT_GRAY);
        switchButton.setBorder (BorderFactory.createBevelBorder (true));
        addChild (switchButton);

        container = new Widget (scene);
        container.setBorder (BorderFactory.createLineBorder ());
        addChild (container);

        container.setLayout (LayoutFactory.createCardLayout (container));

        switchButton.getActions ().addAction (ActionFactory.createSwitchCardAction (container));
    }

    public void addCard (Widget widget) {
        container.addChild (widget);
        if (LayoutFactory.getActiveCard (container) == null)
            LayoutFactory.setActiveCard (container, widget);
    }

    public static void main (String[] args) {
        Scene scene  = new Scene();
        scene.getActions ().addAction (ActionFactory.createZoomAction ());
        scene.getActions ().addAction (ActionFactory.createPanAction ());

        LayerWidget layer = new LayerWidget (scene);
        scene.addChild (layer);

        CardContainerWidget container = new CardContainerWidget (scene);
        container.getActions ().addAction (ActionFactory.createMoveAction ());
        container.setPreferredLocation (new Point (100, 100));
        layer.addChild (container);

        Widget card1 = new LabelWidget (scene, "This is the first card. Drag me to to move me.");
        container.addCard (card1);

        IconNodeWidget card2 = new IconNodeWidget (scene);
        card2.setLabel ("This is the second card. Drag me to to move me.");
        card2.setImage (Utilities.loadImage ("test/resources/displayable_64.png"));
        container.addCard (card2);

        SceneSupport.show (scene);
    }

}
