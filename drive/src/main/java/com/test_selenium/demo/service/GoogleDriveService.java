package com.test_selenium.demo.service;

import com.google.api.services.drive.model.File;
import com.test_selenium.demo.contains.Role;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface GoogleDriveService {
    //    https://developers.google.com/drive/api/guides/manage-sharing?hl=vi
    List<File> listEverything() throws GeneralSecurityException, IOException;

    List<File> listFolderContent(String parentId) throws GeneralSecurityException, IOException;

    List<File> findFolderByName(String folderName) throws GeneralSecurityException, IOException;

    List<File> findFileByName(String fileName, String folderName) throws GeneralSecurityException, IOException;

    List<File> findByName(String name) throws GeneralSecurityException, IOException;

    List<String> shareFile(String fileId, String gmail, Role role) throws GeneralSecurityException, IOException;

    String uploadFile(java.io.File file, String folderName) throws GeneralSecurityException, IOException;

    File createFolder(String folderName) throws GeneralSecurityException, IOException;

    void deleteFile(String fileId) throws GeneralSecurityException, IOException;
}
