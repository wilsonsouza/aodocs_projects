import java.util.ArrayList;

/*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-12-2014
*  Description store all selected relations in combo
*  Last updated 
*/
try
{
   class Properties
   {
      public static String MAKEARRAY = ";";
      public static String STARTKEY  = "---METADADOS---";
      public static String NAME      = "Nome";
      public static String CPF       = "CPF";
   };
   /**********************************************************************************************/
   class ListData
   {
      public String Name = null;
      public String Value = null;
      public ListData(String szName, String szValue)
      {
         this.Name = szName;
         this.Value = szValue;
      }
   };
   /**********************************************************************************************/
   class WhatDocumentClass
   {
      private String[] m_pListOfClass = null;
      private ReadableDocument m_pDoc = null;
      private String m_szActualClass = null;
      public WhatDocumentClass(ReadableDocument pDoc) throws Exception
      {
         this.m_pDoc = pDoc;
         this.m_szActualClass = this.m_pDoc.getDocumentClass().toLowerCase().trim();
         /**/
         if(getParam("ListOfClass") == null)
            throw new Exception("Campo com lista de classes não encontrada na lista!");
         /**/
         m_pListOfClass = getParam("ListOfClass").split(Properties.MAKEARRAY);
      }
      public boolean IsAdmission() throws Exception
      {
         return this.m_szActualClass.equals(this.m_pListOfClass[0].trim().toLowerCase());
      }
      public boolean IsDemission() throws Exception
      {
         return this.m_szActualClass.equals(this.m_pListOfClass[1].trim().toLowerCase());
      }
      public boolean IsRequestStaff() throws Exception
      {
         return this.m_szActualClass.equals(this.m_pListOfClass[2].trim().toLowerCase());
      }
   };
   /**********************************************************************************************/
   class Parameters
   {
      private String[] m_pAdmissionRelation = getParam("Admission_Relation").split(Properties.MAKEARRAY);
      private String[] m_pAdmissionProperty = getParam("Admission_Property").split(Properties.MAKEARRAY);
      private String[] m_pDemissionRelation = getParam("Demission_Relation").split(Properties.MAKEARRAY);
      private String[] m_pDemissionProperty = getParam("Demission_Property").split(Properties.MAKEARRAY);
      private String[] m_pRequestRelation   = getParam("Request_Relation").split(Properties.MAKEARRAY);
      private String[] m_pRequestProperty   = getParam("Request_Property").split(Properties.MAKEARRAY);
      private ArrayList<ListData> m_pList = new ArrayList<ListData>();
      public Parameters()
      {
         debug(String.format("A:%d == %d", this.m_pAdmissionRelation.length, this.m_pAdmissionProperty.length));
         debug(String.format("D:%d == %d", this.m_pDemissionRelation.length, this.m_pDemissionProperty.length));
         debug(String.format("R:%d == %d", this.m_pRequestRelation.length, this.m_pRequestProperty.length)); 
      }
      public ArrayList<ListData> IndexOf(ReadableDocument pDoc) throws Exception
      {
         WhatDocumentClass p = new WhatDocumentClass(pDoc);
         /**/
         this.m_pList.clear();
         /**/
         if(p.IsAdmission())
         {
            for(int i = 0; i < this.m_pAdmissionProperty.length; i++)
               this.m_pList.add(new ListData(this.m_pAdmissionRelation[i].trim(), this.m_pAdmissionProperty[i].trim()));
            return this.m_pList;
         }
         /**/
         if(p.IsDemission())
         {
            for(int i = 0; i < this.m_pDemissionProperty.length; i++)
               this.m_pList.add(new ListData(this.m_pDemissionRelation[i].trim(), this.m_pDemissionProperty[i].trim()));
            return this.m_pList;
         }
         /**/
         if(p.IsRequestStaff())
         {
            for(int i = 0; i < this.m_pRequestProperty.length; i++)
               this.m_pList.add(new ListData(this.m_pRequestRelation[i].trim(), this.m_pRequestProperty[i].trim()));
            return this.m_pList;
         }
         return this.m_pList;
      }
   };
   /**********************************************************************************************/
   class UpdateProperties
   {
      private ReadableDocument m_pDoc = null;
      public UpdateProperties(ReadableDocument pDoc)
      {
         this.m_pDoc = pDoc;
      }
      public void Execute() throws Exception
      {
         Parameters p = new Parameters();
         ArrayList<ListData> pList = p.IndexOf(this.m_pDoc);
         /**/
         for(ListData pCurrent: pList)
         {
            ArrayList<String> pListRelations = (ArrayList<String>)this.m_pDoc.getToRelatedDocumentIds(pCurrent.Name);
            /**/
            debug(String.format("Rel %d, ", pListRelations.size()) + pCurrent.Name + ", " + pCurrent.Value);
            /**/
            if(pListRelations.size() > 0)
            {
               for(String szName: pListRelations)
               {
                  Document pDoc = getDocumentService().loadDocument(szName);
                  String szData = pDoc.getTitle();
                  /**/
                  try
                  {
                     if(pCurrent.Value.equalsIgnoreCase(Properties.CPF))
                        szData += "/" + pDoc.getField(Properties.NAME).toString();
                     else
                        szData = pDoc.getField(Properties.NAME).toString();
                  }
                  catch(Exception e)
                  {
                     szData = pDoc.getTitle();
                  }
                     
                  debug("Doc " + szData);
                  getDocumentService().lockDocument(this.m_pDoc).setField(pCurrent.Value, szData);
               }
            }
         }
      }
   };
   /**********************************************************************************************
    * launch operations 
    * 
    */
   UpdateProperties p = new UpdateProperties(document);
   /*
    * check if manual
    */
   if(document.getRichText().indexOf(Properties.STARTKEY) != -1)
      return;
   
   p.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}