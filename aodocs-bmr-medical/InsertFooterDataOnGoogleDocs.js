/*
*
*  Copyright (C) 2014 Dedalus Prime
*  Created by Wilson.Souza
*
*  for support BMR Medical AODocs project
*
*  Last update 7-17-2014 by wilson.souza
*  Last update 7-23-2014 by wilson.souza -> added field DocState on Request object
*  Last update 8-06-2014 by wilson.souza -> added new methods to support AODocs web service
*  Last update 8-20-2014 by eduardo.junior -> verify the content of variable pItems
*  Last update 8-21-2014 by wilson.souza -> changed called of Alignment class to document
*  Last update 8-21-2014 by wilson.souza -> changed called of Alignment class to document
*  Last update 9-03-2014 by wilson.souza -> were modified formation of text of footer
*/
function doPost(Request)
{
   var strResult = "IDD_ERROR";

   try
   {
      var pDoc = DocumentApp.openById(Request.parameters.DocID);
      var szVersao = Request.parameters.Versao;
      var szElaborador = Request.parameters.Elaborador;
      var szAprovador = Request.parameters.Aprovador;
      var lDataAprovacao = Request.parameters.DataAprovacao;
      var lKey = "PLOJV6Yw2xmEyGiXml9YPvy3vNk99HHXXX7NjHWahE7wIeqrBtKoxOKcQEdFuN7";
      var szDocState = Request.parameters.DocState;
      /*
      * Log register
      */
      Logger.log("Document ID %s", Request.parameters.DocID);

      /*
      * compare key
      */
      if (Request.parameters.Key != lKey)
      {
         Logger.log("Invalid KEY %s!", Request.parameters.Key);
         throw "IDD_INVALID_KEY";
      }

      /* 
      * check document
      */
      if (pDoc == null)
      {
         Logger.log("Document ID %s not opened!", Request.parameters.DocID);
         throw "IDD_DOC_IS_NULL";
      }

      /* 
      * check if already exists a footer on document 
      */
      var pFoot = pDoc.getFooter();

      try
      {
        if(pFoot == null)
          pFoot = pDoc.addFooter();
      }
      catch (pEx)
      {
         Logger.log("Footer class exception %s", pEx.message);
         throw "IDD_FOOTER_EXCEPTION";
      }
      //
      //work with MainDocId parameter
      try
      {
        szVersao = GetLastVersion(Request.parameters.MainDocId, Request.parameters.SecurityCodeId);
      }
      catch(e)
      {
        Logger.log(e.message);
        throw "IDD_INVALID_MAIN_DOCID";
      }
      
      /* 
      * version, manufacturers, approve, date of approved, state
      */
      var szFmt = "Versão: "     + szVersao       + "| " +
        		  "Elaborador: " + szElaborador   + "| " +
                  "Aprovador: "  + szAprovador    + "| " +
                  "Data: "       + lDataAprovacao + "| " +
                  "Estado: "     + szDocState;
      var bAlreadyExists = false;
      var pItems = null;
      
      //check if already exists
      for(var i in pFoot.getParagraphs())
      {
        var p = pFoot.getParagraphs()[i];
        var s = p.getText();
        
        if(s.indexOf("Elaborador") != -1)
        {
          p.setText(szFmt);
          bAlreadyExists = true;
          pItems = p;
          break;
        }
      }
      // footer is not exists
      if(!bAlreadyExists)
      {
        for(var i in pFoot.getParagraphs())
        {
          var p = pFoot.getParagraphs()[i];
          
          if(p.getText().length == 0)
          {
            p.setText(szFmt);
            pItems = p;
            break;
          }
        }
      }
     
     //this new condition added were cause of generate an exception
     //but still are know why!!!
     if(null == pItems) 
     {
       pItems = pFoot.appendParagraph(szFmt);
     }
     
      /* get alignment object of main document */
      pItems.setAlignment(DocumentApp.HorizontalAlignment.CENTER);
      
      /* set foot style */
      var pAStyles = {};
      pAStyles[DocumentApp.Attribute.FONT_SIZE] = 8;
      pAStyles[DocumentApp.Attribute.SPACING_BEFORE] = 0.0;
      pAStyles[DocumentApp.Attribute.SPACING_AFTER] = 0.0;
      pAStyles[DocumentApp.Attribute.LINE_SPACING] = 1.0;
      pItems.setAttributes(pAStyles);

      /*
       * process finished with successfully
      */
      Logger.log("Processo finalizado com sucesso!");
      strResult = "IDD_OK:" + szVersao;
   }
   catch (pEx)
   {
      strResult = pEx.message;
   }
   return ContentService.createTextOutput(strResult).setMimeType(ContentService.MimeType.TEXT);
}
