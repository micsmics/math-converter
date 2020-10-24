const fs = require('fs');
const mjAPI = require("mathjax-node-svg2png");

mjAPI.config({
    MathJax: {}
  });
mjAPI.start();

function start(expressao, filepath) {
    mjAPI.typeset({
        math: expressao,
        format: "TeX",
        svg: false,
        png: true,
        scale: 50,
        svg: true
      }, function (data) {
        if (!data.errors) {
            const datapng = data.png.substring(data.png.indexOf(",") + 1);

            if (filepath != null) {
              if (!fs.existsSync(filepath)) {
                fs.mkdirSync(filepath);
              }
            }

            fs.writeFile(((filepath != null) ? (filepath + '/') : ('')) + 'imagem-da-expressao.png', datapng, 'base64', function (err) {
                if (err) return console.log(err);
            });
            fs.writeFile(((filepath != null) ? (filepath + '/') : ('')) + 'imagem-da-expressao.svg', data.svg, function (err) {
              if (err) return console.log(err);
          });
        }
      });
}

if (process.argv.length >= 3)
    start("---expressao---", process.argv[2]);
else
    start("\\begin{bmatrix}1 & 2 & 3\\\\4 & 5 & 6\\\\7 & 8 & 9\\\\\\end{bmatrix}");