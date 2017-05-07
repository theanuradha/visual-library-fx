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
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.EventProcessingType;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import test.SceneSupport;

/**
 * @author David Kaspar
 */
public class EnterKeyProcessingTest {
    
    public static void main(String[] args) {
        Scene scene = new Scene();
        scene.setLayout(LayoutFactory.createVerticalFlowLayout ());
        WidgetAction action = new KeyEventLoggerAction();
        
        LabelWidget label1 = new LabelWidget(scene, "First non-editable label. Should not receive any key event.");
        label1.getActions().addAction(action);
        scene.addChild(label1);
        
        LabelWidget label2 = new LabelWidget(scene, "Second editable label.");
        label2.getActions().addAction(ActionFactory.createInplaceEditorAction(new LabelEditor()));
        scene.addChild(label2);
        
        LabelWidget label3 = new LabelWidget(scene, "Third non-editable label. Should not receive any key event.");
        label3.getActions().addAction(action);
        scene.addChild(label3);
        
        scene.setKeyEventProcessingType(EventProcessingType.FOCUSED_WIDGET_AND_ITS_CHILDREN);
        scene.setFocusedWidget (label2);
        
        SceneSupport.show(scene);
    }
    
    private static class KeyEventLoggerAction extends WidgetAction.Adapter {
        
        public State keyPressed(Widget widget, WidgetKeyEvent event) {
            System.out.println("KeyPressed at " + ((LabelWidget) widget).getLabel());
            return State.REJECTED;
        }
        
        public State keyReleased(Widget widget, WidgetKeyEvent event) {
            System.out.println("KeyReleased at " + ((LabelWidget) widget).getLabel());
            return State.REJECTED;
        }
        
        public State keyTyped(Widget widget, WidgetKeyEvent event) {
            System.out.println("KeyTyped at " + ((LabelWidget) widget).getLabel());
            return State.REJECTED;
        }
        
    }
    
    private static class LabelEditor implements TextFieldInplaceEditor {
        
        public boolean isEnabled(Widget widget) {
            return true;
        }

        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }
        
        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
        }
        
    }
    
}
