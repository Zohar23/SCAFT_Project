package Controllers;

import Services.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Map;

public class MainWindow_Controller {

    //region Settings Section
    @FXML
    private Button btnEditSettings;
    @FXML
    private Button btnAddNeighbor;
    @FXML
    private Button btnRemoveNeighbor;
    @FXML
    private ListView lvNeighbors;
    @FXML
    private TextField txtSharedPassword;
    public TextField txtAddNeighborName;
    public TextField txtAddNeighborIPPort;
    //endregion Settings Section

    //region Chat Section
    @FXML
    private Button btnSendFile;
    @FXML
    private Button btnSendMessage;
    @FXML
    private TextArea taMessage;
    @FXML
    private TextArea taChat;
    @FXML
    private ListView lvConnectedNeighbors;
    @FXML
    private Button btnConnect;
    //endregion Chat Section

    Settings settings;
    Map<String,String> neighbors;

    public void initialize(){
        settings = new Settings();
        txtSharedPassword.setText(settings.getSharedPassword());
        UpdateNeighborsList();
    }

    //region Settings Methods

    private void UpdateNeighborsList(){
        neighbors = settings.getAllNeighbors();
        lvNeighbors.getItems().clear();
        if(neighbors.size() == 0){
            btnRemoveNeighbor.setDisable(true);
        }
        else{
            //lvNeighbors.setItems(FXCollections.observableArrayList(neighbors.keySet()));
            for (String neighbor:neighbors.keySet()) {
                lvNeighbors.getItems().add(neighbor + " (" + neighbors.get(neighbor) + ")");
            }
        }
    }

    public void OnOffSettings(){
        if(btnEditSettings.getText().equals("Edit Settings")) {
            btnEditSettings.setText("Save Settings");
            btnAddNeighbor.setDisable(false);
            if(neighbors.size() > 0){
                btnRemoveNeighbor.setDisable(false);
            }
            lvNeighbors.setDisable(false);
            txtSharedPassword.setDisable(false);
            txtAddNeighborName.setDisable(false);
            txtAddNeighborIPPort.setDisable(false);
        }
        else{
            if(txtSharedPassword.getText().equals("")){
                showAlert("Shared password cannot be empty");
            }
            else {
                btnEditSettings.setText("Edit Settings");
                btnAddNeighbor.setDisable(true);
                btnRemoveNeighbor.setDisable(true);
                lvNeighbors.setDisable(true);
                txtSharedPassword.setDisable(true);
                txtAddNeighborName.setDisable(true);
                txtAddNeighborIPPort.setDisable(true);
                String pass = txtSharedPassword.getText();
                settings.setSharedPassword(pass);
                settings.SaveChanges();
            }
        }
    }

    public void addNeighbor(){
        //TODO check if ip is in the correct format
        if(!txtAddNeighborName.getText().equals("") || !txtAddNeighborIPPort.getText().equals("")) {
            btnRemoveNeighbor.setDisable(false);
            settings.addNeighbor(txtAddNeighborName.getText(), txtAddNeighborIPPort.getText());
            txtAddNeighborIPPort.setText("");
            txtAddNeighborName.setText("");
            UpdateNeighborsList();
        }
        else{
            showAlert("Please enter neighbor name and IP:Port");
        }
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.showAndWait();
    }

    public void removeNeighbor(){
        //String neighborToRemove = lvNeighbors.getSelectionModel().getSelectedItem().toString();
        int index = lvNeighbors.getSelectionModel().getSelectedIndex();
        if(index >= 0) {
            Object[] neighborsNames = neighbors.keySet().toArray();
            settings.removeNeighbor(neighborsNames[index].toString());
            UpdateNeighborsList();
        }
        else{
            showAlert("Please select a neighbor to remove");
        }
    }
    //endregion Settings Methods

}
