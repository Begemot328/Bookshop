package by.epam.bookshop.util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class GoogleDriveUtil {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final String PATH = "https://drive.google.com/uc?export=view&id=";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveUtil.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    public static java.io.File downloadFile(String url) throws IOException {
        String filePathString = url;
        String fileName;
        if (filePathString.contains("/")) {
            fileName = filePathString.substring(filePathString.lastIndexOf("/") + 1, filePathString.length() - 1);
        } else {
            fileName = filePathString;
        }
        java.io.File localFile = new java.io.File("temp/" + fileName);
        org.apache.commons.io.FileUtils.copyURLToFile(new URL(filePathString), localFile);
        return localFile;
    }

    public static String uploadFile(java.io.File localFile, String pathId) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        File fileMetadata = new File();
        //fileMetadata.setParents(Collections.singletonList("1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs"));
        fileMetadata.setParents(Collections.singletonList(pathId));
        String fileName = localFile.getName();

        fileMetadata.setName(fileName);
        FileContent mediaContent = new FileContent("image/jpeg", localFile);
        File file = service.files().create(fileMetadata, mediaContent)
                .setFields("id")
                .execute();
        System.out.println("File ID: " + file.getId());
        String photoLink = PATH.concat(file.getId());

        return photoLink;
    }

    public static String transferFile(String url, String pathId) throws GeneralSecurityException, IOException {
        java.io.File localFile = downloadFile(url);

        return transferFile(localFile, pathId);
    }



    public static String transferFile( java.io.File localFile, String pathId) throws GeneralSecurityException, IOException {

        String photoLink = uploadFile(localFile, pathId);

        localFile.delete();
        return photoLink;
    }

    public static String transferFile(java.io.File localFile, String pathId,
                                      Function<java.io.File, java.io.File> function)
            throws GeneralSecurityException, IOException {

        localFile = function.apply(localFile);
        String photoLink = uploadFile(localFile, pathId);

        localFile.delete();
        return photoLink;
    }

    public static String transferFile(String url, String pathId,
                                      Function<java.io.File, java.io.File> function)
            throws GeneralSecurityException, IOException {
        java.io.File localFile = downloadFile(url);

        return transferFile(localFile, pathId, function);
    }

    public static void main(String... args) throws IOException, GeneralSecurityException {

        System.out.println(transferFile("https://cs12.pikabu.ru/post_img/big/2021/01/08/11/1610133618136126776.jpg",
                "1ACaioGkteNxu6IHd9J-ReQxJRdqADcXs"));
    }

}
