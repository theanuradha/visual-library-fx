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
package test.huge;

import test.SceneSupport;

import java.awt.*;

/**
 * @author David Kaspar
 */
public class HugeTest {

    public static void main (String[] args) {
        RadialGraphScene scene = new RadialGraphScene ();
        createStructure (scene, 1, 10, 200, 400, 450);
        createStructure (scene, 2, 100, 1000, 1000, 1000);
        createStructure (scene, 3, 1000, 5000, 7500, 9500);

        SceneSupport.show (scene);
    }

    private static void createStructure (RadialGraphScene scene, int setID, int count, int centerX, int centerY, float maxRadius) {
        String rootNode = "Root of Set no. " + setID;
        scene.addNode (rootNode).setPreferredLocation (new Point (centerX, centerY));

        for (int index = 0; index < count; index ++) {
            double radius = maxRadius * index / count;
            double angle = 2 * Math.PI * index / count;
            int x = (int) (centerX + radius * Math.cos (angle));
            int y = (int) (centerY + radius * Math.sin (angle));

            String node = "Set no. " + setID + " - Child " + index;
            scene.addNode (node).setPreferredLocation (new Point (x, y));

            String edge = "Set no. " + setID + " - Edge " + index;
            scene.addEdge (edge);
            scene.setEdgeSource (edge, rootNode);
            scene.setEdgeTarget (edge, node);
        }
    }

}
