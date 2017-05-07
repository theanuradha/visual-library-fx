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
package test.widget;

import org.netbeans.api.visual.widget.ImageWidget;
import org.netbeans.api.visual.widget.Scene;
import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class AnimatedImageTest {

    public static void main (String[] args) {
        Scene scene = new Scene ();
        ImageWidget widget = new ImageWidget (scene, Toolkit.getDefaultToolkit ().createImage (AnimatedImageTest.class.getClassLoader ().getResource ("test/resources/animated-image.gif"))); // NOI18N
        // HINT - do not use Utilities.loadImage for loading animated images
//        ImageWidget widget = new ImageWidget (scene, Utilities.loadImage ("test/widget/a.gif")); // NOI18N
        scene.addChild (widget);
        SceneSupport.show (scene);
    }

}
