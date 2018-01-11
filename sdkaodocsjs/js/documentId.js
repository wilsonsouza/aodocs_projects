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
function DocumentId( hdocumentId, hAODocsClient )
{
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/document/v1/', hdocumentId].join( '' );
   this.m_urlbase = ['https://api-dot-ao-docs.appspot.com/_ah/api/document/v1'].join( '' );
   this.m_documentid = hdocumentId;
   this.m_libraryid = hAODocsClient.m_libraryid;
   this.m_securitycode = hAODocsClient.m_securitycode;
   this.m_deletemode = { 'permanent': 'PERMANENT', 'trash': 'TRASH' };
}
//-----------------------------------------------------------------------------------------------//
DocumentId.prototype.checkIn = function ( szversionName, szversionDescription, Error )
{
   var url = [this.m_url, '/checkin'].join( '' );
   var params =
      {
         'versionName': szName,
         'securityCode': this.m_securitycode,
         'versionDescription': szversionDescription
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
DocumentId.prototype.checkOut = function ( Error )
{
   var url = [this.m_url, '/checkout'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.copy = function ( Error )
{
   var url = [this.m_url, '/copy'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.createVersion = function ( versionDescription, versionLabel, versionCreationDate, Error )
{
   var url = [this.m_url, '/version'].join( '' );
   var params =
      {
         'versionCreationDate': versionCreationDate,
         'versionLabel': versionLabel,
         'securityCode': this.m_securitycode,
         'versionDescription': versionDescription
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
DocumentId.prototype.delete = function ( eDeleteMode, Error )
{
   var url = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode,
         'deleteMode': eDeleteMode
      };
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
DocumentId.prototype.discardCheckOut = function ( Error )
{
   var url = [this.m_url, '/discardcheckout'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.getDraft = function ( hclassId, hfolderId, Error )
{
   var url = [this.m_urlbase].join( '' );
   var params =
      {
         'classId': hclassId,
         'libraryId': this.m_libraryid,
         'folderId': hfolderId,
         'securityCode': this.m_securitycode
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
DocumentId.prototype.insert = function ( jrequestBody, Error )
{
   var url = [this.m_urlbase].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result = { 'result': null, 'success': false };

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
DocumentId.prototype.listVersions = function ( Error )
{
   var url = [this.m_url, '/version'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.load = function ( Error )
{
   var url = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.loadVersion = function ( hversionId, Error )
{
   var url = [this.m_url, '/version/', hversionId].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.move = function ( hfolderAodocsId, Error )
{
   var url = [this.m_url, '/move/', hfolderAodocsId].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.patch = function ( jpatchBody, Error )
{
   var url = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result = { 'result': null, 'success': false };

   $.ajax(
      {
         type: 'PUT',
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
DocumentId.prototype.revertVersion = function ( hversionId, newVersionDescription, newVersionNumber, Error )
{
   var url = [this.m_url, '/version/', hversionId].join( '' );
   var params =
      {
         'newVersionNumber': newVersionNumber,
         'newVersionDescription': newVersionDescription,
         'securityCode': this.m_securitycode
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
DocumentId.prototype.untrash = function ( Error )
{
   var url = [this.m_url, '/untrash'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
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
DocumentId.prototype.update = function ( jrequestBody, Error )
{
   var url = [this.m_url].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      };
   var result = { 'result': null, 'success': false };

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
