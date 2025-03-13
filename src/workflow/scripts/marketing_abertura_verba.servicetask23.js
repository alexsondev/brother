// Notificar Grupo Brother (Início da Ação)
function servicetask23(attempt, message) {
  try {
    log.info("servicetask23 - ini");
    enviaEmail('iniAcao', 'N', 'S', 'N');
  } catch (error) {
    throw error;
  }

}