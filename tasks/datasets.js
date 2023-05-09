
const { src, dest } = require('gulp'),
  tap = require('gulp-tap'),
  babel = require('gulp-babel'),
  concat = require('gulp-concat'),
  uglify = require('gulp-uglify'),
  gulpif = require('gulp-if'),
  params = require('./params')(),
  util = require('gulp-util'),
  gulpInjectPartials = require('gulp-inject-partials');

const datasets = () =>
  src(`${params.srcdir}/datasets/${params.dataset}`)
    .pipe(tap(file =>
      src(file.path)
        .pipe(gulpInjectPartials({
          start: '/*$$ {{path}}',
          end: '$$*/',
          removeTags: true
        }))
        .pipe(babel({
          presets: ['@babel/env']
        }))
        .pipe(concat(file.relative))
        .pipe(gulpif(params.uglify, uglify(params.uglifyOptions)
          .on('error', util.log)))
        .pipe(dest(`${params.builddir}/datasets`))
    ));

exports.datasets = datasets