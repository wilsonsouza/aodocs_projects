/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-09-2014
*  Description export AODocs object to Google Spreadsheet
*  Last update 9-17-2014
*  Last update 9-18-2014
*  Last update 9-19-2014
*  Last update 9-22-2014
*  Last update 9-23-2014
*  Last update 12-16-2014 added folderId
*/
try
{
   class UpdateDoc
   {
      /**********************************************************************************************/
      class URLEncoder 
      {
         /**
          * MÃ©todo para codificar uma String para que seja enviada como
          * um parÃ¢metro do application/x-www-form-urlencoded.
          */
         public static String encode(String input) throws Exception 
         {
             StringBuilder sb = new StringBuilder();

             for(byte b : input.getBytes("UTF-8")) 
             {

               if(b == 0x20) 
               {
                  sb.append('+');
               } 
               else if ( ('0' <= b && b <= '9') || ('a' <= b && b <= 'z') || ('A' <= b && b <= 'Z') ) 
               {
                  sb.append((char)b);
               } 
               else if (b=='$' || b=='-' || b=='_' || b=='.' || b=='!' || b=='*' || b=='\'' || b=='(' || b==')' || b==',') 
               {
                  sb.append((char)b);
               } 
               else 
               {
                  sb.append(String.format("%%%02x", b));
               }
             }
             return sb.toString();
         }
      }
      /**********************************************************************************************/
      public class SpreadData
      {
         public String Name;
         public String Value;
         public String Description;
         public SpreadData(){}
         public SpreadData(String szName, String szValue, String szComment)
         {
            this.Name = szName;
            this.Value = szValue;
            this.Description = szComment;
         }
      };
      /**********************************************************************************************/
      public class Customer
      {
         private ReadableDocument m_pDoc = null;
         public String ID;
         public String LibraryID;
         public String FileName;
         public String CreateDate;
         public String Updated;
         public String Link;
         public String Company;
         public String Contract;
         public String DocumentKind;
         public ArrayList<SpreadData> Body = new ArrayList<SpreadData>();
         public Customer(ReadableDocument pDoc)
         {
            this.m_pDoc = pDoc;
         }
         public boolean AsBool(String szValue) throws Exception
         {
            if(szValue == null)
               return false;
            
            return Boolean.parseBoolean(szValue);
         }
         public String AsDate(Date pDate) throws Exception
         {
            if(pDate == null)
               return (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            
            return (new SimpleDateFormat("dd/MM/yyyy")).format(pDate);
         }
         public String GetProperty(String szName) throws Exception
         {
            if(this.m_pDoc.getField(szName) == null)
               return new String(" ");
            
            return this.m_pDoc.getField(szName).toString();
         }
         public void Read() throws Exception
         {
            this.ID = this.m_pDoc.getId();
            this.LibraryID = getLibraryId();
            this.FileName = this.m_pDoc.getTitle();
            /***/
            this.CreateDate = this.AsDate(this.m_pDoc.getCreationDate());
            this.Updated = this.AsDate(this.m_pDoc.getLastModificationDate());
            /**/
            this.Link = this.m_pDoc.getDriveUrl();
            /**/
            if(this.Link == null)
               this.Link = new String(" ");
            /**/
            this.Company = this.GetProperty("Coligada");
            this.Contract = this.GetProperty("Contrato");
            this.DocumentKind = this.GetProperty("Tipo de Documento");
            /**/
         }
         public boolean Export() throws Exception
         {
            this.Body.clear();
            
            this.Body.add(new SpreadData("ID", this.ID, null));
            this.Body.add(new SpreadData("Biblioteca", this.LibraryID, null));
            this.Body.add(new SpreadData("Nome do Arquivo", this.FileName, null));
            this.Body.add(new SpreadData("Data de Criação", this.CreateDate, null));
            this.Body.add(new SpreadData("Data Atualização", this.Updated, null));
            this.Body.add(new SpreadData("Link", this.Link, null));
            this.Body.add(new SpreadData("Coligada", this.Company, null));
            this.Body.add(new SpreadData("Contrato", this.Contract, null));
            this.Body.add(new SpreadData("Tipo de Documento", this.DocumentKind, null));

            return false;
         } 
      };
      /**********************************************************************************************/
      public class Admission extends Customer
      {
         public String CPF;
         public boolean DepartmentReview;
         public boolean AcceptsRegistration;
         public boolean FileReception;
         public Admission(ReadableDocument pDoc)
         {
            super(pDoc);
         }
         @Override
         public void Read() throws Exception
         {
            super.Read();
            /**/
            this.CPF = this.GetProperty("CPF");
            this.DepartmentReview = this.AsBool(this.GetProperty("Revisão Departamento"));
            this.AcceptsRegistration = this.AsBool(this.GetProperty("Aceite Cadastro"));
            this.FileReception = this.AsBool(this.GetProperty("Recebimento Arquivo"));
         }
         @Override
         public boolean Export() throws Exception
         {
            super.Export();
            
            this.Body.add(new SpreadData("CPF", this.CPF, null));
            this.Body.add(new SpreadData("Revisão Admissão", this.DepartmentReview? "Sim": "Não", null));
            this.Body.add(new SpreadData("Aceite Cadastro", this.AcceptsRegistration? "Sim": "Não", null));
            this.Body.add(new SpreadData("Recebimento Arquivo", this.FileReception? "Sim": "Não", null));
            return false;
         }
      };
      /**********************************************************************************************/
      public class Demission extends Customer
      {
         public String CPF = "";
         public boolean DPReview = false;
         public boolean AcceptsRegistration = false;
         public boolean FileReception = false;
         public Demission(ReadableDocument pDoc)
         {
            super(pDoc);
         }
         @Override
         public void Read() throws Exception
         {
            super.Read();
            /**/
            this.CPF = this.GetProperty("CPF");
            this.DPReview = this.AsBool(this.GetProperty("Revisão DP"));
            this.AcceptsRegistration = this.AsBool(this.GetProperty("Aceite Cadastro"));
            this.FileReception = this.AsBool(this.GetProperty("Recebimento Arquivo"));
            /**/
         }
         @Override
         public boolean Export() throws Exception
         {
            super.Export();
            
            this.Body.add(new SpreadData("CPF", this.CPF, null));
            this.Body.add(new SpreadData("Revisão DP", this.DPReview? "Sim": "Não", null));
            this.Body.add(new SpreadData("Aceite Cadastro", this.AcceptsRegistration? "Sim": "Não", null));
            this.Body.add(new SpreadData("Recebimento Arquivo", this.FileReception? "Sim": "Não", null));
            
            return false;
         }
      };
      /**********************************************************************************************/
      public class Requeststaff extends Customer
      {
         public String Position = "";
         public boolean AdmissionReview = false;
         public Requeststaff(ReadableDocument pDoc)
         {
            super(pDoc);
         }
         @Override
         public void Read() throws Exception
         {
            super.Read();
            /**/
            this.Position = this.GetProperty("Posição");
            this.AdmissionReview = this.AsBool(this.GetProperty("Revisão Admissão"));
         }
         @Override
         public boolean Export() throws Exception
         {
            super.Export();
            
            this.Body.add(new SpreadData("Posição", this.Position, null));
            this.Body.add(new SpreadData("Revisão Admissão", this.AdmissionReview? "Sim": "Não", null));
            
            return false;
         }
      };
      /**********************************************************************************************/
      public class Properties
      {
         public class Position
         {
            public static int IDD_ADMISSION = 0;
            public static int IDD_DEMISSION = 1;
            public static int IDD_REQUEST   = 2;
         }
         
         public String Name;
         public Object Value;
         public Customer Clazz;
         public Properties(){}
         public Properties(String szName, Object pValue, Customer szClass)
         {
            this.Name = szName;
            this.Value= pValue;
            this.Clazz= szClass;
         }
      }
      /**********************************************************************************************/
      public class Parameters
      {
         private String[] m_pListOfProperties = null;
         private ReadableDocument m_pDoc = null;
         public long StartDate = 0;
         public long EndDate = 0;
         public String ClassName = new String();
         public Admission AdmissionClazz = null;
         public Demission DemissionClazz = null;
         public Requeststaff RequestClazz = null;
         public Parameters(ReadableDocument pDoc) throws Exception
         {
            /* test if an list of strings */
            if(getParam("ListOfProperties") == null)
               throw new Exception("Campo com lista de propriedades não encontrado!");
            /**/
            this.m_pListOfProperties = getParam("ListOfProperties").split(";");
            /**/
            this.m_pDoc = pDoc;
            String szStart = this.m_pDoc.getField(this.m_pListOfProperties[0]).toString();
            String szEnd   = this.m_pDoc.getField(this.m_pListOfProperties[1]).toString();
            
            this.StartDate = Date.parse(szStart);
            this.EndDate   = Date.parse(szEnd);
            
            this.ClassName = this.m_pDoc.getField(this.m_pListOfProperties[2]).toString();
         }
         public boolean IsInterval(Date pValue)
         {
            int ns = pValue.getTime() >= this.StartDate? 1: 0;
            int ne = pValue.getTime() <= this.EndDate? 1: 0;

            /* this inside interval */
            if(ns == 1 && ne == 1)
               return true;
            
            return false;
         }
      };
      /**********************************************************************************************/
      public class ActualData
      { 
         private String m_szUrl = new String("https://script.google.com/macros/s/AKfycby0VASs6VbfxB6Ikp-3QBYHb6y50o7_kFcs0Juz-U4tgvSZBVTU/exec");
         private String m_szKey = "PLOJV6Yw2xmEyGiXml9YPvy3vNk01HHXXX7NjHWahE7wIeqrBtKoxOKcQEdFuN7";
         private String m_szFolderId = new String("0B5nq06YnVukeWU4wMldVVzlIYWM"); //default folder for wrr files
         private LinkedList<HTTPHeader> m_pHeader = new LinkedList<HTTPHeader>();
         private UrlFetchService m_pFetch = getUrlFetchService();
         private String m_szDocName = new String();
         byte[] m_byData = null;
         FetchOptions m_foOptions = FetchOptions.Builder.withDeadline(0x3c).doNotFollowRedirects(); 
         public ReadableDocument DocumentHandle = null;
         public String FormatPost = null;
         public  String FileId = new String();
         public ActualData(ReadableDocument pDoc)
         {
            this.DocumentHandle = pDoc;
         }
         public void SetParameter(String szFileName)
         {
            this.m_szDocName = szFileName;
            this.FormatPost = String.format("DocName=%s&Key=%s", szFileName, this.m_szKey);
            this.m_byData = this.FormatPost.getBytes();
         }
         public void SetParameter(String szFileName, String szComplement) throws Exception
         {
            this.m_szDocName = szFileName;
            /**/
            szComplement = URLEncoder.encode(szComplement);
            szFileName   = URLEncoder.encode(szFileName);
            /* format string to send */
            this.FormatPost = String.format("DocName=%s&AuthorizeKey=%s&Items=%s", szFileName, this.m_szKey, szComplement);
            this.m_byData = this.FormatPost.getBytes("UTF-8");
         }
         public boolean Execute() throws Exception
         {
            HTTPResponse pHttp = this.m_pFetch.fetch(this.m_szUrl, HTTPMethod.POST, this.m_byData, this.m_pHeader, this.m_foOptions);
            String szContent = null;
            int nSuccess = ((pHttp.getResponseCode() / 0x64) % 0x3);
            String szParam = "IDD_OK?ID(";
            String szData = null;
            /**/
            if(nSuccess == 0)
            {
               for(HTTPHeader p: pHttp.getHeaders())
               {
                  boolean bExists = p.getName().equalsIgnoreCase("Location");
   
                  if(bExists)
                  {
                     int n = 0;
                     pHttp = this.m_pFetch.fetch(p.getValue());
                     szContent = new String(pHttp.getContent());
                     /***/
                     debug(szContent);
                     /**/
                     if(szContent.startsWith("IDD_ERROR"))
                        throw new Exception("Google Script ocorreu de execução!");
                     
                     if(szContent.startsWith("IDD_INVALID_KEY"))
                        throw new Exception("Chamada com chave invalida!");
                     
                     if(!szContent.startsWith("IDD_OK"))
                        throw new Exception("Erro ao executar Google script!");
                     /**/
                     n = szContent.indexOf("IDD_OK?ID(");
                     /**/
                     if(n == -1)
                        throw new Exception(String.format("ID do arquivo %s não encontrado!", this.m_szDocName));
                     
                     szData = szContent.substring(n);
                     this.FileId = szData.substring(szParam.length(), szData.indexOf(")"));
                     return true;
                  }
               }
            }
            else
            {
               String szMsg = String.format("Ocorreu erro, chamada HTTP código %d invalido!", pHttp.getResponseCode());
               throw new Exception(szMsg);
            }
            
            return false;
         }
         public void AttachCurrentSpreadsheet() throws Exception
         {
            Object[] pAtt = this.DocumentHandle.getAttachments().toArray();
            /* check if already exists attached document then return */
            if(pAtt.length > 0)
               return;
            /* attach file */
            Attachment pCopyTo = getDriveService().copyDocument(this.FileId, Optional.of(this.m_szFolderId), Optional.absent());
            getDocumentService().lockDocument(this.DocumentHandle).addAttachment(pCopyTo);
         }
      }
      /**********************************************************************************************/
      class CellData
      {
         public int Row;
         public int Col;
         public String Data;
         public CellData(int x, int y, String szData)
         {
            this.Row = x;
            this.Col = y;
            this.Data = szData;
         }
      }
      public class ExportDoc
      {
         private SpreadsheetEntry m_pHandle = null;
         private ActualData m_pActualData = null;
         private int m_nRow = 2;
         private DocumentService m_pDocumentService = getDocumentService();
         private SpreadsheetService m_pSpreadHandle = getSpreadsheetService();
         private String m_szDocumentBody = null;
         private ArrayList<CellData> m_pCells = new ArrayList<CellData>();
         private String m_szJson = new String();
         public ExportDoc(ReadableDocument pDoc) throws Exception
         {
            this.m_pActualData = new ActualData(pDoc);
         }
         public boolean Execute() throws Exception
         {
            String[] pClazz = getParam("ListOfClass").split(";");
            Parameters pp = new Parameters(this.m_pActualData.DocumentHandle);
            
            /* verify if has any selected option */
            if(pp.ClassName == null)
               throw new Exception("Nenhuma classe para exportação foi selecionada!");
            
            /* set file name to spreadsheet document on google drive */
            this.m_pActualData.SetParameter(this.m_pActualData.DocumentHandle.getTitle());
            
            //if(this.m_pActualData.Execute())
            {
               Date start = new Date();
               Date endrt = new Date();
               /* open spreadsheet */
               DocumentSearchRequest pSearch = new DocumentSearchRequest(pp.ClassName);
               ArrayList<ReadableDocument> pDocList = (ArrayList<ReadableDocument>)this.m_pDocumentService.findDocuments(pSearch);
                  /***/
               
               if(pDocList.size() == 0)
                  throw new Exception(String.format("Nenhum documento foi encontrado neste classe %s para exportar!", pp.ClassName));
                  
               debug(String.format("start %s", (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(start)));
               
               for(ReadableDocument pDoc: pDocList)
               {
                  Date pLastModification = pDoc.getLastModificationDate();
                  
                  if(!pp.IsInterval(pLastModification))
                     continue;
                  
                  /****/
                  if(pp.ClassName.equals(pClazz[0]))
                  {
                     pp.AdmissionClazz = new Admission(pDoc);
                     pp.AdmissionClazz.Read();
                     pp.AdmissionClazz.Export();
                     this.Write(pp.AdmissionClazz.Body);
                  }
                  
                  if(pp.ClassName.equals(pClazz[1]))
                  {
                     pp.DemissionClazz = new Demission(pDoc);
                     
                     pp.DemissionClazz.Read();
                     pp.DemissionClazz.Export();
                     this.Write(pp.DemissionClazz.Body);
                  }
                  
                  if(pp.ClassName.equals(pClazz[2]))
                  {
                     pp.RequestClazz = new Requeststaff(pDoc);
                     
                     pp.RequestClazz.Read();
                     pp.RequestClazz.Export();
                     this.Write(pp.RequestClazz.Body);
                  }
                  /***/
               }
               /* put on spreadsheet */
               if(pp.AdmissionClazz != null)
                  this.FlushOnSpreadsheet(pp.AdmissionClazz.Body);
               
               if(pp.DemissionClazz != null)
                  this.FlushOnSpreadsheet(pp.DemissionClazz.Body);
               
               if(pp.RequestClazz != null)
                  this.FlushOnSpreadsheet(pp.RequestClazz.Body);
               
               /** force garbage collector to free memory **/
               pp.AdmissionClazz = null;
               pp.DemissionClazz = null;
               pp.RequestClazz   = null;
               /**/
               endrt.setTime(start.getTime());
               /**/
               this.m_pActualData.AttachCurrentSpreadsheet();
               debug(String.format("end %s", (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(endrt)));
            }
            return true;
         }
         /**********************************************************************************************/
         public boolean Write(ArrayList<SpreadData> pItems) throws Exception
         {
            String szLast = ((SpreadData)pItems.get(pItems.size()-1)).Name;
            
            /* mount header */
            if(this.m_szJson.isEmpty())
            {
               this.m_szJson += String.format("{\"Items\":[[");
               
               for(SpreadData p: pItems)
               {
                  this.m_szJson += String.format("\"%s\"", p.Name);
                  
                  if(szLast != p.Name)
                     this.m_szJson += ",";
               }
               
               this.m_szJson += "]"; 
            }

            if(!this.m_szJson.isEmpty())
            {
               this.m_szJson += ",[";
               
               /* mount spreadsheet body */
               for(SpreadData p: pItems)
               {
                  this.m_szJson += String.format("\"%s\"", p.Value);
                  
                  if(szLast != p.Name)
                     this.m_szJson += ",";
               }
               
               this.m_szJson += "]";
            }
            /* increment */
            return false;
         }
         /**********************************************************************************************/
         public boolean FlushOnSpreadsheet(ArrayList<SpreadData> pItems) throws Exception
         {
            this.m_szJson += "]}";
            this.m_pActualData.SetParameter(this.m_pActualData.DocumentHandle.getTitle(), this.m_szJson);
            this.m_pActualData.Execute();
            /**/
            return true;
         }
      }
      /**********************************************************************************************/
      public void Run(ReadableDocument pDoc) throws Exception
      {
         ExportDoc p = new ExportDoc(pDoc);
         p.Execute();
      }
   };
   /***/
   UpdateDoc p = new UpdateDoc();
   p.Run(document);
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
