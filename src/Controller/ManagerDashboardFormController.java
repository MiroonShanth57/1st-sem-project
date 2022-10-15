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

public class ManagerDashboardFormController {
    public Pane managerPane_Context;
    public Label lblTime;
    public Label lblDate;


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

    public void back_to_welcomeForm(MouseEvent mouseEvent) throws IOException {
        Stage window=(Stage)managerPane_Context.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../View/WelcomeForm.fxml"))));
    }

    public void open_InvoiceForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/InvoiceForm.fxml");
        Parent load = FXMLLoader.load(resource);
        managerPane_Context.getChildren().clear();
        managerPane_Context.getChildren().add(load);
    }

    public void open_DeliveryForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/DeliveryForm.fxml");
        Parent load = FXMLLoader.load(resource);
        managerPane_Context.getChildren().clear();
        managerPane_Context.getChildren().add(load);
    }

    public void open_PaymentForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/PaymentForm.fxml");
        Parent load = FXMLLoader.load(resource);
        managerPane_Context.getChildren().clear();
        managerPane_Context.getChildren().add(load);
    }

    public void open_ReportsForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/ReportsForm.fxml");
        Parent load = FXMLLoader.load(resource);
        managerPane_Context.getChildren().clear();
        managerPane_Context.getChildren().add(load);
    }

    public void open_ChequeForm(MouseEvent mouseEvent) throws IOException {
        URL resource = getClass().getResource("../View/ChequeForm.fxml");
        Parent load = FXMLLoader.load(resource);
        managerPane_Context.getChildren().clear();
        managerPane_Context.getChildren().add(load);
    }

}
