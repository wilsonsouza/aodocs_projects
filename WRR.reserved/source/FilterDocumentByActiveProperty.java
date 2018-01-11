/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 18-12-2014
*  Description filter documents by active property on object
*  Last update 2-25-15
*  Last update 3-03-15
*/
try
{
   class JsonDecode
   {
      static public String[] JsonToArray(String szContent) throws Exception
      {
         String szData = szContent.replaceAll("\\{", "").replaceAll("\\[", "").
         replaceAll("\\}", "").replaceAll("\\]", "").replaceAll("\"", "");
         return szData.split(",");
      }
      static public String[] Search(String[] pSearchList, String szData) throws Exception
      {
         if(!szData.trim().isEmpty())
         {
            for(String szRow: pSearchList)
            {
               //
               if(szRow.indexOf(szData.trim()) != -1)
                  return szRow.split(":");
            }
         }
         return new String[]{ szData, new String() };
      }
   }
   /**********************************************************************************************/
   class UrlLaunch
   {
      public String m_szContent = null;
      public UrlLaunch(String szUrl) throws Exception
      {
         HTTPResponse pHttp = getUrlFetchService().
               fetch(szUrl, 
                     HTTPMethod.POST, 
                     new byte[]{}, 
                     this.AllocHeader(new String[]{"Content-Type: application/json", "X-JavaScript-User-Agent:  Google APIs Explorer"}),
                     FetchOptions.Builder.withDefaults());
         /* fill result */
         this.m_szContent = new String(pHttp.getContent());
         
         /* verify result code */
         if(pHttp.getResponseCode() != 0xc8)
            throw new Exception("UrlLaunch http %d!" + String.format("%d", pHttp.getResponseCode()));
      }
      public boolean IsExists(ReadableDocument pDoc) throws Exception
      {
         String[] szResult = JsonDecode.Search(JsonDecode.JsonToArray(this.m_szContent), pDoc.getId());
         return szResult[1].indexOf(pDoc.getId()) != -1;
      }
      public ArrayList<HTTPHeader> AllocHeader(String[] pHeaderList)
      {
         ArrayList<HTTPHeader> pHeader = new ArrayList<HTTPHeader>();
         /* mount */
         for(String szRow: pHeaderList)
         {
            String[] pItems = szRow.split(":");
            pHeader.add(new HTTPHeader(pItems[0], pItems[1]));
         }
         return pHeader;
      }
   }
   /**********************************************************************************************/
   class BuildParameters
   {
      static public boolean ToBool(String szValue) throws Exception
      {
         if(szValue == null)
            throw new Exception("Parameter szValue is NULL!");
         /* assume default value */
         if(szValue.isEmpty())
            szValue = "false";
         /* convert string value to boolean value and return to source */
         return Boolean.parseBoolean(szValue);
      }
      static public String GetPropertyByName(ReadableDocument pDoc, String szName) throws Exception
      {
         /* read property by name */
         return new String(pDoc.getField(szName).toString());
      }
   }
   /**********************************************************************************************/
   /* check class name */
   String szClass = document.getDocumentClass();
   boolean bProcess = false;
   /* get parameter value */
   String szName = new String("Status");
   ArrayList<ReadableDocument> pDocListToKill = new ArrayList<ReadableDocument>();
   /* remove document of list */
   for(ReadableDocument pDoc: relatedDocuments)
   {
      //debug(String.format("bProcess %d", bProcess? 1: 0));
      /* if company */
      if(bProcess)
      {
         /*
          * Parameters definitions
          * SecurityCode=OxUYelc11TRn6SoL1a
          * LibraryId=OxTTKOg3phttYDRAAa
          * ClassId=OxTyvgt67lJXumKwNO
          * Url=
          */
         String szSecurityCode = new String(getParam("SecurityCode"));
         String szLibraryId = new String(getParam("LibraryId"));
         String szClassId = new String(getParam("ClassId"));
         String szUrl = new String(getParam("Url"));
         /* 
          * mount url
          * "https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/%s/search?classId=%s&securityCode=%s" 
          * */
         String szLink = String.format(szUrl, szLibraryId, szClassId, szSecurityCode);
         UrlLaunch pLaunch = new UrlLaunch(szLink);
         /* is not exists */
         if(!pLaunch.IsExists(pDoc))
            pDocListToKill.add(pDoc);
         /* debug out */
         debug(String.format("Document %s result %d", pDoc.getId(), pLaunch.IsExists(pDoc)? 1: 0));
      }
      else
      {
         /* get status of document */
         boolean bEnabled = BuildParameters.ToBool(BuildParameters.GetPropertyByName(pDoc, szName));
         /* remove document if deactived */
         if(!bEnabled)
         {
            /* mark to remotion */
            pDocListToKill.add(pDoc);
         }
      }
   }
   /* remove document of list */
   for(ReadableDocument pDoc: pDocListToKill)
	   relatedDocuments.remove(pDoc);
   
   debug("Processed...");
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
return relatedDocuments;