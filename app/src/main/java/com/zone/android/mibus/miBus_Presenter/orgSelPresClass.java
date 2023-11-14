package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Model.orgSelModelClass;
import com.zone.android.mibus.miBus_Model.orgSelModelInter;
import com.zone.android.mibus.miBus_View.orgSelectionViewInter;

import java.util.List;

public class orgSelPresClass implements orgSelPresInter {

    orgSelectionViewInter orgSelectionViewInter;
    orgSelModelInter orgSelModelInter;

    public orgSelPresClass( orgSelectionViewInter orgSelectionViewInter){

        this.orgSelectionViewInter=orgSelectionViewInter;
        this.orgSelModelInter=new orgSelModelClass();
    }

    @Override
    public void getOrganization(Context context) {
        orgSelModelInter.getOrganizations(context,this);

    }

    @Override
    public void setOrganizations(List<Org> organizations) {
        orgSelectionViewInter.setOrganizations(organizations);

    }

    @Override
    public void getHostName(Context context, String orgName) {
        orgSelModelInter.getHostName(context,this,orgName);
    }

    @Override
    public void setHostName(List<Org> organizations) {
        orgSelectionViewInter.setHostName(organizations);

    }

    @Override
    public void getRoles(Context context, String hostName) {
orgSelModelInter.getRoles(context,hostName,this);
    }

    @Override
    public void showMessage(int message) {

        orgSelectionViewInter.showMessage(message);
    }
}
