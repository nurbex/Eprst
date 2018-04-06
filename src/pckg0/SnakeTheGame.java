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

    private int screenSizeX=1000;
    private int screenSizeY=600;

    private int skipTime=10;
    private int skipTimeCounter=0;

    private Pane root;

    private List<GameObject> foods=new ArrayList<>();
    private List<GameObject> snake=new ArrayList<>();

    private Parent createContent(){
        root=new Pane();
        root.setPrefSize(screenSizeX,screenSizeY);
        addSnakeTail(new Snake(),300,300);
        addSnakeTail(new Snake(),280,300);
        addSnakeTail(new Snake(),260,300);
        snake.get(0).setDirectionX(1);
        snake.get(0).setDirectionY(0);



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
                if(snake.isEmpty()){
                    stop();
                }


            }
        };
        timer.start();

        return root;

    }



    private void addFood(GameObject food,double x,double y){
        foods.add(food);
        addGameobject(food,x,y);
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


        for(int i=2;i<snake.size();i++){

            if(snake.get(i).isColliding(snake.get(0))){
                snake.get(snake.size()-1).setAlive(false);
                root.getChildren().removeAll(snake.get(snake.size()-1).getView());
            }

        }

        for(int i=0;i<foods.size();i++){
            if(snake.get(0).isColliding(foods.get(i))){
                foods.get(i).setAlive(false);
                root.getChildren().removeAll(foods.get(i).getView());
                addSnakeTail(new Snake(), snake.get(snake.size()-1).getView().getTranslateX(),snake.get(snake.size()-1).getView().getTranslateY());
            }
        }


        foods.removeIf(GameObject::isDead);
        snake.removeIf(GameObject::isDead);

        locationPassToSnake();
        for(int i =0;i<snake.size();i++){
            snake.get(i).update(screenSizeX,screenSizeY);
        }





        if(Math.random()<0.03){
            int x=((int)(Math.random()*root.getPrefWidth())/20)*20;
            int y=((int)(Math.random()*root.getPrefHeight())/20)*20;
            addFood(new Food(), x,y);
        }

    }


    private class Food extends GameObject{
        Food(){
            super(new Rectangle(19,19,Color.GREEN));
            super.getView().setLayoutX(1);
            super.getView().setLayoutY(1);
        }
    }
    private class Snake extends GameObject{
        Snake(){

            super(new Rectangle(19,19,Color.BLUE));
            super.getView().setLayoutX(1);
            super.getView().setLayoutY(1);
        }

    }
    private void locationPassToSnake(){
        for(int i=snake.size()-1;i>0;i--){
            snake.get(i).getView().setTranslateX(snake.get(i-1).getView().getTranslateX());
            snake.get(i).getView().setTranslateY(snake.get(i-1).getView().getTranslateY());
        }
    }

    public void start(Stage stage){
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e ->{
            if(e.getCode() == KeyCode.LEFT){
                snake.get(0).turnLeft();

            }
            if(e.getCode() == KeyCode.RIGHT){
                snake.get(0).turnRight();

            }
        });
        stage.setTitle("Snake The Game!");
        stage.show();
    }


    public static void main(String[] args){
        launch();
    }




}
