package com.epam.services;

import com.epam.utils.FileTypes;
import java.io.File;

public class FileHandlerService extends ParentService {

    public File getNewFile(FileTypes typeOfFileEnum) {
        return awsHandler.createRandomFile(typeOfFileEnum);
    }
}
