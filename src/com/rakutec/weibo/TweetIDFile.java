package com.rakutec.weibo;

import java.io.*;
import java.util.logging.Logger;

public class TweetIDFile {
    private static final Logger log = Logger.getLogger(TweetIDFile.class.getName());

    public String userId;

    public Long latestId;

    public TweetIDFile(String userId) {
        this.userId = userId + ".id";
    }

    public void update(Long tweetId) {
        log.info("updating latest id to " + tweetId);
        this.latestId = tweetId;
        try {
            FileWriter fw = new FileWriter(this.userId, false);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(String.valueOf(this.latestId));
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static TweetIDFile loadTweetID(String user) {
        TweetIDFile tid = new TweetIDFile(user);
        try {
            log.info("FOUND TID:" + tid.latestId);
            BufferedReader in = new BufferedReader(new FileReader(tid.userId));

            String line = in.readLine();
            tid.latestId = Long.valueOf(line);

            in.close();
        } catch (IOException e) {
            tid.latestId = (long) 0;
            log.info("TID NOT FOUND");
        }
        return tid;
    }
}