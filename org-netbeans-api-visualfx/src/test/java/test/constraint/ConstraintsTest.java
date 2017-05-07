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
package test.constraint;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.layout.Layout;
import test.SceneSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.*;

/**
 * @author David Kaspar
 */
public class ConstraintsTest {

    private static final String[] unsortedLabels = new String[] {
        "Item - This is the 2. line.",
        "Screen - This is the 4. line.",
        "Exit - This is the 1. line.",
        "Ok - This is the 3. line."
    };

    public static void main (String[] args) {
        Scene scene = new Scene ();
        for (String label : unsortedLabels)
            scene.addChild (new LabelWidget (scene, label), label);
        scene.setLayout (new AlphabeticalLayout ());
        SceneSupport.show (scene);
    }

    private static final class AlphabeticalLayout implements Layout {

        public void layout (final Widget widget) {
            ArrayList<Widget> widgets = new ArrayList<Widget> (widget.getChildren ());
            Collections.sort (widgets, new Comparator<Widget>() {
                public int compare (Widget o1, Widget o2) {
                    return ((String) widget.getChildConstraint (o1)).compareTo ((String) widget.getChildConstraint (o2));
                }
            });
            int y = 0;
            for (Widget child : widgets) {
                y += 20;
                child.resolveBounds (new Point (10, y), null);
            }
        }

        public boolean requiresJustification (Widget widget) {
            return false;
        }

        public void justify (Widget widget) {
        }
    }

}
