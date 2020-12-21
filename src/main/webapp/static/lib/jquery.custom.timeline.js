(function($){
    $.fn.TimeLine = function(options){
        var defaults = {
            data:null,
            vertical:false
        };
       //debugger;
        var options = $.extend(defaults,options);
        var _data = options.data;
        var _vertical = options.vertical;
        var _showDiv = $(this).addClass("timeline");
        var _ul = $("<ul />").appendTo(_showDiv);
        if(_vertical){
            _ul.addClass("ulvec");
        }
        else{
            _ul.addClass("ulhor");
        }
        for(var i= 0,dl=_data.length;i<dl;i++){
            var _li = $("<li />").appendTo(_ul);
            if(_vertical){
                _li.addClass("livec");
            }
            else{
                _li.addClass("lihor");
            }
            var _span = $("<span />").attr("value",_data[i].val).html(_data[i].label).appendTo(_li);
            _span.on("click",function(){
                var _value = this.getAttribute("value");
                //active(_value);
            });
        }
        function active(value){
            $("li").removeClass("active");
            var _spans = $("ul").find("span");
            for(var i= 0,dl=_spans.length;i<dl;i++){
                var _span = _spans[i];
                if(_span.getAttribute("value")===value){
                    var _li = $(_span.parentNode);
                    _li.addClass("active");
                }
            }
        }
        this.active = active;
        return this;
    }
})(jQuery);
