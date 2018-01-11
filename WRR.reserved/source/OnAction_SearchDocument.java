/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-01-2014
*  Description parse all body of document and update all metadata of self.
*  Last updated 9-03-2014
*/
try
{
	class Properties
   {
      public static String ISDOCUMENTCREATEDBYEMAIL = "IsDocumentCreatedbyEmail";
      public static String STORELISTMETADATA        = "StoreListMetadata";
      public static String IDD_DIVLINE              = ":";
      public static String IDD_CPF                  = "CPF";
      public static String ISBYEMAIL                = "---METADADOS---";
   }
	/**********************************************************************************************/
   /* class Metadata is stored in property StoreListMetadata */
   class Metadata
   {
      public String Name;
      public String Value;
      public Metadata(String szName, String szValue)
      { 
         this.Name = szName; 
         this.Value = szValue; 
      }
   }
   /**********************************************************************************************/
   /* search document */
   class SearchDocument
   {
      public DocumentService m_pDocumentService = getDocumentService();
      public ArrayList<ReadableDocument> m_pListReadableDocument = null;
      /**/
      public SearchDocument(String szClassName, String szFreeTextMask)
      {
         try
         {
            DocumentSearchRequest pDocumentSearchRequest = new DocumentSearchRequest(szClassName);
            /*check mask parameter*/
            if(szFreeTextMask != null)
               pDocumentSearchRequest.setFreeText(szFreeTextMask);
            /*search on class*/
            this.m_pListReadableDocument = (ArrayList<String>)this.m_pDocumentService.findDocuments(pDocumentSearchRequest);
            /**/
            debug(String.format("Docs %d", this.m_pListReadableDocument.size()));
         }
         catch(Exception e)
         {
            debug(e.getMessage());
            throw e;
         }
      }
      public ArrayList<ReadableDocument> GetResult()
      {
         return this.m_pListReadableDocument;
      }
   }
   /**********************************************************************************************/
   /* decode field property StoreListMetadata */
   class DecodeStoreListMetadataProperty
   {
      public ArrayList<Metadata> m_pPropertyList = new ArrayList<Metadata>();
      /**/
      public DecodeStoreListMetadataProperty(ReadableDocument pDoc) throws Exception
      {
         ArrayList<String> pField = (ArrayList<String>)pDoc.getMultiField(Properties.STORELISTMETADATA);
         /**/
         for(String szLine: pField)
         {
            String[] p = szLine.split(Properties.IDD_DIVLINE);
            this.m_pPropertyList.add(new Metadata(p[0].trim(), p[1].trim()));
            /***/
            debug(String.format("Property %d", this.m_pPropertyList.size()));
         }
      }
      public Metadata IndexOf(String szValue) throws Exception
      {
         for(Metadata p: this.m_pPropertyList)
            if(p.Name.equals(szValue))
               return p;
         /* register to debug */
         debug(String.format("Metadata %s is not found!", szValue));
         return null;
      }
      public boolean IsDocumentCreatedbyEmail(ReadableDocument pDoc) throws Exception
      {
         try
         {
            if(pDoc.getRichText().indexOf(Properties.ISBYEMAIL) == -1)
               throw new Exception("Documento não criado por e-mail!");
            
            return true;
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }
         return false;
      }
   }
   /* main code */
   try
   {
      DecodeStoreListMetadataProperty pDoc = new DecodeStoreListMetadataProperty(document);
      Metadata pMetadata = null;
      /*isn´t by e-mail then terminate*/
      if(!pDoc.IsDocumentCreatedbyEmail(document))
         return;
      /**/
      if((pMetadata = pDoc.IndexOf(Properties.IDD_CPF)) != null)
      {
         SearchDocument pSeek = new SearchDocument(pMetadata.Name, null);
         ArrayList<String> pResult = pSeek.GetResult();
         /**/
         for(String szOut: pResult)
            debug(String.format("S: %s", szOut));
      }
   }
   catch(Exception e)
   {
      debug(e.getMessage());
      throw e;
   }
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
