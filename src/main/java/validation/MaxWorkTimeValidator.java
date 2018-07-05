package validation;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

public class MaxWorkTimeValidator extends ValidatorBase {

    @Override
    protected void eval(){
        if(srcControl.get() instanceof TextInputControl){
            evalTextInputField();
        }
    }

    private void evalTextInputField(){
        TextInputControl textField = (TextInputControl) srcControl.get();

        try{
            Double worktimeInMin = Double.parseDouble(textField.getText());
            if(worktimeInMin < 540L){
                hasErrors.set(false);
            }
            else{
                hasErrors.set(true);
            }
        }
        catch (Error e){
            hasErrors.set(true);
        }
    }
}
