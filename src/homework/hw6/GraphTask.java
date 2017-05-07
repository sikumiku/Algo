package homework.hw6;

import java.util.*;

/**
 * Container class to different classes, that makes the whole
 * numberhashset of classes one class formally.
 * Kasutatud materjal:
 * https://www.quora.com/How-do-I-calculate-the-number-of-edges-in-a-complete-graph
 * http://www.math.ucsd.edu/~fan/graphdraw/llu/graphproduct/Graph.java
 * http://enos.itcollege.ee/~jpoial/oop/naited/GraphSource.java
 * http://stackoverflow.com/questions/11871520/how-can-i-read-input-from-the-console-using-the-scanner-class-in-java
 * http://stackoverflow.com/questions/3059333/validating-input-using-java-util-scanner
 */
public class GraphTask {

    /**
     * Main method.
     */
    public static void main(String[] args) {
        GraphTask a = new GraphTask();
        a.run();
    }

    /**
     * Actual main method to run examples and everything.
     */
    public void run() {
        System.out.println("Programm loob esimesena kasutaja määratud tippude ja servadega lihtgraafi, siis loob sellele vastava täiendgraafi ning jooksutab erinevaid teste.");
        System.out.println("Suvalise lihtgraafi kasutamiseks tuleb anda meetodile createRandomSimpleGraph() ette tippude ja servade arv.");
        System.out.println("Täiendgraafi kasutamiseks tuleb anda meetodile createComplementGraphforSimpleGraph() ette Graph andmetüüpi lihtgraaf.");
        Scanner kasutaja = new Scanner(System.in);
        System.out.println("Sisesta tippude summa");
        if (kasutaja.hasNextInt()) {
        } else {
            throw new RuntimeException("Sisestus peab olema arv.");
        }
        int sisestus1 = kasutaja.nextInt();
        System.out.println("Sisesta servade summa");
        if (kasutaja.hasNextInt()) {
        } else {
            throw new RuntimeException("Sisestus peab olema arv.");
        }
        int sisestus2 = kasutaja.nextInt();

        Graph g = new Graph("G");
        g.createRandomSimpleGraph(sisestus1, sisestus2);
        System.out.println(g);
        Graph g11 = new Graph("G11");
        g11.createComplementGraphforSimpleGraph(g);
        System.out.println(g11);

        Graph g2 = new Graph("G");
        g2.createRandomSimpleGraph(6, 9);
        System.out.println(g2);
        Graph g22 = new Graph("G22");
        g22.createComplementGraphforSimpleGraph(g2);
        System.out.println(g22);

        //6 different tests
        testComplementEdgeCountwithCorrectCount(8, 10);

        compareRandomFirstEdgeinComplementGraph(6, 9);

        testIfEdgesDifferinSimpleandComplementGraph();

        testComplementGraphEdgeCountWithMinimumVerticesandEdges();

        testWithMaximumVerticesandEdges();

//        testComplementEdgeCountwithIncorrectCount(6, 9);
    }

    /**
     * Koostada meetod etteantud lihtgraafi täiendgraafi leidmiseks.
     */
    class Vertex {

        private String id;
        private Vertex next;
        private Arc first;
        private int info = 0;

        // You can add more fields, if needed

        Vertex(String s, Vertex v, Arc e) {
            id = s;
            next = v;
            first = e;
        }

        Vertex(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }

        // TODO!!! Your Vertex methods here!
    }


    /**
     * Arc represents one arrow in the graph. Two-directional edges are
     * represented by two Arc objects (for both directions).
     */
    class Arc {

        private String id;
        private Vertex target;
        private Arc next;
        private int info = 0;
        // You can add more fields, if needed

        Arc(String s, Vertex v, Arc a) {
            id = s;
            target = v;
            next = a;
        }

        Arc(String s) {
            this(s, null, null);
        }

        @Override
        public String toString() {
            return id;
        }

        // TODO!!! Your Arc methods here!
    }


    class Graph {

        private String id;
        private Vertex first;
        private int info = 0;
        private int[][] graphconnection;
        private int vertexcount = 0;
        private Vertex[] vertarray;
        // You can add more fields, if needed

        Graph(String s, Vertex v) {
            id = s;
            first = v;
        }

        Graph(String s) {
            this(s, null);
        }

        @Override
        public String toString() {
            String nl = System.getProperty("line.separator");
            StringBuffer sb = new StringBuffer(nl);
            sb.append(id);
            sb.append(nl);
            Vertex v = first;
            while (v != null) {
                sb.append(v.toString());
                sb.append(" -->");
                Arc a = v.first;
                while (a != null) {
                    sb.append(" ");
                    sb.append(a.toString());
                    sb.append(" (");
                    sb.append(v.toString());
                    sb.append("->");
                    sb.append(a.target.toString());
                    sb.append(")");
                    a = a.next;
                }
                sb.append(nl);
                v = v.next;
            }
            return sb.toString();
        }

        public Vertex createVertex(String vid) {
            Vertex res = new Vertex(vid);
            res.next = first;
            first = res;
            return res;
        }

        public Arc createArc(String aid, Vertex from, Vertex to) {
            Arc res = new Arc(aid);
            res.next = from.first;
            from.first = res;
            res.target = to;
            return res;
        }

        /**
         * Create a connected undirected random tree with n vertices.
         * Each new vertex is connected to some random existing vertex.
         *
         * @param n number of vertices added to this graph
         */
        public void createRandomTree(int n) {
            if (n <= 0)
                return;
            Vertex[] varray = new Vertex[n];
            for (int i = 0; i < n; i++) {
                varray[i] = createVertex("v" + String.valueOf(n - i));
                if (i > 0) {
                    int vnr = (int) (Math.random() * i);
                    createArc("a" + varray[vnr].toString() + "_"
                            + varray[i].toString(), varray[vnr], varray[i]);
                    createArc("a" + varray[i].toString() + "_"
                            + varray[vnr].toString(), varray[i], varray[vnr]);
                } else {
                }
            }
        }

        /**
         * Create Tree for new custom graph
         *
         */
        public void createTree(int n) {
            if (n <= 0)
                return;
            Vertex[] varray = new Vertex[n];
            for (int i = 0; i < n; i++) {
                varray[i] = createVertex("v" + String.valueOf(n - i));
            }
        }

        /**
         * Create an adjacency matrix of this graph.
         * Side effect: corrupts info fields in the graph
         *
         * @return adjacency matrix
         */
        public int[][] createAdjMatrix() {
            info = 0;
            Vertex v = first;
            while (v != null) {
                v.info = info++;
                v = v.next;
            }
            int[][] res = new int[info][info];
            v = first;
            while (v != null) {
                int i = v.info;
                Arc a = v.first;
                while (a != null) {
                    int j = a.target.info;
                    res[i][j]++;
                    a = a.next;
                }
                v = v.next;
            }
            return res;
        }

        /**
         * Create a connected simple (undirected, no loops, no multiple
         * arcs) random graph with n vertices and m edges.
         *
         * @param n number of vertices
         * @param m number of edges
         */
        public void createRandomSimpleGraph(int n, int m) {
            if (n <= 0)
                throw new IllegalArgumentException("No vertices.");
            if (n > 2500)
                throw new IllegalArgumentException("Too many vertices: " + n);
            if (m < n - 1 || m > n * (n - 1) / 2)
                throw new IllegalArgumentException
                        ("Impossible number of edges: " + m);
            first = null;
            createRandomTree(n);       // n-1 edges created here
            Vertex[] vert = new Vertex[n];
            Vertex v = first;
            int c = 0;
            while (v != null) {
                vert[c++] = v;
                vertexcount++;
                v = v.next;
            }
            vertarray = vert;
            int[][] connected = createAdjMatrix();
            int edgeCount = m - n + 1;  // remaining edges
            while (edgeCount > 0) {
                int i = (int) (Math.random() * n);  // random source
                int j = (int) (Math.random() * n);  // random target
                if (i == j)
                    continue;  // no loops
                if (connected[i][j] != 0 || connected[j][i] != 0)
                    continue;  // no multiple edges
                Vertex vi = vert[i];
                Vertex vj = vert[j];
                createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
                connected[i][j] = 1;
                createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
                connected[j][i] = 1;
                edgeCount--;  // a new edge happily created
            }
            graphconnection = connected;

        }

        /**
         * Create a complement graph for the randomly created simple graph.
         *
         * @param g - randomly created simple graph
         */
        public void createComplementGraphforSimpleGraph(Graph g) {
            this.id = "Ĝ";
            this.graphconnection = g.graphconnection;
            this.vertexcount = g.vertexcount;

            createTree(g.vertexcount);

            Vertex[] vertarray = new Vertex[vertexcount];
            this.vertarray = vertarray;
            Vertex v = first;
            int c = 0;
            while (v != null) {
                vertarray[c++] = v;
                v = v.next;
            }

            int i;
            int j;
            for (i = 0; i < vertexcount; i++) {
                for (j = 0; j < vertexcount; j++) {
                    if (i == j)
                        continue;
                    if (g.graphconnection[i][j] == 1 || g.graphconnection[j][i] == 1) {

                        this.graphconnection[i][j] = 2;
                        this.graphconnection[j][i] = 2;
                    }
                }
            }

            for (i = 0; i < vertexcount; i++) {
                for (j = 0; j < vertexcount; j++) {
                    if (i == j)
                        continue;
                    if (g.graphconnection[i][j] == 0 || g.graphconnection[j][i] == 0) {
                        Vertex vi = vertarray[i];
                        Vertex vj = vertarray[j];
                        createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);
                        this.graphconnection[i][j] = 1;
                        createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);
                        this.graphconnection[j][i] = 1;

                    }
                }
            }

        }

    }

    /**
     * Test if the amount of Arcs in simple and its complement graph
     * add up to the amount of Arcs in a complete graph with same
     * amount of vertices.
     *
     * @param n number of vertices
     * @param m number of edges
     */
    public void testComplementEdgeCountwithCorrectCount(int n, int m) {
        Graph g1 = new Graph("G1");
        if (n <= 0)
            throw new IllegalArgumentException("No vertices.");
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);

        g1.createRandomSimpleGraph(n, m);
        int[][] conn1 = g1.graphconnection;
        int count1 = 0;
        int i;
        int j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn1[i][j] == 1 || conn1[j][i] == 1) {
                    count1++;
                }
            }
        }
        Graph g11 = new Graph("G11");
        g11.createComplementGraphforSimpleGraph(g1);
        int[][] conn2 = g11.graphconnection;
        int count2 = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn2[i][j] == 1 || conn2[j][i] == 1) {

                    count2++;
                }
            }
        }
        int result = count2 + count1;
        int edgecount = n * (n - 1);
        if (result == edgecount) {
            System.out.println("Lihtgraafi ja täiendgraafi servade summa võrdub maksimaalse servade arvuga.");
        } else {
            throw new RuntimeException("Lihtgraafi ja täiendgraafi servade summa peab võrduma maksimaalse servade arvuga!");
        }
    }

    /**
     * Test if the amount of Arcs in simple and its complement graph
     * add up to the amount of Arcs in a complete graph with same
     * amount of vertices if the connected vertices were removed in
     * complement graph.
     *
     * @param n number of vertices
     * @param m number of edges
     */
    public void testComplementEdgeCountwithIncorrectCount(int n, int m) {
        Graph g2 = new Graph("G2");
        if (n <= 0)
            throw new IllegalArgumentException("No vertices.");
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);
        g2.createRandomSimpleGraph(n, m);
        int[][] conn1 = g2.graphconnection;
        int count1 = 0;
        int i;
        int j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn1[i][j] == 1 || conn1[j][i] == 1) {
                    count1++;
                }
            }
        }
        Graph g22 = new Graph("G22");
        g22.createComplementGraphforSimpleGraph(g2);
        Vertex[] vert = g22.vertarray;
        int[][] conn2 = g22.graphconnection;
        int count2 = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn2[i][j] == 0 || conn2[j][i] == 0) {
                    Vertex vi = vert[i];
                    Vertex vj = vert[j];
                    vi.first.target = null;
                    vi.first = vi.first.next;
                    vj.first.target = null;
                    vj.first = vj.first.next;
                    conn2[i][j] = 1;
                    conn2[j][i] = 1;
                    count2++;
                }
            }
        }
        int result = count2 + count1;
        int edgecount = n * (n - 1);
        if (result == edgecount) {
            System.out.println("Lihtgraafi ja täiendgraafi servade summa võrdub maksimaalse servade arvuga.");
        } else {
            throw new RuntimeException("Lihtgraafi ja täiendgraafi servade summa peab võrduma maksimaalse servade arvuga!");
        }
    }

    /**
     * Find a random vertex in a random simple graph and
     * check if its first element in graph is the same as in
     * its complement graph.
     *
     * @param n number of vertices
     * @param m number of edges
     */
    public void compareRandomFirstEdgeinComplementGraph(int n, int m) {
        Graph g3 = new Graph("G3");
        int i = (int) (Math.random() * n);

        g3.createRandomSimpleGraph(n, m);
        Vertex[] vert1 = g3.vertarray;
        Vertex vi1 = vert1[i];
        Arc e1 = vi1.first;

        Graph g33 = new Graph("G33");
        g33.createComplementGraphforSimpleGraph(g3);
        Vertex[] vert2 = g33.vertarray;
        Vertex vi2 = vert2[i];
        Arc e2 = vi2.first;

        if (e1 != e2) {
            System.out.println("Element täiendgraafis erineb samal kohal asuvas elemendist algses lihtgraafis.");
        } else {
            throw new RuntimeException("Elemendid samal kohal täiendgraafis ja lihtgraafis ei tohi olla samad!");
        }
    }

    /**
     * Create a random simple graph with 3 vertices and 2 edges
     * and create a complement graph for it. Test if each edge in
     * both graphs differ from each other.
     *
     */
    public void testIfEdgesDifferinSimpleandComplementGraph() {
        Graph g4 = new Graph("G");
        Arc e1 = null;
        Arc e2 = null;
        Arc e3 = null;
        Arc e4 = null;
        Arc e5 = null;
        Arc e6 = null;
        Arc e7 = null;
        Arc e8 = null;
        Arc e9 = null;
        Arc e10 = null;
        Arc e11 = null;
        Arc e12 = null;
        g4.createRandomSimpleGraph(3, 2);
        System.out.println(g4);
        Vertex[] vert = g4.vertarray;
        Vertex vi = vert[0];
        Vertex vj = vert[1];
        Vertex vk = vert[2];

        if (vi.first != null) {
            e1 = vi.first;
            if (vi.first.next != null) {
                e2 = vi.first.next;
            }
        }

        if (vj.first != null) {
            e3 = vi.first;
            if (vj.first.next != null) {
                e4 = vj.first.next;
            }
        }

        if (vk.first != null) {
            e5 = vi.first;
            if (vk.first.next != null) {
                e6 = vk.first.next;
            }
        }

        Graph g44 = new Graph("G44");
        g44.createComplementGraphforSimpleGraph(g4);
        System.out.println(g44);
        Vertex[] vert2 = g44.vertarray;
        Vertex vl = vert2[0];
        Vertex vm = vert2[1];
        Vertex vn = vert2[2];

        if (vl.first != null) {
            e7 = vl.first;
            if (vl.first.next != null) {
                e8 = vl.first.next;
            }
        }

        if (vm.first != null) {
            e9 = vl.first;
            if (vm.first.next != null) {
                e10 = vm.first.next;
            }
        }

        if (vn.first != null) {
            e11 = vl.first;
            if (vn.first.next != null) {
                e12 = vn.first.next;
            }
        }

        if (e1 != null && e2 != null) {
            if (e1 != e7 && e2 != e8) {
                System.out.println("Esimese tipu servad lihtgraafis erinevad mõlemast täiendgraafi esimese tipu servadest.");
            }
        } else if (e1 != e7 || e2 != e8) {
            System.out.println("Esimese tipu serv lihtgraafis erineb esimese tipu servast täiendgraafis.");
        } else {
            throw new RuntimeException("Esimese tipu servad lihtgraafis ei tohi olla samad, mis täiendgraafis!");
        }

        if (e3 != null && e4 != null) {
            if (e3 != e9 && e4 != e10) {
                System.out.println("Teise tipu servad lihtgraafis erinevad mõlemast täiendgraafi teise tipu servadest.");
            }
        } else if (e3 != e9 || e4 != e10) {
            System.out.println("Teise tipu serv lihtgraafis erineb teise tipu servast täiendgraafis.");
        } else {
            throw new RuntimeException("Teise tipu servad lihtgraafis ei tohi olla samad, mis täiendgraafis!");
        }

        if (e5 != null && e6 != null) {
            if (e5 != e11 && e6 != e12) {
                System.out.println("Kolmanda tipu servad lihtgraafis erinevad mõlemast täiendgraafi kolmanda tipu servadest.");
            }
        } else if (e5 != e11 || e6 != e12) {
            System.out.println("Kolmamnda tipu serv lihtgraafis erineb kolmanda tipu servast täiendgraafis.");
        } else {
            throw new RuntimeException("Kolmanda tipu servad lihtgraafis ei tohi olla samad, mis täiendgraafis!");
        }

    }

    /**
     * Test how long it takes to create a complement
     * graph with maximum amount of vertices and edges.
     *
     */
    public void testWithMaximumVerticesandEdges() {
        Graph g5 = new Graph("G3");
        g5.createRandomSimpleGraph(2500, 3123750);

        long t0 = System.currentTimeMillis();
        Graph g55 = new Graph("G55");
        g55.createComplementGraphforSimpleGraph(g5);
        long t1 = System.currentTimeMillis();
        int delta = (int) (t1 - t0);
        System.out.println("Maksimaalne tippude arv on 2500 ja maksimum sildade arv on 3123750.");
        System.out.println("Suurima võimaliku täiendgraafi loomine võttis aega: " + delta + " ms");
    }

    /**
     * Test complement graph edge count when simple graph
     * has minimal amount of vertices and edges.
     *
     */
    public void testComplementGraphEdgeCountWithMinimumVerticesandEdges() {
        Graph g6 = new Graph("G");

        g6.createRandomSimpleGraph(1, 0);
        System.out.println(g6);
        Graph g66 = new Graph("G66");
        g66.createComplementGraphforSimpleGraph(g6);
        System.out.println(g66);
        Vertex[] vert = g66.vertarray;
        Vertex vi = vert[0];
        Arc e1 = vi.first;

        if (e1 == null) {
            System.out.println("Nulli servaga ühetipulisel lihtgraafi täiendgraafil ei saagi tekkida vastanduvat serva.");
        } else {
            throw new RuntimeException("Nulli servaga ühetipulise lihtgraafi täiendgraaf ei tohi omada serva.");
        }
    }

}

