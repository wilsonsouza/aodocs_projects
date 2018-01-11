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
function Folder( hFolderId, pAODocs )
{
   this.m_handle =
      {
         'folder': hFolderId,
         'securitycode': pAODocs.securitycode,
         'library': pAODocs.library,
         'folders': '/folders/' + hFolderId
      };
   this.m_url = 'https://api-dot-ao-docs.appspot.com/_ah/api/folder/v1';
}
//-----------------------------------------------------------------------------------------------//
Folder.prototype.CheckAlreadyImportedFoldersRecursively = function ( Error )
{
   var url = [this.m_url, this.m_handle.folders, '/checkfolder'].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'POST',
         url: url + '?' + CreateParams(params),
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
Folder.prototype.CheckAlreadyImportedPermissions = function ( Error )
{
   var url = [this.m_url, this.m_handle.folders, '/checkPermissions'].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'POST',
         url: url + '?' + CreateParams(params),
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
Foler.prototype.DeleteFolderValue = function ( hfolderAodocsId, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders/', hfolderAodocsId].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'DELETE',
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
Folder.prototype.DeleteFolderValueByField = function ( hFolderAodocsId, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders/', hFolderAodocsId].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'DELETE',
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
Folder.prototype.GetFolder = function ( hFolderAodocsId, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders/', hFolderAodocsId].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
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
Folder.prototype.GetFolderByFileId = function ( hFileId, Error )
{
   var url = [this.m_url, '/files/', hFileId].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
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
Folder.prototype.InsertChildFolderValue = function ( szfolderValueName, hparentFolderAodocsId, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders' ].join( '' );
   var params =
      {
         'folderValueName': szfolderValueName,
         'parentFolderAodocsId': hparentFolderAodocsId,
         'securityCode': this.m_handle.securitycode
      };
   var result =
      {
         'result': null, 'success': false
      };

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
Folder.prototype.InsertChildFolderValueByFileId = function ( hfileId, szfolderValueName, Error )
{
   var url = [this.m_url, '/files/', hfileId ].join( '' );
   var params =
      {
         'folderValueName': szfolderValueName,
         'securityCode': this.m_handle.securitycode
      };
   var result = { 'result': null, 'success': false };

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
Folder.prototype.ListChildFolderValues = function ( hfolderAodocsId, bonlyDirectChildren, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders/children'].join( '' );
   var params =
      {
         'onlyDirectChildren': bonlyDirectChildren,
         'securityCode': this.m_handle.securitycode,
         'folderAodocsId': hfolderAodocsId
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
Folder.prototype.ListChildFolderValuesByFileId = function ( hfileId, bonlyDirectChildren, Error )
{
   var url = [this.m_url, '/files/', hfileId, '/children'].join( '' );
   var params =
      {
         'onlyDirectChildren': bonlyDirectChildren,
         'securityCode': this.m_handle.securitycode
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
Folder.prototype.ListHierarchyFolderValues = function ( Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders'].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
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
Folder.prototype.ListHierarchyFolderValuesByFileId = function ( hfileId, Error )
{
   var url = [this.m_url, '/files'].join( '' );
   var params =
      {
         'fileId': hfileId,
         'securityCode': this.m_handle.securitycode
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
Folder.prototype.LoadFolderDefinition = function ( Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
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
Folder.prototype.LoadFolderDrivePermissions = function ( hfolderId, Error )
{
   var url = [this.m_url, '/folders/', hfolderId, '/permissions'].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
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
Folder.prototype.MoveFolderByFileId = function ( hfileId, hparentId, Error )
{
   var url = [this.m_url, '/folders/', hfileId, '/move/', hparentId].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

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
Folder.prototype.PatchFolderDefinition = function ( jpathbody, Error )
{
   var url = [this.m_url].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'PATCH',
         url: url + '?' + CreateParams( params ),
         data: jpathbody,
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
Folder.prototype.UpdateFolderDefinition = function ( jrequestbody, Error )
{
   var url = [this.m_url].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'POST',
         url: url + '?' + CreateParams( params ),
         data: jrequestbody,
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
Folder.prototype.UpdateFolderDrivePermissions = function ( hfolderId, jrequestbody, Error )
{
   var url = [this.m_url, '/folders/', hfolderId, '/permissions'].join( '' );
   var params = { 'securityCode': this.m_handle.securitycode };
   var result = { 'result': null, 'success': false };

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
Folder.prototype.UpdateFolderValue = function ( hfolderAodocsId, szfolderValueName, Error )
{
   var url = [this.m_url, '/libraries/', this.m_handle.library, '/folders/', hfolderAodocsId].join( '' );
   var params =
      {
         'folderValueName':szfolderValueName,
         'securityCode': this.m_handle.securitycode
      };
   var result = { 'result': null, 'success': false };

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
Folder.prototype.UpdateFolderValueByFileId = function ( hfileId, szfolderValueName, Error )
{
   var url = [this.m_url, '/files/', hfileId].join( '' );
   var params =
      {
         'folderValueName': szfolderValueName,
         'securityCode': this.m_handle.securitycode
      };
   var result = { 'result': null, 'success': false };

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
