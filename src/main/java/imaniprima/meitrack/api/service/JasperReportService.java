package imaniprima.meitrack.api.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.web.util.WebHtmlResourceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class JasperReportService {
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ResourceLoader resourceLoader;

    public static JasperDesign jasperDesign;
    public static JasperPrint jasperPrint;
    public static JasperReport jasperReport;

    public void exportToPDF(HttpServletResponse response, Map<String, Object> parameters) throws SQLException, JRException, IOException {
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename=history_maintenance.pdf"));
        String path = resourceLoader.getResource("classpath:/jasper/history_maintenance.jrxml").getURI().getPath();
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        JasperReport jasperReport = JasperCompileManager.compileReport(path);

        // Parameters for report
        Map<String, Object> params = new HashMap<String, Object>();
        /*params.put("title", "Employee Report");
        params.put("minSalary", 15000.0);
        params.put("condition", " LAST_NAME ='Smith' ORDER BY FIRST_NAME");*/
        params.putAll(parameters);

        JasperPrint print = JasperFillManager.fillReport(jasperReport, params, conn);
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, out);
    }

    public void exportToExcell (HttpServletResponse response, String jasperFile, String excellFile, Map<String,Object> parameters) throws JRException, IOException, SQLException {
        response.setContentType("application/x-xls");
        response.setHeader("Content-Disposition", String.format("attachment; filename="+excellFile+".xls"));
        InputStream jasperStream = this.getClass().getResourceAsStream("/jasper/"+jasperFile+".jrxml");
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        JasperDesign design = JRXmlLoader.load(jasperStream);
        JasperReport report = JasperCompileManager.compileReport(design);

        // Parameters for report
        Map<String, Object> params = new HashMap<String, Object>();
        params.putAll(parameters);

        JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, conn);

        final OutputStream outputStream = response.getOutputStream();
        JRXlsExporter exportXLS = new JRXlsExporter();
        exportXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        exportXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outputStream);

        exportXLS.exportReport();
    }

    public void exportToHTML(HttpServletResponse response, Map<String,Object> parameters) throws IOException, JRException, SQLException {

        Connection conn = jdbcTemplate.getDataSource().getConnection();
        InputStream jasperStream = this.getClass().getResourceAsStream("/jasper/history_maintenance.jrxml");

        Map<String,Object> params = new HashMap();
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);

        HtmlExporter exporter = new HtmlExporter();
        List<JasperPrint> jasperPrintsList = new ArrayList<JasperPrint>();
        jasperPrintsList.add(jasperPrint);
        exporter.setExporterInput(SimpleExporterInput.getInstance(jasperPrintsList));
        //set ImageHandler. Hack for images export to HTML
        SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(response.getWriter());
        WebHtmlResourceHandler webHtmlResourceHandler =  new WebHtmlResourceHandler("image?image={0}");
        output.setImageHandler(webHtmlResourceHandler);
        exporter.setExporterOutput(output);
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();

    }

}
