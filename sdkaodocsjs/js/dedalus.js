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
* SecurityCode: OlgqC9A3SwGL9nDVOF
* documentId  : Owdx1FVzgXMiHtrqtr
* libraryId   : OwY8Qd57asvk3wImtV
*/

//-----------------------------------------------------------------------------------------------//
function DedalusAODocs(hSecurityCodeId, hLibraryId)
{
   this.m_securitycode = hSecurityCodeId;
   this.m_libraryid = hLibraryId;
}
//-----------------------------------------------------------------------------------------------//
function CreateParams(params)
{
   var queue = [];
   var bFirst = true;

   for ( p in params )
   {
      if(!bFirst)
      {
         if ( params[p] != null )
         {
            queue.push( '&' );
         }
      }
      else
      {
         bFirst = false;
      }
      /**/
      if ( params[p] != null )
      {
         queue.push( encodeURIComponent( p ) );
         queue.push( '=' );
         queue.push( encodeURIComponent( params[p] ) );
      }
   }
   return queue.join( '' );
}
//-----------------------------------------------------------------------------------------------//
function ArrayToJson(pList)
{
   if ( pList == null )
   {
      throw('Invalid paramter received!');
   }
   /**/
   for ( var i = 0; i < pList.length; i++ )
   {
      s += "'" + pList[i][0] + "'" + ':' + pList[i][1];

      if ( pList[i][0] != pList[pList.length - 1][0] )
      {
         s += ',';
      }
   }
   /**/
   s += '}';
   return JSON.parse( s );
}


/* eof */