/*
*
* AODocs SDK Version 1.0.1.alpha
* Copyright (c) 2014 Dedalus Prime
* 
* Created by Wilson.Souza
* Date 10-03-2014
* 
* Description: offer support for called to AODocs Web Service by java script
* 
*/

/*
 * class
 * initialize AODocs SDK
 * 
 */
function Initialize_AODocsSDK(szSecurityCodeId, szLibraryId)
{
   this.szSecurityCodeId = szSecurityCodeId;
   this.szLibraryId = szLibraryId;
   /* set security code to access aodocs web services */
   this.SetSecurityCode = function(szSecurityCodeId)
   {
      this.szSecurityCodeId = szSecurityCodeId;
   };
   /* set library identification handle */
   this.SetLibraryId = function(szLibraryId)
   {
      this.szLibraryId = szLibraryId;
   };
   /* get security code */
   this.GetSecurityCode = function()
   {
      return this.szSecurityCode;
   };
   /* get library id */
   this.GetLibraryId = function()
   {
      return this.szLibraryId;
   };
   /* get json combination of security code and library identification */
   this.GetJSONSecurityCodeCombinationWithLibraryId = function()
   {
      return {SecurityCodeId: this.szSecurityCode, LibraryId: this.szLibraryId};
   };
}
/* eof */