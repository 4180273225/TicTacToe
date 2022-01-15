import javafx.scene.layout.VBox;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.awt.event.ActionEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

    public class TicTacToePane extends VBox {
        //data
        private int wins,loss,ties,numOfGame,turn;
        private boolean gameover = false;
        //GUI component
        private Button [][] tictactoeBTN;
        private Label headerLabel,winsLabel,lossesLabel,tiesLabel,numOfGamesLabel;
        private Button playAgain;
        private  int Xcount = 2;
        private int Ocount2=0;


        private void doButtonAction(javafx.event.ActionEvent event) {
            if (!gameover) {
                Button clicBTN = (Button) event.getSource();

                //if spot taken , exit method
                if (clicBTN.getText().length() > 0) {
                    if (turn % 2 != 0 )
                    {
                        doTurnForAI();
                    }
                    return;
                }

                String place = "";
                if (turn % 2 == 0) {
                    // X's turn
                    place = "X";
                } else {
                    //O's turn
                    place = "0";
                }

                turn++;

                headerLabel.setText(String.format("%S's turn", place));
                clicBTN.setText(place);
                if (turn >= 5) {
                    //TODO - Check for winner

                    if (checkIfWon(place)) {
                        //TODO - Stop Game

                        headerLabel.setText(String.format("%S won", place));
                        gameover = true;
                        return;

                    }
                }
                if (turn == 9 )
                {
                    gameover=true;
                    headerLabel.setText("Game over..... No winner");
                    return;
                }

                headerLabel.setText(String.format("%S's turn  " ,turn%2 == 0 ? "X" : "O"));

                if (turn % 2 != 0 )
                {
                    //AI's turn
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            Platform.runLater(() -> {
                                doTurnForAI();
                            });
                        }
                    };
                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
                    service.schedule(runnable,800, TimeUnit.MILLISECONDS);


                }
            }
        }


        public TicTacToePane()
        {
            GridPane gridPane = new GridPane();
            gridPane.setAlignment(Pos.CENTER);

            Button exit = new Button("Exit");
            Button reset = new Button("Reset");

            exit.setOnAction(event -> {
                System.exit(0);
            });

            reset.setOnAction(event -> {
                for (int c=0;c<3;c++)
                {
                    for (int r=0;r<3;r++)
                    {
                        tictactoeBTN[c][r].setText(null);
                    }
                }
            });
            tictactoeBTN = new Button[3][3];
            for (int c= 0; c < 3 ; c++)
            {
                for (int r=0; r<3 ; r++)
                {
                    tictactoeBTN[c][r] = new Button("");
                    tictactoeBTN[c][r].setFont(new Font(36));
                    tictactoeBTN[c][r].setPrefWidth(65);
                    tictactoeBTN[c][r].setPrefHeight(70);
                    gridPane.add(tictactoeBTN[c][r],c,r);
                    tictactoeBTN[c][r].setOnAction(this::doButtonAction);
                }
            }
            headerLabel = new Label("");
            headerLabel.setFont(new Font(24));
            setAlignment(Pos.CENTER);

            setData();
            restGame();

            //set stat lable
            winsLabel = new Label("wins:"+Xcount);
            lossesLabel = new Label("Losses:"+Ocount2);
            tiesLabel = new Label("ties:"+ties);
            numOfGamesLabel = new Label("Games"+numOfGame);
            VBox statbox = new VBox(winsLabel,lossesLabel,tiesLabel,numOfGamesLabel,exit,reset);
            statbox.setAlignment(Pos.CENTER_RIGHT);
            statbox.setSpacing(10);

            HBox midel = new HBox(statbox,gridPane);
            midel.setSpacing(10);
            midel.setAlignment(Pos.CENTER);
            gameSocer();


            getChildren().addAll(headerLabel,midel);

        }






        private void doTurnForAI() {
            //TODO - Make smart

            //in meantime - AI is dumb
            tictactoeBTN[(int) (Math.random() *3)][(int) (Math.random()*3)].fire();

        }

        private boolean checkIfWon(String player) {

            //check tic tac  Buttons


            //check rows

            if ((player.equals(tictactoeBTN[0][0].getText()) && player.equals(tictactoeBTN[1][0].getText()) &&
                    player.equals(tictactoeBTN[2][0].getText())) || (player.equals(tictactoeBTN[0][1].getText()) && player.equals(tictactoeBTN[1][1].getText())
                    && player.equals(tictactoeBTN[2][1].getText())) || (player.equals(tictactoeBTN[0][2].getText()) && player.equals(tictactoeBTN[1][2].getText()) && player.equals(tictactoeBTN[2][2].getText())))
            {

                return true;
            }

            //check columns
            if ((player.equals(tictactoeBTN[0][0].getText()) && player.equals(tictactoeBTN[0][1].getText()) &&
                    player.equals(tictactoeBTN[0][2].getText())) || (player.equals(tictactoeBTN[1][0].getText()) && player.equals(tictactoeBTN[1][1].getText())
                    && player.equals(tictactoeBTN[1][2].getText())) || (player.equals(tictactoeBTN[2][0].getText()) && player.equals(tictactoeBTN[2][1].getText()) && player.equals(tictactoeBTN[2][2].getText())))
            {

                return true;
            }


            //check diagonally
            if (player.equals(tictactoeBTN[0][0].getText())
                    && player.equals(tictactoeBTN[1][1].getText())
                    && player.equals(tictactoeBTN[2][2].getText()))
            {
                return true;
            }

            return false;

        }

        private void setData()
        {
            //read file for data :wins,losses,ties
            //TODO - READ File
            wins = 0;
            loss=0;
            ties=0;
            numOfGame=0;
            turn = 0;
        }
        private void restGame()
        {
            for (int c= 0; c < 3 ; c++)
            {
                for (int r=0; r<3 ; r++)
                {
                    tictactoeBTN[c][r] .setText("");
                }
            }
            turn = 0 ;
            headerLabel.setText(" X's turn , CLick Button to take spot");
        }
        private void gameSocer()
        {
            winsLabel.setText(String.valueOf(Xcount));
            lossesLabel.setText(String.valueOf(Ocount2));

        }




}
