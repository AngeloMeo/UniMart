package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import UniMartTeam.model.Beans.Prodotto;
import UniMartTeam.model.Extractors.CategoriaExtractor;
import UniMartTeam.model.Extractors.ProdottoExtractor;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;

public class CategoriaDAO
{
   public static boolean doSave(Categoria c) throws SQLException
   {
      if(c == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("categoria", "cat");

         try (PreparedStatement ps = con.prepareStatement(qb.insert("nome", "aliquota").getQuery()))
         {
            ps.setString(1, c.getNome());
            ps.setFloat(2, c.getAliquota());
            if (ps.executeUpdate() == 0) {
               return false;
            }
            return true;
         }
      }
   }

   public static boolean doSaveOrUpdate(Categoria c) throws SQLException
   {
      if(c == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("categoria", "");

         try (PreparedStatement ps = con.prepareStatement(qb.select().where("nome = ?").getQuery()))
         {
            ps.setString(1, c.getNome());
            ResultSet rs = ps.executeQuery();
            //update
            if (rs.next())
            {
               try (PreparedStatement pss = con.prepareStatement(qb.update("nome", "aliquota").where("nome = ?").getQuery()))
               {
                  pss.setString(1, c.getNome());
                  pss.setFloat(2, c.getAliquota());
                  pss.setString(3, c.getNome());

                  if (pss.executeUpdate() == 0) {
                     return false;
                  }
                  return true;
               }
            } else
            { //doSave
               doSave(c);
            }
         }
      }
      return false;
   }

   public static List<Categoria> doRetrieveAll(int offset, int size) throws SQLException
   {
      if(offset<1 || size<1)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select().limit(true).getQuery()))
         {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {
               list.add(CategoriaExtractor.Extract(rs, alias));
            }
            return list;
         }
      }
   }

   public static List<Categoria> doRetrieveAll() throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias);
         try (PreparedStatement ps = con.prepareStatement(qb.select().getQuery()))
         {

            ResultSet rs = ps.executeQuery();
            ArrayList<Categoria> list = new ArrayList<>();

            while (rs.next())
            {
               list.add(CategoriaExtractor.Extract(rs, alias));
            }

            if(list.isEmpty())
               list.add(null);

            return list;
         }
      }
   }

   public static List<Categoria> doRetrieveByKey(String categoryName, boolean likeMode, boolean limit, int start, int end) throws SQLException
   {
      if(categoryName.isEmpty())
         return null;

      try (Connection con = ConPool.getConnection())
      {
         List<Categoria> results = new ArrayList<>();
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias).select();

         if (likeMode)
            qb.where("cat.nome LIKE(?)");
         else
            qb.where("cat.nome=?");

         if (limit)
            qb.limit(true);

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, categoryName);
            if (limit)
            {
               ps.setInt(2, start);
               ps.setInt(3, end);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
               results.add(CategoriaExtractor.Extract(rs, alias));
            }

            if (results.isEmpty())
               results.add(null);

            return results;
         }
      }
   }

   public static Categoria doRetrieveByKey(String categoryName) throws SQLException
   {
      if(categoryName.isEmpty())
         return null;

      try (Connection con = ConPool.getConnection())
      {
         String alias = "cat";
         QueryBuilder qb = new QueryBuilder("categoria", alias).select().where("cat.nome=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, categoryName);
            ResultSet rs = ps.executeQuery();

            if (rs.next())
            {
               return CategoriaExtractor.Extract(rs, alias);
            }
            return null;
         }
      }
   }

   public static List<Prodotto> doRetrieveProducts(Categoria c) throws SQLException
   {
      if(c == null)
         return null;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("prodotto", "p").select().where("p.nomeCategoria=?");
         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, c.getNome());
            ResultSet rs = ps.executeQuery();
            ArrayList<Prodotto> list = new ArrayList<>();

            while (rs.next())
               list.add(ProdottoExtractor.Extract(rs, "p", c));

            return list;
         }
      }
   }

   public static boolean doDelete(String name) throws SQLException
   {
      if(name.isEmpty())
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("categoria", "").delete().where("nome=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, name);

            if (ps.executeUpdate() == 0)
               return false;

            return true;
         }
      }
   }
}