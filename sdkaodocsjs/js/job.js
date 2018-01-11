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
function Jobs(pAODocsClient, jobid)
{
   this.m_handle = pAODocsClient;
   this.m_url = ['https://api-dot-ao-docs.appspot.com/_ah/api/job/v1'].join( '' );
   this.m_jobid = jobid;
   this.m_securitycode = pAODocsClient.m_securitycode;
   this.m_result = { 'result': null, 'success': false };
}
//-----------------------------------------------------------------------------------------------//
Jobs.prototype.getJob = function(domain, fields)
{
   var url = [this.m_url, '/', this.m_jobid].join( '' );
   var params = { 'securityCode': this.m_securitycode };
   /**/
   $.ajax(
      {
      } );
}