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
package test.justify;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class JustifyTest extends Scene {

    public JustifyTest () {
        Widget r = creeateVerticalBox ();
        r.setMinimumSize (new Dimension (200, 200));
        addChild (r);

        Widget h1 = creeateHorizontalBox ();
        r.addChild (h1);
        Widget h2 = creeateHorizontalBox ();
        r.addChild (h2);

        Widget v11 = creeateVerticalBox ();
        h1.addChild (v11);
        Widget v12 = creeateVerticalBox ();
        h1.addChild (v12);

        Widget v21 = creeateVerticalBox ();
        h2.addChild (v21);
        Widget v22 = creeateVerticalBox ();
        h2.addChild (v22);

        v11.addChild (createLabel ("Hi"));
        v11.addChild (createLabel ("Privet"));
        v12.addChild (createLabel ("Cau"));
        v12.addChild (createLabel ("Caio"));
        v21.addChild (createLabel ("Good morning"));
        v21.addChild (createLabel ("Dobry den"));
        v22.addChild (createLabel ("Welcome"));
        v22.addChild (createLabel ("Vitejte"));
    }

    private LabelWidget createLabel (String text) {
        final LabelWidget label = new LabelWidget (this, text);
        label.setBorder (BorderFactory.createLineBorder (10, Color.RED));
        label.getActions ().addAction (ActionFactory.createResizeAction ());
        label.getActions ().addAction (ActionFactory.createInplaceEditorAction (new TextFieldInplaceEditor () {
            public boolean isEnabled (Widget widget) {
                return true;
            }
            public String getText (Widget widget) {
                return label.getLabel ();
            }
            public void setText (Widget widget, String text) {
                label.setLabel (text);
            }
        }));
        return label;
    }

    private Widget creeateVerticalBox () {
        Widget vbox = new Widget (this);
        vbox.setBorder (BorderFactory.createLineBorder (10, Color.GREEN));
        vbox.setLayout (LayoutFactory.createVerticalFlowLayout ());
        vbox.getActions ().addAction (ActionFactory.createResizeAction ());
        return vbox;
    }

    private Widget creeateHorizontalBox () {
        Widget hbox = new Widget (this);
        hbox.setBorder (BorderFactory.createLineBorder (10, Color.BLUE));
        hbox.setLayout (LayoutFactory.createHorizontalFlowLayout ());
        hbox.getActions ().addAction (ActionFactory.createResizeAction ());
        return hbox;
    }

    public static void main (String[] args) {
        SceneSupport.show (new JustifyTest ());
    }

}
