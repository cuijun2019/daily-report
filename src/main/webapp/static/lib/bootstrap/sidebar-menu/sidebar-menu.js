(function ($) {
    $.fn.sidebarMenu = function (options) {
        options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
        var target = $(this);
        target.addClass('nav');
        //target.addClass('nav-list');
        if (options.data) {
            init(target, options.data);
        }
        else {
            if (!options.url) return;
            $.ajaxSettings.async = false;   // 同步执行
            $.getJSON(options.url, options.param, function (data) {
                init(target, data);
            });
        }
        var url = window.location.pathname;
        //menu = target.find("[href='" + url + "']");
        //menu.parent().addClass('active');
        //menu.parent().parentsUntil('.nav-list', 'li').addClass('active').addClass('open');

        function init(target, data) {
            var arr = ['bg-violet', 'bg-blue', 'bg-red', 'bg-yellow', 'bg-grey', 'bg-pink', 'bg-dark', 'bg-primary',  'bg-orange', 'bg-green'];// div 的颜色数组
            $.each(data, function (i, item) {
                var li = $('<li></li>');
                var a = $('<a></a>');
                var icon = $('<i></i>');
                //icon.addClass('glyphicon');
                icon.addClass(item.icon);
                var text = $('<span></span>');
				
               
				if(!item.url){ //根据是否有url判断是否有子菜单
					 text.addClass('menu-title').text(item.text);

                     //一级菜单 在sidebar-colors菜单样式 加最左边的效果
                     var div = $('<div></div>');
                     div.addClass("icon-bg "+arr[Math.floor(Math.random()*10)]);//随机获取颜色
                     icon.append(div);

					 a.append(icon);
                     a.append(text);
				
					var text2 = $('<span></span>'); // 最右边的标志
					text2.addClass('fa arrow');
					a.append(text2);
				}else{
					text.addClass('submenu-title').text(item.text);
					a.append(icon);
                    a.append(text);
				}
                
                if (item.menus&&item.menus.length>0) {
                    a.attr('href', '#');
                    //a.addClass('dropdown-toggle');
                   /* var arrow = $('<b></b>'); //等同于上面的 text2 
                    arrow.addClass('arrow').addClass('icon-angle-down');
                    a.append(arrow);*/
                    li.append(a);
                    var menus = $('<ul></ul>');
                    menus.addClass('nav nav-second-level');
                    init(menus, item.menus);
                    li.append(menus);
                }
                else { //打开tab页的触发方法
                    if(item.id==273||item.id==274){
                        a.attr('href', item.url);
                        a.attr('title', item.text);
                        a.attr('target', '_blank');
                    }else{
                        var href = 'javascript:bs.addTabs({id:\'' + item.id + '\',title: \'' + item.text + '\',close: true,url: \'' + item.url + '\'});';
                        a.attr('href', href);
                    }
                    //if (item.istab)
                    //    a.attr('href', href);
                    //else {
                    //    a.attr('href', item.url);
                    //    a.attr('title', item.text);
                    //    a.attr('target', '_blank')
                    //}
                    li.append(a);
                }
                target.append(li);
            });
        }
    }

    $.fn.sidebarMenu.defaults = {
        url: null,
        param: null,
        data: null
    };
})(jQuery);