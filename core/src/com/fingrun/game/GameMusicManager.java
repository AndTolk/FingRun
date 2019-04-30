package com.fingrun.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;


public class GameMusicManager {

    private Music music;

    public GameMusicManager() {
        music = Gdx.audio.newMusic(Gdx.files.internal("StandartTheme.mp3"));
        music.setLooping(true);
        music.play();
    }
    public void playMusic(boolean play){
        if(play && !music.isPlaying()) {
            music.play();
        }
       if(!play && music.isPlaying()) {
           music.pause();
       }
    }

    public boolean isPlayingMusic(){
        if(music.isPlaying())
            return true;
        return false;
    }
}
