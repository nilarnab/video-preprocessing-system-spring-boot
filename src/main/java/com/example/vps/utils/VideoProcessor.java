package com.example.vps.utils;

import com.example.vps.Controller;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class VideoProcessor {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private String inputLocation;
    private String outputLocation;

    public VideoProcessor(String inputLocation, String outputLocation){
        setInputLocation(inputLocation);
        setOutputLocation(outputLocation);
    }

    public boolean convert() throws IOException {
        FFmpeg ffmpeg = new FFmpeg("/usr/bin/ffmpeg");
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(inputLocation)
                .addOutput(outputLocation)
                .setFormat("dash")
                .done();
        try {
            ffmpeg.run(builder);
            logger.info("Video conversion completed successfully.");
            return true;
        } catch (IOException e) {
            logger.info("Error converting video: " + e.getMessage());
            return false;
        }
    }

    public void setInputLocation(String inputLocation) {
        this.inputLocation = inputLocation;
    }

    public void setOutputLocation(String outputLocation)
    {
        this.outputLocation = outputLocation;
    }
}