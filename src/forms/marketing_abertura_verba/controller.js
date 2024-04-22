angular
  .module("MarketingAberturaVerbaApp", [
    "angular.fluig",
    "ngAnimate",
    "brother.services",
    "brother.directives",
    "ngFileUpload",
  ])

  .controller("MarketingAberturaVerbaController", [
    "$scope",
    "$window",
    "$http",
    "$compile",
    "$timeout",
    "$log",
    "formService",
    "brotherService",
    "fluigService",
    "erpService",
    "globalService",
    "Upload",
    function MarketingAberturaVerbaController(
      $scope,
      $window,
      $http,
      $compile,
      $timeout,
      $log,
      formService,
      brotherService,
      fluigService,
      erpService,
      globalService,
      Upload
    ) {
      const vm = this;

      if (window.location.hostname == "localhost") {
        angular.forEach(angular.element("[tablename]"), (value) => {
          const table = angular.element(value);
          angular.forEach(table.find("tbody"), (tbody) => {
            angular.element(tbody).attr("ng-non-bindable", null);
            $compile(table)($scope);
          });
        });
      }

      formService.atualizaFormulario($scope, vm).then(() => {
        vm.loading = FLUIGC.loading(".collapse");

        vm.checkLocal();

        if (!vm.Params.mobile && parent && parent.WCMAPI) {
          vm.WCMAPI = parent.WCMAPI;
        }

        vm.inicia();
      });

      vm.checkLocal = function checkLocal() {
        if (window.location.hostname == "localhost") {
          vm.Params = {
            edit: true,
            etapa: "inicio", //'consulta', //'inicio', //"gerenciarVales",
            user: "admin",
            formMode: "ADD",
            companyId: 1,
            managerMode: false,
          };

          if (vm.Params.formMode != "ADD") {
            $http.get(`../../../tmp/solicitacao.json`).then((res) => {
              console.log(res);
              vm.Formulario = res.data;
            });
          }
        }
      };
      vm.inicia = function inicia() {
        vm.desktop = !vm.Params.mobile;
        vm.dataAtual = new Date().getTime();

        vm.createExtMav();
        vm.checkRegras();

        if (vm.Params.formMode == "ADD") {
          vm.Formulario.userAprovGerMarketing = {};
          vm.Formulario.userAprovPresidenciaVp = {};
          vm.Formulario.userValMarketing = {};
          vm.Formulario.executivo = {};
          vm.Formulario.cliente = {};
          vm.Formulario.necAprovacaoGerMkt = true;

          erpService.getBusinessSegment().then((categorias) => {
            categorias.forEach((c) => {
              vm.Formulario.rateioCategoria.push({
                categoria: c,
                perc: 0,
              });
            });
          });
        }

        if (
          vm.Params.edit &&
          !vm.Formulario.guid &&
          vm.Params.formMode != "ADD"
        ) {
          vm.Formulario.guid = vm.guid();
        }

        if (vm.Params.edit && vm.Params.etapa == "validarEvidencias") {
          vm.Formulario.necEnvioNd = vm.Formulario.userValidacaoEvid
            ? vm.Formulario.necEnvioNd
            : vm.Formulario.tipoAcao.tipoAcaoCodigo == "spiff"
              ? false
              : true;
        }

        fluigService.getUsuarios(vm.Params.user).then((resp) => {
          vm.Usuario = resp[0];
          vm.checkEtapa();
          vm.calculaTotais();
        });

        fluigService
          .getPasta(vm.Params.companyId || 1, "Cadastros%7CMarketing%7CAnexos")
          .then((pasta) => {
            vm.Formulario.folderAttach = pasta[0].documentId;
          });

        brotherService.getMarketingParametros().then((resp) => {
          vm.MarketingParametros = resp[0];
        });

        // vm.Formulario.numControle = '2020.0056'

        if (vm.Params.edit) {
          vm.Formulario.importado = false;
        }

        vm.getItens();

        // switch (vm.etapaNotificacao) {
        //   case 1: tipo = 'iniAcao'; break;
        //   case 2: tipo = 'fimAcao'; break;
        //   case 3: tipo = 'evidencia'; break;
        //   case 4: tipo = 'envioND'; break;
        //   case 5: tipo = 'pagamento'; break;
        //   default: tipo = 'iniAcao'; break;
        // }

        // if (vm.Formulario.status == 'CANCELADA') {
        //   tipo = 'cancelamento';
        // }
        // vm.NotificationTypes = [
        //   { id: 'iniAcao', label: 'Início da Ação' },
        //   { id: 'fimAcao', label: 'Término da Ação' },
        //   { id: 'evidencia', label: 'Envio de Evidências' },
        //   { id: 'envioND', label: 'Envio da ND' },
        //   { id: 'pagamento', label: 'Pagamento' },
        //   { id: 'cancelamento', label: 'Cancelamento' },
        //   { id: 'vales', label: 'Acompanhamento de Vales' }
        // ];
        vm.NotificationTypes = [];
      };

      vm.checkRegras = function checkRegras() {
        vm.etapas = [
          "consulta",
          "inicio",
          "validarMarketing",
          "revisarSolicitacao",
          "aprovarGerMarketing",
          "aprovarPresidencia",
          "analisarErros",
          "autorizarNotificacaoInicio",
          "aguardandoFimDaAcao",
          "autorizarNotificacaoFim",
          "evidenciasControle",
          "enviarEvidencias",
          "validarEvidencias",
          "aprovarVerbaMaior",
          "aprovarVerbaMenor",
          "enviarND",
          "validarND",
          "gerenciarVales",
          "conferirFinanceiro",
          "aprovarPagamento",
          "atualizarStatus",
          "autorizarNotificacaoPagamento",
          "fim",
        ];

        vm.regras = {};
        [
          { regra: "showResumo", def: true, etapas: vm.etapas },
          {
            regra: "showSolicitacao",
            def: true,
            etapas: [
              "inicio",
              "consulta",
              "revisarSolicitacao",
              "analisarErros",
            ],
          },
          {
            regra: "enableSolicitacao",
            def: vm.Params.edit,
            etapas: ["inicio", "revisarSolicitacao"],
          },

          {
            regra: "showExecutivos",
            def: true,
            etapas: [
              "inicio",
              "consulta",
              "revisarSolicitacao",
              "analisarErros",
              "validarMarketing",
            ],
          },
          {
            regra: "enableExecutivos",
            def: vm.Params.edit,
            etapas: ["inicio", "revisarSolicitacao", "validarMarketing"],
          },

          {
            regra: "showCopiarAcao",
            def: true,
            etapas: ["inicio"],
          },
          {
            regra: "enableCopiarAcao",
            def: vm.Params.edit,
            etapas: ["inicio"],
          },

          { regra: "showObsInternas", def: true, etapas: vm.etapas },
          {
            regra: "enableObsInternas",
            def: vm.Params.edit,
            etapas: vm.etapas,
          },

          {
            regra: "showEncerramentoAntecipado",
            def: true,
            etapas: ["aguardandoFimDaAcao"],
          },
          {
            regra: "enableEncerramentoAntecipado",
            def: vm.Params.edit,
            etapas: ["aguardandoFimDaAcao"],
          },

          { regra: 'showValidacaoMarketing', def: true, etapas: ['consulta', 'revisarSolicitacao', 'aprovarGerMarketing', 'analisarErros', 'validarMarketing', 'aprovarPresidencia'] },
          { regra: 'enableValidacaoMarketing', def: vm.Params.edit, etapas: ['validarMarketing'] },
          { regra: "showReenvia", def: true, etapas: ["autorizarNotificacaoInicio", "autorizarNotificacaoFim", "aprovarPagamento", "validarEvidencias", "validarND", "conferirFinanceiro", "autorizarNotificacaoPagamento", "fim"] },
          { regra: "showFim", def: 0, etapas: ["fim"] },
          { regra: 'showRateioCategoria', def: true, etapas: vm.etapas },
          { regra: 'showResumoVerbasCliente', def: true, etapas: vm.etapas },
          { regra: "showAnexo", def: true, etapas: vm.etapas },
          { regra: "enableAnexo", def: true, etapas: vm.etapas },
          { regra: 'showAprovGerMarketing', def: true, etapas: ['consulta', 'revisarSolicitacao', 'analisarErros', 'aprovarGerMarketing', 'validarMarketing', 'aprovarPresidencia'] },
          { regra: 'enableAprovGerMarketing', def: true, etapas: ['aprovarGerMarketing'] },
          { regra: 'showAprovPresidenciaVp', def: true, etapas: ['consulta', 'revisarSolicitacao', 'analisarErros', 'aprovarPresidencia', 'aprovarGerMarketing', 'validarMarketing'] },
          { regra: 'enableAprovPresidenciaVp', def: true, etapas: ['aprovarPresidencia'] },

          {
            regra: "showAprovVerbaMaior",
            def: true,
            etapas: [
              "consulta",
              "aprovarVerbaMaior",
              "aprovarGerMarketing",
              "validarEvidencias",
              "analisarErros",
            ],
          },
          {
            regra: "enableAprovVerbaMaior",
            def: true,
            etapas: ["aprovarVerbaMaior"],
          },

          {
            regra: "showSuspenderAcao",
            def: vm.Params.managerMode,
            etapas: vm.etapas.filter((e) => e !== "inicio"),
          },
          {
            regra: "enableSuspenderAcao",
            def: vm.Params.managerMode,
            etapas: vm.etapas.filter((e) => e !== "consulta"),
          },

          {
            regra: "showAprovVerbaMenor",
            def: true,
            etapas: [
              "consulta",
              "aprovarVerbaMenor",
              "aprovarGerMarketing",
              "validarEvidencias",
              "analisarErros",
            ],
          },
          {
            regra: "enableAprovVerbaMenor",
            def: true,
            etapas: ["aprovarVerbaMenor"],
          },

          { regra: 'showNotificacaoCliente', def: true, etapas: vm.etapas },
          { regra: 'enableNotificacaoCliente', def: vm.Params.edit, etapas: ['analisarErros', 'autorizarNotificacaoInicio', 'autorizarNotificacaoFim', 'enviarEvidencias', 'evidenciasControle', 'validarEvidencias', 'autorizarNotificacaoPagamento'] },
          { //mudança ACL
            regra: 'enableNotificacaoCliente3',
            def: true,
            etapas: ['consulta', 'inicio', 'revisarSolicitacao', 'aprovarGerMarketing', 'aprovarPresidencia',
              'analisarErros', 'autorizarNotificacaoInicio', 'autorizarNotificacaoFim', 'aguardandoFimDaAcao', 'enviarEvidencias', 'evidenciasControle',
              'validarEvidencias', 'aprovarVerbaMaior', 'aprovarVerbaMenor', 'enviarND', 'validarND', 'gerenciarVales',
              'conferirFinanceiro', 'aprovarPagamento', 'atualizarStatus', 'autorizarNotificacaoPagamento'
            ]
          },
          { regra: 'showEvidencias', def: true, etapas: ['consulta', 'enviarEvidencias', 'evidenciasControle', 'validarND', 'aprovarVerbaMaior', 'aprovarVerbaMenor', 'validarEvidencias', 'aprovarPagamento', 'analisarErros', 'conferirFinanceiro', 'gerenciarVales'] },
          { regra: 'enableEvidencias', def: true, etapas: ['enviarEvidencias', 'evidenciasControle', 'analisarErros'] },
          { regra: 'enableValidacaoEvidencias', def: true, etapas: ['validarEvidencias', 'analisarErros'] },

          {
            regra: "showND",
            def: true,
            etapas: [
              "consulta",
              "enviarND",
              "validarND",
              "analisarErros",
              "conferirFinanceiro",
              "validarEvidencias",
              "aprovarPagamento",
            ],
          },
          {
            regra: "enableND",
            def: true,
            etapas: ["enviarND", "analisarErros"],
          },
          {
            regra: "enableValidacaoND",
            def: true,
            etapas: ["validarND", "analisarErros"],
          },

          {
            regra: "showSelecionarDuplicatas",
            def: true,
            etapas: [
              "consulta",
              "conferirFinanceiro",
              "aprovarPagamento",
              "validarEvidencias",
              "analisarErros",
              "autorizarNotificacaoPagamento",
            ],
          },
          {
            regra: "enableSelecionarDuplicatas",
            def: true,
            etapas: ["conferirFinanceiro", "analisarErros"],
          },

          {
            regra: "showGerenciarVales",
            def: true,
            etapas: ["consulta", "gerenciarVales", "analisarErros"],
          },
          {
            regra: "enableGerenciarVales",
            def: true,
            etapas: ["gerenciarVales", "analisarErros"],
          },

          {
            regra: "showAprovPagamento",
            def: true,
            etapas: [
              "consulta",
              "aprovarPagamento",
              "conferirFinanceiro",
              "validarEvidencias",
              "analisarErros",
            ],
          },
          {
            regra: "enableAprovPagamento",
            def: true,
            etapas: ["aprovarPagamento"],
          },

          { regra: "showStatusErp", def: true, etapas: [] },
          { regra: "enableStatusErp", def: true, etapas: [] },
        ].forEach((o) => {
          vm.regras[o.regra] =
            vm.Params.user == "adminx" && vm.Params.edit
              ? true
              : o.etapas.indexOf(vm.Params.etapa) >= 0
                ? o.def
                : false;
        });
      };

      vm.calculaPercCategoria = () => {
        vm.Formulario.rateioCategoria.forEach((cat) => (cat.valor = 0));
        console.log("🚀 ~ vm.Formulario.rateioCategoria:", vm.Formulario.rateioCategoria)

        switch (vm.Formulario.tipoAcao.tipoAcaoCodigo) {
          case "sellout":
            vm.Formulario.itensSellout.forEach((it, index) => {
              if (it.item) {
                let cat = vm.Formulario.rateioCategoria.filter(
                  (c) => c.categoria.descricao == it.item.ccusto
                )[0];
                if (cat) {
                  cat.valor += it.rebateTotal;
                }
              }
            });
            break;

          //Alteração ACL ok
          case "price":
            vm.Formulario.itensPrice.forEach(function (it, o) {
              if (it.item) {
                let cat = vm.Formulario.rateioCategoria.filter(function (o) {
                  return o.categoria.descricao == it.item.ccusto;
                })[0];
                if (cat) {
                  cat.valor += it.rebateTotal;
                }
              }
            });
            break;
          //fim Alteração
          case 'sellin':
            if (vm.Formulario.tipoSellin == 'item' || vm.Formulario.tipoSellin == 'net') {
              vm.Formulario.itensSellinIt.forEach((it, index) => {
                if (it.item) {
                  console.log(it.item.ccusto);
                  let cat = vm.Formulario.rateioCategoria.filter(
                    (c) => c.categoria.descricao == it.item.ccusto
                  )[0];
                  console.log(cat);
                  if (cat) {
                    cat.valor += it.rebateTotal;
                  }
                }
              });
            }
            break;

          case "spiff":
            if (vm.Formulario.tipoSpiff == "item") {
              vm.Formulario.itensSpiffIt.forEach((it, index) => {
                if (it.item) {
                  console.log(it.item.ccusto);
                  let cat = vm.Formulario.rateioCategoria.filter(
                    (c) => c.categoria.descricao == it.item.ccusto
                  )[0];
                  console.log(cat);
                  if (cat) {
                    cat.valor += it.vlTotal;
                  }
                }
              });
            }
            break;
        }

        vm.Formulario.rateioCategoria.forEach((cat) => {
          cat.perc = cat.valor / vm.Formulario.valorTotalVerba;
        });

        vm.calculaTotalRateio();
      };

      vm.checkEtapa = function checkEtapa() {
        vm.etapaNotificacao = 0;
        switch (true) {
          case vm.Params.etapa == "inicio":
            vm.Formulario.solicitanteCodigo = vm.Usuario.colleagueId;
            // vm.Formulario.dataAbertura = vm.dataAtual;
            vm.Formulario.status = 'INÍCIO';
            break;
          case vm.Params.etapa == "validarMarketing":
            vm.Formulario.userValMarketing = vm.Usuario;
            vm.Formulario.dataValidacaoMarketing = vm.dataAtual;
            vm.Formulario.statusValidacaoMarketing = "PENDENTE";
            vm.Formulario.obsValidacaoMarketing = "";

            if (!vm.Formulario.emailsCliente.length) {
              vm.changeCliente()
              // vm.calculaPercCategoria()
              // vm.buscaContatosCliente();
            }
            // if (!vm.Formulario.executivos.length) {
            //   vm.buscaExecutivosCliente()
            // }
            break;

          case vm.Params.etapa == "aprovarGerMarketing":
            vm.Formulario.userAprovGerMarketing = vm.Usuario;
            vm.Formulario.dataAprovGerMarketing = vm.dataAtual;
            vm.Formulario.statusAprovGerMarketing = "PENDENTE";
            vm.Formulario.obsAprovGerMarketing = "";
            vm.buscaResumoVerbas();
            break;

          case vm.Params.etapa == "aprovarPresidencia":
            vm.Formulario.userAprovPresidenciaVp = vm.Usuario;
            vm.Formulario.dataAprovPresidenciaVp = vm.dataAtual;
            vm.Formulario.statusAprovPresidenciaVp = "PENDENTE";
            vm.Formulario.obsAprovPresidenciaVp = "";
            vm.buscaResumoVerbas();
            break;

          case vm.Params.etapa == "autorizarNotificacaoInicio":
            vm.Formulario.userAutorizNotifIni = vm.Usuario;
            vm.Formulario.dataAutorizNotifIni = vm.dataAtual;
            vm.Formulario.notificacaoEtapa = "INÍCIO DA AÇÃO";
            vm.etapaNotificacao = 1;
            break;

          case vm.Params.etapa == "aguardandoFimDaAcao":
            vm.Formulario.userEncerramentoAntecip = vm.Usuario;
            vm.Formulario.dataEncerramentoAntecip = vm.dataAtual;
            vm.etapaNotificacao = 1;
            break;

          case vm.Params.etapa == "autorizarNotificacaoFim":
            vm.Formulario.userAutorizNotifFim = vm.Usuario;
            vm.Formulario.dataAutorizNotifFim = vm.dataAtual;
            vm.Formulario.notificacaoEtapa = "FIM DA AÇÃO";
            vm.etapaNotificacao = 2;
            break;

          case vm.Params.etapa == "aprovarVerbaMaior":
            vm.Formulario.userAprovVerbaMaior = vm.Usuario;
            vm.Formulario.dataAprovVerbaMaior = vm.dataAtual;
            vm.Formulario.statusAprovVerbaMaior = "PENDENTE";
            vm.Formulario.obsAprovVerbaMaior = "";
            vm.buscaResumoVerbas();
            break;

          case vm.Params.etapa == "aprovarVerbaMenor":
            vm.Formulario.userAprovVerbaMenor = vm.Usuario;
            vm.Formulario.dataAprovVerbaMenor = vm.dataAtual;
            vm.Formulario.statusAprovVerbaMenor = "PENDENTE";
            vm.Formulario.obsAprovVerbaMenor = "";
            vm.buscaResumoVerbas();
            break;

          case vm.Params.etapa == "autorizarNotificacaoPagamento":
            vm.Formulario.userAutorizNotifPagto = vm.Usuario;
            vm.Formulario.dataAutorizNotifPagto = vm.dataAtual;
            vm.Formulario.notificacaoEtapa = "PAGAMENTO";
            vm.etapaNotificacao = 5;
            break;

          case vm.Params.etapa == 'validarEvidencias' || vm.Params.etapa == 'enviarEvidencias' || vm.Params.etapa == 'evidenciasControle':
            // vm.Formulario.userValidacaoEvid = vm.Usuario;
            // vm.Formulario.dataValidacaoEvid = vm.dataAtual;
            // vm.Formulario.statusValidacaoEvid = 'PENDENTE';
            // // vm.Formulario.valorLiberado = vm.Formulario.valorLiberado ? vm.Formulario.valorLiberado : vm.Formulario.valorResultado;
            // vm.Formulario.obsValidacaoEvid = '';
            // vm.checkEtapaNotificacao();
            // vm.checkUrlArquivos()

            console.log('Tiago d.Formulario:', vm.Formulario);
            vm.Formulario.userValidacaoEvid = vm.Usuario, vm.Formulario.dataValidacaoEvid = vm.dataAtual, vm.Formulario.statusValidacaoEvid = "PENDENTE", vm.Formulario.obsValidacaoEvid = "", /*d.Formulario.necEnvioNd = d.Formulario.tipoAcaoCodigo != 'spiff' ? true : false,*/ vm.checkEtapaNotificacao(), vm.checkUrlArquivos();

            console.log('Tiago aqui 1 d.Formulario.necEnvioNd:', vm.Formulario.necEnvioNd);

            if (vm.Formulario.tipoAcaoCodigo != 'spiff' && vm.Formulario.necEnvioNd != true) {
              console.log('entrei no == spiff');
              //                	d.Formulario.necEnvioNd = true;
              let element = document.getElementsByName('necEnvioNd_i')[0];
              element.click();
              console.log('flavio aqui d.Formulario.necEnvioNd:', vm.Formulario.necEnvioNd);
            }

            console.log('Tiago aqui 2 d.Formulario.necEnvioNd:', vm.Formulario.necEnvioNd);
            break;

          case vm.Params.etapa == "validarND" || vm.Params.etapa == "enviarND":
            vm.Formulario.userValidacaoND = vm.Usuario;
            vm.Formulario.dataValidacaoND = vm.dataAtual;
            vm.Formulario.statusValidacaoND = 'PENDENTE';
            vm.Formulario.obsValidacaoND = '';
            vm.etapaNotificacao = 4;
            vm.checkEtapaNotificacao();
            vm.checkUrlArquivos();
            break;

          case vm.Params.etapa == "aprovarPagamento":
            vm.Formulario.userAprovPagamento = vm.Usuario;
            vm.Formulario.dataAprovPagamento = vm.dataAtual;
            vm.Formulario.statusAprovPagamento = "PENDENTE";
            vm.Formulario.obsAprovPagamento = "";
            vm.etapaNotificacao = 5;
            vm.buscaResumoVerbas();
            break;

          case vm.Params.etapa == "gerenciarVales":
            vm.etapaNotificacao = 7;
            break;
          case vm.Params.etapa == "conferirFinanceiro":
            vm.Formulario.userFinanceiro = vm.Usuario;
            vm.Formulario.dataFinanceiro = vm.dataAtual;
            vm.Formulario.statusFinanceiro = "PENDENTE";
            vm.Formulario.obsConferenciaFinanceiro = "";
            vm.etapaNotificacao = 5;
            vm.buscaDuplicatas();
            vm.calculaTotalDuplicatas();
            vm.checkUrlArquivos();
            break;

          case vm.Params.etapa == "consulta":
            switch (vm.Params.numState) {
              case "31": //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case "32": //inicio da ação
                //vm.Formulario.userEncerramentoAntecip = vm.Usuario, vm.Formulario.dataEncerramentoAntecip = vm.dataAtual, vm.etapaNotificacao = 1;
                vm.etapaNotificacao = 1;
                break;
              case "36": //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case "129": //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case "41": //inicio da ação
                vm.etapaNotificacao = 2;
                break;
              case "53": //início da ação
                vm.etapaNotificacao = 1;
                break;
              case "43": //fim da ação
                console.log(
                  "ANTES ACL TGO vm.Formulario.userAutorizNotifFim:: ",
                  vm.Formulario.userAutorizNotifFim
                );
                console.log(
                  "ANTES ACL TGO vm.Formulario.dataAutorizNotifFim:: ",
                  vm.Formulario.dataAutorizNotifFim
                );
                console.log(
                  "ANTES ACL TGO vm.Formulario.notificacaoEtapa:: ",
                  vm.Formulario.notificacaoEtapa
                );
                console.log(
                  "ANTES ACL TGO vm.etapaNotificacao:: ",
                  vm.etapaNotificacao
                );
                //vm.Formulario.userAutorizNotifFim = vm.Usuario, vm.Formulario.dataAutorizNotifFim = vm.dataAtual, vm.Formulario.notificacaoEtapa = "FIM DA AÇÃO", vm.etapaNotificacao = 2;
                console.log(
                  "DEPOIS ACL TGO vm.Formulario.userAutorizNotifFim:: ",
                  vm.Formulario.userAutorizNotifFim
                );
                console.log(
                  "DEPOIS ACL TGO vm.Formulario.dataAutorizNotifFim:: ",
                  vm.Formulario.dataAutorizNotifFim
                );
                console.log(
                  "DEPOIS ACL TGO vm.Formulario.notificacaoEtapa:: ",
                  vm.Formulario.notificacaoEtapa
                );
                console.log(
                  "DEPOIS ACL TGO vm.etapaNotificacao:: ",
                  vm.etapaNotificacao
                );
                vm.etapaNotificacao = 2;
                break;
              case "49": //fim da ação
                vm.etapaNotificacao = 2;
                break;
              case "54": //fim da ação
                vm.etapaNotificacao = 2;
                break;
              case "62": //Enviar Evidências via Portal
                vm.etapaNotificacao = 3;
                break;
              case "180": //Enviar Evidências via Portal
                vm.etapaNotificacao = 3;
                break;
              case "103": //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              //                    case '186': //Envio da ND
              //                        vm.etapaNotificacao = 4;
              //                        break;
              case "240": //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case "75": //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case "151": //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case "113": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "116": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "121": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "132": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "125": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "215": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "220": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "140": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "143": //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case "148": //Fim
                vm.etapaNotificacao = 5;
                break;
              case "202": // vales
                vm.etapaNotificacao = 7;
                break;
            }
            break;
          case "consulta" == vm.Params.etapa:
            switch (vm.Params.numState) {
              case '31': //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case '32': //inicio da ação
                //d.Formulario.userEncerramentoAntecip = d.Usuario, d.Formulario.dataEncerramentoAntecip = d.dataAtual, d.etapaNotificacao = 1;
                vm.etapaNotificacao = 1;
                break;
              case '36': //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case '129': //inicio da ação
                vm.etapaNotificacao = 1;
                break;
              case '41': //inicio da ação
                vm.etapaNotificacao = 2;
                break;
              case '53': //início da ação
                vm.etapaNotificacao = 1;
                break;
              case '43': //fim da ação
                console.log("ANTES ACL TGO d.Formulario.userAutorizNotifFim:: ", vm.Formulario.userAutorizNotifFim);
                console.log("ANTES ACL TGO d.Formulario.dataAutorizNotifFim:: ", vm.Formulario.dataAutorizNotifFim);
                console.log("ANTES ACL TGO d.Formulario.notificacaoEtapa:: ", vm.Formulario.notificacaoEtapa);
                console.log("ANTES ACL TGO d.etapaNotificacao:: ", vm.etapaNotificacao);
                //d.Formulario.userAutorizNotifFim = d.Usuario, d.Formulario.dataAutorizNotifFim = d.dataAtual, d.Formulario.notificacaoEtapa = "FIM DA AÇÃO", d.etapaNotificacao = 2;
                console.log("DEPOIS ACL TGO d.Formulario.userAutorizNotifFim:: ", vm.Formulario.userAutorizNotifFim);
                console.log("DEPOIS ACL TGO d.Formulario.dataAutorizNotifFim:: ", vm.Formulario.dataAutorizNotifFim);
                console.log("DEPOIS ACL TGO d.Formulario.notificacaoEtapa:: ", vm.Formulario.notificacaoEtapa);
                console.log("DEPOIS ACL TGO d.etapaNotificacao:: ", vm.etapaNotificacao);
                vm.etapaNotificacao = 2;
                break;
              case '49': //fim da ação
                vm.etapaNotificacao = 2;
                break;
              case '54': //fim da ação
                vm.etapaNotificacao = 2;
                break;
              case '62': //Enviar Evidências via Portal
                vm.etapaNotificacao = 3;
                break;
              case '180': //Enviar Evidências via Portal
                vm.etapaNotificacao = 3;
                break;
              case '103': //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              //                    case '186': //Envio da ND
              //                        d.etapaNotificacao = 4;
              //                        break;
              case '240': //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case '75': //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case '151': //Envio da ND
                vm.etapaNotificacao = 4;
                break;
              case '113': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '116': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '121': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '132': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '125': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '215': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '220': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '140': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '143': //Pagamento
                vm.etapaNotificacao = 5;
                break;
              case '148': //Fim
                vm.etapaNotificacao = 5;
                break;
              case '202': // vales
                vm.etapaNotificacao = 7;
                break;
            }
            break;
        }

        if (vm.regras.enableSolicitacao) {
          brotherService.getMarketingTipoAcao().then((resp) => {
            vm.TiposAcao = resp;
            vm.TiposAcao.forEach((tipoAcao) => {
              tipoAcao.tipoAcao = JSON.parse(tipoAcao.tipoAcao);
              tipoAcao.contaContabil = JSON.parse(tipoAcao.contaContabil);
            });
          });
        }

        if (vm.regras.enableCopiarAcao) {
          vm.Solicitacoes = [];
          brotherService.getMarketingAberturaVerba().then((solicitacoes) => {
            solicitacoes.forEach((s) => {
              s.displaykey = `${s.suspenderAcao == "true" ? "SUSPENSA - " : ""
                } ${s.solicitacao} - ${s.tipoAcaoDescricao} - ${s.nomeAcao} - ${s.clienteNome
                }`;
              vm.Solicitacoes.push(s);
            });
          });
        }

        if (vm.etapaNotificacao === 1) {
          vm.NotificationTypes.push({ id: "iniAcao", label: "Início da Ação" });
        }
        if (vm.etapaNotificacao === 2) {
          vm.NotificationTypes.push({
            id: "fimAcao",
            label: "Término da Ação",
          });
        }
        if (vm.etapaNotificacao === 3) {
          vm.NotificationTypes.push({
            id: "evidencia",
            label: "Envio de Evidências",
          });
        }
        if (vm.etapaNotificacao === 4) {
          vm.NotificationTypes.push({ id: "envioND", label: "Envio da ND" });
        }
        if (vm.etapaNotificacao === 5) {
          vm.NotificationTypes.push({ id: "pagamento", label: "Pagamento" });
        }
        if (vm.etapaNotificacao === 7) {
          vm.NotificationTypes.push({
            id: "vales",
            label: "Acompanhamento de Vales",
          });
        }
        if (vm.etapaNotificacao === 0) {
          vm.NotificationTypes.push({
            id: "pagamento",
            label: "Pagamento",
          });
        }
        if (vm.etapaNotificacao === 0) {
          vm.NotificationTypes.push({ id: "pagamento", label: "Pagamento" });
        }

        if (vm.Formulario.status === "CANCELADA") {
          vm.NotificationTypes = [
            { id: "cancelamento", label: "Cancelamento" },
          ];
        }
      };

      vm.reenviaNotificacao = (user, type) => {
        FLUIGC.message.confirm(
          {
            message: `Confirma o envio da notificação de ${type.label}?`,
            title: "Reenviar notificação",
          },
          (answer) => {
            console.log(answer);
            if (answer) {
              let enviaTodos = user ? "N" : "S";
              let email = user ? user.email : null;
              // let tipo;
              // switch (vm.etapaNotificacao) {
              //   case 1: tipo = 'iniAcao'; break;
              //   case 2: tipo = 'fimAcao'; break;
              //   case 3: tipo = 'evidencia'; break;
              //   case 4: tipo = 'envioND'; break;
              //   case 5: tipo = 'pagamento'; break;
              //   default: tipo = 'iniAcao'; break;
              // }

              // if (vm.Formulario.status == 'CANCELADA') {
              //   tipo = 'cancelamento';
              // }

              // console.log(vm.Formulario.solicitacao, tipo, email, enviaTodos, enviaTodos, enviaTodos)

              brotherService
                .notificaAcaoMarketing(
                  vm.Formulario.solicitacao,
                  type.id,
                  enviaTodos,
                  enviaTodos,
                  enviaTodos,
                  email
                )
                .then((result) => {
                  console.log(result);
                  FLUIGC.toast({
                    title: "Feito! ",
                    message: "Notificação enviada",
                    type: "success",
                  });
                });
            }
          }
        );
      };

      vm.changeAcaoCopiada = () => {
        if (vm.Formulario.acaoCopiada && vm.Formulario.acaoCopiada.displaykey) {
          FLUIGC.message.confirm(
            {
              message: `Deseja copiar os dados da solicitação ${vm.Formulario.acaoCopiada.displaykey}?`,
              title: "Copiar ação",
            },
            (result) => {
              if (result) {
                vm.copiaDadosAcao();

                // Array.splice($index, 1);
                $scope.$apply();
              }
            }
          );
        }
      };

      vm.copiaDadosAcao = () => {
        [
          'cliente', 'nomeAcao', 'tipoAcao', 'inicioAcao', 'terminoAcao', 'tipoQuantidade',
          'tipoVpc', 'tipoSellin', 'tipoSellout', 'tipoPrice', 'tipoSpiff', 'descricaoDetalhada', 'valorTotalVerba'
        ]
          .forEach(field => {
            try {

              vm.Formulario[field] = globalService.isJson(vm.Formulario.acaoCopiada[field]) ? JSON.parse(vm.Formulario.acaoCopiada[field]) : vm.Formulario.acaoCopiada[field];
            } catch (error) {
              vm.Formulario[field] = ''
            }
          });

        if (
          vm.Formulario.tipoAcao.tipoAcaoCodigo == "sellout" &&
          !vm.Formulario.tipoSellout
        ) {
          vm.Formulario.tipoSellout = "srp";
        }
        if (
          vm.Formulario.tipoAcao.tipoAcaoCodigo == "price" &&
          !vm.Formulario.tipoPrice
        ) {
          vm.Formulario.tipoPrice = "srp";
        }
        if (vm.Formulario.tipoAcao.tipoAcaoCodigo == 'price' && !vm.Formulario.tipoPrice) {
          vm.Formulario.tipoPrice = 'srp';
        }

        vm.buscaContatosCliente();

        const tablesToCopy = [
          { //Alteração verificar ACL
            //"price" != d.Formulario.tipoAcao.tipoAcaoCodigo || d.Formulario.tipoPrice || (d.Formulario.tipoPrice = "srp"), d.buscaContatosCliente(),{
            tablename: "itensPrice",
            fieldPrefix: "itemPrice",
            fields: ["target", "finalidade", "item", "srpInicial", "srpSugerido", "netInicial", "netSugerido", "rebateUnit", "qtde", "rebateTotal", "data"]
          },
          {
            tablename: 'itensSellout', fieldPrefix: 'itemSellout', fields:
              [
                'target', 'finalidade', 'item', 'srpInicial', 'srpSugerido',
                'netInicial', 'netSugerido', 'rebateUnit', 'qtde', 'rebateTotal', 'data',
                // 'qtdEvidencia', 'valEvidencia', 'totEvidencia'
              ]
          },
          // {
          //   tablename: "itensSellout",
          //   fieldPrefix: "itemSellout",
          //   fields: [
          //     "target",
          //     "finalidade",
          //     "item",
          //     "srpInicial",
          //     "srpSugerido",
          //     "netInicial",
          //     "netSugerido",
          //     "rebateUnit",
          //     "qtde",
          //     "rebateTotal",
          //     "data",
          //     "qtdEvidencia",
          //     "valEvidencia",
          //     "totEvidencia",
          //   ],
          // },
          {
            tablename: "itensSellinIt",
            fieldPrefix: "itemSellinIt",
            fields: [
              "item",
              "srpInicial",
              "srpSugerido",
              "gpInicial",
              "netInicial",
              "netSugerido",
              "gpSugerido",
              "rebateUnit",
              "qtde",
              "rebateTotal",
              "dolar",
            ],
          },
          {
            tablename: "itensSellinTg",
            fieldPrefix: "itemSellinTg",
            fields: [
              "descricao",
              "target",
              "qtde",
              "perc",
              "vlTarget",
              "vlTotal",
            ],
          },
          {
            tablename: "itensSellinTgAc",
            fieldPrefix: "itemSellinTgAc",
            fields: [
              "descricao",
              "target",
              "qtde",
              "perc",
              "vlTarget",
              "vlTotal",
            ],
          },
          {
            tablename: "itensSpiffIt",
            fieldPrefix: "itemSpiffIt",
            fields: ["item", "spiffUnit", "qtde", "vlTotal"],
          },
          {
            tablename: "itensSpiffTg",
            fieldPrefix: "itemSpiffTg",
            fields: ["foco", "target", "perc", "vlUnit", "vlTotal", "qtde"],
          },
          {
            tablename: "itensVpcEvt",
            fieldPrefix: "itemVpcEvt",
            fields: [
              "nomeEvento",
              "finalidade",
              "inicio",
              "termino",
              "perc",
              "vlTotal",
            ],
          },
          {
            tablename: "itensVpcOutros",
            fieldPrefix: "itemVpcOutros",
            fields: ["tipo", "finalidade", "qtd", "perc", "vlTotal"],
          },
          {
            tablename: "executivos",
            fieldPrefix: "executivo",
            fields: ["executivo"],
          },
          {
            tablename: "rateioCategoria",
            fieldPrefix: "rateio",
            fields: ["perc", "categoria"],
          },
        ];

        tablesToCopy.forEach((t) => {
          vm.Formulario[t.tablename] = [];
          fluigService
            .getDatasetAsync("marketing_abertura_verba", {
              documentid: vm.Formulario.acaoCopiada.documentid,
              tablename: t.tablename,
            })
            .then((children) => {
              children.forEach((i) => {
                let item = {};
                t.fields.forEach((field) => {
                  item[field] = globalService.isJson(
                    i[`${t.fieldPrefix}_${field}`]
                  )
                    ? JSON.parse(i[`${t.fieldPrefix}_${field}`])
                    : i[`${t.fieldPrefix}_${field}`];
                });
                vm.Formulario[t.tablename].push(item);
              });

              if (t.tablename === "rateioCategoria") {
                erpService.getBusinessSegment().then((categorias) => {
                  categorias.forEach((c) => {
                    if (
                      !vm.Formulario.rateioCategoria.filter(
                        (r) => r.categoria.codigo === c.codigo
                      )[0]
                    ) {
                      vm.Formulario.rateioCategoria.push({
                        categoria: c,
                        perc: 0,
                      });
                    }
                  });
                });
                vm.calculaTotalRateio();
                // vm.calculaTotais();
              }
            });
        });
      };

      vm.checkUrlArquivos = () => {
        vm.Formulario.arquivosEvidencias.forEach((a) => {
          $http
            .get(`/api/public/2.0/documents/getDownloadURL/${a.documentid}`)
            .then((res) => {
              console.log(res);
              a.url = res.data.content;
            });
        });

        vm.Formulario.arquivosND.forEach((a) => {
          $http
            .get(`/api/public/2.0/documents/getDownloadURL/${a.documentid}`)
            .then((res) => {
              console.log(res);
              a.url = res.data.content;
            });
        });
      };
      vm.checkEtapaNotificacao = function checkEtapaNotificacao() {
        if (
          vm.Params.etapa === "validarEvidencias" ||
          vm.Params.etapa == "enviarEvidencias" ||
          vm.Params.etapa == "evidenciasControle"
        ) {
          vm.Formulario.evRecusada =
            vm.Formulario.arquivosEvidencias.filter(
              (arquivo) => !arquivo.removed && !arquivo.aceito
            ).length > 0 && !vm.Formulario.suspenderAcao;
          vm.Formulario.revisao = vm.Formulario.evRecusada;
          if (vm.Formulario.evRecusada) {
            vm.Formulario.notificacaoEtapa = "ENVIO DAS EVIDÊNCIAS";
            vm.etapaNotificacao = 3;
            vm.regras.showNotificacaoCliente = true;
          } else {
            vm.Formulario.motivoRecusaEv = "";
            if (vm.Formulario.necEnvioNd) {
              vm.Formulario.notificacaoEtapa = "ENVIO DA ND";
              vm.etapaNotificacao = 4;
              vm.regras.showNotificacaoCliente = true;
            } else {
              vm.etapaNotificacao = 5;
              vm.regras.showNotificacaoCliente = false;
            }
          }
        } else {
          if (vm.Params.etapa == "validarND" || vm.Params.etapa == "enviarND") {
            vm.Formulario.ndRecusada =
              vm.Formulario.arquivosND.filter(
                (arquivo) => !arquivo.removed && !arquivo.aceito
              ).length > 0 && !vm.Formulario.suspenderAcao;
            vm.Formulario.revisao = vm.Formulario.ndRecusada;
            if (vm.Formulario.ndRecusada) {
              vm.Formulario.notificacaoEtapa = "ENVIO DA ND";
              vm.etapaNotificacao = 4;

              vm.regras.showNotificacaoCliente = true;
            } else {
              vm.Formulario.motivoRecusaND = "";
              vm.regras.showNotificacaoCliente = false;
            }
          }
        }
      };

      vm.changeCliente = function changeCliente() {
        vm.Formulario.emailsCliente = [];
        if (vm.Formulario.cliente && vm.Formulario.cliente.codigo) {
          if (vm.Formulario.cliente.executivo) {
            vm.Formulario.executivo = fluigService.getUsuarios(
              vm.Formulario.cliente.executivo
            )[0];
          }

          vm.Formulario.itensSellinIt.forEach((itemSellinIt) => {
            vm.calculaItemErp(itemSellinIt);
          });

          vm.Formulario.itensSellout.forEach(itemSellout => {
            vm.calculaItemErp(itemSellout)
          })
          vm.Formulario.itensPrice.forEach(itemPrice => {
            vm.calculaItemErp(itemPrice)
          })

          vm.buscaContatosCliente();

          vm.buscaExecutivosCliente()

        }
      };

      vm.buscaContatosCliente = () => {
        brotherService
          .getMarketingCliente(vm.Formulario.cliente.codigo)
          .then((cliente) => {
            if (cliente[0]) {
              brotherService
                .getContatosCliente(cliente[0].documentid)
                .then((contatos) => {
                  vm.Formulario.emailsCliente = [];
                  contatos.forEach((contato) => {
                    vm.Formulario.emailsCliente.push({
                      email: contato.contato_email,
                      iniAcao: contato.contato_iniAcao,
                      evidencia: contato.contato_evidencia,
                      envioND: contato.contato_envioND,
                      pagamento: contato.contato_pagamento,
                      cancelamento: contato.contato_cancelamento,
                      vales: contato.contato_vales,
                    });
                  });
                });
            }
          });
      };

      //Nova função busca executivos
      vm.buscaExecutivosCliente = function () {
        (vm.Formulario.executivos = []),
          fluigService
            .getDatasetAsync("totvs_busca_executivo", {
              nome: vm.Formulario.cliente.executivo,
            })
            .then(function (executivos) {
              executivos.forEach(function (executivo) {
                vm.Formulario.executivos.push({
                  executivo: {
                    codigo: executivo.codigo,
                    nome: executivo.nome,
                    email: executivo.email,
                  },
                });
              });
            });
      };
      // fim busca executivos

      vm.buscaExecutivosCliente = function () {
        vm.Formulario.executivos = [],
          fluigService.getDatasetAsync("totvs_busca_executivo", {
            nome: vm.Formulario.cliente.executivo
          }).then(function (o) {
            o.forEach(function (o) {
              vm.Formulario.executivos.push({
                executivo: {
                  codigo: o.codigo,
                  nome: o.nome,
                  email: o.email
                }
              })
            })
          })
      }

      vm.buscaResumoVerbas = function buscaResumoVerbas() {
        // erpService.getResumoVerbas(vm.Formulario.cliente.codigo);
        // vm.Formulario.resumoVerbas = [
        //   { titulo: 'AGUARDANDO APROVAÇÃO', class: 'warning', rebateSellout: 9000, rebateSellin: 0, spiff: 300, vpc: 0, total: 9300 },
        //   { titulo: 'FY 2018', class: 'success', rebateSellout: 50000, rebateSellin: 0, spiff: 10000, vpc: 30000, total: 90000 },
        //   { titulo: 'FY 2019 - YTD', class: 'success', rebateSellout: 10000, rebateSellin: 0, spiff: 10000, vpc: 1500, total: 12500 },
        //   { titulo: 'PAGAMENTOS EFETUADOS - FY ATUAL (YTD)', class: 'active', rebateSellout: 55000, rebateSellin: 0, spiff: 5600, vpc: 7000, total: 67600 },
        //   { titulo: 'TOTAL', class: 'info', rebateSellout: 124000, rebateSellin: 0, spiff: 16900, vpc: 38500, total: 89400 },
        // ]
      }
      vm.changeItemPrice = function changeItemPrice(item, index) {
        if (item.item && item.item.codigo) {
          vm.calculaItemErp(item);
        }
      }
      vm.changeItemSellout = function changeItemSellout(item, index) {
        if (item.item && item.item.codigo) {
          vm.calculaItemErp(item);
        }
      };

      //==================Alteração ACL===========================
      vm.changeItemPrice = function (item, index) {
        item.item && item.item.codigo && vm.calculaItemErp(item);
      };
      //=================FIM Alteração============================

      vm.changeItemSellinIt = function changeItemSellinIt(item, index) {
        if (item.item && item.item.codigo) {
          vm.calculaItemErp(item);
        }
      };

      vm.calculaItemErp = function (item, loadContainer) {
        if (item.item && item.item.codigo && item.alterado) {
          if (
            (vm.Formulario.tipoAcao.tipoAcaoCodigo == 'sellout' && vm.Formulario.tipoSellout == 'net') ||
            (vm.Formulario.tipoAcao.tipoAcaoCodigo == 'price' && vm.Formulario.tipoPrice == 'net') ||
            (vm.Formulario.tipoAcao.tipoAcaoCodigo == 'sellin' && vm.Formulario.tipoSellin == 'net')) {
            item.rebateUnit = parseFloat(Number(item.netInicial - item.netSugerido).toFixed(4));

            vm.calculaRebateTotal(item);

            item.alterado = false;
          } else if (
            vm.Formulario.tipoAcao.tipoAcaoCodigo == "sellout" &&
            vm.Formulario.tipoSellout == "target"
          ) {
            vm.calculaRebateTotal(item);
          } else {
            if (item.srpInicial || item.srpSugerido) {
              item.loading = true;

              vm.Errors = [];

              erpService
                .calculaItemErp(
                  item.item.codigo,
                  vm.Formulario.cliente.codigo,
                  item.srpInicial,
                  item.srpSugerido
                )
                .then(
                  (result) => {
                    item.alterado = false;
                    item.loading = false;

                    if (result[0].erro) {
                      let msg = `Ocorreu um erro ao calcular o item. Não será possível iniciar a solicitação \n\n ${result[0].erro}`;
                      FLUIGC.message.error({
                        message: msg,
                        title: "Oops",
                      });
                      vm.Errors.push(msg);
                      return;
                    }

                    let valores = fluigService.fixDataset(result);

                    if (valores.length == 0) {
                      FLUIGC.message.error(
                        {
                          message: "Não foi possível calcular o item no ERP",
                          title: "Oops",
                        },
                        (result) => { }
                      );
                      return;
                    }

                    item.netInicial = parseFloat(
                      valores[0].netInicial.toFixed(4)
                    );
                    item.netSugerido = parseFloat(
                      valores[0].netSugerido.toFixed(4)
                    );
                    // item.gpInicial = parseFloat(valores[0].gpInicial.toFixed(4));
                    // item.gpSugerido = parseFloat(valores[0].gpSugerido.toFixed(4));
                    item.dolar = parseFloat(valores[0].dolar.toFixed(4));
                    item.rebateUnit = parseFloat(
                      Number(item.netInicial - item.netSugerido).toFixed(4)
                    );

                    vm.calculaRebateTotal(item);
                  },
                  (error) => {
                    item.alterado = false;
                    item.loading = false;
                    FLUIGC.loading(".panel-heading").hide();
                  }
                );
            }
          }
        }
      };

      vm.calculaRebateTotal = function calculaRebateTotal(item) {
        item.rebateTotal = item.rebateUnit * item.qtde;
        item.rebateTotal = parseFloat(item.rebateTotal.toFixed(4));
        vm.calculaTotais();
      };

      vm.calculaTotalRateio = function calculaTotalRateio() {
        vm.Formulario.totalRateio = 0;
        vm.Formulario.rateioCategoria.forEach((r) => {
          vm.Formulario.totalRateio += r.perc;
        });

        vm.Formulario.totalRateio = parseFloat(
          vm.Formulario.totalRateio.toFixed(4)
        );
      };

      vm.changeTipoAcao = function changeTipoAcao() {
        vm.Formulario.itensSellout = [];
        vm.Formulario.itensPrice = [];
        vm.Formulario.itensSellinIt = [];
        vm.Formulario.itensSellinTg = [];
        vm.Formulario.itensSellinTgAc = [];
        vm.Formulario.itensVpcOutros = [];
        vm.Formulario.itensVpcEvt = [];
        vm.Formulario.itensSpiffIt = [];
        vm.Formulario.itensSpiffTg = [];
        vm.calculaTotais();
        vm.bloqRateio = false;
        vm.Formulario.rateioCategoria.forEach((r) => (r.perc = 0));

        if (vm.Formulario.tipoAcao && vm.Formulario.tipoAcao.tipoAcaoCodigo) {
          switch (vm.Formulario.tipoAcao.tipoAcaoCodigo) {
            case "sellout":
              vm.incluiItem(vm.Formulario.itensSellout);
              if (
                vm.Formulario.tipoSellout == "srp" ||
                vm.Formulario.tipoSellout == "net"
              ) {
                vm.bloqRateio = true;
              }
              break;
            case 'price':
              vm.incluiItem(vm.Formulario.itensPrice);
              if (vm.Formulario.tipoPrice == 'srp' || vm.Formulario.tipoPrice == 'net') {
                vm.bloqRateio = true;
              }
              break;
            case 'sellin':
              if (vm.Formulario.tipoSellin == 'item' || vm.Formulario.tipoSellin == 'net') {
                vm.incluiItem(vm.Formulario.itensSellinIt);
                vm.bloqRateio = true;
              } else {
                vm.incluiItem(vm.Formulario.itensSellinTg);
                // vm.incluiItem(vm.Formulario.itensSellinTgAc);
              }
              break;
            case "vpc":
              if (vm.Formulario.tipoVpc == "eventos") {
                vm.incluiItem(vm.Formulario.itensVpcEvt);
              } else {
                vm.incluiItem(vm.Formulario.itensVpcOutros);
              }
              break;
            case "spiff":
              if (vm.Formulario.tipoSpiff == "item") {
                vm.incluiItem(vm.Formulario.itensSpiffIt);
                vm.bloqRateio = true;
              } else {
                vm.incluiItem(vm.Formulario.itensSpiffTg);
              }
              break;
          }
        }
      };

      vm.calculaTotalDuplicatas = function calculaTotalDuplicatas() {
        let total = 0;
        vm.Formulario.duplicatas.forEach((d) => {
          if (!d.valorAntecipa) {
            d.valorAntecipa = 0;
          }
          total += Number(d.valorAntecipa);
          d.saldoAposAbatimento =
            Number(d.valorSaldo) - Number(d.valorAntecipa);
        });

        total = parseFloat(total.toFixed(4));

        vm.Formulario.difValorLiberado =
          vm.Formulario.valorLiberado - vm.Formulario.valorAntecipacao - total;
      };

      vm.incluiItem = function incluiItem(obj) {
        obj.push({
          data: new Date().getTime(),
          item: {},
          usuario: vm.Usuario,
          qtde: 0,
          vlTotal: 0,
          rebateTotal: 0,
          perc: 0,
          gpInicial: 0,
          gpSugerido: 0,
          srpInicial: 0,
          srpSugerido: 0,
          netInicial: 0,
          netSugerido: 0,
          rebateUnit: 0,
          dolar: 0,
        });
      };

      vm.removeChild = function removeChild(Array, item) {
        FLUIGC.message.confirm(
          {
            message: "Deseja excluir esse registro?",
            title: "Excluir",
          },
          (result) => {
            if (result) {
              Array.splice(Array.indexOf(item), 1);
              // Array.splice($index, 1);
              $scope.$apply();
            }
          }
        );
      };

      vm.changeDataVales = () => {
        if (vm.Formulario.dataEntregaVales) {
          vm.Formulario.statusVales = "ENTREGUE";
        } else {
          if (vm.Formulario.dataEnvioVales) {
            vm.Formulario.statusVales = "ENVIADO";
          } else {
            if (vm.Formulario.dataCompraVales) {
              vm.Formulario.statusVales = "COMPRADO";
            }
          }
        }
      };

      vm.buscaDuplicatas = function buscaDuplicatas() {
        let loading = FLUIGC.loading(`#collapseSelecionarDuplicatas`);

        loading.show();

        vm.Formulario.saldoTitulos = 0;
        vm.Formulario.difValorLiberado = 0;
        vm.Formulario.valorAntecipacao = 0;

        erpService
          .getTitulosCliente(vm.Formulario.cliente.codigo)
          .then((duplicatas) => {
            loading.hide();

            if (duplicatas[0] && duplicatas[0].erro) {
              let msg = `Ocorreu um erro ao buscar as duplicatas. Não será possível movimentar a solicitação \n\n ${duplicatas[0].erro}`;
              FLUIGC.message.error({
                message: msg,
                title: "Oops",
              });

              vm.Errors.push(msg);
              return;
            }

            brotherService
              .getMarketingAberturaVerba(vm.Formulario.cliente.codigo)
              .then((solicitacoes) => {
                let solicitacoesEmAprovacao = solicitacoes.filter(
                  (s) => s.status == "APROVAÇÃO FINANCEIRA"
                );
                let titulos = [];
                solicitacoesEmAprovacao.forEach((s) => {
                  titulos = titulos.concat(
                    DatasetFactory.getDataset(
                      "marketing_abertura_verba",
                      null,
                      [
                        DatasetFactory.createConstraint(
                          "documentid",
                          s.documentid,
                          s.documentid,
                          ConstraintType.MUST
                        ),
                        DatasetFactory.createConstraint(
                          "tablename",
                          "duplicatas",
                          "duplicatas",
                          ConstraintType.MUST
                        ),
                      ]
                    ).values
                  );
                });

                vm.Formulario.duplicatas.forEach((d) => {
                  d.emAprovacao =
                    titulos.filter(
                      (t) =>
                        t.titulo_numTitulo == d.numTitulo &&
                        t.titulo_parcela == d.parcela
                    ).length > 0;
                });
              });

            duplicatas.forEach((duplicata) => {
              let regDuplicata = vm.Formulario.duplicatas.filter(
                (d) =>
                  d.numTitulo == duplicata.numTitulo &&
                  d.parcela == duplicata.parcela
              )[0];

              if (!regDuplicata) {
                vm.Formulario.duplicatas.push(duplicata);
              } else {
                regDuplicata.codCliente = duplicata.codCliente;
                regDuplicata.codEspec = duplicata.codEspec;
                regDuplicata.codEstab = duplicata.codEstab;
                regDuplicata.codSerie = duplicata.codSerie;
                regDuplicata.matriz = duplicata.matriz;
                regDuplicata.dataEmissao = duplicata.dataEmissao;
                regDuplicata.dataVencto = duplicata.dataVencto;
                regDuplicata.numTitulo = duplicata.numTitulo;
                regDuplicata.parcela = duplicata.parcela;
                regDuplicata.valorOriginal = duplicata.valorOriginal;
                regDuplicata.valorSaldo = duplicata.valorSaldo;
              }

              vm.Formulario.saldoTitulos += Number(duplicata.valorSaldo);
            });

            if (vm.Formulario.saldoTitulos < vm.Formulario.valorLiberado) {
              vm.Formulario.valorAntecipacao =
                vm.Formulario.valorLiberado - vm.Formulario.saldoTitulos;
              vm.Formulario.duplicatas.forEach((titulo) => {
                titulo.valorAntecipa = titulo.valorSaldo;
              });
            }

            vm.calculaTotalDuplicatas();
          });
      };

      vm.selecionaArquivosEvidencias = (item, files) => {
        // vm.selectFiles(vm.Formulario.arquivosEvidencias, null, $files);

        files.forEach((file) => {
          file.uploading = true;
          file.descricao = file.name;
          file.nome = file.name;
          file.novo = true;
          file.item = item;

          vm.Formulario.arquivosEvidencias.push(file);

          Upload.upload({
            url: "/ecm/upload",
            data: {
              userId: vm.Params.user || "fluigpost2",
              uploadWithTimeStamp: true,
              file: file,
            },
            // headers: $oauth.oauth.toHeader($oauth.oauth.authorize(reqUpload, $oauth.token))
          }).then(
            function (resp) {
              resp.data.files.forEach((uploadedFile) => {
                vm.createDocument(file, uploadedFile);
              });
            },
            function (resp) {
              console.log("Error status: " + resp.status);
            },
            function (evt) {
              file.progressPercentage = parseInt(
                (100.0 * evt.loaded) / evt.total
              );
            }
          );
        });
      };

      vm.getItens = () => {
        vm.ItensEvidencia = [];

        console.log("vm.Formulario.tipoAcao = ", vm.Formulario.tipoAcao);

        if (
          vm.Formulario.tipoAcao &&
          vm.Formulario.tipoAcao.tipoAcaoCodigo &&
          vm.Formulario.tipoSellout !== "target" &&
          vm.Formulario.tipoPrice !== "target"
        ) {
          switch (vm.Formulario.tipoAcao.tipoAcaoCodigo) {
            case "sellout":
              vm.Formulario.itensSellout.forEach((it, index) => {
                if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.rebateUnit;
                vm.ItensEvidencia.push({
                  tablename: "itensSellout",
                  index,
                  descricao: it.item.displaykey,
                  valEvidencia: it.rebateUnit,
                  valorTotal: it.rebateTotal,
                });
              });
              break
            case 'price':
              vm.Formulario.itensPrice.forEach((it, index) => {
                if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.rebateUnit;
                vm.ItensEvidencia.push({ tablename: 'itensPrice', index, descricao: it.item.displaykey, valEvidencia: it.rebateUnit, valorTotal: it.rebateTotal });
              });
              break

            case "sellin":
              if (
                vm.Formulario.tipoSellin == "item" ||
                vm.Formulario.tipoSellin == "net"
              ) {
                vm.Formulario.itensSellinIt.forEach((it, index) => {
                  if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.rebateUnit;
                  vm.ItensEvidencia.push({
                    tablename: "itensSellinIt",
                    index,
                    descricao: it.item.displaykey,
                    valEvidencia: it.rebateUnit,
                    valorTotal: it.rebateTotal,
                  });
                });
              }
              break;

            case "spiff":
              if (vm.Formulario.tipoSpiff == "item") {
                vm.Formulario.itensSpiffIt.forEach((it, index) => {
                  if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.valorUnit;
                  vm.ItensEvidencia.push({
                    tablename: "itensSpiffIt",
                    index,
                    descricao: it.item.displaykey,
                    valEvidencia: it.rebateUnit,
                    valorTotal: it.vlTotal,
                  });
                });
              }
              break;
          }
        }

        vm.ItensEvidencia.forEach((item) => {
          vm.calculaTotalItemEvidencia(item);
        });
      };

      vm.calculaTotalItemEvidencia = (item) => {
        vm.Formulario[item.tablename][item.index].valEvidencia =
          vm.Formulario[item.tablename][item.index].valEvidencia || 0;
        vm.Formulario[item.tablename][item.index].qtdEvidencia =
          vm.Formulario[item.tablename][item.index].qtdEvidencia || 0;

        vm.Formulario[item.tablename][item.index].totEvidencia =
          vm.Formulario[item.tablename][item.index].valEvidencia *
          vm.Formulario[item.tablename][item.index].qtdEvidencia;
        vm.Formulario.valorResultado = 0;
        vm.ItensEvidencia.forEach((item) => {
          vm.Formulario.valorResultado +=
            vm.Formulario[item.tablename][item.index].totEvidencia || 0;
        });
      };

      vm.calculaTotais = function calculaTotais() {
        vm.Formulario.valorTotalVerba = 0;
        vm.Formulario.gpMedioSugerido = 0;
        let qtdItem = 0;

        if (vm.Formulario.tipoAcao && vm.Formulario.tipoAcao.tipoAcaoCodigo) {
          switch (vm.Formulario.tipoAcao.tipoAcaoCodigo) {
            case 'sellout':
              "net" == vm.Formulario.tipoSellout ? (vm.Formulario.itensSellout.forEach((it) => {
                vm.Formulario.valorTotalVerba += it.rebateTotal || 0, vm.Formulario.gpMedioSugerido += it.gpSugerido || 0, qtdItem++
              }), vm.Formulario.gpMedioSugerido = vm.Formulario.gpMedioSugerido / qtdItem, vm.calculaPercCategoria()) : (vm.Formulario.itensSellout.forEach(function (it) {
                vm.Formulario.valorTotalVerba += it.rebateTotal || 0
              }), vm.Formulario.itensSellout.forEach(function (it) {
                vm.Formulario.valorTotalVerba += it.rebateTotal || 0
              }));
              break;
            case "price":
              "net" == vm.Formulario.tipoPrice ? (vm.Formulario.itensPrice.forEach((it) => {
                vm.Formulario.valorTotalVerba += it.rebateTotal || 0, vm.Formulario.gpMedioSugerido += it.gpSugerido || 0, qtdItem++
              }), vm.Formulario.gpMedioSugerido = vm.Formulario.gpMedioSugerido / qtdItem, vm.calculaPercCategoria()) : (vm.Formulario.itensPrice.forEach((it) => {
                vm.Formulario.valorTotalVerba += it.vlTotal || 0
              }), vm.Formulario.itensPrice.forEach(function (it) {
                vm.Formulario.valorTotalVerba += it.vlTotal || 0
              }));
              break;
            case 'sellin':
              if (vm.Formulario.tipoSellin == 'item' || vm.Formulario.tipoSellin == 'net') {
                vm.Formulario.itensSellinIt.forEach(it => {
                  vm.Formulario.valorTotalVerba += it.rebateTotal || 0;
                  vm.Formulario.gpMedioSugerido += it.gpSugerido || 0;
                  qtdItem++;
                });
                vm.Formulario.gpMedioSugerido =
                  vm.Formulario.gpMedioSugerido / qtdItem;
                vm.calculaPercCategoria();
              } else {
                vm.Formulario.itensSellinTg.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
                vm.Formulario.itensSellinTgAc.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
              }

              break;

            case "vpc":
              if (vm.Formulario.tipoVpc == "eventos") {
                vm.Formulario.itensVpcEvt.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
              } else {
                vm.Formulario.itensVpcOutros.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
              }

              break;

            case "spiff":
              if (vm.Formulario.tipoSpiff == "item") {
                vm.Formulario.itensSpiffIt.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
                vm.calculaPercCategoria();
              } else {
                vm.Formulario.itensSpiffTg.forEach((it) => {
                  vm.Formulario.valorTotalVerba += it.vlTotal || 0;
                });
              }

              break;
          }
        }
      };

      vm.incluirEmailNotificacao = function incluirEmailNotificacao() {
        vm.Formulario.emailsCliente.push({
          iniAcao: vm.etapaNotificacao < 2,
          fimAcao: vm.etapaNotificacao < 3,
          evidencia: vm.etapaNotificacao < 4,
          envioND: vm.etapaNotificacao < 5,
          pagamento: vm.etapaNotificacao < 6,
        });
      };

      vm.calculaTarget = function calculaTarget(item) {
        item.vlTotal = (item.vlTarget || item.vlUnit || 0) * (item.qtde || 0);
        item.vlTotal = parseFloat(item.vlTotal.toFixed(4));
        vm.calculaTotais();
      };

      vm.excluirAnexo = function excluirAnexo(arquivo, $index) {
        FLUIGC.message.confirm(
          {
            message: "Deseja excluir esse anexo?",
            title: "Excluir anexo",
          },
          (result) => {
            if (result) {
              let docsToDelete = [];

              docsToDelete.push({
                docId: arquivo.anexo.documentId,
                isLink: false,
                parentId: vm.Formulario.folderAttach,
              });

              $http({
                method: "DELETE",
                url: "/ecm/api/rest/ecm/navigation/removeDoc",
                data: {
                  docsToDelete: docsToDelete,
                  metadataFormsToDelete: [],
                },
                headers: {
                  "Content-type": "application/json;charset=utf-8",
                },
              }).then(
                function (response) {
                  arquivo.anexo = {};
                },
                function (rejection) {
                  console.log(rejection.data);
                }
              );
            }
          }
        );
      };

      vm.uploadFiles = function uploadFiles(files) {
        if (files && files.length) {
          for (var i = 0; i < files.length; i++) {
            vm.upload(files[i]);
          }
        }
      };

      vm.removeFile = function removeFile(doc, index) {
        FLUIGC.message.confirm(
          { message: "Deseja excluir esse arquivo?", title: "Excluir arquivo" },
          (result) => {
            if (result) {
              $oauth
                .post("/api/public/ecm/document/remove", {
                  id: doc.doc_arquivos[index].id,
                })
                .then((response) => {
                  doc.doc_arquivos.splice(index, 1);
                  // $scope.$apply();
                });
            }
          }
        );
      };

      vm.selectFiles = function selectFiles(array, object, files) {
        files.forEach((file) => {
          file.uploading = true;
          file.descricao = file.name;
          file.nome = file.name;
          file.novo = true;
          file.item = {};

          if (array) {
            array.push(file);
          }
          if (object) {
            vm.Formulario[object] = file;
          }

          Upload.upload({
            url: "/ecm/upload",
            data: {
              userId: vm.Params.user || "fluigpost2",
              uploadWithTimeStamp: true,
              file: file,
            },
            // headers: $oauth.oauth.toHeader($oauth.oauth.authorize(reqUpload, $oauth.token))
          }).then(
            function (resp) {
              resp.data.files.forEach((uploadedFile) => {
                vm.createDocument(file, uploadedFile);
              });
            },
            function (resp) {
              console.log("Error status: " + resp.status);
            },
            function (evt) {
              file.progressPercentage = parseInt(
                (100.0 * evt.loaded) / evt.total
              );
            }
          );
        });
      };
      /*=====================================inicio anexo=================================================*/

      vm.salvar = function salvar(loading) {
        if (!vm.alterado) {
          return;
        }

        vm.alterado = false;

        var notUploaded = false;
        vm.Formulario.anexos.forEach(function (arquivo) {
          if (!arquivo.url) {
            notUploaded = true;
          }
        });

        if (notUploaded) {
          return;
        }

        if (loading) FLUIGC.loading('body').show();

        vm.loading = loading;
        //d.Formulario.solicitacao = '7292';
        console.log('TGO + ACL d.Formulario:', vm.Formulario);
        console.log('TGO + ACL d.guid():', vm.guid());

        $http.post('/brother-api/v1/marketing/update', vm.Formulario, {
          headers: {
            guid: vm.guid()
          }
        }).then(function () {
          if (loading) FLUIGC.loading('body').hide();

          vm.setRegras();
          if (loading) {
            vm.showConfirmPage();
          }

          vm.loading = false;
        }, function (error) {
          vm.done = true;
          if (loading) FLUIGC.loading('body').hide();
          $log.log(error);

          vm.loading = false;
        });
      };

      vm.selectFiles1 = function selectFiles1(tablename, files, item) {
        console.log('TGO + ACL tablename', tablename);
        console.log('TGO + ACL files', files);
        console.log('TGO + ACL item', item);

        if (!vm.Formulario[tablename]) {
          vm.Formulario[tablename] = [];
        }
        files.forEach(function (file) {
          file.nome = file.name;
          // file.descricao = file.name;
          file.tipo = file.type;
          file.item = item || {};
          vm.Formulario[tablename].push(file);
          vm.upload(file);
        });
      };

      vm.upload = function upload(file) {
        Upload.upload({
          url: '/brother-api/v1/file/upload',
          data: {
            file: file,
            parentDocumentId: vm.Formulario.folderAttach
          }
        }).then(function (resp) {
          file.documentid = resp.data.documentid;
          file.numero = '';
          file.descricao = '';
          file.nome = resp.data.filename;
          file.filename = resp.data.filename;
          file.url = resp.data.url;
          file.description = resp.data.description;
          file.version = resp.data.version;
          file.uploaded = true;
          file.removed = false;

          $log.log(file);

          vm.alterado = true;
          vm.salvar();

        }, function (resp) {
          $log.log('Error status: ' + resp.status);
        }, function (evt) {
          file.progressPercentage = parseInt(100.0 * evt.loaded / evt.total, 10);
        });
      };

      vm.removeArquivo = function removeArquivo(arquivo) {
        FLUIGC.message.confirm({ message: 'Deseja excluir esse arquivo?', title: 'Excluir arquivo' }, function (result) {
          if (result) {
            arquivo.removed = true;
            vm.alterado = true;
            $scope.$apply();
            vm.salvar();
          }
        });
      };

      /*==============================================fim anexo=========================================*/

      /*=====================================inicio anexo=================================================*/

      vm.salvar = function salvar(loading) {
        if (!vm.alterado) {
          return;
        }

        vm.alterado = false;

        var notUploaded = false;
        vm.Formulario.anexos.forEach(function (arquivo) {
          if (!arquivo.url) {
            notUploaded = true;
          }
        });

        if (notUploaded) {
          return;
        }

        if (loading) FLUIGC.loading("body").show();

        vm.loading = loading;
        //vm.Formulario.solicitacao = '7292';
        console.log("TGO + ACL vm.Formulario:", vm.Formulario);
        console.log("TGO + ACL vm.guid():", vm.guid());

        $http
          .post("/brother-api/v1/marketing/update", vm.Formulario, {
            headers: {
              guid: vm.guid(),
            },
          })
          .then(
            function () {
              if (loading) FLUIGC.loading("body").hide();

              vm.setRegras();
              if (loading) {
                vm.showConfirmPage();
              }

              vm.loading = false;
            },
            function (error) {
              vm.done = true;
              if (loading) FLUIGC.loading("body").hide();
              $log.log(error);

              vm.loading = false;
            }
          );
      };

      vm.selectFiles1 = function selectFiles1(tablename, files, item) {
        console.log("TGO + ACL tablename", tablename);
        console.log("TGO + ACL files", files);
        console.log("TGO + ACL item", item);

        if (!vm.Formulario[tablename]) {
          vm.Formulario[tablename] = [];
        }
        files.forEach(function (file) {
          file.nome = file.name;
          // file.descricao = file.name;
          file.tipo = file.type;
          file.item = item || {};
          vm.Formulario[tablename].push(file);
          vm.upload(file);
        });
      };

      vm.upload = function upload(file) {
        Upload.upload({
          url: "/brother-api/v1/file/upload",
          data: {
            file: file,
            parentDocumentId: vm.Formulario.folderAttach,
          },
        }).then(
          function (resp) {
            file.documentid = resp.data.documentid;
            file.numero = "";
            file.descricao = "";
            file.nome = resp.data.filename;
            file.filename = resp.data.filename;
            file.url = resp.data.url;
            file.description = resp.data.description;
            file.version = resp.data.version;
            file.uploaded = true;
            file.removed = false;

            $log.log(file);

            vm.alterado = true;
            vm.salvar();
          },
          function (resp) {
            $log.log("Error status: " + resp.status);
          },
          function (evt) {
            file.progressPercentage = parseInt(
              (100.0 * evt.loaded) / evt.total,
              10
            );
          }
        );
      };

      vm.removeArquivo = function removeArquivo(arquivo) {
        FLUIGC.message.confirm(
          { message: "Deseja excluir esse arquivo?", title: "Excluir arquivo" },
          function (result) {
            if (result) {
              arquivo.removed = true;
              vm.alterado = true;
              $scope.$apply();
              vm.salvar();
            }
          }
        );
      };

      /*==============================================fim anexo=========================================*/

      vm.createDocument = function createDocument(file, uploadedFile) {
        $http
          .post("/api/public/2.0/documents/createDocument", {
            parentDocumentId: vm.Formulario.folderAttach,
            documentDescription: uploadedFile.name,
            inheritSecurity: true,
            internalVisualizer: true,
          })
          .then(
            (response) => {
              file.documentid = response.data.content.documentId;
              file.version = response.data.content.version;
              $http
                .get(
                  "/api/public/2.0/documents/getDownloadURL/" +
                  response.data.content.documentId
                )
                .then(
                  (url) => {
                    file.url = url.data.content;
                    delete file.uploading;
                    delete file.progressPercentage;
                  },
                  (error) => {
                    console.log(error);
                  }
                );
            },
            (error) => {
              console.log(error);
            }
          );
      };

      vm.updateArquivoContrato = function updateArquivoContrato(
        arquivo,
        files
      ) {
        let file = files[0];

        arquivo.uploading = true;
        Upload.upload({
          url: "/ecm/upload",
          data: {
            userId: parent.WCMAPI.userCode,
            uploadWithTimeStamp: true,
            file: file,
          },
          // headers: $oauth.oauth.toHeader($oauth.oauth.authorize(reqUpload, $oauth.token))
        }).then(
          function (resp) {
            resp.data.files.forEach((uploadedFile) => {
              vm.updateFile(arquivo, uploadedFile);
            });
          },
          function (resp) {
            console.log("Error status: " + resp.status);
          },
          function (evt) {
            arquivo.progressPercentage = parseInt(
              (100.0 * evt.loaded) / evt.total
            );
          }
        );
      };

      vm.updateFile = function updateFile(file, uploadedFile) {
        $http
          .post("/api/public/ecm/document/updateFile", {
            comments: "",
            fileName: uploadedFile.name,
            id: file.arquivo_documentId,
            version: file.arquivo_version,
            versionAction: "KEEP_VERSION",
          })
          .then(
            (response) => {
              file.arquivo_version = response.data.content.version;
              $http
                .get(
                  "/api/public/2.0/documents/getDownloadURL/" +
                  file.arquivo_documentId
                )
                .then(
                  (url) => {
                    file.url = url.content;
                    delete file.uploading;
                    delete file.progressPercentage;
                  },
                  (error) => {
                    console.log(error);
                  }
                );
            },
            (error) => {
              console.log(error);
            }
          );
      };

      vm.sendChatMessage = function sendChatMessage() {
        vm.Formulario.chat.push({
          texto: vm.chatMessage,
          time: new Date().getTime(),
          user: vm.Params.user,
          userName: vm.Usuario.colleagueName,
        });

        vm.chatMessage = null;
      };

      vm.openForm = (solicitacao) => { };

      vm.createExtMav = () => {
        if (vm.Formulario.solicitacao) {
          fluigService
            .getPasta(
              vm.Params.companyId || 1,
              "Cadastros%7CMarketing%7CIntegração"
            )
            .then((pasta) => {
              // vm.Formulario.folderAttach = pasta[0].documentId;
              if (pasta[0]) {
                brotherService
                  .getExtMav(vm.Formulario.solicitacao)
                  .then((result) => {
                    let extMav = result[0];
                    if (!extMav) {
                      let formData = [
                        {
                          name: "solicitacao",
                          value: vm.Formulario.solicitacao,
                        },
                        { name: "pendenteTotvs", value: "true" },
                      ];
                      fluigService.createCard(
                        vm.Formulario.solicitacao,
                        pasta[0].documentId,
                        formData
                      );
                    }
                  });
              }
            });
        }
      };

      vm.guid = function guid() {
        function s4() {
          return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
        }
        return `${s4() + s4()}$${s4()}$${s4()}$${s4()}$${s4()}${s4()}${s4()}`;
      };
    },
  ])

  .directive("ngEnter", function () {
    return function (scope, element, attrs) {
      element.bind("keydown keypress", function (event) {
        if (event.which === 13) {
          scope.$apply(function () {
            scope.$eval(attrs.ngEnter);
          });
          event.preventDefault();
        }
      });
    };
  });
