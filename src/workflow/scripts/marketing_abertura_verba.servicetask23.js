// Notificar Grupo Brother (Início da Ação)
function servicetask23(attempt, message) {
  try {
    log.info("servicetask23 - ini");
    enviaEmail('iniAcao', 'S', 'S', 'S');
  } catch(error) {
    throw error;
  }
  
}