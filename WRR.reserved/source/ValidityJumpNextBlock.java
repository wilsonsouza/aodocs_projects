/*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-12-2014
*  Description change value of defined property
*/
try
{
   class DataList
   {
      public String Name = null;
      public String Value = null;
      public DataList(String szName, String szValue) throws Exception
      {
         Name = new String(szName);
         Value = new String(szValue);
      }
   }
   //
   class LoadParameters
   {
      public String[] FieldList = null; //getParam("FieldList");
      public String[] StateList = null;
      private ArrayList<DataList> m_pDataList = new ArrayList<DataList>();
      public LoadParameters() throws Exception
      {
         //
         if(getParam("FieldList") == null)
            throw new Exception("Parametro FieldList não existe!");
         //
         if(getParam("StateList") == null)
            throw new Exception("Parametro StateList não existe!");
         //
         FieldList = getParam("FieldList").split("@");
         StateList = getParam("StateList").split("@");
         //
         if(FieldList.size() != StateList.size())
            throw new Exception("Discrepância encontrada entre lista de campos e estados!");
         //
         if(FieldList.size() == 0 || StateList.size() == 0)
            throw new Exception("Lista de campo(s) ou estado(s) com tamanho igual a zero!");
         //
         for(int i = 0; i < StateList.size(); i++)
         {
            this.m_pData.Add(StateList[i].trim(), FieldList[i].trim());
         }  
      }
   }   
   //
   class VerifyField extends LoadParameters
   {
      private ReadableDocument m_pDoc = null;
      public VerifyField(ReadableDocument pDoc)
      {
         super();
         this.m_pDoc = pDoc;
      }
      public void ReadField(String szFieldName) throws Exception
      {
         Object pData = this.m_pDoc.getField(szFieldName);

         if(pData == null)
            throw new Exception(
            String.format("Erro: campo %s não pode ficar em branco!", szFieldName));
      }
      public void ProcessSubFields(String[] pFields) throws Exception
      {
         for(String szField: pFields)
            this.ReadField(szField);
      }
      public void Execute() throws Exception
      {
         String szState = this.m_pDoc.getDocumentState();
         //
         for(DataList pData: this.m_pDataList)
         {
            if(pData.Name.equalsIgnoreCase(szState))
            {
               if(pData.Value.indexOf(":") != -1)
                  this.ReadField(pData.Value.trim());
               else
                  this.ProcessSubFields(pData.Value.split(":"));
            }
         }
      }
   }
   //
   VerifyField pCheck = new VerifyField(document);
   pCheck.Execute();                  
}
catch(Exception e)
{
   debug("Error: " + e.getMessage());
   throw e;
}