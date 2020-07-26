/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Machenike
 */
public class Node {
    public int x;
    public int y;
    public int f;
    //g represents the movement cost from the start point along the generated path to the point to be detected
    public int g;
    //h represents the movement cost from the point to be detected to the end point
    public int h;
    //f is the sum of g and h.
    public void calF(){
        this.f = g + h;
    }
    public Node(int x,int y){
        this.x = x;
        this.y = y;
    }
    public Node parent;
}
