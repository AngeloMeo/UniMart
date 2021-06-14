package UniMartTeam.model.EnumForBeans;

public enum StatoOrdine
{
   Salvato, Accettato, Preparazione, Spedito, InConsegna, Consegnato, Annullato;

   public static StatoOrdine StringToEnum(String stato)
   {
      if(stato == null)
         return null;

      switch (stato)
      {
         case "salvato": return Salvato;
         case "accettato": return Accettato;
         case "preparazione": return Preparazione;
         case "spedito": return Spedito;
         case "in consegna": return InConsegna;
         case "consegnato": return Consegnato;
         case "annullato": return Annullato;
         default: return null;
      }
   }
}