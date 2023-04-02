package com.test_selenium.demo.service.impl;

import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.test_selenium.demo.config.GoogleDriveManager;
import com.test_selenium.demo.contains.Role;
import com.test_selenium.demo.service.GoogleDriveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class GoogleDriveServiceImpl implements GoogleDriveService {
    private final GoogleDriveManager googleDriveManager;

    @Override
    public List<File> listEverything() throws GeneralSecurityException, IOException {
        // Print the names and IDs for up to 10 files.
        FileList result = googleDriveManager.getInstance().files().list()
//                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, parents)").execute();
        return result.getFiles();
    }

    @Override
    public List<File> listFolderContent(String parentId) throws GeneralSecurityException, IOException {
        if (Objects.isNull(parentId)) {
            parentId = "root";
        }

        String query = String.format("'%s' in parents", parentId);
        FileList result = googleDriveManager.getInstance().files().list().setQ(query)
                .setFields("nextPageToken, files(id, name, parents)").execute();
        return result.getFiles();
    }

    @Override
    public List<File> findFolderByName(String folderName) throws GeneralSecurityException, IOException {
        String query = String.format("mimeType='application/vnd.google-apps.folder' and trashed=false and name contains '%s'", folderName);
        FileList result = googleDriveManager.getInstance().files().list().setQ(query).setFields("nextPageToken, files(id, name, parents)").execute();
        return result.getFiles();
    }

    @Override
    public List<File> findFileByName(String fileName, String folderName) throws GeneralSecurityException, IOException {
        String query = String.format("(mimeType != 'application/vnd.google-apps.folder' and trashed=false and name contains '%s')", fileName);
        if (Objects.nonNull(folderName)) {
            query = query.concat(String.format(" and (mimeType='application/vnd.google-apps.folder' and trashed=false and name contains '%s')", folderName));
        }
        FileList result = googleDriveManager.getInstance().files().list().setQ(query).setFields("nextPageToken, files(id, name, parents)").execute();
        return result.getFiles();
    }

    @Override
    public List<File> findByName(String name) throws GeneralSecurityException, IOException {
        String query = String.format("trashed=false and name contains '%s'", name);
        FileList result = googleDriveManager.getInstance().files().list().setQ(query).setFields("nextPageToken, files(id, name, parents)").execute();
        return result.getFiles();
    }

    @Override
    public List<String> shareFile(String fileId, String gmail, Role role) throws GeneralSecurityException, IOException {
        List<String> ids = new ArrayList<>();
        JsonBatchCallback<Permission> callback = new JsonBatchCallback<>() {
            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) {
                // Handle error
                log.error("Error: {}", e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission, HttpHeaders responseHeaders) {
                log.info("Permission ID: {}", permission.getId());
                ids.add(permission.getId());
            }
        };
        BatchRequest batch = googleDriveManager.getInstance().batch();
        Permission userPermission = new Permission()
                .setType("user")
                .setRole(role.getValue())
                .setEmailAddress(gmail);
        try {
            googleDriveManager.getInstance().permissions().create(fileId, userPermission)
                    .setFields("id")
                    .queue(batch, callback);
            batch.execute();

            return ids;
        } catch (GoogleJsonResponseException e) {
            log.error("Error: {}", e.getDetails());
            throw e;
        }
    }

    @Override
    public String uploadFile(java.io.File file, String folderName) throws GeneralSecurityException, IOException {
        if (!file.exists()) {
            return null;
        }

        File fileUpload = new File();
        fileUpload.setName(file.getName());

        if (Objects.nonNull(folderName)) {
            List<String> folderIds = findFolderByName(folderName).stream().map(File::getId).toList();
//            if folderIds is null upload file to root
            if (folderIds.isEmpty()) {
                folderIds = Collections.singletonList(createFolder(folderName).getId());
            }
            fileUpload.setParents(folderIds);
        }

        Path path = file.toPath();
        String mimeType = Files.probeContentType(path);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(Files.readAllBytes(path));
        InputStreamContent inputStreamContent = new InputStreamContent(mimeType, arrayInputStream);

        return googleDriveManager.getInstance().files().create(fileUpload, inputStreamContent)
                .setFields("id")
                .execute().getId();
    }

    @Override
    public File createFolder(String folderName) throws GeneralSecurityException, IOException {
        File fileMetadata = new File();
        fileMetadata.setName(folderName);
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        return googleDriveManager.getInstance().files().create(fileMetadata)
                .setFields("id")
                .execute();
    }

    @Override
    public void deleteFile(String fileId) throws GeneralSecurityException, IOException {
        googleDriveManager.getInstance().files().delete(fileId).execute();
    }
}
