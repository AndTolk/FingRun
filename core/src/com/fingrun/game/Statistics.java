package com.fingrun.game;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Statistics implements Serializable {

    private static final int COUNT_ACHIVMENTS = 1;

    private boolean[] allAchivments;
    private int bestScore;

    public Statistics() {
        allAchivments = new boolean[COUNT_ACHIVMENTS];
    }

    public void saveScore(Statistics statistics) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("score");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(statistics);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBestScore(int curScore){
        if (bestScore < curScore)
            bestScore = curScore;
        if (bestScore > 500)
            allAchivments[0] = true;
    }

    public int getBestScore() {
        return bestScore;
    }
}
