function check()
{
    required(document.getElementById('nome'));
    assertDouble(document.getElementById('prezzo'), 'Formato Prezzo non valido');
    assertDouble(document.getElementById('peso'), 'Formato Peso non valido');
    assertDouble(document.getElementById('volumeOccupato'), 'Formato Volume non valido');
    required(document.getElementById('descrizione'));
}