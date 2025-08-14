import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date getDate(String d) {
        try{
            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
            return dateFormat.parse(d);
        }catch (ParseException ex)
        {

            ex.printStackTrace();
            return null;
        }

    }
    public static String displayString(Date d)  {

            SimpleDateFormat dateFormat= new SimpleDateFormat("dd/mm/yyyy");
            return dateFormat.format(d);


    }

}
