/*
*  Created by wilson.souza
*  Copyright (c) 2014 Dedalus
*
*  
*
*  Receive follow parameters
*  First   FileID copied
*  Second  AODocsDocumentTitle to change
*/
function doPost(Request)
{
   var szContextResult = "IDD_ERROR";
   var szKeyID = "PLOJV6Yw2xmEyGiXml9YPvy3vNk99HHXXX7NjHWahE7wIeqrBtKoxOKcQEdFuN7";

   try
   {
      //check key
      if (szKeyID != Request.parameters.KeyID)
         throw "IDD_INVALID_KEY";

      DriveApp.
      getFileById(Request.parameters.FileID).            //get file by ID
      setName(Request.parameters.AODocsDocumentTitle);   //change file name
      // mount response with file name em
      szContextResult = "IDD_OK";
   }
   catch (e)
   {
      szContextResult = e.message;
      Logger.log(e.message);
   }
   return ContentService.createTextOutput(szContextResult).setMimeType(ContentService.MimeType.TEXT);
}
// JavaScript source code
