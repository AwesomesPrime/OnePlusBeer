package validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class BICValidator extends ValidatorBase {

    @Override
    protected void eval(){
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    private void evalTextInputField(){
        TextInputControl textField = (TextInputControl) srcControl.get();

        if(textField.getText().matches("([a-zA-Z]{4}[a-zA-Z]{2}[a-zA-Z0-9]{2}([a-zA-Z0-9]{3})?)")){
           hasErrors.set(false);
        }
        else{
            hasErrors.set(true);
        }
    }
}
