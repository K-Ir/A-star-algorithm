

import java.util.ArrayList;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Machenike
 */
public class AStar{
    //0 means this point can be accessed,1 means this point is an obstacle which cannot pass
    public static final int maps[][]={
        {0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0,0,0},
        {0,0,0,0,0,1,0,0,0,0,0,0},
        {0,0,0,0,0,1,1,1,1,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,0,0,0,0},
    };
    //the cost of each step;
    public static final int step1 = 10;
    public static final int step2 = 14;
    
    //openList store the accessible points and closeList store the visited points
    public ArrayList<Node> openList = new ArrayList<Node>();
    public ArrayList<Node> closeList = new ArrayList<Node>();
    
    //find the node with the smallest f in openList,to choose the next node to access.
    public Node findMinF(){
        Node curNode = openList.get(0);
        for(Node node : openList){
            if(node.f < curNode.f)
                curNode = node;
        }
        return curNode;
    }
    
    //determine whether this point can be accessed
    public boolean accessible(int x,int y){
        if((x >= 0 && x < maps.length) &&(y >= 0 && y < maps.length)){
            return maps[x][y] == 0;
        }
        return false;
    }
    
    //show whether this node has existed in the list.
    public static boolean exist(ArrayList<Node> nodes,Node n){
        for(Node node : nodes){
            if((node.x == n.x) && (node.y == n.y))
                return true;
        }
        return false;
    }
    
    //put the neighbor nodes into arraylist
    public ArrayList<Node> neighbors(Node currentNode){
        ArrayList<Node> arr = new ArrayList<Node>();
        Node leftNode = new Node(currentNode.x,currentNode.y-1);
        Node rightNode = new Node(currentNode.x,currentNode.y+1);
        Node upNode = new Node(currentNode.x-1,currentNode.y);
        Node downNode = new Node(currentNode.x+1,currentNode.y);
        Node leftUpNode = new Node(currentNode.x-1,currentNode.y-1);
        Node rightUpNode = new Node(currentNode.x-1,currentNode.y+1);
        Node leftDownNode = new Node(currentNode.x+1,currentNode.y-1);
        Node rightDownNode = new Node(currentNode.x+1,currentNode.y+1);
        //judge whether this node can be accessed or have existed in closeList
        if(accessible(leftNode.x,leftNode.y)&&(!exist(closeList,leftNode))){
            arr.add(leftNode);
        }
        if(accessible(rightNode.x,rightNode.y)&&(!exist(closeList,rightNode))){
            arr.add(rightNode);
        }
        if(accessible(upNode.x,upNode.y)&&(!exist(closeList,upNode))){
            arr.add(upNode);
        }
        if(accessible(downNode.x,downNode.y)&&(!exist(closeList,downNode))){
            arr.add(downNode);
        }
        if(accessible(leftUpNode.x,leftUpNode.y)&&(!exist(closeList,leftUpNode))){
            arr.add(leftUpNode);
        }
        if(accessible(rightUpNode.x,rightUpNode.y)&&(!exist(closeList,rightUpNode))){
            arr.add(rightUpNode);
        }
        if(accessible(leftDownNode.x,leftDownNode.y)&&(!exist(closeList,leftDownNode))){
            arr.add(leftDownNode);
        }
        if(accessible(rightDownNode.x,rightDownNode.y)&&(!exist(closeList,rightDownNode))){
            arr.add(rightDownNode);
        }
        return arr;
    }
    
    //To calculate the g of the current node and its neighbors;
    public int calG(Node startNode,Node curNode){
        int G = 0;
        if(Math.abs(startNode.x-curNode.x)+Math.abs(startNode.y-curNode.y)==2){
            G = step2;
        }else
            G = step1;
        int parentG = 0;
        if(curNode.parent != null){
            parentG = curNode.parent.g;
        }
        return G+parentG;
    }
    
    //calculate H of each node.
    public int calH(Node endNode,Node curNode){
        int cost = Math.abs(endNode.x-curNode.x)+Math.abs(endNode.y-curNode.y);
        return cost;
    }
    
    public void found(Node temp,Node node){
        int g = calG(temp,node);
        if(g<node.g){
            node.parent = temp;
            node.g = g;
            node.calF();
        }
    }
    public void notFound(Node temp,Node end,Node node){
        node.parent = temp;
        node.g = calG(temp,node);
        node.h = calH(end,node);
        node.calF();
        openList.add(node);
    }
    
    //traverse the openList to find the minmum f.
    public Node findpath(Node start,Node end){
        //put the start node into open list
        openList.add(start);
        while(openList.size()!=0){
            //choose a node with the smallest F in openList and put it into closeList.
            Node curNode = findMinF();
            //delete the node with smallest F and put it into close list
            openList.remove(curNode);
            closeList.add(curNode);
            //find the neighbors which don't exist in close list
            ArrayList<Node> arr = neighbors(curNode);
            for(Node node : arr){
                //put neighbours into openList
                //if this node has exists
                if(exist(openList,node)){
                    found(curNode,node);
                }else{
                    notFound(curNode,end,node);
                }
            }
            //if find the end node in open list,it means the path has been found
            if(find(openList,end) != null){
                return find(openList,end);
            }
        }
        return find(openList,end);
    }
    
    //To find whether this node has existed in the list
    public Node find(ArrayList<Node> list,Node node){
        for(Node n : list){
            if(n.x==node.x && n.y==node.y){
                return n;
            }
        } 
        return null;
    }
    
    public static void main(String[] args) {
            System.out.println("current map:");
            for(int i=0;i<12;i++){
                for(int j=0;j<12;j++){
                    System.out.print(maps[i][j]+", ");
                }
                System.out.println("");
            }
            int x1,y1,x2,y2;
            Scanner sc = new Scanner(System.in);
            System.out.println("insert the index of a start Node:");
            x1=sc.nextInt();
            y1=sc.nextInt();
            System.out.println("insert the index of an end Node:");
            x2=sc.nextInt();
            y2=sc.nextInt();
            if(maps[x1][y1]==1){
                System.out.println("The start node is set on the obstacle");
            }
            if(maps[x2][y2]==1){
                System.out.println("The end node is set on the obstacle");
            }
            if(maps[x1][y1]==0&&maps[x2][y2]==0){
                Node startNode = new Node(x1, y1);
                Node endNode = new Node(x2, y2);
                Node parent = new AStar().findpath(startNode, endNode);// The end point is returned, but at this time the parent node has been established and can be traced to the start node

                ArrayList<Node> arrayList = new ArrayList<Node>();
                //Traverse the path
                while (parent != null) {
                    arrayList.add(new Node(parent.x, parent.y));
                    parent = parent.parent;
                }
                System.out.println("After the path has found:");
                //print the new map after find the path
                for (int i = 0; i < 12; i++) {
                    for (int j = 0; j < 12; j++) {
                            if (exist(arrayList, new Node(i,j))) {// use "!" to show the founded path
                                System.out.print("!, ");
                            } else {
                                System.out.print(maps[i][j] + ", ");
                            }
                    }
                        System.out.println();
                    }
            }
	}
}
