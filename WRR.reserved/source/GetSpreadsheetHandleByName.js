/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  
*  Searching by spreadsheet name
*  return pointer to Sheet
*
*/
function GetSpreadsheetHandleByName(szSpreadsheetName)
{
   try
   {
      var pSheet = SpreadsheetApp.getActiveSpreadsheet();
      //
      for (var i = 0; i < pSheet.getSheets().length; i++)
      {
         var p = pSheet.getSheets()[i];
         //
         Logger.log(p.getName());
         //
         if (p.getName() == szSpreadsheetName)
            return p;
      }
      //
      //generate exception
      throw Utilities.formatString("Failed! Spreadsheet %s not found", szSpreadsheetName);
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }            
}
