import java.util.*;
import java.util.Map.*;
/**
 * Write a description of class Pather here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Pather
{

    public static void main()
    {
        int[][] matrix = {
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 4},
                {0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0},
                {0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1},
                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 1},
                {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 1, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0},
                {3, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            };

        Graph g = matrixToGraph(matrix);
        Node start = findStart(g);
        if (start == null)
        {
            System.out.println("A start point was not encountered");
        }
        Node end = findEnd(g);
        if (end == null)
        {
            System.out.println("An end point was not encountered");
        }
        Node result = calculateShortestPathFromSource(g, start, end);
        if (result == null)
        {
            System.out.println("A result point was not encountered");
        }
        if(end == result)
        {
            System.out.println("The algorithm stopped on intended node: " + end.getName());
            printPath(result);
            setActivePath(result);
            visualizeGraph(g);
        }
        else
        {
            System.out.println("The algorithm did not stop on the intended node: expected- " + end.getName() + " actual- " + result.getName());
        }

        
    }
    
    private static void setActivePath(Node n)
    {
        for(Node node : n.getShortestPath())
        {
            node.value = 2;
        }
    }

    private static void printPath(Node n)
    {
        for(Node node : n.getShortestPath())
        {
            System.out.println(node.getName());
        }
    }

    private static void visualizeGraph(Graph g)
    {
        int[][] matrix = graphToIntegerMatrix(g);
        printMap(matrix);
        MatrixIllustrator.drawMatrix(matrix);
    }

    private static Node findStart(Graph g)
    {
        for(Node n : g.getNodes())
        {
            //System.out.println(n.value);
            if (n.value == 3)
            {
                return n;
            }
        }
        return null;
    }

    private static Node findEnd(Graph g)
    {
        for(Node n : g.getNodes())
        {
            if (n.value == 4)
            {
                return n;
            }
        }
        return null;
    }

    /*
     * given a matrix, convert every point on the matrix to a node and assemble a graph out of it, and connect adjacent nodes.
     */
    private static Graph matrixToGraph(int[][] matrix)
    {
        Graph g = new Graph();
        Set<Node> nodes = g.getNodes();
        Node n;
        for(int i = 0; i < matrix.length; i ++)
        {
            for(int j = 0; j < matrix[0].length; j++)
            {
                int val = matrix[i][j];
                if (val == 1)
                {
                    continue;
                }
                String name = j + "-" + i;
                n = new Node(name);
                n.x = j;
                n.y = i;
                n.value = val;
                nodes.add(n);
            }
        }
        System.out.println("made " + nodes.size() + " nodes");
        connectAdjacents(g);

        return g;
    }

    private static void connectAdjacents(Graph g)
    {
        Node[][] nodes = graphToNodeMatrix(g);

        for(int y = 0; y < nodes.length; y++)
        {
            for(int x = 0; x < nodes[0].length; x++)
            {
                Node n = nodes[y][x], n2;
                if (n == null)
                {
                    continue;
                }
                if (x > 0)
                {
                    //add left neighbor to adjacent
                    if (!(nodes[y][x-1] == null))
                    {
                        n.adjacentNodes.put(nodes[y][x-1], 1); 
                    }
                }
                if (x < nodes[0].length - 1)
                {
                    //add right neighbor to adjacent
                    if (!(nodes[y][x+1] == null))
                    {
                        n.adjacentNodes.put(nodes[y][x+1], 1); 
                    }
                }
                if (y > 0)
                {
                    if (!(nodes[y-1][x] == null))
                    {
                        //add above neighbor to adjacent
                        n.adjacentNodes.put(nodes[y-1][x], 1); 
                    }
                }
                if (y < nodes.length - 1)
                {
                    if (!(nodes[y+1][x] == null))
                    {
                        //add below neighbor to adjacent
                        n.adjacentNodes.put(nodes[y+1][x], 1); 
                    }
                }
            }
        }

    }

    private static Node[][] graphToNodeMatrix(Graph g)
    {
        String[] range = findRange(g).split("-");
        int x = Integer.parseInt(range[0]), y = Integer.parseInt(range[1]);
        Node[][] matrix = new Node[y][x];

        for(Node n : g.getNodes())
        {
            int x2 = n.x, y2 = n.y;

            matrix[y2][x2] = n;
        }

        /*
        for(int i = 0; i < matrix.length; i++)
        {
        for(int j = 0; j < matrix[0].length; j++)
        {
        if (matrix[i][j] == null)
        {
        System.out.println("Hole in matrix detected.");
        }
        }
        }
         */

        return matrix;
    }

    /*
     * with a matrix initialized at 1(wall) for all values, iterate through a graph and replace coordinates with true values from graph
     */
    private static int[][] graphToIntegerMatrix(Graph g)
    {
        String[] range = findRange(g).split("-");
        int x = Integer.parseInt(range[0]), y = Integer.parseInt(range[1]);

        int[][] matrix = new int[y][x];

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[0].length; j++)
            {
                matrix[i][j] = 1;
            }
        }

        for(Node n : g.getNodes())
        {
            int x2 = n.x, y2 = n.y, val = n.value;

            matrix[y2][x2] = val;
        }

        return matrix;
    }

    /*
     * return a string that contains the dimensions that the graph occupies separated by a '-'
     */
    private static String findRange(Graph g)
    {
        String s = "-";
        int x = 0, y = 0;
        for(Node n : g.getNodes())
        {
            int x2 = n.x + 1, y2 = n.y + 1;
            if (x2 > x)
            {
                x = x2;
            }
            if (y2 > y)
            {
                y = y2;
            }
        }
        s = x + s + y;
        System.out.println("Range- " + s);
        return s;
    }

    private static void connectAdjacentNodes(Graph g)
    {
        for(Node n : g.getNodes())
        {
            String[] inter = n.getName().split("-");
            int x = Integer.parseInt(inter[0]), y = Integer.parseInt(inter[1]);
            for(Node n2 : g.getNodes())
            {
                String[] inter2 = n2.getName().split("-");
                int x2 = Integer.parseInt(inter2[0]), y2 = Integer.parseInt(inter2[1]);

                if (x == x2)
                {
                    if (y == y2 + 1 || y == y2 - 1)
                    {
                        n.addDestination(n2, 1);
                    }
                }
                else if(y == y2)
                {
                    if (x == x2 + 1 || x == x2 - 1)
                    {
                        n.addDestination(n2, 1);
                    }
                }
            }
        }
    }

    private static Node calculateShortestPathFromSource(Graph graph, Node source, Node destination)
    {
        source.setDistance(0);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();

        unsettledNodes.add(source);

        while(unsettledNodes.size() != 0)
        {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for(Entry <Node, Integer> adjacencyPair: currentNode.getAdjacentNodes().entrySet())
            {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode))
                {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                    if (adjacentNode.value == 4)
                    {
                        return adjacentNode;
                    }
                }
            }
            settledNodes.add(currentNode);
        }

        return null;
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes)
    {
        Node lowest = null;
        int lowestDistance = Integer.MAX_VALUE;
        for(Node node: unsettledNodes)
        {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance)
            {
                lowestDistance = nodeDistance;
                lowest = node;
            }
        }
        return lowest;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeight, Node sourceNode)
    {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeight < evaluationNode.getDistance())
        {
            evaluationNode.setDistance(sourceDistance + edgeWeight);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    public static void printMap(int[][] map)
    {
        for(int i = 0; i < map.length; i ++)
        {
            String s = "";
            for (int j = 0; j < map[0].length; j++)
            {
                s += map[i][j];
            }
            System.out.println(s);
        }
    }

}
