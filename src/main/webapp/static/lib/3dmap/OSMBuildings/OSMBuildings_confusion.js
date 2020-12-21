/**
 * Created by Administrator on 2016/10/9 0009.
 */
function setState(a, b) {
    b.lat = void 0 !== b.lat ? parseFloat(b.lat) : DEFAULT_LAT,
        b.lon = void 0 !== b.lon ? parseFloat(b.lon) : DEFAULT_LON,
        b.zoom = b.zoom ? Math.max(Math.min(parseInt(b.zoom, 10), MAX_ZOOM), 0) : DEFAULT_ZOOM,
        b.rotation = void 0 !== b.rotation ? parseFloat(b.rotation) : 0,
        b.tilt = void 0 !== b.tilt ? parseFloat(b.tilt) : 30,
        a.setPosition({
            latitude: b.lat,
            longitude: b.lon
        }),
        a.setZoom(b.zoom),
        a.setRotation(b.rotation),
        a.setTilt(b.tilt)
}
function getState(a) {
    var b = {},
        c = a.getPosition();
    b.lat = parseFloat(c.latitude.toFixed(5)),
        b.lon = parseFloat(c.longitude.toFixed(5)),
        b.zoom = parseFloat(a.getZoom().toFixed(1));
    var d = Math.round(a.getRotation());
    d && (b.rotation = d);
    var e = Math.round(a.getTilt());
    return e && (b.tilt = e),
        b
}
function geoDistance(a, b, c, d) {
    var e = Math.PI * a / 180,
        f = Math.PI * c / 180,
        g = b - d,
        h = Math.PI * g / 180,
        i = Math.sin(e) * Math.sin(f) + Math.cos(e) * Math.cos(f) * Math.cos(h);
    return i = Math.acos(i),
        i = 180 * i / Math.PI,
        i = 60 * i * 1.1515,
        i = 1.609344 * i
}
function updateLocationInfo(a, b) {
    if (b) {
        var c = b.annotations.timezone.offset_sec,
            d = new Date,
            e = 60 * -d.getTimezoneOffset(),
            f = d.getTime();
        d.setTime(f + 1e3 * (c - e)),
            a.setDate(d),
            POSITION.name = b.components.city ? b.components.city + " (" + b.components.country_code.toUpperCase() + ")": "",
            POSITION.position = {
                latitude: b.geometry.lat,
                longitude: b.geometry.lng
            },
            document.title = (POSITION.name ? POSITION.name + " ? ": "") + "OSM Buildings"
    }
} !
    function(a, b, c) {
        var d = a.L,
            e = {};
        e.version = "0.7",
            "object" == typeof module && "object" == typeof module.exports ? module.exports = e: "function" == typeof define && define.amd && define(e),
            e.noConflict = function() {
                return a.L = d,
                    this
            },
            a.L = e,
            e.Util = {
                extend: function(a) {
                    var b, c, d, e, f = Array.prototype.slice.call(arguments, 1);
                    for (c = 0, d = f.length; d > c; c++) {
                        e = f[c] || {};
                        for (b in e) e.hasOwnProperty(b) && (a[b] = e[b])
                    }
                    return a
                },
                bind: function(a, b) {
                    var c = arguments.length > 2 ? Array.prototype.slice.call(arguments, 2) : null;
                    return function() {
                        return a.apply(b, c || arguments)
                    }
                },
                stamp: function() {
                    var a = 0,
                        b = "_leaflet_id";
                    return function(c) {
                        return c[b] = c[b] || ++a,
                            c[b]
                    }
                } (),
                invokeEach: function(a, b, c) {
                    var d, e;
                    if ("object" == typeof a) {
                        e = Array.prototype.slice.call(arguments, 3);
                        for (d in a) b.apply(c, [d, a[d]].concat(e));
                        return ! 0
                    }
                    return ! 1
                },
                limitExecByInterval: function(a, b, c) {
                    var d, e;
                    return function f() {
                        var g = arguments;
                        return d ? void(e = !0) : (d = !0, setTimeout(function() {
                                d = !1,
                                e && (f.apply(c, g), e = !1)
                            },
                            b), void a.apply(c, g))
                    }
                },
                falseFn: function() {
                    return ! 1
                },
                formatNum: function(a, b) {
                    var c = Math.pow(10, b || 5);
                    return Math.round(a * c) / c
                },
                trim: function(a) {
                    return a.trim ? a.trim() : a.replace(/^\s+|\s+$/g, "")
                },
                splitWords: function(a) {
                    return e.Util.trim(a).split(/\s+/)
                },
                setOptions: function(a, b) {
                    return a.options = e.extend({},
                        a.options, b),
                        a.options
                },
                getParamString: function(a, b, c) {
                    var d = [];
                    for (var e in a) d.push(encodeURIComponent(c ? e.toUpperCase() : e) + "=" + encodeURIComponent(a[e]));
                    return (b && -1 !== b.indexOf("?") ? "&": "?") + d.join("&")
                },
                compileTemplate: function(a, b) {
                    return a = a.replace(/"/g, '\\"'),
                        a = a.replace(/\{ *([\w_]+) *\}/g,
                            function(a, c) {
                                return '" + o["' + c + '"]' + ("function" == typeof b[c] ? "(o)": "") + ' + "'
                            }),
                        new Function("o", 'return "' + a + '";')
                },
                template: function(a, b) {
                    var c = e.Util._templateCache = e.Util._templateCache || {};
                    return c[a] = c[a] || e.Util.compileTemplate(a, b),
                        c[a](b)
                },
                isArray: Array.isArray ||
                function(a) {
                    return "[object Array]" === Object.prototype.toString.call(a)
                },
                emptyImageUrl: "data:image/gif;base64,R0lGODlhAQABAAD/ACwAAAAAAQABAAACADs="
            },
            function() {
                function b(b) {
                    var c, d, e = ["webkit", "moz", "o", "ms"];
                    for (c = 0; c < e.length && !d; c++) d = a[e[c] + b];
                    return d
                }
                function c(b) {
                    var c = +new Date,
                        e = Math.max(0, 16 - (c - d));
                    return d = c + e,
                        a.setTimeout(b, e)
                }
                var d = 0,
                    f = a.requestAnimationFrame || b("RequestAnimationFrame") || c,
                    g = a.cancelAnimationFrame || b("CancelAnimationFrame") || b("CancelRequestAnimationFrame") ||
                        function(b) {
                            a.clearTimeout(b)
                        };
                e.Util.requestAnimFrame = function(b, d, g, h) {
                    return b = e.bind(b, d),
                        g && f === c ? void b() : f.call(a, b, h)
                },
                    e.Util.cancelAnimFrame = function(b) {
                        b && g.call(a, b)
                    }
            } (),
            e.extend = e.Util.extend,
            e.bind = e.Util.bind,
            e.stamp = e.Util.stamp,
            e.setOptions = e.Util.setOptions,
            e.Class = function() {},
            e.Class.extend = function(a) {
                var b = function() {
                        this.initialize && this.initialize.apply(this, arguments),
                        this._initHooks && this.callInitHooks()
                    },
                    c = function() {};
                c.prototype = this.prototype;
                var d = new c;
                d.constructor = b,
                    b.prototype = d;
                for (var f in this) this.hasOwnProperty(f) && "prototype" !== f && (b[f] = this[f]);
                a.statics && (e.extend(b, a.statics), delete a.statics),
                a.includes && (e.Util.extend.apply(null, [d].concat(a.includes)), delete a.includes),
                a.options && d.options && (a.options = e.extend({},
                    d.options, a.options)),
                    e.extend(d, a),
                    d._initHooks = [];
                var g = this;
                return b.__super__ = g.prototype,
                    d.callInitHooks = function() {
                        if (!this._initHooksCalled) {
                            g.prototype.callInitHooks && g.prototype.callInitHooks.call(this),
                                this._initHooksCalled = !0;
                            for (var a = 0,
                                     b = d._initHooks.length; b > a; a++) d._initHooks[a].call(this)
                        }
                    },
                    b
            },
            e.Class.include = function(a) {
                e.extend(this.prototype, a)
            },
            e.Class.mergeOptions = function(a) {
                e.extend(this.prototype.options, a)
            },
            e.Class.addInitHook = function(a) {
                var b = Array.prototype.slice.call(arguments, 1),
                    c = "function" == typeof a ? a: function() {
                        this[a].apply(this, b)
                    };
                this.prototype._initHooks = this.prototype._initHooks || [],
                    this.prototype._initHooks.push(c)
            };
        var f = "_leaflet_events";
        e.Mixin = {},
            e.Mixin.Events = {
                addEventListener: function(a, b, c) {
                    if (e.Util.invokeEach(a, this.addEventListener, this, b, c)) return this;
                    var d, g, h, i, j, k, l, m = this[f] = this[f] || {},
                        n = c && c !== this && e.stamp(c);
                    for (a = e.Util.splitWords(a), d = 0, g = a.length; g > d; d++) h = {
                        action: b,
                        context: c || this
                    },
                        i = a[d],
                        n ? (j = i + "_idx", k = j + "_len", l = m[j] = m[j] || {},
                        l[n] || (l[n] = [], m[k] = (m[k] || 0) + 1), l[n].push(h)) : (m[i] = m[i] || [], m[i].push(h));
                    return this
                },
                hasEventListeners: function(a) {
                    var b = this[f];
                    return !! b && (a in b && b[a].length > 0 || a + "_idx" in b && b[a + "_idx_len"] > 0)
                },
                removeEventListener: function(a, b, c) {
                    if (!this[f]) return this;
                    if (!a) return this.clearAllEventListeners();
                    if (e.Util.invokeEach(a, this.removeEventListener, this, b, c)) return this;
                    var d, g, h, i, j, k, l, m, n, o = this[f],
                        p = c && c !== this && e.stamp(c);
                    for (a = e.Util.splitWords(a), d = 0, g = a.length; g > d; d++) if (h = a[d], k = h + "_idx", l = k + "_len", m = o[k], b) {
                        if (i = p && m ? m[p] : o[h]) {
                            for (j = i.length - 1; j >= 0; j--) i[j].action !== b || c && i[j].context !== c || (n = i.splice(j, 1), n[0].action = e.Util.falseFn);
                            c && m && 0 === i.length && (delete m[p], o[l]--)
                        }
                    } else delete o[h],
                        delete o[k],
                        delete o[l];
                    return this
                },
                clearAllEventListeners: function() {
                    return delete this[f],
                        this
                },
                fireEvent: function(a, b) {
                    if (!this.hasEventListeners(a)) return this;
                    var c, d, g, h, i, j = e.Util.extend({},
                            b, {
                                type: a,
                                target: this
                            }),
                        k = this[f];
                    if (k[a]) for (c = k[a].slice(), d = 0, g = c.length; g > d; d++) c[d].action.call(c[d].context, j);
                    h = k[a + "_idx"];
                    for (i in h) if (c = h[i].slice()) for (d = 0, g = c.length; g > d; d++) c[d].action.call(c[d].context, j);
                    return this
                },
                addOneTimeEventListener: function(a, b, c) {
                    if (e.Util.invokeEach(a, this.addOneTimeEventListener, this, b, c)) return this;
                    var d = e.bind(function() {
                            this.removeEventListener(a, b, c).removeEventListener(a, d, c)
                        },
                        this);
                    return this.addEventListener(a, b, c).addEventListener(a, d, c)
                }
            },
            e.Mixin.Events.on = e.Mixin.Events.addEventListener,
            e.Mixin.Events.off = e.Mixin.Events.removeEventListener,
            e.Mixin.Events.once = e.Mixin.Events.addOneTimeEventListener,
            e.Mixin.Events.fire = e.Mixin.Events.fireEvent,
            function() {
                var d = "ActiveXObject" in a,
                    f = d && !b.addEventListener,
                    g = navigator.userAgent.toLowerCase(),
                    h = -1 !== g.indexOf("webkit"),
                    i = -1 !== g.indexOf("chrome"),
                    j = -1 !== g.indexOf("phantom"),
                    k = -1 !== g.indexOf("android"),
                    l = -1 !== g.search("android [23]"),
                    m = -1 !== g.indexOf("gecko"),
                    n = typeof orientation != c + "",
                    o = a.navigator && a.navigator.msPointerEnabled && a.navigator.msMaxTouchPoints && !a.PointerEvent,
                    p = a.PointerEvent && a.navigator.pointerEnabled && a.navigator.maxTouchPoints || o,
                    q = "devicePixelRatio" in a && a.devicePixelRatio > 1 || "matchMedia" in a && a.matchMedia("(min-resolution:144dpi)") && a.matchMedia("(min-resolution:144dpi)").matches,
                    r = b.documentElement,
                    s = d && "transition" in r.style,
                    t = "WebKitCSSMatrix" in a && "m11" in new a.WebKitCSSMatrix,
                    u = "MozPerspective" in r.style,
                    v = "OTransition" in r.style,
                    w = !a.L_DISABLE_3D && (s || t || u || v) && !j,
                    x = !a.L_NO_TOUCH && !j &&
                        function() {
                            var a = "ontouchstart";
                            if (p || a in r) return ! 0;
                            var c = b.createElement("div"),
                                d = !1;
                            return c.setAttribute ? (c.setAttribute(a, "return;"), "function" == typeof c[a] && (d = !0), c.removeAttribute(a), c = null, d) : !1
                        } ();
                e.Browser = {
                    ie: d,
                    ielt9: f,
                    webkit: h,
                    gecko: m && !h && !a.opera && !d,
                    android: k,
                    android23: l,
                    chrome: i,
                    ie3d: s,
                    webkit3d: t,
                    gecko3d: u,
                    opera3d: v,
                    any3d: w,
                    mobile: n,
                    mobileWebkit: n && h,
                    mobileWebkit3d: n && t,
                    mobileOpera: n && a.opera,
                    touch: x,
                    msPointer: o,
                    pointer: p,
                    retina: q
                }
            } (),
            e.Point = function(a, b, c) {
                this.x = c ? Math.round(a) : a,
                    this.y = c ? Math.round(b) : b
            },
            e.Point.prototype = {
                clone: function() {
                    return new e.Point(this.x, this.y)
                },
                add: function(a) {
                    return this.clone()._add(e.point(a))
                },
                _add: function(a) {
                    return this.x += a.x,
                        this.y += a.y,
                        this
                },
                subtract: function(a) {
                    return this.clone()._subtract(e.point(a))
                },
                _subtract: function(a) {
                    return this.x -= a.x,
                        this.y -= a.y,
                        this
                },
                divideBy: function(a) {
                    return this.clone()._divideBy(a)
                },
                _divideBy: function(a) {
                    return this.x /= a,
                        this.y /= a,
                        this
                },
                multiplyBy: function(a) {
                    return this.clone()._multiplyBy(a)
                },
                _multiplyBy: function(a) {
                    return this.x *= a,
                        this.y *= a,
                        this
                },
                round: function() {
                    return this.clone()._round()
                },
                _round: function() {
                    return this.x = Math.round(this.x),
                        this.y = Math.round(this.y),
                        this
                },
                floor: function() {
                    return this.clone()._floor()
                },
                _floor: function() {
                    return this.x = Math.floor(this.x),
                        this.y = Math.floor(this.y),
                        this
                },
                distanceTo: function(a) {
                    a = e.point(a);
                    var b = a.x - this.x,
                        c = a.y - this.y;
                    return Math.sqrt(b * b + c * c)
                },
                equals: function(a) {
                    return a = e.point(a),
                    a.x === this.x && a.y === this.y
                },
                contains: function(a) {
                    return a = e.point(a),
                    Math.abs(a.x) <= Math.abs(this.x) && Math.abs(a.y) <= Math.abs(this.y)
                },
                toString: function() {
                    return "Point(" + e.Util.formatNum(this.x) + ", " + e.Util.formatNum(this.y) + ")"
                }
            },
            e.point = function(a, b, d) {
                return a instanceof e.Point ? a: e.Util.isArray(a) ? new e.Point(a[0], a[1]) : a === c || null === a ? a: new e.Point(a, b, d)
            },
            e.Bounds = function(a, b) {
                if (a) for (var c = b ? [a, b] : a, d = 0, e = c.length; e > d; d++) this.extend(c[d])
            },
            e.Bounds.prototype = {
                extend: function(a) {
                    return a = e.point(a),
                        this.min || this.max ? (this.min.x = Math.min(a.x, this.min.x), this.max.x = Math.max(a.x, this.max.x), this.min.y = Math.min(a.y, this.min.y), this.max.y = Math.max(a.y, this.max.y)) : (this.min = a.clone(), this.max = a.clone()),
                        this
                },
                getCenter: function(a) {
                    return new e.Point((this.min.x + this.max.x) / 2, (this.min.y + this.max.y) / 2, a)
                },
                getBottomLeft: function() {
                    return new e.Point(this.min.x, this.max.y)
                },
                getTopRight: function() {
                    return new e.Point(this.max.x, this.min.y)
                },
                getSize: function() {
                    return this.max.subtract(this.min)
                },
                contains: function(a) {
                    var b, c;
                    return a = "number" == typeof a[0] || a instanceof e.Point ? e.point(a) : e.bounds(a),
                        a instanceof e.Bounds ? (b = a.min, c = a.max) : b = c = a,
                    b.x >= this.min.x && c.x <= this.max.x && b.y >= this.min.y && c.y <= this.max.y
                },
                intersects: function(a) {
                    a = e.bounds(a);
                    var b = this.min,
                        c = this.max,
                        d = a.min,
                        f = a.max,
                        g = f.x >= b.x && d.x <= c.x,
                        h = f.y >= b.y && d.y <= c.y;
                    return g && h
                },
                isValid: function() {
                    return ! (!this.min || !this.max)
                }
            },
            e.bounds = function(a, b) {
                return ! a || a instanceof e.Bounds ? a: new e.Bounds(a, b)
            },
            e.Transformation = function(a, b, c, d) {
                this._a = a,
                    this._b = b,
                    this._c = c,
                    this._d = d
            },
            e.Transformation.prototype = {
                transform: function(a, b) {
                    return this._transform(a.clone(), b)
                },
                _transform: function(a, b) {
                    return b = b || 1,
                        a.x = b * (this._a * a.x + this._b),
                        a.y = b * (this._c * a.y + this._d),
                        a
                },
                untransform: function(a, b) {
                    return b = b || 1,
                        new e.Point((a.x / b - this._b) / this._a, (a.y / b - this._d) / this._c)
                }
            },
            e.DomUtil = {
                get: function(a) {
                    return "string" == typeof a ? b.getElementById(a) : a
                },
                getStyle: function(a, c) {
                    var d = a.style[c];
                    if (!d && a.currentStyle && (d = a.currentStyle[c]), (!d || "auto" === d) && b.defaultView) {
                        var e = b.defaultView.getComputedStyle(a, null);
                        d = e ? e[c] : null
                    }
                    return "auto" === d ? null: d
                },
                getViewportOffset: function(a) {
                    var c, d = 0,
                        f = 0,
                        g = a,
                        h = b.body,
                        i = b.documentElement;
                    do {
                        if (d += g.offsetTop || 0, f += g.offsetLeft || 0, d += parseInt(e.DomUtil.getStyle(g, "borderTopWidth"), 10) || 0, f += parseInt(e.DomUtil.getStyle(g, "borderLeftWidth"), 10) || 0, c = e.DomUtil.getStyle(g, "position"), g.offsetParent === h && "absolute" === c) break;
                        if ("fixed" === c) {
                            d += h.scrollTop || i.scrollTop || 0,
                                f += h.scrollLeft || i.scrollLeft || 0;
                            break
                        }
                        if ("relative" === c && !g.offsetLeft) {
                            var j = e.DomUtil.getStyle(g, "width"),
                                k = e.DomUtil.getStyle(g, "max-width"),
                                l = g.getBoundingClientRect(); ("none" !== j || "none" !== k) && (f += l.left + g.clientLeft),
                                d += l.top + (h.scrollTop || i.scrollTop || 0);
                            break
                        }
                        g = g.offsetParent
                    } while ( g );
                    g = a;
                    do {
                        if (g === h) break;
                        d -= g.scrollTop || 0, f -= g.scrollLeft || 0, g = g.parentNode
                    } while ( g );
                    return new e.Point(f, d)
                },
                documentIsLtr: function() {
                    return e.DomUtil._docIsLtrCached || (e.DomUtil._docIsLtrCached = !0, e.DomUtil._docIsLtr = "ltr" === e.DomUtil.getStyle(b.body, "direction")),
                        e.DomUtil._docIsLtr
                },
                create: function(a, c, d) {
                    var e = b.createElement(a);
                    return e.className = c,
                    d && d.appendChild(e),
                        e
                },
                hasClass: function(a, b) {
                    if (a.classList !== c) return a.classList.contains(b);
                    var d = e.DomUtil._getClass(a);
                    return d.length > 0 && new RegExp("(^|\\s)" + b + "(\\s|$)").test(d)
                },
                addClass: function(a, b) {
                    if (a.classList !== c) for (var d = e.Util.splitWords(b), f = 0, g = d.length; g > f; f++) a.classList.add(d[f]);
                    else if (!e.DomUtil.hasClass(a, b)) {
                        var h = e.DomUtil._getClass(a);
                        e.DomUtil._setClass(a, (h ? h + " ": "") + b)
                    }
                },
                removeClass: function(a, b) {
                    a.classList !== c ? a.classList.remove(b) : e.DomUtil._setClass(a, e.Util.trim((" " + e.DomUtil._getClass(a) + " ").replace(" " + b + " ", " ")))
                },
                _setClass: function(a, b) {
                    a.className.baseVal === c ? a.className = b: a.className.baseVal = b
                },
                _getClass: function(a) {
                    return a.className.baseVal === c ? a.className: a.className.baseVal
                },
                setOpacity: function(a, b) {
                    if ("opacity" in a.style) a.style.opacity = b;
                    else if ("filter" in a.style) {
                        var c = !1,
                            d = "DXImageTransform.Microsoft.Alpha";
                        try {
                            c = a.filters.item(d)
                        } catch(e) {
                            if (1 === b) return
                        }
                        b = Math.round(100 * b),
                            c ? (c.Enabled = 100 !== b, c.Opacity = b) : a.style.filter += " progid:" + d + "(opacity=" + b + ")"
                    }
                },
                testProp: function(a) {
                    for (var c = b.documentElement.style,
                             d = 0; d < a.length; d++) if (a[d] in c) return a[d];
                    return ! 1
                },
                getTranslateString: function(a) {
                    var b = e.Browser.webkit3d,
                        c = "translate" + (b ? "3d": "") + "(",
                        d = (b ? ",0": "") + ")";
                    return c + a.x + "px," + a.y + "px" + d
                },
                getScaleString: function(a, b) {
                    var c = e.DomUtil.getTranslateString(b.add(b.multiplyBy( - 1 * a))),
                        d = " scale(" + a + ") ";
                    return c + d
                },
                setPosition: function(a, b, c) {
                    a._leaflet_pos = b,
                        !c && e.Browser.any3d ? (a.style[e.DomUtil.TRANSFORM] = e.DomUtil.getTranslateString(b), e.Browser.mobileWebkit3d && (a.style.WebkitBackfaceVisibility = "hidden")) : (a.style.left = b.x + "px", a.style.top = b.y + "px")
                },
                getPosition: function(a) {
                    return a._leaflet_pos
                }
            },
            e.DomUtil.TRANSFORM = e.DomUtil.testProp(["transform", "WebkitTransform", "OTransform", "MozTransform", "msTransform"]),
            e.DomUtil.TRANSITION = e.DomUtil.testProp(["webkitTransition", "transition", "OTransition", "MozTransition", "msTransition"]),
            e.DomUtil.TRANSITION_END = "webkitTransition" === e.DomUtil.TRANSITION || "OTransition" === e.DomUtil.TRANSITION ? e.DomUtil.TRANSITION + "End": "transitionend",
            function() {
                if ("onselectstart" in b) e.extend(e.DomUtil, {
                    disableTextSelection: function() {
                        e.DomEvent.on(a, "selectstart", e.DomEvent.preventDefault)
                    },
                    enableTextSelection: function() {
                        e.DomEvent.off(a, "selectstart", e.DomEvent.preventDefault)
                    }
                });
                else {
                    var c = e.DomUtil.testProp(["userSelect", "WebkitUserSelect", "OUserSelect", "MozUserSelect", "msUserSelect"]);
                    e.extend(e.DomUtil, {
                        disableTextSelection: function() {
                            if (c) {
                                var a = b.documentElement.style;
                                this._userSelect = a[c],
                                    a[c] = "none"
                            }
                        },
                        enableTextSelection: function() {
                            c && (b.documentElement.style[c] = this._userSelect, delete this._userSelect)
                        }
                    })
                }
                e.extend(e.DomUtil, {
                    disableImageDrag: function() {
                        e.DomEvent.on(a, "dragstart", e.DomEvent.preventDefault)
                    },
                    enableImageDrag: function() {
                        e.DomEvent.off(a, "dragstart", e.DomEvent.preventDefault)
                    }
                })
            } (),
            e.LatLng = function(a, b, d) {
                if (a = parseFloat(a), b = parseFloat(b), isNaN(a) || isNaN(b)) throw new Error("Invalid LatLng object: (" + a + ", " + b + ")");
                this.lat = a,
                    this.lng = b,
                d !== c && (this.alt = parseFloat(d))
            },
            e.extend(e.LatLng, {
                DEG_TO_RAD: Math.PI / 180,
                RAD_TO_DEG: 180 / Math.PI,
                MAX_MARGIN: 1e-9
            }),
            e.LatLng.prototype = {
                equals: function(a) {
                    if (!a) return ! 1;
                    a = e.latLng(a);
                    var b = Math.max(Math.abs(this.lat - a.lat), Math.abs(this.lng - a.lng));
                    return b <= e.LatLng.MAX_MARGIN
                },
                toString: function(a) {
                    return "LatLng(" + e.Util.formatNum(this.lat, a) + ", " + e.Util.formatNum(this.lng, a) + ")"
                },
                distanceTo: function(a) {
                    a = e.latLng(a);
                    var b = 6378137,
                        c = e.LatLng.DEG_TO_RAD,
                        d = (a.lat - this.lat) * c,
                        f = (a.lng - this.lng) * c,
                        g = this.lat * c,
                        h = a.lat * c,
                        i = Math.sin(d / 2),
                        j = Math.sin(f / 2),
                        k = i * i + j * j * Math.cos(g) * Math.cos(h);
                    return 2 * b * Math.atan2(Math.sqrt(k), Math.sqrt(1 - k))
                },
                wrap: function(a, b) {
                    var c = this.lng;
                    return a = a || -180,
                        b = b || 180,
                        c = (c + b) % (b - a) + (a > c || c === b ? b: a),
                        new e.LatLng(this.lat, c)
                }
            },
            e.latLng = function(a, b) {
                return a instanceof e.LatLng ? a: e.Util.isArray(a) ? "number" == typeof a[0] || "string" == typeof a[0] ? new e.LatLng(a[0], a[1], a[2]) : null: a === c || null === a ? a: "object" == typeof a && "lat" in a ? new e.LatLng(a.lat, "lng" in a ? a.lng: a.lon) : b === c ? null: new e.LatLng(a, b)
            },
            e.LatLngBounds = function(a, b) {
                if (a) for (var c = b ? [a, b] : a, d = 0, e = c.length; e > d; d++) this.extend(c[d])
            },
            e.LatLngBounds.prototype = {
                extend: function(a) {
                    if (!a) return this;
                    var b = e.latLng(a);
                    return a = null !== b ? b: e.latLngBounds(a),
                        a instanceof e.LatLng ? this._southWest || this._northEast ? (this._southWest.lat = Math.min(a.lat, this._southWest.lat), this._southWest.lng = Math.min(a.lng, this._southWest.lng), this._northEast.lat = Math.max(a.lat, this._northEast.lat), this._northEast.lng = Math.max(a.lng, this._northEast.lng)) : (this._southWest = new e.LatLng(a.lat, a.lng), this._northEast = new e.LatLng(a.lat, a.lng)) : a instanceof e.LatLngBounds && (this.extend(a._southWest), this.extend(a._northEast)),
                        this
                },
                pad: function(a) {
                    var b = this._southWest,
                        c = this._northEast,
                        d = Math.abs(b.lat - c.lat) * a,
                        f = Math.abs(b.lng - c.lng) * a;
                    return new e.LatLngBounds(new e.LatLng(b.lat - d, b.lng - f), new e.LatLng(c.lat + d, c.lng + f))
                },
                getCenter: function() {
                    return new e.LatLng((this._southWest.lat + this._northEast.lat) / 2, (this._southWest.lng + this._northEast.lng) / 2)
                },
                getSouthWest: function() {
                    return this._southWest
                },
                getNorthEast: function() {
                    return this._northEast
                },
                getNorthWest: function() {
                    return new e.LatLng(this.getNorth(), this.getWest())
                },
                getSouthEast: function() {
                    return new e.LatLng(this.getSouth(), this.getEast())
                },
                getWest: function() {
                    return this._southWest.lng
                },
                getSouth: function() {
                    return this._southWest.lat
                },
                getEast: function() {
                    return this._northEast.lng
                },
                getNorth: function() {
                    return this._northEast.lat
                },
                contains: function(a) {
                    a = "number" == typeof a[0] || a instanceof e.LatLng ? e.latLng(a) : e.latLngBounds(a);
                    var b, c, d = this._southWest,
                        f = this._northEast;
                    return a instanceof e.LatLngBounds ? (b = a.getSouthWest(), c = a.getNorthEast()) : b = c = a,
                    b.lat >= d.lat && c.lat <= f.lat && b.lng >= d.lng && c.lng <= f.lng
                },
                intersects: function(a) {
                    a = e.latLngBounds(a);
                    var b = this._southWest,
                        c = this._northEast,
                        d = a.getSouthWest(),
                        f = a.getNorthEast(),
                        g = f.lat >= b.lat && d.lat <= c.lat,
                        h = f.lng >= b.lng && d.lng <= c.lng;
                    return g && h
                },
                toBBoxString: function() {
                    return [this.getWest(), this.getSouth(), this.getEast(), this.getNorth()].join(",")
                },
                equals: function(a) {
                    return a ? (a = e.latLngBounds(a), this._southWest.equals(a.getSouthWest()) && this._northEast.equals(a.getNorthEast())) : !1
                },
                isValid: function() {
                    return ! (!this._southWest || !this._northEast)
                }
            },
            e.latLngBounds = function(a, b) {
                return ! a || a instanceof e.LatLngBounds ? a: new e.LatLngBounds(a, b)
            },
            e.Projection = {},
            e.Projection.SphericalMercator = {
                MAX_LATITUDE: 85.0511287798,
                project: function(a) {
                    var b = e.LatLng.DEG_TO_RAD,
                        c = this.MAX_LATITUDE,
                        d = Math.max(Math.min(c, a.lat), -c),
                        f = a.lng * b,
                        g = d * b;
                    return g = Math.log(Math.tan(Math.PI / 4 + g / 2)),
                        new e.Point(f, g)
                },
                unproject: function(a) {
                    var b = e.LatLng.RAD_TO_DEG,
                        c = a.x * b,
                        d = (2 * Math.atan(Math.exp(a.y)) - Math.PI / 2) * b;
                    return new e.LatLng(d, c)
                }
            },
            e.Projection.LonLat = {
                project: function(a) {
                    return new e.Point(a.lng, a.lat)
                },
                unproject: function(a) {
                    return new e.LatLng(a.y, a.x)
                }
            },
            e.CRS = {
                latLngToPoint: function(a, b) {
                    var c = this.projection.project(a),
                        d = this.scale(b);
                    return this.transformation._transform(c, d)
                },
                pointToLatLng: function(a, b) {
                    var c = this.scale(b),
                        d = this.transformation.untransform(a, c);
                    return this.projection.unproject(d)
                },
                project: function(a) {
                    return this.projection.project(a)
                },
                scale: function(a) {
                    return 256 * Math.pow(2, a)
                },
                getSize: function(a) {
                    var b = this.scale(a);
                    return e.point(b, b)
                }
            },
            e.CRS.Simple = e.extend({},
                e.CRS, {
                    projection: e.Projection.LonLat,
                    transformation: new e.Transformation(1, 0, -1, 0),
                    scale: function(a) {
                        return Math.pow(2, a)
                    }
                }),
            e.CRS.EPSG3857 = e.extend({},
                e.CRS, {
                    code: "EPSG:3857",
                    projection: e.Projection.SphericalMercator,
                    transformation: new e.Transformation(.5 / Math.PI, .5, -.5 / Math.PI, .5),
                    project: function(a) {
                        var b = this.projection.project(a),
                            c = 6378137;
                        return b.multiplyBy(c)
                    }
                }),
            e.CRS.EPSG900913 = e.extend({},
                e.CRS.EPSG3857, {
                    code: "EPSG:900913"
                }),
            e.CRS.EPSG4326 = e.extend({},
                e.CRS, {
                    code: "EPSG:4326",
                    projection: e.Projection.LonLat,
                    transformation: new e.Transformation(1 / 360, .5, -1 / 360, .5)
                }),
            e.Map = e.Class.extend({
                includes: e.Mixin.Events,
                options: {
                    crs: e.CRS.EPSG3857,
                    fadeAnimation: e.DomUtil.TRANSITION && !e.Browser.android23,
                    trackResize: !0,
                    markerZoomAnimation: e.DomUtil.TRANSITION && e.Browser.any3d
                },
                initialize: function(a, b) {
                    b = e.setOptions(this, b),
                        this._initContainer(a),
                        this._initLayout(),
                        this._onResize = e.bind(this._onResize, this),
                        this._initEvents(),
                    b.maxBounds && this.setMaxBounds(b.maxBounds),
                    b.center && b.zoom !== c && this.setView(e.latLng(b.center), b.zoom, {
                        reset: !0
                    }),
                        this._handlers = [],
                        this._layers = {},
                        this._zoomBoundLayers = {},
                        this._tileLayersNum = 0,
                        this.callInitHooks(),
                        this._addLayers(b.layers)
                },
                setView: function(a, b) {
                    return b = b === c ? this.getZoom() : b,
                        this._resetView(e.latLng(a), this._limitZoom(b)),
                        this
                },
                setZoom: function(a, b) {
                    return this._loaded ? this.setView(this.getCenter(), a, {
                        zoom: b
                    }) : (this._zoom = this._limitZoom(a), this)
                },
                zoomIn: function(a, b) {
                    return this.setZoom(this._zoom + (a || 1), b)
                },
                zoomOut: function(a, b) {
                    return this.setZoom(this._zoom - (a || 1), b)
                },
                setZoomAround: function(a, b, c) {
                    var d = this.getZoomScale(b),
                        f = this.getSize().divideBy(2),
                        g = a instanceof e.Point ? a: this.latLngToContainerPoint(a),
                        h = g.subtract(f).multiplyBy(1 - 1 / d),
                        i = this.containerPointToLatLng(f.add(h));
                    return this.setView(i, b, {
                        zoom: c
                    })
                },
                fitBounds: function(a, b) {
                    b = b || {},
                        a = a.getBounds ? a.getBounds() : e.latLngBounds(a);
                    var c = e.point(b.paddingTopLeft || b.padding || [0, 0]),
                        d = e.point(b.paddingBottomRight || b.padding || [0, 0]),
                        f = this.getBoundsZoom(a, !1, c.add(d)),
                        g = d.subtract(c).divideBy(2),
                        h = this.project(a.getSouthWest(), f),
                        i = this.project(a.getNorthEast(), f),
                        j = this.unproject(h.add(i).divideBy(2).add(g), f);
                    return f = b && b.maxZoom ? Math.min(b.maxZoom, f) : f,
                        this.setView(j, f, b)
                },
                fitWorld: function(a) {
                    return this.fitBounds([[ - 90, -180], [90, 180]], a)
                },
                panTo: function(a, b) {
                    return this.setView(a, this._zoom, {
                        pan: b
                    })
                },
                panBy: function(a) {
                    return this.fire("movestart"),
                        this._rawPanBy(e.point(a)),
                        this.fire("move"),
                        this.fire("moveend")
                },
                setMaxBounds: function(a) {
                    return a = e.latLngBounds(a),
                        this.options.maxBounds = a,
                        a ? (this._loaded && this._panInsideMaxBounds(), this.on("moveend", this._panInsideMaxBounds, this)) : this.off("moveend", this._panInsideMaxBounds, this)
                },
                panInsideBounds: function(a, b) {
                    var c = this.getCenter(),
                        d = this._limitCenter(c, this._zoom, a);
                    return c.equals(d) ? this: this.panTo(d, b)
                },
                addLayer: function(a) {
                    var b = e.stamp(a);
                    return this._layers[b] ? this: (this._layers[b] = a, !a.options || isNaN(a.options.maxZoom) && isNaN(a.options.minZoom) || (this._zoomBoundLayers[b] = a, this._updateZoomLevels()), this.options.zoomAnimation && e.TileLayer && a instanceof e.TileLayer && (this._tileLayersNum++, this._tileLayersToLoad++, a.on("load", this._onTileLayerLoad, this)), this._loaded && this._layerAdd(a), this)
                },
                removeLayer: function(a) {
                    var b = e.stamp(a);
                    return this._layers[b] ? (this._loaded && a.onRemove(this), delete this._layers[b], this._loaded && this.fire("layerremove", {
                        layer: a
                    }), this._zoomBoundLayers[b] && (delete this._zoomBoundLayers[b], this._updateZoomLevels()), this.options.zoomAnimation && e.TileLayer && a instanceof e.TileLayer && (this._tileLayersNum--, this._tileLayersToLoad--, a.off("load", this._onTileLayerLoad, this)), this) : this
                },
                hasLayer: function(a) {
                    return a ? e.stamp(a) in this._layers: !1
                },
                eachLayer: function(a, b) {
                    for (var c in this._layers) a.call(b, this._layers[c]);
                    return this
                },
                invalidateSize: function(a) {
                    a = e.extend({
                            animate: !1,
                            pan: !0
                        },
                        a === !0 ? {
                            animate: !0
                        }: a);
                    var b = this.getSize();
                    if (this._sizeChanged = !0, this._initialCenter = null, !this._loaded) return this;
                    var c = this.getSize(),
                        d = b.divideBy(2).round(),
                        f = c.divideBy(2).round(),
                        g = d.subtract(f);
                    return g.x || g.y ? (a.animate && a.pan ? this.panBy(g) : (a.pan && this._rawPanBy(g), this.fire("move"), a.debounceMoveend ? (clearTimeout(this._sizeTimer), this._sizeTimer = setTimeout(e.bind(this.fire, this, "moveend"), 200)) : this.fire("moveend")), this.fire("resize", {
                        oldSize: b,
                        newSize: c
                    })) : this
                },
                addHandler: function(a, b) {
                    if (!b) return this;
                    var c = this[a] = new b(this);
                    return this._handlers.push(c),
                    this.options[a] && c.enable(),
                        this
                },
                remove: function() {
                    this._loaded && this.fire("unload"),
                        this._initEvents("off");
                    try {
                        delete this._container._leaflet
                    } catch(a) {
                        this._container._leaflet = c
                    }
                    return this._clearPanes(),
                    this._clearControlPos && this._clearControlPos(),
                        this._clearHandlers(),
                        this
                },
                getCenter: function() {
                    return this._checkIfLoaded(),
                        this._initialCenter && !this._moved() ? this._initialCenter: this.layerPointToLatLng(this._getCenterLayerPoint())
                },
                getZoom: function() {
                    return this._zoom
                },
                getBounds: function() {
                    var a = this.getPixelBounds(),
                        b = this.unproject(a.getBottomLeft()),
                        c = this.unproject(a.getTopRight());
                    return new e.LatLngBounds(b, c)
                },
                getMinZoom: function() {
                    return this.options.minZoom === c ? this._layersMinZoom === c ? 0 : this._layersMinZoom: this.options.minZoom
                },
                getMaxZoom: function() {
                    return this.options.maxZoom === c ? this._layersMaxZoom === c ? 1 / 0 : this._layersMaxZoom: this.options.maxZoom
                },
                getBoundsZoom: function(a, b, c) {
                    a = e.latLngBounds(a);
                    var d, f = this.getMinZoom() - (b ? 1 : 0),
                        g = this.getMaxZoom(),
                        h = this.getSize(),
                        i = a.getNorthWest(),
                        j = a.getSouthEast(),
                        k = !0;
                    c = e.point(c || [0, 0]);
                    do f++,
                        d = this.project(j, f).subtract(this.project(i, f)).add(c),
                        k = b ? d.x < h.x || d.y < h.y: h.contains(d);
                    while (k && g >= f);
                    return k && b ? null: b ? f: f - 1
                },
                getSize: function() {
                    return (!this._size || this._sizeChanged) && (this._size = new e.Point(this._container.clientWidth, this._container.clientHeight), this._sizeChanged = !1),
                        this._size.clone()
                },
                getPixelBounds: function() {
                    var a = this._getTopLeftPoint();
                    return new e.Bounds(a, a.add(this.getSize()))
                },
                getPixelOrigin: function() {
                    return this._checkIfLoaded(),
                        this._initialTopLeftPoint
                },
                getPanes: function() {
                    return this._panes
                },
                getContainer: function() {
                    return this._container
                },
                getZoomScale: function(a) {
                    var b = this.options.crs;
                    return b.scale(a) / b.scale(this._zoom)
                },
                getScaleZoom: function(a) {
                    return this._zoom + Math.log(a) / Math.LN2
                },
                project: function(a, b) {
                    return b = b === c ? this._zoom: b,
                        this.options.crs.latLngToPoint(e.latLng(a), b)
                },
                unproject: function(a, b) {
                    return b = b === c ? this._zoom: b,
                        this.options.crs.pointToLatLng(e.point(a), b)
                },
                layerPointToLatLng: function(a) {
                    var b = e.point(a).add(this.getPixelOrigin());
                    return this.unproject(b)
                },
                latLngToLayerPoint: function(a) {
                    var b = this.project(e.latLng(a))._round();
                    return b._subtract(this.getPixelOrigin())
                },
                containerPointToLayerPoint: function(a) {
                    return e.point(a).subtract(this._getMapPanePos())
                },
                layerPointToContainerPoint: function(a) {
                    return e.point(a).add(this._getMapPanePos())
                },
                containerPointToLatLng: function(a) {
                    var b = this.containerPointToLayerPoint(e.point(a));
                    return this.layerPointToLatLng(b)
                },
                latLngToContainerPoint: function(a) {
                    return this.layerPointToContainerPoint(this.latLngToLayerPoint(e.latLng(a)))
                },
                mouseEventToContainerPoint: function(a) {
                    return e.DomEvent.getMousePosition(a, this._container)
                },
                mouseEventToLayerPoint: function(a) {
                    return this.containerPointToLayerPoint(this.mouseEventToContainerPoint(a))
                },
                mouseEventToLatLng: function(a) {
                    return this.layerPointToLatLng(this.mouseEventToLayerPoint(a))
                },
                _initContainer: function(a) {
                    var b = this._container = e.DomUtil.get(a);
                    if (!b) throw new Error("Map container not found.");
                    if (b._leaflet) throw new Error("Map container is already initialized.");
                    b._leaflet = !0
                },
                _initLayout: function() {
                    var a = this._container;
                    e.DomUtil.addClass(a, "leaflet-container" + (e.Browser.touch ? " leaflet-touch": "") + (e.Browser.retina ? " leaflet-retina": "") + (e.Browser.ielt9 ? " leaflet-oldie": "") + (this.options.fadeAnimation ? " leaflet-fade-anim": ""));
                    var b = e.DomUtil.getStyle(a, "position");
                    "absolute" !== b && "relative" !== b && "fixed" !== b && (a.style.position = "relative"),
                        this._initPanes(),
                    this._initControlPos && this._initControlPos()
                },
                _initPanes: function() {
                    var a = this._panes = {};
                    this._mapPane = a.mapPane = this._createPane("leaflet-map-pane", this._container),
                        this._tilePane = a.tilePane = this._createPane("leaflet-tile-pane", this._mapPane),
                        a.objectsPane = this._createPane("leaflet-objects-pane", this._mapPane),
                        a.shadowPane = this._createPane("leaflet-shadow-pane"),
                        a.overlayPane = this._createPane("leaflet-overlay-pane"),
                        a.markerPane = this._createPane("leaflet-marker-pane"),
                        a.popupPane = this._createPane("leaflet-popup-pane");
                    var b = " leaflet-zoom-hide";
                    this.options.markerZoomAnimation || (e.DomUtil.addClass(a.markerPane, b), e.DomUtil.addClass(a.shadowPane, b), e.DomUtil.addClass(a.popupPane, b))
                },
                _createPane: function(a, b) {
                    return e.DomUtil.create("div", a, b || this._panes.objectsPane)
                },
                _clearPanes: function() {
                    this._container.removeChild(this._mapPane)
                },
                _addLayers: function(a) {
                    a = a ? e.Util.isArray(a) ? a: [a] : [];
                    for (var b = 0,
                             c = a.length; c > b; b++) this.addLayer(a[b])
                },
                _resetView: function(a, b, c, d) {
                    var f = this._zoom !== b;
                    d || (this.fire("movestart"), f && this.fire("zoomstart")),
                        this._zoom = b,
                        this._initialCenter = a,
                        this._initialTopLeftPoint = this._getNewTopLeftPoint(a),
                        c ? this._initialTopLeftPoint._add(this._getMapPanePos()) : e.DomUtil.setPosition(this._mapPane, new e.Point(0, 0)),
                        this._tileLayersToLoad = this._tileLayersNum;
                    var g = !this._loaded;
                    this._loaded = !0,
                    g && (this.fire("load"), this.eachLayer(this._layerAdd, this)),
                        this.fire("viewreset", {
                            hard: !c
                        }),
                        this.fire("move"),
                    (f || d) && this.fire("zoomend"),
                        this.fire("moveend", {
                            hard: !c
                        })
                },
                _rawPanBy: function(a) {
                    e.DomUtil.setPosition(this._mapPane, this._getMapPanePos().subtract(a))
                },
                _getZoomSpan: function() {
                    return this.getMaxZoom() - this.getMinZoom()
                },
                _updateZoomLevels: function() {
                    var a, b = 1 / 0,
                        d = -(1 / 0),
                        e = this._getZoomSpan();
                    for (a in this._zoomBoundLayers) {
                        var f = this._zoomBoundLayers[a];
                        isNaN(f.options.minZoom) || (b = Math.min(b, f.options.minZoom)),
                        isNaN(f.options.maxZoom) || (d = Math.max(d, f.options.maxZoom))
                    }
                    a === c ? this._layersMaxZoom = this._layersMinZoom = c: (this._layersMaxZoom = d, this._layersMinZoom = b),
                    e !== this._getZoomSpan() && this.fire("zoomlevelschange")
                },
                _panInsideMaxBounds: function() {
                    this.panInsideBounds(this.options.maxBounds)
                },
                _checkIfLoaded: function() {
                    if (!this._loaded) throw new Error("Set map center and zoom first.")
                },
                _initEvents: function(b) {
                    if (e.DomEvent) {
                        b = b || "on",
                            e.DomEvent[b](this._container, "click", this._onMouseClick, this);
                        var c, d, f = ["dblclick", "mousedown", "mouseup", "mouseenter", "mouseleave", "mousemove", "contextmenu"];
                        for (c = 0, d = f.length; d > c; c++) e.DomEvent[b](this._container, f[c], this._fireMouseEvent, this);
                        this.options.trackResize && e.DomEvent[b](a, "resize", this._onResize, this)
                    }
                },
                _onResize: function() {
                    e.Util.cancelAnimFrame(this._resizeRequest),
                        this._resizeRequest = e.Util.requestAnimFrame(function() {
                                this.invalidateSize({
                                    debounceMoveend: !0
                                })
                            },
                            this, !1, this._container)
                },
                _onMouseClick: function(a) { ! this._loaded || !a._simulated && (this.dragging && this.dragging.moved() || this.boxZoom && this.boxZoom.moved()) || e.DomEvent._skipped(a) || (this.fire("preclick"), this._fireMouseEvent(a))
                },
                _fireMouseEvent: function(a) {
                    if (this._loaded && !e.DomEvent._skipped(a)) {
                        var b = a.type;
                        if (b = "mouseenter" === b ? "mouseover": "mouseleave" === b ? "mouseout": b, this.hasEventListeners(b)) {
                            "contextmenu" === b && e.DomEvent.preventDefault(a);
                            var c = this.mouseEventToContainerPoint(a),
                                d = this.containerPointToLayerPoint(c),
                                f = this.layerPointToLatLng(d);
                            this.fire(b, {
                                latlng: f,
                                layerPoint: d,
                                containerPoint: c,
                                originalEvent: a
                            })
                        }
                    }
                },
                _onTileLayerLoad: function() {
                    this._tileLayersToLoad--,
                    this._tileLayersNum && !this._tileLayersToLoad && this.fire("tilelayersload")
                },
                _clearHandlers: function() {
                    for (var a = 0,
                             b = this._handlers.length; b > a; a++) this._handlers[a].disable()
                },
                whenReady: function(a, b) {
                    return this._loaded ? a.call(b || this, this) : this.on("load", a, b),
                        this
                },
                _layerAdd: function(a) {
                    a.onAdd(this),
                        this.fire("layeradd", {
                            layer: a
                        })
                },
                _getMapPanePos: function() {
                    return e.DomUtil.getPosition(this._mapPane)
                },
                _moved: function() {
                    var a = this._getMapPanePos();
                    return a && !a.equals([0, 0])
                },
                _getTopLeftPoint: function() {
                    return this.getPixelOrigin().subtract(this._getMapPanePos())
                },
                _getNewTopLeftPoint: function(a, b) {
                    var c = this.getSize()._divideBy(2);
                    return this.project(a, b)._subtract(c)._round()
                },
                _latLngToNewLayerPoint: function(a, b, c) {
                    var d = this._getNewTopLeftPoint(c, b).add(this._getMapPanePos());
                    return this.project(a, b)._subtract(d)
                },
                _getCenterLayerPoint: function() {
                    return this.containerPointToLayerPoint(this.getSize()._divideBy(2));
                },
                _getCenterOffset: function(a) {
                    return this.latLngToLayerPoint(a).subtract(this._getCenterLayerPoint())
                },
                _limitCenter: function(a, b, c) {
                    if (!c) return a;
                    var d = this.project(a, b),
                        f = this.getSize().divideBy(2),
                        g = new e.Bounds(d.subtract(f), d.add(f)),
                        h = this._getBoundsOffset(g, c, b);
                    return this.unproject(d.add(h), b)
                },
                _limitOffset: function(a, b) {
                    if (!b) return a;
                    var c = this.getPixelBounds(),
                        d = new e.Bounds(c.min.add(a), c.max.add(a));
                    return a.add(this._getBoundsOffset(d, b))
                },
                _getBoundsOffset: function(a, b, c) {
                    var d = this.project(b.getNorthWest(), c).subtract(a.min),
                        f = this.project(b.getSouthEast(), c).subtract(a.max),
                        g = this._rebound(d.x, -f.x),
                        h = this._rebound(d.y, -f.y);
                    return new e.Point(g, h)
                },
                _rebound: function(a, b) {
                    return a + b > 0 ? Math.round(a - b) / 2 : Math.max(0, Math.ceil(a)) - Math.max(0, Math.floor(b))
                },
                _limitZoom: function(a) {
                    var b = this.getMinZoom(),
                        c = this.getMaxZoom();
                    return Math.max(b, Math.min(c, a))
                }
            }),
            e.map = function(a, b) {
                return new e.Map(a, b)
            },
            e.Projection.Mercator = {
                MAX_LATITUDE: 85.0840591556,
                R_MINOR: 6356752.314245179,
                R_MAJOR: 6378137,
                project: function(a) {
                    var b = e.LatLng.DEG_TO_RAD,
                        c = this.MAX_LATITUDE,
                        d = Math.max(Math.min(c, a.lat), -c),
                        f = this.R_MAJOR,
                        g = this.R_MINOR,
                        h = a.lng * b * f,
                        i = d * b,
                        j = g / f,
                        k = Math.sqrt(1 - j * j),
                        l = k * Math.sin(i);
                    l = Math.pow((1 - l) / (1 + l), .5 * k);
                    var m = Math.tan(.5 * (.5 * Math.PI - i)) / l;
                    return i = -f * Math.log(m),
                        new e.Point(h, i)
                },
                unproject: function(a) {
                    for (var b, c = e.LatLng.RAD_TO_DEG,
                             d = this.R_MAJOR,
                             f = this.R_MINOR,
                             g = a.x * c / d,
                             h = f / d,
                             i = Math.sqrt(1 - h * h), j = Math.exp( - a.y / d), k = Math.PI / 2 - 2 * Math.atan(j), l = 15, m = 1e-7, n = l, o = .1; Math.abs(o) > m && --n > 0;) b = i * Math.sin(k),
                        o = Math.PI / 2 - 2 * Math.atan(j * Math.pow((1 - b) / (1 + b), .5 * i)) - k,
                        k += o;
                    return new e.LatLng(k * c, g)
                }
            },
            e.CRS.EPSG3395 = e.extend({},
                e.CRS, {
                    code: "EPSG:3395",
                    projection: e.Projection.Mercator,
                    transformation: function() {
                        var a = e.Projection.Mercator,
                            b = a.R_MAJOR,
                            c = .5 / (Math.PI * b);
                        return new e.Transformation(c, .5, -c, .5)
                    } ()
                }),
            e.TileLayer = e.Class.extend({
                includes: e.Mixin.Events,
                options: {
                    minZoom: 0,
                    maxZoom: 18,
                    tileSize: 256,
                    subdomains: "abc",
                    errorTileUrl: "",
                    attribution: "",
                    zoomOffset: 0,
                    opacity: 1,
                    unloadInvisibleTiles: e.Browser.mobile,
                    updateWhenIdle: e.Browser.mobile
                },
                initialize: function(a, b) {
                    b = e.setOptions(this, b),
                    b.detectRetina && e.Browser.retina && b.maxZoom > 0 && (b.tileSize = Math.floor(b.tileSize / 2), b.zoomOffset++, b.minZoom > 0 && b.minZoom--, this.options.maxZoom--),
                    b.bounds && (b.bounds = e.latLngBounds(b.bounds)),
                        this._url = a;
                    var c = this.options.subdomains;
                    "string" == typeof c && (this.options.subdomains = c.split(""))
                },
                onAdd: function(a) {
                    this._map = a,
                        this._animated = a._zoomAnimated,
                        this._initContainer(),
                        a.on({
                                viewreset: this._reset,
                                moveend: this._update
                            },
                            this),
                    this._animated && a.on({
                            zoomanim: this._animateZoom,
                            zoomend: this._endZoomAnim
                        },
                        this),
                    this.options.updateWhenIdle || (this._limitedUpdate = e.Util.limitExecByInterval(this._update, 150, this), a.on("move", this._limitedUpdate, this)),
                        this._reset(),
                        this._update()
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                onRemove: function(a) {
                    this._container.parentNode.removeChild(this._container),
                        a.off({
                                viewreset: this._reset,
                                moveend: this._update
                            },
                            this),
                    this._animated && a.off({
                            zoomanim: this._animateZoom,
                            zoomend: this._endZoomAnim
                        },
                        this),
                    this.options.updateWhenIdle || a.off("move", this._limitedUpdate, this),
                        this._container = null,
                        this._map = null
                },
                bringToFront: function() {
                    var a = this._map._panes.tilePane;
                    return this._container && (a.appendChild(this._container), this._setAutoZIndex(a, Math.max)),
                        this
                },
                bringToBack: function() {
                    var a = this._map._panes.tilePane;
                    return this._container && (a.insertBefore(this._container, a.firstChild), this._setAutoZIndex(a, Math.min)),
                        this
                },
                getAttribution: function() {
                    return this.options.attribution
                },
                getContainer: function() {
                    return this._container
                },
                setOpacity: function(a) {
                    return this.options.opacity = a,
                    this._map && this._updateOpacity(),
                        this
                },
                setZIndex: function(a) {
                    return this.options.zIndex = a,
                        this._updateZIndex(),
                        this
                },
                setUrl: function(a, b) {
                    return this._url = a,
                    b || this.redraw(),
                        this
                },
                redraw: function() {
                    return this._map && (this._reset({
                        hard: !0
                    }), this._update()),
                        this
                },
                _updateZIndex: function() {
                    this._container && this.options.zIndex !== c && (this._container.style.zIndex = this.options.zIndex)
                },
                _setAutoZIndex: function(a, b) {
                    var c, d, e, f = a.children,
                        g = -b(1 / 0, -(1 / 0));
                    for (d = 0, e = f.length; e > d; d++) f[d] !== this._container && (c = parseInt(f[d].style.zIndex, 10), isNaN(c) || (g = b(g, c)));
                    this.options.zIndex = this._container.style.zIndex = (isFinite(g) ? g: 0) + b(1, -1)
                },
                _updateOpacity: function() {
                    var a, b = this._tiles;
                    if (e.Browser.ielt9) for (a in b) e.DomUtil.setOpacity(b[a], this.options.opacity);
                    else e.DomUtil.setOpacity(this._container, this.options.opacity)
                },
                _initContainer: function() {
                    var a = this._map._panes.tilePane;
                    if (!this._container) {
                        if (this._container = e.DomUtil.create("div", "leaflet-layer"), this._updateZIndex(), this._animated) {
                            var b = "leaflet-tile-container";
                            this._bgBuffer = e.DomUtil.create("div", b, this._container),
                                this._tileContainer = e.DomUtil.create("div", b, this._container)
                        } else this._tileContainer = this._container;
                        a.appendChild(this._container),
                        this.options.opacity < 1 && this._updateOpacity()
                    }
                },
                _reset: function(a) {
                    for (var b in this._tiles) this.fire("tileunload", {
                        tile: this._tiles[b]
                    });
                    this._tiles = {},
                        this._tilesToLoad = 0,
                    this.options.reuseTiles && (this._unusedTiles = []),
                        this._tileContainer.innerHTML = "",
                    this._animated && a && a.hard && this._clearBgBuffer(),
                        this._initContainer()
                },
                _getTileSize: function() {
                    var a = this._map,
                        b = a.getZoom() + this.options.zoomOffset,
                        c = this.options.maxNativeZoom,
                        d = this.options.tileSize;
                    return c && b > c && (d = Math.round(a.getZoomScale(b) / a.getZoomScale(c) * d)),
                        d
                },
                _update: function() {
                    if (this._map) {
                        var a = this._map,
                            b = a.getPixelBounds(),
                            c = a.getZoom(),
                            d = this._getTileSize();
                        if (! (c > this.options.maxZoom || c < this.options.minZoom)) {
                            var f = e.bounds(b.min.divideBy(d)._floor(), b.max.divideBy(d)._floor());
                            this._addTilesFromCenterOut(f),
                            (this.options.unloadInvisibleTiles || this.options.reuseTiles) && this._removeOtherTiles(f)
                        }
                    }
                },
                _addTilesFromCenterOut: function(a) {
                    var c, d, f, g = [],
                        h = a.getCenter();
                    for (c = a.min.y; c <= a.max.y; c++) for (d = a.min.x; d <= a.max.x; d++) f = new e.Point(d, c),
                    this._tileShouldBeLoaded(f) && g.push(f);
                    var i = g.length;
                    if (0 !== i) {
                        g.sort(function(a, b) {
                            return a.distanceTo(h) - b.distanceTo(h)
                        });
                        var j = b.createDocumentFragment();
                        for (this._tilesToLoad || this.fire("loading"), this._tilesToLoad += i, d = 0; i > d; d++) this._addTile(g[d], j);
                        this._tileContainer.appendChild(j)
                    }
                },
                _tileShouldBeLoaded: function(a) {
                    if (a.x + ":" + a.y in this._tiles) return ! 1;
                    var b = this.options;
                    if (!b.continuousWorld) {
                        var c = this._getWrapTileNum();
                        if (b.noWrap && (a.x < 0 || a.x >= c.x) || a.y < 0 || a.y >= c.y) return ! 1
                    }
                    if (b.bounds) {
                        var d = b.tileSize,
                            e = a.multiplyBy(d),
                            f = e.add([d, d]),
                            g = this._map.unproject(e),
                            h = this._map.unproject(f);
                        if (b.continuousWorld || b.noWrap || (g = g.wrap(), h = h.wrap()), !b.bounds.intersects([g, h])) return ! 1
                    }
                    return ! 0
                },
                _removeOtherTiles: function(a) {
                    var b, c, d, e;
                    for (e in this._tiles) b = e.split(":"),
                        c = parseInt(b[0], 10),
                        d = parseInt(b[1], 10),
                    (c < a.min.x || c > a.max.x || d < a.min.y || d > a.max.y) && this._removeTile(e)
                },
                _removeTile: function(a) {
                    var b = this._tiles[a];
                    this.fire("tileunload", {
                        tile: b,
                        url: b.src
                    }),
                        this.options.reuseTiles ? (e.DomUtil.removeClass(b, "leaflet-tile-loaded"), this._unusedTiles.push(b)) : b.parentNode === this._tileContainer && this._tileContainer.removeChild(b),
                    e.Browser.android || (b.onload = null, b.src = e.Util.emptyImageUrl),
                        delete this._tiles[a]
                },
                _addTile: function(a, b) {
                    var c = this._getTilePos(a),
                        d = this._getTile();
                    e.DomUtil.setPosition(d, c, e.Browser.chrome || e.Browser.android23),
                        this._tiles[a.x + ":" + a.y] = d,
                        this._loadTile(d, a),
                    d.parentNode !== this._tileContainer && b.appendChild(d)
                },
                _getZoomForUrl: function() {
                    var a = this.options,
                        b = this._map.getZoom();
                    return a.zoomReverse && (b = a.maxZoom - b),
                        b += a.zoomOffset,
                        a.maxNativeZoom ? Math.min(b, a.maxNativeZoom) : b
                },
                _getTilePos: function(a) {
                    var b = this._map.getPixelOrigin(),
                        c = this._getTileSize();
                    return a.multiplyBy(c).subtract(b)
                },
                getTileUrl: function(a) {
                    return e.Util.template(this._url, e.extend({
                            s: this._getSubdomain(a),
                            z: a.z,
                            x: a.x,
                            y: a.y
                        },
                        this.options))
                },
                _getWrapTileNum: function() {
                    var a = this._map.options.crs,
                        b = a.getSize(this._map.getZoom());
                    return b.divideBy(this.options.tileSize)
                },
                _adjustTilePoint: function(a) {
                    var b = this._getWrapTileNum();
                    this.options.continuousWorld || this.options.noWrap || (a.x = (a.x % b.x + b.x) % b.x),
                    this.options.tms && (a.y = b.y - a.y - 1),
                        a.z = this._getZoomForUrl()
                },
                _getSubdomain: function(a) {
                    var b = Math.abs(a.x + a.y) % this.options.subdomains.length;
                    return this.options.subdomains[b]
                },
                _getTile: function() {
                    if (this.options.reuseTiles && this._unusedTiles.length > 0) {
                        var a = this._unusedTiles.pop();
                        return this._resetTile(a),
                            a
                    }
                    return this._createTile()
                },
                _resetTile: function() {},
                _createTile: function() {
                    var a = e.DomUtil.create("img", "leaflet-tile");
                    return a.style.width = a.style.height = this._getTileSize() + "px",
                        a.galleryimg = "no",
                        a.onselectstart = a.onmousemove = e.Util.falseFn,
                    e.Browser.ielt9 && this.options.opacity !== c && e.DomUtil.setOpacity(a, this.options.opacity),
                    e.Browser.mobileWebkit3d && (a.style.WebkitBackfaceVisibility = "hidden"),
                        a
                },
                _loadTile: function(a, b) {
                    a._layer = this,
                        a.onload = this._tileOnLoad,
                        a.onerror = this._tileOnError,
                        this._adjustTilePoint(b),
                        a.src = this.getTileUrl(b),
                        this.fire("tileloadstart", {
                            tile: a,
                            url: a.src
                        })
                },
                _tileLoaded: function() {
                    this._tilesToLoad--,
                    this._animated && e.DomUtil.addClass(this._tileContainer, "leaflet-zoom-animated"),
                    this._tilesToLoad || (this.fire("load"), this._animated && (clearTimeout(this._clearBgBufferTimer), this._clearBgBufferTimer = setTimeout(e.bind(this._clearBgBuffer, this), 500)))
                },
                _tileOnLoad: function() {
                    var a = this._layer;
                    this.src !== e.Util.emptyImageUrl && (e.DomUtil.addClass(this, "leaflet-tile-loaded"), a.fire("tileload", {
                        tile: this,
                        url: this.src
                    })),
                        a._tileLoaded()
                },
                _tileOnError: function() {
                    var a = this._layer;
                    a.fire("tileerror", {
                        tile: this,
                        url: this.src
                    });
                    var b = a.options.errorTileUrl;
                    b && (this.src = b),
                        a._tileLoaded()
                }
            }),
            e.tileLayer = function(a, b) {
                return new e.TileLayer(a, b)
            },
            e.TileLayer.WMS = e.TileLayer.extend({
                defaultWmsParams: {
                    service: "WMS",
                    request: "GetMap",
                    version: "1.1.1",
                    layers: "",
                    styles: "",
                    format: "image/jpeg",
                    transparent: !1
                },
                initialize: function(a, b) {
                    this._url = a;
                    var c = e.extend({},
                            this.defaultWmsParams),
                        d = b.tileSize || this.options.tileSize;
                    b.detectRetina && e.Browser.retina ? c.width = c.height = 2 * d: c.width = c.height = d;
                    for (var f in b) this.options.hasOwnProperty(f) || "crs" === f || (c[f] = b[f]);
                    this.wmsParams = c,
                        e.setOptions(this, b)
                },
                onAdd: function(a) {
                    this._crs = this.options.crs || a.options.crs,
                        this._wmsVersion = parseFloat(this.wmsParams.version);
                    var b = this._wmsVersion >= 1.3 ? "crs": "srs";
                    this.wmsParams[b] = this._crs.code,
                        e.TileLayer.prototype.onAdd.call(this, a)
                },
                getTileUrl: function(a) {
                    var b = this._map,
                        c = this.options.tileSize,
                        d = a.multiplyBy(c),
                        f = d.add([c, c]),
                        g = this._crs.project(b.unproject(d, a.z)),
                        h = this._crs.project(b.unproject(f, a.z)),
                        i = this._wmsVersion >= 1.3 && this._crs === e.CRS.EPSG4326 ? [h.y, g.x, g.y, h.x].join(",") : [g.x, h.y, h.x, g.y].join(","),
                        j = e.Util.template(this._url, {
                            s: this._getSubdomain(a)
                        });
                    return j + e.Util.getParamString(this.wmsParams, j, !0) + "&BBOX=" + i
                },
                setParams: function(a, b) {
                    return e.extend(this.wmsParams, a),
                    b || this.redraw(),
                        this
                }
            }),
            e.tileLayer.wms = function(a, b) {
                return new e.TileLayer.WMS(a, b)
            },
            e.TileLayer.Canvas = e.TileLayer.extend({
                options: {
                    async: !1
                },
                initialize: function(a) {
                    e.setOptions(this, a)
                },
                redraw: function() {
                    this._map && (this._reset({
                        hard: !0
                    }), this._update());
                    for (var a in this._tiles) this._redrawTile(this._tiles[a]);
                    return this
                },
                _redrawTile: function(a) {
                    this.drawTile(a, a._tilePoint, this._map._zoom)
                },
                _createTile: function() {
                    var a = e.DomUtil.create("canvas", "leaflet-tile");
                    return a.width = a.height = this.options.tileSize,
                        a.onselectstart = a.onmousemove = e.Util.falseFn,
                        a
                },
                _loadTile: function(a, b) {
                    a._layer = this,
                        a._tilePoint = b,
                        this._redrawTile(a),
                    this.options.async || this.tileDrawn(a)
                },
                drawTile: function() {},
                tileDrawn: function(a) {
                    this._tileOnLoad.call(a)
                }
            }),
            e.tileLayer.canvas = function(a) {
                return new e.TileLayer.Canvas(a)
            },
            e.ImageOverlay = e.Class.extend({
                includes: e.Mixin.Events,
                options: {
                    opacity: 1
                },
                initialize: function(a, b, c) {
                    this._url = a,
                        this._bounds = e.latLngBounds(b),
                        e.setOptions(this, c)
                },
                onAdd: function(a) {
                    this._map = a,
                    this._image || this._initImage(),
                        a._panes.overlayPane.appendChild(this._image),
                        a.on("viewreset", this._reset, this),
                    a.options.zoomAnimation && e.Browser.any3d && a.on("zoomanim", this._animateZoom, this),
                        this._reset()
                },
                onRemove: function(a) {
                    a.getPanes().overlayPane.removeChild(this._image),
                        a.off("viewreset", this._reset, this),
                    a.options.zoomAnimation && a.off("zoomanim", this._animateZoom, this)
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                setOpacity: function(a) {
                    return this.options.opacity = a,
                        this._updateOpacity(),
                        this
                },
                bringToFront: function() {
                    return this._image && this._map._panes.overlayPane.appendChild(this._image),
                        this
                },
                bringToBack: function() {
                    var a = this._map._panes.overlayPane;
                    return this._image && a.insertBefore(this._image, a.firstChild),
                        this
                },
                setUrl: function(a) {
                    this._url = a,
                        this._image.src = this._url
                },
                getAttribution: function() {
                    return this.options.attribution
                },
                _initImage: function() {
                    this._image = e.DomUtil.create("img", "leaflet-image-layer"),
                        this._map.options.zoomAnimation && e.Browser.any3d ? e.DomUtil.addClass(this._image, "leaflet-zoom-animated") : e.DomUtil.addClass(this._image, "leaflet-zoom-hide"),
                        this._updateOpacity(),
                        e.extend(this._image, {
                            galleryimg: "no",
                            onselectstart: e.Util.falseFn,
                            onmousemove: e.Util.falseFn,
                            onload: e.bind(this._onImageLoad, this),
                            src: this._url
                        })
                },
                _animateZoom: function(a) {
                    var b = this._map,
                        c = this._image,
                        d = b.getZoomScale(a.zoom),
                        f = this._bounds.getNorthWest(),
                        g = this._bounds.getSouthEast(),
                        h = b._latLngToNewLayerPoint(f, a.zoom, a.center),
                        i = b._latLngToNewLayerPoint(g, a.zoom, a.center)._subtract(h),
                        j = h._add(i._multiplyBy(.5 * (1 - 1 / d)));
                    c.style[e.DomUtil.TRANSFORM] = e.DomUtil.getTranslateString(j) + " scale(" + d + ") "
                },
                _reset: function() {
                    var a = this._image,
                        b = this._map.latLngToLayerPoint(this._bounds.getNorthWest()),
                        c = this._map.latLngToLayerPoint(this._bounds.getSouthEast())._subtract(b);
                    e.DomUtil.setPosition(a, b),
                        a.style.width = c.x + "px",
                        a.style.height = c.y + "px"
                },
                _onImageLoad: function() {
                    this.fire("load")
                },
                _updateOpacity: function() {
                    e.DomUtil.setOpacity(this._image, this.options.opacity)
                }
            }),
            e.imageOverlay = function(a, b, c) {
                return new e.ImageOverlay(a, b, c)
            },
            e.Icon = e.Class.extend({
                options: {
                    className: ""
                },
                initialize: function(a) {
                    e.setOptions(this, a)
                },
                createIcon: function(a) {
                    return this._createIcon("icon", a)
                },
                createShadow: function(a) {
                    return this._createIcon("shadow", a)
                },
                _createIcon: function(a, b) {
                    var c = this._getIconUrl(a);
                    if (!c) {
                        if ("icon" === a) throw new Error("iconUrl not set in Icon options (see the docs).");
                        return null
                    }
                    var d;
                    return d = b && "IMG" === b.tagName ? this._createImg(c, b) : this._createImg(c),
                        this._setIconStyles(d, a),
                        d
                },
                _setIconStyles: function(a, b) {
                    var c, d = this.options,
                        f = e.point(d[b + "Size"]);
                    c = "shadow" === b ? e.point(d.shadowAnchor || d.iconAnchor) : e.point(d.iconAnchor),
                    !c && f && (c = f.divideBy(2, !0)),
                        a.className = "leaflet-marker-" + b + " " + d.className,
                    c && (a.style.marginLeft = -c.x + "px", a.style.marginTop = -c.y + "px"),
                    f && (a.style.width = f.x + "px", a.style.height = f.y + "px")
                },
                _createImg: function(a, c) {
                    return c = c || b.createElement("img"),
                        c.src = a,
                        c
                },
                _getIconUrl: function(a) {
                    return e.Browser.retina && this.options[a + "RetinaUrl"] ? this.options[a + "RetinaUrl"] : this.options[a + "Url"]
                }
            }),
            e.icon = function(a) {
                return new e.Icon(a)
            },
            e.Icon.Default = e.Icon.extend({
                options: {
                    iconSize: [25, 41],
                    iconAnchor: [12, 41],
                    popupAnchor: [1, -34],
                    shadowSize: [41, 41]
                },
                _getIconUrl: function(a) {
                    var b = a + "Url";
                    if (this.options[b]) return this.options[b];
                    e.Browser.retina && "icon" === a && (a += "-2x");
                    var c = e.Icon.Default.imagePath;
                    if (!c) throw new Error("Couldn't autodetect L.Icon.Default.imagePath, set it manually.");
                    return c + "/marker-" + a + ".png"
                }
            }),
            e.Icon.Default.imagePath = function() {
                var a, c, d, e, f, g = b.getElementsByTagName("script"),
                    h = /[\/^]leaflet[\-\._]?([\w\-\._]*)\.js\??/;
                for (a = 0, c = g.length; c > a; a++) if (d = g[a].src, e = d.match(h)) return f = d.split(h)[0],
                (f ? f + "/": "") + "images"
            } (),
            e.Marker = e.Class.extend({
                includes: e.Mixin.Events,
                options: {
                    icon: new e.Icon.Default,
                    title: "",
                    alt: "",
                    clickable: !0,
                    draggable: !1,
                    keyboard: !0,
                    zIndexOffset: 0,
                    opacity: 1,
                    riseOnHover: !1,
                    riseOffset: 250
                },
                initialize: function(a, b) {
                    e.setOptions(this, b),
                        this._latlng = e.latLng(a)
                },
                onAdd: function(a) {
                    this._map = a,
                        a.on("viewreset", this.update, this),
                        this._initIcon(),
                        this.update(),
                        this.fire("add"),
                    a.options.zoomAnimation && a.options.markerZoomAnimation && a.on("zoomanim", this._animateZoom, this)
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                onRemove: function(a) {
                    this.dragging && this.dragging.disable(),
                        this._removeIcon(),
                        this._removeShadow(),
                        this.fire("remove"),
                        a.off({
                                viewreset: this.update,
                                zoomanim: this._animateZoom
                            },
                            this),
                        this._map = null
                },
                getLatLng: function() {
                    return this._latlng
                },
                setLatLng: function(a) {
                    return this._latlng = e.latLng(a),
                        this.update(),
                        this.fire("move", {
                            latlng: this._latlng
                        })
                },
                setZIndexOffset: function(a) {
                    return this.options.zIndexOffset = a,
                        this.update(),
                        this
                },
                setIcon: function(a) {
                    return this.options.icon = a,
                    this._map && (this._initIcon(), this.update()),
                    this._popup && this.bindPopup(this._popup),
                        this
                },
                update: function() {
                    if (this._icon) {
                        var a = this._map.latLngToLayerPoint(this._latlng).round();
                        this._setPos(a)
                    }
                    return this
                },
                _initIcon: function() {
                    var a = this.options,
                        b = this._map,
                        c = b.options.zoomAnimation && b.options.markerZoomAnimation,
                        d = c ? "leaflet-zoom-animated": "leaflet-zoom-hide",
                        f = a.icon.createIcon(this._icon),
                        g = !1;
                    f !== this._icon && (this._icon && this._removeIcon(), g = !0, a.title && (f.title = a.title), a.alt && (f.alt = a.alt)),
                        e.DomUtil.addClass(f, d),
                    a.keyboard && (f.tabIndex = "0"),
                        this._icon = f,
                        this._initInteraction(),
                    a.riseOnHover && e.DomEvent.on(f, "mouseover", this._bringToFront, this).on(f, "mouseout", this._resetZIndex, this);
                    var h = a.icon.createShadow(this._shadow),
                        i = !1;
                    h !== this._shadow && (this._removeShadow(), i = !0),
                    h && e.DomUtil.addClass(h, d),
                        this._shadow = h,
                    a.opacity < 1 && this._updateOpacity();
                    var j = this._map._panes;
                    g && j.markerPane.appendChild(this._icon),
                    h && i && j.shadowPane.appendChild(this._shadow)
                },
                _removeIcon: function() {
                    this.options.riseOnHover && e.DomEvent.off(this._icon, "mouseover", this._bringToFront).off(this._icon, "mouseout", this._resetZIndex),
                        this._map._panes.markerPane.removeChild(this._icon),
                        this._icon = null
                },
                _removeShadow: function() {
                    this._shadow && this._map._panes.shadowPane.removeChild(this._shadow),
                        this._shadow = null
                },
                _setPos: function(a) {
                    e.DomUtil.setPosition(this._icon, a),
                    this._shadow && e.DomUtil.setPosition(this._shadow, a),
                        this._zIndex = a.y + this.options.zIndexOffset,
                        this._resetZIndex()
                },
                _updateZIndex: function(a) {
                    this._icon.style.zIndex = this._zIndex + a
                },
                _animateZoom: function(a) {
                    var b = this._map._latLngToNewLayerPoint(this._latlng, a.zoom, a.center).round();
                    this._setPos(b)
                },
                _initInteraction: function() {
                    if (this.options.clickable) {
                        var a = this._icon,
                            b = ["dblclick", "mousedown", "mouseover", "mouseout", "contextmenu"];
                        e.DomUtil.addClass(a, "leaflet-clickable"),
                            e.DomEvent.on(a, "click", this._onMouseClick, this),
                            e.DomEvent.on(a, "keypress", this._onKeyPress, this);
                        for (var c = 0; c < b.length; c++) e.DomEvent.on(a, b[c], this._fireMouseEvent, this);
                        e.Handler.MarkerDrag && (this.dragging = new e.Handler.MarkerDrag(this), this.options.draggable && this.dragging.enable())
                    }
                },
                _onMouseClick: function(a) {
                    var b = this.dragging && this.dragging.moved(); (this.hasEventListeners(a.type) || b) && e.DomEvent.stopPropagation(a),
                    b || (this.dragging && this.dragging._enabled || !this._map.dragging || !this._map.dragging.moved()) && this.fire(a.type, {
                        originalEvent: a,
                        latlng: this._latlng
                    })
                },
                _onKeyPress: function(a) {
                    13 === a.keyCode && this.fire("click", {
                        originalEvent: a,
                        latlng: this._latlng
                    })
                },
                _fireMouseEvent: function(a) {
                    this.fire(a.type, {
                        originalEvent: a,
                        latlng: this._latlng
                    }),
                    "contextmenu" === a.type && this.hasEventListeners(a.type) && e.DomEvent.preventDefault(a),
                        "mousedown" !== a.type ? e.DomEvent.stopPropagation(a) : e.DomEvent.preventDefault(a)
                },
                setOpacity: function(a) {
                    return this.options.opacity = a,
                    this._map && this._updateOpacity(),
                        this
                },
                _updateOpacity: function() {
                    e.DomUtil.setOpacity(this._icon, this.options.opacity),
                    this._shadow && e.DomUtil.setOpacity(this._shadow, this.options.opacity)
                },
                _bringToFront: function() {
                    this._updateZIndex(this.options.riseOffset)
                },
                _resetZIndex: function() {
                    this._updateZIndex(0)
                }
            }),
            e.marker = function(a, b) {
                return new e.Marker(a, b)
            },
            e.DivIcon = e.Icon.extend({
                options: {
                    iconSize: [12, 12],
                    className: "leaflet-div-icon",
                    html: !1
                },
                createIcon: function(a) {
                    var c = a && "DIV" === a.tagName ? a: b.createElement("div"),
                        d = this.options;
                    return d.html !== !1 ? c.innerHTML = d.html: c.innerHTML = "",
                    d.bgPos && (c.style.backgroundPosition = -d.bgPos.x + "px " + -d.bgPos.y + "px"),
                        this._setIconStyles(c, "icon"),
                        c
                },
                createShadow: function() {
                    return null
                }
            }),
            e.divIcon = function(a) {
                return new e.DivIcon(a)
            },
            e.Map.mergeOptions({
                closePopupOnClick: !0
            }),
            e.Popup = e.Class.extend({
                includes: e.Mixin.Events,
                options: {
                    minWidth: 50,
                    maxWidth: 300,
                    autoPan: !0,
                    closeButton: !0,
                    offset: [0, 7],
                    autoPanPadding: [5, 5],
                    keepInView: !1,
                    className: "",
                    zoomAnimation: !0
                },
                initialize: function(a, b) {
                    e.setOptions(this, a),
                        this._source = b,
                        this._animated = e.Browser.any3d && this.options.zoomAnimation,
                        this._isOpen = !1
                },
                onAdd: function(a) {
                    this._map = a,
                    this._container || this._initLayout();
                    var b = a.options.fadeAnimation;
                    b && e.DomUtil.setOpacity(this._container, 0),
                        a._panes.popupPane.appendChild(this._container),
                        a.on(this._getEvents(), this),
                        this.update(),
                    b && e.DomUtil.setOpacity(this._container, 1),
                        this.fire("open"),
                        a.fire("popupopen", {
                            popup: this
                        }),
                    this._source && this._source.fire("popupopen", {
                        popup: this
                    })
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                openOn: function(a) {
                    return a.openPopup(this),
                        this
                },
                onRemove: function(a) {
                    a._panes.popupPane.removeChild(this._container),
                        e.Util.falseFn(this._container.offsetWidth),
                        a.off(this._getEvents(), this),
                    a.options.fadeAnimation && e.DomUtil.setOpacity(this._container, 0),
                        this._map = null,
                        this.fire("close"),
                        a.fire("popupclose", {
                            popup: this
                        }),
                    this._source && this._source.fire("popupclose", {
                        popup: this
                    })
                },
                getLatLng: function() {
                    return this._latlng
                },
                setLatLng: function(a) {
                    return this._latlng = e.latLng(a),
                    this._map && (this._updatePosition(), this._adjustPan()),
                        this
                },
                getContent: function() {
                    return this._content
                },
                setContent: function(a) {
                    return this._content = a,
                        this.update(),
                        this
                },
                update: function() {
                    this._map && (this._container.style.visibility = "hidden", this._updateContent(), this._updateLayout(), this._updatePosition(), this._container.style.visibility = "", this._adjustPan())
                },
                _getEvents: function() {
                    var a = {
                        viewreset: this._updatePosition
                    };
                    return this._animated && (a.zoomanim = this._zoomAnimation),
                    ("closeOnClick" in this.options ? this.options.closeOnClick: this._map.options.closePopupOnClick) && (a.preclick = this._close),
                    this.options.keepInView && (a.moveend = this._adjustPan),
                        a
                },
                _close: function() {
                    this._map && this._map.closePopup(this)
                },
                _initLayout: function() {
                    var a, b = "leaflet-popup",
                        c = b + " " + this.options.className + " leaflet-zoom-" + (this._animated ? "animated": "hide"),
                        d = this._container = e.DomUtil.create("div", c);
                    this.options.closeButton && (a = this._closeButton = e.DomUtil.create("a", b + "-close-button", d), a.href = "#close", a.innerHTML = "&#215;", e.DomEvent.disableClickPropagation(a), e.DomEvent.on(a, "click", this._onCloseButtonClick, this));
                    var f = this._wrapper = e.DomUtil.create("div", b + "-content-wrapper", d);
                    e.DomEvent.disableClickPropagation(f),
                        this._contentNode = e.DomUtil.create("div", b + "-content", f),
                        e.DomEvent.disableScrollPropagation(this._contentNode),
                        e.DomEvent.on(f, "contextmenu", e.DomEvent.stopPropagation),
                        this._tipContainer = e.DomUtil.create("div", b + "-tip-container", d),
                        this._tip = e.DomUtil.create("div", b + "-tip", this._tipContainer)
                },
                _updateContent: function() {
                    if (this._content) {
                        if ("string" == typeof this._content) this._contentNode.innerHTML = this._content;
                        else {
                            for (; this._contentNode.hasChildNodes();) this._contentNode.removeChild(this._contentNode.firstChild);
                            this._contentNode.appendChild(this._content)
                        }
                        this.fire("contentupdate")
                    }
                },
                _updateLayout: function() {
                    var a = this._contentNode,
                        b = a.style;
                    b.width = "",
                        b.whiteSpace = "nowrap";
                    var c = a.offsetWidth;
                    c = Math.min(c, this.options.maxWidth),
                        c = Math.max(c, this.options.minWidth),
                        b.width = c + 1 + "px",
                        b.whiteSpace = "",
                        b.height = "";
                    var d = a.offsetHeight,
                        f = this.options.maxHeight,
                        g = "leaflet-popup-scrolled";
                    f && d > f ? (b.height = f + "px", e.DomUtil.addClass(a, g)) : e.DomUtil.removeClass(a, g),
                        this._containerWidth = this._container.offsetWidth
                },
                _updatePosition: function() {
                    if (this._map) {
                        var a = this._map.latLngToLayerPoint(this._latlng),
                            b = this._animated,
                            c = e.point(this.options.offset);
                        b && e.DomUtil.setPosition(this._container, a),
                            this._containerBottom = -c.y - (b ? 0 : a.y),
                            this._containerLeft = -Math.round(this._containerWidth / 2) + c.x + (b ? 0 : a.x),
                            this._container.style.bottom = this._containerBottom + "px",
                            this._container.style.left = this._containerLeft + "px"
                    }
                },
                _zoomAnimation: function(a) {
                    var b = this._map._latLngToNewLayerPoint(this._latlng, a.zoom, a.center);
                    e.DomUtil.setPosition(this._container, b)
                },
                _adjustPan: function() {
                    if (this.options.autoPan) {
                        var a = this._map,
                            b = this._container.offsetHeight,
                            c = this._containerWidth,
                            d = new e.Point(this._containerLeft, -b - this._containerBottom);
                        this._animated && d._add(e.DomUtil.getPosition(this._container));
                        var f = a.layerPointToContainerPoint(d),
                            g = e.point(this.options.autoPanPadding),
                            h = e.point(this.options.autoPanPaddingTopLeft || g),
                            i = e.point(this.options.autoPanPaddingBottomRight || g),
                            j = a.getSize(),
                            k = 0,
                            l = 0;
                        f.x + c + i.x > j.x && (k = f.x + c - j.x + i.x),
                        f.x - k - h.x < 0 && (k = f.x - h.x),
                        f.y + b + i.y > j.y && (l = f.y + b - j.y + i.y),
                        f.y - l - h.y < 0 && (l = f.y - h.y),
                        (k || l) && a.fire("autopanstart").panBy([k, l])
                    }
                },
                _onCloseButtonClick: function(a) {
                    this._close(),
                        e.DomEvent.stop(a)
                }
            }),
            e.popup = function(a, b) {
                return new e.Popup(a, b)
            },
            e.Map.include({
                openPopup: function(a, b, c) {
                    if (this.closePopup(), !(a instanceof e.Popup)) {
                        var d = a;
                        a = new e.Popup(c).setLatLng(b).setContent(d)
                    }
                    return a._isOpen = !0,
                        this._popup = a,
                        this.addLayer(a)
                },
                closePopup: function(a) {
                    return a && a !== this._popup || (a = this._popup, this._popup = null),
                    a && (this.removeLayer(a), a._isOpen = !1),
                        this
                }
            }),
            e.Marker.include({
                openPopup: function() {
                    return this._popup && this._map && !this._map.hasLayer(this._popup) && (this._popup.setLatLng(this._latlng), this._map.openPopup(this._popup)),
                        this
                },
                closePopup: function() {
                    return this._popup && this._popup._close(),
                        this
                },
                togglePopup: function() {
                    return this._popup && (this._popup._isOpen ? this.closePopup() : this.openPopup()),
                        this
                },
                bindPopup: function(a, b) {
                    var c = e.point(this.options.icon.options.popupAnchor || [0, 0]);
                    return c = c.add(e.Popup.prototype.options.offset),
                    b && b.offset && (c = c.add(b.offset)),
                        b = e.extend({
                                offset: c
                            },
                            b),
                    this._popupHandlersAdded || (this.on("click", this.togglePopup, this).on("remove", this.closePopup, this).on("move", this._movePopup, this), this._popupHandlersAdded = !0),
                        a instanceof e.Popup ? (e.setOptions(a, b), this._popup = a) : this._popup = new e.Popup(b, this).setContent(a),
                        this
                },
                setPopupContent: function(a) {
                    return this._popup && this._popup.setContent(a),
                        this
                },
                unbindPopup: function() {
                    return this._popup && (this._popup = null, this.off("click", this.togglePopup, this).off("remove", this.closePopup, this).off("move", this._movePopup, this), this._popupHandlersAdded = !1),
                        this
                },
                getPopup: function() {
                    return this._popup
                },
                _movePopup: function(a) {
                    this._popup.setLatLng(a.latlng)
                }
            }),
            e.LayerGroup = e.Class.extend({
                initialize: function(a) {
                    this._layers = {};
                    var b, c;
                    if (a) for (b = 0, c = a.length; c > b; b++) this.addLayer(a[b])
                },
                addLayer: function(a) {
                    var b = this.getLayerId(a);
                    return this._layers[b] = a,
                    this._map && this._map.addLayer(a),
                        this
                },
                removeLayer: function(a) {
                    var b = a in this._layers ? a: this.getLayerId(a);
                    return this._map && this._layers[b] && this._map.removeLayer(this._layers[b]),
                        delete this._layers[b],
                        this
                },
                hasLayer: function(a) {
                    return a ? a in this._layers || this.getLayerId(a) in this._layers: !1
                },
                clearLayers: function() {
                    return this.eachLayer(this.removeLayer, this),
                        this
                },
                invoke: function(a) {
                    var b, c, d = Array.prototype.slice.call(arguments, 1);
                    for (b in this._layers) c = this._layers[b],
                    c[a] && c[a].apply(c, d);
                    return this
                },
                onAdd: function(a) {
                    this._map = a,
                        this.eachLayer(a.addLayer, a)
                },
                onRemove: function(a) {
                    this.eachLayer(a.removeLayer, a),
                        this._map = null
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                eachLayer: function(a, b) {
                    for (var c in this._layers) a.call(b, this._layers[c]);
                    return this
                },
                getLayer: function(a) {
                    return this._layers[a]
                },
                getLayers: function() {
                    var a = [];
                    for (var b in this._layers) a.push(this._layers[b]);
                    return a
                },
                setZIndex: function(a) {
                    return this.invoke("setZIndex", a)
                },
                getLayerId: function(a) {
                    return e.stamp(a)
                }
            }),
            e.layerGroup = function(a) {
                return new e.LayerGroup(a)
            },
            e.FeatureGroup = e.LayerGroup.extend({
                includes: e.Mixin.Events,
                statics: {
                    EVENTS: "click dblclick mouseover mouseout mousemove contextmenu popupopen popupclose"
                },
                addLayer: function(a) {
                    return this.hasLayer(a) ? this: ("on" in a && a.on(e.FeatureGroup.EVENTS, this._propagateEvent, this), e.LayerGroup.prototype.addLayer.call(this, a), this._popupContent && a.bindPopup && a.bindPopup(this._popupContent, this._popupOptions), this.fire("layeradd", {
                        layer: a
                    }))
                },
                removeLayer: function(a) {
                    return this.hasLayer(a) ? (a in this._layers && (a = this._layers[a]), a.off(e.FeatureGroup.EVENTS, this._propagateEvent, this), e.LayerGroup.prototype.removeLayer.call(this, a), this._popupContent && this.invoke("unbindPopup"), this.fire("layerremove", {
                        layer: a
                    })) : this
                },
                bindPopup: function(a, b) {
                    return this._popupContent = a,
                        this._popupOptions = b,
                        this.invoke("bindPopup", a, b)
                },
                openPopup: function(a) {
                    for (var b in this._layers) {
                        this._layers[b].openPopup(a);
                        break
                    }
                    return this
                },
                setStyle: function(a) {
                    return this.invoke("setStyle", a)
                },
                bringToFront: function() {
                    return this.invoke("bringToFront")
                },
                bringToBack: function() {
                    return this.invoke("bringToBack")
                },
                getBounds: function() {
                    var a = new e.LatLngBounds;
                    return this.eachLayer(function(b) {
                        a.extend(b instanceof e.Marker ? b.getLatLng() : b.getBounds())
                    }),
                        a
                },
                _propagateEvent: function(a) {
                    a = e.extend({},
                        a, {
                            layer: a.target,
                            target: this
                        }),
                        this.fire(a.type, a)
                }
            }),
            e.featureGroup = function(a) {
                return new e.FeatureGroup(a)
            },
            e.Path = e.Class.extend({
                includes: [e.Mixin.Events],
                statics: {
                    CLIP_PADDING: function() {
                        var b = e.Browser.mobile ? 1280 : 2e3,
                            c = (b / Math.max(a.outerWidth, a.outerHeight) - 1) / 2;
                        return Math.max(0, Math.min(.5, c))
                    } ()
                },
                options: {
                    stroke: !0,
                    color: "#0033ff",
                    dashArray: null,
                    lineCap: null,
                    lineJoin: null,
                    weight: 5,
                    opacity: .5,
                    fill: !1,
                    fillColor: null,
                    fillOpacity: .2,
                    clickable: !0
                },
                initialize: function(a) {
                    e.setOptions(this, a)
                },
                onAdd: function(a) {
                    this._map = a,
                    this._container || (this._initElements(), this._initEvents()),
                        this.projectLatlngs(),
                        this._updatePath(),
                    this._container && this._map._pathRoot.appendChild(this._container),
                        this.fire("add"),
                        a.on({
                                viewreset: this.projectLatlngs,
                                moveend: this._updatePath
                            },
                            this)
                },
                addTo: function(a) {
                    return a.addLayer(this),
                        this
                },
                onRemove: function(a) {
                    a._pathRoot.removeChild(this._container),
                        this.fire("remove"),
                        this._map = null,
                    e.Browser.vml && (this._container = null, this._stroke = null, this._fill = null),
                        a.off({
                                viewreset: this.projectLatlngs,
                                moveend: this._updatePath
                            },
                            this)
                },
                projectLatlngs: function() {},
                setStyle: function(a) {
                    return e.setOptions(this, a),
                    this._container && this._updateStyle(),
                        this
                },
                redraw: function() {
                    return this._map && (this.projectLatlngs(), this._updatePath()),
                        this
                }
            }),
            e.Map.include({
                _updatePathViewport: function() {
                    var a = e.Path.CLIP_PADDING,
                        b = this.getSize(),
                        c = e.DomUtil.getPosition(this._mapPane),
                        d = c.multiplyBy( - 1)._subtract(b.multiplyBy(a)._round()),
                        f = d.add(b.multiplyBy(1 + 2 * a)._round());
                    this._pathViewport = new e.Bounds(d, f)
                }
            }),
            e.Path.SVG_NS = "http://www.w3.org/2000/svg",
            e.Browser.svg = !(!b.createElementNS || !b.createElementNS(e.Path.SVG_NS, "svg").createSVGRect),
            e.Path = e.Path.extend({
                statics: {
                    SVG: e.Browser.svg
                },
                bringToFront: function() {
                    var a = this._map._pathRoot,
                        b = this._container;
                    return b && a.lastChild !== b && a.appendChild(b),
                        this
                },
                bringToBack: function() {
                    var a = this._map._pathRoot,
                        b = this._container,
                        c = a.firstChild;
                    return b && c !== b && a.insertBefore(b, c),
                        this
                },
                getPathString: function() {},
                _createElement: function(a) {
                    return b.createElementNS(e.Path.SVG_NS, a)
                },
                _initElements: function() {
                    this._map._initPathRoot(),
                        this._initPath(),
                        this._initStyle()
                },
                _initPath: function() {
                    this._container = this._createElement("g"),
                        this._path = this._createElement("path"),
                    this.options.className && e.DomUtil.addClass(this._path, this.options.className),
                        this._container.appendChild(this._path)
                },
                _initStyle: function() {
                    this.options.stroke && (this._path.setAttribute("stroke-linejoin", "round"), this._path.setAttribute("stroke-linecap", "round")),
                    this.options.fill && this._path.setAttribute("fill-rule", "evenodd"),
                    this.options.pointerEvents && this._path.setAttribute("pointer-events", this.options.pointerEvents),
                    this.options.clickable || this.options.pointerEvents || this._path.setAttribute("pointer-events", "none"),
                        this._updateStyle()
                },
                _updateStyle: function() {
                    this.options.stroke ? (this._path.setAttribute("stroke", this.options.color), this._path.setAttribute("stroke-opacity", this.options.opacity), this._path.setAttribute("stroke-width", this.options.weight), this.options.dashArray ? this._path.setAttribute("stroke-dasharray", this.options.dashArray) : this._path.removeAttribute("stroke-dasharray"), this.options.lineCap && this._path.setAttribute("stroke-linecap", this.options.lineCap), this.options.lineJoin && this._path.setAttribute("stroke-linejoin", this.options.lineJoin)) : this._path.setAttribute("stroke", "none"),
                        this.options.fill ? (this._path.setAttribute("fill", this.options.fillColor || this.options.color), this._path.setAttribute("fill-opacity", this.options.fillOpacity)) : this._path.setAttribute("fill", "none")
                },
                _updatePath: function() {
                    var a = this.getPathString();
                    a || (a = "M0 0"),
                        this._path.setAttribute("d", a)
                },
                _initEvents: function() {
                    if (this.options.clickable) { (e.Browser.svg || !e.Browser.vml) && e.DomUtil.addClass(this._path, "leaflet-clickable"),
                        e.DomEvent.on(this._container, "click", this._onMouseClick, this);
                        for (var a = ["dblclick", "mousedown", "mouseover", "mouseout", "mousemove", "contextmenu"], b = 0; b < a.length; b++) e.DomEvent.on(this._container, a[b], this._fireMouseEvent, this)
                    }
                },
                _onMouseClick: function(a) {
                    this._map.dragging && this._map.dragging.moved() || this._fireMouseEvent(a)
                },
                _fireMouseEvent: function(a) {
                    if (this.hasEventListeners(a.type)) {
                        var b = this._map,
                            c = b.mouseEventToContainerPoint(a),
                            d = b.containerPointToLayerPoint(c),
                            f = b.layerPointToLatLng(d);
                        this.fire(a.type, {
                            latlng: f,
                            layerPoint: d,
                            containerPoint: c,
                            originalEvent: a
                        }),
                        "contextmenu" === a.type && e.DomEvent.preventDefault(a),
                        "mousemove" !== a.type && e.DomEvent.stopPropagation(a)
                    }
                }
            }),
            e.Map.include({
                _initPathRoot: function() {
                    this._pathRoot || (this._pathRoot = e.Path.prototype._createElement("svg"), this._panes.overlayPane.appendChild(this._pathRoot), this.options.zoomAnimation && e.Browser.any3d ? (e.DomUtil.addClass(this._pathRoot, "leaflet-zoom-animated"), this.on({
                        zoomanim: this._animatePathZoom,
                        zoomend: this._endPathZoom
                    })) : e.DomUtil.addClass(this._pathRoot, "leaflet-zoom-hide"), this.on("moveend", this._updateSvgViewport), this._updateSvgViewport())
                },
                _animatePathZoom: function(a) {
                    var b = this.getZoomScale(a.zoom),
                        c = this._getCenterOffset(a.center)._multiplyBy( - b)._add(this._pathViewport.min);
                    this._pathRoot.style[e.DomUtil.TRANSFORM] = e.DomUtil.getTranslateString(c) + " scale(" + b + ") ",
                        this._pathZooming = !0
                },
                _endPathZoom: function() {
                    this._pathZooming = !1
                },
                _updateSvgViewport: function() {
                    if (!this._pathZooming) {
                        this._updatePathViewport();
                        var a = this._pathViewport,
                            b = a.min,
                            c = a.max,
                            d = c.x - b.x,
                            f = c.y - b.y,
                            g = this._pathRoot,
                            h = this._panes.overlayPane;
                        e.Browser.mobileWebkit && h.removeChild(g),
                            e.DomUtil.setPosition(g, b),
                            g.setAttribute("width", d),
                            g.setAttribute("height", f),
                            g.setAttribute("viewBox", [b.x, b.y, d, f].join(" ")),
                        e.Browser.mobileWebkit && h.appendChild(g)
                    }
                }
            }),
            e.Path.include({
                bindPopup: function(a, b) {
                    return a instanceof e.Popup ? this._popup = a: ((!this._popup || b) && (this._popup = new e.Popup(b, this)), this._popup.setContent(a)),
                    this._popupHandlersAdded || (this.on("click", this._openPopup, this).on("remove", this.closePopup, this), this._popupHandlersAdded = !0),
                        this
                },
                unbindPopup: function() {
                    return this._popup && (this._popup = null, this.off("click", this._openPopup).off("remove", this.closePopup), this._popupHandlersAdded = !1),
                        this
                },
                openPopup: function(a) {
                    return this._popup && (a = a || this._latlng || this._latlngs[Math.floor(this._latlngs.length / 2)], this._openPopup({
                        latlng: a
                    })),
                        this
                },
                closePopup: function() {
                    return this._popup && this._popup._close(),
                        this
                },
                _openPopup: function(a) {
                    this._popup.setLatLng(a.latlng),
                        this._map.openPopup(this._popup)
                }
            }),
            e.Browser.vml = !e.Browser.svg &&
                function() {
                    try {
                        var a = b.createElement("div");
                        a.innerHTML = '<v:shape adj="1"/>';
                        var c = a.firstChild;
                        return c.style.behavior = "url(#default#VML)",
                        c && "object" == typeof c.adj
                    } catch(d) {
                        return ! 1
                    }
                } (),
            e.Path = e.Browser.svg || !e.Browser.vml ? e.Path: e.Path.extend({
                statics: {
                    VML: !0,
                    CLIP_PADDING: .02
                },
                _createElement: function() {
                    try {
                        return b.namespaces.add("lvml", "urn:schemas-microsoft-com:vml"),
                            function(a) {
                                return b.createElement("<lvml:" + a + ' class="lvml">')
                            }
                    } catch(a) {
                        return function(a) {
                            return b.createElement("<" + a + ' xmlns="urn:schemas-microsoft.com:vml" class="lvml">')
                        }
                    }
                } (),
                _initPath: function() {
                    var a = this._container = this._createElement("shape");
                    e.DomUtil.addClass(a, "leaflet-vml-shape" + (this.options.className ? " " + this.options.className: "")),
                    this.options.clickable && e.DomUtil.addClass(a, "leaflet-clickable"),
                        a.coordsize = "1 1",
                        this._path = this._createElement("path"),
                        a.appendChild(this._path),
                        this._map._pathRoot.appendChild(a)
                },
                _initStyle: function() {
                    this._updateStyle()
                },
                _updateStyle: function() {
                    var a = this._stroke,
                        b = this._fill,
                        c = this.options,
                        d = this._container;
                    d.stroked = c.stroke,
                        d.filled = c.fill,
                        c.stroke ? (a || (a = this._stroke = this._createElement("stroke"), a.endcap = "round", d.appendChild(a)), a.weight = c.weight + "px", a.color = c.color, a.opacity = c.opacity, c.dashArray ? a.dashStyle = e.Util.isArray(c.dashArray) ? c.dashArray.join(" ") : c.dashArray.replace(/( *, *)/g, " ") : a.dashStyle = "", c.lineCap && (a.endcap = c.lineCap.replace("butt", "flat")), c.lineJoin && (a.joinstyle = c.lineJoin)) : a && (d.removeChild(a), this._stroke = null),
                        c.fill ? (b || (b = this._fill = this._createElement("fill"), d.appendChild(b)), b.color = c.fillColor || c.color, b.opacity = c.fillOpacity) : b && (d.removeChild(b), this._fill = null)
                },
                _updatePath: function() {
                    var a = this._container.style;
                    a.display = "none",
                        this._path.v = this.getPathString() + " ",
                        a.display = ""
                }
            }),
            e.Map.include(e.Browser.svg || !e.Browser.vml ? {}: {
                _initPathRoot: function() {
                    if (!this._pathRoot) {
                        var a = this._pathRoot = b.createElement("div");
                        a.className = "leaflet-vml-container",
                            this._panes.overlayPane.appendChild(a),
                            this.on("moveend", this._updatePathViewport),
                            this._updatePathViewport()
                    }
                }
            }),
            e.Browser.canvas = function() {
                return !! b.createElement("canvas").getContext
            } (),
            e.Path = e.Path.SVG && !a.L_PREFER_CANVAS || !e.Browser.canvas ? e.Path: e.Path.extend({
                statics: {
                    CANVAS: !0,
                    SVG: !1
                },
                redraw: function() {
                    return this._map && (this.projectLatlngs(), this._requestUpdate()),
                        this
                },
                setStyle: function(a) {
                    return e.setOptions(this, a),
                    this._map && (this._updateStyle(), this._requestUpdate()),
                        this
                },
                onRemove: function(a) {
                    a.off("viewreset", this.projectLatlngs, this).off("moveend", this._updatePath, this),
                    this.options.clickable && (this._map.off("click", this._onClick, this), this._map.off("mousemove", this._onMouseMove, this)),
                        this._requestUpdate(),
                        this._map = null
                },
                _requestUpdate: function() {
                    this._map && !e.Path._updateRequest && (e.Path._updateRequest = e.Util.requestAnimFrame(this._fireMapMoveEnd, this._map))
                },
                _fireMapMoveEnd: function() {
                    e.Path._updateRequest = null,
                        this.fire("moveend")
                },
                _initElements: function() {
                    this._map._initPathRoot(),
                        this._ctx = this._map._canvasCtx
                },
                _updateStyle: function() {
                    var a = this.options;
                    a.stroke && (this._ctx.lineWidth = a.weight, this._ctx.strokeStyle = a.color),
                    a.fill && (this._ctx.fillStyle = a.fillColor || a.color)
                },
                _drawPath: function() {
                    var a, b, c, d, f, g;
                    for (this._ctx.beginPath(), a = 0, c = this._parts.length; c > a; a++) {
                        for (b = 0, d = this._parts[a].length; d > b; b++) f = this._parts[a][b],
                            g = (0 === b ? "move": "line") + "To",
                            this._ctx[g](f.x, f.y);
                        this instanceof e.Polygon && this._ctx.closePath()
                    }
                },
                _checkIfEmpty: function() {
                    return ! this._parts.length
                },
                _updatePath: function() {
                    if (!this._checkIfEmpty()) {
                        var a = this._ctx,
                            b = this.options;
                        this._drawPath(),
                            a.save(),
                            this._updateStyle(),
                        b.fill && (a.globalAlpha = b.fillOpacity, a.fill()),
                        b.stroke && (a.globalAlpha = b.opacity, a.stroke()),
                            a.restore()
                    }
                },
                _initEvents: function() {
                    this.options.clickable && (this._map.on("mousemove", this._onMouseMove, this), this._map.on("click", this._onClick, this))
                },
                _onClick: function(a) {
                    this._containsPoint(a.layerPoint) && this.fire("click", a)
                },
                _onMouseMove: function(a) {
                    this._map && !this._map._animatingZoom && (this._containsPoint(a.layerPoint) ? (this._ctx.canvas.style.cursor = "pointer", this._mouseInside = !0, this.fire("mouseover", a)) : this._mouseInside && (this._ctx.canvas.style.cursor = "", this._mouseInside = !1, this.fire("mouseout", a)))
                }
            }),
            e.Map.include(e.Path.SVG && !a.L_PREFER_CANVAS || !e.Browser.canvas ? {}: {
                _initPathRoot: function() {
                    var a, c = this._pathRoot;
                    c || (c = this._pathRoot = b.createElement("canvas"), c.style.position = "absolute", a = this._canvasCtx = c.getContext("2d"), a.lineCap = "round", a.lineJoin = "round", this._panes.overlayPane.appendChild(c), this.options.zoomAnimation && (this._pathRoot.className = "leaflet-zoom-animated", this.on("zoomanim", this._animatePathZoom), this.on("zoomend", this._endPathZoom)), this.on("moveend", this._updateCanvasViewport), this._updateCanvasViewport())
                },
                _updateCanvasViewport: function() {
                    if (!this._pathZooming) {
                        this._updatePathViewport();
                        var a = this._pathViewport,
                            b = a.min,
                            c = a.max.subtract(b),
                            d = this._pathRoot;
                        e.DomUtil.setPosition(d, b),
                            d.width = c.x,
                            d.height = c.y,
                            d.getContext("2d").translate( - b.x, -b.y)
                    }
                }
            }),
            e.LineUtil = {
                simplify: function(a, b) {
                    if (!b || !a.length) return a.slice();
                    var c = b * b;
                    return a = this._reducePoints(a, c),
                        a = this._simplifyDP(a, c)
                },
                pointToSegmentDistance: function(a, b, c) {
                    return Math.sqrt(this._sqClosestPointOnSegment(a, b, c, !0))
                },
                closestPointOnSegment: function(a, b, c) {
                    return this._sqClosestPointOnSegment(a, b, c)
                },
                _simplifyDP: function(a, b) {
                    var d = a.length,
                        e = typeof Uint8Array != c + "" ? Uint8Array: Array,
                        f = new e(d);
                    f[0] = f[d - 1] = 1,
                        this._simplifyDPStep(a, f, b, 0, d - 1);
                    var g, h = [];
                    for (g = 0; d > g; g++) f[g] && h.push(a[g]);
                    return h
                },
                _simplifyDPStep: function(a, b, c, d, e) {
                    var f, g, h, i = 0;
                    for (g = d + 1; e - 1 >= g; g++) h = this._sqClosestPointOnSegment(a[g], a[d], a[e], !0),
                    h > i && (f = g, i = h);
                    i > c && (b[f] = 1, this._simplifyDPStep(a, b, c, d, f), this._simplifyDPStep(a, b, c, f, e))
                },
                _reducePoints: function(a, b) {
                    for (var c = [a[0]], d = 1, e = 0, f = a.length; f > d; d++) this._sqDist(a[d], a[e]) > b && (c.push(a[d]), e = d);
                    return f - 1 > e && c.push(a[f - 1]),
                        c
                },
                clipSegment: function(a, b, c, d) {
                    var e, f, g, h = d ? this._lastCode: this._getBitCode(a, c),
                        i = this._getBitCode(b, c);
                    for (this._lastCode = i;;) {
                        if (! (h | i)) return [a, b];
                        if (h & i) return ! 1;
                        e = h || i,
                            f = this._getEdgeIntersection(a, b, e, c),
                            g = this._getBitCode(f, c),
                            e === h ? (a = f, h = g) : (b = f, i = g)
                    }
                },
                _getEdgeIntersection: function(a, b, c, d) {
                    var f = b.x - a.x,
                        g = b.y - a.y,
                        h = d.min,
                        i = d.max;
                    return 8 & c ? new e.Point(a.x + f * (i.y - a.y) / g, i.y) : 4 & c ? new e.Point(a.x + f * (h.y - a.y) / g, h.y) : 2 & c ? new e.Point(i.x, a.y + g * (i.x - a.x) / f) : 1 & c ? new e.Point(h.x, a.y + g * (h.x - a.x) / f) : void 0
                },
                _getBitCode: function(a, b) {
                    var c = 0;
                    return a.x < b.min.x ? c |= 1 : a.x > b.max.x && (c |= 2),
                        a.y < b.min.y ? c |= 4 : a.y > b.max.y && (c |= 8),
                        c
                },
                _sqDist: function(a, b) {
                    var c = b.x - a.x,
                        d = b.y - a.y;
                    return c * c + d * d
                },
                _sqClosestPointOnSegment: function(a, b, c, d) {
                    var f, g = b.x,
                        h = b.y,
                        i = c.x - g,
                        j = c.y - h,
                        k = i * i + j * j;
                    return k > 0 && (f = ((a.x - g) * i + (a.y - h) * j) / k, f > 1 ? (g = c.x, h = c.y) : f > 0 && (g += i * f, h += j * f)),
                        i = a.x - g,
                        j = a.y - h,
                        d ? i * i + j * j: new e.Point(g, h)
                }
            },
            e.Polyline = e.Path.extend({
                initialize: function(a, b) {
                    e.Path.prototype.initialize.call(this, b),
                        this._latlngs = this._convertLatLngs(a)
                },
                options: {
                    smoothFactor: 1,
                    noClip: !1
                },
                projectLatlngs: function() {
                    this._originalPoints = [];
                    for (var a = 0,
                             b = this._latlngs.length; b > a; a++) this._originalPoints[a] = this._map.latLngToLayerPoint(this._latlngs[a])
                },
                getPathString: function() {
                    for (var a = 0,
                             b = this._parts.length,
                             c = ""; b > a; a++) c += this._getPathPartStr(this._parts[a]);
                    return c
                },
                getLatLngs: function() {
                    return this._latlngs
                },
                setLatLngs: function(a) {
                    return this._latlngs = this._convertLatLngs(a),
                        this.redraw()
                },
                addLatLng: function(a) {
                    return this._latlngs.push(e.latLng(a)),
                        this.redraw()
                },
                spliceLatLngs: function() {
                    var a = [].splice.apply(this._latlngs, arguments);
                    return this._convertLatLngs(this._latlngs, !0),
                        this.redraw(),
                        a
                },
                closestLayerPoint: function(a) {
                    for (var b, c, d = 1 / 0,
                             f = this._parts,
                             g = null,
                             h = 0,
                             i = f.length; i > h; h++) for (var j = f[h], k = 1, l = j.length; l > k; k++) {
                        b = j[k - 1],
                            c = j[k];
                        var m = e.LineUtil._sqClosestPointOnSegment(a, b, c, !0);
                        d > m && (d = m, g = e.LineUtil._sqClosestPointOnSegment(a, b, c))
                    }
                    return g && (g.distance = Math.sqrt(d)),
                        g
                },
                getBounds: function() {
                    return new e.LatLngBounds(this.getLatLngs())
                },
                _convertLatLngs: function(a, b) {
                    var c, d, f = b ? a: [];
                    for (c = 0, d = a.length; d > c; c++) {
                        if (e.Util.isArray(a[c]) && "number" != typeof a[c][0]) return;
                        f[c] = e.latLng(a[c])
                    }
                    return f
                },
                _initEvents: function() {
                    e.Path.prototype._initEvents.call(this)
                },
                _getPathPartStr: function(a) {
                    for (var b, c = e.Path.VML,
                             d = 0,
                             f = a.length,
                             g = ""; f > d; d++) b = a[d],
                    c && b._round(),
                        g += (d ? "L": "M") + b.x + " " + b.y;
                    return g
                },
                _clipPoints: function() {
                    var a, b, c, d = this._originalPoints,
                        f = d.length;
                    if (this.options.noClip) return void(this._parts = [d]);
                    this._parts = [];
                    var g = this._parts,
                        h = this._map._pathViewport,
                        i = e.LineUtil;
                    for (a = 0, b = 0; f - 1 > a; a++) c = i.clipSegment(d[a], d[a + 1], h, a),
                    c && (g[b] = g[b] || [], g[b].push(c[0]), (c[1] !== d[a + 1] || a === f - 2) && (g[b].push(c[1]), b++))
                },
                _simplifyPoints: function() {
                    for (var a = this._parts,
                             b = e.LineUtil,
                             c = 0,
                             d = a.length; d > c; c++) a[c] = b.simplify(a[c], this.options.smoothFactor)
                },
                _updatePath: function() {
                    this._map && (this._clipPoints(), this._simplifyPoints(), e.Path.prototype._updatePath.call(this))
                }
            }),
            e.polyline = function(a, b) {
                return new e.Polyline(a, b)
            },
            e.PolyUtil = {},
            e.PolyUtil.clipPolygon = function(a, b) {
                var c, d, f, g, h, i, j, k, l, m = [1, 4, 2, 8],
                    n = e.LineUtil;
                for (d = 0, j = a.length; j > d; d++) a[d]._code = n._getBitCode(a[d], b);
                for (g = 0; 4 > g; g++) {
                    for (k = m[g], c = [], d = 0, j = a.length, f = j - 1; j > d; f = d++) h = a[d],
                        i = a[f],
                        h._code & k ? i._code & k || (l = n._getEdgeIntersection(i, h, k, b), l._code = n._getBitCode(l, b), c.push(l)) : (i._code & k && (l = n._getEdgeIntersection(i, h, k, b), l._code = n._getBitCode(l, b), c.push(l)), c.push(h));
                    a = c
                }
                return a
            },
            e.Polygon = e.Polyline.extend({
                options: {
                    fill: !0
                },
                initialize: function(a, b) {
                    e.Polyline.prototype.initialize.call(this, a, b),
                        this._initWithHoles(a)
                },
                _initWithHoles: function(a) {
                    var b, c, d;
                    if (a && e.Util.isArray(a[0]) && "number" != typeof a[0][0]) for (this._latlngs = this._convertLatLngs(a[0]), this._holes = a.slice(1), b = 0, c = this._holes.length; c > b; b++) d = this._holes[b] = this._convertLatLngs(this._holes[b]),
                    d[0].equals(d[d.length - 1]) && d.pop();
                    a = this._latlngs,
                    a.length >= 2 && a[0].equals(a[a.length - 1]) && a.pop()
                },
                projectLatlngs: function() {
                    if (e.Polyline.prototype.projectLatlngs.call(this), this._holePoints = [], this._holes) {
                        var a, b, c, d;
                        for (a = 0, c = this._holes.length; c > a; a++) for (this._holePoints[a] = [], b = 0, d = this._holes[a].length; d > b; b++) this._holePoints[a][b] = this._map.latLngToLayerPoint(this._holes[a][b])
                    }
                },
                setLatLngs: function(a) {
                    return a && e.Util.isArray(a[0]) && "number" != typeof a[0][0] ? (this._initWithHoles(a), this.redraw()) : e.Polyline.prototype.setLatLngs.call(this, a)
                },
                _clipPoints: function() {
                    var a = this._originalPoints,
                        b = [];
                    if (this._parts = [a].concat(this._holePoints), !this.options.noClip) {
                        for (var c = 0,
                                 d = this._parts.length; d > c; c++) {
                            var f = e.PolyUtil.clipPolygon(this._parts[c], this._map._pathViewport);
                            f.length && b.push(f)
                        }
                        this._parts = b
                    }
                },
                _getPathPartStr: function(a) {
                    var b = e.Polyline.prototype._getPathPartStr.call(this, a);
                    return b + (e.Browser.svg ? "z": "x")
                }
            }),
            e.polygon = function(a, b) {
                return new e.Polygon(a, b)
            },
            function() {
                function a(a) {
                    return e.FeatureGroup.extend({
                        initialize: function(a, b) {
                            this._layers = {},
                                this._options = b,
                                this.setLatLngs(a)
                        },
                        setLatLngs: function(b) {
                            var c = 0,
                                d = b.length;
                            for (this.eachLayer(function(a) {
                                    d > c ? a.setLatLngs(b[c++]) : this.removeLayer(a)
                                },
                                this); d > c;) this.addLayer(new a(b[c++], this._options));
                            return this
                        },
                        getLatLngs: function() {
                            var a = [];
                            return this.eachLayer(function(b) {
                                a.push(b.getLatLngs())
                            }),
                                a
                        }
                    })
                }
                e.MultiPolyline = a(e.Polyline),
                    e.MultiPolygon = a(e.Polygon),
                    e.multiPolyline = function(a, b) {
                        return new e.MultiPolyline(a, b)
                    },
                    e.multiPolygon = function(a, b) {
                        return new e.MultiPolygon(a, b)
                    }
            } (),
            e.Rectangle = e.Polygon.extend({
                initialize: function(a, b) {
                    e.Polygon.prototype.initialize.call(this, this._boundsToLatLngs(a), b)
                },
                setBounds: function(a) {
                    this.setLatLngs(this._boundsToLatLngs(a))
                },
                _boundsToLatLngs: function(a) {
                    return a = e.latLngBounds(a),
                        [a.getSouthWest(), a.getNorthWest(), a.getNorthEast(), a.getSouthEast()]
                }
            }),
            e.rectangle = function(a, b) {
                return new e.Rectangle(a, b)
            },
            e.Circle = e.Path.extend({
                initialize: function(a, b, c) {
                    e.Path.prototype.initialize.call(this, c),
                        this._latlng = e.latLng(a),
                        this._mRadius = b
                },
                options: {
                    fill: !0
                },
                setLatLng: function(a) {
                    return this._latlng = e.latLng(a),
                        this.redraw()
                },
                setRadius: function(a) {
                    return this._mRadius = a,
                        this.redraw()
                },
                projectLatlngs: function() {
                    var a = this._getLngRadius(),
                        b = this._latlng,
                        c = this._map.latLngToLayerPoint([b.lat, b.lng - a]);
                    this._point = this._map.latLngToLayerPoint(b),
                        this._radius = Math.max(this._point.x - c.x, 1)
                },
                getBounds: function() {
                    var a = this._getLngRadius(),
                        b = this._mRadius / 40075017 * 360,
                        c = this._latlng;
                    return new e.LatLngBounds([c.lat - b, c.lng - a], [c.lat + b, c.lng + a])
                },
                getLatLng: function() {
                    return this._latlng
                },
                getPathString: function() {
                    var a = this._point,
                        b = this._radius;
                    return this._checkIfEmpty() ? "": e.Browser.svg ? "M" + a.x + "," + (a.y - b) + "A" + b + "," + b + ",0,1,1," + (a.x - .1) + "," + (a.y - b) + " z": (a._round(), b = Math.round(b), "AL " + a.x + "," + a.y + " " + b + "," + b + " 0,23592600")
                },
                getRadius: function() {
                    return this._mRadius
                },
                _getLatRadius: function() {
                    return this._mRadius / 40075017 * 360
                },
                _getLngRadius: function() {
                    return this._getLatRadius() / Math.cos(e.LatLng.DEG_TO_RAD * this._latlng.lat)
                },
                _checkIfEmpty: function() {
                    if (!this._map) return ! 1;
                    var a = this._map._pathViewport,
                        b = this._radius,
                        c = this._point;
                    return c.x - b > a.max.x || c.y - b > a.max.y || c.x + b < a.min.x || c.y + b < a.min.y
                }
            }),
            e.circle = function(a, b, c) {
                return new e.Circle(a, b, c)
            },
            e.CircleMarker = e.Circle.extend({
                options: {
                    radius: 10,
                    weight: 2
                },
                initialize: function(a, b) {
                    e.Circle.prototype.initialize.call(this, a, null, b),
                        this._radius = this.options.radius
                },
                projectLatlngs: function() {
                    this._point = this._map.latLngToLayerPoint(this._latlng)
                },
                _updateStyle: function() {
                    e.Circle.prototype._updateStyle.call(this),
                        this.setRadius(this.options.radius)
                },
                setLatLng: function(a) {
                    e.Circle.prototype.setLatLng.call(this, a),
                    this._popup && this._popup._isOpen && this._popup.setLatLng(a)
                },
                setRadius: function(a) {
                    return this.options.radius = this._radius = a,
                        this.redraw()
                },
                getRadius: function() {
                    return this._radius
                }
            }),
            e.circleMarker = function(a, b) {
                return new e.CircleMarker(a, b)
            },
            e.Polyline.include(e.Path.CANVAS ? {
                _containsPoint: function(a, b) {
                    var c, d, f, g, h, i, j, k = this.options.weight / 2;
                    for (e.Browser.touch && (k += 10), c = 0, g = this._parts.length; g > c; c++) for (j = this._parts[c], d = 0, h = j.length, f = h - 1; h > d; f = d++) if ((b || 0 !== d) && (i = e.LineUtil.pointToSegmentDistance(a, j[f], j[d]), k >= i)) return ! 0;
                    return ! 1
                }
            }: {}),
            e.Polygon.include(e.Path.CANVAS ? {
                _containsPoint: function(a) {
                    var b, c, d, f, g, h, i, j, k = !1;
                    if (e.Polyline.prototype._containsPoint.call(this, a, !0)) return ! 0;
                    for (f = 0, i = this._parts.length; i > f; f++) for (b = this._parts[f], g = 0, j = b.length, h = j - 1; j > g; h = g++) c = b[g],
                        d = b[h],
                    c.y > a.y != d.y > a.y && a.x < (d.x - c.x) * (a.y - c.y) / (d.y - c.y) + c.x && (k = !k);
                    return k
                }
            }: {}),
            e.Circle.include(e.Path.CANVAS ? {
                _drawPath: function() {
                    var a = this._point;
                    this._ctx.beginPath(),
                        this._ctx.arc(a.x, a.y, this._radius, 0, 2 * Math.PI, !1)
                },
                _containsPoint: function(a) {
                    var b = this._point,
                        c = this.options.stroke ? this.options.weight / 2 : 0;
                    return a.distanceTo(b) <= this._radius + c
                }
            }: {}),
            e.CircleMarker.include(e.Path.CANVAS ? {
                _updateStyle: function() {
                    e.Path.prototype._updateStyle.call(this)
                }
            }: {}),
            e.GeoJSON = e.FeatureGroup.extend({
                initialize: function(a, b) {
                    e.setOptions(this, b),
                        this._layers = {},
                    a && this.addData(a)
                },
                addData: function(a) {
                    var b, c, d, f = e.Util.isArray(a) ? a: a.features;
                    if (f) {
                        for (b = 0, c = f.length; c > b; b++) d = f[b],
                        (d.geometries || d.geometry || d.features || d.coordinates) && this.addData(f[b]);
                        return this
                    }
                    var g = this.options;
                    if (!g.filter || g.filter(a)) {
                        var h = e.GeoJSON.geometryToLayer(a, g.pointToLayer, g.coordsToLatLng, g);
                        return h.feature = e.GeoJSON.asFeature(a),
                            h.defaultOptions = h.options,
                            this.resetStyle(h),
                        g.onEachFeature && g.onEachFeature(a, h),
                            this.addLayer(h)
                    }
                },
                resetStyle: function(a) {
                    var b = this.options.style;
                    b && (e.Util.extend(a.options, a.defaultOptions), this._setLayerStyle(a, b))
                },
                setStyle: function(a) {
                    this.eachLayer(function(b) {
                            this._setLayerStyle(b, a)
                        },
                        this)
                },
                _setLayerStyle: function(a, b) {
                    "function" == typeof b && (b = b(a.feature)),
                    a.setStyle && a.setStyle(b)
                }
            }),
            e.extend(e.GeoJSON, {
                geometryToLayer: function(a, b, c, d) {
                    var f, g, h, i, j = "Feature" === a.type ? a.geometry: a,
                        k = j.coordinates,
                        l = [];
                    switch (c = c || this.coordsToLatLng, j.type) {
                        case "Point":
                            return f = c(k),
                                b ? b(a, f) : new e.Marker(f);
                        case "MultiPoint":
                            for (h = 0, i = k.length; i > h; h++) f = c(k[h]),
                                l.push(b ? b(a, f) : new e.Marker(f));
                            return new e.FeatureGroup(l);
                        case "LineString":
                            return g = this.coordsToLatLngs(k, 0, c),
                                new e.Polyline(g, d);
                        case "Polygon":
                            if (2 === k.length && !k[1].length) throw new Error("Invalid GeoJSON object.");
                            return g = this.coordsToLatLngs(k, 1, c),
                                new e.Polygon(g, d);
                        case "MultiLineString":
                            return g = this.coordsToLatLngs(k, 1, c),
                                new e.MultiPolyline(g, d);
                        case "MultiPolygon":
                            return g = this.coordsToLatLngs(k, 2, c),
                                new e.MultiPolygon(g, d);
                        case "GeometryCollection":
                            for (h = 0, i = j.geometries.length; i > h; h++) l.push(this.geometryToLayer({
                                    geometry: j.geometries[h],
                                    type: "Feature",
                                    properties: a.properties
                                },
                                b, c, d));
                            return new e.FeatureGroup(l);
                        default:
                            throw new Error("Invalid GeoJSON object.")
                    }
                },
                coordsToLatLng: function(a) {
                    return new e.LatLng(a[1], a[0], a[2])
                },
                coordsToLatLngs: function(a, b, c) {
                    var d, e, f, g = [];
                    for (e = 0, f = a.length; f > e; e++) d = b ? this.coordsToLatLngs(a[e], b - 1, c) : (c || this.coordsToLatLng)(a[e]),
                        g.push(d);
                    return g
                },
                latLngToCoords: function(a) {
                    var b = [a.lng, a.lat];
                    return a.alt !== c && b.push(a.alt),
                        b
                },
                latLngsToCoords: function(a) {
                    for (var b = [], c = 0, d = a.length; d > c; c++) b.push(e.GeoJSON.latLngToCoords(a[c]));
                    return b
                },
                getFeature: function(a, b) {
                    return a.feature ? e.extend({},
                        a.feature, {
                            geometry: b
                        }) : e.GeoJSON.asFeature(b)
                },
                asFeature: function(a) {
                    return "Feature" === a.type ? a: {
                        type: "Feature",
                        properties: {},
                        geometry: a
                    }
                }
            });
        var g = {
            toGeoJSON: function() {
                return e.GeoJSON.getFeature(this, {
                    type: "Point",
                    coordinates: e.GeoJSON.latLngToCoords(this.getLatLng())
                })
            }
        };
        e.Marker.include(g),
            e.Circle.include(g),
            e.CircleMarker.include(g),
            e.Polyline.include({
                toGeoJSON: function() {
                    return e.GeoJSON.getFeature(this, {
                        type: "LineString",
                        coordinates: e.GeoJSON.latLngsToCoords(this.getLatLngs())
                    })
                }
            }),
            e.Polygon.include({
                toGeoJSON: function() {
                    var a, b, c, d = [e.GeoJSON.latLngsToCoords(this.getLatLngs())];
                    if (d[0].push(d[0][0]), this._holes) for (a = 0, b = this._holes.length; b > a; a++) c = e.GeoJSON.latLngsToCoords(this._holes[a]),
                        c.push(c[0]),
                        d.push(c);
                    return e.GeoJSON.getFeature(this, {
                        type: "Polygon",
                        coordinates: d
                    })
                }
            }),
            function() {
                function a(a) {
                    return function() {
                        var b = [];
                        return this.eachLayer(function(a) {
                            b.push(a.toGeoJSON().geometry.coordinates)
                        }),
                            e.GeoJSON.getFeature(this, {
                                type: a,
                                coordinates: b
                            })
                    }
                }
                e.MultiPolyline.include({
                    toGeoJSON: a("MultiLineString")
                }),
                    e.MultiPolygon.include({
                        toGeoJSON: a("MultiPolygon")
                    }),
                    e.LayerGroup.include({
                        toGeoJSON: function() {
                            var b, c = this.feature && this.feature.geometry,
                                d = [];
                            if (c && "MultiPoint" === c.type) return a("MultiPoint").call(this);
                            var f = c && "GeometryCollection" === c.type;
                            return this.eachLayer(function(a) {
                                a.toGeoJSON && (b = a.toGeoJSON(), d.push(f ? b.geometry: e.GeoJSON.asFeature(b)))
                            }),
                                f ? e.GeoJSON.getFeature(this, {
                                    geometries: d,
                                    type: "GeometryCollection"
                                }) : {
                                    type: "FeatureCollection",
                                    features: d
                                }
                        }
                    })
            } (),
            e.geoJson = function(a, b) {
                return new e.GeoJSON(a, b)
            },
            e.DomEvent = {
                addListener: function(a, b, c, d) {
                    var f, g, h, i = e.stamp(c),
                        j = "_leaflet_" + b + i;
                    return a[j] ? this: (f = function(b) {
                        return c.call(d || a, b || e.DomEvent._getEvent())
                    },
                        e.Browser.pointer && 0 === b.indexOf("touch") ? this.addPointerListener(a, b, f, i) : (e.Browser.touch && "dblclick" === b && this.addDoubleTapListener && this.addDoubleTapListener(a, f, i), "addEventListener" in a ? "mousewheel" === b ? (a.addEventListener("DOMMouseScroll", f, !1), a.addEventListener(b, f, !1)) : "mouseenter" === b || "mouseleave" === b ? (g = f, h = "mouseenter" === b ? "mouseover": "mouseout", f = function(b) {
                            return e.DomEvent._checkMouse(a, b) ? g(b) : void 0
                        },
                            a.addEventListener(h, f, !1)) : "click" === b && e.Browser.android ? (g = f, f = function(a) {
                            return e.DomEvent._filterClick(a, g)
                        },
                            a.addEventListener(b, f, !1)) : a.addEventListener(b, f, !1) : "attachEvent" in a && a.attachEvent("on" + b, f), a[j] = f, this))
                },
                removeListener: function(a, b, c) {
                    var d = e.stamp(c),
                        f = "_leaflet_" + b + d,
                        g = a[f];
                    return g ? (e.Browser.pointer && 0 === b.indexOf("touch") ? this.removePointerListener(a, b, d) : e.Browser.touch && "dblclick" === b && this.removeDoubleTapListener ? this.removeDoubleTapListener(a, d) : "removeEventListener" in a ? "mousewheel" === b ? (a.removeEventListener("DOMMouseScroll", g, !1), a.removeEventListener(b, g, !1)) : "mouseenter" === b || "mouseleave" === b ? a.removeEventListener("mouseenter" === b ? "mouseover": "mouseout", g, !1) : a.removeEventListener(b, g, !1) : "detachEvent" in a && a.detachEvent("on" + b, g), a[f] = null, this) : this
                },
                stopPropagation: function(a) {
                    return a.stopPropagation ? a.stopPropagation() : a.cancelBubble = !0,
                        e.DomEvent._skipped(a),
                        this
                },
                disableScrollPropagation: function(a) {
                    var b = e.DomEvent.stopPropagation;
                    return e.DomEvent.on(a, "mousewheel", b).on(a, "MozMousePixelScroll", b)
                },
                disableClickPropagation: function(a) {
                    for (var b = e.DomEvent.stopPropagation,
                             c = e.Draggable.START.length - 1; c >= 0; c--) e.DomEvent.on(a, e.Draggable.START[c], b);
                    return e.DomEvent.on(a, "click", e.DomEvent._fakeStop).on(a, "dblclick", b)
                },
                preventDefault: function(a) {
                    return a.preventDefault ? a.preventDefault() : a.returnValue = !1,
                        this
                },
                stop: function(a) {
                    return e.DomEvent.preventDefault(a).stopPropagation(a)
                },
                getMousePosition: function(a, c) {
                    var d = b.body,
                        f = b.documentElement,
                        g = e.DomUtil.documentIsLtr() ? a.pageX ? a.pageX - d.scrollLeft - f.scrollLeft: a.clientX: e.Browser.gecko ? a.pageX - d.scrollLeft - f.scrollLeft: a.pageX ? a.pageX - d.scrollLeft + f.scrollLeft: a.clientX,
                        h = a.pageY ? a.pageY - d.scrollTop - f.scrollTop: a.clientY,
                        i = new e.Point(g, h);
                    if (!c) return i;
                    var j = c.getBoundingClientRect(),
                        k = j.left - c.clientLeft,
                        l = j.top - c.clientTop;
                    return i._subtract(new e.Point(k, l))
                },
                getWheelDelta: function(a) {
                    var b = 0;
                    return a.wheelDelta && (b = a.wheelDelta / 120),
                    a.detail && (b = -a.detail / 3),
                        b
                },
                _skipEvents: {},
                _fakeStop: function(a) {
                    e.DomEvent._skipEvents[a.type] = !0
                },
                _skipped: function(a) {
                    var b = this._skipEvents[a.type];
                    return this._skipEvents[a.type] = !1,
                        b
                },
                _checkMouse: function(a, b) {
                    var c = b.relatedTarget;
                    if (!c) return ! 0;
                    try {
                        for (; c && c !== a;) c = c.parentNode
                    } catch(d) {
                        return ! 1
                    }
                    return c !== a
                },
                _getEvent: function() {
                    var b = a.event;
                    if (!b) for (var c = arguments.callee.caller; c && (b = c.arguments[0], !b || a.Event !== b.constructor);) c = c.caller;
                    return b
                },
                _filterClick: function(a, b) {
                    var c = a.timeStamp || a.originalEvent.timeStamp,
                        d = e.DomEvent._lastClick && c - e.DomEvent._lastClick;
                    return d && d > 100 && 1e3 > d || a.target._simulatedClick && !a._simulated ? void e.DomEvent.stop(a) : (e.DomEvent._lastClick = c, b(a))
                }
            },
            e.DomEvent.on = e.DomEvent.addListener,
            e.DomEvent.off = e.DomEvent.removeListener,
            e.Draggable = e.Class.extend({
                includes: e.Mixin.Events,
                statics: {
                    START: e.Browser.touch ? ["touchstart", "mousedown"] : ["mousedown"],
                    END: {
                        mousedown: "mouseup",
                        touchstart: "touchend",
                        pointerdown: "touchend",
                        MSPointerDown: "touchend"
                    },
                    MOVE: {
                        mousedown: "mousemove",
                        touchstart: "touchmove",
                        pointerdown: "touchmove",
                        MSPointerDown: "touchmove"
                    }
                },
                initialize: function(a, b) {
                    this._element = a,
                        this._dragStartTarget = b || a
                },
                enable: function() {
                    if (!this._enabled) {
                        for (var a = e.Draggable.START.length - 1; a >= 0; a--) e.DomEvent.on(this._dragStartTarget, e.Draggable.START[a], this._onDown, this);
                        this._enabled = !0
                    }
                },
                disable: function() {
                    if (this._enabled) {
                        for (var a = e.Draggable.START.length - 1; a >= 0; a--) e.DomEvent.off(this._dragStartTarget, e.Draggable.START[a], this._onDown, this);
                        this._enabled = !1,
                            this._moved = !1
                    }
                },
                _onDown: function(a) {
                    if (this._moved = !1, !(a.shiftKey || 1 !== a.which && 1 !== a.button && !a.touches || (e.DomEvent.stopPropagation(a), e.Draggable._disabled || (e.DomUtil.disableImageDrag(), e.DomUtil.disableTextSelection(), this._moving)))) {
                        var c = a.touches ? a.touches[0] : a;
                        this._startPoint = new e.Point(c.clientX, c.clientY),
                            this._startPos = this._newPos = e.DomUtil.getPosition(this._element),
                            e.DomEvent.on(b, e.Draggable.MOVE[a.type], this._onMove, this).on(b, e.Draggable.END[a.type], this._onUp, this)
                    }
                },
                _onMove: function(a) {
                    if (a.touches && a.touches.length > 1) return void(this._moved = !0);
                    var c = a.touches && 1 === a.touches.length ? a.touches[0] : a,
                        d = new e.Point(c.clientX, c.clientY),
                        f = d.subtract(this._startPoint); (f.x || f.y) && (e.DomEvent.preventDefault(a), this._moved || (this.fire("dragstart"), this._moved = !0, this._startPos = e.DomUtil.getPosition(this._element).subtract(f), e.DomUtil.addClass(b.body, "leaflet-dragging"), e.DomUtil.addClass(a.target || a.srcElement, "leaflet-drag-target")), this._newPos = this._startPos.add(f), this._moving = !0, e.Util.cancelAnimFrame(this._animRequest), this._animRequest = e.Util.requestAnimFrame(this._updatePosition, this, !0, this._dragStartTarget))
                },
                _updatePosition: function() {
                    this.fire("predrag"),
                        e.DomUtil.setPosition(this._element, this._newPos),
                        this.fire("drag")
                },
                _onUp: function(a) {
                    e.DomUtil.removeClass(b.body, "leaflet-dragging"),
                        e.DomUtil.removeClass(a.target || a.srcElement, "leaflet-drag-target");
                    for (var c in e.Draggable.MOVE) e.DomEvent.off(b, e.Draggable.MOVE[c], this._onMove).off(b, e.Draggable.END[c], this._onUp);
                    e.DomUtil.enableImageDrag(),
                        e.DomUtil.enableTextSelection(),
                    this._moved && (e.Util.cancelAnimFrame(this._animRequest), this.fire("dragend", {
                        distance: this._newPos.distanceTo(this._startPos)
                    })),
                        this._moving = !1
                }
            }),
            e.Handler = e.Class.extend({
                initialize: function(a) {
                    this._map = a
                },
                enable: function() {
                    this._enabled || (this._enabled = !0, this.addHooks())
                },
                disable: function() {
                    this._enabled && (this._enabled = !1, this.removeHooks())
                },
                enabled: function() {
                    return !! this._enabled
                }
            }),
            e.Map.mergeOptions({
                dragging: !0,
                inertia: !e.Browser.android23,
                inertiaDeceleration: 3400,
                inertiaMaxSpeed: 1 / 0,
                inertiaThreshold: e.Browser.touch ? 32 : 18,
                easeLinearity: .25,
                worldCopyJump: !1
            }),
            e.Map.Drag = e.Handler.extend({
                addHooks: function() {
                    if (!this._draggable) {
                        var a = this._map;
                        this._draggable = new e.Draggable(a._mapPane, a._container),
                            this._draggable.on({
                                    dragstart: this._onDragStart,
                                    drag: this._onDrag,
                                    dragend: this._onDragEnd
                                },
                                this),
                        a.options.worldCopyJump && (this._draggable.on("predrag", this._onPreDrag, this), a.on("viewreset", this._onViewReset, this), a.whenReady(this._onViewReset, this))
                    }
                    this._draggable.enable()
                },
                removeHooks: function() {
                    this._draggable.disable()
                },
                moved: function() {
                    return this._draggable && this._draggable._moved
                },
                _onDragStart: function() {
                    var a = this._map;
                    a._panAnim && a._panAnim.stop(),
                        a.fire("movestart").fire("dragstart"),
                    a.options.inertia && (this._positions = [], this._times = [])
                },
                _onDrag: function() {
                    if (this._map.options.inertia) {
                        var a = this._lastTime = +new Date,
                            b = this._lastPos = this._draggable._newPos;
                        this._positions.push(b),
                            this._times.push(a),
                        a - this._times[0] > 200 && (this._positions.shift(), this._times.shift())
                    }
                    this._map.fire("move").fire("drag")
                },
                _onViewReset: function() {
                    var a = this._map.getSize()._divideBy(2),
                        b = this._map.latLngToLayerPoint([0, 0]);
                    this._initialWorldOffset = b.subtract(a).x,
                        this._worldWidth = this._map.project([0, 180]).x
                },
                _onPreDrag: function() {
                    var a = this._worldWidth,
                        b = Math.round(a / 2),
                        c = this._initialWorldOffset,
                        d = this._draggable._newPos.x,
                        e = (d - b + c) % a + b - c,
                        f = (d + b + c) % a - b - c,
                        g = Math.abs(e + c) < Math.abs(f + c) ? e: f;
                    this._draggable._newPos.x = g
                },
                _onDragEnd: function(a) {
                    var b = this._map,
                        c = b.options,
                        d = +new Date - this._lastTime,
                        f = !c.inertia || d > c.inertiaThreshold || !this._positions[0];
                    if (b.fire("dragend", a), f) b.fire("moveend");
                    else {
                        var g = this._lastPos.subtract(this._positions[0]),
                            h = (this._lastTime + d - this._times[0]) / 1e3,
                            i = c.easeLinearity,
                            j = g.multiplyBy(i / h),
                            k = j.distanceTo([0, 0]),
                            l = Math.min(c.inertiaMaxSpeed, k),
                            m = j.multiplyBy(l / k),
                            n = l / (c.inertiaDeceleration * i),
                            o = m.multiplyBy( - n / 2).round();
                        o.x && o.y ? (o = b._limitOffset(o, b.options.maxBounds), e.Util.requestAnimFrame(function() {
                            b.panBy(o, {
                                duration: n,
                                easeLinearity: i,
                                noMoveStart: !0
                            })
                        })) : b.fire("moveend")
                    }
                }
            }),
            e.Map.addInitHook("addHandler", "dragging", e.Map.Drag),
            e.Map.mergeOptions({
                doubleClickZoom: !0
            }),
            e.Map.DoubleClickZoom = e.Handler.extend({
                addHooks: function() {
                    this._map.on("dblclick", this._onDoubleClick, this)
                },
                removeHooks: function() {
                    this._map.off("dblclick", this._onDoubleClick, this)
                },
                _onDoubleClick: function(a) {
                    var b = this._map,
                        c = b.getZoom() + (a.originalEvent.shiftKey ? -1 : 1);
                    "center" === b.options.doubleClickZoom ? b.setZoom(c) : b.setZoomAround(a.containerPoint, c)
                }
            }),
            e.Map.addInitHook("addHandler", "doubleClickZoom", e.Map.DoubleClickZoom),
            e.Map.mergeOptions({
                scrollWheelZoom: !0
            }),
            e.Map.ScrollWheelZoom = e.Handler.extend({
                addHooks: function() {
                    e.DomEvent.on(this._map._container, "mousewheel", this._onWheelScroll, this),
                        e.DomEvent.on(this._map._container, "MozMousePixelScroll", e.DomEvent.preventDefault),
                        this._delta = 0
                },
                removeHooks: function() {
                    e.DomEvent.off(this._map._container, "mousewheel", this._onWheelScroll),
                        e.DomEvent.off(this._map._container, "MozMousePixelScroll", e.DomEvent.preventDefault)
                },
                _onWheelScroll: function(a) {
                    var b = e.DomEvent.getWheelDelta(a);
                    this._delta += b,
                        this._lastMousePos = this._map.mouseEventToContainerPoint(a),
                    this._startTime || (this._startTime = +new Date);
                    var c = Math.max(40 - ( + new Date - this._startTime), 0);
                    clearTimeout(this._timer),
                        this._timer = setTimeout(e.bind(this._performZoom, this), c),
                        e.DomEvent.preventDefault(a),
                        e.DomEvent.stopPropagation(a)
                },
                _performZoom: function() {
                    var a = this._map,
                        b = this._delta,
                        c = a.getZoom();
                    b = b > 0 ? Math.ceil(b) : Math.floor(b),
                        b = Math.max(Math.min(b, 4), -4),
                        b = a._limitZoom(c + b) - c,
                        this._delta = 0,
                        this._startTime = null,
                    b && ("center" === a.options.scrollWheelZoom ? a.setZoom(c + b) : a.setZoomAround(this._lastMousePos, c + b))
                }
            }),
            e.Map.addInitHook("addHandler", "scrollWheelZoom", e.Map.ScrollWheelZoom),
            e.extend(e.DomEvent, {
                _touchstart: e.Browser.msPointer ? "MSPointerDown": e.Browser.pointer ? "pointerdown": "touchstart",
                _touchend: e.Browser.msPointer ? "MSPointerUp": e.Browser.pointer ? "pointerup": "touchend",
                addDoubleTapListener: function(a, c, d) {
                    function f(a) {
                        var b;
                        if (e.Browser.pointer ? (o.push(a.pointerId), b = o.length) : b = a.touches.length, !(b > 1)) {
                            var c = Date.now(),
                                d = c - (h || c);
                            i = a.touches ? a.touches[0] : a,
                                j = d > 0 && k >= d,
                                h = c
                        }
                    }
                    function g(a) {
                        if (e.Browser.pointer) {
                            var b = o.indexOf(a.pointerId);
                            if ( - 1 === b) return;
                            o.splice(b, 1)
                        }
                        if (j) {
                            if (e.Browser.pointer) {
                                var d, f = {};
                                for (var g in i) d = i[g],
                                    "function" == typeof d ? f[g] = d.bind(i) : f[g] = d;
                                i = f
                            }
                            i.type = "dblclick",
                                c(i),
                                h = null
                        }
                    }
                    var h, i, j = !1,
                        k = 250,
                        l = "_leaflet_",
                        m = this._touchstart,
                        n = this._touchend,
                        o = [];
                    a[l + m + d] = f,
                        a[l + n + d] = g;
                    var p = e.Browser.pointer ? b.documentElement: a;
                    return a.addEventListener(m, f, !1),
                        p.addEventListener(n, g, !1),
                    e.Browser.pointer && p.addEventListener(e.DomEvent.POINTER_CANCEL, g, !1),
                        this
                },
                removeDoubleTapListener: function(a, c) {
                    var d = "_leaflet_";
                    return a.removeEventListener(this._touchstart, a[d + this._touchstart + c], !1),
                        (e.Browser.pointer ? b.documentElement: a).removeEventListener(this._touchend, a[d + this._touchend + c], !1),
                    e.Browser.pointer && b.documentElement.removeEventListener(e.DomEvent.POINTER_CANCEL, a[d + this._touchend + c], !1),
                        this
                }
            }),
            e.extend(e.DomEvent, {
                POINTER_DOWN: e.Browser.msPointer ? "MSPointerDown": "pointerdown",
                POINTER_MOVE: e.Browser.msPointer ? "MSPointerMove": "pointermove",
                POINTER_UP: e.Browser.msPointer ? "MSPointerUp": "pointerup",
                POINTER_CANCEL: e.Browser.msPointer ? "MSPointerCancel": "pointercancel",
                _pointers: [],
                _pointerDocumentListener: !1,
                addPointerListener: function(a, b, c, d) {
                    switch (b) {
                        case "touchstart":
                            return this.addPointerListenerStart(a, b, c, d);
                        case "touchend":
                            return this.addPointerListenerEnd(a, b, c, d);
                        case "touchmove":
                            return this.addPointerListenerMove(a, b, c, d);
                        default:
                            throw "Unknown touch event type"
                    }
                },
                addPointerListenerStart: function(a, c, d, f) {
                    var g = "_leaflet_",
                        h = this._pointers,
                        i = function(a) {
                            e.DomEvent.preventDefault(a);
                            for (var b = !1,
                                     c = 0; c < h.length; c++) if (h[c].pointerId === a.pointerId) {
                                b = !0;
                                break
                            }
                            b || h.push(a),
                                a.touches = h.slice(),
                                a.changedTouches = [a],
                                d(a)
                        };
                    if (a[g + "touchstart" + f] = i, a.addEventListener(this.POINTER_DOWN, i, !1), !this._pointerDocumentListener) {
                        var j = function(a) {
                            for (var b = 0; b < h.length; b++) if (h[b].pointerId === a.pointerId) {
                                h.splice(b, 1);
                                break
                            }
                        };
                        b.documentElement.addEventListener(this.POINTER_UP, j, !1),
                            b.documentElement.addEventListener(this.POINTER_CANCEL, j, !1),
                            this._pointerDocumentListener = !0
                    }
                    return this
                },
                addPointerListenerMove: function(a, b, c, d) {
                    function e(a) {
                        if (a.pointerType !== a.MSPOINTER_TYPE_MOUSE && "mouse" !== a.pointerType || 0 !== a.buttons) {
                            for (var b = 0; b < g.length; b++) if (g[b].pointerId === a.pointerId) {
                                g[b] = a;
                                break
                            }
                            a.touches = g.slice(),
                                a.changedTouches = [a],
                                c(a)
                        }
                    }
                    var f = "_leaflet_",
                        g = this._pointers;
                    return a[f + "touchmove" + d] = e,
                        a.addEventListener(this.POINTER_MOVE, e, !1),
                        this
                },
                addPointerListenerEnd: function(a, b, c, d) {
                    var e = "_leaflet_",
                        f = this._pointers,
                        g = function(a) {
                            for (var b = 0; b < f.length; b++) if (f[b].pointerId === a.pointerId) {
                                f.splice(b, 1);
                                break
                            }
                            a.touches = f.slice(),
                                a.changedTouches = [a],
                                c(a)
                        };
                    return a[e + "touchend" + d] = g,
                        a.addEventListener(this.POINTER_UP, g, !1),
                        a.addEventListener(this.POINTER_CANCEL, g, !1),
                        this
                },
                removePointerListener: function(a, b, c) {
                    var d = "_leaflet_",
                        e = a[d + b + c];
                    switch (b) {
                        case "touchstart":
                            a.removeEventListener(this.POINTER_DOWN, e, !1);
                            break;
                        case "touchmove":
                            a.removeEventListener(this.POINTER_MOVE, e, !1);
                            break;
                        case "touchend":
                            a.removeEventListener(this.POINTER_UP, e, !1),
                                a.removeEventListener(this.POINTER_CANCEL, e, !1)
                    }
                    return this
                }
            }),
            e.Map.mergeOptions({
                touchZoom: e.Browser.touch && !e.Browser.android23,
                bounceAtZoomLimits: !0
            }),
            e.Map.TouchZoom = e.Handler.extend({
                addHooks: function() {
                    e.DomEvent.on(this._map._container, "touchstart", this._onTouchStart, this)
                },
                removeHooks: function() {
                    e.DomEvent.off(this._map._container, "touchstart", this._onTouchStart, this)
                },
                _onTouchStart: function(a) {
                    var c = this._map;
                    if (a.touches && 2 === a.touches.length && !c._animatingZoom && !this._zooming) {
                        var d = c.mouseEventToLayerPoint(a.touches[0]),
                            f = c.mouseEventToLayerPoint(a.touches[1]),
                            g = c._getCenterLayerPoint();
                        this._startCenter = d.add(f)._divideBy(2),
                            this._startDist = d.distanceTo(f),
                            this._moved = !1,
                            this._zooming = !0,
                            this._centerOffset = g.subtract(this._startCenter),
                        c._panAnim && c._panAnim.stop(),
                            e.DomEvent.on(b, "touchmove", this._onTouchMove, this).on(b, "touchend", this._onTouchEnd, this),
                            e.DomEvent.preventDefault(a)
                    }
                },
                _onTouchMove: function(a) {
                    var b = this._map;
                    if (a.touches && 2 === a.touches.length && this._zooming) {
                        var c = b.mouseEventToLayerPoint(a.touches[0]),
                            d = b.mouseEventToLayerPoint(a.touches[1]);
                        this._scale = c.distanceTo(d) / this._startDist,
                            this._delta = c._add(d)._divideBy(2)._subtract(this._startCenter),
                        1 !== this._scale && (b.options.bounceAtZoomLimits || !(b.getZoom() === b.getMinZoom() && this._scale < 1 || b.getZoom() === b.getMaxZoom() && this._scale > 1)) && (this._moved || (e.DomUtil.addClass(b._mapPane, "leaflet-touching"), b.fire("movestart").fire("zoomstart"), this._moved = !0), e.Util.cancelAnimFrame(this._animRequest), this._animRequest = e.Util.requestAnimFrame(this._updateOnMove, this, !0, this._map._container), e.DomEvent.preventDefault(a))
                    }
                },
                _updateOnMove: function() {
                    var a = this._map,
                        b = this._getScaleOrigin(),
                        c = a.layerPointToLatLng(b),
                        d = a.getScaleZoom(this._scale);
                    a._animateZoom(c, d, this._startCenter, this._scale, this._delta)
                },
                _onTouchEnd: function() {
                    if (!this._moved || !this._zooming) return void(this._zooming = !1);
                    var a = this._map;
                    this._zooming = !1,
                        e.DomUtil.removeClass(a._mapPane, "leaflet-touching"),
                        e.Util.cancelAnimFrame(this._animRequest),
                        e.DomEvent.off(b, "touchmove", this._onTouchMove).off(b, "touchend", this._onTouchEnd);
                    var c = this._getScaleOrigin(),
                        d = a.layerPointToLatLng(c),
                        f = a.getZoom(),
                        g = a.getScaleZoom(this._scale) - f,
                        h = g > 0 ? Math.ceil(g) : Math.floor(g),
                        i = a._limitZoom(f + h),
                        j = a.getZoomScale(i) / this._scale;
                    a._animateZoom(d, i, c, j)
                },
                _getScaleOrigin: function() {
                    var a = this._centerOffset.subtract(this._delta).divideBy(this._scale);
                    return this._startCenter.add(a)
                }
            }),
            e.Map.addInitHook("addHandler", "touchZoom", e.Map.TouchZoom),
            e.Map.mergeOptions({
                tap: !0,
                tapTolerance: 15
            }),
            e.Map.Tap = e.Handler.extend({
                addHooks: function() {
                    e.DomEvent.on(this._map._container, "touchstart", this._onDown, this)
                },
                removeHooks: function() {
                    e.DomEvent.off(this._map._container, "touchstart", this._onDown, this)
                },
                _onDown: function(a) {
                    if (a.touches) {
                        if (e.DomEvent.preventDefault(a), this._fireClick = !0, a.touches.length > 1) return this._fireClick = !1,
                            void clearTimeout(this._holdTimeout);
                        var c = a.touches[0],
                            d = c.target;
                        this._startPos = this._newPos = new e.Point(c.clientX, c.clientY),
                        d.tagName && "a" === d.tagName.toLowerCase() && e.DomUtil.addClass(d, "leaflet-active"),
                            this._holdTimeout = setTimeout(e.bind(function() {
                                    this._isTapValid() && (this._fireClick = !1, this._onUp(), this._simulateEvent("contextmenu", c))
                                },
                                this), 1e3),
                            e.DomEvent.on(b, "touchmove", this._onMove, this).on(b, "touchend", this._onUp, this)
                    }
                },
                _onUp: function(a) {
                    if (clearTimeout(this._holdTimeout), e.DomEvent.off(b, "touchmove", this._onMove, this).off(b, "touchend", this._onUp, this), this._fireClick && a && a.changedTouches) {
                        var c = a.changedTouches[0],
                            d = c.target;
                        d && d.tagName && "a" === d.tagName.toLowerCase() && e.DomUtil.removeClass(d, "leaflet-active"),
                        this._isTapValid() && this._simulateEvent("click", c)
                    }
                },
                _isTapValid: function() {
                    return this._newPos.distanceTo(this._startPos) <= this._map.options.tapTolerance
                },
                _onMove: function(a) {
                    var b = a.touches[0];
                    this._newPos = new e.Point(b.clientX, b.clientY)
                },
                _simulateEvent: function(c, d) {
                    var e = b.createEvent("MouseEvents");
                    e._simulated = !0,
                        d.target._simulatedClick = !0,
                        e.initMouseEvent(c, !0, !0, a, 1, d.screenX, d.screenY, d.clientX, d.clientY, !1, !1, !1, !1, 0, null),
                        d.target.dispatchEvent(e)
                }
            }),
        e.Browser.touch && !e.Browser.pointer && e.Map.addInitHook("addHandler", "tap", e.Map.Tap),
            e.Map.mergeOptions({
                boxZoom: !0
            }),
            e.Map.BoxZoom = e.Handler.extend({
                initialize: function(a) {
                    this._map = a,
                        this._container = a._container,
                        this._pane = a._panes.overlayPane,
                        this._moved = !1
                },
                addHooks: function() {
                    e.DomEvent.on(this._container, "mousedown", this._onMouseDown, this)
                },
                removeHooks: function() {
                    e.DomEvent.off(this._container, "mousedown", this._onMouseDown),
                        this._moved = !1
                },
                moved: function() {
                    return this._moved
                },
                _onMouseDown: function(a) {
                    return this._moved = !1,
                        !a.shiftKey || 1 !== a.which && 1 !== a.button ? !1 : (e.DomUtil.disableTextSelection(), e.DomUtil.disableImageDrag(), this._startLayerPoint = this._map.mouseEventToLayerPoint(a), void e.DomEvent.on(b, "mousemove", this._onMouseMove, this).on(b, "mouseup", this._onMouseUp, this).on(b, "keydown", this._onKeyDown, this))
                },
                _onMouseMove: function(a) {
                    this._moved || (this._box = e.DomUtil.create("div", "leaflet-zoom-box", this._pane), e.DomUtil.setPosition(this._box, this._startLayerPoint), this._container.style.cursor = "crosshair", this._map.fire("boxzoomstart"));
                    var b = this._startLayerPoint,
                        c = this._box,
                        d = this._map.mouseEventToLayerPoint(a),
                        f = d.subtract(b),
                        g = new e.Point(Math.min(d.x, b.x), Math.min(d.y, b.y));
                    e.DomUtil.setPosition(c, g),
                        this._moved = !0,
                        c.style.width = Math.max(0, Math.abs(f.x) - 4) + "px",
                        c.style.height = Math.max(0, Math.abs(f.y) - 4) + "px"
                },
                _finish: function() {
                    this._moved && (this._pane.removeChild(this._box), this._container.style.cursor = ""),
                        e.DomUtil.enableTextSelection(),
                        e.DomUtil.enableImageDrag(),
                        e.DomEvent.off(b, "mousemove", this._onMouseMove).off(b, "mouseup", this._onMouseUp).off(b, "keydown", this._onKeyDown)
                },
                _onMouseUp: function(a) {
                    this._finish();
                    var b = this._map,
                        c = b.mouseEventToLayerPoint(a);
                    if (!this._startLayerPoint.equals(c)) {
                        var d = new e.LatLngBounds(b.layerPointToLatLng(this._startLayerPoint), b.layerPointToLatLng(c));
                        b.fitBounds(d),
                            b.fire("boxzoomend", {
                                boxZoomBounds: d
                            })
                    }
                },
                _onKeyDown: function(a) {
                    27 === a.keyCode && this._finish()
                }
            }),
            e.Map.addInitHook("addHandler", "boxZoom", e.Map.BoxZoom),
            e.Map.mergeOptions({
                keyboard: !0,
                keyboardPanOffset: 80,
                keyboardZoomOffset: 1
            }),
            e.Map.Keyboard = e.Handler.extend({
                keyCodes: {
                    left: [37],
                    right: [39],
                    down: [40],
                    up: [38],
                    zoomIn: [187, 107, 61, 171],
                    zoomOut: [189, 109, 173]
                },
                initialize: function(a) {
                    this._map = a,
                        this._setPanOffset(a.options.keyboardPanOffset),
                        this._setZoomOffset(a.options.keyboardZoomOffset)
                },
                addHooks: function() {
                    var a = this._map._container; - 1 === a.tabIndex && (a.tabIndex = "0"),
                        e.DomEvent.on(a, "focus", this._onFocus, this).on(a, "blur", this._onBlur, this).on(a, "mousedown", this._onMouseDown, this),
                        this._map.on("focus", this._addHooks, this).on("blur", this._removeHooks, this)
                },
                removeHooks: function() {
                    this._removeHooks();
                    var a = this._map._container;
                    e.DomEvent.off(a, "focus", this._onFocus, this).off(a, "blur", this._onBlur, this).off(a, "mousedown", this._onMouseDown, this),
                        this._map.off("focus", this._addHooks, this).off("blur", this._removeHooks, this)
                },
                _onMouseDown: function() {
                    if (!this._focused) {
                        var c = b.body,
                            d = b.documentElement,
                            e = c.scrollTop || d.scrollTop,
                            f = c.scrollLeft || d.scrollLeft;
                        this._map._container.focus(),
                            a.scrollTo(f, e)
                    }
                },
                _onFocus: function() {
                    this._focused = !0,
                        this._map.fire("focus")
                },
                _onBlur: function() {
                    this._focused = !1,
                        this._map.fire("blur")
                },
                _setPanOffset: function(a) {
                    var b, c, d = this._panKeys = {},
                        e = this.keyCodes;
                    for (b = 0, c = e.left.length; c > b; b++) d[e.left[b]] = [ - 1 * a, 0];
                    for (b = 0, c = e.right.length; c > b; b++) d[e.right[b]] = [a, 0];
                    for (b = 0, c = e.down.length; c > b; b++) d[e.down[b]] = [0, a];
                    for (b = 0, c = e.up.length; c > b; b++) d[e.up[b]] = [0, -1 * a]
                },
                _setZoomOffset: function(a) {
                    var b, c, d = this._zoomKeys = {},
                        e = this.keyCodes;
                    for (b = 0, c = e.zoomIn.length; c > b; b++) d[e.zoomIn[b]] = a;
                    for (b = 0, c = e.zoomOut.length; c > b; b++) d[e.zoomOut[b]] = -a
                },
                _addHooks: function() {
                    e.DomEvent.on(b, "keydown", this._onKeyDown, this)
                },
                _removeHooks: function() {
                    e.DomEvent.off(b, "keydown", this._onKeyDown, this)
                },
                _onKeyDown: function(a) {
                    var b = a.keyCode,
                        c = this._map;
                    if (b in this._panKeys) {
                        if (c._panAnim && c._panAnim._inProgress) return;
                        c.panBy(this._panKeys[b]),
                        c.options.maxBounds && c.panInsideBounds(c.options.maxBounds)
                    } else {
                        if (! (b in this._zoomKeys)) return;
                        c.setZoom(c.getZoom() + this._zoomKeys[b])
                    }
                    e.DomEvent.stop(a)
                }
            }),
            e.Map.addInitHook("addHandler", "keyboard", e.Map.Keyboard),
            e.Handler.MarkerDrag = e.Handler.extend({
                initialize: function(a) {
                    this._marker = a
                },
                addHooks: function() {
                    var a = this._marker._icon;
                    this._draggable || (this._draggable = new e.Draggable(a, a)),
                        this._draggable.on("dragstart", this._onDragStart, this).on("drag", this._onDrag, this).on("dragend", this._onDragEnd, this),
                        this._draggable.enable(),
                        e.DomUtil.addClass(this._marker._icon, "leaflet-marker-draggable")
                },
                removeHooks: function() {
                    this._draggable.off("dragstart", this._onDragStart, this).off("drag", this._onDrag, this).off("dragend", this._onDragEnd, this),
                        this._draggable.disable(),
                        e.DomUtil.removeClass(this._marker._icon, "leaflet-marker-draggable")
                },
                moved: function() {
                    return this._draggable && this._draggable._moved
                },
                _onDragStart: function() {
                    this._marker.closePopup().fire("movestart").fire("dragstart")
                },
                _onDrag: function() {
                    var a = this._marker,
                        b = a._shadow,
                        c = e.DomUtil.getPosition(a._icon),
                        d = a._map.layerPointToLatLng(c);
                    b && e.DomUtil.setPosition(b, c),
                        a._latlng = d,
                        a.fire("move", {
                            latlng: d
                        }).fire("drag")
                },
                _onDragEnd: function(a) {
                    this._marker.fire("moveend").fire("dragend", a)
                }
            }),
            e.Control = e.Class.extend({
                options: {
                    position: "topright"
                },
                initialize: function(a) {
                    e.setOptions(this, a)
                },
                getPosition: function() {
                    return this.options.position
                },
                setPosition: function(a) {
                    var b = this._map;
                    return b && b.removeControl(this),
                        this.options.position = a,
                    b && b.addControl(this),
                        this
                },
                getContainer: function() {
                    return this._container
                },
                addTo: function(a) {
                    this._map = a;
                    var b = this._container = this.onAdd(a),
                        c = this.getPosition(),
                        d = a._controlCorners[c];
                    return e.DomUtil.addClass(b, "leaflet-control"),
                        -1 !== c.indexOf("bottom") ? d.insertBefore(b, d.firstChild) : d.appendChild(b),
                        this
                },
                removeFrom: function(a) {
                    var b = this.getPosition(),
                        c = a._controlCorners[b];
                    return c.removeChild(this._container),
                        this._map = null,
                    this.onRemove && this.onRemove(a),
                        this
                },
                _refocusOnMap: function() {
                    this._map && this._map.getContainer().focus()
                }
            }),
            e.control = function(a) {
                return new e.Control(a)
            },
            e.Map.include({
                addControl: function(a) {
                    return a.addTo(this),
                        this
                },
                removeControl: function(a) {
                    return a.removeFrom(this),
                        this
                },
                _initControlPos: function() {
                    function a(a, f) {
                        var g = c + a + " " + c + f;
                        b[a + f] = e.DomUtil.create("div", g, d)
                    }
                    var b = this._controlCorners = {},
                        c = "leaflet-",
                        d = this._controlContainer = e.DomUtil.create("div", c + "control-container", this._container);
                    a("top", "left"),
                        a("top", "right"),
                        a("bottom", "left"),
                        a("bottom", "right")
                },
                _clearControlPos: function() {
                    this._container.removeChild(this._controlContainer)
                }
            }),
            e.Control.Zoom = e.Control.extend({
                options: {
                    position: "topleft",
                    zoomInText: "+",
                    zoomInTitle: "Zoom in",
                    zoomOutText: "-",
                    zoomOutTitle: "Zoom out"
                },
                onAdd: function(a) {
                    var b = "leaflet-control-zoom",
                        c = e.DomUtil.create("div", b + " leaflet-bar");
                    return this._map = a,
                        this._zoomInButton = this._createButton(this.options.zoomInText, this.options.zoomInTitle, b + "-in", c, this._zoomIn, this),
                        this._zoomOutButton = this._createButton(this.options.zoomOutText, this.options.zoomOutTitle, b + "-out", c, this._zoomOut, this),
                        this._updateDisabled(),
                        a.on("zoomend zoomlevelschange", this._updateDisabled, this),
                        c
                },
                onRemove: function(a) {
                    a.off("zoomend zoomlevelschange", this._updateDisabled, this)
                },
                _zoomIn: function(a) {
                    this._map.zoomIn(a.shiftKey ? 3 : 1)
                },
                _zoomOut: function(a) {
                    this._map.zoomOut(a.shiftKey ? 3 : 1)
                },
                _createButton: function(a, b, c, d, f, g) {
                    var h = e.DomUtil.create("a", c, d);
                    h.innerHTML = a,
                        h.href = "#",
                        h.title = b;
                    var i = e.DomEvent.stopPropagation;
                    return e.DomEvent.on(h, "click", i).on(h, "mousedown", i).on(h, "dblclick", i).on(h, "click", e.DomEvent.preventDefault).on(h, "click", f, g).on(h, "click", this._refocusOnMap, g),
                        h
                },
                _updateDisabled: function() {
                    var a = this._map,
                        b = "leaflet-disabled";
                    e.DomUtil.removeClass(this._zoomInButton, b),
                        e.DomUtil.removeClass(this._zoomOutButton, b),
                    a._zoom === a.getMinZoom() && e.DomUtil.addClass(this._zoomOutButton, b),
                    a._zoom === a.getMaxZoom() && e.DomUtil.addClass(this._zoomInButton, b)
                }
            }),
            e.Map.mergeOptions({
                zoomControl: !0
            }),
            e.Map.addInitHook(function() {
                this.options.zoomControl && (this.zoomControl = new e.Control.Zoom, this.addControl(this.zoomControl))
            }),
            e.control.zoom = function(a) {
                return new e.Control.Zoom(a)
            },
            e.Control.Attribution = e.Control.extend({
                options: {
                    position: "bottomright",
                    prefix: '<a href="http://leafletjs.com" title="A JS library for interactive maps">Leaflet</a>'
                },
                initialize: function(a) {
                    e.setOptions(this, a),
                        this._attributions = {}
                },
                onAdd: function(a) {
                    this._container = e.DomUtil.create("div", "leaflet-control-attribution"),
                        e.DomEvent.disableClickPropagation(this._container);
                    for (var b in a._layers) a._layers[b].getAttribution && this.addAttribution(a._layers[b].getAttribution());
                    return a.on("layeradd", this._onLayerAdd, this).on("layerremove", this._onLayerRemove, this),
                        this._update(),
                        this._container
                },
                onRemove: function(a) {
                    a.off("layeradd", this._onLayerAdd).off("layerremove", this._onLayerRemove)
                },
                setPrefix: function(a) {
                    return this.options.prefix = a,
                        this._update(),
                        this
                },
                addAttribution: function(a) {
                    return a ? (this._attributions[a] || (this._attributions[a] = 0), this._attributions[a]++, this._update(), this) : void 0
                },
                removeAttribution: function(a) {
                    return a ? (this._attributions[a] && (this._attributions[a]--, this._update()), this) : void 0
                },
                _update: function() {
                    if (this._map) {
                        var a = [];
                        for (var b in this._attributions) this._attributions[b] && a.push(b);
                        var c = [];
                        this.options.prefix && c.push(this.options.prefix),
                        a.length && c.push(a.join(", ")),
                            this._container.innerHTML = c.join(" | ")
                    }
                },
                _onLayerAdd: function(a) {
                    a.layer.getAttribution && this.addAttribution(a.layer.getAttribution())
                },
                _onLayerRemove: function(a) {
                    a.layer.getAttribution && this.removeAttribution(a.layer.getAttribution())
                }
            }),
            e.Map.mergeOptions({
                attributionControl: !0
            }),
            e.Map.addInitHook(function() {
                this.options.attributionControl && (this.attributionControl = (new e.Control.Attribution).addTo(this))
            }),
            e.control.attribution = function(a) {
                return new e.Control.Attribution(a)
            },
            e.Control.Scale = e.Control.extend({
                options: {
                    position: "bottomleft",
                    maxWidth: 100,
                    metric: !0,
                    imperial: !0,
                    updateWhenIdle: !1
                },
                onAdd: function(a) {
                    this._map = a;
                    var b = "leaflet-control-scale",
                        c = e.DomUtil.create("div", b),
                        d = this.options;
                    return this._addScales(d, b, c),
                        a.on(d.updateWhenIdle ? "moveend": "move", this._update, this),
                        a.whenReady(this._update, this),
                        c
                },
                onRemove: function(a) {
                    a.off(this.options.updateWhenIdle ? "moveend": "move", this._update, this)
                },
                _addScales: function(a, b, c) {
                    a.metric && (this._mScale = e.DomUtil.create("div", b + "-line", c)),
                    a.imperial && (this._iScale = e.DomUtil.create("div", b + "-line", c))
                },
                _update: function() {
                    var a = this._map.getBounds(),
                        b = a.getCenter().lat,
                        c = 6378137 * Math.PI * Math.cos(b * Math.PI / 180),
                        d = c * (a.getNorthEast().lng - a.getSouthWest().lng) / 180,
                        e = this._map.getSize(),
                        f = this.options,
                        g = 0;
                    e.x > 0 && (g = d * (f.maxWidth / e.x)),
                        this._updateScales(f, g)
                },
                _updateScales: function(a, b) {
                    a.metric && b && this._updateMetric(b),
                    a.imperial && b && this._updateImperial(b)
                },
                _updateMetric: function(a) {
                    var b = this._getRoundNum(a);
                    this._mScale.style.width = this._getScaleWidth(b / a) + "px",
                        this._mScale.innerHTML = 1e3 > b ? b + " m": b / 1e3 + " km"
                },
                _updateImperial: function(a) {
                    var b, c, d, e = 3.2808399 * a,
                        f = this._iScale;
                    e > 5280 ? (b = e / 5280, c = this._getRoundNum(b), f.style.width = this._getScaleWidth(c / b) + "px", f.innerHTML = c + " mi") : (d = this._getRoundNum(e), f.style.width = this._getScaleWidth(d / e) + "px", f.innerHTML = d + " ft")
                },
                _getScaleWidth: function(a) {
                    return Math.round(this.options.maxWidth * a) - 10
                },
                _getRoundNum: function(a) {
                    var b = Math.pow(10, (Math.floor(a) + "").length - 1),
                        c = a / b;
                    return c = c >= 10 ? 10 : c >= 5 ? 5 : c >= 3 ? 3 : c >= 2 ? 2 : 1,
                    b * c
                }
            }),
            e.control.scale = function(a) {
                return new e.Control.Scale(a)
            },
            e.Control.Layers = e.Control.extend({
                options: {
                    collapsed: !0,
                    position: "topright",
                    autoZIndex: !0
                },
                initialize: function(a, b, c) {
                    e.setOptions(this, c),
                        this._layers = {},
                        this._lastZIndex = 0,
                        this._handlingClick = !1;
                    for (var d in a) this._addLayer(a[d], d);
                    for (d in b) this._addLayer(b[d], d, !0)
                },
                onAdd: function(a) {
                    return this._initLayout(),
                        this._update(),
                        a.on("layeradd", this._onLayerChange, this).on("layerremove", this._onLayerChange, this),
                        this._container
                },
                onRemove: function(a) {
                    a.off("layeradd", this._onLayerChange).off("layerremove", this._onLayerChange)
                },
                addBaseLayer: function(a, b) {
                    return this._addLayer(a, b),
                        this._update(),
                        this
                },
                addOverlay: function(a, b) {
                    return this._addLayer(a, b, !0),
                        this._update(),
                        this
                },
                removeLayer: function(a) {
                    var b = e.stamp(a);
                    return delete this._layers[b],
                        this._update(),
                        this
                },
                _initLayout: function() {
                    var a = "leaflet-control-layers",
                        b = this._container = e.DomUtil.create("div", a);
                    b.setAttribute("aria-haspopup", !0),
                        e.Browser.touch ? e.DomEvent.on(b, "click", e.DomEvent.stopPropagation) : e.DomEvent.disableClickPropagation(b).disableScrollPropagation(b);
                    var c = this._form = e.DomUtil.create("form", a + "-list");
                    if (this.options.collapsed) {
                        e.Browser.android || e.DomEvent.on(b, "mouseover", this._expand, this).on(b, "mouseout", this._collapse, this);
                        var d = this._layersLink = e.DomUtil.create("a", a + "-toggle", b);
                        d.href = "#",
                            d.title = "Layers",
                            e.Browser.touch ? e.DomEvent.on(d, "click", e.DomEvent.stop).on(d, "click", this._expand, this) : e.DomEvent.on(d, "focus", this._expand, this),
                            e.DomEvent.on(c, "click",
                                function() {
                                    setTimeout(e.bind(this._onInputClick, this), 0)
                                },
                                this),
                            this._map.on("click", this._collapse, this)
                    } else this._expand();
                    this._baseLayersList = e.DomUtil.create("div", a + "-base", c),
                        this._separator = e.DomUtil.create("div", a + "-separator", c),
                        this._overlaysList = e.DomUtil.create("div", a + "-overlays", c),
                        b.appendChild(c)
                },
                _addLayer: function(a, b, c) {
                    var d = e.stamp(a);
                    this._layers[d] = {
                        layer: a,
                        name: b,
                        overlay: c
                    },
                    this.options.autoZIndex && a.setZIndex && (this._lastZIndex++, a.setZIndex(this._lastZIndex))
                },
                _update: function() {
                    if (this._container) {
                        this._baseLayersList.innerHTML = "",
                            this._overlaysList.innerHTML = "";
                        var a, b, c = !1,
                            d = !1;
                        for (a in this._layers) b = this._layers[a],
                            this._addItem(b),
                            d = d || b.overlay,
                            c = c || !b.overlay;
                        this._separator.style.display = d && c ? "": "none"
                    }
                },
                _onLayerChange: function(a) {
                    var b = this._layers[e.stamp(a.layer)];
                    if (b) {
                        this._handlingClick || this._update();
                        var c = b.overlay ? "layeradd" === a.type ? "overlayadd": "overlayremove": "layeradd" === a.type ? "baselayerchange": null;
                        c && this._map.fire(c, b)
                    }
                },
                _createRadioElement: function(a, c) {
                    var d = '<input type="radio" class="leaflet-control-layers-selector" name="' + a + '"';
                    c && (d += ' checked="checked"'),
                        d += "/>";
                    var e = b.createElement("div");
                    return e.innerHTML = d,
                        e.firstChild
                },
                _addItem: function(a) {
                    var c, d = b.createElement("label"),
                        f = this._map.hasLayer(a.layer);
                    a.overlay ? (c = b.createElement("input"), c.type = "checkbox", c.className = "leaflet-control-layers-selector", c.defaultChecked = f) : c = this._createRadioElement("leaflet-base-layers", f),
                        c.layerId = e.stamp(a.layer),
                        e.DomEvent.on(c, "click", this._onInputClick, this);
                    var g = b.createElement("span");
                    g.innerHTML = " " + a.name,
                        d.appendChild(c),
                        d.appendChild(g);
                    var h = a.overlay ? this._overlaysList: this._baseLayersList;
                    return h.appendChild(d),
                        d
                },
                _onInputClick: function() {
                    var a, b, c, d = this._form.getElementsByTagName("input"),
                        e = d.length;
                    for (this._handlingClick = !0, a = 0; e > a; a++) b = d[a],
                        c = this._layers[b.layerId],
                        b.checked && !this._map.hasLayer(c.layer) ? this._map.addLayer(c.layer) : !b.checked && this._map.hasLayer(c.layer) && this._map.removeLayer(c.layer);
                    this._handlingClick = !1,
                        this._refocusOnMap()
                },
                _expand: function() {
                    e.DomUtil.addClass(this._container, "leaflet-control-layers-expanded")
                },
                _collapse: function() {
                    this._container.className = this._container.className.replace(" leaflet-control-layers-expanded", "")
                }
            }),
            e.control.layers = function(a, b, c) {
                return new e.Control.Layers(a, b, c)
            },
            e.PosAnimation = e.Class.extend({
                includes: e.Mixin.Events,
                run: function(a, b, c, d) {
                    this.stop(),
                        this._el = a,
                        this._inProgress = !0,
                        this._newPos = b,
                        this.fire("start"),
                        a.style[e.DomUtil.TRANSITION] = "all " + (c || .25) + "s cubic-bezier(0,0," + (d || .5) + ",1)",
                        e.DomEvent.on(a, e.DomUtil.TRANSITION_END, this._onTransitionEnd, this),
                        e.DomUtil.setPosition(a, b),
                        e.Util.falseFn(a.offsetWidth),
                        this._stepTimer = setInterval(e.bind(this._onStep, this), 50)
                },
                stop: function() {
                    this._inProgress && (e.DomUtil.setPosition(this._el, this._getPos()), this._onTransitionEnd(), e.Util.falseFn(this._el.offsetWidth))
                },
                _onStep: function() {
                    var a = this._getPos();
                    return a ? (this._el._leaflet_pos = a, void this.fire("step")) : void this._onTransitionEnd()
                },
                _transformRe: /([-+]?(?:\d*\.)?\d+)\D*, ([-+]?(?:\d*\.)?\d+)\D*\)/,
                _getPos: function() {
                    var b, c, d, f = this._el,
                        g = a.getComputedStyle(f);
                    if (e.Browser.any3d) {
                        if (d = g[e.DomUtil.TRANSFORM].match(this._transformRe), !d) return;
                        b = parseFloat(d[1]),
                            c = parseFloat(d[2])
                    } else b = parseFloat(g.left),
                        c = parseFloat(g.top);
                    return new e.Point(b, c, !0)
                },
                _onTransitionEnd: function() {
                    e.DomEvent.off(this._el, e.DomUtil.TRANSITION_END, this._onTransitionEnd, this),
                    this._inProgress && (this._inProgress = !1, this._el.style[e.DomUtil.TRANSITION] = "", this._el._leaflet_pos = this._newPos, clearInterval(this._stepTimer), this.fire("step").fire("end"))
                }
            }),
            e.Map.include({
                setView: function(a, b, d) {
                    if (b = b === c ? this._zoom: this._limitZoom(b), a = this._limitCenter(e.latLng(a), b, this.options.maxBounds), d = d || {},
                        this._panAnim && this._panAnim.stop(), this._loaded && !d.reset && d !== !0) {
                        d.animate !== c && (d.zoom = e.extend({
                                animate: d.animate
                            },
                            d.zoom), d.pan = e.extend({
                                animate: d.animate
                            },
                            d.pan));
                        var f = this._zoom !== b ? this._tryAnimatedZoom && this._tryAnimatedZoom(a, b, d.zoom) : this._tryAnimatedPan(a, d.pan);
                        if (f) return clearTimeout(this._sizeTimer),
                            this
                    }
                    return this._resetView(a, b),
                        this
                },
                panBy: function(a, b) {
                    if (a = e.point(a).round(), b = b || {},
                        !a.x && !a.y) return this;
                    if (this._panAnim || (this._panAnim = new e.PosAnimation, this._panAnim.on({
                                step: this._onPanTransitionStep,
                                end: this._onPanTransitionEnd
                            },
                            this)), b.noMoveStart || this.fire("movestart"), b.animate !== !1) {
                        e.DomUtil.addClass(this._mapPane, "leaflet-pan-anim");
                        var c = this._getMapPanePos().subtract(a);
                        this._panAnim.run(this._mapPane, c, b.duration || .25, b.easeLinearity)
                    } else this._rawPanBy(a),
                        this.fire("move").fire("moveend");
                    return this
                },
                _onPanTransitionStep: function() {
                    this.fire("move")
                },
                _onPanTransitionEnd: function() {
                    e.DomUtil.removeClass(this._mapPane, "leaflet-pan-anim"),
                        this.fire("moveend")
                },
                _tryAnimatedPan: function(a, b) {
                    var c = this._getCenterOffset(a)._floor();
                    return (b && b.animate) === !0 || this.getSize().contains(c) ? (this.panBy(c, b), !0) : !1
                }
            }),
            e.PosAnimation = e.DomUtil.TRANSITION ? e.PosAnimation: e.PosAnimation.extend({
                run: function(a, b, c, d) {
                    this.stop(),
                        this._el = a,
                        this._inProgress = !0,
                        this._duration = c || .25,
                        this._easeOutPower = 1 / Math.max(d || .5, .2),
                        this._startPos = e.DomUtil.getPosition(a),
                        this._offset = b.subtract(this._startPos),
                        this._startTime = +new Date,
                        this.fire("start"),
                        this._animate()
                },
                stop: function() {
                    this._inProgress && (this._step(), this._complete())
                },
                _animate: function() {
                    this._animId = e.Util.requestAnimFrame(this._animate, this),
                        this._step()
                },
                _step: function() {
                    var a = +new Date - this._startTime,
                        b = 1e3 * this._duration;
                    b > a ? this._runFrame(this._easeOut(a / b)) : (this._runFrame(1), this._complete())
                },
                _runFrame: function(a) {
                    var b = this._startPos.add(this._offset.multiplyBy(a));
                    e.DomUtil.setPosition(this._el, b),
                        this.fire("step")
                },
                _complete: function() {
                    e.Util.cancelAnimFrame(this._animId),
                        this._inProgress = !1,
                        this.fire("end")
                },
                _easeOut: function(a) {
                    return 1 - Math.pow(1 - a, this._easeOutPower)
                }
            }),
            e.Map.mergeOptions({
                zoomAnimation: !0,
                zoomAnimationThreshold: 4
            }),
        e.DomUtil.TRANSITION && e.Map.addInitHook(function() {
            this._zoomAnimated = this.options.zoomAnimation && e.DomUtil.TRANSITION && e.Browser.any3d && !e.Browser.android23 && !e.Browser.mobileOpera,
            this._zoomAnimated && e.DomEvent.on(this._mapPane, e.DomUtil.TRANSITION_END, this._catchTransitionEnd, this)
        }),
            e.Map.include(e.DomUtil.TRANSITION ? {
                _catchTransitionEnd: function() {
                    this._animatingZoom && this._onZoomTransitionEnd()
                },
                _nothingToAnimate: function() {
                    return ! this._container.getElementsByClassName("leaflet-zoom-animated").length
                },
                _tryAnimatedZoom: function(a, b, c) {
                    if (this._animatingZoom) return ! 0;
                    if (c = c || {},
                        !this._zoomAnimated || c.animate === !1 || this._nothingToAnimate() || Math.abs(b - this._zoom) > this.options.zoomAnimationThreshold) return ! 1;
                    var d = this.getZoomScale(b),
                        e = this._getCenterOffset(a)._divideBy(1 - 1 / d),
                        f = this._getCenterLayerPoint()._add(e);
                    return c.animate === !0 || this.getSize().contains(e) ? (this.fire("movestart").fire("zoomstart"), this._animateZoom(a, b, f, d, null, !0), !0) : !1
                },
                _animateZoom: function(a, b, c, d, f, g) {
                    this._animatingZoom = !0,
                        e.DomUtil.addClass(this._mapPane, "leaflet-zoom-anim"),
                        this._animateToCenter = a,
                        this._animateToZoom = b,
                    e.Draggable && (e.Draggable._disabled = !0),
                        this.fire("zoomanim", {
                            center: a,
                            zoom: b,
                            origin: c,
                            scale: d,
                            delta: f,
                            backwards: g
                        })
                },
                _onZoomTransitionEnd: function() {
                    this._animatingZoom = !1,
                        e.DomUtil.removeClass(this._mapPane, "leaflet-zoom-anim"),
                        this._resetView(this._animateToCenter, this._animateToZoom, !0, !0),
                    e.Draggable && (e.Draggable._disabled = !1)
                }
            }: {}),
            e.TileLayer.include({
                _animateZoom: function(a) {
                    this._animating || (this._animating = !0, this._prepareBgBuffer());
                    var b = this._bgBuffer,
                        c = e.DomUtil.TRANSFORM,
                        d = a.delta ? e.DomUtil.getTranslateString(a.delta) : b.style[c],
                        f = e.DomUtil.getScaleString(a.scale, a.origin);
                    b.style[c] = a.backwards ? f + " " + d: d + " " + f
                },
                _endZoomAnim: function() {
                    var a = this._tileContainer,
                        b = this._bgBuffer;
                    a.style.visibility = "",
                        a.parentNode.appendChild(a),
                        e.Util.falseFn(b.offsetWidth),
                        this._animating = !1
                },
                _clearBgBuffer: function() {
                    var a = this._map; ! a || a._animatingZoom || a.touchZoom._zooming || (this._bgBuffer.innerHTML = "", this._bgBuffer.style[e.DomUtil.TRANSFORM] = "")
                },
                _prepareBgBuffer: function() {
                    var a = this._tileContainer,
                        b = this._bgBuffer,
                        c = this._getLoadedTilesPercentage(b),
                        d = this._getLoadedTilesPercentage(a);
                    return b && c > .5 && .5 > d ? (a.style.visibility = "hidden", void this._stopLoadingImages(a)) : (b.style.visibility = "hidden", b.style[e.DomUtil.TRANSFORM] = "", this._tileContainer = b, b = this._bgBuffer = a, this._stopLoadingImages(b), void clearTimeout(this._clearBgBufferTimer))
                },
                _getLoadedTilesPercentage: function(a) {
                    var b, c, d = a.getElementsByTagName("img"),
                        e = 0;
                    for (b = 0, c = d.length; c > b; b++) d[b].complete && e++;
                    return e / c
                },
                _stopLoadingImages: function(a) {
                    var b, c, d, f = Array.prototype.slice.call(a.getElementsByTagName("img"));
                    for (b = 0, c = f.length; c > b; b++) d = f[b],
                    d.complete || (d.onload = e.Util.falseFn, d.onerror = e.Util.falseFn, d.src = e.Util.emptyImageUrl, d.parentNode.removeChild(d))
                }
            }),
            e.Map.include({
                _defaultLocateOptions: {
                    watch: !1,
                    setView: !1,
                    maxZoom: 1 / 0,
                    timeout: 1e4,
                    maximumAge: 0,
                    enableHighAccuracy: !1
                },
                locate: function(a) {
                    if (a = this._locateOptions = e.extend(this._defaultLocateOptions, a), !navigator.geolocation) return this._handleGeolocationError({
                        code: 0,
                        message: "Geolocation not supported."
                    }),
                        this;
                    var b = e.bind(this._handleGeolocationResponse, this),
                        c = e.bind(this._handleGeolocationError, this);
                    return a.watch ? this._locationWatchId = navigator.geolocation.watchPosition(b, c, a) : navigator.geolocation.getCurrentPosition(b, c, a),
                        this
                },
                stopLocate: function() {
                    return navigator.geolocation && navigator.geolocation.clearWatch(this._locationWatchId),
                    this._locateOptions && (this._locateOptions.setView = !1),
                        this
                },
                _handleGeolocationError: function(a) {
                    var b = a.code,
                        c = a.message || (1 === b ? "permission denied": 2 === b ? "position unavailable": "timeout");
                    this._locateOptions.setView && !this._loaded && this.fitWorld(),
                        this.fire("locationerror", {
                            code: b,
                            message: "Geolocation error: " + c + "."
                        })
                },
                _handleGeolocationResponse: function(a) {
                    var b = a.coords.latitude,
                        c = a.coords.longitude,
                        d = new e.LatLng(b, c),
                        f = 180 * a.coords.accuracy / 40075017,
                        g = f / Math.cos(e.LatLng.DEG_TO_RAD * b),
                        h = e.latLngBounds([b - f, c - g], [b + f, c + g]),
                        i = this._locateOptions;
                    if (i.setView) {
                        var j = Math.min(this.getBoundsZoom(h), i.maxZoom);
                        this.setView(d, j)
                    }
                    var k = {
                        latlng: d,
                        bounds: h,
                        timestamp: a.timestamp
                    };
                    for (var l in a.coords)"number" == typeof a.coords[l] && (k[l] = a.coords[l]);
                    this.fire("locationfound", k)
                }
            })
    } (window, document),
    function(a) {
        "use strict";
        function b(a, b) {
            var c = a.x - b.x,
                d = a.y - b.y;
            return c * c + d * d
        }
        function c(a) {
            var c = a.length;
            if (16 > c) return ! 1;
            var d, e = 1 / 0,
                f = -(1 / 0),
                g = 1 / 0,
                h = -(1 / 0);
            for (d = 0; c - 1 > d; d += 2) e = Math.min(e, a[d]),
                f = Math.max(f, a[d]),
                g = Math.min(g, a[d + 1]),
                h = Math.max(h, a[d + 1]);
            var i = f - e,
                j = h - g,
                k = i / j;
            if (.85 > k || k > 1.15) return ! 1;
            var l = {
                    x: e + i / 2,
                    y: g + j / 2
                },
                m = (i + j) / 4,
                n = m * m;
            for (d = 0; c - 1 > d; d += 2) {
                var o = b({
                        x: a[d],
                        y: a[d + 1]
                    },
                    l);
                if (.8 > o / n || o / n > 1.2) return ! 1
            }
            return ! 0
        }
        function d(a, b, c, d, e, f) {
            var g, h = e - c,
                i = f - d;
            return (0 !== h || 0 !== i) && (g = ((a - c) * h + (b - d) * i) / (h * h + i * i), g > 1 ? (c = e, d = f) : g > 0 && (c += h * g, d += i * g)),
                h = a - c,
                i = b - d,
            h * h + i * i
        }
        function e(a) {
            var b, c, e, f, g = 2,
                h = a.length / 2,
                i = new P(h),
                j = 0,
                k = h - 1,
                l = [],
                m = [],
                n = [];
            for (i[j] = i[k] = 1; k;) {
                for (c = 0, b = j + 1; k > b; b++) e = d(a[2 * b], a[2 * b + 1], a[2 * j], a[2 * j + 1], a[2 * k], a[2 * k + 1]),
                e > c && (f = b, c = e);
                c > g && (i[f] = 1, l.push(j), m.push(f), l.push(f), m.push(k)),
                    j = l.pop(),
                    k = m.pop()
            }
            for (b = 0; h > b; b++) i[b] && n.push(a[2 * b], a[2 * b + 1]);
            return n
        }
        function f(a) {
            for (var b = 1 / 0,
                     c = -(1 / 0), d = 1 / 0, e = -(1 / 0), f = 0, g = a.length - 3; g > f; f += 2) b = I(b, a[f]),
                c = J(c, a[f]),
                d = I(d, a[f + 1]),
                e = J(e, a[f + 1]);
            return {
                x: b + (c - b) / 2 << 0,
                y: d + (e - d) / 2 << 0
            }
        }
        function g(a) {
            for (var b = 180,
                     c = -180,
                     d = 0,
                     e = a.length; e > d; d += 2) b = I(b, a[d + 1]),
                c = J(c, a[d + 1]);
            return (c - b) / 2
        }
        function h(a) {
            return a / Z * 180
        }
        function i(a, b) {
            var c = {};
            return a /= u,
                b /= u,
                c[ca] = 0 >= b ? 90 : b >= 1 ? -90 : h(2 * G(B(Z * (1 - 2 * b))) - $),
                c[da] = 360 * (1 === a ? 1 : (a % 1 + 1) % 1) - 180,
                c
        }
        function j(a, b) {
            var c = I(1, J(0, .5 - C(F(_ + $ * a / 180)) / Z / 2)),
                d = b / 360 + .5;
            return {
                x: d * u << 0,
                y: c * u << 0
            }
        }
        function k(a) {
            for (var b = ea + ia,
                     c = fa + ja,
                     d = 0,
                     e = a.length - 3; e > d; d += 2) if (a[d] > ia && a[d] < b && a[d + 1] > ja && a[d + 1] < c) return ! 0;
            return ! 1
        }
        function l() {
            z || (z = setInterval(function() {
                    for (var a = va.items,
                             b = !1,
                             c = 0,
                             d = a.length; d > c; c++) a[c].scale < 1 && (a[c].scale += .1, a[c].scale > 1 && (a[c].scale = 1), b = !0);
                    Da.render(),
                    b || (clearInterval(z), z = null)
                },
                33))
        }
        function m(a) {
            ia = a.x,
                ja = a.y
        }
        function n(a) {
            w = ga + a.x,
                x = fa + a.y,
                Da.render(!0)
        }
        function o(a) {
            ea = a.width,
                fa = a.height,
                ga = ea / 2 << 0,
                ha = fa / 2 << 0,
                w = ga,
                x = fa,
                Da.setSize(ea, fa),
                v = ta - 50
        }
        function p(a) {
            t = a,
                u = aa << t;
            var b = i(ia + ga, ja + ha),
                c = j(b.latitude, 0),
                d = j(b.latitude, 1);
            qa = d.x - c.x,
                ra = N(.95, t - ba),
                na = "" + ka.alpha(ra),
                oa = "" + la.alpha(ra),
                pa = "" + ma.alpha(ra)
        }
        function q(a) {
            Da.render(),
                va.update()
        }
        function r() {
            y = !0,
                Da.render()
        }
        function s(a) {
            y = !1,
                p(a.zoom),
                va.update(),
                Da.render()
        }
        var t, u, v, w, x, y, z, A = Math,
            B = A.exp,
            C = A.log,
            D = A.sin,
            E = A.cos,
            F = A.tan,
            G = A.atan,
            H = A.atan2,
            I = A.min,
            J = A.max,
            K = A.sqrt,
            M = A.ceil,
            N = (A.floor, A.round, A.pow),
            O = O || Array,
            P = P || Array,
            Q = /iP(ad|hone|od)/g.test(navigator.userAgent),
            R = !!~navigator.userAgent.indexOf("Trident"),
            S = !a.requestAnimationFrame || Q || R ?
                function(a) {
                    a()
                }: a.requestAnimationFrame,
            T = function(a) {
                function b(a, b, c) {
                    return 0 > c && (c += 1),
                    c > 1 && (c -= 1),
                        1 / 6 > c ? a + 6 * (b - a) * c: .5 > c ? b: 2 / 3 > c ? a + (b - a) * (2 / 3 - c) * 6 : a
                }
                function c(a, b) {
                    return Math.min(b, Math.max(0, a))
                }
                var d = {
                        aqua: "#00ffff",
                        black: "#000000",
                        blue: "#0000ff",
                        fuchsia: "#ff00ff",
                        gray: "#808080",
                        grey: "#808080",
                        green: "#008000",
                        lime: "#00ff00",
                        maroon: "#800000",
                        navy: "#000080",
                        olive: "#808000",
                        orange: "#ffa500",
                        purple: "#800080",
                        red: "#ff0000",
                        silver: "#c0c0c0",
                        teal: "#008080",
                        white: "#ffffff",
                        yellow: "#ffff00"
                    },
                    e = function(a, b, c, d) {
                        this.H = a,
                            this.S = b,
                            this.L = c,
                            this.A = d
                    };
                return e.parse = function(a) {
                    var b, c = 0,
                        e = 0,
                        f = 0,
                        g = 1;
                    if (a = ("" + a).toLowerCase(), a = d[a] || a, b = a.match(/^#(\w{2})(\w{2})(\w{2})$/)) c = parseInt(b[1], 16),
                        e = parseInt(b[2], 16),
                        f = parseInt(b[3], 16);
                    else {
                        if (! (b = a.match(/rgba?\((\d+)\D+(\d+)\D+(\d+)(\D+([\d.]+))?\)/))) return;
                        c = parseInt(b[1], 10),
                            e = parseInt(b[2], 10),
                            f = parseInt(b[3], 10),
                            g = b[4] ? parseFloat(b[5]) : 1
                    }
                    return this.fromRGBA(c, e, f, g)
                },
                    e.fromRGBA = function(a, b, c, d) {
                        "object" == typeof a ? (b = a.g / 255, c = a.b / 255, d = a.a, a = a.r / 255) : (a /= 255, b /= 255, c /= 255);
                        var f, g, h = Math.max(a, b, c),
                            i = Math.min(a, b, c),
                            j = (h + i) / 2,
                            k = h - i;
                        if (k) {
                            switch (g = j > .5 ? k / (2 - h - i) : k / (h + i), h) {
                                case a:
                                    f = (b - c) / k + (c > b ? 6 : 0);
                                    break;
                                case b:
                                    f = (c - a) / k + 2;
                                    break;
                                case c:
                                    f = (a - b) / k + 4
                            }
                            f *= 60
                        } else f = g = 0;
                        return new e(f, g, j, d)
                    },
                    e.prototype = {
                        toRGBA: function() {
                            var a = c(this.H, 360),
                                d = c(this.S, 1),
                                e = c(this.L, 1),
                                f = {
                                    a: c(this.A, 1)
                                };
                            if (0 === d) f.r = e,
                                f.g = e,
                                f.b = e;
                            else {
                                var g = .5 > e ? e * (1 + d) : e + d - e * d,
                                    h = 2 * e - g;
                                a /= 360,
                                    f.r = b(h, g, a + 1 / 3),
                                    f.g = b(h, g, a),
                                    f.b = b(h, g, a - 1 / 3)
                            }
                            return {
                                r: Math.round(255 * f.r),
                                g: Math.round(255 * f.g),
                                b: Math.round(255 * f.b),
                                a: f.a
                            }
                        },
                        toString: function() {
                            var a = this.toRGBA();
                            return 1 === a.a ? "#" + ((1 << 24) + (a.r << 16) + (a.g << 8) + a.b).toString(16).slice(1, 7) : "rgba(" + [a.r, a.g, a.b, a.a.toFixed(2)].join(",") + ")"
                        },
                        hue: function(a) {
                            return new e(this.H * a, this.S, this.L, this.A)
                        },
                        saturation: function(a) {
                            return new e(this.H, this.S * a, this.L, this.A)
                        },
                        lightness: function(a) {
                            return new e(this.H, this.S, this.L * a, this.A)
                        },
                        alpha: function(a) {
                            return new e(this.H, this.S, this.L, this.A * a)
                        }
                    },
                    e
            } (this),
            U = function() {
                function a(a) {
                    return a.valueOf() / s - .5 + t
                }
                function b(b) {
                    return a(b) - u
                }
                function c(a, b) {
                    return q(m(a) * n(v) - o(b) * m(v), n(a))
                }
                function d(a, b) {
                    return p(m(b) * n(v) + n(b) * m(v) * m(a))
                }
                function e(a, b, c) {
                    return q(m(a), n(a) * m(b) - o(c) * n(b))
                }
                function f(a, b, c) {
                    return p(m(b) * m(c) + n(b) * n(c) * n(a))
                }
                function g(a, b) {
                    return r * (280.16 + 360.9856235 * a) - b
                }
                function h(a) {
                    return r * (357.5291 + .98560028 * a)
                }
                function i(a) {
                    return r * (1.9148 * m(a) + .02 * m(2 * a) + 3e-4 * m(3 * a))
                }
                function j(a, b) {
                    var c = 102.9372 * r;
                    return a + b + c + l
                }
                var k = Math,
                    l = k.PI,
                    m = k.sin,
                    n = k.cos,
                    o = k.tan,
                    p = k.asin,
                    q = k.atan2,
                    r = l / 180,
                    s = 864e5,
                    t = 2440588,
                    u = 2451545,
                    v = 23.4397 * r;
                return function(a, k, m) {
                    var n = r * -m,
                        o = r * k,
                        p = b(a),
                        q = h(p),
                        s = i(q),
                        t = j(q, s),
                        u = d(t, 0),
                        v = c(t, 0),
                        w = g(p, n),
                        x = w - v;
                    return {
                        altitude: f(x, o, u),
                        azimuth: e(x, o, u) - l / 2
                    }
                }
            } (),
            V = function() {
                function a(a) {
                    return a = a.toLowerCase(),
                        "#" === a[0] ? a: i[j[a] || a] || null
                }
                function b(a) {
                    var b, c, d, e, f, g, h = 0;
                    for (f = 0, g = a.length - 3; g > f; f += 2) b = a[f],
                        c = a[f + 1],
                        d = a[f + 2],
                        e = a[f + 3],
                        h += b * e - d * c;
                    return h / 2 > 0 ? k: l
                }
                function c(a, c) {
                    var d = b(a);
                    if (d === c) return a;
                    for (var e = [], f = a.length - 2; f >= 0; f -= 2) e.push(a[f], a[f + 1]);
                    return e
                }
                function d(b) {
                    var c = {};
                    b = b || {},
                        c.height = b.height || (b.levels ? b.levels * h: sa),
                        c.minHeight = b.minHeight || (b.minLevel ? b.minLevel * h: 0);
                    var d = b.material ? a(b.material) : b.wallColor || b.color;
                    d && (c.wallColor = d);
                    var e = b.roofMaterial ? a(b.roofMaterial) : b.roofColor;
                    switch (e && (c.roofColor = e), b.shape) {
                        case "cylinder":
                        case "cone":
                        case "dome":
                        case "sphere":
                            c.shape = b.shape,
                                c.isRotational = !0;
                            break;
                        case "pyramid":
                            c.shape = b.shape
                    }
                    switch (b.roofShape) {
                        case "cone":
                        case "dome":
                            c.roofShape = b.roofShape,
                                c.isRotational = !0;
                            break;
                        case "pyramid":
                            c.roofShape = b.roofShape
                    }
                    return c.roofShape && b.roofHeight ? (c.roofHeight = b.roofHeight, c.height = J(0, c.height - c.roofHeight)) : c.roofHeight = 0,
                        c
                }
                function e(a) {
                    var b, d, f, g, h = [];
                    switch (a.type) {
                        case "GeometryCollection":
                            for (h = [], b = 0, d = a.geometries.length; d > b; b++)(g = e(a.geometries[b])) && h.push.apply(h, g);
                            return h;
                        case "MultiPolygon":
                            for (h = [], b = 0, d = a.coordinates.length; d > b; b++)(g = e({
                                type: "Polygon",
                                coordinates: a.coordinates[b]
                            })) && h.push.apply(h, g);
                            return h;
                        case "Polygon":
                            f = a.coordinates;
                            break;
                        default:
                            return []
                    }
                    var i, j, m, n = 1,
                        o = 0,
                        p = [],
                        q = [];
                    for (m = f[0], b = 0, d = m.length; d > b; b++) p.push(m[b][n], m[b][o]);
                    for (p = c(p, k), b = 0, d = f.length - 1; d > b; b++) {
                        for (m = f[b + 1], q[b] = [], i = 0, j = m.length; j > i; i++) q[b].push(m[i][n], m[i][o]);
                        q[b] = c(q[b], l)
                    }
                    return [{
                        outer: p,
                        inner: q.length ? q: null
                    }]
                }
                function f(a) {
                    var b = {};
                    for (var c in a) a.hasOwnProperty(c) && (b[c] = a[c]);
                    return b
                }
                var h = 3,
                    i = {
                        brick: "#cc7755",
                        bronze: "#ffeecc",
                        canvas: "#fff8f0",
                        concrete: "#999999",
                        copper: "#a0e0d0",
                        glass: "#e8f8f8",
                        gold: "#ffcc00",
                        plants: "#009933",
                        metal: "#aaaaaa",
                        panel: "#fff8f0",
                        plaster: "#999999",
                        roof_tiles: "#f08060",
                        silver: "#cccccc",
                        slate: "#666666",
                        stone: "#996666",
                        tar_paper: "#333333",
                        wood: "#deb887"
                    },
                    j = {
                        asphalt: "tar_paper",
                        bitumen: "tar_paper",
                        block: "stone",
                        bricks: "brick",
                        glas: "glass",
                        glassfront: "glass",
                        grass: "plants",
                        masonry: "stone",
                        granite: "stone",
                        panels: "panel",
                        paving_stones: "stone",
                        plastered: "plaster",
                        rooftiles: "roof_tiles",
                        roofingfelt: "tar_paper",
                        sandstone: "stone",
                        sheet: "canvas",
                        sheets: "canvas",
                        shingle: "tar_paper",
                        shingles: "tar_paper",
                        slates: "slate",
                        steel: "metal",
                        tar: "tar_paper",
                        tent: "canvas",
                        thatch: "plants",
                        tile: "roof_tiles",
                        tiles: "roof_tiles"
                    },
                    k = "CW",
                    l = "CCW";
                return {
                    read: function(a) {
                        if (!a || "FeatureCollection" !== a.type) return [];
                        var b, c, h, i, j, k, l, m, n = a.features,
                            o = [];
                        for (b = 0, c = n.length; c > b; b++) if (j = n[b], "Feature" === j.type && Ga(j) !== !1) for (l = d(j.properties), k = e(j.geometry), h = 0, i = k.length; i > h; h++) m = f(l),
                            m.footprint = k[h].outer,
                        m.isRotational && (m.radius = g(m.footprint)),
                        k[h].inner && (m.holes = k[h].inner),
                        (j.id || j.properties.id) && (m.id = j.id || j.properties.id),
                        j.properties.relationId && (m.relationId = j.properties.relationId),
                            o.push(m);
                        return o
                    }
                }
            } (),
            W = "0.2.2b",
            X = '&copy; <a href="https://osmbuildings.org/">OSM Buildings</a>',
            Y = "https://{s}.data.osmbuildings.org/0.2/{k}/tile/{z}/{x}/{y}.json",
            Z = Math.PI,
            $ = Z / 2,
            _ = Z / 4,
            aa = 256,
            ba = 15,
            ca = "latitude",
            da = "longitude",
            ea = 0,
            fa = 0,
            ga = 0,
            ha = 0,
            ia = 0,
            ja = 0,
            ka = T.parse("rgba(200, 190, 180)"),
            la = ka.lightness(.8),
            ma = ka.lightness(1.2),
            na = "" + ka,
            oa = "" + la,
            pa = "" + ma,
            qa = 0,
            ra = 1,
            sa = 5,
            ta = 450,
            ua = function() {
                function a(a, f) {
                    if (b[a]) return void(f && f(b[a]));
                    var g = new XMLHttpRequest;
                    return g.onreadystatechange = function() {
                        if (4 === g.readyState && !(!g.status || g.status < 200 || g.status > 299) && f && g.responseText) {
                            var h = g.responseText;
                            for (b[a] = h, c.push({
                                url: a,
                                size: h.length
                            }), d += h.length, f(h); d > e;) {
                                var i = c.shift();
                                d -= i.size,
                                    delete b[i.url]
                            }
                        }
                    },
                        g.open("GET", a),
                        g.send(null),
                        g
                }
                var b = {},
                    c = [],
                    d = 0,
                    e = 5242880;
                return {
                    loadJSON: function(b, c) {
                        return a(b,
                            function(a) {
                                var b;
                                try {
                                    b = JSON.parse(a)
                                } catch(d) {}
                                c(b)
                            })
                    }
                }
            } (),
            va = {
                loadedItems: {},
                items: [],
                getPixelFootprint: function(a) {
                    for (var b, c = new O(a.length), d = 0, f = a.length - 1; f > d; d += 2) b = j(a[d], a[d + 1]),
                        c[d] = b.x,
                        c[d + 1] = b.y;
                    return c = e(c),
                        c.length < 8 ? void 0 : c
                },
                resetItems: function() {
                    this.items = [],
                        this.loadedItems = {},
                        Ca.reset()
                },
                addRenderItems: function(a, b) {
                    for (var c, d, e, f = V.read(a), g = 0, h = f.length; h > g; g++) c = f[g],
                        e = c.id || [c.footprint[0], c.footprint[1], c.height, c.minHeight].join(","),
                    this.loadedItems[e] || (d = this.scale(c)) && (d.scale = b ? 0 : 1, this.items.push(d), this.loadedItems[e] = 1);
                    l()
                },
                scale: function(a) {
                    var b = {},
                        d = 6 / N(2, t - ba);
                    if (a.id && (b.id = a.id), b.height = I(a.height / d, v), b.minHeight = isNaN(a.minHeight) ? 0 : a.minHeight / d, !(b.minHeight > v) && (b.footprint = this.getPixelFootprint(a.footprint), b.footprint)) {
                        if (b.center = f(b.footprint), a.radius && (b.radius = a.radius * qa), a.shape && (b.shape = a.shape), a.roofShape && (b.roofShape = a.roofShape), "cone" !== b.roofShape && "dome" !== b.roofShape || b.shape || !c(b.footprint) || (b.shape = "cylinder"), a.holes) {
                            b.holes = [];
                            for (var e, g = 0,
                                     h = a.holes.length; h > g; g++)(e = this.getPixelFootprint(a.holes[g])) && b.holes.push(e)
                        }
                        var i;
                        if (a.wallColor && (i = T.parse(a.wallColor)) && (i = i.alpha(ra), b.altColor = "" + i.lightness(.8), b.wallColor = "" + i), a.roofColor && (i = T.parse(a.roofColor)) && (b.roofColor = "" + i.alpha(ra)), a.relationId && (b.relationId = a.relationId), b.hitColor = Ca.idToColor(a.relationId || a.id), b.roofHeight = isNaN(a.roofHeight) ? 0 : a.roofHeight / d, !(b.height + b.roofHeight <= b.minHeight)) return b
                    }
                },
                set: function(a) {
                    this.isStatic = !0,
                        this.resetItems(),
                        this._staticData = a,
                        this.addRenderItems(this._staticData, !0)
                },
                load: function(a, b) {
                    this.src = a || Y.replace("{k}", b || "ph2apjye"),
                        this.update()
                },
                update: function() {
                    function a(a) {
                        k.addRenderItems(a)
                    }
                    if (this.resetItems(), !(ba > t)) {
                        if (this.isStatic && this._staticData) return void this.addRenderItems(this._staticData);
                        if (this.src) {
                            var b, c, d = 15,
                                e = 256,
                                f = t > d ? e << t - d: e >> d - t,
                                g = ia / f << 0,
                                h = ja / f << 0,
                                i = M((ia + ea) / f),
                                j = M((ja + fa) / f),
                                k = this;
                            for (c = h; j >= c; c++) for (b = g; i >= b; b++) this.loadTile(b, c, d, a)
                        }
                    }
                },
                loadTile: function(a, b, c, d) {
                    var e = "abcd" [(a + b) % 4],
                        f = this.src.replace("{s}", e).replace("{x}", a).replace("{y}", b).replace("{z}", c);
                    return ua.loadJSON(f, d)
                }
            },
            wa = {
                draw: function(a, b, c, d, e, f, g, h) {
                    var i, j, k = this._extrude(a, b, d, e, f, g),
                        l = [];
                    if (c) for (i = 0, j = c.length; j > i; i++) l[i] = this._extrude(a, c[i], d, e, f, g);
                    if (a.fillStyle = h, a.beginPath(), this._ring(a, k), c) for (i = 0, j = l.length; j > i; i++) this._ring(a, l[i]);
                    a.closePath(),
                        a.stroke(),
                        a.fill()
                },
                _extrude: function(a, b, c, d, e, f) {
                    for (var g, h, i = ta / (ta - c), j = ta / (ta - d), k = {
                            x: 0,
                            y: 0
                        },
                             l = {
                                 x: 0,
                                 y: 0
                             },
                             m = [], n = 0, o = b.length - 3; o > n; n += 2) k.x = b[n] - ia,
                        k.y = b[n + 1] - ja,
                        l.x = b[n + 2] - ia,
                        l.y = b[n + 3] - ja,
                        g = za.project(k, i),
                        h = za.project(l, i),
                    d && (k = za.project(k, j), l = za.project(l, j)),
                    (l.x - k.x) * (g.y - k.y) > (g.x - k.x) * (l.y - k.y) && (k.x < l.x && k.y < l.y || k.x > l.x && k.y > l.y ? a.fillStyle = f: a.fillStyle = e, a.beginPath(), this._ring(a, [l.x, l.y, k.x, k.y, g.x, g.y, h.x, h.y]), a.closePath(), a.fill()),
                        m[n] = g.x,
                        m[n + 1] = g.y;
                    return m
                },
                _ring: function(a, b) {
                    a.moveTo(b[0], b[1]);
                    for (var c = 2,
                             d = b.length - 1; d > c; c += 2) a.lineTo(b[c], b[c + 1])
                },
                simplified: function(a, b, c) {
                    if (a.beginPath(), this._ringAbs(a, b), c) for (var d = 0,
                                                                        e = c.length; e > d; d++) this._ringAbs(a, c[d]);
                    a.closePath(),
                        a.stroke(),
                        a.fill()
                },
                _ringAbs: function(a, b) {
                    a.moveTo(b[0] - ia, b[1] - ja);
                    for (var c = 2,
                             d = b.length - 1; d > c; c += 2) a.lineTo(b[c] - ia, b[c + 1] - ja)
                },
                shadow: function(a, b, c, d, e) {
                    for (var f, g, h = null,
                             i = {
                                 x: 0,
                                 y: 0
                             },
                             j = {
                                 x: 0,
                                 y: 0
                             },
                             k = 0, l = b.length - 3; l > k; k += 2) i.x = b[k] - ia,
                        i.y = b[k + 1] - ja,
                        j.x = b[k + 2] - ia,
                        j.y = b[k + 3] - ja,
                        f = Ba.project(i, d),
                        g = Ba.project(j, d),
                    e && (i = Ba.project(i, e), j = Ba.project(j, e)),
                        (j.x - i.x) * (f.y - i.y) > (f.x - i.x) * (j.y - i.y) ? (1 === h && a.lineTo(i.x, i.y), h = 0, k || a.moveTo(i.x, i.y), a.lineTo(j.x, j.y)) : (0 === h && a.lineTo(f.x, f.y), h = 1, k || a.moveTo(f.x, f.y), a.lineTo(g.x, g.y));
                    if (c) for (k = 0, l = c.length; l > k; k++) this._ringAbs(a, c[k])
                },
                shadowMask: function(a, b, c) {
                    if (this._ringAbs(a, b), c) for (var d = 0,
                                                         e = c.length; e > d; d++) this._ringAbs(a, c[d])
                },
                hitArea: function(a, b, c, d, e, f) {
                    var g, h, i = null,
                        j = {
                            x: 0,
                            y: 0
                        },
                        k = {
                            x: 0,
                            y: 0
                        },
                        l = ta / (ta - d),
                        m = ta / (ta - e);
                    a.fillStyle = f,
                        a.beginPath();
                    for (var n = 0,
                             o = b.length - 3; o > n; n += 2) j.x = b[n] - ia,
                        j.y = b[n + 1] - ja,
                        k.x = b[n + 2] - ia,
                        k.y = b[n + 3] - ja,
                        g = za.project(j, l),
                        h = za.project(k, l),
                    e && (j = za.project(j, m), k = za.project(k, m)),
                        (k.x - j.x) * (g.y - j.y) > (g.x - j.x) * (k.y - j.y) ? (1 === i && a.lineTo(j.x, j.y), i = 0, n || a.moveTo(j.x, j.y), a.lineTo(k.x, k.y)) : (0 === i && a.lineTo(g.x, g.y), i = 1, n || a.moveTo(g.x, g.y), a.lineTo(h.x, h.y));
                    a.closePath(),
                        a.fill()
                }
            },
            xa = {
                draw: function(a, b, c, d, e, f, g, h, i) {
                    var j, k, l = {
                            x: b.x - ia,
                            y: b.y - ja
                        },
                        m = ta / (ta - e),
                        n = ta / (ta - f),
                        o = za.project(l, m);
                    d *= m,
                    f && (l = za.project(l, n), c *= n);
                    var p = this._tangents(l, c, o, d);
                    p ? (j = H(p[0].y1 - l.y, p[0].x1 - l.x), k = H(p[1].y1 - l.y, p[1].x1 - l.x)) : (j = 1.5 * Z, k = 1.5 * Z),
                        a.fillStyle = g,
                        a.beginPath(),
                        a.arc(o.x, o.y, d, $, j, !0),
                        a.arc(l.x, l.y, c, j, $),
                        a.closePath(),
                        a.fill(),
                        a.fillStyle = h,
                        a.beginPath(),
                        a.arc(o.x, o.y, d, k, $, !0),
                        a.arc(l.x, l.y, c, $, k),
                        a.closePath(),
                        a.fill(),
                        a.fillStyle = i,
                        this._circle(a, o, d)
                },
                simplified: function(a, b, c) {
                    this._circle(a, {
                            x: b.x - ia,
                            y: b.y - ja
                        },
                        c)
                },
                shadow: function(a, b, c, d, e, f) {
                    var g, h, i = {
                            x: b.x - ia,
                            y: b.y - ja
                        },
                        j = Ba.project(i, e);
                    f && (i = Ba.project(i, f));
                    var k = this._tangents(i, c, j, d);
                    k ? (g = H(k[0].y1 - i.y, k[0].x1 - i.x), h = H(k[1].y1 - i.y, k[1].x1 - i.x), a.moveTo(k[1].x2, k[1].y2), a.arc(j.x, j.y, d, h, g), a.arc(i.x, i.y, c, g, h)) : (a.moveTo(i.x + c, i.y), a.arc(i.x, i.y, c, 0, 2 * Z))
                },
                shadowMask: function(a, b, c) {
                    var d = {
                        x: b.x - ia,
                        y: b.y - ja
                    };
                    a.moveTo(d.x + c, d.y),
                        a.arc(d.x, d.y, c, 0, 2 * Z)
                },
                hitArea: function(a, b, c, d, e, f, g) {
                    var h, i, j = {
                            x: b.x - ia,
                            y: b.y - ja
                        },
                        k = ta / (ta - e),
                        l = ta / (ta - f),
                        m = za.project(j, k);
                    d *= k,
                    f && (j = za.project(j, l), c *= l);
                    var n = this._tangents(j, c, m, d);
                    a.fillStyle = g,
                        a.beginPath(),
                        n ? (h = H(n[0].y1 - j.y, n[0].x1 - j.x), i = H(n[1].y1 - j.y, n[1].x1 - j.x), a.moveTo(n[1].x2, n[1].y2), a.arc(m.x, m.y, d, i, h), a.arc(j.x, j.y, c, h, i)) : (a.moveTo(j.x + c, j.y), a.arc(j.x, j.y, c, 0, 2 * Z)),
                        a.closePath(),
                        a.fill()
                },
                _circle: function(a, b, c) {
                    a.beginPath(),
                        a.arc(b.x, b.y, c, 0, 2 * Z),
                        a.stroke(),
                        a.fill()
                },
                _tangents: function(a, b, c, d) {
                    var e = a.x - c.x,
                        f = a.y - c.y,
                        g = b - d,
                        h = e * e + f * f;
                    if (! (g * g >= h)) {
                        var i, j, k, l = K(h),
                            m = -e / l,
                            n = -f / l,
                            o = g / l,
                            p = [];
                        i = K(J(0, 1 - o * o));
                        for (var q = 1; q >= -1; q -= 2) j = m * o - q * i * n,
                            k = n * o + q * i * m,
                            p.push({
                                x1: a.x + b * j << 0,
                                y1: a.y + b * k << 0,
                                x2: c.x + d * j << 0,
                                y2: c.y + d * k << 0
                            });
                        return p
                    }
                }
            },
            ya = {
                draw: function(a, b, c, d, e, f, g) {
                    for (var h = {
                            x: c.x - ia,
                            y: c.y - ja
                        },
                             i = ta / (ta - d), j = ta / (ta - e), k = za.project(h, i), l = {
                            x: 0,
                            y: 0
                        },
                             m = {
                                 x: 0,
                                 y: 0
                             },
                             n = 0, o = b.length - 3; o > n; n += 2) l.x = b[n] - ia,
                        l.y = b[n + 1] - ja,
                        m.x = b[n + 2] - ia,
                        m.y = b[n + 3] - ja,
                    e && (l = za.project(l, j), m = za.project(m, j)),
                    (m.x - l.x) * (k.y - l.y) > (k.x - l.x) * (m.y - l.y) && (l.x < m.x && l.y < m.y || l.x > m.x && l.y > m.y ? a.fillStyle = g: a.fillStyle = f, a.beginPath(), this._triangle(a, l, m, k), a.closePath(), a.fill())
                },
                _triangle: function(a, b, c, d) {
                    a.moveTo(b.x, b.y),
                        a.lineTo(c.x, c.y),
                        a.lineTo(d.x, d.y)
                },
                _ring: function(a, b) {
                    a.moveTo(b[0] - ia, b[1] - ja);
                    for (var c = 2,
                             d = b.length - 1; d > c; c += 2) a.lineTo(b[c] - ia, b[c + 1] - ja)
                },
                shadow: function(a, b, c, d, e) {
                    for (var f = {
                            x: 0,
                            y: 0
                        },
                             g = {
                                 x: 0,
                                 y: 0
                             },
                             h = {
                                 x: c.x - ia,
                                 y: c.y - ja
                             },
                             i = Ba.project(h, d), j = 0, k = b.length - 3; k > j; j += 2) f.x = b[j] - ia,
                        f.y = b[j + 1] - ja,
                        g.x = b[j + 2] - ia,
                        g.y = b[j + 3] - ja,
                    e && (f = Ba.project(f, e), g = Ba.project(g, e)),
                    (g.x - f.x) * (i.y - f.y) > (i.x - f.x) * (g.y - f.y) && this._triangle(a, f, g, i)
                },
                shadowMask: function(a, b) {
                    this._ring(a, b)
                },
                hitArea: function(a, b, c, d, e, f) {
                    var g = {
                            x: c.x - ia,
                            y: c.y - ja
                        },
                        h = ta / (ta - d),
                        i = ta / (ta - e),
                        j = za.project(g, h),
                        k = {
                            x: 0,
                            y: 0
                        },
                        l = {
                            x: 0,
                            y: 0
                        };
                    a.fillStyle = f,
                        a.beginPath();
                    for (var m = 0,
                             n = b.length - 3; n > m; m += 2) k.x = b[m] - ia,
                        k.y = b[m + 1] - ja,
                        l.x = b[m + 2] - ia,
                        l.y = b[m + 3] - ja,
                    e && (k = za.project(k, i), l = za.project(l, i)),
                    (l.x - k.x) * (j.y - k.y) > (j.x - k.x) * (l.y - k.y) && this._triangle(a, k, l, j);
                    a.closePath(),
                        a.fill()
                }
            },
            za = {
                project: function(a, b) {
                    return {
                        x: (a.x - w) * b + w << 0,
                        y: (a.y - x) * b + x << 0
                    }
                },
                render: function() {
                    var a = this.context;
                    if (a.clearRect(0, 0, ea, fa), !(ba > t || y)) {
                        var c, d, e, f, g, h, i, j = {
                                x: w + ia,
                                y: x + ja
                            },
                            l = va.items;
                        l.sort(function(a, c) {
                            return a.minHeight - c.minHeight || b(c.center, j) - b(a.center, j) || c.height - a.height
                        });
                        for (var m = 0,
                                 n = l.length; n > m; m++) if (c = l[m], !Aa.isSimple(c) && (f = c.footprint, k(f))) {
                            switch (d = c.scale < 1 ? c.height * c.scale: c.height, e = 0, c.minHeight && (e = c.scale < 1 ? c.minHeight * c.scale: c.minHeight), g = c.wallColor || na, h = c.altColor || oa, i = c.roofColor || pa, a.strokeStyle = h, c.shape) {
                                case "cylinder":
                                    xa.draw(a, c.center, c.radius, c.radius, d, e, g, h, i);
                                    break;
                                case "cone":
                                    xa.draw(a, c.center, c.radius, 0, d, e, g, h);
                                    break;
                                case "dome":
                                    xa.draw(a, c.center, c.radius, c.radius / 2, d, e, g, h);
                                    break;
                                case "sphere":
                                    xa.draw(a, c.center, c.radius, c.radius, d, e, g, h, i);
                                    break;
                                case "pyramid":
                                    ya.draw(a, f, c.center, d, e, g, h);
                                    break;
                                default:
                                    wa.draw(a, f, c.holes, d, e, g, h, i)
                            }
                            switch (c.roofShape) {
                                case "cone":
                                    xa.draw(a, c.center, c.radius, 0, d + c.roofHeight, d, i, "" + T.parse(i).lightness(.9));
                                    break;
                                case "dome":
                                    xa.draw(a, c.center, c.radius, c.radius / 2, d + c.roofHeight, d, i, "" + T.parse(i).lightness(.9));
                                    break;
                                case "pyramid":
                                    ya.draw(a, f, c.center, d + c.roofHeight, d, i, T.parse(i).lightness(.9))
                            }
                        }
                    }
                }
            },
            Aa = {
                maxZoom: ba + 2,
                maxHeight: 5,
                isSimple: function(a) {
                    return t <= this.maxZoom && a.height + a.roofHeight < this.maxHeight
                },
                render: function() {
                    var a = this.context;
                    if (a.clearRect(0, 0, ea, fa), !(ba > t || y || t > this.maxZoom)) for (var b, c, d = va.items,
                                                                                                e = 0,
                                                                                                f = d.length; f > e; e++) if (b = d[e], !(b.height >= this.maxHeight) && (c = b.footprint, k(c))) switch (a.strokeStyle = b.altColor || oa, a.fillStyle = b.roofColor || pa, b.shape) {
                        case "cylinder":
                        case "cone":
                        case "dome":
                        case "sphere":
                            xa.simplified(a, b.center, b.radius);
                            break;
                        default:
                            wa.simplified(a, c, b.holes)
                    }
                }
            },
            Ba = {
                enabled: !0,
                color: "#666666",
                blurColor: "#000000",
                blurSize: 15,
                date: new Date,
                direction: {
                    x: 0,
                    y: 0
                },
                project: function(a, b) {
                    return {
                        x: a.x + this.direction.x * b,
                        y: a.y + this.direction.y * b
                    }
                },
                render: function() {
                    var a, b, c, d, e = this.context;
                    if (e.clearRect(0, 0, ea, fa), !(!this.enabled || ba > t || y || (a = i(ga + ia, ha + ja), b = U(this.date, a.latitude, a.longitude), b.altitude <= 0))) {
                        c = 1 / F(b.altitude),
                            d = 5 > c ? .75 : 1 / c * 5,
                            this.direction.x = E(b.azimuth) * c,
                            this.direction.y = D(b.azimuth) * c;
                        var f, g, h, j, l, m, n = va.items;
                        for (e.canvas.style.opacity = d / (2 * ra), e.shadowColor = this.blurColor, e.shadowBlur = this.blurSize * (ra / 2), e.fillStyle = this.color, e.beginPath(), f = 0, g = n.length; g > f; f++) if (h = n[f], m = h.footprint, k(m)) {
                            switch (j = h.scale < 1 ? h.height * h.scale: h.height, l = 0, h.minHeight && (l = h.scale < 1 ? h.minHeight * h.scale: h.minHeight), h.shape) {
                                case "cylinder":
                                    xa.shadow(e, h.center, h.radius, h.radius, j, l);
                                    break;
                                case "cone":
                                    xa.shadow(e, h.center, h.radius, 0, j, l);
                                    break;
                                case "dome":
                                    xa.shadow(e, h.center, h.radius, h.radius / 2, j, l);
                                    break;
                                case "sphere":
                                    xa.shadow(e, h.center, h.radius, h.radius, j, l);
                                    break;
                                case "pyramid":
                                    ya.shadow(e, m, h.center, j, l);
                                    break;
                                default:
                                    wa.shadow(e, m, h.holes, j, l)
                            }
                            switch (h.roofShape) {
                                case "cone":
                                    xa.shadow(e, h.center, h.radius, 0, j + h.roofHeight, j);
                                    break;
                                case "dome":
                                    xa.shadow(e, h.center, h.radius, h.radius / 2, j + h.roofHeight, j);
                                    break;
                                case "pyramid":
                                    ya.shadow(e, m, h.center, j + h.roofHeight, j)
                            }
                        }
                        for (e.closePath(), e.fill(), e.shadowBlur = null, e.globalCompositeOperation = "destination-out", e.beginPath(), f = 0, g = n.length; g > f; f++) if (h = n[f], m = h.footprint, k(m) && !h.minHeight) switch (h.shape) {
                            case "cylinder":
                            case "cone":
                            case "dome":
                                xa.shadowMask(e, h.center, h.radius);
                                break;
                            default:
                                wa.shadowMask(e, m, h.holes)
                        }
                        e.fillStyle = "#00ff00",
                            e.fill(),
                            e.globalCompositeOperation = "source-over"
                    }
                }
            },
            Ca = {
                _idMapping: [null],
                reset: function() {
                    this._idMapping = [null]
                },
                render: function() {
                    if (!this._timer) {
                        var a = this;
                        this._timer = setTimeout(function() {
                                a._timer = null,
                                    a._render()
                            },
                            500)
                    }
                },
                _render: function() {
                    var a = this.context;
                    if (a.clearRect(0, 0, ea, fa), !(ba > t || y)) {
                        var c, d, e, f, g, h = {
                                x: w + ia,
                                y: x + ja
                            },
                            i = va.items;
                        i.sort(function(a, c) {
                            return a.minHeight - c.minHeight || b(c.center, h) - b(a.center, h) || c.height - a.height
                        });
                        for (var j = 0,
                                 l = i.length; l > j; j++) if (c = i[j], (g = c.hitColor) && (f = c.footprint, k(f))) {
                            switch (d = c.height, e = 0, c.minHeight && (e = c.minHeight), c.shape) {
                                case "cylinder":
                                    xa.hitArea(a, c.center, c.radius, c.radius, d, e, g);
                                    break;
                                case "cone":
                                    xa.hitArea(a, c.center, c.radius, 0, d, e, g);
                                    break;
                                case "dome":
                                    xa.hitArea(a, c.center, c.radius, c.radius / 2, d, e, g);
                                    break;
                                case "sphere":
                                    xa.hitArea(a, c.center, c.radius, c.radius, d, e, g);
                                    break;
                                case "pyramid":
                                    ya.hitArea(a, f, c.center, d, e, g);
                                    break;
                                default:
                                    wa.hitArea(a, f, c.holes, d, e, g)
                            }
                            switch (c.roofShape) {
                                case "cone":
                                    xa.hitArea(a, c.center, c.radius, 0, d + c.roofHeight, d, g);
                                    break;
                                case "dome":
                                    xa.hitArea(a, c.center, c.radius, c.radius / 2, d + c.roofHeight, d, g);
                                    break;
                                case "pyramid":
                                    ya.hitArea(a, f, c.center, d + c.roofHeight, d, g)
                            }
                        }
                        ea && fa && (this._imageData = this.context.getImageData(0, 0, ea, fa).data)
                    }
                },
                getIdFromXY: function(a, b) {
                    var c = this._imageData;
                    if (c) {
                        var d = 4 * ((0 | b) * ea + (0 | a)),
                            e = c[d] | c[d + 1] << 8 | c[d + 2] << 16;
                        return this._idMapping[e]
                    }
                },
                idToColor: function(a) {
                    var b = this._idMapping.indexOf(a); - 1 === b && (this._idMapping.push(a), b = this._idMapping.length - 1);
                    var c = 255 & b,
                        d = b >> 8 & 255,
                        e = b >> 16 & 255;
                    return "rgb(" + [c, d, e].join(",") + ")"
                }
            },
            Da = {
                container: document.createElement("DIV"),
                items: [],
                init: function() {
                    this.container.style.pointerEvents = "none",
                        this.container.style.position = "absolute",
                        this.container.style.left = 0,
                        this.container.style.top = 0,
                        Ba.context = this.createContext(this.container),
                        Aa.context = this.createContext(this.container),
                        za.context = this.createContext(this.container),
                        Ca.context = this.createContext()
                },
                render: function(a) {
                    S(function() {
                        a || (Ba.render(), Aa.render(), Ca.render()),
                            za.render()
                    })
                },
                createContext: function(a) {
                    var b = document.createElement("CANVAS");
                    b.style.transform = "translate3d(0, 0, 0)",
                        b.style.imageRendering = "optimizeSpeed",
                        b.style.position = "absolute",
                        b.style.left = 0,
                        b.style.top = 0;
                    var c = b.getContext("2d");
                    return c.lineCap = "round",
                        c.lineJoin = "round",
                        c.lineWidth = 1,
                        c.imageSmoothingEnabled = !1,
                        this.items.push(b),
                    a && a.appendChild(b),
                        c
                },
                appendTo: function(a) {
                    a.appendChild(this.container)
                },
                remove: function() {
                    this.container.parentNode.removeChild(this.container)
                },
                setSize: function(a, b) {
                    for (var c = 0,
                             d = this.items.length; d > c; c++) this.items[c].width = a,
                        this.items[c].height = b
                },
                setPosition: function(a, b) {
                    this.container.style.left = a + "px",
                        this.container.style.top = b + "px"
                }
            };
        Da.init();
        var Ea = function(a) {
                this.offset = {
                    x: 0,
                    y: 0
                },
                a && a.addLayer(this)
            },
            Fa = Ea.prototype = L.Layer ? new L.Layer: {};
        Fa.addTo = function(a) {
            return a.addLayer(this),
                this
        },
            Fa.onAdd = function(a) {
                this.map = a,
                    Da.appendTo(a._panes.overlayPane);
                var b = this.getOffset(),
                    c = a.getPixelOrigin();
                o({
                    width: a._size.x,
                    height: a._size.y
                }),
                    m({
                        x: c.x - b.x,
                        y: c.y - b.y
                    }),
                    p(a._zoom),
                    Da.setPosition( - b.x, -b.y),
                    a.on({
                            move: this.onMove,
                            moveend: this.onMoveEnd,
                            zoomstart: this.onZoomStart,
                            zoomend: this.onZoomEnd,
                            resize: this.onResize,
                            viewreset: this.onViewReset,
                            click: this.onClick
                        },
                        this),
                a.options.zoomAnimation && a.on("zoomanim", this.onZoom, this),
                a.attributionControl && a.attributionControl.addAttribution(X),
                    va.update()
            },
            Fa.onRemove = function() {
                var a = this.map;
                a.attributionControl && a.attributionControl.removeAttribution(X),
                    a.off({
                            move: this.onMove,
                            moveend: this.onMoveEnd,
                            zoomstart: this.onZoomStart,
                            zoomend: this.onZoomEnd,
                            resize: this.onResize,
                            viewreset: this.onViewReset,
                            click: this.onClick
                        },
                        this),
                a.options.zoomAnimation && a.off("zoomanim", this.onZoom, this),
                    Da.remove(),
                    a = null
            },
            Fa.onMove = function(a) {
                var b = this.getOffset();
                n({
                    x: this.offset.x - b.x,
                    y: this.offset.y - b.y
                })
            },
            Fa.onMoveEnd = function(a) {
                if (this.noMoveEnd) return void(this.noMoveEnd = !1);
                var b = this.map,
                    c = this.getOffset(),
                    d = b.getPixelOrigin();
                this.offset = c,
                    Da.setPosition( - c.x, -c.y),
                    n({
                        x: 0,
                        y: 0
                    }),
                    o({
                        width: b._size.x,
                        height: b._size.y
                    }),
                    m({
                        x: d.x - c.x,
                        y: d.y - c.y
                    }),
                    q(a)
            },
            Fa.onZoomStart = function(a) {
                r(a)
            },
            Fa.onZoom = function(a) {},
            Fa.onZoomEnd = function(a) {
                var b = this.map,
                    c = this.getOffset(),
                    d = b.getPixelOrigin();
                m({
                    x: d.x - c.x,
                    y: d.y - c.y
                }),
                    s({
                        zoom: b._zoom
                    }),
                    this.noMoveEnd = !0
            },
            Fa.onResize = function() {},
            Fa.onViewReset = function() {
                var a = this.getOffset();
                this.offset = a,
                    Da.setPosition( - a.x, -a.y),
                    n({
                        x: 0,
                        y: 0
                    })
            },
            Fa.onClick = function(a) {
                var b = Ca.getIdFromXY(a.containerPoint.x, a.containerPoint.y);
                b && Ha({
                    feature: b,
                    lat: a.latlng.lat,
                    lon: a.latlng.lng
                })
            },
            Fa.getOffset = function() {
                return L.DomUtil.getPosition(this.map._mapPane)
            },
            Fa.style = function(a) {
                a = a || {};
                var b;
                return (b = a.color || a.wallColor) && (ka = T.parse(b), na = "" + ka.alpha(ra), la = ka.lightness(.8), oa = "" + la.alpha(ra), ma = ka.lightness(1.2), pa = "" + ma.alpha(ra)),
                a.roofColor && (ma = T.parse(a.roofColor), pa = "" + ma.alpha(ra)),
                void 0 !== a.shadows && (Ba.enabled = !!a.shadows),
                    Da.render(),
                    this
            },
            Fa.date = function(a) {
                return Ba.date = a,
                    Ba.render(),
                    this
            },
            Fa.load = function(a) {
                return va.load(a),
                    this
            },
            Fa.set = function(a) {
                return va.set(a),
                    this
            };
        var Ga = function() {};
        Fa.each = function(a) {
            return Ga = function(b) {
                return a(b)
            },
                this
        };
        var Ha = function() {};
        Fa.click = function(a) {
            return Ha = function(b) {
                return a(b)
            },
                this
        },
            Ea.VERSION = W,
            Ea.ATTRIBUTION = X,
            a.OSMBuildings = Ea
    } (this),
    function() {
        function a(a, b) {
            var c = a[0] - b[0],
                d = a[1] - b[1];
            return c * c + d * d
        }
        function b(b, c) {
            for (var d, e = b[0], f = [e], g = 1, h = b.length; h > g; g++) d = b[g],
            a(d, e) > c && (f.push(d), e = d);
            return e !== d && f.push(d),
                f.length < 3 ? b: f
        }
        function c(a) {
            for (var c, d, e = 0,
                     f = 0,
                     g = b(a, 10), h = 0, i = g.length - 1; i > h; h++) e = ba.len(ba.sub(g[h + 1], g[h])),
            e > f && (f = e, d = [g[h], g[h + 1]]);
            return c = ba.sub(d[1], d[0]),
                [c[0] / f, c[1] / f]
        }
        function d(a, b) {
            for (var c, d, f = [], g = 0, h = a.length - 1; h > g; g++) c = [a[g], a[g + 1]],
                d = e(c, b),
            void 0 !== d && f.push({
                index: g,
                segment: c
            });
            return f
        }
        function e(a, b) {
            if (! (ba.equals(a[0], b[0]) || ba.equals(a[0], b[1]) || ba.equals(a[1], b[0]) || ba.equals(a[1], b[1]))) {
                var c = ba.sub(a[1], a[0]),
                    d = ba.sub(b[1], b[0]),
                    e = ba.dot(c, d);
                if (! (Math.abs(e) < 1e-10)) {
                    var f = ba.sub(b[0], a[0]),
                        g = ba.dot(f, d) / e;
                    if (! (0 > g || g > 1)) {
                        var h = ba.dot(f, c) / e;
                        if (! (0 > h || h > 1)) return ba.add(a[0], ba.scale(c, g))
                    }
                }
            }
        }
        function f(a, b) {
            var c = b[0],
                d = b[1];
            if (c[0] !== d[0] || c[1] !== d[1]) {
                var e = (d[1] - c[1]) / (d[0] - c[0]),
                    f = c[1] - e * c[0];
                if (0 === e) return Math.abs(f - a[1]);
                if (e === 1 / 0) return Math.abs(c[0] - a[0]);
                var g = -1 / e,
                    h = a[1] - g * a[0],
                    i = (h - f) / (e - g),
                    j = e * i + f,
                    k = a[0] - i,
                    l = a[1] - j;
                return Math.sqrt(k * k + l * l)
            }
        }
        function g(a) {
            return ba.add(a[0], ba.scale(ba.sub(a[1], a[0]), .5))
        }
        function h(a, b, e, h, i, j, k) {
            h = 0;
            var l, m, n, o = e[0];
            void 0 !== b.roofRidgeDirection ? (m = parseFloat(b.roofRidgeDirection), isNaN(m) || (n = 90 + m * Math.PI / 180, l = [Math.sin(n), Math.cos(n)])) : void 0 !== b.roofDirection ? (m = parseFloat(b.roofDirection), isNaN(m) || (n = m * Math.PI / 180, l = [Math.sin(n), Math.cos(n)])) : (l = c(o), b.roofOrientation && "across" === b.roofOrientation && (l = [ - l[1], l[0]])),
                l = ba.scale(l, 1e3);
            var p = d(o, [ba.sub(i.center, l), ba.add(i.center, l)]);
            if (p.length < 2) throw new Error("can't handle ridged roof geometry");
            var q = p[0],
                r = p[1];
            if (q.index > r.index) {
                var s = q;
                q = r,
                    r = s
            }
            if (q.center = g(q.segment), r.center = g(r.segment), 0 === h) {
                var t, u = [q.center, r.center],
                    v = 0,
                    w = [];
                for (t = 0; t < o.length; t++) w[t] = f(o[t], u),
                    v = Math.max(v, w[t]);
                for (t = 0; t < o.length; t++) o[t][2] = (1 - w[t] / v) * i.roofHeight;
                q.center[2] = i.roofHeight,
                    r.center[2] = i.roofHeight;
                var x = [q.center];
                x = x.concat(o.slice(q.index + 1, r.index + 1)),
                    x.push(r.center, q.center),
                    ea.polygon(a, [x], i.roofZ, k);
                var y = [r.center];
                for (y = y.concat(o.slice(r.index + 1, o.length - 1)), y = y.concat(o.slice(0, q.index + 1)), y.push(q.center, r.center), ea.polygon(a, [y], i.roofZ, k), o.splice(q.index + 1, 0, q.center), o.splice(r.index + 2, 0, r.center), t = 0; t < o.length - 1; t++) ea.quad(a, [o[t][0], o[t][1], i.roofZ + o[t][2]], [o[t][0], o[t][1], i.roofZ], [o[t + 1][0], o[t + 1][1], i.roofZ], [o[t + 1][0], o[t + 1][1], i.roofZ + o[t + 1][2]], j)
            }
        }
        function i(a, b, e, g, h, i) {
            var j, k, l, m, n, o = e[0];
            void 0 !== b.roofSlopeDirection ? (m = parseFloat(b.roofSlopeDirection), isNaN(m) || (n = m * Math.PI / 180, l = [Math.sin(n), Math.cos(n)])) : void 0 !== b.roofDirection ? (m = parseFloat(b.roofDirection), isNaN(m) || (n = m * Math.PI / 180, l = [Math.sin(n), Math.cos(n)])) : (l = c(o), l = [ - l[1], l[0]], b.roofOrientation && "across" === b.roofOrientation && (l = [ - l[1], l[0]])),
                l = ba.scale(l, 1e3);
            var p, q = d(o, [ba.sub(g.center, l), ba.add(g.center, l)]),
                r = 0,
                s = 0;
            for (j = 0, k = q.length; k > j; j++) r = f(g.center, q[j].segment),
            r > s && (p = q[j].segment, s = r);
            if (void 0 !== p) {
                s = 0;
                var t = [];
                for (j = 0; j < o.length; j++) t[j] = f(o[j], p),
                    s = Math.max(s, t[j]);
                for (j = 0; j < o.length; j++) o[j][2] = (1 - t[j] / s) * g.roofHeight;
                for (ea.polygon(a, [o], g.roofZ, i), j = 0; j < o.length - 1; j++) ea.quad(a, [o[j][0], o[j][1], g.roofZ + o[j][2]], [o[j][0], o[j][1], g.roofZ], [o[j + 1][0], o[j + 1][1], g.roofZ], [o[j + 1][0], o[j + 1][1], g.roofZ + o[j + 1][2]], h)
            }
        }
        function j(a, b) {
            return [a[0] + b[0], a[1] + b[1]]
        }
        function k(a, b) {
            return [a[0] * b, a[1] * b]
        }
        function l(a, b) {
            return {
                x: a.clientX - b.x,
                y: a.clientY - b.y
            }
        }
        function m(a) {
            if (a.getBoundingClientRect) {
                var b = a.getBoundingClientRect();
                return {
                    x: b.left,
                    y: b.top
                }
            }
            for (var c = {
                x: 0,
                y: 0
            }; 1 === a.nodeType;) c.x += a.offsetLeft,
                c.y += a.offsetTop,
                a = a.parentNode;
            return c
        }
        function n(a) {
            a.preventDefault && a.preventDefault(),
                a.returnValue = !1
        }
        function o(a, b, c) {
            a.addEventListener(b, c, !1)
        }
        function p(a, b) {
            return a.replace(/\{(\w+)\}/g,
                function(a, c) {
                    return b[c] || a
                })
        }
        function q(a, b) {
            if (!a) throw b
        }
        function r(a, b) {
            var c = [];
            for (var d in a) c.push([a[d][0], a[d][1], b]);
            return c
        }
        function s(a, b, c) {
            return Math.min(c, Math.max(a, b))
        }
        function t(a, b) {
            var c = a[0] - b[0],
                d = a[1] - b[1];
            return c * c + d * d
        }
        function q(a, b) {
            if (!a) throw b
        }
        function u(a, b, c) {
            var d = [a[0] < b[0] ? 1 : -1, a[1] < b[1] ? 1 : -1],
                e = c[0] + (a[0] < b[0] ? 1 : 0),
                f = c[1] + (a[1] < b[1] ? 1 : 0),
                g = (e - a[0]) / (b[0] - a[0]),
                h = (f - a[1]) / (b[1] - a[1]);
            return (0 >= g || g > 1) && (0 >= h || h > 1) ? [void 0, void 0] : 0 >= g || g > 1 ? [c[0], c[1] + d[1]] : 0 >= h || h > 1 ? [c[0] + d[0], c[1]] : h > g ? [c[0] + d[0], c[1]] : [c[0], c[1] + d[1]]
        }
        function v(a, b, c) {
            var d = [a, b, c];
            if (d.sort(function(a, b) {
                    return a[1] < b[1]
                }), a = d[0], b = d[1], c = d[2], a[1] == b[1]) return w(a, b, c);
            if (b[1] == c[1]) return w(b, c, a);
            var e = (b[1] - a[1]) / (c[1] - a[1]),
                f = [a[0] + e * (c[0] - a[0]), b[1]];
            return w(f, b, a).concat(w(f, b, c))
        }
        function w(a, b, c) {
            var d = [];
            if (q(a[1] === b[1], "not a flat triangle"), q(c[1] !== a[1], "not a triangle"), q(a[0] !== b[0], "not a triangle"), a[0] > b[0]) {
                var e = a;
                a = b,
                    b = e
            }
            var f = [c[0] << 0, c[1] << 0],
                g = f.slice(0);
            d.push(f.slice(0));
            for (var h, i, j = c[1] < a[1] ? 1 : -1, k = f[1], l = (a[1] << 0) + j, m = k; l * j > m * j; m += j) {
                do d.push(f.slice(0)),
                    h = f,
                    f = u(c, a, f);
                while (f[1] * j <= m * j);
                f = h;
                do d.push(g.slice(0)),
                    i = g,
                    g = u(c, b, g);
                while (g[1] * j <= m * j);
                g = i;
                for (var n = f[0]; n <= g[0]; n++) d.push([n, m])
            }
            return d
        }
        function x(a) {
            q(4 == a.length, "Error: Quadrilateral with more or less than four vertices");
            var b = v(a[0], a[1], a[2]),
                c = v(a[0], a[2], a[3]);
            return b.concat(c)
        }
        function y(a, b, c) {
            var d = S(a, b),
                e = S(b, c);
            return X([d[1] * e[2] - d[2] * e[1], d[2] * e[0] - d[0] * e[2], d[0] * e[1] - d[1] * e[0]])
        }
        function z(a, b, c) {
            var d = aa.Matrix.invert(a),
                e = C( - 1, -1, d),
                f = C(1, -1, d),
                g = C(1, 1, d),
                h = C( - 1, 1, d);
            if (e && f) {
                var i, l, m, n, o;
                return h && g || (m = C( - 1, -.9, d), i = Q(P(m, e)), o = O(i, c), h = j(e, k(i, b / o)), n = C(1, -.9, d), l = Q(P(n, f)), o = O(l, c), g = j(f, k(l, b / o))),
                O(c, P(h, e)) > b && (i = Q(P(h, e)), o = O(i, c), h = j(e, k(i, b / o))),
                O(c, P(g, f)) > b && (l = Q(P(g, f)), o = O(l, c), g = j(f, k(l, b / o))),
                    [e, f, g, h]
            }
        }
        function A(a, b, c, d, e) {
            for (var f = B(b.data, a[0]), g = f[0], h = f[0], i = f[1], j = f[1], k = 0; k < a.length; k++) {
                var l = B(b.data, a[k]);
                g = Math.min(g, l[0]),
                    h = Math.max(h, l[0]),
                    i = Math.max(i, l[1]),
                    j = Math.min(j, l[1])
            }
            return new aa.Matrix.Ortho(g, h, i, j, c, d)
        }
        function B(a, b) {
            var c = b[0] * a[0] + b[1] * a[4] + b[2] * a[8] + a[12],
                d = b[0] * a[1] + b[1] * a[5] + b[2] * a[9] + a[13],
                e = b[0] * a[2] + b[1] * a[6] + b[2] * a[10] + a[14],
                f = b[0] * a[3] + b[1] * a[7] + b[2] * a[11] + a[15];
            return [c / f, d / f, e / f]
        }
        function C(a, b, c) {
            var d = B(c, [a, b, 0]),
                e = B(c, [a, b, 1]),
                f = S(e, d);
            if (! (f[2] >= 0)) {
                var g = -d[2] / f[2],
                    h = T(d, V(f, g));
                return [h[0], h[1]]
            }
        }
        function D(a, b, c, d) {
            var e = wa * Math.cos(ha.position.latitude / 180 * Math.PI),
                f = L(a, c),
                g = M(b, c),
                h = new aa.Matrix;
            h.translate((f - ha.position.longitude) * e, -(g - ha.position.latitude) * wa, 0);
            var i = G(ha.position.latitude, c),
                j = aa.Matrix.multiply(h, d),
                k = B(j, [0, 0, 0]),
                l = B(j, [i, 0, 0]),
                m = B(j, [0, i, 0]),
                n = B(j, [i, i, 0]),
                o = [k, l, m, n];
            for (var p in o) o[p][0] = (o[p][0] + 1) / 2 * ha.width,
                o[p][1] = (o[p][1] + 1) / 2 * ha.height;
            return F([k, l, n, m])
        }
        function E(a, b, c) {
            var d = N(P(a, b)),
                e = N(P(a, c)),
                f = N(P(b, c)),
                g = .5 * (d + e + f);
            return Math.sqrt(g * (g - d) * (g - e) * (g - f))
        }
        function F(a) {
            return E(a[0], a[1], a[2]) + E(a[0], a[2], a[3])
        }
        function G(a, b) {
            return va * Math.cos(a / 180 * Math.PI) / Math.pow(2, b)
        }
        function H(a) {
            var b = wa * Math.cos(ha.position.latitude / 180 * Math.PI);
            return {
                longitude: ha.position.longitude + a[0] / b,
                latitude: ha.position.latitude - a[1] / wa
            }
        }
        function I(a, b) {
            var c = H(a);
            return [J(c.longitude, b), K(c.latitude, b)]
        }
        function J(a, b) {
            return (a + 180) / 360 * Math.pow(2, b)
        }
        function K(a, b) {
            return (1 - Math.log(Math.tan(a * Math.PI / 180) + 1 / Math.cos(a * Math.PI / 180)) / Math.PI) / 2 * Math.pow(2, b)
        }
        function L(a, b) {
            return a / Math.pow(2, b) * 360 - 180
        }
        function M(a, b) {
            var c = Math.PI - 2 * Math.PI * a / Math.pow(2, b);
            return 180 / Math.PI * Math.atan(.5 * (Math.exp(c) - Math.exp( - c)))
        }
        function N(a) {
            return Math.sqrt(a[0] * a[0] + a[1] * a[1])
        }
        function O(a, b) {
            return a[0] * b[0] + a[1] * b[1]
        }
        function P(a, b) {
            return [a[0] - b[0], a[1] - b[1]]
        }
        function j(a, b) {
            return [a[0] + b[0], a[1] + b[1]]
        }
        function k(a, b) {
            return [a[0] * b, a[1] * b]
        }
        function Q(a) {
            var b = N(a);
            return [a[0] / b, a[1] / b]
        }
        function R(a, b) {
            return a[0] * b[0] + a[1] * b[1] + a[2] * b[2]
        }
        function S(a, b) {
            return [a[0] - b[0], a[1] - b[1], a[2] - b[2]]
        }
        function T(a, b) {
            return [a[0] + b[0], a[1] + b[1], a[2] + b[2]]
        }
        function U(a, b) {
            return [a[0] + b, a[1] + b, a[2] + b]
        }
        function V(a, b) {
            return [a[0] * b, a[1] * b, a[2] * b]
        }
        function W(a) {
            return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2])
        }
        function X(a) {
            var b = W(a);
            return [a[0] / b, a[1] / b, a[2] / b]
        }
        function Y(a, b) {
            return a[0] === b[0] && a[1] === b[1] && a[2] === b[2]
        }
        var Z = function() {
            function a(a, b, c) {
                return 0 > c && (c += 1),
                c > 1 && (c -= 1),
                    1 / 6 > c ? a + 6 * (b - a) * c: .5 > c ? b: 2 / 3 > c ? a + (b - a) * (2 / 3 - c) * 6 : a
            }
            function b(a, b) {
                return Math.min(b, Math.max(0, a || 0))
            }
            var c = {
                    aliceblue: "#f0f8ff",
                    antiquewhite: "#faebd7",
                    aqua: "#00ffff",
                    aquamarine: "#7fffd4",
                    azure: "#f0ffff",
                    beige: "#f5f5dc",
                    bisque: "#ffe4c4",
                    black: "#000000",
                    blanchedalmond: "#ffebcd",
                    blue: "#0000ff",
                    blueviolet: "#8a2be2",
                    brown: "#a52a2a",
                    burlywood: "#deb887",
                    cadetblue: "#5f9ea0",
                    chartreuse: "#7fff00",
                    chocolate: "#d2691e",
                    coral: "#ff7f50",
                    cornflowerblue: "#6495ed",
                    cornsilk: "#fff8dc",
                    crimson: "#dc143c",
                    cyan: "#00ffff",
                    darkblue: "#00008b",
                    darkcyan: "#008b8b",
                    darkgoldenrod: "#b8860b",
                    darkgray: "#a9a9a9",
                    darkgrey: "#a9a9a9",
                    darkgreen: "#006400",
                    darkkhaki: "#bdb76b",
                    darkmagenta: "#8b008b",
                    darkolivegreen: "#556b2f",
                    darkorange: "#ff8c00",
                    darkorchid: "#9932cc",
                    darkred: "#8b0000",
                    darksalmon: "#e9967a",
                    darkseagreen: "#8fbc8f",
                    darkslateblue: "#483d8b",
                    darkslategray: "#2f4f4f",
                    darkslategrey: "#2f4f4f",
                    darkturquoise: "#00ced1",
                    darkviolet: "#9400d3",
                    deeppink: "#ff1493",
                    deepskyblue: "#00bfff",
                    dimgray: "#696969",
                    dimgrey: "#696969",
                    dodgerblue: "#1e90ff",
                    firebrick: "#b22222",
                    floralwhite: "#fffaf0",
                    forestgreen: "#228b22",
                    fuchsia: "#ff00ff",
                    gainsboro: "#dcdcdc",
                    ghostwhite: "#f8f8ff",
                    gold: "#ffd700",
                    goldenrod: "#daa520",
                    gray: "#808080",
                    grey: "#808080",
                    green: "#008000",
                    greenyellow: "#adff2f",
                    honeydew: "#f0fff0",
                    hotpink: "#ff69b4",
                    indianred: "#cd5c5c",
                    indigo: "#4b0082",
                    ivory: "#fffff0",
                    khaki: "#f0e68c",
                    lavender: "#e6e6fa",
                    lavenderblush: "#fff0f5",
                    lawngreen: "#7cfc00",
                    lemonchiffon: "#fffacd",
                    lightblue: "#add8e6",
                    lightcoral: "#f08080",
                    lightcyan: "#e0ffff",
                    lightgoldenrodyellow: "#fafad2",
                    lightgray: "#d3d3d3",
                    lightgrey: "#d3d3d3",
                    lightgreen: "#90ee90",
                    lightpink: "#ffb6c1",
                    lightsalmon: "#ffa07a",
                    lightseagreen: "#20b2aa",
                    lightskyblue: "#87cefa",
                    lightslategray: "#778899",
                    lightslategrey: "#778899",
                    lightsteelblue: "#b0c4de",
                    lightyellow: "#ffffe0",
                    lime: "#00ff00",
                    limegreen: "#32cd32",
                    linen: "#faf0e6",
                    magenta: "#ff00ff",
                    maroon: "#800000",
                    mediumaquamarine: "#66cdaa",
                    mediumblue: "#0000cd",
                    mediumorchid: "#ba55d3",
                    mediumpurple: "#9370db",
                    mediumseagreen: "#3cb371",
                    mediumslateblue: "#7b68ee",
                    mediumspringgreen: "#00fa9a",
                    mediumturquoise: "#48d1cc",
                    mediumvioletred: "#c71585",
                    midnightblue: "#191970",
                    mintcream: "#f5fffa",
                    mistyrose: "#ffe4e1",
                    moccasin: "#ffe4b5",
                    navajowhite: "#ffdead",
                    navy: "#000080",
                    oldlace: "#fdf5e6",
                    olive: "#808000",
                    olivedrab: "#6b8e23",
                    orange: "#ffa500",
                    orangered: "#ff4500",
                    orchid: "#da70d6",
                    palegoldenrod: "#eee8aa",
                    palegreen: "#98fb98",
                    paleturquoise: "#afeeee",
                    palevioletred: "#db7093",
                    papayawhip: "#ffefd5",
                    peachpuff: "#ffdab9",
                    peru: "#cd853f",
                    pink: "#ffc0cb",
                    plum: "#dda0dd",
                    powderblue: "#b0e0e6",
                    purple: "#800080",
                    rebeccapurple: "#663399",
                    red: "#ff0000",
                    rosybrown: "#bc8f8f",
                    royalblue: "#4169e1",
                    saddlebrown: "#8b4513",
                    salmon: "#fa8072",
                    sandybrown: "#f4a460",
                    seagreen: "#2e8b57",
                    seashell: "#fff5ee",
                    sienna: "#a0522d",
                    silver: "#c0c0c0",
                    skyblue: "#87ceeb",
                    slateblue: "#6a5acd",
                    slategray: "#708090",
                    slategrey: "#708090",
                    snow: "#fffafa",
                    springgreen: "#00ff7f",
                    steelblue: "#4682b4",
                    tan: "#d2b48c",
                    teal: "#008080",
                    thistle: "#d8bfd8",
                    tomato: "#ff6347",
                    turquoise: "#40e0d0",
                    violet: "#ee82ee",
                    wheat: "#f5deb3",
                    white: "#ffffff",
                    whitesmoke: "#f5f5f5",
                    yellow: "#ffff00",
                    yellowgreen: "#9acd32"
                },
                d = function(a) {
                    if (a = a || "", "object" == typeof a) {
                        var d = a;
                        this.r = b(d.r, 1),
                            this.g = b(d.g, 1),
                            this.b = b(d.b, 1),
                            this.a = void 0 !== d.a ? b(d.a, 1) : 1
                    } else if ("string" == typeof a) {
                        a = a.toLowerCase(),
                            a = c[a] || a;
                        var e; (e = a.match(/^#?(\w{2})(\w{2})(\w{2})$/)) ? (this.r = parseInt(e[1], 16) / 255, this.g = parseInt(e[2], 16) / 255, this.b = parseInt(e[3], 16) / 255, this.a = 1) : (e = a.match(/rgba?\((\d+)\D+(\d+)\D+(\d+)(\D+([\d.]+))?\)/)) && (this.r = parseInt(e[1], 10) / 255, this.g = parseInt(e[2], 10) / 255, this.b = parseInt(e[3], 10) / 255, this.a = e[4] ? parseFloat(e[5]) : 1)
                    }
                };
            return d.prototype = {
                toHSL: function() {
                    if (void 0 !== this.r && void 0 !== this.g && void 0 !== this.b) {
                        var a, b, c = Math.max(this.r, this.g, this.b),
                            d = Math.min(this.r, this.g, this.b),
                            e = (c + d) / 2,
                            f = c - d;
                        if (f) {
                            switch (b = e > .5 ? f / (2 - c - d) : f / (c + d), c) {
                                case this.r:
                                    a = (this.g - this.b) / f + (this.g < this.b ? 6 : 0);
                                    break;
                                case this.g:
                                    a = (this.b - this.r) / f + 2;
                                    break;
                                case this.b:
                                    a = (this.r - this.g) / f + 4
                            }
                            a *= 60
                        } else a = b = 0;
                        return {
                            h: a,
                            s: b,
                            l: e
                        }
                    }
                },
                fromHSL: function(b) {
                    if (0 === b.s) this.r = b.l,
                        this.g = b.l,
                        this.b = b.l;
                    else {
                        var c = b.l < .5 ? b.l * (1 + b.s) : b.l + b.s - b.l * b.s,
                            d = 2 * b.l - c;
                        b.h /= 360,
                            this.r = a(d, c, b.h + 1 / 3),
                            this.g = a(d, c, b.h),
                            this.b = a(d, c, b.h - 1 / 3)
                    }
                    return this
                },
                toString: function() {
                    return void 0 === this.r || void 0 === this.g || void 0 === this.b ? "": 1 === this.a ? "#" + ((1 << 24) + (Math.round(255 * this.r) << 16) + (Math.round(255 * this.g) << 8) + Math.round(255 * this.b)).toString(16).slice(1, 7) : "rgba(" + [Math.round(255 * this.r), Math.round(255 * this.g), Math.round(255 * this.b), this.a.toFixed(2)].join(",") + ")"
                },
                toArray: function() {
                    return void 0 !== this.r && void 0 !== this.g && void 0 !== this.b ? [this.r, this.g, this.b] : void 0
                },
                hue: function(a) {
                    var b = this.toHSL();
                    return b.h *= a,
                        this.fromHSL(b),
                        this
                },
                saturation: function(a) {
                    var b = this.toHSL();
                    return b.s *= a,
                        this.fromHSL(b),
                        this
                },
                lightness: function(a) {
                    var b = this.toHSL();
                    return b.l *= a,
                        this.fromHSL(b),
                        this
                },
                alpha: function(a) {
                    return this.a *= a,
                        this
                }
            },
                d
        } ();
        "object" == typeof module && (module.exports = Z);
        var $ = function() {
                "use strict";
                function a(a) {
                    return a.valueOf() / r - .5 + s
                }
                function b(b) {
                    return a(b) - t
                }
                function c(a, b) {
                    return p(l(a) * m(u) - n(b) * l(u), m(a))
                }
                function d(a, b) {
                    return o(l(b) * m(u) + m(b) * l(u) * l(a))
                }
                function e(a, b, c) {
                    return p(l(a), m(a) * l(b) - n(c) * m(b))
                }
                function f(a, b, c) {
                    return o(l(b) * l(c) + m(b) * m(c) * m(a))
                }
                function g(a, b) {
                    return q * (280.16 + 360.9856235 * a) - b
                }
                function h(a) {
                    return q * (357.5291 + .98560028 * a)
                }
                function i(a) {
                    var b = q * (1.9148 * l(a) + .02 * l(2 * a) + 3e-4 * l(3 * a)),
                        c = 102.9372 * q;
                    return a + b + c + k
                }
                function j(a) {
                    var b = h(a),
                        e = i(b);
                    return {
                        dec: d(e, 0),
                        ra: c(e, 0)
                    }
                }
                var k = Math.PI,
                    l = Math.sin,
                    m = Math.cos,
                    n = Math.tan,
                    o = Math.asin,
                    p = Math.atan2,
                    q = k / 180,
                    r = 864e5,
                    s = 2440588,
                    t = 2451545,
                    u = 23.4397 * q;
                return function(a, c, d) {
                    var h = q * -d,
                        i = q * c,
                        k = b(a),
                        l = j(k),
                        m = g(k, h) - l.ra;
                    return {
                        azimuth: e(m, i, l.dec),
                        altitude: f(m, i, l.dec)
                    }
                }
            } (),
            _ = {
                picking: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\n#define halfPi 1.57079632679\nattribute vec4 aPosition;\nattribute vec3 aId;\nattribute vec4 aFilter;\nuniform mat4 uModelMatrix;\nuniform mat4 uMatrix;\nuniform float uFogRadius;\nuniform float uTime;\nvarying vec4 vColor;\nvoid main() {\n  float t = clamp((uTime-aFilter.r) / (aFilter.g-aFilter.r), 0.0, 1.0);\n  float f = aFilter.b + (aFilter.a-aFilter.b) * t;\n  if (f == 0.0) {\n    gl_Position = vec4(0.0, 0.0, 0.0, 0.0);\n    vColor = vec4(0.0, 0.0, 0.0, 0.0);\n  } else {\n    vec4 pos = vec4(aPosition.x, aPosition.y, aPosition.z*f, aPosition.w);\n    gl_Position = uMatrix * pos;\n    vec4 mPosition = vec4(uModelMatrix * pos);\n    float distance = length(mPosition);\n    if (distance > uFogRadius) {\n      vColor = vec4(0.0, 0.0, 0.0, 0.0);\n    } else {\n      vColor = vec4(aId, 1.0);\n    }\n  }\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nvarying vec4 vColor;\nvoid main() {\n  gl_FragColor = vColor;\n}\n"
                },
                buildings: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\n#define halfPi 1.57079632679\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nattribute vec3 aNormal;\nattribute vec3 aColor;\nattribute vec4 aFilter;\nattribute vec3 aId;\nuniform mat4 uModelMatrix;\nuniform mat4 uMatrix;\nuniform mat3 uNormalTransform;\nuniform vec3 uLightDirection;\nuniform vec3 uLightColor;\nuniform vec3 uHighlightColor;\nuniform vec3 uHighlightId;\nuniform vec2 uViewDirOnMap;\nuniform vec2 uLowerEdgePoint;\nuniform float uTime;\nvarying vec3 vColor;\nvarying vec2 vTexCoord;\nvarying float verticalDistanceToLowerEdge;\nconst float gradientHeight = 90.0;\nconst float gradientStrength = 0.4;\nvoid main() {\n  float t = clamp((uTime-aFilter.r) / (aFilter.g-aFilter.r), 0.0, 1.0);\n  float f = aFilter.b + (aFilter.a-aFilter.b) * t;\n  if (f == 0.0) {\n    gl_Position = vec4(0.0, 0.0, 0.0, 0.0);\n    vColor = vec3(0.0, 0.0, 0.0);\n  } else {\n    vec4 pos = vec4(aPosition.x, aPosition.y, aPosition.z*f, aPosition.w);\n    gl_Position = uMatrix * pos;\n    //*** highlight object ******************************************************\n    vec3 color = aColor;\n    if (uHighlightId == aId) {\n      color = mix(aColor, uHighlightColor, 0.5);\n    }\n    //*** light intensity, defined by light direction on surface ****************\n    vec3 transformedNormal = aNormal * uNormalTransform;\n    float lightIntensity = max( dot(transformedNormal, uLightDirection), 0.0) / 1.5;\n    color = color + uLightColor * lightIntensity;\n    vTexCoord = aTexCoord;\n    //*** vertical shading ******************************************************\n    float verticalShading = clamp((gradientHeight-pos.z) / (gradientHeight/gradientStrength), 0.0, gradientStrength);\n    //***************************************************************************\n    vColor = color-verticalShading;\n    vec4 worldPos = uModelMatrix * pos;\n    vec2 dirFromLowerEdge = worldPos.xy / worldPos.w - uLowerEdgePoint;\n    verticalDistanceToLowerEdge = dot(dirFromLowerEdge, uViewDirOnMap);\n  }\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nvarying vec3 vColor;\nvarying vec2 vTexCoord;\nvarying float verticalDistanceToLowerEdge;\nuniform vec3 uFogColor;\nuniform float uFogDistance;\nuniform float uFogBlurDistance;\nuniform sampler2D uWallTexIndex;\nvoid main() {\n    \n  float fogIntensity = (verticalDistanceToLowerEdge - uFogDistance) / uFogBlurDistance;\n  fogIntensity = clamp(fogIntensity, 0.0, 1.0);\n  gl_FragColor = vec4( vColor* texture2D(uWallTexIndex, vTexCoord).rgb, 1.0-fogIntensity);\n}\n"
                },
                "buildings.shadows": {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\n#define halfPi 1.57079632679\nattribute vec4 aPosition;\nattribute vec3 aNormal;\nattribute vec3 aColor;\nattribute vec4 aFilter;\nattribute vec3 aId;\nattribute vec2 aTexCoord;\nuniform mat4 uModelMatrix;\nuniform mat4 uMatrix;\nuniform mat4 uSunMatrix;\nuniform mat3 uNormalTransform;\nuniform vec3 uHighlightColor;\nuniform vec3 uHighlightId;\nuniform vec2 uViewDirOnMap;\nuniform vec2 uLowerEdgePoint;\nuniform float uTime;\nvarying vec3 vColor;\nvarying vec2 vTexCoord;\nvarying vec3 vNormal;\nvarying vec3 vSunRelPosition;\nvarying float verticalDistanceToLowerEdge;\nfloat gradientHeight = 90.0;\nfloat gradientStrength = 0.4;\nvoid main() {\n  float t = clamp((uTime-aFilter.r) / (aFilter.g-aFilter.r), 0.0, 1.0);\n  float f = aFilter.b + (aFilter.a-aFilter.b) * t;\n  if (f == 0.0) {\n    gl_Position = vec4(0.0, 0.0, 0.0, 0.0);\n    vColor = vec3(0.0, 0.0, 0.0);\n  } else {\n    vec4 pos = vec4(aPosition.x, aPosition.y, aPosition.z*f, aPosition.w);\n    gl_Position = uMatrix * pos;\n    //*** highlight object ******************************************************\n    vec3 color = aColor;\n    if (uHighlightId == aId) {\n      color = mix(aColor, uHighlightColor, 0.5);\n    }\n    //*** light intensity, defined by light direction on surface ****************\n    vNormal = aNormal;\n    vTexCoord = aTexCoord;\n    //vec3 transformedNormal = aNormal * uNormalTransform;\n    //float lightIntensity = max( dot(aNormal, uLightDirection), 0.0) / 1.5;\n    //color = color + uLightColor * lightIntensity;\n    //*** vertical shading ******************************************************\n    float verticalShading = clamp((gradientHeight-pos.z) / (gradientHeight/gradientStrength), 0.0, gradientStrength);\n    //***************************************************************************\n    vColor = color-verticalShading;\n    vec4 worldPos = uModelMatrix * pos;\n    vec2 dirFromLowerEdge = worldPos.xy / worldPos.w - uLowerEdgePoint;\n    verticalDistanceToLowerEdge = dot(dirFromLowerEdge, uViewDirOnMap);\n    \n    // *** shadow mapping ********\n    vec4 sunRelPosition = uSunMatrix * pos;\n    vSunRelPosition = (sunRelPosition.xyz / sunRelPosition.w + 1.0) / 2.0;\n  }\n}\n",
                    fragment: "\n#ifdef GL_FRAGMENT_PRECISION_HIGH\n  precision highp float;\n#else\n  precision mediump float;\n#endif\nvarying vec2 vTexCoord;\nvarying vec3 vColor;\nvarying vec3 vNormal;\nvarying vec3 vSunRelPosition;\nvarying float verticalDistanceToLowerEdge;\nuniform vec3 uFogColor;\nuniform vec2 uShadowTexDimensions;\nuniform sampler2D uShadowTexIndex;\nuniform sampler2D uWallTexIndex;\nuniform float uFogDistance;\nuniform float uFogBlurDistance;\nuniform float uShadowStrength;\nuniform vec3 uLightDirection;\nuniform vec3 uLightColor;\nfloat isSeenBySun(const vec2 sunViewNDC, const float depth, const float bias) {\n  if ( clamp( sunViewNDC, 0.0, 1.0) != sunViewNDC)  //not inside sun's viewport\n    return 1.0;\n  \n  float depthFromTexture = texture2D( uShadowTexIndex, sunViewNDC.xy).x;\n  \n  //compare depth values not in reciprocal but in linear depth\n  return step(1.0/depthFromTexture, 1.0/depth + bias);\n}\nvoid main() {\n  vec3 normal = normalize(vNormal); //may degenerate during per-pixel interpolation\n  float diffuse = dot(uLightDirection, normal);\n  diffuse = max(diffuse, 0.0);\n  // reduce shadow strength with:\n  // - lowering sun positions, to be consistent with the shadows on the basemap (there,\n  //   shadows are faded out with lowering sun positions to hide shadow artifacts caused\n  //   when sun direction and map surface are almost perpendicular\n  // - large angles between the sun direction and the surface normal, to hide shadow\n  //   artifacts that occur when surface normal and sun direction are almost perpendicular\n  float shadowStrength = pow( max( min(\n    dot(uLightDirection, vec3(0.0, 0.0, 1.0)),\n    dot(uLightDirection, normal)\n  ), 0.0), 1.5);\n  if (diffuse > 0.0 && shadowStrength > 0.0) {\n    // note: the diffuse term is also the cosine between the surface normal and the\n    // light direction\n    float bias = clamp(0.0007*tan(acos(diffuse)), 0.0, 0.01);\n    vec2 pos = fract( vSunRelPosition.xy * uShadowTexDimensions);\n    \n    vec2 tl = floor(vSunRelPosition.xy * uShadowTexDimensions) / uShadowTexDimensions;\n    float tlVal = isSeenBySun( tl,                           vSunRelPosition.z, bias);\n    float trVal = isSeenBySun( tl + vec2(1.0, 0.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    float blVal = isSeenBySun( tl + vec2(0.0, 1.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    float brVal = isSeenBySun( tl + vec2(1.0, 1.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    float occludedBySun = mix( \n                            mix(tlVal, trVal, pos.x), \n                            mix(blVal, brVal, pos.x),\n                            pos.y);\n    diffuse *= 1.0 - (shadowStrength * (1.0 - occludedBySun));\n  }\n  vec3 color = vColor* texture2D( uWallTexIndex, vTexCoord.st).rgb +\n              (diffuse/1.5) * uLightColor;\n  float fogIntensity = (verticalDistanceToLowerEdge - uFogDistance) / uFogBlurDistance;\n  fogIntensity = clamp(fogIntensity, 0.0, 1.0);\n  //gl_FragColor = vec4( mix(color, uFogColor, fogIntensity), 1.0);\n  gl_FragColor = vec4( color, 1.0-fogIntensity);\n}\n"
                },
                flatColor: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nuniform mat4 uMatrix;\nvoid main() {\n  gl_Position = uMatrix * aPosition;\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform vec4 uColor;\nvoid main() {\n  gl_FragColor = uColor;\n}\n"
                },
                skywall: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\n#define halfPi 1.57079632679\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nuniform mat4 uMatrix;\nuniform float uAbsoluteHeight;\nvarying vec2 vTexCoord;\nvarying float vRelativeHeight;\nconst float gradientHeight = 10.0;\nconst float gradientStrength = 1.0;\nvoid main() {\n  gl_Position = uMatrix * aPosition;\n  vTexCoord = aTexCoord;\n  vRelativeHeight = aPosition.z / uAbsoluteHeight;\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform sampler2D uTexIndex;\nuniform vec3 uFogColor;\nvarying vec2 vTexCoord;\nvarying float vRelativeHeight;\nvoid main() {\n  float blendFactor = min(100.0 * vRelativeHeight, 1.0);\n  vec4 texColor = texture2D(uTexIndex, vTexCoord);\n  gl_FragColor = mix( vec4(uFogColor, 1.0), texColor,  blendFactor);\n}\n"
                },
                basemap: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\n#define halfPi 1.57079632679\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nuniform mat4 uModelMatrix;\nuniform mat4 uViewMatrix;\nuniform mat4 uProjMatrix;\nuniform mat4 uMatrix;\nuniform vec2 uViewDirOnMap;\nuniform vec2 uLowerEdgePoint;\nvarying vec2 vTexCoord;\nvarying float verticalDistanceToLowerEdge;\nvoid main() {\n  gl_Position = uMatrix * aPosition;\n  vTexCoord = aTexCoord;\n  vec4 worldPos = uModelMatrix * aPosition;\n  vec2 dirFromLowerEdge = worldPos.xy / worldPos.w - uLowerEdgePoint;\n  verticalDistanceToLowerEdge = dot(dirFromLowerEdge, uViewDirOnMap);\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform sampler2D uTexIndex;\nuniform vec3 uFogColor;\nvarying vec2 vTexCoord;\nvarying float verticalDistanceToLowerEdge;\nuniform float uFogDistance;\nuniform float uFogBlurDistance;\nvoid main() {\n  float fogIntensity = (verticalDistanceToLowerEdge - uFogDistance) / uFogBlurDistance;\n  fogIntensity = clamp(fogIntensity, 0.0, 1.0);\n  gl_FragColor = vec4(texture2D(uTexIndex, vec2(vTexCoord.x, 1.0-vTexCoord.y)).rgb, 1.0-fogIntensity);\n}\n"
                },
                texture: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nuniform mat4 uMatrix;\nvarying vec2 vTexCoord;\nvoid main() {\n  gl_Position = uMatrix * aPosition;\n  vTexCoord = aTexCoord;\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform sampler2D uTexIndex;\nvarying vec2 vTexCoord;\nvoid main() {\n  gl_FragColor = vec4(texture2D(uTexIndex, vTexCoord.st).rgb, 1.0);\n}\n"
                },
                fogNormal: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nattribute vec4 aFilter;\nattribute vec3 aNormal;\nuniform mat4 uMatrix;\nuniform mat4 uModelMatrix;\nuniform mat3 uNormalMatrix;\nuniform vec2 uViewDirOnMap;\nuniform vec2 uLowerEdgePoint;\nvarying float verticalDistanceToLowerEdge;\nvarying vec3 vNormal;\nuniform float uTime;\nvoid main() {\n  float t = clamp((uTime-aFilter.r) / (aFilter.g-aFilter.r), 0.0, 1.0);\n  float f = aFilter.b + (aFilter.a-aFilter.b) * t;\n  if (f == 0.0) {\n    gl_Position = vec4(0.0, 0.0, 0.0, 0.0);\n    verticalDistanceToLowerEdge = 0.0;\n  } else {\n    vec4 pos = vec4(aPosition.x, aPosition.y, aPosition.z*f, aPosition.w);\n    gl_Position = uMatrix * pos;\n    vNormal = uNormalMatrix * aNormal;\n    vec4 worldPos = uModelMatrix * pos;\n    vec2 dirFromLowerEdge = worldPos.xy / worldPos.w - uLowerEdgePoint;\n    verticalDistanceToLowerEdge = dot(dirFromLowerEdge, uViewDirOnMap);\n  }\n}\n",
                    fragment: "\n#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform float uFogDistance;\nuniform float uFogBlurDistance;\nvarying float verticalDistanceToLowerEdge;\nvarying vec3 vNormal;\nvoid main() {\n  float fogIntensity = (verticalDistanceToLowerEdge - uFogDistance) / uFogBlurDistance;\n  gl_FragColor = vec4(normalize(vNormal) / 2.0 + 0.5, clamp(fogIntensity, 0.0, 1.0));\n}\n"
                },
                ambientFromDepth: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nvarying vec2 vTexCoord;\nvoid main() {\n  gl_Position = aPosition;\n  vTexCoord = aTexCoord;\n}\n",
                    fragment: "#ifdef GL_FRAGMENT_PRECISION_HIGH\n  // we need high precision for the depth values\n  precision highp float;\n#else\n  precision mediump float;\n#endif\nuniform sampler2D uDepthTexIndex;\nuniform sampler2D uFogTexIndex;\nuniform vec2 uInverseTexSize;   //in 1/pixels, e.g. 1/512 if the texture is 512px wide\nuniform float uEffectStrength;\nuniform float uNearPlane;\nuniform float uFarPlane;\nvarying vec2 vTexCoord;\n/* Retrieves the depth value 'offset' pixels away from 'pos' from texture 'uDepthTexIndex'. */\nfloat getDepth(vec2 pos, ivec2 offset)\n{\n  float z = texture2D(uDepthTexIndex, pos + float(offset) * uInverseTexSize).x;\n  return (2.0 * uNearPlane) / (uFarPlane + uNearPlane - z * (uFarPlane - uNearPlane)); // linearize depth\n}\n/* getOcclusionFactor() determines a heuristic factor (from [0..1]) for how \n * much the fragment at 'pos' with depth 'depthHere'is occluded by the \n * fragment that is (dx, dy) texels away from it.\n */\nfloat getOcclusionFactor(float depthHere, vec2 pos, ivec2 offset)\n{\n    float depthThere = getDepth(pos, offset);\n    /* if the fragment at (dx, dy) has no depth (i.e. there was nothing rendered there), \n     * then 'here' is not occluded (result 1.0) */\n    if (depthThere == 0.0)\n      return 1.0;\n    /* if the fragment at (dx, dy) is further away from the viewer than 'here', then\n     * 'here is not occluded' */\n    if (depthHere < depthThere )\n      return 1.0;\n      \n    float relDepthDiff = depthThere / depthHere;\n    float depthDiff = abs(depthThere - depthHere) * uFarPlane;\n    /* if the fragment at (dx, dy) is closer to the viewer than 'here', then it occludes\n     * 'here'. The occlusion is the higher the bigger the depth difference between the two\n     * locations is.\n     * However, if the depth difference is too high, we assume that 'there' lies in a\n     * completely different depth region of the scene than 'here' and thus cannot occlude\n     * 'here'. This last assumption gets rid of very dark artifacts around tall buildings.\n     */\n    return depthDiff < 50.0 ? mix(0.99, 1.0, 1.0 - clamp(depthDiff, 0.0, 1.0)) : 1.0;\n}\n/* This shader approximates the ambient occlusion in screen space (SSAO). \n * It is based on the assumption that a pixel will be occluded by neighboring \n * pixels iff. those have a depth value closer to the camera than the original\n * pixel itself (the function getOcclusionFactor() computes this occlusion \n * by a single other pixel).\n *\n * A naive approach would sample all pixels within a given distance. For an\n * interesting-looking effect, the sampling area needs to be at least 9 pixels \n * wide (-/+ 4), requiring 81 texture lookups per pixel for ambient occlusion.\n * This overburdens many GPUs.\n * To make the ambient occlusion computation faster, we do not consider all \n * texels in the sampling area, but only 16. This causes some sampling artifacts\n * that are later removed by blurring the ambient occlusion texture (this is \n * done in a separate shader).\n */\nvoid main() {\n  float depthHere = getDepth(vTexCoord, ivec2(0, 0));\n  float fogIntensity = texture2D(uFogTexIndex, vTexCoord).w;\n  if (depthHere == 0.0)\n  {\n	//there was nothing rendered 'here' --> it can't be occluded\n    gl_FragColor = vec4(1.0);\n    return;\n  }\n  float occlusionFactor = 1.0;\n  \n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-1,  0));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+1,  0));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2( 0, -1));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2( 0, +1));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-2, -2));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+2, +2));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+2, -2));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-2, +2));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-4,  0));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+4,  0));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2( 0, -4));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2( 0, +4));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-4, -4));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+4, +4));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(+4, -4));\n  occlusionFactor *= getOcclusionFactor(depthHere, vTexCoord, ivec2(-4, +4));\n  occlusionFactor = pow(occlusionFactor, 4.0) + 55.0/255.0; // empirical bias determined to let SSAO have no effect on the map plane\n  occlusionFactor = 1.0 - ((1.0 - occlusionFactor) * uEffectStrength * (1.0-fogIntensity));\n  gl_FragColor = vec4(vec3(occlusionFactor), 1.0);\n}\n"
                },
                blur: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nvarying vec2 vTexCoord;\nvoid main() {\n  gl_Position = aPosition;\n  vTexCoord = aTexCoord;\n}\n",
                    fragment: "#ifdef GL_ES\n  precision mediump float;\n#endif\nuniform sampler2D uTexIndex;\nuniform vec2 uInverseTexSize;   //in 1/pixels, e.g. 1/512 if the texture is 512px wide\nvarying vec2 vTexCoord;\n/* Retrieves the texel color 'offset' pixels away from 'pos' from texture 'uTexIndex'. */\nvec4 getTexel(vec2 pos, vec2 offset)\n{\n  return texture2D(uTexIndex, pos + offset * uInverseTexSize);\n}\nvoid main() {\n  vec4 center = texture2D(uTexIndex, vTexCoord);\n  vec4 nonDiagonalNeighbors = getTexel(vTexCoord, vec2(-1.0,  0.0)) +\n                              getTexel(vTexCoord, vec2(+1.0,  0.0)) +\n                              getTexel(vTexCoord, vec2( 0.0, -1.0)) +\n                              getTexel(vTexCoord, vec2( 0.0, +1.0));\n  vec4 diagonalNeighbors =    getTexel(vTexCoord, vec2(-1.0, -1.0)) +\n                              getTexel(vTexCoord, vec2(+1.0, +1.0)) +\n                              getTexel(vTexCoord, vec2(-1.0, +1.0)) +\n                              getTexel(vTexCoord, vec2(+1.0, -1.0));\n  \n  //approximate Gaussian blur (mean 0.0, stdev 1.0)\n  gl_FragColor = 0.2/1.0 * center + \n                 0.5/4.0 * nonDiagonalNeighbors + \n                 0.3/4.0 * diagonalNeighbors;\n}\n"
                },
                "basemap.shadows": {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec3 aPosition;\nattribute vec3 aNormal;\nuniform mat4 uModelMatrix;\nuniform mat4 uMatrix;\nuniform mat4 uSunMatrix;\nuniform vec2 uViewDirOnMap;\nuniform vec2 uLowerEdgePoint;\n//varying vec2 vTexCoord;\nvarying vec3 vSunRelPosition;\nvarying vec3 vNormal;\nvarying float verticalDistanceToLowerEdge;\nvoid main() {\n  vec4 pos = vec4(aPosition.xyz, 1.0);\n  gl_Position = uMatrix * pos;\n  vec4 sunRelPosition = uSunMatrix * pos;\n  vSunRelPosition = (sunRelPosition.xyz / sunRelPosition.w + 1.0) / 2.0;\n  vNormal = aNormal;\n  vec4 worldPos = uModelMatrix * pos;\n  vec2 dirFromLowerEdge = worldPos.xy / worldPos.w - uLowerEdgePoint;\n  verticalDistanceToLowerEdge = dot(dirFromLowerEdge, uViewDirOnMap);\n}\n",
                    fragment: "\n#ifdef GL_FRAGMENT_PRECISION_HIGH\n  precision highp float;\n#else\n  precision mediump float;\n#endif\n/* This shader computes the diffuse brightness of the map layer. It does *not* \n * render the map texture itself, but is instead intended to be blended on top\n * of an already rendered map.\n * Note: this shader is not (and does not attempt to) be physically correct.\n *       It is intented to be a blend between a useful illustration of cast\n *       shadows and a mitigation of shadow casting artifacts occuring at\n *       low angles on incidence.\n *       Map brightness is only affected by shadows, not by light direction.\n *       Shadows are darkest when light comes from straight above (and thus\n *       shadows can be computed reliably) and become less and less visible\n *       with the light source close to the horizont (where moirC) and offset\n *       artifacts would otherwise be visible).\n */\n//uniform sampler2D uTexIndex;\nuniform sampler2D uShadowTexIndex;\nuniform vec3 uFogColor;\nuniform vec3 uDirToSun;\nuniform vec2 uShadowTexDimensions;\nuniform float uShadowStrength;\nvarying vec2 vTexCoord;\nvarying vec3 vSunRelPosition;\nvarying vec3 vNormal;\nvarying float verticalDistanceToLowerEdge;\nuniform float uFogDistance;\nuniform float uFogBlurDistance;\nfloat isSeenBySun( const vec2 sunViewNDC, const float depth, const float bias) {\n  if ( clamp( sunViewNDC, 0.0, 1.0) != sunViewNDC)  //not inside sun's viewport\n    return 1.0;\n  \n  float depthFromTexture = texture2D( uShadowTexIndex, sunViewNDC.xy).x;\n  \n  //compare depth values not in reciprocal but in linear depth\n  return step(1.0/depthFromTexture, 1.0/depth + bias);\n}\nvoid main() {\n  //vec2 tl = floor(vSunRelPosition.xy * uShadowTexDimensions) / uShadowTexDimensions;\n  //gl_FragColor = vec4(vec3(texture2D( uShadowTexIndex, tl).x), 1.0);\n  //return;\n  float diffuse = dot(uDirToSun, normalize(vNormal));\n  diffuse = max(diffuse, 0.0);\n  \n  float shadowStrength = uShadowStrength * pow(diffuse, 1.5);\n  if (diffuse > 0.0) {\n    // note: the diffuse term is also the cosine between the surface normal and the\n    // light direction\n    float bias = clamp(0.0007*tan(acos(diffuse)), 0.0, 0.01);\n    \n    vec2 pos = fract( vSunRelPosition.xy * uShadowTexDimensions);\n    \n    vec2 tl = floor(vSunRelPosition.xy * uShadowTexDimensions) / uShadowTexDimensions;\n    float tlVal = isSeenBySun( tl,                           vSunRelPosition.z, bias);\n    float trVal = isSeenBySun( tl + vec2(1.0, 0.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    float blVal = isSeenBySun( tl + vec2(0.0, 1.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    float brVal = isSeenBySun( tl + vec2(1.0, 1.0) / uShadowTexDimensions, vSunRelPosition.z, bias);\n    diffuse = mix( mix(tlVal, trVal, pos.x), \n                   mix(blVal, brVal, pos.x),\n                   pos.y);\n  }\n  diffuse = mix(1.0, diffuse, shadowStrength);\n  \n  float fogIntensity = (verticalDistanceToLowerEdge - uFogDistance) / uFogBlurDistance;\n  fogIntensity = clamp(fogIntensity, 0.0, 1.0);\n  float darkness = (1.0 - diffuse);\n  darkness *=  (1.0 - fogIntensity);\n  gl_FragColor = vec4(vec3(1.0 - darkness), 1.0);\n}\n"
                },
                outlineMap: {
                    vertex: "precision highp float;  //is default in vertex shaders anyway, using highp fixes #49\nattribute vec4 aPosition;\nattribute vec2 aTexCoord;\nuniform mat4 uMatrix;\nvarying vec2 vTexCoord;\nvoid main() {\n  gl_Position = uMatrix * aPosition;\n  vTexCoord = aTexCoord;\n}\n",
                    fragment: "#ifdef GL_FRAGMENT_PRECISION_HIGH\n  // we need high precision for the depth values\n  precision highp float;\n#else\n  precision mediump float;\n#endif\nuniform sampler2D uDepthTexIndex;\nuniform sampler2D uFogNormalTexIndex;\nuniform sampler2D uIdTexIndex;\nuniform vec2 uInverseTexSize;   //in 1/pixels, e.g. 1/512 if the texture is 512px wide\nuniform float uEffectStrength;\nuniform float uNearPlane;\nuniform float uFarPlane;\nvarying vec2 vTexCoord;\n/* Retrieves the depth value 'offset' pixels away from 'pos' from texture 'uDepthTexIndex'. */\nfloat getDepth(vec2 pos, vec2 offset)\n{\n  float z = texture2D(uDepthTexIndex, pos + offset * uInverseTexSize).x;\n  return (2.0 * uNearPlane) / (uFarPlane + uNearPlane - z * (uFarPlane - uNearPlane)); // linearize depth\n}\nvec3 getNormal(vec2 pos, vec2 offset)\n{\n  return normalize(texture2D(uFogNormalTexIndex, pos + offset * uInverseTexSize).xyz * 2.0 - 1.0);\n}\nvec3 getEncodedId(vec2 pos, vec2 offset)\n{\n  return texture2D(uIdTexIndex, pos + offset * uInverseTexSize).xyz;\n}\nvoid main() {\n  float fogIntensity = texture2D(uFogNormalTexIndex, vTexCoord).w;\n  vec3 normalHere =  getNormal(vTexCoord, vec2(0, 0));\n  vec3 normalRight = getNormal(vTexCoord, vec2(1, 0));\n  vec3 normalAbove = getNormal(vTexCoord, vec2(0,-1));\n  \n  float edgeStrengthFromNormal = \n    step( dot(normalHere, normalRight), 0.9) +\n    step( dot(normalHere, normalAbove), 0.9);\n  float depthHere =  getDepth(vTexCoord, vec2(0,  0));\n  float depthRight = getDepth(vTexCoord, vec2(1,  0));\n  float depthAbove = getDepth(vTexCoord, vec2(0, -1));\n  float depthDiffRight = abs(depthHere - depthRight) * 7500.0;\n  float depthDiffAbove = abs(depthHere - depthAbove) * 7500.0;\n  float edgeStrengthFromDepth = step(10.0, depthDiffRight) + \n                                step(10.0, depthDiffAbove);\n  \n  vec3 idHere = getEncodedId(vTexCoord, vec2(0,0));\n  vec3 idRight = getEncodedId(vTexCoord, vec2(1,0));\n  vec3 idAbove = getEncodedId(vTexCoord, vec2(0,-1));\n  float edgeStrengthFromId = (idHere != idRight || idHere != idAbove) ? 1.0 : 0.0;\n  \n  float edgeStrength = max( edgeStrengthFromId, max( edgeStrengthFromNormal, edgeStrengthFromDepth));\n  float occlusionFactor = 1.0 - (edgeStrength * uEffectStrength);\n  occlusionFactor = 1.0 - ((1.0- occlusionFactor) * (1.0-fogIntensity));\n  gl_FragColor = vec4(vec3(occlusionFactor), 1.0);\n}\n"
                }
            },
            aa = function() {
                var a = {};
                return a.getContext = function(a) {
                    var b = {
                        antialias: !ha.options.fastMode,
                        depth: !0,
                        premultipliedAlpha: !1
                    };
                    try {
                        ia = a.getContext("webgl", b)
                    } catch(c) {}
                    if (!ia) try {
                        ia = a.getContext("experimental-webgl", b)
                    } catch(c) {}
                    if (!ia) throw new Error("WebGL not supported");
                    return a.addEventListener("webglcontextlost",
                        function(a) {
                            console.warn("context lost")
                        }),
                        a.addEventListener("webglcontextrestored",
                            function(a) {
                                console.warn("context restored")
                            }),
                        ia.viewport(0, 0, ha.width, ha.height),
                        ia.cullFace(ia.BACK),
                        ia.enable(ia.CULL_FACE),
                        ia.enable(ia.DEPTH_TEST),
                        ia.clearColor(.5, .5, .5, 1),
                    ha.options.fastMode || (ia.anisotropyExtension = ia.getExtension("EXT_texture_filter_anisotropic"), ia.anisotropyExtension && (ia.anisotropyExtension.maxAnisotropyLevel = ia.getParameter(ia.anisotropyExtension.MAX_TEXTURE_MAX_ANISOTROPY_EXT)), ia.depthTextureExtension = ia.getExtension("WEBGL_depth_texture")),
                        ia
                },
                    a.start = function(a) {
                        return setInterval(function() {
                                requestAnimationFrame(a)
                            },
                            17)
                    },
                    a.stop = function(a) {
                        clearInterval(a)
                    },
                    a.destroy = function() {
                        void 0 !== ia && (ia.canvas.parentNode.removeChild(ia.canvas), ia = void 0)
                    },
                    a.util = {},
                    a.util.nextPowerOf2 = function(a) {
                        return a--,
                            a |= a >> 1,
                            a |= a >> 2,
                            a |= a >> 4,
                            a |= a >> 8,
                            a |= a >> 16,
                            a++,
                            a
                    },
                    a.util.calcNormal = function(a, b, c, d, e, f, g, h, i) {
                        var j = a - d,
                            k = b - e,
                            l = c - f,
                            m = d - g,
                            n = e - h,
                            o = f - i,
                            p = k * o - l * n,
                            q = l * m - j * o,
                            r = j * n - k * m;
                        return this.calcUnit(p, q, r)
                    },
                    a.util.calcUnit = function(a, b, c) {
                        var d = Math.sqrt(a * a + b * b + c * c);
                        return 0 === d && (d = 1e-5),
                            [a / d, b / d, c / d]
                    },
                    a.Buffer = function(a, b) {
                        this.id = ia.createBuffer(),
                            this.itemSize = a,
                            this.numItems = b.length / a,
                            ia.bindBuffer(ia.ARRAY_BUFFER, this.id),
                            ia.bufferData(ia.ARRAY_BUFFER, b, ia.STATIC_DRAW),
                            b = null
                    },
                    a.Buffer.prototype = {
                        enable: function() {
                            ia.bindBuffer(ia.ARRAY_BUFFER, this.id)
                        },
                        destroy: function() {
                            ia.deleteBuffer(this.id),
                                this.id = null
                        }
                    },
                    a.Framebuffer = function(a, b, c) {
                        if (c && !ia.depthTextureExtension) throw "Depth textures are not supported by your GPU";
                        this.useDepthTexture = !!c,
                            this.setSize(a, b)
                    },
                    a.Framebuffer.prototype = {
                        setSize: function(b, c) {
                            if (this.frameBuffer) {
                                if (b === this.width && c === this.height) return
                            } else this.frameBuffer = ia.createFramebuffer();
                            if (ia.bindFramebuffer(ia.FRAMEBUFFER, this.frameBuffer), this.width = b, this.height = c, this.depthRenderBuffer && (ia.deleteRenderbuffer(this.depthRenderBuffer), this.depthRenderBuffer = null), this.depthTexture && (this.depthTexture.destroy(), this.depthTexture = null), this.useDepthTexture ? (this.depthTexture = new a.texture.Image, this.depthTexture.enable(0), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MIN_FILTER, ia.NEAREST), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MAG_FILTER, ia.NEAREST), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_S, ia.CLAMP_TO_EDGE), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_T, ia.CLAMP_TO_EDGE), ia.texImage2D(ia.TEXTURE_2D, 0, ia.DEPTH_STENCIL, b, c, 0, ia.DEPTH_STENCIL, ia.depthTextureExtension.UNSIGNED_INT_24_8_WEBGL, null), ia.framebufferTexture2D(ia.FRAMEBUFFER, ia.DEPTH_STENCIL_ATTACHMENT, ia.TEXTURE_2D, this.depthTexture.id, 0)) : (this.depthRenderBuffer = ia.createRenderbuffer(), ia.bindRenderbuffer(ia.RENDERBUFFER, this.depthRenderBuffer), ia.renderbufferStorage(ia.RENDERBUFFER, ia.DEPTH_COMPONENT16, b, c), ia.framebufferRenderbuffer(ia.FRAMEBUFFER, ia.DEPTH_ATTACHMENT, ia.RENDERBUFFER, this.depthRenderBuffer)), this.renderTexture && this.renderTexture.destroy(), this.renderTexture = new a.texture.Data(b, c), ia.bindTexture(ia.TEXTURE_2D, this.renderTexture.id), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_S, ia.CLAMP_TO_EDGE), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_T, ia.CLAMP_TO_EDGE), ia.framebufferTexture2D(ia.FRAMEBUFFER, ia.COLOR_ATTACHMENT0, ia.TEXTURE_2D, this.renderTexture.id, 0), ia.checkFramebufferStatus(ia.FRAMEBUFFER) !== ia.FRAMEBUFFER_COMPLETE) throw new Error("This combination of framebuffer attachments does not work");
                            ia.bindRenderbuffer(ia.RENDERBUFFER, null),
                                ia.bindFramebuffer(ia.FRAMEBUFFER, null)
                        },
                        enable: function() {
                            ia.bindFramebuffer(ia.FRAMEBUFFER, this.frameBuffer),
                            this.useDepthTexture || ia.bindRenderbuffer(ia.RENDERBUFFER, this.depthRenderBuffer)
                        },
                        disable: function() {
                            ia.bindFramebuffer(ia.FRAMEBUFFER, null),
                            this.useDepthTexture || ia.bindRenderbuffer(ia.RENDERBUFFER, null)
                        },
                        getPixel: function(a, b) {
                            var c = new Uint8Array(4);
                            if (! (0 > a || 0 > b || a >= this.width || b >= this.height)) return ia.readPixels(a, b, 1, 1, ia.RGBA, ia.UNSIGNED_BYTE, c),
                                c
                        },
                        getData: function() {
                            var a = new Uint8Array(this.width * this.height * 4);
                            return ia.readPixels(0, 0, this.width, this.height, ia.RGBA, ia.UNSIGNED_BYTE, a),
                                a
                        },
                        destroy: function() {
                            this.renderTexture && this.renderTexture.destroy(),
                            this.depthTexture && this.depthTexture.destroy()
                        }
                    },
                    a.Shader = function(a) {
                        var b;
                        if (this.shaderName = a.shaderName, this.id = ia.createProgram(), this.attach(ia.VERTEX_SHADER, a.vertexShader), this.attach(ia.FRAGMENT_SHADER, a.fragmentShader), ia.linkProgram(this.id), !ia.getProgramParameter(this.id, ia.LINK_STATUS)) throw new Error(ia.getProgramParameter(this.id, ia.VALIDATE_STATUS) + "\n" + ia.getError());
                        for (this.attributeNames = a.attributes || [], this.uniformNames = a.uniforms || [], ia.useProgram(this.id), this.attributes = {},
                                 b = 0; b < this.attributeNames.length; b++) this.locateAttribute(this.attributeNames[b]);
                        for (this.uniforms = {},
                                 b = 0; b < this.uniformNames.length; b++) this.locateUniform(this.uniformNames[b])
                    },
                    a.Shader.warned = {},
                    a.Shader.prototype = {
                        locateAttribute: function(a) {
                            var b = ia.getAttribLocation(this.id, a);
                            return 0 > b ? void console.warn('unable to locate attribute "%s" in shader "%s"', a, this.shaderName) : void(this.attributes[a] = b)
                        },
                        locateUniform: function(a) {
                            var b = ia.getUniformLocation(this.id, a);
                            return b ? void(this.uniforms[a] = b) : void console.warn('unable to locate uniform "%s" in shader "%s"', a, this.shaderName)
                        },
                        attach: function(a, b) {
                            var c = ia.createShader(a);
                            if (ia.shaderSource(c, b), ia.compileShader(c), !ia.getShaderParameter(c, ia.COMPILE_STATUS)) throw new Error(ia.getShaderInfoLog(c));
                            ia.attachShader(this.id, c)
                        },
                        enable: function() {
                            ia.useProgram(this.id);
                            for (var a in this.attributes) ia.enableVertexAttribArray(this.attributes[a]);
                            return this
                        },
                        disable: function() {
                            if (this.attributes) for (var a in this.attributes) ia.disableVertexAttribArray(this.attributes[a])
                        },
                        bindBuffer: function(b, c) {
                            if (void 0 === this.attributes[c]) {
                                var d = this.shaderName + ":" + c;
                                return void(a.Shader.warned[d] || (console.warn('attempt to bind VBO to invalid attribute "%s" in shader "%s"', c, this.shaderName), a.Shader.warned[d] = !0))
                            }
                            b.enable(),
                                ia.vertexAttribPointer(this.attributes[c], b.itemSize, ia.FLOAT, !1, 0, 0)
                        },
                        setUniform: function(b, c, d) {
                            if (void 0 === this.uniforms[b]) {
                                var e = this.shaderName + ":" + b;
                                return void(a.Shader.warned[e] || (console.warn('attempt to bind to invalid uniform "%s" in shader "%s"', b, this.shaderName), a.Shader.warned[e] = !0))
                            }
                            ia["uniform" + c](this.uniforms[b], d)
                        },
                        setUniforms: function(a) {
                            for (var b in a) this.setUniform(a[b][0], a[b][1], a[b][2])
                        },
                        setUniformMatrix: function(b, c, d) {
                            if (void 0 === this.uniforms[b]) {
                                var e = this.shaderName + ":" + b;
                                return void(a.Shader.warned[e] || (console.warn('attempt to bind to invalid uniform "%s" in shader "%s"', b, this.shaderName), a.Shader.warned[e] = !0))
                            }
                            ia["uniformMatrix" + c](this.uniforms[b], !1, d)
                        },
                        setUniformMatrices: function(a) {
                            for (var b in a) this.setUniformMatrix(a[b][0], a[b][1], a[b][2])
                        },
                        bindTexture: function(a, b, c) {
                            c.enable(b),
                                this.setUniform(a, "1i", b)
                        },
                        destroy: function() {
                            this.disable(),
                                this.id = null
                        }
                    },
                    a.Matrix = function(a) {
                        this.data = new Float32Array(a ? a: [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1])
                    },
                    a.Matrix.identity = function() {
                        return new a.Matrix([1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1])
                    },
                    a.Matrix.identity3 = function() {
                        return new a.Matrix([1, 0, 0, 0, 1, 0, 0, 0, 1])
                    },
                    function() {
                        function b(a) {
                            return a * Math.PI / 180
                        }
                        function c(a, b, c) {
                            var d = b[0],
                                e = b[1],
                                f = b[2],
                                g = b[3],
                                h = b[4],
                                i = b[5],
                                j = b[6],
                                k = b[7],
                                l = b[8],
                                m = b[9],
                                n = b[10],
                                o = b[11],
                                p = b[12],
                                q = b[13],
                                r = b[14],
                                s = b[15],
                                t = c[0],
                                u = c[1],
                                v = c[2],
                                w = c[3],
                                x = c[4],
                                y = c[5],
                                z = c[6],
                                A = c[7],
                                B = c[8],
                                C = c[9],
                                D = c[10],
                                E = c[11],
                                F = c[12],
                                G = c[13],
                                H = c[14],
                                I = c[15];
                            a[0] = d * t + e * x + f * B + g * F,
                                a[1] = d * u + e * y + f * C + g * G,
                                a[2] = d * v + e * z + f * D + g * H,
                                a[3] = d * w + e * A + f * E + g * I,
                                a[4] = h * t + i * x + j * B + k * F,
                                a[5] = h * u + i * y + j * C + k * G,
                                a[6] = h * v + i * z + j * D + k * H,
                                a[7] = h * w + i * A + j * E + k * I,
                                a[8] = l * t + m * x + n * B + o * F,
                                a[9] = l * u + m * y + n * C + o * G,
                                a[10] = l * v + m * z + n * D + o * H,
                                a[11] = l * w + m * A + n * E + o * I,
                                a[12] = p * t + q * x + r * B + s * F,
                                a[13] = p * u + q * y + r * C + s * G,
                                a[14] = p * v + q * z + r * D + s * H,
                                a[15] = p * w + q * A + r * E + s * I
                        }
                        a.Matrix.prototype = {
                            multiply: function(a) {
                                return c(this.data, this.data, a.data),
                                    this
                            },
                            translate: function(a, b, d) {
                                return c(this.data, this.data, [1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, a, b, d, 1]),
                                    this
                            },
                            rotateX: function(a) {
                                var d = b(a),
                                    e = Math.cos(d),
                                    f = Math.sin(d);
                                return c(this.data, this.data, [1, 0, 0, 0, 0, e, f, 0, 0, -f, e, 0, 0, 0, 0, 1]),
                                    this
                            },
                            rotateY: function(a) {
                                var d = b(a),
                                    e = Math.cos(d),
                                    f = Math.sin(d);
                                return c(this.data, this.data, [e, 0, -f, 0, 0, 1, 0, 0, f, 0, e, 0, 0, 0, 0, 1]),
                                    this
                            },
                            rotateZ: function(a) {
                                var d = b(a),
                                    e = Math.cos(d),
                                    f = Math.sin(d);
                                return c(this.data, this.data, [e, -f, 0, 0, f, e, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1]),
                                    this
                            },
                            scale: function(a, b, d) {
                                return c(this.data, this.data, [a, 0, 0, 0, 0, b, 0, 0, 0, 0, d, 0, 0, 0, 0, 1]),
                                    this
                            }
                        },
                            a.Matrix.multiply = function(a, b) {
                                var d = new Float32Array(16);
                                return c(d, a.data, b.data),
                                    d
                            },
                            a.Matrix.Perspective = function(b, c, d, e) {
                                var f = 1 / Math.tan(b * (Math.PI / 180) / 2),
                                    g = 1 / (d - e);
                                return new a.Matrix([f / c, 0, 0, 0, 0, f, 0, 0, 0, 0, (e + d) * g, -1, 0, 0, 2 * e * d * g, 0])
                            },
                            a.Matrix.Frustum = function(b, c, d, e, f, g) {
                                var h = 1 / (c - b),
                                    i = 1 / (d - e),
                                    j = 1 / (f - g);
                                return new a.Matrix([2 * f * h, 0, 0, 0, 0, 2 * f * i, 0, 0, (c + b) * h, (d + e) * i, (g + f) * j, -1, 0, 0, g * f * 2 * j, 0])
                            },
                            a.Matrix.OffCenterProjection = function(b, c, d, e, f, g) {
                                var h = X(S(d, b)),
                                    i = X(S(c, b)),
                                    j = y(b, c, d),
                                    k = S(b, e),
                                    l = S(c, e),
                                    m = S(d, e),
                                    n = -R(k, j),
                                    o = R(h, k) * f / n,
                                    p = R(h, m) * f / n,
                                    q = R(i, k) * f / n,
                                    r = R(i, l) * f / n;
                                return a.Matrix.Frustum(o, p, r, q, f, g)
                            },
                            a.Matrix.Ortho = function(b, c, d, e, f, g) {
                                return new a.Matrix([2 / (c - b), 0, 0, 0, 0, 2 / (d - e), 0, 0, 0, 0, -2 / (g - f), 0, -(c + b) / (c - b), -(d + e) / (d - e), -(g + f) / (g - f), 1])
                            },
                            a.Matrix.invert3 = function(a) {
                                var b = a[0],
                                    c = a[1],
                                    d = a[2],
                                    e = a[4],
                                    f = a[5],
                                    g = a[6],
                                    h = a[8],
                                    i = a[9],
                                    j = a[10],
                                    k = j * f - g * i,
                                    l = -j * e + g * h,
                                    m = i * e - f * h,
                                    n = b * k + c * l + d * m;
                                return n ? (n = 1 / n, [k * n, ( - j * c + d * i) * n, (g * c - d * f) * n, l * n, (j * b - d * h) * n, ( - g * b + d * e) * n, m * n, ( - i * b + c * h) * n, (f * b - c * e) * n]) : null
                            },
                            a.Matrix.transpose3 = function(a) {
                                return new Float32Array([a[0], a[3], a[6], a[1], a[4], a[7], a[2], a[5], a[8]])
                            },
                            a.Matrix.transpose = function(a) {
                                return new Float32Array([a[0], a[4], a[8], a[12], a[1], a[5], a[9], a[13], a[2], a[6], a[10], a[14], a[3], a[7], a[11], a[15]])
                            },
                            a.Matrix.transform = function(a) {
                                var b = a[12],
                                    c = a[13],
                                    d = a[14],
                                    e = a[15];
                                return {
                                    x: (b / e + 1) / 2,
                                    y: (c / e + 1) / 2,
                                    z: (d / e + 1) / 2
                                }
                            },
                            a.Matrix.invert = function(a) {
                                var b = new Float32Array(16),
                                    c = a[0],
                                    d = a[1],
                                    e = a[2],
                                    f = a[3],
                                    g = a[4],
                                    h = a[5],
                                    i = a[6],
                                    j = a[7],
                                    k = a[8],
                                    l = a[9],
                                    m = a[10],
                                    n = a[11],
                                    o = a[12],
                                    p = a[13],
                                    q = a[14],
                                    r = a[15],
                                    s = c * h - d * g,
                                    t = c * i - e * g,
                                    u = c * j - f * g,
                                    v = d * i - e * h,
                                    w = d * j - f * h,
                                    x = e * j - f * i,
                                    y = k * p - l * o,
                                    z = k * q - m * o,
                                    A = k * r - n * o,
                                    B = l * q - m * p,
                                    C = l * r - n * p,
                                    D = m * r - n * q,
                                    E = s * D - t * C + u * B + v * A - w * z + x * y;
                                if (E) return E = 1 / E,
                                    b[0] = (h * D - i * C + j * B) * E,
                                    b[1] = (e * C - d * D - f * B) * E,
                                    b[2] = (p * x - q * w + r * v) * E,
                                    b[3] = (m * w - l * x - n * v) * E,
                                    b[4] = (i * A - g * D - j * z) * E,
                                    b[5] = (c * D - e * A + f * z) * E,
                                    b[6] = (q * u - o * x - r * t) * E,
                                    b[7] = (k * x - m * u + n * t) * E,
                                    b[8] = (g * C - h * A + j * y) * E,
                                    b[9] = (d * A - c * C - f * y) * E,
                                    b[10] = (o * w - p * u + r * s) * E,
                                    b[11] = (l * u - k * w - n * s) * E,
                                    b[12] = (h * z - g * B - i * y) * E,
                                    b[13] = (c * B - d * z + e * y) * E,
                                    b[14] = (p * t - o * v - q * s) * E,
                                    b[15] = (k * v - l * t + m * s) * E,
                                    b
                            }
                    } (),
                    a.texture = {},
                    a.texture.Image = function() {
                        this.id = ia.createTexture(),
                            ia.bindTexture(ia.TEXTURE_2D, this.id),
                            ia.bindTexture(ia.TEXTURE_2D, null)
                    },
                    a.texture.Image.prototype = {
                        clamp: function(a, b) {
                            if (a.width <= b && a.height <= b) return a;
                            var c = b,
                                d = b,
                                e = a.width / a.height;
                            1 > e ? c = Math.round(d * e) : d = Math.round(c / e);
                            var f = ta.createElement("CANVAS");
                            f.width = c,
                                f.height = d;
                            var g = f.getContext("2d");
                            return g.drawImage(a, 0, 0, f.width, f.height),
                                f
                        },
                        load: function(a, b) {
                            var c = new Image;
                            return c.crossOrigin = "*",
                                c.onload = function() {
                                    this.set(c),
                                    b && b(c)
                                }.bind(this),
                                c.onerror = function() {
                                    b && b()
                                },
                                c.src = a,
                                this
                        },
                        color: function(a) {
                            return ia.bindTexture(ia.TEXTURE_2D, this.id),
                                ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MIN_FILTER, ia.LINEAR),
                                ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MAG_FILTER, ia.LINEAR),
                                ia.texImage2D(ia.TEXTURE_2D, 0, ia.RGBA, 1, 1, 0, ia.RGBA, ia.UNSIGNED_BYTE, new Uint8Array([255 * a[0], 255 * a[1], 255 * a[2], 255 * (void 0 === a[3] ? 1 : a[3])])),
                                ia.bindTexture(ia.TEXTURE_2D, null),
                                this
                        },
                        set: function(a) {
                            return this.id ? (a = this.clamp(a, ia.getParameter(ia.MAX_TEXTURE_SIZE)), ia.bindTexture(ia.TEXTURE_2D, this.id), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MIN_FILTER, ia.LINEAR_MIPMAP_NEAREST), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MAG_FILTER, ia.LINEAR), ia.texImage2D(ia.TEXTURE_2D, 0, ia.RGBA, ia.RGBA, ia.UNSIGNED_BYTE, a), ia.generateMipmap(ia.TEXTURE_2D), ia.anisotropyExtension && ia.texParameterf(ia.TEXTURE_2D, ia.anisotropyExtension.TEXTURE_MAX_ANISOTROPY_EXT, ia.anisotropyExtension.maxAnisotropyLevel), ia.bindTexture(ia.TEXTURE_2D, null), this) : void 0
                        },
                        enable: function(a) {
                            return this.id ? (ia.activeTexture(ia.TEXTURE0 + (a || 0)), ia.bindTexture(ia.TEXTURE_2D, this.id), this) : void 0
                        },
                        destroy: function() {
                            ia.bindTexture(ia.TEXTURE_2D, null),
                                ia.deleteTexture(this.id),
                                this.id = null
                        }
                    },
                    a.texture.Data = function(a, b, c, d) {
                        this.id = ia.createTexture(),
                            ia.bindTexture(ia.TEXTURE_2D, this.id),
                            ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MIN_FILTER, ia.NEAREST),
                            ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_MAG_FILTER, ia.NEAREST);
                        var e = null;
                        if (c) {
                            var f = a * b * 4;
                            e = new Uint8Array(f),
                                e.set(c.subarray(0, f))
                        }
                        ia.texImage2D(ia.TEXTURE_2D, 0, ia.RGBA, a, b, 0, ia.RGBA, ia.UNSIGNED_BYTE, e),
                            ia.bindTexture(ia.TEXTURE_2D, null)
                    },
                    a.texture.Data.prototype = {
                        enable: function(a) {
                            return ia.activeTexture(ia.TEXTURE0 + (a || 0)),
                                ia.bindTexture(ia.TEXTURE_2D, this.id),
                                this
                        },
                        destroy: function() {
                            ia.bindTexture(ia.TEXTURE_2D, null),
                                ia.deleteTexture(this.id),
                                this.id = null
                        }
                    },
                    a
            } (),
            ba = {
                len: function(a) {
                    return Math.sqrt(a[0] * a[0] + a[1] * a[1])
                },
                add: function(a, b) {
                    return [a[0] + b[0], a[1] + b[1]]
                },
                sub: function(a, b) {
                    return [a[0] - b[0], a[1] - b[1]]
                },
                dot: function(a, b) {
                    return a[1] * b[0] - a[0] * b[1]
                },
                scale: function(a, b) {
                    return [a[0] * b, a[1] * b]
                },
                equals: function(a, b) {
                    return a[0] === b[0] && a[1] === b[1]
                }
            },
            ca = {
                len: function(a) {
                    return Math.sqrt(a[0] * a[0] + a[1] * a[1] + a[2] * a[2])
                },
                sub: function(a, b) {
                    return [a[0] - b[0], a[1] - b[1], a[2] - b[2]]
                },
                unit: function(a) {
                    var b = this.len(a);
                    return [a[0] / b, a[1] / b, a[2] / b]
                },
                normal: function(a, b, c) {
                    var d = this.sub(a, b),
                        e = this.sub(b, c);
                    return this.unit([d[1] * e[2] - d[2] * e[1], d[2] * e[0] - d[0] * e[2], d[0] * e[1] - d[1] * e[0]])
                }
            },
            da = function() {
                function a(a, c, e) {
                    e = e || 2;
                    var f = c && c.length,
                        g = f ? c[0] * e: a.length,
                        h = b(a, 0, g, e, !0),
                        j = [];
                    if (!h) return j;
                    var k, l, m, n, o, p, q;
                    if (f && (h = i(a, c, h, e)), a.length > 80 * e) {
                        k = m = a[0],
                            l = n = a[1];
                        for (var r = e; g > r; r += e) o = a[r],
                            p = a[r + 1],
                        k > o && (k = o),
                        l > p && (l = p),
                        o > m && (m = o),
                        p > n && (n = p);
                        q = Math.max(m - k, n - l)
                    }
                    return d(h, j, e, k, l, q),
                        j
                }
                function b(a, b, c, d, e) {
                    var f, g;
                    if (e === C(a, b, c, d) > 0) for (f = b; c > f; f += d) g = z(f, a[f], a[f + 1], g);
                    else for (f = c - d; f >= b; f -= d) g = z(f, a[f], a[f + 1], g);
                    return g && t(g, g.next) && (A(g), g = g.next),
                        g
                }
                function c(a, b) {
                    if (!a) return a;
                    b || (b = a);
                    var c, d = a;
                    do
                        if (c = !1, d.steiner || !t(d, d.next) && 0 !== s(d.prev, d, d.next)) d = d.next;
                        else {
                            if (A(d), d = b = d.prev, d === d.next) return null;
                            c = !0
                        }
                    while (c || d !== b);
                    return b
                }
                function d(a, b, i, j, k, l, n) {
                    if (a) { ! n && l && m(a, j, k, l);
                        for (var o, p, q = a; a.prev !== a.next;) if (o = a.prev, p = a.next, l ? f(a, j, k, l) : e(a)) b.push(o.i / i),
                            b.push(a.i / i),
                            b.push(p.i / i),
                            A(a),
                            a = p.next,
                            q = p.next;
                        else if (a = p, a === q) {
                            n ? 1 === n ? (a = g(a, b, i), d(a, b, i, j, k, l, 2)) : 2 === n && h(a, b, i, j, k, l) : d(c(a), b, i, j, k, l, 1);
                            break
                        }
                    }
                }
                function e(a) {
                    var b = a.prev,
                        c = a,
                        d = a.next;
                    if (s(b, c, d) >= 0) return ! 1;
                    for (var e = a.next.next; e !== a.prev;) {
                        if (q(b.x, b.y, c.x, c.y, d.x, d.y, e.x, e.y) && s(e.prev, e, e.next) >= 0) return ! 1;
                        e = e.next
                    }
                    return ! 0
                }
                function f(a, b, c, d) {
                    var e = a.prev,
                        f = a,
                        g = a.next;
                    if (s(e, f, g) >= 0) return ! 1;
                    for (var h = e.x < f.x ? e.x < g.x ? e.x: g.x: f.x < g.x ? f.x: g.x, i = e.y < f.y ? e.y < g.y ? e.y: g.y: f.y < g.y ? f.y: g.y, j = e.x > f.x ? e.x > g.x ? e.x: g.x: f.x > g.x ? f.x: g.x, k = e.y > f.y ? e.y > g.y ? e.y: g.y: f.y > g.y ? f.y: g.y, l = o(h, i, b, c, d), m = o(j, k, b, c, d), n = a.nextZ; n && n.z <= m;) {
                        if (n !== a.prev && n !== a.next && q(e.x, e.y, f.x, f.y, g.x, g.y, n.x, n.y) && s(n.prev, n, n.next) >= 0) return ! 1;
                        n = n.nextZ
                    }
                    for (n = a.prevZ; n && n.z >= l;) {
                        if (n !== a.prev && n !== a.next && q(e.x, e.y, f.x, f.y, g.x, g.y, n.x, n.y) && s(n.prev, n, n.next) >= 0) return ! 1;
                        n = n.prevZ
                    }
                    return ! 0
                }
                function g(a, b, c) {
                    var d = a;
                    do {
                        var e = d.prev,
                            f = d.next.next; ! t(e, f) && u(e, d, d.next, f) && w(e, f) && w(f, e) && (b.push(e.i / c), b.push(d.i / c), b.push(f.i / c), A(d), A(d.next), d = a = f), d = d.next
                    } while ( d !== a );
                    return d
                }
                function h(a, b, e, f, g, h) {
                    var i = a;
                    do {
                        for (var j = i.next.next; j !== i.prev;) {
                            if (i.i !== j.i && r(i, j)) {
                                var k = y(i, j);
                                return i = c(i, i.next),
                                    k = c(k, k.next),
                                    d(i, b, e, f, g, h),
                                    void d(k, b, e, f, g, h)
                            }
                            j = j.next
                        }
                        i = i.next
                    } while ( i !== a )
                }
                function i(a, d, e, f) {
                    var g, h, i, l, m, n = [];
                    for (g = 0, h = d.length; h > g; g++) i = d[g] * f,
                        l = h - 1 > g ? d[g + 1] * f: a.length,
                        m = b(a, i, l, f, !1),
                    m === m.next && (m.steiner = !0),
                        n.push(p(m));
                    for (n.sort(j), g = 0; g < n.length; g++) k(n[g], e),
                        e = c(e, e.next);
                    return e
                }
                function j(a, b) {
                    return a.x - b.x
                }
                function k(a, b) {
                    if (b = l(a, b)) {
                        var d = y(b, a);
                        c(d, d.next)
                    }
                }
                function l(a, b) {
                    var c, d = b,
                        e = a.x,
                        f = a.y,
                        g = -(1 / 0);
                    do {
                        if (f <= d.y && f >= d.next.y) {
                            var h = d.x + (f - d.y) * (d.next.x - d.x) / (d.next.y - d.y);
                            if (e >= h && h > g) {
                                if (g = h, h === e) {
                                    if (f === d.y) return d;
                                    if (f === d.next.y) return d.next
                                }
                                c = d.x < d.next.x ? d: d.next
                            }
                        }
                        d = d.next
                    } while ( d !== b );
                    if (!c) return null;
                    if (e === g) return c.prev;
                    var i, j = c,
                        k = c.x,
                        l = c.y,
                        m = 1 / 0;
                    for (d = c.next; d !== j;) e >= d.x && d.x >= k && q(l > f ? e: g, f, k, l, l > f ? g: e, f, d.x, d.y) && (i = Math.abs(f - d.y) / (e - d.x), (m > i || i === m && d.x > c.x) && w(d, a) && (c = d, m = i)),
                        d = d.next;
                    return c
                }
                function m(a, b, c, d) {
                    var e = a;
                    do null === e.z && (e.z = o(e.x, e.y, b, c, d)),
                        e.prevZ = e.prev,
                        e.nextZ = e.next,
                        e = e.next;
                    while (e !== a);
                    e.prevZ.nextZ = null,
                        e.prevZ = null,
                        n(e)
                }
                function n(a) {
                    var b, c, d, e, f, g, h, i, j = 1;
                    do {
                        for (c = a, a = null, f = null, g = 0; c;) {
                            for (g++, d = c, h = 0, b = 0; j > b && (h++, d = d.nextZ, d); b++);
                            for (i = j; h > 0 || i > 0 && d;) 0 === h ? (e = d, d = d.nextZ, i--) : 0 !== i && d ? c.z <= d.z ? (e = c, c = c.nextZ, h--) : (e = d, d = d.nextZ, i--) : (e = c, c = c.nextZ, h--),
                                f ? f.nextZ = e: a = e,
                                e.prevZ = f,
                                f = e;
                            c = d
                        }
                        f.nextZ = null, j *= 2
                    } while ( g > 1 );
                    return a
                }
                function o(a, b, c, d, e) {
                    return a = 32767 * (a - c) / e,
                        b = 32767 * (b - d) / e,
                        a = 16711935 & (a | a << 8),
                        a = 252645135 & (a | a << 4),
                        a = 858993459 & (a | a << 2),
                        a = 1431655765 & (a | a << 1),
                        b = 16711935 & (b | b << 8),
                        b = 252645135 & (b | b << 4),
                        b = 858993459 & (b | b << 2),
                        b = 1431655765 & (b | b << 1),
                    a | b << 1
                }
                function p(a) {
                    var b = a,
                        c = a;
                    do b.x < c.x && (c = b),
                        b = b.next;
                    while (b !== a);
                    return c
                }
                function q(a, b, c, d, e, f, g, h) {
                    return (e - g) * (b - h) - (a - g) * (f - h) >= 0 && (a - g) * (d - h) - (c - g) * (b - h) >= 0 && (c - g) * (f - h) - (e - g) * (d - h) >= 0
                }
                function r(a, b) {
                    return a.next.i !== b.i && a.prev.i !== b.i && !v(a, b) && w(a, b) && w(b, a) && x(a, b)
                }
                function s(a, b, c) {
                    return (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y)
                }
                function t(a, b) {
                    return a.x === b.x && a.y === b.y
                }
                function u(a, b, c, d) {
                    return t(a, b) && t(c, d) || t(a, d) && t(c, b) ? !0 : s(a, b, c) > 0 != s(a, b, d) > 0 && s(c, d, a) > 0 != s(c, d, b) > 0
                }
                function v(a, b) {
                    var c = a;
                    do {
                        if (c.i !== a.i && c.next.i !== a.i && c.i !== b.i && c.next.i !== b.i && u(c, c.next, a, b)) return ! 0;
                        c = c.next
                    } while ( c !== a );
                    return ! 1
                }
                function w(a, b) {
                    return s(a.prev, a, a.next) < 0 ? s(a, b, a.next) >= 0 && s(a, a.prev, b) >= 0 : s(a, b, a.prev) < 0 || s(a, a.next, b) < 0
                }
                function x(a, b) {
                    var c = a,
                        d = !1,
                        e = (a.x + b.x) / 2,
                        f = (a.y + b.y) / 2;
                    do c.y > f != c.next.y > f && e < (c.next.x - c.x) * (f - c.y) / (c.next.y - c.y) + c.x && (d = !d),
                        c = c.next;
                    while (c !== a);
                    return d
                }
                function y(a, b) {
                    var c = new B(a.i, a.x, a.y),
                        d = new B(b.i, b.x, b.y),
                        e = a.next,
                        f = b.prev;
                    return a.next = b,
                        b.prev = a,
                        c.next = e,
                        e.prev = c,
                        d.next = c,
                        c.prev = d,
                        f.next = d,
                        d.prev = f,
                        d
                }
                function z(a, b, c, d) {
                    var e = new B(a, b, c);
                    return d ? (e.next = d.next, e.prev = d, d.next.prev = e, d.next = e) : (e.prev = e, e.next = e),
                        e
                }
                function A(a) {
                    a.next.prev = a.prev,
                        a.prev.next = a.next,
                    a.prevZ && (a.prevZ.nextZ = a.nextZ),
                    a.nextZ && (a.nextZ.prevZ = a.prevZ)
                }
                function B(a, b, c) {
                    this.i = a,
                        this.x = b,
                        this.y = c,
                        this.prev = null,
                        this.next = null,
                        this.z = null,
                        this.prevZ = null,
                        this.nextZ = null,
                        this.steiner = !1
                }
                function C(a, b, c, d) {
                    for (var e = 0,
                             f = b,
                             g = c - d; c > f; f += d) e += (a[g] - a[f]) * (a[f + 1] + a[g + 1]),
                        g = f;
                    return e
                }
                return a.deviation = function(a, b, c, d) {
                    var e, f, g = b && b.length,
                        h = g ? b[0] * c: a.length,
                        i = Math.abs(C(a, 0, h, c));
                    if (g) for (e = 0, f = b.length; f > e; e++) {
                        var j = b[e] * c,
                            k = f - 1 > e ? b[e + 1] * c: a.length;
                        i -= Math.abs(C(a, j, k, c))
                    }
                    var l = 0;
                    for (e = 0, f = d.length; f > e; e += 3) {
                        var m = d[e] * c,
                            n = d[e + 1] * c,
                            o = d[e + 2] * c;
                        l += Math.abs((a[m] - a[o]) * (a[n + 1] - a[m + 1]) - (a[m] - a[n]) * (a[o + 1] - a[m + 1]))
                    }
                    return 0 === i && 0 === l ? 0 : Math.abs((l - i) / i)
                },
                    a.flatten = function(a) {
                        for (var b = a[0][0].length, c = {
                                vertices: [],
                                holes: [],
                                dimensions: b
                            },
                                 d = 0, e = 0; e < a.length; e++) {
                            for (var f = 0; f < a[e].length; f++) for (var g = 0; b > g; g++) c.vertices.push(a[e][f][g]);
                            e > 0 && (d += a[e - 1].length, c.holes.push(d))
                        }
                        return c
                    },
                    a
            } (this),
            ea = {
                NUM_Y_SEGMENTS: 24,
                NUM_X_SEGMENTS: 32,
                quad: function(a, b, c, d, e, f) {
                    this.triangle(a, b, c, d, f),
                        this.triangle(a, d, e, b, f)
                },
                triangle: function(a, b, c, d, e) {
                    var f = ca.normal(b, c, d); [].push.apply(a.vertices, [].concat(b, d, c)),
                        [].push.apply(a.normals, [].concat(f, f, f)),
                        [].push.apply(a.colors, [].concat(e, e, e)),
                        a.texCoords.push(0, 0, 0, 0, 0, 0)
                },
                circle: function(a, b, c, d, e) {
                    d = d || 0;
                    for (var f, g, h = 0; h < this.NUM_X_SEGMENTS; h++) f = h / this.NUM_X_SEGMENTS,
                        g = (h + 1) / this.NUM_X_SEGMENTS,
                        this.triangle(a, [b[0] + c * Math.sin(f * Math.PI * 2), b[1] + c * Math.cos(f * Math.PI * 2), d], [b[0], b[1], d], [b[0] + c * Math.sin(g * Math.PI * 2), b[1] + c * Math.cos(g * Math.PI * 2), d], e)
                },
                polygon: function(a, b, c, d) {
                    c = c || 0;
                    var e, f, g, h, i, j = [],
                        k = [],
                        l = 0;
                    for (e = 0, f = b.length; f > e; e++) {
                        for (h = b[e], g = 0; g < h.length; g++) i = h[g],
                            j.push(i[0], i[1], c + (i[2] || 0));
                        e && (l += b[e - 1].length, k.push(l))
                    }
                    var m, n, o, p = da(j, k, 3);
                    for (e = 0, f = p.length - 2; f > e; e += 3) m = 3 * p[e],
                        n = 3 * p[e + 1],
                        o = 3 * p[e + 2],
                        this.triangle(a, [j[m], j[m + 1], j[m + 2]], [j[n], j[n + 1], j[n + 2]], [j[o], j[o + 1], j[o + 2]], d)
                },
                cube: function(a, b, c, d, e, f, g, h) {
                    e = e || 0,
                        f = f || 0,
                        g = g || 0;
                    var i = [e, f, g],
                        j = [e + b, f, g],
                        k = [e + b, f + c, g],
                        l = [e, f + c, g],
                        m = [e, f, g + d],
                        n = [e + b, f, g + d],
                        o = [e + b, f + c, g + d],
                        p = [e, f + c, g + d];
                    this.quad(a, j, i, l, k, h),
                        this.quad(a, m, n, o, p, h),
                        this.quad(a, i, j, n, m, h),
                        this.quad(a, j, k, o, n, h),
                        this.quad(a, k, l, p, o, h),
                        this.quad(a, l, i, m, p, h)
                },
                cylinder: function(a, b, c, d, e, f, g) {
                    f = f || 0;
                    for (var h, i, j, k, l, m, n = this.NUM_X_SEGMENTS,
                             o = 2 * Math.PI,
                             p = 0; n > p; p++) h = p / n * o,
                        i = (p + 1) / n * o,
                        j = Math.sin(h),
                        k = Math.cos(h),
                        l = Math.sin(i),
                        m = Math.cos(i),
                        this.triangle(a, [b[0] + c * j, b[1] + c * k, f], [b[0] + d * l, b[1] + d * m, f + e], [b[0] + c * l, b[1] + c * m, f], g),
                    0 !== d && this.triangle(a, [b[0] + d * j, b[1] + d * k, f + e], [b[0] + d * l, b[1] + d * m, f + e], [b[0] + c * j, b[1] + c * k, f], g)
                },
                dome: function(a, b, c, d, e, f) {
                    e = e || 0;
                    for (var g, h, i, j, k, l, m, n, o, p, q = this.NUM_Y_SEGMENTS / 2,
                             r = Math.PI / 2,
                             s = 0; q > s; s++) g = s / q * r - r,
                        h = (s + 1) / q * r - r,
                        i = Math.sin(g),
                        j = Math.cos(g),
                        k = Math.sin(h),
                        l = Math.cos(h),
                        m = j * c,
                        n = l * c,
                        o = (k - i) * d,
                        p = e - k * d,
                        this.cylinder(a, b, n, m, o, p, f)
                },
                sphere: function(a, b, c, d, e, f) {
                    e = e || 0;
                    var g = 0;
                    return g += this.circle(a, b, c, e, f),
                        g += this.cylinder(a, b, c, c, d, e, f),
                        g += this.circle(a, b, c, e + d, f)
                },
                pyramid: function(a, b, c, d, e, f) {
                    e = e || 0,
                        b = b[0];
                    for (var g = 0,
                             h = b.length - 1; h > g; g++) this.triangle(a, [b[g][0], b[g][1], e], [b[g + 1][0], b[g + 1][1], e], [c[0], c[1], e + d], f)
                },
                extrusion: function(a, b, c, d, e, f) {
                    d = d || 0;
                    var g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v = f[2] * c,
                        w = f[3] * c;
                    for (r = 0, s = b.length; s > r; r++) for (g = b[r], t = 0, u = g.length - 1; u > t; t++) h = g[t],
                        i = g[t + 1],
                        j = ba.len(ba.sub(h, i)),
                        k = [h[0], h[1], d],
                        l = [i[0], i[1], d],
                        m = [i[0], i[1], d + c],
                        n = [h[0], h[1], d + c],
                        o = ca.normal(k, l, m),
                        [].push.apply(a.vertices, [].concat(k, m, l, k, n, m)),
                        [].push.apply(a.normals, [].concat(o, o, o, o, o, o)),
                        [].push.apply(a.colors, [].concat(e, e, e, e, e, e)),
                        p = f[0] * j << 0,
                        q = f[1] * j << 0,
                        a.texCoords.push(p, w, q, v, q, w, p, w, p, v, q, v)
                }
            },
            fa = function() {
                function a(a, c, d, f, g) {
                    for (var h, i = [r * Math.cos(d[1] / 180 * Math.PI), r], k = e(c.geometry), l = 0, m = k.length; m > l; l++) h = b(k[l], d, i),
                        j(a, c.properties, h, f, g)
                }
                function b(a, b, d) {
                    return a.map(function(a, e) {
                        return 0 === e !== c(a) && a.reverse(),
                            a.map(function(a) {
                                return [(a[0] - b[0]) * d[0], -(a[1] - b[1]) * d[1]]
                            })
                    })
                }
                function c(a) {
                    return 0 < a.reduce(function(a, b, c, d) {
                                return a + (c < d.length - 1 ? (d[c + 1][0] - b[0]) * (d[c + 1][1] + b[1]) : 0)
                            },
                            0)
                }
                function d(a) {
                    for (var b = 1 / 0,
                             c = 1 / 0,
                             d = -(1 / 0), e = -(1 / 0), f = 0; f < a.length; f++) b = Math.min(b, a[f][0]),
                        c = Math.min(c, a[f][1]),
                        d = Math.max(d, a[f][0]),
                        e = Math.max(e, a[f][1]);
                    return {
                        minX: b,
                        minY: c,
                        maxX: d,
                        maxY: e
                    }
                }
                function e(a) {
                    switch (a.type) {
                        case "MultiPolygon":
                            return a.coordinates;
                        case "Polygon":
                            return [a.coordinates];
                        default:
                            return []
                    }
                }
                function f(a) {
                    return "string" != typeof a ? null: (a = a.toLowerCase(), "#" === a[0] ? a: o[p[a] || a] || null)
                }
                function g(a, b) {
                    b = b || 0;
                    var c = new Z(a).toArray();
                    return void 0 === c && (c = m),
                        [c[0] + b, c[1] + b, c[2] + b]
                }
                function j(a, b, c, e, j) {
                    var l = k(b, d(c[0])),
                        m = g(e || b.wallColor || b.color || f(b.material), j),
                        n = g(e || b.roofColor || f(b.roofMaterial), j);
                    switch (b.shape) {
                        case "cone":
                            return void ea.cylinder(a, l.center, l.radius, 0, l.wallHeight, l.wallZ, m);
                        case "dome":
                            return void ea.dome(a, l.center, l.radius, l.wallHeight, l.wallZ, m);
                        case "pyramid":
                            return void ea.pyramid(a, c, l.center, l.wallHeight, l.wallZ, m);
                        case "sphere":
                            return void ea.sphere(a, l.center, l.radius, l.wallHeight, l.wallZ, m)
                    }
                    switch (b.roofShape) {
                        case "cone":
                            ea.cylinder(a, l.center, l.radius, 0, l.roofHeight, l.roofZ, n);
                            break;
                        case "dome":
                            ea.dome(a, l.center, l.radius, l.roofHeight, l.roofZ, n);
                            break;
                        case "pyramid":
                            "cylinder" === b.shape ? ea.cylinder(a, l.center, l.radius, 0, l.roofHeight, l.roofZ, n) : ea.pyramid(a, c, l.center, l.roofHeight, l.roofZ, n);
                            break;
                        case "skillion":
                            i(a, b, c, l, m, n);
                            break;
                        case "gabled":
                            h(a, b, c, 0, l, m, n);
                            break;
                        case "hipped":
                            h(a, b, c, 1 / 3, l, m, n);
                            break;
                        case "half-hipped":
                            h(a, b, c, 0, l, m, n);
                            break;
                        case "gambrel":
                            h(a, b, c, 0, l, m, n);
                            break;
                        case "mansard":
                            h(a, b, c, 0, l, m, n);
                            break;
                        case "onion":
                            for (var o, p, r = [{
                                rScale: 1,
                                hScale: 0
                            },
                                {
                                    rScale: .8,
                                    hScale: .15
                                },
                                {
                                    rScale: 1,
                                    hScale: .5
                                },
                                {
                                    rScale: .8,
                                    hScale: .7
                                },
                                {
                                    rScale: .4,
                                    hScale: .8
                                },
                                {
                                    rScale: 0,
                                    hScale: 1
                                }], s = 0, t = r.length - 1; t > s; s++) o = l.roofHeight * r[s].hScale,
                                p = l.roofHeight * r[s + 1].hScale,
                                ea.cylinder(a, l.center, l.radius * r[s].rScale, l.radius * r[s + 1].rScale, p - o, l.roofZ + o, n);
                            break;
                        default:
                            "cylinder" === b.shape ? ea.circle(a, l.center, l.radius, l.roofZ, n) : ea.polygon(a, c, l.roofZ, n)
                    }
                    switch (b.roofShape) {
                        case "none":
                            return;
                        case "cylinder":
                            return void ea.cylinder(a, l.center, l.radius, l.radius, l.wallHeight, l.wallZ, m);
                        default:
                            var u = .2,
                                v = .4;
                            "glass" !== b.material && (u = 0, v = 0, b.levels && (v = parseFloat(b.levels) - parseFloat(b.minLevel || 0) << 0)),
                                ea.extrusion(a, c, l.wallHeight, l.wallZ, m, [0, q, u / l.wallHeight, v / l.wallHeight])
                    }
                }
                function k(a, b) {
                    var c = {};
                    switch (c.center = [b.minX + (b.maxX - b.minX) / 2, b.minY + (b.maxY - b.minY) / 2], c.radius = (b.maxX - b.minX) / 2, c.roofHeight = a.roofHeight || (a.roofLevels ? a.roofLevels * n: 0), a.roofShape) {
                        case "cone":
                        case "pyramid":
                        case "dome":
                        case "onion":
                            c.roofHeight = c.roofHeight || 1 * c.radius;
                            break;
                        case "gabled":
                        case "hipped":
                        case "half-hipped":
                        case "skillion":
                        case "gambrel":
                        case "mansard":
                        case "round":
                            c.roofHeight = c.roofHeight || 1 * n;
                            break;
                        case "flat":
                            c.roofHeight = 0;
                            break;
                        default:
                            c.roofHeight = 0
                    }
                    var d;
                    if (c.wallZ = a.minHeight || (a.minLevel ? a.minLevel * n: 0), void 0 !== a.height) d = a.height,
                        c.roofHeight = Math.min(c.roofHeight, d),
                        c.roofZ = d - c.roofHeight,
                        c.wallHeight = d - c.roofHeight - c.wallZ;
                    else if (void 0 !== a.levels) d = a.levels * n,
                        c.roofZ = d,
                        c.wallHeight = d - c.wallZ;
                    else {
                        switch (a.shape) {
                            case "cone":
                            case "dome":
                            case "pyramid":
                                d = 2 * c.radius,
                                    c.roofHeight = 0;
                                break;
                            case "sphere":
                                d = 4 * c.radius,
                                    c.roofHeight = 0;
                                break;
                            default:
                                d = l
                        }
                        c.roofZ = d,
                            c.wallHeight = d - c.wallZ
                    }
                    return c
                }
                var l = 10,
                    m = new Z("rgb(220, 210, 200)").toArray(),
                    n = 3,
                    o = {
                        brick: "#cc7755",
                        bronze: "#ffeecc",
                        canvas: "#fff8f0",
                        concrete: "#999999",
                        copper: "#a0e0d0",
                        glass: "#e8f8f8",
                        gold: "#ffcc00",
                        plants: "#009933",
                        metal: "#aaaaaa",
                        panel: "#fff8f0",
                        plaster: "#999999",
                        roof_tiles: "#f08060",
                        silver: "#cccccc",
                        slate: "#666666",
                        stone: "#996666",
                        tar_paper: "#333333",
                        wood: "#deb887"
                    },
                    p = {
                        asphalt: "tar_paper",
                        bitumen: "tar_paper",
                        block: "stone",
                        bricks: "brick",
                        glas: "glass",
                        glassfront: "glass",
                        grass: "plants",
                        masonry: "stone",
                        granite: "stone",
                        panels: "panel",
                        paving_stones: "stone",
                        plastered: "plaster",
                        rooftiles: "roof_tiles",
                        roofingfelt: "tar_paper",
                        sandstone: "stone",
                        sheet: "canvas",
                        sheets: "canvas",
                        shingle: "tar_paper",
                        shingles: "tar_paper",
                        slates: "slate",
                        steel: "metal",
                        tar: "tar_paper",
                        tent: "canvas",
                        thatch: "plants",
                        tile: "roof_tiles",
                        tiles: "roof_tiles"
                    },
                    q = .5,
                    r = 6378137 * Math.PI / 180;
                return a
            } ();
        if (void 0 === ga) {
            var ga = function(a, b) {
                b = b || {
                        bubbles: !1,
                        cancelable: !1,
                        detail: void 0
                    };
                var c = ta.createEvent("CustomEvent");
                return c.initCustomEvent(a, b.bubbles, b.cancelable, b.detail),
                    c
            };
            ga.prototype = window.Event.prototype
        }
        var ha, ia, ja = function(a) {
            if (ha = this, ha.options = a || {},
                    ha.options.style) {
                var b = ha.options.style; (b.color || b.wallColor) && (pa = new Z(b.color || b.wallColor).toArray())
            }
            ha.baseURL = ha.options.baseURL || ".",
                Ga.backgroundColor = new Z(ha.options.backgroundColor || sa).toArray(),
                Ga.fogColor = new Z(ha.options.fogColor || ra).toArray(),
            ha.options.highlightColor && (qa = new Z(ha.options.highlightColor).toArray()),
                Ga.Buildings.showBackfaces = ha.options.showBackfaces,
                Ga.effects = {};
            for (var c = ha.options.effects || [], d = 0; d < c.length; d++) Ga.effects[c[d]] = !0;
            ha.attribution = ha.options.attribution || ja.ATTRIBUTION,
                ha.minZoom = Math.max(parseFloat(ha.options.minZoom || 14.5), 14.5),
                ha.maxZoom = Math.min(parseFloat(ha.options.maxZoom || 20), 20),
            ha.maxZoom < ha.minZoom && (ha.minZoom = 14.5, ha.maxZoom = 20),
                ha.bounds = ha.options.bounds,
                ha.position = ha.options.position || {
                        latitude: 52.52,
                        longitude: 13.41
                    },
                ha.zoom = ha.options.zoom || ha.minZoom + (ha.maxZoom - ha.minZoom) / 2,
                ha.rotation = ha.options.rotation || 0,
                ha.tilt = ha.options.tilt || 0,
            ha.options.disabled && ha.setDisabled(!0)
        };
        ja.VERSION = "3.2.2",
            ja.ATTRIBUTION = '<a href="https://osmbuildings.org/">? OSM Buildings</a>',
            ja.prototype = {
                appendTo: function(a, b, c) {
                    "string" == typeof a && (a = ta.getElementById(a)),
                        ha.container = ta.createElement("DIV"),
                        ha.container.className = "osmb",
                        a.appendChild(ha.container),
                        ha.width = void 0 !== b ? b: a.offsetWidth,
                        ha.height = void 0 !== c ? c: a.offsetHeight;
                    var d = ta.createElement("CANVAS");
                    return d.className = "osmb-viewport",
                        d.width = ha.width,
                        d.height = ha.width,
                        ha.container.appendChild(d),
                        ia = aa.getContext(d),
                        ka.init(d),
                        ha._getStateFromUrl(),
                    ha.options.state && (ha._setStateToUrl(), ha.on("change", ha._setStateToUrl)),
                        ha._attribution = ta.createElement("DIV"),
                        ha._attribution.className = "osmb-attribution",
                        ha.container.appendChild(ha._attribution),
                        ha._updateAttribution(),
                        ha.setDate(new Date),
                        Ga.start(),
                        ha
                },
                remove: function() {},
                on: function(a, b) {
                    return ia.canvas.addEventListener(a, b),
                        ha
                },
                off: function(a, b) {
                    ia.canvas.removeEventListener(a, b)
                },
                emit: function(a, b) {
                    var c = new ga(a, {
                        detail: b
                    });
                    ia.canvas.dispatchEvent(c)
                },
                setStyle: function() {
                    return ha
                },
                setDate: function(a) {
                    return Ha.setDate("string" == typeof a ? new Date(a) : a),
                        ha
                },
                project: function(a, b, c) {
                    var d = wa * Math.cos(ha.position.latitude / 180 * Math.PI),
                        e = [(b - ha.position.longitude) * d, -(a - ha.position.latitude) * wa, c * na],
                        f = B(Ga.viewProjMatrix.data, e);
                    return f = V(T(f, [1, 1, 1]), .5),
                    {
                        x: f[0] * ha.width,
                        y: (1 - f[1]) * ha.height,
                        z: f[2]
                    }
                },
                unproject: function(a, b) {
                    var c = aa.Matrix.invert(Ga.viewProjMatrix.data),
                        d = [a / ha.width, 1 - b / ha.height];
                    d = j(k(d, 2), [ - 1, -1, -1]);
                    var e = C(d[0], d[1], c);
                    return void 0 !== e ? (metersPerDegreeLongitude = wa * Math.cos(ha.position.latitude / 180 * Math.PI), {
                        latitude: ha.position.latitude - e[1] / wa,
                        longitude: ha.position.longitude + e[0] / metersPerDegreeLongitude
                    }) : void 0
                },
                addOBJ: function(a, b, c) {
                    return new Fa.OBJ(a, b, c)
                },
                addGeoJSON: function(a, b) {
                    return new Fa.GeoJSON(a, b)
                },
                addGeoJSONTiles: function(a, b) {
                    return b = b || {},
                        b.fixedZoom = b.fixedZoom || 15,
                        ha.dataGrid = new Ca(a, Ea.Tile, b),
                        ha.dataGrid
                },
                addMapTiles: function(a, b) {
                    return ha.basemapGrid = new Ca(a, Ia.Tile, b),
                        ha.basemapGrid
                },
                highlight: function(a, b) {
                    return Ga.Buildings.highlightId = a ? Ga.Picking.idToColor(a) : null,
                        Ga.Buildings.highlightColor = a && b ? new Z(b).toArray() : qa,
                        ha
                },
                show: function(a, b) {
                    return Da.remove("hidden", a, b),
                        ha
                },
                hide: function(a, b) {
                    return Da.add("hidden", a, b),
                        ha
                },
                getTarget: function(a, b, c) {
                    return Ga.Picking.getTarget(a, b, c),
                        ha
                },
                screenshot: function(a) {
                    return Ga.screenshotCallback = a,
                        ha
                },
                _updateAttribution: function() {
                    var a = [];
                    ha.attribution && a.push(ha.attribution),
                        ha._attribution.innerHTML = a.join(" ¡¤ ")
                },
                _getStateFromUrl: function() {
                    var a = location.search,
                        b = {};
                    a && a.substring(1).replace(/(?:^|&)([^&=]*)=?([^&]*)/g,
                        function(a, c, d) {
                            c && (b[c] = d)
                        }),
                        ha.setPosition(void 0 !== b.lat && void 0 !== b.lon ? {
                            latitude: b.lat,
                            longitude: b.lon
                        }: ha.position),
                        ha.setZoom(void 0 !== b.zoom ? b.zoom: ha.zoom),
                        ha.setRotation(void 0 !== b.rotation ? b.rotation: ha.rotation),
                        ha.setTilt(void 0 !== b.tilt ? b.tilt: ha.tilt)
                },
                _setStateToUrl: function() {
                    history.replaceState && !ha.stateDebounce && (ha.stateDebounce = setTimeout(function() {
                            ha.stateDebounce = null;
                            var a = [];
                            a.push("lat=" + ha.position.latitude.toFixed(6)),
                                a.push("lon=" + ha.position.longitude.toFixed(6)),
                                a.push("zoom=" + ha.zoom.toFixed(1)),
                                a.push("tilt=" + ha.tilt.toFixed(1)),
                                a.push("rotation=" + ha.rotation.toFixed(1)),
                                history.replaceState({},
                                    "", "?" + a.join("&"))
                        },
                        1e3))
                },
                setDisabled: function(a) {
                    return ka.disabled = !!a,
                        ha
                },
                isDisabled: function() {
                    return !! ka.disabled
                },
                getBounds: function() {
                    var a = Ga.getViewQuad(),
                        b = [];
                    for (var c in a) b[c] = H(a[c]);
                    return b
                },
                setZoom: function(a, b) {
                    return a = parseFloat(a),
                        a = Math.max(a, ha.minZoom),
                        a = Math.min(a, ha.maxZoom),
                    ha.zoom !== a && (ha.zoom = a, ha.emit("zoom", {
                        zoom: a
                    }), ha.emit("change")),
                        ha
                },
                getZoom: function() {
                    return ha.zoom
                },
                setPosition: function(a) {
                    var b = parseFloat(a.latitude),
                        c = parseFloat(a.longitude);
                    return isNaN(b) || isNaN(c) ? void 0 : (ha.position = {
                        latitude: s(b, -90, 90),
                        longitude: s(c, -180, 180)
                    },
                        ha.emit("change"), ha)
                },
                getPosition: function() {
                    return ha.position
                },
                setSize: function(a) {
                    return (a.width !== ha.width || a.height !== ha.height) && (ha.width = a.width, ha.height = a.height, ha.emit("resize", {
                        width: ha.width,
                        height: ha.height
                    })),
                        ha
                },
                getSize: function() {
                    return {
                        width: ha.width,
                        height: ha.height
                    }
                },
                setRotation: function(a) {
                    return a = parseFloat(a) % 360,
                    ha.rotation !== a && (ha.rotation = a, ha.emit("rotate", {
                        rotation: a
                    }), ha.emit("change")),
                        ha
                },
                getRotation: function() {
                    return ha.rotation
                },
                setTilt: function(a) {
                    return a = s(parseFloat(a), 0, 45),
                    ha.tilt !== a && (ha.tilt = a, ha.emit("tilt", {
                        tilt: a
                    }), ha.emit("change")),
                        ha
                },
                getTilt: function() {
                    return ha.tilt
                },
                destroy: function() {
                    Ga.destroy(),
                        aa.destroy(),
                        ha.container.innerHTML = ""
                }
            },
            "function" == typeof define ? define([], ja) : "object" == typeof module ? module.exports = ja: window.OSMBuildings = ja;
        var ka = {};
        ka.disabled = !1,
            ka.init = function(a) {
                function b(a) {
                    n(a),
                    ka.disabled || ha.setZoom(ha.zoom + 1, a);
                    var b = l(a, m(a.target));
                    ha.emit("doubleclick", {
                        x: b.x,
                        y: b.y,
                        button: a.button
                    })
                }
                function c(a) {
                    if (n(a), !(a.button > 1)) {
                        z = ha.zoom,
                            A = ha.rotation,
                            B = ha.tilt,
                            u = m(a.target);
                        var b = l(a, u);
                        x = v = b.x,
                            y = w = b.y,
                            C = !0,
                            ha.emit("pointerdown", {
                                x: b.x,
                                y: b.y,
                                button: a.button
                            })
                    }
                }
                function d(a) {
                    var b;
                    C ? (0 !== a.button || a.altKey ? h(a, u) : g(a, u), b = l(a, u), v = b.x, w = b.y) : b = l(a, m(a.target)),
                        ha.emit("pointermove", {
                            x: b.x,
                            y: b.y
                        })
                }
                function e(a) {
                    if (C) {
                        var b = l(a, u);
                        0 !== a.button || a.altKey ? h(a, u) : (Math.abs(b.x - x) > 5 || Math.abs(b.y - y) > 5) && g(a, u),
                            C = !1,
                            ha.emit("pointerup", {
                                x: b.x,
                                y: b.y,
                                button: a.button
                            })
                    }
                }
                function f(a) {
                    n(a);
                    var b = 0;
                    if (a.wheelDeltaY ? b = a.wheelDeltaY: a.wheelDelta ? b = a.wheelDelta: a.detail && (b = -a.detail), !ka.disabled) {
                        var c = .2 * (b > 0 ? 1 : 0 > b ? -1 : 0);
                        ha.setZoom(ha.zoom + c, a)
                    }
                }
                function g(a, b) {
                    if (!ka.disabled) {
                        var c = .86 * Math.pow(2, -ha.zoom),
                            d = 1 / Math.cos(ha.position.latitude / 180 * Math.PI),
                            e = l(a, b),
                            f = e.x - v,
                            g = e.y - w,
                            h = ha.rotation * Math.PI / 180,
                            i = [Math.cos(h), Math.sin(h)],
                            m = [Math.cos(h - Math.PI / 2), Math.sin(h - Math.PI / 2)],
                            n = j(k(i, f), k(m, -g)),
                            o = {
                                longitude: ha.position.longitude - n[0] * c * d,
                                latitude: ha.position.latitude + n[1] * c
                            };
                        ha.setPosition(o),
                            ha.emit("move", o)
                    }
                }
                function h(a, b) {
                    if (!ka.disabled) {
                        var c = l(a, b);
                        A += (c.x - v) * (360 / innerWidth),
                            B -= (c.y - w) * (360 / innerHeight),
                            ha.setRotation(A),
                            ha.setTilt(B)
                    }
                }
                function i(a) {
                    var b = a.touches[0],
                        c = a.touches[1],
                        d = b.clientX - c.clientX,
                        e = b.clientY - c.clientY,
                        f = d * d + e * e,
                        g = Math.atan2(e, d);
                    s({
                        rotation: (g - E) * (180 / Math.PI) % 360,
                        scale: Math.sqrt(f / D)
                    })
                }
                function p(a) {
                    if (C = !0, n(a), 2 === a.touches.length && !("ongesturechange" in window)) {
                        var b = a.touches[0],
                            c = a.touches[1],
                            d = b.clientX - c.clientX,
                            e = b.clientY - c.clientY;
                        D = d * d + e * e,
                            E = Math.atan2(e, d),
                            F = !0
                    }
                    z = ha.zoom,
                        A = ha.rotation,
                        B = ha.tilt,
                    a.touches.length && (a = a.touches[0]),
                        u = m(a.target);
                    var f = l(a, u);
                    x = v = f.x,
                        y = w = f.y,
                        ha.emit("pointerdown", {
                            x: f.x,
                            y: f.y,
                            button: 0
                        })
                }
                function q(a) {
                    if (C) {
                        var b = l(a.touches[0], u);
                        a.touches.length > 1 ? (ha.setTilt(B + (w - b.y) * (360 / innerHeight)), B = ha.tilt, "ongesturechange" in window || i(a)) : (g(a.touches[0], u), ha.emit("pointermove", {
                            x: b.x,
                            y: b.y
                        })),
                            v = b.x,
                            w = b.y
                    }
                }
                function r(a) {
                    if (C) if (F = !1, 0 === a.touches.length) C = !1,
                        ha.emit("pointerup", {
                            x: v,
                            y: w,
                            button: 0
                        });
                    else if (1 === a.touches.length) {
                        var b = l(a.touches[0], u);
                        v = b.x,
                            w = b.y
                    }
                }
                function s(a) {
                    C && (n(a), ka.disabled || (ha.setZoom(z + (a.scale - 1)), ha.setRotation(A - a.rotation)), ha.emit("gesture", a))
                }
                "ontouchstart" in window ? (o(a, "touchstart", p), o(ta, "touchmove", q), o(ta, "touchend", r), o(ta, "gesturechange", s)) : (o(a, "mousedown", c), o(ta, "mousemove", d), o(ta, "mouseup", e), o(a, "dblclick", b), o(a, "mousewheel", f), o(a, "DOMMouseScroll", f));
                var t;
                o(window, "resize",
                    function() {
                        t || (t = setTimeout(function() {
                                t = null,
                                    ha.setSize({
                                        width: a.offsetWidth,
                                        height: a.offsetHeight
                                    })
                            },
                            250))
                    });
                var u, v = 0,
                    w = 0,
                    x = 0,
                    y = 0,
                    z = 0,
                    A = 0,
                    B = 0,
                    C = !1,
                    D = 0,
                    E = 0,
                    F = !1
            };
        var la = {}; !
            function() {
                var a, b = 0;
                la.setBusy = function() {
                    b || (a ? (clearTimeout(a), a = null) : ha.emit("busy")),
                        b++
                },
                    la.setIdle = function() {
                        b && (b--, b || (a = setTimeout(function() {
                                a = null,
                                    ha.emit("idle")
                            },
                            33)))
                    },
                    la.isBusy = function() {
                        return !! b
                    }
            } ();
        var ma = 256,
            na = 1,
            oa = 25,
            pa = new Z("rgb(220, 210, 200)").toArray(),
            qa = new Z("#f08000").toArray(),
            ra = "#e8e0d8",
            sa = "#efe8e0",
            ta = window.document,
            ua = 6378137,
            va = ua * Math.PI * 2,
            wa = va / 360,
            xa = 2e3,
            ya = 100,
            za = 2048,
            Aa = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEAAAABAAQMAAACQp+OdAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3wwCCAUQLpaUSQAAABl0RVh0Q29tbWVudABDcmVhdGVkIHdpdGggR0lNUFeBDhcAAAAGUExURebm5v///zFES9kAAAAcSURBVCjPY/gPBQyUMh4wAAH/KAPCoFaoDnYGAAKtZsamTRFlAAAAAElFTkSuQmCC",
            Ba = {}; !
            function() {
                function a(a, b) {
                    var c = new XMLHttpRequest;
                    return c.onreadystatechange = function() {
                        4 === c.readyState && (!c.status || c.status < 200 || c.status > 299 || b(c))
                    },
                        c.open("GET", a),
                        c.send(null),
                    {
                        abort: function() {
                            c.abort()
                        }
                    }
                }
                Ba.getText = function(b, c) {
                    return a(b,
                        function(a) {
                            void 0 !== a.responseText && c(a.responseText)
                        })
                },
                    Ba.getXML = function(b, c) {
                        return a(b,
                            function(a) {
                                void 0 !== a.responseXML && c(a.responseXML)
                            })
                    },
                    Ba.getJSON = function(b, c) {
                        return a(b,
                            function(a) {
                                if (a.responseText) {
                                    var d;
                                    try {
                                        d = JSON.parse(a.responseText)
                                        for(var i = 0 ; i < d.features.length; i ++){
                                            if(myMaps.length>0){
                                                for(var j = 0 ;j < myMaps.length; j ++){
                                                    if(d.features[i].id == myMaps[j].buildingID){
                                                        d.features[i].properties.color = myMaps[j].wallColor;
                                                    }
                                                    //alert(json.features[0].properties.color);
                                                }
                                            }
                                            else{
                                                console.log("Can not access data.");
                                                break;
                                            }
                                        }
                                    } catch(e) {
                                        console.warn("Could not parse JSON from " + b + "\n" + e.message)
                                    }
                                    c(d)
                                }
                            })
                    },
                    Ba.destroy = function() {}
            } ();
        var Ca = function(a, b, c) {
            this.tiles = {},
                this.buffer = 1,
                this.source = a,
                this.tileClass = b,
                c = c || {},
                this.bounds = c.bounds,
                this.fixedZoom = c.fixedZoom,
                this.tileOptions = {
                    color: c.color,
                    fadeIn: c.fadeIn
                },
                this.minZoom = Math.max(parseFloat(c.minZoom || 14.5), ha.minZoom),
                this.maxZoom = Math.min(parseFloat(c.maxZoom || 20), ha.maxZoom),
            this.maxZoom < this.minZoom && (this.minZoom = 14.5, this.maxZoom = 20),
                ha.on("change", this._onChange = function() {
                    this.update(500)
                }.bind(this)),
                ha.on("resize", this._onResize = this.update.bind(this)),
                this.update()
        };
        Ca.prototype = {
            update: function(a) {
                return ha.zoom < this.minZoom || ha.zoom > this.maxZoom ? void 0 : a ? void(this.debounce || (this.debounce = setTimeout(function() {
                    this.debounce = null,
                        this.loadTiles()
                }.bind(this), a))) : void this.loadTiles()
            },
            getURL: function(a, b, c) {
                var d = "abcd" [(a + b) % 4];
                return p(this.source, {
                    s: d,
                    x: a,
                    y: b,
                    z: c
                })
            },
            getClosestTiles: function(a, b) {
                a.sort(function(a, c) {
                    var d = Math.pow(a[0] + .5 - b[0], 2) + Math.pow(a[1] + .5 - b[1], 2),
                        e = Math.pow(c[0] + .5 - b[0], 2) + Math.pow(c[1] + .5 - b[1], 2);
                    return d > e
                });
                var c, d;
                return a.filter(function(a) {
                    return a[0] === c && a[1] === d ? !1 : (c = a[0], d = a[1], !0)
                })
            },
            mergeTiles: function(a, b, c) {
                var d, e = {},
                    f = {},
                    g = [];
                if (0 === b || b <= this.minZoom) {
                    for (d in a) a[d][2] = b;
                    return a
                }
                for (d in a) {
                    var h = a[d],
                        i = (h[0] << 0) / 2,
                        j = (h[1] << 0) / 2;
                    if (void 0 === e[[i, j]]) {
                        var k = D(i, j, b - 1, Ga.viewProjMatrix);
                        e[[i, j]] = c > k
                    }
                    e[[i, j]] || void 0 === f[[h[0], h[1]]] && (f[[h[0], h[1]]] = !0, g.push([h[0], h[1], b]))
                }
                var l = [];
                for (d in e) if (e[d]) {
                    var m = d.split(",");
                    l.push([parseInt(m[0]), parseInt(m[1]), b - 1])
                }
                return l.length > 0 && (l = this.mergeTiles(l, b - 1, c)),
                    g.concat(l)
            },
            loadTiles: function() {
                var a, b, c, d, e, f = Math.round(this.fixedZoom || ha.zoom),
                    g = [],
                    h = Ga.getViewQuad(Ga.viewProjMatrix.data),
                    i = [J(ha.position.longitude, f), K(ha.position.latitude, f)];
                for (e = 0; 4 > e; e++) h[e] = I(h[e], f);
                var j = x(h);
                for (j = this.fixedZoom ? this.getClosestTiles(j, i) : this.mergeTiles(j, f, .5 * ma * ma), this.visibleTiles = {},
                         e = 0; e < j.length; e++) void 0 === j[e][2] && (j[e][2] = f),
                    this.visibleTiles[j[e]] = !0;
                for (var k in this.visibleTiles) a = k.split(","),
                    b = parseInt(a[0]),
                    c = parseInt(a[1]),
                    d = parseInt(a[2]),
                this.tiles[k] || (this.tiles[k] = new this.tileClass(b, c, d, this.tileOptions, this.tiles), g.push({
                    tile: this.tiles[k],
                    dist: t([b, c], i)
                }));
                for (this.purge(), g.sort(function(a, b) {
                    return a.dist - b.dist
                }), e = 0; e < g.length; e++) a = g[e].tile,
                    a.load(this.getURL(a.x, a.y, a.zoom))
            },
            purge: function() {
                var a, b, c = Math.round(ha.zoom);
                for (var d in this.tiles) a = this.tiles[d],
                this.visibleTiles[d] || (this.fixedZoom ? (this.tiles[d].destroy(), delete this.tiles[d]) : a.zoom === c + 1 && (b = [a.x / 2 << 0, a.y / 2 << 0, c].join(","), this.visibleTiles[b]) || a.zoom === c - 1 && (this.visibleTiles[[2 * a.x, 2 * a.y, c].join(",")] || this.visibleTiles[[2 * a.x + 1, 2 * a.y, c].join(",")] || this.visibleTiles[[2 * a.x, 2 * a.y + 1, c].join(",")] || this.visibleTiles[[2 * a.x + 1, 2 * a.y + 1, c].join(",")]) || delete this.tiles[d])
            },
            destroy: function() {
                ha.off("change", this._onChange),
                    ha.off("resize", this._onResize),
                    clearTimeout(this.debounce);
                for (var a in this.tiles) this.tiles[a].destroy();
                this.tiles = [],
                    this.visibleTiles = {}
            }
        };
        var Da = {
                start: Date.now(),
                now: 0,
                items: [],
                add: function(a, b, c) {
                    c = c || 0;
                    var d = this.items;
                    for (k = 0, l = d.length; l > k; k++) if (d[k].type === a && d[k].selector === b) return;
                    d.push({
                        type: a,
                        selector: b,
                        duration: c
                    });
                    for (var e, f, g, h, i = this.getTime(), j = i + c, k = 0, l = Ea.Index.items.length; l > k; k++) if (e = Ea.Index.items[k], e.applyFilter) {
                        for (g = 0, h = e.items.length; h > g; g++) f = e.items[g],
                        b(f.id, f.data) && (f.filter = [i, j, f.filter ? f.filter[3] : 1, 0]);
                        e.applyFilter()
                    }
                },
                remove: function(a, b, c) {
                    c = c || 0;
                    var d, e;
                    this.items = this.items.filter(function(c) {
                        return c.type !== a || c.selector !== b
                    });
                    var f, g, h, i, j = this.getTime(),
                        k = j + c;
                    for (d = 0, e = Ea.Index.items.length; e > d; d++) if (f = Ea.Index.items[d], f.applyFilter) {
                        for (h = 0, i = f.items.length; i > h; h++) g = f.items[h],
                        b(g.id, g.data) && (g.filter = [j, k, g.filter ? g.filter[3] : 0, 1]);
                        f.applyFilter()
                    }
                },
                apply: function(a) {
                    var b, c, d, e, f, g = this.items;
                    if (a.applyFilter) {
                        for (var h = 0,
                                 i = g.length; i > h; h++) for (b = g[h].type, c = g[h].selector, e = 0, f = a.items.length; f > e; e++) d = a.items[e],
                        c(d.id, d.data) && (d.filter = [0, 0, 0, 0]);
                        a.applyFilter()
                    }
                },
                getTime: function() {
                    return this.now
                },
                nextTick: function() {
                    this.now = Date.now() - this.start
                },
                destroy: function() {
                    this.items = []
                }
            },
            Ea = {
                Index: {
                    items: [],
                    add: function(a) {
                        this.items.push(a)
                    },
                    remove: function(a) {
                        this.items = this.items.filter(function(b) {
                            return b !== a
                        })
                    },
                    destroy: function() {
                        this.items = []
                    }
                }
            };
        Ea.Tile = function(a, b, c, d) {
            this.x = a,
                this.y = b,
                this.zoom = c,
                this.key = [a, b, c].join(","),
                this.options = d
        },
            Ea.Tile.prototype = {
                load: function(a) {
                    this.mesh = new Fa.GeoJSON(a, this.options)
                },
                destroy: function() {
                    this.mesh && this.mesh.destroy()
                }
            };
        var Fa = {};
        Fa.GeoJSON = function() {
            function a(a, b) {
                if (b = b || {},
                        this.forcedId = b.id, this.forcedColor = b.color, this.replace = !!b.replace, this.scale = b.scale || 1, this.rotation = b.rotation || 0, this.elevation = b.elevation || 0, this.shouldFadeIn = "fadeIn" in b ? !!b.fadeIn: !0, this.minZoom = Math.max(parseFloat(b.minZoom || 14.5), ha.minZoom), this.maxZoom = Math.min(parseFloat(b.maxZoom || 20), ha.maxZoom), this.maxZoom < this.minZoom && (this.minZoom = 14.5, this.maxZoom = 20), this.items = [], la.setBusy(), "object" == typeof a) {
                    var c = a;
                    this.setData(c)
                } else this.request = Ba.getJSON(a,
                    function(a) {
                        this.request = null,
                            this.setData(a)
                    }.bind(this))
            }
            var b = 90,
                c = 75;
            return a.prototype = {
                setData: function(a) {
                    if (a && a.features.length) {
                        var d, e, f, g, h, i, j = {
                                vertices: [],
                                texCoords: [],
                                normals: [],
                                colors: []
                            },
                            k = [],
                            l = this.getOrigin(a.features[0].geometry),
                            m = 0,
                            n = a.features.length,
                            o = m + Math.min(n, b);
                        this.position = {
                            latitude: l[1],
                            longitude: l[0]
                        };
                        var p = function() {
                            for (var q = m; o > q; q++) {
                                d = a.features[q],
                                    ha.emit("loadfeature", d),
                                    f = d.properties,
                                    e = this.forcedId || f.relationId || d.id || f.id,
                                    g = j.vertices.length,
                                    fa(j, d, l, this.forcedColor),
                                    h = (j.vertices.length - g) / 3,
                                    i = Ga.Picking.idToColor(e);
                                for (var r = 0; h > r; r++)[].push.apply(k, i);
                                this.items.push({
                                    id: e,
                                    vertexCount: h,
                                    data: f.data
                                })
                            }
                            return o === n ? (this.vertexBuffer = new aa.Buffer(3, new Float32Array(j.vertices)), this.normalBuffer = new aa.Buffer(3, new Float32Array(j.normals)), this.texCoordBuffer = new aa.Buffer(2, new Float32Array(j.texCoords)), this.colorBuffer = new aa.Buffer(3, new Float32Array(j.colors)), this.idBuffer = new aa.Buffer(3, new Float32Array(k)), this.fadeIn(), Da.apply(this), Ea.Index.add(this), this.isReady = !0, void la.setIdle()) : (m = o, o = m + Math.min(n - m, b), void(this.relaxTimer = setTimeout(p, c)))
                        }.bind(this);
                        p()
                    }
                },
                fadeIn: function() {
                    var a, b = [],
                        c = Da.getTime(),
                        d = c;
                    this.shouldFadeIn && (c += 250, d += 750);
                    for (var e = 0,
                             f = this.items.length; f > e; e++) {
                        a = this.items[e],
                            a.filter = [c, d, 0, 1];
                        for (var g = 0,
                                 h = a.vertexCount; h > g; g++) b.push.apply(b, a.filter)
                    }
                    this.filterBuffer = new aa.Buffer(4, new Float32Array(b))
                },
                applyFilter: function() {
                    for (var a, b = [], c = 0, d = this.items.length; d > c; c++) {
                        a = this.items[c];
                        for (var e = 0,
                                 f = a.vertexCount; f > e; e++) b.push.apply(b, a.filter)
                    }
                    this.filterBuffer = new aa.Buffer(4, new Float32Array(b))
                },
                getMatrix: function() {
                    var a = new aa.Matrix;
                    this.elevation && a.translate(0, 0, this.elevation),
                        a.scale(this.scale, this.scale, this.scale * na),
                    this.rotation && a.rotateZ( - this.rotation);
                    var b = this.position.latitude - ha.position.latitude,
                        c = this.position.longitude - ha.position.longitude,
                        d = wa * Math.cos(ha.position.latitude / 180 * Math.PI);
                    return a.translate(c * d, -b * wa, 0),
                        a
                },
                getOrigin: function(a) {
                    var b = a.coordinates;
                    switch (a.type) {
                        case "Point":
                            return b;
                        case "MultiPoint":
                        case "LineString":
                            return b[0];
                        case "MultiLineString":
                        case "Polygon":
                            return b[0][0];
                        case "MultiPolygon":
                            return b[0][0][0]
                    }
                },
                destroy: function() {
                    this.isReady = !1,
                        clearTimeout(this.relaxTimer),
                        Ea.Index.remove(this),
                    this.request && this.request.abort(),
                        this.items = [],
                    this.isReady && (this.vertexBuffer.destroy(), this.normalBuffer.destroy(), this.colorBuffer.destroy(), this.idBuffer.destroy())
                }
            },
                a
        } (),
            Fa.MapPlane = function() {
                function a(a) {
                    a = a || {},
                        this.id = a.id,
                        this.radius = a.radius || 5e3,
                        this.createGlGeometry(),
                        this.minZoom = ha.minZoom,
                        this.maxZoom = ha.maxZoom
                }
                return a.prototype = {
                    createGlGeometry: function() {
                        var a = 50,
                            b = 2 * this.radius / a;
                        this.vertexBuffer = [],
                            this.normalBuffer = [],
                            this.filterBuffer = [];
                        for (var c = [0, 0, 1], d = [].concat(c, c, c, c, c, c), e = [0, 1, 1, 1], f = [].concat(e, e, e, e, e, e), g = 0; a > g; g++) for (var h = 0; a > h; h++) {
                            var i = -this.radius + g * b,
                                j = -this.radius + h * b;
                            this.vertexBuffer.push(i, j, 0, i + b, j + b, 0, i + b, j, 0, i, j, 0, i, j + b, 0, i + b, j + b, 0),
                                this.vertexBuffer.push(i, j, 0, i + b, j, 0, i + b, j + b, 0, i, j, 0, i + b, j + b, 0, i, j + b, 0),
                                [].push.apply(this.normalBuffer, d),
                                [].push.apply(this.normalBuffer, d),
                                [].push.apply(this.filterBuffer, f),
                                [].push.apply(this.filterBuffer, f)
                        }
                        this.vertexBuffer = new aa.Buffer(3, new Float32Array(this.vertexBuffer)),
                            this.normalBuffer = new aa.Buffer(3, new Float32Array(this.normalBuffer)),
                            this.filterBuffer = new aa.Buffer(4, new Float32Array(this.filterBuffer))
                    },
                    getMatrix: function() {
                        var a = new aa.Matrix;
                        return a
                    },
                    destroy: function() {
                        this.vertexBuffer.destroy(),
                            this.normalBuffer.destroy(),
                            this.colorBuffer.destroy(),
                            this.idBuffer.destroy()
                    }
                },
                    a
            } (),
            Fa.DebugQuad = function() {
                function a(a) {
                    a = a || {},
                        this.id = a.id,
                        this.v1 = this.v2 = this.v3 = this.v4 = [!1, !1, !1],
                        this.updateGeometry([0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0]),
                        this.minZoom = ha.minZoom,
                        this.maxZoom = ha.maxZoom
                }
                return a.prototype = {
                    updateGeometry: function(a, b, c, d) {
                        if (! (Y(a, this.v1) && Y(b, this.v2) && Y(c, this.v3) && Y(d, this.v4))) {
                            this.v1 = a,
                                this.v2 = b,
                                this.v3 = c,
                                this.v4 = d,
                            this.vertexBuffer && this.vertexBuffer.destroy();
                            var e = [].concat(a, b, c, a, c, d);
                            this.vertexBuffer = new aa.Buffer(3, new Float32Array(e)),
                            this.normalBuffer && this.normalBuffer.destroy(),
                                this.normalBuffer = new aa.Buffer(3, new Float32Array([0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1]));
                            var f = [1, .5, .25];
                            this.colorBuffer && this.colorBuffer.destroy(),
                                this.colorBuffer = new aa.Buffer(3, new Float32Array([].concat(f, f, f, f, f, f))),
                            this.idBuffer && this.idBuffer.destroy(),
                                this.idBuffer = new aa.Buffer(3, new Float32Array([].concat(f, f, f, f, f, f))),
                                this.texCoordBuffer = new aa.Buffer(2, new Float32Array([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]));
                            var g = [0, 1, 1, 1];
                            this.filterBuffer = new aa.Buffer(4, new Float32Array([].concat(g, g, g, g, g, g)))
                        }
                    },
                    getMatrix: function() {
                        var a = new aa.Matrix;
                        return a
                    },
                    destroy: function() {
                        this.vertexBuffer.destroy(),
                            this.normalBuffer.destroy(),
                            this.colorBuffer.destroy(),
                            this.idBuffer.destroy()
                    }
                },
                    a
            } (),
            Fa.OBJ = function() {
                function a(a) {
                    for (var c, d = a.split(/[\r\n]/g), e = {},
                             f = null, g = 0, h = d.length; h > g; g++) switch (c = d[g].trim().split(/\s+/), c[0]) {
                        case "newmtl":
                            b(e, f),
                                f = {
                                    id: c[1],
                                    color: {}
                                };
                            break;
                        case "Kd":
                            f.color = [parseFloat(c[1]), parseFloat(c[2]), parseFloat(c[3])];
                            break;
                        case "d":
                            f.color[3] = parseFloat(c[1])
                    }
                    return b(e, f),
                        a = null,
                        e
                }
                function b(a, b) {
                    null !== b && (a[b.id] = b.color)
                }
                function c(a, b) {
                    for (var c, e, f, g = [], h = a.split(/[\r\n]/g), i = [], j = [], k = 0, l = h.length; l > k; k++) switch (c = h[k].trim().split(/\s+/), c[0]) {
                        case "g":
                        case "o":
                            d(g, i, e, f, j),
                                e = c[1],
                                j = [];
                            break;
                        case "usemtl":
                            d(g, i, e, f, j),
                            b[c[1]] && (f = b[c[1]]),
                                j = [];
                            break;
                        case "v":
                            g.push([parseFloat(c[1]), parseFloat(c[2]), parseFloat(c[3])]);
                            break;
                        case "f":
                            j.push([parseFloat(c[1]) - 1, parseFloat(c[2]) - 1, parseFloat(c[3]) - 1])
                    }
                    return d(g, i, e, f, j),
                        a = null,
                        i
                }
                function d(a, b, c, d, f) {
                    if (f.length) {
                        var g = e(a, f);
                        b.push({
                            id: c,
                            color: d,
                            vertices: g.vertices,
                            normals: g.normals,
                            texCoords: g.texCoords
                        })
                    }
                }
                function e(a, b) {
                    for (var c, d, e, f, g = {
                            vertices: [],
                            normals: [],
                            texCoords: []
                        },
                             h = 0, i = b.length; i > h; h++) c = a[b[h][0]],
                        d = a[b[h][1]],
                        e = a[b[h][2]],
                        f = y(c, d, e),
                        g.vertices.push(c[0], c[2], c[1], d[0], d[2], d[1], e[0], e[2], e[1]),
                        g.normals.push(f[0], f[1], f[2], f[0], f[1], f[2], f[0], f[1], f[2]),
                        g.texCoords.push(0, 0, 0, 0, 0, 0);
                    return g
                }
                function f(b, c, d) {
                    d = d || {},
                        this.forcedId = d.id,
                    d.color && (this.forcedColor = new Z(d.color).toArray()),
                        this.replace = !!d.replace,
                        this.scale = d.scale || 1,
                        this.rotation = d.rotation || 0,
                        this.elevation = d.elevation || 0,
                        this.position = c,
                        this.shouldFadeIn = "fadeIn" in d ? !!d.fadeIn: !0,
                        this.minZoom = Math.max(parseFloat(d.minZoom || 14.5), ha.minZoom),
                        this.maxZoom = Math.min(parseFloat(d.maxZoom || 20), ha.maxZoom),
                    this.maxZoom < this.minZoom && (this.minZoom = 14.5, this.maxZoom = 20),
                        this.data = {
                            colors: [],
                            ids: [],
                            vertices: [],
                            normals: [],
                            texCoords: []
                        },
                        la.setBusy(),
                        this.request = Ba.getText(b,
                            function(c) {
                                this.request = null;
                                var d; (d = c.match(/^mtllib\s+(.*)$/m)) ? this.request = Ba.getText(b.replace(/[^\/]+$/, "") + d[1],
                                    function(b) {
                                        this.request = null,
                                            this.onLoad(c, a(b))
                                    }.bind(this)) : this.onLoad(c, null)
                            }.bind(this))
                }
                return f.prototype = {
                    onLoad: function(a, b) {
                        this.items = [],
                            this.addItems(c(a, b)),
                            this.onReady()
                    },
                    addItems: function(a) {
                        for (var b, c, d, e, f, g, h, i = 0,
                                 j = a.length; j > i; i++) {
                            for (b = a[i], ha.emit("loadfeature", b), [].push.apply(this.data.vertices, b.vertices), [].push.apply(this.data.normals, b.normals), [].push.apply(this.data.texCoords, b.texCoords), g = this.forcedId || b.id, d = Ga.Picking.idToColor(g), h = (g / 2 % 2 ? -1 : 1) * (g % 2 ? .03 : .06), c = this.forcedColor || b.color || pa, e = 0, f = b.vertices.length - 2; f > e; e += 3)[].push.apply(this.data.colors, U(c, h)),
                                [].push.apply(this.data.ids, d);
                            this.items.push({
                                id: g,
                                vertexCount: b.vertices.length / 3,
                                data: b.data
                            })
                        }
                    },
                    fadeIn: function() {
                        var a, b = [],
                            c = Da.getTime(),
                            d = c;
                        this.shouldFadeIn && (c += 250, d += 750);
                        for (var e = 0,
                                 f = this.items.length; f > e; e++) {
                            a = this.items[e],
                                a.filter = [c, d, 0, 1];
                            for (var g = 0,
                                     h = a.vertexCount; h > g; g++) b.push.apply(b, a.filter)
                        }
                        this.filterBuffer = new aa.Buffer(4, new Float32Array(b))
                    },
                    applyFilter: function() {
                        for (var a, b = [], c = 0, d = this.items.length; d > c; c++) {
                            a = this.items[c];
                            for (var e = 0,
                                     f = a.vertexCount; f > e; e++) b.push.apply(b, a.filter)
                        }
                        this.filterBuffer = new aa.Buffer(4, new Float32Array(b))
                    },
                    onReady: function() {
                        this.vertexBuffer = new aa.Buffer(3, new Float32Array(this.data.vertices)),
                            this.normalBuffer = new aa.Buffer(3, new Float32Array(this.data.normals)),
                            this.texCoordBuffer = new aa.Buffer(2, new Float32Array(this.data.texCoords)),
                            this.colorBuffer = new aa.Buffer(3, new Float32Array(this.data.colors)),
                            this.idBuffer = new aa.Buffer(3, new Float32Array(this.data.ids)),
                            this.fadeIn(),
                            this.data = null,
                            Da.apply(this),
                            Ea.Index.add(this),
                            this.isReady = !0,
                            la.setIdle()
                    },
                    getMatrix: function() {
                        var a = new aa.Matrix;
                        this.elevation && a.translate(0, 0, this.elevation),
                            a.scale(this.scale, this.scale, this.scale),
                        this.rotation && a.rotateZ( - this.rotation);
                        var b = wa * Math.cos(ha.position.latitude / 180 * Math.PI),
                            c = this.position.latitude - ha.position.latitude,
                            d = this.position.longitude - ha.position.longitude;
                        return a.translate(d * b, -c * wa, 0),
                            a
                    },
                    destroy: function() {
                        Ea.Index.remove(this),
                        this.request && this.request.abort(),
                            this.items = [],
                        this.isReady && (this.vertexBuffer.destroy(), this.normalBuffer.destroy(), this.colorBuffer.destroy(), this.idBuffer.destroy())
                    }
                },
                    f
            } ();
        var Ga = {
            getViewQuad: function() {
                return z(this.viewProjMatrix.data, this.fogDistance + this.fogBlurDistance, this.viewDirOnMap)
            },
            start: function() {
                ia.depthTextureExtension || (console.log('[WARN] effects "shadows" and "outlines" disabled in OSMBuildings, because your GPU does not support WEBGL_depth_texture'), delete Ga.effects.shadows, delete Ga.effects.outlines),
                    ha.on("change", this._onChange = this.onChange.bind(this)),
                    ha.on("resize", this._onResize = this.onResize.bind(this)),
                    this.onResize(),
                    ia.cullFace(ia.BACK),
                    ia.enable(ia.CULL_FACE),
                    ia.enable(ia.DEPTH_TEST),
                    Ga.Picking.init(),
                    Ga.sky = new Ga.SkyWall,
                    Ga.Buildings.init(),
                    Ga.Basemap.init(),
                    Ga.Overlay.init(),
                    Ga.AmbientMap.init(),
                    Ga.OutlineMap.init(),
                    Ga.blurredAmbientMap = new Ga.Blur,
                    Ga.blurredOutlineMap = new Ga.Blur,
                    Ga.MapShadows.init(),
                (Ga.effects.shadows || Ga.effects.outlines) && (Ga.cameraGBuffer = new Ga.DepthFogNormalMap),
                Ga.effects.shadows && (Ga.sunGBuffer = new Ga.DepthFogNormalMap, Ga.sunGBuffer.framebufferSize = [za, za]),
                    requestAnimationFrame(this.renderFrame.bind(this))
            },
            renderFrame: function() {
                if (Da.nextTick(), requestAnimationFrame(this.renderFrame.bind(this)), this.onChange(), ia.clearColor(this.fogColor[0], this.fogColor[1], this.fogColor[2], 0), ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT), !(ha.zoom < ha.minZoom || ha.zoom > ha.maxZoom)) {
                    var a = this.getViewQuad();
                    Ha.updateView(a),
                        Ga.sky.updateGeometry(a);
                    var b = [ha.width, ha.height];
                    Ga.effects.shadows ? (Ga.cameraGBuffer.render(this.viewMatrix, this.projMatrix, b, !0), Ga.sunGBuffer.render(Ha.viewMatrix, Ha.projMatrix), Ga.AmbientMap.render(Ga.cameraGBuffer.getDepthTexture(), Ga.cameraGBuffer.getFogNormalTexture(), b, 2), Ga.blurredAmbientMap.render(Ga.AmbientMap.framebuffer.renderTexture, b), Ga.Buildings.render(Ga.sunGBuffer.framebuffer, .5), Ga.Basemap.render(), Ga.effects.outlines && (Ga.Picking.render(b), Ga.OutlineMap.render(Ga.cameraGBuffer.getDepthTexture(), Ga.cameraGBuffer.getFogNormalTexture(), Ga.Picking.framebuffer.renderTexture, b, 1), Ga.blurredOutlineMap.render(Ga.OutlineMap.framebuffer.renderTexture, b)), ia.enable(ia.BLEND), ia.blendFuncSeparate(ia.ZERO, ia.SRC_COLOR, ia.ZERO, ia.ONE), Ga.effects.outlines && Ga.Overlay.render(Ga.blurredOutlineMap.framebuffer.renderTexture, b), Ga.MapShadows.render(Ha, Ga.sunGBuffer.framebuffer, .5), Ga.Overlay.render(Ga.blurredAmbientMap.framebuffer.renderTexture, b), ia.blendFuncSeparate(ia.ONE_MINUS_DST_ALPHA, ia.DST_ALPHA, ia.ONE, ia.ONE), ia.disable(ia.DEPTH_TEST), Ga.sky.render(), ia.enable(ia.DEPTH_TEST), ia.disable(ia.BLEND)) : (Ga.Buildings.render(), Ga.Basemap.render(), Ga.effects.outlines && (Ga.cameraGBuffer.render(this.viewMatrix, this.projMatrix, b, !0), Ga.Picking.render(b), Ga.OutlineMap.render(Ga.cameraGBuffer.getDepthTexture(), Ga.cameraGBuffer.getFogNormalTexture(), Ga.Picking.framebuffer.renderTexture, b, 1), Ga.blurredOutlineMap.render(Ga.OutlineMap.framebuffer.renderTexture, b)), ia.enable(ia.BLEND), Ga.effects.outlines && (ia.blendFuncSeparate(ia.ZERO, ia.SRC_COLOR, ia.ZERO, ia.ONE), Ga.Overlay.render(Ga.blurredOutlineMap.framebuffer.renderTexture, b)), ia.blendFuncSeparate(ia.ONE_MINUS_DST_ALPHA, ia.DST_ALPHA, ia.ONE, ia.ONE), ia.disable(ia.DEPTH_TEST), Ga.sky.render(), ia.disable(ia.BLEND), ia.enable(ia.DEPTH_TEST)),
                    this.screenshotCallback && (this.screenshotCallback(ia.canvas.toDataURL()), this.screenshotCallback = null)
                }
            },
            stop: function() {
                clearInterval(this.loop)
            },
            onChange: function() {
                var a = 1.3567 * Math.pow(2, ha.zoom - 17),
                    b = ha.width,
                    c = ha.height,
                    d = 1024,
                    e = 45;
                ia.viewport(0, 0, b, c),
                    this.viewMatrix = (new aa.Matrix).rotateZ(ha.rotation).rotateX(ha.tilt).translate(0, 8 / a, 0).translate(0, 0, -1220 / a),
                    this.viewDirOnMap = [Math.sin(ha.rotation / 180 * Math.PI), -Math.cos(ha.rotation / 180 * Math.PI)];
                var f = d / (2 * Math.tan(e / 2 / 180 * Math.PI)),
                    g = 2 * Math.atan(c / 2 / f) / Math.PI * 180;
                if (this.projMatrix = (new aa.Matrix).translate(0, -c / (2 * a), 0).scale(1, -1, 1).multiply(new aa.Matrix.Perspective(g, b / c, 1, 7500)).translate(0, -1, 0), this.viewProjMatrix = new aa.Matrix(aa.Matrix.multiply(this.viewMatrix, this.projMatrix)), this.lowerLeftOnMap = C( - 1, -1, aa.Matrix.invert(this.viewProjMatrix.data)), void 0 !== this.lowerLeftOnMap) {
                    var h = N(this.lowerLeftOnMap);
                    this.fogDistance = Math.max(3e3, h),
                        this.fogBlurDistance = 500
                }
            },
            onResize: function() {
                ia.canvas.width = ha.width,
                    ia.canvas.height = ha.height,
                    this.onChange()
            },
            destroy: function() {
                ha.off("change", this._onChange),
                    ha.off("resize", this._onResize),
                    this.stop(),
                    Ga.Picking.destroy(),
                    Ga.sky.destroy(),
                    Ga.Buildings.destroy(),
                    Ga.Basemap.destroy(),
                Ga.cameraGBuffer && Ga.cameraGBuffer.destroy(),
                Ga.sunGBuffer && Ga.sunGBuffer.destroy(),
                    Ga.AmbientMap.destroy(),
                    Ga.blurredAmbientMap.destroy(),
                    Ga.blurredOutlineMap.destroy()
            }
        };
        Ga.Picking = {
            idMapping: [null],
            viewportSize: 512,
            init: function() {
                this.shader = new aa.Shader({
                    vertexShader: _.picking.vertex,
                    fragmentShader: _.picking.fragment,
                    shaderName: "picking shader",
                    attributes: ["aPosition", "aId", "aFilter"],
                    uniforms: ["uModelMatrix", "uMatrix", "uFogRadius", "uTime"]
                }),
                    this.framebuffer = new aa.Framebuffer(this.viewportSize, this.viewportSize)
            },
            render: function(a) {
                var b = this.shader,
                    c = this.framebuffer;
                c.setSize(a[0], a[1]),
                    b.enable(),
                    c.enable(),
                    ia.viewport(0, 0, a[0], a[1]),
                    ia.clearColor(0, 0, 0, 1),
                    ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT),
                    b.setUniforms([["uFogRadius", "1f", Ga.fogDistance], ["uTime", "1f", Da.getTime()]]);
                for (var d, e, f = Ea.Index.items,
                         g = 0,
                         h = f.length; h > g; g++) d = f[g],
                ha.zoom < d.minZoom || ha.zoom > d.maxZoom || (e = d.getMatrix()) && (b.setUniformMatrices([["uModelMatrix", "4fv", e.data], ["uMatrix", "4fv", aa.Matrix.multiply(e, Ga.viewProjMatrix)]]), b.bindBuffer(d.vertexBuffer, "aPosition"), b.bindBuffer(d.idBuffer, "aId"), b.bindBuffer(d.filterBuffer, "aFilter"), ia.drawArrays(ia.TRIANGLES, 0, d.vertexBuffer.numItems));
                this.shader.disable(),
                    this.framebuffer.disable(),
                    ia.viewport(0, 0, ha.width, ha.height)
            },
            getTarget: function(a, b, c) {
                requestAnimationFrame(function() {
                    this.render([this.viewportSize, this.viewportSize]),
                        a = a / ha.width * this.viewportSize << 0,
                        b = b / ha.height * this.viewportSize << 0,
                        this.framebuffer.enable();
                    var d = this.framebuffer.getPixel(a, this.viewportSize - 1 - b);
                    if (this.framebuffer.disable(), void 0 === d) return void c(void 0);
                    var e = d[0] | d[1] << 8 | d[2] << 16;
                    c(this.idMapping[e])
                }.bind(this))
            },
            idToColor: function(a) {
                var b = this.idMapping.indexOf(a);
                return - 1 === b && (this.idMapping.push(a), b = this.idMapping.length - 1),
                    [(255 & b) / 255, (b >> 8 & 255) / 255, (b >> 16 & 255) / 255]
            },
            destroy: function() {}
        };
        var Ha = {
            setDate: function(a) {
                var b = $(a, ha.position.latitude, ha.position.longitude);
                this.direction = [ - Math.sin(b.azimuth) * Math.cos(b.altitude), Math.cos(b.azimuth) * Math.cos(b.altitude), Math.sin(b.altitude)];
                var c = b.azimuth / (Math.PI / 180),
                    d = 90 - b.altitude / (Math.PI / 180);
                this.viewMatrix = (new aa.Matrix).rotateZ(c).rotateX(d).translate(0, 0, -5e3).scale(1, -1, 1)
            },
            updateView: function(a) {
                this.projMatrix = A(r(a, 0).concat(r(a, ya)), this.viewMatrix, 1e3, 7500),
                    this.viewProjMatrix = new aa.Matrix(aa.Matrix.multiply(this.viewMatrix, this.projMatrix))
            }
        };
        Ga.SkyWall = function() {
            this.v1 = this.v2 = this.v3 = this.v4 = [!1, !1, !1],
                this.updateGeometry([[0, 0, 0], [0, 0, 0], [0, 0, 0], [0, 0, 0]]),
                this.shader = new aa.Shader({
                    vertexShader: _.skywall.vertex,
                    fragmentShader: _.skywall.fragment,
                    shaderName: "sky wall shader",
                    attributes: ["aPosition", "aTexCoord"],
                    uniforms: ["uAbsoluteHeight", "uMatrix", "uTexIndex", "uFogColor"]
                }),
                this.floorShader = new aa.Shader({
                    vertexShader: _.flatColor.vertex,
                    fragmentShader: _.flatColor.fragment,
                    attributes: ["aPosition"],
                    uniforms: ["uColor", "uMatrix"]
                }),
                la.setBusy();
            var a = ha.baseURL + "/skydome.jpg";
            this.texture = (new aa.texture.Image).load(a,
                function(a) {
                    la.setIdle(),
                    a && (this.isReady = !0)
                }.bind(this))
        },
            Ga.SkyWall.prototype.updateGeometry = function(a) {
                var b = [a[3][0], a[3][1], 0],
                    c = [a[2][0], a[2][1], 0],
                    d = [a[2][0], a[2][1], xa],
                    e = [a[3][0], a[3][1], xa];
                if (! (Y(b, this.v1) && Y(c, this.v2) && Y(d, this.v3) && Y(e, this.v4))) {
                    this.v1 = b,
                        this.v2 = c,
                        this.v3 = d,
                        this.v4 = e,
                    this.vertexBuffer && this.vertexBuffer.destroy();
                    var f = [].concat(b, c, d, b, d, e);
                    this.vertexBuffer = new aa.Buffer(3, new Float32Array(f)),
                    this.texCoordBuffer && this.texCoordBuffer.destroy();
                    var g = aa.Matrix.invert(Ga.viewProjMatrix.data),
                        h = C(0, -1, g),
                        i = Q(P(b, h)),
                        j = Q(P(c, h)),
                        k = Math.atan2(i[1], i[0]) / (2 * Math.PI),
                        l = Math.atan2(j[1], j[0]) / (2 * Math.PI);
                    k > l && (l += 1);
                    var m = k,
                        n = l;
                    this.texCoordBuffer = new aa.Buffer(2, new Float32Array([m, 1, n, 1, n, 0, m, 1, n, 0, m, 0])),
                        b = [a[0][0], a[0][1], 1],
                        c = [a[1][0], a[1][1], 1],
                        d = [a[2][0], a[2][1], 1],
                        e = [a[3][0], a[3][1], 1],
                    this.floorVertexBuffer && this.floorVertexBuffer.destroy(),
                        this.floorVertexBuffer = new aa.Buffer(3, new Float32Array([].concat(b, c, d, e)))
                }
            },
            Ga.SkyWall.prototype.render = function() {
                if (this.isReady) {
                    var a = Ga.fogColor,
                        b = this.shader;
                    b.enable(),
                        b.setUniforms([["uFogColor", "3fv", a], ["uAbsoluteHeight", "1f", 10 * xa]]),
                        b.setUniformMatrix("uMatrix", "4fv", Ga.viewProjMatrix.data),
                        b.bindBuffer(this.vertexBuffer, "aPosition"),
                        b.bindBuffer(this.texCoordBuffer, "aTexCoord"),
                        b.bindTexture("uTexIndex", 0, this.texture),
                        ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                        b.disable(),
                        this.floorShader.enable(),
                        this.floorShader.setUniform("uColor", "4fv", a.concat([1])),
                        this.floorShader.setUniformMatrix("uMatrix", "4fv", Ga.viewProjMatrix.data),
                        this.floorShader.bindBuffer(this.floorVertexBuffer, "aPosition"),
                        ia.drawArrays(ia.TRIANGLE_FAN, 0, this.floorVertexBuffer.numItems),
                        this.floorShader.disable()
                }
            },
            Ga.SkyWall.prototype.destroy = function() {
                this.texture.destroy()
            },
            Ga.Buildings = {
                init: function() {
                    this.shader = Ga.effects.shadows ? new aa.Shader({
                        vertexShader: _["buildings.shadows"].vertex,
                        fragmentShader: _["buildings.shadows"].fragment,
                        shaderName: "quality building shader",
                        attributes: ["aPosition", "aTexCoord", "aColor", "aFilter", "aNormal", "aId"],
                        uniforms: ["uFogDistance", "uFogBlurDistance", "uHighlightColor", "uHighlightId", "uLightColor", "uLightDirection", "uLowerEdgePoint", "uMatrix", "uModelMatrix", "uSunMatrix", "uShadowTexIndex", "uShadowTexDimensions", "uTime", "uViewDirOnMap", "uWallTexIndex"]
                    }) : new aa.Shader({
                        vertexShader: _.buildings.vertex,
                        fragmentShader: _.buildings.fragment,
                        shaderName: "building shader",
                        attributes: ["aPosition", "aTexCoord", "aColor", "aFilter", "aNormal", "aId"],
                        uniforms: ["uModelMatrix", "uViewDirOnMap", "uMatrix", "uNormalTransform", "uLightColor", "uLightDirection", "uLowerEdgePoint", "uFogDistance", "uFogBlurDistance", "uHighlightColor", "uHighlightId", "uTime", "uWallTexIndex"]
                    }),
                        this.wallTexture = new aa.texture.Image,
                        this.wallTexture.color([1, 1, 1]),
                        this.wallTexture.load(Aa)
                },
                render: function(a) {
                    var b = this.shader;
                    b.enable(),
                    this.showBackfaces && ia.disable(ia.CULL_FACE),
                        b.setUniforms([["uFogDistance", "1f", Ga.fogDistance], ["uFogBlurDistance", "1f", Ga.fogBlurDistance], ["uHighlightColor", "3fv", this.highlightColor || [0, 0, 0]], ["uHighlightId", "3fv", this.highlightId || [0, 0, 0]], ["uLightColor", "3fv", [.5, .5, .5]], ["uLightDirection", "3fv", Ha.direction], ["uLowerEdgePoint", "2fv", Ga.lowerLeftOnMap], ["uTime", "1f", Da.getTime()], ["uViewDirOnMap", "2fv", Ga.viewDirOnMap]]),
                    Ga.effects.shadows || b.setUniformMatrix("uNormalTransform", "3fv", aa.Matrix.identity3().data),
                        b.bindTexture("uWallTexIndex", 0, this.wallTexture),
                    a && (b.setUniform("uShadowTexDimensions", "2fv", [a.width, a.height]), b.bindTexture("uShadowTexIndex", 1, a.depthTexture));
                    for (var c, d, e = Ea.Index.items,
                             f = 0,
                             g = e.length; g > f; f++) c = e[f],
                    ha.zoom < c.minZoom || ha.zoom > c.maxZoom || !(d = c.getMatrix()) || (b.setUniformMatrices([["uModelMatrix", "4fv", d.data], ["uMatrix", "4fv", aa.Matrix.multiply(d, Ga.viewProjMatrix)]]), Ga.effects.shadows && b.setUniformMatrix("uSunMatrix", "4fv", aa.Matrix.multiply(d, Ha.viewProjMatrix)), b.bindBuffer(c.vertexBuffer, "aPosition"), b.bindBuffer(c.texCoordBuffer, "aTexCoord"), b.bindBuffer(c.normalBuffer, "aNormal"), b.bindBuffer(c.colorBuffer, "aColor"), b.bindBuffer(c.filterBuffer, "aFilter"), b.bindBuffer(c.idBuffer, "aId"), ia.drawArrays(ia.TRIANGLES, 0, c.vertexBuffer.numItems));
                    this.showBackfaces && ia.enable(ia.CULL_FACE),
                        b.disable()
                },
                destroy: function() {}
            },
            Ga.MapShadows = {
                init: function() {
                    this.shader = new aa.Shader({
                        vertexShader: _["basemap.shadows"].vertex,
                        fragmentShader: _["basemap.shadows"].fragment,
                        shaderName: "map shadows shader",
                        attributes: ["aPosition", "aNormal"],
                        uniforms: ["uModelMatrix", "uViewDirOnMap", "uMatrix", "uDirToSun", "uLowerEdgePoint", "uFogDistance", "uFogBlurDistance", "uShadowTexDimensions", "uShadowStrength", "uShadowTexIndex", "uSunMatrix"]
                    }),
                        this.mapPlane = new Fa.MapPlane
                },
                render: function(a, b, c) {
                    var d = this.shader;
                    d.enable(),
                    this.showBackfaces && ia.disable(ia.CULL_FACE),
                        d.setUniforms([["uDirToSun", "3fv", a.direction], ["uViewDirOnMap", "2fv", Ga.viewDirOnMap], ["uLowerEdgePoint", "2fv", Ga.lowerLeftOnMap], ["uFogDistance", "1f", Ga.fogDistance], ["uFogBlurDistance", "1f", Ga.fogBlurDistance], ["uShadowTexDimensions", "2fv", [b.width, b.height]], ["uShadowStrength", "1f", c]]),
                        d.bindTexture("uShadowTexIndex", 0, b.depthTexture);
                    var e = this.mapPlane;
                    if (! (ha.zoom < e.minZoom || ha.zoom > e.maxZoom)) {
                        var f; (f = e.getMatrix()) && (d.setUniformMatrices([["uModelMatrix", "4fv", f.data], ["uMatrix", "4fv", aa.Matrix.multiply(f, Ga.viewProjMatrix)], ["uSunMatrix", "4fv", aa.Matrix.multiply(f, a.viewProjMatrix)]]), d.bindBuffer(e.vertexBuffer, "aPosition"), d.bindBuffer(e.normalBuffer, "aNormal"), ia.drawArrays(ia.TRIANGLES, 0, e.vertexBuffer.numItems), this.showBackfaces && ia.enable(ia.CULL_FACE), d.disable())
                    }
                },
                destroy: function() {}
            },
            Ga.Basemap = {
                init: function() {
                    this.shader = new aa.Shader({
                        vertexShader: _.basemap.vertex,
                        fragmentShader: _.basemap.fragment,
                        shaderName: "basemap shader",
                        attributes: ["aPosition", "aTexCoord"],
                        uniforms: ["uModelMatrix", "uMatrix", "uTexIndex", "uFogDistance", "uFogBlurDistance", "uLowerEdgePoint", "uViewDirOnMap"]
                    })
                },
                render: function() {
                    var a = ha.basemapGrid;
                    if (a && !(ha.zoom < a.minZoom || ha.zoom > a.maxZoom)) {
                        var b, c = this.shader,
                            d = Math.round(ha.zoom);
                        c.enable(),
                            c.setUniforms([["uFogDistance", "1f", Ga.fogDistance], ["uFogBlurDistance", "1f", Ga.fogBlurDistance], ["uViewDirOnMap", "2fv", Ga.viewDirOnMap], ["uLowerEdgePoint", "2fv", Ga.lowerLeftOnMap]]);
                        for (var e in a.visibleTiles) if (b = a.tiles[e], b && b.isReady) this.renderTile(b, c);
                        else {
                            var f = [b.x / 2 << 0, b.y / 2 << 0, d - 1].join(",");
                            if (a.tiles[f] && a.tiles[f].isReady) this.renderTile(a.tiles[f], c);
                            else for (var g = [[2 * b.x, 2 * b.y, b.zoom + 1].join(","), [2 * b.x + 1, 2 * b.y, b.zoom + 1].join(","), [2 * b.x, 2 * b.y + 1, b.zoom + 1].join(","), [2 * b.x + 1, 2 * b.y + 1, b.zoom + 1].join(",")], h = 0; 4 > h; h++) a.tiles[g[h]] && a.tiles[g[h]].isReady && this.renderTile(a.tiles[g[h]], c)
                        }
                        c.disable()
                    }
                },
                renderTile: function(a, b) {
                    var c = wa * Math.cos(ha.position.latitude / 180 * Math.PI),
                        d = new aa.Matrix;
                    d.translate((a.longitude - ha.position.longitude) * c, -(a.latitude - ha.position.latitude) * wa, 0),
                        ia.enable(ia.POLYGON_OFFSET_FILL),
                        ia.polygonOffset(oa - a.zoom, oa - a.zoom),
                        b.setUniforms([["uViewDirOnMap", "2fv", Ga.viewDirOnMap], ["uLowerEdgePoint", "2fv", Ga.lowerLeftOnMap]]),
                        b.setUniformMatrices([["uModelMatrix", "4fv", d.data], ["uMatrix", "4fv", aa.Matrix.multiply(d, Ga.viewProjMatrix)]]),
                        b.bindBuffer(a.vertexBuffer, "aPosition"),
                        b.bindBuffer(a.texCoordBuffer, "aTexCoord"),
                        b.bindTexture("uTexIndex", 0, a.texture),
                        ia.drawArrays(ia.TRIANGLE_STRIP, 0, a.vertexBuffer.numItems),
                        ia.disable(ia.POLYGON_OFFSET_FILL)
                },
                destroy: function() {}
            },
            Ga.HudRect = {
                init: function() {
                    var a = this.createGeometry();
                    this.vertexBuffer = new aa.Buffer(3, new Float32Array(a.vertices)),
                        this.texCoordBuffer = new aa.Buffer(2, new Float32Array(a.texCoords)),
                        this.shader = new aa.Shader({
                            vertexShader: _.texture.vertex,
                            fragmentShader: _.texture.fragment,
                            shaderName: "HUD rectangle shader",
                            attributes: ["aPosition", "aTexCoord"],
                            uniforms: ["uMatrix", "uTexIndex"]
                        })
                },
                createGeometry: function() {
                    var a = [],
                        b = [];
                    return a.push(0, 0, 1e-5, 1, 0, 1e-5, 1, 1, 1e-5),
                        a.push(0, 0, 1e-5, 1, 1, 1e-5, 0, 1, 1e-5),
                        b.push(.5, .5, 1, .5, 1, 1),
                        b.push(.5, .5, 1, 1, .5, 1),
                    {
                        vertices: a,
                        texCoords: b
                    }
                },
                render: function(a) {
                    var b = this.shader;
                    b.enable(),
                        ia.uniformMatrix4fv(b.uniforms.uMatrix, !1, aa.Matrix.identity().data),
                        this.vertexBuffer.enable(),
                        ia.vertexAttribPointer(b.attributes.aPosition, this.vertexBuffer.itemSize, ia.FLOAT, !1, 0, 0),
                        this.texCoordBuffer.enable(),
                        ia.vertexAttribPointer(b.attributes.aTexCoord, this.texCoordBuffer.itemSize, ia.FLOAT, !1, 0, 0),
                        a.enable(0),
                        ia.uniform1i(b.uniforms.uTexIndex, 0),
                        ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                        b.disable()
                },
                destroy: function() {}
            },
            Ga.DepthFogNormalMap = function() {
                this.shader = new aa.Shader({
                    vertexShader: _.fogNormal.vertex,
                    fragmentShader: _.fogNormal.fragment,
                    shaderName: "fog/normal shader",
                    attributes: ["aPosition", "aFilter", "aNormal"],
                    uniforms: ["uMatrix", "uModelMatrix", "uNormalMatrix", "uTime", "uFogDistance", "uFogBlurDistance", "uViewDirOnMap", "uLowerEdgePoint"]
                }),
                    this.framebuffer = new aa.Framebuffer(128, 128, !0),
                    this.mapPlane = new Fa.MapPlane
            },
            Ga.DepthFogNormalMap.prototype.getDepthTexture = function() {
                return this.framebuffer.depthTexture
            },
            Ga.DepthFogNormalMap.prototype.getFogNormalTexture = function() {
                return this.framebuffer.renderTexture
            },
            Ga.DepthFogNormalMap.prototype.render = function(a, b, c, d) {
                var e = this.shader,
                    f = this.framebuffer,
                    g = new aa.Matrix(aa.Matrix.multiply(a, b));
                c = c || this.framebufferSize,
                    f.setSize(c[0], c[1]),
                    e.enable(),
                    f.enable(),
                    ia.viewport(0, 0, c[0], c[1]),
                    ia.clearColor(0, 0, 0, 1),
                    ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT);
                var h, i;
                e.setUniform("uTime", "1f", Da.getTime());
                for (var j = Ea.Index.items.concat([this.mapPlane]), k = 0; k < j.length; k++) h = j[k],
                ha.zoom < h.minZoom || ha.zoom > h.maxZoom || (i = h.getMatrix()) && (e.setUniforms([["uViewDirOnMap", "2fv", Ga.viewDirOnMap], ["uLowerEdgePoint", "2fv", Ga.lowerLeftOnMap], ["uFogDistance", "1f", Ga.fogDistance], ["uFogBlurDistance", "1f", Ga.fogBlurDistance]]), e.setUniformMatrices([["uMatrix", "4fv", aa.Matrix.multiply(i, g)], ["uModelMatrix", "4fv", i.data], ["uNormalMatrix", "3fv", aa.Matrix.transpose3(aa.Matrix.invert3(aa.Matrix.multiply(i, a)))]]), e.bindBuffer(h.vertexBuffer, "aPosition"), e.bindBuffer(h.normalBuffer, "aNormal"), e.bindBuffer(h.filterBuffer, "aFilter"), ia.drawArrays(ia.TRIANGLES, 0, h.vertexBuffer.numItems));
                e.disable(),
                    f.disable(),
                    ia.viewport(0, 0, ha.width, ha.height)
            },
            Ga.DepthFogNormalMap.prototype.destroy = function() {},
            Ga.AmbientMap = {
                init: function() {
                    this.shader = new aa.Shader({
                        vertexShader: _.ambientFromDepth.vertex,
                        fragmentShader: _.ambientFromDepth.fragment,
                        shaderName: "SSAO shader",
                        attributes: ["aPosition", "aTexCoord"],
                        uniforms: ["uInverseTexSize", "uNearPlane", "uFarPlane", "uDepthTexIndex", "uFogTexIndex", "uEffectStrength"]
                    }),
                        this.framebuffer = new aa.Framebuffer(128, 128),
                        this.vertexBuffer = new aa.Buffer(3, new Float32Array([ - 1, -1, 1e-5, 1, -1, 1e-5, 1, 1, 1e-5, -1, -1, 1e-5, 1, 1, 1e-5, -1, 1, 1e-5])),
                        this.texCoordBuffer = new aa.Buffer(2, new Float32Array([0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1]))
                },
                render: function(a, b, c, d) {
                    var e = this.shader,
                        f = this.framebuffer;
                    void 0 === d && (d = 1),
                        f.setSize(c[0], c[1]),
                        ia.viewport(0, 0, c[0], c[1]),
                        e.enable(),
                        f.enable(),
                        ia.clearColor(1, 0, 0, 1),
                        ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT),
                        e.setUniforms([["uInverseTexSize", "2fv", [1 / c[0], 1 / c[1]]], ["uEffectStrength", "1f", d], ["uNearPlane", "1f", 1], ["uFarPlane", "1f", 7500]]),
                        e.bindBuffer(this.vertexBuffer, "aPosition"),
                        e.bindBuffer(this.texCoordBuffer, "aTexCoord"),
                        e.bindTexture("uDepthTexIndex", 0, a),
                        e.bindTexture("uFogTexIndex", 1, b),
                        ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                        e.disable(),
                        f.disable(),
                        ia.viewport(0, 0, ha.width, ha.height)
                },
                destroy: function() {}
            },
            Ga.Overlay = {
                init: function() {
                    var a = this.createGeometry();
                    this.vertexBuffer = new aa.Buffer(3, new Float32Array(a.vertices)),
                        this.texCoordBuffer = new aa.Buffer(2, new Float32Array(a.texCoords)),
                        this.shader = new aa.Shader({
                            vertexShader: _.texture.vertex,
                            fragmentShader: _.texture.fragment,
                            shaderName: "overlay texture shader",
                            attributes: ["aPosition", "aTexCoord"],
                            uniforms: ["uMatrix", "uTexIndex"]
                        })
                },
                createGeometry: function() {
                    var a = [],
                        b = [];
                    return a.push( - 1, -1, 1e-5, 1, -1, 1e-5, 1, 1, 1e-5),
                        a.push( - 1, -1, 1e-5, 1, 1, 1e-5, -1, 1, 1e-5),
                        b.push(0, 0, 1, 0, 1, 1),
                        b.push(0, 0, 1, 1, 0, 1),
                    {
                        vertices: a,
                        texCoords: b
                    }
                },
                render: function(a, b) {
                    var c = this.shader;
                    c.enable(),
                        ia.disable(ia.DEPTH_TEST),
                        c.setUniformMatrix("uMatrix", "4fv", aa.Matrix.identity().data),
                        c.bindBuffer(this.vertexBuffer, "aPosition"),
                        c.bindBuffer(this.texCoordBuffer, "aTexCoord"),
                        c.bindTexture("uTexIndex", 0, a),
                        ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                        ia.enable(ia.DEPTH_TEST),
                        c.disable()
                },
                destroy: function() {}
            },
            Ga.OutlineMap = {
                init: function() {
                    this.shader = new aa.Shader({
                        vertexShader: _.outlineMap.vertex,
                        fragmentShader: _.outlineMap.fragment,
                        shaderName: "outline map shader",
                        attributes: ["aPosition", "aTexCoord"],
                        uniforms: ["uMatrix", "uInverseTexSize", "uNearPlane", "uFarPlane", "uDepthTexIndex", "uFogNormalTexIndex", "uIdTexIndex", "uEffectStrength"]
                    }),
                        this.framebuffer = new aa.Framebuffer(128, 128),
                        this.vertexBuffer = new aa.Buffer(3, new Float32Array([ - 1, -1, 1e-5, 1, -1, 1e-5, 1, 1, 1e-5, -1, -1, 1e-5, 1, 1, 1e-5, -1, 1, 1e-5])),
                        this.texCoordBuffer = new aa.Buffer(2, new Float32Array([0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1]))
                },
                render: function(a, b, c, d, e) {
                    var f = this.shader,
                        g = this.framebuffer;
                    void 0 === e && (e = 1),
                        g.setSize(d[0], d[1]),
                        ia.viewport(0, 0, d[0], d[1]),
                        f.enable(),
                        g.enable(),
                        ia.clearColor(1, 0, 0, 1),
                        ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT),
                        ia.uniformMatrix4fv(f.uniforms.uMatrix, !1, aa.Matrix.identity().data),
                        f.setUniforms([["uInverseTexSize", "2fv", [1 / d[0], 1 / d[1]]], ["uEffectStrength", "1f", e], ["uNearPlane", "1f", 1], ["uFarPlane", "1f", 7500]]),
                        f.bindBuffer(this.vertexBuffer, "aPosition"),
                        f.bindBuffer(this.texCoordBuffer, "aTexCoord"),
                        f.bindTexture("uDepthTexIndex", 0, a),
                        f.bindTexture("uFogNormalTexIndex", 1, b),
                        f.bindTexture("uIdTexIndex", 2, c),
                        ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                        f.disable(),
                        g.disable(),
                        ia.viewport(0, 0, ha.width, ha.height)
                },
                destroy: function() {}
            },
            Ga.Blur = function() {
                this.shader = new aa.Shader({
                    vertexShader: _.blur.vertex,
                    fragmentShader: _.blur.fragment,
                    shaderName: "blur shader",
                    attributes: ["aPosition", "aTexCoord"],
                    uniforms: ["uInverseTexSize", "uTexIndex"]
                }),
                    this.framebuffer = new aa.Framebuffer(128, 128),
                    this.vertexBuffer = new aa.Buffer(3, new Float32Array([ - 1, -1, 1e-5, 1, -1, 1e-5, 1, 1, 1e-5, -1, -1, 1e-5, 1, 1, 1e-5, -1, 1, 1e-5])),
                    this.texCoordBuffer = new aa.Buffer(2, new Float32Array([0, 0, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1]))
            },
            Ga.Blur.prototype.render = function(a, b) {
                var c = this.shader,
                    d = this.framebuffer;
                d.setSize(b[0], b[1]),
                    ia.viewport(0, 0, b[0], b[1]),
                    c.enable(),
                    d.enable(),
                    ia.clearColor(1, 0, 0, 1),
                    ia.clear(ia.COLOR_BUFFER_BIT | ia.DEPTH_BUFFER_BIT),
                    c.setUniform("uInverseTexSize", "2fv", [1 / d.width, 1 / d.height]),
                    c.bindBuffer(this.vertexBuffer, "aPosition"),
                    c.bindBuffer(this.texCoordBuffer, "aTexCoord"),
                    c.bindTexture("uTexIndex", 0, a),
                    ia.drawArrays(ia.TRIANGLES, 0, this.vertexBuffer.numItems),
                    c.disable(),
                    d.disable(),
                    ia.viewport(0, 0, ha.width, ha.height)
            },
            Ga.Blur.prototype.destroy = function() {
                this.framebuffer && this.framebuffer.destroy()
            };
        var Ia = {};
        Ia.Tile = function(a, b, c) {
            this.x = a,
                this.y = b,
                this.latitude = M(b, c),
                this.longitude = L(a, c),
                this.zoom = c,
                this.key = [a, b, c].join(",");
            var d = G(this.latitude, c),
                e = [d, d, 0, d, 0, 0, 0, d, 0, 0, 0, 0],
                f = [1, 0, 1, 1, 0, 0, 0, 1];
            this.vertexBuffer = new aa.Buffer(3, new Float32Array(e)),
                this.texCoordBuffer = new aa.Buffer(2, new Float32Array(f))
        },
            Ia.Tile.prototype = {
                load: function(a) {
                    la.setBusy(),
                        this.texture = (new aa.texture.Image).load(a,
                            function(a) {
                                la.setIdle(),
                                a && (this.isReady = !0, ia.bindTexture(ia.TEXTURE_2D, this.texture.id), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_S, ia.CLAMP_TO_EDGE), ia.texParameteri(ia.TEXTURE_2D, ia.TEXTURE_WRAP_T, ia.CLAMP_TO_EDGE))
                            }.bind(this))
                },
                destroy: function() {
                    this.vertexBuffer.destroy(),
                        this.texCoordBuffer.destroy(),
                    this.texture && this.texture.destroy()
                }
            }
    } ();