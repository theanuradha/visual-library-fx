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

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;

import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class ActionMapActionTest {

	public static void main(String[] args) {
		Scene scene = new Scene();
		scene.addChild(new LabelWidget(scene, "Press Enter key to invoke the action"));

		InputMap inputMap = new InputMap();
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false), "myAction");

		ActionMap actionMap = new ActionMap();
		actionMap.put("myAction", new MyAction());

		// scene.getActions ().addAction (ActionFactory.createActionMapAction
		// (inputMap, actionMap));

		SceneSupport.show(scene);
	}

	private static class MyAction extends AbstractAction {

		public MyAction() {
			super("My Action");
		}

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "My Action has been invoked");
		}
	}

}
