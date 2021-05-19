package UniMartTeam.model.DAO;

import UniMartTeam.model.Beans.*;
import UniMartTeam.model.Extractors.*;
import UniMartTeam.model.Utils.ConPool;
import UniMartTeam.model.Utils.QueryBuilder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO
{
   public static final int CF = 1, NOME = 2, COGNOME = 3, CITTA = 4, REGIONE = 5, TIPOUTENTE = 6, USERNAME = 7, EMAIL = 8;

   public static boolean doSave(Utente u) throws SQLException
   {
      if(u == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "");
         qb.insert("CF", "nome", "cognome", "viaCivico", "fotoProfilo", "citta", "regione", "telefono", "email", "token", "username"
                 , "passwordHash", "tipo", "dataDiNascita");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, u.getCF());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getCognome());
            ps.setString(4, u.getViaCivico());
            ps.setString(5, u.getFotoProfilo());
            ps.setString(6, u.getCitta());
            ps.setString(7, u.getRegione());
            ps.setString(8, u.getTelefono());
            ps.setString(9, u.getEmail());
            ps.setString(10, u.getToken());
            ps.setString(11, u.getUsername());
            ps.setString(12, u.getPasswordHash());
            ps.setString(13, u.getTipo().toString());
            ps.setDate(14, Date.valueOf(u.getDataDiNascita()));

            return (ps.executeUpdate() == 0) ? false : true;
         }
      }
   }

   public static boolean doUpdate(Utente u) throws SQLException
   {
      if(u == null)
         return false;

      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "");
         qb.update("nome", "cognome", "viaCivico", "fotoProfilo", "citta", "regione", "telefono", "email", "token", "username"
                 , "passwordHash", "tipo", "dataDiNascita");
         qb.where("CF = " + u.getCF());

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ResultSet rs = ps.executeQuery();

            ps.setString(1, u.getNome());
            ps.setString(2, u.getCognome());
            ps.setString(3, u.getViaCivico());
            ps.setString(4, u.getFotoProfilo());
            ps.setString(5, u.getCitta());
            ps.setString(6, u.getRegione());
            ps.setString(7, u.getTelefono());
            ps.setString(8, u.getEmail());
            ps.setString(9, u.getToken());
            ps.setString(10, u.getUsername());
            ps.setString(11, u.getPasswordHash());
            ps.setString(12, u.getTipo().toString());
            ps.setDate(13, Date.valueOf(u.getDataDiNascita()));

            return (ps.executeUpdate() == 0) ? false : true;
         }
      }
   }

   public static boolean doDelete(String CF) throws SQLException
   {
      try (Connection con = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "").delete().where("utente=?");

         try (PreparedStatement ps = con.prepareStatement(qb.getQuery()))
         {
            ps.setString(1, CF);

            return (ps.executeUpdate() == 0) ? false : true;
         }
      }
   }

   public static List<Utente> doRetrieveAll(int offset, int size) throws SQLException
   {
      if(offset < 0 || size < 0)
         return null;

      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "");
         qb.select("CF", "nome", "cognome", "viaCivico", "fotoProfilo", "citta", "regione", "telefono", "email", "token", "username"
                 , "passwordHash", "tipo", "dataDiNascita").limit(true);

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, size);

            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Utente> doRetrieveAll() throws SQLException
   {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "");
         qb.select("CF", "nome", "cognome", "viaCivico", "fotoProfilo", "citta", "regione", "telefono", "email", "token", "username"
                 , "passwordHash", "tipo", "dataDiNascita");

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static List<Utente> doRetrieveByCond(int mode, String param) throws SQLException
   {
      try(Connection connection = ConPool.getConnection())
      {
         QueryBuilder qb = new QueryBuilder("utente", "");
         qb.select("CF", "nome", "cognome", "viaCivico", "fotoProfilo", "citta", "regione", "telefono", "email", "token", "username"
                 , "passwordHash", "tipo", "dataDiNascita");

         switch (mode)
         {
            case CF:
               qb.where("CF=" + param);
               break;

            case NOME:
               qb.where("nome=" + param);
               break;

            case COGNOME:
               qb.where("cognome=" + param);
               break;

            case CITTA:
               qb.where("citta=" + param);
               break;

            case REGIONE:
               qb.where("regione=" + param);
               break;

            case TIPOUTENTE:
               qb.where("tipo=" + param);
               break;

            case USERNAME:
               qb.where("username=" + param);
               break;

            case EMAIL:
               qb.where("email=" + param);
               break;


            default:
               return null;
         }

         try(PreparedStatement preparedStatement = connection.prepareStatement(qb.getQuery()))
         {
            return ListFiller(preparedStatement);
         }
      }
   }

   public static Utente doRetriveInventarioList(Utente u) throws SQLException
   {
      if(u == null)
         return null;

      try(Connection con = ConPool.getConnection())
      {
         String alias = "i"; //Alias inventario
         QueryBuilder query = new QueryBuilder("utente", "u").select().outerJoin("inventario", alias, 1).on("u.CF =" + alias + ".cfResponsabile");
         query.where("u.CF=" + u.getCF());

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
               Inventario i = InventarioExtractor.Extract(rs, alias, u);

               u.setInventarioList(new ArrayList<Inventario>());
               u.addInventarioList(i);
            }
            return u;
         }
      }
   }

   public static Utente doRetriveOrdineList(Utente u) throws SQLException
   {
      if(u == null)
         return null;

      try(Connection con = ConPool.getConnection())
      {
         String alias = "o"; //Alias ordine
         QueryBuilder query = new QueryBuilder("utente", "u").select().outerJoin("ordine", "o", 1).on("u.CF =" + alias + ".cfCliente");
         query.where("u.CF=" + u.getCF());

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ResultSet rs = ps.executeQuery();

            u.setOrdineList(new ArrayList<Ordine>());

            while(rs.next())
            {
               Ordine ordine = OrdineExtractor.Extract(rs, alias, u, null, null);
               ordine.setCoupon(CouponDAO.doRetrieveByCond(ordine));

               u.addOrdineList(ordine);
            }
            return u;
         }
      }
   }

   public static Utente doRetriveCouponList(Utente u) throws SQLException
   {
      if(u == null)
         return null;

      try(Connection con = ConPool.getConnection())
      {
         String alias = "c"; //Alias coupon
         QueryBuilder query = new QueryBuilder("utente", "u").select().outerJoin("ordine", "o", 1).on("u.CF =" + alias + ".cfCreatore").where("u.CF=" + u.getCF());

         try(PreparedStatement ps = con.prepareStatement(query.getQuery()))
         {
            ResultSet rs = ps.executeQuery();

            u.setCouponList(new ArrayList<Coupon>());

            while(rs.next())
            {
               Coupon coupon = CouponExtractor.Extract(rs, alias, u, null);
               coupon.setOrdine(OrdineDAO.doRetrieveByCond(coupon));

               u.addCouponList(coupon);
            }
            return u;
         }
      }
   }

   private static List<Utente> ListFiller(PreparedStatement preparedStatement) throws SQLException
   {
      if(preparedStatement == null)
         return null;

      ResultSet rs = preparedStatement.executeQuery();
      ArrayList<Utente> list = new ArrayList<>();

      while(rs.next())
      {
         Utente utente = UtenteExtractor.Extract(rs, "");

         list.add(utente);
      }

      if(list.isEmpty())
         list.add(new Utente());

      return list;
   }
}