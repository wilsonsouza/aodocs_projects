/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-15-2014
*  Description parse all body of document and update all metadata of self.
*  Last updated 08-19-2014
*  Last updated 09-01-2014
*  Last updated 10-03-2014 - added try catch to controls exception and non stop process
*/
try
{
   class Properties
   {
      public class Common
      {
         public static String ISDOCUMENTCREATEDBYEMAIL = "IsDocumentCreatedbyEmail";
         public static String STORELISTMETADATA        = "StoreListMetadata";
         public static String STARTFAILED              = "Marcador inicial do METADADO não encontrado no corpo deste e-mail!";
         public static String ENDFAILED                = "Marcador final do METADADO não encontrado no corpo deste e-mail!";
         public static String Name                     = "Nome";
         public static String Status                   = "Status";
         public static String Admission                = "admissão";
         public static String Demission                = "demissão";
         public static String OriginalDocKind          = "Tipo de documento";
         public static String OriginalAdmission        = "Contratação";
         public static String AdmissionParameter       = "Admission";
         public static String OriginalDemission        = "Demissão";
         public static String DemissionParameter       = "Demission";
         public static String RequeststaffParameter    = "Request";
      }
      /**/
      public class Classes
      {
         public static String CPF          = "CPF";
         public static String Company      = "Coligada";
         public static String Relations    = "Relations";
         public static String DocumentKind = "Tipo de Documento"; 
         public static String Contract     = "Contrato";
         public static String Requeststaff = "Requisição de pessoal";
         public static String Position     = "Posição";
      };
      //class property to identify all names in use on source code
      public class MetadataKey
      {
         public static String START     = "---METADADOS---";
         public static String END       = "---";
         public static String DIVLINE   = ":";
         public static String DELTRASH  = "\\<.*?>";
         public static String MAKEARRAY = ";";
         public static String DELLINE   = "\r";
      }
      /**/
      public class Logical
      {
         public static String NO        = "NÃO";
         public static String YES       = "SIM";
         public static String True      = "true";
         public static String False     = "false";
      }
      /**/
      /* CPF */
      public class CPF
      {
         public static String Title = "Titulo";
         public static String Requeststaff = "Requisição de Pessoal";
      };
      /* Admission */
      public class Adimission
      {
         public static String Company  = "Coligada";
         public static String Contract = "Contrato";
         public static String DocKind  = "Tipo de Documento";
         public static String CPF      = "CPF";
         public static String ReviewD  = "Revisão Departamento";
         public static String Accepted = "Aceite Cadastro";
         public static String Received = "Recebimento Arquivo";
      };
      /***/
      public class MainClasses
      {
         public static String Admission    = "Documentos de admissão";
         public static String Demission    = "Documentos de demissão";
         public static String Requeststaff = "Requisição de pessoal";
      }
   };
   /**********************************************************************************************/
   class ValidateProperty
   {
      public static boolean Eval(ReadableDocument pDoc, String szPropertyName)
      {
         return Boolean.parseBoolean(pDoc.getField(szPropertyName).toString());
      }
   };
   /**********************************************************************************************/
   class ValidateDocumentClass
   {
      public static boolean IsAdmission(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(Properties.MainClasses.Admission.toLowerCase());
      }
      public static boolean IsDemission(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(Properties.MainClasses.Demission.toLowerCase());
      }
      public static boolean IsRequestStaff(ReadableDocument pDoc)
      {
         return pDoc.getDocumentClass().toLowerCase().equals(Properties.MainClasses.Requeststaff.toLowerCase());
      }
   };
   /**********************************************************************************************/
   //class of property of metadata of document
   class Metadata
   {
      public String Name  = null;
      public String Value = null;
      public Metadata()
      {
         this.Name  = new String();
         this.Value = new String();
      }
      public Metadata(String szName, String szValue)
      { 
         this.Name = szName; 
         this.Value = szValue; 
      }
   };
   /**********************************************************************************************/
   class Offset
   {
      public int Start   = -1;
      public int End     = -1;
      public String Data = null;
      public Offset()
      {
         this.Start = -1;
         this.End   = -1;
         this.Data  = new String();
      }
      public Offset(int nStart, int nEnd, String szData)
      {
         this.Start = nStart;
         this.End   = nEnd;
         this.Data  = szData;
      }
   };
   /**********************************************************************************************/
   class Parse
   {
      private ArrayList<Metadata> List = new ArrayList<Metadata>();
      private Offset Data = null;
      /**/
      public Parse(String szBuffer)
      {
         this.Data = new Offset(-1, -1, szBuffer);
      }
      public Offset SearchEntrance() throws Exception
      {
         Offset p = this.Data;
         p.Start = p.Data.indexOf(Properties.MetadataKey.START);
         //check result
         if(p.Start == -1)
            throw new Exception(Properties.Common.STARTFAILED);
         //
         p.Start += Properties.MetadataKey.START.length();
         p.Data = p.Data.substring(p.Start);
         return p;
      }
      public Offset SearchFinal() throws Exception
      {
         Offset p = this.Data;
         p.End = p.Data.lastIndexOf(Properties.MetadataKey.END);
         //
         if(p.End == -1)
            throw new Exception(Properties.Common.ENDFAILED);
         //
         p.Data = p.Data.substring(0, p.End);
         return p;
      }
      public Offset RemoveTags()
      {
         Offset p = Data;
         /**/
         p.Data = p.Data.replaceAll(Properties.MetadataKey.DELTRASH, "");
         p.Data = p.Data.trim();
         return p;
      }
      public ArrayList<Metadata> MountListOfMetadata() throws Exception
      {
         Offset p = Data;
         String[] pALines = p.Data.split(Properties.MetadataKey.MAKEARRAY);
         /**/
         //debug(String.format("pALines %d", pALines.length));
         //
         for(String szLine: pALines)
         {
            String[] pData = szLine.split(Properties.MetadataKey.DIVLINE);
            /**/
            if(pData.length != 2)
               continue;
            /**/
            if(pData[1].trim().toLowerCase().equalsIgnoreCase(Properties.Logical.NO.toLowerCase()))
               pData[1] = "false";
            /**/
            if(pData[1].trim().toLowerCase().equalsIgnoreCase(Properties.Logical.YES.toLowerCase()))
               pData[1] = "true";
            /**/
            this.List.add(new Metadata(pData[0].trim(), pData[1].trim()));
         }
         return this.List;
      }
   };
   /**********************************************************************************************/
   class ActualData
   {
      public Metadata Meta = null;
      public ReadableDocument CPF = null;
      public ReadableDocument Coligada = null;
      public ReadableDocument DocumentKind = null;
      public ReadableDocument Contract = null;
      public boolean SectionReview = false;
      public boolean SignupAccepted = false;
      public boolean ArchiveReceived = false;
      public ActualData(){}
   };
   /**********************************************************************************************/
   //parse email
   class DecodeEmail
   {
      private String m_szBuffer = null;
      private ArrayList<Metadata> m_pMetadata = new ArrayList<Metadata>();
      private ActualData m_pActualData = new ActualData();
      private ReadableDocument m_pDoc = null;
      /**/
      public DecodeEmail(ReadableDocument pReadableDoc) throws Exception
      {
         this.m_pDoc = pReadableDoc;
         this.m_szBuffer = this.m_pDoc.getRichText();
      }
      public void Decode(Parse p) throws Exception
      {
         p.SearchEntrance();
         p.SearchFinal();
         p.RemoveTags();
         /**/
         this.m_pMetadata = p.MountListOfMetadata();
      }
      /* search property on metadata class */
      public Metadata IndexOf(String szClassName) throws Exception
      {
         for(Metadata p: this.m_pMetadata)
            if(p.Name.equals(szClassName))
               return new Metadata(p.Name, p.Value);
         /**/
         return null;
      }
      /* search by document on array list of documents */
      public ReadableDocument IndexOf(ArrayList<ReadableDocument> pDocList, String szDocName)
      {
         for(ReadableDocument p: pDocList)
         {
            String szName = p.getTitle().replaceAll("\\.", "").replaceAll("\\-", "").trim();
            String szDoc  = szDocName.replaceAll("\\.", "").replaceAll("\\-", "").trim();
            /* debug out */
            debug(szName + "==" + szDoc);
            /**/
            if(szName.equals(szDoc))
               return p;
         }
         /**/
         return null;
      }
      public ReadableDocument FindDocuments(Metadata p) throws Exception
      {
         DocumentSearchRequest pDoc = new DocumentSearchRequest(p.Name);
         ArrayList<ReadableDocument> pList = (ArrayList<ReadableDocument>)getDocumentService().findDocuments(pDoc);
         ReadableDocument pExistsDoc = null;
         /**/
         if(pList.size() == 0)
            throw new Exception("Classe " + p.Name + " não tem nenhum documento registrado!");
         
         /* case document is not found return null pointer */
         if((pExistsDoc = this.IndexOf(pList, p.Value)) == null)
            return null;
         /**/
         return pExistsDoc;
      }
      /* 
       * turn off all exeception controls 
       * 
       * customer request
       * 10-03-2014 changed by wilson.souza
       * 
       * */
      public void ValidateCPF() throws Exception
      {
         /* find class cpf */
         if((this.m_pActualData.Meta = this.IndexOf(Properties.Classes.CPF)) == null)
            throw new Exception("Propriedade da classe " + Properties.Classes.CPF + " não encontrada na lista de metadados!");
         
         /* load list of documents */
         if((this.m_pActualData.CPF = this.FindDocuments(m_pActualData.Meta)) == null)
            throw new Exception("Documento da classe " + Properties.Classes.CPF + "->" + this.m_pActualData.Meta.Value + " não encontrado!");
         
         /* teste property field */
         if(Boolean.parseBoolean(this.m_pActualData.CPF.getField(Properties.Common.Status).toString()) == false)
            throw new Exception("Document " + this.m_pActualData.CPF.getTitle() + 
                  " da classe " + this.m_pActualData.CPF.getDocumentClass() + " esta inativo!");
      }
      /*
       * turn off all exception controls
       * 
       * customer request
       * 10-03-2014 changed by wilson.souza
       * 
       */
      public void ValidateColigada() throws Exception
      {
         /* find property coligada */
         if((this.m_pActualData.Meta = this.IndexOf(Properties.Classes.Company)) == null)
            throw new Exception("Propriedade " + Properties.Classes.Company + " não encontrada na lista de metadados!");
         
         /* search coligada */
         if((this.m_pActualData.Coligada = this.FindDocuments(m_pActualData.Meta)) == null)
            throw new Exception("Documento " + Properties.Classes.Company + "->" + this.m_pActualData.Meta.Value + " não encontrado!");
      }
      /*
       * turn off all exception controls
       * 
       * customer request
       * 10-03-2014 changed by wilson.souza
       * 
       */
      public void ValidateContract() throws Exception
      {
         /* search contract metadata */
         if((this.m_pActualData.Meta = this.IndexOf(Properties.Classes.Contract)) == null)
            throw new Exception("Propriedade " + Properties.Classes.Contract + " não encontrada na lista de metadados!");
         
         /* search the contract on documents list */
         if((this.m_pActualData.Contract = this.FindDocuments(this.m_pActualData.Meta)) == null)
            throw new Exception("Documento " + Properties.Classes.Contract + "->" + this.m_pActualData.Meta.Value + " não encontrado!");
      }
      /*
       * turn off all exception controls
       * 
       * customer request
       * 10-03-2014 changed by wilson.souza
       */
      public void ValidateDocumentKind() throws Exception
      {
         /* search DocumentKind */
         if((this.m_pActualData.Meta = this.IndexOf(Properties.Classes.DocumentKind)) == null)
            throw new Exception("Propriedade " + Properties.Classes.DocumentKind + " não encontrada na lista de metadados!");
         
         /*change the class name to original name*/
         if(ValidateDocumentClass.IsAdmission(this.m_pDoc))
            this.m_pActualData.Meta.Name = Properties.Common.OriginalDocKind + " " + Properties.Common.Admission;
         
         if(ValidateDocumentClass.IsDemission(this.m_pDoc))
            this.m_pActualData.Meta.Name = Properties.Common.OriginalDocKind + " " + Properties.Common.Demission;
         
         if(ValidateDocumentClass.IsRequestStaff(this.m_pDoc))
            this.m_pActualData.Meta.Name = Properties.Common.OriginalDocKind + " " + Properties.Classes.Requeststaff.toLowerCase();
         
         if((this.m_pActualData.DocumentKind = this.FindDocuments(this.m_pActualData.Meta)) == null)
            throw new Exception("Documento " + this.m_pActualData.Meta.Value + " da classe " + this.m_pActualData.Meta.Name + " não encontrado!");
         
         /* verify if document is enable */
         if(!ValidateProperty.Eval(this.m_pActualData.DocumentKind, Properties.Common.Status))
            throw new Exception("Documento " + this.m_pActualData.DocumentKind.getTitle() + " não esta habilitado para uso!");
      }
      /* 
       * prepare all process but interrupt case ocurred any fail
       *  
       * last updated by wilson.souza 10-03-2014
       * 
       * */
      public boolean Prepare() throws Exception
      {
         /* prepare CPF conditions */
         try
         {
            if(this.IndexOf(Properties.Classes.CPF) != null)
               this.ValidateCPF();
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }
         
         /* prepare Coligada conditions */
         try
         {
            this.ValidateColigada();
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }
            
         /* prepare Contrato conditions */
         try
         {
            this.ValidateContract();
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }
         
         /* prepare Tipo Documento conditions */
         try
         {
            this.ValidateDocumentKind();
         }
         catch(Exception e)
         {
            /*
             * register error but not interrupt process
             */
            debug(e.getMessage());
         }
         return true;
      }
      public boolean MakeRelations() throws Exception
      {
         String[] pRelations = null;
         /*
          * verify class is running
          */
         if(ValidateDocumentClass.IsAdmission(this.m_pDoc))
            pRelations = getParam(Properties.Common.AdmissionParameter).split(Properties.MetadataKey.MAKEARRAY);
         
         if(ValidateDocumentClass.IsDemission(this.m_pDoc))
            pRelations = getParam(Properties.Common.DemissionParameter).split(Properties.MetadataKey.MAKEARRAY);

         if(ValidateDocumentClass.IsRequestStaff(this.m_pDoc))
            pRelations = getParam(Properties.Common.RequeststaffParameter).split(Properties.MetadataKey.MAKEARRAY);
         /*
          * filter
          */
         try
         {
            for(String szName: pRelations)
            {
               /* add relation to cpf */
               if(szName.indexOf(Properties.Classes.CPF) != -1)
                  getDocumentService().lockDocument(this.m_pDoc).addToRelatedDocumentId(szName, this.m_pActualData.CPF.getId());
               
               /* add relation to coligada */
               if(szName.indexOf(Properties.Classes.Company) != -1)
                  getDocumentService().lockDocument(this.m_pDoc).addToRelatedDocumentId(szName, this.m_pActualData.Coligada.getId());
               
               /* add relation to contrato */
               if(szName.indexOf(Properties.Classes.Contract) != -1)
                  getDocumentService().lockDocument(this.m_pDoc).addToRelatedDocumentId(szName, this.m_pActualData.Contract.getId());
            }
         }
         catch(Exception e)
         {
            /*
             * register error but not interrupt process
             */
            debug(e.getMessage());
         }
         return true;
      }
      public boolean WriteProperties() throws Exception
      {
         /* write properties with separation it allow which if processed one by one property */
         try
         {
            Document pDoc = getDocumentService().lockDocument(this.m_pDoc);
            /**/
            try
            {
               String szColigada = this.m_pActualData.Coligada.getField(Properties.Common.Name).toString();
               pDoc.setField(Properties.Classes.Company, szColigada);
            }
            catch(Exception e)
            {
               debug(e.getMessage());
            }
            /**/
            try
            {
               String szContract = this.m_pActualData.Contract.getField(Properties.Common.Name).toString();
               pDoc.setField(Properties.Classes.Contract, szContract);
            }
            catch(Exception e)
            {
               debug(e.getMessage());
            }
            /**/
            try
            {
               String szDocKind  = this.m_pActualData.DocumentKind.getField(Properties.Common.Name).toString();
               pDoc.setField(Properties.Classes.DocumentKind, szDocKind);
            }
            catch(Exception e)
            {
               debug(e.getMessage());
            }
            /**/
            try
            {
               if(this.IndexOf(Properties.Classes.CPF) != null)
                  pDoc.setField(Properties.Classes.CPF, this.m_pActualData.CPF.getTitle());
            }
            catch(Exception e)
            {
               debug(e.getMessage());
            }
            
            /**/
            for(Metadata p: this.m_pMetadata)
            {
               if(p.Value.equals(Properties.Logical.False))
                  pDoc.setField(p.Name, Boolean.parseBoolean(p.Value));
               
               if(p.Value.equals(Properties.Logical.True))
                  pDoc.setField(p.Name, Boolean.parseBoolean(p.Value));
            }
         }
         catch(Exception e)
         {
            /*
             * register all errors but not interrupt process
             */
            debug(e.getMessage());
         }
         return true;
      }
      //process all data
      public void Execute(ReadableDocument pReadableDoc) throws Exception
      {
         Parse p = new Parse(this.m_szBuffer);
         
         /* verify if received by e-mail */
         if(pReadableDoc.getRichText().indexOf(Properties.MetadataKey.START) == -1)
            return;
         
         /* decode email */
         this.Decode(p);
         
         /* search by class name CPF and process*/
         this.Prepare();
         
         /* make document relations */
         this.MakeRelations();
      
         /* write all properties on current document */
         this.WriteProperties();

         /**/
         debug("Process finalized with successfully...");
      }
   };
   /**********************************************************************************************/
   //launch class
   DecodeEmail pDecode = new DecodeEmail(document);
   pDecode.Execute(document);
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}  