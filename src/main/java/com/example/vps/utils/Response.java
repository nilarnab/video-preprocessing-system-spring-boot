package com.example.vps.utils;

import java.util.HashMap;

public class Response {
    private boolean verdict;
    private String message;
    public Response(boolean verdict, String message)
    {
        setVerdict(verdict);
        setMessage(message);
    }

    public HashMap<String, Object> getResponse()
    {
        HashMap<String, Object> resp = new HashMap<>();
        resp.put("verdict", verdict);
        resp.put("message", message);
        return resp;
    }
    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
