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
package test.bird;

import org.netbeans.api.visual.widget.BirdViewController;
import test.SceneSupport;
import test.general.StringGraphScene;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class BirdViewTest {

    public static void main (String[] args) {
        StringGraphScene scene = new StringGraphScene ();
        for (int a = 0; a < 100; a ++)
            scene.addNode ("node" + String.valueOf (a)).setPreferredLocation (new Point (SceneSupport.randInt (1000), SceneSupport.randInt (1000)));

        BirdViewController birdViewController = scene.createBirdView ();

        scene.createView (); // main view has to be created before showing the bird view
        birdViewController.show ();

        SceneSupport.show (scene);

//        SceneSupport.sleep (2000);
//        birdViewController.setZoomFactor (5.0);
//        birdViewController.setWindowSize (new Dimension (400, 400));
//
//        SceneSupport.sleep (2000);
//        birdViewController.hide ();
//
//        SceneSupport.sleep (2000);
//        birdViewController.show ();
    }

}
