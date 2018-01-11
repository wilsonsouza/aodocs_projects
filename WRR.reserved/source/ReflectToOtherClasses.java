/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 11-24-2014
*  Description replicate data
*  Last updated 
*  12-08-2014 fixed all failures
*  02-02-2015 added support for all libraries and classes by parameters
*
*  replicate all documents of this class
*  
*  parameters format 
*  LibrariesClass=<lib_name:lib_id@class_name:class_id;...>
*  SecurityCodeId=<oadocs_code>
*  
*  --ClassId: OxTwHO51zH17fyd6Dz / LibraryId: OxTTvrB5RrM6nrziV7
*  Financeiro.Coligada=ClassId: OxTwHO51zH17fyd6Dz LibraryId: OxTTvrB5RrM6nrziV7
*  Recursos Humanos.Coligada=ClassId: On146Vc7yeynbILrON LibraryId: OmwXApG0OrNXRZPbXA
*  
*  LibrariesClass=Recursos Humanos:OmwXApG0OrNXRZPbXA@Coligada:On146Vc7yeynbILrON;Financeiro:OxTTvrB5RrM6nrziV7@Coligada:OxTwHO51zH17fyd6Dz
*  SecurityCodeId=OxUYelc11TRn6SoL1a
*  
*  Company support
*  LibrariesClasses=Recursos Humanos:OmwXApG0OrNXRZPbXA@Coligada:On146Vc7yeynbILrON;Financeiro:OxTTvrB5RrM6nrziV7@Coligada:OxTwHO51zH17fyd6Dz
*  
*  Supply support
*  LibrariesClasses=Financeiro:OxTTvrB5RrM6nrziV7@Fornecedor:OxTwE1m1YSrfXwKf8y;Contratos:<id>@Contrato:<id>
*  
*  Contract support
*  LibrariesClasses=Recursos Humanos:OmwXApG0OrNXRZPbXA@Contrato:On149mRwOGeKVyWLFa;
*  Financeiro:OxTTvrB5RrM6nrziV7@Contrato:P3nICaAzkZtCT2Qhxi;Contratos:P4jUVn013JhSbIGM22@Contrato:P4r4AjWutL6vc7SwQt
*  
*  ClassId_ P4r4AjWutL6vc7SwQt /LibraryId_ 
*  
*  Center Of Cost
*  LIbrariesClasses=Financeiro:OxTTvrB5RrM6nrziV7@Centro de Custo:OxTwGwrzTEj13c6VdD;
*  Contratos:P4jUVn013JhSbIGM22@Centro de Custo:P4r3uej0DkuaJdcweM
*  
*  Contratos:P4jUVn013JhSbIGM22@Coligada:P4qtsxhvwRhr76ynQs;
*/
try
{
   /**********************************************************************************************/
   class LinkLib
   {
      public String Name = null;
      public String ID = null;
      public LinkLib(String szName, String szID)
      {
         this.Name = szName;
         this.ID = szID;
      }
   }
   /**********************************************************************************************/
   class LinkClass extends LinkLib
   {
      public LinkClass(String szName, String szID)
      {
         /* initialize super class with name and id */
         super(szName, szID);
      }
   }
   /**********************************************************************************************/
   class DocumentInfo extends LinkLib
   {
      public String Property = null;
      public DocumentInfo(String szName, String szValue, String szProperty)
      {
         /* initialize super class */
         super(szName, szValue);
         this.Property = szProperty;
      }
   }
   /**********************************************************************************************/
   class Links
   {
      public LinkLib Lib = null;
      public LinkClass Class = null;
      public Links(LinkLib pLib, LinkClass pClass)
      {
         this.Lib = pLib;
         this.Class = pClass;
      }
   }
   /**********************************************************************************************/
   class ListParameters
   {
      static public String LibrariesClasses = ".libraries";
      static public String SecuirtyCodeId = "SecurityCodeId";
      static public String LibraryID = "@";
      static public String ClassID = ":";
      static public String MakeArray = ";";
   }
   /**********************************************************************************************/
   class DecodeJson
   {
      static public String[] JsonToArray(String szContent) throws Exception
      {
         String szData = szContent.replaceAll("\\{", "").replaceAll("\\[", "").
         replaceAll("\\}", "").replaceAll("\\]", "").replaceAll("\"", "");
         String[] pData = szData.split(",");
         return pData;
      }
      static public String[] Search(String[] pSearchList, String szData) throws Exception
      {
         if(!szData.trim().isEmpty())
         {
            for(String szRow: pSearchList)
            {
               /**/
               if(szRow.indexOf(szData.trim()) != -1)
                  return szRow.split(":");
            }
         }
         return new String[]{ szData, new String() };
      }
   }
   /**********************************************************************************************/
   class Properties
   {
      static public String FinancialRelated = "FinancialRelatedID";
      static public String HumanResourcesRelated = "HumanResourcesRelatedID";
      private ReadableDocument m_pDoc = null;
      public Properties(ReadableDocument pDoc) throws Exception
      {
         /* assign current handle */
         this.m_pDoc = pDoc;
      }
      public DocumentInfo GetFinancialRelated() throws Exception
      {
         String[] p = this.m_pDoc.getField(this.FinancialRelated).toString().split(ListParameters.LibraryID);
         /* {FileID, FileName} */
         return new DocumentInfo(p[0], p[1], this.FinancialRelated);
      }
      public DocumentInfo GetHumanResourcesRelated() throws Exception
      {
         String[] p = this.m_pDoc.getField(this.HumanResourcesRelated).toString().split(ListParameters.LibraryID);
         /* {FileID, FileName} */
         return new DocumentInfo(p[0], p[1], this.HumanResourcesRelated);
      }
      public Properties PutField(String szFieldName, String szValue) throws Exception
      {
         getDocumentService().lockDocument(this.m_pDoc).setField(szFieldName, szValue);
         return this;
      }
      public String GetField(String szFieldName) throws Exception
      {
         try
         {
            return getDocumentService().lockDocument(this.m_pDoc).getField(szFieldName).toString();
         }
         catch(Exception e)
         {
            debug("GetField:" + e.getMessage());
         }
         return new String();
      }
      public Properties Update(DocumentInfo pDoc, String szContent) throws Exception
      {
         String[] pResult = DecodeJson.Search(DecodeJson.JsonToArray(szContent), pDoc.ID);
         /* debug out */
         debug("Update:" + pDoc.Property + "," + this.m_pDoc.getTitle() + "@" + pResult[0] + pResult[1]);
         /**/
         if(!pResult[1].isEmpty())
            this.PutField(pDoc.Property, this.m_pDoc.getTitle() +"@" + pResult[1].trim());
         return this;
      }
      public Properties Set(DocumentInfo pDoc, String szContent) throws Exception
      {
         String[] pData = DecodeJson.Search(DecodeJson.JsonToArray(szContent), "id:");
         /* debug out */
         debug("Set:" + pDoc.Property + "," + this.m_pDoc.getTitle() + "@" + pData[0] + pData[1]);
         /**/
         if(!pData[1].isEmpty())
            this.PutField(pDoc.Property, this.m_pDoc.getTitle() + "@" + pData[1].trim());
         return this;
      }
   }
   /**********************************************************************************************/
   class PropertiesImpl extends Properties
   {
      /* assign property field of document */
      private String m_szFieldDoc = new String();
      /* content all classes and libraries */
      static public String DocumentsNameID = "DocumentsNameID";
      public PropertiesImpl(ReadableDocument pDoc) throws Exception
      {
         super(pDoc);
      }
      /* process new format parameter DocumentsNameID */
      public DocumentInfo[] GetDocumentsNameID() throws Exception
      {
         String szParameter = this.GetField(this.DocumentsNameID);
         String szClassName = this.m_pDoc.getDocumentClass();
         int nLength = getParam(szClassName + ListParameters.LibrariesClasses).split(ListParameters.MakeArray).length;
         DocumentInfo[] pDocList = new DocumentInfo[nLength];
         /* debug out */
         debug(String.format("nLength Offset %d szParameter Offset %d", nLength, szParameter.length()));
         /* check if isn´t empty */
         if(szParameter.trim().isEmpty())
         {
            for(int i = 0; i < nLength; i++)
               pDocList[i] = new DocumentInfo(new String(), new String(), this.DocumentsNameID);
            
            /* debug out */
            debug(String.format("pOut Offset %d", pDocList.length));
            return pDocList;
         }
         /**/
         String[] pQueue = szParameter.split(ListParameters.LibraryID);
         int i = 0;
         /* debug out */
         debug(String.format("LibrariesClasses length %d", nLength));
         
         /* generate array to bring new list of libraries and classes by order */
         for(String szData: pQueue)
         {
            String[] pInfo = szData.split(ListParameters.ClassID);
            /* library first and class second */
            pDocList[i++] = new DocumentInfo(pInfo[0], pInfo[1], this.DocumentsNameID);
         }
         /* return filled array */
         return pDocList;
      }
      /* search by document id */
      public String SearchDocumentById(String[] pQueue, String szID) throws Exception
      {
         for(String szRow: pQueue)
         {
            if(szRow.indexOf(szID) != -1)
               return szRow;
         }
         /* isn´t found then empty string */
         return new String();
      }
      /* override method updated to support new property */
      @Override
      public Properties Update(DocumentInfo pDoc, String szContent) throws Exception
      {
         String[] pData = DecodeJson.Search(DecodeJson.JsonToArray(szContent), pDoc.ID);
         String szOldData = this.GetField(pDoc.Property);
         /* debug out */
         debug("Update get old value->" + szOldData);
         /* get old value */
         if(!szOldData.isEmpty())
         {
            String[] pQueue = szOldData.split(ListParameters.LibraryID);
            
            if(pQueue.length < 2)
               szOldData += ListParameters.LibraryID + this.m_pDoc.getTitle() + ListParameters.ClassID + pData[1].trim();
            else
               szOldData = this.m_pDoc.getTitle() + ListParameters.ClassID + pData[1].trim();
         }
         else
            szOldData = this.m_pDoc.getTitle() + ListParameters.ClassID + pData[1].trim();
         /* debug out */
         debug("Update put new value->" + szOldData);
         /* put data on field property */
         this.PutField(pDoc.Property, szOldData);
         return this;
      }
      /* override method set to support of new property */
      @Override
      public Properties Set(DocumentInfo pDoc, String szContent) throws Exception
      {
         String[] pData = DecodeJson.Search(DecodeJson.JsonToArray(szContent), "id:");
         String szOldData = this.GetField(pDoc.Property);
         /* debug out */
         debug("Insert get old value->" + szOldData);
         /* get old value */
         if(!szOldData.isEmpty())
            szOldData += ListParameters.LibraryID + this.m_pDoc.getTitle() + ListParameters.ClassID + pData[1].trim();
         else
            szOldData = this.m_pDoc.getTitle() + ListParameters.ClassID + pData[1].trim();
         /* debug out */
         debug("Insert put new value->" + szOldData);
         /* put data on field property */
         this.PutField(pDoc.Property, szOldData);
         return this;
      }
   }
   /**********************************************************************************************/
   class FetchResult
   {
      public int Result = 0;
      public String Content = null;
      public FetchResult(int nResult, String szContent)
      {
         this.Result = nResult;
         this.Content = szContent;
      }
   }
   /**********************************************************************************************/
   class JsonParameters
   {
      static public String className = "\"className\":";
      static public String classId = "\"classId\":";
      static public String libraryName = "\"libraryName\":";
      static public String libraryId = "\"libraryId\":";
      static public String userCanChangeProperties = "\"userCanChangeProperties\":";
      static public String userCanChangeAcl = "\"userCanChangeAcl\":";
      static public String title = "\"title\":";
      static public String richText = "\"richText\":";
      static public String initialAuthor = "\"initialAuthor\":";
      static public String userCanDelete = "\"userCanDelete\":";
      static public String onlyAdminChangeAcl = "\"onlyAdminChangeAcl\":";
      static public String onlyAdminCanDelete = "\"onlyAdminCanDelete\":";
      static public String attachments = "\"attachments\":";
      static public String categories = "\"categories\":";
      static public String creationDate = "\"creationDate\":";
      static public String deleted = "\"deleted\":";
      static public String id = "\"id\":";
      static public String importFile = "\"importFile\":";
      static public String kind = "\"kind\":";
      static public String modificationDate = "\"modificationDate\":";
      static public String state = "\"state\":";
      static public String updateAuthor = "\"updateAuthor\":";
      static public String versionLabel =  "\"versionLabel\":";
      static public String versionNumber = "\"versionNumber\":";
      
      class Fields
      {
         static public String fields = "\"fields\":";
         static public String fieldName = "\"fieldName\":";
         static public String kind = "\"kind\":";
         static public String values = "\"values\":";
         static public String type = "\"type\":";
      };

      class Permissions
      {
         static public String permissions = "\"permissions\":";
         static public String inherited = "\"inherited\":";
         static public String kind = "\"kind\":";
         static public String type = "\"type\":";
         static public String right = "\"right\":";
         
         class Role
         {
            static public String roleNames = "\"roleNames\":";
         }
         
         class Field
         {
            static public String fieldNames = "\"fieldNames\":";
         }
         
         static public String value = "\"value\":";
      }
      
      class Token
      {
         static public String OpenKey = "{";
         static public String CloseKey = "}";
         static public String OpenBracket = "[";
         static public String CloseBracket = "]";
      }
   }
   /**********************************************************************************************/
   class Json
   {
      private String m_szFmt = new String();
      public Json(){}
      public Json StartBracket()
      {
         this.m_szFmt += JsonParameters.Token.OpenBracket;
         return this;
      }
      public Json EndBracket()
      {
         this.m_szFmt += JsonParameters.Token.CloseBracket;
         return this;
      }
      public Json StartKey()
      {
         this.m_szFmt += JsonParameters.Token.OpenKey;
         return this;
      }
      public Json EndKey()
      {
         this.m_szFmt += JsonParameters.Token.CloseKey;
         return this;
      }
      public Json AddProperty(String szName, String szValue)
      {
         this.m_szFmt += szName + "\"" + szValue + "\"";
         return this;
      }
      public Json StartArray(String szName)
      {
         this.m_szFmt += szName + this.StartBracket();
         return this;
      }
      public Json EndArray()
      {
         this.m_szFmt += this.EndBracket();
         return this;
      }
      public Json NextLine()
      {
         this.m_szFmt += ",";
         return this;
      }
      public String GetJsonString()
      {
         return this.m_szFmt;
      }
      public void SetFields(String szName, String szKind, String szValue, boolean bStarted)
      {
         if(bStarted)
            this.m_szFmt += "\"fields\":" + "[";
         
         this.m_szFmt += "{" + "\"fieldName\":" + "\"" + szName + "\"," 
               + "\"type\":" + "\"" + szKind + "\"," 
               + "\"values\":" + "[" + "\"" + szValue + "\"]}";
      }
   }
   /**********************************************************************************************/
   class Fetch
   {
      class Data
      {
         public String LibID = new String();
         public String ClassID = new String();
         public String DocID = new String();
         public Data(String szLibID, String szClassID, String szDocID)
         {
            this.LibID = szLibID;
            this.ClassID = szClassID;
            this.DocID = szDocID;
         }
      }
      
      class Urls
      {
         private String m_szInsertDocument = "https://api-dot-ao-docs.appspot.com/_ah/api/document/v1?securityCode=%s";
         private String m_szSearchDocument = "https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/%s/search?classId=%s&securityCode=%s";
         private String m_szUpdateDocument = "https://api-dot-ao-docs.appspot.com/_ah/api/document/v1/%s?securityCode=%s";
         private String m_szListDocument = "https://api-dot-ao-docs.appspot.com/_ah/api/search/v1/libraries/";
         private Fetch.Data m_pData = null;
         public Urls(Fetch.Data pData)
         {
            this.m_pData = pData;
         }
         public String InsertDocument()
         {
            return String.format(this.m_szInsertDocument, getParam(ListParameters.SecuirtyCodeId));
         }
         public String SearchDocument()
         {
            return String.format(this.m_szSearchDocument, this.m_pData.LibID, this.m_pData.ClassID, getParam(ListParameters.SecuirtyCodeId));
         }
         public String UpdateDocument()
         {
            return String.format(this.m_szUpdateDocument, this.m_pData.DocID, getParam(ListParameters.SecuirtyCodeId));
         }
         public String ListDocuments(String szPageToken)
         {
        	 String szFmt = this.m_szListDocument + this.m_pData.LibID;
        	 
        	 if(!szPageToken.trim().isEmpty())
        		 szFmt += String.format("%s/list?pageToken=%s&pageSize=100&classId=%s&securityCode=%s",
        				 this.m_pData.LibID, 
        				 szPageToken.trim(), 
        				 this.m_pData.ClassID, 
        				 getParam(ListParameters.SecuirtyCodeId));
        	 else
        		 szFmt += String.format("%s/list?pageSize=100&classId=%s&securityCode=%s",
        				 this.m_pData.LibID,
        				 this.m_pData.ClassID, 
        				 getParam(ListParameters.SecuirtyCodeId));
        	 return szFmt;
         }
      }
      
      private String m_szSecurityCodeId = new String();
      private ReadableDocument m_pDoc = null;
      private Links m_pLinks = null;
      public Fetch(ReadableDocument pDoc) throws Exception
      {
         /* get security code */
         this.m_szSecurityCodeId = new String(getParam(ListParameters.SecuirtyCodeId));
         /* store current handle of document */
         this.m_pDoc = pDoc;
         /* debug */
         debug(this.getClass().getName() + "SCode:" + this.m_szSecurityCodeId);
      }
      public Fetch SetLinks(Links pLink) throws Exception
      {
         this.m_pLinks = pLink;
         return this;
      }
      public ArrayList<HTTPHeader> AllocHeader(String[] pType) throws Exception
      {
         ArrayList<HTTPHeader> pHeader = new ArrayList<HTTPHeader>();
         /* put on */
         for(String szData: pType)
         {
            String[] pData = szData.split(ListParameters.ClassID);
            pHeader.add(new HTTPHeader(pData[0], pData[1]));
         }
         /**/
         return pHeader;
      }
      public FetchResult InsertDocument(Json pParameters) throws Exception
      {
         Fetch.Urls pUrl = new Fetch.Urls(null);
         HTTPResponse pHttp = getUrlFetchService().
               fetch(pUrl.InsertDocument(), 
                     HTTPMethod.PUT, 
                     pParameters.GetJsonString().getBytes("UTF-8"), 
                     this.AllocHeader(new String[]{"Content-Type: application/json", "X-JavaScript-User-Agent:  Google APIs Explorer"}), 
                     FetchOptions.Builder.withDeadline(0x3c));
         String szContent = new String(pHttp.getContent());
         /* debug out url */
         debug("URL:" + pUrl.InsertDocument());
         return new FetchResult(pHttp.getResponseCode(), szContent);
      }
      public FetchResult UpdateDocument(DocumentInfo pDocInfo, Json pParameters) throws Exception
      {
         Fetch.Urls pUrl = new Fetch.Urls(new Fetch.Data(null, null, pDocInfo.ID));
         HTTPResponse pHttp = getUrlFetchService().
               fetch(pUrl.UpdateDocument(), 
                     HTTPMethod.POST, 
                     pParameters.GetJsonString().getBytes("UTF-8"),
                     this.AllocHeader(new String[]{"Content-Type: application/json", "X-JavaScript-User-Agent:  Google APIs Explorer"}), 
                     FetchOptions.Builder.withDeadline(0x3c));
         return new FetchResult(pHttp.getResponseCode(), new String(pHttp.getContent()));
      }
      public boolean SearchDocuments(DocumentInfo pQuery) throws Exception
      {
    	  return this.ListDocuments(pQuery);
    	 /* debug out */
//    	 debug(String.format("Doc.%s Doc.ID.%s", pQuery.Name, pQuery.ID));
//    	 /**/
//    	 Fetch.Urls pUrl = new Fetch.Urls(new Fetch.Data(this.m_pLinks.Lib.ID, this.m_pLinks.Class.ID, null));
//         HTTPResponse pHttp = getUrlFetchService().
//               fetch(pUrl.SearchDocument(), 
//                     HTTPMethod.POST, 
//                     new byte[]{}, 
//                     this.AllocHeader(new String[]
//                    		 {
//	                    		 "Content-Type: application/json", 
//	                    		 "X-JavaScript-User-Agent:  Google APIs Explorer"
//                    		 }),
//                    		 FetchOptions.Builder.withDefaults().withDeadline(0x3c));
//         String szContent = new String(pHttp.getContent());
//         boolean bExists = false;
//         //String[] pData = DecodeJson.Search(DecodeJson.JsonToArray(szContent), pQuery.ID);
//         String[] pData = DecodeJson.Search(DecodeJson.JsonToArray(szContent), pQuery.ID);
//         /**/
//         if(!pData[1].trim().isEmpty() && !pQuery.ID.trim().isEmpty())
//            bExists = pData[1].indexOf(pQuery.ID) != -1;
//         /**/
//         debug("Query:" + pQuery.ID + "-IsOK:" + (bExists? "Yes": "No"));
//         debug("Data:" + pData[0] + "-" + pData[1]);
//         /* check result */
//         if(pHttp.getResponseCode() != 0xc8)
//            throw new Exception("SearchDocuments http %d!" + String.format("%d", pHttp.getResponseCode()));
         /**/
//         return bExists;
      }
      public boolean ListDocuments(DocumentInfo pDocI) throws Exception
      {
    	  String szPageToken = new String();
    	  boolean bExists = false;
    	  /***/
    	  while(!bExists)
    	  {
	     	 Fetch.Urls pUrl = new Fetch.Urls(new Fetch.Data(this.m_pLinks.Lib.ID, this.m_pLinks.Class.ID, null));
	         HTTPResponse pHttp = getUrlFetchService().
	               fetch(pUrl.ListDocuments(szPageToken), 
	                     HTTPMethod.POST, 
	                     new byte[]{}, 
	                     this.AllocHeader(new String[]
	                    		 {
		                    		 "Content-Type: application/json", 
		                    		 "X-JavaScript-User-Agent:  Google APIs Explorer"
	                    		 }),
	                    		 FetchOptions.Builder.withDeadline(0x3c));
	         String szContent = new String(pHttp.getContent()); 
	         String[] pContent = DecodeJson.JsonToArray(szContent);
	         String[] pHasNextPage = DecodeJson.Search(pContent, "pageToken");
	         String[] pDocAlreadyExists = DecodeJson.Search(pContent, pDocI.ID);
	         String[] pDocumentID = DecodeJson.Search(pContent, "id:");
	         /* debug out */
	         debug(String.format("Next.%s", pHasNextPage[1]));
	         debug(String.format("Exists.%s Idfound.%s", pDocAlreadyExists[1], pDocumentID[1]));
	         
	         /* list of documents arrived eof */
	         if(pDocumentID[1].isEmpty())
	        	 break;
	         /***/
	         if(pDocAlreadyExists[1].trim().equals(pDocI.ID))
	        	 bExists = !bExists;
	         else
	         {
	        	 if(!pHasNextPage[1].isEmpty())
	        		 szPageToken = pHasNextPage[1];
	        	 else
	        		 break;
	         }
    	  }
    	  return bExists;
      }
   }
   /**********************************************************************************************/
   class ProcessParameters
   {
      private String m_szQueue = null;
      public ProcessParameters(ReadableDocument pDoc) throws Exception
      {
    	 this.m_szQueue = new String(getParam(pDoc.getDocumentClass() + ListParameters.LibrariesClasses));
         debug(this.m_szQueue);
         /* check content of parameter */
         if(this.m_szQueue.isEmpty())
            throw new Exception("Paramêtro LibrariesClasses esta vazio!");
      }
      public ArrayList<Links> GetListLinks() throws Exception
      {
         ArrayList<Links> pData = new ArrayList<Links>();
         String[] pQueue = this.m_szQueue.split(ListParameters.MakeArray);
         /* debug out */
         debug(String.format("pQueue Offset %d", pQueue.length));
         
         /* mount links */
         for(String szData: pQueue)
         {
            String[] pNext = szData.split(ListParameters.LibraryID);
            /* 
             * 0 index library name and library id
             * 1 index class name and class id
             */
            String[] pLib = pNext[0].split(ListParameters.ClassID);
            LinkLib pLID = new LinkLib(pLib[0], pLib[1]);
            /*
             * 0 index 
             * 1 index
             */
            String[] pClass = pNext[1].split(ListParameters.ClassID);
            LinkClass pCID = new LinkClass(pClass[0], pClass[1]);
            /*
             * add lib and class
             */
            pData.add(new Links(pLID, pCID));
         }
         /* debug out */
         debug(String.format("pData Offset %d", pData.size()));
         return pData;
      }
   }
   /**********************************************************************************************/
   class ReflectDocument
   {
      private String[] m_pTarget = null;
      private String[] m_pSource = null;
      private ReadableDocument m_pDoc = null;
      private Fetch m_pFetch = null;
      public ReflectDocument(ReadableDocument pDoc) throws Exception
      {
         /* store current document handle */
         this.m_pDoc = pDoc;
         this.m_pFetch = new Fetch(pDoc);
      }
      public void Execute() throws Exception
      {
         /* read properties */
         //Properties p = new Properties(this.m_pDoc);
         PropertiesImpl p = new PropertiesImpl(this.m_pDoc);
         ProcessParameters pp = new ProcessParameters(this.m_pDoc);
         /* get class and libraries identifications */
         ArrayList<Links> pClassLibs = pp.GetListLinks();
         DocumentInfo[] pDocList = null;
         
         try
         {
            debug("Call GetDocumentsNameID");
            //pDocList = new DocumentInfo[]{ p.GetFinancialRelated(), p.GetHumanResourcesRelated() };
            pDocList = p.GetDocumentsNameID();
            //debug out get array offset
            debug(String.format("pDocList Offset %d", pDocList.length));
         }
         catch(Exception e)
         {
            debug("Error:" + e.getClass().getName() + "," + e.getMessage());
            throw e;
         }
         /* now starting searching by documents */
         for(int i = 0; i < pClassLibs.size(); i++)
         {
            Links pLink = (Links)pClassLibs.get(i);
            DocumentInfo pDocI = pDocList[i];
            FetchResult pResult = null;
            Json j = new Json();
            SimpleDateFormat pNow = new SimpleDateFormat("dd/MM/yyyy");
            String szMergeTitle = this.UpdateDocumentTitle();
            
            /* prepare json parameters */
            j.StartKey().
            AddProperty(JsonParameters.libraryName, pLink.Lib.Name).NextLine().
            AddProperty(JsonParameters.libraryId, pLink.Lib.ID).NextLine().
            AddProperty(JsonParameters.className, pLink.Class.Name).NextLine().
            AddProperty(JsonParameters.classId, pLink.Class.ID).NextLine().
            AddProperty(JsonParameters.title, szMergeTitle).NextLine();
            /*set to default status always*/
            getDocumentService().lockDocument(this.m_pDoc).setField("Status", true);
            /* debug out */
            debug(String.format("Lib.Name.%s Lib.ID.%s Class.Name.%s Class.ID.%s, Row %d", 
            		pLink.Lib.Name, pLink.Lib.ID, pLink.Class.Name, pLink.Class.ID, i));
            //
            try
            {
               Object pName = this.m_pDoc.getField("Nome");
               Object pStatus = this.m_pDoc.getField("Status");
               //
               if(pName != null)
                  j.SetFields("Nome", "STRING", pName.toString().trim(), true);
               else
                  j.SetFields("Nome", "STRING", "", true);
               //
               j.NextLine();
               j.SetFields("Status", "BOOLEAN", "true", false);
               
               /* detemine that´s class */
               if(this.m_pDoc.getDocumentClass().equals("Fornecedor"))
               {
                  Object szID = this.m_pDoc.getField("CPF-CNPJ");
                  /**/
                  if(szID != null)
                  {
                     j.NextLine();
                     j.SetFields("CPF-CNPJ", "STRING", szID.toString(), false);
                  }
               }
               /* close json bracket */
               j.EndBracket();   
            }
            catch(Exception e)
            {
               debug(e.getMessage());
            }
            
            /* set default data base*/
            this.m_pFetch.SetLinks(pLink);
            /* search by document */
            if(this.m_pFetch.ListDocuments(pDocI))
            {
               j.EndKey();
               /* json debug out */
               debug("Update:" + j.GetJsonString());
               /* update document */
               pResult = this.m_pFetch.UpdateDocument(pDocI, j);
               /* update property */
               p.Update(pDocI, pResult.Content);
            }
            else
            {
               /* permissions */
               j.EndKey();
               /* json debug out */
               debug("Insert:" + j.GetJsonString());
               
               /* document is not exists then insert */
               pResult = this.m_pFetch.InsertDocument(j);
               
               /* put file and id on properties */
               p.Set(pDocI, pResult.Content);
            }
            
            /* verify result object */
            if(pResult.Result != 0xc8)
               throw new Exception(String.format("HTTP código %d", pResult.Result));
         }
      }
      public String UpdateDocumentTitle() throws Exception
      {
    	  String szCaption = this.m_pDoc.getTitle().trim();
    	  CharSequence csSeq = "|";
    	  Object pName = this.m_pDoc.getField("Nome");
    	  /**/
    	  if(!szCaption.contains(csSeq))
    		  szCaption = String.format("%s | %s", 
    				  this.m_pDoc.getTitle().trim(), pName);
    	  else
    		  szCaption = this.m_pDoc.getTitle().trim();
    	  
    	  /* dispatch new value to property */
    	  getDocumentService().lockDocument(this.m_pDoc).setTitle(szCaption);
    	  return szCaption;
      }
   }
   /**********************************************************************************************/
   ReflectDocument p = new ReflectDocument(document);
   p.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}