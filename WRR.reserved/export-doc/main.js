/**
 * Monta a p�gina principal.
 *
 * @return a p�gina HTML para exibi��o.
 */
function doGet( request )
{
   return HtmlService.createTemplateFromFile( 'index' )
       .evaluate()
       .setSandboxMode( HtmlService.SandboxMode.IFRAME );
}

/**
 * Retorna o conte�do de uma p�gina HTML.
 *
 * @param filename o nome do arquivo.
 *
 * @return o conte�do HTML do arquivo.
 */
function include( filename )
{
   return HtmlService.createTemplateFromFile( filename )
       .evaluate()
       .setSandboxMode( HtmlService.SandboxMode.IFRAME )
       .getContent();
}

/**
 * O c�digo de seguran�a do AODocs deve estar definido na propriedade do script, item SECURITYCODE.
 *
 * @return o c�digo de seguran�a do AODocs.
 */
function getSecurityCode()
{
   var props = PropertiesService.getScriptProperties();
   return props.getProperty( "SECURITYCODE" );
}

/**
 * O c�digo da pasta onde gravar a planilha deve estar definido em EXPORTFOLDERID na
 * propriedade do script.
 *
 * @return um objeto da pasta, baseado no c�digo.
 */
function getExportFolder()
{
   var props = PropertiesService.getScriptProperties();
   var exportFolderId = props.getProperty( "EXPORTFOLDERID" );
   return DriveApp.getFolderById( exportFolderId );
}

/**
 * Classe para controle do cabe�alho.
 *
 * Tem intelig�ncia para ir adicionando novos itens ao cabe�alho se n�o
 * estiver pr�-definido.
 *
 * Para definir a ordem dos campos do cabe�alho, inicie a vari�vel
 *   this.headers
 * com os cabe�alhos e sua posi��o.
 *
 * Exemplo:
 *  this.headers = {'title':0, 'param2':2, 'param1':1};
 *
 * Neste exemplo, title ficar� na primeira posi��o, param1 na segunda e param2 na terceira.
 */
var Header = function ()
{
   this.headers = {};
   this.count = Object.keys( this.headers ).length;
};

/**
 * Solicita ou cria a posi��o de um cabe�alho.
 *
 * Se o cabe�alho n�o existir, ele ser� adicionado ao final da lista.
 *
 * @headerName o nome do cabe�alho.
 *
 * @return a posi��o do cabe�alho.
 */
Header.prototype.get = function ( headerName )
{
   if ( !( headerName in this.headers ) )
   {
      this.headers[headerName] = this.count++;
   }
   return this.headers[headerName];
}

/**
 * Solicita os nomes do cabe�alho, em ordem.
 *
 * @return um array com os nomes do cabe�alho, em ordem.
 */
Header.prototype.getValues = function ()
{
   var values = []
   for ( var h in this.headers )
   {
      values[this.headers[h]] = h;
   }
   return values;
}

/**
 * Cria uma planilha � partir de uma lista de documentos.
 *
 * @param documentList a lista de documentos do AODocs.
 *
 * @return informa��es da planilha criada.
 */
function createSpreadsheet( documentList )
{
   var d = getData( documentList );

   var rows = d.data.length + 1;
   var columns = d.header.count;

   var now = new Date();
   var spreadsheet = SpreadsheetApp.create( 'export-' + now.getTime(), rows, columns );
   moveSpreadsheet_( spreadsheet );

   var sheet = spreadsheet.getSheets()[0];

   var headerRange = sheet.getRange( 1, 1, 1, columns );
   headerRange.setValues( [d.header.getValues()] );

   var dataRange = sheet.getRange( 2, 1, rows - 1, columns );
   dataRange.setValues( d.data );

   return { 'name': spreadsheet.getName(), 'url': spreadsheet.getUrl(), 'id': spreadsheet.getId() };
}

/**
 * Cria o cabe�alho e os dados para gravar na planilha.
 *
 * @param documentList a lista de documentos do AODocs.
 *
 * @return um objecto com o cabe�alho (header) e os dados (data).
 */
function getData( documentList )
{
   var d = { header: new Header(), data: [] };

   try
   {
      for ( var i in documentList )
      {
         var doc = documentList[i];

         var row = [];

         row[d.header.get( 'title' )] = doc['title'];

         for ( var j in doc['fields'] )
         {
            var field = doc['fields'][j];

            if ( field['values'] )
            {
               if ( field['type'] == 'DATE' )
               {
                  var pDate = new Date();
                  pDate.setTime( parseInt( field['values'] ) );
                  /**/
                  row[d.header.get( field['fieldName'] )] = pDate.toUTCString();
               }
               else
               {
                  row[d.header.get( field['fieldName'] )] = field['values'].join( ', ' );
               }
            }
            else
            {
               row[d.header.get( field['fieldName'] )] = '';
            }
         }

         d.data.push( row );
      }
   }
   catch(e)
   {
      Logger.log( e );
   }
   return d;
}

/**
 * Move a planilha para a pasta definida na propriedade do script.
 *
 * @param spreadsheet um objeto Spreadsheet que � a planilha a ser movimentada.
 */
function moveSpreadsheet_( spreadsheet )
{
   var spreadsheetFile = DriveApp.getFileById( spreadsheet.getId() );

   var folders = spreadsheetFile.getParents();
   while ( folders.hasNext() )
   {
      var folder = folders.next();
      folder.removeFile( spreadsheetFile );
   }

   var exportFolder = getExportFolder();
   exportFolder.addFile( spreadsheetFile );
}


/** S� para teste **/

function test()
{
   var sample = [{ 'title': 'Teste', 'fields': [{ 'values': ['v1'], 'fieldName': 'f1' }, { 'values': ['v2'], 'fieldName': 'f2' }] }];

   createSpreadsheet( sample );
}