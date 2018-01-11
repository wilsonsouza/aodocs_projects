/*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-12-2014
*  Description change value of defined property
*  Last updated 9-16-2014
*  Last updated 2-23-2015
*  Last updated 2-26-2015
*  Last updated 3-02-2015
*  
*  RIC.state=Controladoria@Contabilidade@RH@TI@Compras@Fiscal@Faturamento@Fim
*  RIC.properties=none@none@none@Recrutamento:Sessão:Jornada Trabalho:Sindicato@none@Equipamentos TI:
*  Serviços Telecom@none@Requisições de Compra@none@Nova Filial:Cadastro do Cliente:Criação Itens de Serviço:Regras Fiscais
*/
try
{
   class Properties
   {
      public static String None = "none";
   };
   /**********************************************************************************************/
   class ClassID
   {
      public static int AccountsPlayable = 0;
      public static int Billing = 1;
   }
   /**********************************************************************************************/
   class WhatDocumentClass
   {
      private ReadableDocument m_pDoc = null;
      private String m_szActualClass = null;
      public String[] ListOfClass = null;
      public WhatDocumentClass(ReadableDocument pDoc) throws Exception
      {
         /* parameter
          * ListOfClass must be: Contas a Pagar@Faturamento
          */
         this.m_pDoc = pDoc;
         this.m_szActualClass = this.m_pDoc.getDocumentClass().toLowerCase().trim();
         /**/
         if(getParam("ListOfClass") == null)
            throw new Exception("Campo com lista de classes não encontrada na lista!");
         /**/
         this.ListOfClass = getParam("ListOfClass").split("@");
      }
      public boolean IsThatClass(int nID) throws Exception
      {
         debug("Class selected " + this.ListOfClass[nID]);
         return this.m_szActualClass.equalsIgnoreCase(this.ListOfClass[nID]);
      }
      public String GetCurrentClassName() throws Exception
      {
         String szClassName = this.m_pDoc.getDocumentClass();
         /**/
         for(String szName: this.ListOfClass)
         {
            if(szName.trim().equalsIgnoreCase(szClassName))
               return szName.trim();
         }
         return null;
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
      public Parameters()
      {
      }
      /*
       * 
       * Contas a Pagar.properties=
       * none@none@none@Fornecedor no sistema$Dados Bancarios@Documento Inserido@Pagamento Feito 
       * Contas a Pagar.state=Arquivista@Departamentos@Compras@Fiscal@Financeiro
       */
      public ArrayList<Metadata> IndexOf(ReadableDocument pDoc) throws Exception
      {
         WhatDocumentClass pWhat = new WhatDocumentClass(pDoc);
         ArrayList<Metadata> pList = new ArrayList<Metadata>();
         String szName = pWhat.GetCurrentClassName();
         String[] pData = getParam(szName + ".properties").split("@");
         String[] pState = getParam(szName + ".state").split("@");
         /**/
         for(int i = 0; i < pState.length; i++)
         {
            byte[] byData = pData[i].trim().getBytes("UTF-8");
            byte[] byState = pState[i].trim().getBytes("UTF-8");
            pList.add(new Metadata(new String(byState, "UTF-8"), new String(byData, "UTF-8")));
            /**/
            debug(String.format("byData %s", pData[i].trim()));
         }
         /**/
         return pList;
      }
   };
   /**********************************************************************************************/
   class UpdatePropertyWhenChangeState extends Parameters
   {
      private ReadableDocument m_pDoc = null;
      public UpdatePropertyWhenChangeState(ReadableDocument pDoc) throws Exception
      {
         super();
         this.m_pDoc = pDoc;
      }
      public boolean Execute() throws Exception
      {
         String szActualDocState = this.m_pDoc.getState();
         ArrayList<Metadata> pList = super.IndexOf(this.m_pDoc);
         /**/
         for(Metadata pData: pList)
         {
            debug("S:" + pData.State + " P:" + pData.Property);
            
            if(pData.State.equalsIgnoreCase(szActualDocState))
            {
               if(pData.Property.equalsIgnoreCase(Properties.None))
                  return false;
               /**/
               debug(String.format("Property index %d", pData.Property.indexOf(":")));
               
               if(pData.Property.indexOf(":") == -1)
                  getDocumentService().lockDocument(this.m_pDoc).setField(pData.Property, true);
               else
               {
                  String[] pQueue = pData.Property.split(":");
                  /* array list */
                  //debug(String.format("Offset %d %s-%s", pQueue.length, pQueue[0], pQueue[1] ));
                  /**/
                  for(String szName: pQueue)
                     getDocumentService().lockDocument(this.m_pDoc).setField(szName, true);
               }
               return true;
            }
         }
         return false;
      }
   };
   /**********************************************************************************************/
   UpdatePropertyWhenChangeState pUpdate = new UpdatePropertyWhenChangeState(document);
   pUpdate.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}