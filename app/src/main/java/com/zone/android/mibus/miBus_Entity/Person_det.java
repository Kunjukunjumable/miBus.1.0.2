package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Inspiron on 01-03-2018.
 */
@Entity
public class Person_det {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "student_id")
    private String studentId;

    @ColumnInfo(name = "first_name")
    private String firstName;

    public void setIdentitiy(String identitiy) {
        this.identitiy = identitiy;
    }

    @ColumnInfo(name = "identitiy")
    private String identitiy;


    @ColumnInfo(name = "last_name")
    private String lastName;



    public String getIdentitiy() {
        return identitiy;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
