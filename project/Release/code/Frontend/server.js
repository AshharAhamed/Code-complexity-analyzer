const Bundler = require('parcel-bundler');
const express = require('express');
const bundler = new Bundler('./front-end/index.html', {});
const PORT = 3000 ;
const app = express();
app.use(express.json());

// Utilizing the resources
app.use(bundler.middleware());
app.use(express.static('./dist'));

// Utilizing the middleware
app.get('/', function (req, res) {
    res.sendFile('./dist/index.html');
});

// // Set access control CORS policy
// app.use(function (req, res, next) {
//     res.setHeader('Access-Control-Allow-Origin', '*');
//     next();
// });

// Opening the port and listening
app.listen(PORT, err => {
    if (err) {
        console.error(err);
        return;
    }
    console.log(`Code Complexity Analyser is running on port ${PORT}`);
});

