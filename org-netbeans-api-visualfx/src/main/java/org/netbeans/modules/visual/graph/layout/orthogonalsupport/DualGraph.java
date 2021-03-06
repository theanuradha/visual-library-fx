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
package org.netbeans.modules.visual.graph.layout.orthogonalsupport;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.netbeans.modules.visual.graph.layout.orthogonalsupport.MGraph.Edge;
import org.netbeans.modules.visual.graph.layout.orthogonalsupport.MGraph.Vertex;

/**
 *
 * @author ptliu
 */
public class DualGraph {

	private EmbeddedPlanarGraph originalGraph;
	private Map<Face, FaceVertex> vertexMap;
	private Collection<FaceVertex> vertices;
	private Map<Edge, FaceEdge> edgeMap;
	private Collection<FaceEdge> edges;
	private Collection<Edge> edgesToIgnore;
	private Collection<Face> facesToIgnore;

	/**
	 * 
	 * @param graph
	 * @param facesToIgnore
	 * @param edgesToIgnore
	 * @return
	 */
	public static DualGraph createGraph(EmbeddedPlanarGraph graph, Collection<Face> facesToIgnore,
			Collection<Edge> edgesToIgnore) {
		DualGraph dualGraph = new DualGraph(graph, facesToIgnore, edgesToIgnore);
		dualGraph.createGraph();

		return dualGraph;
	}

	/**
	 * 
	 * @param graph
	 * @param facesToIgnore
	 * @param edgesToIgnore
	 */
	private DualGraph(EmbeddedPlanarGraph graph, Collection<Face> facesToIgnore, Collection<Edge> edgesToIgnore) {
		this.originalGraph = graph;
		this.facesToIgnore = facesToIgnore;
		this.edgesToIgnore = edgesToIgnore;

		vertexMap = new HashMap<Face, FaceVertex>();
		vertices = new ArrayList<FaceVertex>();
		edgeMap = new HashMap<Edge, FaceEdge>();
		edges = new ArrayList<FaceEdge>();
	}

	/**
	 * 
	 */
	private void createGraph() {
		createFaces();
		createEdges();
	}

	/**
	 * 
	 */
	private void createFaces() {
		for (Face f : originalGraph.getFaces()) {
			// never ignore the outer face
			if (!facesToIgnore.contains(f) || f.isOuterFace()) {
				getVertex(f);
			}
		}
	}

	/**
	 * 
	 */
	private void createEdges() {
		for (FaceVertex fv : getVertices()) {
			for (FaceVertex gv : getVertices()) {
				if (fv == gv) {
					continue;
				}
				for (Edge e : fv.getFace().getEdges()) {
					if (edgesToIgnore.contains(e)) {
						continue;
					}
					if (gv.getFace().containsEdge(e)) {
						FaceEdge faceEdge = getEdge(fv, gv, e);
						fv.addEdge(faceEdge);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	public void updateFaces() {
		int faceCount = originalGraph.getFaces().size();
		int vertexCount = vertices.size();

		if (faceCount > vertexCount) {
			createFaces();
		} else if (faceCount < vertexCount) {
			vertices.clear();
			vertexMap.clear();
			createFaces();
		}
	}

	/**
	 * 
	 */
	public void updateEdges() {
		edges.clear();
		edgeMap.clear();

		for (FaceVertex fv : getVertices()) {
			fv.getEdges().clear();
		}

		createEdges();
	}

	/**
	 * 
	 * @return
	 */
	public EmbeddedPlanarGraph getOriginalGraph() {
		return originalGraph;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<FaceVertex> getVertices() {
		return vertices;
	}

	/**
	 * 
	 * @return
	 */
	public Collection<FaceEdge> getEdges() {
		return edges;
	}

	/**
	 * 
	 * @param face
	 * @return
	 */
	private FaceVertex getVertex(Face face) {
		FaceVertex vertex = vertexMap.get(face);

		if (vertex == null) {
			vertex = new FaceVertex(face);
			vertexMap.put(face, vertex);
			vertices.add(vertex);
		}

		return vertex;
	}

	/**
	 * 
	 * @param f
	 * @param g
	 * @param e
	 * @return
	 */
	private FaceEdge getEdge(FaceVertex f, FaceVertex g, Edge e) {
		FaceEdge edge = edgeMap.get(e);

		if (edge == null) {
			edge = new FaceEdge(f, g, e);
			edgeMap.put(e, edge);
			edges.add(edge);
		}

		return edge;
	}

	/**
	 * TODO: need to optimize
	 * 
	 * @param e
	 * @return
	 */
	public Collection<FaceVertex> getVerticesBorderingEdge(Edge e) {
		Collection<FaceVertex> result = new ArrayList<FaceVertex>();

		for (FaceVertex v : getVertices()) {
			if (v.getFace().containsEdge(e)) {
				result.add(v);
			}
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	public String toString() {
		String s = "DualGraph:\n";

		s = s + "vertices:\n";
		for (FaceVertex fv : vertices) {
			s = s + "\t" + fv + "\n";
		}

		s = s + "edges\n";
		for (FaceEdge fe : edges) {
			s = s + "\t" + fe + "\n";
		}

		return s;
	}

	/**
	 * 
	 */
	public class FaceVertex {

		private Face face;
		private Collection<FaceEdge> edges;

		/**
		 * 
		 * @param face
		 */
		public FaceVertex(Face face) {
			this.face = face;
			edges = new LinkedHashSet<FaceEdge>();
		}

		/**
		 * 
		 * @return
		 */
		public Face getFace() {
			return face;
		}

		/**
		 * 
		 * @return
		 */
		public Collection<FaceEdge> getEdges() {
			return edges;
		}

		/**
		 * 
		 * @param edge
		 */
		public void addEdge(FaceEdge edge) {
			if (!edges.contains(edge)) {
				edges.add(edge);
			}
		}

		/**
		 * 
		 * @return
		 */
		public String toString() {
			return "FaceVertex: " + face.toString();
		}
	}

	/**
	 * 
	 */
	public class FaceEdge {

		private FaceVertex f;
		private FaceVertex g;
		private Edge edge;

		/**
		 * 
		 * @param f
		 * @param g
		 * @param e
		 */
		public FaceEdge(FaceVertex f, FaceVertex g, Edge e) {
			this.f = f;
			this.g = g;
			this.edge = e;
		}

		/**
		 * 
		 * @return
		 */
		public FaceVertex getF() {
			return f;
		}

		/**
		 * 
		 * @return
		 */
		public FaceVertex getG() {
			return g;
		}

		/**
		 * 
		 * @return
		 */
		public Edge getEdge() {
			return edge;
		}

		/**
		 * 
		 * @param v
		 * @return
		 */
		public boolean contains(FaceVertex v) {
			return (f == v) || (g == v);
		}

		/**
		 * 
		 * @param v
		 * @return
		 */
		public FaceVertex getOppositeVertex(FaceVertex v) {
			if (v == f) {
				return g;
			} else if (v == g) {
				return f;
			}

			return null;
		}

		/**
		 * 
		 * @param v
		 * @return
		 */
		public FaceVertex getVertex(Vertex v) {
			if (f.face.containsVertex(v)) {
				return f;
			}
			if (g.face.containsVertex(v)) {
				return g;
			}
			return null;
		}

		/**
		 * 
		 * @return
		 */
		@Override
		public String toString() {
			String s = "FaceEdge:\n";
			s = s + "\t" + f + "\n";
			s = s + "\t" + g + "\n";
			s = s + "\t" + edge + "\n";

			return s;
		}
	}
}
