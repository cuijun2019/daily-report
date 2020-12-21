Ext.setup({
    icon: 'icon.png',
    tabletStartupScreen: 'tablet_startup.png',
    phoneStartupScreen: 'phone_startup.png',
    glossOnIcon: false,
    onReady: function() {

        //判断是否是手机设备，只有手机设备才使用此布局
        if (Ext.is.Phone) {
            new Ext.Panel({  //使用一个Panel来填满屏幕
                fullscreen: true,  //是否全屏，选择true则横竖屏切换时仍可以保证自动适应尺寸
                id: 'content',
                scroll: 'vertical',  //滚动方向
                html: '这里放置内容',  //Panel里面放置的内容
                layout: {type: 'vbox', align: 'center'},  //布局方式，vbox纵向布局，且每行内容居中
                items: [],  //这里放置容器内部的子元素
                dockedItems: []  //这里放置Panel本身所拥有的dock元素（你可以理解为自带工具栏）
            });
        }
    }
});