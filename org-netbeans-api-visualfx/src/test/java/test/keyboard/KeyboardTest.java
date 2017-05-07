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
package test.keyboard;

import test.SceneSupport;
import org.netbeans.api.visual.action.ActionFactory;

/**
 * @author David Kaspar
 */
public class KeyboardTest {

    public static void main (String[] args) {
        KeyboardGraphScene scene = new KeyboardGraphScene ();
        scene.getActions ().addAction (ActionFactory.createCycleObjectSceneFocusAction ());

        scene.addNode ("A1");
        scene.addNode ("B2");
        scene.addNode ("C3");
        scene.addNode ("D4");
        scene.addEdge ("A1", "B2");
        scene.addEdge ("A1", "C3");
        scene.addEdge ("C3", "D4");

        SceneSupport.show (scene);
    }

}
