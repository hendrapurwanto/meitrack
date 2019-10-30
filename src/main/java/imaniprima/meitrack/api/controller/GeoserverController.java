package imaniprima.meitrack.api.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping(path = "/geoserver")
@Api(tags = "Geoserver")
public class GeoserverController {

    private static final String USER_AGENT = "USER_AGENT";

    @Autowired
    private Environment env;

    @PostMapping(path = "")
    @ApiOperation("Post Geoserver")
    public String getGeoserver(
        @ApiParam(value = "The model XML you want to store" ,required=true )
        @RequestBody String model
    ) throws IOException {

//        try{
            URL url = new URL(env.getProperty("wfs-url")+"/wfs");
            String userCredentials = env.getProperty("wfs-user")+":"+env.getProperty("wfs-password");
            String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));

            System.err.println("basicAuth :: "+basicAuth);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty ("Authorization", basicAuth);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Language", "en-US");
            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(model);
            osw.flush();
            osw.close();
            /*System.err.println("basicAuth :: "+basicAuth);
            System.err.println("osw.toString() = "+osw.toString());
            System.err.println("connection.getResponseCode()");
            System.err.println(connection.getResponseCode());*/

            // reading the response
            InputStreamReader reader = new InputStreamReader( connection.getInputStream() );
            StringBuilder buf = new StringBuilder();
            char[] cbuf = new char[ 2048 ];
            int num;
            while ( -1 != (num=reader.read( cbuf )))
            {
                buf.append( cbuf, 0, num );
            }
            String result = buf.toString();
            System.err.println( "\nResponse from server after POST:\n" + result );


            if (connection.getResponseCode() == 200) {
                connection.getResponseMessage();
            }
            return result;
        /*} catch (MalformedURLException e) {
            e.printStackTrace();
            return "2";
        } catch (IOException e) {
            e.printStackTrace();
            return "1";
        }*/
    }
}
