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
function DriveImport(hImportId, hAODocsClient)
{
   this.m_handle =
      {
         'library': hAODocsClient.library,
         'security': hAODocsClient.security,
         'import': hImportId
      };
   this.urlcomposite = ['https://api-dot-ao-docs.appspot.com/_ah/api/driveImport/v1/imports/', hImportId].join( '' );
   this.url = ['https://api-dot-ao-docs.appspot.com/_ah/api/driveImport/v1'].join( '' );
}
//-----------------------------------------------------------------------------------------------//
DriveImport.prototype.getImport = function ( Error )
{
   var url = [this.urlcomposite].join( '' );
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
DriveImport.prototype.getImportFiles = function ( Error )
{
   var url = [this.urlcomposite, '/files'].join( '' );
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
DriveImport.prototype.getImports = function ( Error )
{
   var url = [this.url, '/imports'].join( '' );
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
DriveImport.prototype.importFileOrFolder = function ( hparentFolderid, hfileId, Error )
{
   var url = [this.url, '/imports/folders/', hparentFolderid, hfileId].join( '' );
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
DriveImport.prototype.importFileOrFolders = function ( hParentFolderId, jfields, Error )
{
   var url = [this.url, '/imports/batch/folders/', hParentFolderId].join( '' );
   var params =
      {
         'fields': jfields.fields,
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

