package Controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.scene.chart.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.sql.SQLException;
import java.time.MonthDay;

public class ReportsFormController {
    public PieChart pieChart;

    public void initialize(){
    }



    public void SalaryReport(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/SalaryReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);

            JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DBConnection.getInstance().getConnection());
            JasperViewer.viewReport(jasperPrint,false);
    }

    public void StockBalance(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/StockB.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void MonthlySaes(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/MonthlySales.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    public void PaymentCollection(ActionEvent actionEvent) throws JRException, SQLException, ClassNotFoundException {
        JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/View/Reports/Payment.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(design);

        JasperPrint jasperPrint= JasperFillManager.fillReport(jasperReport,null,DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
