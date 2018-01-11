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
   public class Dedalus
   {
      public string SecurityCode { get; set; }
      public Dedalus(string szSecurityCode)
      {
         this.SecurityCode =  szSecurityCode;
      }
      public System.Collections.ArrayList Dedalus.GetLibraryList()
      {
         return new System.Collections.ArrayList();
      }
   }
};
/* eof */