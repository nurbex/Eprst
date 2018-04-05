package pckg0;


import javafx.scene.Node;

public class GameObject {

    private Node view;
    private int velocity=20;
    private int directionX;
    private int directionY;



    private boolean alive=true;
    public GameObject(Node view){
        this.view=view;
    }


    public void setDirectionX(int x){
        directionX=x;
    }
    public void setDirectionY(int y){
        directionY=y;
    }

    public int getDirectionX(){
        return directionX;
    }
    public int getDirectionY(){
        return directionY;
    }



    public void update(){


        view.setTranslateX(view.getTranslateX() + directionX*velocity);
        view.setTranslateY(view.getTranslateY() + directionY*velocity);

        if((view.getTranslateX() + directionX*velocity)>620){
            view.setTranslateX(-20 + directionX*velocity);
        }
        else if(((view.getTranslateX() + directionX*velocity)<-20)){
            view.setTranslateX(580 + directionX*velocity);
        }


        if((view.getTranslateY() + directionY*velocity)>620){
            view.setTranslateY(-20 + directionY*velocity);
        }
        else if(((view.getTranslateY() + directionY*velocity)<-20)){
            view.setTranslateY(580 + directionY*velocity);
        }
    }

    public Node getView() {
        return view;
    }
    public boolean isAlive(){return alive;}

    public boolean isDead(){ return !alive;}

    public void setAlive(boolean alive){this.alive=alive;}


    public void turnLeft(){
        if((directionX==1)&&(directionY==0)){
            directionX=0;
            directionY=-1;
        }else
        if((directionX==0)&&(directionY==-1)){
            directionX=-1;
            directionY=0;
        }else
        if((directionX==-1)&&(directionY==0)){
            directionX=0;
            directionY=1;
        }else
        if((directionX==0)&&(directionY==1)){
            directionX=1;
            directionY=0;
        }else{
            System.out.println(directionX);
            System.out.println(directionY);
        }


    }
    public void turnRight(){
        if((directionX==1)&&(directionY==0)){
            directionX=0;
            directionY=1;
        }else
        if((directionX==0)&&(directionY==1)){
            directionX=-1;
            directionY=0;
        }else
        if((directionX==-1)&&(directionY==0)){
            directionX=0;
            directionY=-1;
        }else
        if((directionX==0)&&(directionY==-1)){
            directionX=1;
            directionY=0;
        }else{
            System.out.println(directionX);
            System.out.println(directionY);
        }
    }



    public boolean isColliding(GameObject other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }


}

