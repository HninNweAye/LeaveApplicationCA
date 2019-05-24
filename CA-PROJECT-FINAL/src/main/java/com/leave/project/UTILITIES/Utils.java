package com.leave.project.UTILITIES;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
  public static String format(Date date) {
  	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
  	return sdf.format(date);
  }
}
