package com.itzjacki.afterglow.controllers;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class NicknameChangeDialog extends Dialog{

    private final TextField inputField;
    private final Skin skin;

    public NicknameChangeDialog(Skin skin){
        super("", skin);
        this.skin = skin;
        inputField = new TextField("", this.skin);
        TextButton changeButton = new TextButton("Change", this.skin);

        changeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(changeNickname()){
                    giveSuccessfulFeedback();
                }
                else{
                    giveUnsuccessfulFeedback();
                }

            }
        });

        this.text("Choose new nickname: ");
        this.getContentTable().add(inputField);
        this.getContentTable().add(changeButton).row();
        this.button("Back");
    }

    // Returns true if nickname is valid, false if not
    private boolean changeNickname(){
        String newNickname = inputField.getText();

        // Checks that length is between 3 and 16 (inclusive)
        boolean isValid = newNickname.length() >= 3 && newNickname.length() <= 16;

        // Checks that all characters are alphanumerics, dashes or underscores
        for(char c : newNickname.toCharArray()){
            isValid = isValid && (Character.isLetterOrDigit(c) || c == '-' || c == '_');
        }

        if(isValid){
            EventManager.getInstance().changeNickname(newNickname);
        }

        return isValid;
    }

    private void giveSuccessfulFeedback(){
        this.getContentTable().reset();
        this.text("Name changed successfully");
    }

    private void giveUnsuccessfulFeedback(){
        Dialog instructionDialog = new Dialog("", this.skin);
        instructionDialog.text("Invalid username. Username must be:\n- 3 to 16 characters long.\n- Composed only of letters, digits,\ndashes and underscores");
        instructionDialog.button("Ok");
        instructionDialog.show(this.getStage());
    }
}
