/*
 * 
 * Copyright (C) 2014 Dedalus Prime
 * 
 * Created by Wilson.Souza Created 17-02-2015 Description load google spreedsheet on the AODocs by
 * webserver call Last updated
 */
var g_nVersion = 1.0;
/*
 * Method ImportCompanyData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description get company documents and property
 */
function FillCompanyKeys()
{
   try
   {
      var bSuccess = SetActiveSheet("CompanyKeys");
      /**/
      if(bSuccess)
      {
         var pHandle = SpreadsheetApp.getActiveSpreadsheet();
         var pSheet = pHandle.getSheetByName("CompanyKeys");
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.CompanyId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 10000
         };
         var pDocList = ListDocuments(pSearch);
         /* register log */
         Logger.log(pDocList.Handle);
         /* clear all row and columns */
         pSheet.clear();
         /**/
         for(var i in pDocList.Handle.documentList)
         {
           var pDoc = pDocList.Handle.documentList[i];
           /* append in sheet */
           pSheet.appendRow([pDoc.title, pDoc.id, pDoc.fields[0].values[0]]);
         }
      }
      else
      {
         throw "Sheet CompanyKeys is not found!";
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      SpreadsheetApp.getUi().alert(e.message);
      throw e;
   }
}
/*
 * Method ImportCompanyData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description get contract documents and property
 */
function FillContractKeys()
{
   try
   {
      var bSuccess = SetActiveSheet("ContractKeys");
      /**/
      if(bSuccess)
      {
         var pHandle = SpreadsheetApp.getActiveSpreadsheet();
         var pSheet = pHandle.getSheetByName("ContractKeys");
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.ContractId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 10000
         };
         var pDocList = ListDocuments(pSearch);
         /* register log */
         Logger.log(pDocList.Handle);
         /**/
         pSheet.clear();
         /**/
         for(var i in pDocList.Handle.documentList)
         {
           var pDoc = pDocList.Handle.documentList[i];
           /* append in sheet */
           pSheet.appendRow([pDoc.title, pDoc.id, pDoc.fields[0].values[0]]);
         }
      }
      else
      {
         throw "Sheet ContractKeys is not found!";
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      SpreadsheetApp.getUi().alert(e.message);
      throw e;
   }
}
/*
 * Method ImportCompanyData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description get supply documents and property
 */
function FillSupplyKeys()
{
   try
   {
      var bSuccess = SetActiveSheet("SupplyKeys");
      /**/
      if(bSuccess)
      {
         var pHandle = SpreadsheetApp.getActiveSpreadsheet();
         var pSheet = pHandle.getSheetByName("SupplyKeys");
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.SupplyId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 10000
         };
         var pDocList = ListDocuments(pSearch);
         /* register log */
         Logger.log(pDocList.Handle);
         /**/
         pSheet.clear();
         /**/
         for(var i in pDocList.Handle.documentList)
         {
           var pDoc = pDocList.Handle.documentList[i];
           /* append in sheet */
           pSheet.appendRow([pDoc.title, pDoc.id, pDoc.fields[0].values[0]]);
         }
      }
      else
      {
         throw "Sheet SupplyKeys is not found!";
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      SpreadsheetApp.getUi().alert(e.message);
      throw e;
   }
}
