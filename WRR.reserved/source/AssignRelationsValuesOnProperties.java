/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 12-18-2014
*  Description assign any properties with value of selected relations
*  Last update 2-23-2015
*  Last update 2-27-2015
*  Last update 3-18-2015 disabled run
*/
try
{
   /*
    * ListOfClass=Contas a Pagar@Faturamento
    * Contas a Pagar.relations=Coligada@Fornecedor@Tipo de Documento@Centro de Custo
    * Contas a Pagar.properties=Coligada@CNPJ/CPF Fornecedor@Tipo de Documento@Centro de Custo
    * 
    * Faturamento.relations=Coligada@Contrato@Tipo de Documento
    * Faturamento.properties=Coligada@Contrato@Tipo de Documento
    * 
    * RIC.relations=
    * RIC.properties=Coligada@Serviço@Mercado@Cliente@Centro de Custo@Contrato
    */
   class AssignRelationsValuesOnProperties
   {
      public String[] ListOfClass = getParam("ListOfClass").split("@");
      public ReadableDocument Doc = null;
      public AssignRelationsValuesOnProperties(ReadableDocument pDoc) throws Exception
      {
         this.Doc = pDoc;
         /* check class quantity */
         if(this.ListOfClass.length == 0)
        	 throw new Exception("Lista de classes esta vazia!");
      }
      public boolean Assign() throws Exception
      {
         String[] pRelations = getParam(this.Doc.getDocumentClass()+".relations").split("@");
         String[] pProperties = getParam(this.Doc.getDocumentClass()+".properties").split("@");
         String szClassName = this.Doc.getDocumentClass();
         /* loop about relations list */
         if(pRelations.length != pProperties.length || pRelations.length == 0 || pProperties.length == 0)
        	 throw new Exception(
        			 String.format("Encontrada discrepância de tamanho entre propriedades e relações!"));
         /***/
         for(int nRow = 0; nRow < pRelations.length; nRow++)
         {
            String szName = pRelations[nRow];
            String szPropertyName = pProperties[nRow];
            ArrayList<String> pID = (ArrayList<String>)
            		this.Doc.getToRelatedDocumentIds(szClassName + "->" + szName);
            /***/
            if(pID.size() == 0)
            	return false;
            
            if(!pID.isEmpty())
            {
               String szValue = (String)pID.get(0);
               ReadableDocument pDoc = getDocumentService().loadReadableDocument(szValue);
               /**/
               szValue = pDoc.getTitle();
               /**/
               getDocumentService().lockDocument(this.Doc).setField(szPropertyName, szValue);
            }
            else
            {
            	throw new Exception(String.format("Relação %s não existe ou não encontrada!", (String)pID.get(0)));
            }
         }
         
         return true;
      }
   }
   //
   AssignRelationsValuesOnProperties p = new AssignRelationsValuesOnProperties(document);
   p.Assign();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}