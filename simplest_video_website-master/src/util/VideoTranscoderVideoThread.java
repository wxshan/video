package util;

import bean.Configure;
import bean.Video;
import bean.Videostate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import service.BaseService;

import javax.servlet.ServletContext;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2017/10/19 0019.
 */
public class VideoTranscoderVideoThread extends Thread{

    private String url;
    private String newurl;

    //构造函数
    public VideoTranscoderVideoThread(String url,String newurl){
        super();
        this.url = url;
        this.newurl = newurl;
    }

    public void run(){
        try{

            //获取传入的url
            String videotranscodecommand = "cmd ";
            videotranscodecommand += "/c start ";
            //String videotranscodecommand = "C:\\Users\\Administrator\\Desktop\\test\\ffmpeg\\bin ";
            videotranscodecommand += "ffmpeg -rtsp_transport tcp ";
            videotranscodecommand += "-i ";
            videotranscodecommand += "\""+url+"\" ";
            videotranscodecommand += "-acodec aac -strict experimental -ar 44100 -ac 2 -b:a 96k -r 40 -b:v 500k -s 1280x720 ";
            videotranscodecommand += "-f flv ";
            videotranscodecommand += "\"";
            videotranscodecommand += newurl;
            videotranscodecommand += "\"";


            Process process = Runtime.getRuntime().exec(videotranscodecommand);

            BufferedInputStream in = new BufferedInputStream(process.getInputStream());
            BufferedInputStream err = new BufferedInputStream(process.getErrorStream());
            BufferedReader inBr = new BufferedReader(new InputStreamReader(in));
            BufferedReader errBr = new BufferedReader(new InputStreamReader(err));
            String lineStr;
            while ((lineStr = inBr.readLine()) != null){
                System.out.println(lineStr);
            }
            while ((lineStr = errBr.readLine()) != null){
                System.out.println(lineStr);
            }

            if (process.waitFor() != 0){
                if (process.exitValue() == 1)
                    System.out.println("Failed!");
            }
            inBr.close();
            in.close();

            sleep(10*1000);


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
