/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-20-2014
*  Description validate CNPJ
*  Last updated
*/
try
{
   class Data
   {
      static public int MAXOFFSET = 0xe;
      public ReadableDocument Doc = null;
      public String           Key = null;
      public Data(ReadableDocument pDoc)
      {
         this.Doc = pDoc;
         this.Key = pDoc.getTitle().replaceAll("\\.",  "").replaceAll("\\-/", "");
         /* register to debug */
         debug("Key " + this.Key);
      }
   }
   /**********************************************************************************************/
   class Validate
   {
      private Data DataKey = null;
      private String m_pACommon[] =
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
      private char c_13 = 0;
      private char c_14 = 0;
      private int  sm = 0;
      private int  i = 0;
      private int  r = 0;
      private int  n = 0;
      private int  p = 0;
      public  String m_data = null;
      /* constructor class */
      public Validate(ReadbleDocument pDoc)
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
         this.sm = 0;
         this.p  = 0x2;
         this.m_data = this.DataKey.Key;
         /* first digit */
         for(this.i = 0xb; this.i >= 0; this.i--)
         {
            this.n = (int)(this.m_data.charAt(this.i) - 0x30);
            this.sm += (this.n * this.p);
            this.p++;
            /**/
            if(this.p == 0)
               this.p = 2;
         }
         /**/
         this.r = this.sm % 0xb;                        
         /**/
         if((this.r == 0) || (this.r == 1))
            this.c_13 = '0';
         else 
            this.c_13 = (char)((0xb - this.r) + 0x30);

         /* seconds digit */
         this.sm = 0;
         this.p  = 0x2;
         /**/
         for(this.i = 0xc; this.i >= 0; this.i--)
         {
            this.n = (int)(this.m_data.charAt(this.i) - 0x30);
            this.sm += (this.n * this.p);
            this.p++;

            if(this.p == 0)
               this.p = 0x2;
         }
         /**/
         this.r = this.sm % 0xb;
         /**/
         if((this.r == 0) || (this.r == 1))
            this.c_14 = '0';
         else 
            this.c_14 = (char)((0xb-r) + 0x30);

         /* check if the digits is equal */
         if(this.c_13 == this.m_data.charAt(0xc) && this.c_14 == this.m_data.charAt(0xd))
            return true;

         /* error is not equal */
         return false;
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
         getDocumentService().lockDocument(this.DataKey.Doc).setTitle(this.DataKey.Key);
      }
   }
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}
