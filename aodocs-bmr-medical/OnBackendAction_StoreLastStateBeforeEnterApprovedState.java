/*
*  Created by Wilson.Souza
*  Copyright (c) 2014 Dedalus
*
*
*  Project BMR Medical 
*  Description
*/
try
{
   //get and store current document state
   getDocumentService().lockDocument(document).setField("GetOldStateBefore_RunScript", document.getState());
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}