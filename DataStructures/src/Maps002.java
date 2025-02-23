import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Maps002 {
    //Creating the Graph;
    static void createTheGraph(ArrayList<Edge> graph[]){
        for(int i=0;i<graph.length;i++){
            graph[i]=new ArrayList<>();
        }
        graph[0].add(new Edge(0, 1));
        graph[0].add(new Edge(0, 2));
        graph[1].add(new Edge(1, 0));
        graph[1].add(new Edge(1, 3));
        graph[2].add(new Edge(2, 0));
        graph[2].add(new Edge(2, 4));
        graph[3].add(new Edge(3, 1));
        graph[3].add(new Edge(3, 4));
        graph[3].add(new Edge(3, 5));
        graph[4].add(new Edge(4, 2));
        graph[4].add(new Edge(4, 3));
        graph[4].add(new Edge(4, 5));
        graph[5].add(new Edge(5, 3));
        graph[5].add(new Edge(5, 4));
        graph[5].add(new Edge(5, 6));
        graph[6].add(new Edge(6, 5));
    }
    public static void main(String[] args) {
        System.out.println("Maps DFS and BFS");
        //DFS
        //create a graph
        int v=7;
        ArrayList<Edge> graph[]=new ArrayList[v];
        createTheGraph(graph);
        for(int i=0;i<graph.length;i++){
            System.out.print(i+" :");
            for( Edge x : graph[i]){
                System.out.print(x.dest+" ");
            }
            System.out.println();
        }
        //BFS
        boolean visited[]=new boolean[v];
        int a=0;
        for(int i=0;i<v;i++){
            if(!visited[i]){
                a++;
                bfs(graph,visited,i);
            }
        }
        System.out.println("NO. of components :"+a);
        //DFS
        //fill the array with false
        Arrays.fill(visited,false);
        for(int i=0;i<v;i++){
             dfs(graph,visited,0);
        }


    }
    static void dfs(ArrayList<Edge> []graph,boolean []visited,int start){

        System.out.print(start+"  ");
        visited[start]=true;
        for(Edge e : graph[start]){
            if(!visited[e.dest]){
                dfs(graph,visited,e.dest);
            }
        }
    }
    static void bfs(ArrayList<Edge> []graph,boolean []visited,int start){
        Queue<Integer> q=new LinkedList<>();
        q.add(start);
        while(!q.isEmpty()){
            int x=q.remove();
            System.out.print(x+ "  ");
            visited[x]=true;
            for(Edge e: graph[x]){
                if(!visited[e.dest]) {
                    q.add(e.dest);
                    visited[e.dest] = true;
                }
            }
//            for(int i=0;i<graph[x].size();i++){
//
//            }
        }
    }
}
//class Edge{
//    int src;
//}
