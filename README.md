Für Janik: 

Index: 
```
            try {
                //1. Load view (This time with View from other package)
                Parent view = (Parent) fxmlLoader.load();
                //
                RoomDetailController roomDetailController = fxmlLoader.<RoomDetailController>getController();
                roomDetailController.getDataFromIndexController(roomModelToEdit);
                //2. generate new scene from the loaded view
                Scene newScene = new Scene(view);
                //3. get stage info to change state
                Stage stage = (Stage) anchorPaneIndex.getScene().getWindow();
                //4. change scene
                stage.setScene(newScene);
            } catch (IOException e) {
                e.printStackTrace();
            }

Detail: 
    private RoomModel roomModelToEdit;

    public void getDataFromIndexController(RoomModel roomModel) {
        roomModelToEdit = roomModel;
        typeTxt.setText(roomModel.getType());
        descriptionTxt.setText(roomModel.getDescription());
        amountOfWorkspaces.setText(Integer.toString(roomModel.getAmountWorkspaces()));
}
