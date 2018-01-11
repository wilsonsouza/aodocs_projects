/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-22-2014
*  Description fill google spreedsheet with my informations
*  Last updated
*/
function LoadMySpreadsheet()
{
  var pSpreadsheet = SpreadsheetApp.getActiveSheet();
  var nColumnId = 4;
  //
  try
  {
    var pData = [
    "Wilson Alves de Souza", "12:00 às 13:00", "8:00 às 17:00", "8:00 horas", "", "Analista Programador Sênior"];
  
    //only make any cause if timesheet
    if(pSpreadsheet.getName().indexOf("Folha de Ponto") != -1)
    {
      var pALines = [3, 5, 6, 7, 8, 9]
      var pAValues = pSpreadsheet.getDataRange().getValues();
      //
      for(var nLines = 0; nLines < pALines.length; nLines++)
      {
        var pValue = pAValues[nLines][nColumnId];
        var pCell  = pSpreadsheet.getDataRange().getCell(pALines[nLines], nColumnId);
        //register logger
        Logger.log(Utilities.formatString("pValue %s, length %d pCell %s", pValue, pValue.length, pCell.getValue()));
        //
        if(pValue.length == 0)
          pCell.setValue(pData[nLines]);
      }
    }
  }
  catch(e)
  {
    Logger.log(e);
    SpreadsheetApp.getUi().alert(e.message);
  }
  return pSpreadsheet;
}
