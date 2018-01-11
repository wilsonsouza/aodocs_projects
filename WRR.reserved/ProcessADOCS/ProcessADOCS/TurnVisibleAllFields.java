/*
*
*  Copyright (C) 2015 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 04-09-2014
*  Description turn visible all string and date fields
*  Last updated
*/
try
{
   class TurnVisibleAllFields
   {
      public String[] FieldList = null; //get field list
      public ReadableDocument m_pDoc = null;
      public TurnVisibleAllFields(ReadableDocument pDoc) throws Exception
      {
    	 this.m_pDoc = pDoc;
    	  
         if(getParam("FieldList") == null)
            return;
         //
         if((this.FieldList = getParam("FieldList").split("@")).length == 0)
            return;
         
         this.Execute();
      }
      public void Execute() throws Exception
      {
         for(String szFieldName: this.FieldList)
         {
           Object pField = this.m_pDoc.getField(szFieldName);
           //
//           debug(String.format("FieldName %s", szFieldName));
//           //
//           debug(String.format("Type %s and pField %s", 
//        		   pField instanceof String? "String": 
//        			   pField instanceof Date? "Date": "Object",
//        					   pField == null? "null": "is not null"));
           //
           if(szFieldName.indexOf("Data ") != -1 ||
        		   szFieldName.indexOf("data ") != -1)
           {
        	   if(pField == null)
        	   {
        		  SimpleDateFormat pSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        		  Date pDate = new Date();
        		  //
	              getDocumentService().
	                 lockDocument(this.m_pDoc).
	                    setField(szFieldName, new Date(pSimpleDateFormat.format(pDate)));
        	   }
              return;
           }
           //
           if(((String)pField).trim().isEmpty())
           {
	           getDocumentService().
	           	lockDocument(this.m_pDoc).
	           		setField(szFieldName, new String(" "));
           }
         }
      }
   }
   //
   TurnVisibleAllFields pTurn = new TurnVisibleAllFields(document);
   //force free memory
   pTurn = null;
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
