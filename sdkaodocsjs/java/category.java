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
function Category()
{
   this.szHttpAddress = "https://api-dot-ao-docs.appspot.com/_ah/api/category/v1/";
   this.szHttpLibrary = "libraries/";
   this.szHttpCategory = "/categories/";
   this.szHttpSecurityCode = "?securityCode=";
   
   /* methods */
   this.DeleteDefinition = function(szLibraryId, szCategoryId, szSecurityCode)
   {
     var szCmd = this.szHttpAddress + this.szHttpLibrary + 
     szLibraryId + this.szHttpCategory + szCategoryId + this.szHttpSecurityCode + szSecurityCode;
     /**/
     var pSuccess = new Ajax.Request(szCmd,
     {
        method: "get",
        onSuccess: function(pHttp)
        {
           return pHttp;
        },
        onFailure: function()
        {
           return null;
        }
     });
   };
}