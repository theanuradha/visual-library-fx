/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2007 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */
package org.netbeans.api.visual.widget;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Map;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.FXHints;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.modules.visual.action.PopupMenuAction;

import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * @author David Kaspar
 */
public final class SceneNode extends Canvas {

	private Scene scene;
	private Widget lockedWidget;
	private WidgetAction lockedAction;
	private long eventIDcounter = 0;
	private final Tooltip tp;
	private final FXGraphics2D g2;

	public SceneNode(Scene scene) {

		canvas = this;
		this.g2 = new FXGraphics2D(canvas.getGraphicsContext2D());
		canvas.setFocusTraversable(true);
		setStyle("-fx-focus-color: transparent;");

		canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());
		this.scene = scene;

		
		tp = new Tooltip();
		canvas.setOnMouseReleased(e -> {

			processLocationOperator(Operator.MOUSE_RELEASED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));

		});
		 
		
		canvas.setOnMouseMoved(e -> {
			MouseContext context = new MouseContext();
			Point point = scene.convertViewToScene(new Point((int) e.getX(), (int) e.getY()));
			resolveContext(scene, point, context);
			context.commit(this);
			processLocationOperator(Operator.MOUSE_MOVED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
		});
		canvas.setOnMouseClicked(e -> {
			
			processLocationOperator(Operator.MOUSE_CLICKED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
			
		});
		canvas.setOnMousePressed(e -> {
			PopupMenuAction.hide();
			processLocationOperator(Operator.MOUSE_PRESSED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
		});
		canvas.setOnMouseEntered(e -> {
			processLocationOperator(Operator.MOUSE_ENTERED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
		});
		canvas.setOnMouseExited(e -> {
			processLocationOperator(Operator.MOUSE_EXITED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
		});
		canvas.setOnMouseDragged(e -> {
			processLocationOperator(Operator.MOUSE_DRAGGED, new WidgetAction.WidgetMouseEvent(++eventIDcounter, e));
		});

		canvas.focusedProperty().addListener((e, oldV, newV) -> {

			processOperator(newV ? Operator.FOCUS_GAINED : Operator.FOCUS_LOST,
					new WidgetAction.WidgetFocusEvent(++eventIDcounter));

		});

		canvas.setOnKeyTyped(e -> {

			WidgetAction.State state = processKeyOperator(Operator.KEY_TYPED,
					new WidgetAction.WidgetKeyEvent(++eventIDcounter, e));
			if (state.isConsumed()) {
				e.consume();
			}

		});

		canvas.setOnKeyPressed(e -> {

			// HACK for invoking tooltip using Ctrl+F1 because a condition in
			// ToolTipManager.shouldRegisterBindings cannot be satisfied
			if (e.getCode() == KeyCode.F1 && e.isControlDown()) {
				MouseContext context = new MouseContext();
				resolveContext(scene.getFocusedWidget(), context);
				context.commit(this);

				Widget focusedWidget = scene.getFocusedWidget();
				Point location = focusedWidget.getScene()
						.convertSceneToView(focusedWidget.convertLocalToScene(focusedWidget.getBounds().getLocation()));
				tp.show(this, location.x, location.y);
			}

			WidgetAction.State state = processKeyOperator(Operator.KEY_PRESSED,
					new WidgetAction.WidgetKeyEvent(++eventIDcounter, e));
			if (state.isConsumed()) {
				e.consume();
			}

		});

		canvas.setOnKeyReleased(e -> {

			WidgetAction.State state = processKeyOperator(Operator.KEY_RELEASED,
					new WidgetAction.WidgetKeyEvent(++eventIDcounter, e));
			if (state.isConsumed()) {
				e.consume();
			}

		});

		canvas.setOnDragEntered(e -> {

			WidgetAction.State state = processLocationOperator(Operator.DRAG_ENTER,
					new WidgetAction.WidgetDropTargetDragEvent(++eventIDcounter, e));
			if (!state.isConsumed()) {
				e.acceptTransferModes(TransferMode.NONE);
			}

			e.consume();
		});
//		canvas.setOnZoom(e->{
//			System.out.println(e.getTotalZoomFactor());
//			
//		});
//		canvas.setOnDragDetected(value);(e -> {
//
//			WidgetAction.State state = processLocationOperator(Operator.DROP_ACTION_CHANGED,
//					new WidgetAction.WidgetDropTargetDragEvent(++eventIDcounter, e));
//
//			if (!state.isConsumed()) {
//				e.acceptTransferModes(TransferMode.NONE);
//			}
//
//			e.consume();
//		});

		canvas.setOnDragOver(e -> {

			WidgetAction.State state = processLocationOperator(Operator.DRAG_OVER,
					new WidgetAction.WidgetDropTargetDragEvent(++eventIDcounter, e));
			if (!state.isConsumed()) {
				e.acceptTransferModes(TransferMode.NONE);
			}

			e.consume();

		});

		canvas.setOnDragExited(e -> {

			processOperator(Operator.DRAG_EXIT, new WidgetAction.WidgetDropTargetEvent(++eventIDcounter, e));
			e.consume();

		});

		canvas.setOnDragDropped(e -> {

			WidgetAction.State state = processLocationOperator(Operator.DROP,
					new WidgetAction.WidgetDropTargetDropEvent(++eventIDcounter, e));
			if (!state.isConsumed()) {
				e.acceptTransferModes(TransferMode.NONE);
			} else {
				e.setDropCompleted(true);
			}

			e.consume();

		});

		// addMouseMotionListener (this);
		// addKeyListener (this);
		// setDropTarget (new DropTarget (this,
		// DnDConstants.ACTION_COPY_OR_MOVE, this));
		// setAutoscrolls (true);
		// setRequestFocusEnabled (true);
		// setFocusable (true);
		// setFocusTraversalKeysEnabled (false);
		parentProperty().addListener(e -> {
			Parent parent = getParent();
			if (parent != null) {
				scene.setGraphics(g2);
				scene.revalidate();
				scene.setViewShowing(true);
				scene.validate();
			} else {
				scene.setViewShowing(false);
			}

		});

	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void draw() {

		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		Parent parent = canvas.getParent();
		if(parent!=null)
		{
		    scene.setMinimumSize(new Dimension((int)parent.getBoundsInLocal().getWidth(),(int) parent.getBoundsInLocal().getHeight()));
		}
		
		Graphics2D gr = g2;
		Object props = Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints"); // NOI18N
	
		if (props instanceof Map) {
			gr.addRenderingHints((Map) props);
		}
		gr.setRenderingHint(FXHints.KEY_USE_FX_FONT_METRICS, true);
		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		scene.setGraphics(gr);
		AffineTransform previousTransform = gr.getTransform();
		double zoomFactor = scene.getZoomFactor();
		gr.scale(zoomFactor, zoomFactor);
		scene.setPaintEverything(false);
		scene.paint();
		scene.setPaintEverything(true);
		gr.setTransform(previousTransform);

		int width = scene.getClientArea().width;
		int height = scene.getClientArea().height;
		
		if(width<scene.getMinimumSize().width)
		    width = scene.getMinimumSize().width;
		if(height<scene.getMinimumSize().height)
		    height = scene.getMinimumSize().height;
		
		

		if(width>getWidth())
		canvas.setWidth(width);
		 if(height>getHeight())
		canvas.setHeight(height);
		
	}

	private final Canvas canvas;

	// @Override
	// public void setBounds (int x, int y, int width, int height) {
	// super.setBounds (x, y, width, height);
	//
	// Rectangle bounds = scene.getBounds ();
	// double zoomFactor = scene.getZoomFactor();
	// if (bounds != null && width == (int) (bounds.width * zoomFactor) &&
	// height == (int) (bounds.height * zoomFactor))
	// return;
	//
	// scene.revalidate ();
	// scene.validate ();
	// }
	private WidgetAction.State processLocationOperator(Operator operator, WidgetAction.WidgetLocationEvent event) {
		Point oldSceneLocation = scene.getLocation();
		// Rectangle oldVisibleRect = getVisibleRect ();
		Point viewPoint = event.getPoint();
		Point oldScenePoint = scene.convertViewToScene(viewPoint);
		event.setPoint(new Point(oldScenePoint));

		WidgetAction.State state;
		Point location;
		String tool = scene.getActiveTool();

		WidgetAction.Chain priorActions = scene.getPriorActions();
		if (!priorActions.getActions().isEmpty()) {
			location = scene.getLocation();
			event.translatePoint(location.x, location.y);
			if (operator.operate(priorActions, scene, event).isConsumed()) {
				return WidgetAction.State.CONSUMED;
			}
			event.translatePoint(-location.x, -location.y);
		}

		if (lockedAction != null) {
			location = lockedWidget.convertSceneToLocal(new Point());
			event.translatePoint(location.x, location.y);
			state = operator.operate(lockedAction, lockedWidget, event);
			event.translatePoint(-location.x, -location.y);

			if (!state.isConsumed()) {
				location = scene.getLocation();
				event.translatePoint(location.x, location.y);
				state = processLocationOperator(operator, tool, scene, event);
			}
		} else {
			location = scene.getLocation();
			event.translatePoint(location.x, location.y);
			state = processLocationOperator(operator, tool, scene, event);
		}

		lockedWidget = state.getLockedWidget();
		lockedAction = state.getLockedAction();
		scene.validate();

		// if (lockedAction != null) {
		// Point sceneLocation = scene.getLocation ();
		// Rectangle visibleRect = getVisibleRect ();
		// int xadd = (int) ((sceneLocation.x - oldSceneLocation.x) *
		// scene.getZoomFactor ());
		// int yadd = (int) ((sceneLocation.y - oldSceneLocation.y) *
		// scene.getZoomFactor ());
		// if (xadd != 0 || yadd != 0)
		// scrollRectToVisible (new Rectangle (oldVisibleRect.x + xadd,
		// oldVisibleRect.y + yadd, visibleRect.width, visibleRect.height));
		// scrollRectToVisible (new Rectangle (scene.convertSceneToView
		// (oldScenePoint)));
		//
		// }
		return state;
	}

	private WidgetAction.State processLocationOperator(Operator operator, String tool, Widget widget,
			WidgetAction.WidgetLocationEvent event) {
		if (!widget.isVisible() || !widget.isEnabled()) {
			return WidgetAction.State.REJECTED;
		}

		Point location = widget.getLocation();
		event.translatePoint(-location.x, -location.y);

		Rectangle bounds = widget.getBounds();
		assert bounds != null : Widget.MESSAGE_NULL_BOUNDS;
		if (bounds.contains(event.getPoint())) {
			WidgetAction.State state;

			List<Widget> children = widget.getChildren();
			Widget[] childrenArray = children.toArray(new Widget[children.size()]);

			for (int i = childrenArray.length - 1; i >= 0; i--) {
				Widget child = childrenArray[i];
				state = processLocationOperator(operator, tool, child, event);
				if (state.isConsumed()) {
					return state;
				}
			}

			if (widget.isHitAt(event.getPoint())) {
				WidgetAction.Chain actions;
				actions = widget.getActions();
				state = operator.operate(actions, widget, event);
				if (state.isConsumed()) {
					return state;
				}

				actions = widget.getActions(tool);
				if (actions != null) {
					state = operator.operate(actions, widget, event);
					if (state.isConsumed()) {
						return state;
					}
				}
			}
		}

		event.translatePoint(location.x, location.y);
		return WidgetAction.State.REJECTED;
	}

	private WidgetAction.State processOperator(Operator operator, WidgetAction.WidgetEvent event) {
		WidgetAction.State state;
		String tool = scene.getActiveTool();

		WidgetAction.Chain priorActions = scene.getPriorActions();
		if (!priorActions.getActions().isEmpty()) {
			if (operator.operate(priorActions, scene, event).isConsumed()) {
				return WidgetAction.State.CONSUMED;
			}
		}

		if (lockedAction != null) {
			state = operator.operate(lockedAction, lockedWidget, event);
			if (!state.isConsumed()) {
				state = processOperator(operator, tool, scene, event);
			}
		} else {
			state = processOperator(operator, tool, scene, event);
		}

		lockedWidget = state.getLockedWidget();
		lockedAction = state.getLockedAction();
		scene.validate();

		// if (lockedWidget != null)
		// scrollRectToVisible (scene.convertSceneToView
		// (lockedWidget.convertLocalToScene (lockedWidget.getBounds ())));
		return state;
	}

	private WidgetAction.State processOperator(Operator operator, String tool, Widget widget,
			WidgetAction.WidgetEvent event) {
		if (!widget.isVisible() || !widget.isEnabled()) {
			return WidgetAction.State.REJECTED;
		}

		WidgetAction.State state;

		List<Widget> children = widget.getChildren();
		Widget[] childrenArray = children.toArray(new Widget[children.size()]);

		for (int i = childrenArray.length - 1; i >= 0; i--) {
			Widget child = childrenArray[i];
			state = processOperator(operator, tool, child, event);
			if (state.isConsumed()) {
				return state;
			}
		}

		state = operator.operate(widget.getActions(), widget, event);
		if (state.isConsumed()) {
			return state;
		}

		WidgetAction.Chain actions = widget.getActions(tool);
		if (actions != null) {
			state = operator.operate(actions, widget, event);
			if (state.isConsumed()) {
				return state;
			}
		}

		return WidgetAction.State.REJECTED;
	}

	private WidgetAction.State processSingleOperator(Operator operator, String tool, Widget widget,
			WidgetAction.WidgetEvent event) {
		WidgetAction.State state;

		state = operator.operate(widget.getActions(), widget, event);
		if (state.isConsumed()) {
			return state;
		}

		WidgetAction.Chain actions = widget.getActions(tool);
		if (actions != null) {
			state = operator.operate(actions, widget, event);
			if (state.isConsumed()) {
				return state;
			}
		}

		return WidgetAction.State.REJECTED;
	}

	private WidgetAction.State processParentOperator(Operator operator, String tool, Widget widget,
			WidgetAction.WidgetKeyEvent event) {
		while (widget != null) {
			WidgetAction.State state;

			state = operator.operate(widget.getActions(), widget, event);
			if (state.isConsumed()) {
				return state;
			}

			WidgetAction.Chain actions = widget.getActions(tool);
			if (actions != null) {
				state = operator.operate(actions, widget, event);
				if (state.isConsumed()) {
					return state;
				}
			}

			widget = widget.getParentWidget();
		}

		return WidgetAction.State.REJECTED;
	}

	private Widget resolveTopMostDisabledWidget(Widget widget) {
		Widget disabledWidget = null;
		Widget tempWidget = widget;
		while (tempWidget != null) {
			if (!tempWidget.isVisible() || !tempWidget.isEnabled()) {
				disabledWidget = tempWidget;
			}
			tempWidget = tempWidget.getParentWidget();
		}
		return disabledWidget;
	}

	private WidgetAction.State processKeyOperator(Operator operator, WidgetAction.WidgetKeyEvent event) {
		WidgetAction.State state;
		String tool = scene.getActiveTool();

		WidgetAction.Chain priorActions = scene.getPriorActions();
		if (!priorActions.getActions().isEmpty()) {
			if (operator.operate(priorActions, scene, event).isConsumed()) {
				return WidgetAction.State.CONSUMED;
			}
		}

		if (lockedAction != null) {
			state = operator.operate(lockedAction, lockedWidget, event);
			if (!state.isConsumed()) {
				state = processKeyOperator(operator, tool, scene, event);
			}
		} else {
			state = processKeyOperator(operator, tool, scene, event);
		}

		lockedWidget = state.getLockedWidget();
		lockedAction = state.getLockedAction();
		scene.validate();

		// if (lockedWidget != null)
		// scrollRectToVisible (scene.convertSceneToView
		// (lockedWidget.convertLocalToScene (lockedWidget.getBounds ())));
		return state;
	}

	private WidgetAction.State processKeyOperator(Operator operator, String tool, Scene scene,
			WidgetAction.WidgetKeyEvent event) {
		Widget focusedWidget = scene.getFocusedWidget();
		WidgetAction.State state;
		Widget disabledWidget;
		switch (scene.getKeyEventProcessingType()) {
		case ALL_WIDGETS:
			return processOperator(operator, tool, scene, event);
		case FOCUSED_WIDGET_AND_ITS_PARENTS:
			disabledWidget = resolveTopMostDisabledWidget(focusedWidget);
			return processParentOperator(operator, tool,
					disabledWidget != null ? disabledWidget.getParentWidget() : focusedWidget, event);
		case FOCUSED_WIDGET_AND_ITS_CHILDREN:
			disabledWidget = resolveTopMostDisabledWidget(focusedWidget);
			if (disabledWidget != null) {
				return WidgetAction.State.REJECTED;
			}
			state = processSingleOperator(operator, tool, focusedWidget, event);
			if (state.isConsumed()) {
				return state;
			}
			return processOperator(operator, tool, focusedWidget, event);
		case FOCUSED_WIDGET_AND_ITS_CHILDREN_AND_ITS_PARENTS:
			disabledWidget = resolveTopMostDisabledWidget(focusedWidget);
			if (disabledWidget == null) {
				state = processSingleOperator(operator, tool, focusedWidget, event);
				if (state.isConsumed()) {
					return state;
				}
				state = processOperator(operator, tool, focusedWidget, event);
				if (state.isConsumed()) {
					return state;
				}
			}
			return processParentOperator(operator, tool,
					disabledWidget != null ? disabledWidget.getParentWidget() : focusedWidget.getParentWidget(), event);
		default:
			throw new IllegalStateException();
		}
	}

	private boolean resolveContext(Widget widget, Point point, MouseContext context) {
		// Point location = widget.getLocation ();
		// point.translate (- location.x, - location.y);

		if (widget.getBounds().contains(point)) {
			List<Widget> children = widget.getChildren();
			for (int i = children.size() - 1; i >= 0; i--) {
				Widget child = children.get(i);
				Point location = child.getLocation();
				point.translate(-location.x, -location.y);
				boolean resolved = resolveContext(child, point, context);
				point.translate(location.x, location.y);
				if (resolved) {
					return true;
				}
			}
			if (widget.isHitAt(point)) {
				context.update(widget, point);
			}
		}

		// point.translate (location.x, location.y);
		return false;
	}

	private void resolveContext(Widget widget, MouseContext context) {
		if (widget == null) {
			return;
		}
		context.update(widget, null);
		resolveContext(widget.getParentWidget(), context);
	}

	public void scrollRectToVisible(Rectangle rectangle) {
		
		ScrollPane scrollPane = findScrollPane(canvas);
		if(scrollPane!=null)
		{
			// scrolling values range from 0 to 1
	        scrollPane.setVvalue(rectangle.y/rectangle.height);
	        scrollPane.setHvalue(rectangle.x/rectangle.width);
		}
	}
	
	
	private javafx.scene.control.ScrollPane findScrollPane(Node component) {
		for (;;) {
			if (component == null)
				return null;
			if (component instanceof javafx.scene.control.ScrollPane)
				return ((javafx.scene.control.ScrollPane) component);
			Node parent = component.getParent();
			if (!(parent instanceof Node))
				return null;
			component = (Node) parent;
		}
	}

	public Rectangle getVisibleRect() {
		final Bounds boundsInParent = canvas.getBoundsInParent();
		return new Rectangle((int) boundsInParent.getWidth(), (int) boundsInParent.getHeight());
	}

	public Point getLocationOnScreen() {
		Bounds bounds = canvas.getBoundsInLocal();
		Bounds screenBounds = canvas.localToScreen(bounds);
		return new Point((int) screenBounds.getMinX(), (int) screenBounds.getMinY());
	}

	public boolean isShowing() {
		return scene.isViewShowing();
	}

	private interface Operator {

		public static final Operator MOUSE_CLICKED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseClicked(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_PRESSED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mousePressed(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_RELEASED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseReleased(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_ENTERED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseEntered(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_EXITED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseExited(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_DRAGGED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseDragged(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_MOVED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseMoved(widget, (WidgetAction.WidgetMouseEvent) event);
			}
		};

		public static final Operator MOUSE_WHEEL = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.mouseWheelMoved(widget, (WidgetAction.WidgetMouseWheelEvent) event);
			}
		};

		public static final Operator KEY_TYPED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.keyTyped(widget, (WidgetAction.WidgetKeyEvent) event);
			}
		};

		public static final Operator KEY_PRESSED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.keyPressed(widget, (WidgetAction.WidgetKeyEvent) event);
			}
		};

		public static final Operator KEY_RELEASED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.keyReleased(widget, (WidgetAction.WidgetKeyEvent) event);
			}
		};

		public static final Operator FOCUS_GAINED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.focusGained(widget, (WidgetAction.WidgetFocusEvent) event);
			}
		};

		public static final Operator FOCUS_LOST = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.focusLost(widget, (WidgetAction.WidgetFocusEvent) event);
			}
		};

		public static final Operator DRAG_ENTER = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.dragEnter(widget, (WidgetAction.WidgetDropTargetDragEvent) event);
			}
		};

		public static final Operator DRAG_OVER = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.dragOver(widget, (WidgetAction.WidgetDropTargetDragEvent) event);
			}
		};

		public static final Operator DROP_ACTION_CHANGED = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.dropActionChanged(widget, (WidgetAction.WidgetDropTargetDragEvent) event);
			}
		};

		public static final Operator DRAG_EXIT = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.dragExit(widget, (WidgetAction.WidgetDropTargetEvent) event);
			}
		};

		public static final Operator DROP = new Operator() {
			public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event) {
				return action.drop(widget, (WidgetAction.WidgetDropTargetDropEvent) event);
			}
		};

		public WidgetAction.State operate(WidgetAction action, Widget widget, WidgetAction.WidgetEvent event);

	}

	private static final class MouseContext {

		private String toolTipText;
		private Cursor cursor;
		// private AccessibleContext accessibleContext;

		public boolean update(Widget widget, Point localLocation) {
			if (cursor == null && localLocation != null) {
				java.awt.Cursor awtCursor = widget.getCursorAt(localLocation);
				cursor = null;
				if (awtCursor != null)
					switch (awtCursor.getType()) {

					case java.awt.Cursor.HAND_CURSOR:
						cursor = Cursor.HAND;
						break;
					case java.awt.Cursor.MOVE_CURSOR:
						cursor = Cursor.MOVE;
						break;
					case java.awt.Cursor.CROSSHAIR_CURSOR:
						cursor = Cursor.CROSSHAIR;
						break;
					case java.awt.Cursor.DEFAULT_CURSOR:
						cursor = Cursor.DEFAULT;
						break;
					case java.awt.Cursor.TEXT_CURSOR:
						cursor = Cursor.TEXT;
						break;
					case java.awt.Cursor.WAIT_CURSOR:
						cursor = Cursor.WAIT;
						break;
					case java.awt.Cursor.N_RESIZE_CURSOR:
						cursor = Cursor.N_RESIZE;
						break;
					case java.awt.Cursor.S_RESIZE_CURSOR:
						cursor = Cursor.S_RESIZE;
						break;
					case java.awt.Cursor.W_RESIZE_CURSOR:
						cursor = Cursor.W_RESIZE;
						break;
					case java.awt.Cursor.E_RESIZE_CURSOR:
						cursor = Cursor.E_RESIZE;
						break;
					case java.awt.Cursor.NW_RESIZE_CURSOR:
						cursor = Cursor.NW_RESIZE;
						break;
					case java.awt.Cursor.SW_RESIZE_CURSOR:
						cursor = Cursor.SW_RESIZE;
						break;
					case java.awt.Cursor.SE_RESIZE_CURSOR:
						cursor = Cursor.SE_RESIZE;
						break;
					case java.awt.Cursor.NE_RESIZE_CURSOR:
						cursor = Cursor.NE_RESIZE;
						break;

					default:
						cursor = null;
					}
			}
			if (toolTipText == null) {
				toolTipText = widget.getToolTipText();
			}
			return cursor == null || toolTipText == null;// || accessibleContext
															// == null;
		}

		public void commit(SceneNode component) {
			component.tp.setText(toolTipText);
			if (toolTipText == null || toolTipText.isEmpty()) {

				Tooltip.uninstall(component, component.tp);
			} else {

				Tooltip.install(component, component.tp);
			}
			component.setCursor(cursor);
		}

	}

}
