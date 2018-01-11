/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 11-24-2014
*  Description decode and mount document body
*  Last updated 03-02-2015
*  Last updated 03-18-2015 ware removed all controls of relations
*  Last updated 06-04-2015 fix name Data Emissão to Data Emissao
*  
*  the four first elements must be utilized for relations
*  Coligada String Tabela associada com lista de todas as coligadas
*  CNPJ/CPF do Fornecedor String Código de CNPJ ou CPF do Fornecedor
*  Centro de Custo String Tabela associada com lista de os centros de custo
*  Tipo de Documento String Tabela associada com lista de todos os tipos de documentos de 
*  
*  Data Emissão Date Data de emissão do documento
*  Data de Vencimento String Data de vencimento da fatura
*  Fornecedor no sistema Sim/Não Opção de selecionar sim ou não (default será Não)
*  Código do Fornecedor String Tabela associada com lista de todos os fornecedores
*  Dados Bancarios Sim/Não Opção de selecionar sim ou não (default será Não)
*  Numero OC String Número de ordem de compra
*  Documento Inserido Sim/Não Opção de selecionar sim ou não (default será Não)
*  Numero Transação String Número de transação da fatura no sistema
*  Pagamento Feito Sim/Não Opção de selecionar sim ou não (default será Não)
*  Data de Pagamento Date Data de pagamento da fatura
*  
*  classes=Coligada@Fornecedor@Centro de Custo
*  <class>.relations=Coligada@Fornecedor@Centro de Custo@Tipo de Documento
*  <class>.fields=Coligada@Fornecedor@Tipo de Documento@Data Emissão
*  
*/
try
{
   class Parameters
   {
      static public String ListClass = "classes";
      static public String ListRelations = ".relations";
      static public String FieldsOnEmail = ".fields";
      static public String DocumentTop = "---METADADOS---";
      static public String DocumentEnd = "---";
      static public String DivideLine = ":";
      static public String RemoveTokens = "\\<.*?>";
      static public String GenerateArray = ";";
      static public String RemoveLineControl = "\r";
   }   
   /**********************************************************************************************/
   class SpecialProperties
   {
      static public String IsDocumentAlreadyProcessed = "IsDocumentAlreadyProcessed"; //boolean
      static public String CNPJ_CPF_Forncedor = "CNPJ/CPF Fornecedor"; 
   }
   /**********************************************************************************************/
   class Messages
   {
      static public String DocStartNotFound = "Marcador inicial do METADADO não encontrado no corpo do e-mail!";
      static public String DocEndNotFound = "Marcador final do METADADO não encontrado no corpo do e-mail!";
   }
   /**********************************************************************************************/
   class LoadFieldsOnEmail
   {
      private String m_szFieldsOnEmailData = null;
      private ReadableDocument m_pDoc = null;
      private String m_szClassName = null;
      public LoadFieldsOnEmail(ReadableDocument pDoc) throws Exception
      {
    	  this.m_pDoc = pDoc;
    	  this.m_szClassName = pDoc.getDocumentClass();
    	  this.m_szFieldsOnEmailData = new String(getParam(this.m_szClassName + Parameters.FieldsOnEmail));
    	  //
         if(this.m_szFieldsOnEmailData.isEmpty())
            throw new Exception("Paramêtro com lista de propriedades do e-mail não encontrado!");
      }
      public String[] GetFields() throws Exception
      {
         return this.m_szFieldsOnEmailData.split("@");
      }
      public String IndexOf(String szValue) throws Exception
      {
         String[] pFields = this.GetFields();
         /**/
         for(String szData: pFields)
         {
        	 String szFmt = new String(szValue.getBytes("UTF-8"), "UTF-8");
        	 String szRec = new String(szData.getBytes("UTF-8"), "UTF-8");
        	 
            if(szRec.trim().equalsIgnoreCase(szFmt))
               return szRec.trim();
         }
         return new String();
      }
   }
   /**********************************************************************************************/
   class Offset
   {
      public int X = -1;
      public int Y = -1;
      public String Data = null;
      public Offset(){}
      public Offset(int x, int y, String szData)
      {
         this.X = x;
         this.Y = y;
         this.Data = new String(szData);
      }
   }
   /**********************************************************************************************/
   class DataQueue 
   {
      public String Name = null;
      public String Value = null;
      public DataQueue(){}
      public DataQueue(String szName, String szValue)
      { 
         this.Name = new String(szName); 
         this.Value = new String(szValue); 
      }
   }
   /**********************************************************************************************/
   class Parse
   {
      private ArrayList<DataQueue> m_pList = new ArrayList<DataQueue>();
      private Offset m_pOffset = null;
      public Parse(String szData)
      {
         /* initialize buffer */
         this.m_pOffset = new Offset(-1, -1, szData);
      }
      public boolean IsMustProcess() throws Exception
      {
         if(this.m_pOffset.Data.indexOf(Parameters.DocumentTop) == -1)
            return false;
         /**/
         return true;
      }
      public Offset Entrance() throws Exception
      {
         if((this.m_pOffset.X = this.m_pOffset.Data.indexOf(Parameters.DocumentTop)) == -1)
            throw new Exception(Messages.DocStartNotFound);
         /**/
         this.m_pOffset.X += Parameters.DocumentTop.length();
         this.m_pOffset.Data = this.m_pOffset.Data.substring(this.m_pOffset.X);
         /**/
         debug("Entrance:" + this.m_pOffset.Data);
         return this.m_pOffset;
      }
      public Offset Finish() throws Exception
      {
         if((this.m_pOffset.Y = this.m_pOffset.Data.indexOf(Parameters.DocumentEnd)) == -1)
            throw new Exception(Messages.DocEndNotFound);
         /**/
         this.m_pOffset.Data = this.m_pOffset.Data.substring(0,  this.m_pOffset.Y);
         debug("Finish:" + this.m_pOffset.Data);
         return this.m_pOffset;
      }
      public Offset RemoveTags() throws Exception
      {
         this.m_pOffset.Data = this.m_pOffset.Data.replaceAll(Parameters.RemoveTokens, "");
         this.m_pOffset.Data = this.m_pOffset.Data.trim();
         return this.m_pOffset;
      }
      public ArrayList<DataQueue> GetDataQueue() throws Exception
      {
         Offset p = this.m_pOffset;
         String[] pData = p.Data.split(Parameters.GenerateArray);
         /**/
         this.m_pList.clear();
         /**/
         for(String szData: pData)
         {
            String[] pQueue = szData.split(Parameters.DivideLine);
            /* debug */
            debug("Row:" + szData);
            
            /* case invalid line or array */
            if(pQueue.length != 0x2)
               continue;
            /**/
            /* check boolean line */
            String szBoolean = pQueue[1].trim().toLowerCase();
            /**/
            if(szBoolean.equalsIgnoreCase("true"))
               pQueue[1] = "true";
            else if(szBoolean.equalsIgnoreCase("false"))
               pQueue[1] = "false";
            /****/
            this.m_pList.add(new DataQueue(pQueue[0].trim(), pQueue[1].trim()));
         }
         /**/
         return this.m_pList;
      }
      public void Process() throws Exception
      {
         debug("Started process...");
         this.Entrance();
         this.Finish();
         this.RemoveTags();
         debug("Finalized process...");
      }
   }
   /**********************************************************************************************/
   class ClassNames
   {
      public ClassNames()
      {
      }
      public boolean IsAllowed(ReadableDocument pDoc) throws Exception
      {
    	  String[] szClasses = getParam(Parameters.ListClass).split("@");
    	  //
    	  for(String szName: szClasses)
    	  {
    		  if(pDoc.getDocumentClass().equalsIgnoreCase(szName))
    			  return true;
    	  }
    	  return false;
      }
      /* search document  - DataQueue {Name:ClassName, Value:Data} */
      public ReadableDocument SearchDocument(DataQueue pData) throws Exception
      {
         DocumentSearchRequest pDoc = new DocumentSearchRequest(pData.Name);
         ArrayList<ReadableDocument> pDocList = (ArrayList<ReadableDocument>)getDocumentService().findDocuments(pDoc);
         /* check content */
         if(pDocList.size() == 0)
            throw new Exception(String.
            		format("Classe %s não contêm nenhum documento registrado!", pData.Name));
         /**/
         debug(pData.Name + "=" + pData.Value);
         /* search by document name */
         for(ReadableDocument p: pDocList)
         {
    		 String szCaptions = p.getTitle().toUpperCase().trim();
    		 String szValue = pData.Value.toUpperCase().trim();
    		 Object pName = p.getField("Nome");
    		 String szClass = p.getDocumentClass();
    		 /**/
    		 /**/
    		 debug(String.format("Title %s Position %d", szCaptions, szCaptions.indexOf(szValue)));
    		 /**/
    		 if(szCaptions.indexOf(szValue) != -1)
    			 return p;
    		 /* verify object name if is not null */
     		 if(pName != null)
    		 {
    			 if(pName.toString().equalsIgnoreCase(szValue))
    				 return p;
    		 }
    		 /* compare class name */
    		 if(szClass.equalsIgnoreCase("Fornecedor"))
    		 {
    			 Object pDocId = p.getField("CPF-CNPJ");
    			 
    			 if(pDocId != null)
    			 {
    				 if(pDocId.toString().equalsIgnoreCase(pData.Value))
    					 return p;
    			 }
    		 }
         }
         /* document is not found */
         return null;
      }
   }
   /**********************************************************************************************/
   class DecodeEmail extends Parse
   {
      private String m_szBuffer = null;
      private ReadableDocument m_pDoc = null;
      private String[] m_pRelations = null;
      private String m_szRelations = null; 
      public DecodeEmail(ReadableDocument pDoc) throws Exception
      {
         super(pDoc.getRichText());
         this.m_pDoc = pDoc;
         this.m_szRelations = new String(getParam(pDoc.getDocumentClass() + Parameters.ListRelations));
         /* check */
         if(this.m_szRelations.isEmpty())
            throw new Exception("Lista de relações não encontrada ou esta vazia!");
         
         /* assign */
         this.m_pRelations = this.m_szRelations.split("@");
      }
      public String SearchRelation(String szClassName) throws Exception
      {
         for(String szData: this.m_pRelations)
            if(szData.indexOf(szClassName) != -1)
               return szData;
         /**/
         return new String();
      }
      public void Decode() throws Exception
      {
         super.Process();
      }
      /* process all metadata on e-mail */
      public void Validate() throws Exception
      {
         ClassNames pClass = new ClassNames();
         LoadFieldsOnEmail pField = new LoadFieldsOnEmail(this.m_pDoc);
         ArrayList<DataQueue> pQueue = super.GetDataQueue();
         /* debug */
         debug(String.format("Offset: %d", pQueue.size()));
         /* verify if all documents exists */
         for(DataQueue pData: pQueue)
         {
            String szClassName = null;
            /**/
            debug("Property:" + pData.Name + " Value:" + pData.Value);
            /**/
            if(!(szClassName = pField.IndexOf(pData.Name)).isEmpty())
            {
            	debug("class " + szClassName);
               /* create new data process with new property */
               DataQueue p = new DataQueue(szClassName, pData.Value);
//               DataQueue pDocProc = new DataQueue(p.Name, p.Value);
//               /* prepare to special property name */
//               if(p.Name.equalsIgnoreCase(SpecialProperties.CNPJ_CPF_Forncedor))
//            	   pDocProc.Name = "Fornecedor";
//               /**/
//               ReadableDocument pDoc = pClass.SearchDocument(pDocProc);
//               /**/
//               if(pDoc == null)
//                  throw new Exception(String.
//                		  format("Documento %s propriedade %s relacionado à classe %s não encontrado!",
//                				  pData.Value, p.Name, pDocProc.Name));
//               /**/
//               String szRelation = this.SearchRelation(pDocProc.Name);
//               /* set new name */
//               p.Value = pDoc.getTitle();
//               /**/
//               debug(String.format("Relation %s", szRelation));
//               
//               if(!szRelation.isEmpty())
//               {
//                  this.GenerateRelationsLink(szRelation, pDoc.getId());
                  this.WritePropertiesValue(p);
//               }
            }
            else
            {
               /* case property isn´t exists generate an exception */
               throw new Exception(String.format("Propriedade %s não encontrada ou não existe!", pData.Name));
            }
         }
      }
      public void GenerateRelationsLink(String szRelationName, String szDocId) throws Exception
      {
    	  String szRelation = this.m_pDoc.getDocumentClass() + "->" + szRelationName;
    	  /* create new relation on document */
//          getDocumentService().lockDocument(this.m_pDoc).addToRelatedDocumentId(szRelation, szDocId);
      }
      public void WritePropertiesValue(DataQueue pData) throws Exception
      {
         String szPropertyName = "Data Emissão";
         /* all values must to be put in property */
         if(pData.Name.equalsIgnoreCase(szPropertyName) || pData.Name.equalsIgnoreCase("Data Emissao"))
         {
            Date pDate = new Date(Date.parse(pData.Value));
            /* change to correctly name */
            pData.Name = szPropertyName;
            /* set */
            getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, pDate);
            return;
         }
         /**/
         if(pData.Name.equalsIgnoreCase("Posição") || pData.Name.equalsIgnoreCase("Posicao"))
         {
            pData.Name = "Posição";
         }
         /**/
         if(pData.Name.equalsIgnoreCase("Serviço") || pData.Name.equalsIgnoreCase("Servico"))
         {
            pData.Name = "Serviço";
         }
         /* write */
         debug(String.format("Write %s=%s", pData.Name, pData.Value));
         getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, pData.Value);
      }
      public void Execute() throws Exception
      {
         this.Decode();
         this.Validate();
      }
   }
   /**********************************************************************************************/
   class ProcessAccountsPayableDocBody extends DecodeEmail 
   {
      public ProcessAccountsPayableDocBody(ReadableDocument pDoc) throws Exception
      {
         super(pDoc);
         this.m_pDoc = pDoc;
      }
      @Override
      public void Execute() throws Exception
      {
    	  ClassNames p = new ClassNames();
         /* check if must be processed */
         if(!super.IsMustProcess())
            return;
         /* check class */
         if(!p.IsAllowed(this.m_pDoc))
            return;
         
         /* when used the word super call super method override */
         super.Execute();
      }
   }
   /**********************************************************************************************/
   ProcessAccountsPayableDocBody pAcc = new ProcessAccountsPayableDocBody(document);
   pAcc.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}