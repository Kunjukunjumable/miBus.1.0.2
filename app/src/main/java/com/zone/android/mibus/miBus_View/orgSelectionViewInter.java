package com.zone.android.mibus.miBus_View;

import com.zone.android.mibus.miBus_Entity.Org;

import java.util.List;

public interface orgSelectionViewInter {
    void setOrganizations(List<Org> organizations);
    void setHostName(List<Org> organizations);
    void showMessage(int message);


}
