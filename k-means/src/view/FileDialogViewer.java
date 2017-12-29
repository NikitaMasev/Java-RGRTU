package view;

import Constants.ConstanterError;
import com.sun.javafx.scene.paint.GradientUtils;
import java.awt.Point;
import java.io.File;
import javafx.scene.effect.Light;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FileDialogViewer {

  private String filter = "";
  private String filterDescribtion="";
  private Stage stage;
  public FileDialogViewer(String filter,String filterDescribtion,Stage stage) {
    this.filter = filter;
    this.filterDescribtion = filterDescribtion;
    this.stage = stage;
  }
  public File getFile () throws Exception {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(filterDescribtion,filter);
    fileChooser.setInitialDirectory(new File(".").getAbsoluteFile());
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(stage);
    if (file==null) {
      throw new Exception(ConstanterError.FILE_NOT_FOUND);
    }
    return file;
  }
}
