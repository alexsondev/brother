const { src, dest } = require('gulp'),
  params = require('./params')(),
  tap = require('gulp-tap');

let folder = '';
const workflow = () => src([
  `${params.srcdir}/workflow/diagrams/${params.workflow}.*`,
  `${params.srcdir}/workflow/scripts/${params.workflow}.*`
])
  .pipe(tap((file) => {
    folder = file.dirname.split("/")
    folder = folder[folder.length - 1]
    // console.log(folder)
    src(file.path)
      .pipe(dest(`./${params.builddir}/workflow/${folder}`));
  }))


exports.workflow = workflow