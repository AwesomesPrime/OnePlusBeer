package reports;

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

public class callReport {

    public static void main() throws JRException, SQLException {

        String reportName = "Lohnkostenreport";

        String reportSrcFile = "C:/Users/Nils/Documents/GitHub/OnePlusBeer/Reports/" + reportName + ".jrxml";

        // First, compile jrxml file.
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/oneplusbier", "root", "root");

        // Parameters for report
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("prmEmployeeId",1);

        JasperPrint print = JasperFillManager.fillReport(jasperReport,
                parameters, conn);

        // Make sure the output directory exists.
        File outDir = new File("C:/Users/Nils/Documents/GitHub/OnePlusBeer/Reports");
        outDir.mkdirs();

        // PDF Exportor.
        JRPdfExporter exporter = new JRPdfExporter();

        ExporterInput exporterInput = new SimpleExporterInput(print);
        // ExporterInput
        exporter.setExporterInput(exporterInput);

        // ExporterOutput
        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                "C:/Users/Nils/Documents/GitHub/OnePlusBeer/Reports/" + reportName + ".pdf");
        // Output
        exporter.setExporterOutput(exporterOutput);

        //
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

        System.out.print("Done!");
    }

}
