package UniMartTeam.model.EnumForBeans;

public enum StatoCoupon
{
   Disponibile, Riscattato;

   public static StatoCoupon StringToEnum(String tipo)
   {
      if(tipo == null)
         return null;

      switch (tipo)
      {
         case "Disponibile": return Disponibile;
         case "Riscattato": return Riscattato;
         default: return null;
      }
   }
}