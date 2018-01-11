/*
*  Created by Wilson.Souza
*  Copyright (c) 2014 Dedalus
*
*
*  Project BMR Medical 
*  Description fill property field with current user name
*  Last update 9-17-14 by wilson.souza -> fix property elaborador where changed by parameter field
*/
try
{
   getDocumentService().lockDocument(document).
   setField("elaborador", getPermissionService().getCurrentUser());
   debug(String.format("Elaborator name %s", getPermissionService().getCurrentUser()));
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}