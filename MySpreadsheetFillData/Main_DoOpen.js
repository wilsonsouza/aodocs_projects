/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-22-2014
*  Description fill google spreedsheet with my informations
*  Last updated
*/
function onOpen(Request)
{
   var pInterface = SpreadsheetApp.getUi();
   //create sidebar
   var pHtmlHandle = HtmlService.createHtmlOutputFromFile("SideBarToFillMySpreadsheet")
   .setTitle("Only for me")
   .setWidth(0x96);

   //show sidebar
   pInterface.showSidebar(pHtmlHandle);
}

