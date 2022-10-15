package Controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AdministratorDashboardFormController {
    public Label lblTime;
    public Label lblDate;
    public Pane AdministratorPane_Context;

    public void initialize(){
        loadDateAndTime();
    }

    public void loadDateAndTime(){
        Date date=new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));


        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    public void back_to_WelcomeForm(MouseEvent mouseEvent) throws IOException {
        Stage window=(Stage)AdministratorPane_Context.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/WelcomeForm.fxml"))));

    }

    public void open_EmployeeForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/EmployeeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorPane_Context.getChildren().clear();
        AdministratorPane_Context.getChildren().add(load);
    }

    public void open_PerfumeForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/PerfumeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorPane_Context.getChildren().clear();
        AdministratorPane_Context.getChildren().add(load);
    }

    public void open_SalaryForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/SalaryForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorPane_Context.getChildren().clear();
        AdministratorPane_Context.getChildren().add(load);
    }

    public void open_VehicleForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/VehicleForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorPane_Context.getChildren().clear();
        AdministratorPane_Context.getChildren().add(load);
    }

    public void open_SupplierForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/SupplierForm.fxml");
        Parent load = FXMLLoader.load(resource);
        AdministratorPane_Context.getChildren().clear();
        AdministratorPane_Context.getChildren().add(load);
    }


}
