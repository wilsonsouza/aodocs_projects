/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-20-2014
*  Description validate CPF
*  Last updated by wilson.souza 11-24-2014 joined national corporate registration and individual regsitration one class
*/
try
{
   class CodeKey
   {
      public String Value = null;
      public CodeKey(ReadableDocument pDoc) throws Exception
      {
         if(pDoc.getDocumentClass().equalsIgnoreCase("Fornecedor"))
         {
            Object pValue = pDoc.getField("CPF-CNPJ");
            //
            if(pValue != null)
               Value = new String(pDoc.getField("CPF-CNPJ").toString().trim());
            else
               Value = new String();
         }
         else
            Value = new String(pDoc.getTitle().trim());
      }
   }
   /**********************************************************************************************/
   class IndividualRegistration
   {
      private Validate m_pValidate = null;
      /**********************************************************************************************/
      class Data
      {
         static public int MAXOFFSET = 0xb;
         public ReadableDocument Doc = null;
         public String           Key = null;
         public Data(ReadableDocument pDoc) throws Exception
         {
            CodeKey p = new CodeKey(pDoc);
            //
            this.Doc = pDoc;
            this.Key = new String(p.Value.replaceAll("\\.",  "").replaceAll("\\-", ""));
            /* register to debug */
            debug("Key " + this.Key);
         }
      }
      /**********************************************************************************************/
      class Validate
      {
         public Data DataKey = null;
         private String m_data = null;
         private Integer m_first = null;
         private Integer m_second = null;
         private int m_weigth = 0xa;
         private int m_sum = 0;
         private String[] m_pCommon = new String[] 
         {
            "00000000000", 
            "11111111111", 
            "22222222222",
            "33333333333",
            "44444444444",
            "55555555555",
            "66666666666",
            "77777777777",
            "88888888888",
            "99999999999"
         };
         public Validate(ReadableDocument pDoc) throws Exception
         {
            this.DataKey = new Data(pDoc);
         }
         public void Verify(String szValue) throws Exception
         {
            /* check length */
            if(this.DataKey.Key.length() != this.DataKey.MAXOFFSET)
               throw new Exception(String.format("Quantidade de dígito(s) diferente de %d! Por favor, verifique e tente novamente!", 
                     this.DataKey.MAXOFFSET));
            /* check */
            for(String v: this.m_pCommon)
               if(v.equals(this.DataKey.Key))
                  throw new Exception("CPF (" + this.DataKey.Key + ") com excesso de digitos identicos! Por favor, verifique e tente novamente!");
            /* check if all value is numeric */
            try
            {
               Long.parseLong(szValue);
            }
            catch(NumberFormatException e)
            {
               throw new Exception("Encontrado(s) caractere(s) invalido(s) nesta composição! Por favor, verifique e tente novamente!");
            }
         }
         public boolean Decode() throws Exception
         {
            this.m_data = this.DataKey.Key.substring(0, 9);
            //
            for (int i = 0; i < this.m_data.length(); i++)
               this.m_sum += Integer.parseInt(this.m_data.substring(i, i + 1)) * this.m_weigth--;
            //
            if (this.m_sum % this.DataKey.MAXOFFSET == 0 | this.m_sum % this.DataKey.MAXOFFSET == 1)
               this.m_first = new Integer(0);
            else
               this.m_first = new Integer(this.DataKey.MAXOFFSET - (this.m_sum % this.DataKey.MAXOFFSET));
            /**/ 
            this.m_sum = 0;
            this.m_weigth = this.DataKey.MAXOFFSET;
            /**/
            for (int i = 0; i < this.m_data.length(); i++)
               this.m_sum += Integer.parseInt(this.m_data.substring(i, i + 1)) * this.m_weigth--;
            /**/
            this.m_sum += this.m_first.intValue() * 2;
   
            if (this.m_sum % this.DataKey.MAXOFFSET == 0 | this.m_sum % this.DataKey.MAXOFFSET == 1)
               this.m_second = new Integer(0);
            else
               this.m_second = new Integer(this.DataKey.MAXOFFSET - (this.m_sum % this.DataKey.MAXOFFSET));
    
            return (this.m_first.toString() + this.m_second.toString()).equals(this.DataKey.Key.substring(9, this.DataKey.MAXOFFSET));
         }
         public void Execute() throws Exception
         {
            /* validate */
            this.Verify(this.DataKey.Key);
            
            /* decode code */
            if(!Decode())
               throw new Exception("CPF " + this.DataKey.Key + " invalido! Por favor, verifique e tente novamente!");
            
            /* set title of document to code without formation */
            if(!this.DataKey.Doc.getDocumentClass().equalsIgnoreCase("Fornecedor"))
               getDocumentService().lockDocument(this.DataKey.Doc).setTitle(this.DataKey.Key);
         }
      }
      /**********************************************************************************************/
      public IndividualRegistration(ReadableDocument pDoc) throws Exception
      {
         /* initialized */
         this.m_pValidate = new Validate(pDoc);
      }
      public boolean IsOK() throws Exception
      {
         debug(String.format("KeyOffset %d StaticOffset %d", 
               this.m_pValidate.DataKey.Key.length(), this.m_pValidate.DataKey.MAXOFFSET));
         return (this.m_pValidate.DataKey.Key.length() == this.m_pValidate.DataKey.MAXOFFSET);
      }
      public void Execute() throws Exception
      {
         this.m_pValidate.Execute();
      }
   }
   /**********************************************************************************************/
   class NationalCorporateRegistration
   {
      private Validate m_pValidate = null;
      /**********************************************************************************************/
      class Data
      {
         static public int MAXOFFSET = 0xe;
         public ReadableDocument Doc = null;
         public String           Key = null;
         public Data(ReadableDocument pDoc) throws Exception
         {
            CodeKey p = new CodeKey(pDoc);
            //
            this.Doc = pDoc;
            this.Key = new String(p.Value.replaceAll("[^\\d]", ""));
            /* register to debug */
            debug("Key " + this.Key);
         }
      }
      /**********************************************************************************************/
      class Validate
      {
         private Data DataKey = null;
         private String[] m_pCommon = new String[]
         {
            "00000000000000",
            "11111111111111",
            "22222222222222",
            "33333333333333",
            "44444444444444",
            "55555555555555",
            "66666666666666",
            "77777777777777",
            "88888888888888",
            "99999999999999"
         };
         public  String m_data = null;
         /* constructor class */
         public Validate(ReadableDocument pDoc) throws Exception
         {
            this.DataKey = new Data(pDoc);
         }
         public void Verify(String szValue) throws Exception
         {
            /* check length */
            if(this.DataKey.Key.length() != this.DataKey.MAXOFFSET)
               throw new Exception(
               String.format("Quantidade de dígito(s) diferente de %d! Por favor, " +
               "verifique e tente novamente!", this.DataKey.MAXOFFSET));
            /* check */
            for(String v: this.m_pCommon)
               if(v.equals(this.DataKey.Key))
                  throw new Exception("CNPJ (" + this.DataKey.Key + ") com excesso de digitos identicos! Por favor, verifique e tente novamente!");
            /* check if all value is numeric */
            try
            {
               Long.parseLong(szValue);
            }
            catch(NumberFormatException e)
            {
               throw new Exception("Encontrado(s) caractere(s) invalido(s) nesta composição!"+
               " Por favor, verifique e tente novamente!");
            }
         }
         public boolean Decode() throws Exception
         {
            int d1 = 0;
            int d2 = 0;
            this.m_data = this.DataKey.Key;
            // calculate both digit
            for (int i = 0, m1 = 5, m2 = 6; i < 12; i++, m1--, m2--) 
            {
               m1 = (m1 < 2) ? m1 + 8 : m1; // shift list
               d1 += Integer.parseInt(this.m_data.substring(i, i + 1)) * m1;
               m2 = (m2 < 2) ? m2 + 8 : m2;
               d2 += Integer.parseInt(this.m_data.substring(i, i + 1)) * m2;
            }
            //
            d1 = 11 - d1 % 11;
            d1 = (d1 > 9) ? 0 : d1;
            // complete using the previous calculated digit
            d2 += d1 * 2;
            d2 = 11 - d2 % 11;
            d2 = (d2 > 9) ? 0 : d2;
            /***/
            return this.m_data.equals(this.m_data.substring(0, 0xc) + ("" + d1 + d2));
         }
         public void Execute() throws Exception
         {
            /* validate */
            this.Verify(this.DataKey.Key);

            /* decode code */
            if(!this.Decode())
               throw new Exception("CNPJ " + this.DataKey.Key 
               + " invalido! Por favor, verifique e tente novamente!");
            
            /* set title of document to code without formation */
            if(!this.DataKey.Doc.getDocumentClass().equalsIgnoreCase("Fornecedor"))
               getDocumentService().lockDocument(this.DataKey.Doc).setTitle(this.DataKey.Key);
         }
      }
      /**********************************************************************************************/
      public NationalCorporateRegistration(ReadableDocument pDoc) throws Exception
      {
         /* initialize */
         this.m_pValidate = new Validate(pDoc);
      }
      public boolean IsOK() throws Exception
      {
         return (this.m_pValidate.DataKey.Key.length() == this.m_pValidate.DataKey.MAXOFFSET);
      }
      public void Execute() throws Exception
      {
         this.m_pValidate.Execute();
      }
   }
   /**********************************************************************************************/
   /* process */
   IndividualRegistration pPhysical = new IndividualRegistration(document);
   NationalCorporateRegistration pCorporate = new NationalCorporateRegistration(document);
   /**/
   if(pPhysical.IsOK())
      pPhysical.Execute();
   else if(pCorporate.IsOK())
      pCorporate.Execute();
   else
   {
      if(document.getDocumentClass().equalsIgnoreCase("Fornecedor"))
         throw new Exception("CPF ou CNPJ invalido!");
      else
         throw new Exception(String.format("Isto %s não e um CPF ou CNPJ valido! Por favor verifique!", document.getTitle()));
   }
   /**/
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}