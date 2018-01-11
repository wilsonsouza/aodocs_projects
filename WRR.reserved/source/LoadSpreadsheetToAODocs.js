/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Load spreadsheet and send on AODocs by web service call
*
*  Process all data
*/
function LoadSpreadsheet()
{
   try
   {
      //search by spreadsheet with tokens
      var pTokenSpreadsheetHandle = GetSpreadsheetHandleByName("Categorias");
      //read all tokens necessary
      var pATokens = LoadTokensFromSpreadsheet(pTokenSpreadsheetHandle);
      //
      //
      for (var y in pATokens)
      {
         var szSecurityCodeId = pATokens[y][0];
         var szLibraryId = pATokens[y][0];
         var szCategoryId = pATokens[y][0];
         var pValuesSpreadsheetHandle = GetSpreadsheetHandleByName("Sheet1");
         var pAValues = pValuesSpreadsheetHandle.getDataRange().getValues();

         //list all data
         for (var i in pAValues)
            InsertChildCategoryValue(szLibraryId, szCategoryId, i, szSecurityCodeId, i);
      }
   }
   catch (e)
   {
      SpreadsheetApp.getUi().alert("Erro!!!", e.message, SpreadsheetApp.getUi().ButtonSet.OK);
      throw e;
   }
}