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
package test.multiview;

import org.netbeans.api.visual.widget.Scene;
import test.object.ObjectTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author David Kaspar
 */
public class MultiViewTest {

    public static void main (String[] args) {
        final ObjectTest scene = new ObjectTest ();
        scene.addNode ("form [Form]");
        scene.addNode ("list [List]");
        scene.addNode ("canvas [Canvas]");
        scene.addNode ("alert [Alert]");
        scene.moveTo (null);

        show (scene);
    }

    public static void show (final Scene scene) {
        int width = 800, height = 600;
        JFrame frame = new JFrame ();//new JDialog (), true);
        Container contentPane = frame.getContentPane ();
        contentPane.setLayout (new BorderLayout ());

        //JComponent sceneView = scene.createView ();

       // JScrollPane panel = new JScrollPane (sceneView);
//        panel.getHorizontalScrollBar ().setUnitIncrement (32);
//        panel.getHorizontalScrollBar ().setBlockIncrement (256);
//        panel.getVerticalScrollBar ().setUnitIncrement (32);
//        panel.getVerticalScrollBar ().setBlockIncrement (256);
//        contentPane.add (panel, BorderLayout.CENTER);

//        contentPane.add (scene.createSatelliteView (), BorderLayout.NORTH);
//        contentPane.add (scene.createSatelliteView (), BorderLayout.SOUTH);
        contentPane.add (scene.createSatelliteView (), BorderLayout.WEST);
//        contentPane.add (scene.createSatelliteView (), BorderLayout.EAST);

        final JButton button = new JButton ("Preview");
        button.addActionListener (new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                JPopupMenu popup = new JPopupMenu ();
                popup.setLayout (new BorderLayout ());
                JComponent satelliteView = scene.createSatelliteView ();
                popup.add (satelliteView, BorderLayout.CENTER);
                popup.show (button, (button.getSize ().width - satelliteView.getPreferredSize ().width) / 2, button.getSize ().height);
            }
        });
        contentPane.add (button, BorderLayout.NORTH);


        frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit ().getScreenSize ();
        frame.setBounds ((screenSize.width - width) / 2, (screenSize.height - height) / 2, width, height);
        frame.setVisible (true);
    }

}
