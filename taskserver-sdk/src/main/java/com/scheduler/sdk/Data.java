package com.scheduler.sdk;

import java.io.IOException;

public interface Data {

    String getURL();

    int download(String filePath) throws IOException;
}
