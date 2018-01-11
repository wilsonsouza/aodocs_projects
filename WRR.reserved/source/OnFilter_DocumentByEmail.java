import com.altirnao.aodocs.script.*;
import com.altirnao.aodocs.custom.*;
import java.util.*;
import java.lang.*;

try
{
   debug(String.format("Relations %d", relatedDocuments.size()));
   //
   if(relatedDocuments.size() > 4)
      throw new Exception("Quantidade de documentos relacionados excedida!");
   //
   return relatedDocuments;
}
catch(Exception e)
{
   debug(e.getMessage());
   throw e;
}