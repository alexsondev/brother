function createCardIndex(documentId, data) {
  const apiCard = fluigAPI.getCardAPIService()

  /**	create​(Integer documentId, List<CardFieldVO> cardFieldVOs)	
  Cria um registro de Formulário */

  const cardFieldVOs = new java.util.ArrayList()

  for (var field in data) {
    const cardFieldVO = new com.fluig.sdk.api.cardindex.CardFieldVO()
    cardFieldVO.setFieldId(field)
    cardFieldVO.setValue(String(data[field]))
    cardFieldVOs.add(cardFieldVO)
  }

  apiCard.create(documentId, cardFieldVOs)
}