package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Org {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "org_id")
    private String orgId;

    @NonNull
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

    @ColumnInfo(name = "org_name")
    private String orgName;

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    @ColumnInfo(name = "host_name")
    private String hostName;



}
