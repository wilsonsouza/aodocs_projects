/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-15-2014
*  Description verify quantity of relations entry documents
*  Last updated 8-20-2014
*  Last updated 9-08-2014
*  Last updated 2-23-2015
*  Last updated 3-18-2015 disabled run
*/
try
{
   /*
    * ListOfClass=Contas a Pagar@Faturamento
    * Contas a Pagar.relations=Coligada@Fornecedor@Centro de Custo@Tipo de Documento
    * 
    */
   /**********************************************************************************************/
   class ValidateDocumentClass
   {
      public ReadableDocument m_pDoc = null;
      public String[] ListOfClass = getParam("ListOfClass").split("@");
      public ValidateDocumentClass(ReadableDocument pDoc)
      {
         this.m_pDoc = pDoc;
      }
      public String GetCurrentClassName() throws Exception
      {
         for(String szName: this.ListOfClass)
         {
            if(szName.equalsIgnoreCase(this.m_pDoc.getDocumentClass()))
               return szName;
         }
         return new String();
      }
   };
   /**********************************************************************************************/
   class LoadParameter extends ValidateDocumentClass
   {
      public LoadParameter(ReadableDocument pDoc)
      {
         super(pDoc);
      }
      public String[] Load() throws Exception
      {
         String szClassName = this.GetCurrentClassName();
         
         if(szClassName.isEmpty())
            throw new Exception("Classe não identificado, processo abortado!");
         
         return getParam(szClassName + ".relations").split("@");
      }
   };
   /**********************************************************************************************/
   class VerifyQuantityOfRelations
   {
      private ReadableDocument m_pDoc = null;
      /**/
      public VerifyQuantityOfRelations(ReadableDocument pDoc)
      {
         this.m_pDoc = pDoc;
      }
      public void Execute() throws Exception
      {
         LoadParameter p = new LoadParameter(this.m_pDoc);
         String[] pQueue = p.Load();
         String szCurrentClassName = p.GetCurrentClassName(); 
         
         /* verify quantity of relations */
         for(String szName: pQueue)
         {
            if(this.m_pDoc.getToRelatedDocumentIds(szCurrentClassName + "->" + szName).size() > 1)
               throw new Exception("Quantidade de documento(s) na relação " + szName + " acima do permitido!");
         }
      }
   };
   /**********************************************************************************************/
   //execute code
   VerifyQuantityOfRelations pVerify = new VerifyQuantityOfRelations(document);
//   pVerify.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
