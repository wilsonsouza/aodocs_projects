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
function Search(hAODocsClient, hclassId, hViewId)
{
   this.m_libraryId = hAODocsClient.m_libraryId;
   this.m_securityCode = hAODocsClient.m_securityCode;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/search/v1'].join( '' );
   this.result = { 'result': null, 'success': false, 'pagetoken': null };
   this.m_classId = hclassId;
   this.m_deleteOption = { 'all': 'ALL', 'deleted': 'DELETED', 'not_deleted': 'NOT_DELETED' };
   this.m_pageSize = 100;
   this.m_listdelete = this.m_deleteOption.not_deleted;
   this.m_requestbody = null;
   this.m_ViewId = hViewId;
}
//-----------------------------------------------------------------------------------------------//
Search.prototype.countDocuments = function ( jrequestBody, eDeleteOption, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId, '/count'].join( '' );
   var result = this.m_result;
   var p = new Array();
   /**/
   if ( this.m_classId != null )
   {
      p.push( ['classId', this.m_classId] );
   }
   /**/
   if(eDeleteOption != null)
   {
      p.push(['deleteOption', eDeleteOption]);
   }
   /**/
   p.push(['securityCode', this.m_securityCode]);
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( ArrayToJson( p ) ),
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
Search.prototype.listDocuments = function ( eDeleteOption, nPageSize, jrequestBody, Error )
{
   var szUrl = [this.m_url,'/libraries/', this.m_libraryId, '/list'].join( '' );
   var params =
         {
            'classId': this.m_classId,
            'pageSize': nPageSize == 0 && nPageSize > 100? this.m_pageSize: nPageSize,
            'securityCode': this.m_securitycode,
            'deleteOption': eDeleteOption == null? this.m_listdelete: eDeleteOption
         };
   var result = this.m_result;
   /**/
   this.m_pageSize = nPageSize;
   this.m_listdelete = eDeleteOption;
   this.m_requestbody = jrequestBody;
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( params ),
         data: jrequestBody,
         success: function ( data )
         {
            result.result = data;
            result.pagetoken = data.pageToken;
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
Search.prototype.nextListDocuments = function ( hResult, Error )
{
   var szUrl = [this.m_url,'/libraries/', this.m_libraryId, '/list'].join( '' );
   var params =
         {
            'pageToken': hResult.pagetoken,
            'classId': this.m_classId,
            'pageSize': this.m_pageSize,
            'securityCode': this.m_securitycode,
            'deleteOption': this.m_listdelete
         }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( params ),
         data: this.m_requestbody,
         success: function ( data )
         {
            result.result = data;
            result.pagetoken = data.pageToken;
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
/*
jparams = 
{
   'deleteOption': <value>,
   'domain': <value>,
   'limit':<value>,
   'offset': <value>,
   'searchAttachments': <value>,
   'searchQuery': <value>
   };
*/
Search.prototype.searchDocuments = function ( jparams, jrequestBody, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId, '/search'].join( '' );
   var result = this.m_result;
   var p = new Array();
   /**/
   p.push( ['classId', this.m_classId] );
   /**/
   if ( jparams != null )
   {
      try
      {
         if ( jparams.deleteOption != null )
         {
            p.push( [ 'deleteOption', jparams.deleteOption ] );
         }
         /**/
         if(jparams.domain != null)
         {
            p.push( [ 'domain', jparams.domain ] );
         }
         /**/
         if(jparams.limit != null)
         {
            p.push( [ 'limit', jparams.limit ] );
         }
         /**/
         if(jparams.offset != null)
         {
            p.push( { 'offset': jparams.offset } );
         }
         /**/
         if(jparams.searchInAttachments != null)
         {
            p.push(['searchInAttachments', jparams.searchInAttachments]);
         }
         /**/
         if(jparams.searchQuery != null)
         {
            p.push( [ 'searchQuery', jparams.searchQuery ] );
         }
      }
      catch ( e )
      {
         throw e;
      }
   }
   /**/
   p.push( ['securityCode', this.m_securityCode] );
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( ArrayToJson( p ) ),
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
Search.prototype.searchDocumentsByView = function ( szDomain, nLimit, nOffset, szSearchQuery, jrequestBody, Error )
{
   var szUrl = [this.m_url, '/libraries/', this.m_libraryId,'/views/', this.m_ViewId] .join( '' );
   var result = this.m_result;
   var p = new Array();
   /**/
   if ( nOffset != null )
   {
      p.push( ['offset', nOffset] );
   }
   /**/
   if ( szDomain != null )
   {
      p.push(['domain', szDomain]);
   }
   /**/
   if ( szSearchQuery != null )
   {
      p.push( ['searchQuery', szSearchQuery] );
   }
   /**/
   if ( nLimit != null )
   {
      p.push( ['limit', nLimit] );
   }
   /**/
   p.push( ['securityCode', this.m_securityCode] );
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( ArrayToJson( p ) ),
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
// the parameters must be formatted:
// json = {'domain': <value>, 'minimumDocumentNumber': <value>, 'searchQuery': <value>}
//
Search.prototype.searchDocumentsInDomain = function ( szDomain, nMinimumDocumentNumber, szSearchQuery, jrequestBody, Error )
{
   var szUrl = [this.m_url,'/domain'].join( '' );
   var result = this.m_result;
   var p = new Array();
   /**/
   if ( szDomain != null )
   {
      p.push( ['domain', szDomain] );
   }
   /**/
   if ( nMinimumDocumentNumber != 0 )
   {
      p.push( ['minimumDocumentNumber', nMinimumDocumentNumber] );
   }
   /**/
   if ( szSearchQuery != null )
   {
      p.push( ['searchQuery', szSearchQuery] );
   }
   /**/
   p.push( ['securityCode', this.m_securityCode] );
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams( ArrayToJson( p ) ),
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