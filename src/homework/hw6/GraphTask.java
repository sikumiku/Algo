package homework.hw6;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Container class to different classes, that makes the whole
 * set of classes one class formally.
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
        Scanner kasutaja = new Scanner(System.in);
        System.out.println("Sisesta vertexite suurus");
        int sisestus1 = kasutaja.nextInt();
        System.out.println("Sisesta servade suurus");
        int sisestus2 = kasutaja.nextInt();

        Graph g = new Graph("G");
        g.createRandomSimpleGraph(sisestus1, sisestus2);
        System.out.println(g);
        g.createComplementaryGraphforSimpleGraph();
        System.out.println(g);

        testComplementEdgeCountwithCorrectCount(8, 10);

//        testComplementEdgeCountwithIncorrectCount();

        testRandomElementinComplementGraph(6, 9);

        testIfEdgesDifferinSimpleandComplementGraph();

        testWith5000VertixesandEdges();

    }

    // TODO!!! add javadoc relevant to your problem
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

        public void createComplementaryGraphforSimpleGraph() {
            this.id = "Ĝ";
            int[][] conn = graphconnection;
            int count = vertexcount;
            Vertex[] vert = vertarray;
            int i;
            int j;
            for (i = 0; i < count; i++) {
                for (j = 0; j < count; j++) {
                    if (i == j)
                        continue;
                    if (conn[i][j] == 1 || conn[j][i] == 1) {
                        Vertex vi = vert[i];
                        Vertex vj = vert[j];
                        vi.first.target = null;
                        vi.first = vi.first.next;
                        vj.first.target = null;
                        vj.first = vj.first.next;
                        conn[i][j] = 2;
                        conn[j][i] = 2;
                    }


                }
            }
            for (i = 0; i < count; i++) {
                for (j = 0; j < count; j++) {
                    if (i == j)
                        continue;
                    if (conn[i][j] == 0 || conn[j][i] == 0) {
                        Vertex vi = vert[i];
                        Vertex vj = vert[j];
                        createArc("a" + vi.toString() + "_" + vj.toString(), vi, vj);

                        conn[i][j] = 1;
                        createArc("a" + vj.toString() + "_" + vi.toString(), vj, vi);

                        conn[j][i] = 1;
                    }
                }
            }

        }

    }

    public void testComplementEdgeCountwithCorrectCount(int n, int m) {
        Graph g1 = new Graph("G1");
        if (n <= 0)
            throw new IllegalArgumentException("No vertices.");
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);

        g1.createRandomSimpleGraph(n,m);
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
        g1.createComplementaryGraphforSimpleGraph();
        int count2 = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn1[i][j] == 1 || conn1[j][i] == 1) {

                    count2++;
                }
            }
        }
        int result = count2+count1;
        int edgecount = n*(n-1);
        if (result == edgecount) {
            System.out.println("Lihtgraafi ja t2iendgraafi servade summa v6rdub maksimaalse servade arvuga.");
        } else {
            throw new RuntimeException("Lihtgraafi ja t2iendgraafi servade summa peab v6rduma maksimaalse servade arvuga!");
        }
    }

    public void testComplementEdgeCountwithIncorrectCount(int n, int m) {
        Graph g2 = new Graph("G2");
        if (n <= 0)
            throw new IllegalArgumentException("No vertices.");
        if (n > 2500)
            throw new IllegalArgumentException("Too many vertices: " + n);
        if (m < n - 1 || m > n * (n - 1) / 2)
            throw new IllegalArgumentException
                    ("Impossible number of edges: " + m);

        g2.createRandomSimpleGraph(n,m);
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
        g2.createComplementaryGraphforSimpleGraph();
        Vertex[] vert = g2.vertarray;
        int count2 = 0;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                if (i == j)
                    continue;
                if (conn1[i][j] == 1 || conn1[j][i] == 1) {
                    Vertex vi = vert[i];
                    Vertex vj = vert[j];
                    vi.first.target = null;
                    vi.first = vi.first.next;
                    vj.first.target = null;
                    vj.first = vj.first.next;
                    conn1[i][j] = 2;
                    conn1[j][i] = 2;
                    count2++;
                }
            }
        }
        int result = count2+count1;
        int edgecount = n*(n-1);
        if (result == edgecount) {
            System.out.println("Lihtgraafi ja t2iendgraafi servade summa v6rdub maksimaalse servade arvuga.");
        } else {
            throw new RuntimeException("Lihtgraafi ja t2iendgraafi servade summa peab v6rduma maksimaalse servade arvuga!");
        }
    }

    public void testRandomElementinComplementGraph(int n, int m) {
        Graph g3 = new Graph("G3");

        g3.createRandomSimpleGraph(n,m);
        Vertex[] vert = g3.vertarray;
        int i = (int) (Math.random() * n);
        Vertex vi = vert[i];
        Arc e1 = vi.first;

        g3.createComplementaryGraphforSimpleGraph();

        Arc e2 = vi.first;

        if (e1 != e2) {
            System.out.println("Element t2iendgraafis erineb samal kohal asuvas elemendist algses lihtgraafis.");
        } else {
            throw new RuntimeException("Elemendid samal kohal t2iendgraafis ja lihtgraafis ei tohi olla samad!");
        }

    }

    public void testIfEdgesDifferinSimpleandComplementGraph() {
        Graph g4 = new Graph("G4");
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
        g4.createRandomSimpleGraph(3,2);
        System.out.println(g4);
        Vertex[] vert = g4.vertarray;
        Vertex vi = vert[0];
        if (vi.first != null) {
            e1 = vi.first;
            if (vi.first.next != null) {
                e2 = vi.first.next;
            }
        }
        Vertex vj = vert[1];
        if(vj.first != null) {
            e3 = vi.first;
            if (vj.first.next != null) {
                e4 = vj.first.next;
            }
        }

        Vertex vk = vert[2];
        if(vk.first != null) {
            e5 = vi.first;
            if (vk.first.next != null) {
                e6 = vk.first.next;
            }
        }

        g4.createComplementaryGraphforSimpleGraph();
        System.out.println(g4);
        if (vi.first != null) {
            e7 = vi.first;
            if (vi.first.next != null) {
                e8 = vi.first.next;
            }
        }

        if(vj.first != null) {
            e9 = vi.first;
            if (vj.first.next != null) {
                e10 = vj.first.next;
            }
        }

        if(vk.first != null) {
            e11 = vi.first;
            if (vk.first.next != null) {
                e12 = vk.first.next;
            }
        }

        if (e1 != null && e2 != null) {
            if (e1 != e7 && e2 != e8) {
                System.out.println("Esimese tipu sillad lihtgraafis erinevad m6lemast t2iendgraafi esimese tipu sildadest.");
            }
        } else if (e1 != e7) {
            System.out.println("Esimese tipu sild lihtgraafis erineb esimese tipu sillast t2iendgraafis.");
        }

        if (e3 != null && e4 != null) {
            if (e3 != e9 && e4 != e10) {
                System.out.println("Teise tipu sillad lihtgraafis erinevad m6lemast t2iendgraafi teise tipu sildadest.");
            }
        } else if (e3 != e9) {
            System.out.println("Teise tipu sild lihtgraafis erineb teise tipu sillast t2iendgraafis.");
        }

        if (e5 != null && e6 != null) {
            if (e5 != e11 && e6 != e12) {
                System.out.println("Kolmanda tipu sillad lihtgraafis erinevad m6lemast t2iendgraafi kolmanda tipu sildadest.");
            }
        } else if (e5 != e11) {
            System.out.println("Kolmamnda tipu sild lihtgraafis erineb kolmanda tipu sillast t2iendgraafis.");
        }

    }

    public void testWith5000VertixesandEdges() {
        Graph g3 = new Graph("G3");

        g3.createRandomSimpleGraph(5000,5000);

        long t0 = System.currentTimeMillis();
        g3.createComplementaryGraphforSimpleGraph();
        long t1 = System.currentTimeMillis();
        int delta = (int)(t1-t0);
        System.out.println ("Creating complementary Graph took: " + delta + " ms");
    }
}

