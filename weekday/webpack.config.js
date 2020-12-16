let path = require('path');
const VueLoaderPlugin = require('vue-loader/lib/plugin')

module.exports = {
    entry: './src/main/resources/static/js/page/login.js',
    devtool: 'sourcemaps',
    cache: true,
    mode: 'development',
    output: {
        path: __dirname,
        filename: './src/main/resources/static/built/bundle.js'
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                loader: 'babel-loader'
            },
            // {
            //     test:/\.vue$/,
            //     loader:'vue-loader'
            // },

        ]
    },
    plugins: [new VueLoaderPlugin()]

};