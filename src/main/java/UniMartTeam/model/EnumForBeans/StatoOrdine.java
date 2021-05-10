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
            case "Salvato": return Salvato;
            case "Accettato": return Accettato;
            case "Preparazione": return Preparazione;
            case "Spedito": return Spedito;
            case "InConsegna": return InConsegna;
            case "Consegnato": return Consegnato;
            case "Annullato": return Annullato;
            default: return null;
        }
    }
}