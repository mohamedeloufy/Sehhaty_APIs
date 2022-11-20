package ReadDataFromFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties_Login_credentials {

    public static Properties Login_credentials =loadProperties
            (System.getProperty("user.dir")+"//src//main//java//properties//Login_credentials.properties");

    private static Properties loadProperties(String path)
    {
        Properties pro =new Properties();
        try {
            FileInputStream stream=new FileInputStream(path);
            pro.load(stream);
        }catch(FileNotFoundException e)
        {
            System.out.println("Error occurred :"+e.getMessage());
        }catch (IOException e)
        {
            System.out.println("Error occurred :"+e.getMessage());
        }
        return pro;
    }
}
