package http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.entity.BufferedHttpEntity;

import com.mobile17173.game.adapt.AppListAdapter;
import com.mobile17173.game.util.TestUtils;

public class DownloadHttpResponseHandler extends AsyncHttpResponseHandler {
    private File mDestFile;
    private boolean flag = true;
    
    public DownloadHttpResponseHandler(File file) {
        super();
        mDestFile = file;
    }
    
    public void stopDownload(boolean state) {
    	flag = state;
    }

    @Override
    void sendResponseMessage(HttpResponse response) {
        StatusLine status = response.getStatusLine();
        Header[] contentTypeHeaders = response.getHeaders("Content-Type");
        byte[] responseBody = null;
        if(contentTypeHeaders.length != 1) {
            sendFailureMessage(new HttpResponseException(status.getStatusCode(), "None, or more than one, Content-Type Header found!"), responseBody);
            return;
        }
        
        
        BufferedOutputStream out = null;
        try {
//            HttpEntity entity = null;
            HttpEntity entity = response.getEntity();
//            if(temp != null) {
//                entity = new BufferedHttpEntity(temp);
//            }
            if (entity == null) {
            	sendFailureMessage(new Throwable("没有获取到数据流"), (byte[]) null);
				return ;
			} 
            if (!mDestFile.exists()) {
                mDestFile.createNewFile();
            } else {
                mDestFile.delete();
                mDestFile.createNewFile();
            }
            InputStream input = new BufferedInputStream(entity.getContent());
            byte[] buffer = new byte[8192];
            out = new BufferedOutputStream(new FileOutputStream(mDestFile));
            int length = 0;
            while((length = input.read(buffer)) != -1) {
            	TestUtils.logI("flag " + flag);
            	if (flag) {
            		out.write(buffer,0,length);
                    out.flush();
                    TestUtils.logI("-----------"+mDestFile.length()+"--------------");
                    System.out.println("-----------"+mDestFile.length()+"--------------");
                    onProgressUpdate(entity.getContentLength(), mDestFile.length());
				} else {
					sendFailureMessage(new Throwable("终止下载"), "用户终止下载");
					TestUtils.logI("终止下载");
					break;
				}
                
            }
            } catch (IOException e) {
                e.printStackTrace();
                sendFailureMessage(e, (byte[]) null);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } 

        if(status.getStatusCode() >= 300) {
            sendFailureMessage(new HttpResponseException(status.getStatusCode(), status.getReasonPhrase()), responseBody);
        } else if (flag){
            sendSuccessMessage(status.getStatusCode(), response.getAllHeaders(), mDestFile.getPath());
        }
    }
    
    public void onProgressUpdate(long totalSize, long currentSize){};

}
