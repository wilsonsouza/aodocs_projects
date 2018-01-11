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
namespace adocs_csharp
{
   public class Library
   {
      public aodocs_csharp.Dedalus Handle { get; set; }
      public Library(aodocs_csharp.Dedalus pHandle)
      {
         this.Handle = pHandle;
      }
   }
};