import com.sun.tools.jdeps.Archive;
import org.omg.CORBA.ARG_IN;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Node implements  GNode{
    private String name;
    private ArrayList<Node>  childrens;

    public Node() {
    }

    public Node(String name, ArrayList<Node> childrens) {
        this.name = name;
        this.childrens = childrens;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<GNode> getChildren() {
        return null;
    }
   public void setName(String name) {
        this.name = name;
    }

    public void setChildrens(ArrayList<Node> childrens) {
        this.childrens = childrens;
    }
    public ArrayList<Node> getChildrens(){
        return childrens;
    }
}


public class Graph    {

    private  Node root;


    public Graph() {
        buildGraph();
    }

    public Graph(Node root) {
        this.root = root;
    }

    private  void buildGraph() {
        /*
                        A
                     B      C
                  D     K
         */
        root = new Node() ;
        root.setName("A");
        ArrayList<Node> children = new ArrayList<>();
        ArrayList<Node> childrenLevelOfB = new ArrayList<>();
        childrenLevelOfB.add(new Node("D", new ArrayList<>()));
        childrenLevelOfB.add(new Node("K", new ArrayList<>()));
        children.add(new Node("B", childrenLevelOfB));
        children.add(new Node("C", new ArrayList<>()));
        root.setChildrens(children);
    }

    // BFS Algorithm. We go through all the children of the node and add those to the queue
    // and we poll it out as we go to the next level of children.
    // This graph is no cycle, then I think there is no duplicate in different level
    public ArrayList<Node> walkGraph(Node node){
        ArrayList<Node> results = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()){
            Node children = queue.poll();
            results.add(children);
            for (Node c: children.getChildrens()){
                    queue.add(c);
            }
        }
        return results;
    }
    public Node getRoot(){return root;}

    public void displayChildrens(ArrayList<Node> arr) {
        for (int i = 0 ; i < arr.size(); ++i){
            System.out.print(arr.get(i).getName() + " ");
        }
    }

    public ArrayList<ArrayList<Node>>  path(Node node) {
        if (node == null){return new ArrayList<>();}
        return pathHelper(node);
    }
    // DFS Algorithm. When we see the node has no child, we will add that node to the arraylist and then using arraylist of arraylist to add
    // else if the node has children , we will add that children to the list and then recursively for that children to find another point
    // that connects to that children
    private ArrayList<ArrayList<Node>>  pathHelper(Node node){
        ArrayList<ArrayList<Node>> results = new ArrayList<>();
        if (node.getChildrens().size() == 0){
            ArrayList<Node> endPath = new ArrayList<>();
            endPath.add(node);
            results.add(endPath) ;
        }else {
            for (Node child : node.getChildrens()) {
                ArrayList<ArrayList<Node>> lists = pathHelper(child);
                for (ArrayList<Node> list: lists){
                    list.add(0, node);
                    results.add(list);
                }
            }
        }
        return results;
    }

    public static  void main (String ...args) {
        Graph graph = new Graph();
        ArrayList<Node> allVertices = graph.walkGraph(graph.getRoot());
        System.out.println("All vertices: ");
        graph.displayChildrens(allVertices);
        System.out.println();
        for (Node vertex : allVertices) {
            ArrayList<ArrayList<Node>> results = graph.path(vertex);
            System.out.println("All Path from " + vertex.getName() + ":");
            for (int i = 0; i < results.size(); i++) {
                for (int j = 0; j < results.get(i).size(); j++) {
                    System.out.print(results.get(i).get(j).getName() + " ");
                }
                System.out.println();
            }
        }
    }
}
