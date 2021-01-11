package ro.mta.se.lab.model;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class FileWorker {

    private String filename;

    public FileWorker(String filename) throws IOException {
        this.filename = filename;
        File file = new File(getFilename());
        file.createNewFile();
    }

    public ArrayList<String> readFromFile() throws Exception {

        ArrayList<String> retArray = new ArrayList<>();
        BufferedReader buf = new BufferedReader(new FileReader(this.getFilename()));

        String strRead;
        strRead = buf.readLine();
        while ((strRead = buf.readLine()) != null) {
            retArray.add(strRead);
        }
        buf.close();

        return retArray;
    }

    public void writeToFile(Weather w) throws IOException {

        FileWriter fw = new FileWriter(getFilename(), true);
        String s = w.getCurrentDate() + " || " + w.getCurrentCity() + " -- " + w.getDesc()
                + " -- Temp: " + w.getTemp() + " -- Wspeed:  " + w.getWind()
                + " -- Pres:  " + w.getPression() + " -- Hum:  " + w.getHumidity()
                + " -- Min:  " + w.getMinTemp() + ", Max: " + w.getMaxTemp() + "\n";

        fw.write(s);
        fw.close();
    }

    //region getters and setters
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    //endregion

}
