package validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

import java.util.Date;

public class IBANValidator extends ValidatorBase {

    @Override
    protected void eval(){
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    private void evalTextInputField(){
        TextInputControl textField = (TextInputControl) srcControl.get();

        if(textField.getText().matches("^DE\\d{2}\\s?([0-9a-zA-Z]{4}\\s?){4}[0-9a-zA-Z]{2}$")){
           hasErrors.set(false);
        }
        else{
            hasErrors.set(true);
        }
    }
}
