package demo;

public class indexDrama {


    private String title;
    private String weburl;
    private float score;
  
    public static final String TITLE = "title";
    public static final String WEBURL = "weburl";

    public indexDrama(String title, String weburl) {
      
        this.title = title;
        this.weburl = weburl;
    }


    public String getTitle() {
        return title;
    }
 
    public String getweburl() {
        return weburl;
    }

    @Override
    public String toString() {
        return title;
    }
}
