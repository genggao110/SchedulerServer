package com.scheduler.sdk;

import com.scheduler.utils.MyHttpUtils;

import java.io.File;
import java.io.IOException;

/**
 * @Author: wangming
 * @Date: 2019-11-28 10:55
 */
public class DCData extends Service implements Data {

    private String id;

    public DCData(String ip, int port, String id) {
        super(ip, port);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getURL() {
        String url = this.getBaseUrl() + "dataResource/getResource?sourceStoreId=" + this.getId();
        return url;
    }

    @Override
    public int download(String filePath) throws IOException {
        String url = this.getURL();
        File file = MyHttpUtils.downloadFile(url, filePath);
        if (file.exists()){
            return 1;
        }else {
            return 0;
        }
    }
}
