/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-1-2014
*  Description load google spreedsheet on the AODocs by webserver call
*  Last updated
*/
function onOpen(Request)
{
   var pInterface = SpreadsheetApp.getUi();
   //create sidebar
   var pPageHandle = HtmlService.createHtmlOutputFromFile("PageToLoadOnADocs")
   .setTitle("AODocs Category Link...")
   .setWidth(0x96);

   //show sidebar
   pInterface.showSidebar(pPageHandle);
}
