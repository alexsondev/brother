/* eslint-disable angular/json-functions */
const fs = require('fs');
const yargs = require('yargs/yargs')
  // .argv;
const { hideBin } = require('yargs/helpers')

const argv = yargs(hideBin(process.argv)).argv

module.exports = function params() {
  const nodePackage = JSON.parse(fs.readFileSync('package.json'));
  console.log("🚀 ~ params ~ argv:", argv)

  const p = {};
  p.project = nodePackage.name;
  p.cliServer = nodePackage.cliServer;
  p.srcdir = argv.srcdir || './src';
  
  p.workflow = argv.workflow || '*';
  p.dataset = argv.dataset || "*.js";
  p.widget = argv.widget || '*';
  p.form = argv.form || "*";
  p.builddir = argv.builddir || "deploy";

  p.prod = argv.prod;
  p.uglify = argv.uglify;
  p.htmlmin = argv.htmlmin;
  p.watch = true;
  p.jsmap = argv.jsmap;
  p.export = argv.export;
  p.forms = [];
  p.uglifyOptions = {
    mangle: true,
    compress: {
      drop_console: true
    }
  };

  if (p.prod) {
    p.uglify = true;
    p.htmlmin = true;
    p.jsmap = false;
  }
  p.babelOptions = {
    presets: ['es2015'],
    // ignore: [
    //   './node_modules/**/*.js',
    //   '../AngularFluig/**/*.js',
    // ],
    plugins: ['transform-remove-strict-mode']
  };

  return p;
};
