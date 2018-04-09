package pckg0;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class SnakeGame extends Application {

    private int screenSizeX=600;
    private int screenSizeY=screenSizeX;

    private int uiAreaWidth =0;//250

    //private String mainLabelText="Enter parameters:";


    private int skipTime=20;
    private int skipTimeCounter=0;

    private Pane root;
    //private Pane uiArea;
    private Pane gameArea;


    private List<GameObject> foods=new ArrayList<>();
    private List<GameObject> snake=new ArrayList<>();

    private Parent createContent(){

        root=new Pane();
        root.setPrefSize(screenSizeX+ uiAreaWidth, screenSizeY);
        root.setStyle("-fx-background-color: white; ");

        /*uiArea=new Pane();
        uiArea.setPrefSize(uiAreaWidth,screenSizeY);
        uiArea.setTranslateX(0);
        uiArea.setStyle("-fx-background-color: white; ");
        uiArea.getStylesheets().add("pckg0/SnakeGameCSS.css");


        //A label with the text element
        Label labelMain = new Label(mainLabelText);
        labelMain.setTranslateX(0);
        labelMain.setTranslateY(0);
        uiArea.getChildren().addAll(labelMain);


        //radio button groupRadio

        final ToggleGroup groupRadio=new ToggleGroup();

        RadioButton rb1 = new RadioButton("300x300");
        rb1.setSelected(true);
        rb1.setTranslateX(10);
        rb1.setTranslateY(30);

        RadioButton rb2 = new RadioButton("600x600");
        rb2.setTranslateX(10);
        rb2.setTranslateY(50);

        groupRadio.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
                if (groupRadio.getSelectedToggle() != null) {
                    System.out.println(groupRadio.getSelectedToggle().getUserData().toString()+"toggle");
                }
            }
        });

        uiArea.getChildren().addAll(rb1,rb2);

        //A label with the text element
        Label labelResult = new Label();
        labelResult.setTranslateX(100);
        labelResult.setTranslateY(240);
        uiArea.getChildren().addAll(labelResult);

        //A button with the specified text caption.
        Button buttonSnake = new Button("  Update  ");
        buttonSnake.setTranslateX(100);
        buttonSnake.setTranslateY(200);

        DropShadow shadow = new DropShadow(1,0,1,Color.LIGHTGREY);
        shadow.setSpread(0.0001);

        //Adding the shadow when the mouse cursor is on
        buttonSnake.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        buttonSnake.setEffect(shadow);
                    }
                });
        //Removing the shadow when the mouse cursor is off
        buttonSnake.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {

                        buttonSnake.setEffect(null);
                        labelResult.setText("");
                        buttonSnake.setStyle("-fx-background-color: ghostwhite; -fx-text-fill:gray;");
                    }
                });

        buttonSnake.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                labelResult.setText("Accepted");
                labelResult.setStyle("-fx-text-fill:green;");
                buttonSnake.setStyle("-fx-background-color: #b6e7c9; -fx-text-fill:green;");
            }
        });
        uiArea.getChildren().addAll(buttonSnake);*/





        gameArea=new Pane();
        gameArea.setPrefSize(screenSizeX,screenSizeY);
        gameArea.setTranslateX(uiAreaWidth+0);

        gameArea.setStyle("-fx-background-color: whitesmoke"); //-fx-border-color:lightgray;

        // Adding default snake length.
        addSnakeTail(new Snake(),(screenSizeX/2),(screenSizeY/2));
        addSnakeTail(new Snake(),(screenSizeX/2-20),(screenSizeY/2));
        addSnakeTail(new Snake(),(screenSizeX/2-40),(screenSizeY/2));
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

        root.getChildren().addAll(gameArea); //uiArea,
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
        gameArea.getChildren().add(object.getView());
    }

    private void onUpdate(){


        for(int i=3;i<snake.size();i++){

            if(snake.get(i).isColliding(snake.get(0))){
                snake.get(snake.size()-1).setAlive(false);
                gameArea.getChildren().removeAll(snake.get(snake.size()-1).getView());
            }

        }

        for(int i=0;i<foods.size();i++){
            for(int k=0; k<snake.size();k++) {
                if (snake.get(k).isColliding(foods.get(i))) {
                    foods.get(i).setAlive(false);
                    gameArea.getChildren().removeAll(foods.get(i).getView());
                    addSnakeTail(new Snake(), snake.get(snake.size() - 1).getView().getTranslateX(), snake.get(snake.size() - 1).getView().getTranslateY());
                }
            }
        }


        foods.removeIf(GameObject::isDead);
        snake.removeIf(GameObject::isDead);

        locationPassToSnake();
        for(int i =0;i<snake.size();i++){
            snake.get(i).update(screenSizeX,screenSizeY);

        }


        if(foods.isEmpty()){
            int x=((int)(Math.random()*gameArea.getPrefWidth())/20)*20;
            int y=((int)(Math.random()*gameArea.getPrefHeight())/20)*20;
            addFood(new Food(), x,y);
        }



    }


    private class Food extends GameObject{
        Food(){
            super(new Rectangle(18,18,Color.LIGHTGREEN));
            super.getView().setLayoutX(2);
            super.getView().setLayoutY(2);
            super.getView().setStyle("-fx-stroke: green");

        }
    }
    private class Snake extends GameObject{
        Snake(){

            super(new Rectangle(18,18,Color.DEEPSKYBLUE));
            super.getView().setLayoutX(2);
            super.getView().setLayoutY(2);
            super.getView().setStyle("-fx-stroke: blue");

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

        stage.setMaxWidth(screenSizeX+ uiAreaWidth);
        stage.setMaxHeight(screenSizeY);

        stage.setTitle("Snake The Game!");
        stage.show();
    }


    public static void main(String[] args){
        launch();
    }




}
