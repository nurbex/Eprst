package pckg0;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class SnakeTheGame extends Application {

    private int screenSizeX=600;
    private int screenSizeY=600;

    private int skipTime=10;
    private int skipTimeCounter=0;

    private Pane root;

    private List<GameObject> foods=new ArrayList<>();
    private List<GameObject> poisons=new ArrayList<>();
    private List<GameObject> snake=new ArrayList<>();

    private Parent createContent(){
        root=new Pane();
        root.setPrefSize(screenSizeX,screenSizeY);
        addSnakeTail(new Snake(),300,300);
        addSnakeTail(new Snake(),280,300);
        addSnakeTail(new Snake(),260,300);



        AnimationTimer timer=new AnimationTimer(){
            @Override
            public void handle(long now){

                if(skipTimeCounter==0){
                    onUpdate();
                }
                if(skipTimeCounter==skipTime){
                    skipTimeCounter=-1;
                }
                skipTimeCounter++;


            }
        };
        timer.start();

        return root;

    }



    private void addFood(GameObject food,double x,double y){
        foods.add(food);
        addGameobject(food,x,y);
    }
    private void addPoison(GameObject poison,double x,double y){
        poisons.add(poison);
        addGameobject(poison,x,y);
    }
    private void addSnakeTail(GameObject snakeTail,double x,double y){
        snake.add(snakeTail);
        addGameobject(snakeTail,x,y);
    }

    private void addGameobject(GameObject object, double x, double y){
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate(){
        for(GameObject poison:poisons){
            for(GameObject snakeTail:snake){
                if(snakeTail.isColliding(poison)){
                    snakeTail.setAlive(false);
                    poison.setAlive(false);
                    root.getChildren().removeAll(poison.getView(),snakeTail.getView());
                }
            }
        }
        for(int i=0;i<foods.size();i++){

            if(snake.get(0).isColliding(foods.get(i))){
                foods.get(i).setAlive(false);
                root.getChildren().removeAll(foods.get(i).getView());
                //addSnakeTail(snake.get(snake.size()), snake.get(snake.size()).getView().getTranslateX(),snake.get(snake.size()).getView().getTranslateY());
            }
        }
        for(GameObject poison:poisons){
            for(GameObject food:foods){
                if(food.isColliding(poison)){
                    poison.setAlive(false);
                    root.getChildren().removeAll(poison.getView());
                }
            }
        }

        foods.removeIf(GameObject::isDead);
        poisons.removeIf(GameObject::isDead);
        snake.removeIf(GameObject::isDead);


        snake.forEach(GameObject::update);

        if(Math.random()<0.01){
            int x=((int)(Math.random()*root.getPrefWidth())/20)*20;
            int y=((int)(Math.random()*root.getPrefHeight())/20)*20;
            addPoison(new Poison(), x,y);
        }

        if(Math.random()<0.01){
            int x=((int)(Math.random()*root.getPrefWidth())/20)*20;
            int y=((int)(Math.random()*root.getPrefHeight())/20)*20;
            addFood(new Food(), x,y);
        }

    }

    private class Poison extends GameObject{
        Poison(){
            super(new Rectangle(20,20, Color.BLUE));
        }

    }
    private class Food extends GameObject{
        Food(){
            super(new Rectangle(20,20, Color.GREENYELLOW));

        }

    }
    private class Snake extends GameObject{
        Snake(){
            super(new Rectangle(-10,-10,20,20));
        }

    }

    public void start(Stage stage){
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.LEFT){
                snake.get(0).turnLeft();

            }else if(e.getCode() == KeyCode.RIGHT){
                snake.get(0).turnRight();

            }else{
                snake.get(0).setDirectionX(1);
                snake.get(0).setDirectionY(0);
                System.out.println(snake.get(0).getDirectionX());
                System.out.println(snake.get(0).getDirectionY());
            }
        });
        stage.setTitle("Snake The Game!");
        stage.show();
    }


    public static void main(String[] args){
        launch();
    }




}
