package game.sound;

import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * MusicPlayer: 배경 음악을 재생하는 클래스입니다.
 */
public class MusicPlayer {
    private Thread musicThread;
    private boolean musicPlaying;

    public void start() {
        musicThread = new Thread(() -> playBackgroundMusic("/resource/sounds/PuzzleGameBM.mp3"));
        musicThread.start();
    }

    public void stop() {
        musicPlaying = false;
        if (musicThread != null && musicThread.isAlive()) {
            musicThread.interrupt();
        }
    }

    private void playBackgroundMusic(String filepath) {
        musicPlaying = true;
        while (musicPlaying) {
            try {
                URL soundURL = getClass().getResource(filepath);
                if (soundURL == null) {
                    throw new RuntimeException("Sound file not found: " + filepath);
                }
                InputStream inputStream = new BufferedInputStream(soundURL.openStream());
                Player player = new Player(inputStream);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
