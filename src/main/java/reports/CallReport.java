package reports;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import entities.Employee;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import utilities.AlerterMessagePopup;

public class CallReport {

    private final AlerterMessagePopup popup = new AlerterMessagePopup();

    public void generateReport(String reportName, Map<String, Object> parameterMap) throws JRException, SQLException {

        String path = new File("Reports/" + reportName + ".jrxml").getAbsolutePath();

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oneplusbier", "root", "");

        JasperPrint print = JasperFillManager.fillReport(jasperReport,
                parameterMap, conn);

        // PDF Exportor.
        JRPdfExporter exporter = new JRPdfExporter();

        ExporterInput exporterInput = new SimpleExporterInput(print);
        // ExporterInput
        exporter.setExporterInput(exporterInput);

        // ExporterOutput
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                "C:\\Users\\d_klein\\" + reportName + ".pdf");
        // Output
        exporter.setExporterOutput(exporterOutput);

        //
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        popup.generateInformationPopupWindow("Ihr Report wurde erstellt.");
    }

}
