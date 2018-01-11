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
function DocumentType( hdocumentTypeId, hAOdocsClient )
{
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/documentType/v1'].join( '' );
   this.m_documenttypeid = hdocumentTypeId;
   this.m_libraryid = hAOdocsClient.m_libraryid;
   this.m_securitycode = hAOdocsClient.m_securitycode;
}
//-----------------------------------------------------------------------------------------------//
DocumentType.prototype.list = function ( Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryid].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'GET',
         url: szUrl + '?' + CreateParams( params ),
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
DocumentType.prototype.load = function ( Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryid,
      '/documentTypes/', this.m_documenttypeid].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'GET',
         url: szUrl + '?' + CreateParams( params ),
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
DocumentType.prototype.patch = function ( jpathBody, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryid,
   '/documentTypes/', this.m_documenttypeid].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PATCH',
         url: szUrl + '?' + CreateParams( params ),
         data: jpathBody,
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
DocumentType.prototype.update = function ( jrequestBody, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryid,
      '/documentTypes/', this.m_documenttypeid].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'DELETE',
         url: szUrl + '?' + CreateParams( params ),
         data: jrequestBody,
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
