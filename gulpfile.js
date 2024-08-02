
const { watch, series } = require('gulp');

const params = require('./tasks/params')();

const datasets = require('./tasks/datasets').datasets;
const forms = require('./tasks/forms').forms;
// const java = require('./tasks/java').java;
const widget = require('./tasks/wcm').widget;
const workflow = require('./tasks/workflow').workflow;
console.log(params)
params.watch && watch([`./src/datasets/**/*`], {}, series(datasets));
params.watch && watch([`./src/forms/**/*`], {}, series(forms));
// params.watch && watch([`./src/java/**/*`], {}, series(forms));
// params.watch && watch([`./src/progress/**/*`], {}, series(progress));
// params.watch && watch([`./src/templates/**/*`], {}, series(templates));
params.watch && watch([`./src/wcm/**/*`], {}, series(widget));
params.watch && watch([`./src/workflow/**/*`], {}, series(workflow));

exports.datasets = datasets
exports.forms = forms
exports.widget = widget
exports.workflow = workflow

// const watchDataset = () => {
//   watch([`./src/datasets/**/*`], {}, series(datasets));
// }

exports.default = series(
  datasets,
  forms,
  // java,
  // progress,
  // templates,
  widget,
  workflow
);
