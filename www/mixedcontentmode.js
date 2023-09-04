/*
 * MIT License
 *
 * Copyright 2023 Carlos Bello A.K.A: CarlosBet
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/* global cordova */

const exec = require('cordova/exec');

const MixedContentMode = {
    NeverAllow: function () {
        // Default mode in Cordova.
        // The WebView will not allow a secure origin to load content from an 
        // insecure origin.
        exec(null, null, 'MixedContentMode', 'neverallow', []);
    },
    CompatibilityMode: function () {
        // The WebView will attempt to be compatible with the approach of a 
        // modern web browser with regard to mixed content.
        exec(null, null, 'MixedContentMode', 'compatibilitymode', []);
    },    
    AlwaysAllow: function () {
        // The WebView will allow a secure origin to load content from any 
        // other origin, even if that origin is insecure.
        exec(null, null, 'MixedContentMode', 'alwaysallow', []);
    }
};

module.exports = MixedContentMode;
