/*
 * 
 * Copyright (C) 2014 Dedalus Prime
 * 
 * Created by Wilson.Souza Created 8-1-2014 Description load google spreedsheet on the AODocs by
 * webserver call Last updated
 */
var g_pHandle =
{
   'SecurityCode': 'OxUYelc11TRn6SoL1a',
   'LibraryName': 'Comum',
   'Handle':
   {
      'LibraryId': 'OxTTKOg3phttYDRAAa',
      'CompanyId': 'OxTyvgt67lJXumKwNO',
      'SupplyId': 'P2aTI9yvx572t6qQKB',
      'ContractId': 'P2aTKinuQzWSgmWsmn'
   },
   'Company':
   {
      'ClassName': 'Coligada'
   },
   'Supply':
   {
      'ClassName': 'Forncedor'
   },
   'Contract':
   {
      'ClassName': 'Contrato'
   }
};
/*
 * Method ImportCompanyData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description import data to ao-docs
 */
function SetActiveSheet(szSheetName)
{
   try
   {
      var pSheet = SpreadsheetApp.getActive().getSheets();
      //
      for ( var i in pSheet)
      {
         if (pSheet[i].getName().equalsIgnoreCase(szSheetName))
         {
            SpreadsheetApp.setActiveSheet(pSheet[i]);
            return true;
         }
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      throw e;
   }
   return false;
}
/*
 * Method FindDoc Parameters json Return json Created 2-11-2015 Author wilson.souza Project WRR
 * Group Description search by document name
 */
function FindDoc(pDocList, szDocName)
{
   for ( var nDoc in pDocList.Handle.documentList)
   {
      if (pDocList.Handle.documentList[nDoc].title.equalsIgnoreCase(szDocName))
         return{
            'DocInfo': pDocList.Handle.documentList[nDoc],
            'Result': true
         };
   }
   return{
      'DocInfo': null,
      'Result': false
   };
}
/*
 * Method ImportCompanyData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description import data to ao-docs
 */
function ImportCompanyData()
{
   try
   {
      var bActived = SetActiveSheet('Coligada');

      if (bActived)
      {
         var pHandle = SpreadsheetApp.getActiveSheet();
         var pRange = pHandle.getDataRange();
         var pValues = pRange.getValues();
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.CompanyId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 10000
         };
         /* get list of all documents on the  library*/
         var pDocList = ListDocuments(pSearch);
         /**/
         for ( var nRow in pValues)
         {
            var szDocName = pValues[nRow][0];
            var szDescription = pValues[nRow][1];
            /**/
            if (nRow == 0)
               continue;
            /**/
            /**/
            if (pValues[nRow].length < 2)
               throw Utilities
                     .formatString('Spreadsheet with invalid columns!');
            /* check first line */
            if (pValues[nRow][0].length == 0)
            {
               Logger.log(Utilities.formatString(
                     "Line %d is empty, jump next line!", nRow));
               continue;
            }
            /* search document */
            var pSuccess = FindDoc(pDocList, szDocName);
            /**/
            var pData =
            {
               "libraryId": g_pHandle.Handle.LibraryId,
               "libraryName": g_pHandle.LibraryName,
               "className": g_pHandle.Company.ClassName,
               "classId": g_pHandle.Handle.CompanyId,
               "title": szDocName,
               "initialAuthor": "wilson.souza",
               "fields": [
               {
                  "fieldName": "Nome",
                  "type": "STRING",
                  "values": [szDescription]
               },
               {
                  "fieldName": "Status",
                  "type": "BOOLEAN",
                  "values": ["true"]
               }],
               "onlyAdminCanDelete": "true",
               "onlyAdminCanChangeAcl": "false",
               "userCanChangeProperties": "true",
               "userCanChangeAcl": "true",
               "userCanDelete": "false",
            };
            /**/
            Logger.log(pSuccess);
            /**/
            if (!pSuccess.Result)
            {
               var pKey =
               {
                  'SecurityCode': pSearch.SecurityCode
               };
               /**/
               var pSuccess = InsertDocument(pKey, pData);
               /* debug out */
               Logger.log(pSuccess);
               /* insert */
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t created!', pSearch.FileName));
               else
               {
                  /**/
                  Logger.log(Utilities.formatString(
                        'File ID %s inserted with successfully',
                        pSuccess.FileName));
               }
            }
            else
            {
               var pKey =
               {
                  'DocId': pSuccess.DocInfo.id,
                  'SecurityCode': pSearch.SecurityCode
               };
               var pSuccess = UpdateDocument(pKey, pData);
               /* debug out */
               Logger.log(pSuccess);
               /**/
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t updated!', pData.title));
               else
               {
                  Logger.log(Utilities.formatString(
                        'File %s ID %s inserted with successfully',
                        pSuccess.Handle.id, pSuccess.Handle.title));
               }
            }
         }
      }
      else
      {
         SpreadsheetApp.getUi().alert('Aba Coligada não esta selecionada!');
         throw "Spreadsheet Coligada isn´t actived!";
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
 * Method ImportContractData Parameters none Return none Created 2-11-2015 Author wilson.souza
 * Project WRR Group Description import data to ao-docs
 */
function ImportContractData()
{
   try
   {
      var bActived = SetActiveSheet('Contrato');

      if (bActived)
      {
         var pHandle = SpreadsheetApp.getActiveSheet();
         var pRange = pHandle.getDataRange();
         var pValues = pRange.getValues();
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.ContractId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 1000,
         };
         var pDocList = ListDocuments(pSearch);
         /**/
         for ( var nRow in pValues)
         {
            if (nRow == 0)
               continue;
            /**/
            if (pValues[nRow].length < 2)
               throw Utilities
                     .formatString('Spreadsheet with invalid columns!');

            /* check first line if is not empty */
            if (pValues[nRow][0].length == 0)
            {
               Logger.log(Utilities.formatString(
                     "Line %d is empty, jump next line!", nRow));
               continue;
            }
            /**/
            var szDocName = pValues[nRow][0];
            var szDescription = pValues[nRow][1];
            var pSuccess = FindDoc(pDocList, szDocName);
            var pData =
            {
               "libraryId": g_pHandle.Handle.LibraryId,
               "libraryName": g_pHandle.LibraryName,
               "className": g_pHandle.Contract.ClassName,
               "classId": g_pHandle.Handle.ContractId,
               "title": szDocName,
               "initialAuthor": "wilson.souza",
               "fields": [
               {
                  "fieldName": "Nome",
                  "type": "STRING",
                  "values": [szDescription]
               },
               {
                  "fieldName": "Status",
                  "type": "BOOLEAN",
                  "values": ["true"]
               }]
            };
            
            /**/
            //Logger.log(pSuccess);
            /**/
            if (!pSuccess.Result)
            {
               var pKey =
               {
                  'SecurityCode': pSearch.SecurityCode
               };
               var pSuccess = InsertDocument(pKey, pData);
               /* debug out */
               //Logger.log(pData);
               /* insert */
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t created!', pData.title));
               else
                  Logger.log(Utilities.formatString(
                        'File ID %s inserted with successfully',
                        pSuccess.Handle.title));
            }
            else
            {
               var pKey =
               {
                  'DocId': pSuccess.DocInfo.id,
                  'SecurityCode': pSearch.SecurityCode
               };
               var pSuccess = UpdateDocument(pKey, pData);
               /* debug out */
               //Logger.log(pSuccess);
               /**/
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t updated!', pData.title));
               else
                  Logger.log(Utilities.formatString(
                        'File %s ID %s inserted with successfully',
                        pSuccess.Handle.id, pSuccess.Handle.title));
            }
         }
      }
      else
      {
         SpreadsheetApp.getUi().alert('Aba Contrato não esta selecionada!');
         throw "Spreadsheet Contract isn´t actived!";
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
 * Method ImportSupplyData Parameters none Return none Created 2-11-2015 Author wilson.souza Project
 * WRR Group Description import data to ao-docs
 */
function ImportSupplyData()
{
   try
   {
      var bActived = SetActiveSheet('Fornecedor');

      if (bActived)
      {
         var pHandle = SpreadsheetApp.getActiveSheet();
         /**/
         var pRange = pHandle.getDataRange();
         var pValues = pRange.getValues();
         var pSearch =
         {
            'LibId': g_pHandle.Handle.LibraryId,
            'ClassId': g_pHandle.Handle.SupplyId,
            'SecurityCode': g_pHandle.SecurityCode,
            'PageSize': 10000,
         };
         var pDocList = ListDocuments(pSearch);
         /**/
         for ( var nRow in pValues)
         {
            if (nRow == 0)
               continue;
            /**/
            if (pValues[nRow].length < 3)
               throw Utilities
                     .formatString('Spreadsheet with invalid columns!');
            /* check first line */
            if (pValues[nRow][1].length == 0)
            {
               Logger.log(Utilities.formatString(
                     "Line %d is empty, jump next line!", nRow));
               continue;
            }
            /**/
            var szDocName = pValues[nRow][0];
            var szDoc = pValues[nRow][1];
            var szDescription = pValues[nRow][2];
            var pSuccess = FindDoc(pDocList, szDocName);
            var pData =
            {
               "libraryId": g_pHandle.Handle.LibraryId,
               "libraryName": g_pHandle.LibraryName,
               "className": g_pHandle.Supply.ClassName,
               "classId": g_pHandle.Handle.SupplyId,
               "title": szDocName,
               "initialAuthor": "wilson.souza",
               "fields": [
               {
                  "fieldName": "Nome",
                  "type": "STRING",
                  "values": [szDescription]
               },
               {
                  "fieldName": "Status",
                  "type": "BOOLEAN",
                  "values": ["true"]
               },
               {
                  "fieldName": "CPF-CNPJ",
                  "type": "STRING",
                  "values": [szDoc]
               }]
            };
            /**/
            //Logger.log(pSuccess);
            /**/
            if (!pSuccess.Result)
            {
               var pKey =
               {
                  'SecurityCode': pSearch.SecurityCode
               };
               /**/
               var pSuccess = InsertDocument(pKey, pData);
               /* debug out */
               //Logger.log(pData);
               /* insert */
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t created!', pData.title));
               else
                  Logger.log(Utilities.formatString(
                        'File %s ID %s inserted with successfully',
                        pSuccess.Handle.id, pSuccess.Handle.title));
            }
            else
            {
               var pKey =
               {
                  'DocId': pSuccess.DocInfo.id,
                  'SecurityCode': pSearch.SecurityCode
               };
               var pSuccess = UpdateDocument(pKey, pData);
               /* debug out */
               //Logger.log(pSuccess);
               /**/
               if (!pSuccess.Result)
                  Logger.log(Utilities.formatString(
                        'Document %s isn´t updated!', pData.title));
               else
                  Logger.log(Utilities.formatString(
                        'File %s ID %s inserted with successfully',
                        pSuccess.Handle.id, pSuccess.Handle.title));
            }
         }
      }
      else
      {
         SpreadsheetApp.getUi().alert('Aba Fornecedor não esta selecionada!');
         throw "Spreadsheet Supply isn´t actived!";
      }
   }
   catch(e)
   {
      Logger.log(e.message);
      SpreadsheetApp.getUi().alert(e.message);
      throw e;
   }
}
/* eof */