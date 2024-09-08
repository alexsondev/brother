
const { watch, series } = require('gulp');

const params = require('./tasks/params');

const datasets = require('./tasks/datasets').datasets;
const forms = require('./tasks/forms').forms;
// const java = require('./tasks/java').java;
const widget = require('./tasks/wcm').widget;
const workflow = require('./tasks/workflow').workflow;

watch([`./src/datasets/**/*`], {}, series(datasets));
watch([`./src/forms/**/*`], {}, series(forms));
// watch([`./src/java/**/*`], {}, series(forms));
// watch([`./src/progress/**/*`], {}, series(progress));
// watch([`./src/templates/**/*`], {}, series(templates));
watch([`./src/wcm/**/*`], {}, series(widget));
watch([`./src/workflow/**/*`], {}, series(workflow));

exports.datasets = datasets
exports.forms = forms
exports.widget = widget
exports.workflow = workflow

exports.default = series(
  datasets,
  forms,
  // java,
  // progress,
  // templates,
  widget,
  workflow
);
