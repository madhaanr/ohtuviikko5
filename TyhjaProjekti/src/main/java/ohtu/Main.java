package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;
 
public class Main {
 
    public static void main(String[] args) throws IOException {
        String studentNr = "13695923";
        if ( args.length>0) {
            studentNr = args[0];
        }
 
        String url = "http://ohtustats-2013.herokuapp.com/opiskelija/"+studentNr+".json";
 
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        client.executeMethod(method);
 
        InputStream stream =  method.getResponseBodyAsStream();
 
        String bodyText = IOUtils.toString(stream);
 
//        System.out.println("json-muotoinen data:");
//        System.out.println( bodyText );
 
        Gson mapper = new Gson();
        Palautukset palautukset = mapper.fromJson(bodyText, Palautukset.class);
 
//        System.out.println("oliot:");
//        for (Palautus palautus : palautukset.getPalautukset()) {
//            System.out.println( palautus );
//        }
        int tehtavienSumma=0;
        int aikaaMeni=0;
        int count = 0;
        for (Palautus palautus : palautukset.getPalautukset()) {
            if(palautus.getViikko()==9) {
                System.out.println("\nyhteensä  "+ tehtavienSumma+" tehtävää "+aikaaMeni+" tuntia");
                System.exit(0);
            }
            if(count==0) {     
                System.out.println("opiskelijanumero "+palautus.getOpiskelijanumero()+"\n");
                ++count;
            }
//            System.out.println("viikko "+palautus.getViikko()+": "+palautus.getTehtavia()
//                    +" tehtävää "+palautus.getTehtavat()+"\t aikaa kului "+palautus.getTunteja()
//                    +" tuntia");
            System.out.println(String.format("%-7s%2s%2s%2s%-10s%-27s%5s%3s","viikko",palautus.getViikko(),": ",palautus.getTehtavia()
                    ," tehtävää",palautus.getTehtavat()," aikaa kului ",palautus.getTunteja()
                    +" tuntia"));
            tehtavienSumma+=palautus.getTehtavia();
            aikaaMeni+=palautus.getTunteja();
        }      
    }
}
