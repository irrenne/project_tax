package com.epam.tax.test.xml;

import com.epam.tax.xml.ReportXml;
import com.epam.tax.xml.SAXController;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestXML {
    @Test
    public void parseSAXTest() throws Exception {
        String xmlFileName = "input.xml";
        ReportXml reportCheck = templateReportXml();

        SAXController saxController = new SAXController(xmlFileName);
        ReportXml reportFromFile = saxController.parseSAX();

        assertEquals(reportCheck, reportFromFile);
    }

    private ReportXml templateReportXml() {
        ReportXml reportXml = new ReportXml();
        reportXml.setSalary(100);
        reportXml.setCompany("Company name");
        return reportXml;
    }
}
