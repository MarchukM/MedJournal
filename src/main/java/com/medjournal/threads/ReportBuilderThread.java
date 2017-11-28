package com.medjournal.threads;

import com.medjournal.MainApp;
import com.medjournal.util.ReportBuilder;

import java.io.File;

public class ReportBuilderThread implements Runnable {
    private File file;

    public ReportBuilderThread(File file) {
        this.file = file;
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        synchronized (MainApp.class) {
            ReportBuilder.buildReports(file);
            System.out.println("Reports was built successfully");
        }
    }
}
