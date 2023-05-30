function checkValor30k() {

	var dsParametros = DatasetFactory.getDataset("marketing_parametros", null, [], null);
	var limiteResultado = 30000;

  if (dsParametros) {
		limiteResultado = Number(dsParametros.getValue(0, "limiteResultado"));
  }

	var valorLiberado = Number(hAPI.getCardValue("valorLiberado"));
	var valorTotalVerba = Number(hAPI.getCardValue("valorTotalVerba"));
	var diferencaResultado = valorLiberado - valorTotalVerba;

	log.info('+++ MKT Abertura Verba checkValor30k valorLiberado: ' + valorLiberado);
	log.info('+++ MKT Abertura Verba checkValor30k valorTotalVerba: ' + valorTotalVerba);
	log.info('+++ MKT Abertura Verba checkValor30k diferencaResultado: ' + diferencaResultado);

	if (diferencaResultado >= limiteResultado) {
		log.info('+++ MKT Abertura Verba checkValor30k retorna maior');
		return "maior";
	} else {
		if (diferencaResultado > 0 && diferencaResultado < limiteResultado) {
			log.info('+++ MKT Abertura Verba checkValor30k retorna ate');
			return "ate";
		}
	}

	log.info('+++ MKT Abertura Verba checkValor30k retorna ""');
	return "";
}
