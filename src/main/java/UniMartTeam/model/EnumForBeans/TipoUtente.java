package UniMartTeam.model.EnumForBeans;

public enum TipoUtente//
{
   Semplice, Amministratore;

   public static TipoUtente StringToEnum(String tipo)
   {
      if(tipo == null)
         return null;

      switch (tipo)
      {
         case "Semplice": return Semplice;
         case "Amministratore": return Amministratore;
         default: return null;
      }
   }
}
