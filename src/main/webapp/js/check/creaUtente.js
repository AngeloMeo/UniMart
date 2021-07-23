function check()
{
    assertCF(document.getElementById('CF'), 'Formato CF non valido');
    required(document.getElementById('nome'));
    required(document.getElementById('cognome'));
    required(document.getElementById('viaCivico'));
    required(document.getElementById('citta'));
    required(document.getElementById('regione'));
    assertPhone(document.getElementById('telefono'), 'Formato Telefono non corretto');
    assertEmail(document.getElementById('email'), 'Formato Email non corretto');
    assertUsername(document.getElementById('username'), 'Formato Username non corretto');
    assertPassword(document.getElementById('password'), 'Formato Password non corretto');
}