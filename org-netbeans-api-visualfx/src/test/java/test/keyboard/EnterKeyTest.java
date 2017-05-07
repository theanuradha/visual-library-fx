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

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 * @author David Kaspar
 */
public class EnterKeyTest {

	public static void main(String[] args) {
		Scene scene = new Scene();

		LabelWidget label = new LabelWidget(scene, "This is a label");
		scene.addChild(label);
		label.getActions().addAction(ActionFactory.createInplaceEditorAction(new TextFieldInplaceEditor() {
			public boolean isEnabled(Widget widget) {
				return true;
			}

			public String getText(Widget widget) {
				return ((LabelWidget) widget).getLabel();
			}

			public void setText(Widget widget, String text) {
				((LabelWidget) widget).setLabel(text);
			}
		}));

		// JComponent view = scene.createView ();
		//
		// final JDialog dialog = new JDialog ();
		// dialog.setSize (200, 100);
		// dialog.setLayout (new BorderLayout ());
		// dialog.add (view, BorderLayout.CENTER);
		//
		// JButton button = new JButton ("Close");
		// button.setDefaultCapable (true);
		// button.addActionListener (new ActionListener() {
		// public void actionPerformed (ActionEvent e) {
		// dialog.setVisible (false);
		// dialog.dispose ();
		// }
		// });
		// dialog.add (button, BorderLayout.SOUTH);
		//
		// dialog.setVisible (true);
	}

}
