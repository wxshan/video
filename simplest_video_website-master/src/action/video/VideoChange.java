package action.video;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.apache.commons.lang.ObjectUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import util.VideoTranscoderVideoThread;

import javax.servlet.http.HttpServletResponse;


/**
 * Created by Administrator on 2017/10/23 0023.
 */

public class VideoChange extends ActionSupport{

    private String url="";
    private String newurl;
    private Integer id;

    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id=id;
    }

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


    public String execute(){

        HttpServletResponse response = ServletActionContext.getResponse();

        response.setContentType("text/plain;charset=UTF-8");

        try {
            //进行推流的转换，将地址进行转换之后返回
            newurl="rtmp://192.168.63.160:1935/live/test1"+id;
            VideoTranscoderVideoThread videoTranscoderVideoThread = new VideoTranscoderVideoThread(url,newurl);
            videoTranscoderVideoThread.start();
            //将newurl返回到前台

            response.getWriter().write(newurl);
            return null;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return ERROR;
        }
    }
}
