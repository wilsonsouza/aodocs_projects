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
* SecurityCode: OlgqC9A3SwGL9nDVOF
* documentId  : Owdx1FVzgXMiHtrqtr
* libraryId   : OwY8Qd57asvk3wImtV
* CategoryId  : OwY8RE4AtI71oI1FJQ
*/
document.writeln( "<script type='text/javascript' scr='dedalus.js'></script>" );
//-----------------------------------------------------------------------------------------------//
function Category(hHandle, hCategoryId)
{
   this.m_libraryid = hHandle.m_libraryid;
   this.m_securitycode = hHandle.m_securitycode;
   this.m_categoryid = hCategoryId;
   this.m_libraries = ['/libraries/', this.m_libraryid].join( '' );
   this.categories = ['/categories/', this.m_categoryid].join( '' );
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/category/v1'].join( '' );
   this.m_urlbase = [this.m_url, this.m_libraries, this.m_categoryid].join( '' );
}
//-----------------------------------------------------------------------------------------------//
Category.prototype.DeleteCategoryDefinition = function(Error)
{
   var szUrl = [this.m_urlbase].join( '' );
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
         url: szUrl + '?' + CreateParams(params),
         data:{},
         success: function(data)
         {
            result.result = data;
            result.success = !result.success;
         },
         error: function(e)
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
Category.prototype.DeleteCategoryValue = function (szCategoryValueId, Error)
{
   var szUrl = [this.m_urlbase, '/values/', szCategoryValueId].join( '' );
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
         url: szUrl + '?' + CreateParams(params),
         data:{},
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
Category.prototype.InsertCategoryDefinition = function (pRequestBody, Error)
{
   var szUrl = [this.m_url].join('');
   var pData =
      {
         'libraryId': this.m_pHandle.m_szLibraryId,
         'dynamicValues': pRequestBody.dynamicValues,
         'hidden': pRequestBody.hidden,
         'id': pRequestBody.id,
         'kind': pRequestBody.kind,
         'mandatory': pRequestBody.mandatory,
         'multiple': pRequestBody.multiple,
         'name': pRequestBody.name
      };
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
         url: szUrl + '?' + CreateParams(params),
         data: pData,
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
Category.prototype.InsertChildCategoryValue = function ( szCategoryValueName, szDomain,
   szParentCategoryValueId, szFields, Error )
{
   var szUrl = [this.m_urlbase, '/values'].join('');
   var params =
      {
         'parentCategoryValueId': szParentCategoryValueId,
         'fields': szFields,
         'categoryValueName': szCategoryValueName,
         'domain': szDomain,
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
         url: szUrl + '?' + CreateParams(params),
         data: {},
         success: function(data)
         {
            result.result = data;
            result.success = !result.success;
         },
         error: function(e)
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
Category.prototype.ListCategoryDefinitions = function(Error)
{
   var szUrl = [this.m_url, this.m_libraries].join('');
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
         url: szUrl + '?' + CreateParams(params),
         data: {},
         success: function(data)
         {
            result.result = data;
            result.success = !result.success;
         },
         error: function(e)
         {
            Error( JSON.parse( e.responseText ) );
         },
         dataType: 'json',
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
Category.prototype.ListChildCategoryValues = function(szParentCategoryValueId, Error)
{
   var szUrl = [this.m_handle.urlbase, '/values'].join('');
   var params =
      {
         'parentCategoryValueId': szParentCategoryValueId,
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
         url: szUrl + '?' + CreateParams(params),
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
Category.prototype.ListHierarchyCategoryValues = function(Error)
{
   var szUrl = [this.m_urlbase, '/allvalueshierarchy'].join('');
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
         url: szUrl + '?' + CreateParams(params),
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
Category.prototype.LoadCategoryDefinition = function(Error)
{
   var szUrl = [this.m_handle.urlbase].join('');
   var params = { 'securityCode': this.m_securitycode };
   var result = { 'result': null, 'success': false };
   /**/
   $.ajax(
      {
         type: 'GET',
         url: szUrl + '?' + CreateParams(params),
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
Category.prototype.PatchCategoryDefinition = function(jPatchBody, Error)
{
   var szUrl = [this.m_url].join( '' );
   var params = { 'securityCode': this.m_securitycode };
   var result = { 'result': null, 'success': false };
   /**/
   $.ajax(
      {
         type: 'PATCH',
         url: szUrl + '?' + CreateParams(params),
         data: jPatchBody,
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
Category.prototype.PatchCategoryValue = function(jpatchbody, Error)
{
   var szUrl = [this.m_urlbase, '/values', ].join('');
   var params = { 'securityCode': this.m_securitycode };
   var result = { 'result': null, 'success': false };
   /**/
   $.ajax(
      {
         type: 'PATCH',
         url: szUrl + '?' + CreateParams(params) ,
         data: jpatchbody,
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
Category.prototype.UpdateCategoryDefinition = function(jrequestbody, Error)
{
   var szUrl = [this.m_url].join('');
   var params = { 'securityCode': this.m_securitycode };
   var result = { 'result': null, 'success': false };
   /**/
   $.ajax(
      {
         type: 'POST',
         url: szUrl + '?' + CreateParams(params),
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
/*eof*/