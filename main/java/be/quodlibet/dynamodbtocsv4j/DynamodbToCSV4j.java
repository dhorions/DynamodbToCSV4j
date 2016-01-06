package be.quodlibet.dynamodbtocsv4j;

import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Dries Horions <dries@quodlibet.be>
 */
public class DynamodbToCSV4j
{
    public static void main(String[] args)
    {
        String configFile = "config.json";
        JSONObject config = new JSONObject();
        if (args.length > 0)
        {
            configFile = args[0];
        }
        try
        {
            config = new JSONObject(new String(Files.readAllBytes(Paths.get(configFile))));
        }
        catch (JSONException ex)
        {
            System.out.println(configFile + " is not a valid JSON config file(" + ex.getMessage() + ").");
        }
        catch (IOException ex)
        {
            System.out.println(configFile + " can not be read(" + ex.getMessage() + ").");
        }
        d2csv d = new d2csv(config);
    }
}
