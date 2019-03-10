package control;

import java.io.*;

public class CSVReader {

    private File managedFile;

    public CSVReader(String fileName){
        managedFile = new File(fileName);
    }

    public String[] readFromCSVFile(){
        try(InputStreamReader reader = new FileReader(managedFile)) {


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
