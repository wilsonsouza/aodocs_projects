/**
 * Monta a página principal.
 *
 * @return a página HTML para exibição.
 */
function doGet( request )
{
   return HtmlService.createTemplateFromFile( 'index' )
       .evaluate()
       .setSandboxMode( HtmlService.SandboxMode.IFRAME );
}

/**
 * Retorna o conteúdo de uma página HTML.
 *
 * @param filename o nome do arquivo.
 *
 * @return o conteúdo HTML do arquivo.
 */
function include( filename )
{
   return HtmlService.createTemplateFromFile( filename )
       .evaluate()
       .setSandboxMode( HtmlService.SandboxMode.IFRAME )
       .getContent();
}

/**
 * O código de segurança do AODocs deve estar definido na propriedade do script, item SECURITYCODE.
 *
 * @return o código de segurança do AODocs.
 */
function getSecurityCode()
{
   var props = PropertiesService.getScriptProperties();
   return props.getProperty( "SECURITYCODE" );
}

/**
 * O código da pasta onde gravar a planilha deve estar definido em EXPORTFOLDERID na
 * propriedade do script.
 *
 * @return um objeto da pasta, baseado no código.
 */
function getExportFolder()
{
   var props = PropertiesService.getScriptProperties();
   var exportFolderId = props.getProperty( "EXPORTFOLDERID" );
   return DriveApp.getFolderById( exportFolderId );
}

/**
 * Classe para controle do cabeçalho.
 *
 * Tem inteligência para ir adicionando novos itens ao cabeçalho se não
 * estiver pré-definido.
 *
 * Para definir a ordem dos campos do cabeçalho, inicie a variável
 *   this.headers
 * com os cabeçalhos e sua posição.
 *
 * Exemplo:
 *  this.headers = {'title':0, 'param2':2, 'param1':1};
 *
 * Neste exemplo, title ficará na primeira posição, param1 na segunda e param2 na terceira.
 */
var Header = function ()
{
   this.headers = {};
   this.count = Object.keys( this.headers ).length;
};

/**
 * Solicita ou cria a posição de um cabeçalho.
 *
 * Se o cabeçalho não existir, ele será adicionado ao final da lista.
 *
 * @headerName o nome do cabeçalho.
 *
 * @return a posição do cabeçalho.
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
 * Solicita os nomes do cabeçalho, em ordem.
 *
 * @return um array com os nomes do cabeçalho, em ordem.
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
 * Cria uma planilha à partir de uma lista de documentos.
 *
 * @param documentList a lista de documentos do AODocs.
 *
 * @return informações da planilha criada.
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
 * Cria o cabeçalho e os dados para gravar na planilha.
 *
 * @param documentList a lista de documentos do AODocs.
 *
 * @return um objecto com o cabeçalho (header) e os dados (data).
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
 * @param spreadsheet um objeto Spreadsheet que é a planilha a ser movimentada.
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


/** Só para teste **/

function test()
{
   var sample = [{ 'title': 'Teste', 'fields': [{ 'values': ['v1'], 'fieldName': 'f1' }, { 'values': ['v2'], 'fieldName': 'f2' }] }];

   createSpreadsheet( sample );
}