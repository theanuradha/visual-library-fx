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
package test.resize;

import test.SceneSupport;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ResizeControlPointResolver;
import org.netbeans.api.visual.action.ResizeProvider;
import org.netbeans.api.visual.border.BorderFactory;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class ResizeControlPointResolverTest extends Scene {

    public ResizeControlPointResolverTest () {
        LabelWidget widget = new LabelWidget (this, "Only bottom-right corner allows the label resizing");

        widget.setOpaque (true);
        widget.setBackground (Color.LIGHT_GRAY);
        widget.setBorder (BorderFactory.createResizeBorder (8));

        widget.getActions ().addAction (ActionFactory.createResizeAction (null, new MyResizeControlPointResolver (), null));

        addChild (widget);
    }

    public static void main (String[] args) {
        SceneSupport.show (new ResizeControlPointResolverTest ());
    }

    private static class MyResizeControlPointResolver implements ResizeControlPointResolver {

        public ResizeProvider.ControlPoint resolveControlPoint (Widget widget, Point point) {
            Rectangle bounds = widget.getBounds ();
            Insets insets = widget.getBorder ().getInsets ();
            if (new Rectangle (bounds.x + bounds.width - insets.right, bounds.y + bounds.height - insets.bottom, insets.right, insets.bottom).contains (point))
                return ResizeProvider.ControlPoint.BOTTOM_RIGHT;
            return null;
        }
    }

}
