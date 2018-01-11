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
function Relation(hAODocsClient, hDocumentId, hRelationId)
{
   this.m_securityCode = hAODocsClient.security;
   this.m_libraryId = hAODocsClient.library;
   this.m_documentId = hDocumentId;
   this.m_relationId = hRelationId;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/relation/v1'].join( '' );
   this.m_side_related =
      {
         'both': 'FETCH_BOTH_SIDE',
         'from': 'FETCH_FROM_SIDE',
         'to': 'FETCH_TO_SIDE'
      };
   this.m_size_connected =
      {
         'from': 'FROM',
         'to': 'TO'
      };
}
//-----------------------------------------------------------------------------------------------//
Relation.prototype.getAllRelatedDocuments = function ( bCustomRender, Error )
{
   var url = [this.m_url, '/document/', this.m_documentId].join( '' );
   var params =
      {
         'customRender': bCustomRender ? 'true' : 'false',
         'securityCode': this.m_securityCode
      };
   var result = { 'result': null, 'success': false };
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
Relation.prototype.getConnectableDocuments = function ( szSide, szSearchQuery, Error )
{
   var url = [this.m_url, '/relations/', this.m_relationId,
      '/', szSide, '/documents/', this.m_documentId].join( '' );
   var params = { 'securityCode': this.m_securityCode };
   var result = { 'result': null, 'success': false };
   /**/
   if ( szSearchQuery != null )
   {
      var s = {'searchQuery': szSearchQuery, params};
      params = s;
   }
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
// nSide paramter
// 0  -  FETCH_BOTH_SIDE
// 1  -  FETCH_FROM_SIDE
// 2  -  FETCH_TO_SIDE
//
Relation.prototype.loadRelatedDocuments = function ( eSide, Error )
{
   var url = [this.m_url, '/libraries/', this.m_libraryId, '/relations/', this.m_relationId,
      '/documents/', this.m_documentId].join( '' );
   var params = { 'securityCode': this.m_securityCode };
   var result = { 'result': null, 'success': false };
   /**/
   if ( eSide != null )
   {
      var s = { 'side': eSide, params };
      params = s;
   }
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
Relation.prototype.patchRelations = function ( jrequestBody, Error )
{
   var url = [this.m_url, '/document/', this.m_documentId].join( '' );
   var params =
      {
         'securityCode': this.m_securityCode
      };
   var result = { 'result': null, 'success': false };
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