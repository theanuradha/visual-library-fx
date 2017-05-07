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
package test.action;

import org.netbeans.api.visual.action.ActionFactory;
import test.SceneSupport;
import test.general.StringGraphScene;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class WheelPanActionTest {

    public static void main (String[] args) {
        StringGraphScene scene = new StringGraphScene ();

        for (int a = 1; a <= 100; a ++)
            scene.addNode (String.valueOf(a)).setPreferredLocation (new Point (a * 10, a * 10));

        scene.getActions ().addAction (ActionFactory.createWheelPanAction ());
        scene.getActions ().addAction (ActionFactory.createMouseCenteredZoomAction (1.5));

        SceneSupport.show (scene);
    }

}
