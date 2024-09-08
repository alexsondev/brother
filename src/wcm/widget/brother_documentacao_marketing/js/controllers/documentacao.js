angular
  .module('BrotherDocumentacaoMarketingMainApp')
  .controller('BrotherDocumentacaoMarketingController', ['$scope', '$log', '$http', '$routeParams', '$timeout', 'fluigService', '$window', 'Upload',
    function BrotherDocumentacaoMarketingController($scope, $log, $http, $routeParams, $timeout, fluigService, $window, Upload) {
      const vm = this;

      vm.Errors = [];
      vm.loader = FLUIGC.loading('.container');

      vm.inicia = () => {
        vm.Param = {
          guid: $routeParams.guid
        };
        if (vm.Param.guid) {
          vm.buscaSolicitacao();
        }
      };

      vm.logout = () => {
        $http.get('/portal/p/api/servlet/logout.do');
      };

      vm.buscaSolicitacao = function buscaSolicitacao() {
        FLUIGC.loading('body').show();

        vm.Formulario = null;

        $http.get('/brother-api/v1/marketing/textos').then((response) => {
          vm.Textos = response.data;
          // vm.showConfirmPage();
        }, (error) => {
          console.log("🚀 ~ file: documentacao.js:32 ~ $http.get ~ error:", error)
          $log.log(error);
          console.log("🚀 ~ file: documentacao.js:34 ~ $http.get ~ error:", error)
        });

        $http.get(`/brother-api/v1/marketing/search/${vm.Param.guid}`)
          .then((response) => {
            FLUIGC.loading('body').hide();
            vm.done = true;
            // vm.show = true;
            vm.Params = {};

            vm.Formulario = response.data;
            for (const key in vm.Formulario) {
              if (typeof vm.Formulario[key] === 'string') {
                if (vm.Formulario[key] === 'null') {
                  vm.Formulario[key] = '';
                }
              }
            }

            vm.setRegras();
            vm.getItens();
          }, (error) => {
            console.log("🚀 ~ file: documentacao.js:49 ~ .then ~ error:", error)
            vm.done = true;
            $log.log(error);
            console.log("🚀 ~ file: documentacao.js:52 ~ .then ~ error:", error)
          });
      };

      vm.showConfirmPage = function showConfirmPage() {
        const settings = {
          element: '#message-page',
          target: '#form',
          title: 'Solicitação enviada',
          description: 'Acesse esta página no futuro para acompanhar o status da solicitação',
          header: 'O que você deseja fazer?',
          transitionEffect: true,
          messageType: 'success',
          links: [{
            description: 'Acompanhar Status',
            bind: 'data-close-message-page',
            href: `#!/${vm.Param.guid}`
          }, {
            description: 'Encerrar',
            href: 'https://www.brother.com.br/'
          }],
          actionClose: {
            label: 'Voltar',
            bind: 'data-close-message-page',
            href: `#!/${vm.Param.guid}`
          }
        };

        const messagePage = FLUIGC.messagePage(settings);
        messagePage.show();

        $(document).on('click', '[data-close-message-page]', () => {
          messagePage.hide();
        });
      };

      vm.setRegras = function setRegras() {
        if (!vm.Formulario) {
          return;
        }

        switch (vm.Formulario.currentStepPortal) {
          case 0:
            vm.regras = {
              enableEnvioEvidencias: false,
              showEnvioEvidencias: false,
              enableND: false,
              showND: false,
              enablePagamento: false,
              showPagamento: false
            };
            break;
          case 1:
            vm.regras = {
              enableEnvioEvidencias: true,
              showEnvioEvidencias: true,
              enableND: false,
              showND: false,
              enablePagamento: false,
              showPagamento: false
            };
            break;
          case 2:
            vm.regras = {
              enableEnvioEvidencias: false,
              showEnvioEvidencias: true,
              enableND: false,
              showND: false,
              enablePagamento: false,
              showPagamento: false
            };
            break;
          case 3:
            vm.regras = {
              enableEnvioEvidencias: false,
              showEnvioEvidencias: true,
              enableND: true,
              showND: true,
              enablePagamento: false,
              showPagamento: false
            };
            break;
          case 4:
            vm.regras = {
              enableEnvioEvidencias: false,
              showEnvioEvidencias: true,
              enableND: false,
              showND: true,
              enablePagamento: false,
              showPagamento: false
            };
            break;
          case 5:
            vm.regras = {
              enableEnvioEvidencias: false,
              showEnvioEvidencias: true,
              enableND: false,
              showND: true,
              enablePagamento: false,
              showPagamento: true
            };
            break;
          default:
            break;
        }

        if (vm.Formulario.status === 'CANCELADA') {
          vm.regras.enableEnvioEvidencias = false;
          vm.regras.enableND = false;
          vm.regras.enablePagamento = false;
        }

        vm.Params.edit = vm.regras.enableEnvioEvidencias || vm.regras.enableND || vm.regras.enablePagamento;
      };

      vm.salvar = async (completeTask) => {
        console.log("🚀 ~ file: documentacao.js:164 ~ vm.sal ~ completeTask:", completeTask)

        if (vm.saving) {
          if (completeTask) {
            vm.completeTask = true;
            vm.loader.show();
          }

          if (vm.alterado || vm.completeTask) {
            vm.pendingSaving = true;
          }
          return;
        }

        console.log("🚀 ~ salvando... ")

        vm.pendingSaving = false;
        // if (!vm.alterado || vm.saving) {
        //   if (completeTask) {
        //     vm.completeTask = true;
        //     vm.loader.show();
        //     setTimeout(() => {
        //       vm.salvar(completeTask);
        //     }, 5000);
        //   }
        //   return;
        // }
        
        if (completeTask) vm.loader.show();

        vm.completeTask = completeTask || false;
        vm.saving = true;

        console.log("🚀 ~ file: documentacao.js:183 ~ vm.sal ~ vm.Formulario:", vm.Formulario)
        try {
          const response = await $http.post('/brother-api/v1/marketing/update', vm.Formulario, { headers: { guid: vm.Param.guid, completeTask: vm.completeTask } })

          console.log("🚀 ~ vm.sal ~ response:", response)

          vm.setRegras();
          
          if (completeTask) {
            vm.showConfirmPage();
          }
        } catch (error) {
          console.log("🚀 ~ file: documentacao.js:190 ~ vm.sal ~ error:", error)

          // showError('Oops...', 'Ocorreu algum erro ao salvar os dados. Tente novamente', error.data);

        } finally {
          
          console.log("🚀 ~ file: documentacao.js:194 ~ vm.finally ~ vm.saving:", vm.saving)
          console.log("🚀 ~ file: documentacao.js:194 ~ vm.finally ~ vm.vm.pendingSaving:", vm.pendingSaving)
          
            vm.completeTask = false;
            vm.loader.hide();
            vm.saving = false;

            if (vm.pendingSaving) {
              vm.salvar(completeTask);
            }
        }
      
        $scope.$apply();


      };

      vm.selectFiles = async function selectFiles(files, item) {
        const tablename = vm.regras.enableND ? 'nd' : 'evidencias';
        vm.uploading = true;
        // vm.loader.show();

        try {
          const div = tablename === 'nd' ? $("#arquivosND") : $("#arquivosEvidencia")
          const position = div.offset().top + div.outerHeight()
          scrollTo({top: position, behavior: "smooth"})
          const uploadedFiles = await Promise.all(files.map(async (file) => {
            let newFile = {
              nome: file.name,
              tipo: file.type,
              item: item || {},
              progressPercentage: 1
            }
            
            vm.Formulario[tablename].push(newFile);
            await vm.upload(file, newFile);
            // file = uploadedFile;
            return newFile
          }))


        } catch (error) {
          showError('Oops...', 'Ocorreram erros ao enviar os arquivos. Verifique os arquivos com erro e tente enviar novamente', error.data);
        } finally {
          vm.uploading = false;
          // vm.loader.hide();
          if (!vm.Formulario[tablename]) {
            vm.Formulario[tablename] = [];
          }        
        }
        
        $scope.$apply();
        
        console.log("🚀 ~ selectFiles ~ finish upload:", vm.uploading)

        vm.salvar()
      };

      vm.upload = async function upload(file, data) {
        return await new Promise(async (resolve, reject) => {
          Upload.upload({
            url: '/brother-api/v1/file/upload',
            data: {
              file,
              parentDocumentId: vm.Formulario.folderAttach
            }
          }).then(async (resp) => {
            // data = resp.data;
            data.documentid = resp.data.documentid;
            data.numero = '';
            data.descricao = '';
            data.nome = resp.data.filename;
            data.filename = resp.data.filename;
            data.url = resp.data.url;
            data.description = resp.data.description;
            data.version = resp.data.version;
            data.uploaded = true;
            data.removed = false;

            resolve(resp.data);
          
            // $log.log(file);
  
            // vm.alterado = true;
            // await vm.salvar();
  
          }, (error) => {
            // showError('Ocorreram erros ao enviar o arquivo', error);
            reject(error);
          }, (evt) => {
            data.progressPercentage = parseInt((100.0 * evt.loaded) / evt.total, 10);
            console.log("🚀 ~ returnnewPromise ~ data.progressPercentage:", data.progressPercentage)
          });
        })
        
      };

      vm.removeArquivo = function removeArquivo(arquivo) {
        FLUIGC.message.confirm({ message: 'Deseja excluir esse arquivo?', title: 'Excluir arquivo' }, (result) => {
          if (result) {
            arquivo.removed = true;
            vm.alterado = true;
            $scope.$apply();
            vm.salvar();
          }
        });
      };

      vm.enviar = function enviar() {
        const Errors = [];
        // console.log("🚀 ~ file: documentacao.js:279 ~ enviar ~ Error:", Error)

        if (vm.regras.enableEnvioEvidencias) {
          vm.Formulario.evidencias.forEach((arquivo) => {
            if (!arquivo.descricao && !arquivo.removed) {
              Errors.push(`<li>Informe a descrição no arquivo ${arquivo.nome}</li>`);
            }
            // console.log("🚀 ~ file: documentacao.js:286 ~ vm.Formulario.evidencias.forEach ~ Error:", Error)
          });

          vm.ItensEvidencia.forEach((item) => {
            if (vm.Formulario[item.tablename][item.index].valEvidencia === 0) {
              Errors.push(`<li>Informe o valor no item ${item.descricao}</li>`);
            }
            // console.log("🚀 ~ file: documentacao.js:293 ~ vm.ItensEvidencia.forEach ~ Error:", Error)
            // if (vm.Formulario[item.tablename][item.index].qtdEvidencia === 0) {
            //   Errors.push(`<li>Informe a quantidade no item ${item.descricao}</li>`);
            // }
            // console.log("🚀 ~ file: documentacao.js:297 ~ vm.ItensEvidencia.forEach ~ Error:", Error)

            vm.calculaTotalItemEvidencia(item);
          });
        }

        if (vm.regras.enableND) {
          vm.Formulario.nd.forEach((arquivo) => {
            if (!arquivo.removed) {
              if (!arquivo.documentid || !arquivo.url) {
                Errors.push(`<li>O envio do arquivo ${arquivo.nome} não foi concluído corretamente. Exclua-o ou envie novamente</li>`);
              }
              if (!arquivo.descricao) {
                Errors.push(`<li>Informe a descrição no arquivo ${arquivo.nome}</li>`);
              }
              // console.log("🚀 ~ file: documentacao.js:309 ~ vm.Formulario.nd.forEach ~ Error:", Error)
              if (!arquivo.numero) {
                Errors.push(`<li>Informe o número da ND no arquivo ${arquivo.nome}</li>`);
              }
              // console.log("🚀 ~ file: documentacao.js:313 ~ vm.Formulario.nd.forEach ~ Error:", Error)
              if (!isNaN(Number(arquivo.numero)) && Number(arquivo.numero) === 0) {
                Errors.push(`<li>Número da ND no arquivo ${arquivo.nome} deve ser diferente de 0</li>`);
              }
              // console.log("🚀 ~ file: documentacao.js:317 ~ vm.Formulario.nd.forEach ~ Error:", Error)
            }
          });
        }

        if (Errors.length > 0) {
          console.log("🚀 ~ file: documentacao.js:323 ~ enviar ~ Error:", Errors)
          // FLUIGC.toast({
          //   title: 'Oops.. ocorreram erros ao enviar seus dados:',
          //   message: `<ul>${Errors.join('')}</ul>`,
          //   type: 'danger',
          //   timeout: 5000

          // });
          showError('Oops.. ', `Ocorreram os erros abaixo ao enviar a solicitação. Por favor verifique: <br> <ul>${Errors.join('')}</ul>`);
          return;
        }

        let message;
        if (vm.regras.enableEnvioEvidencias) {
          message = vm.Formulario.evidencias.filter(s => !s.removed).length > 0 ? 'Confirma o envio dos arquivos?' : 'ATENÇÃO. Não foram selecionados os arquivos de evidência. Confirma o envio da solicitação sem arquivos?';
        }

        if (vm.regras.enableND) {
          message = vm.Formulario.nd.filter(s => !s.removed).length > 0 ? 'Confirma o envio dos arquivos?' : 'ATENÇÃO. Não foram selecionados os arquivos de ND. Confirma o envio da solicitação sem arquivos?';
        }

        FLUIGC.message.confirm({ message, title: 'Enviar documentação' }, (result) => {
          if (result) {
            vm.Formulario.currentStepPortal += 1;

            if (vm.regras.enableEnvioEvidencias) {
              vm.Formulario.envioEvidenciasConcluido = true;
            }

            if (vm.regras.enableND) {
              vm.Formulario.envioNDConcluido = true;
            }

            vm.alterado = true;
            vm.setRegras();
            $scope.$apply();
            vm.salvar(true);
          }
        });
      }

      vm.getItens = () => {
        vm.ItensEvidencia = [];

        if (vm.Formulario.tipoSellout !== 'target') {
          vm.Formulario.itensSellout.forEach((it, index) => {
            if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.rebateUnit;
            vm.ItensEvidencia.push({ tablename: 'itensSellout', index, descricao: `${it.item.codigo} - ${it.item.descricao}`, valorTotal: it.rebateTotal });
          });

          vm.Formulario.itensSellinIt.forEach((it, index) => {
            if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.rebateUnit;
            vm.ItensEvidencia.push({ tablename: 'itensSellinIt', index, descricao: `${it.item.codigo} - ${it.item.descricao}`, valorTotal: it.rebateTotal });
          });
          vm.Formulario.itensSpiffIt.forEach((it, index) => {
            if (!it.valEvidencia || it.valEvidencia === 0) it.valEvidencia = it.valorUnit;
            vm.ItensEvidencia.push({ tablename: 'itensSpiffIt', index, descricao: `${it.item.codigo} - ${it.item.descricao}`, valorTotal: it.valorTotal });
          });

          console.log(vm.Formulario.itensSpiffIt, vm.ItensEvidencia);

          vm.ItensEvidencia.forEach((item) => {
            // vm.Formulario[item.tablename][item.index].valEvidencia = vm.Formulario[item.tablename][item.index].valorUnit || vm.Formulario[item.tablename][item.index].rebateUnit || 0;
            // vm.Formulario[item.tablename][item.index].qtdEvidencia = vm.Formulario[item.tablename][item.index].qtde || 0;

            vm.calculaTotalItemEvidencia(item);
          });
        };
      }

      vm.calculaTotalItemEvidencia = (item) => {
        // vm.Formulario[item.tablename][item.index].valEvidencia = vm.Formulario[item.tablename][item.index].valEvidencia || 0;
        // vm.Formulario[item.tablename][item.index].qtdEvidencia = vm.Formulario[item.tablename][item.index].qtdEvidencia || 0;

        vm.Formulario[item.tablename][item.index].totEvidencia = vm.Formulario[item.tablename][item.index].valEvidencia * vm.Formulario[item.tablename][item.index].qtdEvidencia;
        vm.Formulario.valorResultado = 0;
        vm.ItensEvidencia.forEach((itemEv) => {
          vm.Formulario.valorResultado += vm.Formulario[itemEv.tablename][itemEv.index].totEvidencia || 0;
        });

        vm.alterado = true;
      };

        vm.inicia();
      }
  ])


  const showError = (title, message, details) => {
    FLUIGC.message.error({
      title: title,
      message,
      details
  }, function(el, ev) {
      //Callback action executed by the user...
   
      //el: Element (button) clicked...
      //ev: Event triggered...
   
      // this.someFunc();
  });
  }