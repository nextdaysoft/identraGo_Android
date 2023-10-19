package com.project.identranaccess.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class QRClass implements Parcelable {

    private Integer id;
    private String code;

    protected QRClass(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        code = in.readString();
    }

    public static final Creator<QRClass> CREATOR = new Creator<QRClass>() {
        @Override
        public QRClass createFromParcel(Parcel in) {
            return new QRClass(in);
        }

        @Override
        public QRClass[] newArray(int size) {
            return new QRClass[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(code);
    }
}
