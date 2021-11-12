package edu.byuh.cis.cs203.numberedsquares;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by tylajeffs
 */

public class CountingGame implements GameStyle
{
    //fields
    private ArrayList<String> labelsPerLevel;
    private int expectedId;
    private GameStatus gameStatus;
    private int numLevels;
    private String lLabels;
    private int level;
    private ArrayList<String> levelLabels;



    /**
     * constructor, initializes a few things
     */
    public CountingGame(int maxLevels)
    {
        //set level
        level = 1;

        //set the number of levels
        numLevels = maxLevels;

        //initialize labels
        labelsPerLevel = new ArrayList<String>();
        levelLabels = new ArrayList<String>();

        lLabels = "";

        //add labels to the list
        for(int i = 1; i <= maxLevels; i++)
        {
            //create string for each level
            lLabels += i;

            //add i to the list
            labelsPerLevel.add(lLabels);
        }

        //set the label id to 0
        resetExpectedId();

    }


    /**
     * Method to get the string for the next level toast message
     *
     * @param res
     * @return String for the next level toast
     */
    @Override
    public String getNextLevelLabel(Resources res)
    {
        return "good job! advance to level " + level;
    }


    /**
     * Method to get the label for the try again toast
     *
     * @param res
     * @return String for the toast
     */
    @Override
    public String getTryAgainLabel(Resources res)
    {
        return "Wrong square! Try again";
    }




    /**
     * method to get the labels for the squares for that level
     * @return list of string labels
     */
    @Override
    public List<String> getSquareLabels()
    {
        //split the string of level labels into a new list of labels
        getThisLevelsLabels();

        return levelLabels;
    }


    /**
     * method to return the game status - continue, level complete, try again
     * @param ns Numbered Square that was touched
     * @return
     */
    @Override
    public GameStatus getGameStatus(NumberedSquare ns)
    {
        //check if square label is equal to expected label
        if(ns.id == expectedId)
        {
            //if the labels match, check to see if it is the last square in the level
            if(ns.id == level)
            {
                increaseLevel();
                resetExpectedId();
                //return level complete if it is the last square
                return gameStatus.LEVEL_COMPLETE;
            }
            else
            {
                //it's just the right square, return continue and increase the expected label
                increaseExpectedId();
                return gameStatus.CONTINUE;
            }

        }
        else   //wrong square
        {

            //(if it's not frozen) wrong square, try again
            if(!ns.frozen)
            {
                //reset the expected label id
                resetExpectedId();
                return gameStatus.TRY_AGAIN;
            }
            else //continue if the square is already frozen
            {
                return gameStatus.CONTINUE;
            }

        }

    }




    /**
     * helper method to reset the expected label id back to 1
     */
    private void resetExpectedId()
    {
        //reset the expected label id to 1
        expectedId = 1;
    }


    /**
     * helper method to increase the expected label Id by 1
     */
    private void increaseExpectedId()
    {
        //increase the expected label by one
        expectedId += 1;
    }


    /**
     * helper method that increases the level by 1
     */
    private void increaseLevel()
    {
        //increase the level by one
        level++;
    }


    /**
     * helper method to split the string per level and find the labels for one specific level
     */
    private void getThisLevelsLabels()
    {
        //clear level labels
        levelLabels.clear();

        //split the string into single characters at the index specified by level
        //in the labels per level list
        String[] theseLabels = labelsPerLevel.get(level-1).split("");

        //add each of the labels to the level labels arraylist
        for(String character : theseLabels)
        {
            levelLabels.add(character);
        }

    }

}
