const { src, dest } = require('gulp'),
  params = require('./params')(),
  tap = require('gulp-tap'),
  rename = require('gulp-rename'),
  babel = require('gulp-babel'),
  concat = require('gulp-concat'),
  beautify = require('gulp-beautify'),
  uglify = require('gulp-uglify'),
  sourcemaps = require('gulp-sourcemaps'),
  gulpInjectPartials = require('gulp-inject-partials');

const forms = () => src([`${params.srcdir}/forms/${params.form}`])
  .pipe(tap((dir) => {
    if (dir.stat.isDirectory() && dir.relative !== 'partials') {
      src(`${dir.path}/index.html`)
        .pipe(gulpInjectPartials({
          removeTags: true
        }))
        // .pipe(beautify())
        .pipe(rename(`${dir.relative}.html`))
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}`));

      src([`${dir.path}/controller.js`])
        .pipe(sourcemaps.init())
        .pipe(concat('custom.js'))
        .pipe(babel({
          presets: ['@babel/env']
        }))
        .pipe(beautify())
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}`))
        .pipe(rename('custom.min.js'))
        .pipe(uglify())

        .pipe(sourcemaps.write('.', { includeContent: true, sourceRoot: `/src/forms/${dir.relative}/js` }))
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}`));

      src(`${dir.path}/events/**`)
        .pipe(babel({
          presets: ['@babel/env']
        }))
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}/events`));

      src(`${params.srcdir}/forms/partials/events/**`)
        .pipe(babel({
          presets: ['@babel/env']
        }))
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}/events`));

      src(`${dir.path}/resources/**`)
        .pipe(dest(`./${params.builddir}/forms/${dir.relative}`));
    }
  }));

exports.forms = forms