package com.example.binumtontine.activity.convertisseur;

import java.util.Date;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author Valdès
 */
public class DateTools {
    private static String pattern = "dd-MM-yyyy";
//    private static String pattern = "yyyy-MM-dd";
    public static String formatterDateToString(Date date){

        return (date!=null? DateFormatUtils.format(date, pattern):null);
    }


}
