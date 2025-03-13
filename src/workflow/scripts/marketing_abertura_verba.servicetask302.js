function servicetask302(attempt, message) {
  try {
    log.info("servicetask302 - ini");
    enviaEmail('iniAcao', 'S', 'S', 'S');
  } catch (error) {
    throw error;
  }
}