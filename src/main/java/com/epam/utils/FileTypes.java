package com.epam.utils;

public enum FileTypes {
    TXT(".txt"),
    HTML(".html"),
    XML(".xml");


    private String fileType;

    FileTypes(String fileType) {
        this.fileType = fileType;
    }

    public String fileTypeAsString() {
        return fileType;
    }
}
