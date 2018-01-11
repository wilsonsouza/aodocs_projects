/*
 * 
 * Copyright (C) 2014, 2015 Dedalus Prime
 * 
 * Created by Wilson.Souza support to ao-docs-api search rest api
 * 
 * this library is not complete yet
 * 
 * Parameters {'SecurityCode':<value>, 'LibraryId':<value>, 'ClassId':<value>, 'LibraryName':<value>,
 * 'ClassName':<value>, 'Title':<value>, 'DocName':<value>, { 'FieldList':<value> } } '
 */
var g_pHeaders =
{
   'Content-Type': 'application/json',
   'X-JavaScript-User-Agent': 'Google APIs Explorer'
};
/*
 * Method InsertDocument Parameters json pvalue {'SecurityCode': <value>, 'AO_Docs_Data': <value>}
 * Return Document id Created 2-11-2015 Author wilson.souza Project WRR Group Description insert new
 * document
 */
function InsertDocument(pKey, pValue)
{
   try
   {
      var szUrl = Utilities
            .formatString(
                  "https://api-dot-ao-docs.appspot.com/_ah/api/document/v1?securityCode=%s",
                  pKey.SecurityCode);
      var pOptions =
      {
         'contentType': 'application/json; charset=UTF-8',
         'headers': g_pHeaders,
         'method': 'put',
         'payload': JSON.stringify(pValue),
         'muteHttpExceptions': false,
         'followRedirects': false
      };
      var pHandle = UrlFetchApp.fetch(szUrl, pOptions);
      /**/
      // Logger.log(pHandle);
      /**/
      if (pHandle.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 0xc8!",
               pHandle.getResponseCode());
      /**/
      var pSuccess = JSON.parse(pHandle.getContentText());
      /**/
      // Logger.log(pSuccess);
      /**/
      if (pSuccess != null)
         return{
            "Handle": pSuccess,
            "Result": pSuccess.id.length > 0? true: false
         };
   }
   catch(e)
   {
      SpreadsheetApp.getUi().alert(e.message);
      Logger.log(e.message);
      // throw e;
   }
   return{
      "Handle": null,
      "Result": false
   };
}
/*
 * Method UpdateDocument Parameters json pvalue {libname, libid, classname, classid, securityid,
 * filename, fileid} Return Document id Created 2-11-2015 Author wilson.souza Project WRR Group
 * Description update one existed document
 */
function UpdateDocument(pKey, pValue)
{
   try
   {
      var szUrl = Utilities
            .formatString(
                  "https://api-dot-ao-docs.appspot.com/_ah/api/document/v1/%s?securityCode=%s",
                  pKey.DocId, pKey.SecurityCode);
      var pOptions =
      {
         'contentType': 'application/json; charset=UTF-8',
         'headers': g_pHeaders,
         'method': 'post',
         'payload': JSON.stringify(pValue),
         'muteHttpExceptions': false,
         'followRedirects': false
      };
      var pHandle = UrlFetchApp.fetch(szUrl, pOptions);
      /**/
      if (pHandle.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 0xc8!",
               pHandle.getResponseCode());
      /**/
      var pSuccess = JSON.parse(pHandle.getContentText());
      /**/
      /**/
      if (pSuccess != null)
         return{
            "Handle": pSuccess,
            "Result": pSuccess.id.length != 0? true: false
         };
   }
   catch(e)
   {
      SpreadsheetApp.getUi().alert(e.message);
      Logger.log(e.message);
      // throw e;
   }
   return   {
      "Handle": null,
      "Result": false
   };
}

/** ********************************************************************************************** */
function TestUpdateDocument()
{
   var pDocList = ListDocuments(
   {
      'LibId': 'OxTTKOg3phttYDRAAa',
      'PageSize': 10000,
      'ClassId': 'OxTyvgt67lJXumKwNO',
      'SecurityCode': 'OxUYelc11TRn6SoL1a'
   });
   //
   Logger.log(pDocList.Result);
  
   if (pDocList.Result == true)
   {
      for ( var n in pDocList.Handle.documentList)
      {
         var d = pDocList.Handle.documentList[n];
         var pData =
         {
            "libraryId": d.libraryId,
            "libraryName": d.libraryName,
            "className": d.className,
            "classId": d.classId,
            "title": d.title,
            "initialAuthor": "wilson.souza",
            "onlyAdminCanDelete": "true",
            "onlyAdminCanChangeAcl": "false",
            "userCanChangeProperties": "true",
            "userCanChangeAcl": "true",
            "userCanDelete": "false",
           "fields": d.fields
         };
        
         Logger.log(d.id);

         var p = UpdateDocument(
         {
            "DocId": d.id,
            "SecurityCode": "OxUYelc11TRn6SoL1a"
         }, pData);
         //
         Logger.log(Utilities.formatString("Doc %s updated %d", p.Handle.title,
               p.Result? 1: 0));
      }
   }
}
/* eof */