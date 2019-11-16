import java.util.*;
public class Node
{
    private String name;
    public int x, y, value;
    private List<Node> shortestPath = new LinkedList<>();
    private Integer distance = Integer.MAX_VALUE;
    Map<Node, Integer> adjacentNodes = new HashMap<>();
    public void addDestination(Node destination, int distance)
    {
        adjacentNodes.put(destination, distance);
    }

    public Node(String name)
    {
        this.name = name;
    }
    
    public void setDistance(int d)
    {
        distance = d;
    }
    
    public int getDistance()
    {
        return distance;
    }
    
    public void setShortestPath(List<Node> p)
    {
        shortestPath = p;
    }
    
    public List<Node> getShortestPath()
    {
        return shortestPath;
    }
    
    public Map<Node, Integer> getAdjacentNodes()
    {
        return adjacentNodes;
    }
    
    public String getName()
    {
        return name;
    }
}
