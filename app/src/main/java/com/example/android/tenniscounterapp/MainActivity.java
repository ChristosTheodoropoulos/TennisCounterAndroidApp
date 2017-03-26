package com.example.android.tenniscounterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * Initialize global variables.
     */
    String scoreOfPlayerA = "0";
    String scoreOfPlayerB = "0";

    int tieBreakScorePlayerA = 0;
    int tieBreakScorePlayerB = 0;

    int gamesPlayerA = 0;
    int gamesPlayerB = 0;
    int setsPlayerA = 0;
    int setsPlayerB = 0;

    boolean tieBreak = false;
    boolean gameCompleted = false;

    /**
     * Declare all my constant values (e.g. 15, 30, 40, A) as global constants.
     */
    public static final String zero = "0";
    public static final String fithteen = "15";
    public static final String thirty = "30";
    public static final String forty = "40";
    public static final String advantage = "A";

    /**
     * Declare the views globally for best performance.
     */
    TextView scoreViewPlayerA;
    TextView scoreViewPlayerB;
    TextView gamesViewPlayerA;
    TextView gamesViewPlayerB;
    TextView setsViewPlayerA;
    TextView setsViewPlayerB;

    /**
     * When i rotate the device to landscape, the Activity is recreated again.
     * So by default the app doesn't remember the values of the variables I am using.
     * I must implement onSaveInstanceState() and add key-value pairs to the Bundle object.
     * @param savedInstanceState: Save additional state information for my activity
     */
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        // Save custom values into the bundle
        savedInstanceState.putString("scoreOfPlayerA", scoreOfPlayerA);
        savedInstanceState.putString("scoreOfPlayerB", scoreOfPlayerB);

        savedInstanceState.putInt("tieBreakScorePlayerA", tieBreakScorePlayerA);
        savedInstanceState.putInt("tieBreakScorePlayerB", tieBreakScorePlayerB);

        savedInstanceState.putInt("gamesPlayerA", gamesPlayerA);
        savedInstanceState.putInt("gamesPlayerB", gamesPlayerB);

        savedInstanceState.putInt("setsPlayerA", setsPlayerA);
        savedInstanceState.putInt("setsPlayerB", setsPlayerB);

        savedInstanceState.putBoolean("tieBreak", tieBreak);
        savedInstanceState.putBoolean("gameCompleted", gameCompleted);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Initialize the views.
         */
        scoreViewPlayerA = (TextView) findViewById(R.id.player_a_score);
        scoreViewPlayerB = (TextView) findViewById(R.id.player_b_score);
        gamesViewPlayerA = (TextView) findViewById(R.id.player_a_games);
        gamesViewPlayerB = (TextView) findViewById(R.id.player_b_games);
        setsViewPlayerA = (TextView) findViewById(R.id.player_a_sets);
        setsViewPlayerB = (TextView) findViewById(R.id.player_b_sets);

        if (savedInstanceState != null) {
            scoreOfPlayerA = savedInstanceState.getString("scoreOfPlayerA");
            scoreOfPlayerB = savedInstanceState.getString("scoreOfPlayerB");

            tieBreakScorePlayerA = savedInstanceState.getInt("tieBreakScorePlayerA");
            tieBreakScorePlayerB = savedInstanceState.getInt("tieBreakScorePlayerB");

            gamesPlayerA = savedInstanceState.getInt("gamesPlayerA");
            gamesPlayerB = savedInstanceState.getInt("gamesPlayerB");

            setsPlayerA = savedInstanceState.getInt("setsPlayerA");
            setsPlayerB = savedInstanceState.getInt("setsPlayerB");

            tieBreak = savedInstanceState.getBoolean("tieBreak");
            gameCompleted = savedInstanceState.getBoolean("gameCompleted");

            if (tieBreak == false){
                scoreViewPlayerA.setText(scoreOfPlayerA);
                scoreViewPlayerB.setText(scoreOfPlayerB);
            }
            else {
                scoreViewPlayerA.setText(String.valueOf(tieBreakScorePlayerA));
                scoreViewPlayerB.setText(String.valueOf(tieBreakScorePlayerB));
            }
            gamesViewPlayerA.setText(String.valueOf(gamesPlayerA));
            gamesViewPlayerB.setText(String.valueOf(gamesPlayerB));

            setsViewPlayerA.setText(String.valueOf(setsPlayerA));
            setsViewPlayerB.setText(String.valueOf(setsPlayerB));
        }
    }

    /**
     * This method is called when the +1 point button is clicked for player A.
     * In this method, i check all possible situations that may occur when
     * player a wins a point.
     */
    public void addPointForPlayerA(View view) {

        if (tieBreak == false) {
            if (scoreOfPlayerA == zero) {
                scoreOfPlayerA = fithteen;
            } else if (scoreOfPlayerA == fithteen) {
                scoreOfPlayerA = thirty;
            } else if (scoreOfPlayerA == thirty) {
                scoreOfPlayerA = forty;
            } else if (scoreOfPlayerA == forty) {
                if (scoreOfPlayerB == advantage) {
                    scoreOfPlayerA = forty;
                    scoreOfPlayerB = forty;
                } else if (scoreOfPlayerB == forty) {
                    scoreOfPlayerA = advantage;
                } else {
                    if (gamesPlayerA == 5 && gamesPlayerB < 5) {
                        setsPlayerA++;
                        gamesPlayerA = 0;
                        gamesPlayerB = 0;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    } else if (gamesPlayerA == 6 && gamesPlayerB == 5) {
                        setsPlayerA++;
                        gamesPlayerA = 0;
                        gamesPlayerB = 0;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    } else {
                        gameCompleted = true;
                        gamesPlayerA++;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    }
                }
            } else {
                gamesPlayerA++;
                scoreOfPlayerA = zero;
                scoreOfPlayerB = zero;
                gameCompleted = true;
            }
        }
        if (gamesPlayerA == 6 && gamesPlayerB == 6) {
            tieBreak = true;
            scoreOfPlayerA = scoreOfPlayerB = zero;
        }
        if (tieBreak == false) {
            setDisplay();
        } else {
            if (gameCompleted == false) {
                tieBreakScorePlayerA++;
            }
            if (tieBreakScorePlayerA < 7) {
                setDisplayTieBreak();
            } else {
                if (tieBreakScorePlayerA - tieBreakScorePlayerB >= 2) {
                    scoreOfPlayerA = scoreOfPlayerB = zero;
                    gamesPlayerA = gamesPlayerB = 0;
                    setsPlayerA++;
                    tieBreakScorePlayerA = tieBreakScorePlayerB = 0;
                    tieBreak = false;
                    setDisplayTieBreak();
                } else {
                    setDisplayTieBreak();
                }
            }
        }
        gameCompleted = false;
    }

    /**
     * This method is called when the +1 point button is clicked for player B.
     * In this method, i check all possible situations that may occur when
     * player b wins a point.
     */
    public void addPointForPlayerB(View view) {

        if (tieBreak == false) {
            if (scoreOfPlayerB == zero) {
                scoreOfPlayerB = fithteen;
            } else if (scoreOfPlayerB == fithteen) {
                scoreOfPlayerB = thirty;
            } else if (scoreOfPlayerB == thirty) {
                scoreOfPlayerB = forty;
            } else if (scoreOfPlayerB == forty) {
                if (scoreOfPlayerA == advantage) {
                    scoreOfPlayerA = forty;
                    scoreOfPlayerB = forty;
                } else if (scoreOfPlayerA == forty) {
                    scoreOfPlayerB = advantage;
                } else {
                    if (gamesPlayerB == 5 && gamesPlayerA < 5) {
                        setsPlayerB++;
                        gamesPlayerA = 0;
                        gamesPlayerB = 0;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    } else if (gamesPlayerB == 6 && gamesPlayerA == 5) {
                        setsPlayerB++;
                        gamesPlayerA = 0;
                        gamesPlayerB = 0;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    } else {
                        gameCompleted = true;
                        gamesPlayerB++;
                        scoreOfPlayerA = scoreOfPlayerB = zero;
                    }
                }
            } else {
                gamesPlayerB++;
                scoreOfPlayerA = zero;
                scoreOfPlayerB = zero;
                gameCompleted = true;
            }
        }
        if (gamesPlayerA == 6 && gamesPlayerB == 6) {
            tieBreak = true;
            scoreOfPlayerA = scoreOfPlayerB = zero;
        }
        if (tieBreak == false) {
            setDisplay();
        } else {
            if (gameCompleted == false) {
                tieBreakScorePlayerB++;
            }
            if (tieBreakScorePlayerB < 7) {
                setDisplayTieBreak();
            } else {
                if (tieBreakScorePlayerB - tieBreakScorePlayerA >= 2) {
                    scoreOfPlayerA = scoreOfPlayerB = zero;
                    gamesPlayerA = gamesPlayerB = 0;
                    setsPlayerB++;
                    tieBreakScorePlayerA = tieBreakScorePlayerB = 0;
                    tieBreak = false;
                    setDisplayTieBreak();
                } else {
                    setDisplayTieBreak();
                }
            }
        }
        gameCompleted = false;
    }


    /**
     * This method is called when the New Game button is clicked.
     * In this method i initialize all the variables, in order to
     * start a new tennis game.
     */
    public void resetScore(View view) {
        scoreOfPlayerA = zero;
        scoreOfPlayerB = zero;
        tieBreakScorePlayerA = 0;
        tieBreakScorePlayerB = 0;
        gamesPlayerA = 0;
        gamesPlayerB = 0;
        setsPlayerA = 0;
        setsPlayerB = 0;
        tieBreak = false;
        gameCompleted = false;
        setDisplay();
    }

    /**
     * Setting in order to display the right score everytime.
     */
    public void setDisplay(){
        displayPointsForPlayer(scoreOfPlayerA, scoreViewPlayerA);
        displayPointsForPlayer(scoreOfPlayerB, scoreViewPlayerB);
        displayGamesSetsForPlayer(gamesPlayerA, gamesViewPlayerA);
        displayGamesSetsForPlayer(gamesPlayerB, gamesViewPlayerB);
        displayGamesSetsForPlayer(setsPlayerA, setsViewPlayerA);
        displayGamesSetsForPlayer(setsPlayerB, setsViewPlayerB);
    }

    public void setDisplayTieBreak(){
        displayPointsForPlayer(String.valueOf(tieBreakScorePlayerA), scoreViewPlayerA);
        displayPointsForPlayer(String.valueOf(tieBreakScorePlayerB), scoreViewPlayerB);
        displayGamesSetsForPlayer(gamesPlayerA, gamesViewPlayerA);
        displayGamesSetsForPlayer(gamesPlayerB, gamesViewPlayerB);
        displayGamesSetsForPlayer(setsPlayerA, setsViewPlayerA);
        displayGamesSetsForPlayer(setsPlayerB, setsViewPlayerB);
    }

    /**
     * Methods for showing the score between the two players.
     */

    /**
     * Displays the given score for Player A or B.
     */
    public void displayPointsForPlayer(String score, TextView scoreView) {
        scoreView.setText(String.valueOf(score));
    }

    /**
     * Displays the games or the sets for Player A or B.
     */
    public void displayGamesSetsForPlayer(int score, TextView setsGamesView) {
        setsGamesView.setText(String.valueOf(score));
    }
}