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

import org.netbeans.api.visual.vmd.VMDGraphScene;
import org.openide.util.Utilities;
import test.SceneSupport;

import java.awt.*;
import java.util.Arrays;

/**
 * @author David Kaspar
 */
public class VMDCollisionTest {

    private static final Image IMAGE_LIST = Utilities.loadImage ("test/resources/list_32.png"); // NOI18N
    private static final Image IMAGE_CANVAS = Utilities.loadImage ("test/resources/custom_displayable_32.png"); // NOI18N
    private static final Image IMAGE_COMMAND = Utilities.loadImage ("test/resources/command_16.png"); // NOI18N
    private static final Image IMAGE_ITEM = Utilities.loadImage ("test/resources/item_16.png"); // NOI18N
    private static final Image GLYPH_PRE_CODE = Utilities.loadImage ("test/resources/preCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_POST_CODE = Utilities.loadImage ("test/resources/postCodeGlyph.png"); // NOI18N
    private static final Image GLYPH_CANCEL = Utilities.loadImage ("test/resources/cancelGlyph.png"); // NOI18N

    private static int nodeID = 1;
    private static int edgeID = 1;

    public static void main (String[] args) {
        VMDGraphScene scene = new VMDGraphScene ();

        String mobile = VMDTest.createNode (scene, 100, 100, IMAGE_LIST, "menu", "List", null);
        VMDTest.createPin (scene, mobile, "start", IMAGE_ITEM, "Start", "Element");

        String game = VMDTest.createNode (scene, 600, 100, IMAGE_CANVAS, "gameCanvas", "MyCanvas", Arrays.asList (GLYPH_PRE_CODE, GLYPH_CANCEL, GLYPH_POST_CODE));
        VMDTest.createPin (scene, game, "ok", IMAGE_COMMAND, "okCommand1", "Command");

        VMDTest.createEdge (scene, "start", game);
        VMDTest.createEdge (scene, "ok", mobile);

        SceneSupport.show (scene);
    }

}
