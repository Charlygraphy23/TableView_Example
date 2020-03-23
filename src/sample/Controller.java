package sample;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.model.Employee;

import java.io.*;
import java.util.ArrayList;

public class Controller {


    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, String> desCol;

    @FXML
    private TableColumn<Employee, String> salaryCol;

    @FXML
    private TextField nameText;

    @FXML
    private TextField designationText;

    @FXML
    private JFXButton editButton;

    @FXML
    private TextField salaryTExt;

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton replaceButton;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem newMenu;

    @FXML
    private MenuItem saveMenue;

    @FXML
    private MenuItem OpenMenu;

    @FXML
    private MenuItem closeMenu;

    @FXML
    private MenuItem aboutMenue;

    private ObservableList<Employee> observableList;
    private File file=null;
    private  Employee employee;

    @FXML
    void initialize() {
        editButton.setVisible(true);
        replaceButton.setVisible(false);

        observableList= FXCollections.observableArrayList();

        nameCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("empName"));
        desCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("empDesignation"));
        salaryCol.setCellValueFactory(new PropertyValueFactory<Employee,String>("empSalary"));

        addButton.setOnAction(new AddDetails());
        deleteButton.setOnAction(new Deletedetails());
        saveMenue.setOnAction(new Save());
        OpenMenu.setOnAction(new open());
        newMenu.setOnAction(new New());
        closeMenu.setOnAction(e->{
            System.exit(0);
        });
        aboutMenue.setOnAction(new About());
        editButton.setOnAction(new Edit());
        replaceButton.setOnAction(new Replace());
        table.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                editButton.setVisible(true);
                replaceButton.setVisible(false);
                nameText.setText("");
                salaryTExt.setText("");
                designationText.setText("");
            }
        });

        if(observableList.isEmpty()){
            editButton.setVisible(false);
            replaceButton.setVisible(false);
            addButton.setVisible(true);
        }

    }

    private class AddDetails implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {

           if(!nameText.getText().equals("")){
                   employee=new Employee(nameText.getText(),designationText.getText(),salaryTExt.getText());
                   observableList.addAll(employee);
                   table.setItems(observableList);
                   nameText.setText("");
                   salaryTExt.setText("");
                   designationText.setText("");
                   editButton.setVisible(true);

           }
           else {
               Alert alert=new Alert(Alert.AlertType.ERROR,"Enter Details Properly", ButtonType.CLOSE);
              Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
              stage.getIcons().add(new Image("/sample/assets/images.png"));
               alert.show();
           }
        }
    }

    private class Deletedetails implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
          if(observableList.isEmpty()){
              Alert alert=new Alert(Alert.AlertType.ERROR);
              alert.setHeaderText("Nothing to delete");
              alert.getButtonTypes().setAll(ButtonType.CLOSE);
              Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
              stage.getIcons().add(new Image("/sample/assets/images.png"));
              alert.show();
          }
          else {
              observableList.remove(table.getSelectionModel().getSelectedIndex());
              initialize();
          }
        }
    }

    private class Save implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            ArrayList<Employee> list=new ArrayList<>(observableList);
            FileChooser fileChooser=new FileChooser();
            FileChooser.ExtensionFilter text_files=new FileChooser.ExtensionFilter("Text Documents(*.txt)","*.txt");
            FileChooser.ExtensionFilter all_files=new FileChooser.ExtensionFilter("All Files","*.*");
            fileChooser.getExtensionFilters().addAll(text_files,all_files);
            file=fileChooser.showSaveDialog(menubar.getScene().getWindow());
            if(file!=null){
                try {
                    BufferedWriter writer=new BufferedWriter(new FileWriter(file));
                    int i=0;
                    while(i<list.size()){
                        writer.write(list.get(i).getEmpName()+"%"+list.get(i).getEmpDesignation()+"%"+list.get(i).getEmpSalary()+"\n");
                        i++;
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class open implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
           observableList.clear();
           FileChooser fileChooser=new FileChooser();
            FileChooser.ExtensionFilter text_files=new FileChooser.ExtensionFilter("Text Documents(*.txt)","*.txt");
            FileChooser.ExtensionFilter all_files=new FileChooser.ExtensionFilter("All Files","*.*");
            fileChooser.getExtensionFilters().addAll(text_files,all_files);
           file=fileChooser.showOpenDialog(menubar.getScene().getWindow());
           if(file!=null){
               try {
                   BufferedReader reader=new BufferedReader(new FileReader(file));
                   String s=null;
                   while ((s=reader.readLine())!=null){
                       String [] r=s.split("%");
                    for(int i=0;i<r.length;i++){
                     employee=new Employee(r[0],r[1],r[2]);
                   }
                    observableList.addAll(employee);
                    table.setItems(observableList);

//                       StringTokenizer tokenizer=new StringTokenizer(s,"%");
//                       while (tokenizer.hasMoreTokens()){
//                           employee=new Employee(tokenizer.nextToken(),tokenizer.nextToken(),tokenizer.nextToken());
//                       }
//                       observableList.addAll(employee);
//                       table.setItems(observableList);

                   }
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    private class New implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            observableList.clear();
            nameText.setText("");
            salaryTExt.setText("");
            designationText.setText("");
        }
    }

    private class About implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Add Table Application" +
                    "\n@Copyright Dipta Biswas" +
                    "\n Version 1.09Va12");
            alert.getButtonTypes().setAll(ButtonType.CLOSE);
            Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/sample/assets/images.png"));
            alert.show();
        }
    }

    private class Edit implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            if(observableList.isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Nothing to Edit");
                alert.getButtonTypes().setAll(ButtonType.CLOSE);
                Stage stage=(Stage) alert.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image("/sample/assets/images.png"));
                alert.show();
            }
            else {
                nameText.setText(observableList.get(table.getSelectionModel().getSelectedIndex()).getEmpName());
                designationText.setText(observableList.get(table.getSelectionModel().getSelectedIndex()).getEmpDesignation());
                salaryTExt.setText(observableList.get(table.getSelectionModel().getSelectedIndex()).getEmpSalary());
                replaceButton.setVisible(true);
                editButton.setVisible(false);
                addButton.setVisible(false);
            }
        }
    }

    private class Replace implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            int index=observableList.indexOf(table.getSelectionModel().getSelectedItem());
            editButton.setVisible(true);
            replaceButton.setVisible(false);
            addButton.setVisible(true);
            employee=new Employee(nameText.getText(),designationText.getText(),salaryTExt.getText());
            observableList.set(index,employee);
            nameText.setText("");
            salaryTExt.setText("");
            designationText.setText("");

        }
    }
}
