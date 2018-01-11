/*
*  Created by wilson.souza 15-09-14
*  Copyright (c) 2014 Dedalus
*  Project WRR
*  Description: create an google spreadsheet to export data
*  Last update by wilson.souza 9-15-14 
*  Last update by wilson.souza 9-17-14
*  Last update by wilson.souza 9-19-14
*  Last update by wilson.souza 9-23-14
*/
function doPost(Request)
{
  var szResult = "IDD_ERROR";
  var szKey = "PLOJV6Yw2xmEyGiXml9YPvy3vNk01HHXXX7NjHWahE7wIeqrBtKoxOKcQEdFuN7";
  
  try
  {
    /**/
    if(szKey != Request.parameters.AuthorizeKey)
      throw "IDD_INVALID_KEY";
      
    /*
    * decode JSON array
    */
    var pItems = JSON.parse(Request.parameter.Items).Items;

    /* 
    * archive is not exists then create new file
    */
    var pHandle = SpreadsheetApp.create(Request.parameter.DocName);
    var pSheet = pHandle.getSheets()[0];
    var pData   = pSheet.getRange(1,1,pItems.length, pItems[0].length);

    pData.setValues(pItems);
      
    /* flush data of cache for disk */
    SpreadsheetApp.flush();
    /* mount result */
    szResult = "IDD_OK?ID(" + pHandle.getId() + ")";
  }
  catch(e)
  {
    szResult = new String(e.message);
    /**/
    if(szResult.indexOf("IDD_") == -1)
      szResult = "IDD_EXCEPTION_" + e.message;
    
    /**/
    Logger.log(szResult);
  }
  
  Logger.log(szResult);
  return ContentService.createTextOutput(szResult).setMimeType(ContentService.MimeType.TEXT);
}
/* eof */