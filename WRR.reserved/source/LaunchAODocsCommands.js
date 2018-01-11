/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  make called of some commands of AODocs by web service
*
*  this library is not complete yet
*/
var g_szDefaultSecurityCodeId = "OlgqC9A3SwGL9nDVOF"; //tester methods
/*
* Method      ListChildCategoryValues
* Parameters  string szLiraryId, string szDataValue
* Return      Array with twice dimision (first propertyname second value)
* Created     5-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description list all child category
*/
function Test_GetLastVersion()
{
   var szVersion = GetLastVersion("OmBTZro1tICBvQ7p0Y", g_szDefaultSecurityCodeId);
   Logger.log(szVersion);
}
/*
* Method      ListChildCategoryValues
* Parameters  string szLiraryId, string szDataValue
* Return      Array with twice dimision (first propertyname second value)
* Created     5-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description list all child category
*/
function ListChildCategoryValues(szLibraryId, szCategoryId, szParentCategoryValue, szSecurityCode)
{
   try
   {
      var szUrl = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s/categories/%s/values?securityCode=%s",
       szLibraryId, szCategoryId, szSecurityCode);
      var pSuccess = UrlFetchApp.fetch(szUrl);
      var pAOut = new Array();
      //
      if (pSuccess.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 200!", pSuccess.getResponseCode());
      //
      JSON.parse(pSuccess.getContentText(), function (szProperty, szValue)
      {
         if (szProperty == "name")
         {
            pAOut.push(new Array());
            pAOut[pAOut.length - 1][0] = szValue;
         }

         if (szProperty == "value")
            pAOut[pAOut.length - 1][1] = szValue;
      });
      //
      return pAOut;
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      ListCategoryDefinitions
* Parameters  string szLiraryId, string szDataValue
* Return      Array with twice dimision (first propertyname second value)
* Created     5-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description list all category (tester LibraryId=OitNtfZ8EJfxeKdbQW SecurityCode=OlgqC9A3SwGL9nDVOF)
*/
function ListCategoryDefinitions(szLibraryId, szSecurityCode)
{
   try
   {
      var szUrl = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s?securityCode=%s",
      szLibraryId, szSecurityCode);
      var pSuccess = UrlFetchApp.fetch(szUrl);
      var pAOut = new Array();
      //
      if (pSuccess.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 200!", pSuccess.getResponseCode());

      //call lambda code
      JSON.parse(pSuccess.getContentText(), function (szProperty, szValue)
      {
         if (szProperty == "name")
         {
            pAOut.push(new Array());
            pAOut[pAOut.length - 1][0] = szValue;
         }

         if (szProperty == "value")
            pAOut[pAOut.length - 1][1] = szValue;
      });
      //
      return pAOut;
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      DeleteCategoryValue
* Parameters  string szLiraryId, string szCategoryId, string szCategoryValueId, string szDataValue
* Return      bool
* Created     5-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description remove child category by identification code is necessary always pass security code
*/
function DeleteCategoryValue(szLibraryId, szCategoryId, szCategoryValueId, szSecurityCode)
{
   try
   {
      var szUrl = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s/categories/%s/values/%s?securityCode=%s",
      szLibraryId, szCategoryId, szCategoryValueId, szSecurityCode);
      var pAOptions =
      {
         "method": "delete",
         "payload": ""
      };
      var pResult = UrlFetchApp.fetch(szUrl, pAOptions);
      return true;
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      InsertChildCategoryValue
* Parameters  string szLiraryId, string szCategoryId, string szCategoryValueName, string szSecurityCodeId, string szDataValue
* Return      bool
* Created     5-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description insert child category on root categoty selected by cateroty identification code
*/
function InsertChildCategoryValue(szLibraryId, szCategoryId, szCategoryValueName, szSecurityCode, szDataValue)
{
   try
   {
      var szUrl = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s/categories/%s/values?categoryValueName=%s&securityCode=%s",
      szLibraryId, szCategoryId, szDataValue, szSecurityCode);
      var pAPayLoad =
      {
         "name": szDataValue,
         "value": szDataValue
      };
      var pAOptions =
      {
         "method": "PUT",
         "payload": pAPayLoad
      };
      var pSuccess = UrlFetchApp.fetch(szUrl, pAOptions);
      //
      if (pSuccess.getResponseCode() != 0xc8)
         throw Utilities.formatString("HTTP result code %d different of 200!", pSuccess.getResponseCode());
      //
      return true;
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      GetLastVersion
* Parameters  string szDocumentId, string szSecurityCodeId
* Return      string (example 1.0)
* Created     6-8-2014
* Author      wilson.souza
* Project     BMR Medical
* Description get last version created in document
*/
function GetLastVersion(szDocumentId, szSecurityCodeId)
{
   try
   {
      var szUrl = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/document/v1/%s/version?securityCode=%s", szDocumentId, szSecurityCodeId);
      var pSuccess = UrlFetchApp.fetch(szUrl);
      var szVersionId = "1.0";
      //
      JSON.parse(pSuccess.getContentText(), function (szName, szValue)
      {
         //Logger.log(Utilities.formatString("%s = %s\n", szName, szValue));

         if (szName == "versionName")
            szVersionId = szValue;
      });
      return szVersionId;
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/* eof */
