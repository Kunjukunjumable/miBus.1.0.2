package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Presenter.orgSelPresInter;

public interface orgSelModelInter {
    void getOrganizations(Context context, orgSelPresInter orgSelPresInter);
    void getHostName(Context context,orgSelPresInter orgSelPresInter,String orgName);
    void getRoles(Context context,String hostName,orgSelPresInter orgSelPresInter);


}
