// JavaScript source code
import com.altirnao.aodocs.script.*;
import com.altirnao.aodocs.custom.*;


String fileId = getParam("templateId");
		
class URLEncoder 
{
   private String encode(String str) 
   {
      StringBuffer result = new StringBuffer();
      String specialChars = " &+?/=%";

      for (int i = 0; i < str.length(); i++) 
      {
         char c = str.charAt(i);
   
         if (specialChars.indexOf(c) >= 0) 
         {
            byte[] bytes = ("" + c).getBytes();
            result.append("%");

            for (int j = 0; j < bytes.length; j++) 
            {
               if (bytes[j] < 0x10) 
               {
                  result.append("0");
               }
               result.append(Integer.toHexString(bytes[j]));
            }
         } 
         else 
         {
            result.append(c);
         }
      }
      return result.toString();
   }
}
