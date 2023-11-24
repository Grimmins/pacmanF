package gui;

/**
 * Cette classe est responsable de la création des éléments graphiques pour UNE CELLULE du labyrinthe.
 */


import geometry.IntCoordinates;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.Ghost;
import model.Items.Item;
import model.Items.ItemTest;
import model.MazeState;
import model.PacMan;
import model.Items.Dot;
import model.Items.Energizer;
import model.Items.FakeEnergizer;
import config.Cell;
import model.Items.ItemTest;

public class CellGraphicsFactory {
    private final double scale;

    public CellGraphicsFactory(double scale) {
        this.scale = scale;
    }

    /**
     * Crée les éléments graphiques pour une cellule donnée.
     * @param state
     * @param pos
     * @return un objet GraphicsUpdater qui permet de mettre à jour les éléments graphiques
     */

    /**
     * Bon maintenant, avec respect : c'est quoi ce code ? KAPPA
     *
     * 1. Utilisation de constante : scale/15, scale/5, scale/10, scale/2 ou 9 * scale / 10
     *  c'est quand même déguelasse
     * 2. Méthodes distinctes pour chaque élément graphique
     * 4. COMPLIQUÉ : Gestion des dimensions dynamiques :
     * pour l'instant les dimensions sont fixes, mais si on veut changer la taille de la fenêtre
     * il faut changer les dimensions de chaque élément graphique
     */

    public void setEnergized(Energizer e){
        if(e.isActive()){
            e.frameActivity ++;
            if(e.frameActivity>500){
                e.setActive(false);
                Ghost.energized = Energizer.isOneActive();
                PacMan.INSTANCE.setEnergized(Energizer.isOneActive());
            }
        }
    }

    public void setActiveItemTest(ItemTest t){
        if(t.isActive()) {
            t.frameActivity++;
            if (t.frameActivity > 500) {
                t.setActive(false);
            }
        }
    }

    public void setFakeEnergized(FakeEnergizer e){
        if(FakeEnergizer.isFakeEnergized()){
            FakeEnergizer.frameEnergizer ++;
        }

        if(FakeEnergizer.frameEnergizer>750){
            FakeEnergizer.setFakeEnergized(false);
            //Ghost.energized = false;
            PacMan.INSTANCE.setFakeEnergized(false);
        }
    }

    public GraphicsUpdater makeGraphics(MazeState state, IntCoordinates pos, Color color) {
        Group group = new Group(); // permet de mettre dans groupe tous les node à afficher (mur + dot)
        group.setTranslateX(pos.x()*scale);
        group.setTranslateY(pos.y()*scale);
        Cell cell = state.getConfig().getCell(pos);

        // creer les dots
        Circle dot = new Circle();
        group.getChildren().add(dot);

        double radius =0;
        if(cell.initialItem().getClass() == Dot.class)  radius = scale/20;
        if((cell.initialItem() instanceof Energizer) || (cell.initialItem() instanceof ItemTest) || (cell.initialItem() instanceof FakeEnergizer)) radius = scale/7;
        dot.setRadius(radius);

        dot.setCenterX(scale/2);
        dot.setCenterY(scale/2);

        if(cell.initialItem() instanceof ItemTest) { dot.setFill(Color.RED); }
        else if (cell.initialItem() instanceof FakeEnergizer) { dot.setFill(Color.GREEN); }
        else { dot.setFill(Color.WHITE); }
        double taille = scale;


        if((cell.initialItem() instanceof Energizer) || (cell.initialItem() instanceof ItemTest) || (cell.initialItem() instanceof FakeEnergizer)){
            ScaleTransition blink = new ScaleTransition(Duration.millis(600), dot);
            blink.setFromX(1);
            blink.setFromY(1);
            blink.setToX(0.6);
            blink.setToY(0.6);
            blink.setAutoReverse(true);
            blink.setCycleCount(Timeline.INDEFINITE);
            blink.play();
        }

        //rajout des murs pour chaque case
        if (cell.northWall()) {
            ImageView mur = new ImageView(new Image("mur-north.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
        }
        if (cell.eastWall()) {
            ImageView mur = new ImageView(new Image("mur-east.png", taille, taille, true, false));
            mur.setTranslateX(9*scale/10);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
        }
        if (cell.southWall()) {
            ImageView mur = new ImageView(new Image("mur-south.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(9*scale/10);
            group.getChildren().add(mur);
        }
        if (cell.westWall()) {
            ImageView mur = new ImageView(new Image("mur-west.png", taille, taille, true, false));
            mur.setTranslateX(0);
            mur.setTranslateY(0);
            group.getChildren().add(mur);
        }

        return new GraphicsUpdater() {

            @Override
            public void update() {

                //afficher les points si pacman pas passé dessus
                dot.setVisible(!state.getGridState(pos));

                if (cell.initialItem() instanceof Energizer){
                    setEnergized((Energizer)cell.initialItem());
                }
                if(cell.initialItem() instanceof ItemTest){
                    setActiveItemTest((ItemTest)cell.initialItem());
                }
                if (cell.initialItem() instanceof FakeEnergizer){
                    setFakeEnergized((FakeEnergizer)cell.initialItem());
                }
                for (Node n : group.getChildren()){
                    n.setVisible(!ItemTest.isOneActive());
                }
                dot.setVisible(!state.getGridState(pos));
            }

            @Override
            public Node getNode() {
                return group;
            }
        };
    }

//    private Rectangle createWall(double width, double height, Color color, double x, double y) {
//            var wall = new Rectangle();
//            wall.setWidth(width);
//            wall.setHeight(height);
//            wall.setX(x);
//            wall.setY(y);
//            wall.setFill(color);
//            return wall;
//        }

}

