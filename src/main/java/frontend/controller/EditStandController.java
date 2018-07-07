package frontend.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.base.ValidatorBase;
import controller.EntityController;
import entities.Stand;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utilities.AlerterMessagePopup;
import java.net.URL;
import java.util.ResourceBundle;

public class EditStandController implements Initializable {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();
    private final EntityController controller = new EntityController();

    private ValidatorBase vBase;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtType;

    @FXML
    private JFXTextArea txtComments;

    @FXML
    private ScrollPane editStandPane;


    private Stand stand;


    @FXML
    public void initialize(URL url, ResourceBundle rb){

    }

    /**
     * liest die Bedienereingabe der StandView aus
     * @param stand stand Entität
     */
    public void setDataFromView(Stand stand) {

        this.stand = stand;

        txtName.setText(stand.getName());
        txtType.setText(stand.getType());
        txtComments.setText(stand.getComment());
    }

    /**
     * Event des Speicherbuttons,
     * Entweder ein Update oder Save
     * @param event
     */
    @FXML
    public void apply(ActionEvent event){
        try{
            controller.save(Stand.class, generate());
            popup.generateInformationPopupWindow(txtName.getText() + " wurde verarbeitet.");
            Stage stage = (Stage) editStandPane.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
        catch(NumberFormatException e){
            popup.generateWarningPopupWindow("Es wurden ungültige Zeichen in reinen Zahlenfeldern festgestellt.");
        }
    }

    /**
     * erstelle Stand von vorhandenem Stand
     * @return Stand
     */
    private Stand generate() {
        if(this.stand == null){
            this.stand =  new Stand();
        }

        stand.setName(txtName.getText());
        stand.setType(txtType.getText());
        stand.setComment(txtComments.getText());

        return stand;
    }
}
