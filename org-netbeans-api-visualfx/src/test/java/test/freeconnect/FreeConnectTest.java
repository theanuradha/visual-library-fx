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
package test.freeconnect;

import java.awt.Color;
import java.awt.Point;

import javax.swing.JComponent;

import org.netbeans.api.visual.widget.Widget;

import test.SceneSupport;

/**
 *
 * @author alex
 */
public class FreeConnectTest  {

	 
	

	public  DemoGraphScene createScene() {
		
		DemoGraphScene scene = new DemoGraphScene();

		String nodeID1 = "Node 1";
		String nodeID2 = "Node 2";
		String edge = "edge";

		Widget hello = scene.addNode(nodeID1);
		Widget world = scene.addNode(nodeID2);

		scene.addEdge(edge);

		scene.setEdgeSource(edge, nodeID1);
		scene.setEdgeTarget(edge, nodeID2);

		hello.setPreferredLocation(new Point(100, 100));
		world.setPreferredLocation(new Point(400, 200));
		scene.setBackground(Color.WHITE);
		//scene.initGrids();
		
		return scene;
		
	}

	

	public static void main(String args[]) {
		SceneSupport.show(()->new FreeConnectTest().createScene());
		
	
	}


	// End of variables declaration//GEN-END:variables

}
