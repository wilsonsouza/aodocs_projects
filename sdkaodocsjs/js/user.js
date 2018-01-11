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
function User(hAODocsClient, hUserId)
{
   this.m_handle = hAODocsClient;
   this.m_url = [];
   this.m_library = this.m_handle.m_library;
   this.m_securitycode = this.m_handle.m_securitycode;
   this.m_userid = hUserId;
   this.m_result = { 'result': null, 'success': false };
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetCombinedSuggestions = function ()
{
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetCurrentUser = function ()
{
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetGroupMembers = function ()
{
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetGroupSuggestions = function ()
{
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetUser = function ()
{
}
//-----------------------------------------------------------------------------------------------//
User.prototype.GetUserSuggestions = function ()
{
}
//-----------------------------------------------------------------------------------------------//
