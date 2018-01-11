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
namespace aodocs_csharp
{
   public class DiscoreryService
   {
      public Dedalus Handle { get; set; }
      public DiscoreryService(Dedalus pHandle)
      {
         this.Handle = pHandle;
      }
   }
}
/* eof */