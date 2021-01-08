import com.github.unidbg.Emulator;
import com.github.unidbg.file.FileResult;
import com.lang.handler.XhsSheildHandler;
import com.lang.platform.xhs.xhsShield;
import com.lang.sekiro.netty.client.SekiroClient;

import java.io.IOException;

public class server {

    public static void main(String[] args) {
        xhsShield test = null;
        try {
            test = new xhsShield() {
                @Override
                public FileResult resolve(Emulator emulator, String pathname, int oflags) {
                    return null;
                }
            };
            final SekiroClient sekiroClient = SekiroClient.start("124.196.9.211", 11000, "123456789", "xhs");
            sekiroClient.registerHandler("sheild", new XhsSheildHandler(test));
        } catch (Exception e) {
            e.printStackTrace();
            try {
                test.destroy();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}
