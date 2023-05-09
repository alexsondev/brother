/* eslint-disable angular/json-functions */
/* eslint-disable no-empty */
const { src, dest } = require('gulp'),
  tap = require('gulp-tap'),
  babel = require('gulp-babel'),
  concat = require('gulp-concat'),
  uglify = require('gulp-uglify'),
  gulpInjectPartials = require('gulp-inject-partials'),
  gulpif = require('gulp-if'),
  fs = require('fs'),
  params = require('./params')(),
  util = require('gulp-util'),
  sass = require('gulp-sass')(require('sass')),
  htmlmin = require('gulp-htmlmin'),
  embedTemplates = require('gulp-angular-embed-templates');

const widget = () => src(`./src/wcm/widget/${params.widget}`)
  .pipe(tap((file) => {
    if (file.stat.isDirectory() && file.relative !== 'partials') {
      
      let vendor = [];
      try {
        vendor = JSON.parse(fs.readFileSync(`${file.path}/vendor.json`));
      } catch (err) { }

      const widgetDir = `./${params.builddir}/wcm/widget/${file.relative}`;

      html(`${file.path}/**.html`, `${widgetDir}/src/main/resources`);
      image(`${file.path}/images/**/*`, widgetDir);
      js(`${file.path}/js/**/*.js`, `${widgetDir}/js`, 'script.min.js', true);
      statics(`${file.path}/resources/**.*`, `${widgetDir}/resources`);
      sassTask(`${file.path}/sass/*.scss`, `${widgetDir}/css`);

      if (vendor.fonts) {
        statics(vendor.fonts, `${widgetDir}/fonts`);
      }

      if (vendor.js) {
        js(vendor.js, `${widgetDir}/js`, 'vendor.min.js');
      }

      if (vendor.sass) {
        sassTask(vendor.sass, `${widgetDir}/css`);
      }

      war(file.path, widgetDir);
    }
  }));


function sassTask(source, target) {
  console.log(source, target)
  src(source)
    .pipe(sass()
      .on('error', sass.logError))
    .pipe(gulpif(params.uglify, uglify({
      maxLineLen: 80,
      uglyComments: true
    })))
    .pipe(dest(target));
}

function statics(source, target) {
  src(source)
    .pipe(dest(target));
}

function js(source, target, filename, tpl) {
  src(source)
    .pipe(gulpif(tpl, embedTemplates()))
    .pipe(babel({
      presets: ['@babel/env']
    }))
    .pipe(concat(filename))
    .pipe(gulpif(params.uglify, uglify(params.uglifyOptions)
      .on('error', util.log)))
    .pipe(dest(target));
}

function html(source, target) {
  // ****** Busca os arquivos html da raiz
  src(source)
    //   injeta trechos parciais
    .pipe(gulpInjectPartials({
      removeTags: true
    }))
    // se produção, compacta o html
    .pipe(gulpif(params.htmlmin, htmlmin({
      collapseWhitespace: true
    })))
    // envia para o diretório de projeto do fluig
    .pipe(dest(target));
}

function image(source, target) {
  src(source)
    .pipe(dest(`${target}/images`));
}

function war(source, target) {
  src(`${source}/application.info`)
    .pipe(dest(`${target}/src/main/resources`));

  src(`${source}/jboss-web.xml`)
    .pipe(dest(`${target}/src/main/webapp/WEB-INF`));

  src(`src/wcm/widget/web.xml`)
    .pipe(dest(`${target}/src/main/webapp/WEB-INF`));
}


exports.widget = widget