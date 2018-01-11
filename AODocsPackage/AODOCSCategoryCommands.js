/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Support to AODocs Web Service
*
*  category
*/
var g_szLibraryVersion =
{
   "Version": 1.0,
   "Author": "Wilson.Souza",
   "Company": "Dedalus Prime",
   "Libray": "AODOCS Command",
   "Description":"Call AODocs by Web Service"
};

/*
* Method      DeleteCategoryDefinition
* Parameters  string szLiraryId, string szCategoryId, string szSecurityCode
* Return      
* Created     8-13-2014
* Author      wilson.souza
* Project     AODOCS
* Description delete category
*/
function DeleteCategoryDefinition(szLibraryId, szCategoryId, szSecurityCode)
{
   try
   {
      var szCmd = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s/categories/%s?securityCode=%s",
      szLibraryId, szCategoryId, szSecurityCode);
      var pResult = UrlFetchApp.fetch(szCmd);
      //
      if (pResult.getResponseCode() != 0xcc)
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
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
* Method      DeleteCategoryValue
* Parameters  string szLiraryId, string szCategoryId, string szCategoryValueId, string szDataValue
* Return      bool
* Created     8-5-2014
* Author      wilson.souza
* Project     AODOCS
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
      //
      if (pResult.getResponseCode() != 0xcc)
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
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
* Method      InsertCategoryValue
* Parameters  szSecurityCode, JSON data
* Return      bool
* Created     8-5-2014
* Author      wilson.souza
* Project     AODOCS
* Description 
*/
function InsertCategoryValue(szSecurityCode, pJSONData)
{
   try
   {
      var szCmd = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1?securityCode=%s", szSecurityCode);
      var pOptions =
      {
         "method": "put",
         "payload": pJSONData
      };
      var pResult = UrlFetchApp.fetch(szCmd, pOptions);
      var szCategoryId = null;
      //
      if (pResult.getResponseCode() != 0xc8)
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
      //
      return JSON.parse(pResult.getContentString());
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
* Created     8-13-2014
* Author      wilson.souza
* Project     AODOCS
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
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
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
* Method      ListCategoryDefinitions
* Parameters  string szLiraryId, string szDataValue
* Return      JSON data
* Created     8-13-2014
* Author      wilson.souza
* Project     AODOCS
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
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());

      //call lambda code
      return JSON.parse(pSuccess.getContentText());
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      ListChildCategoryValues
* Parameters  string szLiraryId, string szDataValue
* Return      JSON data
* Created     8-13-2014
* Author      wilson.souza
* Project     OADOCS
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
      //
      if (pSuccess.getResponseCode() != 0xc8)
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
      //
      return JSON.parse(pSuccess.getContentText());
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}
/*
* Method      ListHierarchyCategoryValues
* Parameters  szLiraryId, szCategoryId, szSecurityCode
* Return      JSON data
* Created     8-13-2014
* Author      wilson.souza
* Project     OADOCS
* Description list hierarchy category
*/
function ListHierarchyCategoryValues(szLibraryId, szCategoryId, szSecurityCode)
{
   try
   {
      var szCmd = Utilities.formatString(
      "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/libraries/%s/categories/%s/allvalueshierarchy?securityCode=%s",
      szLibraryId, szCategoryId, szSecurityCode);
      var pResult = UrlFetchApp.fetch(szCmd);
      //
      if (pResult.getResponseCode() != 0xc8)
         throw Utilities.formatString("IDD_RESPONSE_CODE_%d", pResult.getResponseCode());
      //
      //process result content
      return JSON.parse(pResult.getContentString());
   }
   catch (e)
   {
      Logger.log(e.message);
      throw e;
   }
}               