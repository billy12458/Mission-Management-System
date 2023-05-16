package com.cabbage.mms.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import lombok.Cleanup;

/** The basic controller for downloading chart files */
@SuppressWarnings("all")
@Service("fileService")
public class FileService {

    @Autowired
    public Environment environment;

    @Autowired
    public GridFsTemplate template;

    @Autowired
    public GridFSBucket bucket;

    // 现在还不知道他那个下载是不是生成csv文件然后下载的，先写着
    public void downLoadFile(String fileName, HttpServletResponse response) throws IOException {
        File file = new File(fileName);
        // forces the browser to directly download the file
        response.setContentType("application/force-download");
        // attach the source file to the header
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        @Cleanup
        BufferedInputStream bufferStream1 = new BufferedInputStream(new FileInputStream(file));
        @Cleanup
        OutputStream responseOut = response.getOutputStream();
        IOUtils.copy(bufferStream1, responseOut);
        response.flushBuffer();
        response.reset();
        bufferStream1.close();
    }

    public void uploadFileToMongo(Integer missionId, MultipartFile[] allFiles)
            throws IOException {
        DBObject missionObject = new BasicDBObject("missionId", missionId);
        for (MultipartFile file : allFiles) {
            template.store(file.getInputStream(), file.getOriginalFilename(),
                    file.getContentType(), missionObject);
        }
    }
}
