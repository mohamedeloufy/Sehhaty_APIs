package ReadDataFromFiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties_Verify_otp {

    public static Properties verify_otp =loadProperties
            (System.getProperty("user.dir")+"//src//main//java//properties//verify_otp.properties");

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
