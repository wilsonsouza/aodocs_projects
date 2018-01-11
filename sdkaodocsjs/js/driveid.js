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
function DriveId(hdriveId, hAODocsClient)
{
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/document/v1'].join( '' );
   this.m_urlcomposite = [this.m_url, '/drive/', hdriveId].join( '' );
   this.m_libraryid = hAODocsClient.m_libraryid;
   this.m_securitycode = hAODocsClient.m_securitycode;
   this.m_driveid = hdriveId;
   this.m_deleteMode = {'permanent':'PERMANENT', 'trash': 'TRASH'};
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.checkFile = function ( Error )
{
   var url = [this.m_urlcomposite, '/check'].join( '' );
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
   return result;
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.checkInDocument = function ( versionName, Error )
{
   var url = [this.m_urlcomposite, '/checkin' ].join( '' );
   var params =
         {
            'versionName': versionName,
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
DriveId.prototype.checkOutDocument = function ( Error )
{
   var url = [this.m_urlcomposite, '/checkout'].join( '' );
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
DriveId.prototype.copyDocument = function ( Error )
{
   var url = [this.m_urlcomposite, '/copy'].join( '' );
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
   return result;
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.createNativeFile = function ( hfolderId, sztitle, sztype, Error )
{
   var url = [this.m_url, '/drive/native'].join( '' );
   var params =
         {
            'folderId': hfolderId,
            'title': sztitle,
            'type': sztype,
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
   return result;
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.createVersion = function ( szVersionDescription, szVersionLabel, Error )
{
   var url = [this.m_urlcomposite, '/version'].join( '' );
   var result =
      {
         'result': null,
         'success': false
      };
   var p = new Array();
   /**/
   if ( szVersionLabel != null )
   {
      p.push( ['versionLabel', szVersionLabel] );
   }
   /**/
   p.push( ['securityCode', this.m_securitycode] );

   if ( szVersionDescription != null )
   {
      p.push( ['versionDescription', szVersionDescription] );
   }
   /**/
   $.ajax(
      {
         type: 'GET',
         url: szUrl + '?' + CreateParams( ArrayToJson( p ) ),
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
DriveId.prototype.createWebDavLink = function ( Error )
{
   var url = [this.m_urlcomposite, '/webdav'].join( '' );
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
DriveId.prototype.deleteDocument = function ( bDeleteMode, Error )
{
   var url = [this.m_urlcomposite].join( '' );
   var result =
      {
         'result': null,
         'success': false
      };
   /**/
   var p = new Array();

   p.push( ['securityCode', this.m_securitycode] );
   p.push( ['deleteMode', bDeleteMode ? this.m_deleteMode.permanent : this.m_deleteMode.trash] );

   $.ajax(
      {
         type: 'DELETE',
         url: szUrl + '?' + CreateParams( ArrayOfJson( p ) ),
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
DriveId.prototype.discardCheckOutDocument = function ( Error )
{
   var url = [this.m_urlcomposite, '/discardcheckout'].join( '' );
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
DriveId.prototype.getDraftDocument = function ( hClassId, hFolderId, Error )
{
   var url = [this.m_url].join( '' );
   var params =
         {
            'classId': hClassId,
            'libraryId': this.m_libraryid,
            'folderId': hFolderId,
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
DriveId.prototype.insertDocument = function ( jresquestBody, Error )
{
   var url = [this.m_url].join( '' );
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
         type: 'PUT',
         url: szUrl + '?' + CreateParams( params ),
         data: jresquestBody,
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
DriveId.prototype.listVersions = function ( Error )
{
   var url = [this.m_urlcomposite, '/version'].join( '' );
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
DriveId.prototype.loadDocument = function ( Error )
{
   var url = [this.m_urlcomposite].join( '' );
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
DriveId.prototype.loadDocumentPermissions = function ( Error )
{
   var url = [this.m_urlcomposite, '/permission'].join( '' );
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
DriveId.prototype.loadVersion = function ( hVersionId, Error )
{
   var url = [this.m_urlcomposite, '/version', hVersionId].join( '' );
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
DriveId.prototype.moveDocument = function ( hParentId, Error )
{
   var url = [this.m_urlcomposite, '/move/', hParentId].join( '' );
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
   return result;
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.patchDocument = function ( jpatchBody, Error )
{
   var url = [this.m_urlcomposite].join( '' );
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
DriveId.prototype.revertVersion = function ( hVersionId, szNewVersionNumber, szNewVersionDescription, Error )
{
   var url = [this.m_urlcomposite, '/version/', hVersionId].join( '' );
   var params =
         {
            'newVersionNumber': szNewVersionNumber,
            'newVersionDescription': szNewVersionDescription,
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
   return result;
}
//-----------------------------------------------------------------------------------------------//
DriveId.prototype.untrash = function ( Error )
{
   var url = [this.m_urlcomposite, '/untrash'].join( '' );
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
DriveId.prototype.updateDocument = function ( jrequestBody, Error )
{
   var url = [this.m_urlcomposite].join( '' );
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
         type: 'POST',
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
DriveId.prototype.updateDocumentPermissions = function ( jrequestBody, Error )
{
   var url = [this.m_urlcomposite, '/permission'].join( '' );
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
         type: 'POST',
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
