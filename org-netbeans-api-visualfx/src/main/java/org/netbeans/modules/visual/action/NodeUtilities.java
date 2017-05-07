/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.modules.visual.action;

import java.awt.Point;
import javafx.stage.Window;
import org.netbeans.api.visual.widget.SceneNode;

/**
 *
 * @author dev
 */
class NodeUtilities {

    static void convertPointToScreen(Point p, SceneNode view) {

        //TODO: Dummy IMPL
        
        int x, y;
        final Window window = view.getScene().getWindow();

        x = (int) window.getX();
        y = (int) window.getY();

        p.x += x;
        p.y += y;

    }

}
