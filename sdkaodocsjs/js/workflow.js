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
*/
document.writeln( "<script type='text/javascript' scr='dedalus.js'></script>" );
//-----------------------------------------------------------------------------------------------//
function Workflow(pAODocsClient, hdocumentId, hWorkflowId)
{
   /* store aodocs base class */
   this.m_libraryId = pAODocsClient.m_libraryid;
   this.m_securitycode = pAODocsClient.m_securitycode;
   this.m_szUrl = ["https://api-dot-ao-docs.appspot.com/_ah/api/workflow/v1"];
   this.m_documentId = documentId;
   this.m_workflowId = hWorkflowId;
   this.m_result = { 'result': null, 'success': false };
}
//-----------------------------------------------------------------------------------------------//
Workflow.prototype.ChangeDocumentState = function ( hstateId, szMessage, Error )
{
   var szUrl = [this.m_szUrl, "/documents/",
      this.m_documentId, "/states/", hstateId,  "/message/", szMessage].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: "POST",
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
         dataType: "json",
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
Workflow.prototype.ExecuteDocumentTransition = function ( transitionId, message, Error )
{
   var szUrl = [this.m_szUrl, "/documents/", this.m_documentId,
      "/transitions/", transitionId, "/message/", message].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: "POST",
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
         dataType: "json",
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
Workflow.prototype.ListAvailableDocumentTransitions = function ( Error )
{
   var szUrl = [this.m_szUrl, "/documents/", this.m_documentId, '/transitions'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: "GET",
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
         dataType: "json",
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
Workflow.prototype.ListDocumentsToValidate = function ( Error )
{
   var szUrl = [this.m_szUrl, "/libraries/", this.m_libraryId, '/workbasket'].join( '' );
   var params =
      {
         'securityCode': this.m_securitycode
      }
   var result = this.m_result;
   /**/
   $.ajax(
      {
         type: "GET",
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
         dataType: "json",
         async: true
      } );
   return result;
}
//-----------------------------------------------------------------------------------------------//
Workflow.prototype.ListWorkflowStateTransitions = function ( hStateId, Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId,
      '/workflows/', this.m_workflowId, '/states/', hStateId, '/transitions'].join( '' );
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
Workflow.prototype.ListWorkflowStates = function ( Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId,
      '/workflows/', this.m_workflowId, '/states'].join( '' );
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
Workflow.prototype.ListWorkflows = function ( Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId].join( '' );
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
Workflow.prototype.LoadWorkflow = function ( Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId, '/workflows/', this.m_workflowId].join( '' );
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
Workflow.prototype.LoadWorkflowState = function ( hstateId, Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId,
      '/workflows/', this.m_workflowId, '/states/', hstateId].join( '' );
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
Workflow.prototype.LoadWorkflowStateTransition = function ( hstateId, htransitionId, Error )
{
   var szUrl = [this.m_szUrl, '/libraries/', this.m_libraryId, '/workflows/', this.m_workflowId,
   '/states/',hstateId, '/transtions/', htransitionId].join( '' );
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

