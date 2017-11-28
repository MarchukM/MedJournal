package com.medjournal.threads;

import com.medjournal.MainApp;
import com.medjournal.util.XMLInputOutputUtil;

import java.io.File;

public class XMLInputThread implements Runnable{

    private File file;

    public XMLInputThread(File file) {
        this.file = file;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        synchronized (MainApp.class){
            XMLInputOutputUtil.importDataFromXML(file);
            System.out.println("data was imported from xml");
        }
    }
}
