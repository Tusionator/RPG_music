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
    public static MusicStatusObservable sMusicStatus = new MusicStatusObservable();

    //sets static list of music URIs(as strings) in ManagerMusic, shuffle it and returns names to be write down on list in view
    public static ArrayList<String> setMusicList(File musicFolder) {

        //URI array
        if (sSongsMediaList != null) {
            sSongsMediaList.clear();
        } else {
            sSongsMediaList = new ArrayList<>();
        }
        if (musicFolder == null || !musicFolder.isDirectory() ||
                musicFolder.list() == null || musicFolder.list().length < 1) {
            if (sSong == null) {
                sMusicStatus.setStatus(MusicStatusObservable.NOTHING_TO_PLAY);
            }
            System.out.println("Folder is empty or doesn't exists or isn't a directory");
            return null;
        }
        if (sSong == null || (sSong.getStatus()!=MediaPlayer.Status.PAUSED && sSong.getStatus()!=MediaPlayer.Status.PLAYING)) {
            sMusicStatus.setStatus(MusicStatusObservable.STOPPED);
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
//        if (sSongsMediaList == null || sSongsMediaList.size() < 1) {
//            if (sSong != null) {
//                if (sMusicStatus.getStatus() == MusicStatusObservable.PAUSED) {
//                    sSong.play();
//                    sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
//                } else if (sMusicStatus.getStatus() == MusicStatusObservable.PLAYING) {
//                    sSong.pause();
//                    sMusicStatus.setStatus(MusicStatusObservable.PAUSED);
//                }
//            }
//            System.out.println("no music here");
//            return;
//        }
//        if (sSong == null) {
//            sCurrentMusicNo = 0;
//            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
//            sSong.setOnEndOfMedia(new NextSongRunnable());
//            sSong.setVolume(sVolume);
//            sSong.play();
//            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
//        } else if (sSong.getStatus() == MediaPlayer.Status.PLAYING) {
//            //pause music
//            sSong.pause();
//            sMusicStatus.setStatus(MusicStatusObservable.PAUSED);
//        } else if (sSong.getMedia().equals(sSongsMediaList.get(sCurrentMusicNo))) {
//            sSong.setVolume(sVolume);
//            sSong.play();
//            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
//        } else {
//            sCurrentMusicNo = 0;
//            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
//            sSong.setVolume(sVolume);
//            sSong.play();
//            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
//        }

        if (sSong == null && (sSongsMediaList == null || sSongsMediaList.size() < 1)) {
            sMusicStatus.setStatus(MusicStatusObservable.NOTHING_TO_PLAY);
            System.out.println("no music here");
            return;
        }
        if (sSong == null) {
            //play first in list
            sCurrentMusicNo = 0;
            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
            sSong.setOnEndOfMedia(new NextSongRunnable());
            sSong.setVolume(sVolume);
            sSong.play();
            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
        } else if (sSong.getStatus() == MediaPlayer.Status.PLAYING) {
            //pause music
            sSong.pause();
            sMusicStatus.setStatus(MusicStatusObservable.PAUSED);
        } else {
            //play
            sSong.setVolume(sVolume);
            sSong.play();
            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
        }
    }

    private static class NextSongRunnable implements Runnable {
        @Override
        public void run() {
            if (sSongsMediaList == null || sSongsMediaList.size() < 1) {
                sSong = null;
                sMusicStatus.setStatus(MusicStatusObservable.NOTHING_TO_PLAY);
                System.out.println("no music here");
                return;
            }
            sCurrentMusicNo++;
            sCurrentMusicNo = sCurrentMusicNo % sSongsMediaList.size();
            sSong = new MediaPlayer(sSongsMediaList.get(sCurrentMusicNo));
            sSong.setVolume(sVolume);
            sSong.play();
            sMusicStatus.setStatus(MusicStatusObservable.PLAYING);
            sSong.setOnEndOfMedia(this);
        }
    }

    public static void stopMusic() {
        if (sSong != null) {
            sSong.stop();
            sMusicStatus.setStatus(MusicStatusObservable.STOPPED);
            sCurrentMusicNo = 0;
            sSong = null;
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
