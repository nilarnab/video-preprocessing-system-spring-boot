package com.example.vps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;
import com.example.vps.Response;

@RestController
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/")
    public HashMap<String, Object> home() throws IOException {

        // video generation
        // Input and output file paths
        logger.info("Starting ..");
        String inputFilePath = "./sample.mp4";
        String outputFilePath = "./sampleout.mpd";

        logger.info("object being made");
        FFmpeg ffmpeg = new FFmpeg();

        logger.info("Obj redy");

        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputFilePath)
                .addOutput(outputFilePath)
                .setFormat("dash")
                .done();

        logger.info("Building ready");

        try {
            ffmpeg.run(builder);
            logger.info("Video conversion completed successfully.");
        } catch (IOException e) {
            logger.info("Error converting video: " + e.getMessage());
        }

        // generating response
        HashMap<String, Object> response = new HashMap<>();
        response.put("verdict", 1);
        response.put("message", "VPS is running");
        return response;
    }
}
