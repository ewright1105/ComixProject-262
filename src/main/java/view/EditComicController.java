package view;

import java.math.BigDecimal;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Comic;
import model.MarkedStatus;

public class EditComicController {
    
    @FXML
    private TextField publisherField;
    @FXML
    private TextField seriesTitleField;
    @FXML
    private TextField volumeNumField;
    @FXML
    private TextField issueNumStringField;
    @FXML
    private TextField publicationDateField;
    @FXML
    private TextField creatorsField;
    @FXML
    private TextField principlesCharactersField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField valueField;

    @FXML
    private TextField gradeField;
    @FXML
    private TextField signatiureField;
    @FXML
    private CheckBox slabbedCheck;
    @FXML
    private CheckBox authCheck;
    private Stage stage;
    private Comic comicToEdit;

//    private TextField[] tfArray = {
//            publisherField,
//            seriesTitleField,
//            volumeNumField,
//            issueNumStringField,
//            publicationDateField,};

    public void setComicToEdit(Comic comic) {
        this.comicToEdit = comic;
        populateFields();
    }

    private void populateFields() {
        publisherField.setText(comicToEdit.getPublisher());
        seriesTitleField.setText(comicToEdit.getSeriesTitle());
        volumeNumField.setText(Integer.toString(comicToEdit.getVolumeNum())); // Convert int to String
        issueNumStringField.setText(comicToEdit.getIssueNumString());
        publicationDateField.setText(comicToEdit.getPublicationDate());
        creatorsField.setText(comicToEdit.getCreators());
        principlesCharactersField.setText(comicToEdit.getPrincipalCharacters());
        descriptionField.setText(comicToEdit.getDescription());
        valueField.setText(comicToEdit.getValue().toString()); // Convert BigDecimal to String
        gradeField.setText(String.valueOf(comicToEdit.getGrade()));
    }
    
    @FXML
    public Comic saveChanges() {

        comicToEdit.setPublisher(publisherField.getText());
        comicToEdit.setSeriesTitle(seriesTitleField.getText());
        comicToEdit.setVolumeNum(Integer.parseInt(volumeNumField.getText())); // Convert String to int
        comicToEdit.setIssueNumString(issueNumStringField.getText());
        comicToEdit.setPublicationDate(publicationDateField.getText());
        comicToEdit.setCreators(creatorsField.getText());
        comicToEdit.setPrincipalCharacters(principlesCharactersField.getText());
        comicToEdit.setDescription(descriptionField.getText());
        comicToEdit.setValue(new BigDecimal(valueField.getText())); // Convert String to BigDecimal

        comicToEdit.addMarks(Integer.parseInt(gradeField.getText()), slabbedCheck.isArmed(), signatiureField.getText(), authCheck.isArmed());
        return comicToEdit;
    }
}
