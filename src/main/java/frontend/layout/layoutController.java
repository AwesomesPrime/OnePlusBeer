package frontend.layout;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class layoutController implements Initializable {

    @FXML
    private Pane currentPane;


    @FXML
    public void initialize(URL url, ResourceBundle rb){
        //Leer
    }

    private void setAnchorPaneContraints(Node node){
        AnchorPane.setBottomAnchor(node, 0.0);
        AnchorPane.setTopAnchor(node, 0.0);
        AnchorPane.setLeftAnchor(node, 0.0);
        AnchorPane.setRightAnchor(node, 0.0);
    }

    public void openLogin(MouseEvent event) throws IOException {
        GridPane loginPane =  FXMLLoader.load(getClass().getResource("/frontend/login/login.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        setAnchorPaneContraints(loginPane);
        currentPane.getChildren().add(loginPane);
    }

    public void openEmployeeList(MouseEvent event) throws IOException {
        ScrollPane employeePane =  FXMLLoader.load(getClass().getResource("/frontend/employeelist/employeelist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        setAnchorPaneContraints(employeePane);
        currentPane.getChildren().add(employeePane);
    }

    public void openEventList(MouseEvent event) throws IOException {
        ScrollPane eventPane =  FXMLLoader.load(getClass().getResource("/frontend/eventlist/eventlist.fxml"));
        currentPane.getChildren().remove(0, currentPane.getChildren().size());
        setAnchorPaneContraints(eventPane);
        currentPane.getChildren().add(eventPane);
    }

}
