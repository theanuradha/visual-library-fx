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

package javaone.demo1;

import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class IconNodeWidget extends Widget {

    public IconNodeWidget(Scene scene, String icon, String label) {
        super (scene);

        setOpaque (true);
        setLayout (LayoutFactory.createVerticalFlowLayout (LayoutFactory.SerialAlignment.CENTER, 4)); // use vertical layout

        addChild (new ImageWidget (scene, Utilities.loadImage (icon))); // add image sub-widget
        addChild (new LabelWidget (scene, label)); // add label sub-widget
    }

    public static void main (String[] args) {
        Scene scene = new Scene (); // create a scene

        IconNodeWidget iconNode = new IconNodeWidget (scene, "javaone/resources/netbeans_logo.gif", "Visual Library"); // create our icon node
        scene.addChild (iconNode); // add the icon node into scene

        SceneSupport.show (scene); // create and show the view in JFrame
    }

}
