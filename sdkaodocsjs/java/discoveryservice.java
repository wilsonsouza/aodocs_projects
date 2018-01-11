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
function DiscoveryService()
{
   this.szHttpAddress = "https://api-dot-ao-docs.appspot.com/_ah/api/discovery/v1/apis/";
   
   /* 
    * first method retrieve the description of a particular version of an api 
    * call GET rest api
    * */
   this.GetRest = function(szAPIName, szVersion)
   {
      var szCmd = this.szHttpAddress + szAPIName + "/" + szVersion + "/rest"; 
      /* no source code */
      return {Result: "command without effect!"};
   };
   
   /*
    * retrieve the list of APIS supported at this end point
    */
   this.List = function(szAPIName, bPreferred)
   {
      var szCmd = this.szHttpAddress + "?name=" + szAPIName + "&preferred=" + bPreferred;
      /* no source code */
      return {Result: "command without effect!"};
   };
}