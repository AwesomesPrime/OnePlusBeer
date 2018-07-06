package frontend.controller;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sun.applet.Main;
import utilities.AlerterMessagePopup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public abstract class GenericListController implements Initializable {

    @FXML
    protected JFXTextField search;

    @FXML
    protected FontAwesomeIconView add;

    @FXML
    protected FontAwesomeIconView delete;

    public final AlerterMessagePopup popup = new AlerterMessagePopup();

    protected String fxml;

    protected FXMLLoader fxmlLoader;
    @FXML
    public abstract void initialize(URL url, ResourceBundle rb);

    protected abstract boolean checkPermission();

    protected abstract void resetTableView();

    protected abstract void refreshTableView();

    protected abstract <T> T getSelectedItem();

    protected abstract void setDataToEdit();

    protected abstract void copySelectedItem();

    protected abstract void deleteSelectedItem();

    protected abstract <T> ObservableList<T> getAllItems();

    protected abstract <T> ObservableList<T> getFilteredItems();

    public abstract void search();

    protected void setFXML(String fxmlName){
        fxml = fxmlName;
    }

    protected void setFXMLLoader(String fxmlName){
        fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlName));
    }

    public void add() throws IOException {
        if(checkPermission()){
            openAdd();
        }
    }

    public void edit(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2 && checkPermission()) {
            openEdit();
        }
    }

    public void copy(){
        if(checkPermission()){
            copySelectedItem();
        }
    }

    public void delete(){
        if(checkPermission()){
            deleteSelectedItem();
        }
    }

    private void openEdit() throws IOException {
        setFXMLLoader(fxml);
        Stage stage = setupStage(true);
        setCloseHandler(stage, "edit");
        stage.show();
    }

    private void openAdd() throws IOException {
        setFXMLLoader(fxml);
        Stage stage = setupStage(false);
        setCloseHandler(stage, "add");
        stage.show();
    }

    private Stage setupStage(boolean inject) throws IOException {
        Parent root = fxmlLoader.load();
        if(inject){
            setDataToEdit();
        }
        Scene scene = new Scene(root,500,500);
        scene.getStylesheets().add(Main.class.getResource("/styles/basic.css").toExternalForm());
        Stage stage = new Stage();
        stage.setScene(scene);
        return stage;
    }

    private void setCloseHandler(Stage stage, String option){
        if(option.equals("add")){
            stage.setOnCloseRequest(e -> resetTableView());
        }else if(option.equals("edit")){
            stage.setOnCloseRequest(e -> refreshTableView());
        }
    }

}
