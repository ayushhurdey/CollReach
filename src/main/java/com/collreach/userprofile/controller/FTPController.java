package com.collreach.userprofile.controller;

import java.io.*;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Deprecated
@Controller
@RequestMapping(path = "/ftp")
@CrossOrigin("*")
public class FTPController {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.user}")
    private String user;

    @Value("${ftp.pwd}")
    private String pwd;

    FTPClient ftp = null;

    @PostMapping(path = "/upload")
    public ResponseEntity<String> ftpConnect(@RequestParam("file") MultipartFile file) throws Exception{
        System.out.println("Start");
        var some = file.getInputStream();
        FTPUploader(host, user, pwd);
        //FTP server path is relative. So if FTP account HOME directory is
        // "/home/pankaj/public_html/" and you need to upload
        // files to "/home/pankaj/public_html/wp-content/uploads/image2/",
        // you should pass directory parameter as "/wp-content/uploads/image2/"
        uploadFile(file, file.getOriginalFilename(), "/htdocs/frontend/");
        disconnect();
        System.out.println("Done");
        return ResponseEntity.ok().body("Done successfully.");
    }

    @GetMapping("/delete")
    public ResponseEntity<String> deletingFile() {
        boolean fileDeleted = false;
        try {
            FTPUploader(host,user,pwd);
            fileDeleted = deleteFile();
            disconnect();
        }catch(Exception e){
            return ResponseEntity.ok().body("File not found");
        }
        if(fileDeleted)
            return ResponseEntity.ok().body("deleted Successfully.");
        else
            return ResponseEntity.ok().body("No such file exists.");
    }

    public void FTPUploader(String host, String user, String pwd) throws Exception{
        ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        int reply;
        ftp.connect(host);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new Exception("Exception in connecting to FTP Server");
        }
        ftp.login(user, pwd);
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
    }

    public void uploadFile(MultipartFile file, String fileName, String hostDir)
            throws Exception {
        try(InputStream input = file.getInputStream()){
            this.ftp.storeFile(hostDir + fileName, input);
        }
    }

    public boolean deleteFile() {
        boolean deleted = false;
        try {
            deleted = this.ftp.deleteFile("/htdocs/frontend/TESTIMAGE.png");
        }catch(Exception e){
            System.out.println("-> :" + e);
        }
        return deleted;
    }

    public void disconnect(){
        if (this.ftp.isConnected()) {
            try {
                this.ftp.logout();
                this.ftp.disconnect();
            } catch (IOException f) {
                // do nothing as file is already saved to server
            }
        }
    }

}
