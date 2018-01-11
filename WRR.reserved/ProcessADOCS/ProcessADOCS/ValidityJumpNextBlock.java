/*
*  Copyright (C) 2015 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 4-07-2014
*  Description change value of defined property
*  Last update by wilson.souza 04-07-2015
*  Last update 05-08-2015 fix new field of type double to validation
*  
*  format: 
*  first=<state>@<state>@<...> 
*  complement=<field>@<field[:seguence:[...]]>@<...>
*
*  sample: 
*  RH@TI@Fim
*  Cadastro Cliente:Aceita Faturamento@Aceita Arquivo@Aceita Arquivo
*
*  must have same amount of separators
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
         this.FieldList = getParam("FieldList").split("@");
         this.StateList = getParam("StateList").split("@");
         //
         if(this.FieldList.length != this.StateList.length)
            throw new Exception("Discrepância encontrada entre lista de campos e estados!");
         //
         if(this.FieldList.length == 0 || this.StateList.length == 0)
            throw new Exception("Lista de campo(s) ou estado(s) com tamanho igual a zero!");
         //
         for(int i = 0; i < this.StateList.length; i++)
            this.m_pDataList.add(new DataList(this.StateList[i].trim(), this.FieldList[i].trim()));
         //
         return;
      }
   }   
   //
   class ValidityJumpNextBlock extends LoadParameters
   {
      private ReadableDocument m_pDoc = null;
      public ValidityJumpNextBlock(ReadableDocument pDoc) throws Exception
      {
         super();
         this.m_pDoc = pDoc;
      }
      public void ReadField(String szFieldName) throws Exception
      {
         Object pObject = this.m_pDoc.getField(szFieldName);

         if(pObject == null)
            throw new Exception(
            String.format("Campo %s não pode ser nulo!", szFieldName));
         //
         if(pObject instanceof Date)
         {
        	 Date d = new Date(pObject.toString());
	         //
        	 //debug(String.format("Field %s Value %s", szFieldName, pObject.toString()));
        	 //
        	 if(d.toString().isEmpty())
        		 throw new Exception(
        				 String.format("Por favor selecione uma data valida para continuar!"));
        	 //
        	 return;
         }
         //
		 if(pObject instanceof Double)
		 {
			Double d = (Double)pObject;
			/***/
			if(d == 0)
				throw new Exception(
					String.format("Campo %s necessita conter um valor maior que zero!", szFieldName));
			return;
		 }

         if(((String)pObject).trim().isEmpty())
            throw new Exception(
            String.format("Campo %s não pode ficar em branco!", szFieldName));
      }
      public void ProcessSubFields(String[] pFields) throws Exception
      {
         for(String szField: pFields)
            this.ReadField(szField);
      }
      public void Execute() throws Exception
      {
         String szState = this.m_pDoc.getState();
         //
         for(DataList pData: this.m_pDataList)
         {
            if(pData.Name.equalsIgnoreCase(szState))
            {
               String[] pSub = pData.Value.split(":");
          	   /* check property name */
          	   if(pData.Value.equalsIgnoreCase("none"))
          		  continue;

               if(pSub.length == 0)
                  this.ReadField(pData.Value.trim());
               else
                  this.ProcessSubFields(pSub);
            }
         }
      }
   }
   //execute approch
   ValidityJumpNextBlock pCheck = new ValidityJumpNextBlock(document);
   pCheck.Execute();                  
}
catch(Exception e)
{
   debug("Error: " + e.getMessage());
   throw e;
}
/* eof */