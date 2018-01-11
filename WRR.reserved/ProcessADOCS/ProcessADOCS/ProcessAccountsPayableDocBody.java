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
*  Last updated 04-13-2015 added field IsAlreadyProcessed to inform if already processed
*  Last updated 05-08-2015 fix any failures on source code
*  Last updated 05-11-2015 fix date conversion
*  Last updated 05-19-2015 fix property IsAlreadyProcessed
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
	class TextHtmlProcessor 
	{
		private String html;
		private StringBuilder text;
		private int pos;
		private Deque<String> tagStack = new LinkedList<String>();
		public TextHtmlProcessor(String html) 
		{
			this.html = html;
		}

		public String process() 
		{
			pos = 0;
			text = new StringBuilder();

			while (pos < html.length()) 
			{
				if (getTag())
					continue;
				if (getCharEntity())
					continue;

				getChar();
			}

			return text.toString();
		}

		private boolean getTag() 
		{
			int pos = this.pos;

			if (html.charAt(pos++) != '<')
				return false;

			boolean endTag = false;
			if (html.charAt(pos) == '/') 
			{
				++pos;
				endTag = true;
			}

			StringBuilder tagName = new StringBuilder();

			while (Character.isLetterOrDigit(html.charAt(pos))) 
			{
				tagName.append(html.charAt(pos++));
			}

			if (tagName.length() == 0)
				return false;

			StringBuilder attributes = new StringBuilder();

			boolean voidTag = false;
			char escapeChar = '\0';
			while (pos < html.length()
					&& (html.charAt(pos) != '>' || escapeChar != '\0')) 
			{
				if (escapeChar != '\0') 
				{
					if (escapeChar == html.charAt(pos)) 
					{
						escapeChar = '\0';
					}
				} 
				else 
				{
					if (html.charAt(pos) == '/') 
					{
						voidTag = true;
					}

					if (html.charAt(pos) == '\'' || html.charAt(pos) == '"') 
					{
						escapeChar = html.charAt(pos);
					}
				}

				attributes.append(html.charAt(pos++));
			}

			++pos;

			if (voidTag) 
			{
				gotStartTag(tagName.toString(), attributes.toString());
				gotEndTag(tagName.toString());
			} 
			else if (endTag) 
			{
				gotEndTag(tagName.toString());
			} 
			else 
			{
				gotStartTag(tagName.toString(), attributes.toString());
			}

			this.pos = pos;
			return true;
		}

		private boolean getCharEntity() 
		{
			int pos = this.pos;

			if (html.charAt(pos++) != '&')
				return false;

			StringBuilder charEntity = new StringBuilder("&");

			while (pos < html.length() && html.charAt(pos) != ';')
				charEntity.append(html.charAt(pos++));

			charEntity.append(html.charAt(pos++));
			text.append(convertCharEntity(charEntity.toString()));
			this.pos = pos;
			return true;
		}

		private String convertCharEntity(String t) 
		{
			if (t.equals("&Aacute;"))
				return "\u00C1";
			if (t.equals("&aacute;"))
				return "\u00E1";
			if (t.equals("&Acirc;"))
				return "\u00C2";
			if (t.equals("&acirc;"))
				return "\u00E2";
			if (t.equals("&acute;"))
				return "\u00B4";
			if (t.equals("&AElig;"))
				return "\u00C6";
			if (t.equals("&aelig;"))
				return "\u00E6";
			if (t.equals("&Agrave;"))
				return "\u00C0";
			if (t.equals("&agrave;"))
				return "\u00E0";
			if (t.equals("&AMP;"))
				return "\u0026";
			if (t.equals("&amp;"))
				return "\u0026";
			if (t.equals("&angst;"))
				return "\u00C5";
			if (t.equals("&apos;"))
				return "\u0027";
			if (t.equals("&Aring;"))
				return "\u00C5";
			if (t.equals("&aring;"))
				return "\u00E5";
			if (t.equals("&ast;"))
				return "\u002A";
			if (t.equals("&Atilde;"))
				return "\u00C3";
			if (t.equals("&atilde;"))
				return "\u00E3";
			if (t.equals("&Auml;"))
				return "\u00C4";
			if (t.equals("&auml;"))
				return "\u00E4";
			if (t.equals("&brvbar;"))
				return "\u00A6";
			if (t.equals("&bsol;"))
				return "\\u005C";
			if (t.equals("&Ccedil;"))
				return "\u00C7";
			if (t.equals("&ccedil;"))
				return "\u00E7";
			if (t.equals("&cedil;"))
				return "\u00B8";
			if (t.equals("&Cedilla;"))
				return "\u00B8";
			if (t.equals("&cent;"))
				return "\u00A2";
			if (t.equals("&CenterDot;"))
				return "\u00B7";
			if (t.equals("&centerdot;"))
				return "\u00B7";
			if (t.equals("&circledR;"))
				return "\u00AE";
			if (t.equals("&colon;"))
				return "\u003A";
			if (t.equals("&comma;"))
				return "\u002C";
			if (t.equals("&commat;"))
				return "\u0040";
			if (t.equals("&COPY;"))
				return "\u00A9";
			if (t.equals("&copy;"))
				return "\u00A9";
			if (t.equals("&curren;"))
				return "\u00A4";
			if (t.equals("&deg;"))
				return "\u00B0";
			if (t.equals("&DiacriticalAcute;"))
				return "\u00B4";
			if (t.equals("&DiacriticalGrave;"))
				return "\u0060";
			if (t.equals("&die;"))
				return "\u00A8";
			if (t.equals("&div;"))
				return "\u00F7";
			if (t.equals("&divide;"))
				return "\u00F7";
			if (t.equals("&dollar;"))
				return "\u0024";
			if (t.equals("&Dot;"))
				return "\u00A8";
			if (t.equals("&DoubleDot;"))
				return "\u00A8";
			if (t.equals("&Eacute;"))
				return "\u00C9";
			if (t.equals("&eacute;"))
				return "\u00E9";
			if (t.equals("&Ecirc;"))
				return "\u00CA";
			if (t.equals("&ecirc;"))
				return "\u00EA";
			if (t.equals("&Egrave;"))
				return "\u00C8";
			if (t.equals("&egrave;"))
				return "\u00E8";
			if (t.equals("&equals;"))
				return "\u003D";
			if (t.equals("&ETH;"))
				return "\u00D0";
			if (t.equals("&eth;"))
				return "\u00F0";
			if (t.equals("&Euml;"))
				return "\u00CB";
			if (t.equals("&euml;"))
				return "\u00EB";
			if (t.equals("&excl;"))
				return "\u0021";
			if (t.equals("&fjlig;"))
				return "\u006A";
			if (t.equals("&frac12;"))
				return "\u00BD";
			if (t.equals("&frac14;"))
				return "\u00BC";
			if (t.equals("&frac34;"))
				return "\u00BE";
			if (t.equals("&grave;"))
				return "\u0060";
			if (t.equals("&GT;"))
				return "\u003E";
			if (t.equals("&gt;"))
				return "\u003E";
			if (t.equals("&half;"))
				return "\u00BD";
			if (t.equals("&Hat;"))
				return "\u005E";
			if (t.equals("&Iacute;"))
				return "\u00CD";
			if (t.equals("&iacute;"))
				return "\u00ED";
			if (t.equals("&Icirc;"))
				return "\u00CE";
			if (t.equals("&icirc;"))
				return "\u00EE";
			if (t.equals("&iexcl;"))
				return "\u00A1";
			if (t.equals("&Igrave;"))
				return "\u00CC";
			if (t.equals("&igrave;"))
				return "\u00EC";
			if (t.equals("&iquest;"))
				return "\u00BF";
			if (t.equals("&Iuml;"))
				return "\u00CF";
			if (t.equals("&iuml;"))
				return "\u00EF";
			if (t.equals("&laquo;"))
				return "\u00AB";
			if (t.equals("&lbrace;"))
				return "\u007B";
			if (t.equals("&lbrack;"))
				return "\u005B";
			if (t.equals("&lcub;"))
				return "\u007B";
			if (t.equals("&lowbar;"))
				return "\u005F";
			if (t.equals("&lpar;"))
				return "\u0028";
			if (t.equals("&lsqb;"))
				return "\u005B";
			if (t.equals("&LT;"))
				return "\u003C";
			if (t.equals("&lt;"))
				return "\u003C";
			if (t.equals("&macr;"))
				return "\u00AF";
			if (t.equals("&micro;"))
				return "\u00B5";
			if (t.equals("&midast;"))
				return "\u002A";
			if (t.equals("&middot;"))
				return "\u00B7";
			if (t.equals("&nbsp;")) // || t.equals("&nbsp"))
				return "\u00A0"; // replace("\uc2a0", " ")
			if (t.equals("&NewLine;"))
				return "\\u000A";
			if (t.equals("&NonBreakingSpace;"))
				return "\u00A0";
			if (t.equals("&not;"))
				return "\u00AC";
			if (t.equals("&Ntilde;"))
				return "\u00D1";
			if (t.equals("&ntilde;"))
				return "\u00F1";
			if (t.equals("&num;"))
				return "\u0023";
			if (t.equals("&Oacute;"))
				return "\u00D3";
			if (t.equals("&oacute;"))
				return "\u00F3";
			if (t.equals("&Ocirc;"))
				return "\u00D4";
			if (t.equals("&ocirc;"))
				return "\u00F4";
			if (t.equals("&Ograve;"))
				return "\u00D2";
			if (t.equals("&ograve;"))
				return "\u00F2";
			if (t.equals("&ordf;"))
				return "\u00AA";
			if (t.equals("&ordm;"))
				return "\u00BA";
			if (t.equals("&Oslash;"))
				return "\u00D8";
			if (t.equals("&oslash;"))
				return "\u00F8";
			if (t.equals("&Otilde;"))
				return "\u00D5";
			if (t.equals("&otilde;"))
				return "\u00F5";
			if (t.equals("&Ouml;"))
				return "\u00D6";
			if (t.equals("&ouml;"))
				return "\u00F6";
			if (t.equals("&para;"))
				return "\u00B6";
			if (t.equals("&percnt;"))
				return "\u0025";
			if (t.equals("&period;"))
				return "\u002E";
			if (t.equals("&plus;"))
				return "\u002B";
			if (t.equals("&PlusMinus;"))
				return "\u00B1";
			if (t.equals("&plusmn;"))
				return "\u00B1";
			if (t.equals("&pm;"))
				return "\u00B1";
			if (t.equals("&pound;"))
				return "\u00A3";
			if (t.equals("&quest;"))
				return "\u003F";
			if (t.equals("&QUOT;"))
				return "\\u0022";
			if (t.equals("&quot;"))
				return "\\u0022";
			if (t.equals("&raquo;"))
				return "\u00BB";
			if (t.equals("&rbrace;"))
				return "\u007D";
			if (t.equals("&rbrack;"))
				return "\u005D";
			if (t.equals("&rcub;"))
				return "\u007D";
			if (t.equals("&REG;"))
				return "\u00AE";
			if (t.equals("&reg;"))
				return "\u00AE";
			if (t.equals("&rpar;"))
				return "\u0029";
			if (t.equals("&rsqb;"))
				return "\u005D";
			if (t.equals("&sect;"))
				return "\u00A7";
			if (t.equals("&semi;"))
				return "\u003B";
			if (t.equals("&shy;"))
				return "\u00AD";
			if (t.equals("&sol;"))
				return "\u002F";
			if (t.equals("&strns;"))
				return "\u00AF";
			if (t.equals("&sup1;"))
				return "\u00B9";
			if (t.equals("&sup2;"))
				return "\u00B2";
			if (t.equals("&sup3;"))
				return "\u00B3";
			if (t.equals("&szlig;"))
				return "\u00DF";
			if (t.equals("&Tab;"))
				return "\u0009";
			if (t.equals("&THORN;"))
				return "\u00DE";
			if (t.equals("&thorn;"))
				return "\u00FE";
			if (t.equals("&times;"))
				return "\u00D7";
			if (t.equals("&Uacute;"))
				return "\u00DA";
			if (t.equals("&uacute;"))
				return "\u00FA";
			if (t.equals("&Ucirc;"))
				return "\u00DB";
			if (t.equals("&ucirc;"))
				return "\u00FB";
			if (t.equals("&Ugrave;"))
				return "\u00D9";
			if (t.equals("&ugrave;"))
				return "\u00F9";
			if (t.equals("&uml;"))
				return "\u00A8";
			if (t.equals("&UnderBar;"))
				return "\u005F";
			if (t.equals("&Uuml;"))
				return "\u00DC";
			if (t.equals("&uuml;"))
				return "\u00FC";
			if (t.equals("&verbar;"))
				return "\u007C";
			if (t.equals("&vert;"))
				return "\u007C";
			if (t.equals("&VerticalLine;"))
				return "\u007C";
			if (t.equals("&Yacute;"))
				return "\u00DD";
			if (t.equals("&yacute;"))
				return "\u00FD";
			if (t.equals("&yen;"))
				return "\u00A5";
			if (t.equals("&yuml;"))
				return "\u00FF";

			return "?";
		}

		private boolean getChar() 
		{
			text.append(html.charAt(pos++));
			return true;
		}

		private void gotStartTag(String tagName, String attributes) 
		{
			tagStack.push(tagName.toLowerCase());

			if (tagName.equals("div")) 
			{
				text.append("\n");
			} 
			else if (tagName.equals("p")) 
			{
				text.append("\n\n");
			} 
			else if (tagName.equals("tr")) 
			{
				text.append("\n");
			} 
			else if (tagName.equals("td")) 
			{
				text.append("\t");
			} 
			else if (tagName.equals("br")) 
			{
				text.append("\n");
			}
		}

		private void gotEndTag(String tagName) 
		{
			tagName = tagName.toLowerCase();

			while (true) 
			{
				String tag = (String)tagStack.peek();
				
				if (null == tag)
					break;

				if (!tagName.equals(tag)) 
				{
					gotEndTag(tag);
				} 
				else 
				{
					break;
				}
			}

			try 
			{
				tagStack.pop();
			} 
			catch (NoSuchElementException e) 
			{
				debug(e.getMessage());
			}
		}
	}
	
   class Parameters
   {
      static public String ListClass = "classes";
      static public String ListRelations = ".relations";
      static public String FieldsOnEmail = ".fields";
      static public String DocumentTop = "---METADADOS---";
      static public String DocumentEnd = "---";
      static public String DivideLine = ":";
      static public String RemoveTokens = "\\<.*?>";
      static public String GenerateArray = "\n";
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
   class Parse extends TextHtmlProcessor
   {
      private ArrayList<DataQueue> m_pList = new ArrayList<DataQueue>();
      private Offset m_pOffset = null;
      public Parse(String szData)
      {
    	 super(szData);
         /* initialize buffer and convert html format to text format*/
         this.m_pOffset = new Offset(-1, -1, super.process());
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
            /* check boolean line */
			String szValue = new String(pQueue[1].getBytes("UTF-8"), "UTF-8");
            /**/
            if(szValue.equalsIgnoreCase("sim"))
               szValue = "true";
            else if(szValue.equalsIgnoreCase("não"))
               szValue = "false";
            /****/
            this.m_pList.add(new DataQueue(pQueue[0].trim(), szValue.trim()));
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
	  public String GetValidNumbers(String szData) throws Exception
	  {
		String szOut = new String();
		//
		for(byte c: szData.getBytes())
		{
			if(c >= 0x30 && c <= 0x39)
				szOut += String.format("%c", c);
			//
			if(c == 0x2f)
				szOut += String.format("%c", c);

			//debug(String.format("Char %c", c));
		}
		//debug(String.format("outdata: %s", szOut));
		return szOut;
	  }
      public void WritePropertiesValue(DataQueue pData) throws Exception
      {
         /* all values must to be put in property */
         if(pData.Name.equalsIgnoreCase("Data Emissão"))
         {
			try
			{
				String szValue = new String(this.GetValidNumbers(pData.Value));
				Date pDate = new Date(Date.parse(szValue));
				/* set */
				getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, pDate);
			}
			catch(Exception e)
			{
				debug("WriteField: " + pData.Name + ", " + e.getMessage());
			}
            return;
         }
		 /***/
		 if(pData.Name.equalsIgnoreCase("Data de Vencimento"))
		 {
			try
			{
				String szValue = new String(this.GetValidNumbers(pData.Value));
				Date pDate = new Date(Date.parse(szValue));
				/**/
				getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, pDate);
			}
			catch(Exception e)
			{
				debug("WriteField: " + pData.Name + ", " + e.getMessage());
			}
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
		 /**/
		 if(pData.Name.equalsIgnoreCase("Valor"))
		 {
			try
			{
				String szValue = new String(this.GetValidNumbers(pData.Value));
				double dValue = Double.parseDouble(szValue);
				getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, dValue);
			}
			catch(Exception e)
			{
				debug("WriteField: " + pData.Name + ", " + e.getMessage());
			}
			return; 
		 }
         /* write */
		 try
		 {
			 debug(String.format("Write %s=%s", pData.Name, pData.Value));
			 getDocumentService().lockDocument(this.m_pDoc).setField(pData.Name, pData.Value);
		 }
		 catch(Exception e)
		 {
			debug("WriteField: " + pData.Name + ", " + e.getMessage());
		 }
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
	  public boolean IsUserAccessing()
	  {
         /* validity property */
         try
         {
            Object pData = this.m_pDoc.getField("IsAlreadyProcessed");

			if(pData != null)
				if(((String)pData).equalsIgnoreCase("OK"))
					return false;
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }
		 return true;
	  }
	  public void UpdatePropertyIsAlreadyProcessed()
	  {
		try
		{
			getDocumentService().lockDocument(this.m_pDoc).setField("IsAlreadyProcessed", "OK");
		}
		catch(Exception e)
		{
			debug(e.getMessage());
		}
	  }
      @Override
      public void Execute() throws Exception
      {
    	 ClassNames p = new ClassNames();
         /* verify */
		 if(!this.IsUserAccessing())
			return;
         /* check if must be processed */
         if(!super.IsMustProcess())
            return;
         /* check class */
         if(!p.IsAllowed(this.m_pDoc))
            return;

         /* when used the word super call super method override */
         super.Execute();
		 UpdatePropertyIsAlreadyProcessed();
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