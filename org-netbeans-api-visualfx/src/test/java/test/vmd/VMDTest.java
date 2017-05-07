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
package test.vmd;

import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.EditProvider;
import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.netbeans.api.visual.vmd.VMDNodeWidget;
import org.netbeans.api.visual.vmd.VMDPinWidget;
import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author David Kaspar
 */
public class VMDTest {

    private static final Image IMAGE_LIST = Utilities.loadImage ("test/resources/list_16.png"); // NOI18N
    private static final Image IMAGE_CANVAS = Utilities.loadImage ("test/resources/custom_displayable_16.png"); // NOI18N
    private static final Image IMAGE_COMMAND = Utilities.loadImage ("test/resources/command_16.png"); // NOI18N
    private static final Image IMAGE_ITEM = Utilities.loadImage ("test/resources/item_16.png"); // NOI18N
    private static final Image GLYPH_PRE_CODE = Utilities.loadImage ("test/resources/preCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_POST_CODE = Utilities.loadImage ("test/resources/postCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_CANCEL = Utilities.loadImage ("test/resources/cancelGlyph.png"); // NOI18N

    private static int nodeID = 1;
    private static int edgeID = 1;

    public static void main (String[] args) {
        final VMDGraphScene scene = new VMDGraphScene ();
        runScene (scene);
    }

    static void runScene (final VMDGraphScene scene) {
        String mobile = createNode (scene, 100, 100, IMAGE_LIST, "menu", "List", null);
        createPin (scene, mobile, "start", IMAGE_ITEM, "Start", "Element");
        createPin (scene, mobile, "resume", IMAGE_ITEM, "Resume", "Element");

        String menu = createNode (scene, 400, 400, IMAGE_LIST, "menu", "List", null);
        createPin (scene, menu, "game", IMAGE_ITEM, "New Game", "Element");
        createPin (scene, menu, "options", IMAGE_ITEM, "Options", "Element");
        createPin (scene, menu, "help", IMAGE_ITEM, "Help", "Element");
        createPin (scene, menu, "exit", IMAGE_ITEM, "Exit", "Element");
        createPin (scene, menu, "listCommand1", IMAGE_COMMAND, "Yes", "Command");
        createPin (scene, menu, "listCommand2", IMAGE_COMMAND, "No", "Command");

        String game = createNode (scene, 600, 100, IMAGE_CANVAS, "gameCanvas", "MyCanvas", Arrays.asList (GLYPH_PRE_CODE, GLYPH_CANCEL, GLYPH_POST_CODE));
        createPin (scene, game, "ok", IMAGE_COMMAND, "okCommand1", "Command");
        createPin (scene, game, "cancel", IMAGE_COMMAND, "cancelCommand1", "Command");

        createEdge (scene, "start", menu);
        createEdge (scene, "resume", menu);

        createEdge (scene, "game", game);
        createEdge (scene, "exit", mobile);

        createEdge (scene, "ok", menu);
        createEdge (scene, "cancel", menu);

        VMDNodeWidget widget = (VMDNodeWidget) scene.findWidget (menu);
        HashMap<String, List<Widget>> categories = new HashMap<String, List<Widget>> ();
        categories.put ("Elements", Arrays.asList (scene.findWidget ("game"), scene.findWidget ("options"), scene.findWidget ("help"), scene.findWidget ("exit")));
        categories.put ("Commands", Arrays.asList (scene.findWidget ("listCommand1"), scene.findWidget ("listCommand2")));
        widget.sortPins (categories);

        scene.getActions ().addAction (ActionFactory.createEditAction (new EditProvider() {
            public void edit (Widget widget) {
                scene.layoutScene ();
            }
        }));

        SceneSupport.show (scene);
    }

    static String createNode (VMDGraphScene scene, int x, int y, Image image, String name, String type, List<Image> glyphs) {
        String nodeID = "node" + VMDTest.nodeID ++;
        VMDNodeWidget widget = (VMDNodeWidget) scene.addNode (nodeID);
        widget.setPreferredLocation (new Point (x, y));
        widget.setNodeProperties (image, name, type, glyphs);
        scene.addPin (nodeID, nodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
        return nodeID;
    }

    static void createPin (VMDGraphScene scene, String nodeID, String pinID, Image image, String name, String type) {
        VMDPinWidget pinWidget = ((VMDPinWidget) scene.addPin (nodeID, pinID));
        pinWidget.setProperties (name, null);

        // uncomment this in case, you would like to allow to move a node by dragging any area of the node (including pins)
//        final Widget nodeWidget = scene.findWidget (nodeID);
//        final WidgetAction originalMoveAction = ActionFactory.createMoveAction ();
//
//        pinWidget.getActions ().addAction (new WidgetAction() {
//
//            public State mouseClicked (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseClicked (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mousePressed (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mousePressed (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseReleased (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseReleased (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseEntered (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseEntered (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseExited (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseExited (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseDragged (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseDragged (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseMoved (Widget widget, WidgetMouseEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseMoved (nodeWidget, new WidgetMouseEvent (event.getEventID (), new MouseEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getButton ())));
//            }
//
//            public State mouseWheelMoved (Widget widget, WidgetMouseWheelEvent event) {
//                Point point = nodeWidget.convertSceneToLocal (widget.convertLocalToScene (event.getPoint ()));
//                return originalMoveAction.mouseWheelMoved (nodeWidget, new WidgetMouseWheelEvent (event.getEventID (), new MouseWheelEvent (widget.getScene ().getView (), 0, event.getWhen (), event.getModifiersEx (), point.x, point.y, event.getClickCount (), event.isPopupTrigger (), event.getScrollType (), event.getScrollAmount (), event.getWheelRotation ())));
//            }
//
//            public State keyTyped (Widget widget, WidgetKeyEvent event) {
//                return originalMoveAction.keyTyped (nodeWidget, event);
//            }
//
//            public State keyPressed (Widget widget, WidgetKeyEvent event) {
//                return originalMoveAction.keyPressed (nodeWidget, event);
//            }
//
//            public State keyReleased (Widget widget, WidgetKeyEvent event) {
//                return originalMoveAction.keyReleased (nodeWidget, event);
//            }
//
//            public State focusGained (Widget widget, WidgetFocusEvent event) {
//                return originalMoveAction.focusGained (nodeWidget, event);
//            }
//
//            public State focusLost (Widget widget, WidgetFocusEvent event) {
//                return originalMoveAction.focusLost (nodeWidget, event);
//            }
//
//            public State dragEnter (Widget widget, WidgetDropTargetDragEvent event) {
//                return originalMoveAction.dragEnter (nodeWidget, event);
//            }
//
//            public State dragOver (Widget widget, WidgetDropTargetDragEvent event) {
//                return originalMoveAction.dragOver (nodeWidget, event);
//            }
//
//            public State dropActionChanged (Widget widget, WidgetDropTargetDragEvent event) {
//                return originalMoveAction.dropActionChanged (nodeWidget, event);
//            }
//
//            public State dragExit (Widget widget, WidgetDropTargetEvent event) {
//                return originalMoveAction.dragExit (nodeWidget, event);
//            }
//
//            public State drop (Widget widget, WidgetDropTargetDropEvent event) {
//                return originalMoveAction.drop (nodeWidget, event);
//            }
//        });
    }

    static void createEdge (VMDGraphScene scene, String sourcePinID, String targetNodeID) {
        String edgeID = "edge" + VMDTest.edgeID ++;
        scene.addEdge (edgeID);
        scene.setEdgeSource (edgeID, sourcePinID);
        scene.setEdgeTarget (edgeID, targetNodeID + VMDGraphScene.PIN_ID_DEFAULT_SUFFIX);
    }

}
