/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-20-2014
*  Description validate CPF
*  Last updated
*/
try
{
   class Data
   {
      static public int MAXOFFSET = 0xb;
      public ReadableDocument Doc = null;
      public String           Key = null;
      public Data(ReadableDocument pDoc)
      {
         this.Doc = pDoc;
         this.Key = pDoc.getTitle().replaceAll("\\.",  "").replaceAll("\\-", "");
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
      public Validate(ReadableDocument pDoc)
      {
         this.DataKey = new Data(pDoc);
      }
      public void ValidateCode(String szValue) throws Exception
      {
         /* check length */
         if(this.DataKey.Key.length() != Data.MAXOFFSET)
            throw new Exception(String.format("Quantidade de dígito(s) diferente de %d! Por favor, verifique e tente novamente!", 
                  Data.MAXOFFSET));
         
         /* check if all value is numeric */
         try
         {
            Long.parseLong(szValue);
         }
         catch(NumberFormatException e)
         {
            throw new Exception("Encontrado(s) caractere(s) invalidos nesta composição! Por favor, verifique e tente novamente!");
         }
      }
      public boolean Decode() throws Exception
      {
         this.m_data = this.DataKey.Key.substring(0, 9);

         for(String v: this.m_pCommon)
            if(v.equals(this.DataKey.Key))
               throw new Exception("CPF (" + this.DataKey.Key + ") com excesso de digitos identicos! Por favor, verifique e tente novamente!");
         //
         for (int i = 0; i < this.m_data.length(); i++)
            this.m_sum += Integer.parseInt(this.m_data.substring(i, i + 1)) * this.m_weigth--;
         //
         if (this.m_sum % Data.MAXOFFSET == 0 | this.m_sum % Data.MAXOFFSET == 1)
            this.m_first = new Integer(0);
         else
            this.m_first = new Integer(Data.MAXOFFSET - (this.m_sum % Data.MAXOFFSET));
         /**/ 
         this.m_sum = 0;
         this.m_weigth = Data.MAXOFFSET;
         /**/
         for (int i = 0; i < this.m_data.length(); i++)
            this.m_sum += Integer.parseInt(this.m_data.substring(i, i + 1)) * this.m_weigth--;
         /**/
         this.m_sum += this.m_first.intValue() * 2;

         if (this.m_sum % Data.MAXOFFSET == 0 | this.m_sum % Data.MAXOFFSET == 1)
            this.m_second = new Integer(0);
         else
            this.m_second = new Integer(Data.MAXOFFSET - (this.m_sum % Data.MAXOFFSET));
 
         return (this.m_first.toString() + this.m_second.toString()).equals(this.DataKey.Key.substring(9, Data.MAXOFFSET));
      }
      public void Execute() throws Exception
      {
         /* validate */
         this.ValidateCode(this.DataKey.Key);
         
         /* decode code */
         if(!Decode())
            throw new Exception("CPF " + this.DataKey.Key + " invalido! Por favor, verifique e tente novamente!");
         
         /* set title of document to code without formation */
         getDocumentService().lockDocument(this.DataKey.Doc).setTitle(this.DataKey.Key);
      }
   };
   /**********************************************************************************************/
   /* launch */
   Validate p = new Validate(document);
   p.Execute();
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}