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
function View(hAODocsClient, hViewId)
{
   this.m_libraryId = hAODocsClient.m_libraryId;
   this.m_securityCode = hAODocsClient.m_securityCode;
   this.m_viewId = hViewId;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/view/v1'].join( '' );
   this.result = { 'result': null, 'success': false };
}
//-----------------------------------------------------------------------------------------------//
View.prototype.deleteView = function ( Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId, '/views/', this.m_viewId].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: 'DELETE',
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//
View.prototype.insertView = function ( jrequestBody, Error )
{
   var szUrl = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: 'PUT',
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//
View.prototype.listView = function ( Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//
View.prototype.loadView = function ( Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId, '/views/', this.m_viewId].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//
View.prototype.patchView = function ( jpatchBody, Error )
{
   var szUrl = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: 'PATCH',
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//
View.prototype.updateView = function ( jrequestBody, Error )
{
   var szUrl = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: 'POST',
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
   return bSuccess;
}
//-----------------------------------------------------------------------------------------------//