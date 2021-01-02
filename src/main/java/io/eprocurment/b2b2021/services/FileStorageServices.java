package io.eprocurment.b2b2021.services;


import io.eprocurment.b2b2021.models.files.FileStorage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageServices {
    FileStorage getFileById(String id) throws IOException;

    Boolean handleCSV(String id) throws IOException;

    FileStorage addFileToStorage(String title, MultipartFile file) throws IOException;
}
