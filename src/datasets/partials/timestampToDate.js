function timestampToDate(UNIX_timestamp) {
  // log.info('timestampToDate.. UNIX_timestamp : ' + UNIX_timestamp);
  var a = new Date(Number(UNIX_timestamp));

  // log.info('timestampToDate.. date : ' + a.toString());

  var months = ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'];
  var year = a.getFullYear();
  var month = months[a.getMonth()];
  var date = a.getDate();
  var hour = a.getHours();
  var min = a.getMinutes();
  var sec = a.getSeconds();
  var time = date + '/' + month + '/' + year + ' ' + hour + ':' + min + ':' + sec;

  return time;

}