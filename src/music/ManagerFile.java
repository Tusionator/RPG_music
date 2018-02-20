package music;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.prefs.Preferences;

public class ManagerFile {
    private static File mainDirectory;


    public static File getMainDirectory() {
        if (mainDirectory == null || !mainDirectory.isDirectory()) {
            Preferences preferences = Preferences.userRoot().node(Constants.PREF_NODE_NAME);
            String mainDir = preferences.get(Constants.PREF_DIRECTORY_KEY, "");
            if(mainDir!= null && !mainDir.equals("")){
                File fileMainDir = new File(mainDir);
                if(fileMainDir.isDirectory()){
                    mainDirectory = fileMainDir;
                    return mainDirectory;
                }
            }
            return null;
        }
        return mainDirectory;
    }

    //saves directoryPath in preferences, returns false if path not a directory
    public static boolean setMainDirectory(String directoryPath) {
        mainDirectory = new File(directoryPath);
        Preferences preferences = Preferences.userRoot().node(Constants.PREF_NODE_NAME);
        preferences.put(Constants.PREF_DIRECTORY_KEY, directoryPath);
        if (mainDirectory.isDirectory()) {
            return true;
        }
        mainDirectory = null;
        return false;
    }

    public static ArrayList<File> getSortedMusicFoldersList() {
        File mainDirectory = getMainDirectory();
        if (mainDirectory == null || !mainDirectory.isDirectory()) {
            return null;
        }
        File[] fileList = mainDirectory.listFiles();
        if (fileList == null || fileList.length < 1) {
            return null;
        }
        ArrayList<File> foldersArray = new ArrayList<>();
        for (File file : fileList) {
            if (file.isDirectory() && checkNameConvention(file.getName())) {
                foldersArray.add(file);
            }
        }
        foldersArray.sort(Comparator.comparingInt(ManagerFile::getMusicFolderNumber));
        return foldersArray;
    }

    //function closely connected with #checkNameConvention - if convention changes check both functions
    //Convention: "number_name"
    public static String getMusicFolderName(File musicFolderFile) {
        if (musicFolderFile != null) {
            if (musicFolderFile.isDirectory() && checkNameConvention(musicFolderFile.getName())) {
                String name;
                String fullFolderName = musicFolderFile.getName();
                int separatorIndex = fullFolderName.indexOf('_');
                name = fullFolderName.substring(separatorIndex);
                return name;
            }
        }
        return null;
    }

    //function closely connected with #checkNameConvention - if convention changes check both functions
    //Convention: "number_name"
    public static Integer getMusicFolderNumber(File musicFolderFile) {
        if (musicFolderFile != null) {
            if (musicFolderFile.isDirectory() && checkNameConvention(musicFolderFile.getName())) {
                Integer number;
                String fullFolderName = musicFolderFile.getName();
                int separatorIndex = fullFolderName.indexOf('_');
                String strNumber = fullFolderName.substring(0, separatorIndex);
                number = Integer.valueOf(strNumber);
                return number;
            }
        }
        return null;
    }

    //function closely connected with #getMusicFolderName - if convention changes check both functions
    //Convention of folder name: "number_name"
    private static boolean checkNameConvention(String fileName) {
        if (fileName != null && fileName.contains("_")) {
            String[] splitFileName = fileName.split("_");
            if (splitFileName[0] != null && !splitFileName[0].isEmpty()) {
                String prefix = splitFileName[0];
                for (int i = 0; i < prefix.length(); i++) {
                    if (prefix.charAt(i) < '0' || prefix.charAt(i) > '9') {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


}
