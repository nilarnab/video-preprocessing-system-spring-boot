package com.example.vps;

import com.example.vps.utils.Response;
import com.example.vps.utils.VideoProcessor;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public Iterable<Result<Item>> getFilesBatch ()
    {
        /*
        Sends all the videos that are not yet converted to mpd version
        sends videos present in videos/ but not in dash/
         */
        MinioClient minioClient =
                MinioClient.builder()
                        .endpoint("http://167.99.252.122:9000")
                        .credentials("MpYkYbWeLw0wCyXx", "z7kOJAgkKZVfehgPl7IGyYRZEK2PDEbJ")
                        .build();

        System.out.println("Minio client is built");

        Iterable<Result<Item>> results = minioClient.listObjects(
                ListObjectsArgs.builder().bucket("videos").build());


        return results;
    }

    @GetMapping("/")
    public HashMap<String, Object> home() throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String BASE_URL = "http://167.99.252.122:9000/videos/"; // hardcoded
        String BASE_LOCAL_FILE_STORAGE = "/home/nilarnab/Documents/video-preprocessing-system-spring-boot/src/dashVideos/"; // hardcoded
        Iterable<Result<Item>> convertibleVideosList = getFilesBatch();
        for(Result<Item> videoMP4: convertibleVideosList)
        {
            String videoName = videoMP4.get().objectName().replaceAll(" ", "%20");
            String videoUrl = BASE_URL + videoName;
            String videoKey = videoName.split("\\.")[0];
            String mpdVideoLocalBufferPath = BASE_LOCAL_FILE_STORAGE + videoKey;
            File f1 = new File(mpdVideoLocalBufferPath);
            if (f1.mkdir())
            {
                String outputLocation = mpdVideoLocalBufferPath + '/' + videoKey + ".mpd";
                System.out.println(videoUrl);
                System.out.println(outputLocation);

                VideoProcessor videoProcessor = new VideoProcessor(videoUrl, outputLocation);
                boolean resp = videoProcessor.convert();

                if (resp)
                {
                    // ship the files

                    // edit the .mpd file
                    // ship the .mpd file in a folder
                    // put all other files in the same folder
                    // delete the directory
                }
                else
                {
                    System.out.println("Could not convert " + videoKey);
                }
            }
            else
            {
                System.out.println("Could not make a folder for " + videoKey);
            }
        }

        return (new Response(true, "request complete")).getResponse();
    }
    @GetMapping("/convert")
    public HashMap<String, Object> convert() throws IOException {
        return (new Response(true, "Action complete")).getResponse();
    }
}
