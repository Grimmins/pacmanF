package GhostsAI;

import geometry.*;
import model.Direction;
import config.MazeConfig;
import java.util.ArrayList;
import model.Ghost;
import config.*;

public class BlinkyAI {

    public static Direction whichDir(IntCoordinates ghostPos, IntCoordinates nextPos){
        int gx = ghostPos.x();
        int gy = ghostPos.y();
        int nx = nextPos.x();
        int ny = nextPos.y();
        if (gx == nx) {
            if (gy < ny) { return Direction.SOUTH; }
            else { return Direction.NORTH; }
        }
        else if (gy == ny) {
            if (gx < nx) { return Direction.EAST; }
            else { return Direction.WEST; }
        }
        else { return Direction.NONE; }
    }

    //Fonction classique commune à toutes les IA : getDirection
    public static Direction getDirection(MazeConfig config, IntCoordinates pacPos, IntCoordinates ghostPos){
        if (!Ghost.BLINKY.isAlive()) {
            if (ghostPos.equals(new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()))){
                Ghost.BLINKY.setSpeed(Ghost.BLINKY.getSpeed()/1.5);
                Ghost.BLINKY.setIsAlive(true);
                System.out.println("Je suis ici");
                System.out.println(ghostPos);
                return Direction.NORTH;
            }else{
                ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, new IntCoordinates(config.getGhostHousePos().x(),config.getGhostHousePos().y()), config);
                int pathlen = path.size();
                IntCoordinates nextPos = path.get(pathlen-1);
                return whichDir(ghostPos, nextPos);
            }
        }else{
            ArrayList<IntCoordinates> path = AStar.shortestPath(ghostPos, pacPos, config);
            int pathLen = path.size();
            IntCoordinates nextPos = path.get(pathLen-1);
            return whichDir(ghostPos, nextPos);
        }
    }

}
