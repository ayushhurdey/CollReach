package com.collreach.userprofile.util;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
public class FtpUtil {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.pwd}")
    private String pwd;

    @Value("${ftp.host-dir}")
    private String hostDir;

    @Autowired
    FTPClient ftp;
    // FTPClient ftp = null;

    //InputStream inputStream = null;
    private int downloadTryLimit = 10;
    private int connectionTryLimit = 10;

    public String ftpUpload(MultipartFile file, String fileName) throws Exception{
        System.out.println("Start");
        FTPConnect(host, user, pwd);
            //FTP server path is relative. So if FTP account HOME directory is
            // "/home/pankaj/public_html/" and you need to upload
            // files to "/home/pankaj/public_html/wp-content/uploads/image2/",
            // you should pass directory parameter as "/wp-content/uploads/image2/"
        uploadFile(file, fileName);
        disconnect();
        System.out.println("Done");
        return "Done successfully.";
    }


    public String deletingFile(String fileName) {
        boolean fileDeleted = false;
        try {
            FTPConnect(host,user,pwd);
            fileDeleted = deleteFile(fileName);
            disconnect();
        }catch(Exception e){
            return "Could not connect.";
        }
        if(fileDeleted)
            return "deleted Successfully.";
        else
            return "No such file exists.";
    }

    public void FTPConnect(String host, String user, String pwd) throws Exception{
        //ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        try {
            ftp.connect(host);
            reply = ftp.getReplyCode();
            System.out.println("reply---------------------->   " + reply);
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new Exception("Exception in connecting to FTP Server");
            }
            boolean loginReply = ftp.login(user, pwd);
            if(!loginReply)
                retry();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
        }catch(Exception e){
            System.out.println("Exception: " +e);
            retry();
        }
    }

    private void retry() throws Exception {
        if(connectionTryLimit-- > 0){
            disconnect();
            FTPConnect(host,user,pwd);
        }
    }

    public InputStream downloadFile(String path) throws Exception {
        //inputStream = new ByteArrayInputStream(new byte[0]);
        try{
            FTPConnect(host, user, pwd);
            System.out.println("Before Download");
            return download(path);
        }
        catch(Exception e){
            System.out.println("Could not connect | download file: " + e);
            System.out.println("Retrying.....");
            if(downloadTryLimit-- > 0)
                return downloadFile(path);
            else
                return new ByteArrayInputStream(new byte[0]);
        }
//        finally{
//            disconnect();
//        }
        //return inputStream;
    }

    public InputStream download(String path) throws IOException {
        //inputStream =  ftp.retrieveFileStream(path);
        return ftp.retrieveFileStream(path);
        //System.out.println("Downloaded file for path: " + path);
        //System.out.println(Arrays.toString(inputStream.readAllBytes()));
    }

    public void uploadFile(MultipartFile file, String fileName)
            throws Exception {
        try(InputStream input = file.getInputStream()){
            ftp.storeFile(fileName, input);
        }
    }

    public boolean deleteFile(String fileName) {
        boolean deleted = false;
        try {
            deleted = ftp.deleteFile(hostDir + fileName);
        }catch(Exception e){
            System.out.println("-> :" + e);
        }
        return deleted;
    }

    public void disconnect(){
        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

}

//class Ftp{
//    private static Ftp singletonFTP = null;
//    public FTPClient ftpClient;
//
//    private Ftp(){
//        ftpClient = new FTPClient();
//    }
//    public static Ftp getInstance() {
//        if (singletonFTP == null) {
//            singletonFTP = new Ftp();
//        }
//        return singletonFTP;
//    }
//}
