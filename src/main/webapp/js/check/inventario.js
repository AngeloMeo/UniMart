function check()
{
    assertCF(document.getElementById('cfResponsabile'), 'Formato CF non valido');
    required(document.getElementById('indirizzo'));
    required(document.getElementById('regione'));
    required(document.getElementById('nome'));
    required(document.getElementById('note'));
}