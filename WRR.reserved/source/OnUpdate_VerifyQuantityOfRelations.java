/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-15-2014
*  Description verify quantity of relations entry documents
*  Last updated 8-20-2014
*  Last updated 9-08-2014
*/
try
{
   class Parameters
   {
      public static String Admission = "Admission";
      public static String Demission = "Demission";
      public static String Request   = "Request";
   };
   /**********************************************************************************************/
   class Properties
   {
      public static String MAKEARRAY   = ";";
   };
   /**********************************************************************************************/
   class MainClasses
   {
      public static String Admission    = "Documentos de admissão";
      public static String Demission    = "Documentos de demissão";
      public static String Requeststaff = "Requisição de pessoal";
   };
   /**********************************************************************************************/
   class ValidateDocumentClass
   {
      public static boolean IsAdmission(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(MainClasses.Admission.toLowerCase());
      }
      public static boolean IsDemission(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(MainClasses.Demission.toLowerCase());
      }
      public static boolean IsRequestStaff(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(MainClasses.Requeststaff.toLowerCase());
      }
   };
   /**********************************************************************************************/
   class LoadParameter
   {
      public LoadParameter(){}
      public String[] Load(ReadableDocument pDoc) throws Exception
      {
         if(ValidateDocumentClass.IsAdmission(pDoc))
            return getParam(Parameters.Admission).split(Properties.MAKEARRAY);
         
         if(ValidateDocumentClass.IsDemission(pDoc))
            return getParam(Parameters.Demission).split(Properties.MAKEARRAY);
         
         if(ValidateDocumentClass.IsRequestStaff(pDoc))
            return getParam(Parameters.Request).split(Properties.MAKEARRAY);
         
         return null;
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
         LoadParameter pLoad = new LoadParameter();
         String[] pQueue = pLoad.Load(this.m_pDoc);
         
         /* verify quantity of relations */
         for(String szName: pQueue)
         {
            if(this.m_pDoc.getToRelatedDocumentIds(szName).size() > 1)
               throw new Exception("Quantidade de documento(s) na relação " + szName + " acima do permitido!");
         }
      }
   };
   /**********************************************************************************************/
   //execute code
   VerifyQuantityOfRelations pVerify = new VerifyQuantityOfRelations(document);
   pVerify.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
