package MainGame;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
//music class taken from an online example that creates music objects and allows playing, etc.
public class Music {
    Clip clip;
    AudioInputStream sound;
    private final String fileDir;
    private final boolean noSound = false;

    public Music(String fileDir) {
        this.fileDir = fileDir;
    }

    public void setFile(String soundFileName) {
        if(noSound) return;
        try {//gets file and tries setting the current object to the sound file
            File file = new File(soundFileName);
            sound = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(sound);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void play() {
        if(noSound) return;
        setFile(fileDir);
        clip.start();
    }//plays audio
    public void loop(){
        if(noSound) return;
        setFile(fileDir);
        clip.loop(-1);
    }

        public void stop(){
        if(noSound) return;
        if(sound == null) return;
        try{
            sound.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clip.close();
        clip.stop();
    }
}