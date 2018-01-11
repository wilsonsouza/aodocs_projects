/*
*  Created by Wilson.Souza
*  Copyright (c) 2014 Dedalus
*  Supervised by Eduardo Bortoluzzi Jr
*  
*  Last update 7-23-2014 by wilson.souza
*  Last update 9-03-2014 by wilson.souza -> radical changes of source code
*  Last update 9-17-2014 by wilson.souza -> fix property elaborador
*  Last update 11-19-2014 by wilson.souza -> fix problem when template attached is spreadsheet
*  Last update 11-19-2014 by wilson.souza -> changed all hardcode names by properties and parameters
*  Last update 01-21-2015 by eduardo.junior-> changed variable m_bSuccess to always return TRUE to not generate errors when only documents attached were spreadsheet
*/
try
{   
   class CreateFooter
   {
      /**********************************************************************************************/
      class URLEncoder 
      {
         /*
          * Method to encode a string to be sent as a parameter of application / x-www-form-urlencoded.
          */
         public static String Build(String input) throws Exception 
         {
             StringBuilder sb = new StringBuilder();

             for(byte b : input.getBytes("UTF-8")) 
             {

               if(b == 0x20) 
               {
                  sb.append('+');
               } 
               else if ( ('0' <= b && b <= '9') || ('a' <= b && b <= 'z') || ('A' <= b && b <= 'Z') ) 
               {
                  sb.append((char)b);
               } 
               else if (b=='$' || b=='-' || b=='_' || b=='.' || b=='!' || b=='*' || b=='\'' || b=='(' || b==')' || b==',') 
               {
                  sb.append((char)b);
               } 
               else 
               {
                  sb.append(String.format("%%%02x", b));
               }
             }
             return sb.toString();
         }
      };
      /**********************************************************************************************/
      class HTTPResult
      {
         static final String SUCCESS = "IDD_OK";
         static final String ERROR   = "IDD_ERROR";
         static final String INVALIDKEY = "IDD_INVALID_KEY";
         static final String DOCISNULL = "IDD_DOC_IS_NULL";
         static final String FOOTEREXCEPTION = "IDD_FOOTER_EXCEPTION";
         static final String INVALIDMAINDOCID = "IDD_INVALID_MAIN_DOCID";
         static final String[] RESULTLIST = 
         {
            SUCCESS, 
            ERROR, 
            INVALIDKEY, 
            DOCISNULL, 
            FOOTEREXCEPTION,
            INVALIDMAINDOCID
         };
      };
      /*************************************************************************************************/
      class OADocs_Parameters
      {
         static final String DocKindName = "DocKindName";
         static final String urlScript = "urlScript";
         static final String SecurityCode = "SecurityCodeId";
         static final String AuthenticateKey = "AuthenticateKey";
      };
      /*************************************************************************************************/
      class OADocs_Properties
      {
         static final String DocKind = new String("Tipo de documento");
         static final String Elaborator = "elaborador";
      };
      /*************************************************************************************************/
      class DOCFmt
      {
         static final String ID = "DocID=";
         static final String VERSION = "&Versao=";
         static final String MANUFACTURERS = "&Elaborador=";
         static final String APPROVER = "&Aprovador=";
         static final String APPROVER_DATE = "&DataAprovacao=";
         static final String KEY = "&Key=";
         static final String DOC_STATE = "&DocState=";
         static final String MAIN_DOC_ID = "&MainDocId=";
         static final String SECURITY_CODE_ID = "&SecurityCodeId=";
      };
      /*************************************************************************************************/
      //variables
      private String m_szDocId = new String();
      private String m_szVersion = "1";
      private String m_szManufacturers = new String();
      private String m_szApprover = new String();
      private String m_szUrlScript = new String();
      private String m_szAuthenticateKey = getParam(OADocs_Parameters.AuthenticateKey);
      private String m_szApproverDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
      private String m_szDocumentState = new String();
      private String m_szADOCS_Security = new String();
      private Object[] m_pDocList = null;
      private boolean m_bSuccess = false;
      private Attachment m_pAttchment = null;
      private String m_szFileId = new String();
      private String m_szResourceId = new String();
      private String m_szMainDocumentId = new String();
      private String m_szData = new String();
      private int m_nSumDoc = 0;
      private int m_nErrorDoc = 0;
      private ReadableDocument m_pDoc = null;
      //constructor
      public CreateFooter(ReadableDocument pDoc) throws Exception
      {
         this.m_pDoc = pDoc;
         /**/
         this.m_szApprover        = getPermissionService().getCurrentUser();
         this.m_szDocId           = this.m_pDoc.getId();
         this.m_szMainDocumentId  = this.m_szDocId;
         /**/
         try
         {
            this.m_szManufacturers = this.m_pDoc.getField(OADocs_Properties.Elaborator).toString();
            debug("elaborador " + this.m_szManufacturers);
         }
         catch(Exception e)
         {
            this.m_szManufacturers = getPermissionService().getCurrentUser();
            debug("getLastEditor " + this.m_szManufacturers);
         }
         /*this.m_szDocumentState = pDoc.getField("GetOldStateBefore_RunScript").toString();*/
         this.m_szDocumentState   = this.m_pDoc.getState();
         this.m_pDocList          = this.m_pDoc.getAttachments().toArray();
         /* refresh */
         try
         {
            this.m_szManufacturers   = getContactService().getUserProfile(this.m_szManufacturers).getName().getFullName().getValue();
            this.m_szApprover        = getContactService().getUserProfile(this.m_szApprover).getName().getFullName().getValue();
         }
         catch(Exception e)
         {
            this.m_szManufacturers   = getPermissionService().getCurrentUser();
            this.m_szApprover        = getPermissionService().getCurrentUser();
         }
         /**/
         this.m_szUrlScript       = getParam(OADocs_Parameters.urlScript);
         this.m_szADOCS_Security  = getParam(OADocs_Parameters.SecurityCode);
         /**/
         debug("Call CreateFooter");
      }
      //
      public void Httppost() throws Exception
      {
         try
         {
            HTTPResponse pHttp = getUrlFetchService().fetch(this.m_szUrlScript, 
                  HTTPMethod.POST, 
                  this.m_szData.getBytes("UTF-8"), 
                  new LinkedList<HTTPHeader>(), FetchOptions.Builder.withDeadline(60).doNotFollowRedirects());
            String szContent = null;
            int nSuccess = ((pHttp.getResponseCode() / 0x64) % 0x3);
            //
            if(nSuccess == 0)
            {
               for(HTTPHeader p: pHttp.getHeaders())
               {
                  boolean bExists = p.getName().equalsIgnoreCase("Location");
   
                  if(bExists)
                  {
                     /**/
                     debug(p.getValue());
                     /**/
                     pHttp = getUrlFetchService().fetch(p.getValue());
                     szContent = new String(pHttp.getContent());
                     /**/
                     debug(szContent);
                     break;
                  }
               }
            }
            else
            {
               this.m_nErrorDoc++;
               throw new Exception(String.format("Erro: HTTP invalido código %d!", pHttp.getResponseCode()));
            }
            /**/
            if(pHttp.getResponseCode() == 0xc8)
            {
               if(szContent.startsWith(HTTPResult.SUCCESS))
               {
                  this.m_bSuccess |= true;
                  this.m_nSumDoc++;
               }              
               else
               {
                  this.m_bSuccess |= false;
                  this.m_nErrorDoc++;
               }
            }
            /* debug out */
            /* 2015-01-21 eduardo.junior@dedalusprime.com.br sempre sucesso para não dar erro com documentos que só tenham planilhas. */
            this.m_bSuccess = true;
            this.PutDebugResult(szContent);
         }
         catch(Exception e)
         {
            debug(e.getMessage());
            throw e;
         }
      }
      //
      public void PutDebugResult(String szContent)
      {
         for(String szFmt: HTTPResult.RESULTLIST)
         {
            if(szContent.startsWith(szFmt))
               debug(szFmt);
         }
         /* show result */
         debug(String.format("PutDebugResult %s", szContent));
      }
      //
      public void AlertAboutProcessedDocuments() throws Exception
      {
         /* verify if exists attached documents */
         if(this.m_pDocList.length > 0)
         {
            String szFmt = String.format("Erro: %d documento%s não fo%s processado%s!",
                  this.m_nErrorDoc, (this.m_nErrorDoc > 1? "s": ""), (this.m_nErrorDoc > 1? "ram": "i"), (this.m_nErrorDoc > 1? "s": ""));

            /* register result */
            if(this.m_nErrorDoc > 0)
               debug(szFmt);

            if(!this.m_bSuccess)
               throw new Exception(szFmt);
            else
               debug(String.format("(%d) documento%s processado%s com sucesso!", 
                     this.m_nSumDoc, this.m_pDocList.length > 1? "s": "", this.m_pDocList.length > 1? "s": ""));
         }         
      }
      //
      public void Prepare() throws Exception
      {
         try
         {
            /**/
            debug("entry Prepare");
            this.m_szFileId          = this.m_pAttchment.getFileId();
            this.m_szResourceId      = this.m_pAttchment.getResourceId();
            
            /* mount parameters and encode */
            this.m_szData = DOCFmt.ID + URLEncoder.Build(this.m_szFileId) + 
                            DOCFmt.VERSION + URLEncoder.Build(this.m_szVersion) + 
                            DOCFmt.MANUFACTURERS + URLEncoder.Build(this.m_szManufacturers) + 
                            DOCFmt.APPROVER + URLEncoder.Build(this.m_szApprover).toString() +
                            DOCFmt.APPROVER_DATE + URLEncoder.Build(this.m_szApproverDate) +
                            DOCFmt.KEY + URLEncoder.Build(this.m_szAuthenticateKey) + 
                            DOCFmt.DOC_STATE + URLEncoder.Build(this.m_szDocumentState) + 
                            DOCFmt.MAIN_DOC_ID + URLEncoder.Build(this.m_szMainDocumentId) + 
                            DOCFmt.SECURITY_CODE_ID + URLEncoder.Build(this.m_szADOCS_Security); 
            /* format */
            /* DocID, Version, Manufacturers, Approver, ApproverDate, AuthenticateKey, DocumentState, MainDocumentId, AODocs_SecurityCodeId */
            debug(String.format("P: %s", this.m_szData));
         }
         catch(Exception e)
         {
            debug(e.getMessage());
            throw e;
         }
      }
      //
      public void Execute() throws Exception
      {
         debug(String.format("%s m_pDocList %d", this.getClass().getName(), m_pDocList.length));
         /* process list of attached documents */
         for(Object p: m_pDocList)
         {
            /* convert class object to class attachment */
            this.m_pAttchment = (Attachment)p;
            /* prepare data */
            this.Prepare();
            /* send by http post */
            this.Httppost();
         }
         /* alert */
         this.AlertAboutProcessedDocuments();
      }
   }
   //launch code
   CreateFooter p = new CreateFooter(document);
   String szDocKind = document.getField(CreateFooter.OADocs_Properties.DocKind).toString();
   String szDocKindParameter = getParam(CreateFooter.OADocs_Parameters.DocKindName);
   /* register debug */
   debug(String.format("DocKindName %s", szDocKindParameter));
   
   /* if document kind select is RQ must be processed */
   if(!szDocKind.equals(szDocKindParameter))
      p.Execute();
   else
      debug(String.format("Kind RQ never must be processed!"));
}
catch(Exception e)
{
   debug(String.format("Erro: %s.%s!", e.getClass().getName(), e.getMessage()));
   throw e;
}  
/* eof */