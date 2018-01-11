/*
*  Created by Wilson.Souza
*  Copyright (c) 2014 Dedalus
*  Supervised by Eduardo Bortoluzzi Jr
*  
*  Source created 7-24-2014
*  Last update 7-25-2014
*  Last update 9-17-2014 by wilson.souza -> fix problem when recuse document already attachment.
*  Last update 11-14-2014 by wilson.souza -> fix failure when create other kind  of document different of template attached.
*  Last update 11-19-2014 by wilson.souza -> changed all hardcode names by properties and parameters
*  Last update 02-03-2015 by wilson.souza -> fix parameters removed hardcode
*/
/*import com.altirnao.aodocs.script.*;
import com.altirnao.aodocs.custom.action.*;
import com.altirnao.aodocs.custom.Attachment;
import com.altirnao.aodocs.custom.Document;
import com.altirnao.aodocs.custom.DocumentService;
import com.altirnao.aodocs.custom.ReadableDocument;
import com.altirnao.aodocs.custom.sample.*;
import com.google.common.base.Optional;
import java.util.*;
import com.altirnao.aodocs.common.*;
import com.altirnao.aodocs.custom.*;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.FetchOptions;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPHeader;
import java.text.SimpleDateFormat;
 */
class URLEncoder 
{
   private String encode(String strData, String strReserved) 
   {
      StringBuffer pResult = new StringBuffer();
      String strSpecialChars = " &+?/=%";

      for (int i = 0; i < strData.length(); i++) 
      {
         char c = strData.charAt(i);

         if (strSpecialChars.indexOf(c) >= 0) 
         {
            byte[] bytes = ("" + c).getBytes();
            pResult.append("%");
            
            for (int j = 0; j < bytes.length; j++) 
            {
               if (bytes[j] < 0x10) 
               {
                  pResult.append("0");
               }
               pResult.append(Integer.toHexString(bytes[j]));
            }
         } 
         else 
         {
            pResult.append(c);
         }
      }
      return pResult.toString();
   }
}
/*************************************************************************************************/
class Parameters
{
   static final String DocumentKindList = "DocumentKindList"; //names
   static final String TemplatePathList = "TemplatePathList"; //google drive links
   static final String urlScript = "urlScript";
   static final String AuthenticateKey = "AuthenticateKey";
}
/*************************************************************************************************/
class Propertys
{
   static final String DontAttachFileWhenRecused = "DontAttachFileWhenRecused";
   static final String DocumentKind = "Tipo de documento";
}
/*************************************************************************************************/
class FormatParameters
{
   public final static String ID = "FileID=";
   public final static String TITLE = "&AODocsDocumentTitle=";
   public final static String KEY = Parameters.AuthenticateKey;
   public final static String KEYNAME = "&KeyID=";
}
/*************************************************************************************************/
class ProcessParameters
{
   /* define order by */
   //1E3pfY_IiaKDB7bTuWDGY4UP8C_r2MbkupaFpO41lLWw@1yk004Jpt_1NjzefggyRfcIEgo9AUEzCQ9RAytD1PzuA@1m90V7Um0OzjfExFSyw4VtTp6y9yFQvBJbMVs8FvorMk
   //Instrução de Trabalho@Procedimento@Registro da Qualidade
   
   private String[] m_pALinks = getParam(Parameters.TemplatePathList).split(";");
//   String[] m_pALinks = new String[]
//         {  
//            "1E3pfY_IiaKDB7bTuWDGY4UP8C_r2MbkupaFpO41lLWw", //IT
//            "1yk004Jpt_1NjzefggyRfcIEgo9AUEzCQ9RAytD1PzuA", //PP
//            "1m90V7Um0OzjfExFSyw4VtTp6y9yFQvBJbMVs8FvorMk"  //RQ
//         };
   //1E3pfY_IiaKDB7bTuWDGY4UP8C_r2MbkupaFpO41lLWw;1yk004Jpt_1NjzefggyRfcIEgo9AUEzCQ9RAytD1PzuA;1m90V7Um0OzjfExFSyw4VtTp6y9yFQvBJbMVs8FvorMk;
   private String[] m_pAKinds = getParam(Parameters.DocumentKindList).split(";");
//   String[] m_pAKinds = new String[]
//         {
//            "Instrução de Trabalho",
//            "Procedimento",
//            "Registro da Qualidade"
//         };
   //Instrução de Trabalho;Procedimento;Registro da Qualidade;
   private 
   String m_szDocKind = new String();
   /**/
   public ProcessParameters(String szDocKind) throws Exception
   {
      /**/
      this.m_szDocKind = szDocKind;
      /**/
   }
   public String GetDocID() throws Exception
   {
      int n = -1;
      /**/
      debug(String.format("DocKind %s.", this.m_szDocKind));
      /**/
      for(int i = 0; i < this.m_pAKinds.length; i++)
      {
         if(this.m_pAKinds[i].equalsIgnoreCase(this.m_szDocKind))
            return this.m_pALinks[i];
      }
      /**/
      if(n == -1)
         throw new Exception("Template para este tipo de documento " + this.m_szDocKind + " não encontrado na lista!");
      /* return an empty string */
      return new String();
   }
}
/*************************************************************************************************/
try
{
   try
   {
      boolean bBreak = Boolean.parseBoolean(document.getField(Propertys.DontAttachFileWhenRecused).toString());
      /* document already attached*/
      if(bBreak)
         return;
   }
   catch(Exception e)
   {
      //dont make nothing
      debug(e.getMessage());
   }
   /*
    * start process based on document kind
    * **/
   final String szURL = getParam(Parameters.urlScript);
   final String szDocKindSelected = document.getField(Propertys.DocumentKind).toString();
   final ProcessParameters pProc = new ProcessParameters(szDocKindSelected);
   String szFileID = pProc.GetDocID(); 
   Attachment pAtt = getDriveService().copyDocument(szFileID, Optional.absent(), Optional.absent());
   //put file on attached
   DocumentService pService = getDocumentService();
   //blocked code
   Document pDoc = pService.lockDocument(document);

   // save on drive
   //pService.store(pDoc);
   // attach file
   pDoc.addAttachment(pAtt);
   debug(String.format("Attached %s", pAtt.getFileId()));

   //call external script to change file name
   URLEncoder pEncoder = new URLEncoder();
   String strUrl = szURL;
   String strData = FormatParameters.ID + 
                     pEncoder.encode(pAtt.getFileId(), "").toString() + 
                     FormatParameters.TITLE + 
                     pEncoder.encode(document.getTitle(), "").toString() +
                     FormatParameters.KEYNAME +
                     pEncoder.encode(FormatParameters.KEY, "").toString();
   List<HTTPHeader> pHeaders = new LinkedList<HTTPHeader>();
   FetchOptions fo = FetchOptions.Builder.withDeadline(60).doNotFollowRedirects();
   byte[] pBytes = strData.getBytes();
   UrlFetchService pUrlFetchService = getUrlFetchService();
   HTTPResponse pResponse = pUrlFetchService.fetch(strUrl, HTTPMethod.POST, pBytes, pHeaders, fo);
   String strResponse = new String(pResponse.getContent());
   int nDivideResultValue = ((pResponse.getResponseCode() / 0x64) % 3);
   //
   //debug out
   //debug(String.format("nDivideResultValue %d", nDivideResultValue));
   debug(String.format("ResponseCode %s", pResponse.getResponseCode()));
   String[] strAListResult = new String[]{ "IDD_OK", "IDD_ERROR", "IDD_INVALID_KEY" };

   //for(HTTPHeader p: pResponse.getHeaders())
   //   debug(p.getName() + "=" + p.getValue());
   //
   if(nDivideResultValue == 0)
   {
      for(HTTPHeader pHeader: pResponse.getHeaders())
      {
         boolean bExists = pHeader.getName().equalsIgnoreCase("Location");

         if(bExists)
         {
            pResponse = pUrlFetchService.fetch(pHeader.getValue());
            strResponse = new String(pResponse.getContent());
            break;
         }
      }
   }
   else
      throw new Exception(String.format("Erro: HTTP invalido código %d!", pResponse.getResponseCode()));
   
   //debug out
   for(HTTPHeader p: pResponse.getHeaders())
   {
      if(p.getName().equalsIgnoreCase("X-Frame-Options"))
         if(p.getValue().equalsIgnoreCase("DENY"))
            throw new Exception("Erro: Google Script reportou acesso negado!");
   }

   //to debug out compare result
   for(String p: strAListResult)
      if(strResponse.startsWith(p))
         debug("Result " + p);

   if(pResponse.getResponseCode() == 200)
   {
      if(strResponse.startsWith("IDD_ERROR"))
         throw new Exception("Erro: Script resultou em IDD_ERROR!");
      else if(strResponse.startsWith("IDD_INVALID_KEY"))
         throw new Exception("Erro: Script resultou em IDD_INVALID_KEY!");
      else
      {
         debug("Script processado com sucesso!");
         /* mark document already attached dont make again*/
         getDocumentService().lockDocument(document).setField(Propertys.DontAttachFileWhenRecused, true);
      }
   }         
   else
      throw new Exception(String.format("Erro: HTTP invalido código %d!!!",
      pResponse.getResponseCode()));
              
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}   


