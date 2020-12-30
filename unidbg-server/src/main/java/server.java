import com.lang.handler.XhsSheildHandler;
import com.lang.sekiro.netty.client.SekiroClient;

public class server {

    public static void main(String[] args) {
        final SekiroClient sekiroClient = SekiroClient.start("124.196.9.211", 11000, "123456789", "xhs");
        sekiroClient.registerHandler("sheild", new XhsSheildHandler());
    }
}
