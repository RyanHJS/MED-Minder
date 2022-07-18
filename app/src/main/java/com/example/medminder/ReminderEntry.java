package com.example.medminder;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;

public class ReminderEntry {

    //instance vars taken from course webpage
    private long id;
    private int mReminderType;  // Medication, General, Special Events
    private int mMReminderMedicationType;     // 1,2,3,4,5
    private long mDateTime = Calendar.getInstance().getTimeInMillis();    // When does this entry happen
    private String mMedicationName;         // Medication name
    private String mMedicationType;      // Distance traveled. Either in meters or feet.
    private String mMedicationNotes;       // Comments
    private ArrayList<LatLng> mLatLngs;

    /**
     * Construct a default Exercise Entry object
     */
    public ReminderEntry() {
        mReminderType = 0;
        mMReminderMedicationType = 0;
        mLatLngs = new ArrayList<>();
    }

    //id getter and setter
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }


    //Reminder type getter and setter
    public void setmReminderType(int mReminderType) {
        this.mReminderType = mReminderType;
    }
    public int getmReminderType() {
        return mReminderType;
    }


    //Medication type getter and setter
    public void setmMReminderMedicationType(int mMReminderMedicationType) {
        this.mMReminderMedicationType = mMReminderMedicationType;
    }
    public int getmMReminderMedicationType() {
        return mMReminderMedicationType;
    }


    //dateTime getter and setter
    public void setmDateTime(long mDateTime) {
        this.mDateTime = mDateTime;
    }
    public long getmDateTime() {
        return this.mDateTime;
    }


    //Medication Name getter and setter
    public void setmMedicationName(String mMedicationName) {
        this.mMedicationName = mMedicationName;
    }
    public String getmMedicationName() {
        return mMedicationName;
    }


    //Medication Type getter and setter
    public void setmMedicationType(String mMedicationType) {
        this.mMedicationType = mMedicationType;
    }
    public String getmMedicationType() {
        return mMedicationType;
    }


    //Medication notes getter and setter
    public void setmMedicationNotes(String mMedicationNotes) {
        this.mMedicationNotes = mMedicationNotes;
    }
    public String getmMedicationNotes() {
        return mMedicationNotes;
    }


    //latlngs setter getter and add
    public ArrayList<LatLng> getmLatLngs() { return mLatLngs; }

    public void setmLatLngs(ArrayList<LatLng> mLatLngs) { this.mLatLngs = mLatLngs; }

    public void addLatLng(LatLng latLng) { mLatLngs.add(latLng);}


    //toString
    @Override
    public String toString() {
        return mReminderType + ": " + mMReminderMedicationType + ", " + mDateTime;
    }
}
