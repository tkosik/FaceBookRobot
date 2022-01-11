import java.io.File;
import java.util.ArrayList;

public class FilePage {
    private final File directory = new File(Utlis.PATH_DIRECTORY);

    public int sizeOfPathDirectory(File directory){
        int fileCount = directory.list().length;
        return fileCount;
    }
    public String[] getFileList(){
        String[]fileList;
        fileList = directory.list();
        return  fileList;
    }

    public ArrayList<String> toFilePathBuilder(String pathToFile){
        ArrayList<String> photoList = new ArrayList<String>();
        for(int i = 0; i < directory.list().length; i++){
            String path ="";
            path = directory +"/" + directory.list()[i];
            photoList.add(path);
        }
        return photoList;
    }
}
