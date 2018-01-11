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
document.writeln( "<script type='text/javascript' scr='dedalus.js'></script>" );
//-----------------------------------------------------------------------------------------------//
function DiscoveryService( hAPI, szVersion )
{
   this.m_api = hAPI;
   this.m_version = szVersion;
   this.m_url = 'https://api-dot-ao-docs.appspot.com/_ah/api/discovery/v1';
}
//-----------------------------------------------------------------------------------------------//
DiscoveryService.prototype.getRest = function(Error)
{
   var url = [this.m_url, '/apis/', this.m_api, '/', this.m_version, '/rest'].join( '' );
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'GET',
         url: url,
         data: {},
         success: function ( data )
         {
            result.result = data;
            result.success = !result.success;
         },
         error: function ( e )
         {
            result.result = e;
            Error( JSON.parse( e.responseText ) );
         },
         dataType: 'json',
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
DiscoveryService.prototype.list = function ( szName, bpreferred, Error )
{
   var url = [this.m_url, '/apis'].join( '' );
   var params =
      {
         'name': szName,
         'preferred': bpreferred
      };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'GET',
         url: url + '?' + CreateParams( params ),
         data: {},
         success: function ( data )
         {
            result.result = data;
            result.success = !result.success;
         },
         error: function ( e )
         {
            result.result = e;
            Error( JSON.parse( e.responseText ) );
         },
         dataType: 'json',
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
