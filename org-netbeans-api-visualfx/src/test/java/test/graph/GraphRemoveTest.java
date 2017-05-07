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
public class GraphRemoveTest {

    public static void main (String[] args) {
        StringGraphPinScene scene = new StringGraphPinScene ();

        scene.addNode ("n1");
        scene.addPin ("n1", "p11");
        scene.addPin ("n1", "p12");

        scene.addNode ("n2");
        scene.addPin ("n2", "p2");

        scene.addNode ("n3");
        scene.addPin ("n3", "p3");

        scene.addEdge ("e1");
        scene.setEdgeSource ("e1", "p11");
        scene.setEdgeTarget ("e1", "p2");

        scene.addEdge ("e2");
        scene.setEdgeSource ("e2", "p12");
        scene.setEdgeTarget ("e2", "p3");

        scene.addEdge ("e3");
        scene.setEdgeSource ("e3", "p2");
        scene.setEdgeTarget ("e3", "p3");

        scene.removeNodeWithEdges ("n1");
        scene.removePinWithEdges ("p2");

        System.out.println ("Objects: " + scene.getObjects ());

        scene.addChild (new LabelWidget (scene, "This scene should have empty 'n2' and 'n3' with 'p3' and no edges only."));

        SceneSupport.show (scene);
    }

}
