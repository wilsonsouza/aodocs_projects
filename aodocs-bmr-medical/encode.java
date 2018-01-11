
/*************************************************************************************************/
class URLEncoder 
{
   private String encode(String strData) 
   {
      StringBuffer pResult = new StringBuffer();
      String strSpecialChars = " &+?/=%";

      for (int i = 0; i < strData.length(); i++) 
      {
         char c = strData.charAt(i);
      
         if (strSpecialChars.indexOf(c) >= 0) 
         {
            byte[] bytes = ("" + c).getBytes();
            pResult.append("%");
         
            for (int j = 0; j < bytes.length; j++) 
            {
               if (bytes[j] < 16) 
               {
                  pResult.append("0");
               }
               pResult.append(Integer.toHexString(bytes[j]));
            }
         } 
         else 
         {
            pResult.append(c);
         }
      }
      return pResult.toString();
   }
}
/*************************************************************************************************/