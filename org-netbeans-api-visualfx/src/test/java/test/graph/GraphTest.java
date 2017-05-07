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
package test.graph;

import test.general.StringGraphPinScene;
import test.SceneSupport;
import org.netbeans.api.visual.widget.LabelWidget;

/**
 * @author David Kaspar
 */
public class GraphTest {

    public static void main (String[] args) {
        StringGraphPinScene scene = new StringGraphPinScene ();

        scene.addNode ("n1");
        scene.addPin ("n1", "p1");
        scene.addPin ("n1", "p2");
        scene.removeNode ("n1");

        scene.addChild (new LabelWidget (scene, "This scene has to be empty (except this label)."));

        SceneSupport.show (scene);
    }

}
