/*
 * 
 * Copyright (C) 2014, 2015 Dedalus Prime
 * 
 * Created by Wilson.Souza support to ao-docs-api search rest api
 * 
 * this library is not complete yet
 */

/*
 * Method SearchDocuments Parameters json pvalue {LibId, PageSize, ClassId, SecurityCode} Return
 * json Created 2-11-2015 Author wilson.souza Project WRR Group Description get list of
 */
function ListDocuments(pKey)
{
   try
   {
      var pHeader =
      {
         'Content-Type': 'application/json; charset=UTF-8',
         'X-JavaScript-User-Agent': 'Google APIs Explorer'
      };
      var pOptions =
      {
         'method': 'POST',
         'headers': pHeader
      };
      var szUrl = Utilities
            .formatString(
                  "https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/%s/list?pageSize=%d&classId=%s&securityCode=%s",
                  pKey.LibId, pKey.PageSize, pKey.ClassId, pKey.SecurityCode);
      var pHandle = UrlFetchApp.fetch(szUrl, pOptions);

      if (pHandle.getResponseCode() != 0xc8)
         return null;

      var pOut = JSON.parse(pHandle.getContentText());
      /**/
      if(pOut != null)
         return {"Handle": pOut, "Result": pOut.documentList != null? true : false};
   }
   catch(e)
   {
      Logger.log(e.message);
      SpreadsheetApp.getUI().alert(e.message); /* display alert pop up */
      throw e;
   }
   return {"Handle": null, "Result": false};
}
/*
 * Method SearchDocuments Parameters json pvalue {libname, libid, classname, classid, securityid,
 * filename, fileid} Return Document id Created 2-11-2015 Author wilson.souza Project WRR Group
 * Description update one existed document
 */
function SearchDocuments(pValue)
{
   try
   {
      var pHeader =
      {
         'Content-Type': 'application/json; charset=UTF-8',
         'X-JavaScript-User-Agent': 'Google APIs Explorer'
      };
      var pOptions =
      {
         'method': 'POST',
         'headers': pHeader
      };

      // https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/
      var szUrl = Utilities
            .formatString(
                  "https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/%s/search?classId=%s&securityCode=%s",
                  pValue.LibId, pValue.ClassId, pValue.SecurityCode);
      var pHandle = UrlFetchApp.fetch(szUrl, pOptions);
      /**/
      if (pHandle.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 0xc8!",
               pHandle.getResponseCode());
      /**/
      var szFileID = new String();
      var p = JSON.parse(pHandle.getContentText());

      /**/
      for ( var i in p.documents)
      {
         var pDoc = p.documents[i];
         /* debug out */
         Logger.log(Utilities.
               formatString("FileName %s ID %s", pDoc.title, pDoc.id));
         /**/
         if (pDoc.title.equalsIgnoreCase(pValue.FileName))
            return {"FileName": pDoc.title, "FileId": pDoc.id, "Result":true};
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      throw e;
   }
   return {"FileName": null, "FileId": null, "Result": false};
}
/** ************************************************************************************************************* */
function TestListDocuments()
{
   var pDocList = ListDocuments(
   {
      'LibId': 'OxTTKOg3phttYDRAAa',
      'PageSize': 10000,
      'ClassId': 'OxTyvgt67lJXumKwNO',
      'SecurityCode': 'OxUYelc11TRn6SoL1a'
   });
   //
   for ( var i in pDocList.Handle.documentList)
      Logger.log(pDocList.Handle.documentList[i].title + ", "
            + pDocList.Handle.documentList[i].id);
}
/* oef */
