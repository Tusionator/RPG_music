package music;

import java.io.File;
import java.net.URI;
import java.util.*;

public class ManagerFile {

    private File mainDirectory;


    public File getMainDirectory() {
        if (mainDirectory == null || !mainDirectory.isDirectory()) {
            setMainDirectory();
        }
        return mainDirectory;
    }

    public void setMainDirectory() {
        //public because may be set also later
        //TODO change that this is not hardcoded
        mainDirectory = new File("D:\\muzyka\\RPG");
    }

    public ArrayList<File> getSortedMusicFoldersList() {
        File mainDirectory = getMainDirectory();
        File[] fileList = mainDirectory.listFiles();
        ArrayList<File> foldersArray = new ArrayList<>();
        if (fileList != null) {
            for (File file : fileList) {
                if (file.isDirectory() && checkNameConvention(file.getName())) {
                    foldersArray.add(file);
                }
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
        if(fileName!=null && fileName.contains("_")) {
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

    public List<URI> getMusicUrisFromFolder(String folderName) {

        return null;
    }

    public void setCurrentMusicFolder() {

    }

    public void changeMusicFolderName() {

    }
}
