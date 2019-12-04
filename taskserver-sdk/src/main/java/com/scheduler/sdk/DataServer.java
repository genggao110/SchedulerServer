package com.scheduler.sdk;

import java.io.IOException;

public interface DataServer {

    Data upload(String dataPath, String tag)throws Exception;
}
