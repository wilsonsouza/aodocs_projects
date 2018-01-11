/*
*
* AODocs SDK Version 1.0.1.alpha
* Copyright (c) 2014, 2015 Dedalus Prime
* 
* Created by Wilson.Souza
* Date 10-03-2014
* 
* Description: offer support for called to AODocs Web Service by java script
* 
*/
document.writeln( "<script type='text/javascript' scr='dedalus.js'></script>" );
//-----------------------------------------------------------------------------------------------//
function History( pAODocsCliente )
{
   this.m_handle = pAODocsCliente;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/history/v1/'];
}
//-----------------------------------------------------------------------------------------------//
History.prototype.ListDocumentHistoryByDocumentId = function ( hDocumentId, jRequestBody, Error )
{
   var szUrl = [this.m_url, '/documents/', hDocumentId].join( '' );
   var result = { 'result': null, 'success': false };
   var params =
      {
         'securityCode': this.m_handle.securitycode
      };
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( params ),
         data: jRequestBody,
         Success: function ( data )
         {
            result.result = data;
            result.success = !result.success;
         },
         Error: function ( e )
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
History.prototype.ListDocumentHistoryByLibraryId = function ( jRequestData, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_handle.library].join( '' );
   var params =
      {
         'securityCode': this.m_handle.securitycode
      };
   var result = { 'result': null, 'success': false };
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams(params),
         data: jRequestData,
         Success: function ( data )
         {
            result.result = data;
            result.success = !result.success;
         },
         Error: function ( e )
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
