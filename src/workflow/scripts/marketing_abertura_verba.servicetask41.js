// Notificar Grupo Brother (Fim da Ação)
function servicetask41(attempt, message) {
  try {
    log.info("servicetask41 - fimAcao");
    enviaEmail('fimAcao', 'N', 'S', 'N');
  } catch (error) {
    throw error;
  }
}