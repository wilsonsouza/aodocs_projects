/*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-12-2014
*  Description change value of defined property
*  Last updated 9-16-2014
*/
try
{
   class Properties
   {
      public static String MAKEARRAY = ";"; 
      public static String Homologation = "HOMOLOGACAO";
      public static String HomologationPartialName = "HOMOLOGA";
      public static String None = "none";
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
   class Metadata
   {
      public String State = null;
      public String Property = null;
      public Metadata(String szName, String szValue)
      {
         this.State = szName;
         this.Property = szValue;
      }
   };
   /**********************************************************************************************/
   class Parameters
   {
      private String[] m_pAdmission_State      = getParam("Admission_State").split(Properties.MAKEARRAY);
      private String[] m_pAdmission_Properties = getParam("Admission_Properties").split(Properties.MAKEARRAY);
      private String[] m_pDemission_State      = getParam("Demission_State").split(Properties.MAKEARRAY);
      private String[] m_pDemission_Properties = getParam("Demission_Properties").split(Properties.MAKEARRAY);
      private String[] m_pRequest_State        = getParam("Request_State").split(Properties.MAKEARRAY);
      private String[] m_pRequest_Properties   = getParam("Request_Properties").split(Properties.MAKEARRAY);
      private ArrayList<Metadata> m_pList      = new ArrayList<Metadata>();
      public Parameters()
      {
         debug(String.format("A:%d == %d", this.m_pAdmission_State.length, this.m_pAdmission_Properties.length));
         debug(String.format("D:%d == %d", this.m_pDemission_State.length, this.m_pDemission_Properties.length));
         debug(String.format("R:%d == %d", this.m_pRequest_State.length, this.m_pRequest_Properties.length)); 
      }
      public ArrayList<Metadata> IndexOf(ReadableDocument pDoc) throws Exception
      {
         WhatDocumentClass pWhat = new WhatDocumentClass(pDoc);
         /**/
         this.m_pList.clear();
         
         if(pWhat.IsAdmission())
         {
            for(int i = 0; i < this.m_pAdmission_State.length; i++)
               this.m_pList.add(new Metadata(this.m_pAdmission_State[i].trim(), this.m_pAdmission_Properties[i].trim()));
            return this.m_pList;
         }
         /***/
         if(pWhat.IsDemission())
         {
            for(int i = 0; i < this.m_pDemission_State.length; i++)
               this.m_pList.add(new Metadata(this.m_pDemission_State[i].trim(), this.m_pDemission_Properties[i].trim()));
            return this.m_pList;
         }
         /**/
         if(pWhat.IsRequestStaff())
         {
            for(int i = 0; i < this.m_pRequest_State.length; i++)
               this.m_pList.add(new Metadata(this.m_pRequest_State[i].trim(), this.m_pRequest_Properties[i].trim()));
            return this.m_pList;
         }
         return this.m_pList;
      }
   };
   /**********************************************************************************************/
   class Update
   {
      private ReadableDocument m_pDoc = null;
      public Update(ReadableDocument pDoc) throws Exception
      {
         this.m_pDoc = pDoc;
      }
      public boolean Execute() throws Exception
      {
         String szActualDocState = this.m_pDoc.getState();
         Parameters p = new Parameters();
         ArrayList<Metadata> pList = p.IndexOf(this.m_pDoc);
         /**/
         for(Metadata pData: pList)
         {
            if(pData.State.equalsIgnoreCase(szActualDocState))
            {
               if(pData.Property.equalsIgnoreCase(Properties.None))
                  return false;
               /**/
               getDocumentService().lockDocument(this.m_pDoc).setField(pData.Property, true);
               return true;
            }
         }
         return false;
      }
   };
   /**********************************************************************************************/
   Update pUpdate = new Update(document);
   pUpdate.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
