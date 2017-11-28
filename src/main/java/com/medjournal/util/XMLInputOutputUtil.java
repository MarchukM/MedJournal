package com.medjournal.util;

import com.medjournal.MainApp;
import com.medjournal.stub.DataSource;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

public class XMLInputOutputUtil {

    private static DataSource dataSource;

    static {
        dataSource = DataSource.getInstance();
    }

    public static void exportDataToXML(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(DataWrapper.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            DataWrapper dataWrapper = new DataWrapper(dataSource.getPatients(), dataSource.getDoctors());
            marshaller.marshal(dataWrapper, file);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void importDataFromXML(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(DataWrapper.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            DataWrapper data = (DataWrapper) unmarshaller.unmarshal(file);

            dataSource.clearData();
            dataSource.setData(data);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
