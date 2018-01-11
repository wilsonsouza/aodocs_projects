/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 9-09-2014
*  Description export AODocs object to Google Spreadsheet
*  Last update 12-12-2014
*  Last update 12-16-2014 fix properties names
*  Last update 06-03-2015 were added more classes on the list
*  
*  classes
*  Documentos de admissão
*  Documentos de demissão
*  Requisição de pessoal
*  Afastamento licenciamento ou ausência
*  Mensais DP
*  RIC
*  Contas a Pagar
*  Faturamento
*/
try
{
   class AccountsPayableExportableDocs
   {
      /**********************************************************************************************/
      class URLEncoder 
      {
         static public String encode(String szValue) throws Exception 
         {
             StringBuilder pBuilder = new StringBuilder();

             for(byte b : szValue.getBytes("UTF-8")) 
             {
               if(b == 0x20) 
                  pBuilder.append('+');
               else if ( ('0' <= b && b <= '9') || ('a' <= b && b <= 'z') || ('A' <= b && b <= 'Z') ) 
                  pBuilder.append((char)b);
               else if (b=='$' || b=='-' || b=='_' || b=='.' || b=='!' || b=='*' || b=='\'' || b=='(' || b==')' || b==',') 
                  pBuilder.append((char)b);
               else 
                  pBuilder.append(String.format("%%%02x", b));
             }
             return pBuilder.toString();
         }
      }
      /**********************************************************************************************/
      class SpreadData
      {
         public String Name = null;
         public String Value = null;
         public SpreadData(String szName, String szValue)
         {
            /* prevent variable with null content */
            if(szName == null)
               this.Name = new String();
            else
               this.Name = new String(szName); //assign content
            //
            if(szValue == null)
               this.Value = new String();
            else
               this.Value = new String(szValue);
         }
      };
      /**********************************************************************************************/
      class Definitions
      {
         static public String ID = "ID";
         static public String LibraryID = "Biblioteca";
         static public String FileName = "Nome do Arquivo";
         static public String Created = "Data da Criação";
         static public String Updated = "Data Atualização";
         static public String Link = "Link";
         static public String Company = "Coligada";
         static public String SupplierID = "CNPJ/CPF Fornecedor";
         static public String DocumentType = "Tipo de Documento";
         static public String DueDate = "Data de Emissão";
         static public String CostCenter = "Centro de Custo";
         static public String MaturityDate = "Data de Vencimento";
         static public String SupplierSystem = "Fornecedor no sistema";
         static public String VendorCode = "Código do Fornecedor";
         static public String BankDetails = "Dados Bancarios";
         static public String OCNumber = "Numero OC";
         static public String DocumentInserted = "Documento Inserido";
         static public String TransitionNumber = "Numero Transação";
         static public String PaymentMade = "Pagamento Feito";
         static public String DateOfPayment = "Data de Pagamento";
         /* field billing */
         static public String Contract = "Contrato";
         static public String Month = "Mês de Competencia";
         static public String Year = "Ano de Competencia";
         static public String ReviewDepartament = "Revisão Departamento";
         static public String AcceptBilling = "Aceita Faturamento";
         /* logical class */
         class BOOL
         {
            static public String False = "false";
            static public String True = "true";
         }
         
         class DateFormat
         {
            static public String PTBR = "dd/MM/yyyy";
            static public String DateToString(Date pDate) throws Exception
            {
               SimpleDateFormat pFmt = new SimpleDateFormat(Definitions.DateFormat.PTBR);
               
               if(pDate == null)
                  return pFmt.format(new Date());
               
               return pFmt.format(pDate);
            }
            static public Date StringToDate(String szDate) throws Exception
            {
               if(szDate == null)
                  szDate = Definitions.DateFormat.DateToString(new Date());
               else if(szDate.isEmpty())
                  szDate = Definitions.DateFormat.DateToString(new Date());
               /***/
               return new Date(Date.parse(szDate));
            }
            static public String StringToDateFormatted(String szDate) throws Exception
            {
               return DateToString(DateFormat.StringToDate(szDate));
            }
            static public String StringToDateFormatted(Date pDate) throws Exception
            {
               return DateToString(pDate);
            }
         }
         
         class Expression
         {
            static public String Yes = "Sim";
            static public String No  = "Não";
            
            static public boolean StringToBoolean(String szValue) throws Exception
            {
               if(szValue == null)
                  szValue = new String(Definitions.BOOL.False);
               
               return Boolean.parseBoolean(szValue);
            }
            static public String BooleanToString(boolean bValue) throws Exception
            {
               return (bValue? Yes: No);
            }
         }
         /* list of supported classes */
         class Classes
         {
            static public String AccountsPayable = "Contas a Pagar";
            static public String Billing = "Faturamento";
            static public String Admission = "Documentos de admissão";
            static public String Demission = "Documentos de demissão";
            static public String Requeststaff = "Requisição de pessoal";
            static public String Monthly = "Mensais DP";
            static public String RemovelLiceningAbsence = "Afastamento licenciamento ou ausência";
            static public String SummaryContractualInfo = "RIC";
         }
         
         class Parameters
         {
            static public String ListOfProperties = "ListOfProperties";
            static public String GenerateArrayToken = "@";
         }
      }
      /**********************************************************************************************/
      class Customer
      {
         private ReadableDocument m_pDoc = null;
         private String Value = null;
         public String ID = null;
         public String LibraryID = null;
         public String FileName = null;
         public String Created = null;
         public String Updated = null;
         public String Link = null;
         public ArrayList<SpreadData> Body = new ArrayList<SpreadData>();
         public Customer(ReadableDocument pDoc)
         {
            this.m_pDoc = pDoc;
         }
         public Customer Get(String szName) throws Exception
         {
            try
            {
               this.Value = this.m_pDoc.getField(szName).toString();
               /**debug**/
               debug("Get:" + (this.Value == null? "null": this.Value));
            }
            catch(Exception e)
            {
               //throw e;
               debug(e.getMessage());
               this.Value = new String();
            }
            return this;
         }
         public Date StringToDate(String szValue) throws Exception
         {
            return Definitions.DateFormat.StringToDate(szValue);
         }
         public Customer Set(String szValue) throws Exception
         {
            /* dealloc old memory in use */
            this.Value = null;
            /* alloc new memory space to use */
            this.Value = new String(szValue);
            return this;
         }
         public String DateToString(Date pDate) throws Exception
         {
            return Definitions.DateFormat.DateToString(pDate);
         }
         public boolean StringToBoolean(String szValue) throws Exception
         {
            return Definitions.Expression.StringToBoolean(szValue);
         }
         public String BooleanToString(boolean bValue) throws Exception
         {
            return Definitions.Expression.BooleanToString(bValue);
         }
         public String StringToQuestion(String szValue) throws Exception
         {
            return  Definitions.Expression.BooleanToString(this.StringToBoolean(szValue));
         }
         public void Add(String szName, String szValue) throws Exception
         {
        	 this.Body.add(new SpreadData(szName, szValue));
         }
         public void Add(SpreadData pData) throws Exception
         {
        	 this.Body.add(pData);
         }
         public void Read() throws Exception
         {
            this.ID = this.m_pDoc.getId();
            this.LibraryID = getLibraryId();
            this.FileName = this.m_pDoc.getTitle();
            /***/
            this.Created = this.DateToString(this.m_pDoc.getCreationDate());
            this.Updated = this.DateToString(this.m_pDoc.getLastModificationDate());
            /**/
            if((this.Link = this.m_pDoc.getDriveUrl()) != null)
               this.Link = new String(this.m_pDoc.getDriveUrl());
            else
               this.Link = new String();
            /**/
            debug(this.getClass().getName() + ".super.Read");
         }
         public void Build() throws Exception
         {
            try
            {
               this.Body.clear();
               this.Add(Definitions.ID, this.ID);
               this.Add(Definitions.LibraryID, this.LibraryID);
               this.Add(Definitions.FileName, this.FileName);
               this.Add(Definitions.Created, this.Created);
               this.Add(Definitions.Updated, this.Updated);
               this.Add(Definitions.Link, this.Link);
            }
            catch(Exception e)
            {
               debug(e.getMessage());
               throw e;
            }
            /**/
            debug(this.getClass().getName() + ".super.Build");
         } 
      };
      /**********************************************************************************************/
      class AccountsPayable extends Customer
      {
         public String Company = null;
         public String SupplyID = null;
         public String DocumentType = null;
         public String DueDate = null;
         public String CostCenter = null;
         public String MaturityDate = null;
         public String SupplierSystem = null;
         public String VendorCode = null;
         public String BankDetails = null;
         public String OCNumber = null;
         public String DocumentInserted = null;
         public String TransitionNumber = null;
         public String PaymentMade = null;
         public String DateOfPayment = null;
         public AccountsPayable(ReadableDocument pDoc)
         {
            super(pDoc);
         }
         @Override
         public void Read() throws Exception
         {
            super.Read();
            /***/
            try
            {
               this.Company = this.Get(Definitions.Company).Value;
               this.SupplyID = this.Get(Definitions.SupplierID).Value;
               this.DocumentType = this.Get(Definitions.DocumentType).Value;
               this.DueDate = Definitions.DateFormat.StringToDateFormatted(this.Get(Definitions.DueDate).Value);
               this.CostCenter = this.Get(Definitions.CostCenter).Value;
               this.MaturityDate = this.Get(Definitions.MaturityDate).Value;
               this.SupplierSystem = this.Get(Definitions.SupplierSystem).Value;
               this.VendorCode = this.Get(Definitions.VendorCode).Value;
               this.BankDetails = this.StringToQuestion(this.Get(Definitions.BankDetails).Value);
               this.OCNumber = this.Get(Definitions.OCNumber).Value;
               this.DocumentInserted = this.StringToQuestion(this.Get(Definitions.DocumentInserted).Value);
               this.TransitionNumber = this.Get(Definitions.TransitionNumber).Value;
               this.PaymentMade = this.StringToQuestion(this.Get(Definitions.PaymentMade).Value);
               this.DateOfPayment = Definitions.DateFormat.StringToDateFormatted(this.Get(Definitions.DateOfPayment).Value);
            }
            catch(Exception e)
            {
               debug(e.getMessage());
               throw e;
            }
            /**/
            debug(this.getClass().getName() + ".this.Read");
         }
         @Override
         public void Build() throws Exception
         {
            super.Build();
            /***/
            this.Add(Definitions.Company, this.Company);
            this.Add(Definitions.SupplierID, this.SupplyID);
            this.Add(Definitions.DocumentType, this.DocumentType);
            this.Add(Definitions.DueDate, this.DueDate);
            this.Add(Definitions.CostCenter, this.CostCenter);
            this.Add(Definitions.MaturityDate, this.MaturityDate);
            this.Add(Definitions.SupplierSystem, this.SupplierSystem);
            this.Add(Definitions.VendorCode, this.VendorCode);
            this.Add(Definitions.BankDetails, this.BankDetails);
            this.Add(Definitions.OCNumber, this.OCNumber);
            this.Add(Definitions.DocumentInserted, this.DocumentInserted);
            this.Add(Definitions.TransitionNumber, this.TransitionNumber);
            this.Add(Definitions.PaymentMade, this.PaymentMade);
            this.Add(Definitions.DateOfPayment, this.DateOfPayment);
            /**/
            debug(this.getClass().getName() + ".this.Build");
         }
      }
      /**********************************************************************************************/
      class Billing extends Customer
      {
         public String Company = null;
         public String Contract = null;
         public String DocumentType = null;
         public String Month = null;
         public String Year = null;
         public String ReviewDepart = Definitions.BOOL.False;
         public String AcceptBilling = Definitions.BOOL.False;
         public Billing(ReadableDocument pDoc)
         {
            super(pDoc);
         }
         @Override
         public void Read() throws Exception
         {
            super.Read();
            /***/
            try
            {
               this.Company = this.Get(Definitions.Company).Value;
               this.Contract = this.Get(Definitions.Contract).Value;
               this.DocumentType = this.Get(Definitions.DocumentType).Value;
            }
            catch(Exception e)
            {
               debug(e.getMessage());
               throw e;
            }
         }
         @Override
         public void Build() throws Exception
         {
            super.Build();
            /***/
            this.Add(Definitions.Company, this.Company);
            this.Add(Definitions.Contract, this.Contract);
            this.Add(Definitions.DocumentType, this.DocumentType);
            /***/
         }
      }
      /**********************************************************************************************/
      class SummaryContractualInfo extends Customer
      {
    	  public String Company = null;
    	  public String Service = null;
    	  public String Market = null;
    	  public String Client = null;
    	  public String CostCenter = null;
    	  public String Recruitment = null;
    	  public String Session = null;
    	  public String WorkingHours = null;
    	  public String Syndicate = null;
    	  public String ITEquipament = null;
    	  public String TelecomServices = null;
    	  public String RequestBuy = null;
    	  public String NewBranch = null;
    	  public String CustomerBase = null;
    	  public String CreationServiceItems = null;
    	  public String FinancialRules = null;
    	  public String Contract = null;
    	  public SummaryContractualInfo(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /**/
    		  try
    		  {
    			  this.Company = this.Get("Coligada").Value;
    			  this.Service = this.Get("Serviço").Value;
    			  this.Market = this.Get("Mercado").Value;
    			  this.Client = this.Get("Cliente").Value;
    			  this.CostCenter = this.Get("Centro de Custo").Value;
    			  this.Recruitment = this.StringToQuestion(this.Get("Recrutamento").Value);
    			  this.Session = this.StringToQuestion(this.Get("Sessão").Value);
    			  this.WorkingHours = this.StringToQuestion(this.Get("Jornada Trabalho").Value);
    			  this.Syndicate = this.StringToQuestion(this.Get("Sindicato").Value);
    			  this.ITEquipament = this.StringToQuestion(this.Get("Equipamentos TI").Value);
    			  this.TelecomServices = this.StringToQuestion(this.Get("Serviços Telecom").Value);
    			  this.RequestBuy = this.StringToQuestion(this.Get("Requiçoes de Compra").Value);
    			  this.NewBranch = this.StringToQuestion(this.Get("Nova Filial").Value);
    			  this.CustomerBase = this.StringToQuestion(this.Get("Cadastro Cliente").Value);
    			  this.CreationServiceItems = this.StringToQuestion(this.Get("Criação itens de Serviço").Value);
    			  this.FinancialRules = this.StringToQuestion(this.Get("Regras Fiscais").Value);
    			  this.Contract = this.Get("Contrato de Venda").Value;
      		  }
    		  catch(Exception e)
    		  {
    			  debug(e.getMessage());
    			  throw e;
    		  }
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /***/
    		  this.Add("Coligada", this.Company);
    		  this.Add("Serviço", this.Service);
    		  this.Add("Mercado", this.Market);
    		  this.Add("Cliente", this.Client);
    		  this.Add("Centro de Custo", this.CostCenter);
    		  this.Add("Recrutamento", this.Recruitment);
    		  this.Add("Sessão", this.Session);
    		  this.Add("Jornada Trabalho", this.WorkingHours);
    		  this.Add("Sindicato", this.Syndicate);
    		  this.Add("Equipamentos TI", this.ITEquipament);
    		  this.Add("Serviçoes Telecom", this.TelecomServices);
    		  this.Add("Requições de Compra", this.RequestBuy);
    		  this.Add("Nova Filial", this.NewBranch);
    		  this.Add("Cadastro Cliente", this.CustomerBase);
    		  this.Add("Criação itens de Serviço", this.CreationServiceItems);
    		  this.Add("Regras Fiscais", this.FinancialRules);
    		  this.Add("Contrato de Venda", this.Contract);
    	  }
       }
      /**********************************************************************************************/
      class PersonalControl extends Customer
      {
    	  public String Company = null;
    	  public String Contract = null;
    	  public String CPF = null;
    	  public String DocumentType = null;
    	  public PersonalControl(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.Company = this.Get("Coligada").Value;
    		  this.Contract = this.Get("Contrato").Value;
    		  this.CPF = this.Get("CPF").Value;
    		  this.DocumentType = this.Get("Tipo de Documento").Value;
    		  /***/
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /***/
    		  this.Add("Coligada", this.Company);
    		  /***/
    		  if(!this.Contract.isEmpty())
    			  this.Add("Contrato", this.Contract);
    		  /***/
    		  if(!this.CPF.isEmpty())
    			  this.Add("CPF", this.CPF);
    		  /***/
    		  if(!this.DocumentType.isEmpty())
    			  this.Add("Tipo de Documento", this.DocumentType);
    	  }
      }
      /**********************************************************************************************/
      class Demission extends PersonalControl
      {
    	  public String ReviewDepartment = null;
    	  public Demission(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.ReviewDepartment = this.StringToQuestion(this.Get("Revisão DP").Value);
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /***/
    		  this.Add("Revisão DP", this.ReviewDepartment);
    	  }
      }
      /**********************************************************************************************/
      class Admission extends PersonalControl
      {
    	  public String ReviewDepartment = null;
    	  public String AcceptsBilling = null;
    	  public Admission(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.ReviewDepartment = this.StringToQuestion(this.Get("Revisão Departamento").Value);
    		  this.AcceptsBilling = this.StringToQuestion(this.Get("Aceita Cadastro").Value);
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /****/
    		  this.Add("Revisão Departamento", this.ReviewDepartment);
    		  this.Add("Aceita Faturamento", this.AcceptsBilling);
    	  }
      }
      /**********************************************************************************************/
      class Requeststaff extends Customer
      {
    	  public String Position = null;
    	  public String AdmissionReview = null;
    	  public Requeststaff(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.Position = this.Get("Posição").Value;
    		  this.AdmissionReview = this.StringToQuestion(this.Get("Revisão Admissão").Value);
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		super.Build();
    		/***/
    		this.Add("Posição", this.Position);
    		this.Add("Revisão Admissão", this.AdmissionReview);
    	  }
      }
      /**********************************************************************************************/
      class MonthlyDP extends Customer
      {
    	  public String Company = null;
    	  public String Contract = null;
    	  public String DocumentType = null;
    	  public String CPF = null;
    	  public String CurrentMonth = null;
    	  public String CurrentYear = null;
    	  public String ReviewDepartment = null;
    	  public MonthlyDP(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.Company = this.Get("Coligada").Value;
    		  this.Contract = this.Get("Contrato").Value;
    		  this.DocumentType = this.Get("Tipo de Documento").Value;
    		  this.CPF = this.Get("CPF").Value;
    		  this.CurrentMonth = this.Get("Mês Competencia").Value;
    		  this.CurrentYear = this.Get("Ano Competencia").Value;
    		  this.ReviewDepartment = this.StringToQuestion(this.Get("Revisão Departamento").Value);
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /***/
    		  this.Add("Coligada", this.Company);
    		  this.Add("Contrato", this.Contract);
    		  this.Add("Tipo de Documento", this.DocumentType);
    		  this.Add("CPF", this.CPF);
    		  this.Add("Mês Competencia", this.CurrentMonth);
    		  this.Add("Ano Compentencia", this.CurrentYear);
    		  this.Add("Revisão Departamento", this.ReviewDepartment);
    	  }
      }
      /**********************************************************************************************/
      class RemovalLicensingAbsence extends Customer
      {
    	  public String Company = null;
    	  public String Contract = null;
    	  public String DocumentType = null;
    	  public String CPF = null;
    	  public String DateBeginningAbsence = null;
    	  public String ReviewDP = null;
    	  public RemovalLicensingAbsence(ReadableDocument pDoc)
    	  {
    		  super(pDoc);
    	  }
    	  @Override
    	  public void Read() throws Exception
    	  {
    		  super.Read();
    		  /***/
    		  this.Company = this.Get("Coligada").Value;
    		  this.Contract = this.Get("Contrato").Value;
    		  this.DocumentType = this.Get("Tipo de Documento").Value;
    		  this.CPF = this.Get("CPF").Value;
    		  this.DateBeginningAbsence = 
    				  Definitions.DateFormat.StringToDateFormatted(this.Get("Data Inicio Ausência").Value);
    		  this.ReviewDP = this.StringToQuestion(this.Get("Revisão DP").Value);
    	  }
    	  @Override
    	  public void Build() throws Exception
    	  {
    		  super.Build();
    		  /***/
    		  this.Add("Coligada", this.Company);
    		  this.Add("Contrato", this.Contract);
    		  this.Add("Tipo de Documento", this.DocumentType);
    		  this.Add("CPF", this.CPF);
    		  this.Add("Data Inicio Ausência", this.DateBeginningAbsence);
    		  this.Add("Revisão DP", this.ReviewDP);
    	  }
      }
      /**********************************************************************************************/
      class Properties
      {
         public String Name = null;
         public Object Value = null;
         public Customer Clazz = null;
         public Properties(){}
         public Properties(String szName, Object pValue, Customer szClass)
         {
            this.Name = new String(szName);
            this.Value= pValue;
            this.Clazz= szClass;
         }
      }
      /**********************************************************************************************/
      class Parameters
      {
         private String[] m_pListProperties = null; /* { Data Inicial@Data Final@Selecione a Classe } */
         private ReadableDocument m_pDoc = null;
         public long StartDate = 0;
         public long EndDate = 0;
         public String ClassName = null;
         public Object Handle = null; 
         public Parameters(ReadableDocument pDoc) throws Exception
         {
        	 String pListOf = new String(getParam(Definitions.Parameters.ListOfProperties)); 
            /* test if an list of strings */
            if(pListOf.isEmpty())
               throw new Exception("Campo com lista de propriedades não encontrado!");
            /**/
            this.m_pListProperties = pListOf.split(Definitions.Parameters.GenerateArrayToken);
            /**/
            this.m_pDoc = pDoc;
            String szStart = this.m_pDoc.getField(this.m_pListProperties[0]).toString();
            String szEnd   = this.m_pDoc.getField(this.m_pListProperties[1]).toString();
            
            this.StartDate = Date.parse(szStart);
            this.EndDate   = Date.parse(szEnd);
            /* get selected class name */
            String szClass = this.m_pDoc.getField(this.m_pListProperties[2]).toString();
            /* debug out */
            debug(String.format("Selected class %s", szClass));
            /* alloc new string to class name */
            this.ClassName = new String(szClass);
         }
         public boolean ValidityInterval(Date pValue)
         {
            int ns = (pValue.getTime() >= this.StartDate? 1: 0);
            int ne = (pValue.getTime() <= this.EndDate? 1: 0);

            /* this inside of interval */
            if(ns == 1 && ne == 1)
               return true;
            
            return false;
         }
      };
      /**********************************************************************************************/
      class ActualData
      { 
         private String m_szUrl = new String("https://script.google.com/macros/s/AKfycby0VASs6VbfxB6Ikp-3QBYHb6y50o7_kFcs0Juz-U4tgvSZBVTU/exec");
         private String m_szKey = new String("PLOJV6Yw2xmEyGiXml9YPvy3vNk01HHXXX7NjHWahE7wIeqrBtKoxOKcQEdFuN7");
         private String m_szFolderId = new String("0B5nq06YnVukeWU4wMldVVzlIYWM"); //WRR
         private LinkedList<HTTPHeader> m_pHeader = new LinkedList<HTTPHeader>();
         private UrlFetchService m_pFetch = getUrlFetchService();
         private String m_szDocName = new String();
         byte[] m_pData = null;
         FetchOptions m_foOptions = FetchOptions.Builder.withDeadline(0x3c).doNotFollowRedirects(); 
         public ReadableDocument DocumentHandle = null;
         public String FormatPost = null;
         public String FileId = new String();
         public ActualData(ReadableDocument pDoc)
         {
            this.DocumentHandle = pDoc;
         }
         public void SetParameter(String szFileName, String szComplement) throws Exception
         {
            this.m_szDocName = szFileName;
            /**/
            szComplement = URLEncoder.encode(szComplement);
            szFileName   = URLEncoder.encode(szFileName);
            /* format string to send */
            this.FormatPost = String.format("DocName=%s&AuthorizeKey=%s&Items=%s", szFileName, this.m_szKey, szComplement);
            this.m_pData = this.FormatPost.getBytes("UTF-8");
         }
         public boolean Execute() throws Exception
         {
            HTTPResponse pHttp = this.m_pFetch.fetch(this.m_szUrl, HTTPMethod.POST, this.m_pData, this.m_pHeader, this.m_foOptions);
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
                     
                     /* debug */
                     debug(szContent);
                     /***/
                     szData = szContent.substring(n);
                     this.FileId = szData.substring(szParam.length(), szData.indexOf(")"));
                     /* write out */
                     debug(this.FileId);
                     return true;
                  }
               }
            }
            else
            {
               String szMsg = String.
            		   format("Ocorreu erro, chamada HTTP código %d invalido!", 
            				   pHttp.getResponseCode());
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
            Attachment pCopyTo = getDriveService().
            		copyDocument(this.FileId, Optional.of(this.m_szFolderId), Optional.absent());
            getDocumentService().lockDocument(this.DocumentHandle).addAttachment(pCopyTo);
         }
      }
      /**********************************************************************************************/
      class ExportableDocuments extends ActualData
      {
    	  class DirectionCall
    	  {
    		  Customer[] ListOfHandles = null; /* base class */
    		  String[] ListOfNames = null;	  /* class name */	
    		  public DirectionCall()
    		  {
    		  }
    		  public Customer Lookup(String szName) throws Exception
    		  {
    			  for(int nRow = 0; nRow < this.ListOfNames.length; nRow++)
				  {
    				  if(this.ListOfNames[nRow].equals(szName))
    				  {
    					  /* one failure to fix library variable */
    					  this.ListOfHandles[nRow].LibraryID = szName;
    					  return this.ListOfHandles[nRow];
    				  }
    			  }
    			  return null;
    		  }
    	  }
    	  /***/
         private DocumentService m_pDocumentService = getDocumentService();
         private String m_szJson = new String();
         public ExportableDocuments(ReadableDocument pDoc) throws Exception
         {
            super(pDoc);
         }
         @Override
         public boolean Execute() throws Exception
         {
            Parameters pp = new Parameters(super.DocumentHandle);
            boolean bSuccess = false;
            DirectionCall pListOfCalled = new DirectionCall();
            
            /* verify if has any selected option */
            if(pp.ClassName == null)
               throw new Exception("Nenhuma classe para exportação foi selecionada!");
            
            Date start = new Date();
            Date endrt = new Date();
            /* open spreadsheet */
            DocumentSearchRequest pSearch = new DocumentSearchRequest(pp.ClassName);
            ArrayList<ReadableDocument> pDocList = (ArrayList<ReadableDocument>)this.m_pDocumentService.findDocuments(pSearch);
               /***/
            if(pDocList.size() == 0)
               throw new Exception(String.
            		   format("Nenhum documento foi encontrado nesta classe %s para exportar!", 
            				   pp.ClassName));
               
            debug(String.format("start %s", (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(start)));
            
            for(ReadableDocument pDoc: pDocList)
            {
               Date pLastUpdated = pDoc.getLastModificationDate();
               /**/
               pListOfCalled.ListOfNames = new String[]
            		   {
              			Definitions.Classes.AccountsPayable,
            			Definitions.Classes.Admission,
            			Definitions.Classes.Billing,
            			Definitions.Classes.Demission,
            			Definitions.Classes.Monthly,
            			Definitions.Classes.RemovelLiceningAbsence,
            			Definitions.Classes.Requeststaff,
            			Definitions.Classes.SummaryContractualInfo
            		   };
               pListOfCalled.ListOfHandles = new Customer[]
            		   {
            		   	new AccountsPayable(pDoc),
            		   	new Admission(pDoc),
            		   	new Billing(pDoc),
            		   	new Demission(pDoc),
            		   	new MonthlyDP(pDoc),
            		   	new RemovalLicensingAbsence(pDoc),
            		   	new Requeststaff(pDoc),
            		   	new SummaryContractualInfo(pDoc)
            		   };
               
               if(!pp.ValidityInterval(pLastUpdated))
                  continue;
               
               /****/
               debug(pDoc.getClass().getName() + ".getTitle." + pDoc.getTitle());
               /* process all data of the document */
               if(!(bSuccess = this.ProcessClass(pListOfCalled.Lookup(pp.ClassName))))
            	   throw new Exception(String.
            			   format("Classe %s não suportada nesta operação!", pp.ClassName));
            }
            /* put on spreadsheet */
            if(!bSuccess)
               throw new Exception("Nenhum documento foi encontrado dentro do intervalo!");
            /**/
            endrt.setTime(start.getTime());
            /**/
            super.AttachCurrentSpreadsheet();
            debug(String.format("end %s", (new SimpleDateFormat("dd/MM/yyyy hh:mm:ss")).format(endrt)));
            return true;
         }
         /**********************************************************************************************/
         public boolean ProcessClass(Customer pOwn) throws Exception
         {
        	 if(pOwn == null)
        		 return false;
        	 
        	 /* call super class */
        	 pOwn.Read();
        	 pOwn.Build();
        	 this.Write(pOwn.Body);
        	 this.FlushOnSpreadsheet(pOwn.Body);
        	 /* force garbage collector to free memory */
        	 pOwn = null;
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
            super.SetParameter(super.DocumentHandle.getTitle(), this.m_szJson);
            super.Execute();
            /**/
            return true;
         }
      }
      /**********************************************************************************************/
      public void Run(ReadableDocument pDoc) throws Exception
      {
         ExportableDocuments p = new ExportableDocuments(pDoc);
         p.Execute();
      }
   };
   /**********************************************************************************************/
   AccountsPayableExportableDocs p = new AccountsPayableExportableDocs();
   p.Run(document);
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
