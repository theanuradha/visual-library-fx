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
package test.model;

import org.netbeans.api.visual.model.ObjectScene;

/**
 * @author David Kaspar
 */
public class FindStoredObjectTest {

    public static void main (String[] args) {
        ObjectScene scene = new ObjectScene ();

        scene.addObject (new Obj (1));
        scene.addObject (new Obj (2));
        scene.addObject (new Obj (3));
        scene.addObject (new Obj (4));
        scene.addObject (new Obj (5));

        System.out.println ("Searching for stored obj 5:");
        System.out.println ("found: " + scene.findStoredObject (new Obj (5)));
        System.out.println ("Searching for stored obj 99999:");
        System.out.println ("found: " + scene.findStoredObject (new Obj (99999)));
    }

    private static class Obj {

        private int value;

        public Obj (int value) {
            this.value = value;
        }

        public int hashCode () {
            return value;
        }

        public boolean equals (Object obj) {
            System.out.println ("Comparing: " + this + " with " + obj);
            return obj instanceof Obj  &&  this.value == ((Obj) obj).value;
        }

        public String toString () {
            return "Obj[" + System.identityHashCode (this) + "|" + value + "]"; // NOI18N
        }

    }

}
