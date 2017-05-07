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
package test.expand;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class MouseOverExpandTest extends Scene {

	private WidgetAction expandAction = ActionFactory.createHoverAction(new ExpandController());

	public MouseOverExpandTest() {
		setBackground(Color.LIGHT_GRAY);
		LayerWidget layer = new LayerWidget(this);
		addChild(layer);
		getActions().addAction(expandAction); // required by MouseHoverAction
												// for reseting the hover state

		Widget w;

		w = new ExpandableWidget(this);
		w.setPreferredLocation(new Point(300, 100));
		w.getActions().addAction(expandAction);
		w.getActions().addAction(ActionFactory.createMoveAction());
		layer.addChild(w);

		w = new ExpandableWidget(this);
		w.setPreferredLocation(new Point(100, 200));
		w.getActions().addAction(expandAction);
		w.getActions().addAction(ActionFactory.createMoveAction());
		layer.addChild(w);

		w = new ExpandableWidget(this);
		w.setPreferredLocation(new Point(300, 300));
		w.getActions().addAction(expandAction);
		w.getActions().addAction(ActionFactory.createMoveAction());
		layer.addChild(w);
	}

	public static void main(String[] args) {
		SceneSupport.show(() -> {

			return new MouseOverExpandTest();
		});
	}

	private static class ExpandableWidget extends Widget {

		private boolean expanded = true;
		private Widget detailsWidget;

		public ExpandableWidget(Scene scene) {
			super(scene);
			setLayout(LayoutFactory.createVerticalFlowLayout());
			setOpaque(true);
			setBackground(Color.WHITE);
			setBorder(BorderFactory.createLineBorder(10));

			addChild(new LabelWidget(scene, "Move mouse cursor over the rectangle to EXPAND it."));

			detailsWidget = new Widget(scene);
			detailsWidget.setLayout(LayoutFactory.createVerticalFlowLayout());
			detailsWidget.addChild(new LabelWidget(scene, "Drag the rectangle to MOVE it."));
			detailsWidget.addChild(new LabelWidget(scene, "Move mouse cursor out of the rectangle to COLLAPSE it."));
			detailsWidget.setCheckClipping(true); // required to hide the
													// content of details widget
													// beyond its border
			addChild(detailsWidget);

			collapse();
		}

		public void collapse() {
			if (!expanded)
				return;
			expanded = false;
			detailsWidget.setPreferredBounds(new Rectangle());
			getScene().getSceneAnimator().animatePreferredBounds(detailsWidget, new Rectangle());
		}

		public void expand() {
			if (expanded)
				return;
			expanded = true;
			detailsWidget.setPreferredBounds(null);
			getScene().getSceneAnimator().animatePreferredBounds(detailsWidget, null);
		}

	}

	private class ExpandController implements TwoStateHoverProvider {

		public void unsetHovering(Widget widget) {
			if (widget instanceof ExpandableWidget)
				((ExpandableWidget) widget).collapse();
		}

		public void setHovering(Widget widget) {
			if (widget instanceof ExpandableWidget)
				((ExpandableWidget) widget).expand();
		}

	}

}
