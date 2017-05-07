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
package test.serialization;

import org.netbeans.api.visual.widget.Widget;
import org.openide.util.Exceptions;
import org.openide.xml.XMLUtil;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.awt.*;
import java.io.*;

/**
 * @author David Kaspar
 */
public class SceneSerializer {

    private static final String SCENE_ELEMENT = "Scene"; // NOI18N
    private static final String VERSION_ATTR = "version"; // NOI18N

    private static final String SCENE_NODE_COUNTER_ATTR = "nodeIDcounter"; // NOI18N
    private static final String SCENE_EDGE_COUNTER_ATTR = "edgeIDcounter"; // NOI18N

    private static final String NODE_ELEMENT = "Node"; // NOI18N
    private static final String NODE_ID_ATTR = "id"; // NOI18N
    private static final String NODE_X_ATTR = "x"; // NOI18N
    private static final String NODE_Y_ATTR = "y"; // NOI18N

    private static final String EDGE_ELEMENT = "Edge"; // NOI18N
    private static final String EDGE_ID_ATTR = "id"; // NOI18N
    private static final String EDGE_SOURCE_ATTR = "source"; // NOI18N
    private static final String EDGE_TARGET_ATTR = "target"; // NOI18N

    private static final String VERSION_VALUE_1 = "1"; // NOI18N

    // call in AWT to serialize scene
    public static void serialize (SceneSerializerTest scene, File file) {
        Document document = XMLUtil.createDocument (SCENE_ELEMENT, null, null, null);

        Node sceneElement = document.getFirstChild ();
        setAttribute (document, sceneElement, VERSION_ATTR, VERSION_VALUE_1);
        setAttribute (document, sceneElement, SCENE_NODE_COUNTER_ATTR, Long.toString (scene.nodeIDcounter));
        setAttribute (document, sceneElement, SCENE_EDGE_COUNTER_ATTR, Long.toString (scene.edgeIDcounter));

        for (String node : scene.getNodes ()) {
            Element nodeElement = document.createElement (NODE_ELEMENT);
            setAttribute (document, nodeElement, NODE_ID_ATTR, node);
            Widget widget = scene.findWidget (node);
            Point location = widget.getPreferredLocation ();
            setAttribute (document, nodeElement, NODE_X_ATTR, Integer.toString (location.x));
            setAttribute (document, nodeElement, NODE_Y_ATTR, Integer.toString (location.y));
            sceneElement.appendChild (nodeElement);
        }
        for (String edge : scene.getEdges ()) {
            Element edgeElement = document.createElement (EDGE_ELEMENT);
            setAttribute (document, edgeElement, EDGE_ID_ATTR, edge);
            String sourceNode = scene.getEdgeSource (edge);
            if (sourceNode != null)
                setAttribute (document, edgeElement, EDGE_SOURCE_ATTR, sourceNode);
            String targetNode = scene.getEdgeTarget (edge);
            if (targetNode != null)
                setAttribute (document, edgeElement, EDGE_TARGET_ATTR, targetNode);
            sceneElement.appendChild (edgeElement);
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream (file);
            XMLUtil.write (document, fos, "UTF-8"); // NOI18N
        } catch (Exception e) {
            Exceptions.printStackTrace (e);
        } finally {
            try {
                if (fos != null) {
                    fos.close ();
                }
            } catch (Exception e) {
                Exceptions.printStackTrace (e);
            }
        }
    }

    // call in AWT to deserialize scene
    public static void deserialize (SceneSerializerTest scene, File file) {
        Node sceneElement = getRootNode (file);
        if (! VERSION_VALUE_1.equals (getAttributeValue (sceneElement, VERSION_ATTR)))
            return;
        scene.nodeIDcounter = Long.parseLong (getAttributeValue (sceneElement, SCENE_NODE_COUNTER_ATTR));
        scene.edgeIDcounter = Long.parseLong (getAttributeValue (sceneElement, SCENE_EDGE_COUNTER_ATTR));
        for (Node element : getChildNode (sceneElement)) {
            if (NODE_ELEMENT.equals (element.getNodeName ())) {
                String node = getAttributeValue (element, NODE_ID_ATTR);
                int x = Integer.parseInt (getAttributeValue (element, NODE_X_ATTR));
                int y = Integer.parseInt (getAttributeValue (element, NODE_Y_ATTR));
                Widget nodeWidget = scene.addNode (node);
                nodeWidget.setPreferredLocation (new Point (x, y));
            } else if (EDGE_ELEMENT.equals (element.getNodeName ())) {
                String edge = getAttributeValue (element, EDGE_ID_ATTR);
                String sourceNode = getAttributeValue (element, EDGE_SOURCE_ATTR);
                String targetNode = getAttributeValue (element, EDGE_TARGET_ATTR);
                scene.addEdge (edge);
                scene.setEdgeSource (edge, sourceNode);
                scene.setEdgeTarget (edge, targetNode);
            }
        }
    }

    private static void setAttribute (Document xml, Node node, String name, String value) {
        NamedNodeMap map = node.getAttributes ();
        Attr attribute = xml.createAttribute (name);
        attribute.setValue (value);
        map.setNamedItem (attribute);
    }

    private static Node getRootNode (File file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream (file);
            Document doc = XMLUtil.parse (new InputSource (is), false, false, new ErrorHandler() {
                public void error (SAXParseException e) throws SAXException {
                    throw new SAXException (e);
                }

                public void fatalError (SAXParseException e) throws SAXException {
                    throw new SAXException (e);
                }

                public void warning (SAXParseException e) {
                    Exceptions.printStackTrace (e);
                }
            }, null);
            return doc.getFirstChild ();
        } catch (Exception e) {
            Exceptions.printStackTrace (e);
        } finally {
            try {
                if (is != null)
                    is.close ();
            } catch (IOException e) {
                Exceptions.printStackTrace (e);
            }
        }
        return null;
    }

    private static String getAttributeValue (Node node, String attr) {
        try {
            if (node != null) {
                NamedNodeMap map = node.getAttributes ();
                if (map != null) {
                    node = map.getNamedItem (attr);
                    if (node != null)
                        return node.getNodeValue ();
                }
            }
        } catch (DOMException e) {
            Exceptions.printStackTrace (e);
        }
        return null;
    }

    private static Node[] getChildNode (Node node) {
        NodeList childNodes = node.getChildNodes ();
        Node[] nodes = new Node[childNodes != null ? childNodes.getLength () : 0];
        for (int i = 0; i < nodes.length; i++)
            nodes[i] = childNodes.item (i);
        return nodes;
    }

}
