/*
*
*  Copyright (C) 2014 Dedalus Prime
*
*  Created by Wilson.Souza
*  Created 8-15-2014
*  Description parse all body of document and update all metadata of self.
*  Last updated 8-19-2014
*/
import java.lang.*;
import java.util.*;

try
{
   //properties
   class Properties
   {
      //to class Tipo de Documento de Admissao
      public class TipoDocumentoAdmissao
      {
         public static String CODIGO_TIPO_DOCUMENTO = "codigo_tipo_documento";
         public static String NOME_TIPO_DOCUMENTO   = "nome_tipo_documento";
         public static String STATUS                = "status";
      };
      //to class Tipo de Documento de Demissao
      public class TipoDocumentoDemissao
      {
         public static String CODIGO_TIPO_DOCUMENTO = "codigo_tipo_documento";
         public static String NOME_TIPO_DOCUMENTO   = "nome_tipo_documento";
         public static String STATUS                = "status";
      };
      //to class CPF
      public class CPF
      {
         public static String CODIGO_COLIGADA       = "codigo_coligada";
         public static String NOME_COLIGADA         = "nome_coligada";
         public static String REQUISICAO_PESSOAL_ID = "requisicao_pessoal_id";
         public static String STATUS                = "status";
      };
      //to class Coligada
      public class Coligada
      {
         public static String CODIGO_COLIGADA = "codigo_coligada";
         public static String NOME_COLIGADA   = "nome_coligada";
      };
      //to class Contrato
      public class Contrato
      {
         public static String CODIGO_CONTRATO = "codigo_contrato";
         public static String NOME_CONTRATO   = "nome_contrato";
         public static String STATUS          = "status";
      };
      //to 
      public class DocumentosAdmissao
      {
         public static String COLIGADA                 = "Coligada";
         public static String CONTRATO                 = "Contrato";
         public static String TIPO_DE_DOCUMENTO        = "Tipo de Documento";
         public static String CPF                      = "CPF";
         public static String REVISAO_DEPARTAMENTO     = "Revisão Departamento";
         public static String ACEITE_CADASTRO          = "Aceite Cadastro";
         public static String RECEBIMENTO_ARQUIVO      = "Recebimento Arquivo";
         public static String ISDOCUMENTCREATEDBYEMAIL = "IsDocumentCreatedbyEmail";
         public static String STORELISTMETADATA        = "StoreListMetadata";
      };
      //
      public class DocumentosDemissao
      {
         public static String COLIGADA                 = "Coligada";
         public static String CONTRATO                 = "Contrato";
         public static String TIPO_DE_DOCUMENTO        = "Tipo de Documento";
         public static String CPF                      = "CPF";
         public static String REVISAO_DEPARTAMENTO     = "Revisão DP";
         public static String ACEITE_CADASTRO          = "Aceite Cadastro";
         public static String RECEBIMENTO_ARQUIVO      = "Recebimento Arquivo";
         public static String ISDOCUMENTCREATEDBYEMAIL = "IsDocumentCreatedbyEmail";
         public static String STORELISTMETADATA        = "StoreListMetadata";
      };
      //
      public class RequisaoPessoal
      {
         public static String COLIGADA                 = "Coligada";
         public static String CONTRATO                 = "Contrato";
         public static String POSICAO                  = "Posição";
         public static String TIPO_DE_DOCUMENTO        = "Tipo de Documento";
         public static String REVISAO_ADMISSAO         = "Revisão Admissão";
         public static String ISDOCUMENTCREATEDBYEMAIL = "IsDocumentCreatedbyEmail";
      };
      //class property to identify all names in use on source code
      public class MetaDataKey
      {
         public static String START     = "---METADADOS---";
         public static String END       = "---";
         public static String DIVLINE   = ":";
         public static String DELTRASH  = "\\<.*?\\>";
         public static String MAKEARRAY = "\n";
         public static String DELLINE   = "\r";
         //
         public static int NAME         = 0;
         public static int VALUE        = 1;
         //
         public static String NO        = "NÃO";
         public static String YES       = "SIM";
      };
   };
   //find document
   class SearchDocuments
   {
      ReadableDocument m_pReadable = null;
      DocumentService  m_pService  = getDocumentService();
      Document         m_pDocument = null;

      public SearchDocuments(ReadableDocument pReadable)
      {
         m_pReadable = pReadable;
         m_pDocument = m_pService.lockDocument(m_pReadable);
      }
      public List<ReadableDocuments> Search(String szClassName)
      {
         try
         {
            return m_pService.findDocuments(new DocumentSearchRequest(szClassName));
         }
         catch(Exception e)
         {
            debug(e.getMessage());
         }                        
         return new ArrayList<ReadableDocument>();
      }
   };
   //load relations
   class LoadRelations extends SearchDocuments
   {
      private ArrayList<String> m_pRelations = null;
      /**/
      public LoadRelations(ReadableDocument pReadable, String szRelationName)
      {
         /* bring list of documents on the class */
         super(pReadable);
         m_pRelations = pReadable->getToRelatedDocumentIds(szRelationName)
      }
      public ArrayList<String> GetListRelations()
      {
         return m_pRelations;
      }      
   };
}
catch(Exception e)
{
   debug(e.getMessage());
}