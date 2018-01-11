/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*
*  return array with (SecurityCode, LibraryID, CategoryID)
*/
function LoadTokensFromSpreadsheet(pSheet)
{
   try
   {
      var pAValues = pSheet.getValues();
      var pAOut = new Array();
      //
      if (pAValues.length > 0)
      {
         for (var y in pAValues)
         {
            var pLines = new Array();
            //
            for (var x in pAValues[y])
               pLines.push(pAValues[y][x]);
            //
            pAOut.push(pLines);
         }
         return pAOut;
      }
      else
         throw "Spreadsheet is empty!";
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
   return null;
}