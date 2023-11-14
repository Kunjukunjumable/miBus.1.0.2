package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Inspiron on 07-02-2018.
 */
@Entity
public class Token_det {

    @PrimaryKey()
    @ColumnInfo(name = "token_id")
    private int tokenId;


    @ColumnInfo(name = "token")
    private String token;

    @ColumnInfo(name = "token_inc")
    private int tokenInc;


    public int getTokenId() {
        return tokenId;
    }

    public void setTokenId(int tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getTokenInc() {
        return tokenInc;
    }

    public void setTokenInc(int tokenInc) {
        this.tokenInc = tokenInc;
    }


}
