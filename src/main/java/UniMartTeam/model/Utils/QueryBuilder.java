package UniMartTeam.model.Utils;

import javax.management.monitor.CounterMonitorMBean;
import java.security.InvalidParameterException;
import java.util.StringJoiner;

public class QueryBuilder
{
   private final String table, alias;
   private final StringBuilder query;

   public QueryBuilder(String table, String alias) throws InvalidParameterException
   {
      if(table == null || alias == null)
         throw new InvalidParameterException("Table or Alias is null");

      this.table = table;
      this.alias = alias;
      query = new StringBuilder();
   }

   public String getQuery()
   {
      String queryTmp = query.toString();
      query.delete(0, query.length());

      return queryTmp;
   }

   public QueryBuilder delete()
   {
      query.append("DELETE FROM " + table);

      return this;
   }

   public QueryBuilder limit(boolean flagOffset)
   {
      query.append(" LIMIT " + "?");

      if(flagOffset)
         query.append("," + "?");

      return this;
   }

   public QueryBuilder where(String cond)
   {
      if(cond != null && cond.length() > 0)
         query.append(" WHERE " + cond);

      return this;
   }

   public QueryBuilder having(String cond)
   {
      if(cond != null && cond.length() > 0)
         query.append(" HAVING " + cond);

      return this;
   }

   public QueryBuilder groupBy(boolean asc, String...argv)
   {
      int argc = argv.length;

      if(argc != 0)
      {
         query.append(" GROUP BY ");
         StringJoiner commaSJ = new StringJoiner(",", "(", ")");

         for (int i = 0; i < argc; i++)
            commaSJ.add("?");

         query.append(commaSJ.toString());
      }

      return this;
   }

   public QueryBuilder update(String...argv)
   {
      query.append("UPDATE " + table);

      StringJoiner commaSJ = new StringJoiner(",");

      for(String param : argv)
         commaSJ.add(String.format("%s = %s", param, "?"));

      query.append(commaSJ.toString());

      return this;
   }

   public QueryBuilder insert(String...argv)
   {
      int argc = argv.length;

      if(argc > 0)
      {
         query.append("INSERT INTO  " + table + " ");

         StringJoiner paramJoiner = new StringJoiner(",", "(", ")");

         for (String param : argv)
            paramJoiner.add(param);

         query.append(paramJoiner.toString() + " VALUES ");

         StringJoiner qmJoiner = new StringJoiner(",", "(", ")");

         for (int i = 0; i < argc; i++)
            qmJoiner.add("?");

         query.append(qmJoiner.toString());
      }
      return this;
   }

   public QueryBuilder select(String...argv)
   {
      query.append("SELECT ");

      if(argv.length == 0)
         query.append("*");
      else
      {
         StringJoiner commaSJ = new StringJoiner(",");

         for(String param : argv)
            commaSJ.add(param);

         query.append(commaSJ.toString());
      }
      query.append(" FROM " + table + " AS " + alias);

      return this;
   }

   public QueryBuilder innerJoin(String table, String alias)
   {
      query.append(" INNER JOIN "+ table + " " + alias);

      return this;
   }

   /*
      0 FULL
      1 LEFT
      2 RIGHT
    */
   public QueryBuilder outerJoin(String table, String alias, int mode)
   {
      String joinMode = "";

      if(mode == 0)
         joinMode = " FULL JOIN ";
      else if(mode == 1)
         joinMode = " LEFT JOIN ";
      else if(mode == 2)
         joinMode = " RIGHT JOIN ";

      if(joinMode.length() > 0)
         query.append(joinMode + " " + table + " " + alias);

      return this;
   }

   public QueryBuilder on(String condition)
   {
      query.append(" ON " + condition);

      return this;
   }
}