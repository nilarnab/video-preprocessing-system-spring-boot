package com.example.vps;

import com.example.vps.utils.Response;
import com.example.vps.utils.VideoProcessor;
import io.minio.*;
import io.minio.messages.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import io.minio.errors.MinioException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    public ArrayList<String> getFilesBatch ()
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

        System.out.println("bucket objects " + results);


        return new ArrayList<>();
    }

    @GetMapping("/")
    public HashMap<String, Object> home() throws IOException {
        getFilesBatch();
//        String input = "http://167.99.252.122:9000/videos/086363c1656dcc5391e81856df039a04.mp4";
//        String outputLocation = "/home/nilarnab/Documents/video-preprocessing-system-spring-boot/src/dashVideos/" + "output.mpd";
//        VideoProcessor videoProcessor = new VideoProcessor(input, outputLocation);
//        boolean resp = videoProcessor.convert();
        return (new Response(true, "request complete")).getResponse();
    }
    @GetMapping("/convert")
    public HashMap<String, Object> convert() throws IOException {
        return (new Response(true, "Action complete")).getResponse();
    }
}
