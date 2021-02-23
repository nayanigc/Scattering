import io.jbotsim.core.Topology;
import io.jbotsim.ui.JViewer;
import io.jbotsim.core.Node;

import io.jbotsim.core.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Robot extends Node {

    ArrayList<Point> locations;
    Point target;
    int myMultiplicity ;
    static double EPS = 0.000001;

    @Override
    public void onPreClock() {
        myMultiplicity = -1;
        locations = new ArrayList<Point>();
        for (Node node : getTopology().getNodes() )
        {
            locations.add(node.getLocation());

        }

        for( int i =0; i <locations.size();i++){
            if( this.getX() == locations.get(i).getX() && this.getY() == locations.get(i).getY()){
                myMultiplicity++;
                locations.remove(i);
            }
        }
        locations.add(this.getLocation());

    }

    @Override
    public void onClock(){
        /*
         * Question 6
         * */
        /*for (int i = 0; i<myMultiplicity;i++){
            target = new Point(Math.random() * 800, Math.random() * 400);

        }*/
        /*
         * Question 8
         * */
        int n = new Random().nextInt(2);
        System.out.println("---------------------------------------------------------------------------------------");
        if(n == 0) {
            target = this.getLocation();
        }else{
            target = generateDestinations(n).get(0);
            System.out.println(getTime());
        }

        /*for(Point r : locations)
        {
            if(r.getX() > target.getX() || (r.getX() == target.getX() && r.getY() > target.getY()))
            {
                target = r;
            }
        }*/
    }

    @Override
    public void onPostClock(){
        setDirection(target);
        move(Math.min(10, distance(target)));
    }
    /*
    * Question 7
    * */
    public List<Point> generateDestinations(int n) {
        List<Point> safeDest = new ArrayList<>();

        while(safeDest.size() < n) {
            boolean exists = false;
            Point p = new Point(Math.random() * 200, Math.random() * 200);

            for(Point r : locations) {
                if((r.getX() == p.getX() && r.getY() == p.getY()) || (p.getX() == this.getX() && p.getY() == this.getY())) {
                    exists = true;
                }
            }

            if(!exists) {
                safeDest.add(p);
                locations.add(p);
            }
        }
        for (int i = 0; i <locations.size(); i++){
            System.out.println( i+" "+ locations.get(i));
        }

        return safeDest;
    }
    // Start the simulation
    public static void main(String[] args){

        // Create the Topology (a plane of size 800x400)
        Topology tp = new Topology(800, 400);
        // Create the simulation window
        new JViewer(tp);

        // set the default node to be our Robot class
        // (When the user click in the simulation window,
        //  a default node is automatically added to the topology)
        tp.setDefaultNodeModel(Robot.class);

        // Robots cannot communicate
        tp.disableWireless();

        // Here we remove the sensing range since the robots have unlimited visibility
        tp.setSensingRange(0);

        // Add 20 Robots to the topology (with random positions)
        for (int i = 0; i < 20; i++) {
               tp.addNode(200,200);
        }

        //The clock click every 0.5 sec (so that you can see the evolution slowly)
        tp.setTimeUnit(500);
        tp.getTime();
        // We pause the simulation
        // (to start it, you'll have to right click on the window and resume it)
        tp.pause();
    }
}
