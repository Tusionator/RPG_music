package music;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ManagerFile {

    private  File mainDirectory;


    public  File getMainDirectory(){
        if(mainDirectory == null || !mainDirectory.isDirectory()){
            setMainDirectory();
        }
        return mainDirectory;
    }

    public  void setMainDirectory(){
        //public because may be set also later
        //TODO change that this is not hardcoded
        mainDirectory = new File("D:\\muzyka\\RPG");
    }

    public List<File> getMusicFoldersNames(){
        File mainDirectory = getMainDirectory();
        File[] fileList = mainDirectory.listFiles();

        ArrayList<File> foldersArray = new ArrayList<>();

        if(fileList != null){
            for(File file : fileList){
                if(file.isDirectory() && checkNameConvention(file.getName())){
                    System.out.println("success: " + file.getName());
                }

            }
        }


        return null;
    }

    private boolean checkNameConvention(String fileName){
        String[] strTable = fileName.split("_");
        if(strTable[0] != null && !strTable[0].isEmpty()){
            String prefix = strTable[0];
            for(int i=0; i<prefix.length(); i++){
                if(prefix.charAt(i)<'0' || prefix.charAt(i)>'9'){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public List<URI> getMusicUrisFromFolder(String folderName){

        return null;
    }

    public void setCurrentMusicFolder(){

    }

    public void changeMusicFolderName(){

    }
}
