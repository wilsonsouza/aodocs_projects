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
//-----------------------------------------------------------------------------------------------//
function Library( hAODocsClient, szLibName, hLibTemplateId, szSourceLibId )
{
   this.m_libraryid = hAODocsClient.library;
   this.m_securitycode = hAODocsClient.security;
   this.m_libname = szLibName;
   this.m_libtemplateid = hLibTemplateId;
   this.m_sourcelibid = szSourceLibId;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/library/v1'].join( '' );
}
//-----------------------------------------------------------------------------------------------//
Library.prototype.createLibrary = function ( szLibName, hLibTemplateId, szSourceLibId, Error )
{
   var url = [this.url, '/library/', szLibName].join( '' );
   var params =
         {
            'libraryTemplateId': hLibTemplateId,
            'sourceLibraryId': szSourceLibId,
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'POST',
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
Library.prototype.createLibraryFromTemplate = function ( hLibraryTemplateId, szLibraryName, Error )
{
   var url = [this.url, '/library/', hLibraryTemplateId, '/name/', szLibraryName].join( '' );
   var params =
      {
         'securityCode': this.m_handle.security
      };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PUT',
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
Library.prototype.getLibrary = function ( Error )
{
   var url = [this.url, '/', this.m_handle.library].join( '' );
   var params =
         {
            'securityCode': this.m_handle.security
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
Library.prototype.getStorageAccountEmail = function ( Error )
{
   var url = ['https://api-dot-ao-docs.appspot.com/_ah/api/storageadmin/v1'].join( '' );
   var params =
         {
            'libraryId': this.m_handle.library,
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PUT',
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
Library.prototype.listLibraries = function ( bExplicitAccessOnly, Error )
{
   var url = [this.url].join( '' );
   var params =
         {
            'explicitAccessOnly': bExplicitAccessOnly? 'true': 'false',
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PUT',
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
Library.prototype.listPermissions = function ( Error )
{
   var url = [this.url, '/', this.m_handle.library,'/permission'].join( '' );
   var params =
         {
            'securityCode': this.m_handle.security
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
Library.prototype.listPlainLibraries = function ( bExplicitAccessOnly, Error )
{
   var url = [this.url, '/plain'].join( '' );
   var params =
         {
            'explicitAccessOnly': bExplicitAccessOnly? 'true': false,
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PUT',
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
Library.prototype.patchLibrary = function ( hId, jpatchBody, Error )
{
   var url = [this.url, '/', hId].join( '' );
   var params =
         {
            'securityCode': this.m_handle.security
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
         url: url + '?' + CreateParams( params ),
         data: jpatchBody,
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
Library.prototype.updateLibrary = function ( hId, jrequestBody, Error )
{
   var url = [this.url, '/', hId].join( '' );
   var params =
         {
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'PUT',
         url: url + '?' + CreateParams( params ),
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
Library.prototype.updatePermissions = function ( jrequestBody, Error )
{
   var url = [this.url, '/', this.m_handle.library, '/permission'].join( '' );
   var params =
         {
            'securityCode': this.m_handle.security
         };
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   $.ajax(
      {
         type: 'POST',
         url: url + '?' + CreateParams( params ),
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
