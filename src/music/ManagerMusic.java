package music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ManagerMusic {
    //TODO moze sie wyjebac przy zmianie listy, sprawdz potem
    //string table with music URIs form 1 folder, currently played
    private static ArrayList<Media> sSongsMediaList;
    private static MediaPlayer sSong;
    private static int sCurrentMusicNo = 0;
    private static double sVolume = Constants.INITIAL_VOLUME;

    //sets static list of music URIs(as strings) in ManagerMusic, shuffle it and returns names to be write down on list in view
    public static ArrayList<String> setMusicList(File musicFolder) {
        if (musicFolder == null || !musicFolder.isDirectory() ||
                musicFolder.list() == null || musicFolder.list().length < 1) {
            System.out.println("Folder is empty or doesn't exists or isn't a directory");
            return null;
        }

        //URI array
        if (sSongsMediaList != null) {
            sSongsMediaList.clear();
        } else {
            sSongsMediaList = new ArrayList<>();
        }
        ArrayList<String> titleNames = new ArrayList<>();
        for (File musicFile : musicFolder.listFiles()) {
            try {
                String URIstring = musicFile.toURI().toString();
                Media songMedia = new Media(URIstring);
                sSongsMediaList.add(songMedia);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.shuffle(sSongsMediaList);

        //String array
        titleNames.addAll(Arrays.asList(musicFolder.list()));
        return titleNames;
    }

    public static void playOrPauseMusic() {
        if (sSongsMediaList == null || sSongsMediaList.size() < 1) {
            System.out.println("no music here");
            return;
        }
        if (sSong == null) {
            sCurrentMusicNo = 0;
            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
            sSong.setOnEndOfMedia(new NextSongRunnable());
            sSong.setVolume(sVolume);
            sSong.play();
        } else if (sSong.getStatus() == MediaPlayer.Status.PLAYING) {
            //pause music
            sSong.pause();
        } else if (sSong.getMedia().equals(sSongsMediaList.get(sCurrentMusicNo))) {
            sSong.setVolume(sVolume);
            sSong.play();
        } else {
            sCurrentMusicNo = 0;
            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
            sSong.setVolume(sVolume);
            sSong.play();
        }
    }

    private static class NextSongRunnable implements Runnable {
        @Override
        public void run() {
            sCurrentMusicNo++;
            sCurrentMusicNo = sCurrentMusicNo % sSongsMediaList.size();
            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
            sSong.setVolume(sVolume);
            sSong.play();
            sSong.setOnEndOfMedia(this);
        }
    }

    public static void stopMusic() {
        if(sSong != null) {
            sSong.stop();
            Collections.shuffle(sSongsMediaList);
        }
    }

    public static void changeVolume(double value) {
        sVolume = value;
        if (sSong != null) {
            sSong.setVolume(value);
        }
    }

}
