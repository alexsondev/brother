function params() {
  var Params = {};

  log.info("params ini");

  Params.atividades = {
    inicio: [1],
    validarMarketing: [2],
    revisarSolicitacao: [8],
    aprovarGerMarketing: [4],
    aprovarPresidencia: [6],
    analisarErros: [27, 36, 53, 54, 143, 125],
    aguardandoFimDaAcao: [129],
    autorizarNotificacaoInicio: [309],
    autorizarNotificacaoFim: [305],
    enviarEvidencias: [261],
    evidenciasControle: [280],
    validarEvidencias: [62],
    aprovarVerbaMaior: [151],
    aprovarVerbaMenor: [75],
    enviarND: [264],
    validarND: [103],
    conferirFinanceiro: [113],
    aprovarPagamento: [116],
    atualizarStatus: [132],
    gerarAbatimentos: [121],
    autorizarNotificacaoPagamento: [139],
    gerenciarVales: [202],
    gtwVales: [204],
    finalizado: [148, 206],
    finalizadoSemAntecipacao: [173]
  };

  return Params;
}