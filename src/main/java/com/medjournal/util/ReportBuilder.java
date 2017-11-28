package com.medjournal.util;

import com.medjournal.MainApp;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import java.io.File;

public class ReportBuilder {
    public static void buildReports(File file) {
        try {
            String htmlTemplate = MainApp.class.getResource("/report/template/htmlTemplate.jasper").getPath();
            String pdfTemplate = MainApp.class.getResource("/report/template/pdfTemplate.jasper").getPath();
            String recordPath = "/root/doctors/doctor";

            String xmlFileName = file != null ? file.getPath() : null;

            if (xmlFileName == null) {
                return;
            }

            JRDataSource dataSourcePDF = new JRXmlDataSource(xmlFileName, recordPath);
            JRDataSource dataSourceHTML = new JRXmlDataSource(xmlFileName, recordPath);

            JasperPrint pdfPrint = JasperFillManager.fillReport(pdfTemplate, null, dataSourcePDF);
            JasperPrint htmlPrint = JasperFillManager.fillReport(htmlTemplate, null, dataSourceHTML);

            buildHTMLReport(htmlPrint);
            buildPDFReport(pdfPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    private static void buildHTMLReport(JasperPrint print) throws JRException {
        String outFileName = "C:\\Users\\mi\\Documents\\report.html";
        JRExporter exporter = new JRHtmlExporter();

        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.exportReport();
    }

    private static void buildPDFReport(JasperPrint print) throws JRException {
        String outFileName = "C:\\Users\\mi\\Documents\\report.pdf";
        JRExporter exporter = new JRPdfExporter();

        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.exportReport();
    }
}
