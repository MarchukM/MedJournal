package com.medjournal.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Note {
    private StringProperty disease;
    private StringProperty recommendation;

    public Note() {
        disease = new SimpleStringProperty();
        recommendation = new SimpleStringProperty();
    }

    public Note(String disease, String recommendation) {
        this.disease = new SimpleStringProperty(disease);
        this.recommendation = new SimpleStringProperty(recommendation);
    }

    public String getDisease() {
        return disease.get();
    }

    public StringProperty diseaseProperty() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease.set(disease);
    }

    public String getRecommendation() {
        return recommendation.get();
    }

    public StringProperty recommendationProperty() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation.set(recommendation);
    }
}
