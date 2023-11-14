package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Org;

import java.util.List;

public interface orgSelPresInter {
   void getOrganization(Context context);
   void setOrganizations(List<Org> organizations);

   void getHostName(Context context,String orgName);
   void setHostName(List<Org> organizations);

   void getRoles(Context context,String hostName);

   void showMessage(int message);
}
