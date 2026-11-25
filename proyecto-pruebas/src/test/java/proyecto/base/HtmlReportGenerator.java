package proyecto.base;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;

public class HtmlReportGenerator {

    public static void main(String[] args) throws Exception {
        File reportsDir = new File("target/surefire-reports");
        if (!reportsDir.exists()) {
            reportsDir = new File("proyecto-pruebas/target/surefire-reports");
        }
        File outHtml = new File(reportsDir.getParentFile(), "surefire-report.html");
        outHtml.getParentFile().mkdirs();

        StringBuilder rows = new StringBuilder();
        int total = 0, passed = 0, failed = 0, errors = 0, skipped = 0;

        File[] xmlFiles = reportsDir.listFiles((dir, name) -> name.startsWith("TEST-") && name.endsWith(".xml"));
        if (xmlFiles != null) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            for (File xml : xmlFiles) {
                Document doc = factory.newDocumentBuilder().parse(xml);
                NodeList cases = doc.getElementsByTagName("testcase");
                for (int i = 0; i < cases.getLength(); i++) {
                    Element tc = (Element) cases.item(i);
                    String className = tc.getAttribute("classname");
                    String name = tc.getAttribute("name");
                    String time = tc.getAttribute("time");

                    String status = "PASSED";
                    String detail = "";
                    if (tc.getElementsByTagName("failure").getLength() > 0) {
                        status = "FAILED";
                        failed++;
                        detail = tc.getElementsByTagName("failure").item(0).getTextContent();
                    } else if (tc.getElementsByTagName("error").getLength() > 0) {
                        status = "ERROR";
                        errors++;
                        detail = tc.getElementsByTagName("error").item(0).getTextContent();
                    } else if (tc.getElementsByTagName("skipped").getLength() > 0) {
                        status = "SKIPPED";
                        skipped++;
                    } else {
                        passed++;
                    }
                    total++;

                    rows.append("<tr>")
                            .append("<td>").append(escape(className)).append("</td>")
                            .append("<td>").append(escape(name)).append("</td>")
                            .append("<td>").append(escape(time)).append("s</td>")
                            .append("<td><b>").append(status).append("</b></td>")
                            .append("<td>").append(escape(detail.trim())).append("</td>")
                            .append("</tr>\n");
                }
            }
        }

        String html = "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Surefire HTML Report</title>"
                + "<style>body{font-family:Arial,sans-serif;margin:24px;}table{border-collapse:collapse;width:100%;}"
                + "th,td{border:1px solid #ccc;padding:8px;text-align:left;}th{background:#f2f2f2;}</style></head><body>"
                + "<h1>Reporte de Ejecución de Pruebas - Swag Labs</h1>"
                + "<p>Total: <b>" + total + "</b> | Pasaron: <b>" + passed + "</b> | Fallaron: <b>" + failed
                + "</b> | Errores: <b>" + errors + "</b> | Omitidos: <b>" + skipped + "</b></p>"
                + "<table><thead><tr><th>Clase</th><th>Prueba</th><th>Tiempo</th><th>Estado</th><th>Detalle</th></tr></thead><tbody>"
                + rows + "</tbody></table></body></html>";

        try (FileWriter fw = new FileWriter(outHtml, StandardCharsets.UTF_8)) {
            fw.write(html);
        }
        System.out.println("Reporte HTML generado en: " + outHtml.getAbsolutePath());
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }
}