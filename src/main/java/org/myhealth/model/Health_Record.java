package org.myhealth.model;

// This class stores one health record for a user
public class Health_Record {

    private int id;
    private int userId;
    private String weight;
    private String temperature;
    private String bloodPressure;
    private String note;
    private String recordDate;

    // Constructor used when creating a new health record
    public Health_Record(int userId, String weight, String temperature,
                        String bloodPressure, String note, String recordDate) {
        this.userId = userId;
        this.weight = weight;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.note = note;
        this.recordDate = recordDate;
    }

    // Constructor used when reading records from database
    public Health_Record(int id, int userId, String weight, String temperature,
                        String bloodPressure, String note, String recordDate) {
        this.id = id;
        this.userId = userId;
        this.weight = weight;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.note = note;
        this.recordDate = recordDate;
    }

    public int getId() {

        return id;
    }

    public int getUserId() {

        return userId;
    }

    public String getWeight() {

        return weight;
    }

    public String getTemperature() {

        return temperature;
    }

    public String getBloodPressure() {

        return bloodPressure;
    }

    public String getNote() {

        return note;
    }

    public String getRecordDate() {

        return recordDate;
    }

    public void setWeight(String weight) {

        this.weight = weight;
    }

    public void setTemperature(String temperature) {

        this.temperature = temperature;
    }

    public void setBloodPressure(String bloodPressure) {

        this.bloodPressure = bloodPressure;
    }

    public void setNote(String note) {

        this.note = note;
    }
}
