package model;

import Constants.ConstanterError;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReaderFile {

  private File srcFile;

  public ReaderFile(File srcFile) {
    this.srcFile = srcFile;
  }

  public List<Point> getListPoints () throws Exception {
    List<Point> pointList = new ArrayList<>();
    BufferedReader br = null;
    try {
      br = new BufferedReader(new FileReader(srcFile));
      String line="";
      while ((line=br.readLine())!=null) {
        double x = Double.parseDouble(line.split(" ")[0]);
        double y = Double.parseDouble(line.split(" ")[1]);
        pointList.add(new Point(x,y));
      }
    } catch (IOException e) {
      throw new Exception(ConstanterError.ERROR_READING_FILE);
    } finally {
      br.close();
    }
    return pointList;
  }
}
