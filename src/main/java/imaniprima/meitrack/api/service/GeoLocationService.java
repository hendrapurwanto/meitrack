package imaniprima.meitrack.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

@Service
public class GeoLocationService {
    private static HttpURLConnection con;
    public JsonNode getDataByUrl(String longitude, String latitude){

        String url = "https://nominatim.openstreetmap.org/reverse?format=json&zoom=18&addressdetails=0&lon="+longitude+"&lat="+latitude;

        try {
            URL myurl = null;
            try {
                myurl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            con = (HttpURLConnection) myurl.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(6000000);
            con.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            StringBuilder content;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                in.close();
            }
            String jsonString = content.toString();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode actuaObj = mapper.readTree(jsonString);
//            RawData newJsonData = mapper.readValue(myurl,RawData.class);
            return actuaObj;
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            con.disconnect();
        }
        return null;
    }
}
