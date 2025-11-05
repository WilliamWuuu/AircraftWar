package edu.hitsz.application;

import java.util.ArrayList;

/**
 * @author Yangxin Wu
 * @date 2025/10/20 17:35
 */
public class MusicManager {
    public static boolean enabled = true;

    public static void playOnce(String filename) {
        System.out.println(enabled);
        if(!enabled) return;
        new MusicThread(filename, false).start();
    }

    private static ArrayList<MusicThread> musicThreads = new ArrayList<>();

    public static MusicThread playLoop(String filename) {
        System.out.println(enabled);
        System.out.println(filename);
        if(!enabled) return null;
        var musicThread = new MusicThread(filename, true);
        musicThreads.add(musicThread);
        musicThread.start();
        return musicThread;
    }

    public static void stopall() {
        for(var s : musicThreads) s.stopMusic();
    }
}
