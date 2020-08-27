package com.example.note;

import io.realm.RealmObject;

public class DiaryMetadata extends RealmObject {
    private String fileName;

    public DiaryMetadata(){}
    public DiaryMetadata(String fileName){
        this.fileName = fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
