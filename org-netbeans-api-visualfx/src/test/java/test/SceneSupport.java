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
package test;

import java.awt.Dimension;

import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.SceneNode;
import org.openide.util.RequestProcessor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author David Kaspar
 */
public class SceneSupport extends javafx.application.Application {
	private static Scene scene;
	private static SceneProvider provider;

	public static void show(final Scene scene) {

		SceneSupport.scene = scene;
		Application.launch();

	}

	public static void show(final SceneProvider provider) {

		SceneSupport.provider = provider;
		Application.launch();

	}

	public static interface SceneProvider {
		Scene createScene();

	}

	public static int randInt(int max) {
		return (int) (Math.random() * max);
	}

	public static void invokeLater(final Runnable runnable, int delay) {
		RequestProcessor.getDefault().post(new Runnable() {
			public void run() {
				Platform.runLater(runnable);
			}
		}, delay);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
            if(provider != null)
	    scene = provider.createScene();
            
            
		final SceneNode sceneView =  scene.createView();

		javafx.scene.control.ScrollPane scrollPane = new javafx.scene.control.ScrollPane(sceneView);
		//scrollPane.setPrefSize(300, 300);
		scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		scrollPane.setFitToWidth(true);
		scrollPane.setFitToHeight(true);
		scrollPane.setStyle("-fx-focus-color: transparent;");
		
		scrollPane.widthProperty().addListener(e->{
		   
		    Platform.runLater(()->{
		    	 sceneView.setWidth(scrollPane.getWidth());
				    sceneView.draw();
				
			});
		});
		scrollPane.heightProperty().addListener(e->{
			Platform.runLater(()->{
				sceneView.setHeight(scrollPane.getHeight());
			    sceneView.draw();
				
			});
		    
		});
		BorderPane pane = new BorderPane(scrollPane);
		javafx.scene.Scene scene = new javafx.scene.Scene(pane, 1000, 1000);
		
		
                
		

		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
