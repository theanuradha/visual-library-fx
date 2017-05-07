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
package test.graphlayout;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.graph.layout.GraphLayout;
import org.netbeans.api.visual.graph.layout.GraphLayoutFactory;
import org.netbeans.api.visual.graph.layout.GraphLayoutSupport;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.layout.SceneLayout;
import test.SceneSupport;
import test.general.StringGraphScene;

/**
 * @author David Kaspar
 */
public class TreeGraphLayoutTest extends StringGraphScene {

    public TreeGraphLayoutTest () {
        // new implementation
        GraphLayout<String,String> graphLayout = GraphLayoutFactory.createTreeGraphLayout (100, 100, 50, 50, true);
        GraphLayoutSupport.setTreeGraphLayoutRootNode (graphLayout, "root");
        final SceneLayout sceneGraphLayout = LayoutFactory.createSceneGraphLayout (this, graphLayout);

        getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                // new implementation
                sceneGraphLayout.invokeLayoutImmediately ();
                // old implementation
//                new TreeGraphLayout<String, String> (TreeGraphLayoutTest.this, 100, 100, 50, 50, true).layout ("root");
            }
        }));
    }

    public static void main (String[] args) {
        

        SceneSupport.show (()->{
        
        TreeGraphLayoutTest scene = new TreeGraphLayoutTest ();

        scene.addNode ("root");
        scene.addNode ("n1");
        scene.addNode ("n2");

        scene.addEdge ("e1");
        scene.setEdgeSource ("e1", "root");
        scene.setEdgeTarget ("e1", "n1");

        scene.addEdge ("e2");
        scene.setEdgeSource ("e2", "root");
        scene.setEdgeTarget ("e2", "n2");
        
        return scene;
        
        });
    }

}
