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
package test.visible;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.SelectProvider;
import org.netbeans.api.visual.border.BorderFactory;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class NotifyAddedRemovedTest extends Widget {

    private String label;

    public NotifyAddedRemovedTest (Scene scene, String label) {
        super (scene);
        this.label = label;
    }

    protected void notifyAdded () {
        System.out.println ("ADDED: " + label);
    }

    protected void notifyRemoved () {
        System.out.println ("REMOVED: " + label);
    }

    public static void main (String[] args) {
        Scene scene = new Scene ();

        final NotifyAddedRemovedTest w1 = new NotifyAddedRemovedTest (scene, "Level 1");
        scene.addChild (w1);

        final NotifyAddedRemovedTest w11 = new NotifyAddedRemovedTest (scene, "Level 1-1");
        w1.addChild (w11);

        final NotifyAddedRemovedTest w111 = new NotifyAddedRemovedTest (scene, "Level 1-1-1");
        w11.addChild (w111);

        final NotifyAddedRemovedTest w12 = new NotifyAddedRemovedTest (scene, "Level 1-2");
        w1.addChild (w12);

        final NotifyAddedRemovedTest w2 = new NotifyAddedRemovedTest (scene, "Level 2");
        scene.addChild (w2);

        Widget click = new LabelWidget (scene, "Click me to add/remove 'Level 1-1'");
        click.setBorder (BorderFactory.createLineBorder ());
        click.getActions ().addAction (ActionFactory.createSelectAction (new SelectProvider() {
            public boolean isAimingAllowed (Widget widget, Point localLocation, boolean invertSelection) {
                return false;
            }
            public boolean isSelectionAllowed (Widget widget, Point localLocation, boolean invertSelection) {
                return true;
            }
            public void select (Widget widget, Point localLocation, boolean invertSelection) {
                if (w11.getParentWidget () != null)
                    w11.removeFromParent ();
                else
                    w1.addChild (w11);
            }
        }));
        scene.addChild (click);

        SceneSupport.show (scene);
    }

}
